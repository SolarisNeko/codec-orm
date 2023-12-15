package com.neko233.codec.orm.api

import com.neko233.codec.orm.DeserializeOrmFactoryApi
import com.neko233.codec.orm.annotation.DeserializePostOrm
import org.apache.commons.lang3.StringUtils
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass


// 接口用于反序列化ORM
@JvmDefaultWithCompatibility
interface DeserializePostOrmApi {

    // 伴生对象用于存储工厂实例的映射
    companion object {
        private val classToDeserializeFactoryMap: MutableMap<KClass<out DeserializeOrmFactoryApi<*>>, DeserializeOrmFactoryApi<*>> =
            ConcurrentHashMap()

        // 获取或创建工厂实例
        private fun getOrCreateFactoryInstance(factoryClass: KClass<out DeserializeOrmFactoryApi<*>>): DeserializeOrmFactoryApi<*> {
            return classToDeserializeFactoryMap.computeIfAbsent(factoryClass) {
                it.java.newInstance() as DeserializeOrmFactoryApi<*>
            }
        }
    }

    /**
     * 在反序列化后, 进行自我 ORM.
     * 将 String/ByteArray -> object
     */
    fun deserializePostOrm() {
        // 获取所有声明的字段
        val fields = this::class.java.declaredFields

        for (field in fields) {
            // 获取字段上的 CodecPostOrm 注解
            val annotation = field.getAnnotation(DeserializePostOrm::class.java)
            field.isAccessible = true

            if (annotation == null) {
                continue
            }
            val fromFieldName = annotation.fromFieldName
            val defaultValue = annotation.defaultValue

            // 获取源字段
            val fromField = getFieldByName(this::class.java, fromFieldName)

            if (fromField == null) {
                return
            }
            fromField.isAccessible = true
            val fromValue = fromField.get(this)

            // 获取工厂实例，使用伴生对象的懒加载属性
            val factoryInstance = getOrCreateFactoryInstance(annotation.factoryClass)

            // 如果源值为 null，则使用默认值进行反序列化
            if (fromValue == null) {
                val value = factoryInstance.deserializeFromText(defaultValue)
                field.set(this, value)
                return
            }

            // 根据源值类型选择反序列化方法
            val deserializeValue: Any? = when (fromValue) {
                is String -> {
                    if (StringUtils.isBlank(fromValue)) {
                        null
                    } else {
                        factoryInstance.deserializeFromText(fromValue)
                    }
                }

                is ByteArray -> {
                    if (fromValue == null || fromValue.size == 0) {
                        null
                    } else {
                        factoryInstance.deserializeFromBytes(fromValue)
                    }
                }

                else -> throw RuntimeException("不支持的反序列化ORM类型.type=${fromValue.javaClass}")
            }

            if (deserializeValue == null) {
                if (StringUtils.isNotBlank(defaultValue)) {
                    val defValue = factoryInstance.deserializeFromText(defaultValue)
                    field.set(this, defValue)
                    continue
                }
                field.set(this, null)
                continue
            }

            // 检查 ormValue 类型与字段类型是否一致
            if (field.type.isAssignableFrom(deserializeValue.javaClass)) {
                // 将反序列化后的值设置到字段中
                field.set(this, deserializeValue)
            } else {
                throw RuntimeException("反序列化后的值类型与字段类型不一致. field=${field.name}, expectedType=${field.type}, actualType=${deserializeValue?.javaClass}")
            }
        }
    }

    // 根据字段名获取字段
    private fun getFieldByName(
        clazz: Class<*>,
        fieldName: String,
    ): Field? {
        val allFields = ArrayList<Field>()

        var currentClass: Class<*>? = clazz
        while (currentClass != null) {
            allFields.addAll(currentClass.declaredFields)
            currentClass = currentClass.superclass
        }

        for (field in allFields) {
            if (field.name == fieldName) {
                return field
            }
        }

        return null
    }
}