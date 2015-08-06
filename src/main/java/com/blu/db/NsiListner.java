package com.blu.db;

import com.google.common.collect.Lists;
import oracle.jdbc.dcn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shamim on 21/07/15.
 */
public class NsiListner implements DatabaseChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsiListner.class);
    private HashMap<Integer, List<ORNEvent>> events = new HashMap<Integer, List<ORNEvent>>();

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent databaseChangeEvent) {
        for(QueryChangeDescription qcd : databaseChangeEvent.getQueryChangeDescription()){
            LOGGER.info("Query Id: {}", qcd.getQueryId());
            LOGGER.info("Event Type: {}", qcd.getQueryChangeEventType().name());
            Integer tableId = null;
            final List<ORNEvent> ornEvents = Lists.newArrayList();
            for(TableChangeDescription tcd : qcd.getTableChangeDescription()){
                //ClassDescriptor descriptor = OracleChangeNotificationListener.this.descriptorsByTable.get(new DatabaseTable(tcd.getTableName()));
                LOGGER.info("table Name: {}", tcd.getTableName()); // table name is empty
                LOGGER.info("Object ID: {}", tcd.getObjectNumber()); // use object id
                tableId = tcd.getObjectNumber();
                for(RowChangeDescription rcd : tcd.getRowChangeDescription()){
                    LOGGER.info("Row ID:" + rcd.getRowid().stringValue() + " Operation:" + rcd.getRowOperation().name());
                    ornEvents.add(new ORNEvent(rcd.getRowOperation().name(), rcd.getRowid().stringValue()));
                }
            }
            if(tableId!=null){
                events.put(tableId, ornEvents);
            }

        }

    }

    public HashMap<Integer, List<ORNEvent>> getEvents() {
        return events;
    }
}
