/*package com.avit.itdap.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avit.itdap.bean.report.AssetReport;
import com.avit.itdap.bean.report.CategoryReport;
import com.avit.itdap.bean.report.PlayBackReport;
import com.avit.itdap.common.utils.ListUtils;
import com.avit.itdap.common.utils.LogConstants;
import com.avit.itdap.service.PlayBackReportService;
import com.avit.itdap.task.DataTreatingTask2;

@RestController
public class PlayBackReportController {
	private static Logger logger = LoggerFactory
			.getLogger(PlayBackReportController.class);
	
	@Autowired
	DataTreatingTask2 task;
	
	@Autowired
	PlayBackReportService playBackReportService;
	
	@RequestMapping(value = "/playBackReport", method = RequestMethod.GET)
	public List<PlayBackReport> getPlayBackReport(
			@RequestParam(value = "serviceId",defaultValue = "0", required = false) String serviceId,
			@RequestParam(value = "date", required = true) String beginTime,
			@RequestParam(value = "model", required = true) String model,
			@RequestParam(value = "areaCode", defaultValue = "0", required = false) String areaCode,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size,
			@RequestParam(value = "orderBy", defaultValue = "userCount", required = false) String orderBy) {
		List<PlayBackReport> list = new ArrayList<PlayBackReport>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try{
			calendar.setTime(sdf.parse(beginTime));
			long bt = calendar.getTimeInMillis();
			switch (model) {
			case "day":
				calendar.add(Calendar.DATE, +1);
				break;
			case "week":
				calendar.add(Calendar.WEDNESDAY,+1);
				break;
			case "month":
				calendar.add(Calendar.MONTH,+1);
				break;
			default:
				calendar.add(Calendar.DATE, +1);
				break;
			}
			long et = calendar.getTimeInMillis()-1000;
			Map<String, String> nodeMap = playBackReportService.getPlayBackReportBySql(serviceId, bt, et,areaCode,size,orderBy);
			for(String assetInfo:nodeMap.keySet()){
				PlayBackReport playBackReport = new PlayBackReport();
				String[] assetinfo = assetInfo.split("-");
				String[] reportInfo = nodeMap.get(assetInfo).split("-");
				playBackReport.setEpgName(assetinfo[0]);
				playBackReport.setEpgId(assetinfo[1]);
				playBackReport.setServiceName(assetinfo[2]);
				playBackReport.setUserCount(Integer.parseInt(reportInfo[0]));
				playBackReport.setTimeCount(Integer.parseInt(reportInfo[1]));
				playBackReport.setSumDuration(Long.parseLong(reportInfo[2]));
				playBackReport.setAvgDuration(playBackReport.getSumDuration()/playBackReport.getTimeCount());
				list.add(playBackReport);
			}
			ListUtils.sort(list, false,orderBy);
			if(size+1<list.size()){
				return list.subList(0, size+1);
			}else{
				return list;
			}
		}catch(Exception e){
			logger.error(LogConstants.F_O_EXCEPTION, "search PlayBackReport",
					"error", e.getMessage(), e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sdf.format(calendar.getTime()));
		calendar.add(Calendar.WEDNESDAY, +1);
		System.out.println(sdf.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, +1);
		System.out.println(sdf.format(calendar.getTime()));
	}
	
}
*/