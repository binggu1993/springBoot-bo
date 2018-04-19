package com.avit.itdap.mock;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.avit.itdap.task.ChannelEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMatj {
	private static ChannelEnum getServiceId()
	{
		int enumCase=0;
	    enumCase = new Random().nextInt(20) + 1;
	    switch(enumCase)
	    {
        case 1:
            return ChannelEnum.CCTV1;
        case 2:
        	return ChannelEnum.CCTV2;
        case 3:
        	return ChannelEnum.CCTV3;
        case 4:
        	return ChannelEnum.CCTV4;
        case 5:
        	return ChannelEnum.CCTV5;
        case 6:
        	return ChannelEnum.CCTV6;
        case 7:
        	return ChannelEnum.CCTV7;
        case 8:
        	return ChannelEnum.CCTV8;
        case 9:
        	return ChannelEnum.BJWS;
        case 10:
        	return ChannelEnum.ZJWS;
        case 11:
        	return ChannelEnum.HBWS;
        case 12:
        	return ChannelEnum.HNWS;
        case 13:
        	return ChannelEnum.JXWS;
        case 14:
        	return ChannelEnum.AHWS;
        case 15:
        	return ChannelEnum.LNWS;
        case 16:
        	return ChannelEnum.SZWS;
        default:
        	return ChannelEnum.CCTV1;
    }
	}
	public static void main(String[] args) throws InterruptedException {
		while(true){
			Random random = new Random();
			//
			
			Long startTime = System.currentTimeMillis();
			//随机生成一个5-10分钟的切出
			Long endTime = System.currentTimeMillis()+0+random.nextInt(1200)*1000;
			
			//间隔
			Long duringTime = (endTime - startTime)/1000;
			//随机生成一个1-2000的TVN
			String tvn = random.nextInt(2000)+"";
			//随机生成一个1-100的serviceId
			//String serviceId = random.nextInt(100)+"";
			ChannelEnum channel=getServiceId();
			String serviceId = channel.getKey()+"";
			String serviceName=channel.getValue();
			String aeraCode ="0"+ random.nextInt(9);
			//随机生成nodeId
			Integer nodeId = random.nextInt(10);
			//直播数据  String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			//点播数据String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"asset_id\":"+serviceId+",\"service_code\":\"OTT\",\"node_id\":\""+nodeId+"\",\"operator_name\":\""+opName+"\"}";
			//String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"epg_name\":\""+epgName+"\",\"service_id\":\""+serviceId+"\",\"service_name\":\""+serviceName+"\"}";
			String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			System.out.println(jsonTemplate);
			//使用RestTemplate发送请求
			RestTemplate restTemplate=new RestTemplate();
		    String url="http://192.168.2.202:9200/live-index/live/"+startTime+tvn;
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
	        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));
	        Thread.sleep(1000);
		}
	}
}
