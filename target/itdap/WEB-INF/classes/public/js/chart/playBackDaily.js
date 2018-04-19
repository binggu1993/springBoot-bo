var app = angular.module('playBackTop',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'playBackTopCtrl',
				function($scope,$location,$http,$sce,$timeout,avitUtil,chartUtil) 
			{
					  $scope.selectAptitudes=[];
					  $scope.showChartType="timeCount";//1点播人数份额2点播次数份额3点播时长二分4影片点播排行5影片播放完整度排行
                	  $scope.sendSearchRequest=function(flag,figure)
                	  {
                		  getTopChart();
                	  }
                	  
                	  $scope.change=function()
                	  {
                		  $scope.showChartType=$scope.thechartType;
                		  getTopChart();
                	  }
                	  
                	  var getTopChart=function()
                	  {
                		  
                		  //根据参数请求数据
       	               $.ajax(
       						   {
       							    type: 'GET',
       							    url: '/itdap/playBackReport' ,
       							    data: {
       							    	serviceId:$scope.mychannel,
       							    	date:avitUtil.formatTime($scope.newWordQueryObj.startTime),
       							    	model:$scope.dateType,
       							    	areaCode:$scope.theArea,
       							    	size:$scope.pagess,
       							    	orderBy:$scope.thechartType
       							    } ,
       							    dataType: "json",
       							    async:false,
       							    success: function (data)
       							    {
       							    	$scope.rankDatas = data;
       							    	if(data.length>0)
       							    	{
       							    	if($scope.showChartType=="timeCount")
       							    	{
       							        $scope.maxData=data[0].timeCount;
       							    	}else if($scope.showChartType=="userCount")
       							    	{
       							    		$scope.maxData=data[0].userCount;
       							    	}else if($scope.showChartType=="sumDuration")
       							    	{
       							    		$scope.maxData=data[0].sumDuration;
       							    	}
       							    	}
       							     
       							    },
       							    error: function (err)
       							    {
       							    	alert("getAllChannel error...");
       							    },
       							});
                	  }
                  	  var initPageSize=function()
                	  {
                	       	//初始化监控时间长度
	                    	$scope.pageSizes = [
	 									{numName : "20条", num : "20"},
	 									{numName : "50条", num : "50"},
	 									{numName : "100条", num : "100"}
	 								];
	                    	$scope.pagess=$scope.pageSizes[0].num; 
                	  }
                  	  
                  	  var initChartType=function()
                  	  {
                  		    //初始化直播报表类型
	                    	$scope.chartTypes = [
	                    	            {typeName : "节目回看次数排行", type : "timeCount"},
	 									{typeName : "节目回看用户数排行", type : "userCount"},
	 									{typeName : "节目回总时长排行", type : "sumDuration"}
	 								];
	                    	$scope.thechartType=$scope.chartTypes[0].type; 
	                    	
	                    	$scope.dateTypes = [
	     	                    	            {typeName : "日报表", type : "day"},
	     	 									{typeName : "周报表", type : "week"},
	     	 									{typeName : "月报表", type : "month"}
	     	 								];
	     	                    	$scope.dateType=$scope.dateTypes[0].type; 
                  	  }
                  	  
                  	  
                  	  var initDatePickr=function()
                  	  {

                      	//初始化日期控件
                       	$scope.newWordQueryObj = {
                       		    fileName: '',
                       		    startTime:new Date(),
                       		    order: '0'
                       		}; 
              			    $scope.startDateOptions = {
              		            formatYear: 'yyyy',
              		            startingDay: 1
              		        };
              		        
              		        $scope.format = "yyyy-MM-dd";
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
                  	  
                  	  var initchannelAndGroups=function()
                  	  {
                  		 $.ajax(
      						   {
      							    type: 'GET',
      							    url: "/itdap/channel/allChannel" ,
      							    data: {} ,
      							    dataType: "json",
      							    async:false,
      							    success: function (data)
      							    {
      							    	 $scope.channels = data;
      							    },
      							    error: function (err)
      							    {
      							    	alert("getAllChannel error...");
      							    },
      							});
      	               
                  		
      	               //频道组初始化
      	               $.ajax(
      						   {
      							    type: 'GET',
      							    url: "/itdap/channel/allChannelGroup" ,
      							    data: {} ,
      							    dataType: "json",
      							    async:false,
      							    success: function (data)
      							    {
      							    	 $scope.channelgroups = data;
      							    },
      							    error: function (err)
      							    {
      							    	alert("getAllChannelGroup error...");
      							    },
      							});
      	            $scope.mychannel=$scope.channels[0].serviceId+""; 
      	         	$scope.theChannelGroup=$scope.channelgroups[0].subServiceIds; 
                  	  }
                  
                     $scope.initData = function ()
                     { 
                        initDatePickr();
                    	chartUtil.initAptitude($scope);
                    	initchannelAndGroups();
                    	$scope.theAptitude=$scope.selectAptitudes[0].code;
                    	chartUtil.intArea($scope);  
                    	initPageSize();
                    	initChartType();
                     };
                     
// 	                      
                    $scope.initData();
                     getTopChart();
                	  
			});
