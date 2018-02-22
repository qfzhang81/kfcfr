package cn.kfcfr.core.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import wocentral.infra.core.pagination.PagedList;

/**
 * Restful API返回结果格式
 * status 1-成功 0-失败
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class PagedResponseTransfer<T> extends BaseResponseTransfer {
    private static final long serialVersionUID = 4387519364552504667L;
    private PagedList<T> result;

    public PagedList<T> getPagedList() {
        return result;
    }

    public void setPagedList(PagedList<T> result) {
        this.result = result;
    }

    public PagedResponseTransfer() {
        super();
        this.result = null;
    }

    public PagedResponseTransfer(PagedList<T> result) {
        super();
        this.result = result;
    }

    public PagedResponseTransfer(int status, PagedList<T> result) {
        super(status);
        this.result = result;
    }

    public PagedResponseTransfer(BusinessMessage message) {
        super(message);
        this.result = null;
    }

    public PagedResponseTransfer(Exception ex) {
        super(ex);
        this.result = null;
    }

    public PagedResponseTransfer(int status, BusinessMessage message, Exception ex) {
        super(status, message, ex);
        this.result = null;
    }
}
