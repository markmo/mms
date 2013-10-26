package models.domain;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:21 PM
 */
public class Document extends Resource {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
