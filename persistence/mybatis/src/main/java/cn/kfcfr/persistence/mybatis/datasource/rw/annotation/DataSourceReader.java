package cn.kfcfr.persistence.mybatis.datasource.rw.annotation;

import java.lang.annotation.*;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSourceReader {
    String value() default "";
}
