package cn.kfcfr.core.pojo.result;

import cn.kfcfr.core.pojo.PersistenceAffectedRecord;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PersistenceAffectedRecordReturnResult<T> implements IReturnResult<PersistenceAffectedRecord<T>> {
    private static final long serialVersionUID = 3216868720512281380L;
    private PersistenceAffectedRecord<T> data;

    public PersistenceAffectedRecordReturnResult(PersistenceAffectedRecord<T> data) {
        this.data = data;
    }

    @Override
    public PersistenceAffectedRecord<T> getData() {
        return data;
    }

    @Override
    public void setData(PersistenceAffectedRecord<T> data) {
        this.data = data;
    }
}
