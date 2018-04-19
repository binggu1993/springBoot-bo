var app = angular.module('areaApp',['ui.bootstrap','avit.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'areaCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					 $scope.tableId = "objTable";
					 var path = $location.path(); // will tell you the current path
					 path = "/"+path.substr(1).split('/')[0]; 
					 avitUtil.initHashParams($scope);
					 
					 $scope.queryParams = function (params)
					 {
						 return avitUtil.queryParams(params,$scope);
				     };
				    
				    //操作按钮
				     
				      
					 $scope.actionFormatter = function (value, row, index) 
					 {
						  var result = "";
						  //result += "<a name='operBtn' opType='view' opVal='"+row.id+"' href='javascript:;' class='roleView btn btn-xs green' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
						  result += "<a name='operBtn' opType='edit' opVal='"+row.id+"' href='javascript:;' class='roleEdit btn btn-xs blue'  title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
						  result += "<a name='operBtn' opType='del' opVal='"+row.id+"'  href='javascript:;' class='roleDel btn btn-xs red'    title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
						  return result;
					 };
					 
					//设置bootstrapTable的参数
					$scope.gridParams = 
					{
						   url:  '/itdap/page/areas?rnd=' + Math.random(),      
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
						    sortable: true
						   }, {
						    field: 'areaName',
						    title: '区域名称',
						    sortable: true
						   }, {
						    field: 'areaCode',
						    title: '区域编码',
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
					  //初始化表格数据 
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
					  };
					  $scope.initMainTable();
					 
					 //绑定按钮事件
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
					 
					     //按钮触发查询数据
	                      $scope.searchData = function () 
						  {
	                    	  avitUtil.searchData($scope,$scope.tableId);
						  };	
	                     
						  //重置表单条件
	                     $scope.resetForm = function () 
	                     {
	                    	 avitUtil.resetForm($scope);
	                    	 avitUtil.searchData($scope,$scope.tableId);
	                     };
	                     
	                     $scope.goToUrl = function (pageName)
	                     {  
	                    	 avitUtil.goToUrlWithMemory2($scope,"/itdap/page/system/"+pageName);
	                     };
	                     
	                     $scope.initEditData = function (id) 
						   {
							   var selectRow = $('#'+$scope.tableId).bootstrapTable('getRowByUniqueId', id);
							   var row = {
										areaName:selectRow.areaName,
										areaCode:selectRow.areaCode,
										id:selectRow.id
								   };
							   $scope.$apply(function(){
								   $scope.editObj = row;
							   });
						    };
	                     
	                     //刪除数据
	                     $scope.delDatas = function (ids)
	                     {        
	                    	      $.ajax(
	   							   {
	   								    type: 'DELETE',
	   								    url: "/itdap/areas/"+ids ,
	   								    data: {} ,
	   								    dataType: "json",
	   								    contentType:'application/json',
	   								    async:false,
	   								    success: function (response)
	   								    {
	   								    	//avitTips.alert("操作成功！").on(function () {   });
	   								    	if(response.code='200')
	   								    	{
	   								    		avitUtil.notifyMsg(200,"删除成功！");
	   								    	}else
	   								    	{
	   								    		avitUtil.notifyMsg(400,"删除失败！");
	   								    	}
	   								    	
	   								    	$scope.searchData();
	   								    },
	   								    error: function (err)
	   								    {
	   								    	alert("del area error...");
	   								    },
	   								});
	                     };
	                    
						  
	                      $scope.sendEditRequest = function (opType) 
						  {
							  var urlstr='';
							  var obj = null;
							   if(opType!=null&&opType != "add"){
								   urlstr = path+"/areas/"+$scope.editObj.id;
								   obj = $scope.editObj;
							   }else{
								   urlstr=path+"/areas";
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
								    	if(opType=='update')
								    	{
								    		msg="更新区域成功"
								    	}else
								    	{
								    		msg="新增区域成功"
								    	}
								    	
								    	
								    	
								    	if(response.id==null){
							    			code = 400;
							    			if(opType=='update')
									    	{
									    		msg="更新区域失败"
									    	}else
									    	{
									    		msg="新增区域失败"
									    	}
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