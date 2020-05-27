package com.dawn.foundation.plugins.desensitization;

import com.dawn.foundation.plugins.desensitization.desensitizer.BeanDesensitizer;
import com.dawn.foundation.plugins.desensitization.desensitizer.StringDesensitizer;
import com.dawn.foundation.plugins.desensitization.enumeration.BisDataFormat;
import com.dawn.foundation.util.BeanHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author : leonwang
 * @Descpriction 脱敏工具类
 * @Date:created 2018/9/14
 */
@Component
public class DesensitizationUtils {

    private static Boolean turnOn = true;

    /**
     * 对数据对象进行脱敏
     * @param target
     * @param <T>
     * @return 脱敏后的数据对象
     */
    public static <T> T desensitizeObject(T target){
        if (!turnOn){
            return target;
        }

        return BeanDesensitizer.desensitize(target);
    }

    /**
     * 将数据对象脱敏返回jsonString
     * @param target
     * @return 脱敏后的json字符串
     */
    public static String desensitizeObjectToJson(Object target){
        if (!turnOn){
            return BeanHelper.toJSON(target);
        }

        return BeanHelper.toJSON(BeanDesensitizer.desensitize(target));
    }

    /**
     * 对打印数据字符进行脱敏
     * @param printInfo
     * @return
     */
    public static String desensitizeJson(String printInfo){
        try{
            BeanHelper.fromJSON(printInfo, Map.class);
        }catch (RuntimeException e){
            throw new RuntimeException("json格式非法",e);
        }

        if (!turnOn){
            return printInfo;
        }

        return StringDesensitizer.desensitize(printInfo, BisDataFormat.JSON);
    }


    public static String desensitizeXml(String printInfo){
        try{
            BeanHelper.fromXml(printInfo, Map.class);
        }catch (RuntimeException e){
            throw new RuntimeException("xml格式非法",e);
        }

        if (!turnOn){
            return printInfo;
        }

        return StringDesensitizer.desensitize(printInfo, BisDataFormat.XML);
    }


    @Value("${desensitization.turnOn:true}")
    public void setTurnOn(Boolean turnOn) {
        DesensitizationUtils.turnOn = turnOn;
    }
}


