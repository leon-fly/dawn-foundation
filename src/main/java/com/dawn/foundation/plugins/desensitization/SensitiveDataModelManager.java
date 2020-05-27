package com.dawn.foundation.plugins.desensitization;

import com.dawn.foundation.plugins.desensitization.annotation.Desensitize;
import com.dawn.foundation.plugins.desensitization.enumeration.SensitiveType;
import com.dawn.foundation.plugins.desensitization.model.SensitiveDataModel;
import com.dawn.foundation.util.BeanHelper;
import com.dawn.foundation.util.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author : leonwang
 * @Descpriction 敏感数据管理
 * 获取指定包下的不重复的敏感数据信息模型（通过解析属性上Desensitize的type获取敏感其敏感类型,name为jsonProperties值）
 * @Date:created 2018/9/19
 */
public class SensitiveDataModelManager {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveDataModelManager.class);

    //敏感数据集(仅对string脱敏时使用)
    private static Set<SensitiveDataModel> sensitiveDataModelSet;


    private static final String[] basicScanPackage = {"com.zkj"};

    private static final String sensitiveDataModelSource = "sensitive-data.properties";

    static {
        //获取指定路径下类的属性中有脱敏注解的类，解析到SensitiveDataModel并添加到pool中
        sensitiveDataModelSet = new HashSet<>();

        scanSensitiveDataModel(basicScanPackage);

        loadSensitiveDataInFile(sensitiveDataModelSource);

        logger.info(String.format("敏感信息个数共计:%d \n敏感信息集：%s",sensitiveDataModelSet.size(), BeanHelper.toJSON(sensitiveDataModelSet)));
    }

    /**
     * 扫描指定包下的敏感数据模型
     * (建议仅在模块启动时进行调用)
     *
     * @param packages
     */
    public static void scanSensitiveDataModel(String[] packages) {
        logger.info("[脱敏组件扫描敏感字段] 启动扫描 >>>>>>>>>>>>>>>>>");
        Set<Class> clazzSet = getAllClasses(packages);

        clazzSet.forEach(clazz -> {
            Set<Field> fields = getAllFields(clazz);
            for (Field field : fields) {
                SensitiveDataModel sensitiveDataModel = parseSensitiveDataFromField(field);
                if (sensitiveDataModel != null) {
                    addSensitiveData(sensitiveDataModel);
                }
            }
        });
        logger.info(String.format("[脱敏组件扫描敏感字段] 扫描结束 扫描class文件个数:%d," +
                        "敏感信息个数:%d \n扫描敏感信息集：%s", clazzSet.size(),
                sensitiveDataModelSet.size(), BeanHelper.toJSON(sensitiveDataModelSet)));
    }

    public static void loadSensitiveDataInFile(String file){
        Properties properties = new Properties();
        try {
            properties.load(SensitiveDataModelManager.class.getClassLoader().getResourceAsStream(sensitiveDataModelSource));
        } catch (Exception e) {
            logger.error("read sensitive-data.properties fail", e);
            return;
        }

        Enumeration<String> sensitiveTypes = (Enumeration<String>) properties.propertyNames();

        Set<SensitiveDataModel> sensitiveDataModelSetTemp = new HashSet<>();
        while(sensitiveTypes.hasMoreElements()){
            String sensitiveTypeName = sensitiveTypes.nextElement();
            SensitiveType sensitiveType = null;
            try{
                sensitiveType = Enum.valueOf(SensitiveType.class, sensitiveTypeName);
            }catch (IllegalArgumentException e){
                logger.error(String.format("SensitiveType[%s] is not exist,please check sensitive-data.properties", sensitiveTypeName));
                continue;
            }

            String sensitiveKeyNames = properties.getProperty(sensitiveTypeName);
            if (StringUtils.isNotBlank(sensitiveKeyNames)){
                String[] sensitiveKeyNameArray = sensitiveKeyNames.split(",");
                for (String sensitiveKeyName : sensitiveKeyNameArray) {
                    SensitiveDataModel sensitiveDataModel = SensitiveDataModel.builder()
                            .sensitiveType(sensitiveType)
                            .name(sensitiveKeyName)
                            .build();
                    sensitiveDataModelSetTemp.add(sensitiveDataModel);
                }
            }
        }

        logger.info(String.format("配置文件[%s]中配置敏感信息个数:%d\n配置文件敏感信息集：%s", sensitiveDataModelSource,
                sensitiveDataModelSetTemp.size(), BeanHelper.toJSON(sensitiveDataModelSetTemp)));
        sensitiveDataModelSet.addAll(sensitiveDataModelSetTemp);
    }

    private static SensitiveDataModel parseSensitiveDataFromField(Field field) {
        Desensitize desensitizeAnnotation = field.getAnnotation(Desensitize.class);
        if (desensitizeAnnotation == null) {
            return null;
        }

        String name;
        JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
        if (jsonPropertyAnnotation != null) {
            name = jsonPropertyAnnotation.value();
        } else {
            name = field.getName();
        }
        SensitiveType sensitiveType = desensitizeAnnotation.type();

        SensitiveDataModel sensitiveDataModel = SensitiveDataModel.builder()
                .name(name)
                .sensitiveType(sensitiveType)
                .build();
        return sensitiveDataModel;
    }

    /**
     * 增加敏感信息模型数据
     *
     * @param sensitiveDataModel
     */
    public static void addSensitiveData(SensitiveDataModel sensitiveDataModel) {
        Objects.requireNonNull(sensitiveDataModel);
        if (sensitiveDataModel == null || StringUtils.isBlank(sensitiveDataModel.getName())) {
            return;
        }
        if (sensitiveDataModel.getSensitiveType() == null) {
            sensitiveDataModel.setSensitiveType(SensitiveType.HIGH_LEVEL_PRIVACY_DATA);
        }
        sensitiveDataModelSet.add(sensitiveDataModel);
    }

    public static Set<SensitiveDataModel> getAllSensitiveData() {
        return sensitiveDataModelSet;
    }


    private static Set<Class> getAllClasses(String[] packages) {
        Set<Class> classes = new HashSet<>();
        for (String pkg : packages) {
            classes.addAll(getAllClasses(pkg));
        }
        return classes;
    }

    private static Set<Field> getAllFields(Class clazz) {
        Set<Field> fields = ObjectUtils.getAllFields(clazz);
        return fields;
    }


    /**
     * @param pkg
     * @return
     */
    private static Set<Class<?>> getAllClasses(String pkg) {
        logger.info("[脱敏组件扫描敏感字段] 扫描包:" + pkg);
        Set<Class<?>> classes = new LinkedHashSet<>();

        String pkgDirName = pkg.replace('.', '/');
        try {
            Enumeration<URL> urls = SensitiveDataModelManager.class.getClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {// 如果是以文件的形式保存在服务器上
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");// 获取包的物理路径
                    findClassesByFile(pkg, filePath, classes);
                } else if ("jar".equals(protocol)) {// 如果是 jar 包文件
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    findClassesByJar(pkg, jar, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static void findClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) {
        logger.info(String.format("[脱敏组件扫描敏感字段] 文件形式包下类扫描：pkgName=%s pkgPath=%s ", pkgName, pkgPath));
        File dir = new File(pkgPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        // 过滤获取目录，or class文件
        File[] dirFiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));


        if (dirFiles == null || dirFiles.length == 0) {
            return;
        }

        String className;
        Class clz;
        for (File f : dirFiles) {
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(),
                        pkgPath + "/" + f.getName(),
                        classes);
                continue;
            }

            className = f.getName();
            logger.info("[脱敏组件扫描敏感字段] 扫描类：" + className);
            className = className.substring(0, className.length() - 6);
            // 加载类
            try {
                clz = Class.forName(pkgName + "." + className);
                if (clz != null) {
                    classes.add(clz);
                }
            } catch (ClassNotFoundException e) {
                logger.error("脱敏组件加载类异常", e);
            }

        }
    }

    private static void findClassesByJar(String pkgName, JarFile jar, Set<Class<?>> classes) {
        logger.info(String.format("[脱敏组件扫描敏感字段] jar类型包下类扫描：pkgName=%s jarName=%s", pkgName, jar.getName()));
        String pkgDir = pkgName.replace(".", "/");


        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name, className;
        Class<?> clazz;
        while (entry.hasMoreElements()) {
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }


            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                // 非指定包路径， 非 class 文件
                continue;
            }


            // 去掉后面的 ".class", 将路径转为 package 格式
            className = name.substring(0, name.length() - 6);
            logger.info("[脱敏组件扫描敏感字段] 扫描类：" + className);
            try {
                clazz = Class.forName(className.replace("/", "."));
                if (clazz != null) {
                    classes.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                logger.error("脱敏组件加载类异常", e);
            }
        }
    }

}
