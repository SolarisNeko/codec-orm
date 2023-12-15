package com.neko233.codec.orm.api

import com.neko233.codec.orm.SerializeOrmFactoryApi
import com.neko233.codec.orm.annotation.SerializePreOrm
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass


/**
 * 序列化前的前置 API
 */
@JvmDefaultWithCompatibility
interface SerializePreOrmApi {

    // 伴生对象用于存储工厂实例的映射
    companion object {
        private val classToSerializeFactoryMap: MutableMap<KClass<out SerializeOrmFactoryApi<*>>, SerializeOrmFactoryApi<*>> =
            ConcurrentHashMap()

        // 获取或创建工厂实例
        private fun getOrCreateFactoryInstance(factoryClass: KClass<out SerializeOrmFactoryApi<*>>): SerializeOrmFactoryApi<*> {
            return classToSerializeFactoryMap.computeIfAbsent(factoryClass) {
                it.java.newInstance() as SerializeOrmFactoryApi<*>
            }
        }
    }

    /**
     * 序列化前的 ORM 处理, 将 object -> String/ByteArray
     */
    fun serializePreOrm() {
        val thisClass = this::class.java
        // 获取所有声明的字段
        val fields = thisClass.declaredFields

        for (field in fields) {
            // 获取字段上的 CodecPostOrm 注解
            val annotation = field.getAnnotation(SerializePreOrm::class.java)
            field.isAccessible = true

            if (annotation == null) {
                continue
            }
            val toFieldName = annotation.toFieldName

            // 目标字段
            val toField = getFieldByName(thisClass, toFieldName)

            if (toField == null) {
                continue
            }
            toField.isAccessible = true
            val toFieldType = toField.type
            val fromValue = field.get(this)

            // 获取工厂实例，使用伴生对象的懒加载属性
            val factoryInstance = getOrCreateFactoryInstance(annotation.factoryClass)

            // 如果源值为 null，则使用默认值进行反序列化
            if (fromValue == null) {
                field.set(this, null)
                continue
            }

            // 根据源值类型选择反序列化方法
            val serializeValue: Any? = when (toFieldType) {
                String::class.java -> {
                    factoryInstance.serializeToText(fromValue)
                }

                ByteArray::class.java -> factoryInstance.serializeToBytes(fromValue as Nothing?)
                else -> throw RuntimeException("不支持的反序列化ORM类型.type=${fromValue.javaClass}")
            }

            if (serializeValue == null) {
                field.set(this, null)
                continue
            }

            // 检查 ormValue 类型与字段类型是否一致
            if (toFieldType.isAssignableFrom(serializeValue.javaClass)) {
                // 将反序列化后的值设置到字段中
                toField.set(this, serializeValue)
            } else {
                throw RuntimeException(
                    """
                        前置序列化, value 类型与 field 类型不一致.
                        class=${thisClass.name}
                        from field=${field.name}, toField=${toField.name}
                        expectedType=${field.type}, factory generate type=${serializeValue.javaClass}
                    """.trimIndent()
                )
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