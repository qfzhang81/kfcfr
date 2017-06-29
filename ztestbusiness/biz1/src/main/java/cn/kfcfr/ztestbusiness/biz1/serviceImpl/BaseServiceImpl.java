package cn.kfcfr.ztestbusiness.biz1.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;

/**
 * Created by zhangqf on 2017/6/27.
 */
public class BaseServiceImpl {
    private Logger logger;
    @Resource
    protected JtaTransactionManager springTransactionManager;

    protected Logger getLogger(){
        return logger;
    }

    public BaseServiceImpl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
