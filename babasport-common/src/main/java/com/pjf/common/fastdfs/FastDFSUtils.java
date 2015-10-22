package com.pjf.common.fastdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public class FastDFSUtils {

    public static String uploadPic(byte[] pic,String name,Long size) throws Exception{
        ClassPathResource resource=new ClassPathResource("fdfs_client.conf");
        ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
        TrackerClient client=new TrackerClient();
        TrackerServer server=client.getConnection();
        StorageClient1 storageClient1=new StorageClient1(server,null);
        String ext= FilenameUtils.getExtension(name);
        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("filename",name);
        meta_list[1] = new NameValuePair("fileext",ext);
        meta_list[2] = new NameValuePair("filesize",String.valueOf(size));
        String path = storageClient1.upload_appender_file1(pic, ext, meta_list);
        return path;
    }
}
