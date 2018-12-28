<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>



<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'typeNameC', 'error')} " style='margin-top:-20px;'>
	<label for="typeNameC">
		<g:message code="dictionaryType.typeNameC.label" default="Type Name C" />
		
	</label>
	<g:textField name="typeNameC"  style="width:300px;height:20px;" value="${dictionaryType?.typeNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'typeNameE', 'error')} ">
	<label for="typeNameE">
		<g:message code="dictionaryType.typeNameE.label" default="Type Name E" />
		
	</label>
	<g:textField name="typeNameE"  style="width:300px;height:20px;" value="${dictionaryType?.typeNameE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="dictionaryType.code.label" default="code" />
		
	</label>
	<g:textField name="code" maxlength="200" style="width:300px;height:20px;" value="${dictionaryType?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'showOrder', 'error')} required">
	<label for="showOrder">
		<g:message code="dictionaryType.showOrder.label" default="Show Order" />
	</label>
	<g:select name="showOrder" from="${dictionaryType.constraints.showOrder.inList}" required="" value="${fieldValue(bean: dictionaryType, field: 'showOrder')}" valueMessagePrefix="dictionaryType.showOrder"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'parent', 'error')} ">
	<div class="fieldcontain ${hasErrors(bean: dictionaryType, field: 'parent', 'error')}">
		<label for="parent">
			<g:message code="menu.parent.label" default="ParentType" />
		</label>
		<c:ajaxTreeSelect  id='parent'  readonly="readonly" style="width:300px;height:20px;"  hidden_id='parent_hidden' hidden_name="parent.id" hidden_value="${dictionaryType?.parent?.id}" value="${dictionaryType?.parent?.typeNameC}" url="${createLink(controller:'dictionaryType',action:'jsonListTree')}"></c:ajaxTreeSelect>
<%--		<g:select id="parent" name="parent.id" from="${DictionaryType.list()}" optionKey="id" value="${dictionaryType?.parent?.id}" class="many-to-one" noSelection="['': '根节点']"/>--%>
	</div>
</div>




