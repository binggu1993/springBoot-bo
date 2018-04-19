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
/**
 * autor binggu
 */
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.system.Aptitude;
import com.avit.itdap.bean.system.Message;
import com.avit.itdap.bean.system.PageResult;
import com.avit.itdap.common.utils.SortUtil;
import com.avit.itdap.dto.system.AptitudeDto;
import com.avit.itdap.exception.NotFoundException;
import com.avit.itdap.repository.system.AptitudeRepository;
import com.avit.itdap.service.AptitudeService;

@RestController
public class AptitudeController {
	private static Logger logger = LoggerFactory.getLogger(AptitudeController.class);
	

	@Autowired
	AptitudeRepository repository;
	
	@Autowired
	AptitudeService service;
	
	//分页查询
	@RequestMapping(value = "/page/aptitudes", method = RequestMethod.GET)
    public PageResult<Aptitude> searchPageAptitudes(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
            @RequestParam(value = "searchId", required = false) Long id,
            @RequestParam(value = "searchAptitudeName", required = false) String AptitudeName,
            @RequestParam(value = "searchAptitudeCode", required = false) String AptitudeCode)
    {
        AptitudeDto dto=new AptitudeDto(pageNumber, pageSize, sort, sortOrder, id, AptitudeName, AptitudeCode);
	    logger.debug("ChannelDto is:{}", dto);
        Page<Aptitude> result = null;
        Sort sorts = SortUtil.buildSort(dto);
        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), sorts);
        result = service.findAllByConditions(dto,pageable);  
        PageResult<Aptitude> pr = new PageResult<Aptitude>(result.getTotalElements(),result.getContent());
        return pr;
    }
	
	//查询所有
	@RequestMapping(value = "/allAptitudes", method = RequestMethod.GET)
	public List<Aptitude> allAptitude()
	{		
		List<Aptitude> result = repository.findAll();
		return result;
	}
	
	//根据id查询
	@RequestMapping(value = "/aptitudes/{id}", method = RequestMethod.GET)
	public Aptitude getAptitude(@PathVariable long id) throws NotFoundException{
		Aptitude result = null;
		result = repository.findOne(id);
		if(result == null){
			throw new NotFoundException();
		}
		return result;
	}
	
	//create操作
	@RequestMapping(value = "/aptitudes", method = RequestMethod.POST)
	public Aptitude addAptitude(@RequestBody Aptitude AptitudeObj)
	{
		logger.info("add Aptitude:{}", AptitudeObj);
		Aptitude result = null;
		result = repository.save(AptitudeObj);
		return result;
	}
	
	//update操作
	@RequestMapping(value = "/aptitudes/{id}", method = RequestMethod.PUT)
	public Aptitude updateAptitude(@PathVariable int id, @RequestBody Aptitude aptitude)
	{
		logger.info("update Aptitude info:{},{}", id, aptitude.getName());
		Aptitude result = null;
		Aptitude tmp = repository.findOne(Long.valueOf(id));
		if (tmp != null)
		{
			BeanUtils.copyProperties(aptitude, tmp, "id");
			result = repository.save(tmp);
		}
		return result;
	}
	
	
	//BatchDelete操作,批量删除
	@RequestMapping(value = "/aptitudes/{ids}", method = RequestMethod.DELETE)
	public Message deleteAptitudes(@PathVariable int [] ids){
		logger.info("del Aptitude id:{}", ids);
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
