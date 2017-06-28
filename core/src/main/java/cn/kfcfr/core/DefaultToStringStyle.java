package cn.kfcfr.core;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf on 2017/6/8.
 */
public class DefaultToStringStyle extends ToStringStyle {
    private static final long serialVersionUID = -5742920956427770441L;
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(CommonConstant.TIMESTAMP_FORMAT);

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof Date) {
            value = dateTimeFormat.format(value);
        }
        //buffer.append(value);
        super.appendDetail(buffer, fieldName, value);
    }
}
