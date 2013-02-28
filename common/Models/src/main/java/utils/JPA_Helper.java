package utils;

import javax.persistence.Query;
import java.util.List;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:26 PM
 */
public class JPA_Helper {

    @SuppressWarnings("unchecked")
    public static <T> T getSingleResult(Class<T> clazz, Query q) {
        List<T> resultList = q.setMaxResults(1).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }
}
