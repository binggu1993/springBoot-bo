  var app = angular.module('channelEditApp',['avit.services']);
  
  app.config(['$locationProvider', function($locationProvider) {  
      $locationProvider.html5Mode({
       enabled: true,
       requireBase: false
     });
     }]);
  
  app.controller(
				'channelEditCtrl',
				function($scope,$location,$http,avitUtil) 
				{
					  $scope.id = $location.search().id;
					   $scope.initEditData = function () 
					   {
						   //alert($scope.id);
						   if(typeof($scope.id) != "undefined"){
							   $.ajax(
							   {
									type: 'GET',
									url: "/itdap/channels/"+$scope.id ,
									data: {} ,
									dataType: "json",
									async:false,
									success: function (data)
									{
										 $scope.editObj = data;
										 console.log($scope.editObj);
									},
									error: function (err)
									{
										alert("search channel by id error...");
									},
								});
						   }
							
					    };
	                      $scope.uploadRequest = function () 
						  {
	                    	  var formData = new FormData();  
	                          formData.append('filename', $('#filename')[0].files[0]);  
							   $.ajax(
							   {
								   url:"/itdap/channels/upload",
								   type: 'POST',
								    cache: false,  
		                            data: formData,  
								    dataType: "json",
								    processData: false,  
		                              contentType: false,
								    async:false,
								    success: function (response)
								    {
								    	if(response.message!=null)
								    		alert(response.message);
								    	avitUtil.goToUrlWithHash("/itdap/page/system/channelList.html");
								    },
								    error: function (err)
								    {
								    	alert("上传失败！");
								    },
								});
						  };
					 	
	                      $scope.sendEditRequest = function () 
						  {
							  var urlstr='';
							   if(typeof($scope.id) != "undefined"){
								   urlstr = "/itdap/channels/"+$scope.id;
							   }else{
								   urlstr="/itdap/channels";
							   }
							   $.ajax(
							   {

								    type: 'POST',
								    url: urlstr ,
								    data: JSON.stringify($scope.editObj) ,
								    dataType: "json",
								    contentType:'application/json;charset=UTF-8',
								    async:false,
								    success: function (response)
								    {
								    	avitUtil.goToUrlWithHash("/itdap/page/system/channelList.html");
								    },
								    error: function (err)
								    {
								    	alert("新增失败！");
								    },
								});
						  };
	                     
						  $scope.goToUrl = function (url)
	                      {  
							  window.location.href = "/itdap/page/system/"+url+window.location.hash;
	                      }
						  
						  $scope.initEditData();
						
					
			
				});

