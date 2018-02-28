package cn.kfcfr.persistence.common.repository;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ICrudRepository<T> {
    int add(T entity, boolean ignoreNullField);

    int add(List<T> entities, boolean ignoreNullField);

    int update(T entity, boolean ignoreNullField);

    int update(List<T> entities, boolean ignoreNullField);

    int deleteByKey(Object key);
}
