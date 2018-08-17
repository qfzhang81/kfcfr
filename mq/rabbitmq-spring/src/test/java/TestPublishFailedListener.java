import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.listener.PublisherFailedData;
import cn.kfcfr.mq.rabbitmq.listener.PublisherFailedListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/7/18.
 */
public class TestPublishFailedListener implements PublisherFailedListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void fail(PublisherFailedData data) {
        System.out.println(MessageFormat.format("{0} Publish failed: {1}", sdf.format(new Date()), JsonConvert.toJsonString(data)));
    }
}
