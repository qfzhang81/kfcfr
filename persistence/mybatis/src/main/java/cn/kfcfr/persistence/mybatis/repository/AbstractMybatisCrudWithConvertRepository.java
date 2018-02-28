package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ICrudWithConvertRepository;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
public abstract class AbstractMybatisCrudWithConvertRepository<M, T> extends AbstractMybatisCrudRepository<T> implements ICrudWithConvertRepository<M, T>, ISelectWithConvertRepository<M, T> {
    protected abstract BeanConverter<M, T> getConverter();

    @Override
    public int addWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException {
        T entity = getConverter().convertT1ToT2(model);
        return getMapperUtil().add(getMapper(), entity, ignoreNullField);
    }

    @Override
    public int addWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException {
        List<T> entities = getConverter().listT1ToT2(models);
        return getMapperUtil().add(getMapper(), entities, ignoreNullField);
    }

    @Override
    public int updateWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException {
        T entity = getConverter().convertT1ToT2(model);
        return getMapperUtil().update(getMapper(), entity, ignoreNullField);
    }

    @Override
    public int updateWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException {
        List<T> entities = getConverter().listT1ToT2(models);
        return getMapperUtil().update(getMapper(), entities, ignoreNullField);
    }

    @Override
    public M getWithConvertByKey(Object key) throws ReflectiveOperationException {
        T entity = getMapperUtil().getByKey(getMapper(), key);
        return getConverter().convertT2ToT1(entity);
    }

    @Override
    public PagedList<M> getWithConvertBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        PagedList<T> pagedList = getMapperUtil().getPagedList(getMapper(), searchConditions, pagedBounds);
        return getConverter().pagedT2ToT1(pagedList);
    }
}
