package com.dawn.foundation.plugins.cloud.storage.impl;

import com.dawn.foundation.plugins.cloud.storage.CloudStorage;
import com.dawn.foundation.plugins.cloud.storage.CloudStoragePermission;
import com.dawn.foundation.plugins.cloud.storage.CloudStorageProperties;
import com.dawn.foundation.util.date.DateFieldType;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Component
public class TencentCloudStorageImpl implements CloudStorage {

    @Autowired
    private CloudStorageProperties properties;

    private String bucket;
    private COSClient cosClient;

    @PostConstruct
    public void init() {
        COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(properties.getRegion()));

        this.cosClient = new COSClient(cred, clientConfig);
        this.bucket = properties.getBucket();
        cosClient.shutdown();
    }

    @Override
    public void upload(File localFile, String path, CloudStoragePermission permission) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, localFile);
        putObjectRequest.withCannedAcl(permission == CloudStoragePermission.PUBLIC ?
                CannedAccessControlList.PublicReadWrite : CannedAccessControlList.Private);

        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    @Override
    public void uploadWithPublicReadPermission(File localFile, String path) {
        upload(localFile, path, CloudStoragePermission.PUBLIC);
    }

    @Override
    public void uploadWithPrivateReadPermission(File localFile, String path) {
        upload(localFile, path, CloudStoragePermission.PRIVATE);
    }

    @Override
    public void upload(InputStream inputStream, String path,
                       CloudStoragePermission permission) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStream.available());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path,
                inputStream, objectMetadata);
        putObjectRequest.withCannedAcl(permission == CloudStoragePermission.PUBLIC ?
                CannedAccessControlList.PublicReadWrite : CannedAccessControlList.Private);

        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    @Override
    public void upload(InputStream inputStream, String path) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStream.available());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path,
                inputStream, objectMetadata);

        cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    @Override
    public void uploadWithPublicReadPermission(InputStream inputStream, String path)
            throws IOException {
        upload(inputStream, path, CloudStoragePermission.PUBLIC);
    }

    @Override
    public void uploadWithPrivateReadPermission(InputStream inputStream, String path)
            throws IOException {
        upload(inputStream, path, CloudStoragePermission.PRIVATE);
    }

    @Override
    public void download(String localPath, String path) {
        File downFile = new File(localPath);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, path);

        cosClient.getObject(getObjectRequest, downFile);
        cosClient.shutdown();
    }

    @Override
    public URL getDownLoadURL(String path) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, path, HttpMethodName.GET);

        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        return url;
    }

    @Override
    public URL getDownLoadURL(String path, Date expirationDate) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, path, HttpMethodName.GET);
        req.setExpiration(expirationDate);

        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        return url;
    }

    @Override
    public URL getDownloadURL(String path, Long usefulTime, DateFieldType dateFieldType) {
        long activeSeconds = 0L;
        if(dateFieldType == DateFieldType.SECOND){
            activeSeconds = usefulTime;
        }
        if (dateFieldType == DateFieldType.MINUTE) {
            activeSeconds *= 60;
        }
        if (dateFieldType == DateFieldType.HOUR) {
            activeSeconds *= 3600;
        }
        if (dateFieldType == DateFieldType.DAY) {
            activeSeconds *= 86400;
        }
        if( activeSeconds == 0L){
            throw new RuntimeException("仅支持类型为日，时，分，秒的dateFieldType 和 不为0的usefulTime。" +
                    "请检查:dateFieldType或者usefulTime");
        }

        Date expirationDate = new Date(System.currentTimeMillis() + activeSeconds * 1000L);

        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucket, path, HttpMethodName.GET);
        req.setExpiration(expirationDate);

        URL url = cosClient.generatePresignedUrl(req);
        cosClient.shutdown();
        return url;
    }

}
