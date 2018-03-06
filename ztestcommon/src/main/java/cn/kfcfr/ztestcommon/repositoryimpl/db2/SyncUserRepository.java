package cn.kfcfr.ztestcommon.repositoryimpl.db2;

import cn.kfcfr.core.constant.Operator;
import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.mybatis.mapper.TkCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.TkReadonlyMapper;
import cn.kfcfr.ztestcommon.converter.SyncUserConverter;
import cn.kfcfr.ztestcommon.dao.db2.ISyncUserDao;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import cn.kfcfr.ztestcommon.repository.db2.ISyncUserRepository;
import cn.kfcfr.ztestcommon.repositoryimpl.AbstractMybatisRepositoryImpl;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    protected TkCrudMapper<SyncUserEntity> getCrudMapper() {
        return syncUserDao;
    }

    @Override
    protected TkReadonlyMapper<SyncUserEntity> getReadonlyMapper() {
        return syncUserDao;
    }

    @Override
    protected BeanConverter<SysUser, SyncUserEntity> getConverter() {
        return converter;
    }

    @Override
    public SysUser selectByAccount(String account) throws ReflectiveOperationException {
        List<PropertyCondition> searchConditions = new ArrayList<>();
        PropertyCondition p = new PropertyCondition();
        p.setLogicName("userAccount");
        p.setCompareOperator(Operator.Equal);
        p.setCompareValue(account);
        searchConditions.add(p);
        SyncUserEntity entity = getMapperUtil().selectOne(getReadonlyMapper(), searchConditions);
        return getConverter().convertT2ToT1(entity);
    }
}
