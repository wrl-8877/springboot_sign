package com.example.demo.open.decrypt;

/**
 * 解密接口
 */
public interface Decrypt {
    String decrypt(String payload, String key, String nonce) throws Exception;
}
