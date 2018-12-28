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
					    <td width="100"><span><g:textField id="titleC" name="titleC" maxlength="200" /></span></td>
					    <td width="80"><span><g:message code="article.articleCategory.label" default="Article Category" />：</span></td>
					    <td width="100">
					    	<span>
					    	<g:select id="categoryId" name="categoryId" from="${cn.com.wz.parent.cms.ArticleCategory.createCriteria().list({categoryType{eq('code',"${categoryCode}")}})}" optionKey="id"  noSelection="['':'-全部-']" class="many-to-one"/>
					    	</span>
					    </td>
					    <td align="left" valign="middle">
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
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
							标题
						</td>
						<td style="width:120px;">
							所属栏目
						</td>
						<td style="width:90px;">
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
							<a href="javascript:void(0);" onclick="showDetail('${a.id}','${categoryCode}')">
								<g:if test="${a.titleC.size()>33 }">
									${a.titleC.toString().substring(0, 33) }...
								</g:if>
								<g:else>
								${a.titleC }
								</g:else>
							</a>
						</td>
						<td style="text-align:center;">
							${a.articleCategory?.categoryNameC}
						</td>
						<td style="text-align:center;">
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
					            action="toMoreArticles" total="${lst.totalCount}" /> 
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
   						$("#categoryNameC").val('');
   					}
         	  });     
				$('#btn_query').omButton({
   					width:80,
   					onClick:function(){
   	                    var url = "${createLink(controller:'article',action:'toMoreArticles')}";
   	                 	url+='?'+$('#form_query').serialize();
   	                    window.location.href = url;
   	                  }
         	  });  
				 $("#titleC").keydown(function(e){
 					 if(e.keyCode==13){
 			              $('#categoryId').focus();
 						  return false;
 					}
 			   }); 
				 $("#categoryId").keydown(function(e){
 					 if(e.keyCode==13){
 			              $('#btn_query').click();
 						  return false;
 					}
 			   }); 
			});
			// 加载时执行的语句
			$(function() {
				
	        });
			//用户自定义方法
			function showDetail(id,categoryCode){
			    var title='';
			    if(categoryCode=="notes"){
					title="通知公告";
				}else if(categoryCode=="news"){
					title='公司新闻';
				}else{
					title=='公司新闻';
				}
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
