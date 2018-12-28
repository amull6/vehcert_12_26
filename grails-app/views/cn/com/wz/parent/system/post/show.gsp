
<%@ page import="cn.com.wz.parent.system.post.Post" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'post.label', default: 'Post')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	
		<div id="show-post" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list post">
			
				<g:if test="${postInstance?.postNameC}">
				<li class="fieldcontain">
					<span id="postNameC-label" class="property-label"><g:message code="post.postNameC.label" default="Post Name C" /></span>
					
						<span class="property-value" aria-labelledby="postNameC-label"><g:fieldValue bean="${postInstance}" field="postNameC"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${postInstance?.postType}">
				<li class="fieldcontain">
					<span id="postType-label" class="property-label"><g:message code="post.postType.label" default="Post Type" /></span>
					
						<span class="property-value" aria-labelledby="postType-label"><g:link controller="dictionaryValue" action="show" id="${postInstance?.postType?.id}">${postInstance?.postType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				
				<g:if test="${postInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="post.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${postInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${postInstance?.showOrder}">
				<li class="fieldcontain">
					<span id="showOrder-label" class="property-label"><g:message code="post.showOrder.label" default="Show Order" /></span>
					
						<span class="property-value" aria-labelledby="showOrder-label"><g:fieldValue bean="${postInstance}" field="showOrder"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${postInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="post.remark.label" default="Remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${postInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${postInstance?.createrld}">
				<li class="fieldcontain">
					<span id="createrld-label" class="property-label"><g:message code="post.createrld.label" default="Createrld" /></span>
					
						<span class="property-value" aria-labelledby="createrld-label"><g:fieldValue bean="${postInstance}" field="createrld"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${postInstance?.createTime}">
				<li class="fieldcontain">
					<span id="createTime-label" class="property-label"><g:message code="post.createTime.label" default="Create Time" /></span>
					
						<span class="property-value" aria-labelledby="createTime-label"><g:formatDate date="${postInstance?.createTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${postInstance?.lastUpdaterld}">
				<li class="fieldcontain">
					<span id="lastUpdaterld-label" class="property-label"><g:message code="post.lastUpdaterld.label" default="Last Updaterld" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdaterld-label"><g:fieldValue bean="${postInstance}" field="lastUpdaterld"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${postInstance?.lastUpdateTime}">
				<li class="fieldcontain">
					<span id="lastUpdateTime-label" class="property-label"><g:message code="post.lastUpdateTime.label" default="Last Update Time" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdateTime-label"><g:formatDate date="${postInstance?.lastUpdateTime}" /></span>
					
				</li>
				</g:if>
			
				
			
			
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${postInstance?.id}" />
					<g:link class="edit" action="edit" id="${postInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="post" class="cancel" >${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
