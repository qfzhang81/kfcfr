package cn.kfcfr.persistence.mybatis.mapper;

import cn.kfcfr.core.expression.PropertyCondition;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pagination.SortCondition;
import cn.kfcfr.core.pagination.SortDirection;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Mapper辅助类
 * Created by zhangqf on 2017/6/27.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class MapperUtil implements Serializable {
    private static final long serialVersionUID = -9022124094847750630L;
    private IPagedUtil pagedUtil;
    private EntityColumnMapConverter mapConverter;

    public void setPagedUtil(IPagedUtil pagedUtil) {
        this.pagedUtil = pagedUtil;
    }

    public void setMapConverter(EntityColumnMapConverter mapConverter) {
        this.mapConverter = mapConverter;
    }

    public MapperUtil() {}
    public MapperUtil(IPagedUtil pagedUtil, EntityColumnMapConverter mapConverter) {
        this.pagedUtil = pagedUtil;
        this.mapConverter = mapConverter;
    }

    public <T> int add(BaseInsertMapper<T> dao , T entity, boolean ignoreNullField) {
        int affected;
        if (ignoreNullField) {
            affected = dao.insertSelective(entity);
        }
        else {
            affected = dao.insert(entity);
        }
        return affected;
    }

    public <T> int update(BaseUpdateMapper<T> dao , T entity, boolean ignoreNullField) {
        int affected;
        if(ignoreNullField) {
            affected = dao.updateByPrimaryKeySelective(entity);
        }
        else {
            affected = dao.updateByPrimaryKey(entity);
        }
        return affected;
    }

    public <T> int deleteByKey(BaseDeleteMapper<T> dao , Object key) {
        return dao.deleteByPrimaryKey(key);
    }

    public <T> T getByKey(BaseSelectMapper<T> dao , Object key) {
        return dao.selectByPrimaryKey(key);
    }

    public <T> T getOne(BaseSelectMapper<T> dao, T search) {
        return dao.selectOne(search);
    }

    public <T> List<T> getList(BaseSelectMapper<T> dao, T search) {
        return dao.select(search);
    }

    public <T> List<T> getList(SelectByExampleMapper<T> dao, List<PropertyCondition> searchConditions) {
        Example search = new Example(getEntityClass(dao));
        initExampleCriteria(search, searchConditions);
        return dao.selectByExample(search);
    }

    public <T> List<T> getList(Class<T> clazz, SelectByExampleMapper<T> dao, List<PropertyCondition> searchConditions) {
        Example search = new Example(clazz);
        initExampleCriteria(search, searchConditions);
        return dao.selectByExample(search);
    }

    public <T> PagedList<T> getPagedList(RowBoundsMapper<T> dao, List<PropertyCondition> searchConditions, PagedBounds pagedBounds) {
        Example search = new Example(getEntityClass(dao));
        initExampleCriteria(search, searchConditions);
        initExampleOrderBy(search, pagedBounds, false);
        RowBounds rowBounds = pagedUtil.convertToRowBounds(pagedBounds);
        List<T> list = dao.selectByExampleAndRowBounds(search, rowBounds);
        return pagedUtil.convertToPagedList(list, pagedBounds);
    }

    public <T> PagedList<T> getPagedList(Class<T> clazz, RowBoundsMapper<T> dao, List<PropertyCondition> searchConditions, PagedBounds pagedBounds) {
        Example search = new Example(clazz);
        initExampleCriteria(search, searchConditions);
        initExampleOrderBy(search, pagedBounds, false);
        RowBounds rowBounds = pagedUtil.convertToRowBounds(pagedBounds);
        List<T> list = dao.selectByExampleAndRowBounds(search, rowBounds);
        return pagedUtil.convertToPagedList(list, pagedBounds);
    }

    protected void initExampleCriteria(Example example, List<PropertyCondition> searchConditions) {
        if (searchConditions == null || example == null) return;
        Example.Criteria criteria = example.createCriteria();
        for (PropertyCondition condition : searchConditions) {
            if (condition == null || StringUtils.isEmpty(condition.getLogicName()) || condition.getCompareOperator() == null || condition.getCompareValue() == null) {
                continue;
            }
            EntityColumn entityColumn = mapConverter.getEntityColumn(example.getEntityClass(), condition.getLogicName());
            switch (condition.getCompareOperator()) {
                case Equal:
                    criteria = criteria.andEqualTo(condition.getLogicName(), condition.getCompareValue());
                    break;
                case NotEqual:
                    criteria = criteria.andNotEqualTo(condition.getLogicName(), condition.getCompareValue());
                    break;
                case GreatThan:
                    criteria = criteria.andGreaterThan(condition.getLogicName(), condition.getCompareValue());
                    break;
                case GreatThanOrEqual:
                    criteria = criteria.andGreaterThanOrEqualTo(condition.getLogicName(), condition.getCompareValue());
                    break;
                case LessThan:
                    criteria = criteria.andLessThan(condition.getLogicName(), condition.getCompareValue());
                    break;
                case LessThanOrEqual:
                    criteria = criteria.andLessThanOrEqualTo(condition.getLogicName(), condition.getCompareValue());
                    break;
                case LikeAll:
                    criteria = criteria.andLike(condition.getLogicName(), formatLikeString(condition.getCompareValue(), "%", "%"));
                    break;
                case LikeLeft:
                    criteria = criteria.andLike(condition.getLogicName(), formatLikeString(condition.getCompareValue(), "%", ""));
                    break;
                case LikeRight:
                    criteria = criteria.andLike(condition.getLogicName(), formatLikeString(condition.getCompareValue(), "", "%"));
                    break;
                case IsNull:
                    criteria = criteria.andIsNull(condition.getLogicName());
                    break;
                case IsNotNull:
                    criteria = criteria.andIsNotNull(condition.getLogicName());
                    break;
                case In:
                    if (condition.getCompareValue() != null && condition.getCompareValue().getClass().isAssignableFrom(Iterable.class)) {
                        criteria = criteria.andIn(condition.getLogicName(), (Iterable) condition.getCompareValue());
                    }
                    break;
                case NotIn:
                    if (condition.getCompareValue() != null && condition.getCompareValue().getClass().isAssignableFrom(Iterable.class)) {
                        criteria = criteria.andNotIn(condition.getLogicName(), (Iterable) condition.getCompareValue());
                    }
                    break;
            }
        }
    }

    protected String formatLikeString(Object value, String prefix, String suffix) {
        return prefix + value.toString() + suffix;
    }

    protected void initExampleOrderBy(Example example, PagedBounds pagedBounds, boolean logicName2ColumnName) {
        if(pagedBounds == null || example == null) return;
        Example.OrderBy orderBy = null;
        boolean isFirst = true;
        for(SortCondition sortField : pagedBounds.getSortBy()) {
            if(sortField == null || StringUtils.isEmpty(sortField.getSortLogicName())) {
                continue;
            }
            String columnName = sortField.getSortLogicName();
            if (logicName2ColumnName) {
                columnName = mapConverter.getEntityColumnName(example.getEntityClass(), sortField.getSortLogicName());
            }
            if(isFirst) {
                orderBy = example.orderBy(columnName);
                isFirst = false;
            }
            else {
                orderBy = orderBy.orderBy(columnName);
            }
            if(sortField.getSortDirection()== SortDirection.DESC) {
                orderBy = orderBy.desc();
            }
            else {
                orderBy = orderBy.asc();
            }
        }
    }

    private Class<?> getEntityClass(Object mapper) {
        return (Class<?>) ((ParameterizedType) mapper.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
