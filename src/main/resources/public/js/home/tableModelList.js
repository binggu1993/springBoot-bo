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
					      //模糊查询 
	                      $scope.sendSearchRequest = function () 
						  {
	                    	   var params = {"name":$scope.name,"url":$scope.url,"type":$scope.type,"icon":$scope.icon,"currentPage":$scope.currentPage,"pageSize":$scope.itemsPerPage};
	                    	   var ajaxRequestBody =
	                    	   {
	                    			   type:'GET',
	                    			   url:"/itdap/menusPage",
                    				   params:params,
                    				   isAsync:false,
                    				   sucCallback:$scope.setScopePages,
                    				   failCallback:function (){alert("get data error...");}
	                    	   };
	                    	   avitUtil.ajax(ajaxRequestBody);
						  }
	                     
	                      //重置
	                     $scope.clearConditions = function () 
	                     {
	                    	 $scope.name = "";
	                    	 $scope.icon = "";
	                    	 $scope.type = "";
	                    	 $scope.url = "";
	                     };
	                     
	                     //分页参数设置
	                     $scope.setScopePages = function (response) 
	                     {
	                    	 avitUtil.setScopePages($scope,response);
	                     };
	                     
	                     //地址跳转
	                     $scope.goToUrl = function (pageName)
	                     {  
	                    	 avitUtil.goToUrlWithMemory($scope,"/itdap/page/devToolsPages/"+pageName);
	                     };
	                     
	                     $scope.checkAll = function ()
	                     { 
	                    	if ($('#checkAll').prop("checked")) 
	                    	{
	                    		$("input[name='dataSelects']").prop("checked",true);
	                    	}
	                    	else
	                    	{
	                    		$("input[name='dataSelects']").prop("checked",false);
	                    	}
	                     };
	                     
	                     $scope.delDataBatch = function (id)
	                     {        
	                    	 var selectRows = $("input[name='dataSelects']:checked");
                    		 if (selectRows.length <= 0) {
                    			 avitTips.alert("请选择数据！");
                    		     return;
                    		 }
                    		 avitTips.confirm({ message: "确认要删除这些数据吗？" }).on(function (e) {
	                    		 if (!e) {
	                    		  return;
	                    		 }
	                    		 var ids = [];
	                    		 selectRows.each(
                				 function ()
                				 {
                					 ids.push($(this).val());
                				 });
	                    		 $scope.delDatas(ids);
	                    	 });
	                     };
	                     
	                     
	                     
	                     $scope.delDataSingle = function (id)
	                     { 
	                    	 avitTips.confirm({ message: "确认要删除这条数据吗？" }).on(function (e) {
	                    		 if (!e) {
	                    		  return;
	                    		 }
	                    		 var ids = [id];
		                    	 $scope.delDatas(ids);
	                    	 });
	                     }
	                     
	                     
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
	   								    	$scope.reflash();
	   								    },
	   								    error: function (err)
	   								    {
	   								    	alert("Add menu error...");
	   								    },
	   								});
	                     };
	                     
	                     $scope.initData = function ()
 	                     { 
	                    	 avitUtil.initHashPage($scope);
	                    	 $scope.sendSearchRequest();
 	                     };
 	                    $scope.initData();
 	                     
 	                      $scope.reflash = function ()
	                     { 
 	                    	 window.location.reload();
	                     };
	              
	                    
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
    							    	 $scope.menuObj = data;
    							    },
    							    error: function (err)
    							    {
    							    	alert("searchMenusByPid error...");
    							    },
    							});
                    		    $('#detailModal').modal();
 	                     }
				});