<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>
<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'dicValueNameC', 'error')} " style='margin-top:-20px;'>
	<label for="dicValueNameC">
		<g:message code="dictionaryValue.dicValueNameC.label" default="Dic Value Name C" />
		
	</label>
	<g:textField name="dicValueNameC" maxlength="200" style="width:300px;height:20px;" value="${dictionaryValueInstance?.dicValueNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'dicValueNameE', 'error')} ">
	<label for="dicValueNameE">
		<g:message code="dictionaryValue.dicValueNameE.label" default="Dic Value Name E" />
		
	</label>
	<g:textField name="dicValueNameE" maxlength="200" style="width:300px;height:20px;" value="${dictionaryValueInstance?.dicValueNameE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'code', 'error')} ">
	<label for="dicValueNameE">
		<g:message code="dictionaryValue.code.label" default="code" />
		
	</label>
	<g:textField name="code" maxlength="200" style="width:300px;height:20px;" value="${dictionaryValueInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'value1', 'error')} " >
	<label for="dicValueNameC">
		<g:message code="dictionaryValue.value1.label" default="Value One" />
		
	</label>
	<g:textField name="value1"  style="width:300px;height:20px;" value="${dictionaryValueInstance?.value1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'value2', 'error')} ">
	<label for="dicValueNameC">
		<g:message code="dictionaryValue.value2.label" default="Value Two" />
		
	</label>
	<g:textField name="value2"  style="width:300px;height:20px;" value="${dictionaryValueInstance?.value2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'isvalid', 'error')} ">
	<label for="isvalid">
		<g:message code="dictionaryValue.isvalid.label" default="Isvalid" />
		
	</label>
	<g:checkBox name="isvalid" value="${dictionaryValueInstance?.isvalid}" />
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'ordernum', 'error')} ">
	<label for="ordernum">
		<g:message code="dictionaryValue.ordernum.label" default="Ordernum" />
		
	</label>
	<g:field type="number" name="ordernum" value="${dictionaryValueInstance.ordernum}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'dictionaryType', 'error')} required">
	<label for="dictionaryType">
		<g:message code="dictionaryValue.dictionaryType.label" default="Dictionary Type" />
		<span class="required-indicator">*</span>
	</label>
	<c:ajaxTreeSelect  id='dictionaryType' readonly="readonly" style="width:300px;height:20px;"  hidden_id='parent_hidden' hidden_name="dictionaryType.id" hidden_value="${dictionaryValueInstance?.dictionaryType?.id}" value="${dictionaryValueInstance?.dictionaryType?.typeNameC}" url="${createLink(controller:'dictionaryType',action:'jsonListTree')}"></c:ajaxTreeSelect>
<%--	<g:select id="dictionaryType" name="dictionaryType.id" from="${DictionaryType.list()}" optionKey="id" required="" value="${dictionaryValueInstance?.dictionaryType?.id}" class="many-to-one"/>--%>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryValueInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="dictionaryValue.remark.label" default="remark" />
		
	</label>
	<g:textArea maxlength="500" name="remark"  style="width:300px;height:120px;" value="${dictionaryValueInstance?.remark}"/>
</div>

