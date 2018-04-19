package com.avit.itdap.bean.report;

public class CategoryReport {
	private String nodeId;
	private String nodeName;
	private long totalDuration;
	private long useDuration;
	private long useCount;
	private long useUser;
	private String parentId;
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public long getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}
	public long getUseDuration() {
		return useDuration;
	}
	public void setUseDuration(long useDuration) {
		this.useDuration = useDuration;
	}
	public long getUseCount() {
		return useCount;
	}
	public void setUseCount(long useCount) {
		this.useCount = useCount;
	}
	public long getUseUser() {
		return useUser;
	}
	public void setUseUser(long useUser) {
		this.useUser = useUser;
	}
	
	
}
