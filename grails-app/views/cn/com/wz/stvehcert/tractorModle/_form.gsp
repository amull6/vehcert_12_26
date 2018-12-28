<%@ page import="cn.com.wz.stvehcert.TractorModle" %>

<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'modle', 'error')} required" style="margin-top:4px;">
    <label for="modle">
        车型
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="modle" required="" value="${tractorModleInstance?.modle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'modleName', 'error')} required" style="margin-top:8px;">
    <label for="modleName">
        车型名称
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="modleName" required="" value="${tractorModleInstance?.modleName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Fdjxh', 'error')} required" style="margin-top:8px;">
    %{--@declare id="veh_fdjxh"--}%<label for="veh_Fdjxh">
        发动机型号
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Fdjxh" required="" value="${tractorModleInstance?.veh_Fdjxh}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Jxdl', 'error')} required" style="margin-top:8px;">
    <label for="veh_Jxdl">
        机械大类
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Jxdl" required="" value="${tractorModleInstance?.veh_Jxdl}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Rllx', 'error')} required" style="margin-top:8px;">
    <label for="veh_Rllx">
        燃料类型
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Rllx" required="" value="${tractorModleInstance?.veh_Rllx}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Jxxlb', 'error')} required" style="margin-top:8px;">
    <label for="veh_Jxxlb">
        机械小类别
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Jxxlb" required="" value="${tractorModleInstance?.veh_Jxxlb}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Zycs', 'error')} required" style="margin-top:8px;">
    <label for="veh_Zycs">
        机械产品的主要参数
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Zycs" required="" value="${tractorModleInstance?.veh_Zycs}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'veh_Pfjd', 'error')} required" style="margin-top:8px;">
    <label for="veh_Pfjd">
        排放阶段
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="veh_Pfjd" required="" value="${tractorModleInstance?.veh_Pfjd}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'modle_type', 'error')} required" style="margin-top:8px;">
    <label for="modle_type">
        类型
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="modle_type" required="" value="${tractorModleInstance?.modle_type}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'car_storage_no', 'error')} required" style="margin-top:8px;">
    <label for="car_storage_no">
        公告唯一号
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="car_storage_no" required="" value="${tractorModleInstance?.car_storage_no}"/>
</div>


%{--<div class="fieldcontain ${hasErrors(bean: tractorModleInstance, field: 'note', 'error')} " style="margin-top:8px;">--}%
    %{--<label for="note">--}%
        %{--<g:message code="Workshop.note.label" default="Note" />：--}%
    %{--</label>--}%
    %{--<g:textArea name="note" value="${tractorModleInstance?.note}"  style="width:130px;height:85px;"/>--}%
%{--</div>--}%

