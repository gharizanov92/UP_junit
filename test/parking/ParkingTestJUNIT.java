package parking;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.fail;

public class ParkingTestJUNIT {

    public static Parking newParkingInstance() throws Exception{
        return Parking.class.getConstructor().newInstance();
    }

    public boolean hasSetMethod(String method, Class... params){
        try{
            Parking.class.getMethod(method, params);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public void callSetMethod(Parking instance, String method, Class paramType, Object value) throws Exception {
        Method setMethod = null;
        if(!hasSetMethod(method, paramType)){
            if(paramType.getName().contains("Integer")){
                setMethod = Parking.class.getMethod(method, int.class);
                setMethod.invoke(instance, value);
            }
        } else {
            setMethod = Parking.class.getMethod(method, paramType);
            setMethod.invoke(instance, paramType.cast(value));
        }
    }

    public boolean hasGetMethod(String method){
        try{
            Parking.class.getMethod(method);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public Object callGetMethod(Parking instance, String method) throws Exception{
        Method getMethod = Parking.class.getMethod(method);
        return getMethod.invoke(instance);
    }

    public Car[] generateCarArray() throws Exception{
        Car[] cars = new Car[50];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = CarTest.newCarInstance();
            CarTest.callSetMethod(cars[i], "setAbonament", Integer.class, (int)(Math.random() * 12) + 1);
        }
        return cars;
    }

    @Test
    public void testDeclaredFields() throws Exception{
        Field[] aFields = Parking.class.getFields();
        for (Field aField : aFields) {
            if(!Modifier.isPrivate(aField.getModifiers())){
                if(!Modifier.isPublic(aField.getModifiers()) && !Modifier.isFinal(aField.getModifiers())){
                    System.err.println(String.format("Declared field \"%s\" is not private!",aField.getName()));
                    fail();
                }
            }
        }
        Field nomer = Parking.class.getDeclaredField("TOTAL_CARS");
        assert Modifier.isFinal(nomer.getModifiers());
        assert Modifier.isPublic(nomer.getModifiers());
    }

    @Test
    public void testDefaultConstructor() throws Exception{
        Parking parking = newParkingInstance();
        assert callGetMethod(parking, "getCars") != null;
        assert ((Car[])callGetMethod(parking, "getCars")).length == 50;
        assert callGetMethod(parking, "getLocation") != null;
    }

    @Test
    public void testConstructor(){
        Parking parking = null;
        try{
            Constructor<Parking> ctr = Parking.class.getConstructor(String.class, Car[].class);
            parking = ctr.newInstance("Test",generateCarArray());
            assert callGetMethod(parking, "getCars") != null;
            assert ((Car[])callGetMethod(parking, "getCars")).length == 50;
            assert callGetMethod(parking, "getLocation") == "Test";
        } catch (Exception ex){
            try{
                Constructor<Parking> ctr = Parking.class.getConstructor(Car[].class, String.class);
                parking = ctr.newInstance(generateCarArray(), "Volvo");
                assert callGetMethod(parking, "getCars") != null;
                assert ((Car[])callGetMethod(parking, "getCars")).length == 50;
                assert callGetMethod(parking, "getLocation") == "Test";
            } catch (Exception ex2){
                fail();
            }
        }
    }

    @Test
    public void testCopyCtr() throws Exception{
        Parking parking1 = newParkingInstance();
        callSetMethod(parking1,"setCars", Car[].class, generateCarArray());
        callSetMethod(parking1,"setLocation", String.class, "Test");
        Parking parking2 = Parking.class.getConstructor(Parking.class).newInstance(parking1);
        Car[] parking1Cars = (Car[])callGetMethod(parking1, "getCars");
        Car[] parking2Cars = (Car[])callGetMethod(parking2, "getCars");

        for (int i = 0; i < parking2Cars.length; i++) {
            Car car1 = parking1Cars[i];
            Car car2 = parking2Cars[i];

            assert CarTest.callGetMethod(car1, "getModel").equals( CarTest.callGetMethod(car2, "getModel"));
            assert CarTest.callGetMethod(car1, "getAbonament").equals( CarTest.callGetMethod(car2, "getAbonament"));
        }

        assert callGetMethod(parking2, "getLocation").equals("Test");
    }

    @Test
    public void testSetCars() throws Exception{
        Parking parking = newParkingInstance();
        Car[] cars = generateCarArray();
        callSetMethod(parking, "setCars", Car[].class, cars);
        CarTest.callSetMethod(cars[0], "setAbonament", Integer.class, 12);
        Car[] cars2 = (Car[])callGetMethod(parking, "getCars");
        Integer abonament = (Integer)CarTest.callGetMethod(cars2[0], "getAbonament");
        assert abonament != 12;
    }

    @Test
    public void testSetLocation() throws Exception{
        Parking aParking = newParkingInstance();
        String methodToCall = "setLocation";

        assert hasSetMethod(methodToCall, String.class);
        callSetMethod(aParking, methodToCall, String.class, "Test");
        assert callGetMethod(aParking, "getLocation").equals("Test");
    }

    @Test
    public void testSorted() throws Exception {

    }



}
