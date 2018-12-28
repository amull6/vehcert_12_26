
<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'oil.label', default: 'oil')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-oil" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="show-oil" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">



        <g:if test="${oilInstance?.veh_Clxh}">
            <li class="fieldcontain">
                <span id="veh_Clxh-label" class="property-label"><g:message code="oil.veh_Clxh.label" default="Name" />：</span>

                <span class="property-value" aria-labelledby="veh_Clxh-label"><g:fieldValue bean="${oilInstance}" field="veh_Clxh"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_Clmc}">
            <li class="fieldcontain">
                <span id="veh_Clmc-label" class="property-label"><g:message code="oil.veh_Clmc.label" default="veh_Clmc" />：</span>

                <span class="property-value" aria-labelledby="veh_Clmc-label"><g:fieldValue bean="${oilInstance}" field="veh_Clmc"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_Yh}">
            <li class="fieldcontain">
                <span id="veh_Yh-label" class="property-label"><g:message code="oil.veh_Yh.label" default="veh_Yh" />：</span>

                <span class="property-value" aria-labelledby="veh_Yh-label"><g:fieldValue bean="${oilInstance}" field="veh_Yh"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_Fdjxh}">
            <li class="fieldcontain">
                <span id="veh_Fdjxh-label" class="property-label"><g:message code="oil.veh_Fdjxh.label" default="veh_Fdjxh" />：</span>

                <span class="property-value" aria-labelledby="veh_Fdjxh-label"><g:fieldValue bean="${oilInstance}" field="veh_Fdjxh"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_CarH}">
            <li class="fieldcontain">
                <span id="veh_CarH-label" class="property-label"><g:message code="oil.veh_CarH.label" default="veh_CarH" />：</span>

                <span class="property-value" aria-labelledby="veh_CarH-label"><g:fieldValue bean="${oilInstance}" field="veh_CarH"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_GoodH}">
            <li class="fieldcontain">
                <span id="veh_GoodH-label" class="property-label"><g:message code="oil.veh_GoodH.label" default="veh_GoodH" />：</span>

                <span class="property-value" aria-labelledby="veh_GoodH-label"><g:fieldValue bean="${oilInstance}" field="veh_GoodH"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.veh_Bz}">
            <li class="fieldcontain">
                <span id="veh_Bz-label" class="property-label"><g:message code="oil.veh_Bz.label" default="veh_Bz" />：</span>

                <span class="property-value" aria-labelledby="veh_Bz-label"><g:fieldValue bean="${oilInstance}" field="veh_Bz"/></span>

            </li>
        </g:if>

        <g:if test="${oilInstance?.kind}">
            <li class="fieldcontain">
                <span id="kind-label" class="property-label"><g:message code="oil.kind.label" default="kind" />：</span>

                <span class="property-value" aria-labelledby="kind-label"><g:fieldValue bean="${oilInstance}" field="kind"/></span>

            </li>
        </g:if>


    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${oilInstance?.id}" />
            <g:link class="edit" controller="oil" action="edit" id="${oilInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="oil" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
