package com.avit.itdap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.itdap.bean.system.Area;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.repository.system.AreaRepository;

@Service
public class LiveRealTimeService {
	
	@Autowired
	 AreaRepository areaRepository;
	
	public Map<Long,List<Channel>>   initChannels()
	{
		Map<Long,List<Channel>> initMap=new HashMap<Long, List<Channel>>();
	   List<Channel> channelList=new ArrayList<Channel>();
	   Channel channel=new Channel();
	   channel.setServiceId(60);
	   channel.setChannelName("CCTV1");
	   channel.setChannelType(1);
	   
	   Channel channel1=new Channel();
	   channel1.setServiceId(82);
	   channel1.setChannelName("CCTV2");
	   channel1.setChannelType(1);
	   
	   Channel channel2=new Channel();
	   channel2.setServiceId(32);
	   channel2.setChannelName("CCTV3");
	   channel2.setChannelType(1);
	   
	   Channel channel3=new Channel();
	   channel3.setServiceId(13);
	   channel3.setChannelName("CCTV4");
	   channel3.setChannelType(1);
	   
	   channelList.add(channel);
	   channelList.add(channel1);
	   channelList.add(channel2);
	   channelList.add(channel3);
	   
	   
	   List<Channel> channelGroupList=new ArrayList<Channel>();
	   
	   Channel channel4=new Channel();
	   channel4.setServiceId(0);
	   channel4.setChannelName("央视频道");
	   channel4.setChannelType(2);
	   channel4.setSubServiceIds("82,39,17,90,80");
	   
	   Channel channel5=new Channel();
	   channel5.setServiceId(0);
	   channel5.setChannelName("电视剧场");
	   channel5.setChannelType(2);
	   channel5.setSubServiceIds("61,57,20,82,50");
	   
	   Channel channel6=new Channel();
	   channel6.setServiceId(0);
	   channel6.setChannelName("综艺频道");
	   channel6.setChannelType(2);
	   channel6.setSubServiceIds("70,68,53,61,33");
	   channelGroupList.add(channel4);
	   channelGroupList.add(channel5);
	   channelGroupList.add(channel6);
	   initMap.put(0l, channelList);
	   initMap.put(1l, channelGroupList);
	   return initMap;
		   
	}
	
	
	public List<Area> initArea()
	{
		List<Area> list=areaRepository.findAll();
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

}
