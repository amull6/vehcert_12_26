<%@ page import="cn.com.wz.parent.system.user.User" %>
<%@ page import="cn.com.wz.parent.system.user.UserDetail" %>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'import.label', default: 'Import Page')}" />
		<title><g:message code="defaul.importUrl.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="edit-user" class="content scaffold-edit" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:form method="post" >
				<fieldset class="form">
					<h1><g:message code="user.search.label" default="search" /></h1>
					<div>
						<label for="createTime">
							<span style="width:90px; float:left;"><g:message code="user.inCompanyTime.label" default="inCompanyTime" />：</span>
						</label>
						<c:dataPicker name="createTime" id="createTime" dateFormat='yy-mm-dd' value="${user?.createTime}"></c:dataPicker>到
						<c:dataPicker name="createTimeTo" id="createTimeTo" dateFormat='yy-mm-dd' value="${user?.createTime}"></c:dataPicker>
					</div>
					<br/>
					<div>
						<label for="jobLevel">
							<span style="width:90px; float:left;"><g:message code="user.jobLevel.label" default="Job Level" />：</span>
						</label>
						<g:select id="jobLevel" name="jobLevel.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
							dictionaryType{eq('code','jobLevel') }
							order("ordernum","desc")
							})}" optionKey="id" required="" value="${user?.userDetail?.jobLevel?.id}"  class="one-to-one" />
					</div>
					<br/>
					<div>
						<label for="organs">
							<span style="width:90px; float:left;"><g:message code="user.organsExport.label" default="Organ" />：</span>
						</label>
						<g:set var="organs" value="" />
						<g:each in="${user?.organs }">
							<g:set var="organs" value="${organs=organs+it.organNameC+' '}" />
						</g:each>
						<g:textArea id="organsName"  name="organsName" style="width:150px;height:50px;"  value="${organs}" readonly="true"/>
					</div>
					<br/>
					<div>
						<label for="posts">
							<span style="width:90px; float:left;"><g:message code="user.postsExport.label" default="posts" />：</span>
						</label>
						<g:set var="posts" value="" />
						<g:each in="${user?.posts }">
							<g:set var="posts" value="${posts=posts+it.postNameC+' '}" />
						</g:each>
						<g:textArea id="postsName"  name="postsName" style="width:150px;height:50px;"  value="${posts}" readonly="true"/>
					</div>
					<div class="fieldcontain ${hasErrors(bean: organsIds, field: 'organsIds', 'error')} ">
	   					 <input type="hidden" name='organsIds' id="organsIds" value="${organsIds}" >
					</div>
					<div class="fieldcontain ${hasErrors(bean: postsIds, field: 'postsIds', 'error')} ">
	    				<input type="hidden" name='postsIds' id="postsIds" value="${postsIds}" >
					</div>
				</fieldset>

				<fieldset class="buttons">
					<input type="hidden" name='format' id="fomat" value="${format}"/>
					<input type="hidden" name='extension' id="extension" value="${extension}"/>
					<input id="save" type="button"  class="save" value="${message(code: 'default.button.define.label', default: 'Define')}"/>
					<input id="cancel" type="button" class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}">
				</fieldset>
			</g:form>
		</div>
		<script>
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
		   	//确定按钮事件
			   	 $("#save").click(function(){
				   	 var createTime=$('#createTime').val();
				   	var createTimeTo=$('#createTimeTo').val();
				   	var organsIds=$("#organsIds").val();
				   	var postsIds=$("#postsIds").val();
				   	var format=$("#fomat").val();
				   	var extension=$("#extension").val();
				   	var jobLevel=$("#jobLevel").val();
				   	 var url='${createLink(controller:'User',action:'export')}?format='+format+'&extension='+extension+'&createTime='+createTime+'&organsIds='+organsIds+'&createTimeTo='+createTimeTo+'&postsIds='+postsIds+'&jobLevel='+jobLevel;
				   	 window.location.href=url;
				 });
			   	$("#cancel").click(function(){
				   	parent.closeDialog(window.parent.document.getElementById("wangtao_dialog").parentNode.id);
				 });
			});
			
		//弹出用户选择窗口，并生成组织表格信息
			function showModelDialog2(url){
				var tabId="wangtao_dialog";
				window.name = tabId
                console.log(tabId)
			   	 var organsIds=$("#organsIds").val();
				var content='<iframe id="organs_dialog" style="margin-left:-5px;margin-top:-10px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+"?tabId="+tabId+"&organsIds="+organsIds+'"></iframe>';
				var title='<g:message code='user.organ.label' args='[entityName1]'/>';
					//打开弹出框
					parent.showDialog(1,content,title,600,567);
			}
			
			//弹出用户选择窗口，并生成岗位表格信息
			function showModelDialog(url){
					var tabId="wangtao_dialog";
                    window.name = tabId
				    var postsIds=$("#postsIds").val()
					var content='<iframe id="posts_dialog" style="margin-left:-10px;margin-top:-10px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+"?tabId="+tabId+"&postsIds="+postsIds+'"></iframe>';
					var title='<g:message code='user.post.label' args='[entityName1]'/>';
					//打开弹出框
					parent.showDialog(1,content,title,600,567);
			}
			function getOrgans(organShowName,organIds){
				 $("#organsName").val(organShowName);
				 $("#organsIds").val(organIds);
			}
			function getPosts(postShowName,postIds){
				 $("#postsName").val(postShowName);
				 $("#postsIds").val(postIds);
			}
		</script>