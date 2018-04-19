package com.avit.itdap.controller.system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avit.itdap.bean.system.ChannelEPG;
import com.avit.itdap.bean.system.Message;
import com.avit.itdap.bean.system.PageResult;
import com.avit.itdap.common.utils.SortUtil;
import com.avit.itdap.dto.system.ChannelEPGDto;
import com.avit.itdap.exception.ApplicationException;
import com.avit.itdap.exception.NotFoundException;
import com.avit.itdap.repository.system.ChannelEPGRepository;
import com.avit.itdap.service.ChannelEPGService;

@RestController
public class ChannelEPGController {
	private static Logger logger = LoggerFactory.getLogger(ChannelEPGController.class);
	
	@Autowired
	ChannelEPGService service;
	
	@Autowired
	ChannelEPGRepository repository;

	//分页查询
		@RequestMapping(value = "/page/channel/epgs", method = RequestMethod.GET)
	    public PageResult<ChannelEPG> searchPageChannelEPGs(
	            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
	            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
	            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
	            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
	            @RequestParam(value = "searchId", required = false) Long id,
	            @RequestParam(value = "searchEpgName", required = false) String epgName,
	            @RequestParam(value = "searchStartTime", required = false)  Date startTime,
	            @RequestParam(value = "searchEndTime", required = false)  Date endTime,
	            @RequestParam(value = "searchChannelCode", required = false) String channelCode)
	    {
			ChannelEPGDto dto=new ChannelEPGDto(pageNumber, pageSize, sort, sortOrder, id, epgName, startTime, endTime, channelCode);
		    logger.debug("ChannelDto is:{}", dto);
	        Page<ChannelEPG> result = null;
	        Sort sorts = SortUtil.buildSort(dto);
	        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), sorts);
	        result = service.findAllByConditions(dto,pageable);  
	        PageResult<ChannelEPG> pr = new PageResult<ChannelEPG>(result.getTotalElements(),result.getContent());
	        return pr;
	    }
		
		//查询所有
		@RequestMapping(value = "/allChannelEPG", method = RequestMethod.GET)
		public List<ChannelEPG> allChannelEPG()
		{		
			List<ChannelEPG> result = repository.findAll();
			return result;
		}
		
		//根据id查询
		@RequestMapping(value = "/channel/epgs/{id}", method = RequestMethod.GET)
		public ChannelEPG getChannelEPG(@PathVariable long id) throws NotFoundException{
			ChannelEPG result = null;
			result = repository.findOne(id);
			if(result == null){
				throw new NotFoundException();
			}
			return result;
		}
		
		//create操作
		@RequestMapping(value = "/channel/epgs", method = RequestMethod.POST)
		public ChannelEPG addChannelEPG(@RequestBody ChannelEPG areaObj)
		{
			logger.info("add Area:{}", areaObj);
			ChannelEPG result = null;
			result = repository.save(areaObj);
			return result;
		}
		
		//update操作
		@RequestMapping(value = "/channel/epgs/{id}", method = RequestMethod.PUT)
		public ChannelEPG updateChannelEPG(@PathVariable int id, @RequestBody ChannelEPG channelEPG)
		{
			logger.info("update ChannelEPG info:{},{}", id, channelEPG.getEpgName());
			ChannelEPG result = null;
			ChannelEPG tmp = repository.findOne(Long.valueOf(id));
			if (tmp != null)
			{
				BeanUtils.copyProperties(channelEPG, tmp, "id");
				result = repository.save(tmp);
			}
			return result;
		}
		
		
		//BatchDelete操作,批量删除
		@RequestMapping(value = "/channel/epgs/{ids}", method = RequestMethod.DELETE)
		public Message deleteChannelEPGs(@PathVariable int [] ids){
			logger.info("del channelEPG id:{}", ids);
			for (int id : ids)
			{
				repository.delete(Long.valueOf(id));
			}
			Message msg = new Message();
			msg.setCode("200");
			msg.setMessage("delete ok!");
			return msg;
		}
	
		//节目单上传
		@RequestMapping(value="/channels/epgs/upload")
		@ResponseBody
		public Message importFile(@RequestParam(value="filename") MultipartFile file)  {  	  
		    //判断文件是否为空  
			Message msg = new Message();
			msg.setCode("200");
			msg.setMessage("节目单上传成功！");
		    if(file==null){  
		    	msg.setCode("400");
		    	msg.setMessage("please select file!");
		    }  	      
		    //获取文件名  
		    String fileName=file.getOriginalFilename(); 
		    String tmpPath = "C:/tmp/";
		    String tmpFileName = "epg"+System.currentTimeMillis()+"-"+new Random().nextInt(1000);
		    //批量导入  
		    try{
		    service.importFile(fileName,file,tmpPath,tmpFileName);
		    String result = service.parseChannelEpgXml(tmpPath+tmpFileName);
		    if(!StringUtils.isEmpty(result)){
		    	msg.setMessage("节目单上传失败："+result);
		    }
		    }
		    catch(ApplicationException e)
		    {
		    	e.printStackTrace();
		    	logger.error("节目单上传失败：内部错误");
		    	msg.setCode("400");
		    	msg.setMessage(e.getMessage());
		    
		    }catch(Exception e){
		    	e.printStackTrace();
		    	logger.error("节目单上传失败：：内部错误");
		    	msg.setCode("400");
		    	msg.setMessage(e.getMessage());
		    }
		    return msg;
		} 
		
		@RequestMapping(value = "/channels/epg/templateDownload", method = RequestMethod.GET)  
	    public ResponseEntity<InputStreamResource> downloadEpgTemplate()  
	            {  
		    ByteArrayInputStream bin = null;
		    ByteArrayOutputStream bout = null;
	        try
	        {
	        	String template="<?xml version=\"1.0\" encoding=\"gb2312\" standalone=\"yes\"?><BroadcastData code=\"110000000\" creationtime=\"20170828143838\" version=\"2.0\"><ProviderInfo id=\"ChinaEPG\" name=\"David\" /><SchedulerData type=\"PROGRAM\"><Channel><ChannelText language=\"chi\"><ChannelName>CCTV2</ChannelName></ChannelText><Event eventid=\"1\" begintime=\"20170828002000\" duration=\"003400\" eventtype=\"00\"><EventText language=\"chi\"><Name>经济半小时</Name></EventText></Event><Event eventid=\"2\" begintime=\"20170828005400\" duration=\"010300\" eventtype=\"11\"><EventText language=\"chi\"><Name>秘密大改造</Name></EventText></Event><Event eventid=\"3\" begintime=\"20170828015700\" duration=\"004400\" eventtype=\"22\"><EventText language=\"chi\"><Name>经济信息联播</Name></EventText></Event></Channel></SchedulerData></BroadcastData>";
	            byte[] tpl = template.getBytes("gbk");
	            bout = new ByteArrayOutputStream();
	            bout.write(tpl);
	            bin = new ByteArrayInputStream(bout.toByteArray());
	            bout.close();
	            HttpHeaders headers = new HttpHeaders();  
	            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "epgTemplate.xml"));  
	            headers.add("Pragma", "no-cache");  
	            headers.add("Expires", "0");  
	  
	            return ResponseEntity  
	                    .ok()  
	                    .headers(headers)  
	                    .contentLength(tpl.length)  
	                    .contentType(MediaType.parseMediaType("application/octet-stream"))  
	                    .body(new InputStreamResource(bin));
	        }
	        catch (IOException e)
	        {
	            logger.error("",e);
	        }
	        return null;
	    } 
		
}
