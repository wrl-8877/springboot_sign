package com.example.demo.open.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

public class MD5Util {
	public static final String MD5(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();

			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		SecureRandom secureRandom = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
		String appKey = "xuanYin";
		long currentTime = System.currentTimeMillis();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String nonce = String.valueOf(secureRandom.nextInt(10000));
		String signKey = MD5("signKey" + uuid + appKey + currentTime + nonce);
		String dataKey = MD5("dataKey" + uuid + appKey + currentTime + nonce);
		System.out.println(signKey);
		System.out.println(dataKey);
	}
}
