package com.example.codec.postorm

import com.example.codec.postorm.annotation.DeserializePostOrm
import com.example.codec.postorm.annotation.SerializePreOrm
import com.neko233.codec.orm.CodecOrmApi
import com.neko233.codec.orm.data.User
import com.neko233.codec.orm.factory.UserDeserializeFactoryApi
import com.neko233.codec.orm.factory.UserSerializeFactoryApi

class DemoTestData : CodecOrmApi {

    /**
     * 反序列化来源的文本
     */
    val text: String = """
        {"name": "demo"}
    """.trimIndent()

    @DeserializePostOrm(
        fromFieldName = "text",
        factoryClass = UserDeserializeFactoryApi::class
    )
    @SerializePreOrm(
        toFieldName = "serializeText",
        factoryClass = UserSerializeFactoryApi::class
    )
    val user: User? = null


    var serializeText: String = ""

}

