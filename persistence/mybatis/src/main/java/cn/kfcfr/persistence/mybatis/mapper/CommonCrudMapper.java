package cn.kfcfr.persistence.mybatis.mapper;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

/**
 * Created by zhangqf on 2017/6/9.
 */
public interface CommonCrudMapper<T> extends BaseMapper<T>
        , ExampleMapper<T>
        , DeleteByIdsMapper<T> {
}
