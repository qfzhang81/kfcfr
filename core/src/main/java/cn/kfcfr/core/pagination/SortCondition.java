package cn.kfcfr.core.pagination;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by zhangqf on 2017/6/27.
 */
public class SortCondition implements Serializable {
    private static final long serialVersionUID = 8571279905568628839L;
    private String sortLogicName;
    private SortDirection sortDirection;

    public SortCondition() {
        this.sortDirection = SortDirection.ASC;
    }

    public SortCondition(String sortLogicName, SortDirection sortDirection) {
        this.sortLogicName = sortLogicName;
        this.sortDirection = sortDirection;
    }

    public String getSortLogicName() {
        return sortLogicName;
    }

    public void setSortLogicName(String sortLogicName) {
        this.sortLogicName = sortLogicName;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(sortLogicName)) return "";
        return sortLogicName + " " + (sortDirection == SortDirection.DESC ? "desc" : "asc");
    }
}
