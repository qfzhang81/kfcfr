package cn.kfcfr.core.generator.id;

/**
 * 生成总长度19位的全局ID，由1bit固定0+24bit时间戳+10bit机器名hashcode+10bit进程id+19bit顺序号生成
 * 用于ID需求量中等，一台服务器会部署多个应用
 * Created by zhangqf on 2017/6/16.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithDiffMinute extends GenerateLongWithDiffBase {

    public GenerateLongWithDiffMinute() {
        this(null);
    }

    /***
     * 指定工作号进行初始化
     * @param cusWorkerId 自定义的工作号
     */
    public GenerateLongWithDiffMinute(Integer cusWorkerId) {
        super(null, 60000, 24, 19, 10, cusWorkerId);
        checkIsValid();
    }
}
