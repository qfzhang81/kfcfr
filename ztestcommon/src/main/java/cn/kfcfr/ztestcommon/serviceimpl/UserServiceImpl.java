package cn.kfcfr.ztestcommon.serviceimpl;

import cn.kfcfr.core.convert.BeanCopy;
import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PersistenceAffectedCount;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.mybatis.datasource.rw.annotation.DataSourceReader;
import cn.kfcfr.persistence.mybatis.datasource.rw.annotation.DataSourceWriter;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import cn.kfcfr.ztestcommon.repository.db1.ISysUserRepository;
import cn.kfcfr.ztestcommon.repository.db2.ISyncUserRepository;
import cn.kfcfr.ztestcommon.service.IUserService;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
    private ISysUserRepository sysUserRepository;
    private ISyncUserRepository syncUserRepository;

    @Autowired
    public void setSysUserRepository(ISysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Autowired
    public void setSyncUserRepository(ISyncUserRepository syncUserRepository) {
        this.syncUserRepository = syncUserRepository;
    }

    @DataSourceWriter
    @Transactional
    @Override
    public PersistenceAffectedCount add(SysUser info, boolean ignoreNullField) throws Exception {
        int affected = sysUserRepository.addWithConvert(info, ignoreNullField);
        SysUser syncUser = BeanCopy.deepClone(info);
        syncUser.setUserId(100 + syncUser.getUserId());
        syncUser.setUserName(syncUser.getUserName() + "-sync");
        syncUserRepository.addWithConvert(syncUser, ignoreNullField);
        return new PersistenceAffectedCount(affected, 0, 0, 0, 0);
    }

    @DataSourceWriter
    @Transactional
    @Override
    public PersistenceAffectedCount update(SysUser info, boolean ignoreNullField) throws Exception {
        int affected = sysUserRepository.updateWithConvert(info, ignoreNullField);
        SysUser syncUser = BeanCopy.deepClone(info);
        syncUser.setUserId(100 + syncUser.getUserId());
        syncUser.setUserName(syncUser.getUserName() + "-sync");
        syncUserRepository.updateWithConvert(syncUser, ignoreNullField);
        return new PersistenceAffectedCount(0, affected, 0, 0, 0);
    }

    @DataSourceWriter
    @Transactional
    @Override
    public PersistenceAffectedCount deleteByKey(Long key) throws Exception {
        int affected = sysUserRepository.deleteByKey(key);
        syncUserRepository.deleteByKey(100 + key);
        return new PersistenceAffectedCount(0, 0, affected, 0, 0);
    }

    @DataSourceReader
    @Override
    public SysUser selectById(Long id) throws Exception {
        SysUser syncUser = syncUserRepository.selectWithConvertByKey(100 + id);
        System.out.println(JsonConvert.toJsonString(syncUser));
        SyncUserEntity syncUserEntity = syncUserRepository.selectByKey(100 + id);
        System.out.println(JsonConvert.toJsonString(syncUserEntity));
        return sysUserRepository.selectWithConvertByKey(id);
    }

    @DataSourceReader
    @Override
    public SysUser selectByAccount(String account) throws Exception {
        SysUser syncUser = syncUserRepository.selectByAccount(account);
        System.out.println(JsonConvert.toJsonString(syncUser));
        return sysUserRepository.selectByAccount(account);
    }

    @DataSourceReader
    @Override
    public PagedList<SysUser> selectBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws Exception {
        PagedList<SysUser> list = syncUserRepository.selectPagedListWithConvert(pagedBounds, searchConditions);
        System.out.println(JsonConvert.toJsonString(list));
        return sysUserRepository.selectPagedListWithConvert(pagedBounds, searchConditions);
    }
}
