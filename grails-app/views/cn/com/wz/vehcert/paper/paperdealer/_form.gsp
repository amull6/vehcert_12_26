<%@ page import="cn.com.wz.vehcert.paper.PaperDealer" %>


<div class="fieldcontain ${hasErrors(bean: paperdealerInstance, field: 'paperNum', 'error')} required" style="margin-top:8px;">
    <label for="paperNum">
        <g:message code="paperDealer.paperNum.label" default="paperNum" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="paperNum" required="" value="${paperdealerInstance.paperNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperdealerInstance, field: 'zcinfoNum', 'error')} required" style="margin-top:8px;">
    <label for="zcinfoNum">
        <g:message code="paperDealer.zcinfoNum.label" default="zcinfoNum" />
    </label>
    <g:textField name="zcinfoNum"  value="${paperdealerInstance.zcinfoNum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: paperdealerInstance, field: 'type', 'error')} required" style="margin-top:8px;">
    <label for="paperNum">
        <g:message code="paperDealer.type.label" default="type" />  ：

    </label>
    <select id="type" name="type">
        <g:if test="${paperdealerInstance.type==1}">
            <option value='1'>农用车整车</option>
            <option value="0">汽车整车</option>
            <option value='2'>二类底盘</option>
        </g:if>
        <g:elseif test="${paperdealerInstance.type==2}">
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

<div class="fieldcontain ${hasErrors(bean: paperdealerInstance, field: 'vin', 'error')} required" style="margin-top:8px;">
    <label for="vin">
        <g:message code="paperDealer.vin.label" default="vin" />  ：

    </label>
    <g:textField name="vin"  value="${paperdealerInstance.vin}"/>
</div>


