package cn.kfcfr.persistence.common.repository;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ICrudWithConvertRepository<M, T> extends ICrudRepository<T> {
    int addWithConvert(M model, boolean ignoreNullField);

    int addWithConvert(List<M> models, boolean ignoreNullField);

    int updateWithConvert(M model, boolean ignoreNullField);

    int updateWithConvert(List<M> models, boolean ignoreNullField);
}
