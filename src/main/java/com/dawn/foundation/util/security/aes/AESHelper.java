package com.dawn.foundation.util.security.aes;

import com.dawn.foundation.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2019/5/5
 */
@Component
@Slf4j
public class AESHelper {

    @Value("${channel.aes-secret-key}")
    private String aesSecretKey;

    private SecretKeySpec secretKeySpec;

    @PostConstruct
    public void init() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        log.debug("AES初始密钥："+ aesSecretKey);
        secretKeySpec = AESUtil.getSecretKeySpec(aesSecretKey);
    }

    public String encrypt(String originalData) {
        try {
            return AESUtil.encrypt(originalData, secretKeySpec);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new GenericException("数据加密失败");
        }
    }

    public String encrypt(byte[] originalData) {
        try {
            return AESUtil.encrypt(originalData, secretKeySpec);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new GenericException("数据加密失败");
        }
    }

    public String decrypt(String encryptedData) {
        try {
            return AESUtil.decrypt(encryptedData, secretKeySpec);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new GenericException("数据解密失败");
        }
    }

    /**
     * @description 使用传入的密钥进行aes加密
     * @author lun.yang
     * @date 2020/3/7 22:37
     */
    public String encrypt(String originalData, String aesSecretKey) {
        try {
            return AESUtil.encrypt(originalData, AESUtil.getSecretKeySpec(aesSecretKey));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new GenericException("数据加密失败");
        }
    }
}
