package cn.kfcfr.mq.rabbitmq.helper;

import java.nio.charset.Charset;

/**
 * Created by zhangqf77 on 2018/6/21.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class RabbitMessageHelper {
    protected final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static byte[] convertBodyToBytes(String body, Charset charset) {
        Charset useCharset = charset;
        if (useCharset == null) useCharset = DEFAULT_CHARSET;
        return body.getBytes(useCharset);
    }

    public static String convertBodyToString(byte[] body, Charset charset) {
        Charset useCharset = charset;
        if (useCharset == null) useCharset = DEFAULT_CHARSET;
        return new String(body, useCharset);
    }
}
