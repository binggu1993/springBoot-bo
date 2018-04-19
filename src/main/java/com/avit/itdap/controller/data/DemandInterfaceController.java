package com.avit.itdap.controller.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.xml.CategoryInfo;
import com.avit.itdap.bean.xml.SynCategory;
import com.avit.itdap.repository.data.CategoryRepository;



@RestController
public class DemandInterfaceController {
	@Autowired
	CategoryRepository repository;
	private static Logger logger = LoggerFactory
			.getLogger(DemandInterfaceController.class);
	@RequestMapping(value = "/synCategoryInfo/{spCode}",produces={MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST )
	public void synCategoryInfo(@RequestBody SynCategory info,@PathVariable String spCode) {
		System.out.println(info.getInfos().size());
		for(CategoryInfo category:info.getInfos()){
			category.setSpCode(spCode);
		}
		repository.deleteCategoryBySpCode(spCode);
		repository.save(info.getInfos());
	}
	
	
}
