<%@ page import="cn.com.wz.parent.cms.Article" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
	</head>
	<body>
	
		<div id="show-article" class="content scaffold-show" role="main">
<%--			<h1><g:message code="default.show.label" args="[entityName]" /></h1>--%>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div>
				<table class='tab_tbody_list' style="margin-top:20px;">
					  <tr>
					    <td  style="text-align:right;width:150px;">
					      <span id="titleC-label" class="property-label"><g:message code="article.titleC.label" default="Title C" />：</span>
						</td>
						<td>
							 <span class="property-value" aria-labelledby="titleC-label"><g:fieldValue bean="${articleInstance}" field="titleC"/></span>
						</td>
						
					    <td style="text-align:right;width:120px;">
					      <span><g:message code="article.keyWords.label" default="Key Words" />：</span>
					    </td>
					    <td style="text-align:left;">
					    	 <span class="property-value" aria-labelledby="keyWords-label"><g:fieldValue bean="${articleInstance}" field="keyWords"/></span>
					    </td>
				     </tr>
					 <tr>
						<td style="text-align:right;width:150px;">
				            <span id="articleCategory-label" class="property-label"><g:message code="article.articleCategory.label" default="Article Category" />：</span>
					    </td>
					    <td>
					    	<span class="property-value" aria-labelledby="articleCategory-label">${articleInstance?.articleCategory?.categoryNameC}</span>
					    </td>
					    <td style="text-align:right;width:120px;">
					       <span id="createTime-label" class="property-label"><g:message code="article.createdTime.label" default="createdTime" />：</span>
					    </td>
					    <td>
					    	 <span class="property-value" aria-labelledby="createTime-label"><g:formatDate date="${articleInstance?.createTime}" /></span>
					    </td>
					 </tr>
					 <tr>
					  	<td style="text-align:right;width:150px;">
					       <span id="lastName-label" class="property-label"><g:message code="article.createdby.label" default="createdby" />：</span>
						</td>
						<td>
							 <span class="property-value" aria-labelledby="dicValueNameE-label">
								  <c:getUserName userId='${articleInstance.createrId }'></c:getUserName>
							 </span>
						</td>

					    <td style="text-align:right;width:120px;">
					        <span id="articleCategory-label" class="property-label"><g:message code="article.organNameC.label" default="organNameC" />：</span>
					    </td>
					    <td>
					    	 <span class="property-value" aria-labelledby="articleCategory-label">${organNameC}</span>
					    </td>
					 </tr>
				</table>
			</div>
	<hr/>
			<div>
				<table>
					<tr>
					    <td style='padding-left:100px;padding-right:100px;'>
							${articleInstance.contentC}
		                </td>
					 </tr>
					  <g:if test="${articleInstance.accessorys }">
					  	<tr>
					  		<td style="padding-top:50px;">
					  			  <hr style="color:#000;"/>
					  		</td>
					  	</tr>
					 	<tr>
						    <td style='padding-left:100px;'>
						   
						    		<span>附件下载：</span>
						    		<span>
							    		<ul>
							    			<g:each in='${articleInstance.accessorys }' >
							    			<li><a href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${it?.fileName}&filePath=${it?.filePath}">${it?.fileName}</a></li>
								        	</g:each>
							    		</ul>
						    		</span>
						    </td>
						</tr>
					 </g:if>
				</table>
			</div>
		</div>
	</body>
</html>
