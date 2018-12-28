<%@ page import="cn.com.wz.parent.system.Preference" %>
<html>
	<head>
		<title><g:message code="default.setEmail.label" default="Set Email" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/main.css')}" type="text/css">
		<script src="${resource(dir:"js",file:"parent/jquery-1.7.2.min.js") }" type="text/javascript" ></script>
	</head>
	<body>
		<g:form action="save" >
				<fieldset>
						<div class="fieldcontain ${hasErrors(bean: user, field: 'password', 'error')} required">
		                   <label for="userName">
			                 <g:message code="eamiUserName.label" default="Email" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:textField id="userName" name="userName" required="" value="${userName}" />@wuzheng.com.cn
		                   <input type="hidden" id="address" name="address" value="@wuzheng.com.cn" />
	                    </div>
	                    
	                    <div class="fieldcontain ${hasErrors(bean: user, field: 'password', 'error')} required">
		                   <label for="password">
			                 <g:message code="password.label" default="Password" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:passwordField id="password" name="password" required="" value="${password}"/>
	                    </div>
	                    
	                    <div class="fieldcontain ${hasErrors(bean: user, field: 'rPassword', 'error')} required">
		                   <label for="rPassword">
			                 <g:message code="rPassword.label" default="Repeat　 Password" />：
			                 <span class="required-indicator">*</span>
		                   </label>
		                   <g:passwordField id="rPassword" name="rPassword" required="" value="${password}"/>
	                    </div>
				</fieldset>
				<fieldset class="buttons" >
					<input id="btn_save" style='margin-left:100px;' type="button"  class="save" value="${message(code: 'change.label', default: 'Change')}"/>
					<input id="btn_clear" type="button" class="delete" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					<input id="btn_close" type="button" class="cancel" value="<g:message code="default.button.close.label" default="Close" />"/>
				</fieldset>
			</g:form>
		<script type="text/javascript">
		$(function() {
			$('#btn_clear').click(function(){
           		
	                 $("#password").val('');
	                 $("#userName").val('');
	                 $("#rPassword").val('');
	   	            
            });
            $("#btn_close").click(function(){
            	 parent.closeDialog(window.parent.document.getElementById("email_dialog").parentNode.id);
             });
			$('#btn_save').click(function(){
				//获得用户输入
				var password=$('#password').val();
	       		var userName=$('#userName').val();
	       		var rPassword=$('#rPassword').val();
	       		var address=$('#address').val();
	       		//声明url
	       		var url = '${createLink(controller:'Preference',action:'saveEmail')}';
				if(!userName){
	            	alert("用户名必须填写");
	                return;
		        }
	            if(!password){
	            	alert("登录密码必须填写");
	                return;
		        }
	       		//判断两次输入的新密码是否一致
	       		if(password!=rPassword) {
	           		alert("${message(code:'different.message',default:'The two new passwords is not consistent')}");
	                return;
	            }
	       		//判断新密码长度是否符合要求
		        $.post(url,{"password":password,"userName":userName,"address":address},function(data,textStatus){
			        if(data=="true"){
				        parent.alert("邮箱设置成功");
				        parent.closeDialog(window.parent.document.getElementById("email_dialog").parentNode.id);
			        }else{
				        alert("保存失败");
				    }
			    },'text');
			});
		});
		</script>
	</body>
</html>
