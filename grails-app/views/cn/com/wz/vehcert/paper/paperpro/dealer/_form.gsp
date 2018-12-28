<%@ page import="cn.com.wz.vehcert.paper.Paper" %>


<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'nums', 'error')} required" style="margin-top:8px;">
    <label for="nums">
        <g:message code="paper.nums.label" default="nums" />  ：

    </label>
    <span id="nums">${paperInstance.nums}</span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'numRand', 'error')} required" style="margin-top:8px;">
    <label for="numRand">
        <g:message code="paper.numRand.label" default="paperNum" />  ：

    </label>
    <span id="numRand">${paperInstance.numRand}</span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'type', 'error')} required" style="margin-top:8px;">
    <label for="type">
        <g:message code="paper.type.label" default="type" />  ：

    </label>
    <span id="type">
        <g:if test="${paperInstance.type==0}">
            汽车整车
        </g:if>
        <g:if test="${paperInstance.type==1}">
            农用车整车

        </g:if>
        <g:if test="${paperInstance.type==2}">
            二类底盘
        </g:if>
    </span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'numRand', 'error')} required" style="margin-top:8px;">
    <label for="provideId">
        <g:message code="paper.provideName.label" default="paperNum" />  ：

    </label>
    <span id="provideId">${paperInstance.provideId}</span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'provideTime', 'error')} required" style="margin-top:8px;">
    <label for="provideTime">
        <g:message code="paper.provideTime.label" default="provideTime" />  ：

    </label>
    <span id="provideTime">${paperInstance.provideId}</span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'note', 'error')} " style="margin-top:8px;">
    <label for="note">
        <g:message code="paper.note.label" default="note" />：
    </label>
    <span id="note">${paperInstance.note}</span>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'noteConfirm', 'error')} " style="margin-top:8px;">
    <label for="noteConfirm">
        <g:message code="paper.noteConfirm.label" default="noteConfirm" />：
    </label>
    <g:textArea name="noteConfirm" value="${paperInstance.noteConfirm}"  style="width:130px;height:85px;"/>
</div>

