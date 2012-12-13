package nz.co.datascience.mms.model;

/**
 * User: markmo
 * Date: 24/09/12
 * Time: 4:07 PM
 */
public class DataType {

    public static enum Type {
        STRING
        ,DATE
        ,NUMERIC
        ,BOOLEAN
        ,ERROR
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataType dataType = (DataType) o;

        return (id == dataType.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
