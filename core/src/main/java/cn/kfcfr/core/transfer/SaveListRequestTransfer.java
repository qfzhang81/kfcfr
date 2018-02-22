package cn.kfcfr.core.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 新建、修改时的请求对象封装
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class SaveListRequestTransfer<T> implements Serializable {
    private static final long serialVersionUID = 1591328821502222677L;
    private List<T> list;
    private Boolean ignoreNullField = true;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Boolean getIgnoreNullField() {
        return ignoreNullField;
    }

    public void setIgnoreNullField(boolean ignoreNullField) {
        this.ignoreNullField = ignoreNullField;
    }

    public SaveListRequestTransfer() {
    }

    public SaveListRequestTransfer(List<T> list, boolean ignoreNullField) {
        this.list = list;
        this.ignoreNullField = ignoreNullField;
    }
}
