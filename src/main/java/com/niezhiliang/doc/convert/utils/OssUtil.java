package com.niezhiliang.doc.convert.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

@Component
public class OssUtil {
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    private final static Logger logger = LoggerFactory.getLogger(OssUtil.class);

    private  OSSClient ossClient = null;

    private  void connect() {
        ossClient = new OSSClient(endpoint , accessKeyId ,accessKeySecret );
    }

    /**
     *
     ossUtil.uploadFile(new File("./data/test0.docx"),"test/test2.docx");
     * @param key
     * @param file
     */
    public String upload(File file,String key) {
        if (ossClient == null) {
            connect();
        }
        try {
            ossClient.putObject(bucket , key , file);
        } catch (ClientException e) {
            logger.error("aliyun oss upload local file error: "+e.getErrorMessage());
        } finally {
            //ossClient.shutdown();
            //ossClient = null;
        }
        return "http://"+bucket+"."+endpoint+"/"+key;
    }

    /**
     *
     ossUtil.uploadFile(map);
     * @param map key:oss的key    value:上传的文件对象
     */
    public void uploadManyFile(Map<String,File> map) {
        if (ossClient == null) {
            connect();
        }
        try {
            for (String key :map.keySet()) {
                ossClient.putObject(bucket , key , map.get(key));
            }
        } catch (ClientException e) {
            logger.error("aliyun oss upload many local file error: "+e.getErrorMessage());
        } finally {
            ossClient.shutdown();
            ossClient = null;
        }
    }


    /**
     *
     ossUtil.downloadFile("test/test0.docx" , new File("./data/download.docx"));
     * @param key
     * @param file
     */
    public String download(File file ,String key) {
        if (ossClient == null) {
            connect();
        }
        try {
            ossClient.getObject(new GetObjectRequest(bucket , key) , file);
        } catch (ClientException e) {
            logger.error("aliyun oss download file  error: "+e.getErrorMessage());
        } finally {
            ossClient.shutdown();
            ossClient = null;
        }
        return file.getPath();
    }


    /**
     * 流上传到OSS
     * @param key 路径
     * @param inputStream  文件流
     */
    public String upload(InputStream inputStream, String key ) {
        if (ossClient == null) {
            connect();
        }
        try {
            ossClient.putObject(bucket , key , inputStream);
        } catch (ClientException e) {
            logger.error("aliyun oss upload file Stream  error: "+e.getErrorMessage());
        } finally {
            ossClient.shutdown();
            ossClient = null;
        }
        return "http://"+bucket+"."+endpoint+"/"+key;
    }
    /**
     * byte[]数组上传到OSS
     * @param key 路径
     * @param bytes  byte数组
     */
    public String upload(byte[] bytes, String key) {
        if (ossClient == null) {
            connect();
        }
        try {
            ossClient.putObject(bucket , key , new ByteArrayInputStream(bytes));
        } catch (ClientException e) {
            logger.error("aliyun oss upload file byte[]  error: "+e.getErrorMessage());
        } finally {
            ossClient.shutdown();
            ossClient = null;
        }
        return "http://"+bucket+"."+endpoint+"/"+key;
    }

    /**
     * 文件删除
     * @param key
     */
    public boolean deleteFile(String key) {
        if (ossClient == null) {
            connect();
        }
        try {
            ossClient.deleteObject(bucket, key);
        } catch (ClientException e) {
            logger.error("aliyun oss delete file error: "+e.getErrorMessage());
            return false;
        } finally {
            ossClient.shutdown();
            ossClient = null;
        }
        return true;
    }
}