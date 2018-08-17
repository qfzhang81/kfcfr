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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangqf77 on 2018/7/5.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "all"})
public class MqFactory {
    protected final static Logger logger = LoggerFactory.getLogger(MqFactory.class);

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
            String msg = MessageFormat.format("An error occurred when creating exchange ''{0}''.", exchangeName);
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
        createExchange(factory, exchangeName, BuiltinExchangeType.FANOUT);
    }

    public static void createDirectExchange(ConnectionFactory factory, String exchangeName) {
        createExchange(factory, exchangeName, BuiltinExchangeType.DIRECT);
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
            String msg = MessageFormat.format("An error occurred when creating queue ''{0}''.", queueName);
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
            String msg = MessageFormat.format("An error occurred when creating bind for exchange, from ''{0}'' to ''{1}''.", sourceExchange, targetExchange);
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
            String msg = MessageFormat.format("An error occurred when creating bind for queue ''{0}'' to exchange ''{1}''.", queueName, exchangeName);
            logger.error(msg, ex);
            throw new WrappedException(msg, ex);
        }
        finally {
            //关闭
            closeChannel(channel);
            closeConnection(connection);
        }
    }

    public static void createQueue(ConnectionFactory factory, String queueName, String delayExchange, String redirectExchange, long delaySecond) {
        String delayRoutingKey = queueName + ".delay";

        Map<String, Object> argsDelay = new HashMap<>(2);
        //x-dead-letter-exchange    声明死信交换机
        argsDelay.put("x-dead-letter-exchange", delayExchange);
        //x-dead-letter-routing-key    声明死信路由键
        argsDelay.put("x-dead-letter-routing-key", delayRoutingKey);

        Map<String, Object> argsRedirect = new HashMap<>(3);
        //x-dead-letter-exchange    声明死信交换机
        argsRedirect.put("x-dead-letter-exchange", redirectExchange);
        //x-dead-letter-routing-key    声明死信路由键
        argsRedirect.put("x-dead-letter-routing-key", delayRoutingKey);
        //x-message-ttl    声明消息过期时间，单位毫秒
        argsRedirect.put("x-message-ttl", delaySecond * 1000);

        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel();
            //Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException;
            //创建正常队列，包括死信设置
            channel.queueDeclare(queueName, true, false, false, argsDelay);
            //创建延迟队列，包括死信设置
            channel.queueDeclare(delayRoutingKey, true, false, false, argsRedirect);

            //Exchange.BindOk queueBind(queueName, exchangeName, bindingKey);
            //正常队列注册延迟重定向路由
            channel.queueBind(queueName, redirectExchange, delayRoutingKey);
            //延迟队列注册延迟路由
            channel.queueBind(delayRoutingKey, delayExchange, delayRoutingKey);
        }
        catch (Exception ex) {
            String msg = MessageFormat.format("An error occurred when creating queue ''{0}''.", queueName);
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
