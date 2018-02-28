package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.common.repository.ISelectRepository;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisSelectRepository<T> extends AbstractMybatisRepository implements ISelectRepository<T> {


    protected abstract CommonReadonlyMapper<T> getMapper();

    @Override
    public T getByKey(Object key) {
        return getMapperUtil().getByKey(getMapper(), key);
    }

    @Override
    public PagedList<T> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        return getMapperUtil().getPagedList(getMapper(), searchConditions, pagedBounds);
    }
}
