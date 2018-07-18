import cn.kfcfr.core.convert.JsonConvert;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackData;
import cn.kfcfr.mq.rabbitmq.listener.ConfirmCallBackListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangqf77 on 2018/7/18.
 */
public class TestPublishConfirmListener implements ConfirmCallBackListener {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void confirm(ConfirmCallBackData data) {
        System.out.println(MessageFormat.format("{0} Confirm callback: {1}", sdf.format(new Date()), JsonConvert.toJsonString(data)));
    }
}
