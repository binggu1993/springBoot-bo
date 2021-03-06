/*package com.avit.itdap.controller.report;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.coyote.ajp.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.report.*;
import com.avit.itdap.bean.system.AssetInfo;
import com.avit.itdap.bean.xml.CategoryInfo;
import com.avit.itdap.common.utils.Constant;
import com.avit.itdap.common.utils.ListUtils;
import com.avit.itdap.common.utils.LogConstants;
import com.avit.itdap.repository.data.CategoryRepository;
import com.avit.itdap.repository.system.AssetInfoRepository;
import com.avit.itdap.task.DataTreatingTask2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class DemandController {

	private static Logger logger = LoggerFactory
			.getLogger(DemandController.class);

	@Autowired
	DataTreatingTask2 task;
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	AssetInfoRepository assetInfoRepository;

	// 栏目访问量统计（含专题），各栏目收看内容时长统计，点播平均收视时长、总时长。
	// 点播次数份额：收视次数份额=栏目（节目）点播次数/同时段总点播次数。
	// 点播时长份额：收视时长份额=栏目（节目）点播时长/同时段总点播时长。
	// 点播人数份额：收视人数份额=栏目（节目）点播人数/同时段总点播人数。
	// 全地区点播栏目的收视占比统计。
	// 约定0为所有栏目时长总数，-1为其他栏目（未上报）
	// 含用户数
	// 按点播次数
	@RequestMapping(value = "/getCategoryReport", method = RequestMethod.GET)
	public List<CategoryReport> getCategoryReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			// @RequestParam(value = "pID",defaultValue="0", required = false)
			// String parentID,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			Map<String, String> nodeMap = getCategoryUse(areaCode, bt, et,
					spCode);
			// 从数据库中取得该SP的栏目信息，栏目下文件总时长，组装返回值
			// 注意，栏目需包含子栏目的逐层运算
			
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value = "/getCategoryCount", method = RequestMethod.GET)
	public List<CategoryReport> getCategoryCountReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "100", required = false) int size){
		List<CategoryReport> list = new ArrayList<CategoryReport>();
		List<CategoryReport> tmpList = new ArrayList<CategoryReport>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			Map<String, String> nodeMap = getCategoryCount(areaCode, bt, et,
					spCode);
			List<CategoryInfo> infos = categoryRepository.findCategoryBySpCode(spCode);
			boolean haveRoot = false;
			for(CategoryInfo info:infos){
				if(info.getCategoryId()==Constant.CATEGROY_ROOT){
					haveRoot = true;
				}
				CategoryReport report = new CategoryReport();
				report.setNodeId(info.getCategoryId());
				report.setNodeName(info.getCategoryName());
				report.setParentId(info.getParentId());
				list.add(report);
				if(nodeMap.containsKey(info.getCategoryId())){
					report.setUseCount(Long.parseLong(nodeMap.get(info.getCategoryId())));
					nodeMap.remove(info.getCategoryId());
					tmpList.add(report);
				}
			}
			//填充根节点
			if(!haveRoot){
				CategoryReport report = new CategoryReport();
				report.setNodeId(Constant.CATEGROY_ROOT);
				report.setNodeName("root");
				report.setParentId(Constant.CATEGROY_ROOT_PARENT);
				list.add(report);
			}
			//填充未在栏目中其他栏目
			CategoryReport report = new CategoryReport();
			report.setNodeId(Constant.CATEGROY_ANOTHER);
			report.setNodeName("other");
			report.setParentId(Constant.CATEGROY_ROOT);
			long other = 0L;
			Set<String> keySet = nodeMap.keySet();
			for(String key:keySet){
				other=other+Long.parseLong(nodeMap.get(key));
			}
			report.setUseCount(other);
			list.add(report);
			tmpList.add(report);
			//倒叙填装
			descAdd(list,tmpList,1);			
			
			// 从数据库中取得该SP的栏目信息，栏目下文件总时长，组装返回值
			// 注意，栏目需包含子栏目的逐层运算
			
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		//排序
		//Collections.sort(list, );
		ListUtils.sort(list, false, "useCount");
		if(size+1<list.size()){
			return list.subList(0, size+1);
		}else{
			return list;
		}
	}
	
	private void descAdd(List<CategoryReport> resource,List<CategoryReport> recordList,int type){
		List<CategoryReport> tmpList = new ArrayList<CategoryReport>();
		for(CategoryReport record:recordList){
			for(CategoryReport report:resource){
				if(record.getParentId().equals(report.getNodeId())){
					if(type==1){
						report.setUseCount(report.getUseCount()+record.getUseCount());
						tmpList.add(report);
					}else if(type==2){
						report.setUseDuration(report.getUseDuration()+record.getUseDuration());
						tmpList.add(report);
					}else{
						report.setUseUser(report.getUseUser()+record.getUseUser());
						tmpList.add(report);
					}
					break;
				}
			}
		}
		if(tmpList.size()==0||(tmpList.size()==1&&tmpList.get(0).getNodeId()=="0")){
			
		}else {
			descAdd(resource,tmpList,type);
		}
	}
	
	@RequestMapping(value = "/getCategoryDuration", method = RequestMethod.GET)
	public List<CategoryReport> getCategoryDurationReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "100", required = false) int size){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<CategoryReport> list = new ArrayList<CategoryReport>();
		List<CategoryReport> tmpList = new ArrayList<CategoryReport>();
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			Map<String, String> nodeMap = getCategoryDuration(areaCode, bt, et,
					spCode);
			// 从数据库中取得该SP的栏目信息，栏目下文件总时长，组装返回值
			// 注意，栏目需包含子栏目的逐层运算
			List<CategoryInfo> infos = categoryRepository.findCategoryBySpCode(spCode);
			boolean haveRoot = false;
			for(CategoryInfo info:infos){
				if(info.getCategoryId()==Constant.CATEGROY_ROOT){
					haveRoot = true;
				}
				CategoryReport report = new CategoryReport();
				report.setNodeId(info.getCategoryId());
				report.setNodeName(info.getCategoryName());
				report.setParentId(info.getParentId());
				list.add(report);
				if(nodeMap.containsKey(info.getCategoryId())){
					report.setUseDuration(Long.parseLong(nodeMap.get(info.getCategoryId())));
					nodeMap.remove(info.getCategoryId());
					tmpList.add(report);
				}
			}
			//填充根节点
			if(!haveRoot){
				CategoryReport report = new CategoryReport();
				report.setNodeId(Constant.CATEGROY_ROOT);
				report.setNodeName("root");
				report.setParentId(Constant.CATEGROY_ROOT_PARENT);
				list.add(report);
			}
			//填充未在栏目中其他栏目
			CategoryReport report = new CategoryReport();
			report.setNodeId(Constant.CATEGROY_ANOTHER);
			report.setNodeName("other");
			report.setParentId(Constant.CATEGROY_ROOT);
			long other = 0L;
			Set<String> keySet = nodeMap.keySet();
			for(String key:keySet){
				other=other+Long.parseLong(nodeMap.get(key));
			}
			report.setUseDuration(other);
			list.add(report);
			tmpList.add(report);
			//倒叙填装
			descAdd(list,tmpList,2);
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		ListUtils.sort(list, false, "useDuration");
		if(size+1<list.size()){
			return list.subList(0, size+1);
		}else{
			return list;
		}
	}
	
	@RequestMapping(value = "/getCategoryUser", method = RequestMethod.GET)
	public List<CategoryReport> getCategoryUserReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			// @RequestParam(value = "pID",defaultValue="0", required = false)
			// String parentID,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "100", required = false) int size){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<CategoryReport> list = new ArrayList<CategoryReport>();
		List<CategoryReport> tmpList = new ArrayList<CategoryReport>();
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			Map<String, String> nodeMap = getCategoryUser(areaCode, bt, et,
					spCode);
			// 从数据库中取得该SP的栏目信息，栏目下文件总时长，组装返回值
			// 注意，栏目需包含子栏目的逐层运算
			List<CategoryInfo> infos = categoryRepository.findCategoryBySpCode(spCode);
			boolean haveRoot = false;
			for(CategoryInfo info:infos){
				if(info.getCategoryId()==Constant.CATEGROY_ROOT){
					haveRoot = true;
				}
				CategoryReport report = new CategoryReport();
				report.setNodeId(info.getCategoryId());
				report.setNodeName(info.getCategoryName());
				report.setParentId(info.getParentId());
				list.add(report);
				if(nodeMap.containsKey(info.getCategoryId())){
					report.setUseUser(Long.parseLong(nodeMap.get(info.getCategoryId())));
					nodeMap.remove(info.getCategoryId());
					tmpList.add(report);
				}
			}
			//填充根节点
			if(!haveRoot){
				CategoryReport report = new CategoryReport();
				report.setNodeId(Constant.CATEGROY_ROOT);
				report.setNodeName("root");
				report.setParentId(Constant.CATEGROY_ROOT_PARENT);
				list.add(report);
			}
			//填充未在栏目中其他栏目
			CategoryReport report = new CategoryReport();
			report.setNodeId(Constant.CATEGROY_ANOTHER);
			report.setNodeName("other");
			report.setParentId(Constant.CATEGROY_ROOT);
			long other = 0L;
			Set<String> keySet = nodeMap.keySet();
			for(String key:keySet){
				other=other+Long.parseLong(nodeMap.get(key));
			}
			report.setUseUser(other);
			list.add(report);
			tmpList.add(report);
			//倒叙填装
			descAdd(list,tmpList,3);
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		ListUtils.sort(list, false, "useUser");
		if(size+1<list.size()){
			return list.subList(0, size+1);
		}else{
			return list;
		}
	}
	//影片排行
	@RequestMapping(value = "/getAssetReport", method = RequestMethod.GET)
	public List<AssetReport> getAssetReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			// @RequestParam(value = "pID",defaultValue="0", required = false)
			// String parentID,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "100", required = false) int size) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			List<AssetReport> list = new ArrayList<AssetReport>();
			Map<String, String> nodeMap = getAssetCount(areaCode, bt, et,
					spCode,size);
			
			// 从数据库中取得该SP的影片信息，影片时长，组装返回值
			for(String assetId:nodeMap.keySet()){
				AssetReport assetRep = new AssetReport();
				AssetInfo assetInfo  = assetInfoRepository.findUniquenByAssetId(assetId);
				if(assetInfo!=null){
					assetRep.setAssetId(assetId);
					assetRep.setAssetName(assetInfo.getAssetName());
					assetRep.setCount(Integer.parseInt(nodeMap.get(assetId)));
					list.add(assetRep);
				}
			}
			
			ListUtils.sort(list, false, "count");
			if(size+1<list.size()){
				return list.subList(0, size+1);
			}else{
				return list;
			}
			
			
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		return null;
	}	
	
	//影片播放完整度排行
	//
	@RequestMapping(value = "/getAssetIntegrityReport", method = RequestMethod.GET)
	public List<AssetReport> getAssetIntegrityReport(
			@RequestParam(value = "spCode", required = true) String spCode,
			// @RequestParam(value = "pID",defaultValue="0", required = false)
			// String parentID,
			@RequestParam(value = "bt", required = true) String beginTime,
			@RequestParam(value = "et", required = true) String endTime,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "100", required = false) int size) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			long bt = sdf.parse(beginTime).getTime();
			long et = sdf.parse(endTime).getTime();
			List<AssetReport> list = new ArrayList<AssetReport>();
			Map<String, String> nodeMap = getAvgDuration(areaCode, bt, et,
					spCode,size);
			
			// 从数据库中取得该SP的影片信息，影片时长，组装返回值
			for(String assetId:nodeMap.keySet()){
				AssetReport assetRep = new AssetReport();
				AssetInfo assetInfo  = assetInfoRepository.findUniquenByAssetId(assetId);
				if(assetInfo!=null){
					assetRep.setAssetId(assetId);
					assetRep.setAssetName(assetInfo.getAssetName());
					assetRep.setDuration(Double.parseDouble(assetInfo.getAssetLength()+""));
					assetRep.setAvgDuration(Double.parseDouble(nodeMap.get(assetId)));
					assetRep.setIntegrity(assetRep.getAvgDuration()/assetRep.getDuration());
					list.add(assetRep);
				}
			}
			
			ListUtils.sort(list, false, "integrity");
			if(size+1<list.size()){
				return list.subList(0, size+1);
			}else{
				return list;
			}
			
			
		} catch (Exception e) {
			logger.error(LogConstants.F_O_EXCEPTION, "search category by sp",
					"error", e.getMessage(), e);
		}
		return null;
	}	
	// 栏目使用用户数
	private Map<String, String> getCategoryUser(String areaCode, long beginTime,
			long endTime, String spCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT node_id.keyword,count(distinct user_code.keyword) as tmpNum3 FROM demand_index where operator_name='")
				.append(spCode).append("' and start_time between ")
				.append(beginTime).append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by node_id.keyword  limit 9999");
		logger.debug(LogConstants.F_O_MN_MV, "search category by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("node_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmpNum3");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		return nodeMap;
	}

	// 栏目使用时长
	private Map<String, String> getCategoryDuration(String areaCode, long beginTime,
			long endTime, String spCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT node_id.keyword,sum(duration) as tmpNum FROM demand_index where operator_name='")
				.append(spCode).append("' and start_time between ")
				.append(beginTime).append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by node_id.keyword  limit 9999");
		logger.debug(LogConstants.F_O_MN_MV, "search category by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("node_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmpNum");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		return nodeMap;
	}
	// 栏目使用次数
	private Map<String, String> getCategoryCount(String areaCode, long beginTime,
			long endTime, String spCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT node_id.keyword,count(*) as tmpNum FROM demand_index where operator_name='")
				.append(spCode).append("' and start_time between ")
				.append(beginTime).append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by node_id.keyword  limit 9999");
		logger.debug(LogConstants.F_O_MN_MV, "search category by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("node_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmpNum");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		return nodeMap;
	}
	
	
	// 栏目使用时长,次数，用户数
	private Map<String, String> getCategoryUse(String areaCode, long beginTime,
			long endTime, String spCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT node_id.keyword,sum(duration) as tmpNum,count(*) as tmpNum2,count(distinct user_code.keyword) as tmpNum3 FROM demand_index where operator_name='")
				.append(spCode).append("' and start_time between ")
				.append(beginTime).append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by node_id.keyword  limit 9999");
		logger.debug(LogConstants.F_O_MN_MV, "search category by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("node_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmpNum");
		groupValues.add("tmpNum2");
		groupValues.add("tmpNum3");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		return nodeMap;
	}
	
	*//**
	 * 查询影片平均收看时长（按媒资区分）
	 * @param areaCode
	 * @param beginTime
	 * @param endTime
	 * @param spCode
	 * @return
	 * @throws Exception 
	 *//*
	private Map<String,String> getAvgDuration(String areaCode, long beginTime,
			long endTime, String spCode,int size) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT avg(duration) as tmp FROM demand_index ")
		   .append( "where operator_name='").append(spCode)
		   .append("' and start_time between ").append(beginTime)
		   .append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by asset_id.keyword  limit ").append(size).append(size).append(" order by tmp desc");
		logger.debug(LogConstants.F_O_MN_MV, "search asset avg duration by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("asset_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmp");
		Map<String, String> nodeMap = parseGroupData(result, groups,
				groupValues);
		return nodeMap;
	}

	private Map<String,String> getAssetCount(String areaCode, long beginTime,
			long endTime, String spCode,int size)throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) AS tmp FROM demand_index ")
		   .append( "where operator_name='").append(spCode)
		   .append("' and start_time between ").append(beginTime)
		   .append(" and ").append(endTime);
		if (!"0".equals(areaCode)) {
			sql.append(" and area_code='").append(areaCode).append("'");
		}
		sql.append("  group by asset_id.keyword  limit ").append(size).append(" order by tmp desc");
		logger.debug(LogConstants.F_O_MN_MV, "search asset avg duration by sp",
				"build sql", "sql", sql);
		String result = task.getEsDataBySql(sql.toString());
		List<String> groups = new ArrayList<String>();
		groups.add("asset_id.keyword");
		List<String> groupValues = new ArrayList<String>();
		groupValues.add("tmp");
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
		JsonNode bucketNode = node.get("aggregations")
				.get(groupNames.get(listIndex)).get("buckets");
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