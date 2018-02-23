package cn.kfcfr.core.generator.id;

import cn.kfcfr.core.convert.IntegerConvert;
import cn.kfcfr.core.convert.LongConvert;
import cn.kfcfr.core.math.IntCalc;
import cn.kfcfr.core.math.LongCalc;
import cn.kfcfr.core.system.SystemInformation;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.kfcfr.core.constant.DateTimeConstant.DATETIME_12_FORMAT;

/**
 * 生成总长度19位的全局ID，默认由12位时间+2位工作号(IPv4地址最后2位)+5位顺序号生成
 * Created by zhangqf on 2017/6/15.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GenerateLongWithTimestampBase extends GenerateLongAbstract {
    private final int totalLength = 19;//总的长度
    //自动生成
    private SimpleDateFormat sdf;//时间格式
    private String workerId;//优先使用设定的工作号，否则按规则由IPv4地址(或机器名hashcode)+进程号id生成
    protected Integer timestampLength;//时间部分长度
    //可设置
    private String timestampFormat;//时间格式
    private Integer seqLength;//顺序号长度
    private Integer cusWorkerId;//设定的工作号

    public GenerateLongWithTimestampBase() {
        this(null, null, null);
    }

    /***
     * 构造函数。时间戳+工作号+顺序号 总长度不超19位
     * @param timestampFormat 设置时间戳的格式。Null表示使用默认
     * @param seqLength 设定顺序号长度。Null表示使用默认
     * @param cusWorkerId 设定自定义的工作号。Null表示使用默认
     */
    public GenerateLongWithTimestampBase(String timestampFormat, Integer seqLength, Integer cusWorkerId) {
        if (seqLength != null && seqLength <= 0) {
            throw new IllegalArgumentException("seqLength must be a integer and greater than 0.");
        }
        if (cusWorkerId != null && cusWorkerId < 0) {
            throw new IllegalArgumentException("cusWorkerId must be a integer and greater than 0.");
        }
        this.timestampFormat = timestampFormat;
        this.seqLength = seqLength;
        this.cusWorkerId = cusWorkerId;
    }

    /***
     * 读取时间戳的格式
     * @return 时间格式
     */
    public String getTimestampFormat() {
        return timestampFormat;
    }

    /***
     * 读取顺序号长度
     * @return 顺序号长度
     */
    public Integer getSeqLength() {
        return seqLength;
    }

    /***
     * 读取自定义的工作号
     * @return 自定义的工作号
     */
    public Integer getCusWorkerId() {
        return cusWorkerId;
    }

    @Override
    protected void initParameters() {
        if (StringUtils.isBlank(timestampFormat)) {
            sdf = new SimpleDateFormat(DATETIME_12_FORMAT);
        }
        else {
            sdf = new SimpleDateFormat(timestampFormat);
        }
        timestampLength = sdf.format(new Date()).length();
        if (seqLength == null || seqLength <= 0) {
            seqLength = 5;
        }
        if (cusWorkerId != null && cusWorkerId < 0) {
            cusWorkerId = null;
        }
    }

    @Override
    protected boolean checkParameters() {
        int workerIdLength = (cusWorkerId == null) ? 0 : cusWorkerId.toString().length();
        if ((timestampLength + seqLength + workerIdLength) > totalLength) {
            //长度超过19，不能转换为long
            invalidEx = new NumberFormatException(MessageFormat.format("cannot convert to long because the length is greater than {0}.", totalLength));
            isValid = false;
            return false;
        }
        return true;
    }

    @Override
    protected void initInnerVariables() {
        //最大顺序号
        maxSeq = LongCalc.powDecimal(10, seqLength) - 1;
        //工作号
        workerId = "";
        int workerIdLength = totalLength - timestampLength - seqLength;
        if (workerIdLength > 0) {
            if (cusWorkerId != null) {
                //使用设定的workerId
                workerId = IntegerConvert.padLeft(cusWorkerId, workerIdLength);
            }
            else {
                //自动生成workerId
                createWorkerId(workerIdLength);
            }
        }
    }

    @Override
    protected long createTs(Date date) {
        String tsStr = sdf.format(date);
        return Long.parseLong(tsStr);
    }

    @Override
    protected void sleepInGenerate() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    protected long makeupOne(long ts, long seq) {
        return Long.parseLong(String.valueOf(ts) + workerId + LongConvert.padLeft(seq, seqLength));
    }

    /***
     * 生成工作号
     */
    private void createWorkerId(int workerIdLength) {
        String hostIp = SystemInformation.getLocalIp();
        String machineIdStr;
        if (!StringUtils.isEmpty(hostIp) && !hostIp.equals("127.0.0.1")) {
            //按ip生成，只取最后一段
            String[] ip4 = hostIp.split("\\.");
//                    for (String anIp4 : ip4) {
//                        machineIdStr += NumberFormat.decimalPadLeft(Integer.valueOf(anIp4), 3);
//                    }
            machineIdStr = IntegerConvert.padLeft(Integer.valueOf(ip4[ip4.length - 1]), 3);
        }
        else {
            //按机器名的hashcode生成
            int machineId = Math.abs(SystemInformation.getLocalHostName().hashCode());
            machineIdStr = String.valueOf(machineId);
        }
        int processIdLength = workerIdLength - machineIdStr.length();
        if (processIdLength > 0) {
            //machineId长度不够，加上ProcessId
            String processIdStr = SystemInformation.getProcessId();
            int processId = Integer.parseInt(processIdStr);
            if (processIdStr.length() > processIdLength) {
                processId = processId % IntCalc.powDecimal(10, processIdLength);
            }
            workerId = machineIdStr + IntegerConvert.padLeft(processId, processIdLength);
        }
        else {
            workerId = machineIdStr.substring(0 - processIdLength, machineIdStr.length());
        }
    }
}
