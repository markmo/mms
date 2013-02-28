package service;

import java.io.InputStream;

import models.FileContext;
import models.FileInput;

/**
 * User: markmo
 * Date: 16/02/13
 * Time: 1:46 PM
 */
public interface FileService {

    public void store(FileContext context, FileInput fileInput);

    public InputStream load(String name);

    public void list(FileContext context);

    public void listAll();

}
