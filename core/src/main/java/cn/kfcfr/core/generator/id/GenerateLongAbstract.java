package cn.kfcfr.core.generator.id;

import java.util.Date;

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
        long[] list = getList(1);
        return list[0];
    }

    @Override
    public long[] getList(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be a integer and greater than 0.");
        }
        if (!checkIsValid() && invalidEx != null) {
            throw invalidEx;
        }
        long[] list = new long[length];
        synchronized (this) {
            long ts = createTs(new Date());
            processTs(ts);
            for (int i = 0; i < length; i++) {
                seq++;
                if (seq > maxSeq) {
                    while (lastTs == ts) {
                        try {
                            Thread.sleep(600);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ts = createTs(new Date());
                    }
                    processTs(ts);
                    seq++;
                }
                list[i] = makeupOne(ts, seq);
            }
        }
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

    /***
     * 组合成一个ID
     * @param ts 时间部分
     * @param seq 顺序号
     * @return 全局ID
     */
    protected abstract long makeupOne(long ts, long seq);

}
