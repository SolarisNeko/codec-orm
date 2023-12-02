# codec-orm 编解码, ORM 工具

## 介绍

对 Codec = Serialize / Deserialize 的 object 进行自动化的接口编解码处理 !

统一 API 使用.

## 版本

JVM = 1.8+

# 核心 API

1. CodecOrmApi
   1. SerializePreOrmApi 序列化
   2. DeserializePostOrmApi 反序列化
2. 注解:
    1. @DeserializePostOrm
    2. @SerializePreOrm
3. 工厂 API
   1. DeserializeOrmFactoryApi
   2. SerializeOrmFactoryApi

# 使用

## Define Class 定义 Class

```kotlin

class DemoTestData : CodecOrmApi {

    /**
     * 反序列化来源的文本
     */
    val text: String = """
        {"name": "demo"}
    """.trimIndent()

    // 从哪个 field 反序列化 ?
    @DeserializePostOrm(
        fromFieldName = "text",
        factoryClass = UserDeserializeFactoryApi::class
    )
    // 序列化到哪个 field ?
    @SerializePreOrm(
        toFieldName = "serializeText",
        factoryClass = UserSerializeFactoryApi::class
    )
    val user: User? = null


    var serializeText: String = ""

}

```

## Deserialize Factory 反序列化工厂

```kotlin
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

```

## Serialize Factory 序列化工厂

```kotlin

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

```

## Use & Test

```kotlin

class CodecOrmApiTest {

    @Test
    fun demo() {
        val data = DemoTestData()


        // deserialize
        data.deserializePostOrm()
        println(data.user!!.name)


        // serialize
        data.serializePreOrm()
        println(data.serializeText)

    }
}
```