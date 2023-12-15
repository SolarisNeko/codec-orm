# codec-orm 编解码, ORM 工具

## 介绍

对 Codec = Serialize / Deserialize 的 object 进行自动化的接口编解码处理 !

统一 API 使用.

## 版本

JVM = 1.8+

## 应用场景
一般应用在【通信协议层/配置数据层】的 class

对 String/byte[] field 转成你要的 interface/data class

最大的应用在于: 
1. 提供统一的自定义转换机制, 且可复用
2. 提供手动触发的 API, 不黑箱
3. 能够接入 interface/abstract class

这些 class, 一般是稳定的字段名, 改动往往是新开一个协议对象.


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
    var user: User? = null


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