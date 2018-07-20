package cn.kfcfr.mq.rabbitmq.channel;

import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackListener;
import cn.kfcfr.mq.rabbitmq.listener.PublisherFailedListener;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackListener;
import cn.kfcfr.mq.rabbitmq.message.AbstractMessage;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/5/23.
 */
public class PersistentPublisherChannel<T extends AbstractMessage> extends AbstractPublisherChannel<T> {

    public PersistentPublisherChannel(ConnectionFactory factory, String exchangeName, Charset charset) {
        super(factory, exchangeName, charset, null, null, null);
    }

    public PersistentPublisherChannel(ConnectionFactory factory, String exchangeName, Charset charset, ConfirmCallBackListener confirmCallBackListener, ReturnCallBackListener returnCallBackListener, PublisherFailedListener failedListener) {
        super(factory, exchangeName, charset, confirmCallBackListener, returnCallBackListener, failedListener);
    }

    @Override
    protected void init() {
        super.init();
//        this.durable = true;
        this.basicProperties = MessageProperties.PERSISTENT_TEXT_PLAIN;
    }
}
