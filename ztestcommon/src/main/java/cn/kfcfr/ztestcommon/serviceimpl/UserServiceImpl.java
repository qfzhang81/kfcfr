package cn.kfcfr.ztestcommon.serviceimpl;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.mybatis.datasource.rw.annotation.DataSourceReader;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.ztestcommon.converter.SysUserConverter;
import cn.kfcfr.ztestcommon.dao.db1.ISysUserDao;
import cn.kfcfr.ztestcommon.dao.db2.ISyncUserDao;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import cn.kfcfr.ztestcommon.service.IUserService;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
    private ISysUserDao sysUserDao;
    private ISyncUserDao syncUserDao;

    @Qualifier("mapperUtil")
    protected MapperUtil mapperUtil;
    private final SysUserConverter converter = new SysUserConverter();

//    @Autowired
//    public UserServiceImpl(ISysUserDao sysUserDao) {
//        this.sysUserDao = sysUserDao;
//    }

    @Autowired
    public void setSysUserDao(ISysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Autowired
    public void setSyncUserDao(ISyncUserDao syncUserDao) {
        this.syncUserDao = syncUserDao;
    }

    @Autowired
    public void setMapperUtil(MapperUtil mapperUtil) {
        this.mapperUtil = mapperUtil;
    }

    @Override
    @Transactional
    public int add(SysUser info, boolean ignoreNullField) throws Exception {
        SysUserEntity entity = converter.convertT1ToT2(info);
        return mapperUtil.add(sysUserDao, entity, ignoreNullField);
    }

    @Override
    @Transactional
    public int update(SysUser info, boolean ignoreNullField) throws Exception {
        SysUserEntity entity = converter.convertT1ToT2(info);
        return mapperUtil.update(sysUserDao, entity, ignoreNullField);
    }

    @Override
    @Transactional
    public int deleteByKey(Long key) throws Exception {
        return mapperUtil.deleteByKey(sysUserDao, key);
    }

    @DataSourceReader
    @Override
    public SysUser getById(Long id) throws Exception {
        SysUserEntity entity = mapperUtil.getByKey(sysUserDao, id);
        SyncUserEntity entity2 = mapperUtil.getByKey(syncUserDao, 100 + id);
        return converter.convertT2ToT1(entity);
    }

    @Override
    public SysUser getByAccount(String account) throws Exception {
        SysUserEntity searchEntity = new SysUserEntity();
        searchEntity.setUserAccount(account);
        SysUserEntity entity = mapperUtil.getOne(sysUserDao, searchEntity);
        return converter.convertT2ToT1(entity);
    }

    @Override
    public PagedList<SysUser> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws Exception {
        PagedList<SysUserEntity> lst = mapperUtil.getPagedList(sysUserDao, searchConditions, pagedBounds);
        return converter.pagedT2ToT1(lst);
    }
}
