package com.avit.itdap.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpUtils {
	
	public final static int CONNECT_TIME_OUT= 5000;
	//从connect Manager获取Connection 超时时间
	public final static int REQUEST_TIME_OUT= 2000;
	public final static int READ_TIME_OUT= 50000;
	
	public static String doGet(String url){
		String resultString = "";
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url); 
		RequestConfig requestConfig = RequestConfig.custom()      
		        .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(REQUEST_TIME_OUT)      
		        .setSocketTimeout(READ_TIME_OUT).build(); 
		httpGet.setConfig(requestConfig);
		try{
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity!=null){
				resultString=EntityUtils.toString(entity);
			}
		}catch(Exception e){
			
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(resultString);
		return resultString;
	}
	
	public static String doPost(String url,String json){
		String resultString = "";
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HttpPost post = new HttpPost(url); 
		RequestConfig requestConfig = RequestConfig.custom()      
		        .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(REQUEST_TIME_OUT)      
		        .setSocketTimeout(READ_TIME_OUT).build(); 
		post.setConfig(requestConfig);
		try{
			StringEntity requestEntity = new StringEntity(json, "utf-8");
	        post.setEntity(requestEntity);
	        post.setHeader(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_JSON.toString());
			CloseableHttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			if(entity!=null){
				resultString=EntityUtils.toString(entity);
			}
		}catch(Exception e){
			
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(resultString);
		return resultString;
	}
	public static void main(String[] args) {
		System.out.println(ContentType.APPLICATION_JSON.toString());
	}
}