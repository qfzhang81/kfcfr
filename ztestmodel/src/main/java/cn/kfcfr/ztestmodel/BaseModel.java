package cn.kfcfr.ztestmodel;

import cn.kfcfr.core.DefaultToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhangqf on 2017/6/16.
 */
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 4792315444759869609L;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, new DefaultToStringStyle());
    }
}
