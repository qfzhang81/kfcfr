package cn.kfcfr.ztestcommon.controller;

import cn.kfcfr.core.pojo.response.ExceptionResponseTransfer;
import cn.kfcfr.core.pojo.response.IResponseTransfer;
import cn.kfcfr.core.pojo.response.SingleResponseTransfer;
import cn.kfcfr.ztestcommon.service.IUserService;
import cn.kfcfr.ztestmodel.db1.SysUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
            SysUser rst = userService.getById(id);
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
