package cn.kfcfr.ztestcommon.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangqf on 2017/6/27.
 */
public abstract class BaseServiceImpl {
    private Logger logger;
//    @Resource
//    protected JtaTransactionManager springTransactionManager;

    protected Logger getLogger(){
        return logger;
    }

    public BaseServiceImpl() {
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
