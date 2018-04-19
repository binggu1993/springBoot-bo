package com.avit.itdap.mock;

import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.avit.itdap.task.ChannelEnum;

public class TimeShiftMock {
	private static String getAreaName(String areaCode)
	{
		switch(areaCode)
		{

        case "01":
            return "合肥";
        case "02":
            return "黄山";
        case "03":
            return "潢川";
        case "04":
            return "芜湖";
        case "05":
            return "马鞍山";
        case "06":
            return "滁州";
        case "07":
            return "淮北";
        case "08":
            return "安庆";
        case "09":
            return "宿州";
        default:
        	return "合肥";
    
		}
	}
	
	private static String getEpgName(int random){
		switch(random){
		case 1:
			return "模拟节目1";
		case 2:
			return "模拟节目2";
		case 3:
			return "模拟节目3";
		default:
			return "模拟节目";
		}
	}
	
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
			Long endTime = System.currentTimeMillis()+900000+random.nextInt(300000);
			
			//间隔
			Long duringTime = (endTime - startTime)/1000;
			//随机生成一个1-2000的TVN
			String tvn = random.nextInt(2000)+"";
			//随机生成一个1-100的serviceId
			//String serviceId = random.nextInt(100)+"";
			String aeraCode ="0"+ random.nextInt(9);
			String areaName = getAreaName(aeraCode);
			//随机生成nodeId
			Integer nodeId = random.nextInt(10);
			//随机生成一个提供商
			//String opName = operatorEnum.getValue(random.nextInt(3));
			int epgid = random.nextInt(3);
			String epgName = getEpgName(epgid);
			ChannelEnum channel=getServiceId();
			String serviceId = channel.getKey()+"";
			String serviceName=channel.getValue();
			// String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"area_name\":\""+areaName+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"service_id\":"+serviceId+",\"service_name\":\""+serviceName+"\",\"epg_id\":\""+epgid+"\",\"epg_name\":\""+serviceId+epgName+"\"}";
			String jsonTemplate2 = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"area_name\":\""+areaName+"\",\"start_time\":"+startTime+",\"duration\":"+duringTime+",\"service_id\":"+serviceId+",\"service_name\":\""+serviceName+"\",\"epg_id\":\""+epgid+"\",\"epg_name\":\""+serviceId+epgName+"\"}";
			System.out.println(jsonTemplate);
			//使用RestTemplate发送请求
			RestTemplate restTemplate=new RestTemplate();
//		    String url="http://192.168.2.202:9200/demand_index/data/"+startTime+tvn;
			String url="http://192.168.2.202:9200/time-shift-index/data/"+startTime+tvn;
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
	        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class).toString());
	        RestTemplate restTemplate2=new RestTemplate();
	        String url2="http://192.168.2.202:9200/time-shift-index/data/"+tvn+startTime;
	        HttpEntity entity2 = new HttpEntity(jsonTemplate2, headers);
	        System.out.println(restTemplate2.exchange(url2, HttpMethod.POST, entity2, String.class).toString());
	        Thread.sleep(1000);
	        /* String url3="http://192.168.2.202:9200//time-shift-index/data/"+startTime+tvn;
		    HttpHeaders headers3 = new HttpHeaders();
		    RestTemplate restTemplate3=new RestTemplate();
		    RestTemplate restTemplate4=new RestTemplate();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        HttpEntity entity3 = new HttpEntity(jsonTemplate, headers3);
	        System.out.println(restTemplate3.exchange(url, HttpMethod.POST, entity, String.class).toString());
	        String url4="http://192.168.2.202:9200//time-shift-index/data/"+tvn+startTime;
	        HttpEntity entity4 = new HttpEntity(jsonTemplate2, headers3);
	        System.out.println(restTemplate4.exchange(url2, HttpMethod.POST, entity2, String.class).toString());
	        */
		}
		}
}
