package service.file.jcr;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.modeshape.jcr.api.JcrTools;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import models.domain.file.FileContext;
import models.domain.file.FileUpload;
import service.FileRepoService;

/**
 * User: markmo
 * Date: 15/02/13
 * Time: 8:26 PM
 */
public class ModeShapeFileRepoService implements FileRepoService {

    protected ModeShapeEngine engine;
    protected Session session;

    public ModeShapeFileRepoService() {
        // Create and start the engine ...
        engine = new ModeShapeEngine();
        engine.start();

        // Load the configuration for a repository via the classloader (can also use path to a file)...
        Repository repository = null;
        String repositoryName = null;
        try {
            URL url = ModeShapeFileRepoService.class.getClassLoader().getResource("my-repository-config.json");
            RepositoryConfiguration config = RepositoryConfiguration.read(url);

            // We could change the name of the repository programmatically ...
            // config = config.withName("Some Other Repository");

            // Verify the configuration for the repository ...
            Problems problems = config.validate();
            if (problems.hasErrors()) {
                System.err.println("Problems starting the engine.");
                System.err.println(problems);
                System.exit(-1);
            }

            // Deploy the repository ...
            repository = engine.deploy(config);
            repositoryName = config.getName();
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        try {
            // Get the repository
            repository = engine.getRepository(repositoryName);

            // Create a session ...
            session = repository.login("default");

            System.out.println("Starting a new JCR session");

            Node root = session.getRootNode();

            // Create a new folder node ...
            Node files = root.addNode("files", "nt:folder");

            // The auto-created properties are added when the session is saved ...
            session.save();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (session != null) session.logout();
        System.out.println("Shutting down engine ...");
        try {
            engine.shutdown().get();
            System.out.println("Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void store(FileContext context, FileUpload fileUpload) {
        try {
            File file = fileUpload.getFile();
            ContentHandler contentHandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            Parser parser = new AutoDetectParser();
            FileInputStream is = new FileInputStream(file);
            parser.parse(is, contentHandler, metadata, new ParseContext());
            String mimeType = metadata.get(Metadata.CONTENT_TYPE);
            if (mimeType == null) mimeType = "application/octet-stream";
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            Node category;
            if (files.hasNode(context.getCategory())) {
                category = files.getNode(context.getCategory());
            } else {
                category = files.addNode(context.getCategory(), "nt:folder");
            }
            Node ctxNode;
            if (category.hasNode(context.getId())) {
                ctxNode = category.getNode(context.getId());
            } else {
                ctxNode = category.addNode(context.getId(), "nt:folder");
            }
            Node fileNode = ctxNode.addNode(fileUpload.getName(), "nt:file");
            Node resourceNode = fileNode.addNode("jcr:content", "nt:resource");

            // Create a buffered input stream for the file's contents ...
            InputStream stream = new BufferedInputStream(new FileInputStream(file));

            Binary binary = session.getValueFactory().createBinary(stream);
            resourceNode.setProperty("jcr:mimeType", mimeType);
            resourceNode.setProperty("jcr:encoding", "");
            resourceNode.setProperty("jcr:data", binary);
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(fileUpload.getFile().lastModified());
            resourceNode.setProperty("jcr:lastModified", lastModified);
            session.save();

//            NodeIterator it = files.getNodes();
//            while (it.hasNext()) {
//                System.out.println("+---> " + it.nextNode().getName());
//            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    public InputStream load(String name) {
        InputStream content = null;
        try {
            Node root = session.getRootNode();
            Node node = root.getNode("/files/" + name);
            Node fileNode = node.getNode("jcr:content");
            content = fileNode.getProperty("jcr:data").getBinary().getStream();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return content;
    }

    public void list(FileContext context) {
        try {
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            Node category = files.getNode(context.getCategory());
            Node ctxNode = category.getNode(context.getId());
            NodeIterator it = ctxNode.getNodes();
            while (it.hasNext()) {
                Node fileNode = it.nextNode();
                System.out.println("+---> " + fileNode.getName());
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> index(String path) {
        try {
            Node files = path == null ? session.getNode("/files") : session.getNode(path);
            NodeIterator it = files.getNodes();
            List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
            while (it.hasNext()) {
                Node node = it.nextNode();
                Map<String, Object> file = new HashMap<String, Object>();
                file.put("name", node.getName());
                file.put("id", node.getPath());
                file.put("type", node.getPrimaryNodeType().toString());
                file.put("load_on_demand", node.hasNodes() && !"nt:file".equals(node.getPrimaryNodeType().toString()));
                ret.add(file);
            }
            return ret;
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void listAll() {
        try {
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            JcrTools tools = new JcrTools();
            tools.printSubgraph(files);
            //descend(files, 0);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public Binary getBinary(String filename) {
        System.out.println("getBinary called");
        try {
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            String lang = Query.JCR_SQL2;
            String expr = "select * from [nt:file] where localname() = $filename";
            ValueFactory valueFactory = session.getValueFactory();
            Query query = queryManager.createQuery(expr, lang);
            query.bindValue("filename", valueFactory.createValue(filename));
            QueryResult result = query.execute();
            Node fileNode = result.getNodes().nextNode();
            System.out.println("fileNode: " + fileNode.getName());
            Node jcrContent = fileNode.getNode("jcr:content");
            return jcrContent.getProperty("jcr:data").getBinary();
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAll() {
        try {
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            NodeIterator it = files.getNodes();
            while (it.hasNext()) {
                Node node = it.nextNode();
                node.remove();
            }
            Node text = root.getNode("text");
            text.remove();
            session.save();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void descend(Node node, int level) throws RepositoryException {
        if (level > 10) return;
        int indent = level * 4;
        System.out.println(String.format("%" + indent + "s +---> ", "") + node.getName());
        NodeIterator it = node.getNodes();
        while (it.hasNext()) {
            descend(it.nextNode(), level + 1);
        }
    }
}
