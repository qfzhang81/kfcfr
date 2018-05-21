package cn.kfcfr.core.convert;

import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.system.ClassInstance;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 对象转换器，只能继承使用，子类不能是泛型
 * Created by zhangqf on 2017/6/23.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public abstract class BeanConverter<T1, T2> {
    private final Class<T1> t1Class;
    private final Class<T2> t2Class;

    public BeanConverter() {
        t1Class = (Class<T1>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        t2Class = (Class<T2>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected Class<T1> getT1Class() {
        return t1Class;
    }

    protected Class<T2> getT2Class() {
        return t2Class;
    }

    /***
     * T2列表转成T1列表
     * @param t2list T2列表
     * @return T1列表
     */
    public List<T1> listT2ToT1(List<T2> t2list) {
        List<T1> t1list = ClassInstance.newInstance(t2list.getClass());
        if (getT1Class().isAssignableFrom(getT2Class())) {
            for (T1 t1 : t1list) {
                t2list.add((T2) t1);
            }
        }
        else {
            t1list = BeanCopy.copyProperties(getT2Class(), getT1Class(), t2list, t1list);
        }
        return t1list;
    }

    /***
     * T1列表转成T2列表
     * @param t1list T1列表
     * @return T2列表
     */
    public List<T2> listT1ToT2(List<T1> t1list) {
        List<T2> t2list = ClassInstance.newInstance(t1list.getClass());
        if (getT2Class().isAssignableFrom(getT1Class())) {
            for (T1 t1 : t1list) {
                t2list.add((T2) t1);
            }
        }
        else {
            t2list = BeanCopy.copyProperties(getT1Class(), getT2Class(), t1list, t2list);
        }
        return t2list;
    }

    /***
     * T2对象转成T1对象
     * @param t2 T2对象
     * @return T1对象
     */
    public T1 convertT2ToT1(T2 t2) {
        return BeanConvert.cast(getT2Class(), getT1Class(), t2);
    }

    /***
     * T1对象转成T2对象
     * @param t1 T1对象
     * @return T2对象
     */
    public T2 convertT1ToT2(T1 t1) {
        return BeanConvert.cast(getT1Class(), getT2Class(), t1);
    }

    /***
     * T2分页列表转成T1分页列表
     * @param t2paged T2分页列表
     * @return T1分页列表
     */
    public PagedList<T1> pagedT2ToT1(PagedList<T2> t2paged) {
        List<T1> t1List = BeanConvert.castList(getT2Class(), getT1Class(), t2paged.getList());
        return new PagedList<>(t1List, t2paged.getTotal(), t2paged.getPagedBounds());
    }

    /***
     * T1分页列表转成T2分页列表
     * @param t1paged T1分页列表
     * @return T2分页列表
     */
    public PagedList<T2> pagedT1ToT2(PagedList<T1> t1paged) {
        List<T2> t1List = BeanConvert.castList(getT1Class(), getT2Class(), t1paged.getList());
        return new PagedList<>(t1List, t1paged.getTotal(), t1paged.getPagedBounds());
    }
}
