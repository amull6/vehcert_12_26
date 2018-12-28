
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryValue.label', default: 'DictionaryValue')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- 
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(controller:'dictionary')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div> -->
		<div id="show-dictionaryValue" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dictionaryValue">
			
				<g:if test="${dictionaryValueInstance?.dicValueNameC}">
				<li class="fieldcontain">
					<span id="dicValueNameC-label" class="property-label"><g:message code="dictionaryValue.dicValueNameC.label" default="Dic Value Name C" /></span>
					
						<span class="property-value" aria-labelledby="dicValueNameC-label"><g:fieldValue bean="${dictionaryValueInstance}" field="dicValueNameC"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.dicValueNameE}">
				<li class="fieldcontain">
					<span id="dicValueNameE-label" class="property-label"><g:message code="dictionaryValue.dicValueNameE.label" default="Dic Value Name E" /></span>
					
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryValueInstance}" field="dicValueNameE"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${dictionaryValueInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="dictionaryValue.code.label" default="code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${dictionaryValueInstance}" field="code"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${dictionaryValueInstance?.value1}">
				<li class="fieldcontain">
					<span id="value1-label" class="property-label"><g:message code="dictionaryValue.value1.label" default="Value One" /></span>
					
						<span class="property-value" aria-labelledby="value1-label"><g:fieldValue bean="${dictionaryValueInstance}" field="value1"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${dictionaryValueInstance?.value2}">
				<li class="fieldcontain">
					<span id="value2-label" class="property-label"><g:message code="dictionaryValue.value2.label" default="Value Two" /></span>
					
						<span class="property-value" aria-labelledby="value2-label"><g:fieldValue bean="${dictionaryValueInstance}" field="value2"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.isvalid}">
				<li class="fieldcontain">
					<span id="isvalid-label" class="property-label"><g:message code="dictionaryValue.isvalid.label" default="Isvalid" /></span>
					
						<span class="property-value" aria-labelledby="isvalid-label"><g:formatBoolean boolean="${dictionaryValueInstance?.isvalid}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.ordernum}">
				<li class="fieldcontain">
					<span id="ordernum-label" class="property-label"><g:message code="dictionaryValue.ordernum.label" default="Ordernum" /></span>
					
						<span class="property-value" aria-labelledby="ordernum-label"><g:fieldValue bean="${dictionaryValueInstance}" field="ordernum"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="dictionaryValue.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${dictionaryValueInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.lastUpdatedBy}">
				<li class="fieldcontain">
					<span id="lastUpdatedBy-label" class="property-label"><g:message code="dictionaryValue.lastUpdatedBy.label" default="Last Updated By" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdatedBy-label"><g:fieldValue bean="${dictionaryValueInstance}" field="lastUpdatedBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="dictionaryValue.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${dictionaryValueInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="dictionaryValue.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:fieldValue bean="${dictionaryValueInstance}" field="createdBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dictionaryValueInstance?.dictionaryType}">
				<li class="fieldcontain">
					<span id="dictionaryType-label" class="property-label"><g:message code="dictionaryValue.dictionaryType.label" default="Dictionary Type" /></span>
					
						<span class="property-value" aria-labelledby="dictionaryType-label"><g:link controller="dictionaryType" action="show" id="${dictionaryValueInstance?.dictionaryType?.id}">${dictionaryValueInstance?.dictionaryType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
				
				<g:if test="${dictionaryValueInstance?.remark}">
				<li class="fieldcontain">
					<span id="remark-label" class="property-label"><g:message code="dictionaryValue.remark.label" default="remark" /></span>
					
						<span class="property-value" aria-labelledby="remark-label"><g:fieldValue bean="${dictionaryValueInstance}" field="remark"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${dictionaryValueInstance?.id}" />
					<g:link class="edit" action="edit" id="${dictionaryValueInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="dictionary" class="cancel" params="[currentCategoryId:dictionaryValueInstance?.dictionaryType?.id]">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
