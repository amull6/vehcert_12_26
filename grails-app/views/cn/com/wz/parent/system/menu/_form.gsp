<%@ page import="cn.com.wz.parent.system.Menu" %>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'textC', 'error')} required" style='margin-top:-20px;'>
	<label for="textC">
		<g:message code="menu.textC.label" default="Text" />
	</label>
	<g:textField name="textC" maxlength="20" style="width:300px;height:20px;" required="" value="${menuInstance?.textC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'textE', 'error')} required">
	<label for="textE">
		<g:message code="menu.textE.label" default="English Name" />
	</label>
	<g:textField name="textE" maxlength="20" style="width:300px;height:20px;" required="" value="${menuInstance?.textE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="menu.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${menuInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'controller', 'error')} ">
	<label for="controller">
		<g:message code="menu.controller.label" default="Controller" />
		
	</label>
	<g:textField name="controllerName" style="width:300px;height:20px;" value="${menuInstance?.controllerName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'action', 'error')} ">
	<label for="action">
		<g:message code="menu.action.label" default="Action" />
		
	</label>
	<g:textField name="actionName" style="width:300px;height:20px;" value="${menuInstance?.actionName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'urlPms', 'error')} ">
	<label for="action">
		<g:message code="menu.urlParams.label" default="UrlParams" />
		
	</label>
	<g:textField name="urlPms" style="width:300px;height:20px;" value="${menuInstance?.urlPms}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'classes', 'error')} ">
	<label for="classes">
		<g:message code="menu.classes.label" default="Classes" />
		
	</label>
	<g:textField name="classes" style="width:300px;height:20px;" value="${menuInstance?.classes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'showOrder', 'error')} required">
	<label for="showOrder">
		<g:message code="role.showOrder.label" default="Show Order" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="showOrder" from="${menuInstance.constraints.showOrder.inList}" required="" value="${fieldValue(bean: menuInstance, field: 'showOrder')}" valueMessagePrefix="menu.showOrder"/>
</div>

<div class="fieldcontain ${hasErrors(bean: menuInstance, field: 'parent', 'error')}">
	<label for="parent">
		<g:message code="menu.parent.label" default="Parent" />
	</label>
	<c:ajaxTreeSelect  id='parent' readonly="readonly"  style="width:300px;height:20px;"  hidden_id='parent_hidden' hidden_name="parent.id" hidden_value="${menuInstance?.parent?.id}" value="${menuInstance?.parent?.textC}" url="${createLink(controller:'menu',action:'jsonAllList')}"></c:ajaxTreeSelect>
<%--	<g:select id="parent" name="parent.id" from="${cn.com.wz.parent.system.Menu.list()}" optionKey="id" value="${menuInstance?.parent?.id}" class="many-to-one" noSelection="['null': '根节点']"/>--%>
</div>

