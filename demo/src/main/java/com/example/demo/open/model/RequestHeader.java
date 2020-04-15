package com.example.demo.open.model;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestHeader {
	private String appKey;
	private String appMessageId;
	private String nonce;
	private String signature;
	private long timestamp;
	private String version;

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

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@JSONField(serialize = false)
	public String getSignString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append(",").append(this.appKey).append(",").append(this.nonce).append(",").append(this.appMessageId);
		return builder.toString();
	}

	private boolean isEmpty(String data) {
		if ((null == data) || (data.length() == 0)) {
			return true;
		}
		return false;
	}
}
