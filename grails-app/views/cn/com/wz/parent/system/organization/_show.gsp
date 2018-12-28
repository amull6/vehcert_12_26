
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'organization.label', default: 'Organization')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		
		<div id="show-organization" class="content scaffold-show" role="main">
			<ol class="property-list organization">
			
				<g:if test="${organizationInstance?.organNameC}">
				<li class="fieldcontain">
					<span id="organNameC-label" class="property-label"><g:message code="organization.organNameC.label" default="Organ Name C" /></span>
					<span class="property-value" aria-labelledby="organNameC-label"><g:fieldValue bean="${organizationInstance}" field="organNameC"/></span>
				</li>
				</g:if>
			
				<g:if test="${organizationInstance?.organNameE}">
				<li class="fieldcontain">
					<span id="organNameE-label" class="property-label"><g:message code="organization.organNameE.label" default="Organ Name E" /></span>
					<span class="property-value" aria-labelledby="organNameE-label"><g:fieldValue bean="${organizationInstance}" field="organNameE"/></span>
				</li>
				</g:if>
			
				<g:if test="${organizationInstance?.organCode}">
				<li class="fieldcontain">
					<span id="organCode-label" class="property-label"><g:message code="organization.organCode.label" default="Organ Code" /></span>
					<span class="property-value" aria-labelledby="organCode-label"><g:fieldValue bean="${organizationInstance}" field="organCode"/></span>
				</li>
				</g:if>
			
				<g:if test="${organizationInstance?.showOrder}">
				<li class="fieldcontain">
					<span id="showOrder-label" class="property-label"><g:message code="organization.showOrder.label" default="Show Order" /></span>
					<span class="property-value" aria-labelledby="showOrder-label"><g:fieldValue bean="${organizationInstance}" field="showOrder"/></span>
				</li>
				</g:if>
			
				<g:if test="${organizationInstance?.organType?.id}">
				<li class="fieldcontain">
					<span id="organTypeId-label" class="property-label"><g:message code="organization.organType.label" default="Organ Type" /></span>
					<span class="property-value" aria-labelledby="organTypeId-label">${organizationInstance?.organType?.encodeAsHTML()}</span>
				</li>
				</g:if>
			
				<g:if test="${organizationInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="organization.parent.label" default="Parent" /></span>
					<span class="property-value" aria-labelledby="parent-label"><g:link controller="organization" action="show" id="${organizationInstance?.parent?.id}">${organizationInstance?.parent?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
				
				<g:if test="${organizationInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="organization.description.label" default="Description" /></span>
					<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${organizationInstance}" field="description"/></span>
				</li>
				</g:if>
			</ol>
			
		</div>
	</body>
</html>
