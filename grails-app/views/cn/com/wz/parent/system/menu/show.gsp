
<%@ page import="cn.com.wz.parent.system.Menu" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-menu" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-menu" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list menu">
			
				<g:if test="${menuInstance?.textC}">
				<li class="fieldcontain">
					<span id="textC-label" class="property-label"><g:message code="menu.textC.label" default="Chinese Name" /></span>
					
						<span class="property-value" aria-labelledby="textC-label"><g:fieldValue bean="${menuInstance}" field="textC"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.textE}">
				<li class="fieldcontain">
					<span id="textE-label" class="property-label"><g:message code="menu.textE.label" default="English Name" /></span>
					
						<span class="property-value" aria-labelledby="textE-label"><g:fieldValue bean="${menuInstance}" field="textE"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.enabled}">
				<li class="fieldcontain">
					<span id="enabled-label" class="property-label"><g:message code="menu.enabled.label" default="Enabled" /></span>
					
						<span class="property-value" aria-labelledby="enabled-label"><g:formatBoolean boolean="${menuInstance?.enabled}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.controllerName}">
				<li class="fieldcontain">
					<span id="controller-label" class="property-label"><g:message code="menu.controller.label" default="Controller" /></span>
					
						<span class="property-value" aria-labelledby="controller-label"><g:fieldValue bean="${menuInstance}" field="controllerName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.actionName}">
				<li class="fieldcontain">
					<span id="action-label" class="property-label"><g:message code="menu.action.label" default="Action" /></span>
					
						<span class="property-value" aria-labelledby="action-label"><g:fieldValue bean="${menuInstance}" field="actionName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.classes}">
				<li class="fieldcontain">
					<span id="classes-label" class="property-label"><g:message code="menu.classes.label" default="Classes" /></span>
					
						<span class="property-value" aria-labelledby="classes-label"><g:fieldValue bean="${menuInstance}" field="classes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.children}">
				<li class="fieldcontain">
					<span id="children-label" class="property-label"><g:message code="menu.children.label" default="Children" /></span>
					
						<g:each in="${menuInstance.children}" var="c">
						<span class="property-value" aria-labelledby="children-label"><g:link controller="menu" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="menu.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${menuInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="menu.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${menuInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${menuInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="menu.parent.label" default="Parent" /></span>
					
						<span class="property-value" aria-labelledby="parent-label"><g:link controller="menu" action="show" id="${menuInstance?.parent?.id}">${menuInstance?.parent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${menuInstance?.id}" />
					<g:link class="edit" action="edit" id="${menuInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="menu" class="cancel" params="[currentCategoryId:menuInstance?.id]">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
