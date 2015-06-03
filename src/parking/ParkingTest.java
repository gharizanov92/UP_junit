package parking;
import java.util.Arrays;
import parking.*;

public class ParkingTest {
    public static void main(String[] args) {
        Car[] cars = new Car[Parking.TOTAL_CARS];
        
        for (int i = 0; i < Parking.TOTAL_CARS; i++) {
            int modelInt = (int)(Math.random() + 0.5);
            cars[i] = new Car(modelInt == 0 ? "Opel" : "Volvo", 
                    ((int)(Math.random() * 11) + 1));
        }
        
        Parking parking = new Parking("Sofia", cars);
        parking.sortCars();
        System.out.println(parking);
        
    }
    
}
