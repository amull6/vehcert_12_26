<%@ page import="cn.com.wz.parent.system.user.User" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list user">
			
				<g:if test="${user?.userName}">
				<li class="fieldcontain">
					<span id="userName-label" class="property-label"><g:message code="user.userName.label" default="User Name" />：</span>
					
						<span class="property-value" aria-labelledby="userName-label"><g:fieldValue bean="${user}" field="userName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.userDetail?.realName}">
				<li class="fieldcontain">
					<span id="realName-label" class="property-label"><g:message code="user.realName.label" default="Real Name" />：</span>
					
						<span class="property-value" aria-labelledby="realName-label"><g:fieldValue bean="${user?.userDetail}" field="realName"/></span>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="user.status.label" default="Status" />：</span>
						<span class="property-value" aria-labelledby="status-label">
							<g:if test="${user?.status}">
								已启用
							</g:if>
							<g:else>
								未启用
							</g:else>
						</span>
					
				</li>
			
				<g:if test="${user?.userType}">
				<li class="fieldcontain">
					<span id="userType-label" class="property-label"><g:message code="user.userType.label" default="User Type" />：</span>
					
						<span class="property-value" aria-labelledby="userType-label"><g:fieldValue bean="${user}" field="userType"/></span>
					
				</li>
				</g:if>
					
				<g:if test="${user?.validStartTime}">
				<li class="fieldcontain">
					<span id="validStartTime-label" class="property-label"><g:message code="user.validStartTime.label" default="Valid Start Time" />：</span>
					
						<span class="property-value" aria-labelledby="validStartTime-label"><g:fieldValue bean="${user}" field="validStartTime" /></span>
				</li>
				</g:if>
				
				<g:if test="${user?.validEndTime}">
				<li class="fieldcontain">
					<span id="validEndTime-label" class="property-label"><g:message code="user.validEndTime.label" default="Valid End Time" />：</span>
					
						<span class="property-value" aria-labelledby="validEndTime-label"><g:fieldValue bean="${user}" field="validEndTime" /></span>
					
				</li>
				</g:if>

				<g:if test="${user?.userDetail?.eMail}">
				<li class="fieldcontain">
					<span id="eMail-label" class="property-label"><g:message code="user.eMail.label" default="eMail" />：</span>
					
						<span class="property-value" aria-labelledby="eMail-label"><g:fieldValue bean="${user?.userDetail}" field="eMail"/></span>
					
				</li>
				</g:if>
				<g:if test="${user?.userDetail?.mPhone}">
				<li class="fieldcontain">
					<span id="mPhone-label" class="property-label"><g:message code="user.mPhone.label" default="mPhone" />：</span>
					
						<span class="property-value" aria-labelledby="mPhone-label"><g:fieldValue bean="${user?.userDetail}" field="mPhone"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${user?.userDetail?.phoneCode}">
				<li class="fieldcontain">
					<span id="phoneCode-label" class="property-label"><g:message code="user.phoneCode.label" default="Phone Code" />：</span>
					
						<span class="property-value" aria-labelledby="phoneCode-label"><g:fieldValue bean="${user?.userDetail}" field="phoneCode"/></span>
					
				</li>
				</g:if>
				<g:if test="${user?.userDetail?.phone}">
				<li class="fieldcontain">
					<span id="phone-label" class="property-label"><g:message code="user.phone.label" default="Phone" />：</span>
					
						<span class="property-value" aria-labelledby="phone-label"><g:fieldValue bean="${user?.userDetail}" field="phone"/></span>
					
				</li>
				</g:if>
				<g:if test="${user?.userDetail?.birthDay}">
				<li class="fieldcontain">
					<span id="birthDay-label" class="property-label"><g:message code="user.birthDay.label" default="BirthDay" />：</span>
					
						<span class="property-value" aria-labelledby="birthDay-label"><g:fieldValue bean="${user?.userDetail}" field="birthDay"/></span>
					
				</li>
				</g:if>
				<g:if test="${user?.userDetail?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="user.address.label" default="Address" />：</span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${user?.userDetail}" field="address"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${user?.userDetail?.jobLevel}">
				<li class="fieldcontain">
					<span id="jobLevel-label" class="property-label"><g:message code="user.jobLevel.label" default="Job Level" />：</span>
					
						<span class="property-value" aria-labelledby="jobLevel-label"><g:fieldValue bean="${user?.userDetail}" field="jobLevel"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${user?.organs}">
				<li class="fieldcontain">
					<span id="organs-label" class="property-label"><g:message code="user.organs.label" default="Organ" />：</span>
					
						<span class="property-value" aria-labelledby="organs-label">
							<g:each in="${user?.organs }">
								<g:fieldValue bean="${it}" field="organNameC"/>
							</g:each>
						</span>
					
				</li>
				</g:if>
				
				<g:if test="${user?.posts}">
				<li class="fieldcontain">
					<span id="posts-label" class="property-label"><g:message code="user.posts.label" default="Post" />：</span>
					
					<span class="property-value" aria-labelledby="posts-label">
						<g:each in="${user?.posts }">
							<g:fieldValue bean="${it}" field="postNameC"/>
						</g:each>
					</span>
					
				</li>
				</g:if>
				
				<g:if test="${user?.roles}">
				<li class="fieldcontain">
					<span id="roles-label" class="property-label"><g:message code="user.roles.label" default="Roles" />：</span>
					
					<span class="property-value" aria-labelledby="roles-label">
						<g:each in="${user?.roles }">
							<g:fieldValue bean="${it}" field="roleNameC"/>
						</g:each>
					</span>
					
				</li>
				</g:if>
			
				<g:if test="${user?.lastLoginTime}">
				<li class="fieldcontain">
					<span id="lastLoginTime-label" class="property-label"><g:message code="user.lastLoginTime.label" default="lastLoginTime" />：</span>
					
						<span class="property-value" aria-labelledby="lastLoginTime-label"><g:fieldValue bean="${user}" field="lastLoginTime" /></span>
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${user?.id}" />
					<g:link class="edit" action="edit" id="${user?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link controller="user" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
