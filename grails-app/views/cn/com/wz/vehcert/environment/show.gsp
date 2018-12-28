
<%@ page import="cn.com.wz.vehcert.environment.Environment" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'environment.label', default: 'environment')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-environment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="show-environment" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">



        <g:if test="${environmentInstance?.veh_Clxh}">
            <li class="fieldcontain">
                <span id="veh_Clxh-label" class="property-label"><g:message code="environment.veh_Clxh.label" default="Name" />：</span>

                <span class="property-value" aria-labelledby="veh_Clxh-label"><g:fieldValue bean="${environmentInstance}" field="veh_Clxh"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.veh_Clmc}">
            <li class="fieldcontain">
                <span id="veh_Clmc-label" class="property-label"><g:message code="environment.veh_Clmc.label" default="veh_Clmc" />：</span>

                <span class="property-value" aria-labelledby="veh_Clmc-label"><g:fieldValue bean="${environmentInstance}" field="veh_Clmc"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.veh_Clfl}">
            <li class="fieldcontain">
                <span id="veh_Clfl-label" class="property-label"><g:message code="environment.veh_Clfl.label" default="veh_Clfl" />：</span>

                <span class="property-value" aria-labelledby="veh_Clfl-label"><g:fieldValue bean="${environmentInstance}" field="veh_Clfl"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.veh_Fdjxh}">
            <li class="fieldcontain">
                <span id="veh_Fdjxh-label" class="property-label"><g:message code="environment.veh_Fdjxh.label" default="veh_Fdjxh" />：</span>

                <span class="property-value" aria-labelledby="veh_Fdjxh-label"><g:fieldValue bean="${environmentInstance}" field="veh_Fdjxh"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.veh_Ggpx}">
            <li class="fieldcontain">
                <span id="veh_Ggpx-label" class="property-label"><g:message code="environment.veh_Ggpx.label" default="veh_Ggpx" />：</span>

                <span class="property-value" aria-labelledby="veh_Fdjxh-label"><g:fieldValue bean="${environmentInstance}" field="veh_Ggpx"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.veh_Bz}">
            <li class="fieldcontain">
                <span id="veh_Bz-label" class="property-label"><g:message code="environment.veh_Bz.label" default="veh_Bz" />：</span>

                <span class="property-value" aria-labelledby="veh_Bz-label"><g:fieldValue bean="${environmentInstance}" field="veh_Bz"/></span>

            </li>
        </g:if>

        <g:if test="${environmentInstance?.kind}">
            <li class="fieldcontain">
                <span id="kind-label" class="property-label"><g:message code="environment.kind.label" default="kind" />：</span>

                <span class="property-value" aria-labelledby="kind-label"><g:fieldValue bean="${environmentInstance}" field="kind"/></span>

            </li>
        </g:if>


    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${environmentInstance?.id}" />
            <g:link class="edit" controller="environment" action="edit" id="${environmentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="environment" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
