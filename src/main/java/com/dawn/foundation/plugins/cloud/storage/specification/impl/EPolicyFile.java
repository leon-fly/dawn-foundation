package com.dawn.foundation.plugins.cloud.storage.specification.impl;

import com.dawn.foundation.plugins.cloud.storage.specification.BaseStorageFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : leonwang
 * @Description
 * @Date : created  2019/1/11
 */
@Getter
@Setter
@Builder
public class EPolicyFile extends BaseStorageFile {
    private String insurerCode;
    private String policyNo;
    private String fileSuffix;
    private final String NODE_NAME = "e-policy";
    private final String FILE_SUFFIX_DEFAULT = "pdf";

    @Override
    public String getStoringFileName() {
        return getStoringFileNameByOrderedNode(INSURER_FILE_PATH_ROOT, insurerCode, NODE_NAME, getSimpleFileName());
    }

    private String getSimpleFileName() {
        String fileSuffix = this.fileSuffix == null ? FILE_SUFFIX_DEFAULT : this.fileSuffix;
        return this.policyNo + "." + fileSuffix;
    }
}
