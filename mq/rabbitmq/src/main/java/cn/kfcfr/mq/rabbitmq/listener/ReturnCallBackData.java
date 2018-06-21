package cn.kfcfr.mq.rabbitmq.listener;

/**
 * Created by zhangqf77 on 2018/6/20.
 */
public class ReturnCallBackData {
    private String message;
    private int replyCode;
    private String replyText;
    private String exchange;
    private String routingKey;

    public ReturnCallBackData(String message, int replyCode, String replyText, String exchange, String routingKey) {
        this.message = message;
        this.replyCode = replyCode;
        this.replyText = replyText;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public String getMessage() {
        return message;
    }

    public int getReplyCode() {
        return replyCode;
    }

    public String getReplyText() {
        return replyText;
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
