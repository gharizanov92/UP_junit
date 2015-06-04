/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

/**
 *
 * @author georgi
 */
public class Parking {
    public static final int TOTAL_CARS = 50;
    private Car[] cars;
    private String location;
    public final String UNKNOWN_LOCATION = "unknown";

    public Parking() {
        this.cars = new Car[TOTAL_CARS];
        this.location = UNKNOWN_LOCATION;
    }

    public Parking(String location, Car[] cars) {
        this();
        setCars(cars);
        setLocation(location);
    }
    
    public Parking(Parking other){
        this(other.getLocation(), other.getCars());
    }

    public Car[] getCars() {
        Car[] result = new Car[TOTAL_CARS];
        for (int i = 0; i < result.length; i++) {
            if(cars[i] != null){
                result[i] = new Car(cars[i]);
            }
        }
        return result;
    }

    public void setCars(Car[] cars) {
        for (int i = 0; i < cars.length; i++) {
            if(cars[i] != null){
                this.cars[i] = new Car(cars[i]);
            }
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location == null){
            this.location = UNKNOWN_LOCATION;
        } else {
            this.location = location;
        }
    }
    
    public void sortCars(){
        for (int i = 0; i < cars.length; i++) {
            for (int j = 0; j < cars.length - i - 1; j++) {
                if(cars[j].getAbonament() < cars[j + 1].getAbonament()){
                    Car temp = cars[j];
                    cars[j] = cars[j + 1];
                    cars[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public String toString() {
        String carsStr = "[";
        for (int i = 0; i < cars.length; i++) {
            carsStr += String.format("car ID %s\tSubscription:%d\n", cars[i].getNOMER(), cars[i].getAbonament());
        }
        return String.format("Address: %s, cars: %s", this.location, carsStr);
    }
    
}
