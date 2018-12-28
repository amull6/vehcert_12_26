<%@ page import="cn.com.wz.vehcert.workshop.Workshop" %>

<div class="fieldcontain ${hasErrors(bean: workshopInstance, field: 'serialNum', 'error')} required" style="margin-top:4px;">
    <label for="serialNum">
        <g:message code="Workshop.serialNum.label" default="serialNum" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="serialNum" required="" value="${workshopInstance?.serialNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: workshopInstance, field: 'organs', 'error')} required" style="margin-top:8px;">
    <label for="name">
        <g:message code="Workshop.name.label" default="Name" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${workshopInstance?.name}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: workshopInstance, field: 'note', 'error')} " style="margin-top:8px;">
    <label for="note">
        <g:message code="Workshop.note.label" default="Note" />：
    </label>
    <g:textArea name="note" value="${workshopInstance?.note}"  style="width:130px;height:85px;"/>
</div>

