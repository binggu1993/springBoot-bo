package com.avit.itdap.dto.system;

import com.avit.itdap.dto.BaseDto;

public class ChannelDto extends BaseDto
{
    
    private Long id;
    
    private String channelName;
    private String channelCode;
    private Long serviceId;
    private Integer channelType;
    
    public ChannelDto(Integer currentPage, Integer pageSize, String sortDesc)
    {
        super(currentPage, pageSize, sortDesc);
    }

    public ChannelDto(Integer currentPage, Integer pageSize,String sort,String sortOrder,
            Long id, String channelName, String channelCode, 
            Long serviceId, Integer channelType)
    {
        super(currentPage, pageSize, sort, sortOrder);
        this.id = id;
        this.channelName = channelName;
        this.channelCode = channelCode;
        this.serviceId = serviceId;
        this.channelType = channelType;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }

    public String getChannelCode()
    {
        return channelCode;
    }

    public void setChannelCode(String channelCode)
    {
        this.channelCode = channelCode;
    }

    public Long getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(Long serviceId)
    {
        this.serviceId = serviceId;
    }

    public Integer getChannelType()
    {
        return channelType;
    }

    public void setChannelType(Integer channelType)
    {
        this.channelType = channelType;
    }
    
}
