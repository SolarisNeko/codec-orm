package com.neko233.codec.orm.annotation;


import com.neko233.codec.orm.DeserializeOrmFactoryApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SolarisNeko
 * Date on 2023-12-01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeserializePostOrm {

    // 来源字段名
    String fromFieldName();

    // 反序列化后的对象生产工厂类
    Class<? extends DeserializeOrmFactoryApi<?>> factoryClass();

    // 默认值, 若为空, 则 return null
    String defaultValue() default "";


}
