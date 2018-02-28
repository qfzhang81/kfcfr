package cn.kfcfr.ztestcommon.serviceimpl;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.mybatis.datasource.rw.annotation.DataSourceReader;
import cn.kfcfr.ztestcommon.repository.db1.ISysUserRepository;
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
//    private ISysUserDao sysUserDao;
//    private ISyncUserDao syncUserDao;
//
//    @Qualifier("mapperUtil")
//    protected MapperUtil mapperUtil;
//    private final SysUserConverter converter = new SysUserConverter();

    private ISysUserRepository sysUserRepository;

//    @Autowired
//    public UserServiceImpl(ISysUserDao sysUserDao) {
//        this.sysUserDao = sysUserDao;
//    }

//    @Autowired
//    public void setSysUserDao(ISysUserDao sysUserDao) {
//        this.sysUserDao = sysUserDao;
//    }
//
//    @Autowired
//    public void setSyncUserDao(ISyncUserDao syncUserDao) {
//        this.syncUserDao = syncUserDao;
//    }
//
//    @Autowired
//    public void setMapperUtil(MapperUtil mapperUtil) {
//        this.mapperUtil = mapperUtil;
//    }

    @Autowired
    public void setSysUserRepository(ISysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    @Transactional
    public int add(SysUser info, boolean ignoreNullField) throws Exception {
        return sysUserRepository.addWithConvert(info, ignoreNullField);
    }

    @Override
    @Transactional
    public int update(SysUser info, boolean ignoreNullField) throws Exception {
        return sysUserRepository.updateWithConvert(info, ignoreNullField);
    }

    @Override
    @Transactional
    public int deleteByKey(Long key) throws Exception {
        return sysUserRepository.deleteByKey(key);
    }

    @DataSourceReader
    @Override
    public SysUser getById(Long id) throws Exception {
        //SysUserEntity entity = mapperUtil.getByKey(sysUserDao, id);
//        SysUserEntity entity = sysUserRepository.getByKey(id);
//        SyncUserEntity entity2 = mapperUtil.getByKey(syncUserDao, 100 + id);
        return sysUserRepository.getWithConvertByKey(id);
    }

    @Override
    public SysUser getByAccount(String account) throws Exception {
//        SysUserEntity searchEntity = new SysUserEntity();
//        searchEntity.setUserAccount(account);
//        SysUserEntity entity = mapperUtil.getOne(sysUserDao, searchEntity);
//        return converter.convertT2ToT1(entity);
        return null;
    }

    @Override
    public PagedList<SysUser> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) throws Exception {
        return sysUserRepository.getWithConvertBySearch(pagedBounds, searchConditions);
    }
}
