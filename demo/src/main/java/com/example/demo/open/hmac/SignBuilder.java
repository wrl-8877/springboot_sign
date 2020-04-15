package com.example.demo.open.hmac;

import com.example.demo.open.common.Appinfo;
import com.example.demo.open.exception.CustomException;
import com.example.demo.open.wrapper.HttpRequestWrapper;

import java.io.IOException;




/**
 * 构建服务端签名，默认实现采用hmacSha256
 * 优先采用接入方实现的这个接口进行签名，如果用户没有实现，则使用{@link HmacSha256Sign}
 *
 */
public interface SignBuilder {

    Boolean build(Appinfo appInfo, HttpRequestWrapper httpRequestWrapper) throws IOException, CustomException;
}
