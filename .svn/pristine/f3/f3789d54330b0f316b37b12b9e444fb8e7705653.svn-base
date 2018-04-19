package com.avit.itdap.repository.elastic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TransportClient几个测试以及代码阅读结论
 * 1.TransportClient内部采用基于netty和线程池的异步调度，对外可以使用单例模式
 * 2.保证单例模式的线程安全
 * 3.与ES断开连接后会抛出异常，需要在调用时注意异常处理
 * 4.经测试，内部有连接重建机制，拔断网线第一次请求异常后，重连网线可以恢复正常工作。
 * 
 * 采用具有双重检查锁的单例模式实现client的创建与使用
 * 
 * @author hudongyu
 * @date 2017年12月4日
 */
@Component
public class ClientFactory {
	
	private static Logger logger = LoggerFactory.getLogger(ClientFactory.class);
	
	volatile private static TransportClient esClient;
	//集群名称
	
	private static String clusterName;
	
	//ip列表，逗号分隔
	
	private static String clusterIps;
	
	private static final int socketPort=9300;
	
	
	public static TransportClient getClient(){
		 if(esClient == null){
			 synchronized(TransportClient.class){
				 if(esClient == null){
					 logger.debug("************开始初始化ES连接*************");
					//设置集群名称
					 if(clusterName==null||clusterName.length()==0){
						 logger.error("************ES集群名称配置异常！请确认配置项后重启系统*************");
						 return null;
					 }
			         Settings settings = Settings.builder().put("cluster.name", clusterName).build();
			         esClient = new PreBuiltTransportClient(settings);
					 logger.debug("************ES设备列表："+clusterIps+"*************");
					 if(clusterIps==null||clusterIps.length()==0||checkIps()){
						 logger.error("************ES设备列表配置异常！请确认配置项后重启系统*************");
						 return null;
					 }
					 try{
						 for(String ip:clusterIps.split(",")){
								 esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), socketPort));
						 }
					 }catch(UnknownHostException e){
						 e.printStackTrace();
						 logger.error("解析设备ip--"+e.getMessage()+"--失败,请确认相关配置");
					 }
					 return esClient;
				 }
			 }
		 }
		 return esClient;
	}
	
	private static boolean checkIps(){
		//校验IP地址的正则表达式
		String rex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		for(String ip:clusterIps.split(",")){
			if(!ip.matches(rex)){
				return true;
			}
		}
		return false;
	}

	public String getClusterName() {
		return clusterName;
	}

	@Value("${es.cluster.name}")
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getClusterIps() {
		return clusterIps;
	}

	@Value("${es.cluster.ips}")
	public void setClusterIps(String clusterIps) {
		this.clusterIps = clusterIps;
	}
	
	
	
}
