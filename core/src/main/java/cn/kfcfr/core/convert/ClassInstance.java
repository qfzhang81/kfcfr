package cn.kfcfr.core.convert;

import java.text.MessageFormat;

@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public class ClassInstance {
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        }
        catch (ReflectiveOperationException ex) {
            throw new RuntimeException(MessageFormat.format("New an instance for '{0}' failed.", clazz), ex);
        }
    }
}
