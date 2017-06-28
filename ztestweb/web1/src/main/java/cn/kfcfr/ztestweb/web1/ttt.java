package cn.kfcfr.ztestweb.web1;

import cn.kfcfr.core.bean.BeanCopy;
import cn.kfcfr.ztestdao.db1.converter.SysUserConverter;
import cn.kfcfr.ztestdao.db1.entity.SysUserEntity;
import cn.kfcfr.ztestmodel.db1.SysUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangqf on 2017/6/23.
 */
public class ttt {
    public static void main(String[] args){
//        SysUserConverter converter  = new SysUserConverter();
//        SysUser aa  = new SysUser();
//        aa.setOpId(1234567890L);
//        aa.setUserName("testaa");
//        aa.setLang("zzz");
//        aa.setRegDate(new Date());
//        System.out.println(aa);
//        SysUserEntity ee = null;
//        try {
//            ee = converter.convertT1ToT2(aa);
//        }
//        catch (ReflectiveOperationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(ee);
//        List<SysUser> srclist = new ArrayList<>();
//        SysUser aa  = new SysUser();
//        aa.setOpId(1234567890L);
//        aa.setUserName("testaa");
//        aa.setLang("zzz");
//        aa.setRegDate(new Date());
//        srclist.add(aa);
//        aa  = new SysUser();
//        aa.setOpId(2345678910L);
//        aa.setUserAccount("bb");
//        aa.setUserName("testbb");
//        aa.setRegDate(new Date());
//        srclist.add(aa);
//        aa  = new SysUser();
//        aa.setOpId(3456789120L);
//        aa.setUserAccount("cc");
//        aa.setUserName("testcc");
//        aa.setRegDate(new Date());
//        aa.setOpId(112L);
//        srclist.add(aa);
//        System.out.println(srclist);
//        List<SysUserEntity> tarlist = null;
//        try {
//            tarlist = converter.listT1ToT2(srclist);
//        }
//        catch (ReflectiveOperationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(tarlist);
        SysUser aa  = new SysUser();
        aa.setOpId(1234567890L);
        aa.setUserName("testaa");
        aa.setLang("zzz");
        aa.setRegDate(new Date());
        System.out.println(aa);
        try {
            SysUser bb = BeanCopy.deepClone(aa);
            System.out.println(bb);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
