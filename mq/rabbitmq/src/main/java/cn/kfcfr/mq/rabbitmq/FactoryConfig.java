//package cn.kfcfr.mq.rabbitmq;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
//public class FactoryConfig {
//    public static CachingConnectionFactory createFactory(String host, Integer port, String username, String password, String virtualHost) {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setPort(port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        return connectionFactory;
//    }
//
//    public static DirectExchange createDirectExchange(RabbitAdmin admin, String exchangeName) {
//        DirectExchange exchange = new DirectExchange(exchangeName, true, false);
//        admin.declareExchange(exchange);
//        return exchange;
//    }
//
//    public static FanoutExchange createFanoutExchange(RabbitAdmin admin, String exchangeName) {
//        FanoutExchange exchange = new FanoutExchange(exchangeName, true, false);
//        admin.declareExchange(exchange);
//        return exchange;
//    }
//
//    public static TopicExchange createTopicExchange(RabbitAdmin admin, String exchangeName) {
//        TopicExchange exchange = new TopicExchange(exchangeName, true, false);
//        admin.declareExchange(exchange);
//        return exchange;
//    }
//
//    public static Exchange createDeadLetterExchange(RabbitAdmin admin, String exchangeName) {
//        return createDirectExchange(admin, exchangeName);
//    }
//
//    public static Queue createQueue(RabbitAdmin admin, String queueName) {
//        Queue queue = new Queue(queueName, true, false, false);
//        admin.declareQueue(queue);
//        return queue;
//    }
//
//    public static Queue createQueue(RabbitAdmin admin, String queueName, Map<String, Object> arguments) {
//        Queue queue = new Queue(queueName, true, false, false, arguments);
//        admin.declareQueue(queue);
//        return queue;
//    }
//
//    public static void createBinding(RabbitAdmin admin, Queue queue, Exchange exchange, String routingKey) {
//        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), routingKey, null);
//        admin.declareBinding(binding);
//    }
//
//    public static Queue createQueueWithDelay(RabbitAdmin admin, String queueName, Exchange delayExchange, Exchange redirectExchange, long delaySecond) {
//        String delayRoutingKey = queueName + ".delay";
//
//        Map<String, Object> argsDelay = new HashMap<>(2);
//        //x-dead-letter-exchange    声明死信交换机
//        argsDelay.put("x-dead-letter-exchange", delayExchange.getName());
//        //x-dead-letter-routing-key    声明死信路由键
//        argsDelay.put("x-dead-letter-routing-key", delayRoutingKey);
//
//        Map<String, Object> argsRedirect = new HashMap<>(3);
//        //x-dead-letter-exchange    声明死信交换机
//        argsRedirect.put("x-dead-letter-exchange", redirectExchange.getName());
//        //x-dead-letter-routing-key    声明死信路由键
//        argsRedirect.put("x-dead-letter-routing-key", delayRoutingKey);
//        //x-message-ttl    声明消息过期时间，单位毫秒
//        argsRedirect.put("x-message-ttl", delaySecond * 1000);
//
//        //创建正常队列，包括死信设置
//        Queue queue = new Queue(queueName, true, false, false, argsDelay);
//        admin.declareQueue(queue);
//
//        //创建延迟队列，包括死信设置
//        Queue delayQueue = new Queue(delayRoutingKey, true, false, false, argsRedirect);
//        admin.declareQueue(delayQueue);
//
//        //正常队列注册延迟重定向路由
//        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, redirectExchange.getName(), delayRoutingKey, null);
//        admin.declareBinding(binding);
//
//        //延迟队列注册延迟路由
//        Binding binding2 = new Binding(delayQueue.getName(), Binding.DestinationType.QUEUE, delayExchange.getName(), delayRoutingKey, null);
//        admin.declareBinding(binding2);
//
//        return queue;
//    }
//
////    public static Queue createQueueWithDlx(RabbitAdmin admin, String queueName, Exchange dl_exchange, String dl_routingKey) {
////        Map<String, Object> args = new HashMap<>(2);
////        //x-dead-letter-exchange    声明死信交换机
////        args.put("x-dead-letter-exchange", dl_exchange.getName());
////        //x-dead-letter-routing-key    声明死信路由键
////        args.put("x-dead-letter-routing-key", dl_routingKey);
////        return createQueue(admin, queueName, args);
////    }
//
////    public static Queue createQueueWithDelay(RabbitAdmin admin, String queueName, Exchange delay_exchange, Exchange redirect_exchange, long delaySecond) {
////        String delay_routingKey = queueName + ".delay";
////        String delayQueueName = queueName + ".delay";
////
////        Map<String, Object> argsDelay = new HashMap<>(2);
////        //x-dead-letter-exchange    声明死信交换机
////        argsDelay.put("x-dead-letter-exchange", delay_exchange.getName());
////        //x-dead-letter-routing-key    声明死信路由键
////        argsDelay.put("x-dead-letter-routing-key", delay_routingKey);
////
////        Map<String, Object> argsRedirect = new HashMap<>(2);
////        //x-dead-letter-exchange    声明死信交换机
////        argsRedirect.put("x-dead-letter-exchange", redirect_exchange.getName());
////        //x-dead-letter-routing-key    声明死信路由键
////        argsRedirect.put("x-dead-letter-routing-key", delay_routingKey);
////        //x-message-ttl    声明消息过期时间，单位毫秒
////        argsRedirect.put("x-message-ttl", delaySecond * 1000);
////
////        //创建队列，包括死信设置
////        Queue queue = new Queue(queueName, true, false, false, argsDelay);
////        admin.declareQueue(queue);
////        Queue delayQueue = new Queue(delayQueueName, true, false, false, argsRedirect);
////        admin.declareQueue(delayQueue);
////
////        //设定重定向
////        Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, redirect_exchange.getName(), delay_routingKey, null);
////        admin.declareBinding(binding);
////
////        return queue;
////    }
//
//}
