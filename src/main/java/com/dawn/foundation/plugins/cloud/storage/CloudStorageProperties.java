package com.dawn.foundation.plugins.cloud.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class CloudStorageProperties {

    @Value("${cloud.storage.secretId:''}")
    private String secretId;
    @Value("${cloud.storage.secretKey:''}")
    private String secretKey;
    @Value("${cloud.storage.region:''}")
    private String region;
    @Value("${cloud.storage.bucket:''}")
    private String bucket;

}
