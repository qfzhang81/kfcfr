package cn.kfcfr.core.generator.id;

import java.util.Calendar;
import java.util.Date;

/**
 * 生成总长度19位的全局ID，由6位日期+1位小时+3位IPv4地址(或机器名hashcode)+3位进程号+6位顺序号生成
 * 适用于ID需求量小，一台服务器会部署多个应用
 * Created by zhangqf on 2017/6/15.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithDate extends GenerateLongWithTimestampBase {
    //内部使用
    private Calendar calendar;

    public GenerateLongWithDate() {
        this(null);
    }

    /***
     * 指定工作号进行初始化
     * @param cusWorkerId 自定义的工作号
     */
    public GenerateLongWithDate(Integer cusWorkerId) {
        super("yyMMdd", 6, cusWorkerId);
        calendar = Calendar.getInstance();
        checkIsValid();
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        //时间部分还有1位小数，所以长度要加1
        timestampLength = timestampLength + 1;
    }

    @Override
    protected long createTs(Date date) {
        //6位日期+1位小时
        long ts = super.createTs(date);
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //2位小时转1位
        if (hour >= 21) {
            hour = 9;
        }
        else if (hour >= 19) {
            hour = 8;
        }
        else if (hour >= 17) {
            hour = 7;
        }
        else if (hour >= 15) {
            hour = 6;
        }
        else if (hour >= 13) {
            hour = 5;
        }
        else if (hour >= 11) {
            hour = 4;
        }
        else if (hour >= 9) {
            hour = 3;
        }
        else if (hour >= 6) {
            hour = 2;
        }
        else if (hour >= 3) {
            hour = 1;
        }
        else {
            hour = 0;
        }
        return Long.parseLong(String.valueOf(ts) + String.valueOf(hour));
    }

}
