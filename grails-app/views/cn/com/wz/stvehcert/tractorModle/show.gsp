
<%@ page import="cn.com.wz.stvehcert.TractorModle" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="车型信息" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div id="show-Workshop" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">

        <g:if test="${tractorModleInstance?.modle}">
            <li class="fieldcontain">
                <span class="property-label">车型：</span>

                <span class="property-value" aria-labelledby="serialNum-label"><g:fieldValue bean="${tractorModleInstance}" field="modle"/></span>

            </li>
        </g:if>
        <g:if test="${tractorModleInstance?.modleName}">
            <li class="fieldcontain">
                <span class="property-label">车型名称：</span>

                <span class="property-value" aria-labelledby="serialNum-label"><g:fieldValue bean="${tractorModleInstance}" field="modleName"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${tractorModleInstance?.id}" />
            <g:link class="edit" controller="tractorModle" action="edit" id="${tractorModleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="tractorModle" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
