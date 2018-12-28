<%@ page import="cn.com.wz.parent.system.user.User" %>
<%@ page import="cn.com.wz.parent.system.user.UserDetail" %>

<div class="fieldcontain ${hasErrors(bean: user, field: 'userName', 'error')} required" style="display:none;">
	<label for="userName">
		<g:message code="user.userName.label" default="User Name" />：
	</label>
	<g:textField name="userName" style="width:200px;height:20px;" required="" value="${user?.userName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'realName', 'error')} required">
	<label for="realName">
		<g:message code="user.realNameChange.label" default="RealName" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="realName" style="width:200px;height:20px;" id="realName" required="" value="${user?.userDetail?.realName}"/>
</div>
	<div style="display:none;">
			<div class="fieldcontain ${hasErrors(bean: user, field: 'status', 'error')} ">
				<label for="status">
					<g:message code="user.status.label" default="Enabled account" />：
				</label>
				<g:checkBox name="status" value="${user?.status}" checked="true"/>
			</div>

			<div class="fieldcontain ${hasErrors(bean: user, field: 'userType', 'error')} ">
				<label for="userTypeName">
					<g:message code="user.userType.label" default="User Type" />：
				</label>
				<g:select id="userType" name="userType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','userType')}})}" optionKey="id" required="" value="${user?.userType?.id}" class="one-to-one" />
			</div>

			<div class="fieldcontain ${hasErrors(bean: user, field: 'validStartTime', 'error')} ">
				<label for="validStartTime">
					<g:message code="user.validStartTime.label" default="Valid Start Time" />：
				</label>
				<c:dataPicker name="validStartTime" showTime="true" value="${user?.validStartTime }" ></c:dataPicker>
			</div>
			
			<div class="fieldcontain ${hasErrors(bean: user, field: 'validEndTime', 'error')} ">
				<label for="validEndTime">
					<g:message code="user.validEndTime.label" default="Valid End Time" />：
				</label>
				<c:dataPicker name="validEndTime" value="${user?.validEndTime }" showTime="true" ></c:dataPicker>
			</div>
	</div>

<input type="hidden" id='showDetail' value="0">
<div id="userDetail">
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'eMail', 'error')} ">
		<label for="eMail">
			<g:message code="user.eMail.label" default="E Mail" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="eMail" style="width:200px;height:20px;" id="eMail" value="${user?.userDetail?.eMail}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'mPhone', 'error')}">
		<label for="mPhone">
			<g:message code="user.mPhone.label" default="mPhone" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="mPhone" style="width:200px;height:20px;" id="mPhone"  value="${user?.userDetail?.mPhone}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'phoneCode', 'error')} ">
		<label for="phoneCode">
			<g:message code="user.phoneCode.label" default="Phone Code" />：
		</label>
		<g:textField name="phoneCode" style="width:200px;height:20px;"  id="phoneCode" value="${user?.userDetail?.phoneCode}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'phone', 'error')} ">
		<label for="phone">
			<g:message code="user.phone.label" default="phone" />：
		</label>
		<g:textField name="phone" id="phone" style="width:200px;height:20px;" value="${user?.userDetail?.phone}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'birthDay', 'error')} ">
		<label for="birthDay">
			<g:message code="user.birthDay.label" default="birthDay" />：
		</label>
		<c:dataPicker name="birthDay" id="birthDay" dateFormat='yy-mm-dd' value="${user?.userDetail?.birthDay}"></c:dataPicker>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'address', 'error')} ">
		<label for="address">
			<g:message code="user.address.label" default="address" />：
		</label>
		<g:textArea id="address"  name="address" style="width:300px;height:100px;"  value="${user?.userDetail?.address}"/>
	</div>
	<div style="display:none;">
			<div class="fieldcontain ${hasErrors(bean: organs, field: 'organs', 'error')} ">
			<label for="organs">
				<g:message code="user.organs.label" default="Organ" />：
			</label>
			<g:set var="organs" value="" />
			<g:each in="${user?.organs }">
				<g:set var="organs" value="${organs=organs+it.organNameC+' '}" />
				 
			</g:each>
			<g:textArea id="organsName"  name="organsName" style="width:300px;height:100px;"  value="${organs}" readonly="true"/>
			<input id="chooseOrgans" type="button" style="position:relative;top:80px" class="import"  value="${message(code: 'default.button.chooseOrgans.label', default: 'Choose Organs')}"/>
		</div>
		
		<div class="fieldcontain ${hasErrors(bean: posts, field: 'postsName', 'error')} ">
			<label for="posts">
				<g:message code="user.posts.label" default="posts" />：
			</label>
			<g:set var="posts" value="" />
			<g:each in="${user?.posts }">
				<g:set var="posts" value="${posts=posts+it.postNameC+' '}" />
				 
			</g:each>
			<g:textArea id="postsName"  name="postsName" style="width:300px; heighst:100px;"  value="${posts}" readonly="true"/>
			<input id="choosePosts" type="button"  style="position:relative;top:80px" class="import"  value="${message(code: 'default.button.choosePosts.label', default: 'Choose Posts')}"/>
		</div>
		
		<div class="fieldcontain ${hasErrors(bean: postsIds, field: 'postsIds', 'error')} ">
		    <input type="hidden" name='postsIds' id="postsIds" value="${postsIds}" >
		</div>
		
		<div class="fieldcontain ${hasErrors(bean: organsIds, field: 'organsIds', 'error')} ">
		    <input type="hidden" name='organsIds' id="organsIds" value="${organsIds}" >
		</div>
		<div class="fieldcontain ${hasErrors(bean: rolesIds, field: 'rolesIds', 'error')} ">
	    <input type="hidden" name='rolesIds' id="rolesIds" value="${rolesIds}" >
	</div>
	</div>
	
</div>

