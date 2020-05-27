package com.dawn.foundation.constant;

public enum IdType {

    ID_CARD(1), 			      // 身份证
    PASSPORT(2),			      // 护照
    MILITARY_ID(3),			      // 军官证
    HOME_RETURN_CERTIFICATE(4),	  // 港澳居民来往内地通行证
    TAIWAN_PERMIT(5),			  // 大陆通行证
    DRIVER_LICENCE(6), 		      // 驾驶证
    BIRTH_CERTIFICATE(7),	      // 出生登记号
    REGISTER_NO(8),               // 工商登记号
    ORG_CODE(9),	              // 组织机构代码
    TAX_CODE(10),	              // 税务登记号
    SOCIAL_CREDIT(11);            // 社会统一信用代码

    private int code;

    IdType(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

}
