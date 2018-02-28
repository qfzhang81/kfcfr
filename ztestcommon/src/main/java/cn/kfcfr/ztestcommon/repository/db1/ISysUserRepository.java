package cn.kfcfr.ztestcommon.repository.db1;

import cn.kfcfr.persistence.common.repository.ICrudRepository;
import cn.kfcfr.persistence.common.repository.ISelectRepository;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ISysUserRepository extends ICrudRepository<SysUserEntity>, ISelectRepository<SysUserEntity> {
}
