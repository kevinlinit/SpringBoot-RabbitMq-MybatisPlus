package com.master.common.utils;

import java.util.regex.Pattern;

/**
 * Description: 字符串常用方法工具类
 * Created by masterl on 2020/2/27 11:10 PM
 */
public final class StrUtil {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 此类不需要实例化
     */
    private StrUtil() {
    }

    /**
     * 判断一个字符串是否为空，null也会返回true
     *
     * @param str 需要判断的字符串
     * @return 是否为空，null也会返回true
     */
    public static boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 判断一个字符串是否不为空
     *
     * @param str 需要判断的字符串
     * @return 是否为空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断一组字符串是否有空值
     *
     * @param strs 需要判断的一组字符串
     * @return 判断结果，只要其中一个字符串为null或者为空，就返回true
     */
    public static boolean hasBlank(String... strs) {
        if (null == strs || 0 == strs.length) {
            return true;
        } else {
            for (String str : strs) {
                if (isBlank(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断一个字符串是否不为空
     *
     * @param str 需要判断的字符串
     * @return 为空返回""
     */
    public static String getNotNullStr(Object str) {
        return str == null ? "" : str.toString();
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

}
