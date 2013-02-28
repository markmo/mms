package models;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:21 PM
 */
@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.JOINED)
public class Document extends Resource {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
