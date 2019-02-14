package cn.kfcfr.scheduler.quartz;

/**
 * Created by zhangqf77 on 2018/5/22.
 */
public enum MisfireInstruction {
    ignore_misfires(-1),
    fire_once_now(1),
    do_nothing(2);

    private int code;

    MisfireInstruction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
