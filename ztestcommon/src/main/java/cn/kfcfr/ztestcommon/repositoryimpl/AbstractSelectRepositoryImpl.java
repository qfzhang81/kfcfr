package cn.kfcfr.ztestcommon.repositoryimpl;

import cn.kfcfr.persistence.mybatis.pagination.IPagedUtil;
import cn.kfcfr.persistence.mybatis.pagination.github_pagehelper.GitHubPagedUtil;
import cn.kfcfr.persistence.mybatis.repository.AbstractMybatisSelectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by zhangqf77 on 2018/2/27.
 */
public abstract class AbstractSelectRepositoryImpl<T> extends AbstractMybatisSelectRepository<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${pagehelper.offset-as-page-num}")
    private Boolean pageHelperOffsetAsPageNum;

    @Override
    protected IPagedUtil getPagedUtil() {
        return new GitHubPagedUtil(pageHelperOffsetAsPageNum);
    }
}
