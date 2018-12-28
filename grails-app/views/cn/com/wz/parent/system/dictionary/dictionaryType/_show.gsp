
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryType.label', default: 'DictionaryType')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- 
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(controller:'dictionaryType')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div> -->
		<div id="show-dictionaryType" class="content scaffold-show" role="main">
			
			<ol class="property-list dictionaryType">
			
<%--				<g:if test="${dictionaryType?.id}">--%>
<%--					<li class="fieldcontain">--%>
<%--						<span id="id-label" class="property-label"><g:message code="dictionaryType.id.label" default="id" /></span>--%>
<%--						<span class="property-value" aria-labelledby="id-label"><g:fieldValue bean="${dictionaryType}" field="id"/></span>--%>
<%--						--%>
<%--					</li>--%>
<%--				</g:if>--%>
			
				<g:if test="${dictionaryType?.typeNameC}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.typeNameC.label" default="typeNameC" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="typeNameC"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.typeNameE}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.typeNameE.label" default="typeNameE" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="typeNameE"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.code}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.code.label" default="code" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="code"/></span>
						
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.showOrder}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.showOrder.label" default="showOrder" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="showOrder"/></span>
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.createTime}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.createTime.label" default="createTime" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="createTime"/></span>
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.createrId}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.createrName.label" default="createrName" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${dictionaryType.createrId }'></c:getUserName>
						</span>
					</li>
				</g:if>
				
				<g:if test="${dictionaryType?.lastUpdateTime}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.lastUpdateTime.label" default="lastUpdateTime" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label"><g:fieldValue bean="${dictionaryType}" field="lastUpdateTime"/></span>
					</li>
				</g:if>
	
				
				<g:if test="${dictionaryType?.lastUpdaterId}">
					<li class="fieldcontain">
						<span id="lastName-label" class="property-label"><g:message code="dictionaryType.lastUpdaterName.label" default="lastUpdaterName" /></span>
						<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId="${dictionaryType.lastUpdaterId }"></c:getUserName>
						</span>
					</li>
				</g:if>
		
			
			</ol>
			
		</div>
	</body>
</html>
