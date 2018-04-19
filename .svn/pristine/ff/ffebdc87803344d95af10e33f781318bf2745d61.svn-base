package com.avit.itdap.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;



/**
 * @author panxincheng
 * @date 2011-7-15
 */
public class XmlHelper {
	
	private static final Logger log = LoggerFactory.getLogger(XmlHelper.class);
	
	private static XmlHelper instance = null;
	private static String packagePath = "com.avit.itdap.util.xml";
	private JAXBContext jaxbContext = null;
	private Marshaller marshaller = null;
	private Unmarshaller unmarshaller = null;

	private XmlHelper() {
		super();
	}
	
	public static synchronized XmlHelper getInstance() {
		if (instance == null) {
			instance = new XmlHelper();
			instance.prepareJaxbContext();
		}
		return instance;
	}
	
	public synchronized void prepareJaxbContext() {
		try {
			jaxbContext = JAXBContext.newInstance(packagePath);
			// Class Marshaller controls the process of marshalling i.e: Java
			// Object --> XML
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// Class UnMarshaller controls the process of unmarshalling i.e: XML
			// --> Java Object
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (Exception e) {
			log.error("prepareJaxbContext",e);
		}
	}
	
	public String toXML(Object obj) {
		String docu = null;
		StringWriter sw = null;
		
		synchronized (marshaller) {
			try {
				sw = new StringWriter();
				marshaller.marshal(obj, sw);
				docu = sw.toString();
			} catch (Exception e) {
				log.error("object to xml exception", e);
			} finally {
				if (sw != null) {
					try {
						sw.close();
					} catch (IOException e) {
						sw = null;
						log.error("", e);
					}
				}
			}
		}

		return docu;
	}
	
	public Object toObject(String xml) {
		if (xml == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		Object obj = null;
		
		synchronized (unmarshaller) {	
			try {
				bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				obj = unmarshaller.unmarshal(bais);
			}catch (Exception e) {
				log.error("xml to object exception", e);
			} finally {
				if (bais != null) {
					try {
						bais.close();
					} catch (IOException e) {
						bais = null;
					}
				}
			}
		}

		return obj;
	}
	
	public Object toObject(String xml,String charse) {
		if (xml == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		Object obj = null;
		String charset="UTF-8";
		if(!StringUtils.isEmpty(charse))
		{
			charset=charse;
		}
		synchronized (unmarshaller) {	
			try {
				
				bais = new ByteArrayInputStream(xml.getBytes(charse));
				obj = unmarshaller.unmarshal(bais);
			}catch (Exception e) {
				log.error("xml to object exception", e);
			} finally {
				if (bais != null) {
					try {
						bais.close();
					} catch (IOException e) {
						bais = null;
					}
				}
			}
		}

		return obj;
	}
}
