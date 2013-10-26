package service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;

import models.domain.file.FileContext;
import models.domain.file.FileUpload;

/**
 * User: markmo
 * Date: 16/02/13
 * Time: 1:46 PM
 */
public interface FileRepoService {

    public void store(FileContext context, FileUpload fileUpload);

    public InputStream load(String name);

    public List<Map<String, Object>> index(String path);

    public void list(FileContext context);

    public void listAll();

    public Binary getBinary(String filename);

    public void deleteAll();

    public void shutdown();

}
