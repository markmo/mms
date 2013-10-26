package models.domain.config;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 3:44 PM
 */
public class DatabaseConnection extends DatasourceConnection {

    private String databaseName;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
