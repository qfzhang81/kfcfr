package cn.kfcfr.ztestcommon.repository.db1;

import cn.kfcfr.persistence.common.repository.ICrudWithConvertRepository;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;
import cn.kfcfr.ztestcommon.entity.SysUserEntity;
import cn.kfcfr.ztestmodel.db1.SysUser;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ISysUserRepository extends ICrudWithConvertRepository<SysUser, SysUserEntity>, ISelectWithConvertRepository<SysUser, SysUserEntity> {
}
