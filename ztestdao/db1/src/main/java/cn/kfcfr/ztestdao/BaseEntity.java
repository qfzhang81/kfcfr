package cn.kfcfr.ztestdao;

import cn.kfcfr.core.DefaultToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhangqf on 2017/6/16.
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -5671608785993267719L;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, new DefaultToStringStyle());
    }
}
