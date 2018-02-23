package cn.kfcfr.core.pojo.result;

import java.io.Serializable;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public interface IReturnResult<T> extends Serializable {
    T getData();
    void setData(T data);
}
