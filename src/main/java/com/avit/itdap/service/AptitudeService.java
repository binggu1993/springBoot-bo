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

import com.avit.itdap.bean.system.Aptitude;
import com.avit.itdap.dto.system.AptitudeDto;
import com.avit.itdap.dto.system.AreaDto;
import com.avit.itdap.repository.system.AptitudeRepository;

@Service
public class AptitudeService {

	@Autowired
	private AptitudeRepository repository;
	
	
	/**
	 * 多条件查询
	 * 
	 * @param 
	 * @return
	 */
	public Page<Aptitude> findAllByConditions(final AptitudeDto dto, Pageable pageable)
	{
		Page<Aptitude> result = repository.findAll(new Specification<Aptitude>()
		{
			@Override
			public Predicate toPredicate(Root<Aptitude> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> list = new ArrayList<Predicate>();

				if (!StringUtils.isEmpty(dto.getAptitudeName()))
				{
					list.add(cb.like(root.get("aptitudeName").as(String.class), "%" + dto.getAptitudeName() + "%"));
				}

				if (!StringUtils.isEmpty(dto.getAptitudeCode()))
				{
					list.add(cb.like(root.get("aptitudeCode").as(String.class), "%" + dto.getAptitudeCode() + "%"));
				}
				
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
}
