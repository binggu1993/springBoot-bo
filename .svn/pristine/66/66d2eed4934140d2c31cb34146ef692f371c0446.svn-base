var menuTools = {}; (function() {
	menuTools.loadRootMenu = function() {
		var menuObj = $("#main-menu");
		menuObj.html("").append(menuTools.buildMenuHtml());
		menuObj.find('li').not('.active').has('ul').children('ul').addClass('collapse');

		menuObj.find('li').has('ul').children('a').on('click', function () {
               $(this).parent('li').toggleClass('active').children('ul').collapse('toggle');
               $(this).parent('li').siblings().removeClass('active').children('ul.in').collapse('hide');
           });
		
		
		menuTools.showContent("page/home/main.html");
		
		menuTools.menuClickEvent();
		
	};
	
	menuTools.menuClickEvent = function() {
		var menuObj = $("#main-menu");
		menuObj.find("a[name='goPage']").parent().on('click', function () {
			if("#"!=$(this).attr("url"))
		    { 
				menuTools.showContent($(this).attr("url"),$(this).attr("name"),$(this).attr("pname")); 
			}
        });
	};
	
	menuTools.isSourcePc = function() 
    { 
       var userAgentInfo = navigator.userAgent;
       var Agents = ["Android", "iPhone","SymbianOS", "Windows Phone","iPad", "iPod"];
       var flag = true;
       for (var v = 0; v < Agents.length; v++) 
       {
           if (userAgentInfo.indexOf(Agents[v]) > 0) 
           {
               flag = false;
               break;
           }
       }
       return flag;
   };
  
	menuTools.buildMenuHtml = function() {
		var data = menuTools.getMenus();
		var rootData = menuTools.getMenusByPid(data, -1);
		var menuHtml = "";
		$(rootData).each(function() {
			menuHtml += '<li ><a href="' + this.url + '"> ' + '<i style="font-size:4px;" class="' + this.icon + '"></i> ' +  this.name ;
			menuHtml += menuTools.getHtmlByType( this,data,'nav-second-level');
		});
		return menuHtml
	};
	
	 
	menuTools.getChildrenHtml = function(data, pid, pname,level) {
		var childrenData = menuTools.getMenusByPid(data, pid);
		var childHtml = '<ul class="nav '+level+'">';
		$(childrenData).each(function() {
			var url = "MANY" == this.type?"#":this.url;
			childHtml += "<li  url='"+url+"' name='"+this.name+"' pname='"+pname+"'><a name='goPage' href=#>" + '<i class="' + this.icon + '" style="font-size:4px;"></i> ' + this.name ;
			childHtml += menuTools.getHtmlByType( this,data,'nav-third-level');
		});
		childHtml += "</ul>";
		return childHtml
	};
	
	
	menuTools.getHtmlByType =  function( obj,data,level) {
		var menuHtml ='';
		if ("MANY" == obj.type) 
		{
			menuHtml += '<span class="fa arrow"></span></a>';
			menuHtml += menuTools.getChildrenHtml(data, obj.id, obj.name,level);
		} 
		else
		{
			menuHtml += '</a>';
		}
		menuHtml += '</li>';
		return menuHtml;
	};
	
	menuTools.getMenusByPid = function(data, pid) {
		var retArr = [];
		$(data).each(function() {
			if (pid == this.pid) {
				retArr.push(this)
			}
		});
		return retArr
	};
	
	menuTools.showContent = function(url, name, pname) {
		$("#mainIframe").attr("src", url);
		$("#bcParent").html(pname);
		$("#bcCurrent").html(name);
		
		if(!menuTools.isSourcePc())
		{
			$("#childMenu").hide();
		}
		
		/*  var ifr = document.getElementById('mainIframe');
		  ifr.onload = function() {
		      var doc = ifr.contentDocument || ifr.document;
		      var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
		      var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
		      var height  = Math.max(cHeight, sHeight);
		      ifr.style.height = height + 'px';
		  }*/
	};
	
	menuTools.getMenus = function() {
		var retData = [];
		$.ajax({
			type: "GET",
			url: "/itdap/menus",
			data: {},
			dataType: "json",
			async: false,
			success: function(data) {
				retData = data
			},
			error: function(err) {
				alert("get menu data error.")
			}
		});
		return retData
	}
	
	
	menuTools.sideNavEvent = function() {
	$("#sideNav").click(function()
            {
        		var menuObj = $("#main-menu");
			  if($(this).hasClass('closed'))
			 {
				$('.navbar-side').animate({left: '0px'});
				$(this).removeClass('closed');
				$('#page-wrapper').animate({'margin-left' : '220px'});
				menuObj.children().each(function ()
				{
					 var iObjClass = $(this).children("a").children("i").attr("class");
					 var spanObj = $(this).children("a").children("span") ;
					 if(spanObj.hasClass(iObjClass))
					 {
						 spanObj.removeClass(iObjClass).addClass('fa arrow').css("margin","").css("float","").css("font-size","12px");
					 }
					 $(this).unbind("mouseenter mouseleave");
				}); 
				menuObj.find('li').has('ul').children('a').on('click', function () {
		               $(this).parent('li').toggleClass('active').children('ul').collapse('toggle');
		               $(this).parent('li').siblings().removeClass('active').children('ul.in').collapse('hide');
		           });
			}
			else{
			    $(this).addClass('closed');
				$('.navbar-side').animate({left: '-170px'});
				$('#page-wrapper').animate({'margin-left' : '50px'}); 
				menuObj.find('li').children('ul').removeClass('in');
				menuObj.find('li').has('ul').children('a').unbind('click');
				menuObj.children().each(function ()
				{
					var iObjClass = $(this).children("a").children("i").attr("class");
					 var spanObj = $(this).children("a").children("span") ;
					 if(spanObj.hasClass('fa arrow'))
					 {
						 var name = spanObj.parent().text();
						 spanObj.removeClass('fa arrow').addClass(iObjClass).css("margin","8px 5px 0px 0px").css("float","right").css("font-size","18px").attr("title",name);
						 spanObj.parent().parent().hover(
						function()
						{
							var top = spanObj.offset().top;
							var scrollTop = $(window).scrollTop();
							var liHtml = "";
							$(this).find("ul").children().each(
									function()
									{
										liHtml += "<li  name="+$(this).attr("name")+" pname="+$(this).attr("pname")+" url="+$(this).attr("url")+" style='padding:6px 0px 6px 10px;font-size:13px;cursor:pointer;'>"+$(this).attr("name")+"</li>";
									}
							);
							$("#childMenu").find("ul").html(liHtml);
							$("#childMenu").show("100").css("top",(top-scrollTop-70)+"px");
							$("#childMenu").hover(
									function()
									{
										$(this).show();
									},
									function()
									{
										$(this).hide();
									}
									);
							        $("#childMenu").find("li").hover(
									function()
									{
										$(this).css("background","#575757").css("color","#F36A5A");
									},
									function()
									{
										$(this).css("background","").css("color","");
									}
									);
							        $("#childMenu").find("li").click(
											function()
											{
												menuTools.showContent($(this).attr("url"),$(this).attr("name"),$(this).attr("pname")); 
											}
											);
						},
						function ()
						{
							$("#childMenu").hide();
						}
						 );
					 }
				}); 
			}
		});
	
	};
	
	
	
})();