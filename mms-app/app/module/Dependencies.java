package module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
import com.google.inject.*;
import service.FileService;
import service.ModeShapeFileService;

/**
 * User: markmo
 * Date: 24/11/12
 * Time: 5:51 PM
 */
public class Dependencies implements Module {

    public void configure(Binder binder) {}

    @Provides
    ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate3Module hm = new Hibernate3Module();
        hm.configure(Hibernate3Module.Feature.FORCE_LAZY_LOADING, true);
        mapper.registerModule(hm);
        return mapper;
    }

    @Provides
    @Singleton
    FileService provideFileService() {
        return new ModeShapeFileService();
    }
}
