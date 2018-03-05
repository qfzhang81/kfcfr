package cn.kfcfr.ztestcommon.service;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PersistenceAffectedCount;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.ztestmodel.db1.SysUser;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/19.
 */
public interface IUserService {
    PersistenceAffectedCount add(SysUser info, boolean ignoreNullField) throws Exception;
    PersistenceAffectedCount update(SysUser info, boolean ignoreNullField) throws Exception;
    PersistenceAffectedCount deleteByKey(Long key) throws Exception;
    SysUser selectById(Long id) throws Exception;
    SysUser selectByAccount(String account) throws Exception;
    PagedList<SysUser> selectBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws Exception;
}
