package com.avit.itdap.bean.system;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="T_Channel_Group_ref")
public class ChannelGroupRef {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="parent_id")
	private long parentId;
	@OneToOne
	@JoinColumn(name="sub_id", referencedColumnName="id")
	private Channel sub;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParent() {
		return parentId;
	}
	public void setParent(long parentId) {
		this.parentId = parentId;
	}
	public Channel getSub() {
		return sub;
	}
	public void setSub(Channel sub) {
		this.sub = sub;
	}
	
	
	
}
