package mms.common.models.config;

import mms.common.models.Datasource;

/**
 * User: markmo
 * Date: 28/02/13
 * Time: 3:45 PM
 */
public class DatasourceConnection {

    private String host;
    private int port;
    private String username;
    private String password;
    private boolean isActive;
    private Datasource datasource;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }
}
