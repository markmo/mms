package nz.co.datascience.mms.model;

/**
 * User: markmo
 * Date: 26/09/12
 * Time: 1:49 PM
 */
public class SqlDataType {

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

        SqlDataType that = (SqlDataType) o;

        return (id == that.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
