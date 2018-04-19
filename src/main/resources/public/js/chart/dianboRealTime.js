var app = angular.module('dianboRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'dianboRealTimeCtrl',
				function($scope,$location,$http,$sce,avitUtil,chartUtil) 
				{
						
	                 var figureType=1;//1sp点播次数；2sp点播用户数；3节目点播次数；4节目点播用户数；5栏目点播热度排行
	                 $scope.isReflesh=0; //0刷新；1不刷新
	                 $scope.selectAptitudes=[];
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
							
                  	  $scope.spWatchCount=function(flag)
                	  {
                    		figureType=1;
                      		chartUtil.kibanaChartData($scope,$sce,flag,'dianboRealTimeSpWatchCount');
                      		  
                      };    
 	                 
                     $scope.spUsersCount=function(flag)
                    	  {
                 		figureType=2;
                  		chartUtil.kibanaChartData($scope,$sce,flag,'dianboRealTimeSpUserCount');
                  		  
                  };
                    	  
                    	
                    	  $scope.sendSearchRequest=function(flag,isRefleshs)
                    	  {
                    		  if(isRefleshs)
                    		  {
                    			  $scope.isReflesh=isRefleshs;
                    		  }
                    		  
                    		  if(figureType==1)
                  			{
                  		        $scope.spWatchCount(flag);
                  			}else if(figureType==2)
                  			{
                  				 $scope.spUsersCount(flag);
                  			}
                    	  }
                    	  
 	                     $scope.initData = function ()
 	                     { 
 	                    	 
 	                    	$scope.period = [
 	 									{mins : "最近10分钟", min : "10"},
 	 									{mins : "最近30分钟", min : "30"},
 	 									{mins : "最近60分钟", min : "60"}
 	 								];
 	                    	$scope.selMin=$scope.period[0].min;
 	                   	    chartUtil.initAptitude($scope);
	                    	chartUtil.intArea($scope);      
	                        $scope.sendSearchRequest(1,0);
 	                     };
 	                    $scope.initData();
                    	  
				});
