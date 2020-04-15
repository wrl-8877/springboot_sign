package com.example.demo.open.model;

public class ResponseData {
	private ResponseHeader responseHeader;
	private Object responseBody;

	public ResponseHeader getResponseHeader() {
		return this.responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public Object getResponseBody() {
		return this.responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

	public static ResponseData fail(String code, String message) {
		ResponseData responseData = new ResponseData();
		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setResultCode(code);
		responseHeader.setResultMessage(message);
		responseData.setResponseHeader(responseHeader);
		return responseData;
	}
}
