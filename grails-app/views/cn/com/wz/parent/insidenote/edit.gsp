<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'insideNote.label', default: 'InsideNote')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-insideNote" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="edit-insideNote" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${insideNoteInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${insideNoteInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<form id="form" method="post" >
				<g:hiddenField name="id" value="${insideNoteInstance?.id}" />
				<g:hiddenField name="version" value="${insideNoteInstance?.version}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/insidenote/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<input type="button" id="btn_send" name="send" class="cancel"  value="${message(code: 'default.button.send.label', default: 'send')}" />
					<g:link action="draftBox" class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</form>
		</div>
		
		
		<script type="text/javascript">
		$(function() {
			$("#btn_send").click(function(){
				 $('#form').attr("action","${createLink(controller:'insideNote',action:'send')}?opFlag=1");
				 $("#form").submit();
			});
		});
		$(".cancel").click(function(){
			$('.cancel').attr('href','javascript:void(0);');
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'insideNote',action:'draftBox')}";
			              }
	                }
	            });
		});
		</script>
	</body>
</html>
