package com.avit.itdap.bean.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_System")
public class SystemBean {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="last_live_time")
	private long lastLiveTime;
	@Column(name="live_interval")
	private long liveInterval;
	@Column(name="stb_sum")
	private long stbSum;
	@Column(name="last_playback_time")
	private Date lastPlaybackTime;	
	public Date getLastPlaybackTime() {
		return lastPlaybackTime;
	}
	public void setLastPlaybackTime(Date lastPlaybackTime) {
		this.lastPlaybackTime = lastPlaybackTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLastLiveTime() {
		return lastLiveTime;
	}
	public void setLastLiveTime(long lastLiveTime) {
		this.lastLiveTime = lastLiveTime;
	}
	public long getLiveInterval() {
		return liveInterval;
	}
	public void setLiveInterval(long liveInterval) {
		this.liveInterval = liveInterval;
	}
	public long getStbSum() {
		return stbSum;
	}
	public void setStbSum(long stbSum) {
		this.stbSum = stbSum;
	}
	
	
}
