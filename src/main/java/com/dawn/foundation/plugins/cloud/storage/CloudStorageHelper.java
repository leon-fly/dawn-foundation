package com.dawn.foundation.plugins.cloud.storage;

import com.dawn.foundation.plugins.cloud.storage.specification.StorageFile;
import com.dawn.foundation.util.BeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @Author : leonwang
 * @Description
 * @Date : created  2019/1/11
 */
@Component
public class CloudStorageHelper {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CloudStorage cloudStorage;

    public void uploadWithPrivateReadPermission(File localFile, StorageFile storageFile) {
        String storingFileName = storageFile.getStoringFileName();
        logger.debug("存储文件storing file:[{}],存储位置:[{}]", BeanHelper.toJSON(storageFile), storingFileName);
        cloudStorage.uploadWithPrivateReadPermission(localFile, storingFileName);
    }

    public void upload(InputStream inputStream, StorageFile storageFile) throws IOException {
        String storingFileName = storageFile.getStoringFileName();
        logger.debug("存储文件storing file:[{}],存储位置:[{}]", BeanHelper.toJSON(storageFile), storingFileName);
        cloudStorage.upload(inputStream, storingFileName);
    }

    public URL getDownLoadURL(StorageFile storageFile, Date expirationDate) {
        String storingFileName = storageFile.getStoringFileName();
        logger.debug("云存储下载地址查询,storageFile[{}], 存储位置:[{}]", BeanHelper.toJSON(storageFile), storingFileName);
        return cloudStorage.getDownLoadURL(storingFileName, expirationDate);
    }
}
