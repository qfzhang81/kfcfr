package cn.kfcfr.core.expression;

import java.io.Serializable;

/**
 * Created by zhangqf on 2017/6/27.
 */
public class PropertyCondition implements Serializable {
    private static final long serialVersionUID = 3351967808002455680L;
    private String logicName;
    private Operator compareOperator;
    private Object compareValue;

    public String getLogicName() {
        return logicName;
    }

    public void setLogicName(String logicName) {
        this.logicName = logicName;
    }

    public Operator getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(Operator compareOperator) {
        this.compareOperator = compareOperator;
    }

    public Object getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Object compareValue) {
        this.compareValue = compareValue;
    }
}
