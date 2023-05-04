package org.example;

import java.io.Serializable;

public class Car implements Serializable {

    // Serial version UID to ensure compatibility between serialized objects
    private static final long serialVersionUID = 1L;

    // Private instance variables
    private String make;  // The make of the car (e.g. "Ford")
    private String model; // The model of the car (e.g. "Mustang")
    private boolean isAvailable; // Whether the car is currently available for use

    /**
     * Constructs a new Car object with the specified make and model.
     *
     * @param make  The make of the car
     * @param model The model of the car
     */
    public Car(String make, String model) {
        this.make = make;
        this.model = model;
        this.isAvailable = true; // The car is available by default
    }

    // Getter and setter methods for the private instance variables

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

    // toString method for debugging and logging purposes

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
