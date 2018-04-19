  var app = angular.module('areaEditApp',['avit.services']);
  
  app.config(['$locationProvider', function($locationProvider) {  
      $locationProvider.html5Mode({
       enabled: true,
       requireBase: false
     });
     }]);
  
  app.controller(
				'areaEditCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					  $scope.id = $location.search().id;
					   $scope.initEditData = function () 
					   {
				           $.ajax(
						   {
							    type: 'GET',
							    url: "/itdap/areas/"+$scope.id ,
							    data: {} ,
							    dataType: "json",
							    async:false,
							    success: function (data)
							    {
							    	 $scope.editObj = data;
							    	 //$scope.editObj.sort = parseInt(data.sort);
							    	 //$scope.editObj.pid = data.pid+'';
							    },
							    error: function (err)
							    {
							    	alert("search channel by id error...");
							    },
							});
					    };
					 	
	                      $scope.sendEditRequest = function () 
						  {
	                    	  var url="/itdap/areas/";
	                    	  var requestType='POST';
	                    	  if($scope.id)
	                    	  {
	                    		  url="/itdap/areas/"+$scope.id;
	                    		  requestType='PUT';
	                    	  }
							   $.ajax(
							   {
								    type: requestType,
								    url:  url,
								    data: JSON.stringify($scope.editObj) ,
								    dataType: "json",
								    contentType:'application/json',
								    async:false,
								    success: function (response)
								    {
								    	avitTips.alert("操作成功！").on(function () {avitUtil.goToUrlWithHash("/itdap/page/system/areaList.html")});
								    	avitUtil.goToUrlWithHash("/itdap/page/system/areaList.html");
								    },
								    error: function (err)
								    {
								    	alert("Add obj error...");
								    },
								});
						  };
	                     
						  $scope.goToUrl = function (url)
	                      {  
							  debugger;
							  window.location.href = "/itdap/page/system/"+url+window.location.hash;
	                      }
						  
						  if($scope.id)
						  {
						  $scope.initEditData();
						  }
					
			
				});

