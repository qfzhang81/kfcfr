package cn.kfcfr.core.generator.id;

/**
 * 生成总长度19位的全局ID，由6位日期+2位小时+2位分钟+3位IPv4地址(或机器名hashcode)+6位顺序号生成
 * 用于ID需求量大，一台服务器只部署单个应用
 * Created by zhangqf on 2017/6/16.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithMinute extends GenerateLongWithTimestampBase {

    public GenerateLongWithMinute() {
        this(null);
    }

    /***
     * 指定工作号进行初始化
     * @param cusWorkerId 自定义的工作号
     */
    public GenerateLongWithMinute(Integer cusWorkerId) {
        super("yyMMddHHmm", 6, cusWorkerId);
        checkIsValid();
    }
}
