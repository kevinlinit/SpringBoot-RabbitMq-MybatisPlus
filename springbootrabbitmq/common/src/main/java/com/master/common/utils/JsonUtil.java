package com.master.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import static com.master.common.utils.BeanUtil.requireNonNull;
import static com.master.common.utils.StrUtil.isBlank;

/**
 * Description: json数据转换类
 * Created by masterl on 2020/2/27 10:56 PM
 */
@Slf4j
public final class JsonUtil {

    /**
     * 默认json格式化方式
     */
    private static final SerializerFeature[] DEFAULT_FORMAT = {SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.QuoteFieldNames, SerializerFeature.SkipTransientField,
            SerializerFeature.SortField};

    private JsonUtil() {
    }

    /**
     * 从json获取指定key的字符串
     *
     * @param json json字符串
     * @param key  字符串的key
     * @return 指定key的值
     */
    public static Object getStringFromJSONObject(final String json, final String key) {
        requireNonNull(json, "json is null");
        return JSON.parseObject(json).getString(key);
    }

    /**
     * 将字符串转换成JSON字符串
     *
     * @param jsonStr json字符串
     * @return 转换成的json对象
     */
    public static JSONObject getJSONFromString(final String jsonStr) {
        if (isBlank(jsonStr)) {
            return new JSONObject();
        }
        return JSON.parseObject(jsonStr);
    }

    /**
     * 将json字符串，转换成指定java bean
     *
     * @param jsonStr   json串对象
     * @param beanClass 指定的bean
     * @param <T>       任意bean的类型
     * @return 转换后的java bean对象
     */
    public static <T> T jsonStrToBean(String jsonStr, Class<T> beanClass) {
        requireNonNull(jsonStr, "jsonStr is null");
        JSONObject jo = JSON.parseObject(jsonStr);
        jo.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
        return JSON.parseObject(jo.toJSONString(), beanClass);
    }

    /**
     * 将json字符串，转换成指定java bean
     *
     * @param jsonMap   jsonMap对象
     * @param beanClass 指定的bean
     * @param <T>       任意bean的类型
     * @return 转换后的java bean对象
     */
    public static <T> T jsonMapToBean(Map<String, Object>  jsonMap, Class<T> beanClass) {
        requireNonNull(jsonMap, "jsonMap is null");
        JSONObject jo = JSON.parseObject(toJson(jsonMap));
        jo.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
        return JSON.parseObject(jo.toJSONString(), beanClass);
    }

    /**
     * 将java对象转换成指定json字符串
     *
     * @param obj 需要转换的java bean
     * @param <T> 入参对象类型泛型
     * @return 对应的json字符串
     */
    public static <T> String toJson(T obj) {
        requireNonNull(obj, "obj is null");
        return JSON.toJSONString(obj, DEFAULT_FORMAT);
    }

    /**
     * 美化传入的json,使得该json字符串容易查看
     *
     * @param jsonStr 需要处理的json串
     * @return 美化后的json串
     */
    public static String prettyFormatJson(String jsonStr) {
        requireNonNull(jsonStr, "jsonString is null");
        return JSON.toJSONString(getJSONFromString(jsonStr), true);
    }

    /**
     * 通过Map生成一个json字符串
     *
     * @param map 需要转换的map
     * @return json串
     */
    public static String toJson(Map<String, Object> map) {
        requireNonNull(map, "map is null");
        return JSON.toJSONString(map, DEFAULT_FORMAT);
    }

    /**
     * 将传入的json字符串转换成Map
     *
     * @param jsonStr 需要处理的json串
     * @return 对应的map
     */
    public static Map<String, Object> toMap(String jsonStr) {
        requireNonNull(jsonStr, "jsonStr is null");
        return getJSONFromString(jsonStr);
    }

    /**
     * 通过List生成一个json字符串
     *
     * @param list 需要转换的list
     * @return json串
     */
    public static String toJson(List<Object> list) {
        requireNonNull(list, "list is null");
        return JSON.toJSONString(list, DEFAULT_FORMAT);
    }

    /**
     * 将json字符串，转换成指定List<T>
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clazz){
        requireNonNull(jsonStr, "jsonStr is null");
        return JSON.parseArray(jsonStr,clazz);
    }

    /**
     * 将jsonp字符串转换成JSON字符串
     *
     * @param jsonp jsonp字符串
     * @return 转换成的json对象
     */
    public static JSONObject parseJSONP(final String jsonp){
        if (isBlank(jsonp)) {
            return new JSONObject();
        }

        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex+1, endIndex);
        return JSON.parseObject(json);
    }

}