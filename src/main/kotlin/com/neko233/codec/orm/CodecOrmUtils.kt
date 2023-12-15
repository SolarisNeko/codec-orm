package com.neko233.codec.orm

import com.neko233.codec.orm.api.SerializePreOrmApi
import java.lang.reflect.Field

object CodecOrmUtils {

    // 根据字段名获取字段
    @JvmStatic
    fun getFieldByName(
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

    fun setFieldByCodecType(
        field: Field,
        self: SerializePreOrmApi,
        defaultValue: String,
    ) {
        if (field.type == String::class.java) {
            field.set(self, defaultValue)
            return
        }
        if (field.type == ByteArray::class.java) {
            field.set(self, defaultValue.encodeToByteArray())
            return
        }
        throw RuntimeException("不支持 obj=${self.javaClass.name}}, field=${field.name}, set default value=${defaultValue}")
    }
}
