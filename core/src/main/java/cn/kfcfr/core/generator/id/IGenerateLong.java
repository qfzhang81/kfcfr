package cn.kfcfr.core.generator.id;

import cn.kfcfr.core.generator.LongRange;

import java.util.List;

/**
 * Created by zhangqf on 2017/6/8.
 */
public interface IGenerateLong {
    long getOne();

    List<LongRange> getList(int length);
}
