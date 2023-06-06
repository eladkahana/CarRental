package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarRental implements Serializable {

    private final ArrayList<Car> availableCars;
    private final ArrayList<Car> rentedCars;
    private final ArrayList<Customer> customers;

    private final HashMap<Car, Customer> recording;

    private final Lock lock = new ReentrantLock();

    public CarRental(ArrayList<Car> cars) {
        // Make defensive copy of cars list to prevent external modifications
        this.availableCars = new ArrayList<>(cars);
        this.rentedCars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.recording = new HashMap<>();
    }

    public ArrayList<Car> getAvailableCars() {
        // Return defensive copy of availableCars to prevent external modifications
        return new ArrayList<>(availableCars);
    }

    public ArrayList<Car> getRentedCars() {
        // Return defensive copy of rentedCars to prevent external modifications
        return new ArrayList<>(rentedCars);
    }

    public ArrayList<Customer> getCustomers() {
        // Return defensive copy of customers to prevent external modifications
        return new ArrayList<>(customers);
    }

    public HashMap<Car, Customer> getRecording() {
        // Return defensive copy of recording to prevent external modifications
        return new HashMap<>(recording);
    }

    public synchronized void cancel(Car car){
        //sey available for the car
        car.setAvailable(true);
    }

    public synchronized boolean reserve(Car car) {
        if (car.isAvailable()) {
            car.setAvailable(false);
            new Thread(() -> {
                try {
                    Thread.sleep(300000); // wait for 5 minutes (300 seconds)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    synchronized(this) {
                        if (!rentedCars.contains(car)) {
                            car.setAvailable(true);
                        }
                    }
                }
            }).start();
            return true;
        } else {
            return false;
        }
    }

    public synchronized void rent(Car car, Customer customer) {

            // critical section of code

            customers.add(customer);
            car.setAvailable(false);
            customer.setRentedCar(car);

            availableCars.remove(car);
            rentedCars.add(car);

            recording.put(car, customer);

    }

    public synchronized void carReturn(Car car) {

            // critical section of code
            Customer customer = recording.get(car);
            car.setAvailable(true);
            customer.setRentedCar(null);
            availableCars.add(car);
            rentedCars.remove(car);
            recording.remove(car);

    }
}
