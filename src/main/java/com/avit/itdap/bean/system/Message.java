package com.avit.itdap.bean.system;

import java.io.Serializable;

public class Message implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -8588225595853196436L;
    public String code;
	public String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
