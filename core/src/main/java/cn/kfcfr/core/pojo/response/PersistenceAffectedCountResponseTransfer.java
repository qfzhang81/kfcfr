package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.PersistenceAffectedCount;
import cn.kfcfr.core.pojo.result.PersistenceAffectedCountReturnResult;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PersistenceAffectedCountResponseTransfer extends AbstractResponseTransfer {
    private static final long serialVersionUID = -8784271420023259078L;
    private PersistenceAffectedCountReturnResult result;

    public PersistenceAffectedCountResponseTransfer(PersistenceAffectedCount result) {
        super(ResponseStatus.Success);
        this.result = new PersistenceAffectedCountReturnResult(result);
    }

    @Override
    public PersistenceAffectedCountReturnResult getResult() {
        return result;
    }

    public void setResult(PersistenceAffectedCountReturnResult result) {
        this.result = result;
    }
}
