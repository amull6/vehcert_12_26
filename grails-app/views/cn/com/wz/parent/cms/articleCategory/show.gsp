
<%@ page import="cn.com.wz.parent.cms.ArticleCategory" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'articleCategory.label', default: 'ArticleCategory')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-articleCategory" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="show-articleCategory" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status" style="width:100%">${flash.message}</div>
			</g:if>
			
			<g:form>
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/cms/articleCategory/show"/>
				</fieldset>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${articleCategoryInstance?.id}" />
					<g:link class="edit" action="edit" id="${articleCategoryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="articleCategory" class="cancle" params="[currentCategoryId:articleCategoryInstance?.id]">${message(code: 'default.button.return.label', default: 'return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
