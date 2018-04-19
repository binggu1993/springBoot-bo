var app = angular.module('objApp',['ui.bootstrap','avit.services']);
app.config(['$locationProvider', function($locationProvider) {  
	//console.log($locationProvider);
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'objCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					var path = $location.path(); // will tell you the current path
					path = "/"+path.substr(1).split('/')[0]; 

					 $scope.tableId = "objTable";
					 avitUtil.initHashParams($scope);
					 
					 $scope.queryParams = function (params)
					 {
//						 return avitUtil.queryParams(params,$scope);

						 $scope.isHash = false;
						    var params = {};
						    
						    $("#searchConditionDiv").find("[ng-model]").each(
				    			 function ()
				    			 {   
				    				 var name = $(this).attr("ng-model");
				    				 params[name] = $scope[name];
				    			 }
				    	     );
						    
						    return params;
				    
				     };
				    
				    //操作按鈕
					 $scope.actionFormatter = function (value, row, index) 
					 {
						  var result = "";
						  //result += "<a name='operBtn' opType='view' opVal='"+row.id+"' href='javascript:;' class='roleView btn btn-xs green' title='查看' ><span class='glyphicon glyphicon-search'></span></a>";
						  result += "<a name='operBtn' opType='edit' opVal='"+row.id+"' href='javascript:;' class='roleEdit btn btn-xs blue'  title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
						  result += "<a name='operBtn' opType='del' opVal='"+row.id+"'  href='javascript:;' class='roleDel btn btn-xs red'    title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
						  return result;
					 };

					 
					 
					//設置bootstrapTable的參數
					$scope.gridParams = 
					{
						   url:  path+'/page/channels?rnd=' + Math.random(),      
						   method: 'GET',      
						   toolbar: '#toolbar',    
						   striped: true,     
						   cache: false,      
						   pagination: false,     
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
						    sortable: true
						   }, {
						    field: 'channelName',
						    title: '频道名称',
						    sortable: true
						   }, {
						    field: 'channelCode',
						    title: '频道编码',
						    sortable: true
						   }, {
						    field: 'serviceId',
						    title: '服务id',
						    sortable: true
						   }, {
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
					  //初始化表格數據 
					  $scope.initMainTable  = function() 
					  {
					     $('#'+$scope.tableId).bootstrapTable($scope.gridParams);
					     $('#btn_add').click(function()
					     {
					    	 $('#addModal').on('hidden.bs.modal', function (e) {
					    		 $scope.$apply(function(){
									   $scope.addObj = null;
							    	 });
					    	 });
							 $('#addModal').modal();
					     });
					     $('#btn_delete').click(function()
					     {
					    	avitUtil.delDataBatch($scope);
					     });
					     $('#btn_import').click(function(e)
							{
							$('#uploadModal').modal();
						  });
					     $('#btn_exportTpl').click(function(e){
							 //alert("导出模版");
					    	 avitUtil.goToUrl(path+"/channelstpl/download");
						});
					  };
					  $scope.initMainTable();
					 
					 //綁定按鈕事件
					  $scope.initBtnEvent  = function() 
					  {
						    $("a[name='operBtn']").each(
						        function ()
						    	{
					    			var opType = $(this).attr("opType");
					    			var id = $(this).attr("opVal");
					    			$(this).click(function ()
			    					{
			    						if('edit'==opType)
		    							{
			    							$scope.initEditData(id);
			    							
											$('#editModal').modal('show');
		    							}
			    						else if('del'==opType)
		    							{
			    							avitUtil.delDataSingle(id,$scope);
		    							}
		    					});
					    	});
					 };
					 
					     //按鈕觸發查詢數據
	                      $scope.searchData = function () 
						  {
	                    	  avitUtil.searchData($scope,$scope.tableId);
						  };	
	                     
						  //重置表單條件
	                     $scope.resetForm = function () 
	                     {
	                    	 avitUtil.resetForm($scope);
	                    	 avitUtil.searchData($scope,$scope.tableId);
	                     };
	                     
	                     /*$scope.goToUrl = function (pageName)
	                     {  
	                    	 avitUtil.goToUrlWithMemory2($scope,path+"/page/system/"+pageName);
	                     };*/
	                     
	                     //刪除數據
	                     $scope.delDatas = function (ids)
	                     {        
	                    	      $.ajax(
	   							   {
	   								    type: 'DELETE',
	   								    url: path+"/channels/"+ids ,
	   								    data: {} ,
	   								    dataType: "json",
	   								    contentType:'application/json',
	   								    async:false,
	   								    success: function (response)
	   								    {
	   								    	$scope.searchData();
	   								    	avitUtil.notifySuccessMsg(response.message);
	   								    },
	   								    error: function (err)
	   								    {
	   								    	avitUtil.notifyErrorMsg("delete error");
	   								    },
	   								});
	                     };
					   $scope.initEditData = function (id) 
					   {
						   var selectRow = $('#'+$scope.tableId).bootstrapTable('getRowByUniqueId', id);
						   var row = {
									channelName:selectRow.channelName,
									channelCode:selectRow.channelCode,
									serviceId:selectRow.serviceId,
									channelType:selectRow.channelType,
									id:selectRow.id
							   };
						   $scope.$apply(function(){
							   $scope.editObj = row;
						   });
					    };
	                      $scope.uploadRequest = function () 
						  {
	                    	  var formData = new FormData();  
	                          formData.append('filename', $('#filename')[0].files[0]);  
							   $.ajax(
							   {
								   url:path+"/channels/upload",
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
								    	//avitUtil.showPopover($("#btn_import"),response.code,response.message);
								    	avitUtil.notifyMsg(response.code,response.message);
								    },
								    error: function (err)
								    {
								    	//avitUtil.showPopover($("#btn_import"),500,"upload fail");
								    	avitUtil.notifyErrorMsg("upload fail");
								    },
								});
						  };
						  
	                      $scope.sendEditRequest = function (opType) 
						  {
	                    	  
							  var urlstr='';
							  var obj = null;
							   if(opType!=null&&opType != "add"){
								   urlstr = path+"/channels/"+$scope.editObj.id;
								   $scope.editObj.channelType = 1;
								   obj = $scope.editObj;
							   }else{
								   urlstr=path+"/channels";
								   $scope.addObj.channelType = 1;
								   obj = $scope.addObj;
							   }
							   $.ajax(
							   {

								    type: 'POST',
								    url: urlstr ,
								    data: JSON.stringify(obj) ,
								    dataType: "json",
								    contentType:'application/json;charset=UTF-8',
								    async:false,
								    success: function (response)
								    {
								    	
								    	$scope.searchData();
								    	
								    	var code = 200;
								    	var msg = opType+" success";
								    	
								    	if(response.id==null){
							    			code = 400;
							    			msg = opType + " fail";
							    		}
								    	avitUtil.notifyMsg(code,msg);
								    },
								    error: function (err)
								    {
								    	avitUtil.notifyErrorMsg(opType + " fail");
								    },
								});
						  };
				});