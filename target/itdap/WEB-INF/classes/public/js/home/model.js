var deviceUtil = {deviceKibanaPath:{}};

//获取上下文
deviceUtil.setKibanaPath = function ()
{
     /*  $.ajax(
	   {
		    type: 'GET',
		    url: "/getKibanaPath" ,
		    data: {} ,
		    dataType: "json",
		    async:false,
		    success: function (data)
		    {
		    	deviceUtil.deviceKibanaPath = data;
		    },
		    error: function (err)
		    {
		    	alert("error");
		    },
		});*/
       
       deviceUtil.deviceKibanaPath = {"actual100":"http://192.168.2.202:5601/app/kibana#/visualize/edit/c3c76630-9122-11e7-8310-4706f3445e01?embed=true&_g=(refreshInterval:(display:on,pause:!t,value:10),time:(from:'2017-07-15T01:35:25.131Z',mode:absolute,to:'2017-07-25T01:50:25.131Z'))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'beat.name:%22CDNCS1%22')),uiState:(spy:(mode:(fill:!f,name:table)),vis:(colors:(%E9%A2%91%E9%81%93%E6%B5%81%E5%85%A5:%230A50A1,%E9%A2%91%E9%81%93%E6%B5%81%E5%87%BA:%23EAB839),legendOpen:!t)),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E9%A2%91%E9%81%93%E6%B5%81%E5%85%A5,field:system.cpu.system.pct),schema:metric,type:avg),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E9%A2%91%E9%81%93%E5%90%8D%E7%A7%B0%EF%BC%9ACCTV-1,extended_bounds:(),field:'@timestamp',interval:auto,min_doc_count:1),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(customLabel:%E9%A2%91%E9%81%93%E6%B5%81%E5%87%BA,field:system.memory.used.pct),schema:metric,type:avg)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E9%A2%91%E9%81%93%E5%90%8D%E7%A7%B0%EF%BC%9ACCTV-1),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!t,style:(color:%23eee),valueAxis:ValueAxis-1),interpolate:linear,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E9%A2%91%E9%81%93%E6%B5%81%E5%85%A5),drawLinesBetweenPoints:!t,interpolate:linear,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1),(data:(id:'3',label:%E9%A2%91%E9%81%93%E6%B5%81%E5%87%BA),drawLinesBetweenPoints:!t,interpolate:linear,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:2734',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:''),type:value))),title:ITPAD_%E5%AE%9E%E6%97%B6%E7%9B%B4%E6%92%AD03,type:line))"};
};

//下拉事件
deviceUtil.graphEvent = function ()
{
	var deviceName = $("#deveceName").val();
	var type = $("#kibanaWidth").val();
	deviceUtil.showKibanaGraph(deviceName,type);
};

//显示图表
deviceUtil.showKibanaGraph = function (queryString,type)
{
	var url = deviceUtil.deviceKibanaPath.actual100;
	//设置开始时间
	url = url.replace("${fromTime}","");
	//设置结束时间
	url = url.replace("${toTime}","");
	//设置设备关键字
	url = url.replace("${queryString}",queryString);
	$("#kibanaFrameDiv").html("").append('<iframe id="kibanaFrame" name="kibanaFrame" src="'+url+'"  border="1"	marginwidth="0" marginheight="0" height="620" width="1000" style="border:1px #dcdcdc solid"></iframe>');
};
