package mock;

import cn.kfcfr.core.pojo.PersistenceAffectedCount;
import cn.kfcfr.ztestcommon.repository.db1.ISysUserRepository;
import cn.kfcfr.ztestcommon.repository.db2.ISyncUserRepository;
import cn.kfcfr.ztestcommon.serviceimpl.UserServiceImpl;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ISysUserRepository sysUserRepository;
    @Mock
    private ISyncUserRepository syncUserRepository;

    @Test
    public void addOne() throws Exception {
        int affectRows = 100;
        Mockito.when(sysUserRepository.addWithConvert(Mockito.<SysUser>any(), Mockito.anyBoolean())).thenReturn(affectRows);
        Mockito.when(syncUserRepository.addWithConvert(Mockito.<SysUser>any(), Mockito.anyBoolean())).thenReturn(affectRows);
        SysUser info = new SysUser();
        info.setUserId(1L);
        PersistenceAffectedCount ret = userService.add(info, true);
        System.out.println(ret.getInsertedCount());
        Assert.assertThat("插入记录数不正确", ret.getInsertedCount(), equalTo(affectRows));
    }

}
