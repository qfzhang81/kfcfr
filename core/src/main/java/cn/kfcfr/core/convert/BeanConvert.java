package cn.kfcfr.core.convert;

import java.util.List;

/**
 * 对象转换
 * Created by zhangqf on 2017/6/26.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class BeanConvert {

    /***
     * 源对象属性拷贝到目标对象上
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param source 源对象，不能为null
     * @param target 目标对象，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象
     * @throws ReflectiveOperationException 由Class<T>.newInstance()抛出
     */
    protected static <S, T> T cast(Class<?> sourceClazz, Class<?> targetClazz, S source, T target) throws ReflectiveOperationException {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (source == null) throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        if (target == null) throw new IllegalArgumentException("Parameter 'target' cannot be null.");
        if (sourceClazz.isAssignableFrom(targetClazz)) {
            target = (T) source;
        }
        else {
            target = BeanCopy.copyProperties(source, target);
        }
        return target;
    }

    /***
     * 源对象转换成目标对象
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param source 源对象，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象
     * @throws ReflectiveOperationException 由Class<T>.newInstance()抛出
     */
    public static <S, T> T cast(Class<?> sourceClazz, Class<?> targetClazz, S source) throws ReflectiveOperationException {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (source == null) throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        T target = (T) targetClazz.newInstance();
        return cast(sourceClazz, targetClazz, source, target);
    }

    /***
     * 源对象列表转换成目标对象，并添加到目标对象列表
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param sourceList 源对象列表，不能为null
     * @param targetList 目标对象列表，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象列表
     * @throws ReflectiveOperationException 由Class<T>.newInstance()抛出
     */
    public static <S, T> List<T> castList(Class<?> sourceClazz, Class<?> targetClazz, List<S> sourceList, List<T> targetList) throws ReflectiveOperationException {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (sourceList == null) throw new IllegalArgumentException("Parameter 'sourceList' cannot be null.");
        if (targetList == null) throw new IllegalArgumentException("Parameter 'targetList' cannot be null.");
        if (sourceClazz.isAssignableFrom(targetClazz)) {
            targetList = (List<T>) sourceList;
        }
        else {
            targetList = BeanCopy.copyProperties(sourceClazz, targetClazz, sourceList, targetList);
        }
        return targetList;
    }

    /***
     * 源对象列表转换成目标对象列表
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param sourceList 源对象列表，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象列表
     * @throws ReflectiveOperationException 由Class<T>.newInstance()抛出
     */
    public static <S, T> List<T> castList(Class<?> sourceClazz, Class<?> targetClazz, List<S> sourceList) throws ReflectiveOperationException {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (sourceList == null) throw new IllegalArgumentException("Parameter 'sourceList' cannot be null.");
        List<T> targetList = sourceList.getClass().newInstance();
        return castList(sourceClazz, targetClazz, sourceList, targetList);
    }
}
