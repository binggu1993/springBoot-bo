package com.avit.itdap.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.avit.itdap.bean.system.Area;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.dto.system.AreaDto;
import com.avit.itdap.dto.system.ChannelDto;
import com.avit.itdap.repository.system.AreaRepository;

@Service
public class AreaService {

	@Autowired
	private AreaRepository areaRepository;
	
	
	/**
	 * 多条件查询
	 * 
	 * @param 
	 * @return
	 */
	public Page<Area> findAllByConditions(final AreaDto dto, Pageable pageable)
	{
		Page<Area> result = areaRepository.findAll(new Specification<Area>()
		{
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> list = new ArrayList<Predicate>();

				if (!StringUtils.isEmpty(dto.getAreaName()))
				{
					list.add(cb.like(root.get("areaName").as(String.class), "%" + dto.getAreaName() + "%"));
				}

				if (!StringUtils.isEmpty(dto.getAreaCode()))
				{
					list.add(cb.like(root.get("areaCode").as(String.class), "%" + dto.getAreaCode() + "%"));
				}
				
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
}
