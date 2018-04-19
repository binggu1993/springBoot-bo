package com.avit.itdap.bean.live;

public class ChannelEpg {
	private long epgid;
	private String epgName;	
	private long count;
	private long userCount;
	private long duration;
	private long agvDuration;
	public long getEpgid() {
		return epgid;
	}
	public void setEpgid(long epgid) {
		this.epgid = epgid;
	}
	public String getEpgName() {
		return epgName;
	}
	public void setEpgName(String epgName) {
		this.epgName = epgName;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getUserCount() {
		return userCount;
	}
	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getAgvDuration() {
		return agvDuration;
	}
	public void setAgvDuration(long agvDuration) {
		this.agvDuration = agvDuration;
	}
	
	
}
