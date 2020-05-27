package com.dawn.foundation.plugins.desensitization.annotation;


import com.dawn.foundation.plugins.desensitization.enumeration.SensitiveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2018/9/16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitize {
    SensitiveType type(); //敏感类型
}
