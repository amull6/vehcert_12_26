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
    <h1>编辑重型柴油/城市车辆环保信息</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${heavyDiesel}">
        <ul class="errors" role="alert">
            <g:eachError bean="${heavyDiesel}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" >
        <g:hiddenField name="id" value="${heavyDiesel?.id}" />
        <g:hiddenField name="version" value="${heavyDiesel?.version}" />
        <g:render template="/cn/com/wz/vehcert/environment/heavyDiesel/form"/>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
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
                    window.location.href="${createLink(controller:'heavyDiesel',action:'index')}";
                }
            }
        });
    });
</script>
</body>
</html>
