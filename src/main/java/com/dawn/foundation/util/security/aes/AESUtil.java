package com.dawn.foundation.util.security.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2019/4/18
 */
public class AESUtil {

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CHARSET_DEFAULT = "UTF-8";

    public static SecretKeySpec getSecretKeySpec(String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        byte[] key = secretKey.getBytes(CHARSET_DEFAULT);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);

        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String originalData, SecretKeySpec secretKeySpec) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(originalData.getBytes(CHARSET_DEFAULT), secretKeySpec);
    }

    public static String encrypt(byte[] originalData, SecretKeySpec secretKeySpec) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        return Base64.getEncoder().encodeToString(cipher.doFinal(originalData));
    }

    public static String decrypt(String encryptedData, SecretKeySpec secretKeySpec) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)), CHARSET_DEFAULT);
    }

}