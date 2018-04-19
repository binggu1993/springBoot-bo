package com.avit.itdap.controller.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.system.Menu;
import com.avit.itdap.bean.system.Message;
import com.avit.itdap.bean.system.PageResult;
import com.avit.itdap.common.utils.SortUtil;
import com.avit.itdap.dto.system.MenuDto;
import com.avit.itdap.repository.system.MenuRepository;
import com.avit.itdap.service.MenuService;

@RestController
public class MenuController
{
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuService menuService;
	
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);

	@RequestMapping(value = "/menus", method = RequestMethod.GET)
	public List<Menu> searchMenus()
	{
		// log.info("pid is:{}}", pid);
		// List<Menu> result = menuRepository.findByPidOrderBySortAsc(pid);
		Sort sort = SortUtil.buildSort("sort,asc");
		List<Menu> result = menuRepository.findAll(sort);
		return result;
	}
	
	@RequestMapping(value = "/searchMenusByPid", method = RequestMethod.GET)
	public List<Menu> searchMenusByPid(@RequestParam(value = "pid", defaultValue = "-1", required = false) Integer pid)
	{
		logger.info("pid is:{}}", pid);
		List<Menu> result = menuRepository.findByPidOrderBySortAsc(pid);
		//Sort sort = SortUtil.buildSort("sort,asc");
		//List<Menu> result = menuRepository.findAll(sort);
		return result;
	}
	

	@RequestMapping(value = "/menusPage", method = RequestMethod.GET)
	public Page<Menu> searchMenusPages(
		    @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortDesc", defaultValue = "id,asc", required = false) String sortDesc,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "icon", required = false) String icon,
			@RequestParam(value = "type", required = false) String type)
	{
		MenuDto  menuDto = new MenuDto(name, url, icon, type, currentPage, pageSize, sortDesc);
		logger.info("menuDto is:{}", menuDto);
		Page<Menu> result = null;
		Sort sorts = SortUtil.buildSort(menuDto.getSortDesc());
		Pageable pageable = new PageRequest(menuDto.getCurrentPage() - 1, menuDto.getPageSize(), sorts);

			
			result = menuService.findAllByConditions(menuDto,pageable);  
		return result;
	}
	
	@RequestMapping(value = "/menusPage2", method = RequestMethod.GET)
	public PageResult<Menu> searchMenusPages2(
		    @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
			@RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "icon", required = false) String icon,
			@RequestParam(value = "type", required = false) String type)
	{
		MenuDto  menuDto = new MenuDto(name, url, icon, type, pageNumber, pageSize, "");
		logger.info("menuDto is:{}", menuDto);
		Page<Menu> result = null;
		Sort sorts = SortUtil.buildSort(sort+","+sortOrder);
		Pageable pageable = new PageRequest(menuDto.getCurrentPage() - 1, menuDto.getPageSize(), sorts);
		result = menuService.findAllByConditions(menuDto,pageable);  
		PageResult<Menu> pr = new PageResult<Menu>(result.getTotalElements(),result.getContent());
		return pr;
	}
	
	@RequestMapping(value = "/menus", method = RequestMethod.POST)
	public Menu addMenu(@RequestBody Menu menuObj)
	{
		logger.info("add Menu:{}", menuObj);
		Menu result = null;
		result = menuRepository.save(menuObj);
		return result;
	}

	@RequestMapping(value = "/menus/{id}")
	public Menu getMenu(@PathVariable int id)
	{
		logger.info("get Menu info:{}", id);
		Menu result = null;
		result = menuRepository.findOne(Long.valueOf(id));
		return result;
	}

	@RequestMapping(value = "/menus/{id}", method = RequestMethod.PUT)
	public Menu updateMenu(@PathVariable int id, @RequestBody Menu Menu)
	{
		logger.info("update Menu info:{},{}", id, Menu.getName());
		Menu result = null;
		Menu tmp = menuRepository.findOne(Long.valueOf(id));
		if (tmp != null)
		{
			BeanUtils.copyProperties(Menu, tmp, "id");
			result = menuRepository.save(tmp);
		}
		return result;
	}

	@RequestMapping(value = "/menus/{ids}", method = RequestMethod.DELETE)
	public Message delMenus(@PathVariable int[] ids)
	{
		logger.info("del Menu id:{}", ids);
		for (int id : ids)
		{
			menuRepository.delete(Long.valueOf(id));
		}
		Message msg = new Message();
		msg.setCode("200");
		msg.setMessage("delete ok!");
		return msg;
	}
	
	
	
	
}
