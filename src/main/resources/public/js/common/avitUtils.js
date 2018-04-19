angular.module('avit.services',[]).factory('avitUtil', function() {
	    return {
	    	kibanaurl:function()
	    	{
	    		return "http://192.168.2.202:5601";
	    	},
	    	chartPeriod:function()
	    	{
	    		return 1;
	    	},
	    		
	    	channelQueryString:function(chartType)
	    	{
	    		if(chartType=='zhiboRealTimeRatings'||chartType=='zhiboRealTimeMarketRatings'||chartType=='zhiboNotRealTimeRatings'||chartType=='zhiboNotRealTimeMarketRatings'||chartType=='zhiboNotRealTimeUserCount')
	    		{
	    		return "(input:(query:(query_string:(analyze_wildcard:!t,query:'service_id:{0}'))),label:'{1}'),";
	    		}else if(chartType.indexOf("zhibo")!=-1)
	    		{
	    		return "(input:(query:(query_string:(analyze_wildcard:!t,query:'live.service_id:{0}'))),label:'{1}'),";
	    		}else
	    		{
	    		return "(input:(query:(query_string:(analyze_wildcard:!t,query:'service_id:{0}'))),label:'{1}'),";
	    		}
	    	},
	    	
	        spQueryString:function()
	        {
	        	return "(input:(query:(query_string:(analyze_wildcard:!t,query:'operator_name:{0}'))),label:'{1}'),";
	        },
	    
	    	//ajax请求
	        ajax:function(ajaxRequestBody)
	        {
	        	$.ajax(
			    {
					    type: ajaxRequestBody.type,
					    url: ajaxRequestBody.url,
					    data: ajaxRequestBody.params ,
					    dataType: "json",
					    contentType:'application/json',
					    async:ajaxRequestBody.isAsync,
					    success: function (response)
					    {
					    	ajaxRequestBody.sucCallback(response);
					    },
					    error: function (err)
					    {
					    	ajaxRequestBody.failCallback(err);
					    },
			    });
	        },
	        //初始化分页数据
	        initHashPage:function(scope)
	        {
	        	 scope.datas = [];
                 scope.maxSize = 2;
            	 scope.currentPage = 1;
                 scope.totalItems = 0;
                 scope.itemsPerPage="10";
                 scope.initFlag = 'N';
                 var hashObj = window.location.hash;
     			 if(hashObj != "")
     			 {
 					 hashObj = unescape(hashObj.substring(1));
 					 var searchObj= JSON.parse(hashObj);
 					 for (var item in searchObj) 
 					 {
 						 scope[item] = searchObj[item];
 					 }
 					 window.location.hash = "";
     			 }
	        },
	        
	  
	        
	        
	        //是否从PC机子访问 
	         isSourcePc:function() 
	         { 
	            var userAgentInfo = navigator.userAgent;
	            var Agents = ["Android", "iPhone","SymbianOS", "Windows Phone","iPad", "iPod"];
	            var flag = true;
	            for (var v = 0; v < Agents.length; v++) 
	            {
	                if (userAgentInfo.indexOf(Agents[v]) > 0) 
	                {
	                    flag = false;
	                    break;
	                }
	            }
	            return flag;
	        },
	        
	        goToUrlWithMemory:function(scope,url)
	        {
	        	 var searchObj = {};
            	 $("#searchConditionDiv").find("[ng-model]").each(
    			 function ()
    			 {   
    			      searchObj[$(this).attr("ng-model")] = $(this).val();
    			 });
            	 searchObj.currentPage = scope.currentPage;
            	 searchObj.itemsPerPage = scope.itemsPerPage;
            	 var hashDesc = escape(JSON.stringify(searchObj));
	        	 window.location.href = url+"#"+hashDesc;
	        },
	        
	        format:function(str,keySet) {  
      	          if(str == 0) return this;  
   	        	   var param = keySet;  
   	        	  var s = str;  
   	        	  if(typeof(param) == 'object') {  
   	        	  for(var key in param)  
   	        	   s = s.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);  
   	        	  return s;  
   	        	 } else {  
   	        	  for(var i = 0; i < keySet.length; i++)  
   	        	   s = s.replace(new RegExp("\\{" + i + "\\}", "g"), param[i]);  
   	        	  return s;  
   	        	 }  
   	        	},
	        	dateFormat : function(date,fmt) {
	                var o = {
	                    "M+" : date.getMonth() + 1,
	                    "d+" : date.getDate(),
	                    "h+" : date.getHours(),
	                    "m+" : date.getMinutes(),
	                    "s+" : date.getSeconds(),
	                    "q+" : Math.floor((date.getMonth() + 3) / 3),
	                    "S" : date.getMilliseconds()
	                };
	                if (/(y+)/.test(fmt))
	                    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	                for (var k in o)
	                if (new RegExp("(" + k + ")").test(fmt))
	                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	                return fmt;
	            },
	    
	        
	        //页面跳转 hash
	        goToUrlWithHash:function(url)
	        {
	        	 window.location.href = url+window.location.hash;
	        },
	        
	        //页面跳转 
	        goToUrl:function(url)
	        {
	        	 window.location.href = url;
	        },
	        
	        //设置分页数据
	        setScopePages:function(scope,response)
	        {
	        	 scope.datas = response.content;
                 scope.totalItems = response.totalElements;
                 $("#startRowPages").html((scope.currentPage-1)*scope.itemsPerPage+1);
                 $("#endRowPages").html(scope.totalItems<scope.currentPage*scope.itemsPerPage?scope.totalItems:scope.currentPage*scope.itemsPerPage);
                 $("#totalRowsPages").html(scope.totalItems);
                 if (scope.totalItems == 0) 
                 {
                	 $("#pageDetailDiv").hide();
                     $("#pageShowDiv").hide();
                     $("#pageNodataDiv").show();
                 }
                 else 
                 {
                	 $("#pageDetailDiv").show();
                     $("#pageShowDiv").show();
                     $("#pageNodataDiv").hide();
                 }
                 $("#itemsPerPage").val(scope.itemsPerPage);
	        },
	        
	        
	        
   	       
            initKibana:function()
            {
                  $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/kibanaInit" ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	return data;
							    },
							    error: function (err)
							    {
							    	return "error";
							    },
							});
            },
	        
        	/**
    		 * 转化时间格式 毫秒转化成时间指定时间格式，默认 yyyy-MM-dd HH:mm:ss 格式
    		 * 
    		 * 此方法中 new Date（date）方法对IE8内核不兼容
    		 * @param time 格林威治时间
    		 * @param format 格式
    		 * @returns 转化后的时间值
    		 */
    		formatTime:function(time, format)
    		{
    			var t = new Date(time); 
    			var tf = function(i)
    			{
    				return (i < 10 ? '0' : '') + i;
    			};
    			if(undefined == format)
    			{
    				format = "yyyy-MM-dd HH:mm:ss";
    			}
    			return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a)
    					{ 
    						switch(a){ 
    							case 'yyyy': 
    								return tf(t.getFullYear()); 
    								break; 
    							case 'MM': 
    								return tf(t.getMonth() + 1); 
    								break; 
    							case 'mm': 
    								return tf(t.getMinutes()); 
    								break; 
    							case 'dd': 
    								return tf(t.getDate()); 
    								break; 
    							case 'HH': 
    								return tf(t.getHours()); 
    								break; 
    							case 'ss': 
    								return tf(t.getSeconds()); 
    								break; 
    						}; 
    					}); 
    		}
	        
	        
	        
	    }
	});