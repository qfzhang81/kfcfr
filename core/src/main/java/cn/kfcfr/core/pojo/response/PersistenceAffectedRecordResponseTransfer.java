package cn.kfcfr.core.pojo.response;

import cn.kfcfr.core.pojo.PersistenceAffectedRecord;
import cn.kfcfr.core.pojo.result.PersistenceAffectedRecordReturnResult;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PersistenceAffectedRecordResponseTransfer<T> extends AbstractResponseTransfer {
    private static final long serialVersionUID = -1028756437061968050L;
    private PersistenceAffectedRecordReturnResult<T> result;

    public PersistenceAffectedRecordResponseTransfer(PersistenceAffectedRecord<T> result) {
        super(ResponseStatus.Success);
        this.result = new PersistenceAffectedRecordReturnResult<>(result);
    }

    @Override
    public PersistenceAffectedRecordReturnResult<T> getResult() {
        return result;
    }

    public void setResult(PersistenceAffectedRecordReturnResult<T> result) {
        this.result = result;
    }
}
