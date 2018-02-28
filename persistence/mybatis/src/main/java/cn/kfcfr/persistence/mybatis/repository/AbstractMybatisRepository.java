package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ICrudWithConvertRepository;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;
import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.persistence.mybatis.mapper.EntityColumnMapConverter;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
public abstract class AbstractMybatisRepository<M, T> implements ICrudWithConvertRepository<M, T>, ISelectWithConvertRepository<M, T> {
    private static IPagedUtil pagedUtil;
    private static MapperUtil mapperUtil;
    private final EntityColumnMapConverter mapConverter = new EntityColumnMapConverter();

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

    protected MapperUtil getMapperUtil() {
        if (mapperUtil == null) {
            synchronized (this) {
                if (mapperUtil == null) {
                    mapperUtil = new MapperUtil(getPagedUtil(), mapConverter);
                }
            }
        }
        return mapperUtil;
    }

    protected abstract IPagedUtil createPagedUtil();

    protected abstract CommonCrudMapper<T> getCrudMapper();

    protected abstract CommonReadonlyMapper<T> getReadonlyMapper();

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
    public T getByKey(Object key) {
        return getMapperUtil().getByKey(getReadonlyMapper(), key);
    }

    @Override
    public T selectOne(List<PropertyCondition> searchConditions) {
        return getMapperUtil().getOne(getReadonlyMapper(), searchConditions);
    }

    @Override
    public PagedList<T> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        return getMapperUtil().getPagedList(getReadonlyMapper(), searchConditions, pagedBounds);
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
    public M getWithConvertByKey(Object key) throws ReflectiveOperationException {
        T entity = getMapperUtil().getByKey(getReadonlyMapper(), key);
        return getConverter().convertT2ToT1(entity);
    }

    @Override
    public M selectOneWithConvert(List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        T entity = getMapperUtil().getOne(getReadonlyMapper(), searchConditions);
        return getConverter().convertT2ToT1(entity);
    }

    @Override
    public PagedList<M> getWithConvertBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws ReflectiveOperationException {
        PagedList<T> pagedList = getMapperUtil().getPagedList(getReadonlyMapper(), searchConditions, pagedBounds);
        return getConverter().pagedT2ToT1(pagedList);
    }
    //endregion
}