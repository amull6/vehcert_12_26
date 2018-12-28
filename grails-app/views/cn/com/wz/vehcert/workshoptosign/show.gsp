
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



        <g:if test="${symbolInstance?.workshop.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="Workshop.name.label" default="Name" />：</span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${symbolInstance.workshop}" field="name"/></span>

            </li>
        </g:if>

        <g:if test="${symbolInstance?.workshop.serialNum}">
            <li class="fieldcontain">
                <span id="serialNum-label" class="property-label"><g:message code="Workshop.serialNum.label" default="serialNum" />：</span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${symbolInstance.workshop}" field="serialNum"/></span>

            </li>
        </g:if>

        <g:if test="${symbolInstance?.kind}">
            <li class="fieldcontain">
                <span id="kind-label" class="property-label"><g:message code="Workshop.kind.label" default="kind" />：</span>

                <span class="property-value" aria-labelledby="kind-label"><g:fieldValue bean="${symbolInstance}" field="kind"/></span>

            </li>
        </g:if>

        <g:if test="${symbolInstance?.symbol}">
            <li class="fieldcontain">
                <span id="symbol-label" class="property-label"><g:message code="Workshop.symbol.label" default="symbol" />：</span>

                <span class="property-value" aria-labelledby="symbol-label"><g:fieldValue bean="${symbolInstance}" field="symbol"/></span>

            </li>
        </g:if>

        <g:if test="${symbolInstance?.startNum}">
            <li class="fieldcontain">
                <span id="startNum-label" class="property-label"><g:message code="Workshop.start.label" default="startNum" />：</span>

                <span class="property-value" aria-labelledby="startNum-label"><g:fieldValue bean="${symbolInstance}" field="startNum"/></span>

            </li>
        </g:if>
        <g:if test="${symbolInstance?.maxNum}">
            <li class="fieldcontain">
                <span id="maxNum-label" class="property-label"><g:message code="Workshop.max.label" default="maxNum" />：</span>

                <span class="property-value" aria-labelledby="maxNum-label"><g:fieldValue bean="${symbolInstance}" field="maxNum"/></span>

            </li>
        </g:if>

        <g:if test="${symbolInstance?.symbolNote}">
            <li class="fieldcontain">
                <span id="symbolNote-label" class="property-label"><g:message code="Workshop.symbolNote.label" default="symbolNote" />：</span>

                <span class="property-value" aria-labelledby="symbolNote-label"><g:fieldValue bean="${symbolInstance}" field="symbolNote"/></span>

            </li>
        </g:if>
    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${symbolInstance?.id}" />
            <g:link class="edit" controller="WorkshopToSign" action="edit" id="${symbolInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="WorkshopToSign" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
