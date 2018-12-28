<%@ page import="cn.com.wz.parent.system.remind.Remind" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'remind.label', default: 'Remind')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="create-remind" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${remindInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${remindInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<form id="form" method="post"  style="margin-top:-5px;" >
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/remind/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:5px 8px 8px 4px;padding-left:240px;">
					<input type="button" id="btn_sendNow" name="send" class="edit"  value="${message(code: 'affair.button.send.label', default: 'send')}" />
					<g:link  class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
				</fieldset>
			</form>
		</div>
		<script type="text/javascript">
		$(function() {
			$("#btn_sendNow").click(function(){
				var receiver=$("#receiverIds").val();
				if(receiver==null||receiver.length==0){
					alert("请选择接收人！");
					return false;
				}
				var url="${createLink(controller:'Remind',action:'send')}";
				 $('#form').attr("action",url);
				 $("#form").submit();
			});
			$(".cancel").click(function(){
				$('.cancel').attr('href','javascript:void(0);');
				 $.omMessageBox.confirm({
		                title:'确认',
		                content:'您确定要离开此页面吗？',
		                onClose:function(value){
			                if(value){
			                	window.location.href="${createLink(controller:'Remind',action:'list')}";
				              }
		                }
		            });
			});
			
				
		});
		</script>
	</body>
</html>
