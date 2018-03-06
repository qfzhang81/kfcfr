package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ICrudWithConvertRepository;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;
import cn.kfcfr.persistence.mybatis.mapper.*;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
public abstract class AbstractMybatisRepository<M, T> implements ICrudWithConvertRepository<M, T>, ISelectWithConvertRepository<M, T> {
    private static IPagedUtil pagedUtil;
    private static TkMapperUtil mapperUtil;

    protected IPagedUtil getPagedUtil() {
        if (pagedUtil == null) {
            synchronized (this) {
                if (pagedUtil == null) {
                    pagedUtil = createPagedUtil();
                }
            }
        }
        return pagedUtil;
    }

    protected TkMapperUtil getMapperUtil() {
        if (mapperUtil == null) {
            synchronized (this) {
                if (mapperUtil == null) {
                    mapperUtil = new TkMapperUtil(getPagedUtil());
                }
            }
        }
        return mapperUtil;
    }

    protected abstract IPagedUtil createPagedUtil();

    protected abstract TkCrudMapper<T> getCrudMapper();

    protected abstract TkReadonlyMapper<T> getReadonlyMapper();

    protected abstract BeanConverter<M, T> getConverter();

    //region 实现ICrudRepository<T>接口
    @Override
    public int add(T entity, boolean ignoreNullField) {
        return getMapperUtil().add(getCrudMapper(), entity, ignoreNullField);
    }

    @Override
    public int add(List<T> entities, boolean ignoreNullField) {
        return getMapperUtil().add(getCrudMapper(), entities, ignoreNullField);
    }

    @Override
    public int update(T entity, boolean ignoreNullField) {
        return getMapperUtil().update(getCrudMapper(), entity, ignoreNullField);
    }

    @Override
    public int update(List<T> entities, boolean ignoreNullField) {
        return getMapperUtil().update(getCrudMapper(), entities, ignoreNullField);
    }

    @Override
    public int deleteByKey(Object key) {
        return getMapperUtil().deleteByKey(getCrudMapper(), key);
    }
    //endregion

    //region 实现ISelectRepository<T>接口
    @Override
    public T selectByKey(Object key) {
        return getMapperUtil().selectByKey(getReadonlyMapper(), key);
    }

    @Override
    public T selectOne(List<PropertyCondition> searchConditions) {
        return getMapperUtil().selectOne(getReadonlyMapper(), searchConditions);
    }

    @Override
    public List<T> selectList(List<PropertyCondition> searchConditions) {
        return getMapperUtil().selectList(getReadonlyMapper(), searchConditions);
    }

    @Override
    public PagedList<T> selectPagedList(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        return getMapperUtil().selectPagedList(getReadonlyMapper(), searchConditions, pagedBounds);
    }
    //endregion

    //region 实现ICrudWithConvertRepository<M, T>接口
    @Override
    public int addWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException {
        T entity = getConverter().convertT1ToT2(model);
        return getMapperUtil().add(getCrudMapper(), entity, ignoreNullField);
    }

    @Override
    public int addWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException {
        List<T> entities = getConverter().listT1ToT2(models);
        return getMapperUtil().add(getCrudMapper(), entities, ignoreNullField);
    }

    @Override
    public int updateWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException {
        T entity = getConverter().convertT1ToT2(model);
        return getMapperUtil().update(getCrudMapper(), entity, ignoreNullField);
    }

    @Override
    public int updateWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException {
        List<T> entities = getConverter().listT1ToT2(models);
        return getMapperUtil().update(getCrudMapper(), entities, ignoreNullField);
    }
    //endregion

    //region 实现ISelectWithConvertRepository<M, T>接口
    @Override
    public M selectWithConvertByKey(Object key) throws ReflectiveOperationException {
        T entity = getMapperUtil().selectByKey(getReadonlyMapper(), key);
        return getConverter().convertT2ToT1(entity);
    }

    @Override
    public M selectOneWithConvert(List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        T entity = getMapperUtil().selectOne(getReadonlyMapper(), searchConditions);
        return getConverter().convertT2ToT1(entity);
    }

    @Override
    public List<M> selectListWithConvert(List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        List<T> list = getMapperUtil().selectList(getReadonlyMapper(), searchConditions);
        return getConverter().listT2ToT1(list);
    }

    @Override
    public PagedList<M> selectPagedListWithConvert(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        PagedList<T> pagedList = getMapperUtil().selectPagedList(getReadonlyMapper(), searchConditions, pagedBounds);
        return getConverter().pagedT2ToT1(pagedList);
    }
    //endregion
}
