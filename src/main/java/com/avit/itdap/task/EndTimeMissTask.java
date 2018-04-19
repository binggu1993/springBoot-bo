/*package com.avit.itdap.task;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.avit.itdap.common.utils.HttpUtils;
import com.avit.itdap.common.utils.LogConstants;
import com.avit.itdap.repository.elastic.ClientFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//@Component
public class EndTimeMissTask {
	private static Logger logger = LoggerFactory.getLogger(EndTimeMissTask.class);
	
	@Value("${es.playback.index.name}")
	private String playbackIndex;
	
	@Value("${es.live.index.name}")
	private String liveIndex;
	
	@Value("${es.timeshift.index.name}")
	private String timeshiftIndex;	
	
	//查询偏移多少min没有结束时间的记录进行清理
	@Value("${offset.endtime}")
	private int offsetEndTime;
	
	//多场时间没有结束时间为补齐,直播
	@Value("$live.maxTime")
	private int maxLive;

	//多场时间没有结束时间为补齐,回看
	@Value("$playback.maxTime")
	private int maxPlayBack;

	//多场时间没有结束时间为补齐,时移
	@Value("$timeshift.maxTime")
	private int maxTimeshift;
	
	@Value("${es.cluster.ips}")
	public String esHosts;

	private String[] indexArray;
	
	private int[] maxTimes;
	
	private static final int cycleTime = 30000;// 轮询时间/分片大小（ms）

	*//**
	 * 回看数据清洗
	 *//*
	@Scheduled(fixedRate = cycleTime)
	public void task() {
		indexArray = new String[10];
		indexArray[0] = playbackIndex;
		indexArray[1] = liveIndex;
		indexArray[2] = timeshiftIndex;
		
		maxTimes = new int[10];
		maxTimes[0] = maxPlayBack;
		maxTimes[1] = maxLive;
		maxTimes[2] = maxTimeshift;
		
		for(int i = 0;i<indexArray.length;i++){
			
			String index = indexArray[i];
			int max = maxTimes[i];
			if(StringUtils.isEmpty(index))
				break;
			logger.debug(LogConstants.F_O_VALUE,"task","do Index",index);
			try {
				doIndex(index,max);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void doIndex(String indexName,int max) throws Exception{
		TransportClient client = ClientFactory.getClient();
		
		BulkRequestBuilder bulkRequest = client.prepareBulk(); 
		//SELECT * FROM itdap_index where end_time is null and start_time<now-10m
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM ").append(indexName)
		.append("  where end_time is null and start_time<now-").append(offsetEndTime).append("m limit 0,9999");
		String str = getEsDataBySql(sql.toString());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.reader().readTree(str);
		int total = node.get("hits").get("total").asInt();
		if(total>9999){
			logger.debug(LogConstants.F_O_VALUE,"doIndex","total",total);
		}
		JsonNode array = node.get("hits").get("hits");
		for(int i = 0; i<9999; i++ ){
			if(array.has(i)){
				JsonNode record = array.get(i);
				String id = record.get("_id").asText();
				String index = record.get("_index").asText();
				String type = record.get("_type").asText();
				String userCode = record.get("_source").get("user_code").asText();
				long startTime = record.get("_source").get("start_time").asLong();
				long endTime = getMinTime(startTime, userCode);
								
				if(endTime!=0){
					//updateEndTime,duration
					bulkRequest.add(client.prepareUpdate(index,type,id).setDoc(XContentFactory  
					        .jsonBuilder().startObject()  
					        .field("end_time",endTime)//更新字段
					        .field("duration",(endTime-startTime)/1000)
					        .endObject()  
					        ));

				}else{
					long now = System.currentTimeMillis();
					if((now - startTime)>max*60*1000){
						//updateEndTime,duration
						bulkRequest.add(client.prepareUpdate(index,type,id).setDoc(XContentFactory  
						        .jsonBuilder().startObject()  
						        .field("end_time",startTime+max*60*1000)//更新字段
						        .field("duration",max*60)
						        .endObject()  
						        ));
					}
				}
			}else {
				break;
			}
		}
		BulkResponse bulkResponse = bulkRequest.get();  
		if(bulkResponse.hasFailures()){  
		    logger.error(LogConstants.F_O4IN_VALUE,"doIndex",indexName,bulkResponse.buildFailureMessage());  
		}  
		
	}
	private long getMinTime(long startTime,String userCode) throws Exception{
		long result = 0;
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT min(start_time) FROM ");		
		for(String index:indexArray){
			sql.append(index).append(",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append("  where start_time>").append(startTime).append(" and user_code='").append(userCode).append("'");
		String str = getEsDataBySql(sql.toString());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.reader().readTree(str);
		try{
			result = node.get("aggregations").get("mintime").get("value").asLong();
		}catch(Exception e){
			
		}
		return result;
	}

	public String getEsDataBySql(String sql) throws Exception {
		String host = "http://" + esHosts.split(",")[0] + ":9200";
		String url = host + "/_sql?sql=" + URLEncoder.encode(sql, "utf-8");
		return HttpUtils.doGet(url);
	}
}
*/