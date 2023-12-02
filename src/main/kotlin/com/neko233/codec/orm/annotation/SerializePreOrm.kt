package com.example.codec.postorm.annotation

import com.neko233.codec.orm.factory.SerializeOrmFactoryApi
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SerializePreOrm(

    // 转为哪个字段 ?
    val toFieldName: String = "",

    // 序列化工厂
    val factoryClass: KClass<out SerializeOrmFactoryApi<*>>,
)