import cn.kfcfr.mq.rabbitmq.listener.AbstractConsumerListener;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryData;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestHelloListener extends AbstractConsumerListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String queueName;

    public TestHelloListener(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public boolean handle(ConsumerDeliveryData data) {
        System.out.println(MessageFormat.format("{0}[{3}.{4}] received {1}, routing key = {2}", sdf.format(new Date()), data.getMessage(), data.getRoutingKey(), queueName, this.toString()));
        return true;
    }
}
