package com.neko233.codec.orm.data;

import com.example.codec.postorm.DemoTestDataKt;
import com.neko233.codec.orm.CodecOrmApi;
import com.neko233.codec.orm.annotation.CodecTargetField;
import com.neko233.codec.orm.annotation.DeserializePostOrm;
import com.neko233.codec.orm.annotation.SerializePreOrm;
import com.neko233.codec.orm.api.UserDeserializeFactoryApi;
import com.neko233.codec.orm.api.UserSerializeFactoryApi;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author SolarisNeko
 * Date on 2023-12-15
 */
public class DemoTestDataJava implements CodecOrmApi {


    /**
     * codec target field
     */
    @CodecTargetField(deserializeFlag = false)
    String fromField = " {\"name\": \"demo\"}";


    // serialize + deserialize
    @DeserializePostOrm(
            fromFieldName = "fromField",
            factoryClass = UserDeserializeFactoryApi.class
    )
    @SerializePreOrm(
            toFieldName = "serializeText",
            factoryClass = UserSerializeFactoryApi.class
    )
    User user = null;


    @CodecTargetField(serializeFlag = false)
    String toField = "";


    @Test
    public void demo() {
        DemoTestDataKt data = new DemoTestDataKt();

        // deserialize
        data.deserializePostOrm();
        String name = data.getUser().getName();
//        println(name)
        assertEquals("demo", name);

        // serialize
        data.serializePreOrm();
//        println(data.fromField)
        assertEquals("{\"name\": \"demo\"}", data.getFromField());
    }
}
