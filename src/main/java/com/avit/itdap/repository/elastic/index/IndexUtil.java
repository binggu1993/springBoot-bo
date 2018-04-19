package com.avit.itdap.repository.elastic.index;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * 写入ES索引的工具类
 * 
 * @author hudongyu
 * @date 2017年12月4日
 */
public class IndexUtil {
	
	/**
	 * 指定index与type，id自动生成
	 * @param client
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	public static IndexRequestBuilder getBuilder(TransportClient client, String indexName,String typeName){
		return client.prepareIndex(indexName, typeName);
	}
	
	
	/**
	 * 指定到确定的index/type/id
	 * @param client
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 */
	public static IndexRequestBuilder getBuilder(TransportClient client, String indexName,String typeName,String id){
		return client.prepareIndex(indexName, typeName,id);
	}
	
	/**
	 * 执行插入 返回结果
	 * @param builder
	 * @return
	 */
	public static IndexResponse getResponse(IndexRequestBuilder builder){
		return builder.get();
	}
	
	/**
	 * 基于json的快速插入操作，当id为空时，采用自动生成策略
	 * @param client
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @param jsonInput
	 * @return
	 */
	public static IndexResponse quicklyIndex(TransportClient client, String indexName,String typeName,String id,String jsonInput){
		if(id!=null){
			return client.prepareIndex(indexName, typeName,id).setSource(jsonInput,XContentType.JSON).get();
		}else{
			return client.prepareIndex(indexName, typeName).setSource(jsonInput,XContentType.JSON).get();
		}
		
	} 
	
	
}
