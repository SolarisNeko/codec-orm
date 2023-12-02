package com.neko233.codec.orm

import com.example.codec.postorm.DemoTestData
import org.junit.Test

/**
 *
 *
 * @author SolarisNeko
 * Date on 2023-12-02
 * */
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