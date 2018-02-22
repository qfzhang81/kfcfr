package cn.kfcfr.persistence.mybatis.datasource.annotation;

import java.lang.annotation.*;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSourceWriter {
    String value() default "";
}
