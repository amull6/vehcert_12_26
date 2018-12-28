<%@ page import="cn.com.wz.parent.system.user.User" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'carstorage.label', default: 'carStorage')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<div id="edit-user" class="content scaffold-edit" role="main">
    <h1>编辑双燃料车/轻型汽油车辆环保信息</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${lightDualInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${lightDualInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" >
        <g:hiddenField name="id" value="${lightDualInstance?.id}" />
        <g:hiddenField name="version" value="${lightDualInstance?.version}" />
        <g:render template="/cn/com/wz/vehcert/environment/lightDual/form"/>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link controller="lightDual" class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
        </fieldset>
    </g:form>
</div>
<script type="text/javascript">
    $(".cancel").click(function(){
//        var currentCategoryId=$('#currentCategoryId').val();
        alert('111');
        $('.cancel').attr('href','javascript:void(0);');
        $.omMessageBox.confirm({
            title:'确认',
            content:'您确定要离开此页面吗？',
            onClose:function(value){
                if(value){
                    window.location.href="${createLink(controller:'lightDual',action:'index')}";
                }
            }
        });
    });
</script>
</body>
</html>
