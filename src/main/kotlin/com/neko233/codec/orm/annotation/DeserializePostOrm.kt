package com.example.codec.postorm.annotation

import com.neko233.codec.orm.factory.DeserializeOrmFactoryApi
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class DeserializePostOrm(
    // 来源字段名
    val fromFieldName: String = "",
    // 反序列化后的对象生产工厂类
    val factoryClass: KClass<out DeserializeOrmFactoryApi<*>>,
    // 默认值, 若为空, 则 return null
    val defaultValue: String = "",
)