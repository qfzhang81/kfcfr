package cn.kfcfr.core.pojo.request;

import java.io.Serializable;

/**
 * 保存单个请求对象封装
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class SaveSingleRequestTransfer<T> implements Serializable {
    private static final long serialVersionUID = 1591328821502222677L;
    private T entity;
    private Boolean ignoreNullField = true;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Boolean getIgnoreNullField() {
        return ignoreNullField;
    }

    public void setIgnoreNullField(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public SaveSingleRequestTransfer() {
    }

    public SaveSingleRequestTransfer(T entity, boolean ignoreNullField) {
        this.entity = entity;
        this.ignoreNullField = ignoreNullField;
    }
}
