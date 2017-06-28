package cn.kfcfr.core.generator.id;

/**
 * 生成总长度19位的全局ID，由1bit固定0+31bit时间戳+14bit机器名hashcode+18bit顺序号生成
 * 用于ID需求量大，一台服务器只部署单个应用
 * Created by zhangqf on 2017/6/16.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithDiffSecond extends GenerateLongWithDiffBase {

    public GenerateLongWithDiffSecond() {
        this(null);
    }

    /***
     * 指定工作号进行初始化
     * @param cusWorkerId 自定义的工作号
     */
    public GenerateLongWithDiffSecond(Integer cusWorkerId) {
        super(null, 1000, null, 18, 0, cusWorkerId);
        checkIsValid();
    }
}
