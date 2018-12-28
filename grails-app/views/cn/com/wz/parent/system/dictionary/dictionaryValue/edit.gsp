<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryValue.label', default: 'dictionary.DictionaryValue')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-dictionaryValue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="edit-dictionaryValue" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${dictionaryValueInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${dictionaryValueInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${dictionaryValueInstance?.id}" />
				<g:hiddenField name="version" value="${dictionaryValueInstance?.version}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/dictionary/dictionaryValue/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:link controller="dictionary" class="cancel" params="[currentCategoryId:dictionaryValueInstance?.dictionaryType?.id]">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
