
<%@ page import="cn.com.wz.vehcert.paper.PaperError" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paperError.label', default: 'Paper')}" />
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



        <g:if test="${paperErrorInstance?.dealerNum}">
            <li class="fieldcontain">
                <span id="dealerNum-label" class="property-label"><g:message code="paperError.dealerNum.label" default="dealerNum" />：</span>

                <span class="property-value" aria-labelledby="dealerNum-label"><g:fieldValue bean="${paperErrorInstance}" field="dealerNum"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.dealerName}">
            <li class="fieldcontain">
                <span id="dealerName-label" class="property-label"><g:message code="paperError.dealerName.label" default="dealerName" />：</span>

                <span class="property-value" aria-labelledby="dealerName-label"><g:fieldValue bean="${paperErrorInstance}" field="dealerName"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.paperNum}">
            <li class="fieldcontain">
                <span id="paperNum-label" class="property-label"><g:message code="paperError.paperNum.label" default="paperNum" />：</span>

                <span class="property-value" aria-labelledby="paperNum-label"><g:fieldValue bean="${paperErrorInstance}" field="paperNum"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.veh_Hgzbh}">
            <li class="fieldcontain">
                <span id="veh_Hgzbh-label" class="property-label"><g:message code="paperError.veh_Hgzbh.label" default="veh_Hgzbh" />：</span>

                <span class="property-value" aria-labelledby="veh_Hgzbh-label"><g:fieldValue bean="${paperErrorInstance}" field="veh_Hgzbh"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.type}">
            <li class="fieldcontain">
                <span id="type-label" class="property-label"><g:message code="paper.type.label" default="type" />：</span>

                <span class="property-value" aria-labelledby="vin-label">
                    <g:if test="${paperErrorInstance.type==1}">
                        农用车整车
                    </g:if>
                    <g:elseif test="${paperErrorInstance.type==2}">
                        二类底盘
                    </g:elseif>
                </span>

            </li>
        </g:if>
        <g:else>
            <li class="fieldcontain">
                <span id="type1-label" class="property-label"><g:message code="paper.type.label" default="type" />：</span>

                <span class="property-value" aria-labelledby="vin-label">
                    汽车整车
                </span>

            </li>
        </g:else>

        <g:if test="${paperErrorInstance?.num}">
            <li class="fieldcontain">
                <span id="num-label" class="property-label"><g:message code="paperError.num.label" default="num" />：</span>

                <span class="property-value" aria-labelledby="num-label"><g:fieldValue bean="${paperErrorInstance}" field="num"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.createrId}">
            <li class="fieldcontain">
                <span id="createrId-label" class="property-label"><g:message code="paperError.createrId.label" default="createrId" />：</span>

                <span class="property-value" aria-labelledby="createrId-label"><c:getUserName userId="${paperErrorInstance.createrId}"></c:getUserName></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.createTime}">
            <li class="fieldcontain">
                <span id="createTime-label" class="property-label"><g:message code="paperError.createTime.label" default="createTime" />：</span>

                <span class="property-value" aria-labelledby="createTime-label"><g:fieldValue bean="${paperErrorInstance}" field="createTime"/></span>

            </li>
        </g:if>

        <g:if test="${paperErrorInstance?.note}">
            <li class="fieldcontain">
                <span id="note-label" class="property-label"><g:message code="paperError.note.label" default="note" />：</span>

                <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${paperErrorInstance}" field="note"/></span>

            </li>
        </g:if>


    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${paperErrorInstance?.id}" />
            <g:link class="edit" controller="paperError" action="edit" id="${paperErrorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="paperError" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
