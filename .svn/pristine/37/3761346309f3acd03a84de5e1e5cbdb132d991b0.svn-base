package com.avit.itdap.mock;

import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.avit.itdap.task.ChannelEnum;

/**
 * 启动task需要在主入口ItdapApplicationRun增加@EnableScheduling注解
 * @author hudongyu
 * @date 2017年11月22日
 */
@Component
public class MockTask {
	//生成随机切入切出的方法，每秒一次
	//@Scheduled(cron="*/1 * * * * ?")
	//@Scheduled(fixedRate = 1000)
	public void liveMock(){
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
		String serviceId = random.nextInt(100)+"";
		String aeraCode ="0"+ random.nextInt(9);
		//随机生成nodeId
		Integer nodeId = random.nextInt(10);
		//随机生成一个提供商
		String opName = operatorEnum.getValue(random.nextInt(3));
		// String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
		String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"asset_id\":"+serviceId+",\"service_code\":\"OTT\",\"node_id\":\""+nodeId+"\",\"operator_name\":\""+opName+"\"}";
		System.out.println(jsonTemplate);
		//使用RestTemplate发送请求
		RestTemplate restTemplate=new RestTemplate();
//	    String url="http://192.168.2.202:9200/demand_index/data/"+startTime+tvn;
		String url="http://192.168.2.202:9200/itdap_index/data/"+startTime+tvn;
	    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));
        
	}
	
	
	//@Scheduled(fixedRate = 1000)
	public void dianboMock(){
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
		String serviceId = random.nextInt(100)+"";
		String aeraCode ="0"+ random.nextInt(9);
		//随机生成nodeId
		Integer nodeId = random.nextInt(10);
		//随机生成一个提供商
		String opName = operatorEnum.getValue(random.nextInt(3));
		//直播数据  String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
		String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"asset_id\":"+serviceId+",\"service_code\":\"OTT\",\"node_id\":\""+nodeId+"\",\"operator_name\":\""+opName+"\"}";
		System.out.println(jsonTemplate);
		//使用RestTemplate发送请求
		RestTemplate restTemplate=new RestTemplate();
	    String url="http://192.168.2.202:9200/demand-index/data/"+startTime+tvn;
//		String url="http://192.168.2.202:9200/itdap_index/data/"+startTime+tvn;
	    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));
        
	}
	
/*	@Scheduled(fixedRate = 1000)
	public void liveCleanMock(){
		Random random = new Random();
		Long startTime = System.currentTimeMillis();
		//随机生成一个5-10分钟的切出
		Long endTime = System.currentTimeMillis()+300000+random.nextInt(300000);
		//间隔
		Long duringTime = (endTime - startTime)/1000;
		//随机生成一个1-2000的TVN
		String tvn = random.nextInt(2000)+"";
		//随机生成一个1-100的serviceId
		ChannelEnum channel=getServiceId();
		String serviceId = channel.getKey()+"";
		String serviceName=channel.getValue();
		String areaCode ="0"+ random.nextInt(9);
		String areaName=getAreaName(areaCode);
		String jsonTemplate = "{\"service_id\":\""+serviceId+"\",\"service_name\":"+serviceName+",\"area_code\":\""+areaCode+"\",\"area_name\":"+areaName+",\"epg_code\":"+duringTime+",\"epg_name\":"+duringTime+",\"in_time\":"+duringTime+",\"out_time\":"+",\"user_count\":"+duringTime+",\"audience_rate\":"+duringTime+",\"date\":"+duringTime+"}";
		System.out.println(jsonTemplate);
		//使用RestTemplate发送请求
		RestTemplate restTemplate=new RestTemplate();
	    String url="http://192.168.2.202:9200/itdap_index/live/"+startTime+tvn;
	    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));
	}*/
	
	
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
	
	private String getAreaName(String areaCode)
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
	
	private enum operatorEnum {


		ZIYOU(0,"OP001"),
		HUASHU(1,"SP001"),
		YOUKU(2,"SP002");
		
		
		private Integer key;
		private String value;
		
		private operatorEnum(Integer key, String value){
			this.key = key;
			this.value = value;
		}
		public static operatorEnum getDeviceType(Integer key){
			for(operatorEnum type : operatorEnum.values()){
				if(type.key.equals(key)){
					return type;
				}
			}
			return null;
		}
		public Integer getKey() {
			return key;
		}
		public String getValue(){
			return value;
		}
		public static String getValue(Integer key) {
			for(operatorEnum type : operatorEnum.values()){
				if(type.key.equals(key)){
					return type.value;
				}
			}
			return null;
		}
		
		
		public static Integer getKey(String value)
		{
			for(operatorEnum type:operatorEnum.values())
			{
				if(type.getValue().equals(value))
				{
					return type.getKey();
				}
			}
			return null;
		}

		
	}
	
	public static void main(String[] args) throws InterruptedException {
		while(true){
			/*Random random = new Random();
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
			ChannelEnum channel=getServiceId();
			String serviceId = channel.getKey()+"";
			String serviceName=channel.getValue();
			String aeraCode ="0"+ random.nextInt(9);
			//随机生成nodeId
			Integer nodeId = random.nextInt(10);
			//随机生成一个提供商
			String opName = operatorEnum.getValue(random.nextInt(3));
			String epgName = EpgName.getValue(random.nextInt(5));
			//直播数据  String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			//点播数据String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"asset_id\":"+serviceId+",\"service_code\":\"OTT\",\"node_id\":\""+nodeId+"\",\"operator_name\":\""+opName+"\"}";
			//String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"epg_name\":\""+epgName+"\",\"service_id\":\""+serviceId+"\",\"service_name\":\""+serviceName+"\"}";
			String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			System.out.println(jsonTemplate);
			//使用RestTemplate发送请求
			RestTemplate restTemplate=new RestTemplate();
		    String url="http://192.168.2.202:9200/itdap_index/live/"+startTime+tvn;
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
	        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));*/
	        
	        
	        //点播
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
			String serviceId = random.nextInt(100)+"";
			String aeraCode ="0"+ random.nextInt(9);
			//随机生成nodeId
			Integer nodeId = random.nextInt(10);
			//随机生成一个提供商
			String opName = operatorEnum.getValue(random.nextInt(3));
			//直播数据  String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"start_time\":"+startTime+",\"area_code\":\""+aeraCode+"\",\"end_time\":"+endTime+",\"duration_time\":"+duringTime+",\"live\":{\"service_id\":\""+serviceId+"\",\"action_type\":\"DVB\"}}";
			String jsonTemplate = "{\"user_code\":\""+tvn+"\",\"area_code\":\""+aeraCode+"\",\"start_time\":"+startTime+",\"end_time\":"+endTime+",\"duration\":"+duringTime+",\"asset_id\":"+serviceId+",\"service_code\":\"OTT\",\"node_id\":\""+nodeId+"\",\"operator_name\":\""+opName+"\"}";
			System.out.println(jsonTemplate);
			//使用RestTemplate发送请求
			RestTemplate restTemplate=new RestTemplate();
		    String url="http://192.168.2.202:9200/demand-index/data/"+startTime+tvn;
//			String url="http://192.168.2.202:9200/itdap_index/data/"+startTime+tvn;
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	        HttpEntity entity = new HttpEntity(jsonTemplate, headers);
	        System.out.println(restTemplate.exchange(url, HttpMethod.POST, entity, String.class));
	        
	        
	        
	        Thread.sleep(1000);
	        
		}
	}
}
