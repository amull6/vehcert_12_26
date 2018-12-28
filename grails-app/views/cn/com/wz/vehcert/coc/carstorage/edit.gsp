<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="一致性证书基本信息" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="edit-package" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${cocCarStorageInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${cocCarStorageInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="update" method="post" id="reg" name="reg">
				<g:hiddenField name="cid" value="${cocCarStorageInstance?.id}" />
				<g:hiddenField name="version" value="${cocCarStorageInstance?.version}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/vehcert/coc/carstorage/form"/>
				</fieldset>
				<fieldset class="buttons">
					<input type="button" class="save" onclick="doSubmit();" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:link controller="cocCarStorage" class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</g:form>
		</div>
		<script>
		function doSubmit(){
			if (!$("#reg").valid()){
				return false;
    		}else{
				$('#reg').submit();
    		}
		}
		</script>
	</body>
</html>
