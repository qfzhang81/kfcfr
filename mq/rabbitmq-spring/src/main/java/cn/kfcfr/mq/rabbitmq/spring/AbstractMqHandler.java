package cn.kfcfr.mq.rabbitmq.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class AbstractMqHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    protected ConnectionFactory connectionFactory;
}
