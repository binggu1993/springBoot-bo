angular.module('avit.services',[]).factory('avitUtil', function() {
	    return {
	    	
	        //初始化分页数据
	        initHashParams:function(scope)
	        {
	        	 scope.isHash = false;
            	 scope.pageNumber = 1;
                 scope.pageSize="10";
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
 					 scope.isHash = true;
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
	        
	        //表單重置數據
	        resetForm : function (scope) 
            {
	        	 $("#searchConditionDiv").find("[ng-model]").each(
    			 function ()
    			 {   
    				 scope[$(this).attr("ng-model")] = undefined;
    			 });
            },
            
            //按鈕查詢數據
            searchData : function (scope,tableId) 
            {
            	  //$("#"+tableId).bootstrapTable('destroy');
            	  //scope.gridParams.queryParams = scope.queryParams;
            	  //$("#"+tableId).bootstrapTable(scope.gridParams);
            	  $("#"+tableId).bootstrapTable("refresh",scope.queryParams);
            },
            
            //帶記憶跳轉
	        goToUrlWithMemory2:function(scope,url)
	        {
	        	 var searchObj = {};
            	 $("#searchConditionDiv").find("[ng-model]").each(
    			 function ()
    			 {   
    			      searchObj[$(this).attr("ng-model")] = $(this).val();
    			 });
            	 searchObj.pageNumber = scope.pageNumber;
            	 searchObj.pageSize = scope.pageSize;
            	 var hashDesc = escape(JSON.stringify(searchObj));
	        	 window.location.href = url+"#"+hashDesc;
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
	        
	         //設置查詢參數
	         queryParams :function (params,scope)
			 {
				   var pageNumber = scope.isHash?scope.pageNumber:(params.offset / params.limit) + 1;
				   var pageSize = scope.isHash?scope.pageSize:params.limit;
				    scope.isHash = false;
				    var params = 
				    {
				    	 pageNumber: pageNumber,       
				    	 pageSize: pageSize, 
					     sort: params.sort,  
					     sortOrder: params.order
				    };
				    
				    $("#searchConditionDiv").find("[ng-model]").each(
		    			 function ()
		    			 {   
		    				 var name = $(this).attr("ng-model");
		    				 params[name] = scope[name];
		    			 }
		    	     );
				    
				    scope.pageNumber = params.pageNumber;
					scope.pageSize = params.pageSize;
				    return params;
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
    		},
	    //刪除多個數據
        delDataBatch:function (scope)
        {        
	       	 var selectRows = $("#"+scope.tableId).bootstrapTable('getSelections');
	   		 if (selectRows.length <= 0) 
	   		 {
	   			 avitTips.alert("请选择数据！");
	   		     return;
	   		 }
	   		 avitTips.confirm({ message: "确认要删除这些数据吗？" }).on(function (e) 
	   		 {
	       		 if (!e) {        return;    }
	       		 var ids = [];
	       		 $(selectRows).each(
					 function ()
					 {
						 ids.push($(this).attr("id"));
					 });
	       		scope.delDatas(ids);	
	       	 });
        },
        
        //弹出提示框
        showPopover:function(target,code,msg){
        	var level = code==null?"danger":code<300?"success":code<400?"warning":"danger";
        	target.popover({
        			trigger:'manual',
        			content:msg!=null?msg:level,
        			placement:'top',
        			title:'<div class="text-'+level+'">操作结果</div>',
        			html: 'true',
        			container:"body"
        	});
            target.popover('show');
            //3秒后消失提示框
            var timer = setTimeout(
                function () {
                    target.popover('destroy');
                    clearInterval(timer);
                }, 3000
            );
        },
        
        notifyMsg:function(code,msg){
        	var level = code==null?"danger":code<300?"success":code<400?"info":"danger";
        	$.bootstrapGrowl(msg, {
        		  ele: 'body', 
        		  type: level, // (null, 'info', 'danger', 'success')
        		  offset: {from: 'top', amount: 100},
        		  align: 'center', 
        		  width: 'auto', 
        		  delay: 3000,
        		  allow_dismiss: true,
        		  stackup_spacing: 30
        	});
        },
        notifyErrorMsg:function(msg){
        	this.notifyMsg(500,msg);
        },
        notifySuccessMsg:function(msg){
        	this.notifyMsg(200,msg);
        },
        
        //刪除單個數據
        delDataSingle:function (id,scope)
        { 
       	 avitTips.confirm({ message: "确认要删除这条数据吗？" }).on(function (e) 
       	 {
       		 if (!e) {
       		  return;
       		 }
       		 var ids = [id];
       		scope.delDatas(ids);
       	 });
        }
        
	    };
        
	});