import cn.kfcfr.mq.rabbitmq.listener.AbstractConsumerListener;
import cn.kfcfr.mq.rabbitmq.listener.ConsumerDeliveryData;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by zhangqf77 on 2018/7/13.
 */
public class TestConsumeHelloListener extends AbstractConsumerListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String queueName;

    public TestConsumeHelloListener(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public boolean handle(ConsumerDeliveryData data) {
        boolean rst = true;
        Random randomIntNumber = new Random();
        int a = randomIntNumber.nextInt(100);
        if (a % 10 == 0) {
            rst = false;
        }
        System.out.println(MessageFormat.format("{0}[{3}.{4}] received {1}, routing key = {2}, random handle result = {5}", sdf.format(new Date()), data.getMessage(), data.getRoutingKey(), queueName, this.toString(), rst));
        return rst;
    }
}
