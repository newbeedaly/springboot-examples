package cn.newbeedaly.easyexcel.util;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author newbeedaly
 * @since 2021/05/27
 */
public class CopyUtils {

    public static <T,R> R createBean(@NotNull T source, @NotNull Class<R> targetClass) {
        return createBean(source, targetClass, null);
    }

    public static <T,R> R createBean(@NotNull T source, @NotNull Class<R> targetClass, Collection<String> ignoreFields) {
        R target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("new instance exception!");
        }
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            if(ignoreFields!=null && ignoreFields.contains(sourceField.getName())){
                continue;
            }
            for (Field targetField : target.getClass().getDeclaredFields()) {
                sourceField.setAccessible(true);
                if (targetField.getName().equals(sourceField.getName()) && targetField.getType() == sourceField.getType()) {
                    targetField.setAccessible(true);
                    if (Modifier.isFinal(targetField.getModifiers()) || Modifier.isStatic(targetField.getModifiers())){
                        break;
                    }
                    try {
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException("reflect assignment exception!");
                    }
                }
            }
        }
        return target;
    }

    public static <T,R> List<R> createBeans(@NotNull List<T> sourceList, @NotNull Class<R> targetClass) {
        List<R> targetList = new ArrayList<>();
        for (T source : sourceList) {
            targetList.add(createBean(source, targetClass));
        }
        return targetList;
    }

    public static <T,R> List<R> createBeans(@NotNull List<T> sourceList, @NotNull Class<R> targetClass, Collection<String> ignoreFields) {
        List<R> targetList = new ArrayList<>();
        for (T source : sourceList) {
            targetList.add(createBean(source, targetClass, ignoreFields));
        }
        return targetList;
    }

}
