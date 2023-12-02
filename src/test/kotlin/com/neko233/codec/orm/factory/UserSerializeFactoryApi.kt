package com.neko233.codec.orm.factory

import com.neko233.codec.orm.data.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserSerializeFactoryApi : SerializeOrmFactoryApi<User> {

    override fun type(): Class<User> {
        return User::class.java
    }

    override fun serializeToText(obj: Any?): String? {
        if (obj == null) {
            return null
        }
        return Json.encodeToString(obj as User)
    }

    override fun serializeToBytes(obj: Any?): ByteArray? {
        if (obj == null) {
            return null
        }
        return Json.encodeToString(obj as User).encodeToByteArray()
    }
}
