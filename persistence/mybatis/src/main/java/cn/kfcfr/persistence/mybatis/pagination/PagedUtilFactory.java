package cn.kfcfr.persistence.mybatis.pagination;

import cn.kfcfr.persistence.mybatis.pagination.github_pagehelper.GitHubPagedUtil;

/**
 * Created by zhangqf77 on 2018/2/28.
 */
public class PagedUtilFactory {
    public static IPagedUtil createGitHubPagedUtil(boolean pageHelperOffsetAsPageNum) {
        return new GitHubPagedUtil(pageHelperOffsetAsPageNum);
    }
}
