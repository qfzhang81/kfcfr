package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisSelectWithConvertRepository<M, T> extends AbstractMybatisSelectRepository<T> implements ISelectWithConvertRepository<M, T> {
    protected abstract BeanConverter<M, T> getConverter();

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
