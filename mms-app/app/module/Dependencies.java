package module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.google.inject.*;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import service.FileRepoService;
import service.FileService;
import service.file.hdfs.HadoopFileRepoService;
import service.file.jcr.ModeShapeFileRepoService;

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
        Hibernate4Module hm = new Hibernate4Module();
        hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, true);
        mapper.registerModule(hm);
        return mapper;
    }

    @Provides
    @Singleton
    FileRepoService provideFileRepoService() {
        //return new HadoopFileRepoService();
        return new ModeShapeFileRepoService();
    }

    @Provides
    @Singleton
    FileService provideFileService() {
        return new FileService();
    }

    @Provides
    @Singleton
    AbstractSequenceClassifier<CoreLabel> provideClassifier() {
        String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
        return CRFClassifier.getClassifierNoExceptions(serializedClassifier);
    }
}
