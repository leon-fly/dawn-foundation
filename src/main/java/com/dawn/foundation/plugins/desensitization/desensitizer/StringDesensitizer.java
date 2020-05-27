package com.dawn.foundation.plugins.desensitization.desensitizer;
import com.dawn.foundation.plugins.desensitization.SensitiveDataModelManager;
import com.dawn.foundation.plugins.desensitization.enumeration.BisDataFormat;
import com.dawn.foundation.plugins.desensitization.model.SensitiveDataModel;
import org.apache.commons.lang3.StringUtils;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : leonwang
 * @Descpriction
 * @Date:created 2018/9/19
 */
public class StringDesensitizer {
    //空格、换行不敏感 JSON_BIS_DATA_SECTION_END_FLAG指定的字符为解析业务数据片段结尾符
    private static final String JSON_BIS_DATA_SECTION_REGEX = "\"\\w+\"[ ]*[:][ ]*(\"?)[^\"{]+\\1[,\\n}]";

    //空格、换行不敏感
    private static final String XML_BIS_DATA_SECTION_REGEX = "<(\\w+)>[\\n]*[ ]*[^<]*[\\n]*[ ]*</\\1>";

    private static final char[] JSON_BIS_DATA_SECTION_END_FLAG = {',', '\n', '}'};

    /**
     * 对打印字符串进行脱敏
     *
     * @param printInfo     打印内容
     * @param bisDataFormat 业务数据格式
     * @return
     */
    public static String desensitize(String printInfo, BisDataFormat bisDataFormat) {
        if (StringUtils.isBlank(printInfo)){
            return printInfo;
        }
        //获取脱敏的敏感源集合
        Set<SensitiveDataModel> sensitiveDataModelSet = SensitiveDataModelManager.getAllSensitiveData();


        //校对需要进行匹配的敏感数据模板是否存在
        if (sensitiveDataModelSet == null){
            return printInfo;
        }

        //抽取业务数据片段
        List<String> bisDataSections = extractBisDataSections(printInfo, bisDataFormat);

        //替换敏感数据
        switch (bisDataFormat) {
            case JSON:
                return desensitizeJsonFormatBisDataSections(printInfo, bisDataSections, sensitiveDataModelSet);
            case XML:
                return desensitizeXmlFormatBisDataSections(printInfo, bisDataSections, sensitiveDataModelSet);
            default:
        }

        return printInfo;
    }

    /**
     * 抽取所有业务数据段
     * @param printInfo
     * @param bisDataFormat
     * @return
     */
    private static List<String> extractBisDataSections(String printInfo, BisDataFormat bisDataFormat) {
        Pattern pattern = null;
        switch (bisDataFormat) {
            case JSON:
                pattern = Pattern.compile(JSON_BIS_DATA_SECTION_REGEX);
                break;
            case XML:
                pattern = Pattern.compile(XML_BIS_DATA_SECTION_REGEX);
                break;
            default:
        }

        List<String> list = new LinkedList<>();
        Matcher matcher = pattern.matcher(printInfo);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    /**
     * 脱敏json格式的打印信息
     * @param printInfo
     * @param bisDataSections
     * @param sensitiveDataModelSet
     * @return 脱敏后的打印信息
     */
    private static String desensitizeJsonFormatBisDataSections(String printInfo, List<String> bisDataSections,
                                                               Set<SensitiveDataModel> sensitiveDataModelSet) {

        for (String bisDataSection : bisDataSections) {
            String[] propertyAndValue = bisDataSection.split(":");
            if (propertyAndValue.length != 2) {
                continue;
            }
            String property = propertyAndValue[0].replaceAll("\"", "").trim();
            String value = propertyAndValue[1].replaceAll("\"", "").trim();
            value = removeJsonEndFlagChar(value);
            printInfo = desensitizeBisDataSection(printInfo, bisDataSection, property, value, sensitiveDataModelSet);
        }
        return printInfo;
    }

    /**
     * 脱敏xml格式的打印信息
     * @param printInfo
     * @param bisDataSections
     * @param sensitiveDataModelSet
     * @return 脱敏后的打印信息
     */
    private static String desensitizeXmlFormatBisDataSections(String printInfo, List<String> bisDataSections,
                                                              Set<SensitiveDataModel> sensitiveDataModelSet) {
        for (String bisDataSection : bisDataSections) {
            int propertyEndIndex = bisDataSection.indexOf(">");
            String property = bisDataSection.substring(1, propertyEndIndex).trim();
            String regex = "</?[\\w]*>";   //匹配标签内容
            String value = bisDataSection.replaceAll(regex, "");
            if (StringUtils.isBlank(value)) {
                continue;
            }
            value = value.trim();
            printInfo = desensitizeBisDataSection(printInfo, bisDataSection, property, value, sensitiveDataModelSet);
        }
        return printInfo;
    }

    /**
     *
     * @param printInfo
     * @param bisDataSection
     * @param property
     * @param value
     * @param sensitiveDataModelSet
     * @return
     */
    private static String desensitizeBisDataSection(String printInfo, String bisDataSection, String property,
                                                    String value, Set<SensitiveDataModel> sensitiveDataModelSet) {
        if (StringUtils.isBlank(value)) {
            return printInfo;
        }

        for (SensitiveDataModel sensitiveDataModel : sensitiveDataModelSet) {
            if (sensitiveDataModel.getName().equalsIgnoreCase(property)) {
                String desensitizedValue = SensitiveTypeDesensitizer.desensitize(value,
                        sensitiveDataModel.getSensitiveType());
                String desensitizedBizDataSections = bisDataSection.replace(value, desensitizedValue);
                printInfo = printInfo.replace(bisDataSection, desensitizedBizDataSections);
            }
        }
        return printInfo;
    }

    /**
     * @param bisDataSection
     * @return
     */
    private static String removeJsonEndFlagChar(String bisDataSection) {
        char lastChar = bisDataSection.charAt(bisDataSection.length() - 1);
        for (char endFlag : JSON_BIS_DATA_SECTION_END_FLAG) {
            if (lastChar == endFlag) {
                return bisDataSection.substring(0, bisDataSection.length() - 1);
            }
        }
        return bisDataSection;
    }

}
