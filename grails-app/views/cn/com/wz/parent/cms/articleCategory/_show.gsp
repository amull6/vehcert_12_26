
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'articleCategory.label', default: 'ArticleCategory')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		
		<div id="show-articleCategory" class="content scaffold-show" role="main">
			<ol class="property-list articleCategory">
			
				<g:if test="${articleCategoryInstance?.categoryNameC}">
				<li class="fieldcontain">
					<span id="categoryNameC-label" class="property-label"><g:message code="articleCategory.categoryNameC.label" default="Category Name C" /></span>
					
						<span class="property-value" aria-labelledby="categoryNameC-label"><g:fieldValue bean="${articleCategoryInstance}" field="categoryNameC"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.categoryNameE}">
				<li class="fieldcontain">
					<span id="categoryNameE-label" class="property-label"><g:message code="articleCategory.categoryNameE.label" default="Category Name E" /></span>
					
						<span class="property-value" aria-labelledby="categoryNameE-label"><g:fieldValue bean="${articleCategoryInstance}" field="categoryNameE"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.categoryCode}">
				<li class="fieldcontain">
					<span id="categoryCode-label" class="property-label"><g:message code="articleCategory.categoryCode.label" default="Category Code" /></span>
					
						<span class="property-value" aria-labelledby="categoryCode-label"><g:fieldValue bean="${articleCategoryInstance}" field="categoryCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.showOrder}">
				<li class="fieldcontain">
					<span id="showOrder-label" class="property-label"><g:message code="articleCategory.showOrder.label" default="Show Order" /></span>
					
						<span class="property-value" aria-labelledby="showOrder-label"><g:fieldValue bean="${articleCategoryInstance}" field="showOrder"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="articleCategory.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${articleCategoryInstance}" field="description"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.categoryType?.id}">
				<li class="fieldcontain">
					<span id="categoryTypeName-label" class="property-label"><g:message code="organization.organType.label" default="Organ Type" /></span>
					<span class="property-value" aria-labelledby="categoryTypeName-label">${articleCategoryInstance?.categoryType?.encodeAsHTML()}</span>
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.isDeleted}">
				<li class="fieldcontain">
					<span id="isDeleted-label" class="property-label"><g:message code="articleCategory.isDeleted.label" default="Is Deleted" /></span>
						<span class="property-value" aria-labelledby="isDeleted-label"><g:fieldValue bean="${articleCategoryInstance}" field="isDeleted"/></span>
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.needAuth}">
				<li class="fieldcontain">
					<span id="needAuth-label" class="property-label"><g:message code="articleCategory.needAuth.label" default="Need Auth" /></span>
					
						<span class="property-value" aria-labelledby="needAuth-label"><g:formatBoolean boolean="${articleCategoryInstance?.needAuth}" /></span>
					
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.createTime}">
				<li class="fieldcontain">
					<span id="createTime-label" class="property-label"><g:message code="articleCategory.createTime.label" default="Create Time" /></span>
					
						<span class="property-value" aria-labelledby="createTime-label"><g:formatDate date="${articleCategoryInstance?.createTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.createrId}">
				<li class="fieldcontain">
					<span id="createrId-label" class="property-label"><g:message code="articleCategory.createrId.label" default="Creater Id" /></span>
					<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${articleCategoryInstance.createrId }'></c:getUserName>
					</span>
				</li>
				</g:if>
			
				
			
				<g:if test="${articleCategoryInstance?.lastUpdateTime}">
				<li class="fieldcontain">
					<span id="lastUpdateTime-label" class="property-label"><g:message code="articleCategory.lastUpdateTime.label" default="Last Update Time" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdateTime-label"><g:formatDate date="${articleCategoryInstance?.lastUpdateTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${articleCategoryInstance?.lastUpdaterId}">
				<li class="fieldcontain">
					<span id="lastUpdaterId-label" class="property-label"><g:message code="articleCategory.lastUpdaterId.label" default="Last Updater Id" /></span>
					<span class="property-value" aria-labelledby="dicValueNameE-label">
							<c:getUserName userId='${articleCategoryInstance.lastUpdaterId }'></c:getUserName>
					</span>
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.parent}">
				<li class="fieldcontain">
					<span id="parent-label" class="property-label"><g:message code="articleCategory.parent.label" default="Parent" /></span>
					<span class="property-value" aria-labelledby="parent-label"><g:link controller="articleCategory" action="show" id="${articleCategoryInstance?.parent?.id}">${articleCategoryInstance?.parent?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
				
				<g:if test="${articleCategoryInstance?.style}">
				<li class="fieldcontain">
					<span id="style-label" class="property-label"><g:message code="articleCategory.style.label" default="Style" /></span>
					
						<span class="property-value" aria-labelledby="style-label"><g:fieldValue bean="${articleCategoryInstance}" field="style"/></span>
					
				</li>
				</g:if>
			
			</ol>
			
		</div>
	</body>
</html>
