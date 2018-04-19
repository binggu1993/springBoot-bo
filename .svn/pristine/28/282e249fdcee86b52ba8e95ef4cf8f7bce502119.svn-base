 angular.module('chartUtil.services',[]).factory('chartUtil', function(avitUtil) {
	    return {
	    	
	    	//初始化频道、频道组和区域
	    	initChannelAndChannelGroups:function(scope)
	    	{
	    		//频道初始化
	               $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/channel/allChannel" ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 scope.channels = data;
							    },
							    error: function (err)
							    {
							    	alert("getAllChannel error...");
							    },
							});
	               
	            	
                	if(! scope.channels)
                		{
								scope.channels = [
									{channelName : "CCTV1", serviceId : "60"},
									{channelName : "CCTV2", serviceId : "82"},
									{channelName : "北京卫视", serviceId : "32"},
									{channelName : "CCTV5", serviceId : "13"}
								];
                		}
	                        	  var channelcheck=[];
	                        	  if(scope.channels.length>2)
	                        	 {
	                        		channelcheck[0]=scope.channels[0].serviceId;
	                        		channelcheck[1]=scope.channels[1].serviceId;
	                        		scope.selectChannels[0]=scope.channels[0];
	                        		scope.selectChannels[1]=scope.channels[1];
	                        	 }else if(scope.channels.length>1)
	                        	 {
	                        		channelcheck[0]=scope.channels[0].serviceId;
	                        		scope.selectChannels[0]=scope.channels[0];
	                        	 }
	 	                          //被选中条件：ng-checked的结果为true
	 	                          scope.isSelected = function(id){          
	 	                              return channelcheck.indexOf(id)!=-1; 
	                        	 }
	               
	               //频道组初始化
	               $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/channel/allChannelGroup" ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 scope.channelgroups = data;
							    },
							    error: function (err)
							    {
							    	alert("getAllChannelGroup error...");
							    },
							});
	              
	    	},
	        
	    	
	    	intArea:function(scope)
	    	{
	               //区域初始化
	               $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/allAreas" ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 scope.areas=data;
							    },
							    error: function (err)
							    {
							    	alert("getAllAreas error...");
							    },
							});
	   			
						if(!scope.areas)
						{
						scope.areas=[{areaName : "所有区域", areaCode : "0"},{areaName : "合肥", areaCode : "01"},
							{areaName : "宿州", areaCode : "02"}];
						}
						
                    scope.selectedarea = function(code){          
                         return "0".indexOf(code)!=-1; 
              	 }
                  scope.theArea="0";
	    	},
	    	
	    	initAptitude:function(scope)
	    	{
	    		  $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/allAptitudes" ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 scope.aptitudes = data;
							    },
							    error: function (err)
							    {
							    	alert("getAllAptitudes error...");
							    },
							});
            	
            	if(! scope.aptitudes)
            		{
							scope.aptitudes = [
								{name : "安徽广电", code : "OP001"},
								{name : "华数", code : "SP001"},
								{name : "优酷", code : "SP002"}
								
							];
            		}
                     	   var aptitudecheck=[];
                     	   aptitudecheck[0]=scope.aptitudes[0].code;
                     	   scope.selectAptitudes[0]=scope.aptitudes[0];
                     	 
	                          //被选中条件：ng-checked的结果为true
	                          scope.isSelected = function(id){          
	                              return aptitudecheck.indexOf(id)!=-1; 
                     	 }
	                          
	    	},
	        
	        //得到选中的频道
	        getChannels:function()
	        {
   	        	 var channels = [];
   	        	 var selectRows = $("input[name='channelBox']:checked");
           		 if (selectRows.length <= 0) {
           			 return channels;
           		 }
               		 
               		 selectRows.each(
       				 function ()
       				 {
       					 channel=new Object();
       					 channel.serviceId=$(this).val();
       					 channel.channelName=($(this).parent().text()).replace(/(^\s*)|(\s*$)/g, "");  ;
       					 channels.push(channel);
       				 });
               		 return channels;
   	        },
   	        
   	        //得到选中的频道组
   	       getChannelGroups:function()
   	       {
	        	 var getChannelGroups = [];
	        	 var selectRows = $("input[name='channelGroupBox']:checked");
       		 if (selectRows.length <= 0) {
       			 return getChannelGroups;
       		 }
           		 
           		 selectRows.each(
   				 function ()
   				 {
   					 channel=new Object();
   					 channel.serviceId=$(this).val();
   					 channel.channelName=($(this).parent().text()).replace(/(^\s*)|(\s*$)/g, "");  ;
   					 getChannelGroups.push(channel);
   				 });
           		 return getChannelGroups;
	        
   	       },
   	       
   	       getAptitudes:function()
   	       {

	        	
  	        	 var aptitudes = [];
  	        	 var selectRows = $("input[name='aptitudeBox']:checked");
          		 if (selectRows.length <= 0) {
          			 return aptitudes;
          		 }
              		 
              		 selectRows.each(
      				 function ()
      				 {
      					 aptitude=new Object();
      					 aptitude.code=$(this).val();
      					 aptitude.name=($(this).parent().text()).replace(/(^\s*)|(\s*$)/g, "");  ;
      					 aptitudes.push(aptitude);
      				 });
              		 return aptitudes;
  	        
   	       },
	    	
	    
	    //根据图表类型获取图表链接和需要组装的参数名集合。
	    getKibanaChartUrl:function(type)
        {
        	var chartObj=new Object(); 
        	
        	switch(type)
        	{
        	//直播实时收视次数统计   kibana图表：http://192.168.2.202:5601/goto/d146208f4126f2c5041b9f012821d25e
        	case 'zhiboRealTimeWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/8f5f8340-f118-11e7-a943-bb497dfe1e9a?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),schema:metric,type:count),(enabled:!t,id:'2',params:(filters:!({6})),schema:segment,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(rotate:0,show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:filters),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:288',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E7%9B%B4%E6%92%AD%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0%E7%BB%9F%E8%AE%A1,type:histogram))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
        		return chartObj;
           //直播实时收视用户数统计   kibana图表：http://192.168.2.202:5601/goto/1cc856d6348d688162d9b327b40fa27b
        	case 'zhiboRealTimeUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/e79a2ec0-f118-11e7-a943-bb497dfe1e9a?embed=true&_g=(refreshInterval:(display:{1},pause:!f,section:1,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%94%B6%E8%A7%86%E4%BA%BA%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(filters:!({6})),schema:segment,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(rotate:0,show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:filters),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%94%B6%E8%A7%86%E4%BA%BA%E6%95%B0),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:302',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%94%B6%E8%A7%86%E4%BA%BA%E6%95%B0),type:value))),title:itdap-%E5%AE%9E%E6%97%B6%E7%9B%B4%E6%92%AD%E6%94%B6%E7%9C%8B%E4%BA%BA%E6%95%B0,type:histogram))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	//直播实时收视率   kibana图表：http://192.168.2.202:5601/goto/41f33250b25cb5278901fe45daa8fd53
        	case 'zhiboRealTimeRatings':
        		chartObj.url="{0}/app/kibana#/visualize/edit/e9db79c0-f0f8-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:{1},pause:!f,section:1,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%94%B6%E8%A7%86%E7%8E%87,field:audience_rate),schema:metric,type:avg),(enabled:!t,id:'2',params:(filters:!({6})),schema:segment,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:filters),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%94%B6%E8%A7%86%E7%8E%87),drawLinesBetweenPoints:!t,mode:stacked,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:298',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%94%B6%E8%A7%86%E7%8E%87),type:value))),title:itdap-%E7%9B%B4%E6%92%AD%E5%AE%9E%E6%97%B6%E6%94%B6%E8%A7%86%E7%8E%87,type:histogram))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
                return chartObj;
            //直播实时市场占有率  kibana图表  http://192.168.2.202:5601/goto/528c3cd582f1e4e65ebb08b259290214
        	case 'zhiboRealTimeMarketRatings':
        	     chartObj.url="{0}/app/kibana#/visualize/edit/25920920-f0f9-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:{1},pause:!f,section:1,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87,field:market_rate),schema:metric,type:avg),(enabled:!t,id:'2',params:(filters:!({6})),schema:segment,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87),drawLinesBetweenPoints:!t,mode:stacked,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87),type:value))),title:itdap-%E7%9B%B4%E6%92%AD%E5%AE%9E%E6%97%B6%E5%8D%A0%E6%9C%89%E7%8E%87,type:histogram))";
        	     chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
        	     return chartObj;
        	 // 直播实时频道流入流出   kibana图表：http://192.168.2.202:5601/goto/83e7007135d550d0701db8c4928d2f6f
        	case 'zhiboRealTimeInAndOut':
       		 	 chartObj.url="{0}/app/kibana#/visualize/edit/25ffd430-f0fb-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:{1},pause:!f,section:1,value:{2}),time:(from:now-24h,mode:quick,to:now))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%95%B0%E9%87%8F),schema:metric,type:count),(enabled:!t,id:'2',params:(filters:!((input:(query:(query_string:(analyze_wildcard:!t,query:'start_time:%5B{4}+TO+{5}%5D'))),label:%E6%B5%81%E5%85%A5),(input:(query:(query_string:(analyze_wildcard:!t,query:'end_time:%5B{6}+TO+{7}%5D'))),label:%E6%B5%81%E5%87%BA))),schema:segment,type:filters),(enabled:!t,id:'3',params:(filters:!({8})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(rotate:0,show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:filters),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%95%B0%E9%87%8F),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%95%B0%E9%87%8F),type:value))),title:itdap-%E7%9B%B4%E6%92%AD-%E9%A2%91%E9%81%93%E5%AE%9E%E6%97%B6%E6%B5%81%E5%85%A5%E6%B5%81%E5%87%BA,type:histogram))";
       		 	 chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","area","startTimeStart","startTimeEnd","endTimeStart","endTimeEnd","channelCondition"];
        	     return chartObj;
        	     
        	 //直播非实时收视用户数统计   kibana图表：http://192.168.2.202:5601/goto/238fde2727033d566b725cb01bc73a42
        	case 'zhiboNotRealTimeUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/f0a6efe0-f11c-11e7-b4e6-bd08d7947659?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E8%A7%82%E7%9C%8B%E4%BA%BA%E6%95%B0,field:user_count),schema:metric,type:avg),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:date,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E8%A7%82%E7%9C%8B%E4%BA%BA%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:2384',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E8%A7%82%E7%9C%8B%E4%BA%BA%E6%95%B0),type:value))),title:itdap-%E9%9D%9E%E5%AE%9E%E6%97%B6%E7%9B%B4%E6%92%AD%E8%A7%82%E7%9C%8B%E4%BA%BA%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;   
        	//直播非实时收视次数统计   kibana图表：http://192.168.2.202:5601/goto/238fde2727033d566b725cb01bc73a42 
        	case 'zhiboNotRealTimeWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/a1ac2040-f11c-11e7-a943-bb497dfe1e9a?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),schema:metric,type:count),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:334',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%94%B6%E8%A7%86%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E9%9D%9E%E5%AE%9E%E6%97%B6%E7%9B%B4%E6%92%AD%E8%A7%82%E7%9C%8B%E6%AC%A1%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
            //直播非实时收视率统计   kibana图表：http://192.168.2.202:5601/goto/c31dc578035102efa1422efaf152e122
        	case 'zhiboNotRealTimeRatings':
        		chartObj.url="{0}/app/kibana#/visualize/edit/899d8250-f0fe-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:off,pause:!t,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%94%B6%E8%A7%86%E7%8E%87,field:audience_rate),schema:metric,type:avg),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:date,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%94%B6%E8%A7%86%E7%8E%87),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%94%B6%E8%A7%86%E7%8E%87),type:value))),title:itdap-%E7%9B%B4%E6%92%AD%E9%9D%9E%E5%AE%9E%E6%97%B6%E6%94%B6%E8%A7%86%E7%8E%87,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
            //直播非实时市场占有率统计   kibana图表：http://192.168.2.202:5601/goto/a6dcd7af972e616bdb94a006b32969ca
        	case 'zhiboNotRealTimeMarketRatings':
        		chartObj.url="{0}/app/kibana#/visualize/edit/2b173f40-f0ff-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87,field:market_rate),schema:metric,type:avg),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:date,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%B8%82%E5%9C%BA%E5%8D%A0%E6%9C%89%E7%8E%87),type:value))),title:itdap-%E7%9B%B4%E6%92%AD%E9%9D%9E%E5%AE%9E%E6%97%B6%E5%8D%A0%E6%9C%89%E7%8E%87,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	//直播非实时流入统计   kibana图表：http://192.168.2.202:5601/goto/9898fc06ec5c2e24cfba8fe8bb6c6b56
        	case 'zhiboNotRealTimeIn':
        		chartObj.url="{0}/app/kibana#/visualize/edit/8149e5c0-f0ff-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%B5%81%E5%85%A5%E6%95%B0%E9%87%8F),schema:metric,type:count),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%B5%81%E5%85%A5%E6%95%B0%E9%87%8F),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%B5%81%E5%85%A5%E6%95%B0%E9%87%8F),type:value))),title:itdap-%E9%9D%9E%E5%AE%9E%E6%97%B6%E9%A2%91%E9%81%93%E6%B5%81%E5%85%A5%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	//直播非实时流出统计   kibana图表：http://192.168.2.202:5601/goto/2e09904a7ca3788f4dd93f6698741ed7
        	case 'zhiboNotRealTimeOut':
        		chartObj.url="{0}/app/kibana#/visualize/edit/c9494b40-f0ff-11e7-a51f-1bf237326851?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%B5%81%E5%87%BA%E6%95%B0%E9%87%8F),schema:metric,type:count),(enabled:!t,id:'2',params:(customInterval:'30m',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:end_time,interval:custom,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%B5%81%E5%87%BA%E6%95%B0%E9%87%8F),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:335',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%B5%81%E5%87%BA%E6%95%B0%E9%87%8F),type:value))),title:itdap-%E9%9D%9E%E5%AE%9E%E6%97%B6%E9%A2%91%E9%81%93%E6%B5%81%E5%87%BA%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	//直播非实时收视时长统计   kibana图表：http://192.168.2.202:5601/goto/ecfdbc2fbfa4988403a04fe1030f3993 
        	case 'zhiboNotRealTimeWatchDuration':
        		chartObj.url="{0}/app/kibana#/visualize/edit/4ea6a860-f11d-11e7-b4e6-bd08d7947659?embed=true&_g=(refreshInterval:(display:off,pause:!f,section:1,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E8%A7%82%E7%9C%8B%E6%97%B6%E9%95%BF,field:duration_time),schema:metric,type:sum),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:date,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E8%A7%82%E7%9C%8B%E6%97%B6%E9%95%BF),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E8%A7%82%E7%9C%8B%E6%97%B6%E9%95%BF),type:value))),title:itdap-%E9%9D%9E%E5%AE%9E%E6%97%B6%E7%9B%B4%E6%92%AD%E8%A7%82%E7%9C%8B%E6%97%B6%E9%95%BF,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
            //点播实时点播次数总数统计     kibana图表：http://192.168.2.202:5601/goto/0f0f3aee8d26f713914119d03c51ef35
        	case 'dianboRealTimeSpWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/92dc1f90-f057-11e7-8700-c98013f847e1?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),schema:metric,type:count),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({6})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E7%82%B9%E6%92%AD%E5%AE%9E%E6%97%B6%E6%AC%A1%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","startTimeStart","startTimeEnd","area","spCondition"];
        		return chartObj;
            //点播实时点播用户数总数统计    kibana图表：http://192.168.2.202:5601/goto/dd958a9e7cb7d5d9e979fda1b855d004
        	case 'dianboRealTimeSpUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/f2bd0da0-f077-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'NOT+end_time:*+++AND++area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E4%BD%BF%E7%94%A8%E4%BA%BA%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(filters:!({6})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(rotate:0,show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:''),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E4%BD%BF%E7%94%A8%E4%BA%BA%E6%95%B0),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!(('$$hashKey':'object:2744',id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E4%BD%BF%E7%94%A8%E4%BA%BA%E6%95%B0),type:value))),title:itdap-%E7%82%B9%E6%92%AD%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0,type:histogram))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","startTimeStart","startTimeEnd","area","spCondition"];
        		return chartObj;
        	 //点播非实时点播次数总计     kibana图表：itdap-点播实时次数（id:d8114960-e552-11e7-bc69-ef7ec6643ac9）
        	case 'dianboNotRealTimeSpWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/92dc1f90-f057-11e7-8700-c98013f847e1?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),schema:metric,type:count),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E7%82%B9%E6%92%AD%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E7%82%B9%E6%92%AD%E5%AE%9E%E6%97%B6%E6%AC%A1%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","startTimeStart","startTimeEnd","area","spCondition"];
        		return chartObj;
             //点播非实时接入用户数    kibana图表：itdap-点播实时用户数（id:4363a6e0-e553-11e7-bc69-ef7ec6643ac9）
        	case 'dianboNotRealTimeSpUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/75915fa0-f079-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E7%94%A8%E6%88%B7%E6%8E%A5%E5%85%A5%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E7%94%A8%E6%88%B7%E6%8E%A5%E5%85%A5%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E7%94%A8%E6%88%B7%E6%8E%A5%E5%85%A5%E6%95%B0),type:value))),title:itdap-%E7%82%B9%E6%92%AD%E9%9D%9E%E5%AE%9E%E6%97%B6%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","spCondition"];
        		return chartObj;
            
        	//回看实时节目收视次数统计   kibana图表：http://192.168.2.202:5601/goto/071597ae48d09f63f7c350e883afb79b 
        	case 'playBackRealTimeEpgWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/4e911a40-f07d-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:now-24h,mode:quick,to:now))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'NOT+end_time:*+++AND+area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E8%A7%82%E7%9C%8B%E6%AC%A1%E6%95%B0),schema:metric,type:count),(enabled:!t,id:'2',params:(filters:!({4})),schema:segment,type:filters),(enabled:!t,id:'3',params:(customLabel:%E8%8A%82%E7%9B%AE%E5%90%8D%E7%A7%B0,field:epg_name.keyword,order:desc,orderBy:'1',size:100),schema:segment,type:terms)),listeners:(),params:(addLegend:!t,addTooltip:!t,isDonut:!t,legendPosition:right),title:itdap-%E5%9B%9E%E7%9C%8B%E5%90%84%E8%8A%82%E7%9B%AE%E8%A7%82%E7%9C%8B%E6%80%BB%E9%87%8F,type:pie))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","area","channelCondition"];
        	    return chartObj;
        	
            //回看实时频道收视用户数统计   kibana图表：http://192.168.2.202:5601/goto/d8dcf5ca3ea0379a6cf2bc5d2676a826
        	case 'playBackRealTimeChannelUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/e5abec30-f07c-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'NOT+end_time:*+++AND+area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%9B%9E%E7%9C%8B%E7%94%A8%E6%88%B7%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(filters:!({6})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%9B%9E%E7%9C%8B%E7%94%A8%E6%88%B7%E6%95%B0),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%9B%9E%E7%9C%8B%E7%94%A8%E6%88%B7%E6%95%B0),type:value))),title:itdap-%E5%9B%9E%E7%9C%8B%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0,type:histogram))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
        		return chartObj;
           
            //回看非实时频道播放次数统计   kibana图表：http://192.168.2.202:5601/goto/a86cd9900c53e930c6c28724fb7475dc
        	case 'playBackNotRealTimeChannelWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/99b60d40-f07e-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0,field:start_time),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E5%9B%9E%E7%9C%8B%E9%9D%9E%E5%AE%9E%E6%97%B6%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
            //回看非实时频道播放用户数统计   kibana图表：http://192.168.2.202:5601/goto/4a8b65bcc00a30e01ce7925dae1ceaa7
        	case 'playBackNotRealTimeChannelUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/0c3d0d50-f07f-11e7-80b1-cfafaa21bb6e?embed=true&_g=(refreshInterval:(display:on,pause:!t,value:10),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0),type:value))),title:itdap-%E5%9B%9E%E7%9C%8B%E9%9D%9E%E5%AE%9E%E6%97%B6%E6%8E%A5%E5%85%A5%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	
            //时移实时节目收视次数统计   kibana图表：http://192.168.2.202:5601/goto/fe3d14a3d2d76b8c730dde7db7dff0d9
        	case 'shiftRealTimeEpgWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/9604fda0-f0f6-11e7-8178-955b9be2b417?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:now-24h,mode:quick,to:now))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(),schema:metric,type:count),(enabled:!t,id:'2',params:(filters:!({4})),schema:segment,type:filters),(enabled:!t,id:'3',params:(customLabel:%E8%8A%82%E7%9B%AE%E5%90%8D%E7%A7%B0,field:epg_name.keyword,order:desc,orderBy:'1',size:100),schema:segment,type:terms)),listeners:(),params:(addLegend:!t,addTooltip:!t,isDonut:!t,legendPosition:right),title:itdap-%E6%97%B6%E7%A7%BB%E5%BD%93%E5%89%8D%E8%8A%82%E7%9B%AE%E8%A7%82%E7%9C%8B%E6%80%BB%E6%95%B0%EF%BC%8824H%EF%BC%89,type:pie))";
        		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","area","channelCondition"];
        	    return chartObj;
        	//时移实时频道收视用户数统计   kibana图表：http://192.168.2.202:5601/goto/b233c92920c3833fa2323e8aed18bf4a 
    	    case 'shiftRealTimeChannelUserCount':
    		chartObj.url="{0}/app/kibana#/visualize/edit/3e14a190-f0f6-11e7-8178-955b9be2b417?embed=true&_g=(refreshInterval:(display:{1},pause:!f,value:{2}),time:(from:{3},mode:quick,to:{4}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'NOT+end_time:*++AND+area_code:{5}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%97%B6%E7%A7%BB%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(filters:!({6})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:right,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%97%B6%E7%A7%BB%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0),drawLinesBetweenPoints:!t,mode:normal,show:true,showCircles:!t,type:histogram,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%97%B6%E7%A7%BB%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0),type:value))),title:itdap-%E6%97%B6%E7%A7%BB%E5%AE%9E%E6%97%B6%E7%94%A8%E6%88%B7%E6%95%B0,type:histogram))";
    		chartObj.queryParam=["kibanaurl","isReflesh","refleshValue","fromTime","toTime","area","channelCondition"];
    		return chartObj;
    		//时移非实时频道播放次数统计   kibana图表：http://192.168.2.202:5601/goto/a86cd9900c53e930c6c28724fb7475dc
        	case 'shiftNotRealTimeChannelWatchCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/99b60d40-f07e-11e7-ab21-25a4fbce4f3c?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0,field:start_time),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E5%9B%9E%E7%9C%8B%E4%B8%9A%E5%8A%A1%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0),type:value))),title:itdap-%E5%9B%9E%E7%9C%8B%E9%9D%9E%E5%AE%9E%E6%97%B6%E4%BD%BF%E7%94%A8%E6%AC%A1%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
            //时移非实时频道播放用户数统计   kibana图表：http://192.168.2.202:5601/goto/344db3a0f1d104cda36d94b2736438be
        	case 'shiftNotRealTimeChannelUserCount':
        		chartObj.url="{0}/app/kibana#/visualize/edit/da0b9530-f0f7-11e7-a943-bb497dfe1e9a?embed=true&_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:{1},mode:quick,to:{2}))&_a=(filters:!(),linked:!f,query:(query_string:(analyze_wildcard:!t,query:'area_code:{3}')),uiState:(),vis:(aggs:!((enabled:!t,id:'1',params:(customLabel:%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0,field:user_code.keyword),schema:metric,type:cardinality),(enabled:!t,id:'2',params:(customInterval:'2h',customLabel:%E6%97%B6%E9%97%B4,extended_bounds:(),field:start_time,interval:auto,min_doc_count:0),schema:segment,type:date_histogram),(enabled:!t,id:'3',params:(filters:!({4})),schema:group,type:filters)),listeners:(),params:(addLegend:!t,addTimeMarker:!f,addTooltip:!t,categoryAxes:!((id:CategoryAxis-1,labels:(show:!t,truncate:100),position:bottom,scale:(type:linear),show:!t,style:(),title:(text:%E6%97%B6%E9%97%B4),type:category)),defaultYExtents:!f,drawLinesBetweenPoints:!t,grid:(categoryLines:!f,style:(color:%23eee)),interpolate:cardinal,legendPosition:top,radiusRatio:9,scale:linear,seriesParams:!((data:(id:'1',label:%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0),drawLinesBetweenPoints:!t,interpolate:cardinal,lineWidth:2,mode:normal,show:!t,showCircles:!t,type:line,valueAxis:ValueAxis-1)),setYExtents:!f,showCircles:!t,times:!(),valueAxes:!((id:ValueAxis-1,labels:(filter:!f,rotate:0,show:!t,truncate:100),name:LeftAxis-1,position:left,scale:(mode:normal,type:linear),show:!t,style:(),title:(text:%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0),type:value))),title:itdap-%E6%97%B6%E7%A7%BB%E9%9D%9E%E5%AE%9E%E6%97%B6%E6%8E%A5%E5%85%A5%E4%BA%BA%E6%95%B0,type:line))";
        		chartObj.queryParam=["kibanaurl","fromTime","toTime","area","channelCondition"];
        		return chartObj;
        	}
        	
        	
        },
	    
        //展示报表数据
	    kibanaChartEmbed:function(scope,sce,kibanaUrl,ParamArray)
	    {
	    	url=avitUtil.format(kibanaUrl,ParamArray);
	    	debugger;
    		scope.specialHtml = sce.trustAsHtml(' <iframe id="iframe-projects" name="kibanaFrame2"  border="0" class="frame-border"  marginwidth="0" marginheight="0" height="600" width="100%"  ng-src="{{trustSrc}}" src="'+url+'"))"  ></iframe>');
	    },
	    
	    
	    /**
	     * 下面是组装各个字段的数据。分别是:
	     * startTime开始时间、
	     * startTime结束时间、
	     * endTime开始时间、
	     * endTime结束时间、
	     * 是否刷新
	     * 刷新频率值
	     * 区域值
	     * 频道或频道组筛选条件
	     */
	    
	    startTimeStart:function(scope,chartType,isFromTime)
	    {
	    	var start="now-1m";
	    	if(isFromTime==1)
	    	{
	    		start="now-2m";
	    	}
	    	
	    	if(chartType=='zhiboRealTimeUserCount')
			{
              	if(scope.selMin)
              	{
              		var startEndParam="now-1m";
              		var endStartParam="now-"+(parseInt(scope.selMin)+1)+"m";
              		start="(start_time:%5B0+TO+"+startEndParam+"%5D+AND+end_time:%5B"+endStartParam+"+TO+9999999999999%5D)+OR+(start_time:%5B0+TO+"+endStartParam+"%5D+NOT++end_time:*)";
              	}
              	if(isFromTime==1)
		    	{
		    		start="now-300m";
		    	}
			}else if(chartType.indexOf("NotRealTime")>-1)
			{
				start="'"+scope.newWordQueryObj.startTime.toISOString()+"'";
			}
	    	else
			{
              	if(scope.selMin)
              	{
              		start="now-"+(parseInt(scope.selMin)+1)+"m";
              		if(isFromTime==1)
              		{
              			start="now-"+(parseInt(scope.selMin)+2)+"m";
              		}
              	}
			}
	    	
	    
	    	return start;
	    },
	    
	    startTimeEnd:function(scope,chartType,isToTime)
	    {
	    	var endTime="now-1m";
	    	if(chartType.indexOf("NotRealTime")>-1)
	    	{
	    		endTime="'"+scope.newWordQueryObj.endTime.toISOString()+"'";
	    	}else 
	    	{
	    		if(isToTime==1)
	    	{
	    		endTime="now-1m";
	    	}
	    	}
	    	return endTime;
	    },
	    
	    endTimeStart:function(chartType,scope)
	    {
	    	var endTimeStart="now-1m";
	    	if(chartType=='zhiboRealTimeInAndOut')
			{
              	if(scope.selMin)
              	{
              		endTimeStart="now-"+(parseInt(scope.selMin)+1)+"m";
              	}
			}else if(chartType=='playBackRealTimeEpgUserCount')
			{
				endTimeStart="NOT+end_time:*";
			}
	    	return endTimeStart;
	    },
	    
	    endTimeEnd:function(chartType,scope)
	    {
	    	var endTimeEnd="now-1m";
	    	if(chartType=='zhiboRealTimeInAndOut')
			{
	    		endTimeEnd="now-1m";
	    	}
	    	return endTimeEnd;
	    },
	    
	    isReflesh:function(scope,chartType)
	    {
	    	var reflesh="off";
          	if(scope.isReflesh==0)
          	{
          		reflesh="on"
          		if(chartType=='playBackRealTimeEpgWatchCount'||chartType=='shiftRealTimeEpgWatchCount')
          		{
          			reflesh="\'"+5+"+seconds\'";
          		}else if(chartType.indexOf("dianboRealTime")>-1||chartType.indexOf("playBackRealTime")>-1||chartType.indexOf("shiftRealTime")>-1)
				{
					if(scope.selMin=="10")
					{
						reflesh="\'"+10+"+seconds\'";
					}else if(scope.selMin=="30")
					{
						reflesh="\'"+30+"+seconds\'";
					}else if(scope.selMin=="60")
					{
						reflesh="\'"+60+"+seconds\'";
					}
				}else
				{
					reflesh="\'"+scope.selRefresh+"+seconds\'";
				}
          	}
          	return reflesh;
	    },
	    
	    refleshValue:function(scope,chartType)
	    {
	    	var refleshValue=0;
	    	if(chartType=='playBackRealTimeEpgWatchCount'||chartType=='shiftRealTimeEpgWatchCount')
      		{
	    		refleshValue=5*1000;
      		}else if(chartType.indexOf("dianboRealTime")>-1||chartType.indexOf("playBackRealTime")>-1||chartType.indexOf("shiftRealTime")>-1)
				{
					if(scope.selMin=="10")
					{
						refleshValue=10*1000;
					}else if(scope.selMin=="30")
					{
						refleshValue=30*1000;
					}else if(scope.selMin=="60")
					{
						refleshValue=60*1000;
					}
				}else
				{
					refleshValue=parseInt(scope.selRefresh)*1000;
				}
			return refleshValue;
	    },
	    
    	//得到区域值
	    areaValue:function(scope)
	    {
	    	var areaValue="*";
	      	if(scope.theArea!="0")
	      	{
	      		areaValue=scope.theArea;
	      	}
	      	return areaValue;
	    },
		  
	    
    	//组织kibana报表频道和频道组筛选条件数据
    	channelQueryStringValue:function(scope,flag,channelQueryString)
    	{
//    		var channelQueryString="(input:(query:(query_string:(analyze_wildcard:!t,query:'live.service_id:%22{0}%22'))),label:'{1}'),";
//          	var channelGroupString="(input:(query:(query_string:(analyze_wildcard:!t,query:'live.service_id:{0}'))),label:'{1}'),";
    		var channelGroups=[];
    		if(!flag)
          	{
    			scope.selectChannels =this.getChannels();
          		channelGroups=this.getChannelGroups();
          		if(scope.selectChannels.length==0&&channelGroups.length==0)
          		{
          			alert("请先选择频道或频道组！");
          			return;
          		}
          	}
          	var channelconditions="";
          	for(var i=0;i<scope.selectChannels.length;i++){
          		var channel=scope.selectChannels[i];
          		var queryStingValue=["%22"+channel.serviceId+"%22",channel.channelName];
          		channelconditions+=avitUtil.format(channelQueryString,queryStingValue);
          		}
          	
          	if(channelGroups.leng!=0)
          	{
          		for(var i=0;i<channelGroups.length;i++)
          		{
          		var subChannels=channelGroups[i].serviceId.split(",");
          		var queryChannel="";
          		for(var j=0;j<subChannels.length;j++)
          		{
          			if(j!=(subChannels.length-1))
          			{
          			queryChannel+="%22"+subChannels[j]+"%22+OR+";
          			}else
          			{
          				queryChannel+="%22"+subChannels[j]+"%22";
          			}
          		}
          		var queryStingValue=[queryChannel,channelGroups[i].channelName];
          		channelconditions+=avitUtil.format(channelQueryString,queryStingValue);
          		}
          		}
          	
          	if(channelconditions.length>0)
          		{
          		channelconditions=channelconditions.substring(0,channelconditions.lastIndexOf(","));
          		}
          	
          	return channelconditions;
    	},
    
    	spQueryStringValue:function(scope,flag,spQueryString)
    	{
    		if(!flag)
          	{
    			scope.selectAptitudes =this.getAptitudes();
          		if(scope.selectAptitudes.length==0)
          		{
          			alert("请先选择运营商！");
          			return;
          		}
          	}
          	var spconditions="";
          	for(var i=0;i<scope.selectAptitudes.length;i++){
          		var aptitude=scope.selectAptitudes[i];
          		var queryStingValue=[aptitude.code,aptitude.name];
          		spconditions+=avitUtil.format(spQueryString,queryStingValue);;
          		}
          	
          
          	
          	if(spconditions.length>0)
          	{
          		spconditions=spconditions.substring(0,spconditions.lastIndexOf(","));
          	}
          	return spconditions;
    	},
    	
    	service_idsValue:function(scope,flag)
    	{
//          	var channelGroupString="(input:(query:(query_string:(analyze_wildcard:!t,query:'live.service_id:{0}'))),label:'{1}'),";
    		var channelGroups=[];
    		if(!flag)
          	{
    			scope.selectChannels =this.getChannels();
          		channelGroups=this.getChannelGroups();
          		if(scope.selectChannels.length==0&&channelGroups.length==0)
          		{
          			alert("请先选择频道或频道组！");
          			return;
          		}
          	}
          	var serviceIds="";
          	for(var i=0;i<scope.selectChannels.length;i++){
          		var channel=scope.selectChannels[i];
          		serviceIds+=channel.serviceId+"+OR+";
          		}
          	
          	if(channelGroups.leng!=0)
          	{
          		for(var i=0;i<channelGroups.length;i++)
          		{
          		var subChannels=channelGroups[i].serviceId.split(",");
          		for(var j=0;j<subChannels.length;j++)
          		{
          			if(j!=(subChannels.length-1))
          			{
          				serviceIds+=subChannels[j]+"+OR+";
          			}else
          			{
          				serviceIds+=subChannels[j];
          			}
          		}
          		if(serviceIds.lastIndexOf("+"))
          		{
          			serviceIds=serviceIds.substring(0,serviceIds.lastIndexOf("+")-3);
          		}
          		
          	return serviceIds;
    	
    	}
          	}
    	},
  
        //绘制kibana报表并展示
        kibanaChartData:function(scope,sce,flag,chartType)
        {
        	var chartUrl=this.getKibanaChartUrl(chartType);
        	var paramArry=[];
        	var paramObj=chartUrl.queryParam;
        	for(var i=0;i<paramObj.length;i++)
        	{
        		var paramType=paramObj[i];
        		switch(paramType)
        		{
        		case 'kibanaurl':
        			paramArry.push(avitUtil.kibanaurl());
        			break;
        		case 'isReflesh':
                  	paramArry.push(this.isReflesh(scope,chartType));
        			break;
        		case 'refleshValue':
        			paramArry.push(this.refleshValue(scope,chartType));
        			break;
        		case "fromTime":
        			paramArry.push(this.startTimeStart(scope,chartType,1));
        			break;
        		case "toTime":
        			paramArry.push(this.startTimeEnd(scope,chartType,1));
        			break;
        			break;
        		case 'startTimeStart':
        			paramArry.push(this.startTimeStart(scope,chartType,0));
        			break;
        		case 'startTimeEnd':
        			paramArry.push(this.startTimeEnd(scope,chartType,0));
        			break;
        		case 'endTimeStart':
        			paramArry.push(this.endTimeStart(chartType,scope));
        			break;
        		case 'endTimeEnd':
        			paramArry.push(this.endTimeEnd(chartType,scope));
        			break;
        		case 'area':
                  	paramArry.push(this.areaValue(scope));
        			break;
        		case 'channelCondition':
        			paramArry.push(this.channelQueryStringValue(scope,flag,avitUtil.channelQueryString(chartType)));
        			break;
        		case 'spCondition':
        			paramArry.push(this.spQueryStringValue(scope,flag,avitUtil.spQueryString()));
        			break;
        		case 'service_ids':
        			paramArry.push(this.service_idsValue(scope,flag));
        			break;
        		default:break;
        		}
        		
        	}
        	this.kibanaChartEmbed(scope,sce,chartUrl.url,paramArry);
        },
        
        //初始化直播时间插件
        initZhiboDatePicker:function(scope,timeout)
        {
        	//初始化日期控件
         	scope.newWordQueryObj = {
         		    fileName: '',
         		    startTime:new Date(),
         		    endTime: new Date(),
         		    order: '0'
         		}; 
			    scope.startDateOptions = {
		            formatYear: 'yyyy',
		            maxDate: scope.newWordQueryObj.endTime,
		            startingDay: 1
		        };
		        scope.endDateOptions = {
		            formatYear: 'yyyy',
		            minDate: scope.newWordQueryObj.startTime,
		            maxDate: new Date(),
		            startingDay: 1
		        };
		        this.initDatePickerStartTimeFormat(scope);
		        this.initDatePickerEndTimeFormat(scope);
		        scope.format = "yyyy-MM-dd HH:mm:ss";
		        
		        scope.$watch('newWordQueryObj.startTime', function(newValue,oldValue){
		            //$scope.minEndDate = newValue;
		            scope.endDateOptions.minDate = newValue;
		            if(scope.endtTimeMaxDate)
		            {
		            scope.endDateOptions.maxDate = new Date(newValue.setDate(newValue.getDate()+scope.endtTimeMaxDate));
		            }
		            
		        });
		        scope.$watch('newWordQueryObj.endTime', function(newValue,oldValue){
		            //$scope.maxStartDate = newValue;
		            scope.startDateOptions.maxDate = newValue;
		            if(scope.startTimeMinDate)
		            {
		            scope.startDateOptions.minDate = new Date(newValue.setDate(newValue.getDate()-scope.startTimeMinDate));
		            }
		        });
		        scope.startOpen = function() {
		            timeout(function() {
		                scope.startPopupOpened = true;
		            });
		        };
		        scope.endOpen = function() {
		            timeout(function() {
		                scope.endPopupOpened = true;
		            });
		        };
		        scope.startPopupOpened = false;
		        scope.endPopupOpened = false;
        },
        //初始化点播时间插件
        initDianBoDatePicker:function(scope,timeout)
        {
        	//初始化日期控件
         	scope.newWordQueryObj = {
         		    fileName: '',
         		    startTime:new Date(),
         		    endTime: new Date(),
         		    order: '0'
         		}; 
			    scope.startDateOptions = {
		            formatYear: 'yyyy',
		            maxDate: scope.newWordQueryObj.endTime,
		            startingDay: 1
		        };
		        scope.endDateOptions = {
		            formatYear: 'yyyy',
		            minDate: scope.newWordQueryObj.startTime,
		            maxDate: new Date(),
		            startingDay: 1
		        };
		        
		        this.initDatePickerStartTimeFormat(scope);
		        this.initDatePickerEndTimeFormat(scope);
		        
		        scope.format = "yyyy-MM-dd HH:mm:ss";
		        
		        scope.$watch('newWordQueryObj.startTime', function(newValue,oldValue){
		            //$scope.minEndDate = newValue;
		            scope.endDateOptions.minDate = newValue;
		        });
		        scope.$watch('newWordQueryObj.endTime', function(newValue,oldValue){
		            //$scope.maxStartDate = newValue;
		            scope.startDateOptions.maxDate = newValue;
		        });
		        scope.startOpen = function() {
		            timeout(function() {
		                scope.startPopupOpened = true;
		            });
		        };
		        scope.endOpen = function() {
		            timeout(function() {
		                scope.endPopupOpened = true;
		            });
		        };
		        scope.startPopupOpened = false;
		        scope.endPopupOpened = false;
        },
        
        
        initDatePickerStartTimeFormat:function(scope)
        {
        	scope.newWordQueryObj.startTime.setHours(0);
            scope.newWordQueryObj.startTime.setMinutes(0);
            scope.newWordQueryObj.startTime.setSeconds(0);
        },
        
        
        initDatePickerEndTimeFormat:function(scope)
        {
        	scope.newWordQueryObj.endTime.setHours(23);
            scope.newWordQueryObj.endTime.setMinutes(59);
            scope.newWordQueryObj.endTime.setSeconds(59);
        },
        
         disableCheckBox: function(tagName) { 
        	var obj=document.getElementsByName(tagName) 
        	for(var i=0;i<obj.length;i++) 
        	{ 
        	if ( !obj[i].checked ) 
        	obj[i].disabled = true; 
        	} 
        	},
         
         ableCheckBox: function(tagName) { 
        	var obj=document.getElementsByName(tagName) 
        	for(var i=0;i<obj.length;i++) 
        	obj[i].disabled = false; 
        	}, 
        //控制多选框可选数量
        doCheck:function(tagName,count) 
        	{ 
        	var obj=document.getElementsByName(tagName) 
        	var sun=0; 

        	for(var i=0;i<obj.length;i++) 
        	{ 
        	if(obj[i].type=="checkbox" && 
        	obj[i].checked) 
        	sun++; 

        	if( sun< count ) 
        	{ 
        	this.ableCheckBox(tagName); 
        	} 
        	else if(sun ==count ) 
        	{ 
        	this.disableCheckBox(tagName); 
        	event.srcElement.checked=true; 
        	break; 
        	} 
        	else if(sun > count ) 
        	{ 
        	event.srcElement.checked=false; 
        	break; 
        	} 
        	} 
        	} 
        
	    }
	    
	});