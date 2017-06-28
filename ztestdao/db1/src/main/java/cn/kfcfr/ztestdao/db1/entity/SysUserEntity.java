package cn.kfcfr.ztestdao.db1.entity;

import cn.kfcfr.ztestdao.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangqf on 2017/6/19.
 */
@Table(name = "sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8977930015738881630L;
    @Id
    //@GeneratedValue(generator = "JDBC")
    private Long userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String lang;
    private Date regDate;
    @Column(name = "op_id")
    private Long opId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Long getOpId() {
        return opId;
    }

    public void setOpId(Long opId) {
        this.opId = opId;
    }
}
