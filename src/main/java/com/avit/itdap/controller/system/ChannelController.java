package com.avit.itdap.controller.system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Set;



//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.bean.system.ChannelGroupRef;
import com.avit.itdap.bean.system.Message;
import com.avit.itdap.bean.system.PageResult;
import com.avit.itdap.common.utils.SortUtil;
import com.avit.itdap.dto.system.ChannelDto;
import com.avit.itdap.exception.BadRequestException;
import com.avit.itdap.exception.NotFoundException;
import com.avit.itdap.repository.system.ChannelGroupRefRepository;
import com.avit.itdap.repository.system.ChannelRepository;
import com.avit.itdap.service.ChannelService;

@RestController
public class ChannelController {
    
    private static Logger logger = LoggerFactory.getLogger(ChannelController.class);
	@Autowired
	ChannelRepository repository;
	@Autowired
	ChannelGroupRefRepository groupRepository;
	
	@Autowired
	ChannelService channelService;
	
	@RequestMapping(value = "/page/channels", method = RequestMethod.GET)
    public PageResult<Channel> searchPageChannel(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "-1", required = false) Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
            @RequestParam(value = "searchId", required = false) Long id,
            @RequestParam(value = "searchChannelName", required = false) String channelName,
            @RequestParam(value = "searchChannelCode", required = false) String channelCode,
            @RequestParam(value = "searchServiceId", required = false) Long serviceId,
            @RequestParam(value = "searchChannelType",defaultValue = "1",required = false) Integer channelType)
    {
	    ChannelDto dto = new ChannelDto(pageNumber, pageSize, sort, sortOrder, id, channelName, channelCode, serviceId, channelType);
        logger.debug("ChannelDto is:{}", dto);
        Page<Channel> result = null;
        Sort sorts = SortUtil.buildSort(dto);
//        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), sorts);
        result = channelService.findAllByConditions(dto,null);  
        PageResult<Channel> pr = new PageResult<Channel>(result.getTotalElements(),result.getContent());
        return pr;
    }
	
	@RequestMapping(value = "/channels", method = RequestMethod.GET)
	public Page<Channel> searchChannel(
		    @RequestParam(value = "page", defaultValue = "1", required = false) Integer currentPage,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sort", defaultValue = "id,asc", required = false) String sortDesc,
			@RequestParam(value = "name", required = false) String name)
	{
		Sort sorts = SortUtil.buildSort(sortDesc);
		Pageable pageable = new PageRequest(currentPage- 1, pageSize, sorts);
		Page<Channel> result = null;
		//if(StringUtils.isNotEmpty(name))
		//	result =  repository.findByChannelName(name, pageable);
		//else
			result = repository.findAll(pageable);
		return result;
	}
	@RequestMapping(value = "/channels", method = RequestMethod.POST)
	public Channel addChannel(@RequestBody Channel obj) throws BadRequestException{
		Channel result = null;
		if(obj!=null){
			//if(StringUtils.isEmpty(obj.getChannelName())){
			//	throw new BadRequestException();
			//}
			if(obj.getChannelType()!=1&&obj.getChannelType()!=2){
				throw new BadRequestException();
			}
			if(obj.getChannelType()==1&&(obj.getServiceId()<=0||obj.getServiceId()>=65535)){
				throw new BadRequestException();
			}
			/*if(obj.getChannelType()==2&&(obj.getSubChannels()==null||obj.getSubChannels().size()==0)){
				throw new BadRequestException();
			}*/
			Set<ChannelGroupRef> tmp = obj.getSubChannels();
			result = repository.save(obj);
			if(obj.getChannelType()==2){
				for(ChannelGroupRef ref:tmp){
					ref.setParent(result.getId());					
				}
				groupRepository.save(tmp);
			}
		}
		return result;
	}
	@RequestMapping(value = "/channels/{id}", method = RequestMethod.GET)
	public Channel getChannel(@PathVariable long id) throws NotFoundException{
		Channel result = null;
		result = repository.findOne(id);
		if(result == null){
			throw new NotFoundException();
		}
		return result;
	}
	
	@RequestMapping(value = "/channels/{id}", method = RequestMethod.POST)
	public Channel updateChannel(@PathVariable long id,@RequestBody Channel obj) throws NotFoundException, BadRequestException{
		Channel result = null;
		result = repository.findOne(id);
		if(result == null){
			throw new NotFoundException();
		}
		
		if(obj!=null){
			obj.setId(id);
			/*if(obj.getChannelType()==2&&(obj.getSubChannels()==null||obj.getSubChannels().size()==0)){
				throw new BadRequestException();
			}*/
			Set<ChannelGroupRef> tmp = obj.getSubChannels();
			result = repository.save(obj);
			groupRepository.deleteByParentId(id);
			if(obj.getChannelType()==2){
				for(ChannelGroupRef ref:tmp){
					ref.setParent(result.getId());
				}
				groupRepository.save(tmp);
			}
		}
		result = repository.findOne(id);
		return result;
	}
	
	@RequestMapping(value = "/channels/{ids}", method = RequestMethod.DELETE)
	public Message deleteChannel(@PathVariable long[] ids){
		for(long id:ids){
			repository.delete(id);
			groupRepository.deleteByParentId(id);
			groupRepository.deleteBySubId(id);
		}
		Message msg = new Message();
		msg.setCode("200");
		msg.setMessage("delete ok!");
		return msg;
	}
	
	@RequestMapping(value="/channels/upload")
	@ResponseBody
	public Message importFile(@RequestParam(value="filename") MultipartFile file)  {  	  
	    //判断文件是否为空  
		Message msg = new Message();
		msg.setCode("200");
		msg.setMessage("upload file success");
	    if(file==null){  
	    	msg.setCode("400");
	    	msg.setMessage("please select file!");
	    }  	      
	    //获取文件名  
	    String fileName=file.getOriginalFilename(); 
	    String tmpPath = "C:/tmp/";
	    String tmpFileName = System.currentTimeMillis()+"-"+new Random().nextInt(1000);
	    //批量导入  
	    try{
	    channelService.importFile(fileName,file,tmpPath,tmpFileName);
	    String result = channelService.pareseChannel(tmpPath+tmpFileName);
	    if(!StringUtils.isEmpty(result)){
	        msg.setCode("300");
	    	msg.setMessage("Line"+result+" Error,Not Input!");
	    }
	    }catch(Exception e){
	    	msg.setCode("400");
	    	msg.setMessage(e.getMessage());
	    }
	    return msg;
	} 
	
	@RequestMapping(value = "/channelstpl/download", method = RequestMethod.GET)  
    public ResponseEntity<InputStreamResource> downloadFile()  
            {  
	    ByteArrayInputStream bin = null;
	    ByteArrayOutputStream bout = null;
        try
        {
            byte[] tpl = "频道code,serviceid,频道名称\r\nHNWS,100,湖南卫视".getBytes("utf-8");
            bout = new ByteArrayOutputStream();
            bout.write(tpl);
            bin = new ByteArrayInputStream(bout.toByteArray());
            bout.close();
            //String filePath = "target/classes/documents/channelstpl.txt"; 
            /*file = new FileSystemResource(filePath); 
            infile = file.getInputStream();*/
            HttpHeaders headers = new HttpHeaders();  
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "channelstpl.txt"));  
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
	
	
	@RequestMapping(value = "/channel/allChannel", method = RequestMethod.GET)
	public List<Channel>  allChannel()
	{		
		return channelService.getChannelsByType(1);
	}
	@RequestMapping(value = "/channelGroup/{id}/channels", method = RequestMethod.GET)
    public List<Channel>  getChannelsByGroupId(@PathVariable long id)
    {      
        return channelService.getChannelsByGroupId(id);
    }
	
	@RequestMapping(value = "/channel/allChannelGroup", method = RequestMethod.GET)
	public List<Channel> allChannelGroup()
	{		
		return channelService.getChannelsByType(2);
	}
	
	
	
}
