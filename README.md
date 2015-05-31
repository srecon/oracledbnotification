# oracledbnotification
Example for using oracle DB notification with storm to export data to Cassandra
Start Kafka standalone
start zookeeper
$bin/zookeeper-server-start.sh config/zookeeper.properties
start kafka
$bin/kafka-server-start.sh config/server.properties
create topic test
$bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
start consulmer on topic test
$bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning
# table temp
truncate table temp;

insert into temp(a,b) values ('a','b');
insert into temp(a,b) values ('a','b');
insert into temp(a,b) values ('a','b');
insert into temp(a,b) values ('a','b');
insert into temp(a,b) values ('a','b');
commit;