package club.jackzhan.cloudstore.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-19 9:34
 *
 * @Author: JackZhan
 */
@Slf4j
public class BeanUtils {

    private static Map<Class, MethodAccess> methodMap = new HashMap<Class, MethodAccess>();

    private static Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();

    private static Map<Class, List<String>> fieldMap = new HashMap<Class, List<String>>();


    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> target) {
        List<T> targetList = new ArrayList<>();
        try {
            for (S s : sourceList) {
                targetList.add(copyProperties(s, target));
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return targetList;
    }

    public static <S, T> T copyProperties(S source, Class<T> target) {
        T t = null;
        try {
            t = target.newInstance();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return copyProperties(source, t);
    }

    public static <S, T> T copyProperties(S source, T target) {

        if (source == null) {
            return target;
        }
        MethodAccess descMethodAccess = methodMap.get(target.getClass());
        if (descMethodAccess == null) {
            descMethodAccess = cache(target);
        }
        MethodAccess orgiMethodAccess = methodMap.get(source.getClass());
        if (orgiMethodAccess == null) {
            orgiMethodAccess = cache(source);
        }

        List<String> fieldList = fieldMap.get(source.getClass());
        for (String field : fieldList) {
            String getKey = source.getClass().getName() + "." + "get" + field;
            String setkey = target.getClass().getName() + "." + "set" + field;
            Integer setIndex = methodIndexMap.get(setkey);
            if (setIndex != null) {
                int getIndex = methodIndexMap.get(getKey);
                // 参数一需要反射的对象
                // 参数二class.getDeclaredMethods 对应方法的index
                // 参数对三象集合
                descMethodAccess.invoke(target, setIndex.intValue(),
                        orgiMethodAccess.invoke(source, getIndex));
            }
        }
        return target;
    }

    // 单例模式
    private static MethodAccess cache(Object obj) {
        synchronized (obj.getClass()) {
            MethodAccess methodAccess = MethodAccess.get(obj.getClass());
            Field[] fields = obj.getClass().getDeclaredFields();
            List<String> fieldList = new ArrayList<String>(fields.length);
            for (Field field : fields) {
                if (Modifier.isPrivate(field.getModifiers())
                        && !Modifier.isStatic(field.getModifiers())) { // 是否是私有的，是否是静态的
                    // 非公共私有变量
                    String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
                    int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
                    int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
                    methodIndexMap.put(obj.getClass().getName() + "." + "get"
                            + fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
                    methodIndexMap.put(obj.getClass().getName() + "." + "set"
                            + fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
                    fieldList.add(fieldName); // 将属性名称放入集合里
                }
            }
            fieldMap.put(obj.getClass(), fieldList); // 将类名，属性名称注册到map中
            methodMap.put(obj.getClass(), methodAccess);
            return methodAccess;
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return (T) obj;
    }

    /**
     * 将javabean对象装换为map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }

    public static String beanToJsonStringWithDateFormat(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, JSON.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteDateUseDateFormat);
    }

}
