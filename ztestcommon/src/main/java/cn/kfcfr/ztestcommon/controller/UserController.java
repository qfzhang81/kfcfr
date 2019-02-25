package cn.kfcfr.ztestcommon.controller;

import cn.kfcfr.core.math.DateCalc;
import cn.kfcfr.core.pagination.PagedBounds;
import cn.kfcfr.core.pagination.PagedList;
import cn.kfcfr.core.pagination.SortCondition;
import cn.kfcfr.core.pagination.SortDirection;
import cn.kfcfr.core.pojo.PersistenceAffectedCount;
import cn.kfcfr.core.pojo.PropertyCondition;
import cn.kfcfr.core.pojo.request.SaveSingleRequestTransfer;
import cn.kfcfr.core.pojo.request.SearchRequestTransfer;
import cn.kfcfr.core.pojo.response.*;
import cn.kfcfr.ztestcommon.service.IUserService;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/24.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@RequestMapping(value = "${ms.api.root}/user")
@RestController
public class UserController {
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.GET)
    public ResponseEntity<IResponseTransfer> getByKey(@PathVariable("id") Long id) {
        HttpStatus httpStatus;
        IResponseTransfer res;
        try {
            SysUser rst = userService.selectById(id);
            res = new SingleResponseTransfer<>(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(res, httpStatus);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public ResponseEntity<IResponseTransfer> add(@RequestBody SaveSingleRequestTransfer<SysUser> transfer) {
        HttpStatus httpStatus;
        IResponseTransfer res;
        try {
            PersistenceAffectedCount rst = userService.add(transfer.getEntity(), transfer.getIgnoreNullField());
            res = new PersistenceAffectedCountResponseTransfer(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(res, httpStatus);
    }

    @RequestMapping(value = "/entity", method = RequestMethod.PUT)
    public ResponseEntity<IResponseTransfer> update(@RequestBody SaveSingleRequestTransfer<SysUser> transfer) {
        HttpStatus httpStatus;
        IResponseTransfer res;
        try {
            PersistenceAffectedCount rst = userService.update(transfer.getEntity(), transfer.getIgnoreNullField());
            res = new PersistenceAffectedCountResponseTransfer(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(res, httpStatus);
    }

    @RequestMapping(value = "/entity/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<IResponseTransfer> deleteByKey(@PathVariable("id") Long id) {
        HttpStatus httpStatus;
        IResponseTransfer res;
        try {
            PersistenceAffectedCount rst = userService.deleteByKey(id);
            res = new PersistenceAffectedCountResponseTransfer(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(res, httpStatus);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<IResponseTransfer> find(@RequestBody(required = false) SearchRequestTransfer transfer) {
        HttpStatus httpStatus;
        List<PropertyCondition> searchConditions = null;
        PagedBounds pagedBounds = null;

        String aa = "201801";
        String bb = DateCalc.calcPreviousMonth(aa,"yyyyMM");
        String cc = bb.toString();


        if (transfer != null) {
            searchConditions = transfer.getSearchConditions();
            pagedBounds = transfer.getPagedBounds();
        }
        if (pagedBounds == null) pagedBounds = new PagedBounds(1, 10);
        if (pagedBounds.getSortBy().size() == 0) pagedBounds.addSort(new SortCondition("userId", SortDirection.DESC));
        IResponseTransfer res;
        try {
            PagedList<SysUser> rst = userService.selectBySearch(pagedBounds, searchConditions);
            res = new PagedResponseTransfer<>(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(res, httpStatus);
    }

    @RequestMapping(value = "/account/{account}", method = RequestMethod.GET)
    public ResponseEntity<IResponseTransfer> getByKey(@PathVariable("account") String account) {
        HttpStatus httpStatus;
        IResponseTransfer res;
        try {
            SysUser rst = userService.selectByAccount(account);
            res = new SingleResponseTransfer<>(rst);
            httpStatus = HttpStatus.OK;
        }
        catch (Exception ex) {
            res = new ExceptionResponseTransfer<>(ex);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(res, httpStatus);
    }
}
