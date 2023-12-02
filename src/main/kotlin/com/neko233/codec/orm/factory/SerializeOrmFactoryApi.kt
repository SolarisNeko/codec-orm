package com.neko233.codec.orm.factory

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