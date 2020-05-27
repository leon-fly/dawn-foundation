package com.dawn.foundation.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParsePosition;
import java.util.*;

public class BeanHelper extends BeanUtils {
    private static ObjectMapper mapper;

    private static XmlMapper xmlMapper;

    private static XmlMapper xmlMapperNoDeclaration;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS, true);
        mapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);

        xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        xmlMapperNoDeclaration = new XmlMapper();
        xmlMapperNoDeclaration.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static String toJSON(Object object) {
        try {
            String jsonString = mapper.writeValueAsString(object);
            return jsonString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode toJsonNode(Object object) {
        try {
            return mapper.convertValue(object, JsonNode.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String jsonNodeToString(JsonNode jsonNode) {
        try {
            return mapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode parseJson(String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(String json, TypeReference<T> clazz) {
        try {
            return (T) mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXml(String xml, TypeReference<T> clazz) {
        try {
            return xmlMapper.readValue(xml, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromXml(String xml) {
        try {
            return xmlMapper.readValue(xml, new TypeReference<T>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param object 待序列化的对象
     * @param rootElement 跟元素
     * @param withDeclaration 是否需要xml描述
     * @return
     */
    public static String toXml(Object object, String rootElement, boolean withDeclaration) {

        XmlMapper mapper;
        if (withDeclaration){
            mapper = xmlMapper;
        }else{
            mapper = xmlMapperNoDeclaration;
        }

        try {
            return mapper.writer().withRootName(rootElement).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static String toXml(Object object, String rootElement) {
        try {
            return xmlMapper.writer().withRootName(rootElement).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXml(Object object) {
        try {
            return xmlMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * used for basic java data type，such as java.util.Map, java.util.List.....
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T fromJSON(String json) {
        try {
            return (T) mapper.readValue(json, new TypeReference<T>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(InputStream is, Class<T> clazz) {
        try {
            return (T) mapper.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(InputStream is, TypeReference<T> clazz) {
        try {
            return (T) mapper.readValue(is, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从map复制数据到bean，根据key与properties匹配。浅拷贝
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesFromMap(Map source, Object target) {
        BeanInfo beanInfo = null; // 获取类属性
        try {
            beanInfo = Introspector.getBeanInfo(target.getClass());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if (source.containsKey(propertyName)) {
                Object value = source.get(propertyName);
                try {
                    propertyDescriptor.getWriteMethod().invoke(target, value);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("IllegalArgument propertity is [" + propertyName + "]", e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("IllegalAccess propertity is [" + propertyName + "]", e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("IllegalAccess propertity is [" + propertyName + "]", e);
                }
            }
        }
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static Date parseDate(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return ISO8601Utils.parse(str, new ParsePosition(0));
        } catch (Exception e) {
            throw new RuntimeException("Invalid json date format,value:" + str);
        }
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return ISO8601Utils.format(date, true);
    }

    public static void copyNotNullProperties(Object source, Object target, String... properties) {
        List<String> ignoreProperties = new ArrayList<>();

        ignoreProperties.addAll(Arrays.asList(properties));
        ignoreProperties.addAll(Arrays.asList(getNullPropertyNames(source)));

        BeanUtils.copyProperties(source, target, ignoreProperties.toArray(new String[ignoreProperties.size()]));
    }

    public static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

}

