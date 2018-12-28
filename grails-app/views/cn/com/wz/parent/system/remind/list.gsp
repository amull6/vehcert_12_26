<%@ page import="cn.com.wz.parent.system.remind.Remind" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'remind.label', default: 'remind')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.receiverName.label" default="receiverName" />：</span></td>
					    <td width="100"><span><g:textField id="receiverName" name="receiverName" maxlength="200" value="${insideNoteInstance?.receiverName}"/></span></td>
					    <td width="60"></td>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.send.label" default="sendTime" />：</span></td>
					    <td width="100"><span><c:dataPicker id="firstTime" dateFormat='yy-mm-dd' name="firstTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="15" >到</td>
					    <td width="100"><span><c:dataPicker id="lastTime" dateFormat='yy-mm-dd'  name="lastTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="100"></td>
					    <td align="center" valign="middle">
   	                       <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    	   <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				        </td>
					  </tr>
					</table>
					
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="remind_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
	 	$(function() {
			   $('#page').css({height:$(document).height()-16});
			 //绑定查询按钮事件
               $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'Remind',action:'create')}';
                	   window.location.href = url;
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
	          //构建左侧树
	          //buildLeftTree();
	          buildRightGrid();
           });
		function buildRightGrid(value,event){
			$('#remind_grid').omGrid({
				dataSource:'${createLink(controller:'Remind',action:'jsonList')}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, 
				height : 430,
				colModel:[{header : "${message(code: 'remind.createTime.label', default: 'receiverName')}", name : 'createTime', width : 150, align : 'left'},
                          {header : "${message(code: 'remind.content.label', default: 'content')}", name : 'content', width :1020, align : 'left',
                    		  renderer:function(value,rowData,rowIndex){
										return '<span title="'+value+'">'+value+'</span>';
							  	}
                          }
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

		//消息提醒的查看协同详情方法。
		function showAffair(id){
				 var title='事务协同详情';
				 var categoryCode='affair';
				 var detailUrl='${createLink(controller:'affair',action:'frontShow2')}';
				 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-13px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+detailUrl+"?signCreate=myAffair&id="+id+"&categoryCode="+categoryCode+'"></iframe>';
				 
				showDialogForIndex('div_wrap',1,content,title,900,0);
		}
		 </script>
	</body>
</html>
