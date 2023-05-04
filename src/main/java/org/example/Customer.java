package org.example;

import java.io.Serializable;

public class Customer implements Serializable {

    // Serial version UID to ensure compatibility between serialized objects
    private static final long serialVersionUID = 1L;

    // Private instance variables
    private String name; // The name of the customer
    private String phoneNumber; // The phone number of the customer
    private Car rentedCar; // The car currently rented by the customer

    /**
     * Constructs a new Customer object with the specified name and phone number.
     *
     * @param name        The name of the customer
     * @param phoneNumber The phone number of the customer
     */
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Getter and setter methods for the private instance variables

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    // toString method for debugging and logging purposes

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", rentedCar=" + rentedCar +
                '}';
    }
}
