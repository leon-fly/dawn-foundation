package com.dawn.foundation.util;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateKeyUtil {

    public static String generateAccessKey() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static String generateSecretKey() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public static void main(String[] args) {
        System.out.println(generateAccessKey());
        System.out.println(generateSecretKey());
    }
}
