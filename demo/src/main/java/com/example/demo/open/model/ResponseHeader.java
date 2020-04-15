package com.example.demo.open.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseHeader {
	private String appKey;
	private String appMessageId;
	private String nonce;
	private String resultCode;
	private String resultMessage;
	private String signature;
	private Long timestamp;

	public ResponseHeader() {
	}

	public ResponseHeader(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppMessageId() {
		return this.appMessageId;
	}

	public void setAppMessageId(String appMessageId) {
		this.appMessageId = appMessageId;
	}

	public String getNonce() {
		return this.nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@JSONField(serialize = false)
	public String getSignString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append(this.appKey).append(",").append(this.appMessageId).append(",").append(this.nonce).append(",")
				.append(this.resultCode).append(",").append(this.resultMessage).append(",").append(this.timestamp);
		return builder.toString();
	}
}
