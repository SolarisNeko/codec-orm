package com.neko233.codec.orm.annotation;


import com.neko233.codec.orm.SerializeOrmFactoryApi;

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
public @interface SerializePreOrm {


    // 转为哪个字段
    String toFieldName();

    // 反序列化后的对象生产工厂类
    Class<? extends SerializeOrmFactoryApi<?>> factoryClass();

    // 默认值, 若为空, 则 return null
    String defaultValue() default "";
}
