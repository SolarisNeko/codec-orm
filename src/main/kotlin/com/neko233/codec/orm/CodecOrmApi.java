package com.neko233.codec.orm;

import com.neko233.codec.orm.api.DeserializePostOrmApi;
import com.neko233.codec.orm.api.SerializePreOrmApi;


/**
 * <汇总后的 CodecOrm 入口>
 * 外部使用只需要实现这个 API 即可
 * <p>
 * 编解码 ORM API
 * 提供:
 * 1. serialize 前做 ORM
 * 2. deserialize 后做 ORM
 *
 * @author SolarisNeko
 * Date on 2023-12-02
 */
public interface CodecOrmApi extends SerializePreOrmApi, DeserializePostOrmApi {
}
