var app = angular.module('menuAddApp',['avit.services']);


app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);


app.controller(
				'menuAddCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					   $scope.initRootMenus = function () 
					   {
				               $.ajax(
							   {
								    type: 'GET',
								    url: "/itdap/searchMenusByPid" ,
								    data: {} ,
								    dataType: "json",
								    async:false,
								    success: function (data)
								    {
								    	 $scope.menuObj = {};
								    	 $scope.rootMenus = data;
								    	 $scope.menuObj.pid = $scope.rootMenus[0].id+'';
								    },
								    error: function (err)
								    {
								    	alert("searchMenusByPid error...");
								    },
								});
					    }
					
					 	
	                      $scope.sendAddRequest = function () 
						  {
	                    	   $scope.menuObj.icon = "fa fa-fw fa-table";
							   $.ajax(
							   {
								    type: 'POST',
								    url: "/itdap/menus" ,
								    data: JSON.stringify($scope.menuObj) ,
								    dataType: "json",
								    contentType:'application/json',
								    async:false,
								    success: function (response)
								    {
								    	//avitTips.alert("操作成功！").on(function () {avitUtil.goToUrlWithHash("/itdap/page/devToolsPages/tableModelList.html")});
								    	avitUtil.goToUrlWithHash("/itdap/page/devToolsPages/tableModelList2.html");
								    },
								    error: function (err)
								    {
								    	alert("Add menu error...");
								    },
								});
						  };
	                     
	                      $scope.radioEvent = function (isShow,objId) 
						  {
	                           if(isShow)
	                        	{
	                        	    $("#"+objId).show(200);
	                           }
	                           else 
	                           {
	                        	   $("#"+objId).hide(200);
	                           }
						  };
		
						  
						   
		                     $scope.goToUrl = function (url)
		                     {  
		                    	window.location.href = "/itdap/page/devToolsPages/"+url+window.location.hash;
		                     }
		                     
		                     
		                     $scope.initRootMenus();
			
				});

