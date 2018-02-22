package cn.kfcfr.core.transfer;

import lombok.*;

import java.io.Serializable;

/**
 * 新建、修改时的请求对象封装
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class SaveRequestTransfer<T> implements Serializable {
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

    public SaveRequestTransfer() {
    }

    public SaveRequestTransfer(T entity, boolean ignoreNullField) {
        this.entity = entity;
        this.ignoreNullField = ignoreNullField;
    }
}
