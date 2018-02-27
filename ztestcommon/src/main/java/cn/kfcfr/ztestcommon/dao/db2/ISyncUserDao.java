package cn.kfcfr.ztestcommon.dao.db2;

import cn.kfcfr.persistence.mybatis.mapper.CommonCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.CommonReadonlyMapper;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Mapper
public interface ISyncUserDao extends CommonCrudMapper<SyncUserEntity>, CommonReadonlyMapper<SyncUserEntity> {

}
