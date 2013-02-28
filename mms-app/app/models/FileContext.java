package models;

/**
 * User: markmo
 * Date: 17/02/13
 * Time: 6:14 PM
 */
public class FileContext {

    public String category;
    public String id;

    public FileContext(String category, String id) {
        this.category = category;
        this.id = id;
    }

    public FileContext(String category, Long id) {
        this.category = category;
        this.id = id.toString();
    }

}
