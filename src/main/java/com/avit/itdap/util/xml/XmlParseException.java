package com.avit.itdap.util.xml;

/**
 * 
 * @author panxincheng
 * @date 2011-7-20 
 */
public class XmlParseException extends Exception {

	private static final long serialVersionUID = 4907927045508345966L;

	/**
	 * 
	 */
	public XmlParseException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public XmlParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public XmlParseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public XmlParseException(Throwable cause) {
		super(cause);
	}

}
