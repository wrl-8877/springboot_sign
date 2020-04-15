package com.example.demo.open.model;

import java.io.Serializable;

public class ActionResult<T> implements Serializable {
	private static final long serialVersionUID = 3933901259766164485L;
	public static final String SUCCESS_CODE = "0";
	private String code;
	private T data;
	private boolean isSuccess = true;
	private String message;

	public ActionResult() {
	}

	public ActionResult(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public ActionResult(boolean isSuccess, String message, String code, T retValue) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.code = code;
		this.data = retValue;
	}

	public ActionResult(String errCode, String message, T retValue) {
		this.isSuccess = false;
		this.code = errCode;
		this.message = message;
		this.data = retValue;
	}

	public ActionResult(boolean isSuccess, String message) {
		this.isSuccess = isSuccess;
		this.message = message;
	}

	public ActionResult(boolean isSuccess, T retValue) {
		this.data = retValue;
		this.isSuccess = isSuccess;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return this.isSuccess;
	}

	public boolean hasError() {
		return !this.isSuccess;
	}

	public void setSuccess(boolean success) {
		this.isSuccess = success;
	}

	public static <T> ActionResult<T> getSuccessResult(String code, String message, T retValue) {
		return new ActionResult(true, message, code, retValue);
	}

	public static <T> ActionResult<T> getSuccessResult(String message, T retValue) {
		return getSuccessResult("0", message, retValue);
	}

	public static <T> ActionResult<T> getSuccessResult(T retValue) {
		return getSuccessResult("0", "成功", retValue);
	}

	public static <T> ActionResult<T> getErrorResult(String code, String message) {
		return new ActionResult(false, message, code, null);
	}
}
