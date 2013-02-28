package service;

import java.io.*;
import java.net.URL;
import java.util.Calendar;
import javax.jcr.*;

import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import sun.net.www.MimeTable;

import models.FileContext;
import models.FileInput;

/**
 * User: markmo
 * Date: 15/02/13
 * Time: 8:26 PM
 */
public class ModeShapeFileService implements FileService {

    protected Session session;

    public ModeShapeFileService() {
        // Create and start the engine ...
        ModeShapeEngine engine = new ModeShapeEngine();
        engine.start();

        // Load the configuration for a repository via the classloader (can also use path to a file)...
        Repository repository = null;
        String repositoryName = null;
        try {
            URL url = ModeShapeFileService.class.getClassLoader().getResource("my-repository-config.json");
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
//        } finally {
//            if (session != null) session.logout();
//            System.out.println("Shutting down engine ...");
//            try {
//                engine.shutdown().get();
//                System.out.println("Success!");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public void store(FileContext context, FileInput fileInput) {
        try {
            File file = fileInput.file;
            MimeTable mt = MimeTable.getDefaultTable();
            String mimeType = mt.getContentTypeFor(file.getName());
            if (mimeType == null) mimeType = "application/octet-stream";
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            Node category;
            if (files.hasNode(context.category)) {
                category = files.getNode(context.category);
            } else {
                category = files.addNode(context.category, "nt:folder");
            }
            Node ctxNode;
            if (category.hasNode(context.id)) {
                ctxNode = category.getNode(context.id);
            } else {
                ctxNode = category.addNode(context.id, "nt:folder");
            }
            Node fileNode = ctxNode.addNode(fileInput.name, "nt:file");
            Node resourceNode = fileNode.addNode("jcr:content", "nt:resource");

            // Create a buffered input stream for the file's contents ...
            InputStream stream = new BufferedInputStream(new FileInputStream(file));

            Binary binary = session.getValueFactory().createBinary(stream);
            resourceNode.setProperty("jcr:mimeType", mimeType);
            resourceNode.setProperty("jcr:encoding", "");
            resourceNode.setProperty("jcr:data", binary);
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(fileInput.file.lastModified());
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
            Node category = files.getNode(context.category);
            Node ctxNode = category.getNode(context.id);
            NodeIterator it = ctxNode.getNodes();
            while (it.hasNext()) {
                Node fileNode = it.nextNode();
                System.out.println("+---> " + fileNode.getName());
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void listAll() {
        try {
            Node root = session.getRootNode();
            Node files = root.getNode("files");
            descend(files, 1);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    public void descend(Node node, int level) throws RepositoryException {
        if (level > 10) return;
        int indent = level * 4;
        System.out.println(String.format("%1$#" + indent + "s +---> ", "") + node.getName());
        NodeIterator it = node.getNodes();
        while (it.hasNext()) {
            descend(it.nextNode(), level + 1);
        }
    }
}
