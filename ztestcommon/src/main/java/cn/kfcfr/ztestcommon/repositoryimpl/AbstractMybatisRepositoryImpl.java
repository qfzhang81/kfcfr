package cn.kfcfr.ztestcommon.repositoryimpl;

import cn.kfcfr.persistence.mybatis.mapper.TkCrudMapper;
import cn.kfcfr.persistence.mybatis.mapper.TkReadonlyMapper;
import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;
import cn.kfcfr.persistence.mybatis.pagination.PagedUtilFactory;
import cn.kfcfr.persistence.mybatis.repository.AbstractMybatisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractMybatisRepositoryImpl<M, T> extends AbstractMybatisRepository<M, T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${pagehelper.offset-as-page-num}")
    private Boolean pageHelperOffsetAsPageNum;

    @Override
    protected IPagedUtil createPagedUtil() {
        logger.info(MessageFormat.format("Create GitHubPagedUtil with pageHelperOffsetAsPageNum={0}", pageHelperOffsetAsPageNum));
        return PagedUtilFactory.createGitHubPagedUtil(pageHelperOffsetAsPageNum);
    }

    @Override
    protected TkCrudMapper<T> getCrudMapper() {
        return null;
    }

    @Override
    protected TkReadonlyMapper<T> getReadonlyMapper() {
        return null;
    }
}
