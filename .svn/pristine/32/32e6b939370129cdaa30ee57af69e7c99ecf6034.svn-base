package com.avit.itdap.exception;

/**
 * 抛出运行时异常，用于spring拦截处理事物
 * @author Administrator
 *
 */
public class ApplicationException extends RuntimeException {

	/**
     * 
     */
	private static final long serialVersionUID = 6771252119201382547L;
	
	private Throwable e;

	/**
	 * 错误码
	 */
	private int code;

	/**
	 * 错误消息
	 */
	private String codeMessage;

	public ApplicationException() {
	}


	public ApplicationException(String message) {
		super(message);
		this.codeMessage = message;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		this.codeMessage = message;
	}

	public ApplicationException(int code, String codeMessage) {
		super(codeMessage);
		this.code = code;
		this.codeMessage = codeMessage;
	}

	public ApplicationException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public ApplicationException(int code, String codeMessage, Throwable cause) {
		super(codeMessage, cause);
		this.code = code;
		this.codeMessage = codeMessage;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeMessage() {
		return this.codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	public Throwable getE() {
		return e;
	}

	public void setE(Throwable e) {
		this.e = e;
	}
	
	
}
