package com.avit.itdap.dto.system;

import com.avit.itdap.dto.BaseDto;


 public class MenuDto extends BaseDto
{
	
	private long id;

	private String name;

	private long pid;

	private String url;

	private String icon;

	private String sort;

	
	private String type;
	
	
	
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getPid()
	{
		return pid;
	}

	public void setPid(long pid)
	{
		this.pid = pid;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public MenuDto(String name, String url, String icon, String type,Integer currentPage, Integer pageSize, String sortDesc)
	{
		super( currentPage,  pageSize,  sortDesc);
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.type = type;
	}




	

}
