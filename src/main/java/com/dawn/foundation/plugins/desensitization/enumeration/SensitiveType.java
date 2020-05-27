package com.dawn.foundation.plugins.desensitization.enumeration;

/**
 * @Author : leonwang
 * @Descpriction 敏感数据枚举值
 * @Warning 每新增一个枚举类型,需要考虑SensitiveTypeDesensitizer中增加对应的脱敏方法,否则该敏感数据不做任何处理
 * @Date:created 2018/9/16
 */
public enum SensitiveType {
    CHINESE_NAME,//中文名
    ID_CARD_NO,  //身份证号
    FIXED_PHONE, //座机号
    CELL_PHONE,//手机号
    ADDRESS, //地址
    EMAIL,   //电子邮件
    BANK_CARD, //银行卡
    PASSWORD,  //密码
    HIGH_LEVEL_PRIVACY_DATA; //最高级别隐私
}