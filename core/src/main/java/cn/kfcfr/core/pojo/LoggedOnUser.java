package cn.kfcfr.core.pojo;

import java.io.Serializable;
import java.util.Locale;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class LoggedOnUser implements Serializable {
    private static final long serialVersionUID = 4207356349699574981L;
    private Long userId;
    private String userName;
    private String nickName;
    private String emailSys;
    private Locale localeLang;
    private String skin;
    private String loginName;
    private String userIp;
    private Integer passwordEffectiveDays;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmailSys() {
        return emailSys;
    }

    public void setEmailSys(String emailSys) {
        this.emailSys = emailSys;
    }

    public Locale getLocaleLang() {
        return localeLang;
    }

    public void setLocaleLang(Locale localeLang) {
        this.localeLang = localeLang;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Integer getPasswordEffectiveDays() {
        return passwordEffectiveDays;
    }

    public void setPasswordEffectiveDays(Integer passwordEffectiveDays) {
        this.passwordEffectiveDays = passwordEffectiveDays;
    }
}
