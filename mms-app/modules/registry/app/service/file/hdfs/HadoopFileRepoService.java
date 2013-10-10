package service.file.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.jcr.Binary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import mms.common.models.file.FileContext;
import mms.common.models.file.FileUpload;
import service.FileRepoService;

/**
 * User: markmo
 * Date: 14/03/13
 * Time: 2:46 PM
 */
public class HadoopFileRepoService implements FileRepoService {

    private String scheme;
    private String host;
    private String root;
    private FileSystem fs;

    public HadoopFileRepoService() {
        scheme = "hdfs://";
        host = "localhost";
        root = "mms";
        try {
            Configuration conf = new Configuration();
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void store(FileContext context, FileUpload fileUpload) {
        try {
            File file = fileUpload.getFile();
            FileInputStream in = new FileInputStream(file);
            String dir = "/" + root + "/" + context.getCategory() + "/" + context.getId();
//            if (fs.mkdirs(new Path(dir))) { // create will automatically create any parent directories
                Path p = new Path(dir + "/" + fileUpload.getName());
                FSDataOutputStream out = fs.create(p, false);
                IOUtils.copyBytes(in, out, 4096, true);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream load(String name) {
        return null;
    }

    public List<Map<String, Object>> index(String path) {
        try {
            if (!path.startsWith("/"))
                path = "/" + path;
            Path p = new Path(path);
            FileStatus[] fileStats = fs.listStatus(p);
            List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
            for (FileStatus fileStatus : fileStats) {
                Path f = fileStatus.getPath();
                Map<String, Object> file = new HashMap<String, Object>();
                file.put("name", f.getName());
                file.put("id", f.makeQualified(fs).toString());
                file.put("type", fileStatus.isDir() ? "folder" : "file");
                file.put("load_on_demand", fileStatus.isDir());
                ret.add(file);
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void list(FileContext context) {
    }

    public void listAll() {
        try {
            Path p = new Path(scheme + host + "/");
            FileStatus[] fileStats = fs.listStatus(p);
            for (FileStatus fileStatus : fileStats) {
                Path f = fileStatus.getPath();
                System.out.println(f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Binary getBinary(String filename) {
        return null;
    }

    public void deleteAll() {
    }

    public void shutdown() {
    }
}
