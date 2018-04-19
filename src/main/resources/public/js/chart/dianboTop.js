var app = angular.module('dianboTop',['ui.bootstrap','avit.services','chartUtil.services']);
app.config(['$locationProvider', function($locationProvider) {  
    $locationProvider.html5Mode({
     enabled: true,
     requireBase: false
   });
   }]);

app.controller(
				'dianboTopCtrl',
				function($scope,$location,$http,$sce,$timeout,$window,avitUtil,chartUtil) 
			{
					  $scope.selectAptitudes=[];
					  $scope.showChartType="1";//1点播人数份额2点播次数份额3点播时长二分4影片点播排行5影片播放完整度排行
					  var finished=0; //未结算执行加载更多操作 
					  var sover=0; //0表示未到底，1表示到底了
					  var vid=0; //加载的数据计数，当小于数组集合的长度时，继续加载。 
					
					  //如果屏幕未到整屏自动加载下一页补满  
					  var setFirstPage=function (data)
					  {
					  setInterval(function (){  
					      if(sover==1)
					    	  {
					    	  vid=0;clearInterval(setdefult); 
					    	  }
					      else if($(".dataList").height()<$(window).height()) 
					    	  {
					          loadmore(data);  
					    	  }
					      else
					    	  {
					          clearInterval(setdefult);
					    	  }
					  },500);  
					  }
					  
					//加载完  
					  var loadover =function (){  
					      if(sover==1)  
					      {     
					          var overtext="Duang～到底了";  
					          $(".loadmore").remove();  
					          if($(".loadover").length>0)  
					          {  
					              $(".loadover span").eq(0).html(overtext);  
					          }  
					          else  
					          {  
					              var txt='<div class="loadover"><span>'+overtext+'</span></div>'  
					              $("body").append(txt);  
					          }  
					      }  
					  }  
					  
					  
					  
					//加载更多  
					 
					  var loadmore= function (data){  
					      if(finished==0 && sover==0)  
					      {  
					          var scrollTop = $(window).scrollTop();  
					          var scrollHeight = $(document).height();  
					          var windowHeight = $(window).height();  
					            
					          if($(".loadmore").length==0)  
					          {  
					        	  var txt='<div class="loadmore"><span class="loading"></span>加载中..</div>'  
					              $(".dataList").append(txt);  
					          }  
					            
					          if (scrollTop + windowHeight -scrollHeight<=50 ) {  
					              //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作  
					        	  
					             /* var txt='<div class="rankitem" ><div class="atten1"> 暂无数据</div><div class="atten2"></div><div class="atten3"></div></div>';  
					              $("ranklist").append(txt); */ 
					          
					                
					              //防止未加载完再次执行  
					              finished=1;  
					                
					              var result = "";  
					              
					              for(var i = 0; i < 6; i++){  
					            	  vid++;  
					                  if(vid<data.length)
					                  {
					                  result+='<div class="rankitem" >'+
					                      '<div class="atten1">'+
					                      vid +
				                        '</div> <div class="atten2">'+
				                        data[i].assetName +
				                    '</div> <div class="atten3"><span>'+
				                    '<i style="width:'+data[i].count/$scope.maxData*100;+'%"></i></span>'+
				                        data[i].count+
				                        '</div></div> ' 
					                  }else break;
					                   
				                      
					              }  
					              setTimeout(function(){  
					                  //$(".loadmore").remove();  
					                  $('.dataList').append(result);  
					                  finished=0;  
					                  //最后一页  
					                  if(vid==data.length)  
					                  {  
					                      sover=1;  
					                      loadover();  
					                  }  
					              },500);  
					          }  
					      }  
					  }  
					  
					//页面滚动执行事件  
					  $(window).scroll(function (){  
					      loadmore();  
					  });
					  
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
                		  
                		 var url="/itdap/getCategoryUser";
                		 if($scope.showChartType=="2")
                		 {
                			 url="/itdap/getCategoryCount";
                		 }else if($scope.showChartType=="3")
                		 {
                			 url="/itdap/getCategoryDuration";
                		 }else if($scope.showChartType=="4")
                		 {
                			 url="/itdap/getAssetReport";
                		 }else if($scope.showChartType=="5")
                		 {
                			 url="/itdap/getAssetIntegrityReport";
                		 }
                		  
                		  //根据参数请求数据
       	               $.ajax(
       						   {
       							    type: 'GET',
       							    url: url ,
       							    data: {
       							    	spCode:$scope.theAptitude,
       							    	bt:avitUtil.formatTime($scope.newWordQueryObj.startTime),
       							    	et:avitUtil.formatTime($scope.newWordQueryObj.endTime),
       							    	areaCode:$scope.theArea,
       							    	size:$scope.pagess
       							    } ,
       							    dataType: "json",
       							    async:false,
       							    success: function (data)
       							    {
       							    	$scope.rankDatas = data;
       							    	if(data.length>0)
       							    	{
       							    	$scope.userTotal=data[0];
       							    	if($scope.showChartType=="1")
       							    	{
       							        $scope.maxData=data[1].useUser;
       							    	}else if($scope.showChartType=="2")
       							    	{
       							    		$scope.maxData=data[1].useCount;
       							    	}else if($scope.showChartType=="3")
       							    	{
       							    		$scope.maxData=data[1].useDuration;
       							    	}else if($scope.showChartType=="4")
       							    	{
       							    		$scope.maxData=data[0].count;
       							    	}else if($scope.showChartType=="5")
       							    	{
       							    		$scope.maxData=data[0].integrity;
       							    	}
       							    	
       							    	}else
       							    	{
       							    		$scope.rankDatas=[{"useUser":"暂无数据"}];
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
	                    	            {typeName : "栏目点播人数份额排行", type : "1"},
	 									{typeName : "栏目点播次数份额排行", type : "2"},
	 									{typeName : "栏目点播时长份额排行", type : "3"},
	 									{typeName : "影片点播排行", type : "4"},
	 									{typeName : "影片播放完整度排行", type : "5"}
	 								];
	                    	$scope.thechartType=$scope.chartTypes[0].type; 
                  	  }
                	  
                     $scope.initData = function ()
                     { 
                    	chartUtil.initDianBoDatePicker($scope,$timeout);
                    	chartUtil.initAptitude($scope);
                    	$scope.theAptitude=$scope.selectAptitudes[0].code;
                    	chartUtil.intArea($scope);  
                    	initPageSize();
                    	initChartType();
                     };
                     
// 	                      
                    $scope.initData();
                     getTopChart();
                	  
			});
