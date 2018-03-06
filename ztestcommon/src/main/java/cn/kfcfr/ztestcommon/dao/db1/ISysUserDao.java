package cn.kfcfr.ztestcommon.dao.db1;

import cn.kfcfr.persistence.mybatis.mapper.TkCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.TkReadonlyMapper;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Mapper
public interface ISysUserDao extends TkCrudMapper<SysUserEntity>, TkReadonlyMapper<SysUserEntity> {

}
