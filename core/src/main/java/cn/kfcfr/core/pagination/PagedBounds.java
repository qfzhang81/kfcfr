package cn.kfcfr.core.pagination;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页条件
 * Created by zhangqf on 2017/6/27.
 */
public class PagedBounds implements Serializable {
    private static final long serialVersionUID = 7713933135926567211L;
    private int pageNum;
    private int pageSize;
    private List<SortCondition> sortBy;

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SortCondition> getSortBy() {
        return sortBy;
    }

    public PagedBounds() {
        this.pageNum = 1;
        this.pageSize = Integer.MAX_VALUE;
        sortBy = new ArrayList<>();
    }

    /**
     * 以指定的页号和分页大小进行初始化
     *
     * @param pageNum  需要的页号，从1开始
     * @param pageSize 分页大小
     */
    public PagedBounds(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        sortBy = new ArrayList<>();
    }

    public void clearSort() {
        sortBy.clear();
    }

    public void addSort(SortCondition sort) {
        if (sort == null || StringUtils.isEmpty(sort.getSortLogicName()))
            throw new IllegalArgumentException("sort cannot be empty");
        sortBy.add(sort);
    }
}
