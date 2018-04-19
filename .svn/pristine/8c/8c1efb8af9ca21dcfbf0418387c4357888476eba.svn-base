package com.avit.itdap.repository.elastic;
import java.net.InetAddress;
import java.util.Date;

import org.apache.lucene.store.SleepingLockWrapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregator;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class TestEsClient {
	/**
	 * 
	 * 实现使用java-API进行请求并解析对应返回
	 * 
	 * 请求内容：
	 * GET /itdap_test1_index/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "range": {
            "start_time": {
              "lt": "2017-11-24 4:00:00",
              "format": "yyyy-MM-dd HH:mm:ss||epoch_millis"
            }
          }
        },
        {
          "range": {
            "end_time": {
              "gte": "2017-11-24 3:50:00",
              "format": "yyyy-MM-dd HH:mm:ss||epoch_millis"
            }
          }
        }
      ]
    }
  },
  "size": 0,
  "aggs": {
    "serviceIds": {
      "terms": {
        "field": "live.service_id.keyword",
        "size": 100
      },
      "aggs": {
        "user_count": {
          "cardinality": {
            "field": "user_code.keyword"
          }
        },
        "areas": {
          "terms": {
            "field": "area_code.keyword"
          },
          "aggs": {
            "user_count_area": {
              "cardinality": {
                "field": "user_code.keyword"
              }
            }
          }
        }
      }
    }
  }
}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @author hudongyu
	 * @param args
	 */
    public static void main(String[] args) {

        try {

            //设置集群名称
            Settings settings = Settings.builder().put("cluster.name", "test-cluster").build();
            //创建client
            @SuppressWarnings("resource")
			TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.202"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.203"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.204"), 9300));
            SearchResponse sr = client.prepareSearch("itdap_test1_index").setTypes("live")
            	    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("start_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").lte("2017-11-24 4:00:00")).must(QueryBuilders.rangeQuery("end_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").gte("2017-11-24 3:50:00")))
            	    .setSize(0)
            	    .addAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword"))
            	    .addAggregation(AggregationBuilders.terms("serviceIds").field("live.service_id.keyword").size(2000).subAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword")).subAggregation(AggregationBuilders.terms("areas").field("area_code.keyword").subAggregation(AggregationBuilders.cardinality("user_count_area").field("user_code.keyword"))))
            	    .get();
            System.out.println(client.prepareSearch("itdap_test1_index").setTypes("live")
            	    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("start_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").lte("2017-11-24 4:00:00")).must(QueryBuilders.rangeQuery("end_time").format("yyyy-MM-dd HH:mm:ss||epoch_millis").gte("2017-11-24 3:50:00")))
            	    .setSize(0)
            	    .addAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword"))
            	    .addAggregation(AggregationBuilders.terms("serviceIds").field("live.service_id.keyword").size(2000).subAggregation(AggregationBuilders.cardinality("user_count").field("user_code.keyword")).subAggregation(AggregationBuilders.terms("areas").field("area_code.keyword").subAggregation(AggregationBuilders.cardinality("user_count_area").field("user_code.keyword"))))
            	    .toString());
            Cardinality agg0 = sr.getAggregations().get("user_count");
            System.out.println("*******************"+agg0.getValue());
            Terms agg1 = sr.getAggregations().get("serviceIds");
            for(Bucket bk:agg1.getBuckets()){
            	Cardinality agg2  = bk.getAggregations().get("user_count");
            	System.out.println("|serviceId:"+bk.getKeyAsString()+",user_count:"+agg2.getValue());
            	Terms agg3 = bk.getAggregations().get("areas");
            	for(Bucket bkArea:agg3.getBuckets()){
            		Cardinality agg4  = bkArea.getAggregations().get("user_count_area");
            		System.out.println("|--areaCode:"+bkArea.getKeyAsString()+",user_count_area:"+agg4.getValue());
            	}
            	
            };
            
        	// Get your facet results
        	System.out.println(sr.toString());

            //测试写入数据
        	for(int i=0;i<10;i++){
        		IndexResponse response = client.prepareIndex("twitter", "tweet", "2")
        				.setSource(jsonBuilder()
        						.startObject()
        						.field("user", "kimchy")
        						.field("postDate", new Date())
        						.field("message", "trying out Elasticsearch times:"+i)
        						.endObject()
        						)
        				.get();  
        		//import static org.elasticsearch.common.xcontent.XContentFactory.*; 需要静态引用
        		XContentBuilder builder = jsonBuilder()
        			    .startObject()
        			        .field("user", "kimchy")
        			        .field("postDate", new Date())
        			        .field("message", "trying out Elasticsearch")
        			    .endObject();
        		System.out.println(builder.string());
        		
        		System.out.println(response.getResult());
        	}
            
           
            	//关闭client
            //client.close();
        

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}