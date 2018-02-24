package cn.kfcfr.ztestcommon.service;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.ztestmodel.db1.SysUser;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/19.
 */
public interface IUserService {
    int add(SysUser info, boolean ignoreNullField) throws Exception;
    int update(SysUser info, boolean ignoreNullField) throws Exception;
    int deleteByKey(Long key) throws Exception;
    SysUser getById(Long id) throws Exception;
    SysUser getByAccount(String account) throws Exception;
    PagedList<SysUser> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws Exception;
}
