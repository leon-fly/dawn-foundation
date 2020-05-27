package com.dawn.foundation.plugins.cloud.storage.specification;

/**
 * @Author : leonwang
 * @Description
 * @Date : created  2019/1/11
 */
public interface StorageFile {
    String INSURER_FILE_PATH_ROOT = "insurer";
    String PATH_SEPARATOR = "/";

    String getStoringFileName();
}
