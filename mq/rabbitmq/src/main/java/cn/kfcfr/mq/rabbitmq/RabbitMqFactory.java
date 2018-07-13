package cn.kfcfr.mq.rabbitmq;

import cn.kfcfr.core.exception.WrappedException;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by zhangqf77 on 2018/7/5.
 */
public class RabbitMqFactory {
    protected final static Logger logger = LoggerFactory.getLogger(RabbitMqFactory.class);

    public static ConnectionFactory createFactory(String host, Integer port, String username, String password, String virtualHost) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    protected static void closeConnection(Connection connection) {
        if (connection != null && connection.isOpen()) {
            try {
                connection.close();
            }
            catch (IOException ex) {
                logger.error("An error occurred when closing connection.", ex);
            }
        }
    }

    protected static void closeChannel(Channel channel) {
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            }
            catch (Exception ex) {
                logger.error("An error occurred when closing channel.", ex);
            }
        }
    }

    public static void createExchange(ConnectionFactory factory, String exchangeName, BuiltinExchangeType exchangeType) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel();
            //Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments) throws IOException;
            channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        }
        catch (Exception ex) {
            String msg = MessageFormat.format("An error occurred when creating exchange '{0}'.", exchangeName);
            logger.error(msg, ex);
            throw new WrappedException(msg, ex);
        }
        finally {
            //关闭
            closeChannel(channel);
            closeConnection(connection);
        }
    }

    public static void createFanoutExchange(ConnectionFactory factory, String exchangeName) {
        createExchange(factory, exchangeName, BuiltinExchangeType.DIRECT);
    }

    public static void createDirectExchange(ConnectionFactory factory, String exchangeName) {
        createExchange(factory, exchangeName, BuiltinExchangeType.FANOUT);
    }

    public static void createTopicExchange(ConnectionFactory factory, String exchangeName) {
        createExchange(factory, exchangeName, BuiltinExchangeType.TOPIC);
    }

    public static void createQueue(ConnectionFactory factory, String queueName) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel();
            //Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException;
            channel.queueDeclare(queueName, true, false, false, null);
        }
        catch (Exception ex) {
            String msg = MessageFormat.format("An error occurred when creating queue '{0}'.", queueName);
            logger.error(msg, ex);
            throw new WrappedException(msg, ex);
        }
        finally {
            //关闭
            closeChannel(channel);
            closeConnection(connection);
        }
    }

    public static void createExchangeBind(ConnectionFactory factory, String sourceExchange, String targetExchange, String bindingKey) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel();
            //Exchange.BindOk exchangeBind(String destination, String source, String routingKey) throws IOException;
            channel.exchangeBind(targetExchange, sourceExchange, bindingKey);
        }
        catch (Exception ex) {
            String msg = MessageFormat.format("An error occurred when creating bind for exchange, from '{0}' to '{1}'.", sourceExchange, targetExchange);
            logger.error(msg, ex);
            throw new WrappedException(msg, ex);
        }
        finally {
            //关闭
            closeChannel(channel);
            closeConnection(connection);
        }
    }

    public static void createQueueBind(ConnectionFactory factory, String exchangeName, String queueName, String bindingKey) {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel();
            //Exchange.BindOk queueBind(queueName, exchangeName, bindingKey);
            channel.queueBind(queueName, exchangeName, bindingKey);
        }
        catch (Exception ex) {
            String msg = MessageFormat.format("An error occurred when creating bind for queue '{0}' to exchange '{1}'.", queueName, exchangeName);
            logger.error(msg, ex);
            throw new WrappedException(msg, ex);
        }
        finally {
            //关闭
            closeChannel(channel);
            closeConnection(connection);
        }
    }
}
