package cn.kfcfr.persistence.mybatis.datasource.rw;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.PriorityOrdered;

/**
 * Created by zhangqf77 on 2018/3/1.
 */
public abstract class AbstractRwDataSourceAop implements PriorityOrdered {
//    protected RwDataSourceContextHolder contextHolder;

//    protected abstract RwDataSourceContextHolder getContextHolder();

    protected String getContextHolderType() {
        return RwDataSourceContextHolder.get();
    }

    protected void setContextHolderType(String type) {
        RwDataSourceContextHolder.set(type);
    }

    /***
     * 值越小，越优先执行
     * 要优于事务的执行
     */
    @Override
    public int getOrder() {
        return 10;
    }

    public boolean changeDataSourceType(String type) {
        String currentType = getContextHolderType();
        if (!StringUtils.isEmpty(currentType) && currentType.equals(type)) {
            //无需切换
            return false;
        }
        setContextHolderType(type);
//        for (RwDataSourceType e : RwDataSourceType.values()) {
//            if (e.getType().equals(type)) {
//                if (e.isWritable()) {
//                    if (StringUtils.isBlank(sourceName)) {
//                        setContextHolderType(e.getType());
//                    }
//                    else {
//                        getContextHolder().setWriter(sourceName);
//                    }
//                }
//                else {
//                    if (StringUtils.isBlank(sourceName)) {
//                        getContextHolder().setReader();
//                    }
//                    else {
//                        getContextHolder().setReader(sourceName);
//                    }
//                }
//            }
//        }
        return true;
    }
}
