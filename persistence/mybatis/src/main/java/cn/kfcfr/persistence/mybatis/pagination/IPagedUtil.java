package cn.kfcfr.persistence.mybatis.pagination;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/9.
 */
public interface IPagedUtil {
    RowBounds convertToRowBounds(PagedBounds pageBounds);
    <T> PagedList<T> convertToPagedList(List<T> list, PagedBounds pagedBounds);
    <T> PagedList<T> convertToPagedList(List<T> list, PagedBounds pagedBounds, long total);
}
