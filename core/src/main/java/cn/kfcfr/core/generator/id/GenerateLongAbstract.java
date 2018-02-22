package cn.kfcfr.core.generator.id;

import cn.kfcfr.core.generator.LongRange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成总长度19位的全局ID。由各算法继承该类实现
 * Created by zhangqf on 2017/6/15.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public abstract class GenerateLongAbstract implements IGenerateLong {
    protected Boolean isValid;
    protected RuntimeException invalidEx;
    protected long maxSeq;//顺序号的最大数
    //生成ID使用
    private long seq = 0;//当前顺序号
    private long lastTs = 0;//保存最后一次生成的时间戳

    /***
     * 检查配置是否有效
     * @return True-有效 False-无效
     */
    protected boolean checkIsValid() {
        if (isValid == null) {
            synchronized (this) {
                if (isValid == null) {
                    //各参数格式化
                    initParameters();
                    //检查
                    if (checkParameters()) {
                        try {
                            //初始化内部用变量
                            initInnerVariables();
                            //试转换
                            long ts = createTs(new Date());
                            long ags = makeupOne(ts, seq);
                            //通过检查
                            isValid = true;
                        }
                        catch (RuntimeException ex) {
                            invalidEx = ex;
                            isValid = false;
                        }
                    }
                }
            }
        }
        return isValid;
    }

    /***
     * 按时间初始化顺序号
     * @param ts 时间
     */
    private void processTs(long ts) {
        if (lastTs != ts) {
            lastTs = ts;
            seq = 0;
        }
    }


    @Override
    public long getOne() {
        List<LongRange> list = getList(1);
        return list.get(0).getFrom();
    }

    @Override
    public List<LongRange> getList(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be a integer and greater than 0.");
        }
        if (!checkIsValid() && invalidEx != null) {
            throw invalidEx;
        }
        RuntimeException notExpectedEx = null;
        List<LongRange> list = new ArrayList<>();
        synchronized (this) {
            long ts;
            long thisAssign;
            int assigned = 0;
            while (assigned < length) {
                try {
                    ts = createTs(new Date());
                    processTs(ts);
                    if (seq < maxSeq) {
                        if ((length - assigned) > (maxSeq - seq)) {
                            //本轮seq范围分配不够，先分配一部分
                            thisAssign = maxSeq - seq;
                        }
                        else {
                            //seq范围足够
                            thisAssign = length - assigned;
                        }
                        //记录分配的号码
                        LongRange longRange = new LongRange();
                        longRange.setFrom(makeupOne(ts, seq + 1));
                        longRange.setTo(makeupOne(ts, seq + thisAssign));
                        list.add(longRange);
                        //更新变量
                        assigned += thisAssign;
                        seq += thisAssign;
                    }
                    if (assigned >= length) {
                        break;
                    }
                    //等待指定时间后继续生成
                    sleepInGenerate();
                }
                catch (Exception ex) {
                    //发生错误，记录并退出生成
                    notExpectedEx = new RuntimeException("Error occurred in while when generating id.", ex);
                    break;
                }
            }
        }
        if (notExpectedEx != null) throw notExpectedEx;
        return list;
    }

    /***
     * 初始化参数。参数没有设定值时，设为默认值
     */
    protected abstract void initParameters();

    /***
     * 检查参数是否符合要求
     * @return True-符合 False-不符合
     */
    protected abstract boolean checkParameters();

    /***
     * 初始化内部用的变量
     */
    protected abstract void initInnerVariables();

//    /***
//     * 生成workerId
//     */
//    protected abstract void createWorkerId();

    /***
     * 根据时间生成ID中的时间部分
     * @param date 时间
     * @return 时间部分
     */
    protected abstract long createTs(Date date);

    protected abstract void sleepInGenerate() throws InterruptedException;

    /***
     * 组合成一个ID
     * @param ts 时间部分
     * @param seq 顺序号
     * @return 全局ID
     */
    protected abstract long makeupOne(long ts, long seq);

}
