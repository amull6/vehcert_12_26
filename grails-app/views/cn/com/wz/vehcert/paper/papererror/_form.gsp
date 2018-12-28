<%@ page import="cn.com.wz.vehcert.paper.PaperError" %>


<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'serialNum', 'error')} required" style="margin-top:8px;">
    <label for="dealerNum">
        <g:message code="paperError.dealerNum.label" default="dealerNum" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="dealerNum" required="" value="${paperErrorInstance.dealerNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'dealerName', 'error')} required" style="margin-top:8px;">
    <label for="dealerName">
        <g:message code="paperError.dealerName.label" default="dealerName" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="dealerName" required="" value="${paperErrorInstance.dealerName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'paperNum', 'error')} required" style="margin-top:8px;">
    <label for="paperNum">
        <g:message code="paperError.paperNum.label" default="paperNum" />  ：

    </label>
    <g:textField name="paperNum"  value="${paperErrorInstance.paperNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'veh_Hgzbh', 'error')} required" style="margin-top:8px;">
    <label for="veh_Hgzbh">
        <g:message code="paperError.veh_Hgzbh.label" default="veh_Hgzbh" />  ：

    </label>
    <g:textField name="veh_Hgzbh"  value="${paperErrorInstance.veh_Hgzbh}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'num', 'error')} required" style="margin-top:8px;">
    <label for="num">
        <g:message code="paperError.num.label" default="veh_Hgzbh" />  ：

    </label>
    <g:textField name="num"  value="${paperErrorInstance.num}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'type', 'error')} required" style="margin-top:8px;">
    <label for="type">
        <g:message code="paper.type.label" default="type" />  ：

    </label>
    <select id="type" name="type">
        <g:if test="${paperErrorInstance.type==1}">
            <option value='1'>农用车整车</option>
            <option value="0">汽车整车</option>
            <option value='2'>二类底盘</option>
        </g:if>
        <g:elseif test="${paperErrorInstance.type==2}">
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

<div class="fieldcontain ${hasErrors(bean: paperErrorInstance, field: 'note', 'error')} " style="margin-top:8px;">
    <label for="note">
        <g:message code="paperError.note.label" default="note" />：
    </label>
    <g:textArea name="note" value="${paperErrorInstance.note}"  style="width:130px;height:85px;"/>
</div>

