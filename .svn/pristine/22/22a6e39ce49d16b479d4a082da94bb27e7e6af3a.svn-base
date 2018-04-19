var app = angular.module('channelEpgApp',['ui.bootstrap','avit.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'channelEpgCtrl',
				function($scope,$location,$http,$timeout,avitUtil) 
				{
					 $scope.tableId = "objTable";
					 avitUtil.initHashParams($scope);
					 var path = $location.path(); // will tell you the current path
						path = "/"+path.substr(1).split('/')[0]; 
					 $scope.queryParams = function (params)
					 {
						 var params=avitUtil.queryParams(params,$scope);
						 return params;
				     };
				    
				    // 操作按钮
				     
				      
					 $scope.actionFormatter = function (value, row, index) 
					 {
						  var result = "";
						  // result += "<a name='operBtn' opType='view'
							// opVal='"+row.id+"' href='javascript:;'
							// class='roleView btn btn-xs green'
							// title='查看'><span class='glyphicon
							// glyphicon-search'></span></a>";
//						  result += "<a name='operBtn' opType='edit' opVal='"+row.id+"' href='javascript:;' class='roleEdit btn btn-xs blue'  title='查看'><span class='glyphicon glyphicon-pencil'></span></a>";
						  result += "<a name='operBtn' opType='view' opVal='"+row.id+"' href='javascript:;' class='roleView btn btn-xs green' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
						  result += "<a name='operBtn' opType='del' opVal='"+row.id+"'  href='javascript:;' class='roleDel btn btn-xs red'    title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
						  return result;
					 };
					 
					// 设置bootstrapTable的参数
					$scope.gridParams = 
					{
						   url:  '/itdap/page/channel/epgs?rnd=' + Math.random(),      
						   method: 'GET',      
						   toolbar: '#toolbar',    
						   striped: true,     
						   cache: false,      
						   pagination: true,     
						   sortable: true,      
						   sortOrder: "asc",     
						   sidePagination: "server",   
						   pageNumber: $scope.pageNumber,      
						   pageSize: $scope.pageSize,     
						   pageList: [5,10, 25, 50],  
						   search: false,      
						   strictSearch: false,
						   showColumns: true,     
						   showRefresh: true,     
						   minimumCountColumns: 2,    
						   clickToSelect: true,    
						   showToggle: true,    
						   cardView: false,     
						   detailView: false,     
						   queryParams : $scope.queryParams, 
						   uniqueId:'id',
						   columns: [{
						    checkbox: true,
						    visible: true     
						   }, {
						    field: 'id',
						    title: '编号',
						    visible:false,
						    sortable: true
						   }, {
						    field: 'epgName',
						    title: '节目名称',
						    sortable: true
						   }, {
						    field: 'startTime',
						    title: '开始时间',
						    sortable: true,
						    formatter:function (value, row, index) {
					            if (value == null) {
					                return "";
					            }
					            var offlineTimeStr =avitUtil.formatTime(value);
					            return offlineTimeStr;
					        }
						   }, {
							 field: 'duration',
							 title: '节目时长（分钟）',
							 sortable: true
							}, {
						    field: 'channel.channelCode',
							title: '频道编码',
							sortable: true
						   },{
							field: 'channel.channelName',
						    title: '频道名称',
							sortable: true
						   },{
								field: 'remark',
							    title: '节目描述',
							    visible:false,
								sortable: true
						   },{
						    field:'id',
						    title: '操作列表',
						    width: 120,
						    align: 'center',
						    valign: 'middle',
						    formatter: $scope.actionFormatter
						   }, ],
						   onLoadSuccess: function () 
						   {
							   $scope.initBtnEvent();
						   },
						   onLoadError: function () 
						   {
							   avitTips.alert("数据加载失败！");
						   }
					  };
					  // 初始化表格数据
					  $scope.initMainTable  = function() 
					  {
					     $('#'+$scope.tableId).bootstrapTable($scope.gridParams);
					     $('#btn_add').click(function()
					     {
					    	 $scope.goToUrl('channelEpgEdit.html');
					     });
					     $('#btn_import').click(function()
						 {
								$('#uploadModal').modal();
						 });
					     $('#btn_delete').click(function()
					     {
					    	avitUtil.delDataBatch($scope);
					     });
					     $('#btn_exportTpl').click(function(e){
							 //alert("导出模版");
					    	 avitUtil.goToUrl(path+"/channels/epg/templateDownload");
						});
					  };
					  $scope.initMainTable();
					  $scope.initDatePicker=function()
					  {
						//初始化日期控件
	   					    $scope.startDateOptions = {
	   				            formatYear: 'yyyy',
	   				            maxDate: $scope.searchEndTime,
	   				            startingDay: 1
	   				        };
	   				        $scope.endDateOptions = {
	   				            formatYear: 'yyyy',
	   				            minDate: $scope.searchStartTime,
	   				            maxDate: new Date(),
	   				            startingDay: 1
	   				        };
	   				        
	   				        
	   				        $scope.format = "yyyy-MM-dd";
	   				        $scope.$watch('searchStartTime', function(newValue,oldValue){
	   				            //$scope.minEndDate = newValue;
	   				            $scope.endDateOptions.minDate = newValue;
	   				        });
	   				        $scope.$watch('searchEndTime', function(newValue,oldValue){
	   				            //$scope.maxStartDate = newValue;
	   				            $scope.startDateOptions.maxDate = newValue;
	   				         if($scope.searchStartTime!=undefined&&$scope.searchStartTime!="")
		   				        {
		   				        $scope.searchStartTime.setHours(0);
		   		                $scope.searchStartTime.setMinutes(0);
		   		                $scope.searchStartTime.setSeconds(0);
		   				        }
	   				         if($scope.searchEndTime!=undefined&&$scope.searchStartTime!="")
	   				            {
		   				    	$scope.searchEndTime.setHours(23);
		   		                $scope.searchEndTime.setMinutes( 59 );
		   		                $scope.searchEndTime.setSeconds(59)
	   				            }
	   				        });
	   				        $scope.startOpen = function() {
	   				            $timeout(function() {
	   				                $scope.startPopupOpened = true;
	   				            });
	   				        };
	   				        $scope.endOpen = function() {
	   				            $timeout(function() {
	   				                $scope.endPopupOpened = true;
	   				            });
	   				        };
	   				        $scope.startPopupOpened = false;
	   				        $scope.endPopupOpened = false;
					  }
					  $scope.initDatePicker();
					  
					  //节目单上传
                      $scope.uploadRequest = function () 
					  {
                    	  var formData = new FormData();  
                          formData.append('filename', $('#filename')[0].files[0]);  
						   $.ajax(
						   {
							   url:"/itdap/channels/epgs/upload",
							   type: 'POST',
							   cache: false,  
	                           data: formData,  
							   dataType: "json",
							   processData: false,  
	                           contentType: false,
							   async:false,
							   success: function (response)
							    {
								    $scope.searchData();
								    if(response.code='200')
								    	{
								    		avitUtil.notifyMsg(200,"节目单导入成功！");
								    	}else
								    	{
								    		avitUtil.notifyMsg(400,"节目单导入失败！");
								    	}
							    	avitUtil.notifyMsg(response.code,response.message);
							    },
							    error: function (err)
							    {
							    	avitUtil.notifyMsg(500,"系统异常，节目单导入失败！");
							    },
							});
					  };
					 
					 // 绑定按钮事件
					  $scope.initBtnEvent  = function() 
					  {
						    $("a[name='operBtn']").each(
						        function ()
						    	{
					    			var opType = $(this).attr("opType");
					    			var id = $(this).attr("opVal");
					    			$(this).click(function ()
			    					{
			    						if('view'==opType)
		    							{
			    							$scope.showDetail(id);
			    							$('#showDetailModal').modal('show');
		    							}
			    						else if('edit'==opType)
		    							{
			    							$scope.goToUrl('channelEpgEdit.html?id='+id);
		    							}
			    						else if('del'==opType)
		    							{
			    							avitUtil.delDataSingle(id,$scope);
		    							}
		    					});
					    	});
					 };
					 
					     // 按钮触发查询数据
	                      $scope.searchData = function () 
						  {
	                    	  avitUtil.searchData($scope,$scope.tableId);
						  };	
	                     
						  // 重置表单条件
	                     $scope.resetForm = function () 
	                     {
	                    	 avitUtil.resetForm($scope);
	                    	 avitUtil.searchData($scope,$scope.tableId);
	                     };
	                     
	                     $scope.goToUrl = function (pageName)
	                     {  
	                    	 avitUtil.goToUrlWithMemory2($scope,"/itdap/page/system/"+pageName);
	                     };
	                     
	                     // 刪除数据
	                     $scope.delDatas = function (ids)
	                     {        
	                    	      $.ajax(
	   							   {
	   								    type: 'DELETE',
	   								    url: "/itdap/channel/epgs/"+ids ,
	   								    data: {} ,
	   								    dataType: "json",
	   								    contentType:'application/json',
	   								    async:false,
	   								    success: function (response)
	   								    {
	   								    	// avitTips.alert("操作成功！").on(function
											// () { });
	   								    	$scope.searchData();
	   								    	if(response.code='200')
	   								    	{
	   								    		avitUtil.notifyMsg(200,"删除成功！");
	   								    	}else
	   								    	{
	   								    		avitUtil.notifyMsg(400,"删除失败！");
	   								    	}
	   								    },
	   								    error: function (err)
	   								    {
	   								    	avitUtil.notifyMsg(500,"删除失败，系统异常！");
	   								    },
	   								});
	                     };
	                    
	                     
	                     
	                     // 显示单个详情
                    	  $scope.showDetail = function (id)
 	                     { 
 							   var selectRow = $('#'+$scope.tableId).bootstrapTable('getRowByUniqueId', id);
 							   var row = {
 									  epgName:selectRow.epgName,
 									  startTime:avitUtil.formatTime(selectRow.startTime),
 									  duration:selectRow.duration,
 									  channelCode:selectRow.channel.channelCode,
 									  channelName:selectRow.channel.channelName,
 									  remark:selectRow.remark,
 								   };
 							   $scope.$apply(function(){
 								   $scope.editObj = row;
 							   });
 						    
 	                     };
				});