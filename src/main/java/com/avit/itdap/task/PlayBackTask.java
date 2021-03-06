/*package com.avit.itdap.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import com.avit.itdap.bean.live.ChannelEpg;
import com.avit.itdap.bean.live.PlayBackBean;
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
 * 
 * @author hudongyu
 * @date 2017年12月4日
 *//*

@Component
public class PlayBackTask {
	private static Logger logger = LoggerFactory.getLogger(PlayBackTask.class);

	@Value("${es.playback.index.name}")
	public String indexName;

	@Value("${es.playback.index.trans.name}")
	public String transIndexName;

	@Value("${es.cluster.ips}")
	public String esHosts;
	

	// 偏移量
	@Value("${offset}")
	public int offset;

	@Autowired
	SystemRepository systemRepository;
	@Autowired
	AreaRepository areaRepository;
	@Autowired
	ChannelRepository channelRepository;

	private static final int cycleTime = 30000;// 轮询时间/分片大小（ms）

	*//**
	 * 回看数据清洗
	 *//*
	@Scheduled(fixedRate = cycleTime)
	public void playbackTreat() {
		// 初始化数据
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			SystemBean system = systemRepository.findOne(1L);
			//while(date.equals(system.getLastPlaybackTime())){
			while (date.after(system.getLastPlaybackTime())) {
				Map<String, PlayBackBean> map = new HashMap<String, PlayBackBean>();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(system.getLastPlaybackTime());
				Date doDate = calendar.getTime();
				logger.info(LogConstants.F_O_MN_MV, "playbackTreat", "date",
						doDate);

				List<Channel> channelList = channelRepository.findByChannelType(1);
				long beginTime = doDate.getTime();
				long endTime = doDate.getTime() + 86399000;
				for (Channel channel : channelList) {
					PlayBackBean bean = new PlayBackBean();
					bean.setChannelName(channel.getChannelName());
					bean.setServiceId(channel.getServiceId());
					List<ChannelEpg> lists = new ArrayList<ChannelEpg>();
					bean.setDate(sdf.format(doDate));
					bean.setEpgs(lists);
					map.put(channel.getServiceId() + "", bean);
				}
				// SELECT epg_id.keyword,service_id.keyword,count(*) as
				// tmpNum,count(distinct user_code.keyword) as
				// tmpNum2,sum(duration) as tmpNum3 FROM playback_index where
				// start_time between 1313536544500 and 1514577029613 group by
				// epg_id.keyword,service_id.keyword limit 0,9999
				// 获取用户数
				String sql = "SELECT epg_id.keyword,service_id.keyword,count(*) as tmpNum,count(distinct user_code.keyword) as tmpNum2,sum(duration) as tmpNum3 FROM "+indexName+" where start_time between "
						+ beginTime
						+ " and "
						+ endTime
						+ " group by service_id.keyword,epg_id.keyword,epg_name.keyword limit 0,9999";
				logger.debug(LogConstants.F_O_MN_2MV, "playbackTreat", "query",
						"beginTime", beginTime, "endTime", endTime);
				String result = getEsDataBySql(sql);
				updateData(result, map);

				// 更新
				// 获取client对象
				Set<String> keySet = map.keySet();
				TransportClient client = ClientFactory.getClient();
				IndexRequestBuilder indexBuilder = IndexUtil.getBuilder(client,
						transIndexName, "data");
				for (String key : keySet) {
					PlayBackBean bean = map.get(key);
					for (ChannelEpg epg : bean.getEpgs()) {
						indexBuilder.setSource(jsonBuilder().startObject()
								.field("service_id", bean.getServiceId())
								.field("service_name", bean.getChannelName())
								.field("epg_id", epg.getEpgid())
								.field("epg_name", epg.getEpgName())
								.field("user_count", epg.getUserCount())
								.field("duration_time", epg.getDuration())
								.field("agv_duration", epg.getAgvDuration())
								.field("time_count", epg.getCount())
								.field("date", sdf.parse(bean.getDate()))
								.endObject());
						indexBuilder.get();
					}
				}
				logger.debug(LogConstants.F_O_MN_2MV, "playbackTreat",
						"put to index", "indexName", transIndexName, "size",
						map.size());
				calendar.add(Calendar.DAY_OF_MONTH,1);
				system.setLastPlaybackTime(calendar.getTime());
				systemRepository.save(system);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}// end while
		} catch (Exception ex) {
			logger.error(LogConstants.F_O4CATCH_MN4CLASS_MV_THROWABLE2,
					"error", ex);
		}

	}

	*//**
	 * 
	 * @param queryJson
	 *            查询json
	 * @param namePaths
	 *            需要获取的元素的路径集合
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 *//*
	public static String getElement(String queryJson,
			ArrayList<String> namePaths) throws JsonProcessingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(queryJson);
		JsonNode nodeTmp = node;
		Iterator<String> it = namePaths.iterator();
		while (it.hasNext()) {
			nodeTmp = nodeTmp.get(it.next());
		}
		return nodeTmp.toString();
	}

	*//**
	 * 获取查询json中特定集合使用的scirpt中inline信息
	 * 
	 * @param queryJson
	 *            查询json
	 * @param nickname
	 *            聚合的别名
	 * @param mathod
	 *            聚合的方法
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 *//*
	public static String getScriptInline(String queryJson, String nickname,
			String mathod) throws JsonProcessingException, IOException {
		ArrayList<String> namePaths = new ArrayList<String>();
		namePaths.add("aggregations");
		namePaths.add("live.service_id.keyword");
		namePaths.add("aggregations");
		namePaths.add("area_code.keyword");
		namePaths.add("aggregations");
		namePaths.add(nickname);
		namePaths.add(mathod);
		namePaths.add("script");
		namePaths.add("inline");
		return getElement(queryJson, namePaths);
	}

	public String reWriteSum(String sql) throws JsonProcessingException,
			IOException {
		String queryJson = getQueryJson(sql);
		String inlineString = getScriptInline(queryJson, "tmpNum", "sum")
				.replaceAll("\"", "");
		// 获取变量的值
		int i = inlineString.lastIndexOf(";");
		String variableName = inlineString
				.substring(i + 1, inlineString.length()).trim().split(" ")[1];
		String newInlineString = inlineString + ";return " + variableName + ";";
		String newQueryJson = queryJson.replace(inlineString, newInlineString);
		return newQueryJson;
	}

	private String getQueryJson(String sql) {
		String host = "http://" + esHosts.split(",")[0] + ":9200";
		// RestTemplate restTemplate=new RestTemplate();
		String url = "";
		try {
			url = host + "/_sql/_explain?sql="
					+ URLEncoder.encode(sql, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(LogConstants.F_O4CATCH_MN4CLASS_MV_THROWABLE2,"getQueryJson",e);
		}
		// return restTemplate.exchange(url, HttpMethod.GET, null,
		// String.class).getBody();
		return HttpUtils.doGet(url);
	}

	private long updateData(String str, Map<String, PlayBackBean> map) {
		long result = 0L;
		if (StringUtils.isEmpty(str)) {
			logger.info("updateData str is null");
			return result;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.reader().readTree(str);
			JsonNode liveServiceID = node.get("aggregations")
					.get("service_id.keyword").get("buckets");
			int index = 0;
			while (liveServiceID.has(index)) {
				JsonNode serviceNode = liveServiceID.get(index);
				String serviceId = serviceNode.get("key").textValue();
				JsonNode epgNodes = serviceNode.get("epg_id.keyword").get(
						"buckets");
				if (map.containsKey(serviceId)) {
					List<ChannelEpg> list = map.get(serviceId).getEpgs();
					int epgIndex = 0;
					while (epgNodes.has(epgIndex)) {
						JsonNode epgNode = epgNodes.get(epgIndex);
						String epgId = epgNode.get("key").textValue();
						JsonNode epgNameNodes = epgNode.get("epg_name.keyword")
								.get("buckets");
						int epgNameIndex = 0;
						while (epgNameNodes.has(epgNameIndex)) {
							JsonNode epgNameNode = epgNameNodes
									.get(epgNameIndex);
							String epgName = epgNameNode.get("key").textValue();
							long value = epgNameNode.get("tmpNum").get("value")
									.asLong();
							long value2 = epgNameNode.get("tmpNum2")
									.get("value").asLong();
							long value3 = epgNameNode.get("tmpNum3")
									.get("value").asLong();
							ChannelEpg epg = new ChannelEpg();
							epg.setEpgid(Long.parseLong(epgId));
							epg.setEpgName(epgName);
							epg.setCount(value);
							epg.setUserCount(value2);
							epg.setDuration(value3);
							epg.setAgvDuration(value3 / value);
							list.add(epg);
							epgNameIndex++;
						}
						epgIndex++;
					}
				}
				index++;
			}

		} catch (Exception e) {
			logger.error(LogConstants.F_O4CATCH_MN4CLASS_MV_THROWABLE2,"updateData",e);

		}
		return result;
	}

	public String getEsDataBySql(String sql) throws Exception {
		String host = "http://" + esHosts.split(",")[0] + ":9200";
		// RestTemplate restTemplate=new RestTemplate();
		String url = host + "/_sql?sql=" + URLEncoder.encode(sql, "utf-8");
		return HttpUtils.doGet(url);
		// return restTemplate.exchange(url, HttpMethod.GET, null,
		// String.class).getBody();
	}

	public String postEsDataBysql(String sql) throws JsonProcessingException,
			IOException {
		String json = reWriteSum(sql);
		String host = "http://" + esHosts.split(",")[0] + ":9200";
		String url = host + "/" + indexName + "/_search";
		return HttpUtils.doPost(url, json);
	}	
}
*/