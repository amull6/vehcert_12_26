<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryValue.label', default: 'DictionaryValue')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-dictionaryValue" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<!-- 
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(controller:'dictionary')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div> -->
		<div id="create-dictionaryValue" class="content scaffold-create" role="main">
			<h1><g:message code="default.new.label" args="[entityName]" /></h1>
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
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/dictionary/dictionaryValue/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<g:link controller="dictionary" class="cancel" params="[currentCategoryId:dictionaryValueInstance?.dictionaryType?.id]"> ${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
