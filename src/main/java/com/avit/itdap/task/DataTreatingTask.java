/*package com.avit.itdap.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import com.avit.itdap.repository.elastic.ClientFactory;
import com.avit.itdap.repository.elastic.index.IndexUtil;
import com.avit.itdap.repository.elastic.search.SearchUtil;

*//**
 * 数据清洗定时任务
 * @author hudongyu
 * @date 2017年12月4日
 *//*

@Component
public class DataTreatingTask {
	
	@Value("${es.index.name}")
	private String indexName;
	
	@Value("${es.index.trans.name}")
	private String transIndexName;
	
	private static final int cycleTime = 30000;//轮询时间/分片大小（ms）
	
	*//**
	 * 直播数据清洗
	 *//*
	//@Scheduled(fixedRate=cycleTime)
	public void liveTreat(){
		//从数据库读取标志，判定当前处理进度   稍后做
		
		//获取client对象
		TransportClient client = ClientFactory.getClient();
		
		//获取builder，配置索引名称
		SearchRequestBuilder sreq = SearchUtil.getBuilder(client, indexName).setTypes("live");
		
		//配置查询日期  目前以当前时间到前五分钟
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar ca = Calendar.getInstance();
		Date dateEnd = new Date();
		//使用标准时间
		ca.setTime(dateEnd);
		ca.add(Calendar.MILLISECOND, -cycleTime);
		Date dateBegin = ca.getTime();
		System.out.println(sf.format(dateBegin)+sf.format(dateEnd));
		//在条件中不要忘记设置时区
		sreq.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("start_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").lte(sf.format(dateEnd)).timeZone("+08:00")).must(QueryBuilders.rangeQuery("end_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").gte(sf.format(dateBegin)).timeZone("+08:00")));
		
		//配置搜索条件（只有这一步和其它清洗有差异，感觉可以抽象，配饰不同实现完成不同内容、或者相同内容不同地市的流程）
		sreq.addAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword"))
	    .addAggregation(AggregationBuilders.terms("serviceIds").field("live.service_id.keyword").size(2000).subAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword")).subAggregation(AggregationBuilders.terms("areas").field("area_code.keyword").subAggregation(AggregationBuilders.cardinality("user_count_area").field("user_code.keyword"))));
	   
		System.out.println(sreq.toString());
		//获取返回内容
		SearchResponse sres = sreq.get();
		System.out.println(sres.toString());
		
		//解析返回内容
		Cardinality userCountAgg = sres.getAggregations().get("user_count");//总观看人数
		long userCount = userCountAgg.getValue();
		Terms agg1 = sres.getAggregations().get("serviceIds");
		
		//初始化写入
		IndexRequestBuilder indexBuilder = IndexUtil.getBuilder(client,transIndexName, "live");
		
		
		for(Bucket bk:agg1.getBuckets()){
         	String serviceId = bk.getKeyAsString();//获取频道ID（或频道Name，一对一关系，可清洗）
         	Terms agg3 = bk.getAggregations().get("areas");
         	for(Bucket bkArea:agg3.getBuckets()){
         		Cardinality agg4  = bkArea.getAggregations().get("user_count_area");
         		String areaCode = bkArea.getKeyAsString();//区域同频道
         		long userCountArea = agg4.getValue();//频道-区域分类下的用户数   统计频道用户数和各区域用户数为各区域相加
         		System.out.println("userCount:"+userCount+"**userCountArea:"+userCountArea);
         		//构建写入
         		try {
					indexBuilder.setSource(jsonBuilder().startObject()
														.field("service_id",serviceId)
														.field("area_code",areaCode)
														.field("user_count",userCountArea)
														.field("audience_rate",(float)userCountArea/userCount)
														.field("date",dateEnd)
														.endObject());
					System.out.println(jsonBuilder().startObject()
														.field("service_id",serviceId)
														.field("area_code",areaCode)
														.field("user_count",userCountArea)
														.field("audience_rate",(float)userCountArea/userCount)
														.field("date",dateEnd)
														.endObject().string());
					//执行写入
					IndexResponse response = indexBuilder.get();
					System.out.println(response.getResult());
				} catch (IOException e) {
					e.printStackTrace();
				}
         	}
         	
         };
		
	}
}
*/