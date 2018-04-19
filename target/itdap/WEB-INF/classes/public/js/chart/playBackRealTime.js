var app = angular.module('playBackRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'playBackRealTimeCtrl',
				function($scope,$location,$http,$sce,avitUtil,chartUtil) 
				{
					                 $scope.isShowPeriod=true;
					                 var figureType=1;//1.节目收视次数2.频道收视次数3.节目收视人数 4.频道收视人数
					                 $scope.isReflesh=0; //0刷新；1不刷新
					                 $scope.selectChannels=[];//选中的频道或频道组
									 var updateSelected = function(action,id){ 
										if(action == 'add' && $scope.selected.indexOf(id) == -1){ 
										  $scope.selected.push(id); 
										} 
										if(action == 'remove' && $scope.selected.indexOf(id)!=-1){ 
										  var idx = $scope.selected.indexOf(id); 
										  $scope.selected.splice(idx,1); 
										} 
									  } 
									 
									  $scope.updateSelection = function($event, id){ 
										var checkbox = $event.target; 
										var action = (checkbox.checked?'add':'remove'); 
										updateSelected(action,id); 
									  }
									  $scope.selectArea = function(code){
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
					
                      //点击生成各个报表
                  	  $scope.EpgWatchCount=function(flag)
                	  {
                  		figureType=1;
                  		$scope.isShowPeriod=false;
                  		chartUtil.kibanaChartData($scope,$sce,flag,'playBackRealTimeEpgWatchCount');
                  		  
                	  };    
 	                 
                    	  $scope.ChannelWatchCount=function(flag)
                    	  {
                    		  figureType=2;
                    		  chartUtil.kibanaChartData($scope,$sce,flag,'playBackRealTimeChannelWatchCount');
                    	  };
                    	  
                    	  
                    	  $scope.EpgUserCount=function(flag)
                    	  {
                    		  figureType=3;
                    		  chartUtil.kibanaChartData($scope,$sce,flag,'playBackRealTimeEpgUserCount');
                    	  };
                    	  
                    	  $scope.ChannelUserCount=function(flag)
                    	  {
                    		  figureType=4;
                    		  $scope.isShowPeriod=true;
                    		  chartUtil.kibanaChartData($scope,$sce,flag,'playBackRealTimeChannelUserCount');
                    	  };
                    	
                    	  //监控和停止监控
                    	  $scope.sendSearchRequest=function(flag,isRefleshs)
                    	  {
                    		  if(isRefleshs)
                    		  {
                    			  isReflesh=isRefleshs;
                    		  }
                    		  
                    		  if(figureType==1)
                  			{
                  		        $scope.EpgWatchCount(flag);
                  			}else if(figureType==2)
                  			{
                  				 $scope.ChannelWatchCount(flag);
                  			}else if(figureType==3)
                  			{
                  				$scope.EpgUserCount(flag);
                  			}
                  			else if(figureType==4)
                  			{
                  				$scope.ChannelUserCount(flag);
                  			}
                    	  }
                    	  
                    	  //初始化页面
 	                     $scope.initData = function ()
 	                     { 
 	                    	//初始化监控时间长度
 	                    	$scope.period = [
 	 	 									{mins : "最近10分钟", min : "10"},
 	 	 									{mins : "最近30分钟", min : "30"},
 	 	 									{mins : "最近60分钟", min : "60"}
 	 	 								];
 	                    	$scope.selMin=$scope.period[0].min;
 	                    	//初始化频道、频道组和区域
 	                   	    chartUtil.initChannelAndChannelGroups($scope);
	                    	chartUtil.intArea($scope);
	                    	$scope.sendSearchRequest(1,0);
 	                     };
 	                    $scope.initData();
                    	  
				});
