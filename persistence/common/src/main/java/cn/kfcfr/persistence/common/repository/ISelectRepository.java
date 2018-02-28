package cn.kfcfr.persistence.common.repository;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ISelectRepository<T> {
    T getByKey(Object key);

    T selectOne(List<PropertyCondition> searchConditions);

    PagedList<T> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions);
}
