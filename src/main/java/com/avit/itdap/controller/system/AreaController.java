package com.avit.itdap.controller.system;

/**
 * autor binggu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.avit.itdap.bean.system.Area;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.bean.system.ChannelGroupRef;
import com.avit.itdap.bean.system.Message;
import com.avit.itdap.bean.system.PageResult;
import com.avit.itdap.common.utils.SortUtil;
import com.avit.itdap.dto.system.AreaDto;
import com.avit.itdap.exception.BadRequestException;
import com.avit.itdap.exception.NotFoundException;
import com.avit.itdap.repository.system.AreaRepository;
import com.avit.itdap.service.AreaService;

@RestController
public class AreaController {

	private static Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	AreaRepository repository;
	
	@Autowired
	AreaService areaService;
	
	//分页查询
	@RequestMapping(value = "/page/areas", method = RequestMethod.GET)
    public PageResult<Area> searchPageAreas(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
            @RequestParam(value = "searchId", required = false) Long id,
            @RequestParam(value = "searchAreaName", required = false) String areaName,
            @RequestParam(value = "searchAreaCode", required = false) String areaCode)
    {
        AreaDto dto=new AreaDto(pageNumber, pageSize, sort, sortOrder, id, areaName, areaCode);
	    logger.debug("ChannelDto is:{}", dto);
        Page<Area> result = null;
        Sort sorts = SortUtil.buildSort(dto);
        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), sorts);
        result = areaService.findAllByConditions(dto,pageable);  
        PageResult<Area> pr = new PageResult<Area>(result.getTotalElements(),result.getContent());
        return pr;
    }
	
	//查询所有
	@RequestMapping(value = "/allAreas", method = RequestMethod.GET)
	public List<Area> allAreas()
	{		
		
		List<Area> list=repository.findAll();
		List<Area> areaList=new ArrayList<Area>();
		Area area=new Area();
		area.setAreaCode("0");
		area.setAreaName("所有区域");
		areaList.add(area);
		if(list!=null&&list.size()>0)
		{
			areaList.addAll(list);
		}
		return areaList;
		
	}
	
	//根据id查询
	@RequestMapping(value = "/areas/{id}", method = RequestMethod.GET)
	public Area getarea(@PathVariable long id) throws NotFoundException{
		Area result = null;
		result = repository.findOne(id);
		if(result == null){
			throw new NotFoundException();
		}
		return result;
	}
	
	//create操作
	@RequestMapping(value = "/areas", method = RequestMethod.POST)
	public Area addArea(@RequestBody Area areaObj)
	{
		logger.info("add Area:{}", areaObj);
		Area result = null;
		result = repository.save(areaObj);
		return result;
	}
	
	//update操作
	@RequestMapping(value = "/areas/{id}", method = RequestMethod.POST)
	public Area updateArea(@PathVariable long id,@RequestBody Area obj) throws NotFoundException, BadRequestException{
		logger.info("update Area info:{},{}", id, obj.getAreaName());
		Area result = null;
		Area tmp = repository.findOne(Long.valueOf(id));
		if (tmp != null)
		{
			BeanUtils.copyProperties(obj, tmp, "id");
			result = repository.save(tmp);
		}
		return result;
	}
	
	//BatchDelete操作,批量删除
	@RequestMapping(value = "/areas/{ids}", method = RequestMethod.DELETE)
	public Message deleteAreas(@PathVariable int [] ids){
		logger.info("del Area id:{}", ids);
		for (int id : ids)
		{
			repository.delete(Long.valueOf(id));
		}
		Message msg = new Message();
		msg.setCode("200");
		msg.setMessage("delete ok!");
		return msg;
	}
}
