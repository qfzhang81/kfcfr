package cn.kfcfr.mq.rabbitmq.channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangqf77 on 2018/6/8.
 */
public abstract class AbstractChannel {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    protected ConnectionFactory factory;
    protected Connection connection;

    protected void createConnection() throws IOException, TimeoutException {
        if (connection == null || !connection.isOpen()) {
            synchronized (this) {
                if (connection == null || !connection.isOpen()) {
                    connection = factory.newConnection();
                }
            }
        }
    }

    protected Channel getChannel() throws IOException, TimeoutException {
        createConnection();
        return connection.createChannel();
    }

    protected void closeChannel(Channel channel) {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        }
        catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
        }
    }
}
