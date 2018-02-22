package cn.kfcfr.core.generator;

import java.util.Date;
import java.util.List;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public interface GenerateLongUtils {

    Date getStartDiff();

    Integer getPort();

    /***
     * 根据当前时间生成一个带时间的ID
     * @return long型ID
     */
    long generateOne();

    /***
     * 根据当前时间生成多个带时间的顺序ID
     * @param length 顺序ID个数
     * @return long型ID的数组
     */
    List<LongRange> generateList(int length);

    /***
     * 根据规则生成不重复的顺序ID，并填充到对象列表的指定属性名
     * @param list 需填充ID的对象列表
     * @param clazz 对象类型
     * @param propertyName 要填充ID的属性名，类型必须为Long
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    <T> void addWithOneByOne(List<T> list, Class<T> clazz, String propertyName) throws ReflectiveOperationException;

    /***
     * 根据规则生成一个顺序ID，并填充到对象列表的指定属性名
     * @param list 需填充ID的对象列表
     * @param clazz 对象类型
     * @param propertyName 要填充ID的属性名，类型必须为Long
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    <T> void addWithSame(List<T> list, Class<T> clazz, String propertyName) throws ReflectiveOperationException;
}
