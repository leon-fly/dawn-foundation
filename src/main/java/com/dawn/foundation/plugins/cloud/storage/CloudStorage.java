package com.dawn.foundation.plugins.cloud.storage;


import com.dawn.foundation.util.date.DateFieldType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public interface CloudStorage {

    public void upload(File localFile, String path, CloudStoragePermission permission);

    public void uploadWithPublicReadPermission(File localFile, String path);

    public void uploadWithPrivateReadPermission(File localFile, String path);

    public void upload(InputStream inputStream, String path,
                       CloudStoragePermission permission) throws IOException;

    public void upload(InputStream inputStream, String path) throws IOException;

    public void uploadWithPublicReadPermission(InputStream inputStream, String path)
            throws IOException;

    public void uploadWithPrivateReadPermission(InputStream inputStream, String path)
            throws IOException;

    public void download(String localPath, String path);

    public URL getDownLoadURL(String path);

    public URL getDownLoadURL(String path, Date expirationDate);

    public URL getDownloadURL(String path, Long usefulTime, DateFieldType dateFieldType);

}