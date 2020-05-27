package com.dawn.foundation.plugins.desensitization.desensitizer;

import com.dawn.foundation.plugins.desensitization.enumeration.SensitiveType;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2018/9/19
 */
public class SensitiveTypeDesensitizer {
    public static final char DESENSITIZATION_COVER = '*';

    /**
     * 对指定敏感类型的string数据进行脱敏
     *
     * @param value
     * @param sensitiveType
     * @return
     */
    public static String desensitize(String value, SensitiveType sensitiveType) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        switch (sensitiveType) {
            case CHINESE_NAME:
                return chineseName(value);
            case ID_CARD_NO:
                return idCardNo(value);
            case FIXED_PHONE:
                return fixedPhone(value);
            case CELL_PHONE:
                return cellPhone(value);
            case ADDRESS:
                return address(value);
            case EMAIL:
                return email(value);
            case BANK_CARD:
                return bankCard(value);
            case PASSWORD:
                return password(value);
            case HIGH_LEVEL_PRIVACY_DATA:
                return hideAll(value);
            default:
                return value;
        }
    }

    /**
     * 【中文姓名】只显示第一个汉字 如 张**
     *
     * @param chineseName
     * @return
     */
    private static String chineseName(String chineseName) {
        String name = StringUtils.left(chineseName, 1);
        return StringUtils.rightPad(name, StringUtils.length(chineseName), DESENSITIZATION_COVER);
    }

    /**
     * 【身份证号】显示最后四位 如 **************1710
     *
     * @param id
     * @return
     */
    private static String idCardNo(String id) {
        String num = StringUtils.right(id, 4);
        return StringUtils.leftPad(num, StringUtils.length(id), DESENSITIZATION_COVER);
    }

    /**
     * 【固定电话】 显示后四位
     *
     * @param num
     * @return
     */
    private static String fixedPhone(String num) {
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), DESENSITIZATION_COVER);
    }

    /**
     * 【手机号码】显示前三位，后四位
     *
     * @param num
     * @return
     */
    private static String cellPhone(String num) {
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), DESENSITIZATION_COVER), "***"));
    }

    /**
     * 【地址】最低显示到市，不显示详细地址 如:上海市**** / 山西省临汾市********
     *
     * @param address
     * @return
     */
    private static String address(String address) {

        int sensitiveIndexStart = StringUtils.indexOf(address, "市");

        if (sensitiveIndexStart == -1) {
            sensitiveIndexStart = StringUtils.indexOf(address, "省");
        }

        if (sensitiveIndexStart == -1) {
            sensitiveIndexStart = 0;
        }

        int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, sensitiveIndexStart + 1), length, DESENSITIZATION_COVER);
    }

    /**
     * 【电子邮箱】 邮箱前缀仅显示第一个字母、@及域名 如 9****@qq.com
     *
     * @param email
     * @return
     */
    private static String email(String email) {
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1)
            return email;
        else
            return StringUtils.rightPad(StringUtils.left(email, 1), index, DESENSITIZATION_COVER).concat(StringUtils.mid(email, index, StringUtils.length(email)));
    }

    /**
     * 【银行卡号】显示前六位及后四位 比如：6222600**********1234>
     *
     * @param cardNum
     * @return
     */
    private static String bankCard(String cardNum) {
        if (cardNum.length()<=10){
            return cardNum;
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), DESENSITIZATION_COVER), "******"));
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password
     * @return
     */
    private static String password(String password) {
        return hideAll(password);
    }

    /**
     * 最高安全级别数据
     * @param content
     * @return
     */
    private static String highLevelPrivacyData(String content) {
        return hideAll(content);
    }

    /**
     * @param content
     * @return
     */
    private static String hideAll(String content) {
        return StringUtils.rightPad("", StringUtils.length(content), DESENSITIZATION_COVER);
    }
}
