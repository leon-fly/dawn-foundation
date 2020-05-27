package com.dawn.foundation.exception;

public class GenericException extends RuntimeException {

    public static String FEE_NOT_FOUND = "FEE_001";
    public static String FEE_CATEGORY_MISMATCH = "FEE_002";
    public static String PARTNER_NOT_FOUND = "PARTNER_001";
    public static String PROCESSING_STATUS_ILLEGAL = "PROCESSING_STATUS_001";
    public static String PROCESS_ERROR_INNER = "PROCESS_ERR_001";
    public static String TRANSACTION_RECORD_NOT_EXIST = "TRANSACTION_ERR_001";
    public static String TRANSACTION_FIELD_ILLEGAL = "TRANSACTION_ERR_002";
    public static String TRANSACTION_DATA_ILLEGAL = "TRANSACTION_ERR_003";
    public static String EXCEPTION_UNKNOWN = "EXCEPTION_001";

    private String code;

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}