package club.jackzhan.cloudstore.util;

import org.reflections.Reflections;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class AnnoManageUtil {

    /**
     * 获取指定文件下面的RequestMapping方法保存在mapp中
     *
     * @param packageName
     * @return
     */
    public static<T extends Annotation> List<List<T>> getAnnotations(String packageName, Class<T> clz ) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);

        // 存放url和ExecutorBean的对应关系
        List<List<T>> resp = new ArrayList<>();
        for (Class classes : classesList) {
            List<T> list = new ArrayList<>();
            resp.add(list);
            //得到该类下面的所有方法
            T classesAnnotation = (T) classes.getAnnotation(clz);
            list.add(classesAnnotation);
            Method[] methods = classes.getDeclaredMethods();

            for (Method method : methods) {
                //得到该类下面的clz注解
                T annotation = method.getAnnotation(clz);
                if (null != annotation) {
                    list.add(annotation);
                }
            }
        }
        return resp;
    }

}
