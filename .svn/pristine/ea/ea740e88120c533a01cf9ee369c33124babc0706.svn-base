package com.avit.itdap.repository.elastic.search;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;

/**
 * 向ES发起请求的公用接口类
 * @author hudongyu
 * @date 2017年12月4日
 */
public class SearchUtil {

	/**
	 * 指定查询索引，生成查询builder
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static SearchRequestBuilder getBuilder(TransportClient client, String indexName){
		return client.prepareSearch(indexName).setSize(0);
	}

	
	/**
	 * 带分页的builder
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static SearchRequestBuilder getBuilderFroPageBean(TransportClient client, String indexName,int pageNo,int pageSize){
		//待实现
		return null;
	}
	
	
	
	/**
	 * 根据构建的builder执行查询，返回查询结果
	 * @param builder
	 * @return
	 */
	public static SearchResponse getResponse(SearchRequestBuilder builder){
		return builder.get();
	}

}
