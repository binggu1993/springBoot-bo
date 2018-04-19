var app = angular.module('playBackNotRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'playBackNotRealTimeCtrl',
				function($scope,$location,$http,$sce,$timeout,avitUtil,chartUtil) 
				{
					$scope.isShowPeriod=true;
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
                  	  $scope.channelWatchCount=function(flag)
                	  {
                    		figureType=1;
                    		chartUtil.kibanaChartData($scope,$sce,flag,'playBackNotRealTimeChannelWatchCount');
                	  }; 
                    	  
                      	  $scope.channelUserCount=function(flag)
                    	  {
                      		figureType=2;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'playBackNotRealTimeChannelUserCount');
                    	  };
 	                 
                    	  $scope.epgWatchCount=function(flag)
                    	  {
                      		figureType=3;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'playBackNotRealTimeEpgWatchCount');
                    	  };
 	                 
                    	  
                    	  $scope.epgUserCount=function(flag)
                    	  {
                      		figureType=4;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'playBackNotRealTimeEpgUserCount');
                    	  };
 	                 
                    	  $scope.channelDuration=function(flag)
                    	  {
                      		figureType=5;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'playBackNotRealTimeChannelDuration');
                    	  };
 	                 
                    	
                    	  $scope.sendSearchRequest=function(flag,figure)
                    	  {
                    		  if(figure)
                    		  {
                    			  figureType=figure;
                    	      }

                    		  if(figureType==1)
                  			{
                  		        $scope.channelWatchCount(flag);
                  			}else if(figureType==2)
                  			{
                  				 $scope.channelUserCount(flag);
                  			}else if(figureType==3)
                  			{
                  				$scope.epgWatchCount(flag);
                  			}
                  			else if(figureType==4)
                  			{
                  				$scope.epgUserCount(flag);
                  			}else if(figureType==5)
                  			{
                  				$scope.channelDuration(flag);
                  			}
                    	  }
                    	  
 	                     $scope.initData = function ()
 	                     { 
 	                    	chartUtil.initDianBoDatePicker($scope,$timeout);
 	   				       //初始化频道、频道组和区域
 	                   	    chartUtil.initChannelAndChannelGroups($scope);
	                    	chartUtil.intArea($scope);
	                    	 // avitUtil.initHashPage($scope);
	                    	  $scope.sendSearchRequest(1,1);
 	                     };
 	                    $scope.initData();
                    	  
				});
