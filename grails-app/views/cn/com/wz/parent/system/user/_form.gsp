<%@ page import="cn.com.wz.parent.system.user.User" %>
<%@ page import="cn.com.wz.parent.system.user.UserDetail" %>

<div class="fieldcontain ${hasErrors(bean: user, field: 'userName', 'error')} required" style='margin-top:-20px;'>
	<label for="userName">
		<g:message code="user.userName.label" default="User Name" />：
	</label>
	<g:textField name="userName"  style="width:300px;height:20px;" required="" value="${user?.userName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'realName', 'error')} required">
	<label for="realName">
		<g:message code="user.realName.label" default="RealName" />：
	</label>
	<g:textField name="realName"  style="width:300px;height:20px;" required="" value="${user?.userDetail?.realName}"/>
</div>
	
<div class="fieldcontain ${hasErrors(bean: user, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="user.status.label" default="Enabled account" />：
	</label>
		<g:checkBox name="status" value="${user?.status}"/>
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

<input type="hidden" id='showDetail' value="0">
<div id='manageDetail' onclick="manageDetail()"  style="cursor:pointer;background: url('${resource(dir:'images/',file:'parent/expand.png')}') no-repeat scroll -16px -15px transparent; background-color :#ccc;height:20px;width:100%; line-height:20px;"><font style='font-size: 12px; font-weight :bold;margin-left:20px;'>详细信息</font></div>
<div id="userDetail" style="display:none;">
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'eMail', 'error')} ">
		<label for="eMail">
			<g:message code="user.eMail.label" default="E Mail" />：
		</label>
		<g:textField name="eMail" style="width:300px;height:20px;" value="${user?.userDetail?.eMail}"/>
	</div>

	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'mPhone', 'error')} ">
		<label for="mPhone">
			<g:message code="user.mPhone.label" default="mPhone" />：
		</label>
		<g:textField name="mPhone"  style="width:300px;height:20px;" value="${user?.userDetail?.mPhone}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'phoneCode', 'error')} ">
		<label for="phoneCode">
			<g:message code="user.phoneCode.label" default="Phone Code" />：
		</label>
		<g:textField name="phoneCode"  style="width:300px;height:20px;" value="${user?.userDetail?.phoneCode}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'phone', 'error')} ">
		<label for="phone">
			<g:message code="user.phone.label" default="phone" />：
		</label>
		<g:textField name="phone"  style="width:300px;height:20px;"  value="${user?.userDetail?.phone}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'birthDay', 'error')} ">
		<label for="birthDay">
			<g:message code="user.birthDay.label" default="birthDay" />：
		</label>
		<c:dataPicker name="birthDay" dateFormat='yy-mm-dd' value="${user?.userDetail?.birthDay}"></c:dataPicker>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'address', 'error')} ">
		<label for="address">
			<g:message code="user.address.label" default="address" />：
		</label>
		<g:textField name="address"  style="width:300px;height:20px;" value="${user?.userDetail?.address}"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: userDetail, field: 'jobLevel', 'error')} ">
	<label for="jobLevel">
		<g:message code="user.jobLevel.label" default="Job Level" />：
	</label>
	<g:select id="jobLevel" name="jobLevel.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
		dictionaryType{eq('code','jobLevel') }
		order("ordernum","desc")
		})}" optionKey="id" required="" value="${user?.userDetail?.jobLevel?.id}"  class="one-to-one" />
</div>

	
	<div class="fieldcontain ${hasErrors(bean: organs, field: 'organs', 'error')} ">
		<label for="organs">
			<g:message code="user.organs.label" default="Organ" />：
		</label>
		<g:set var="organs" value="" />
		<g:each in="${user?.organs }">
			<g:set var="organs" value="${organs=organs+it.organNameC+' '}" />
			 
		</g:each>
		<g:textArea id="organsName"  name="organsName"  style="width:300px;height:90px;"  value="${organs}" readonly="true"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: posts, field: 'postsName', 'error')} ">
		<label for="posts">
			<g:message code="user.posts.label" default="posts" />：
		</label>
		<g:set var="posts" value="" />
		<g:each in="${user?.posts }">
			<g:set var="posts" value="${posts=posts+it.postNameC+' '}" />
			 
		</g:each>
		<g:textArea id="postsName"  name="postsName"  style="width:300px;height:90px;"  value="${posts}" readonly="true"/>
	</div>
	
	<div class="fieldcontain ${hasErrors(bean: roles, field: 'roles', 'error')} ">
		<label for="roles">
			<g:message code="user.roles.label" default="roles" />：
		</label>
		<g:set var="roles" value="" />
		<g:each in="${user?.roles }">
			<g:set var="roles" value="${roles=roles+it.roleNameC+' '}" />
			 
		</g:each>
		<g:textArea id="rolesName"  name="rolesName"  style="width:300px;height:90px;"  value="${roles}" readonly="true"/>
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

<script type="text/javascript">
<!--
//事件绑定
	$(function() {
	    $("#postsName").click(function(){
	    	   var url = '${createLink(controller:'User',action:'getPosts')}'
	    	   showModelDialog(url);
	    });

	    $("#organsName").click(function(){
	    	   var url = '${createLink(controller:'User',action:'getOrgans')}'
	    	   showModelDialog2(url);
	    });

	    $("#rolesName").click(function(){
	    	   var url = '${createLink(controller:'User',action:'getRoles')}'
	    	   showModelDialog3(url);
	    });
	});
	//#############################用户自定义方法############################
	//弹出用户选择窗口，并生成岗位表格信息
	function showModelDialog(url){
		    var tabId=window.name
		    if(tabId==''|| tabId==null){
				tabId="iframe_myJob";
			 }
		    var postsIds=$("#postsIds").val()
			var content='<iframe id="posts_dialog" style="margin-left:-10px;margin-top:-10px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+"?tabId="+tabId+"&postsIds="+postsIds+'"></iframe>';
			var title='<g:message code='user.post.label' args='[entityName1]'/>';
			//打开弹出框
			parent.showDialog(1,content,title,600,567);
	}

	//弹出用户选择窗口，并生成组织表格信息
	function showModelDialog2(url){
		    var tabId=window.name
	  	    console.log(tabId)
		    if(tabId==''|| tabId==null){
				tabId="iframe_myJob";
			 }
		    var organsIds=$("#organsIds").val()
			var content='<iframe id="organs_dialog" style="margin-left:-5px;margin-top:-10px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+"?tabId="+tabId+"&organsIds="+organsIds+'"></iframe>';
			var title='<g:message code='user.organ.label' args='[entityName1]'/>';
			//打开弹出框
			parent.showDialog(1,content,title,600,567);
	}

	function showModelDialog3(url){
	    var tabId=window.name
	    if(tabId==''|| tabId==null){
			tabId="iframe_myJob";
		 }
	    var rolesIds=$("#rolesIds").val();
		var content='<iframe id="roles_dialog" style="margin-left:-10px;margin-top:-10px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+"?tabId="+tabId+"&rolesIds="+rolesIds+'"></iframe>';
		var title='<g:message code='user.roles.label' args='[entityName1]'/>';
		//打开弹出框
		parent.showDialog(1,content,title,600,567);
}
	
	/**
	*弹出框回调事件
	*/
	function getPosts(postShowName,postIds){
		 $("#postsName").val(postShowName);
		 $("#postsIds").val(postIds);
	}
	function getOrgans(organShowName,organIds){
		 $("#organsName").val(organShowName);
		 $("#organsIds").val(organIds);
	}
	function getRoles(roleShowName,roleIds){
		 $("#rolesName").val(roleShowName);
		 $("#rolesIds").val(roleIds);
	}
	//打开详细信息
	function manageDetail(){
		var status=$("#showDetail").val();
		//未显示详细信息时，点击后就显示详细信息
		if(status==0){
			$("#showDetail").val(1);
			$("#userDetail").attr('style','');
			$("#manageDetail").attr('style',"cursor:pointer;background:url('${resource(dir:'images/',file:'parent/expand.png')}') no-repeat  -16px 4px #ccc;height:20px;width:100%; line-height:20px;");
		}else{
			$("#showDetail").val(0);
			$("#userDetail").attr('style','display:none');
			$("#manageDetail").attr('style',"cursor:pointer;background: url('${resource(dir:'images/',file:'parent/expand.png')}') no-repeat scroll -16px -15px transparent; background-color :#ccc;height:20px;width:100%; line-height:20px;");
		}
	}
//-->
</script>

