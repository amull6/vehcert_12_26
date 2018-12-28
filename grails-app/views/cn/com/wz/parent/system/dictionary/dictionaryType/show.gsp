
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryType.label', default: 'DictionaryType')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- 
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(controller:'dictionaryType')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div> -->
		<div id="show-employee" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:form>
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/dictionary/dictionaryType/show"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${dictionaryType?.id}" />
					<g:link class="edit" action="edit" id="${dictionaryType?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="dictionaryType" class="cancel" params="[currentCategoryId:dictionaryType?.id]">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
