package utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * User: markmo
 * Date: 25/11/12
 * Time: 8:52 PM
 */
public class CollectionUtils {

    public static <T> List<T> safe(List<T> other) {
        return other == null ? Collections.<T>emptyList() : other;
    }

    public static <T> Set<T> safe(Set<T> other) {
        return other == null ? Collections.<T>emptySet() : other;
    }
}
