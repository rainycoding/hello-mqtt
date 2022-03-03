package cn.raincoding.hello.mqtt.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zengqm
 * @date 2021/9/8 17:25
 */
@Log4j2
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * 将 java 对象转为 json
     *
     * @param obj java 对象
     * @return json 字符串
     */
    public static String obj2json(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("obj to json string failed", e);
            return "";
        }
    }

    /**
     * 将 java 对象转为 json，忽略 null 值
     *
     * @param obj java 对象
     * @return json 字符串
     */
    public static String obj2jsonIgnoreNull(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("obj to json string failed", e);
            return "";
        }
    }

    /**
     * 将 json 字符串转换为 java 对象
     *
     * @param jsonString json 字符串
     * @param clazz      指定 java 对象的字节码对象
     * @param <T>        泛型
     * @return java 对象
     */
    public static <T> T json2obj(String jsonString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            log.error("json string to obj failed", e);
            return null;
        }
    }

    /**
     * 将 json 字符串转换为存储指定 java 对象的 List 集合
     *
     * @param jsonString json 字符串
     * @param clazz      指定 java 对象的字节码对象
     * @param <T>        泛型
     * @return 存储指定 java 对象的 List 集合
     */
    public static <T> List<T> json2list(String jsonString, Class<T> clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            log.error("json string to list failed", e);
            return Collections.emptyList();
        }
    }

    /**
     * 将 json 字符串转换为 Map
     *
     * @param jsonString json 字符串
     * @return Map 对象
     */
    public static Map<String, Object> json2map(String jsonString) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("json string to map failed", e);
            return Collections.emptyMap();
        }
    }

    /**
     * 获取泛型的 Collection Type
     *
     * @param collectionClass 泛型的 Collection
     * @param elementClass 元素类
     * @return Java 类型
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClass) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClass);
    }

}
