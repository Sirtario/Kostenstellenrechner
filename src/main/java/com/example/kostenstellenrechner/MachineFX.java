package com.example.kostenstellenrechner;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class MachineFX {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleFloatProperty cost = new SimpleFloatProperty();

    public MachineFX(String name,float cost) {
        this.name.set(name);
        this.cost.set(cost);
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public float getCost() {
        return cost.get();
    }
    public void setCost(float cost) {
        this.cost.set(cost);
    }
}
