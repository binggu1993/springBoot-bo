var app = angular.module('zhiboRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'zhiboRealTimeCtrl',
				function($scope,$location,$http,$sce,avitUtil,chartUtil) 
		        {
			
			                 var figureType=1;//1表示统计点击次数；2点击用户数；3收视率；4市场占有率；5频道流入流出
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
          	  $scope.showWatchCount=function(flag)
        	  {
          		figureType=1;
          		 initPeriod();
          		chartUtil.kibanaChartData($scope,$sce,flag,'zhiboRealTimeWatchCount');
          		  
        	  };    
             
            	  $scope.showUsersCount=function(flag)
            	  {
            		  figureType=2;
            		  chartUtil.kibanaChartData($scope,$sce,flag,'zhiboRealTimeUserCount');
            	  };
            	  
            	  
            	  $scope.showRatings=function(flag)
            	  {
            		  figureType=3;
            			 //监控时间限定为5分钟不可选。
              		  $scope.period = [
  	 									{mins : "最近5分钟", min : "5"},
  	 								];
  	                    $scope.selMin=$scope.period[0].min;
            		  chartUtil.kibanaChartData($scope,$sce,flag,'zhiboRealTimeRatings');
            	  };
            	  
            	  $scope.showMarketRatings=function(flag)
            	  {
            		  figureType=4;
            		  chartUtil.kibanaChartData($scope,$sce,flag,'zhiboRealTimeMarketRatings');
            	  };
            	  
            	  $scope.showInAndOut=function(flag)
            	  {

            		  figureType=5;
            		  chartUtil.kibanaChartData($scope,$sce,flag,'zhiboRealTimeInAndOut');
            	  
            	  };
            	
            	  //监控和停止监控
            	  $scope.sendSearchRequest=function(flag,isRefleshs)
            	  {
            		  if(isRefleshs)
            		  {
            			  $scope.isReflesh=isRefleshs;
            		  }
            		  
            		  
            		  if(figureType==1)
          			{
            			 
          		        $scope.showWatchCount(flag);
          			}else if(figureType==2)
          			{
          				 $scope.showUsersCount(flag);
          				 
          			}else if(figureType==3)
          			{
          				$scope.showRatings(flag);
          			}
          			else if(figureType==4)
          			{
          				$scope.showMarketRatings(flag);
          			}else if(figureType==5)
          			{
          				$scope.showInAndOut(flag);
          			}
            	  }
            	  var initPeriod=function()
            	  {
            	       	//初始化监控时间长度
                	$scope.period = [
								{mins : "最近1分钟", min : "1"},
								{mins : "最近5分钟", min : "5"},
								{mins : "最近10分钟", min : "10"}
							];
                	$scope.selMin=$scope.period[0].min; 
            	  }
            	  
            	  //初始化页面
                 $scope.initData = function ()
                 { 
                	initPeriod();
                	//初始化刷新频率
                	$scope.ref = [
								{refs : "1分钟", refv : "60"},
								{refs : "5分钟", refv : "300"}
							];
                	
                	$scope.selRefresh=$scope.ref[0].refv;
                	//初始化频道、频道组和区域
                	chartUtil.initChannelAndChannelGroups($scope);
                	chartUtil.intArea($scope);
                	$scope.sendSearchRequest(1,0);
                 };
                $scope.initData();
            	  
		});
