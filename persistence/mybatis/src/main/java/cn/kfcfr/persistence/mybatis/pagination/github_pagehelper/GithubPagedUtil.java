package cn.kfcfr.persistence.mybatis.pagination.github_pagehelper;

import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/9.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GithubPagedUtil implements IPagedUtil {
    private boolean offsetAsPageNum = false;

    public void setOffsetAsPageNum(boolean offsetAsPageNum) {
        this.offsetAsPageNum = offsetAsPageNum;
    }

    public GithubPagedUtil() {
        offsetAsPageNum = true;
    }

    public GithubPagedUtil(boolean offsetAsPageNum) {
        this.offsetAsPageNum = offsetAsPageNum;
    }

    @Override
    public RowBounds convertToRowBounds(PagedBounds pagedBounds) {
        if (pagedBounds == null) return new RowBounds();
        //offsetAsPageNum=true的转换
        if(offsetAsPageNum) {
            return new RowBounds(pagedBounds.getPageNum(), pagedBounds.getPageSize());
        }
        int offset = (pagedBounds.getPageNum() -1) * pagedBounds.getPageSize();
        return new RowBounds(offset, pagedBounds.getPageSize());
    }

    @Override
    public <T> PagedList<T> convertToPagedList(List<T> list, PagedBounds pagedBounds) {
        PagedList<T> pagedList = null;
        if (list != null && list instanceof Page) {
            Page page = (Page) list;
            pagedList = new PagedList<>(list, page.getTotal(), pagedBounds);
        } else if (list != null) {
            pagedList = this.convertToPagedList(list, pagedBounds, list.size());
        }
        return pagedList;
    }

    @Override
    public <T> PagedList<T> convertToPagedList(List<T> list, PagedBounds pagedBounds, long total) {
        return new PagedList<>(list, total, pagedBounds);
    }
}
