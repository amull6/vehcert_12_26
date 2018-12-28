<%@ page import="cn.com.wz.parent.CustomConfigUtil" %>
<!doctype html>
<html lang="<c:preference name='page_lang'></c:preference>" xml:lang="<c:preference name='page_lang'></c:preference>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="shortcut icon" href="${resource(dir: 'images', file: 'parent/favicon.ico')}">
<title>${message(code:"default.page.title.label",Default:"Home")}</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/asix_system4.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/main.css')}" type="text/css">
<script src="${resource(dir:"js",file:"parent/DivDialog.js") }" type="text/javascript" ></script>
<script src="${resource(dir:"js",file:"parent/home.js") }" type="text/javascript" ></script>
<r:require modules="om" />
<r:layoutResources />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: hidden;
}
.om-tabs .om-tabs-panels .om-panel-body{
	padding:0px;
}
</style>
</head>
<body>
	<div id="asix_box" style="margin:0px;padding:0px">
		<!-- 顶部区域 -->
		<div id="page_top" >
			<div id="asix_box_top" class="asix_box_top">
				<div class="asix_name">
					<font  style="margin-left:50px;font-size:20px;font-weight:bold;text-decoration : blink; ">
						    ${message(code:'default.page.title.label',default:'China WuZheng Employee Working PlatForm') }

                    </font>
				</div>
				<div class="asix_navigate">
					<div class="right_button">
						<span ><img  id="img_close"  title="${message(code:'default.exit.label',default:'Exit') }" style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/exit.png')}"  /></span>
						<span><img  id="img_help" title="${message(code:'default.help.label',default:'Help') }" style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/right_help.png')}" /></span>
                        <span><img  id="img_reload" title="${message(code:'default.refresh.label',default:'Refresh') }" style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/right_Refresh.png')}" /></span>
                        <c:isChinese>
                        	<span><img id="img_eng" style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/right_english.png')}" /></span>
                        </c:isChinese>
                        <c:isEnglish>
                        	<span><img id="img_chn" style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/right_chinese.png')}" /></span>
                        </c:isEnglish>
                        <g:if test="${appCode=="app_ewp"}">
                            <span><img id="img_house" title="${message(code:'default.house.label',default:'Main Page') }"  style="cursor:pointer;" src="${resource(dir: 'images', file: 'parent/house.png')}" /></span>
                        </g:if>

					</div>
					<div class="right_navigate">
						<ul>
							<li >
								<span id="user_info"><c:currentUser></c:currentUser> &nbsp;你好！ 今天是</span>
                                <g:if test="${appCode=='app_ewp'}">
                                    <span id="hou_insideNote" ></span>
                                </g:if>

							 </li> 
						</ul>
					</div>
				</div>
			</div>
		</div>
                
        <!-- 左侧区域 -->
		<div id="page_left">
			<div id="asix_box_left" class="asix_box_Content_left">                       
				<div class="asix_box_Content_lefttop_mid">
					<div id="asix_box_Content_Tree" class="asix_box_Content_Tree"></div>
				</div>
 				<div class="asix_box_Content_lefttop_btm"></div> 
			</div>
		</div>
		<!-- 中间区域 -->
		<div id="page_center" style="margin:0px;padding:0px;">
			<ul>
				<li><a href="#tab_welcome">${message(code:"default.welcome.label",Default:"Welcome")}</a></li>
			</ul>
            %{--<div id="tab_welcome" style=" background-image: url('${resource(dir:"images",file:"parent/mainBgColor.GIF")}'); background-repeat: repeat;"><img src="${resource(dir:"images",file:"parent/mainBackground.GIF")}"></div>--}%
	        <div id="tab_welcome"><iframe id="ifr_home"  frameBorder=0 marginheight=0 marginwidth=0  style="width:100%;" src="${createLink(controller:'home',action:'toHome')}" scrolling="auto"></iframe></div>
    </div>
	</div>
	<r:layoutResources />
		
	<script>
			
			var n=0;
			$(function(){
				$("#showMessageHou").live('click',function(){
					showMessageHou('${createLink(controller:'insideNote',action:'inBox')}?opFlag=index&sign=1&tabId=asix_box&unRead=0','${message(code:'home.message.label',default:'Message') }');
				});
				$('#img_house').click(function(){
					window.location.href='${createLink(controller:'home',action:'toHome')}';
				});
				$('#img_chn').click(function(){
						window.location.href='${createLink(controller:'login',action:'login')}?lang=zh_CN';
					});
				$('#img_eng').click(function(){
					window.location.href='${createLink(controller:'login',action:'login')}?lang=en_US';
				});
				$('#img_help').click(function(){
					var url='${createLink(controller:'help',action:'index')}';
					var title='${message(code:'home.userGuide.label',default:'User Guide') }';
					var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
					  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
					  'scrolling="yes" src="'+url+'"></iframe>';
					    //打开弹出框
				    showDialogForIndex('asix_box',1,content,title,900,620);
				});
				$('#img_reload').click(function(){
					window.location.href='${createLink(controller:'login',action:'login')}';
				});
				$('#img_close').click(function(){
                    var url="${createLink(controller:'login',action:'doLogout') }";
                    $.post(url,{},function(data,textStatus){
                        if(data=="success"){
                            window.location.href="${CustomConfigUtil.getCasInfo(request.getServletContext(), request).get('logoutUrl').toString()}";
                        }else{
                            alert("系统注销失败！");
                        }
                    }) ;
				});
			});
	 		//加载时执行的语句
	 		var noteIntId='';
		 	$(function() {
                <g:if test="${appCode=='app_ewp'}">
                     getInsideNotes();
                     noteIntId=setInterval("getInsideNotes();",60000);
                </g:if>

		 		getCurrentDate();
			 	//布局
		 		$('#asix_box').omBorderLayout({
						panels:[{
								id:'page_top',
								region:'north',
								header:false
							},{
								id:'page_left',
								region:'west',
								title:'${message(code:"default.menu.label",Default:"System Menu")}',
								width:212,
								collapsible:true,
								onCollapse:function(){
                                     tabs.width='100%'
                                 }
							},{
								id:'page_center',
								region:'center',
								header:false
							}],
						fit:true,
						spacing:0
			 		});
		 		//主面板
		 		//构造中间工作区的Tab(在ie8和chome浏览器中scrollable属性无效)
			    var tabs = $('#page_center').omTabs({
				    	width:'fit',
				    	height:'100%',
				    	tabWidth : 'auto',
		    			closable : false,
			    		border:true,
			    		tabMenu:true
		    		    
				    });
                 var contentHeight= tabs.height() - tabs.find(".om-tabs-headers").outerHeight()-10;
			    $('#ifr_home').css({height:contentHeight});
				    //构造菜单树
			    //buildTreeMenu();
                buildAccordionMenu();
                //document.all("ifm").style.height=document.body.scrollHeight; 
                //document.all("ifm").style.width=document.body.scrollWidth; 
               
			});
		 	function getInsideNotes(){
				var url='${createLink(controller:'insideNote',action:'getUnReadCount')}';
				$.post(url,{},function(data,textStatus,xhr){

	                //判断是否已经退出系统
	                if(data!=null&&data=="session is loginout"){
	                    exitSystem();
	                    stopInterval();
	                    return;
	                }

					var content='未读短信数读取失败！';
			   		if(textStatus){
						var content='<a id="showMessageHou" href="javascript:void(0);" style="margin-right:50px;width:100px;line-height:19px;">未读短信<span style="color:red;">('+data+')</span>条</a>';
			       	}
			   	 	$("#hou_insideNote").html(content);
			   		
			    },'text');
			}
		 	//构造手风琴形式的菜单
			function buildAccordionMenu(value,event) {
				//1:读取所有根节点，向asix_box_Content_Tree中插入多个层，设定命名规则rootMenu_id
				var menuPanel = $('#asix_box_Content_Tree');
				menuPanel.append('<ul></ul>');
				var ul = $('#asix_box_Content_Tree>ul');
				//在ie下如果不加上时间会导致每次的申请时从缓存中获取
				var rootUrl='${createLink(controller:'menu',action:'jsonList4Tree')}?date='+new Date();
				$.getJSON(rootUrl,function(data){
						if(data!=null){
							if(data.error!=null){
									return;
							}
							$.each(data, function(i, value){
								  if(value.pid==null){
									  	menuPanel.append("<div id='rootMenu_"+value.id+"'></div>");
									  	//创建子树
									  	var rootMenu = 'rootMenu_'+value.id;
									  	var treeDataUrl = "${createLink(controller:'menu',action:'jsonSubMenu')}";
									  	treeDataUrl += "?pid="+value.id;
									  	$('#'+rootMenu).omTree({
									  			dataSource:treeDataUrl,
									  			simpleDataModel:true,
									  			onSelect:function(node,event) {
										  			//只有在叶子节点上才打开新的tab
										  			if(node.children==null){
											  			//当节点的href为空的时候，给出提示
											  			if(node.href==null||node.href.length==0){
												  			$.omMessageBox.alert({
																   title:'${message(code:'default.tipDialog.title.label',default:'Tip Dialog') }',
														           content:'${message(code:'default.tree.node.href.empty.label',default:'This node no URL')}',
														           onClose:function(v){
															       }
															});
												  			return;
												  		}

											  			var tabId = 'tab_'+node.id;
											  			var tabs = $('#page_center');
										  				var index = tabs.omTabs('getAlter', tabId);
									  					var contentHeight = tabs.height() - tabs.find(".om-tabs-headers").outerHeight()-10;
														var url = node.href;//+'?lang='+$('#lang').val();
										  				if(index==null){
										  					//验证打开的标签数是否超过最大数（已打开的仍然可以切换）
												  			var tabsNum = $('#page_center').omTabs('getLength');
												  			if(tabsNum>20){
																alert('打开的标签数，超过最大数！');
																return;
													  		}
										                    $('#page_center').omTabs('add',{
											                  	tabId:'tab_'+node.id,
										                	  	title : node.text,
										                	  	content:"<iframe name='iframe_"+node.id+"'  frameBorder=0 marginheight=0 marginwidth=0 src='"+url+"' height='"+contentHeight+"' width='100%'></iframe>",
										                	  	closable : true,
										                	  	tabMenu:true
												  			});
										  				}else{
											  				$('#page_center').omTabs('activate',tabId);	
											  				var index = $('#page_center').omTabs('getAlter', tabId);
                                                            var tabHeight = tabs.height() - tabs.find(".om-tabs-headers").outerHeight()-10;
											  				//重新加载页面内容
											  				$('#page_center').omTabs('reload', index,'',"<iframe name='iframe_"+node.id+"'  frameBorder=0 marginheight=0 marginwidth=0 src='"+url+"' height='"+contentHeight+"' width='100%'></iframe>");
												  		}
											  		}else{
                                                          if(node.isExpand=='0'){
                                                              $('#'+rootMenu).omTree('expand',node);
                                                              node.isExpand=1;
                                                          }else{
                                                              $('#'+rootMenu).omTree('collapse',node);
                                                              node.isExpand=0;
                                                          }

                                                      }
									             }
										  	});
									  	ul.append("<li><a href='#rootMenu_"+value.id+"' >"+value.text+"</a></li>")
									  }
								});
							}
						menuPanel.omAccordion({
								switchEffect: true,
								collapsible:true,
								width:190,
                                height:'98%'
							});
					});
				
		}
		//设置系统欢迎信息
		function getCurrentDate(){
			var now=new Date();
			var year=now.getFullYear();
			var month=now.getMonth()+1;
			var day=now.getDate();
			var weekDay1=now.getDay();
			var weekDay='';
			switch(weekDay1){
				case 0:
					weekDay='星期天';
					break;
				case 1:
					weekDay='星期一';
					break;
				case 2:
					weekDay='星期二';
					break;
				case 3:
					weekDay='星期三';
					break;
				case 4:
					weekDay='星期四';
					break;
				case 5:
					weekDay='星期五';
					break;
				case 6:
					weekDay='星期六';
					break;
			}

			var date=year+"年"+month+"月"+day+"日  "+weekDay ;
			$("#user_info").html($("#user_info").html()+date);
		}
		//关闭定时器
        function stopInterval(){
            clearInterval(noteIntId);
        }
	 	</script>
</body>
<%--<script>--%>
<%--	window.onbeforeunload=onbeforeunload_handler;--%>
<%--		function  onbeforeunload_handler(){--%>
<%--			if(event.clientX>document.body.clientWidth-50&&event.clientY<0||event.altKey)--%>
<%--			{--%>
<%--				alert(event.clientX+'111');--%>
<%--				alert(document.body.offset().y+'111');--%>
<%--				alert(event.clientY+'111');--%>
<%--				alert(document.body.clientHeight+'111');--%>
<%--			if(alert('您即将退出员工工作平台！')){--%>
<%--				window.location.href='${createLink(controller:'login',action:'doLogout') }';--%>
<%--				}--%>
<%--			}--%>
<%--			 else if (event.clientY>document.body.clientHeight-40||event.altKey)--%>
<%--			 {--%>
<%--				 alert(event.clientX+'222');--%>
<%--					alert(document.body.clientWidth+'222');--%>
<%--					alert(event.clientY+'222');--%>
<%--					alert(document.body.clientHeight+'222');--%>
<%--			if(confirm('确定要退出员工工作平台吗？')){--%>
<%--				window.location.href='${createLink(controller:'login',action:'doLogout') }';--%>
<%--				}--%>
<%--			} --%>
<%--			else {--%>
<%--				alert(event.clientX+'333');--%>
<%--				alert(document.body.clientWidth+'333');--%>
<%--				alert(event.clientY+'333');--%>
<%--				alert(document.body.clientHeight+'333');--%>
<%--			alert('是刷新？');--%>
<%--			}--%>
<%--		}--%>
<%--</script>--%>
</html>