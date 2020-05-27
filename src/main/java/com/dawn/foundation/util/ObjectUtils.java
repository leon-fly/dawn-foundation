package com.dawn.foundation.util;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author : leonwang
 * @Descpriction 脱敏工具类
 * @Date:created 2018/9/14
 */
public class ObjectUtils {
    /**
     * 深度克隆（基于序列化-反序列化方式,被拷贝的对象必须要实现序列化)
     * @param obj
     * @return
     */
    public static <T> T deepCloneObject(T obj) {
        T t = (T) new Object();
        ByteArrayOutputStream byteOut = null;
        ByteArrayInputStream byteIn = null;
        try {
            byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);
            byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            t = (T) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (byteOut != null) {
                try {
                    byteOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteIn != null) {
                try {
                    byteIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * 深度克隆(依赖jackson进行)
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T deepCloneObjectByJackson(T obj) {
        String json = BeanHelper.toJSON(obj);
        T t = (T) BeanHelper.fromJSON(json, obj.getClass());
        return t;
    }


    /**
     * 获取包括父类所有的属性
     *
     * @param target
     * @return
     */
    public static Set<Field> getAllFields(Object target) {
        Class targetClass = target.getClass();
        return getAllFields(targetClass);
    }

    /**
     * 获取包括父类所有的属性
     * @param clazz
     * @return
     */
    public static Set<Field> getAllFields(Class clazz) {
        Set<Field> fieldSet = new HashSet<>();
        while (clazz != null && !clazz.getName().toLowerCase().equals("java.lang.object")) {
            fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldSet;
    }
    /*public boolean detectCircularReference(Object target) {
        return false;
    }*/
}
