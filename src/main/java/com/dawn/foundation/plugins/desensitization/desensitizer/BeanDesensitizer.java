package com.dawn.foundation.plugins.desensitization.desensitizer;

import com.dawn.foundation.plugins.desensitization.annotation.Desensitize;
import com.dawn.foundation.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2018/9/19
 */
public class BeanDesensitizer {
    /**
     * 将数据对象进行脱敏，原数据对象内容不受影响
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T desensitize(T target) {
        Set<Object> desensitizedObjects = new HashSet<>();
        T desensitizedBean = ObjectUtils.deepCloneObjectByJackson(target);

        try {
            desensitize(desensitizedBean, desensitizedObjects);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return desensitizedBean;
    }

    /**
     * 是否已经序列化过的类（避免循环引用）
     *
     * @param target
     * @param desensitizedObjects
     * @return
     */
    private static boolean isAlreadyDesensitized(Object target, final Set<Object> desensitizedObjects) {
        for (Object o : desensitizedObjects) {
            if (target == o) {
                return true;
            }
        }
        return false;
    }

    private static void desensitize(Object javaBean, final Set<Object> desensitizedObject) throws IllegalAccessException {
        if (isAlreadyDesensitized(javaBean, desensitizedObject)) {
            return;
        }
        desensitizedObject.add(javaBean);

        Set<Field> fields = ObjectUtils.getAllFields(javaBean);

        if (null == fields || fields.size() == 0 || javaBean == null) {
            return;
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (field == null) {
                continue;
            }

            Object value = field.get(javaBean);
            if (value == null) {
                continue;
            }

            Class<?> type = value.getClass();

            //处理子属性，包括集合中的
            if (type.isArray()) {//对数组类型的字段进行递归过滤
                int len = Array.getLength(value);
                for (int i = 0; i < len; i++) {
                    Object arrayObject = Array.get(value, i);
                    desensitize(arrayObject, desensitizedObject);
                }
            } else if (value instanceof Collection<?>) {//对集合类型的字段进行递归过滤
                Collection<?> c = (Collection<?>) value;
                Iterator<?> it = c.iterator();
                while (it.hasNext()) {
                    Object collectionObj = it.next();
                    desensitize(collectionObj, desensitizedObject);
                }
            } else if (value instanceof Map<?, ?>) {//对Map类型的字段进行递归过滤
                Map<?, ?> m = (Map<?, ?>) value;
                Set<?> set = m.entrySet();
                for (Object o : set) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
                    Object mapVal = entry.getValue();
                    desensitize(mapVal, desensitizedObject);
                }
            }else if (!type.isPrimitive()
                    && !(value instanceof Enum<?>)
                    && !StringUtils.startsWith(type.getName(), "javax.")
                    && !StringUtils.startsWith(type.getName(), "java.")){
                desensitize(value, desensitizedObject);
            }

            desensitizeField(javaBean, field);
        }
    }


    /**
     * 脱敏属性值
     *
     * @param javaBean
     * @param field
     * @throws IllegalAccessException
     */
    private static void desensitizeField(Object javaBean, Field field) throws IllegalAccessException {
        Desensitize annotation = field.getAnnotation(Desensitize.class);
        if (field.getType().equals(String.class) && null != annotation) {
            String valueStr = (String) field.get(javaBean);
            if (StringUtils.isNotBlank(valueStr)) {
                String desensitizedValue = SensitiveTypeDesensitizer.desensitize(valueStr, annotation.type());
                field.set(javaBean, desensitizedValue);
            }
        }
    }


}
