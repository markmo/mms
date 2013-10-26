package utils;

import java.util.Arrays;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * User: markmo
 * Date: 7/03/13
 * Time: 4:38 PM
 */
public class BeanUtils {

    public static void copyProperties(
            final Object source,
            final Object target,
            final String[] properties) {

        copyProperties(source, target, Arrays.asList(properties));
    }

    public static void copyProperties(
            final Object source,
            final Object target,
            final Iterable<String> properties) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper tgt = new BeanWrapperImpl(target);

        for (final String propertyName : properties) {
            tgt.setPropertyValue(
                    propertyName,
                    src.getPropertyValue(propertyName)
            );
        }
    }
}
