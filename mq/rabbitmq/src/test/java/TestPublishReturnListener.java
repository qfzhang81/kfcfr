import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackData;
import cn.kfcfr.mq.rabbitmq.listener.ReturnCallBackListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/7/18.
 */
public class TestPublishReturnListener implements ReturnCallBackListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void returnedMessage(ReturnCallBackData data) {
        System.out.println(MessageFormat.format("{0} Return callback: {1}", sdf.format(new Date()), JsonConvert.toJsonString(data)));
    }
}
