package models.domain.types;

/**
 * User: markmo
 * Date: 8/10/12
 * Time: 7:42 PM
 */
public class StringValue {

    private String value;
    private boolean inferred;

    public StringValue(String value) {
        this.value = value;
        this.inferred = false;
    }

    public StringValue(String value, boolean inferred) {
        this.value = value;
        this.inferred = inferred;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringValue that = (StringValue) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
