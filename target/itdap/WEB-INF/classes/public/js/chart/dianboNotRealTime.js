var app = angular.module('dianboNotRealTime',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'dianboNotRealTimeCtrl',
				function($scope,$location,$http,$sce,$timeout,avitUtil,chartUtil) 
				{
				    var figureType=6;//1表示统计点击次数；2点击用户数；3收视率；4市场占有率；5频道流入流出
				    $scope.selectAptitudes=[];
					$scope.selectArea = function(code)
					{
					     $scope.theArea=code;
					 };
                  
                	  $scope.showSpWatchCount=function(flag)
                	  {
                  		figureType=6;
                  		chartUtil.kibanaChartData($scope,$sce,flag,'dianboNotRealTimeSpWatchCount');
                	  };
                	  
                	  $scope.showSpUserCount=function(flag)
                	  {
                  		figureType=7;
                  		chartUtil.kibanaChartData($scope,$sce,flag,'dianboNotRealTimeSpUserCount');
                	  };
                    	
                    	  $scope.sendSearchRequest=function(flag,figure)
                    	  {
                    		  if(figure)
                    		  {
                    			  figureType=figure;
                    	      }
                    		   
                    		  if(figureType==6)
                  			{
                  				$scope.showSpWatchCount(flag);
                  			}
                  			else if(figureType==7)
                  			{
                  				$scope.showSpUserCount(flag);
                  			}
                    	  }
                    	  
/*                    	  var initpaickkk=function()
                    	  {
                    		  
                    		  var picker1= $('#datetimepicker1').datetimepicker({
                    		        format: 'yyyy-mm-dd hh:mm:ss',
                    		        locale: moment.locale('zh-cn'),
                    		        minDate: '2016-12-1'
                    		    });
                    		    
                    		   var picker2=$('#datetimepicker2').datetimepicker({
                    		        format: 'yyyy-mm-dd hh:mm:ss',
                    		        locale: moment.locale('zh-cn')
                    		    });
                    		    
                    		    $scope.$watch('timeStart', function(e){
                		            //$scope.minEndDate = newValue;
                    		    	  picker2.data('DateTimePicker').minDate(e);
                		        });
                    		    
                    		    $scope.$watch('timeEnd', function(e){
                		            //$scope.minEndDate = newValue;
                    		    	  picker1.data('DateTimePicker').minDate(e.date);
                		        });
                    		   
                    		    //动态设置最小值
                    		    picker1.on('dp.change', function (e) {
                    		        picker2.data('DateTimePicker').minDate(e.date);
                    		    });
                    		    //动态设置最大值
                    		    picker2.on('dp.change', function (e) {
                    		        picker1.data('DateTimePicker').maxDate(e.date);
                    		    });
                    	  }*/
                    	  
 	                     $scope.initData = function ()
 	                     { 
// 	                    	initpaickkk();
 	                    	chartUtil.initDianBoDatePicker($scope,$timeout);
 	                    	chartUtil.initAptitude($scope);
	                    	chartUtil.intArea($scope);  
	                    	$scope.sendSearchRequest(1,6);
 	                     };
 	                    $scope.initData();
                    	  
				});
