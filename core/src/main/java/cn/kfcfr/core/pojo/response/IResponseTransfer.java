package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.result.IReturnResult;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public interface IResponseTransfer {
    ResponseStatus getStatus();
    IReturnResult getResult();
}
