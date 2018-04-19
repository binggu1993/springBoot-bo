package com.avit.itdap.bean.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "T_ITDAP_MENU")
public class Menu
{
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "pid")
	private long pid;

	@NotNull
	@Column(name = "url")
	private String url;

	@NotNull
	@Column(name = "icon")
	private String icon;

	@NotNull
	@Column(name = "sort")
	private String sort;

	
	@NotNull
	@Column(name = "type")
	private String type;
	
	
	@NotNull
	@Column(name = "show_flag")
	private String showFlag;
	
	
	public String getShowFlag()
	{
		return showFlag;
	}

	public void setShowFlag(String showFlag)
	{
		this.showFlag = showFlag;
	}

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

}
