package com.example.demo.open.model;


import com.example.demo.open.util.SHA;
import com.example.demo.open.exception.SecurityException;

public class RequestData {
	private RequestHeader requestHeader;
	private Object requestBody;

	public RequestHeader getRequestHeader() {
		return this.requestHeader;
	}

	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public Object getRequestBody() {
		return this.requestBody;
	}

	public void setRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}

	public Boolean validRequestRight(String signKey) throws  SecurityException {
		String signString = getRequestHeader().getSignString() + "," + getRequestBody() + "," + signKey;
		String signature = SHA.genSign256(signString);
		if (signature.equals(getRequestHeader().getSignature())) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}
}
