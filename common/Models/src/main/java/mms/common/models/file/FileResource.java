package mms.common.models.file;

import javax.persistence.*;

import mms.common.models.Resource;

/**
 * User: markmo
 * Date: 26/02/13
 * Time: 8:15 PM
 */
@Entity
@DiscriminatorValue("FIL")
public class FileResource extends Resource {

    enum FileFormat {
        CSV,
        TSV,
        XLS,
        XLSX
    }

    enum FileResourceType {
        FILE_UPLOAD,
        API
    }

    private String url;
    private String mimetype;
    private FileResourceType resourceType;
    private FileFormat format;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public FileResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(FileResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public FileFormat getFormat() {
        return format;
    }

    public void setFormat(FileFormat format) {
        this.format = format;
    }
}
