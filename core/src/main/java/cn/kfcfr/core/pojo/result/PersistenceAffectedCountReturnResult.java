package cn.kfcfr.core.pojo.result;

import cn.kfcfr.core.pojo.PersistenceAffectedCount;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PersistenceAffectedCountReturnResult implements IReturnResult<PersistenceAffectedCount> {
    private static final long serialVersionUID = -1540560467932717988L;
    private PersistenceAffectedCount data;

    public PersistenceAffectedCountReturnResult(PersistenceAffectedCount data) {
        this.data = data;
    }

    @Override
    public PersistenceAffectedCount getData() {
        return data;
    }

    @Override
    public void setData(PersistenceAffectedCount data) {
        this.data = data;
    }
}
