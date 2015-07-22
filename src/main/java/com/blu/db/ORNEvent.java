package com.blu.db;

/**
 * Created by shamim on 22/07/15.
 */
public class ORNEvent {
    /*
    * Operation name, update, insert, delete
    * */
    private String operationName;
    /*
    * rowid
    * */
    private String rowId;

    public ORNEvent(String operationName, String rowId) {
        this.operationName = operationName;
        this.rowId = rowId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getRowId() {
        return rowId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ORNEvent ornEvent = (ORNEvent) o;

        if (operationName != null ? !operationName.equals(ornEvent.operationName) : ornEvent.operationName != null)
            return false;
        if (!rowId.equals(ornEvent.rowId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operationName != null ? operationName.hashCode() : 0;
        result = 31 * result + rowId.hashCode();
        return result;
    }
}
