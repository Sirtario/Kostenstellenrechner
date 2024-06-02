package com.example.kostenstellenrechner;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class DataFX {

    private SimpleStringProperty machineName;
    private SimpleStringProperty fromDate;
    private SimpleStringProperty toDate;

public DataFX(String machineName, Date fromDate, Date toDate) {
    this.machineName = new SimpleStringProperty(machineName);
    this.fromDate = new SimpleStringProperty(fromDate.toString());
    this.toDate = new SimpleStringProperty(toDate.toString());

}
    public String getToDate() {
        return toDate.get();
    }

    public void setToDate(String toDate) {
        this.toDate.set(toDate);
    }

    public String getFromDate() {
        return fromDate.get();
    }

    public void setFromDate(String fromDate) {
        this.fromDate.set(fromDate);
    }

    public String getMachineName() {
        return machineName.get();
    }

    public void setMachineName(String machineName) {
        this.machineName.set(machineName);
    }
}
