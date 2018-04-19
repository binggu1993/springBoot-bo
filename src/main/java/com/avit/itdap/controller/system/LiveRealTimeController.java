package com.avit.itdap.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.system.Area;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.service.LiveRealTimeService;

@RestController
public class LiveRealTimeController {

	
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Value("${kibana.addr}")
	private String kibanaAddr;
	
	@Value("${kibana.chart.point.period}")
	private String kibanaChartPointPeriod;
	
	@Value("${kibana.chart.point.count}")
	private String kibanaChartPointCount;
	
	@Autowired
	private LiveRealTimeService liveRealTimeService;
	
	@RequestMapping(value = "/initChannelAndArea", method = RequestMethod.GET)
	public Map<String,List> initChannelAndArea()
	{
		Map<String,List> map=new HashMap<String,List>();
		Map<Long,List<Channel>> channelmap=liveRealTimeService.initChannels();
		
		List<Area> initAreas=liveRealTimeService.initArea();
		
		map.put("channel", channelmap.get(0l));
		map.put("channelgroup", channelmap.get(1l));
		map.put("area", initAreas);
		return map;
	}
	
	@RequestMapping(value = "/kibanaInit", method = RequestMethod.GET)
	public Map<String,String> kibanaInit()
	{
		Map<String,String> map=new HashMap<String,String>();
		map.put("addr", kibanaAddr);
		long pointPeriod=Long.valueOf(kibanaChartPointPeriod);
		long pointCount=Long.valueOf(kibanaChartPointCount);
		long period=pointPeriod*pointCount/60/24;
		map.put("period", period+"");
		map.put("addr", kibanaAddr);
		return map;
	}
	
	
	
}
