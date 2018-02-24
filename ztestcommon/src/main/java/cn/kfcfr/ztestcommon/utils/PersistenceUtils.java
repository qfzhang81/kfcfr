package cn.kfcfr.ztestcommon.utils;

import cn.kfcfr.persistence.mybatis.mapper.EntityColumnMapConverter;
import cn.kfcfr.persistence.mybatis.mapper.MapperUtil;
import cn.kfcfr.persistence.mybatis.pagination.github_pagehelper.GitHubPagedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Component
public class PersistenceUtils {
    protected static Logger logger = LoggerFactory.getLogger(PersistenceUtils.class);

    @Value("${pagehelper.offset-as-page-num}")
    private Boolean pageHelperOffsetAsPageNum;

    @Bean(name = "mapperUtil")
    public MapperUtil mapperUtil() {
        logger.info("------ MapperUtil init ------");
        MapperUtil util = new MapperUtil(new GitHubPagedUtil(pageHelperOffsetAsPageNum), new EntityColumnMapConverter());
        logger.info(MessageFormat.format("MapperUtil init: offsetAsPageNum={0}", pageHelperOffsetAsPageNum));
        return util;
    }
}
