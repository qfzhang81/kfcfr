package cn.kfcfr.persistence.common.repository;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ISelectWithConvertRepository<M, T> extends ISelectRepository<T> {
    M getWithConvertByKey(Object key) throws ReflectiveOperationException;

    M selectOneWithConvert(List<PropertyCondition> searchConditions) throws ReflectiveOperationException;

    PagedList<M> getWithConvertBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws ReflectiveOperationException;
}
