<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="打印机设置" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<div id="edit-workshop" class="content scaffold-edit" role="main">
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${printSetInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${printSetInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" >
        <g:hiddenField name="id" value="${printSetInstance?.id}" />
        <g:hiddenField name="version" value="${printSetInstance?.version}" />
        <fieldset class="form">
            <g:render template="/cn/com/wz/vehcert/printset/form"/>
        </fieldset>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:10px;">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link  class="cancel">关闭</g:link>
        </fieldset>
    </g:form>
</div>
<script type="text/javascript">
    $(".cancel").click(function(){
        $('.cancel').attr('href','javascript:void(0);');
        parent.closeDialog(window.parent.document.getElementById("gonggao_dialog").parentNode.id);
    });
</script>
</body>
</html>
