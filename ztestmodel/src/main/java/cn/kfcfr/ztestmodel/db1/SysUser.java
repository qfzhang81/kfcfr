package cn.kfcfr.ztestmodel.db1;

import cn.kfcfr.ztestmodel.BaseModel;

import java.util.Date;

/**
 * Created by zhangqf on 2017/6/19.
 */
public class SysUser extends BaseModel {
    private static final long serialVersionUID = 5563200715669651682L;
    private Long userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String lang;
    private Date regDate;
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
