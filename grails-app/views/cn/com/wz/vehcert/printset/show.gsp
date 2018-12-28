<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="打印机设置" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div id="show-Workshop" class="content scaffold-show" role="main">
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list ITEquipmentBuy">

        <g:if test="${printSetInstance?.top}">
            <li class="fieldcontain">
                <span id="top" class="property-label">上边距：</span>

                <span class="property-value" aria-labelledby="top"><g:fieldValue bean="${printSetInstance}" field="top"/></span>

            </li>
        </g:if>

        <g:if test="${printSetInstance?.left}">
            <li class="fieldcontain">
                <span id="left" class="property-label">左边距：</span>

                <span class="property-value" aria-labelledby="left"><g:fieldValue bean="${printSetInstance}" field="left"/></span>

            </li>
        </g:if>

        <g:if test="${printSetInstance?.printerName}">
            <li class="fieldcontain">
                <span id="printerName" class="property-label">打印机名称：</span>

                <span class="property-value" aria-labelledby="printerName"><g:fieldValue bean="${printSetInstance}" field="printerName"/></span>

            </li>
        </g:if>
        <g:if test="${printSetInstance?.location}">
            <li class="fieldcontain">
                <span id="location" class="property-label">pdf存放位置：</span>
                <span class="property-value" aria-labelledby="location"><g:fieldValue bean="${printSetInstance}" field="location"/></span>
            </li>
        </g:if>
    </ol>
    <g:form>
        <fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:10px;">
            <g:hiddenField name="id" value="${printSetInstance?.id}" />
            <g:link class="edit" controller="printSet" action="edit" id="${printSetInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:link  class="cancel">关闭</g:link>
        </fieldset>
    </g:form>
</div>
<script type="text/javascript">
    $(".cancel").click(function(){
        $('.cancel').attr('href','javascript:void(0);');
        parent.closeDialog(window.parent.document.getElementById("gonggao_dialog").parentNode.id);
    });
</script>
</body>
</html>
