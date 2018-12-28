<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="default.changePassword.label"  /></title>
	</head>
	<body>
		<div id="create-user" class="content scaffold-create" role="main">
			<h1><g:message code="default.changePassword.label"  /></h1>
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
			<g:form action="save" >
				<fieldset class="form">
						<div class="fieldcontain ${hasErrors(bean: user, field: 'password', 'error')} required">
		                   <label for="password">
			                 <g:message code="password.label" default="Old Password" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:passwordField id="password" name="password"style="width:200px;height:20px;" required="" value="${password}" />
	                    </div>
	                    
	                    <div class="fieldcontain ${hasErrors(bean: user, field: 'newPassword', 'error')} required">
		                   <label for="newPassword">
			                 <g:message code="newPassword.label" default="New Password" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:passwordField id="newPassword" name="newPassword"style="width:200px;height:20px;" required="" value="${newPassword}"/>
	                    </div>
	                    
	                    <div class="fieldcontain ${hasErrors(bean: user, field: 'rNewPassword', 'error')} required">
		                   <label for="rNewPassword">
			                 <g:message code="rNewPassword.label" default="Repeat　New Password" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:passwordField id="rNewPassword" name="rNewPassword"style="width:200px;height:20px;" required="" value="${rNewPassword}"/>
	                    </div>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<input id="btn_change" type="button"  class="save" value="${message(code: 'change.label', default: 'Change')}"/>
					<input id="btn_clear" type="button" class="cancel" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				</fieldset>
			</g:form>
			
			
		</div>
		<script type="text/javascript">
		$(function() {
			$('#btn_clear').click(function(){
           		
	                 $("#password").val('');
	                 $("#newPassword").val('');
	                 $("#rNewPassword").val('');
	   	            
            });
			$('#btn_change').click(function(){
				//获得用户输入
				var password=$('#password').val()
	       		var newPassword=$('#newPassword').val()
	       		var rNewPassword=$('#rNewPassword').val()
	       		//声明url
	       		var url = '${createLink(controller:'ChangePassword',action:'save')}';
	       		//判断两次输入的新密码是否一致
	       		if(newPassword!=rNewPassword) {
	           		alert("${message(code:'different.message',default:'The two new passwords is not consistent')}");
	                return;
	            }
	       		//判断新密码长度是否符合要求
	       		if(newPassword.length<6||newPassword.length>32){
	           		alert("${message(code:'length.message',default:'The length of the new password must be more than 6,and less than 32!')}")
	           		return
	           	}
		        $.post(url,{"password":password,"newPassword":newPassword},function(data,textStatus){
			        var m=data;
		            alert(m.msg); 
		            if(m.suc) {
			            parent.$("#password").val(m.password);
			        } 
			    },'json');
			});
		});
		</script>
	</body>
</html>