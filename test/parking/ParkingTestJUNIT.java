package parking;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.fail;

public class ParkingTestJUNIT extends TestBase{

    private static Car[] cars;
    private static Car car;

    public static Parking newParkingInstance() throws Exception{
        return Parking.class.getConstructor().newInstance();
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

}
