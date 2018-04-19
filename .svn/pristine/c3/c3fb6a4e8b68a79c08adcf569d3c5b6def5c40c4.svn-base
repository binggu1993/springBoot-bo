package com.avit.itdap.bean.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_CHANNEL_EPG")
public class ChannelEPG
{
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="epg_name")
	private String epgName;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="duration")
	private Long duration;
	
	@Column(name="remark")
	private String remark;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id")
	 private Channel channel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getEpgName() {
		return epgName;
	}

	public void setEpgName(String epgName) {
		this.epgName = epgName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
