package cn.kfcfr.ztestcommon.repositoryimpl.db1;

import cn.kfcfr.core.constant.Operator;
import cn.kfcfr.core.convert.BeanConverter;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.ztestcommon.converter.SysUserConverter;
import cn.kfcfr.ztestcommon.dao.db1.ISysUserDao;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import cn.kfcfr.ztestcommon.repository.db1.ISysUserRepository;
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
public class SysUserRepository extends AbstractMybatisRepositoryImpl<SysUser, SysUserEntity> implements ISysUserRepository {
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

    @Override
    protected CommonReadonlyMapper<SysUserEntity> getReadonlyMapper() {
        return sysUserDao;
    }

    @Override
    protected BeanConverter<SysUser, SysUserEntity> getConverter() {
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
        SysUserEntity entity = getMapperUtil().selectOne(getReadonlyMapper(), searchConditions);
        return getConverter().convertT2ToT1(entity);
    }
}
