package nz.co.datascience.mms.analyzers;

import mms.common.models.Cell;
import nz.co.datascience.mms.util.Counter;
import nz.co.datascience.mms.util.Counters;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nz.co.datascience.mms.analyzers.Types.*;

/**
 * User: markmo
 * Date: 16/10/12
 * Time: 9:34 PM
 */
public class TypeAnalyzer implements Analyzer {

    static final String MONTHS = "j f m a s o n d jan feb mar apr may jun jul aug sep sept oct nov dec january february march april may june july august september october november december";
    static final String DAYS = "m t w th f s mon tue wed thu fri sat sun monday tuesday wednesday thursday friday saturday sunday";
    static final String QUARTERS = "q qtr quarter";
    static final String WEEKS = "w wk week";
    static Set<String> months;
    static Set<String> days;
    static Set<String> quarters;
    static Set<String> weeks;

    static {
        months = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(months, MONTHS.split(" "));
        days = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(days, DAYS.split(" "));
        quarters = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(quarters, QUARTERS.split(" "));
        weeks = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        Collections.addAll(weeks, WEEKS.split(" "));
    }

    public AnalysisResult analyze(List<Cell> cells) {
        List<Object> values = new ArrayList<Object>();
        Counter<Object> valueCounter = new Counter<Object>();
        Counter<String> typeCounter = new Counter<String>();

        IndexMap<Types, Integer> indexes = new IndexMap<Types, Integer>();
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            String dataType = cell.getDataType().getName();

            if (dataType.equals("String")) {
                indexes.add(STRING, i);
                values.add(cell.getStringValue());
                valueCounter.incrementCount(cell.getStringValue(), 1);
                typeCounter.incrementCount("String", 1);

            } else if (dataType.equals("Numeric")) {
                double val = cell.getNumericValue();
                if (val % 1 == 0) { // is an integer
                    indexes.add(INTEGER, i);
                } else {
                    indexes.add(NUMERIC, i);
                }
                values.add(cell.getNumericValue());
                valueCounter.incrementCount(cell.getNumericValue(), 1);
                typeCounter.incrementCount("Numeric", 1);

            } else if (dataType.equals("Date")) {
                indexes.add(DATE, i);
                values.add(cell.getDateValue());
                valueCounter.incrementCount(cell.getDateValue(), 1);
                typeCounter.incrementCount("Date", 1);

            } else if (dataType.equals("Boolean")) {
                indexes.add(BOOLEAN, i);
                values.add(cell.getBooleanValue());
                valueCounter.incrementCount(cell.getBooleanValue(), 1);
                typeCounter.incrementCount("Boolean", 1);

            } else if (dataType.equals("Blank")) {
                indexes.add(BLANK, i);
                values.add(null);
                valueCounter.incrementCount("NA", 1);
                typeCounter.incrementCount("Blank", 1);

            } else {
                indexes.add(OTHER, i);
                values.add(cell.getStringValue());
                valueCounter.incrementCount(cell.getStringValue(), 1);
                typeCounter.incrementCount("Other", 1);
            }
        }

        int n = cells.size() - indexes.size(BLANK) - indexes.size(OTHER);
        boolean isUniform = (indexes.size(STRING) == n
                || indexes.size(INTEGER, NUMERIC) == n
                || indexes.size(DATE) == n
                || indexes.size(BOOLEAN) == n
        );

        String type = null;
        Object minValue = null;
        Object maxValue = null;
        if (isUniform) {
            if (!indexes.isEmpty(STRING)) {
                type = "String";

                List<String> stringValues = new ArrayList<String>();
                for (Object val : values) {
                    if (val != null) {
                        stringValues.add(val.toString());
                    }
                }
                Collections.sort(stringValues);
                minValue = stringValues.get(0);
                maxValue = stringValues.get(stringValues.size() - 1);

            } else if (!indexes.isEmpty(INTEGER, NUMERIC)) {
                type = "Numeric";

                List<Double> numericValues = new ArrayList<Double>();
                for (Object val : values) {
                    try {
                        numericValues.add(Double.parseDouble(val.toString()));
                    } catch (Exception e) {
                        numericValues.add(Double.NaN);
                    }
                }
                Collections.sort(numericValues);
                minValue = numericValues.get(0);
                maxValue = numericValues.get(numericValues.size() - 1);

            } else if (!indexes.isEmpty(DATE)) {
                type = "Date";

                List<Date> dateValues = new ArrayList<Date>();
                for (Object val : values) {
                    try {
                        dateValues.add((Date)val);
                    } catch (Exception e) {
                        dateValues.add(null);
                    }
                }
                Collections.sort(dateValues);
                minValue = dateValues.get(0);
                maxValue = dateValues.get(dateValues.size() - 1);

            } else if (!indexes.isEmpty(BOOLEAN)) {
                type = "Boolean";
                minValue = false;
                maxValue = true;
            } else {
                type = "Other";
            }
        } else {
            type = "Mixed";
            List<String> stringValues = new ArrayList<String>();
            for (Object val : values) {
                stringValues.add(val.toString());
            }
            Collections.sort(stringValues);
            minValue = stringValues.get(0);
            maxValue = stringValues.get(stringValues.size() - 1);
        }

        PatternSet patternSet = new PatternSet(values);
        ValueSet valueSet = new ValueSet(values);

        boolean isYear = false;
        boolean isMonth = false;
        boolean isWeek = false;
        boolean isDay = false;
        boolean isQuarter = false;
        boolean isDate = false;
        if (type.equals("Numeric")) {

            if (patternSet.equalsMask("9999") &&
                    valueSet.test(3, new Predicate<Object>() {
                        public boolean apply(Object value) {
                            Integer intValue = (Integer)value;
                            return (intValue > 1900 && intValue < 2100);
                        }
                    }))
            {
                isYear = true;
                type = "Date";
            }

        } else if (type.equals("String")) {

            if (patternSet.matches("a{1,9}", true)) {
                if (valueSet.equalsSet(months, true)) {
                    isMonth = true;
                } else if (valueSet.equalsSet(days, true)) {
                    isDay = true;
                }
                type = "Date";

            } else if (patternSet.matches("a{1,4}\\s*9", true) &&
                valueSet.test(3, new Predicate<Object>() {
                    public boolean apply(Object value) {
                        for (String token : weeks) {
                            if (((String)value).toLowerCase().contains(token)) {
                                return true;
                            }
                        }
                        return false;
                    }
                }))
            {
                isWeek = true;
                type = "Date";

            } else if (patternSet.matches("(a{1,7}\\s*9|9aa\\s+a{1,7}|9a)", true) &&
                valueSet.test(3, new Predicate<Object>() {
                    public boolean apply(Object value) {
                        for (String token : quarters) {
                            if (((String)value).toLowerCase().contains(token)) {
                                return true;
                            }
                        }
                        return false;
                    }
                }))
            {
                isQuarter = true;
                type = "Date";

            } else if (patternSet.matches("9{1,2}\\.9{1,2}\\.9{2,4}", true) &&
                valueSet.test(3, new Predicate<Object>() {
                    public boolean apply(Object value) {
                        Pattern pattern = Pattern.compile("^(\\d+)");
                        Matcher matcher = pattern.matcher((String)value);
                        if (matcher.find()) {
                            Integer n = Integer.parseInt(matcher.group(1));
                            return (n > 0 && n < 32);
                        }
                        return false;
                    }
                }))
            {
                isDate = true;
                type = "Date";
            }
        }

        PropertyBag<String, Object> result = new PropertyBag<String, Object>();
        result.put("indexes", indexes);
        result.put("isUniform", isUniform);
        result.put("type", type);
        result.put("minValue", minValue);
        result.put("maxValue", maxValue);
        result.put("uniqueValues", Counters.toBiggestValuesFirstString(valueCounter));
        result.put("uniqueTypes", Counters.toBiggestValuesFirstString(typeCounter));
        result.put("valueCounter", valueCounter);
        result.put("typeCounter", typeCounter);
        result.put("patternSet", patternSet);
        result.put("isYear", isYear);
        result.put("isMonth", isMonth);
        result.put("isWeek", isWeek);
        result.put("isDay", isDay);
        result.put("isQuarter", isQuarter);
        result.put("isDate", isDate);

        return result;
    }
}
