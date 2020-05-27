package com.dawn.foundation.plugins.cloud.storage.specification;

/**
 * @Author : leonwang
 * @Description
 * @Date : created  2019/1/11
 */
public abstract class BaseStorageFile implements StorageFile {
    protected String getStoringFileNameByOrderedNode(String... nodes) {
        StringBuilder pathSB = new StringBuilder();
        for (String node : nodes) {
            pathSB.append(node);
            pathSB.append(PATH_SEPARATOR);
        }

        //去掉最后的分隔符
        if (nodes.length != 0) {
            String path = pathSB.toString();
            int pathLength = path.length();
            return path.substring(0, pathLength - 1).toLowerCase();
        }

        return pathSB.toString();
    }
}

