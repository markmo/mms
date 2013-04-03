package mms.common.models.factory;

import java.util.ArrayList;
import java.util.List;

import mms.common.models.FilterType;

/**
 * User: markmo
 * Date: 24/11/12
 * Time: 6:47 PM
 */
public class FilterTypeFactory {

    private static List<FilterType> filterTypes;

    private static void initialize() {
        filterTypes = new ArrayList<FilterType>();
    }

    static {
        initialize();
    }

    public static FilterType getFilterType(String typeName) {
        if (typeName == null) {
            return null;
        }
        for (FilterType filterType : filterTypes) {
            if (filterType.getName().equals(typeName)) {
                return filterType;
            }
        }
        FilterType filterType = new FilterType();
        filterType.setName(typeName);
        return filterType;
    }
}
