package models.domain.business;

/**
 * User: markmo
 * Date: 26/05/13
 * Time: 3:39 PM
 */
public enum SourceType {
    DEFINITION("definition");

    private final String value;

    private SourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}
