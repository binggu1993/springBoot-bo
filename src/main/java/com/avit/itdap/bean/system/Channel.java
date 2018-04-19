package com.avit.itdap.bean.system;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="T_Channel")
public class Channel {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="channel_name")
	private String channelName;
	@Column(name="channel_code")
	private String channelCode;
	@Column(name="service_id")
	private long serviceId;
	@Column(name="channel_type")
	private int channelType;

	@OneToMany(fetch=FetchType.EAGER,mappedBy="parentId")
	private Set<ChannelGroupRef> subChannels;
	
	
	@Transient
	private String subServiceIds;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public int getChannelType() {
		return channelType;
	}
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	public Set<ChannelGroupRef> getSubChannels() {
		return subChannels;
	}
	public void setSubChannels(Set<ChannelGroupRef> subChannels) {
		this.subChannels = subChannels;
	}
	public String getSubServiceIds() {
		return subServiceIds;
	}
	public void setSubServiceIds(String subServiceIds) {
		this.subServiceIds = subServiceIds;
	}
}
