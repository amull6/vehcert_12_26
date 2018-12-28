<%@ page import="cn.com.wz.parent.system.user.User" %>
<%@ page import="cn.com.wz.parent.system.user.UserDetail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'changeMessage.label', default: 'User')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="edit-user" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${user}">
			<ul class="errors" role="alert">
				<g:eachError bean="${user}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${user?.id}" />
				<g:hiddenField name="version" value="${user?.version}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/user/changeMessageForm"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:actionSubmit  id="save" class="save" action="updateChangeMessage" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<input id="btn_clear" type="button" class="cancel" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				</fieldset>
			</g:form>
		</div>
		<script type="text/javascript">
			$(function() {
				$('#btn_clear').click(function(){
	           		
	                 $("#realName").val('');
	                 $("#eMail").val('');
	                 $("#mPhone").val('');
	                 $("#phoneCode").val('');
	                 $("#phone").val('');
	                 $("#birthDay").val('');
	                 $("#address").val('');
	   	            
           });
				$('#save').click(function(){
					var realName=$('#realName').val()
					var mPhone=$("#mPhone").val()
					var email=$("#eMail").val();
					
		          	 if(realName.length<1){
			          	 	alert("用户真实姓名不能为空！");
			          	 	return false;
			         } else{
			        	 if(email.length<1){
				        	 alert("邮箱地址不能为空！");
				          	 	return false;
					      }else{
					    	  if(mPhone.length<1){
					       			alert("手机号码不能为空！");
					       			return false;
					           }else{
						           return true;
						       }
						       
						  }
				
				     }
		          	  
		          	
				});
			});
		</script>
	</body>
</html>
