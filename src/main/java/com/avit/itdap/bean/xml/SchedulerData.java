package com.avit.itdap.bean.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "SchedulerData")
public class SchedulerData {
	
	
	@XmlAttribute(name="type")
	private String type;
	
	
	@XmlElement(name="Channel")
	private List<ChannelXmlBean> channelXmlBeanList;


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<ChannelXmlBean> getChannelXmlBeanList() {
		return channelXmlBeanList;
	}


	public void setChannelXmlBeanList(List<ChannelXmlBean> channelXmlBeanList) {
		this.channelXmlBeanList = channelXmlBeanList;
	}

}
