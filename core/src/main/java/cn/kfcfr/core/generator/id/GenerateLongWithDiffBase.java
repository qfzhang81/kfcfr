package cn.kfcfr.core.generator.id;

import cn.kfcfr.core.exception.WrappedException;
import cn.kfcfr.core.system.SystemInformation;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Date;

/**
 * 生成总长度19位的全局ID，默认由1bit固定0+31bit时间戳+15bit工作号(机器名hashcode+进程id)+17bit顺序号生成
 * Created by zhangqf on 2017/6/15.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithDiffBase extends GenerateLongAbstract {
    private final int totalBitLength = 63;//总的有效bit数
    //自动生成
    private long startTs;//时间戳初始值，默认从2016-01-01开始
    private int timestampLeftShift;//时间戳左移位数
    private int workIdLeftShift;//工作号左移位数
    private long workerId;//优先使用设定的工作号，否则按规则由机器名hashcode+进程id生成
    //可设置
    private Date startDiff;//时间戳起始日期
    private Integer timestampConversion;//时间部分毫秒转??的转换比例，默认1000转秒
    private Integer timestampBitLength;//时间戳bit数
    private Integer seqBitLength;//顺序号bit数
    private Integer processIdBitLength;//进程id的bit数，设定cusWorkerId后本值无效
    private Integer cusWorkerId;//设定的工作号

    public GenerateLongWithDiffBase() {
        this(null, null, null, null, null, null);
    }

    /***
     * 构造函数。时间戳+工作号+顺序号 总bit数不超63
     * @param startDiff 时间戳初始值。Null表示使用默认从2016-01-01开始
     * @param timestampConversion 时间部分毫秒转??的转换比例。Null表示默认1000转秒
     * @param timestampBitLength 时间戳bit数。Null表示使用默认
     * @param seqBitLength 顺序号bit数。Null表示使用默认
     * @param processIdBitLength 进程id的bit数。Null表示使用默认。设定cusWorkerId后本值无效
     * @param cusWorkerId 设定自定义的工作号。Null表示使用默认
     */
    public GenerateLongWithDiffBase(Date startDiff, Integer timestampConversion, Integer timestampBitLength, Integer seqBitLength, Integer processIdBitLength, Integer cusWorkerId) {
        if (timestampConversion != null && timestampConversion <= 0) {
            throw new IllegalArgumentException("timestampConversion must be a integer and greater than 0.");
        }
        if (timestampBitLength != null && timestampBitLength <= 0) {
            throw new IllegalArgumentException("timestampBitLength must be a integer and greater than 0.");
        }
        if (seqBitLength != null && seqBitLength <= 0) {
            throw new IllegalArgumentException("seqBitLength must be a integer and greater than 0.");
        }
        if (processIdBitLength != null && processIdBitLength < 0) {
            throw new IllegalArgumentException("processIdBitLength must be a integer and greater than 0.");
        }
        if (cusWorkerId != null && cusWorkerId < 0) {
            throw new IllegalArgumentException("cusWorkerId must be a integer and greater than 0.");
        }
        this.startDiff = startDiff;
        this.timestampConversion = timestampConversion;
        this.timestampBitLength = timestampBitLength;
        this.seqBitLength = seqBitLength;
        this.processIdBitLength = processIdBitLength;
        this.cusWorkerId = cusWorkerId;
    }

    @Override
    protected void initParameters() {
        if (startDiff == null) {
            startTs = 1451577600000L;//从2016-01-01开始
        }
        else {
            startTs = startDiff.getTime();
        }
        if (timestampBitLength == null || timestampBitLength <= 0) {
            timestampBitLength = 31;
        }
        if (seqBitLength == null || seqBitLength <= 0) {
            seqBitLength = 17;
        }
        if (timestampConversion == null || timestampConversion <= 0) {
            timestampConversion = 1000;
        }
    }

    @Override
    protected boolean checkParameters() {
        int workerBitLength = (cusWorkerId == null) ? ((processIdBitLength == null) ? 0 : processIdBitLength) : Integer.toBinaryString(cusWorkerId).length();
        if ((timestampBitLength + seqBitLength + workerBitLength) > totalBitLength) {
            //长度超过19，不能转换为long
            invalidEx = new NumberFormatException(MessageFormat.format("cannot convert to long because the length is greater than {0} bits.", totalBitLength));
            isValid = false;
            return false;
        }
        return true;
    }

    @Override
    protected void initInnerVariables() {
        //最大顺序号
        maxSeq = ~(-1L << seqBitLength);
        //工作号
        int workerBitLength = totalBitLength - timestampBitLength - seqBitLength;
        if (workerBitLength > 0) {
            if (cusWorkerId != null) {
                //使用设定的workerId
                workerId = cusWorkerId;
            }
            else {
                //自动生成workerId
                createWorkerId(workerBitLength);
            }
        }
        //得到位移数
        workIdLeftShift = seqBitLength;
        timestampLeftShift = workerBitLength + seqBitLength;
    }

    @Override
    protected long createTs(Date date) {
        return (new Date().getTime() - this.startTs) / timestampConversion;
    }

    @Override
    protected void sleepInGenerate() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            throw new WrappedException("Error occurred when thread do sleep.", ex);
        }
    }

    @Override
    protected long makeupOne(long ts, long seq) {
        return (ts << timestampLeftShift) | (workerId << workIdLeftShift) | seq;
    }

    /***
     * 生成工作号
     */
    private void createWorkerId(int workerBitLength) {
        long workerIdMask = ~(-1L << workerBitLength);
        long machineId = 0;
        if (processIdBitLength == null || processIdBitLength < 0) {
            processIdBitLength = computeProcessIdBitLength(workerBitLength);
        }
        String hostname = SystemInformation.getLocalHostName();
        if (StringUtils.isNotEmpty(hostname)) {
            machineId = Math.abs(hostname.hashCode());
        }
        if (processIdBitLength > 0) {
            long processIdMask = ~(-1L << processIdBitLength);
            long processId = Long.parseLong(SystemInformation.getProcessId());
            workerId = (machineId << processIdBitLength) | (processId & processIdMask);
        }
        else {
            workerId = machineId;
        }
        workerId = workerId & workerIdMask;
    }

    /***
     * 按规则分配进程id的bit长度
     * @param workerBitLength workerId的总bit长度
     * @return 进程id的bit长度
     */
    private int computeProcessIdBitLength(int workerBitLength) {
        int val = 1;
        if (workerBitLength >= 31) {
            val = 17;
        }
        else if (workerBitLength >= 20) {
            val = 10;
        }
        else if (workerBitLength >= 10) {
            val = workerBitLength / 3;
        }
        return val;
    }
}
