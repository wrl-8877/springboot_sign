package com.example.demo.open.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import com.example.demo.open.exception.SecurityException;


public class AES {
	private static final String CHARSET = "utf-8";
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String ALGORITHM_STREAM = "AES/ECB/PKCS5Padding";

	public static String encrypt128(String text, String aesKey, String nonce) throws SecurityException {
		try {
			SecretKey secretKey = getSecretKey(aesKey);

			Cipher cipher = Cipher.getInstance(ALGORITHM);

			String nonce16 = String.format("%016d", new Object[] { Integer.valueOf(Integer.parseInt(nonce)) });
			IvParameterSpec ivParameterSpec = new IvParameterSpec(nonce16.getBytes());

			cipher.init(1, secretKey, ivParameterSpec);

			byte[] cryptograph = cipher.doFinal(text.getBytes(CHARSET));
			return new String(Base64.getEncoder().encode(cryptograph), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException(100001020, e.getMessage());
		}
	}

	private static SecretKey getSecretKey(String aesKey) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(aesKey.getBytes());
		keyGenerator.init(128, secureRandom);
		return keyGenerator.generateKey();
	}

	public static String decrypt128(String text, String aesKey, String nonce) throws SecurityException {
		try {
			SecretKey secretKey = getSecretKey(aesKey);

			Cipher cipher = Cipher.getInstance(ALGORITHM);

			String nonce16 = String.format("%016d", new Object[] { Integer.valueOf(Integer.parseInt(nonce)) });
			IvParameterSpec ivParameterSpec = new IvParameterSpec(nonce16.getBytes());

			cipher.init(2, secretKey, ivParameterSpec);
			byte[] byteContent = cipher.doFinal(Base64.getDecoder().decode(text));
			return new String(byteContent, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityException(100001030, e.getMessage());
		}
	}

	private static RuntimeException unchecked(Exception e) {
		if ((e instanceof RuntimeException)) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e);
	}
}
