<!doctype html>
<%@ page import="cn.com.wz.parent.system.Preference" %>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="default.setPrint.label"  /></title>
</head>
<body>
<div id="create-user" class="content scaffold-create" role="main">
    <h1><g:message code="default.setPrint.label"  /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${preference}">
        <ul class="errors" role="alert">
            <g:eachError bean="${user}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save" >
        <fieldset class="form">
            <g:if test='${flag ==1}'>
                <div class="fieldcontain ${hasErrors(bean: preference, field: 'oldPwd', 'error')} required">
                    <label for="oldPwd">
                        <g:message code="oldPwd.label" default="old Password" />：
                        <span class="required-indicator">*</span>
                    </label>
                    <g:passwordField id="oldPwd" name="oldPwd" style="width:200px;height:20px;" required="" />
                </div>
            </g:if>

            <div class="fieldcontain ${hasErrors(bean: preference, field: 'preValue', 'error')} required">
                <label for="preValue">
                    <g:message code="preValue.label" default="New Password" />：
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField id="preValue" name="preValue" style="width:200px;height:20px;" required="" /> <span style="color:red;">（注意：不能与登录密码一致）</span>
            </div>

            <div class="fieldcontain ${hasErrors(bean: preference, field: 'rNewPassword', 'error')} required">
                <label for="rNewPassword">
                    <g:message code="rNewPassword.label" default="Repeat　New Password" />：
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField id="rNewPassword" name="rNewPassword" style="width:200px;height:20px;" required="" />
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
            $("#oldPwd").val('');
            $("#preValue").val('');
            $("#rNewPassword").val('');

        });
        $('#btn_change').click(function(){
            //获得用户输入
            var oldPwd=$("#oldPwd").val();
            var preValue=$('#preValue').val()
            var rNewPassword=$('#rNewPassword').val()
            if(preValue==''||rNewPassword==''||oldPwd==''){
                alert('密码不能为空！');
                return;
            }
            //声明url
            var url = '${createLink(controller:'PrintPw',action:'savePrint')}';
            //判断两次输入的新密码是否一致
            if(preValue!=rNewPassword) {
                alert("${message(code:'different.message',default:'The two new passwords is not consistent')}");
                return;
            }
            $.post(url,{'oldPwd':oldPwd,"preValue":preValue},function(data,textStatus){
                if(data==='success'){
                    $.omMessageBox.alert({
                        type:'success',
                        title:'提示',
                        content:'密码设置成功',
                        onClose:function(value){
                               window.location.reload();

                        }
                    });
                }else if(data=='old'){
                    alert('与登录密码重复');
                }else if(data=='noExit'){
                    alert('与原密码不匹配');
                }else{
                    alert('密码设置失败');
                }
            },'text');
        });
    });
</script>
</body>
</html>