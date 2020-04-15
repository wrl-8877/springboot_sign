package com.example.demo.open.exception;

public class SecurityException extends Exception {
	public static final int OK = 0;
	public static final int ComputeSignatureError = 100001010;
	public static final int EncryptAESError = 100001020;
	public static final int DecryptAESError = 100001030;
	public static final int SIGNATURE_ERROR = 100001040;
	private int code;

	private static String getMessage(int code) {
		switch (code) {
		case 100001010:
			return "SHA加密生成签名失败";
		case 100001020:
			return "AES加密失败";
		case 100001030:
			return "AES解密失败";
		}
		return null;
	}

	public SecurityException(int code) {
		super(getMessage(code, null));
	}

	public SecurityException(int code, String message) {
		super(getMessage(code, message));
		this.code = code;
	}

	private static String getMessage(int code, String message) {
		String msg = message;
		if ((null == msg) || ("".equals(msg))) {
			msg = getMessage(code);
		}
		return msg;
	}
}
