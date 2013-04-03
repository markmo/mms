package data.transformers;

import static utils.DateUtils.*;

import java.util.Date;

import org.eobjects.analyzer.beans.api.*;
import org.eobjects.analyzer.data.*;

/**
 * User: markmo
 * Date: 28/03/13
 * Time: 5:24 PM
 */
@TransformerBean("String to Date")
public class DateTransformer implements Transformer<Date> {

    @Configured
    InputColumn<String>[] columns;

    @Override
    public OutputColumns getOutputColumns() {
        String[] names = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            InputColumn<String> column = columns[i];
            String name = column.getName() + " (as date)";
            names[i] = name;
        }
        return new OutputColumns(names);
    }

    @Override
    public Date[] transform(InputRow inputRow) {
        Date[] result = new Date[columns.length];
        for (int i = 0; i < columns.length; i++) {
            InputColumn<String> column = columns[i];
            String value = inputRow.getValue(column).trim();
            if (value != null && value.length() > 0) {
                try {
                    result[i] = parseDate(value).get();
                } catch (IllegalArgumentException e) {
                    // TODO
                    // log exception
                }
            }
        }
        return result;
    }
}
