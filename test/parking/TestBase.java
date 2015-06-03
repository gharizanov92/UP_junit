package parking;

import java.lang.reflect.Method;

/**
 * Created by gharizanov on 3.6.2015 г..
 */
public class TestBase {
    public boolean hasSetMethod(String method, Class... params){
        try{
            Car.class.getMethod(method, params);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public void callSetMethod(Car instance, String method, Class paramType, Object value) throws Exception {
        Method setMethod = null;
        if(!hasSetMethod(method, paramType)){
            if(paramType.getName().contains("Integer")){
                setMethod = Car.class.getMethod(method, int.class);
                setMethod.invoke(instance, value);
            }
        } else {
            setMethod = Car.class.getMethod(method, paramType);
            setMethod.invoke(instance, paramType.cast(value));
        }
    }

    public boolean hasGetMethod(String method){
        try{
            Car.class.getMethod(method);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public Object callGetMethod(Car instance, String method) throws Exception{
        Method getMethod = Car.class.getMethod(method);
        return getMethod.invoke(instance);
    }
}
