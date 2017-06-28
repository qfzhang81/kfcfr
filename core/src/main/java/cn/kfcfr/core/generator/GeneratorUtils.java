package cn.kfcfr.core.generator;

import cn.kfcfr.core.generator.id.GenerateLongWithTimestampBase;
import cn.kfcfr.core.generator.id.IGenerateLong;

/**
 * Created by zhangqf on 2017/6/8.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class GeneratorUtils {
    private static IGenerateLong generateIdLong;

    static {
        generateIdLong = new GenerateLongWithTimestampBase();
    }

    /***
     * 根据当前时间生成一个带时间的ID
     * @return long型ID
     */
    public static long generateLong() {
        return generateIdLong.getOne();
    }

    /***
     * 根据当前时间生成多个带时间的顺序ID
     * @param length 顺序ID个数
     * @return long型ID的数组
     */
    public static long[] generateLongList(int length) {
        return generateIdLong.getList(length);
    }
}
