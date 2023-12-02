package com.neko233.codec.orm.factory

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