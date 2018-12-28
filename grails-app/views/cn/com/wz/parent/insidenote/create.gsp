<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'insideNote.label', default: 'InsideNote')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-insideNote" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="create-insideNote" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${insideNoteInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${insideNoteInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<form id="form" method="post">
				<fieldset class="form">
				<input type='hidden' name='activityId'  id='activityId' value='${activityId}'/>
				<input type='hidden' name='nodeType' id='nodeType' value='${nodeType}'/>
					<g:render template="/cn/com/wz/parent/insidenote/form"/>
				</fieldset>
				<fieldset class="buttons"  style="margin:20px 8px 0px 4px;padding-left:280px;">
					<input type="button" id="btn_send" name="send" class="create"  value="${message(code: 'default.button.send.label', default: 'send')}" />
					<g:if test="${params.nodeType=="online"||params.nodeType=="activity"||params.nodeType=="sysonline"||params.nodeType=="activityList"}">
					<g:if test="${params.nodeType=="online" }">
					<input type="button" id='loguserInsidenote' class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
<%--					<g:link id='loguserInsidenote' class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>--%>
					</g:if>
                    <g:if test="${params.nodeType=="sysonline" }">
                        <input type="button" id='sysloguserInsidenote' class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
                    <%--					<g:link id='loguserInsidenote' class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>--%>
                    </g:if>
					<g:if test="${params.nodeType=="activity" }">
					<input type="button" id='activityInsidenote' class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
<%--					<g:link id='activityInsidenote' controller="Register" params='[id:"${activityId}"]' action="list" class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>--%>
					</g:if>
					<g:if test="${params.nodeType=="activityList" }">
					<input type="button" id='activityList' class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
					<%--					<g:link id='activityInsidenote' controller="Register" params='[id:"${activityId}"]' action="list" class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>--%>
					</g:if>
					</g:if>
					
					<g:else>
					<input type="button" id="btn_save" name="save" class="edit" value="${message(code: 'shortMessage.button.save.label', default: 'save')}" />
					<input type="button" id='insidenote' class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
					</g:else>
					
				</fieldset>
			</form>
		</div>
		
		
		<script type="text/javascript">
		$(function() {
			$("#btn_send").click(function(){
				var receiverName=$('#span_selectedUsers').html();
				var content=$('#content').val();
				if(receiverName==''){
					alert('请选择联系人！');
					return;
				}
				if(content==''){
					alert('请填写内容！');
					return;
				}
				 $('#form').attr("action","${createLink(controller:'insideNote',action:'send')}?opFlag=0&nodeType=${nodeType}&activityId=${activityId}");
				 $("#form").submit();
				 //活动取消操作
				 var nodeType=$('#nodeType').val();
				 var activityId=$('#activityId').val();
				 var url= '${createLink(controller:'Activity',action:'changeStats')}?stats=2';
				 if(nodeType=='activityList'){
					 $.post(url,{"id":activityId},function(data,textStatus){		                   	
                   	},'text');
				}
			});
			$("#btn_save").click(function(){
				var receiverName=$('#span_selectedUsers').html();
				var content=$('#content').val();
				if(receiverName==''){
					alert('请选择联系人！');
					return;
				}
				if(content==''){
					alert('请填写内容！');
					return;
				}
				$('#form').attr("action","${createLink(controller:'insideNote',action:'save')}");
				$("#form").submit();
			});
		});
		$("#insidenote").click(function(){
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'insideNote',action:'index')}";
			              }
	                }
	            });
		});
		$("#loguserInsidenote").click(function(){
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'LogUser',action:'index')}";
			              }
	                }
	            });
		});
        $("#sysloguserInsidenote").click(function(){
            $.omMessageBox.confirm({
                title:'确认',
                content:'您确定要离开此页面吗？',
                onClose:function(value){
                    if(value){
                        window.location.href="${createLink(controller:'LogUser',action:'sysControl')}";
                    }
                }
            });
        });
		$("#activityInsidenote").click(function(){
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'Activity',action:'RegisterList')}?id=${activityId}";
			              }
	                }
	            });
		});
		$("#activityList").click(function(){
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'Activity',action:'index')}";
			              }
	                }
	            });
		});
		</script>
	</body>
</html>
