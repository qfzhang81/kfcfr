package cn.kfcfr.core.pojo.result;

import cn.kfcfr.core.pagination.PagedList;

/**
 * Created by zhangqf77 on 2018/2/23.
 */
public class PagedReturnResult<T> implements IReturnResult<PagedList<T>> {
    private static final long serialVersionUID = 7144455752294632020L;
    private PagedList<T> data;

    public PagedReturnResult(PagedList<T> data) {
        this.data = data;
    }

    @Override
    public PagedList<T> getData() {
        return data;
    }

    @Override
    public void setData(PagedList<T> data) {
        this.data = data;
    }
}
