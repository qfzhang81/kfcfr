package cn.kfcfr.mq.rabbitmq.spring;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangqf77 on 2018/7/5.
 */
public class MqSpringFactory {
    public static CachingConnectionFactory createFactory(String host, Integer port, String username, String password, String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    public static DirectExchange createDirectExchange(RabbitAdmin admin, String exchangeName) {
        DirectExchange exchange = new DirectExchange(exchangeName, true, false);
        admin.declareExchange(exchange);
        return exchange;
    }

    public static FanoutExchange createFanoutExchange(RabbitAdmin admin, String exchangeName) {
        FanoutExchange exchange = new FanoutExchange(exchangeName, true, false);
        admin.declareExchange(exchange);
        return exchange;
    }

    public static TopicExchange createTopicExchange(RabbitAdmin admin, String exchangeName) {
        TopicExchange exchange = new TopicExchange(exchangeName, true, false);
        admin.declareExchange(exchange);
        return exchange;
    }

    public static Exchange createDeadLetterExchange(RabbitAdmin admin, String exchangeName) {
        return createDirectExchange(admin, exchangeName);
    }

    public static Queue createQueue(RabbitAdmin admin, String queueName) {
        Queue queue = new Queue(queueName, true, false, false);
        admin.declareQueue(queue);
        return queue;
    }

    public static Queue createQueue(RabbitAdmin admin, String queueName, Map<String, Object> arguments) {
        Queue queue = new Queue(queueName, true, false, false, arguments);
        admin.declareQueue(queue);
        return queue;
    }

    public static void createQueueBind(RabbitAdmin admin, Queue queue, Exchange exchange, String routingKey) {
        createQueueBind(admin, queue.getName(), exchange.getName(), routingKey);
    }

    public static void createQueueBind(RabbitAdmin admin, String queueName, String exchangeName, String routingKey) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        admin.declareBinding(binding);
    }

    public static Queue createQueueWithDelay(RabbitAdmin admin, String queueName, Exchange delayExchange, Exchange redirectExchange, long delaySecond) {
        String delayRoutingKey = queueName + ".delay";

        Map<String, Object> argsDelay = new HashMap<>(2);
        //x-dead-letter-exchange    声明死信交换机
        argsDelay.put("x-dead-letter-exchange", delayExchange.getName());
        //x-dead-letter-routing-key    声明死信路由键
        argsDelay.put("x-dead-letter-routing-key", delayRoutingKey);

        Map<String, Object> argsRedirect = new HashMap<>(3);
        //x-dead-letter-exchange    声明死信交换机
        argsRedirect.put("x-dead-letter-exchange", redirectExchange.getName());
        //x-dead-letter-routing-key    声明死信路由键
        argsRedirect.put("x-dead-letter-routing-key", delayRoutingKey);
        //x-message-ttl    声明消息过期时间，单位毫秒
        argsRedirect.put("x-message-ttl", delaySecond * 1000);

        //创建正常队列，包括死信设置
        Queue queue = new Queue(queueName, true, false, false, argsDelay);
        admin.declareQueue(queue);

        //创建延迟队列，包括死信设置
        Queue delayQueue = new Queue(delayRoutingKey, true, false, false, argsRedirect);
        admin.declareQueue(delayQueue);

        //正常队列注册延迟重定向路由
        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, redirectExchange.getName(), delayRoutingKey, null);
        admin.declareBinding(binding);

        //延迟队列注册延迟路由
        Binding binding2 = new Binding(delayQueue.getName(), Binding.DestinationType.QUEUE, delayExchange.getName(), delayRoutingKey, null);
        admin.declareBinding(binding2);

        return queue;
    }
}