//package cn.kfcfr.persistence.common;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.Serializable;
//
///**
// * Created by zhangqf on 2017/6/9.
// */
//public class SortField implements Serializable {
//    private static final long serialVersionUID = -4792316740769869204L;
//    private String sortName;
//    private SortDirection sortDirection;
//
//    public SortField() {
//        this.sortDirection = SortDirection.ASC;
//    }
//
//    public SortField(String sortName, SortDirection sortDirection) {
//        this.sortName = sortName;
//        this.sortDirection = sortDirection;
//    }
//
//    public String getSortName() {
//        return sortName;
//    }
//
//    public void setSortName(String sortName) {
//        this.sortName = sortName;
//    }
//
//    public SortDirection getSortDirection() {
//        return sortDirection;
//    }
//
//    public void setSortDirection(SortDirection sortDirection) {
//        this.sortDirection = sortDirection;
//    }
//
//    @Override
//    public String toString()
//    {
//        if(StringUtils.isEmpty(sortName)) return "";
//        return sortName+" "+(sortDirection==SortDirection.DESC ? "desc" : "asc");
//    }
//}
