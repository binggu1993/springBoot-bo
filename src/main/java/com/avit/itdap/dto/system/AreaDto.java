package com.avit.itdap.dto.system;

import com.avit.itdap.dto.BaseDto;

public class AreaDto extends BaseDto {
	
	private Long id;
	private String areaName;
	private String areaCode;
	public AreaDto(Integer currentPage, Integer pageSize, String sortDesc)
	{
		super(currentPage, pageSize, sortDesc);
	}
	

    public AreaDto(Integer currentPage, Integer pageSize,String sort,String sortOrder,
            Long id, String areaName, String areaCode)
    {
        super(currentPage, pageSize, sort, sortOrder);
        this.id = id;
        this.areaName = areaName;
        this.areaCode = areaCode;
    }
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
