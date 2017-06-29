package cn.kfcfr.core.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * Created by zhangqf on 2017/6/27.
 */
public class PagedList<T> implements Serializable {
    private static final long serialVersionUID = 3089263687064293171L;
    private PagedBounds pagedBounds;
    private Integer size;
    private Long total;
    private List<T> list;
    private int pages;
    private int startRow;
    private int endRow;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public PagedBounds getPagedBounds() {
        return pagedBounds;
    }

    public Integer getSize() {
        return size;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public int getPages() {
        return pages;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getPrePage() {
        return prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public boolean getIsFirstPage() {
        return isFirstPage;
    }

    public boolean getIsLastPage() {
        return isLastPage;
    }

    public boolean getHasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean getHasNextPage() {
        return hasNextPage;
    }

    public PagedList(List<T> list) {
        this(list, list.size(), new PagedBounds(1, Integer.MAX_VALUE));
    }

    public PagedList(List<T> list, long total, PagedBounds pagedBounds) {
        this.list = list;
        this.size = list.size();
        this.total = total;
        this.pagedBounds = pagedBounds;
        this.calcPage();
        this.judgePageBoundary();
    }

    private void calcPage() {
        if (this.pagedBounds.getPageSize() > 0) {
            this.pages = (int) (total / (long) this.pagedBounds.getPageSize() + (long) (total % (long) this.pagedBounds.getPageSize() == 0L ? 0 : 1));
        }
        else {
            this.pages = 0;
        }
        if (this.pagedBounds.getPageNum() > 1) {
            this.prePage = this.pagedBounds.getPageNum() - 1;
        }
        if (this.pagedBounds.getPageNum() < this.pages) {
            this.nextPage = this.pagedBounds.getPageNum() + 1;
        }
        if (this.size == 0) {
            this.startRow = 0;
            this.endRow = 0;
        }
        else {
            this.startRow = ((this.pagedBounds.getPageNum() < 1 ? 1 : this.pagedBounds.getPageNum()) - 1) * this.pagedBounds.getPageSize() + 1;
            this.endRow = this.startRow - 1 + this.size;
        }
    }

    private void judgePageBoundary() {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        this.isFirstPage = this.pagedBounds.getPageSize() == 1;
        this.isLastPage = this.pagedBounds.getPageSize() == this.pages;
        this.hasPreviousPage = this.pagedBounds.getPageSize() > 1;
        this.hasNextPage = this.pagedBounds.getPageSize() < this.pages;
    }
}

