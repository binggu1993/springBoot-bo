package com.avit.itdap.common.utils.elastic.sql;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 重写sum（四则运算）形式的请求体实现
 * @author hudongyu
 * @date 2017年12月18日
 */
public class SumAnalysis extends BaseAnalysis{
	/**
	 * 
	 * @param esurl es路径，9200端口
	 * @param sql 请求的sql语句
	 * @return 重写后的queryJson
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String reWriteSum(String esurl,String sql) throws JsonProcessingException, IOException{
		String queryJson = getQueryJson(esurl,sql);
		String inlineString = getScriptInline(queryJson,"sumduration","sum").replaceAll("\"", "");
		//获取变量的值
		int i = inlineString.lastIndexOf(";");
		String variableName = inlineString.substring(i+1,inlineString.length()).trim().split(" ")[1];
		String newInlineString = inlineString+";return "+variableName+";";
		String newQueryJson = queryJson.replace(inlineString, newInlineString);
		return newQueryJson;
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		String sql = "SELECT sum(start_time/1000 - 1513306099) as sumduration FROM itdap_index where ((start_time<1513577029613 and end_time is null) and start_time>=1313536544500)";
		System.out.println(reWriteSum("http://192.168.2.202:9200",sql));
	}
}
