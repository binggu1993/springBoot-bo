package com.avit.itdap.dto.system;

import com.avit.itdap.dto.BaseDto;

public class AptitudeDto extends BaseDto{


	
	private Long id;
	private String aptitudeName;
	private String aptitudeCode;
	public AptitudeDto(Integer currentPage, Integer pageSize, String sortDesc)
	{
		super(currentPage, pageSize, sortDesc);
	}
	

    public AptitudeDto(Integer currentPage, Integer pageSize,String sort,String sortOrder,
            Long id, String aptitudeName, String aptitudeCode)
    {
        super(currentPage, pageSize, sort, sortOrder);
        this.id = id;
        this.aptitudeName = aptitudeName;
        this.aptitudeCode = aptitudeCode;
    }
	public String getAptitudeName() {
		return aptitudeName;
	}
	public void setAptitudeName(String aptitudeName) {
		this.aptitudeName = aptitudeName;
	}
	public String getAptitudeCode() {
		return aptitudeCode;
	}
	public void setAptitudeCode(String aptitudeCode) {
		this.aptitudeCode = aptitudeCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	
}
