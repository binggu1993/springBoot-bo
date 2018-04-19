  var app = angular.module('menuEditApp',['avit.services']);
  
  app.config(['$locationProvider', function($locationProvider) {  
      $locationProvider.html5Mode({
       enabled: true,
       requireBase: false
     });
     }]);
  
  app.controller(
				'menuEditCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					  $scope.id = $location.search().id;
					   $scope.initMenuData = function () 
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
							    	 $scope.rootMenus = data;
							    },
							    error: function (err)
							    {
							    	alert("searchMenusByPid error...");
							    },
							});
						   
						   
				           $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/menus/"+$scope.id ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 $scope.menuObj = data;
							    	 $scope.menuObj.sort = parseInt(data.sort);
							    	 $scope.menuObj.pid = data.pid+'';
							    },
							    error: function (err)
							    {
							    	alert("searchMenusByPid error...");
							    },
							});
					    }
					
					 	
	                      $scope.sendEditRequest = function () 
						  {
							   $.ajax(
							   {
								    type: 'PUT',
								    url: "/itdap/menus/"+$scope.id ,
								    data: JSON.stringify($scope.menuObj) ,
								    dataType: "json",
								    contentType:'application/json',
								    async:false,
								    success: function (response)
								    {
								    	//avitTips.alert("操作成功！").on(function () {avitUtil.goToUrlWithHash("/itdap/page/devToolsPages/tableModelList.html")});
								    	avitUtil.goToUrlWithHash("/itdap/page/devToolsPages/tableModelList.html");
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
						  
						  $scope.initMenuData();
						
					
			
				});

