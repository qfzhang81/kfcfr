//package cn.kfcfr.ztestcommon.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import wocentral.infra.core.format.DateAndTimeFormat;
//import wocentral.infra.core.format.NumberFormat;
//import wocentral.infra.core.generator.GenerateLongUtils;
//import wocentral.infra.core.generator.GenerateLongUtilsImpl;
//
//import java.text.MessageFormat;
//
//@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
//@Component
//public class GenerateUtils {
//    protected static Logger logger = LoggerFactory.getLogger(GenerateUtils.class);
//
//    @Value("${server.port}")
//    private String strPort;
//
//    @Bean(name = "generateLongUtils")
//    public GenerateLongUtils generateLongUtils() {
//        logger.info("------ GenerateLongUtils init ------");
//        GenerateLongUtils utils = null;
//        try {
//            utils = new GenerateLongUtilsImpl(2015, 10, 1, strPort);//2015-10-01
//            logger.info(MessageFormat.format("GenerateLongUtils init: startDiff={0}, port={1}", DateAndTimeFormat.format4TimeStamp(utils.getStartDiff()), NumberFormat.format4Original(utils.getPort())));
//        }
//        catch (Exception ex) {
//            logger.error("Error occurred in constructor of GenerateLongUtils.", ex);
//        }
//        return utils;
//    }
//
//
//}
