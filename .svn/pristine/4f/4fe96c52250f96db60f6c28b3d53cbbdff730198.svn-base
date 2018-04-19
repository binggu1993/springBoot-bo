package com.avit.itdap.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.avit.itdap.bean.Result;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.bean.system.ChannelEPG;
import com.avit.itdap.bean.xml.BroadcastData;
import com.avit.itdap.bean.xml.ChannelXmlBean;
import com.avit.itdap.bean.xml.Event;
import com.avit.itdap.bean.xml.SchedulerData;
import com.avit.itdap.controller.system.ChannelEPGController;
import com.avit.itdap.dto.system.ChannelEPGDto;
import com.avit.itdap.exception.ApplicationException;
import com.avit.itdap.repository.system.ChannelEPGRepository;
import com.avit.itdap.repository.system.ChannelRepository;
import com.avit.itdap.util.CommonUtil;
import com.avit.itdap.util.DateUtil;
import com.avit.itdap.util.ResultCodeEnum;
import com.avit.itdap.util.xml.XmlHelper;

@Service
public class ChannelEPGService {
	private static Logger logger = LoggerFactory.getLogger(ChannelEPGService.class);
	
	@Autowired
	ChannelEPGRepository channelEPGRepository;
	
	@Autowired
	ChannelRepository channelRepository;
	
	/**
	 * 多条件查询
	 * 
	 * @param 
	 * @return
	 */
	public Page<ChannelEPG> findAllByConditions(final ChannelEPGDto dto, Pageable pageable)
	{
		Page<ChannelEPG> result = channelEPGRepository.findAll(new Specification<ChannelEPG>()
		{
			@Override
			public Predicate toPredicate(Root<ChannelEPG> root, CriteriaQuery<?> query, CriteriaBuilder cb)
			{
				List<Predicate> list = new ArrayList<Predicate>();

				if (!StringUtils.isEmpty(dto.getEpgName()))
				{
					list.add(cb.like(root.get("epgName").as(String.class), "%" + dto.getEpgName() + "%"));
				}

				if (dto.getStartTime()!=null)
				{
					list.add(cb.greaterThanOrEqualTo(root.<Date>get("startTime"), dto.getStartTime()));
				}
				
				if (dto.getEndTime()!=null)
				{
					list.add(cb.lessThanOrEqualTo(root.<Date>get("startTime"), dto.getEndTime()));
				}
				
				if(!StringUtils.isEmpty(dto.getChannelCode()))
				{
					 Channel channel=channelRepository.findChannelByChannelCode(dto.getChannelCode());
					 if(channel!=null)
					 {
					 Join<ChannelEPG, Channel> fuJoin = root.join(root.getModel().getSingularAttribute("channel", Channel.class), JoinType.INNER);  
					 list.add(cb.equal(fuJoin.get("id").as(Long.class), channel.getId()));  
					 }else
					 {
						 Join<ChannelEPG, Channel> fuJoin = root.join(root.getModel().getSingularAttribute("channel", Channel.class), JoinType.INNER);  
						 list.add(cb.equal(fuJoin.get("id").as(Long.class), -10000l));  
					 }
				}
				
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		return result;
	}
	
	

	public  void importFile(String fileName, MultipartFile file,
			String destPath, String destName) throws Exception {
		File uploadDir = new File(destPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();
		// 新建一个文件
		File tempFile = new File(destPath + destName);
		try {
			// 将上传的文件写入新建的文件中
			file.transferTo(tempFile);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}
	
	@Transactional
	public String parseChannelEpgXml(String fileName)
	{
		
		
		String resultMessage="";
		File f = new File(fileName);
		try {
			String charset=CommonUtil.getCharset(f);
			String xmlBody = FileUtils.readFileToString(f, charset);
			BroadcastData info = (BroadcastData) XmlHelper.getInstance().toObject(xmlBody,charset);
			Result result=parseChannelEpg(info);
			if(result.getCode().intValue()!=ResultCodeEnum.SUCCESS.getKey().intValue())
			{
				resultMessage= result.getMessage();
			}else
			{
				Map<String,List<ChannelEPG>> egpMap=(Map<String,List<ChannelEPG>>)result.getResult();
				for(String channelName:egpMap.keySet())
				{
					List<ChannelEPG> epgList=egpMap.get(channelName);
					Date startDate=epgList.get(0).getStartTime();
					Date endDate=epgList.get(epgList.size()-1).getStartTime();
					channelEPGRepository.deleteByStartTimeBetween(startDate, endDate);
					channelEPGRepository.save(epgList);
				}
			}
			return resultMessage;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	
	private Result parseChannelEpg(BroadcastData info)
	{
		Result result=new Result();
		Map<String,List<ChannelEPG>> egpMap=new HashMap<String,List<ChannelEPG>>();
		SchedulerData data=info.getSchedulerData();
		try {
			if(data!=null&&data.getChannelXmlBeanList()!=null)
			{
				List<ChannelXmlBean> channelList=data.getChannelXmlBeanList();
				if(channelList.size()>0)
				{
					for(ChannelXmlBean channel:channelList)
					{
						String channelName=channel.getChannelText().getChannelName();
						List<Channel> channels=channelRepository.findChannelByChannelName(channelName);
						if(channels==null||channels.size()==0)
						{
							result.setCode(ResultCodeEnum.NOT_EXIST.getKey());
							result.setMessage(ResultCodeEnum.NOT_EXIST.getValue()+"频道名称："+channelName);
							break;
						}
						Channel dochannel=channels.get(0);
						List<Event> eventList=channel.getEvenList();
						if(eventList!=null&&eventList.size()>0)
						{
						    String validateResult=validateEvent(eventList);
							if(!StringUtils.isEmpty(validateResult))
							{
								result.setCode(ResultCodeEnum.FORMAT_ERROR.getKey());
								result.setMessage(ResultCodeEnum.FORMAT_ERROR.getValue()+":"+validateResult);
								break;
							}
							List<ChannelEPG> epgList=new ArrayList<ChannelEPG>();
							for(Event event:eventList)
							{
								ChannelEPG epg=new ChannelEPG();
								Channel newchannel=new Channel();
								newchannel.setId(dochannel.getId());
								epg.setChannel(newchannel);
								epg.setEpgName(event.getEventText().getName());
								epg.setDuration(CommonUtil.getDuration(event.getDuration()));
								epg.setStartTime(DateUtil.parse(event.getBegintime(), DateUtil.YYYYMMDDHHMMSS));
								epgList.add(epg);
							}
							egpMap.put(channelName, epgList);
						}
					}
				}
			}
		} catch (ParseException e) {
			result.setCode(ResultCodeEnum.FORMAT_ERROR.getKey());
			result.setMessage(ResultCodeEnum.FORMAT_ERROR.getValue());
		
		}
		result.setResult(egpMap);
		return result;
	}
	
	private String validateEvent(List<Event> eventList)
	{
		String result="";
		for(Event event:eventList)
		{
			String beginTime=event.getBegintime();
			String duration=event.getDuration();
			if(StringUtils.isEmpty(beginTime)||StringUtils.isEmpty(duration))
			{
				result+=event.getEventText().getName()+":beginTime or duration can not empty!";
			}
			
			try {
				DateUtil.parse(beginTime, DateUtil.YYYYMMDDHHMMSS);
				DateUtil.parse(beginTime, DateUtil.HHMMSS);
			} catch (ParseException e) {
				result+=event.getEventText().getName()+":beginTime or duration format error!    ";
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	public String parseChannelEpgWithTxt(String fileName) throws Exception
	{
		try {
			List<Map<String,Object>> parseEpgList=pareseChannelEpgwithTxt(fileName);
			String channelCode="";
			if(parseEpgList!=null&&parseEpgList.size()>0)
			{
				for(int i=0;i<parseEpgList.size();i++)
				{
					Map<String,Object> map=parseEpgList.get(i);
					if(i==0)
					{
						channelCode=(String)map.get("channelCode");
					}
					
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	
	
	private List<Map<String,Object>> pareseChannelEpgwithTxt(String fileName) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		String channelCode="";
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> epgMap=null;
		try {
			File f = new File(fileName);
			fr  = new FileReader(f);
			br = new BufferedReader(fr);
			String tmp = "";
			String epgDate="";
			List<String> epgList=null;
			while((tmp=br.readLine())!=null){
				if(StringUtils.isEmpty(tmp))
				{
					continue;
				}
				if(tmp.indexOf("频道名称")>-1)
				{
					channelCode=tmp.substring(tmp.lastIndexOf(" ")+1);
					Map<String,Object> channelMap=new HashMap<String,Object>();
					channelMap.put("channelCode", channelCode);
					list.add(channelMap);
				}else if(tmp.indexOf("播出日期")>-1)
				{
					if(epgList!=null)
					{
						epgMap.put(epgDate,epgList);
						list.add(epgMap);
						epgList=null;
						epgMap=null;
					}
					epgDate=tmp.substring(tmp.lastIndexOf(" ")+1);
					epgList=new ArrayList<String>();
					epgMap=new HashMap<String, Object>();
				}else
				{
					epgList.add(tmp);
				}
				
			}			
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if(br!=null){
				br.close();
				br = null;
			}
			if (fr != null) {
				fr.close();
				fr = null;
			}
		}
		return list;
	}
}
