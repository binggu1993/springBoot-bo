var app = angular.module('zhiboNotRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'zhiboNotRealTimeCtrl',
				function($scope,$location,$http,$sce,$timeout,avitUtil,chartUtil) 
				{
					
				    var figureType=1;//1表示统计点击次数；2点击用户数；3收视率；4市场占有率；5频道流入流出
				    $scope.selectChannels=[];//选中的频道或频道组
				    $scope.startTimeMinDate;
				    $scope.endtTimeMaxDate;

					$scope.selectArea = function(code)
					{
					     $scope.theArea=code;
					 }
					
					$scope.channelDoCheck=function()
					  {
						  chartUtil.doCheck("channelBox",5);
					  }
					  
					  $scope.channelGroupDoCheck=function()
					  {
						  chartUtil.doCheck("channelGroupBox",3);
					  }
                  	  $scope.showWatchCount=function(flag)
                	  {
                    		figureType=1;
                    		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeWatchCount');
                	  }; 
                      	  
 	                 
                    	  $scope.showUsersCount=function(flag)
                    	  {
                      		figureType=2;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeUserCount');
                    	  };
                    	  
                    	  $scope.showWatchDuration=function(flag)
                    	  {
                      		figureType=3;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeWatchDuration');
                    	  };
                    	  
                    	  $scope.showRatings=function(flag)
                    	  {
                      		figureType=4;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeRatings');
                    	  };
 	                 
                    	  $scope.showMarketRatings=function(flag)
                    	  {
                      		figureType=5;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeMarketRatings');
                    	  };
                    	  $scope.showIn=function(flag)
                    	  {
                      		figureType=6;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeIn');
                    	  };
                    	  $scope.showOut=function(flag)
                    	  {
                      		figureType=7;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboNotRealTimeOut');
                    	  };
 	                 
                    	
                    	  $scope.sendSearchRequest=function(flag,figure)
                    	  {
                    		  if(figure)
                    		  {
                    			  figureType=figure;
                    	      }
                    		    if(figureType==2)
                    		    {
                    		        var limitPeriod=parseInt(avitUtil.chartPeriod())*86400;
                         			var startTime=avitUtil.dateFormat($scope.newWordQueryObj.startTime,'yyyy/MM/dd hh:mm:ss');
                                   	var endTime=avitUtil.dateFormat($scope.newWordQueryObj.endTime,'yyyy/MM/dd hh:mm:ss');
                         		    var choosePeriod=(new Date(endTime).getTime()-new Date(startTime).getTime())/1000;
                					if(choosePeriod>limitPeriod)
                					{
                					$scope.newWordQueryObj.startTime=new Date();
                    		    	$scope.newWordQueryObj.endTime=new Date();
                    		    	chartUtil.initDatePickerStartTimeFormat($scope);
                    		    	chartUtil.initDatePickerEndTimeFormat($scope);
                    		    	alert("统计时间超出可选范围，请重新选择时间！");
                					}
                    		    }else
                    		    {
                    		    	$scope.startTimeMinDate=null;
                    		    	$scope.endtTimeMaxDate=null;
                    		    }

                    		  if(figureType==1)
                  			{
                  		        $scope.showWatchCount(flag);
                  			}else if(figureType==2)
                  			{
                  				 $scope.showUsersCount(flag);
                  			}else if(figureType==4)
                  			{
                  				$scope.showRatings(flag);
                  			}
                  			else if(figureType==5)
                  			{
                  				$scope.showMarketRatings(flag);
                  			}else if(figureType==6)
                  			{
                  				$scope.showIn(flag);
                  			}else if(figureType==3)
                  			{
                  				$scope.showWatchDuration(flag);
                  			}else if(figureType==7)
                  			{
                  				$scope.showOut(flag);
                  			}
                    	  }
                    	  
 	                     $scope.initData = function ()
 	                     { 
 	                    	chartUtil.initZhiboDatePicker($scope,$timeout);
 	   				       //初始化频道、频道组和区域
 	                   	    chartUtil.initChannelAndChannelGroups($scope);
	                    	chartUtil.intArea($scope);
	                    	 // avitUtil.initHashPage($scope);
	                    	  $scope.sendSearchRequest(1,1);
 	                     };
 	                    $scope.initData();
                    	  
				});
