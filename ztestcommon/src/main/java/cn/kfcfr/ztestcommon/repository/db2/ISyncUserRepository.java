package cn.kfcfr.ztestcommon.repository.db2;

import cn.kfcfr.persistence.common.repository.ICrudWithConvertRepository;
import cn.kfcfr.persistence.common.repository.ISelectWithConvertRepository;
import cn.kfcfr.ztestcommon.entity.SyncUserEntity;
import cn.kfcfr.ztestmodel.db1.SysUser;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ISyncUserRepository extends ICrudWithConvertRepository<SysUser, SyncUserEntity>, ISelectWithConvertRepository<SysUser, SyncUserEntity> {
    SysUser selectByAccount(String account) throws ReflectiveOperationException;
}
