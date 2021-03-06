/*package com.avit.itdap.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import com.avit.itdap.bean.live.LiveTreatBean;
import com.avit.itdap.bean.system.Area;
import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.bean.system.SystemBean;
import com.avit.itdap.common.utils.HttpUtils;
import com.avit.itdap.common.utils.LogConstants;
import com.avit.itdap.repository.elastic.ClientFactory;
import com.avit.itdap.repository.elastic.index.IndexUtil;
import com.avit.itdap.repository.system.AreaRepository;
import com.avit.itdap.repository.system.ChannelRepository;
import com.avit.itdap.repository.system.SystemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

*//**
 * 数据清洗定时任务
 * @author hudongyu
 * @date 2017年12月4日
 *//*

@Component
public class DataTreatingTask2 {
	private static Logger logger = LoggerFactory.getLogger(DataTreatingTask2.class);
	
	@Value("${es.live.index.name}")
	public String indexName;
	
	@Value("${es.live.index.trans.name}")
	public String transIndexName;
	
	@Value("${es.cluster.ips}")
	public String esHosts;
	
	//偏移量
	@Value("${offset}")
	public int offset;
	
	@Autowired
	SystemRepository systemRepository;
	@Autowired
	AreaRepository areaRepository;
	@Autowired
	ChannelRepository channelRepository;
	
	private static final int cycleTime = 30000;//轮询时间/分片大小（ms）
	
	
	*//**
	 * 直播数据清洗
	 *//*
	@Scheduled(fixedRate=cycleTime)
	public void liveTreat(){
		//初始化数据
		long time=System.currentTimeMillis();
		SystemBean system = systemRepository.findOne(1L);	
		long endTime = system.getLastLiveTime()+system.getLiveInterval()*60*1000;
		long startTime = system.getLastLiveTime();
		long audienceBase = system.getLiveInterval()*60*system.getStbSum();
		while(endTime<(time-offset*1000)){
			logger.info(LogConstants.F_O_MN_2MV,"liveTreat","begin","starttime",startTime,"endtime",endTime);
			Map<String, LiveTreatBean> map = new HashMap<String, LiveTreatBean>();
			List<Area> areaList = areaRepository.findAll();
			List<Channel> channelList = channelRepository.findByChannelType(1);
			for(Area area:areaList){
				for(Channel channel:channelList){
					LiveTreatBean bean = new LiveTreatBean();
					bean.setAreaCode(area.getAreaCode());
					bean.setAreaName(area.getAreaName());
					bean.setChannelName(channel.getChannelName());
					bean.setServiceID(channel.getServiceId());
					String key = area.getAreaCode()+"-"+channel.getServiceId();
					map.put(key, bean);
				}
			}	
			long totaluser =0L;
			try{
				//获取用户数
				String sql="SELECT count(*) as tmpNum,live.service_id.keyword,area_code.keyword FROM "+indexName+" where (end_time between "+startTime+" and "+endTime+" or (end_time is null)) group by live.service_id.keyword,area_code.keyword limit 9999";
				String result = getEsDataBySql(sql);
				totaluser = updateData(result,map,1,1);
				logger.debug(LogConstants.F_O_MN_2MV,"liveTreat","user count","totaluser",totaluser,"endTime",endTime);
				//获取时长1
				sql="SELECT sum(duration_time) as tmpNum,live.service_id.keyword,area_code.keyword FROM "+indexName+" where (end_time between "+startTime+" and "+endTime+" and start_time>="+startTime+") group by live.service_id.keyword,area_code.keyword limit 9999";
				result = getEsDataBySql(sql);
				updateData(result,map,2,1);				
				logger.debug(LogConstants.F_O_MN_MV,"liveTreat","get sum duration for  greater than starttime","endTime",endTime);
				//获取时长2
				sql="SELECT sum(end_time/1000 - "+startTime/1000+") as tmpNum,live.service_id.keyword,area_code.keyword FROM "+indexName+" where (end_time between "+startTime+" and "+endTime+" and start_time<"+startTime+") group by live.service_id.keyword,area_code.keyword limit 9999";
				result = postEsDataBysql(sql);
				updateData(result,map,2,1);				
				logger.debug(LogConstants.F_O_MN_MV,"liveTreat","get sum duration for  less than starttime","endTime",endTime);
				//获取时长3
				sql="SELECT sum("+endTime/1000+" - start_time/1000) as tmpNum,live.service_id.keyword,area_code.keyword FROM "+indexName+" where ((start_time<"+endTime+" and end_time is null) and start_time>=1313536544500) group by live.service_id.keyword,area_code.keyword limit 9999";
				result = postEsDataBysql(sql);
				updateData(result,map,2,1);
				logger.debug(LogConstants.F_O_MN_MV,"liveTreat","get sum duration for is not end and  greater than starttime","endTime",endTime);
				//获取时长4
				sql="SELECT count(*) as tmpNum,live.service_id.keyword,area_code.keyword FROM "+indexName+" where (start_time<"+startTime+" and end_time is null) group by live.service_id.keyword,area_code.keyword limit 9999";
				result = getEsDataBySql(sql);
				updateData(result,map,2,(int)system.getLiveInterval()*60);
				Set<String> keySet=map.keySet();
				logger.debug(LogConstants.F_O_MN_MV,"liveTreat","get sum duration for is not end and  less than starttime","endTime",endTime);
				//更新
				//获取client对象
				TransportClient client = ClientFactory.getClient();
				IndexRequestBuilder indexBuilder = IndexUtil.getBuilder(client,transIndexName, "data");
				for(String key:keySet){
					LiveTreatBean bean = map.get(key);
					indexBuilder.setSource(jsonBuilder().startObject()
							.field("service_id",bean.getServiceID())
							.field("service_name",bean.getChannelName())
							.field("area_code",bean.getAreaCode())
							.field("area_name",bean.getAreaName())
							.field("user_count",bean.getUserCount())
							.field("duration_time",bean.getDuration())
							.field("market_rate",(float)bean.getUserCount()/totaluser)
							.field("audience_rate",(float)bean.getDuration()/audienceBase)
							.field("date",endTime)
							.endObject());
					IndexResponse res = indexBuilder.get();
					System.out.println(res.toString());
				}
				logger.debug(LogConstants.F_O_MN_2MV,"liveTreat","put to index","indexName",transIndexName,"size",map.size());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {				
			}			
			system.setLastLiveTime(endTime);
			systemRepository.save(system);
			startTime = endTime;
			endTime=endTime+system.getLiveInterval()*60*1000;
			logger.info(LogConstants.F_O_MN_2MV,"liveTreat","end","starttime",startTime,"endtime",endTime);
		}//end while		
		
		
	}
	*//**
	 * 
	 * @param queryJson 查询json
	 * @param namePaths 需要获取的元素的路径集合
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 *//*
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
	
	
	*//**
	 * 获取查询json中特定集合使用的scirpt中inline信息
	 * 
	 * @param queryJson 查询json
	 * @param nickname 聚合的别名
	 * @param mathod 聚合的方法
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 *//*
	public static String getScriptInline(String queryJson,String nickname,String mathod) throws JsonProcessingException, IOException{
		ArrayList<String> namePaths=new ArrayList<String>();
		namePaths.add("aggregations");
		namePaths.add("live.service_id.keyword");
		namePaths.add("aggregations");
		namePaths.add("area_code.keyword");
		namePaths.add("aggregations");
		namePaths.add(nickname);
		namePaths.add(mathod);
		namePaths.add("script");
		namePaths.add("inline");
		return getElement(queryJson,namePaths);
	}
	

	public String reWriteSum(String sql) throws JsonProcessingException, IOException{
		String queryJson = getQueryJson(sql);
		String inlineString = getScriptInline(queryJson,"tmpNum","sum").replaceAll("\"", "");
		//获取变量的值
		int i = inlineString.lastIndexOf(";");
		String variableName = inlineString.substring(i+1,inlineString.length()).trim().split(" ")[1];
		String newInlineString = inlineString+";return "+variableName+";";
		String newQueryJson = queryJson.replace(inlineString, newInlineString);
		return newQueryJson;
	}
	
	private String getQueryJson(String sql){
		String host ="http://"+esHosts.split(",")[0]+":9200";
		//RestTemplate restTemplate=new RestTemplate();
		String url="";
		try {
			url = host+"/_sql/_explain?sql="+URLEncoder.encode(sql,"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(LogConstants.F_O4CATCH_MN4CLASS_MV_THROWABLE2,"getQueryJson",e);
		}
		//return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
		return HttpUtils.doGet(url);	
	}
	

	
	private long updateData(String str,Map<String, LiveTreatBean> map,int type,int base){
		long result =0L;
		if(StringUtils.isEmpty(str)){
			logger.info("updateData str is null");
			return result;
		}
		try{
			ObjectMapper mapper = new ObjectMapper();  
			JsonNode node=mapper.reader().readTree(str);
			JsonNode liveServiceID = node.get("aggregations").get("live.service_id.keyword").get("buckets");
			int index = 0;
			while(liveServiceID.has(index)){
				JsonNode serviceNode = liveServiceID.get(index);
				String serviceId = serviceNode.get("key").textValue();
				JsonNode areaNodes = serviceNode.get("area_code.keyword").get("buckets");
				int areaIndex = 0;
				while(areaNodes.has(areaIndex)){
					JsonNode areaNode = areaNodes.get(areaIndex);
					String area=areaNode.get("key").textValue();
					long value = areaNode.get("tmpNum").get("value").asLong()*base;
					String key = area+"-"+serviceId;
					if(map.containsKey(key)){
						LiveTreatBean bean = map.get(key);
						if(type==1){
							result=result+value;
							bean.setUserCount(value);
						}else{
							bean.setDuration(value+bean.getDuration());
						}
					}
					
					areaIndex++;
				}
				index++;
			}
			
		}catch(Exception e){
			logger.error(LogConstants.F_O4CATCH_MN4CLASS_MV_THROWABLE2,"updateData",e);
		}
		return result;
	}
	
	public String getEsDataBySql(String sql) throws Exception{
		String host ="http://"+esHosts.split(",")[0]+":9200";		
		//RestTemplate restTemplate=new RestTemplate();
		String url=host+"/_sql?sql="+URLEncoder.encode(sql,"utf-8");
		return HttpUtils.doGet(url);		
		//return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
	}
	
	public String postEsDataBysql(String sql) throws JsonProcessingException, IOException{
		String json= reWriteSum(sql);
		String host ="http://"+esHosts.split(",")[0]+":9200";
		String url=host+"/"+indexName+"/_search";
		return HttpUtils.doPost(url,json);
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		//String json = "{\"live.service_id.keyword\":{\"terms\":{\"field\":\"live.service_id.keyword\",\"size\":200,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_term\":\"asc\"}]},\"aggregations\":{\"area_code.keyword\":{\"terms\":{\"field\":\"area_code.keyword\",\"size\":10,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_term\":\"asc\"}]},\"aggregations\":{\"tmpNum\":{\"sum\":{\"script\":{\"inline\":\" def subtract_2120888786 = doc['end_time'].value - 1513495200000\",\"lang\":\"painless\"}}}}}}}}";
		String json = "{\"from\":0,\"size\":0,\"query\":{\"bool\":{\"filter\":[{\"bool\":{\"must\":[{\"bool\":{\"must\":[{\"range\":{\"end_time\":{\"from\":1513495200000,\"to\":1513495500000,\"include_lower\":true,\"include_upper\":true,\"boost\":1}}},{\"range\":{\"start_time\":{\"from\":null,\"to\":1513495200000,\"include_lower\":true,\"include_upper\":false,\"boost\":1}}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}}],\"disable_coord\":false,\"adjust_pure_negative\":true,\"boost\":1}},\"_source\":{\"includes\":[\"SUM\",\"live.service_id.keyword\",\"area_code.keyword\"],\"excludes\":[]},\"stored_fields\":[\"live.service_id.keyword\",\"area_code.keyword\"],\"aggregations\":{\"live.service_id.keyword\":{\"terms\":{\"field\":\"live.service_id.keyword\",\"size\":200,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_term\":\"asc\"}]},\"aggregations\":{\"area_code.keyword\":{\"terms\":{\"field\":\"area_code.keyword\",\"size\":10,\"min_doc_count\":1,\"shard_min_doc_count\":0,\"show_term_doc_count_error\":false,\"order\":[{\"_count\":\"desc\"},{\"_term\":\"asc\"}]},\"aggregations\":{\"tmpNum\":{\"sum\":{\"script\":{\"inline\":\" def subtract_3434617 = doc['end_time'].value - 1513495200000\",\"lang\":\"painless\"}}}}}}}}}";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(json);
		System.out.println(node.get("aggregations").get("live.service_id.keyword").get("aggregations").get("area_code.keyword").get("aggregations"));
		
		DataTreatingTask2 task = new DataTreatingTask2();
		task.esHosts="192.168.2.202,192.168.2.203,192.168.2.204";
		task.offerset=10;
		task.indexName="itdap_index";
		task.liveTreat();
//		task.getUserNum(1313536544500L, 1513577029613L);
	}
}
*/