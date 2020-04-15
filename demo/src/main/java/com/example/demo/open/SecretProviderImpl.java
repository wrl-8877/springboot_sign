package com.example.demo.open;

import com.example.demo.open.util.AESUtils;
import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;




/**
 * 自定义加解密方法
 */
public class SecretProviderImpl extends AbstractSecretProvider {
	private static final String EMPTY_STRING = "";
	 @Override
	    public String decrypt(String payload, String key,String nonce) throws Exception {
	        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
	        if (StringUtils.hasText(payload)) {
	            return AESUtils.decrypt(payload, key,nonce);
	        }
	        return EMPTY_STRING;
	    }

	    @Override
	    public String encrypt(String response, String key,String nonce) throws Exception {
	        Preconditions.checkArgument(StringUtils.hasText(key), "key should not be blank");
	        if (StringUtils.hasText(response)) {
	            return AESUtils.encrypt(response, key,nonce);
	        }
	        return EMPTY_STRING;
	    }
}
