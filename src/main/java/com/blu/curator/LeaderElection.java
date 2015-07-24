package com.blu.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shamim on 24/07/15.
 */
public class LeaderElection {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderElection.class);
    private static final String ZOOKEEPER_URI="127.0.0.1:2181";

    public static void main(String[] args) {
        LOGGER.info("Leader election!!");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPER_URI, retryPolicy);
        //client.start();
        // create a znode with mydata
/*        try {
            client.create().forPath("/zk_test", "mydata".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // leader ship
        LeaderSelector leaderSelector = new LeaderSelector(client, "/zk_test", new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                // we are the leader now, This method should not return until we want to relinquish leadership
                final int         waitSeconds = (int)(5 * Math.random()) + 1;
                final String name = "first";
                final AtomicInteger leaderCount = new AtomicInteger();

                System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
                System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
                try{
                    Thread.sleep(waitSeconds);

                } catch(InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();

                } finally{
                    LOGGER.info(name+"relinquishing leadership.\\n");
                }
            }

            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                LOGGER.info("State changed for first");
            }
        });

        //leaderSelector.autoRequeue();
        client.start();
        leaderSelector.start();

    }

}
