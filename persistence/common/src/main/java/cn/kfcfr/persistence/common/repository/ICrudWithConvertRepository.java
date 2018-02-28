package cn.kfcfr.persistence.common.repository;

import java.util.List;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public interface ICrudWithConvertRepository<M, T> extends ICrudRepository<T> {
    int addWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException;

    int addWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException;

    int updateWithConvert(M model, boolean ignoreNullField) throws ReflectiveOperationException;

    int updateWithConvert(List<M> models, boolean ignoreNullField) throws ReflectiveOperationException;
}
