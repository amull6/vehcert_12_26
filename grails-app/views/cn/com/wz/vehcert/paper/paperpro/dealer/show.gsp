
<%@ page import="cn.com.wz.vehcert.paper.Paper" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paper.label', default: 'Paper')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-paper" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="show-paper" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">



        <g:if test="${paperInstance?.dealerNum}">
            <li class="fieldcontain">
                <span id="dealerNum-label" class="property-label"><g:message code="paper.dealerNum.label" default="dealerNum" />：</span>

                <span class="property-value" aria-labelledby="dealerNum-label"><g:fieldValue bean="${paperInstance}" field="dealerNum"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.dealerName}">
            <li class="fieldcontain">
                <span id="dealerName-label" class="property-label"><g:message code="paper.dealerName.label" default="dealerName" />：</span>

                <span class="property-value" aria-labelledby="dealerName-label"><g:fieldValue bean="${paperInstance}" field="dealerName"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.nums}">
            <li class="fieldcontain">
                <span id="nums-label" class="property-label"><g:message code="paper.nums.label" default="nums" />：</span>

                <span class="property-value" aria-labelledby="nums-label"><g:fieldValue bean="${paperInstance}" field="nums"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.numRand}">
            <li class="fieldcontain">
                <span id="numRand-label" class="property-label"><g:message code="paper.numRand.label" default="numRand" />：</span>

                <span class="property-value" aria-labelledby="numRand-label"><g:fieldValue bean="${paperInstance}" field="numRand"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.receiveName}">
            <li class="fieldcontain">
                <span id="receiveName-label" class="property-label"><g:message code="paper.receiveName.label" default="receiveName" />：</span>

                <span class="property-value" aria-labelledby="receiveName-label"><g:fieldValue bean="${paperInstance}" field="receiveName"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.provideId}">
            <li class="fieldcontain">
                <span id="provideId-label" class="property-label"><g:message code="paper.provideName.label" default="provideName" />：</span>

                <span class="property-value" aria-labelledby="provideId-label"><c:getUserName userId="${paperInstance.provideId}"></c:getUserName></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.provideTime}">
            <li class="fieldcontain">
                <span id="provideName-label" class="property-label"><g:message code="paper.provideTime.label" default="provideTime" />：</span>

                <span class="property-value" aria-labelledby="provideTime-label"><g:fieldValue bean="${paperInstance}" field="provideTime"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.confirmTime}">
            <li class="fieldcontain">
                <span id="confirmTime-label" class="property-label"><g:message code="paper.confirmTime.label" default="confirmTime" />：</span>

                <span class="property-value" aria-labelledby="confirmTime-label"><g:fieldValue bean="${paperInstance}" field="confirmTime"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.note}">
            <li class="fieldcontain">
                <span id="note-label" class="property-label"><g:message code="paper.note.label" default="note" />：</span>

                <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${paperInstance}" field="note"/></span>

            </li>
        </g:if>

        <g:if test="${paperInstance?.noteConfirm}">
            <li class="fieldcontain">
                <span id="noteConfirm-label" class="property-label"><g:message code="paper.noteConfirm.label" default="noteConfirm" />：</span>

                <span class="property-value" aria-labelledby="noteConfirm-label"><g:fieldValue bean="${paperInstance}" field="noteConfirm"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${paperInstance?.id}" />
            <g:link class="edit" controller="paperPro" action="edit" id="${paperInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="PaperPro" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
