package parking;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.fail;

public class CarTest extends TestBase {

    private static Car[] cars;
    private static Car car;

    public static Car newCarInstance() throws Exception{
        return Car.class.getConstructor().newInstance();
    }
    
    @Before
    public void setUp() throws Exception {
        car = Car.class.getConstructor().newInstance();
        cars = new Car[50];
        
        for (int i = 0; i < 50; i++) {
            int modelInt = (int)(Math.random() + 0.5);
            cars[i] = new Car(modelInt == 0 ? "Opel" : "Volvo", i % 12);
        }
    }

    @Test
    public void testDeclaredFields() throws Exception{
        Field[] aFields = Car.class.getFields();
        for (Field aField : aFields) {
            if(!Modifier.isPrivate(aField.getModifiers())){
                System.err.println(String.format("Declared field \"%s\" is not private!",aField.getName()));
                fail();
            }
        }
        Field nomer = Car.class.getDeclaredField("NOMER");
        assert Modifier.isFinal(nomer.getModifiers());
    }

    @Test
    public void testDefaultConstructor() throws Exception{
        Car car = newCarInstance();
        assert callGetMethod(car, "getModel") != null;
        assert callGetMethod(car, "getModel").equals("Неизвестен");
        assert callGetMethod(car, "getAbonament").equals(1);
    }

    @Test
    public void testConstructor(){
        try{
            Constructor<Car> ctr = Car.class.getConstructor(String.class, Integer.TYPE);
            Car car = ctr.newInstance("Volvo",5);
            assert callGetMethod(car, "getModel").equals("Volvo");
            assert callGetMethod(car, "getAbonament").equals(5);
        } catch (Exception ex){
            try{
                Constructor<Car> ctr = Car.class.getConstructor(Integer.TYPE, String.class);
                ctr.newInstance(5, "Volvo");
                assert callGetMethod(car, "getModel").equals("Volvo");
                assert callGetMethod(car, "getAbonament").equals(5);
            } catch (Exception ex2){
                fail();
            }
        }
    }

    @Test
    public void testCopyCtr() throws Exception{
        Car car1 = newCarInstance();
        callSetMethod(car1,"setModel", String.class, "Volvo");
        callSetMethod(car1,"setAbonament", Integer.class, 2);
        Car car2 = Car.class.getConstructor(Car.class).newInstance(car1);
        assert callGetMethod(car2, "getModel").equals("Volvo");
        assert callGetMethod(car2, "getAbonament").equals(2);
    }
    
    @Test
    public void testSetModel() throws Exception{
        String methodToCall = "setModel";
        
        assert hasSetMethod(methodToCall, String.class);
        callSetMethod(car, methodToCall, String.class, "Volvo");
        assert callGetMethod(car, "getModel").equals("Volvo");
    }
 
    @Test
    public void testSetAbonament() throws Exception{
        String methodToCall = "setAbonament";
   
        assert hasSetMethod(methodToCall, int.class);
        callSetMethod(car, methodToCall, Integer.class, 5);
        assert callGetMethod(car, "getAbonament").equals(5);
        callSetMethod(car, methodToCall, Integer.class, 15);
        assert !callGetMethod(car, "getAbonament").equals(15);
        callSetMethod(car, methodToCall, Integer.class, -15);
        assert !callGetMethod(car, "getAbonament").equals(-15);
    }
    
    @Test
    public void testToString(){
        assert hasGetMethod("toString");
        
        try{
            callSetMethod(car, "setModel", String.class, "Volvo");
            callSetMethod(car, "setAbonament", Integer.class, 5);
            String result = (String)callGetMethod(car, "toString");
            assert(result.contains("Volvo"));
            assert(result.contains("5"));
        } catch(Exception ex){
            fail();
        }
    }
    
    @Test
    public void testUniqueId() throws Exception{
        Car car1 = newCarInstance();
        Car car2 = newCarInstance();
        String methodName = "getNOMER";
        
        try{    
            if(!hasGetMethod(methodName)){
                methodName = "getNomer";
                if(!hasGetMethod(methodName)){
                    methodName = "getNumber";
                    if(!hasGetMethod(methodName)){
                        fail();
                    }
                }
            }
            
            Object number1 = callGetMethod(car1, methodName);
            Object number2 = callGetMethod(car2, methodName);
            
            assert(!number1.equals(number2));
            assert number1 instanceof String;
            assert ((String) number1).contains("CВ ");
            assert ((String) number1).length() == 7;

        } catch(Exception ex){
            
        }
        
    }
    
    @Test
    public void testSomeMethod() {
        assert cars.length == 50;
    }
}
