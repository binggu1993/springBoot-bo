package com.avit.itdap.bean.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Event")
public class Event {
	
	@XmlAttribute(name="eventid")
	private String eventid;
	
	@XmlAttribute(name="begintime")
	private String begintime;
	
	@XmlAttribute(name="duration")
	private String duration;
	
	@XmlAttribute(name="eventtype")
	private String eventtype;
	
	@XmlElement(name="EventText")
	private EventText eventText;

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

	public EventText getEventText() {
		return eventText;
	}

	public void setEventText(EventText eventText) {
		this.eventText = eventText;
	}
}
