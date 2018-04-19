package com.avit.itdap.dto;


 public class BaseDto
{
	
	private Integer currentPage;
	
	private Integer pageSize;
	
	private String sortDesc;
	
	private String sort;
	
	private String sortOrder;

	public Integer getCurrentPage()
	{
		return null==currentPage?1:currentPage;
	}

	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public Integer getPageSize()
	{
		return null==pageSize?10:pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getSortDesc()
	{
		return null==sortDesc?"id,asc":sortDesc;
	}

	public void setSortDesc(String sortDesc)
	{
		this.sortDesc = sortDesc;
	}

	public BaseDto(Integer currentPage, Integer pageSize, String sortDesc)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.sortDesc = sortDesc;
	}

    public BaseDto(Integer currentPage, Integer pageSize, String sort, String sortOrder)
    {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
        this.sortOrder = sortOrder;
    }
}
