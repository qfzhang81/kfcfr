package cn.kfcfr.core.generator;

import wocentral.infra.core.bean.BeanProperty;
import wocentral.infra.core.format.ParserUtils;
import wocentral.infra.core.generator.id.GenerateLongWithMinuteDiff;
import wocentral.infra.core.generator.id.IGenerateLong;

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
        startDiff = GenerateDateUtils.generateOne(year, month - 1, day);
        port = ParserUtils.string2Int(strPort);
        init(startDiff, port);
    }

    protected void init(Date startDiff, Integer port) {
        generateIdLong = new GenerateLongWithMinuteDiff(startDiff, port);
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
        BeanProperty.addIdWithDiff(list, clazz, propertyName, idList);
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
        BeanProperty.addValueWithSame(list, clazz, propertyName, valId, Long.class);
    }
}
