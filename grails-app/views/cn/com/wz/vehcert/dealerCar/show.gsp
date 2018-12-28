
<%@ page import="cn.com.wz.vehcert.dealercarstorage.DealerCar" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'dealerCar.label', default: 'dealerCar')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-dealerCar" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="show-dealerCar" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">



        <g:if test="${dealerCarInstance?.dc_Ggxh}">
            <li class="fieldcontain">
                <span id="dc_Ggxh-label" class="property-label"><g:message code="dealerCar.dc_Ggxh.label" default="Name" />：</span>

                <span class="property-value" aria-labelledby="dc_Ggxh-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Ggxh"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Jss}">
            <li class="fieldcontain">
                <span id="dc_Jss-label" class="property-label"><g:message code="dealerCar.dc_Jss.label" default="dc_Jss" />：</span>

                <span class="property-value" aria-labelledby="dc_Jss-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Jss"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Ggpc}">
            <li class="fieldcontain">
                <span id="dc_Ggpc-label" class="property-label"><g:message code="dealerCar.dc_Ggpc.label" default="dc_Ggpc" />：</span>

                <span class="property-value" aria-labelledby="dc_Ggpc-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Ggpc"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Pic}">
            <li class="fieldcontain">
                <span id="dc_Pic-label" class="property-label"><g:message code="dealerCar.dc_Pic.label" default="dc_Pic" />：</span>

                <span class="property-value" aria-labelledby="dc_Pic-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Pic"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Jsszcrs}">
            <li class="fieldcontain">
                <span id="dc_Jsszcrs-label" class="property-label"><g:message code="dealerCar.dc_Jsszcrs.label" default="dc_Jsszcrs" />：</span>

                <span class="property-value" aria-labelledby="dc_Jsszcrs-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Jsszcrs"/></span>

            </li>
        </g:if>


        <g:if test="${dealerCarInstance?.dc_Fdj}">
            <li class="fieldcontain">
                <span id="dc_Fdj-label" class="property-label"><g:message code="dealerCar.dc_Fdj.label" default="dc_Fdj" />：</span>

                <span class="property-value" aria-labelledby="dc_Fdj-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Fdj"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Gglt}">
            <li class="fieldcontain">
                <span id="dc_Gglt-label" class="property-label"><g:message code="dealerCar.dc_Gglt.label" default="dc_Gglt" />：</span>

                <span class="property-value" aria-labelledby="dc_Gglt-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Gglt"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Bhps}">
            <li class="fieldcontain">
                <span id="dc_Bhps-label" class="property-label"><g:message code="dealerCar.dc_Bhps.label" default="dc_Bhps" />：</span>

                <span class="property-value" aria-labelledby="dc_Bhps-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Bhps"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Qhlj}">
            <li class="fieldcontain">
                <span id="dc_Qhlj-label" class="property-label"><g:message code="dealerCar.dc_Qhlj.label" default="dc_Qhlj" />：</span>

                <span class="property-value" aria-labelledby="dc_Qhlj-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Qhlj"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Zj}">
            <li class="fieldcontain">
                <span id="dc_Zj-label" class="property-label"><g:message code="dealerCar.dc_Zj.label" default="dc_Zj" />：</span>

                <span class="property-value" aria-labelledby="dc_Zj-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Zj"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Zccc}">
            <li class="fieldcontain">
                <span id="dc_Zccc-label" class="property-label"><g:message code="dealerCar.dc_Zccc.label" default="dc_Zccc" />：</span>

                <span class="property-value" aria-labelledby="dc_Zccc-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Zccc"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Hxcc}">
            <li class="fieldcontain">
                <span id="dc_Hxcc-label" class="property-label"><g:message code="dealerCar.dc_Hxcc.label" default="dc_Hxcc" />：</span>

                <span class="property-value" aria-labelledby="dc_Hxcc-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Hxcc"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Zzl}">
            <li class="fieldcontain">
                <span id="dc_Zzl-label" class="property-label"><g:message code="dealerCar.dc_Zzl.label" default="dc_Zzl" />：</span>

                <span class="property-value" aria-labelledby="dc_Zzl-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Zzl"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Zbzl}">
            <li class="fieldcontain">
                <span id="dc_Zbzl-label" class="property-label"><g:message code="dealerCar.dc_Zbzl.label" default="dc_Zbzl" />：</span>

                <span class="property-value" aria-labelledby="dc_Zbzl-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Zbzl"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_ZAzl}">
            <li class="fieldcontain">
                <span id="dc_ZAzl-label" class="property-label"><g:message code="dealerCar.dc_ZAzl.label" default="dc_ZAzl" />：</span>

                <span class="property-value" aria-labelledby="dc_ZAzl-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_ZAzl"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Kzrq}">
            <li class="fieldcontain">
                <span id="dc_Kzrq-label" class="property-label"><g:message code="dealerCar.dc_Kzrq.label" default="dc_Kzrq" />：</span>

                <span class="property-value" aria-labelledby="dc_Kzrq-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Kzrq"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.kind}">
            <li class="fieldcontain">
                <span id="kind-label" class="property-label"><g:message code="dealer.kind.label" default="kind" />：</span>

                <span class="property-value" aria-labelledby="kind-label"><g:fieldValue bean="${dealerCarInstance}" field="kind"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.dc_Qt}">
            <li class="fieldcontain">
                <span id="dc_Qt-label" class="property-label"><g:message code="dealerCar.dc_Qt.label" default="dc_Qt" />：</span>

                <span class="property-value" aria-labelledby="dc_Qt-label"><g:fieldValue bean="${dealerCarInstance}" field="dc_Qt"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.updaterName}">
            <li class="fieldcontain">
                <span id="updaterName-label" class="property-label"><g:message code="dealerCar.updaterName.label" default="updaterName" />：</span>

                <span class="property-value" aria-labelledby="updaterName-label"><g:fieldValue bean="${dealerCarInstance}" field="updaterName"/></span>

            </li>
        </g:if>

        <g:if test="${dealerCarInstance?.updateTime}">
            <li class="fieldcontain">
                <span id="updateTime-label" class="property-label"><g:message code="dealerCar.updateTime.label" default="updateTime" />：</span>

                <span class="property-value" aria-labelledby="updateTime-label"><g:fieldValue bean="${dealerCarInstance}" field="updateTime"/></span>

            </li>
        </g:if>
    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${dealerCarInstance?.id}" />
            <g:link class="edit" controller="dealerCar" action="edit" id="${dealerCarInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="dealerCar" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
