<!doctype html>
<html>
	<head>
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div>
			<form id="form_query" style="height:50px;">
				<input type="hidden" name='categoryCode' value="${categoryCode }">
				<input type="hidden" name='max' value="${params.max }">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80"><span><g:message code="article.titleC.label" default="TitleC" />：</span></td>
					    <td width="100"><span><g:textField id="titleC" name="titleC" maxlength="200" value="${params.titleC}"/></span></td>
					    <td align="left" valign="middle">
					    	<input id="btn_query" type="submit" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
				</form>
		</div>
		<div id="page1" style="width:100%;">
		
			<table class='tab_tbody_list'>
				<thead>
					<tr>	
						<td style="width:30px;">
							序号
						</td>
						<td>	
							通知名称
						</td>
						<td style="width:70px;">
							发布人
						</td>
						<td style="width:140px;">
							发布时间
						</td>
					</tr>
				</thead>
				<g:each in="${lst }" var='a' status='count'>
					<tr> 
						<td style="text-align:center;">
							${params.offset+count+1 }
						</td>
						<td>
							<a href="javascript:void(0);" onclick="showDetail('${a.id}','notes')">${a.titleC }</a>
						</td>
						<td>
							<c:getUserName userId="${a.createrId }"></c:getUserName>
						</td>
						<td>
							${a.createTime?.toString()?.substring(0,19) }
						</td>
					</tr>
					
				</g:each>
				
				<g:if test="${lst.totalCount>params.max }">
					<tfoot>
						<tr>
							<td colspan="4" style="text-align:center;" class='pager'>
								<g:paginate next="下一页" prev="上一页" max='10'
					             maxsteps="0" controller="article" params='${params}'
					            action="getNewTimeArticles" total="${lst.totalCount}" /> 
							</td>
						</tr>
					</tfoot>
				</g:if>
				
			</table>
    	</div>
	  	
	 	<script>
			// 事件绑定
			$(function() {
				$('#btn_clear').omButton({
   					width:80,
   					onClick:function(){
   						$("#titleC").val('');
   						$("#startTime").val('');
   						$("#endTime").val('');
   					}
         	  });     
				$('#btn_query').omButton({
   					width:80
         	  });    
			});
			// 加载时执行的语句
			$(function() {
				
	        });
			//用户自定义方法
			function showDetail(id,categoryCode){
			    var title="通知公告";
			    var detailUrl='${createLink(controller:'article',action:'frontShow')}'
				var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+detailUrl+"?id="+id+"&categoryCode="+categoryCode+'"></iframe>';
				    //打开弹出框
			    parent.showDialog(1,content,title,900,0);
		   }
		 </script>
	</body>
</html>
