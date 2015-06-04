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
public class Car {
    private final String NOMER;
    private String model;
    private int abonament;
    private static int counter = 1000;
    
    public Car() {
        counter++;
        this.NOMER = "CВ " + counter;
        setAbonament(1);
        setModel("Неизвестен");
    }

    public Car(String model, int abonament) {
        this();
        this.model = model;
        this.abonament = abonament;
    }
    
    public Car(Car other){
        this(other.getModel(), other.getAbonament());
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getAbonament() {
        return abonament;
    }

    public void setAbonament(int abonament) {
        if(abonament < 1 || abonament > 12){
            this.abonament = 1;
        } else {
            this.abonament = abonament;
        }
    }

    public String getNOMER() {
        return NOMER;
    }

    @Override
    public String toString() {
        return String.format("Кола:%s, Номер:%s \n Брой платени месеци: %d", this.model, this.NOMER, this.abonament);
    }
    
    
}
