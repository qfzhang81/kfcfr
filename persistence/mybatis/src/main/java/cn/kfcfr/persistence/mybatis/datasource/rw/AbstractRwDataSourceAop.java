package cn.kfcfr.persistence.mybatis.datasource.rw;

import cn.kfcfr.persistence.mybatis.datasource.DataSourceContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.PriorityOrdered;

/**
 * Created by zhangqf77 on 2018/3/1.
 */
public abstract class AbstractRwDataSourceAop implements PriorityOrdered {
    protected String getContextHolderType() {
        return DataSourceContextHolder.get();
    }

    protected void setContextHolderType(String type) {
        DataSourceContextHolder.set(type);
    }

    /***
     * 值越小，越优先执行
     * 要优于事务的执行
     */
    @Override
    public int getOrder() {
        return 10;
    }

    public boolean changeToReader() {
        return changeDataSourceType(RwDataSourceType.reader.isWritable());
    }

    public boolean changeToWriter() {
        return changeDataSourceType(RwDataSourceType.writer.isWritable());
    }

    public boolean changeDataSourceType(boolean isWritable) {
        String currentType = getContextHolderType();
        if (!StringUtils.isEmpty(currentType)) {
            boolean currentIsWritable = Boolean.parseBoolean(currentType);
            if(currentIsWritable==isWritable) {
                //无需切换
                return false;
            }
        }
        setContextHolderType(String.valueOf(isWritable));
        return true;
    }
}
