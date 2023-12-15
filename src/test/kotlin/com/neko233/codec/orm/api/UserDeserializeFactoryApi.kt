package com.neko233.codec.orm.api

import com.neko233.codec.orm.DeserializeOrmFactoryApi
import com.neko233.codec.orm.data.User
import kotlinx.serialization.json.Json

class UserDeserializeFactoryApi : DeserializeOrmFactoryApi<User> {

    override fun type(): Class<User> {
        return User::class.java
    }

    override fun deserializeFromText(text: String): User {
        return Json.decodeFromString<User>(text)
    }

    override fun deserializeFromBytes(bytes: ByteArray): User {
        return Json.decodeFromString<User>(String(bytes))
    }
}
