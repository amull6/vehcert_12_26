<%@ page import="cn.com.wz.vehcert.paper.PaperError" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paperError.label', default: 'paper')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<a href="#edit-paper" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="edit-paper" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${paperErrorInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${paperErrorInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" controller="paperError" >
        <g:hiddenField name="id" value="${paperErrorInstance?.id}" />
        <g:hiddenField name="version" value="${paperErrorInstance?.version}" />
        <fieldset class="form">
            <g:render template="/cn/com/wz/vehcert/paper/papererror/form"/>
        </fieldset>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:link  class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
        </fieldset>
    </g:form>
</div>
<script type="text/javascript">
    $(".cancel").click(function(){
        $('.cancel').attr('href','javascript:void(0);');
        $.omMessageBox.confirm({
            title:'确认',
            content:'您确定要离开此页面吗？',
            onClose:function(value){
                if(value){
                    window.location.href="${createLink(controller:'paperError',action:'index')}";
                }
            }
        });
    });
</script>
</body>
</html>
