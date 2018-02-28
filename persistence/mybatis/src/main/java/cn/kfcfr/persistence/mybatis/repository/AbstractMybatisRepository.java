package cn.kfcfr.persistence.mybatis.repository;

import cn.kfcfr.persistence.mybatis.mapper.EntityColumnMapConverter;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
public abstract class AbstractMybatisRepository {
    private static IPagedUtil pagedUtil;
    private static MapperUtil mapperUtil;
    private final EntityColumnMapConverter mapConverter = new EntityColumnMapConverter();

    protected IPagedUtil getPagedUtil() {
        if (pagedUtil == null) {
            synchronized (this) {
                if (pagedUtil == null) {
                    pagedUtil = createPagedUtil();
                }
            }
        }
        return pagedUtil;
    }

    protected MapperUtil getMapperUtil() {
        if (mapperUtil == null) {
            synchronized (this) {
                if (mapperUtil == null) {
                    mapperUtil = new MapperUtil(getPagedUtil(), mapConverter);
                }
            }
        }
        return mapperUtil;
    }

    protected abstract IPagedUtil createPagedUtil();
}
