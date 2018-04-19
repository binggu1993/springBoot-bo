var app = angular.module('menuApp',['ui.bootstrap','avit.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'menuCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					 $scope.tableId = "menuTable";
					 avitUtil.initHashParams($scope);
					 
					 $scope.queryParams = function (params)
					 {
						 return avitUtil.queryParams(params,$scope);
				     };
				    
				    //操作按鈕
					 $scope.actionFormatter = function (value, row, index) 
					 {
						  var result = "";
						  result += "<a name='operBtn' opType='view' opVal='"+row.id+"' href='javascript:;' class='roleView btn btn-xs green' title='查看'><span class='glyphicon glyphicon-search'></span></a>";
						  result += "<a name='operBtn' opType='edit' opVal='"+row.id+"' href='javascript:;' class='roleEdit btn btn-xs blue'  title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
						  result += "<a name='operBtn' opType='del' opVal='"+row.id+"'  href='javascript:;' class='roleDel btn btn-xs red'    title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
						  return result;
					 };
					 
					//設置bootstrapTable的參數
					$scope.gridParams = 
					{
						   url:  '/itdap/menusPage2?rnd=' + Math.random(),      
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
						   search: true,      
						   strictSearch: false,
						   showColumns: true,     
						   showRefresh: true,     
						   minimumCountColumns: 2,    
						   clickToSelect: true,    
						   showToggle: true,    
						   cardView: false,     
						   detailView: false,     
						   queryParams : $scope.queryParams, 
						   columns: [{
						    checkbox: true,
						    visible: true     
						   }, {
						    field: 'id',
						    title: '编号',
						    sortable: true
						   }, {
						    field: 'name',
						    title: '菜单名称',
						    sortable: true
						   }, {
						    field: 'pid',
						    title: '父菜单',
						    sortable: true
						   }, {
						    field: 'url',
						    title: '访问地址',
						    sortable: true
						   }, {
						    field: 'icon',
						    title: '图标样式',
						    sortable: true
						   }, {
						    field: 'type',
						    title: '菜单类型',
						    sortable: true
						   }, {
						    field: 'sort',
						    title: '排序',
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
					    	 $scope.goToUrl('tableModelAdd2.html');
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
			    						if('view'==opType)
		    							{
			    							$scope.showDetail(id);
		    							}
			    						else if('edit'==opType)
		    							{
			    							$scope.goToUrl('tableModelEdit2.html?id='+id);
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
	                     };
	                     
	                     $scope.goToUrl = function (pageName)
	                     {  
	                    	 avitUtil.goToUrlWithMemory2($scope,"/itdap/page/devToolsPages/"+pageName);
	                     };
	                     
	                     //刪除數據
	                     $scope.delDatas = function (ids)
	                     {        
	                    	      $.ajax(
	   							   {
	   								    type: 'DELETE',
	   								    url: "/itdap/menus/"+ids ,
	   								    data: {} ,
	   								    dataType: "json",
	   								    contentType:'application/json',
	   								    async:false,
	   								    success: function (response)
	   								    {
	   								    	//avitTips.alert("操作成功！").on(function () {   });	   					
	   								    	$scope.searchData();
	   								    },
	   								    error: function (err)
	   								    {
	   								    	alert("del menu error...");
	   								    },
	   								});
	                     };
	                    
	                     //顯示單個 詳情
                    	  $scope.showDetail = function (id)
 	                     { 
                    		   $.ajax(
    						   {
    							    type: 'GET',
    							    url: "/itdap/menus/"+id ,
    							    data: {} ,
    							    dataType: "json",
    							    async:false,
    							    success: function (data)
    							    {
    							    	// $scope.menuObj = data;
    							    	 $("#idView").val(data.id);
    							    	 $("#nameView").val(data.name);
    							    	 $("#pidView").val(data.pid);
    							    	 $("#typeView").val(data.type);
    							    	 $("#iconView").val(data.icon);
    							    	 $("#urlView").val(data.url);
    							    	 $("#sortView").val(data.sort);
    							    },
    							    error: function (err)
    							    {
    							    	alert("searchMenusByPid error...");
    							    },
    							});
                    		    $('#detailModal').modal();
 	                     };
				});