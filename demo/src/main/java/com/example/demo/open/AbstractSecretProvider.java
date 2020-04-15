package com.example.demo.open;

import com.google.common.base.Preconditions;


import com.example.demo.open.decrypt.Decrypt;
import com.example.demo.open.encrypt.Encrypt;
import com.example.demo.open.util.AESUtils;
import org.springframework.util.StringUtils;

/**
 * 可继承该类并交给spring管理
 * 自定义自己的加解密方法
 */
public abstract class AbstractSecretProvider implements Decrypt, Encrypt {

    private static final String EMPTY_STRING = "";
//TODO 
    @Override
    public String decrypt(String payload, String key,String nonce) throws Exception {
        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
        if (StringUtils.hasText(payload)) {
            return AESUtils.decrypt(payload, key,"");
        }
        return EMPTY_STRING;
    }

    @Override
    public String encrypt(String response, String key,String nonce) throws Exception {
        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
        if (StringUtils.hasText(response)) {
            return AESUtils.encrypt(response, key,"");
        }
        return EMPTY_STRING;
    }
}
