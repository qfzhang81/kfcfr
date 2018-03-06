package cn.kfcfr.persistence.mybatis.mapper;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

/**
 * Created by zhangqf on 2017/6/9.
 */
public interface TkCrudMapper<T> extends BaseMapper<T>
        , ExampleMapper<T>
        , DeleteByIdsMapper<T>
        , IdsMapper<T> {
}
