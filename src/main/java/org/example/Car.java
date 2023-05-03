package org.example;

import java.io.Serializable;

public class Car implements Serializable {
    private int ID;
    private String make;
    private String model;
    private boolean isAvailable;


    public Car(int ID, String make, String model) {
        this.ID = ID;
        this.make = make;
        this.model = model;
        this.isAvailable = true;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
