package cn.kfcfr.core.pojo;

import java.io.Serializable;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class BusinessMessage implements Serializable {
    private static final long serialVersionUID = 377440347127298021L;
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
