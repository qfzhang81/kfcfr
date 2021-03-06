package cn.kfcfr.core.convert;

import cn.kfcfr.core.exception.WrappedException;
import cn.kfcfr.core.system.ClassInstance;
import net.sf.cglib.beans.BeanCopier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by zhangqf on 2017/6/8.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused", "all"})
public class BeanCopy {

    /***
     * 拷贝单个bean对象属性
     * @param source 源对象，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, Class<?> targetClazz) {
        if (source == null) throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        T target = ClassInstance.newInstance(targetClazz);
        BeanCopier bc = BeanCopier.create(source.getClass(), targetClazz, false);
        copyProperties(bc, source, target);
        return target;
    }

    /***
     * 拷贝单个bean对象属性
     * @param source 源对象，不能为null
     * @param target 目标对象，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, T target) {
        if (source == null) throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        if (target == null) throw new IllegalArgumentException("Parameter 'target' cannot be null.");
        BeanCopier bc = BeanCopier.create(source.getClass(), target.getClass(), false);
        copyProperties(bc, source, target);
        return target;
    }

    /***
     * 拷贝整个列表的bean对象属性，列表属性也拷贝。源对象转换成目标对象后添加到目标对象列表
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param sourceList 源对象列表，不能为null
     * @param targetList 目标对象列表，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象列表
     */
    public static <S, T> List<T> copyProperties(Class<?> sourceClazz, Class<?> targetClazz, List<S> sourceList, List<T> targetList) {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (sourceList == null) throw new IllegalArgumentException("Parameter 'sourceList' cannot be null.");
        if (targetList == null) throw new IllegalArgumentException("Parameter 'targetList' cannot be null.");
        BeanCopier bc = BeanCopier.create(sourceList.getClass(), targetList.getClass(), false);
        copyProperties(bc, sourceList, targetList);
        bc = BeanCopier.create(sourceClazz, targetClazz, false);
        for (S source : sourceList) {
            T target = ClassInstance.newInstance(targetClazz);
            copyProperties(bc, source, target);
            targetList.add(target);
        }
        return targetList;
    }

    /***
     * 拷贝整个列表的bean对象属性，列表属性也拷贝
     * @param sourceClazz 源对象类型，不能为null
     * @param targetClazz 目标对象类型，不能为null
     * @param sourceList 源对象列表，不能为null
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 目标对象列表
     */
    public static <S, T> List<T> copyProperties(Class<?> sourceClazz, Class<?> targetClazz, List<S> sourceList) {
        if (sourceClazz == null) throw new IllegalArgumentException("Parameter 'sourceClazz' cannot be null.");
        if (targetClazz == null) throw new IllegalArgumentException("Parameter 'targetClazz' cannot be null.");
        if (sourceList == null) throw new IllegalArgumentException("Parameter 'sourceList' cannot be null.");
        List<T> targetList = ClassInstance.newInstance(sourceList.getClass());
        copyProperties(sourceClazz, targetClazz, sourceList, targetList);
        return targetList;
    }

    private static void copyProperties(BeanCopier bc, Object source, Object target) {
        bc.copy(source, target, null);
    }

    /***
     * 同一Class深度拷贝对象
     * @param source 源对象，不能为null
     * @param <T> 对象类型
     * @return 目标对象
     */
    public static <T> T deepClone(T source) {
        if (source == null) throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        Object target;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            //将对象写到流里进行深度拷贝
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(source);//将对象写到流里
            oos.flush();
            oos.close();
            oos = null;

            byte[] arrByte = byteArrayOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrByte);
            ois = new ObjectInputStream(byteArrayInputStream);
            target = ois.readObject();//从流里读出来
            ois.close();
            ois = null;
        }
        catch (Exception ex) {
            throw new WrappedException(MessageFormat.format("Copy {0} failed.", source), ex);
        }
        finally {
            try {
                if (oos != null) {
                    oos.close();
                    oos = null;
                }
                if (ois != null) {
                    ois.close();
                    ois = null;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return (T) target;
    }
}
