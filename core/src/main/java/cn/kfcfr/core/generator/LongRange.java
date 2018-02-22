package cn.kfcfr.core.generator;

import java.io.Serializable;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@ToString
@EqualsAndHashCode
public class LongRange implements Serializable {
    private static final long serialVersionUID = -8064443761486442883L;
    private long from;
    private long to;

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
