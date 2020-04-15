package com.example.demo.open.encrypt;

/**
 *  加密接口
 */
public interface Encrypt {
    String encrypt(String response, String key, String nonce) throws Exception;
}
