<%@ page import="cn.com.wz.vehcert.paper.Paper" %>


<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'serialNum', 'error')} required" style="margin-top:8px;">
    <label for="dealerNum">
        <g:message code="paper.dealerNum.label" default="dealerNum" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="dealerNum" required="" value="${paperInstance.dealerNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'dealerName', 'error')} required" style="margin-top:8px;">
    <label for="dealerName">
        <g:message code="paper.dealerName.label" default="dealerName" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="dealerName" required="" value="${paperInstance.dealerName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'nums', 'error')} required" style="margin-top:8px;">
    <label for="nums">
        <g:message code="paper.nums.label" default="nums" />  ：

    </label>
    <g:textField name="nums"  value="${paperInstance.nums}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'numRand', 'error')} required" style="margin-top:8px;">
    <label for="numRand">
        <g:message code="paper.numRand.label" default="paperNum" />  ：

    </label>
    <g:textField name="numRand"  value="${paperInstance.numRand}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'type', 'error')} required" style="margin-top:8px;">
    <label for="type">
        <g:message code="paper.type.label" default="type" />  ：

    </label>
    <select id="type" name="type">
        <g:if test="${paperInstance.type==1}">
            <option value='1'>农用车整车</option>
            <option value="0">汽车整车</option>
            <option value='2'>二类底盘</option>
        </g:if>
        <g:elseif test="${paperInstance.type==2}">
            <option value='2'>二类底盘</option>
            <option value='1'>农用车整车</option>
            <option value="0">汽车整车</option>
        </g:elseif>
        <g:else>
            <option value="0">汽车整车</option>
            <option value='1'>农用车整车</option>
            <option value='2'>二类底盘</option>
        </g:else>
    </select>
</div>

<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'receiveName', 'error')} " style="margin-top:8px;">
<label for="receiveName">
    <g:message code="paper.receiveName.label" default="receiveName" /> ：
</label>
<g:textField name="receiveName" value="${paperInstance.receiveName}"  style="margin-top:8px;"/>
</div>
<div class="fieldcontain ${hasErrors(bean: paperInstance, field: 'note', 'error')} " style="margin-top:8px;">
    <label for="note">
        <g:message code="paper.note.label" default="note" />：
    </label>
    <g:textArea name="note" value="${paperInstance.note}"  style="width:130px;height:85px;"/>
</div>

