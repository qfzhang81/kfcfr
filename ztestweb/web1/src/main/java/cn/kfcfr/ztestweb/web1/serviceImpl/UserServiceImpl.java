package cn.kfcfr.ztestweb.web1.serviceImpl;

import cn.kfcfr.core.expression.PropertyCondition;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.ztestdao.db1.converter.SysUserConverter;
import cn.kfcfr.ztestdao.db1.dao.ISysUserDao;
import cn.kfcfr.ztestdao.db1.entity.SysUserEntity;
import cn.kfcfr.ztestmodel.db1.SysUser;
import cn.kfcfr.ztestweb.web1.service.IUserService;
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
//        int affected;
//        if(ignoreNullField) {
//            affected = sysUserDao.insertSelective(entity);
//        }
//        else {
//            affected = sysUserDao.insert(entity);
//        }
//        return affected;
    }

    @Override
    @Transactional
    public int update(SysUser info, boolean ignoreNullField) throws Exception {
        SysUserEntity entity = converter.convertT1ToT2(info);
        return mapperUtil.update(sysUserDao, entity, ignoreNullField);
//        int affected;
//        if(ignoreNullField) {
//            affected = sysUserDao.updateByPrimaryKeySelective(entity);
//        }
//        else {
//            affected = sysUserDao.updateByPrimaryKey(entity);
//        }
//        return affected;
    }

    @Override
    @Transactional
    public int deleteByKey(Long key) throws Exception {
        return mapperUtil.deleteByKey(sysUserDao, key);
//        return sysUserDao.deleteByPrimaryKey(key);
    }

    @Override
    public SysUser getById(Long id) throws Exception {
        SysUserEntity entity = mapperUtil.getByKey(sysUserDao, id);
//        SysUserEntity entity = sysUserDao.selectByPrimaryKey(id);
        return converter.convertT2ToT1(entity);
    }

    @Override
    public SysUser getByAccount(String account) throws Exception {
        SysUserEntity searchEntity = new SysUserEntity();
        searchEntity.setUserAccount(account);
        SysUserEntity entity = mapperUtil.getOne(sysUserDao, searchEntity);
//        SysUserEntity entity = sysUserDao.selectOne(searchEntity);
        return converter.convertT2ToT1(entity);
    }

    @Override
    public PagedList<SysUser> getBySearch(PagedBounds pagedBounds, List<PropertyCondition> searchConditions) {
        //联合查询
//        String orderby = "order by user_account, user_id desc, lang desc";
//        String useraccountcolumn = EntityColumnMapHelper.getEntityColumnName(SysUserEntity.class, "userAccount");
//        List<UserModel> models = slaveUserDao.getByPagination(orderby, pagedBounds.getPageNum(), pagedBounds.getPageSize(), searchmap);
//        PagedList<UserModel> rstlst = pageHelperExtended.convertToPagedList(models, pagedBounds);
//        return rstlst;
//        String useraccountcolumn = EntityColumnMapHelper.getEntityColumnName(SysUserEntity.class, "userAccount");
        //PagedList<SysUser> rstlst = MapperHelper.getPagedList(sysUserDao, pagedBounds)
//        PagedList<SysUserEntity> lst = mapperUtil.getPagedList(sysUserDao, searchConditions, pagedBounds);
//        return converter.listT2ToT1(lst);
        return null;
    }
}
