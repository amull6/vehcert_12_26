<%@ page import="cn.com.wz.parent.system.role.Role" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'role')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- 
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(controller:'role')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div> -->
		<div id="show-role" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list role">
			
				<g:if test="${role?.roleNameC}">
					<li class="fieldcontain">
						<span id="roleNameC-label" class="property-label"><g:message code="role.roleNameC.label" default="Role NameC:"  /></span>
						
							<span class="property-value" aria-labelledby="roleNameC-label"><g:fieldValue bean="${role}" field="roleNameC"/></span>
						
					</li>
				</g:if>
			
				<g:if test="${role?.roleNameE}">
					<li class="fieldcontain">
						<span id="roleNameE-label" class="property-label"><g:message code="role.roleNameE.label" default="Role NameE:"  /></span>
						
						    <span class="property-value" aria-labelledby="roleNameE-label"><g:fieldValue bean="${role}" field="roleNameE"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${role?.showOrder}">
					<li class="fieldcontain">
						<span id="showOrder-label" class="property-label"><g:message code="role.showOrder.label" default="Show Order:"  /></span>
						
						    <span class="property-value" aria-labelledby="showOrder-label"><g:fieldValue bean="${role}" field="showOrder"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${role?.roleCode}">
					<li class="fieldcontain">
						<span id="roleCode-label" class="property-label"><g:message code="role.roleCode.label" default="Role Code:"  /></span>
						
						    <span class="property-value" aria-labelledby="roleCode-label"><g:fieldValue bean="${role}" field="roleCode"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${role?.roleTypeName}">
					<li class="fieldcontain">
						<span id="roleTypeName-label" class="property-label"><g:message code="role.roleType.label" default="Role Type:"  /></span>
						
						    <span class="property-value" aria-labelledby="roleTypeName-label"><g:fieldValue bean="${role}" field="roleTypeName"/></span>
						
					</li>
				</g:if>

                <g:if test="${theSystem}">
                    <li class="fieldcontain">
                        <span id="theSystem-label" class="property-label"><g:message code="role.theSystem.label" default="The System:"  /></span>

                        <span class="property-value" aria-labelledby="roleTypeName-label">${theSystem}</span>

                    </li>
                </g:if>
				
				<g:if test="${role?.createdBy}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="role.createdBy.label" default="createdBy" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${role.createdBy }'></c:getUserName>
						</span>
					</li>
				</g:if>
				
				<g:if test="${role?.dateCreated}">
					<li class="fieldcontain">
						<span id="dateCreated-label" class="property-label"><g:message code="role.dateCreated.label" default="Date Created:" /></span>
						<span class="property-value" aria-labelledby="dateCreated-label"><g:fieldValue bean="${role}" field="dateCreated"/> </span>
						
					</li>
				</g:if>
				
				<g:if test="${role?.updatedBy}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="role.updatedBy.label" default="updatedBy" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${role.updatedBy }'></c:getUserName>
						</span>
					</li>
				</g:if>
				
				<g:if test="${role?.updateTime}">
					<li class="fieldcontain">
						<span id="updateTime-label" class="property-label"><g:message code="role.updateTime.label" default="Update Time:" /></span>
						<span class="property-value" aria-labelledby="updateTime-label"><g:fieldValue bean="${role}" field="updateTime"/> </span>
						
					</li>
				</g:if>
				
				<g:if test="${role?.description}">
					<li class="fieldcontain">
						<span id="description-label" class="property-label"><g:message code="role.description.label" default="Description:" /></span>
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${role}" field="description"/> </span>
						
					</li>
				</g:if>
				
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${role?.id}" />
					<g:link class="edit" action="edit" id="${role?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="role" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

