app.controller('modalCtrl', function($scope, $modalInstance, data) {
    $scope.data= data;

    //在这里处理要进行的操作   
     alert(data[0]+data[1]);
	 $scope.uploadRequest = function () 
	  {
	  var formData = new FormData();  
	     formData.append('filename', $('#filename')[0].files[0]);  
		   $.ajax(
		   {
			   url:data[0],
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
			    	avitUtil.goToUrlWithHash(data[1]);
			    },
			    error: function (err)
			    {
			    	alert("上传失败！");
			    },
			});
	  };
	//在这里处理要进行的操作
      $scope.ok = function() {
          $modalInstance.close();
      };
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
});