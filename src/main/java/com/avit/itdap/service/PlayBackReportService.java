/*package com.avit.itdap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.itdap.common.utils.LogConstants;
import com.avit.itdap.task.DataTreatingTask2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PlayBackReportService {
	private static Logger logger = LoggerFactory.getLogger(PlayBackReportService.class);
	
	@Autowired
	DataTreatingTask2 task;
	
	public Map<String, String> getPlayBackReportBySql(String serviceId, long beginTime,
			long endTime, String areaCode,int size,String orderBy) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(user_count) as userCount,sum(time_count) as timeCount,sum(duration_time) as sumDuration from play_back_trans_index");
		sql.append(" where date between ").append(beginTime).append(" and ").append(endTime);
		if(!"0".equals(serviceId)){
			sql.append(" and service_id = '").append(serviceId).append("'");
		}
		if(!"0".equals(areaCode)){
			sql.append(" and area_code = '").append(areaCode).append("'");
		}
		sql.append(" group by epg_name.keyword,epg_id.keyword,service_name.keyword limit 9999 ").append(" order by ").append(orderBy).append(" desc ");
		logger.debug(LogConstants.F_O_MN_MV, "search PlayBackReport",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("epg_name.keyword");
		groups.add("epg_id.keyword");
		groups.add("service_name.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("userCount");
		groupValues.add("timeCount");
		groupValues.add("sumDuration");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		
		return nodeMap;
	}
	
	private static Map<String, String> parseGroupData(String str,
			List<String> groupNames, List<String> groupValues) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.reader().readTree(str);
			nodeValue("", node, groupNames, 0, result, groupValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void nodeValue(String key, JsonNode node,
			List<String> groupNames, int listIndex, Map<String, String> map,
			List<String> groupValues) {
		JsonNode bucketNode;
		if(listIndex==0){
			bucketNode = node.get("aggregations").get(groupNames.get(listIndex)).get("buckets");
		}else{
			bucketNode = node.get(groupNames.get(listIndex)).get("buckets");
		}
		int index = 0;		
		while (bucketNode.has(index)) {
			String tmpKey= key;
			JsonNode arrayNode = bucketNode.get(index);
			String tmp = arrayNode.get("key").textValue();
			if (StringUtils.isEmpty(key))
				tmpKey = tmp;
			else {
				tmpKey = tmpKey + "-" + tmp;
			}
			if (groupNames.size() == listIndex + 1) {
				String value = "";
				for (String valueName : groupValues) {
					if (StringUtils.isEmpty(value))
						value = arrayNode.get(valueName).get("value")
								.asLong()+"";
					else {
						value = value
								+ "-"
								+ arrayNode.get(valueName).get("value")
										.asLong();
					}
				}
				map.put(tmpKey, value);
			} else {
				nodeValue(tmpKey, arrayNode, groupNames, listIndex + 1, map,
						groupValues);
			}
			index++;
		}
	}
}
*/