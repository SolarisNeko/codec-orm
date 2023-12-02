package com.neko233.codec.orm

import com.neko233.codec.orm.api.DeserializePostOrmApi
import com.neko233.codec.orm.api.SerializePreOrmApi

/**
 * 编解码 ORM API
 * 提供:
 * 1. serialize 前做 ORM
 * 2. deserialize 后做 ORM
 *
 * @author SolarisNeko
 * Date on 2023-12-02
 * */
interface CodecOrmApi
    : SerializePreOrmApi, DeserializePostOrmApi {
}