package cn.kfcfr.ztestcommon.repositoryimpl.db2;

import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.ztestcommon.converter.SyncUserConverter;
import cn.kfcfr.ztestcommon.dao.db2.ISyncUserDao;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import cn.kfcfr.ztestcommon.repository.db2.ISyncUserRepository;
import cn.kfcfr.ztestcommon.repositoryimpl.AbstractMybatisRepositoryImpl;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
@Component
public class SyncUserRepository extends AbstractMybatisRepositoryImpl<SysUser, SyncUserEntity> implements ISyncUserRepository {
    protected final SyncUserConverter converter = new SyncUserConverter();
    protected ISyncUserDao syncUserDao;

    @Autowired
    public void setSyncUserDao(ISyncUserDao syncUserDao) {
        this.syncUserDao = syncUserDao;
    }

    @Override
    protected CommonCrudMapper getCrudMapper() {
        return syncUserDao;
    }

    @Override
    protected CommonReadonlyMapper getReadonlyMapper() {
        return syncUserDao;
    }

    @Override
    protected BeanConverter getConverter() {
        return converter;
    }
}
