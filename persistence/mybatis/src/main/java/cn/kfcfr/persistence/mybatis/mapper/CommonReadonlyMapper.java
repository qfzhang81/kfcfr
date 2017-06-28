package cn.kfcfr.persistence.mybatis.mapper;

import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;
import tk.mybatis.mapper.common.example.SelectCountByExampleMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import tk.mybatis.mapper.entity.IDynamicTableName;

/**
 * Created by zhangqf on 2017/6/9.
 */
public interface CommonReadonlyMapper<T> extends BaseSelectMapper<T>
        , SelectByExampleMapper<T>
        , SelectCountByExampleMapper<T>
        , RowBoundsMapper<T>
        , IDynamicTableName
        , SelectByIdsMapper<T> {
}
