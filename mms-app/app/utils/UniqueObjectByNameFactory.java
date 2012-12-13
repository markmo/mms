package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: markmo
 * Date: 27/11/12
 * Time: 8:28 PM
 */
public class UniqueObjectByNameFactory {

    private static Map<Class, Map<String, Object>> cache;

    public static <T> create(<T> obj) {
        if (obj == null || )
        Object ret = null;
        try {
            Method getter = type.getDeclaredMethod("getName");
            String name = (String)getter.invoke(obj);
            if (cache == null) {
                cache = new HashMap<Class, Map<String, Object>>();
            }
            if (!cache.containsKey(type)) {
                cache.put(type, new HashMap<String, Object>());
            } else {

            }
            ret = type.newInstance();
            Method setter = type.getDeclaredMethod("setName");
            setter.invoke(ret, name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
