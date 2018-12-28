
<%@ page import="cn.com.wz.parent.system.log.Log" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'onlineUser.label', default: 'Online User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;">
	    		<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style='text-align:right;width:100px;'><span>用户姓名：</span></td>
					    <td width="100"><span><g:textField id="realName" name="realName" maxlength="200" /></span></td>
					    <td width="60"></td>
					    <td style='text-align:right;width:80px;'><span><g:message code="user.organization.label" default="organization" />：</span></td>
					    <td width="100"><span><g:textField id="organNames" name="organNames" maxlength="200" /></span></td>
					    <td width="60"></td>
					    <td>
   	                       <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    	   <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				        </td>
					  </tr>
					</table>
					
				</form> 
				<div style="margin-right:8px;margin-top:8px;">
					<div id="log_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
	 	$(function() {
			   $('#page').css({height:$(document).height()-16});
			 //绑定查询按钮事件
			   $("#realName").keydown(function(e){
	     					 if(e.keyCode==13){
	     			              $('#btn_query').click();
	     						  return false;
	     					}
	     			   });
			   $("#organNames").keydown(function(e){
					 if(e.keyCode==13){
			              $('#btn_query').click();
						  return false;
					}
			   });
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
		   	                    var url = "${createLink(controller:'logUser',action:'jsonList')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#log_grid').omGrid('setData',url);
		   	                  }
                   });
               $('#btn_clear').omButton({
					width:80,
					onClick:function(){
						$("#realName").val('');
						$("#organNames").val('');
					}
               });
			});
	    // 加载时执行的语句
		$(function() {
	          //布局
			$('#page').omBorderLayout({
				panels:[{
           	        id:"center-panel",
           	     	header:false,
           	        region:"center"
           	    },{
           	        id:"west-panel",
           	        title:"<g:message code="default.list.label" args="[entityName]" />",
           	        region:"west",
           	        width:200
           	    }]
            });
	          buildRightGrid();
           });
		function show(index){

        	var data = $("#log_grid").omGrid("getData").rows[index];

        	var url = '${createLink(controller:'insideNote',action:'getUserByUserDetail')}';
 		   if(data.length==0){
					alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
     	   }else if(data.length>1){
         	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
     	   }else{
         	   window.location.href = url+'?receiverId='+data.id+'&nodeType=sysonline';
            }
        }

		function outlogin(index){

        	var data = $("#log_grid").omGrid("getData").rows[index];

        	var url = '${createLink(controller:'logUser',action:'sessionout')}';
 		   if(data.length==0){
					alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
     	   }else if(data.length>1){
         	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
     	   }else if(confirm('确定要将用户【'+data.tempName+'】强制下线吗？')){
         	   window.location.href = url+'?userName='+data.userName;
            }
        }
		function buildRightGrid(){
			$('#log_grid').omGrid({
				dataSource:'${createLink(controller:'logUser',action:'jsonList')}',
				title:"&nbsp",
				singleSelect:false,
				limit : 0, 
				height : 460,
				colModel:[{header : "用户姓名", name : 'tempName', width : 70, align : 'center', renderer:
		                 	 function(colValue, rowData, rowIndex){
		             		 var data = rowData;
										return  '<span><a id="btn_view" class="btn_view"  href="javascript:void(0);" onclick="show('+rowIndex+')">'+ data.tempName+'</a></span>';
								  }
						  },
				          {header : "用户登录名", name : 'userName', width : 140, align : 'center'},
<%--				          {header : "用户类型", name : 'userTypeName', width : 70, align : 'center' ,sort:'clientSide'},--%>
						  {header : "${message(code: 'user.organization.label', default: 'Organization')}", name : 'organNames', width : 300, align : 'left' ,sort:'clientSide'},
						  {header : "${message(code: 'post.label', default: 'Post')}", name : 'postNames', width : 140, align : 'left' ,sort:'clientSide'},
						  {header : "${message(code: 'onlineUser.startTime.label', default: 'Login Time')}", name : 'startTime', width : 150, align : 'center' ,sort:'clientSide'},
						  {header : "${message(code: 'onlineUser.online.lable', default: 'Login Time')}", name : 'onlineTime', width : 150, align : 'center' ,sort:'clientSide'},
						  {header : "${message(code: 'onlineUser.operat.label', default: 'operat')}", name : 'operat', width: 150, align:'center', renderer:
                          	 function(colValue, rowData, rowIndex){
                      		 var data = rowData;
                          	 return '<button id="btn_view" class="btn_view" onClick="show('+rowIndex+')">发送消息</button><button style="color:purple" onClick="outlogin('+rowIndex+')">强制下线</button>';
                           	 }}
                          ]
			});
		}
		function showTopTip(content){
	        $.omMessageTip.show({
	            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
	            content : content,
	            timeout : 3000
	        });
	    }
		 </script>
	</body>
</html>
