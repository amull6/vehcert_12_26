<%@ page import="cn.com.wz.parent.system.Menu"%>
<br>
<div id="show-menu" class="content scaffold-show" role="main">
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<g:hasErrors bean="${menuInstance}">
		<ul class="errors" role="alert">
			<g:eachError bean="${menuInstance}" var="error">
				<li
					<g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
						error="${error}" /></li>
			</g:eachError>
		</ul>
	</g:hasErrors>
	<ol class="property-list menu">
		<li class="fieldcontain">
			<span id="label-label" class="property-label">
				<g:message code="menu.textC.label" default="TextC" />
			</span>
			<span class="property-value" aria-labelledby="label-label">
				<g:fieldValue bean="${menuInstance}" field="textC" />
			</span>
		</li>
					
		<li class="fieldcontain"><span id="textE-label"
			class="property-label"><g:message
					code="menu.textE.label" default="English Name" /></span> <span
			class="property-value" aria-labelledby="textE-label"><g:fieldValue
					bean="${menuInstance}" field="textE" /></span></li>
		<li class="fieldcontain"><span id="enabled-label"
			class="property-label"><g:message code="menu.enabled.label"
					default="Enabled" /></span> <span class="property-value"
			aria-labelledby="enabled-label"><g:formatBoolean
					boolean="${menuInstance?.enabled}" /></span></li>
		<li class="fieldcontain"><span id="controller-label"
			class="property-label"><g:message code="menu.controller.label"
					default="Controller" /></span> <span class="property-value"
			aria-labelledby="controller-label"><g:fieldValue
					bean="${menuInstance}" field="controllerName" /></span></li>
		<li class="fieldcontain"><span id="action-label"
			class="property-label"><g:message code="menu.action.label"
					default="Action" /></span> <span class="property-value"
			aria-labelledby="action-label"><g:fieldValue
					bean="${menuInstance}" field="actionName" /></span></li>
		<li class="fieldcontain"><span id="classes-label"
			class="property-label"><g:message code="menu.classes.label"
					default="Classes" /></span> <span class="property-value"
			aria-labelledby="classes-label"><g:fieldValue
					bean="${menuInstance}" field="classes" /></span>
		</li>
		<li class="fieldcontain">
			<span id="classes-label" class="property-label">
				<g:message code="role.showOrder.label" default="showOrder" /></span> 
			<span class="property-value" aria-labelledby="classes-label">
				<g:fieldValue bean="${menuInstance}" field="showOrder" />
			</span>
		</li>
		<!--不显示父节点 
		<li class="fieldcontain"><span id="parent-label"
			class="property-label"><g:message code="menu.parent.label"
					default="Parent" /></span> <span class="property-value"
			aria-labelledby="parent-label"><g:link controller="menu"
					action="show" id="${menuInstance?.parent?.id}">
					${menuInstance?.parent?.encodeAsHTML()}
				</g:link></span></li>
		 -->
	</ol>
</div>