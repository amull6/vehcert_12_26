<%@ page import="cn.com.wz.parent.system.Menu" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-menu" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="create-menu" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${menuInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${menuInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/menu/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<input type="hidden" id="currentCategoryId" value="${menuInstance?.parent?.id}"/>
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<g:link  class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</g:form>
		</div>
		<script type="text/javascript">
		$(".cancel").click(function(){
			var currentCategoryId=$('#currentCategoryId').val();
			$('.cancel').attr('href','javascript:void(0);');
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'menu',action:'index')}?currentCategoryId="+currentCategoryId;
			              }
	                }
	            });
		});
		</script>
	</body>
</html>
