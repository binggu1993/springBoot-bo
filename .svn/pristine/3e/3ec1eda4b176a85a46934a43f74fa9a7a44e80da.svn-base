package com.avit.itdap.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.avit.itdap.bean.system.Menu;
import com.avit.itdap.dto.system.MenuDto;
import com.avit.itdap.repository.system.MenuRepository;

@Service
public class MenuService
{

	@Autowired
	MenuRepository menuRepository;

	/**
	 * 多条件查询
	 * 
	 * @param menuDto
	 * @return
	 */
	@Transactional
	public Page<Menu> findAllByConditions(final MenuDto menuDto, Pageable pageable)
	{

		Page<Menu> result = menuRepository.findAll(new Specification<Menu>()
		{
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> list = new ArrayList<Predicate>();

				
				if (!StringUtils.isEmpty(menuDto.getName()))
				{
					list.add(cb.like(root.get("name").as(String.class), "%" + menuDto.getName() + "%"));
				}

				if (!StringUtils.isEmpty(menuDto.getIcon()))
				{
					list.add(cb.like(root.get("icon").as(String.class), "%" + menuDto.getIcon() + "%"));
				}

				if (!StringUtils.isEmpty(menuDto.getUrl()))
				{
					list.add(cb.like(root.get("url").as(String.class), "%" + menuDto.getUrl() + "%"));
				}

				if (!StringUtils.isEmpty(menuDto.getType()))
				{
					list.add(cb.equal(root.get("type").as(String.class), menuDto.getType()));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}

}
