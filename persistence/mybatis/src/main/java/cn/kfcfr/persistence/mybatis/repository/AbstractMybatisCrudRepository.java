package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.persistence.common.repository.ICrudRepository;
import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisCrudRepository<T> extends AbstractMybatisSelectRepository<T> implements ICrudRepository<T> {
    protected abstract CommonCrudMapper<T> getCrudMapper();

    @Override
    protected CommonReadonlyMapper<T> getReadonlyMapper() {
        return getCrudMapper();
    }

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
}
