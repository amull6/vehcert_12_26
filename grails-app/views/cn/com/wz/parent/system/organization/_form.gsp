<%@ page import="cn.com.wz.parent.system.organization.Organization" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>


<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'organNameC', 'error')} " style='margin-top:-20px;'>
	<label for="organNameC">
		<g:message code="organization.organNameC.label" default="Organ Name C" />
	</label>
	<g:textField name="organNameC" maxlength="200" style="width:300px;height:20px;" value="${organizationInstance?.organNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'organNameE', 'error')} ">
	<label for="organNameE">
		<g:message code="organization.organNameE.label" default="Organ Name E" />
	</label>
	<g:textField name="organNameE" maxlength="200" style="width:300px;height:20px;" value="${organizationInstance?.organNameE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'organCode', 'error')} ">
	<label for="organCode">
		<g:message code="organization.organCode.label" default="Organ Code" />
	</label>
	<g:textField name="organCode" maxlength="200" style="width:300px;height:20px;" value="${organizationInstance?.organCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'showOrder', 'error')} required">
	<label for="showOrder">
		<g:message code="organization.showOrder.label" default="Show Order" />
	</label>
    <g:field type="number" name="showOrder" value="${organizationInstance?.showOrder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'organType', 'error')} ">
	<label for="organType">
		<g:message code="organization.organType.label" default="Organ Type" />
	</label>
	<g:select id="organType" name="organType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','organ')}})}" optionKey="id" required="" value="${organizationInstance?.organType?.id}" class="one-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'parent', 'error')} required">
	<label for="parent">
		<g:message code="organization.parent.label" default="Parent" />
	</label>
	<c:ajaxTreeSelect  id='parent' style="width:300px;height:20px;" readonly="readonly"   hidden_id='parent_hidden' hidden_name="parent.id" hidden_value="${organizationInstance?.parent?.id}" value="${organizationInstance?.parent?.organNameC}" url="${createLink(controller:'organization',action:'jsonListTree')}"></c:ajaxTreeSelect>
<%--	<g:select id="parent" name="parent.id" from="${cn.com.wz.parent.system.organization.Organization.list()}" optionKey="id" required="" value="${organizationInstance?.parent?.id}" class="many-to-one" noSelection="['null': '组织结构']"/>--%>
</div>

<div class="fieldcontain ${hasErrors(bean: organizationInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="organization.description.label" default="Description" />
	</label>
	<g:textArea name="description" style="width:300px;height:90px;" value="${organizationInstance?.description}"></g:textArea>
</div>

