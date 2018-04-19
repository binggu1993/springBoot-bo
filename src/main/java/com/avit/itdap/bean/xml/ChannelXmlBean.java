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
@XmlRootElement(name = "Channel")
public class ChannelXmlBean {
	
	
	@XmlElement(name="ChannelText")
	private ChannelText channelText;
	
	@XmlElement(name="Event")
	private List<Event> evenList;

	public ChannelText getChannelText() {
		return channelText;
	}

	public void setChannelText(ChannelText channelText) {
		this.channelText = channelText;
	}

	public List<Event> getEvenList() {
		return evenList;
	}

	public void setEvenList(List<Event> evenList) {
		this.evenList = evenList;
	}
}
