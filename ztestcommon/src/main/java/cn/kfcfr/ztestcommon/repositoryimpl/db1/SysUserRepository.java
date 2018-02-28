package cn.kfcfr.ztestcommon.repositoryimpl.db1;

import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.ztestcommon.converter.SysUserConverter;
import cn.kfcfr.ztestcommon.dao.db1.ISysUserDao;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import cn.kfcfr.ztestcommon.repository.db1.ISysUserRepository;
import cn.kfcfr.ztestcommon.repositoryimpl.AbstractCrudRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
@Component
public class SysUserRepository extends AbstractCrudRepositoryImpl<SysUserEntity> implements ISysUserRepository {
    protected final SysUserConverter converter = new SysUserConverter();
    protected ISysUserDao sysUserDao;

    @Autowired
    public void setSysUserDao(ISysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Override
    protected CommonCrudMapper<SysUserEntity> getCrudMapper() {
        return sysUserDao;
    }
}
