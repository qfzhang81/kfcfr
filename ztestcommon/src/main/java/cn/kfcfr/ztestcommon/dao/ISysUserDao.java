package cn.kfcfr.ztestcommon.dao;

import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Mapper
public interface ISysUserDao extends CommonCrudMapper<SysUserEntity>, CommonReadonlyMapper<SysUserEntity> {

}
