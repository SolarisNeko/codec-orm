package com.neko233.codec.orm

import com.neko233.codec.orm.api.DeserializePostOrmApi
import com.neko233.codec.orm.api.SerializePreOrmApi


/**
 * 反序列化工厂 API
 */
interface DeserializeOrmFactoryApi<T> {

    /**
     * 处理类型
     */
    fun type(): Class<T>

    /**
     * 从文本中反序列化对象
     */
    fun deserializeFromText(text: String): T

    /**
     * 从 byte[] 中反序列化对象
     */
    fun deserializeFromBytes(bytes: ByteArray): T

}

/**
 * 序列化 ORM 工厂
 */
interface SerializeOrmFactoryApi<T> {

    /**
     * 处理类型
     */
    fun type(): Class<T>

    /**
     * 序列化成 String
     */
    fun serializeToText(obj: Any?): String?

    /**
     * 序列化成 byte[]
     */
    fun serializeToBytes(obj: Any?): ByteArray?

}

