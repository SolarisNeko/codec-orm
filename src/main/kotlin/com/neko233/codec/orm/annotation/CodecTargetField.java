package com.neko233.codec.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记这个 field 是用于 serialize/deserialize 的
 *
 * @author SolarisNeko
 * Date on 2023-12-01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodecTargetField {

    /**
     * 是否序列化 ?
     */
    boolean serializeFlag() default true;

    /**
     * 是否反序列化 ?
     */
    boolean deserializeFlag() default true;

}
