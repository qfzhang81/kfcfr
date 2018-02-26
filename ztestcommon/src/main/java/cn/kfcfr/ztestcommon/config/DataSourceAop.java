package cn.kfcfr.ztestcommon.config;

import cn.kfcfr.persistence.common.datasource.RwDataSourceContextHolder;
import cn.kfcfr.persistence.common.datasource.RwDataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAop implements PriorityOrdered {
    protected static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Around("execution(* cn.kfcfr.ztestcommon.serviceimpl..*.*(..)) "
            + " and @annotation(cn.kfcfr.persistence.common.datasource.annotation.DataSourceReader) ")
    public Object setReaderType(ProceedingJoinPoint joinPoint) throws Throwable {
        //如果已经开启写事务了，那之后的所有读都从写库读
        if (!RwDataSourceType.reader.getType().equals(RwDataSourceContextHolder.get())) {
            logger.info(MessageFormat.format("Need change datasource to reader for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
            RwDataSourceContextHolder.setReader();
        }
        else {
            logger.info(MessageFormat.format("Already use datasource reader for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
        }
        try {
            return joinPoint.proceed();
        }
//        catch (Throwable throwable) {
//            logger.warn(MessageFormat.format("A throwable is catch when executing {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())), throwable);
//            throw throwable;
//        }
        finally {
            logger.info(MessageFormat.format("Change datasource to writer after executing {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
            RwDataSourceContextHolder.setWriter();
        }
    }

    @Before("execution(* cn.kfcfr.ztestcommon.serviceimpl..*.*(..)) "
            + " and @annotation(cn.kfcfr.persistence.common.datasource.annotation.DataSourceWriter) ")
    public void setWriterType(JoinPoint joinPoint) {
        if (!RwDataSourceType.writer.getType().equals(RwDataSourceContextHolder.get())) {
            logger.info(MessageFormat.format("Need change datasource to writer for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
            RwDataSourceContextHolder.setWriter();
        }
    }

//    @Pointcut(value = "@annotation(wocentral.infra.persistence.mybatis.datasource.annotation.DataSourceReader)")
//    private void readerPointcut() {
//    }
//
//    @Pointcut(value = "@annotation(wocentral.infra.persistence.mybatis.datasource.annotation.DataSourceWriter)")
//    private void writerPointcut() {
//    }

//    @Before("readerPointcut() and execution(* wocentral.ms.finance.serviceimpl..*.*(..)) "
//            + " and @annotation(sourceName) ")
//    public void setReaderType(JoinPoint joinPoint, DataSourceReader sourceName) {
//        //如果已经开启写事务了，那之后的所有读都从写库读
//        String db = sourceName.value();
//        if (StringUtils.isBlank(db)) db = "primary";
//        String key = db + "-" + DataSourceType.writer.getType();
//        if (!db.equals(DataSourceContextHolder.get())) {
//            logger.info(MessageFormat.format("Need change datasource to reader for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
//            DataSourceContextHolder.setReader(db);
//        }
//        else {
//            logger.info(MessageFormat.format("Already use datasource reader for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
//        }
//
//    }

//    @Before(value = "writerPointcut() and execution(* wocentral.ms.finance.serviceimpl..*.*(..)) "
//            + " and @annotation(sourceName) ")
//    public void setDbType(JoinPoint joinPoint, DataSourceWriter sourceName) {
//        String db = sourceName.value();
//        if (StringUtils.isBlank(db)) db = "primary";
//        String key = db + "-" + DataSourceType.writer.getType();
//        if (!key.equals(DataSourceContextHolder.get())) {
//            logger.info(MessageFormat.format("Need change datasource to writer for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
//            DataSourceContextHolder.setWriter(db);
//        }
//        else {
//            logger.info(MessageFormat.format("Already use datasource writer for {0}.{1} (ARGS: {2})", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
//        }
//    }

    /***
     * 值越小，越优先执行
     * 要优于事务的执行
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
