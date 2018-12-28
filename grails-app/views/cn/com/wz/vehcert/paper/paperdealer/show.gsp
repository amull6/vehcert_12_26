
<%@ page import="cn.com.wz.vehcert.paper.PaperDealer" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paperDealer.label', default: 'Paper')}" />
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



        <g:if test="${paperdealerInstance?.paperNum}">
            <li class="fieldcontain">
                <span id="paperNum-label" class="property-label"><g:message code="paperDealer.paperNum.label" default="paperNum" />：</span>

                <span class="property-value" aria-labelledby="paperNum-label"><g:fieldValue bean="${paperdealerInstance}" field="paperNum"/></span>

            </li>
        </g:if>

        <g:if test="${paperdealerInstance?.zcinfoNum}">
            <li class="fieldcontain">
                <span id="zcinfoNum-label" class="property-label"><g:message code="paperDealer.zcinfoNum.label" default="zcinfoNum" />：</span>

                <span class="property-value" aria-labelledby="zcinfoNum-label"><g:fieldValue bean="${paperdealerInstance}" field="zcinfoNum"/></span>

            </li>
        </g:if>

        <g:if test="${paperdealerInstance?.type}">
            <li class="fieldcontain">
                <span id="type-label" class="property-label"><g:message code="paperDealer.type.label" default="type" />：</span>

                <span class="property-value" aria-labelledby="vin-label">
                    <g:if test="${paperdealerInstance.type==1}">
                       农用车整车
                    </g:if>
                    <g:elseif test="${paperdealerInstance.type==2}">
                        二类底盘
                    </g:elseif>
                </span>

            </li>
        </g:if>
        <g:else>
            <li class="fieldcontain">
                <span id="type1-label" class="property-label"><g:message code="paperDealer.type.label" default="type" />：</span>

                <span class="property-value" aria-labelledby="vin-label">
                    汽车整车
                </span>

            </li>
        </g:else>

        <g:if test="${paperdealerInstance?.vin}">
            <li class="fieldcontain">
                <span id="vin-label" class="property-label"><g:message code="paperDealer.vin.label" default="vin" />：</span>

                <span class="property-value" aria-labelledby="vin-label"><g:fieldValue bean="${paperdealerInstance}" field="vin"/></span>

            </li>
        </g:if>

        <g:if test="${paperdealerInstance?.createrId}">
            <li class="fieldcontain">
                <span id="createrId-label" class="property-label"><g:message code="paperDealer.createrId.label" default="createrId" />：</span>

                <span class="property-value" aria-labelledby="createrId-label"><c:getUserName userId="${paperdealerInstance.createrId}"></c:getUserName></span>

            </li>
        </g:if>

        <g:if test="${paperdealerInstance?.createTime}">
            <li class="fieldcontain">
                <span id="createTime-label" class="property-label"><g:message code="paperDealer.createTime.label" default="createTime" />：</span>

                <span class="property-value" aria-labelledby="createTime-label"><g:fieldValue bean="${paperdealerInstance}" field="createTime"/></span>

            </li>
        </g:if>


        <g:if test="${paperdealerInstance?.updaterId}">
            <li class="fieldcontain">
                <span id="updaterId-label" class="property-label"><g:message code="paperDealer.updaterId.label" default="updaterId" />：</span>

                <span class="property-value" aria-labelledby="updaterId-label"><c:getUserName userId="${paperdealerInstance.updaterId}"></c:getUserName></span>

            </li>
        </g:if>

        <g:if test="${paperdealerInstance?.updateTime}">
            <li class="fieldcontain">
                <span id="updateTime-label" class="property-label"><g:message code="paperDealer.updateTime.label" default="updateTime" />：</span>

                <span class="property-value" aria-labelledby="updateTime-label"><g:fieldValue bean="${paperdealerInstance}" field="updateTime"/></span>

            </li>
        </g:if>



    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
            <g:hiddenField name="id" value="${paperdealerInstance?.id}" />
            <g:link class="edit" controller="paperDealer" action="edit" id="${paperdealerInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link controller="paperDealer" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
