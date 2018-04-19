package com.avit.itdap.dto.system;

import java.util.Date;

import com.avit.itdap.dto.BaseDto;

public class ChannelEPGDto  extends BaseDto{

	
	private Long id;
	
	private String epgName;
	
	private Date startTime;
	
	private Date endTime;
	
	private String channelCode;
	
	public ChannelEPGDto(Integer currentPage, Integer pageSize, String sortDesc)
	{
		super(currentPage, pageSize, sortDesc);
	}
	
    public ChannelEPGDto(Integer currentPage, Integer pageSize,String sort,String sortOrder,
            Long id, String epgName, Date startTime,Date endTime,String channelCode)
    {
        super(currentPage, pageSize, sort, sortOrder);
        this.id = id;
        this.epgName = epgName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.channelCode = channelCode;
   
 
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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



	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
    
}
