package cn.kfcfr.ztestbusiness.biz1.serviceImpl.accmgr;

import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.ztestbusiness.biz1.service.accmgr.IUserService;
import cn.kfcfr.ztestbusiness.biz1.serviceImpl.BaseServiceImpl;
import cn.kfcfr.ztestdao.db1.converter.SysUserConverter;
import cn.kfcfr.ztestdao.db1.dao.ISysUserDao;
import cn.kfcfr.ztestdao.db1.entity.SysUserEntity;
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
    private final ISysUserDao sysUserDao;

    private final MapperUtil mapperUtil = new MapperUtil();
    private final SysUserConverter converter = new SysUserConverter();

    @Autowired
    public UserServiceImpl(ISysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
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

    @Override
    public SysUser getById(Long id) throws Exception {
        SysUserEntity entity = mapperUtil.getByKey(sysUserDao, id);
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
