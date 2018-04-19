package com.avit.itdap.bean.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "BroadcastData")
public class BroadcastData {
	
	@XmlAttribute(name="code")
	private String code;
	
	@XmlAttribute(name="creationtime")
	private String creationtime;
	
	@XmlAttribute(name="version")
	private String version;
	
	@XmlElement(name="ProviderInfo")
	private ProviderInfo info;
	
	@XmlElement(name="SchedulerData")
	private SchedulerData schedulerData;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ProviderInfo getInfo() {
		return info;
	}

	public void setInfo(ProviderInfo info) {
		this.info = info;
	}

	public SchedulerData getSchedulerData() {
		return schedulerData;
	}

	public void setSchedulerData(SchedulerData schedulerData) {
		this.schedulerData = schedulerData;
	}

}
