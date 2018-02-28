package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ISelectRepository;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.persistence.mybatis.mapper.EntityColumnMapConverter;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisSelectRepository<T> implements ISelectRepository<T> {
    private MapperUtil mapperUtil;

    protected MapperUtil getMapperUtil() {
        if (mapperUtil == null) {
            synchronized (this) {
                if (mapperUtil == null) {
                    mapperUtil = new MapperUtil(getPagedUtil(), new EntityColumnMapConverter());
                }
            }
        }
        return mapperUtil;
    }

    protected abstract IPagedUtil getPagedUtil();

    protected abstract CommonReadonlyMapper<T> getReadonlyMapper();

    @Override
    public T getByKey(Object key) {
        return getMapperUtil().getByKey(getReadonlyMapper(), key);
    }

    @Override
    public PagedList<T> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        return getMapperUtil().getPagedList(getReadonlyMapper(), searchConditions, pagedBounds);
    }
}
