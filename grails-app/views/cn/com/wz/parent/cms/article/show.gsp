<%@ page import="cn.com.wz.parent.cms.Article" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	
		<div id="show-article" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list article">
			
				<g:if test="${articleInstance?.titleC}">
				<li class="fieldcontain">
					<span id="titleC-label" class="property-label"><g:message code="article.titleC.label" default="Title C" /></span>
					
						<span class="property-value" aria-labelledby="titleC-label" style="font-weight:800;font-size:18px"><g:fieldValue bean="${articleInstance}" field="titleC"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleInstance?.titleE}">
				<li class="fieldcontain">
					<span id="titleE-label" class="property-label"><g:message code="article.titleE.label" default="Title E" /></span>
					
						<span class="property-value" aria-labelledby="titleE-label"><g:fieldValue bean="${articleInstance}" field="titleE"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${articleInstance?.keyWords}">
				<li class="fieldcontain">
					<span id="keyWords-label" class="property-label"><g:message code="article.keyWords.label" default="Key Words" /></span>
					
						<span class="property-value" aria-labelledby="keyWords-label"><g:fieldValue bean="${articleInstance}" field="keyWords"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${articleInstance?.contentC}">
				<li class="fieldcontain">
					<span id="contentC-label" class="property-label"><g:message code="article.contentC.label" default="Content C" /></span>
					
						<span class="property-value" aria-labelledby="contentC-label">${articleInstance.contentC}</span>
					
				</li>
				</g:if>
			
				<g:if test="${articleInstance?.contentE}">
				<li class="fieldcontain">
					<span id="contentE-label" class="property-label"><g:message code="article.contentE.label" default="Content E" /></span>
					
						<span class="property-value" aria-labelledby="contentE-label">${articleInstance.contentE}</span>
					
				</li>
				</g:if>
				
				<g:if test="${articleInstance?.picName}">
				<li class="fieldcontain">
					<span id="picName-label" class="property-label"><g:message code="article.picName.label" default="PicName" /></span>
					
						<span class="property-value" aria-labelledby="picName-label"><g:fieldValue bean="${articleInstance}" field="picName"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${articleInstance?.picPath}">
				<li class="fieldcontain">
					<span id="picPath-label" class="property-label"><g:message code="article.picPath.label" default="picPath" /></span>
					
						<span class="property-value" aria-labelledby="picPath-label"><g:fieldValue bean="${articleInstance}" field="picPath"/></span>
					
				</li>
				</g:if>
	
				<g:if test="${articleInstance?.accessorys}">
				<li class="fieldcontain">
					<span id="accessorys-label" class="property-label"><g:message code="article.accessorys.label" default="Accessorys" /></span>
					
						<span class="property-value" aria-labelledby="accessorys-label">${articleInstance.accessorys}</span>
					
				</li>
				</g:if>

				<li class="fieldcontain">
					<span id="isTop-label" class="property-label"><g:message code="article.isTop.label" default="IsTop" /></span>
						<span class="property-value" aria-labelledby="isTop-label">
						<g:if test="${articleInstance?.isTop}">
								已置顶
							</g:if>
							<g:else>
								未置顶
							</g:else>
						</span>
					
				</li>
				
				<g:if test="${articleInstance?.createrId}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="article.createrId.label" default="Creater" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${articleInstance.createrId }'></c:getUserName>
						</span>
					</li>
				</g:if>
			
				<g:if test="${articleInstance?.createTime}">
				<li class="fieldcontain">
					<span id="createTime-label" class="property-label"><g:message code="article.createTime.label" default="Create Time" /></span>
						<span class="property-value" aria-labelledby="createTime-label"><g:formatDate date="${articleInstance?.createTime}" /></span>
				</li>
				</g:if>
			
				<g:if test="${articleInstance?.lastUpdaterId}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="article.lastUpdaterId.label" default="Last Updater" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${articleInstance.lastUpdaterId }'></c:getUserName>
						</span>
				</li>
				</g:if>
			
				<g:if test="${articleInstance?.lastUpdateTime}">
				<li class="fieldcontain">
					<span id="lastUpdateTime-label" class="property-label"><g:message code="article.lastUpdateTime.label" default="Last Update Time" /></span>
						<span class="property-value" aria-labelledby="lastUpdateTime-label"><g:formatDate date="${articleInstance?.lastUpdateTime}" /></span>
				</li>
				</g:if>
			
				<g:if test="${articleInstance?.articleCategory}">
				<li class="fieldcontain">
					<span id="articleCategory-label" class="property-label"><g:message code="article.articleCategory.label" default="Article Category" /></span>
					
						<span class="property-value" aria-labelledby="articleCategory-label"><g:link controller="articleCategory" action="show" id="${articleInstance?.articleCategory?.id}">${articleInstance?.articleCategory?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${articleInstance?.id}" />
					<g:link class="edit" action="edit" id="${articleInstance?.id}"  params="[currentCategoryId:articleInstance?.articleCategory?.id,selectOwn:selectOwn]"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="article" class="cancel" params="[currentCategoryId:articleInstance?.articleCategory?.id,selectOwn:selectOwn]">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
