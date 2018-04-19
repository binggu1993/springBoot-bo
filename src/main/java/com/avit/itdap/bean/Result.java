package com.avit.itdap.bean;

public class Result {
	
	private Integer code;
	private Object result;
	private String message;
	
	public Result()
	{
		this.code=200;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
