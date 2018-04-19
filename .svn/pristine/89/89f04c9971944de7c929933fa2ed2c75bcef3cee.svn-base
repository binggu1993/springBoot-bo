package com.avit.itdap.common.utils;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.avit.itdap.dto.BaseDto;

public class SortUtil
{

	public static Sort buildSort(String sort)
	{
		Sort sorts = new Sort(Sort.Direction.DESC, "id");
		if (!StringUtils.isEmpty(sort))
		{
			String[] str = sort.split(",");
			if (str.length == 2)
			{
				Sort.Direction tmp = Sort.Direction.valueOf(str[1]
						.toUpperCase());
				if (tmp != null)
				{
					sorts = new Sort(tmp, str[0]);
				}
			} else if (str.length == 1)
			{
				sorts = new Sort(Sort.Direction.ASC, str);
			}
		}
		return sorts;
	}
	
	public static Sort buildSort(BaseDto dto)
    {
	    Sort sorts = null;
        if(dto!=null&&!StringUtils.isEmpty(dto.getSort())
                &&!StringUtils.isEmpty(dto.getSortOrder())){
            sorts = buildSort(dto.getSort()+","+dto.getSortOrder());
        }else{
            sorts = new Sort(Sort.Direction.DESC, "id");
        }
        return sorts;
    }
}
