package cn.kfcfr.core.generator;


import cn.kfcfr.core.convert.IntegerConvert;
import cn.kfcfr.core.generator.id.GenerateLongWithDiffMinute;
import cn.kfcfr.core.generator.id.IGenerateLong;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class GenerateLongUtilsImpl implements GenerateLongUtils {
    protected IGenerateLong generateIdLong;
    protected Date startDiff;
    protected Integer port;

    public Date getStartDiff() {
        return startDiff;
    }

    public Integer getPort() {
        return port;
    }

    public GenerateLongUtilsImpl(Date startDiff, Integer port) {
        this.startDiff = startDiff;
        this.port = port;
        init(startDiff, port);
    }

    public GenerateLongUtilsImpl(int year, int month, int day, String strPort) {
        startDiff = DateGenerator.generateOne(year, month - 1, day);
        port = IntegerConvert.parseString(strPort);
        init(startDiff, port);
    }

    protected void init(Date startDiff, Integer port) {
        generateIdLong = new GenerateLongWithDiffMinute(startDiff, port);
    }

    /***
     * 根据当前时间生成一个带时间的ID
     * @return long型ID
     */
    @Override
    public long generateOne() {
        return generateIdLong.getOne();
    }

    /***
     * 根据当前时间生成多个带时间的顺序ID
     * @param length 顺序ID个数
     * @return long型ID的范围列表
     */
    @Override
    public List<LongRange> generateList(int length) {
        return generateIdLong.getList(length);
    }

    /***
     * 根据当前时间生成多个带时间的顺序ID
     * @param length 顺序ID个数
     * @return long型ID的数组
     */
    @Override
    public long[] generateArray(int length) {
        List<LongRange> lst = generateIdLong.getList(length);
        long[] rst = new long[length];
        int index = 0;
        for (LongRange range : lst) {
            for (long id = range.getFrom(); id <= range.getTo(); id++) {
                rst[index] = id;
                index++;
            }
        }
        return rst;
        //return rst.stream().mapToLong(t -> t.longValue()).toArray();
    }

    /***
     * 根据规则生成不重复的顺序ID，并填充到对象列表的指定属性名
     * @param list 需填充ID的对象列表
     * @param clazz 对象类型
     * @param propertyName 要填充ID的属性名，类型必须为Long
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    public <T> void addWithOneByOne(List<T> list, Class<T> clazz, String propertyName) throws ReflectiveOperationException {
        if (list == null || list.size() == 0) return;
        //生成id
        List<LongRange> idList = generateList(list.size());
        if (idList == null || idList.size() == 0) return;
        //填充
        addIdWithDiff(list, clazz, propertyName, idList);
    }

    /***
     * 根据规则生成一个顺序ID，并填充到对象列表的指定属性名
     * @param list 需填充ID的对象列表
     * @param clazz 对象类型
     * @param propertyName 要填充ID的属性名，类型必须为Long
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    public <T> void addWithSame(List<T> list, Class<T> clazz, String propertyName) throws ReflectiveOperationException {
        if (list == null || list.size() == 0) return;
        //生成id
        long valId = generateOne();
        //填充
        addValueWithSame(list, clazz, propertyName, valId, Long.class);
    }

    /***
     * 把指定值填充到对象列表的指定属性名
     * @param list 需填充值的对象列表
     * @param listClazz 对象类型
     * @param propertyName 要填充值的属性名，类型必须同valueClazz
     * @param value 要填充的值
     * @param valueClazz 值的类型
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    protected static <T> void addValueWithSame(List<T> list, Class<T> listClazz, String propertyName, Object value, Class<?> valueClazz) throws ReflectiveOperationException {
        if (list == null || list.size() == 0) return;
        //检查属性名，得到set方法
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method method = listClazz.getMethod(methodName, valueClazz);
        //逐个填充
        if (method != null) {
            for (T t : list) {
                method.invoke(t, value);
            }
        }
    }

    /***
     * 把顺序ID填充到对象列表的指定属性名
     * @param list 需填充ID的对象列表
     * @param clazz 对象类型
     * @param propertyName 要填充ID的属性名，类型必须为Long
     * @param idList 顺序ID列表
     * @param <T> 对象类型
     * @throws ReflectiveOperationException 反射抛出的异常
     */
    protected static <T> void addIdWithDiff(List<T> list, Class<T> clazz, String propertyName, List<LongRange> idList) throws ReflectiveOperationException {
        if (list == null || list.size() == 0) return;
        if (idList == null || idList.size() == 0) return;
        //检查属性名，得到set方法
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method method = clazz.getMethod(methodName, Long.class);
        //逐个填充id
        if (method != null) {
            int idIndex = 0;
            LongRange longRange = idList.get(idIndex);
            long valId = -1;
            for (T t : list) {
                if (valId < 0) {
                    //初始化
                    valId = longRange.getFrom();
                }
                else {
                    valId++;
                }
                if (valId > longRange.getTo()) {
                    //已到本段末尾，移到下一段
                    idIndex++;
                    longRange = idList.get(idIndex);
                    valId = longRange.getFrom();
                }
                //赋值
                method.invoke(t, valId);
            }
        }
    }
}
