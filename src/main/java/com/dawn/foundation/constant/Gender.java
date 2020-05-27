package com.dawn.foundation.constant;

public enum Gender {

    F(0),
    M(1);

    private int code;

    Gender(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
