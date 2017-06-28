//package cn.kfcfr.persistence.common.pagination;
//
//import cn.kfcfr.persistence.common.SortField;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * 分页结果
// * Created by zhangqf on 2017/6/9.
// */
//public class PagedList<T> implements Serializable {
//    private static final long serialVersionUID = 3089263687064293171L;
//    private Integer pageNum;
//    private Integer pageSize;
//    private List<SortField> sortBy;
//    private Integer size;
//    private Long total;
//    private List<T> list;
//    private int pages;
//    private int startRow;
//    private int endRow;
//    private int prePage;
//    private int nextPage;
//    private boolean isFirstPage;
//    private boolean isLastPage;
//    private boolean hasPreviousPage;
//    private boolean hasNextPage;
//
//    public Integer getPageNum() {
//        return pageNum;
//    }
//
//    public Integer getPageSize() {
//        return pageSize;
//    }
//
//    public List<SortField> getSortBy() {
//        return sortBy;
//    }
//
//    public Integer getSize() {
//        return size;
//    }
//
//    public Long getTotal() {
//        return total;
//    }
//
//    public List<T> getList() {
//        return list;
//    }
//
//    public int getPages() {
//        return pages;
//    }
//
//    public int getStartRow() {
//        return startRow;
//    }
//
//    public int getEndRow() {
//        return endRow;
//    }
//
//    public int getPrePage() {
//        return prePage;
//    }
//
//    public int getNextPage() {
//        return nextPage;
//    }
//
//    public boolean getIsFirstPage() {
//        return isFirstPage;
//    }
//
//    public boolean getIsLastPage() {
//        return isLastPage;
//    }
//
//    public boolean getHasPreviousPage() {
//        return hasPreviousPage;
//    }
//
//    public boolean getHasNextPage() {
//        return hasNextPage;
//    }
//
//    public PagedList(List<T> list) {
//        this(list, list.size(), new PagedBounds(1,Integer.MAX_VALUE));
//    }
//
//    public PagedList(List<T> list, long total, PagedBounds pagedBounds) {
//        this.list = list;
//        this.size = list.size();
//        this.total = total;
//        this.pageNum = pagedBounds.getPageNum();
//        this.pageSize = pagedBounds.getPageSize();
//        this.sortBy = pagedBounds.getSortBy();
//        this.calcPage();
//        this.judgePageBoudary();
//    }
//
//    private void calcPage() {
//        if(this.pageSize > 0) {
//            this.pages = (int)(total / (long)this.pageSize + (long)(total % (long)this.pageSize == 0L?0:1));
//        } else {
//            this.pages = 0;
//        }
//        if(this.pageNum > 1) {
//            this.prePage = this.pageNum - 1;
//        }
//        if(this.pageNum < this.pages) {
//            this.nextPage = this.pageNum + 1;
//        }
//        if(this.size == 0) {
//            this.startRow = 0;
//            this.endRow = 0;
//        } else {
//            this.startRow = ((this.pageNum < 1 ? 1 : this.pageNum) - 1) * this.pageSize + 1;
//            this.endRow = this.startRow - 1 + this.size;
//        }
//    }
//
//    private void judgePageBoudary() {
//        this.isFirstPage = false;
//        this.isLastPage = false;
//        this.hasPreviousPage = false;
//        this.hasNextPage = false;
//        this.isFirstPage = this.pageNum == 1;
//        this.isLastPage = this.pageNum == this.pages;
//        this.hasPreviousPage = this.pageNum > 1;
//        this.hasNextPage = this.pageNum < this.pages;
//    }
//}
