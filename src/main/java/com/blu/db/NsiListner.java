package com.blu.db;

import oracle.jdbc.dcn.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shamim on 21/07/15.
 */
public class NsiListner implements DatabaseChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsiListner.class);

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent databaseChangeEvent) {
        QueryChangeDescription qcd =  databaseChangeEvent.getQueryChangeDescription()[0];
        TableChangeDescription tcd = qcd.getTableChangeDescription()[0];
        for(RowChangeDescription rcd : tcd.getRowChangeDescription()){
            LOGGER.info("Updated Row ID:" + rcd.getRowid().stringValue() + " Operation:" + rcd.getRowOperation().name());
        }
    }
}
