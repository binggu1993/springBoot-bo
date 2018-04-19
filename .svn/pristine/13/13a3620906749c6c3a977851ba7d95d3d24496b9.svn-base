import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import afu.org.checkerframework.checker.units.qual.min;

import com.sun.org.apache.bcel.internal.generic.Select;


public class Test {
	/*static Map<String, String> epgMap = null;
	public static void main(String[] args){
		if(epgMap == null&&epgMap.size()==0){
			 
		}else{
			//获得最小开始时间
			String minbegin = selectMinBeginTime();
			//获得最小结束时间
			String minend = SelectMinEndTime();
			String min = min(minbegin,minend);
			if(min==minend){
				selectPlan();
				doremove();
			}
			if(min == minbegin){
				selectPlan();
				doaddorupdate();
			}
			//获取所有在播计划的开始时间和结束时间,min<=starttime
			selectPlan();
			long[] startEndTime= null;
			Arrays.sort(startEndTime);
			Map<Long,List> timeList = new HashMap<Long,List>();
			for(int i = 0;i<startEndTime.length-1;i++){
				long startTime = startEndTime[i];
				//long endTime = startEndTime[i+1];	
				timeList.put(startTime,new ArrayList());
			}
			Set<String>keysSet = epgMap.keySet();
			for(String key:keysSet){
				//if(epgMap.get(key))
				for(int i = 0;i<startEndTime.length-1;i++){
					//如果开始时间小于等于timeList的startTime则继续，否则break;
				}
			}
			
		}
		
	}
	private static void doaddorupdate() {
		// TODO Auto-generated method stub
		
	}
	private static void doremove() {
		// TODO Auto-generated method stub
		
	}
	private static void selectPlan() {
		// TODO Auto-generated method stub
		
	}
	private static String min(String minbegin, String minend) {
		// TODO Auto-generated method stub
		return null;
	}
	private static String SelectMinEndTime() {
		// TODO Auto-generated method stub
		return null;
	}
	private static String selectMinBeginTime() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	public static void main(String[] args) {
		    String url = "http://localhost:8090/itdap/synCategoryInfo";  
		    HttpHeaders requestHeaders = new HttpHeaders();  
		    //requestHeaders.set("Accept", "application/json");  
		    requestHeaders.set("Content-Type", "application/xml");  
		      
		    String xmlStr = "<SynCategory><Category categoryName=\"1234\" categoryId=\"1\" categoryType=\"1\" parentId=\"0\" categoryUrl=\"abc\"/></SynCategory>";  
		  
		    RestTemplate restTemplate = new RestTemplate();  
		    HttpEntity<String> httpEntity = new HttpEntity<String>(xmlStr, requestHeaders);  
		    String jsonData = restTemplate.postForObject(url, httpEntity, String.class); 
		  
		    System.out.println(jsonData);  
	}

}
