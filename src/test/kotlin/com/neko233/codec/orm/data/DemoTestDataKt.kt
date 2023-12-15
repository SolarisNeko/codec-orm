package com.example.codec.postorm

import com.neko233.codec.orm.CodecOrmApi
import com.neko233.codec.orm.annotation.CodecTargetField
import com.neko233.codec.orm.annotation.DeserializePostOrm
import com.neko233.codec.orm.annotation.SerializePreOrm
import com.neko233.codec.orm.api.UserDeserializeFactoryApi
import com.neko233.codec.orm.api.UserSerializeFactoryApi
import com.neko233.codec.orm.data.User
import org.junit.Test
import kotlin.test.assertEquals

class DemoTestDataKt : CodecOrmApi {

    /**
     * codec target field
     */
    @CodecTargetField(serializeFlag = false)
    var fromField: String = """
        {"name": "demo"}
    """.trimIndent()


    // serialize + deserialize
    @DeserializePostOrm(
        fromFieldName = "fromField",
        factoryClass = UserDeserializeFactoryApi::class
    )
    @SerializePreOrm(
        toFieldName = "serializeText",
        factoryClass = UserSerializeFactoryApi::class
    )
    var user: User? = null


    @CodecTargetField(deserializeFlag = false)
    var toField: String = ""


    @Test
    fun demo() {
        val data = DemoTestDataKt()

        // deserialize
        data.deserializePostOrm()
        val name = data.user!!.name
//        println(name)
        assertEquals("demo", name)

        // serialize
        data.serializePreOrm()
//        println(data.fromField)
        assertEquals("{\"name\": \"demo\"}", data.fromField)

    }
}

