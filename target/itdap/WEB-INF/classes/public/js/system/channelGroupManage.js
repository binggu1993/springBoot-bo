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
						 $scope.searchChannelType = 2;
						 return avitUtil.queryParams(params,$scope);
				     };
				    
				    //操作按鈕
					 $scope.actionFormatter = function (value, row, index) 
					 {
						  var result = "";
						  //result += "<a name='operBtn' opType='view' opVal='"+row.id+"' href='javascript:;' class='roleView btn btn-xs green' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
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
						   pagination: true,     
						   sortable: true,      
						   sortOrder: "asc",     
						   sidePagination: "server",   
						   pageNumber: $scope.pageNumber,      
						   pageSize: $scope.pageSize,     
						   pageList: [5,10,30],  
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
						    title: '频道组名称',
						    sortable: true
						   }, {
						    field: 'channelCode',
						    title: '频道组编码',
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
					    	 $('#addModal').on('show.bs.modal', function (e) {
					    		 $scope.$apply(function(){
									   $scope.addObj = null;
							    	 });
					    		 
					    		 $("#add_subChannels").multipleSelect({
		 								position:'top',
		 								maxHeight:150,
								            filter: true,
								            placeholder: "select channels"
								        });
		 						 $scope.initAddData();
					    	 });
							 $('#addModal').modal();
					     });
					     $('#btn_delete').click(function()
					     {
					    	avitUtil.delDataBatch($scope);
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
			    							$("#edit_subChannels").multipleSelect({
			    								position:'top',
			    								maxHeight:150,
		    						            filter: true,
		    						            placeholder: "select channels"
		    						        });
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
	   								    	//avitTips.alert("操作成功！").on(function () {   });	   					
	   								    	$scope.searchData();
	   								    	avitUtil.notifySuccessMsg(response.message);
	   								    },
	   								    error: function (err)
	   								    {
	   								    	avitUtil.notifyErrorMsg("delete error");
	   								    },
	   								});
	                     };
	                   
	                   $scope.initAddData = function(){
	                	 //初始化多选框
						   $.ajax(
								   {
										type: 'GET',
										url: path+"/channel/allChannel",
										data: {} ,
										dataType: "json",
										async:false,
										success: function (data)
										{
											var optarr = [];
											$("#add_subChannels").empty();
											var optemp;
											angular.forEach(data, function (value, key){
												opttemp = $("<option />", {
								                    value: value.id,
								                    text: value.channelName
								                });
												optarr.push(opttemp);
											});
											 $("#add_subChannels").append(optarr).multipleSelect("refresh");
										},
										error: function (err)
										{
											avitUtil.notifyErrorMsg("search all channels error");
										},
							});
	                   };
	                     
					   $scope.initEditData = function (id) 
					   {
						   var selectRow = $('#'+$scope.tableId).bootstrapTable('getRowByUniqueId', id);
						   var subchannelids = [];
						   angular.forEach(selectRow.subChannels, function (value, key){
							   subchannelids.push(value.sub.id);
						   });
						   
						   var row = {
								channelName:selectRow.channelName,
								channelCode:selectRow.channelCode,
								serviceId:selectRow.serviceId,
								channelType:selectRow.channelType,
								id:selectRow.id
						   };
						   
						 //初始化多选框  代码有重复可以跟上面的合并
						   $.ajax(
								   {
										type: 'GET',
										url: path+"/channel/allChannel",
										data: {} ,
										dataType: "json",
										async:false,
										success: function (data)
										{
											var optarr = [];
											$("#edit_subChannels").empty();
											var optemp;
											angular.forEach(data, function (value, key){
												opttemp = $("<option />", {
								                    value: value.id,
								                    text: value.channelName
								                });
												if($.inArray(value.id,subchannelids)!=-1){
													opttemp.prop("selected", true);
												}
												optarr.push(opttemp);
											});
											 $("#edit_subChannels").append(optarr).multipleSelect("refresh");
										},
										error: function (err)
										{
											avitUtil.notifyErrorMsg("search all channels error");
										},
							});
						   
						   $scope.$apply(function(){
							   //loading data;
							   $scope.editObj = row;
						   });
						   
					    };
	                      $scope.sendEditRequest = function (opType) 
						  {
	                    	  
							  var urlstr='';
							  var obj = null;
							   if(opType!=null&&opType != "add"){
								   urlstr = path+"/channels/"+$scope.editObj.id;
								   obj = $scope.editObj;
								   var selectedarr = $('#edit_subChannels').multipleSelect('getSelects');
								   var subrefs = [];
								   angular.forEach(selectedarr, function (value, key){
									   var subChannelref=new Object();
									   var channelobj = new Object();
									   channelobj.id = value;
									   subChannelref.sub = channelobj;
									   subrefs.push(subChannelref);
								   });
								   obj.subChannels = subrefs;
								   
							   }else{
								   urlstr=path+"/channels";
								   obj = $scope.addObj;
								   obj.channelType = 2;
								   obj.serviceId = 0;
								   var selectedarr = $('#add_subChannels').multipleSelect('getSelects');
								   var subrefs = [];
								   angular.forEach(selectedarr, function (value, key){
									   var subChannelref=new Object();
									   var channelobj = new Object();
									   channelobj.id = value;
									   subChannelref.sub = channelobj;
									   subrefs.push(subChannelref);
								   });
								   obj.subChannels = subrefs;
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
								    	avitUtil.notifySuccessMsg(opType+" success!");
								    },
								    error: function (err)
								    {
								    	avitUtil.notifyErrorMsg(opType+" error!");
								    },
								});
						  };
				});