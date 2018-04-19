package com.avit.itdap.common.utils.elastic.sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 从elastic-sql转化的查询请求中获取信息的基类
 * @author hudongyu
 * @date 2017年12月18日
 *
 */
public class BaseAnalysis {
	
	public static String getQueryJson(String esUrl,String sql){
		RestTemplate restTemplate=new RestTemplate();
		String url=esUrl+"/_sql/_explain?sql="+sql;
		return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
	}
	
	
	
	/**
	 * 
	 * @param queryJson 查询json
	 * @param namePaths 需要获取的元素的路径集合
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String getElement(String queryJson,ArrayList<String> namePaths) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(queryJson);
		JsonNode nodeTmp = node;
		Iterator<String> it=namePaths.iterator();
		while(it.hasNext()){
			nodeTmp = nodeTmp.get(it.next());
		}
		return nodeTmp.toString();
	}
	
	
	/**
	 * 获取查询json中特定集合使用的scirpt中inline信息
	 * 
	 * @param queryJson 查询json
	 * @param nickname 聚合的别名
	 * @param mathod 聚合的方法
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String getScriptInline(String queryJson,String nickname,String mathod) throws JsonProcessingException, IOException{
		ArrayList<String> namePaths=new ArrayList<String>();
		namePaths.add("aggregations");
		namePaths.add(nickname);
		namePaths.add(mathod);
		namePaths.add("script");
		namePaths.add("inline");
		return getElement(queryJson,namePaths);
	}
	
	
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		
		String sql = "SELECT sum(start_time/1000 - 1513306099) as sumduration FROM itdap_index where ((start_time<1513577029613 and end_time is null) and start_time>=1313536544500)";
		System.out.println(getQueryJson("http://192.168.2.202:9200",sql));
		
		String jsonString = new String("{\"from\":0,\"size\":0,\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"range\":{\"start_time\":{\"from\":null,\"to\":1513577029613,\"include_lower\":true,\"include_upper\":false,\"boost\":1}}},{\"bool\":{\"must_not\":[{\"exists\":{\"field\":\"end_time\",\"boost\":1}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}},{\"range\":{\"start_time\":{\"from\":1313536544500,\"to\":null,\"include_lower\":true,\"include_upper\":true,\"boost\":1}}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}},\"_source\":{\"includes\":[\"SUM\"],\"excludes\":[]},\"aggregations\":{\"sumduration\":{\"sum\":{\"script\":{\"inline\":\" def divide_1987293863 = doc['start_time'].value / 1000; if( divide_1987293863 instanceof String) divide_1987293863= Double.parseDouble(divide_1987293863);  def subtract_1691620399 = divide_1987293863 - 1513306099\",\"lang\":\"painless\"}}}}}");
		
		System.out.println(getScriptInline(jsonString,"sumduration","sum"));
		
		
		
		
		
		
		ArrayList<String> al=new ArrayList<String>();
		al.add("aggregations");
		al.add("sumduration");
		al.add("sum");
		al.add("script");
		al.add("inline");
		System.out.println(getElement(jsonString,al));
		
	}
}
