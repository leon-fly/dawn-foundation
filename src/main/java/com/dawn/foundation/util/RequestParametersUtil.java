package com.dawn.foundation.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestParametersUtil {

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("signature")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        return createLinkStringByOrderedKeys(params, keys);
    }

    /**
     * 除去数组中的空值和固定参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray, String... paramNames) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        List<String> exceptParamKeys = Stream.of(paramNames).collect(Collectors.toList());

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || exceptParamKeys.contains(key)) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public static String createLinkStringByOrderedMap(LinkedHashMap params) {
        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<String>(params.keySet());
        return createLinkStringByOrderedKeys(params, keys);
    }

    /**
     * 从具有url参数特点的string中解析参数
     *
     * @param urlLike
     * @return
     */
    public static Map<String, Object> parseParams(String urlLike) {
        if (StringUtils.isNotEmpty(urlLike)) {
            HashMap<String, Object> paramsMap = new HashMap<>();
            String[] paramItems = urlLike.split("&");
            for (String paramItem : paramItems) {
                String[] paramPair = paramItem.split("=");
                if (paramPair.length == 2 || (paramItem.contains("="))) {
                    paramsMap.put(paramPair[0], paramPair.length == 2 ? paramPair[1] : "");
                }
            }
            return paramsMap;
        }
        return null;
    }

    private static String createLinkStringByOrderedKeys(Map<String, String> params, List<String> orderedKeys) {
        StringBuilder preSb = new StringBuilder();

        for (int i = 0; i < orderedKeys.size(); i++) {
            String key = orderedKeys.get(i);
            String value = params.get(key);

            if (i == orderedKeys.size() - 1) {//拼接时，不包括最后一个&字符
                preSb.append(key)
                        .append("=")
                        .append(value);

            } else {
                preSb.append(key)
                        .append("=")
                        .append(value)
                        .append("&");
            }
        }

        return preSb.toString();
    }


}
