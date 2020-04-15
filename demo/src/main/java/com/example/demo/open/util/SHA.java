package com.example.demo.open.util;

import java.security.MessageDigest;
import com.example.demo.open.exception.SecurityException;

public class SHA {
	/**
	 * 获取签名
	 * 
	 * @param data
	 * @return
	 * @throws SecurityException
	 */
	public static String genSign256(String data) throws SecurityException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte[] digest = md.digest();

			return BytesHexStringUtil.bytesToHexString(digest);
		} catch (Exception e) {
			throw new SecurityException(100001010, e.getMessage());
		}
	}

	/**
	 * 比较签名
	 * 
	 * @param data
	 * @param sign
	 * @throws SecurityException
	 */
	public static void verifySign256(String data, String sign) throws SecurityException {
		if (!genSign256(data).equals(sign)) {
			throw new SecurityException(100001010, "signature not equal");
		}
	}
}
