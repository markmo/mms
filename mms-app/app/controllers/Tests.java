package controllers;

import analysis.Dialect;
import com.google.inject.Inject;
import play.mvc.*;

import service.FileService;

/**
 * User: markmo
 * Date: 19/03/13
 * Time: 5:46 PM
 */
public class Tests extends Controller {

    @Inject
    private FileService fileService;

    public Result sniff() {
        String analysisHtmlResults = fileService.extractMetadata("test5.csv");
        return ok();
    }

}
