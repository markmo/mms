package mms.common.models.file;

/**
 * User: markmo
 * Date: 17/02/13
 * Time: 6:14 PM
 */
public class FileContext {

    private String category;
    private String id;

    public FileContext(String category, String id) {
        this.category = category;
        this.id = id;
    }

    public FileContext(String category, Long id) {
        this.category = category;
        this.id = id.toString();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
