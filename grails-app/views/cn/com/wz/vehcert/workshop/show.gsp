
<%@ page import="cn.com.wz.vehcert.workshop.Workshop" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-Workshop" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="show-Workshop" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">

        <g:if test="${workshopInstance?.serialNum}">
            <li class="fieldcontain">
                <span id="equipmentName-label" class="property-label"><g:message code="Workshop.serialNum.label" default="SerialNum" />：</span>

                <span class="property-value" aria-labelledby="serialNum-label"><g:fieldValue bean="${workshopInstance}" field="serialNum"/></span>

            </li>
        </g:if>

        <g:if test="${workshopInstance?.name}">
            <li class="fieldcontain">
                <span id="organs-label" class="property-label"><g:message code="Workshop.name.label" default="Name" />：</span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${workshopInstance}" field="name"/></span>

            </li>
        </g:if>

        <g:if test="${workshopInstance?.note}">
            <li class="fieldcontain">
                <span id="post-label" class="property-label"><g:message code="Workshop.note.label" default="Note" />：</span>

                <span class="property-value" aria-labelledby="post-label"><g:fieldValue bean="${workshopInstance}" field="note"/></span>

            </li>
        </g:if>
    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${workshopInstance?.id}" />
            <g:link class="edit" controller="Workshop" action="edit" id="${workshopInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="Workshop" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
