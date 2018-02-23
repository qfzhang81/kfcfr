package cn.kfcfr.core.pojo.request;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pojo.PropertyCondition;

import java.io.Serializable;
import java.util.List;

/**
 * 查询时的请求对象封装
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class SearchRequestTransfer implements Serializable {
    private static final long serialVersionUID = 2716008011844893455L;
    private List<PropertyCondition> searchConditions;
    private PagedBounds pagedBounds;

    public List<PropertyCondition> getSearchConditions() {
        return searchConditions;
    }

    public void setSearchConditions(List<PropertyCondition> searchConditions) {
        this.searchConditions = searchConditions;
    }

    public PagedBounds getPagedBounds() {
        return pagedBounds;
    }

    public void setPagedBounds(PagedBounds pagedBounds) {
        this.pagedBounds = pagedBounds;
    }
}
