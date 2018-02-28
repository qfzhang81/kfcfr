package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ICrudRepository;
import cn.kfcfr.persistence.common.repository.ISelectRepository;
import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisCrudRepository<T> extends AbstractMybatisRepository implements ICrudRepository<T>, ISelectRepository<T> {
    protected abstract CommonCrudMapper<T> getMapper();

    @Override
    public int add(T entity, boolean ignoreNullField) {
        return getMapperUtil().add(getMapper(), entity, ignoreNullField);
    }

    @Override
    public int add(List<T> entities, boolean ignoreNullField) {
        return getMapperUtil().add(getMapper(), entities, ignoreNullField);
    }

    @Override
    public int update(T entity, boolean ignoreNullField) {
        return getMapperUtil().update(getMapper(), entity, ignoreNullField);
    }

    @Override
    public int update(List<T> entities, boolean ignoreNullField) {
        return getMapperUtil().update(getMapper(), entities, ignoreNullField);
    }

    @Override
    public int deleteByKey(Object key) {
        return getMapperUtil().deleteByKey(getMapper(), key);
    }

    @Override
    public T getByKey(Object key) {
        return getMapperUtil().getByKey(getMapper(), key);
    }

    @Override
    public PagedList<T> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        return getMapperUtil().getPagedList(getMapper(), searchConditions, pagedBounds);
    }
}
