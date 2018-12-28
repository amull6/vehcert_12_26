<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'insideNote.label', default: 'InsideNote')}" />
	</head>
	<body>
		<div id="create-insideNote" class="content scaffold-create" role="main">
			<form id="form" method="post">
				<fieldset class="form">
<%--				<div class="fieldcontain ${hasErrors(bean: insideNoteInstance, field: 'title', 'error')} ">--%>
<%--					<label for="title">--%>
<%--						<g:message code="insideNote.title.label" default="title" />--%>
<%--						--%>
<%--					</label>--%>
<%--					<g:textField id="title" name="title" style="width:300px;height:20px;" value="${insideNoteInstance?.title}"/>--%>
<%--				</div>--%>
				
				
				<div class="fieldcontain ${hasErrors(bean: insideNoteInstance, field: 'receiverName', 'error')} ">
					<label for="receiverName">
						<g:message code="insideNote.receiverName.label" default="Receiver Name" />
						
					</label>
					<g:textArea  type="text" name="receiverName" style="width:300px;height:20px;" id="receiverName" value="${insideNoteInstance?.receiverName}" readOnly="true"/>
					<input type="hidden" name='receiverId' id="receiverId" value="${insideNoteInstance?.receiverId}">
				</div>
				
				
				<div class="fieldcontain ${hasErrors(bean: insideNoteInstance, field: 'content', 'error')} required">
					<label for="content">
						<g:message code="insideNote.content.label" default="Content" />
						<span class="required-indicator">*</span>
					</label>
					<g:textArea id="content" name="content" style="width:300px;height:200px;"maxlenght="200" value="${insideNoteInstance?.content}"/>
				</div>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:120px;">
					<input id="btn_reply" type="button"  class="import" value="${message(code: 'default.button.reply.label', default: 'Reply')}"/>
					<input id="btn_close" type="button"  class="delete" value="${message(code: 'default.button.close.label', default: 'Close')}"/>
				</fieldset>
			</form>
		</div>
		
		
		<script type="text/javascript">
		$(function() {
			$("#btn_reply").click(function(){
<%--				var title=$("#title").val();--%>
				var receiverName=$("#receiverName").val();
				var receiverId=$("#receiverId").val();
				var content=$("#content").val();
				if(content.length==0){
						alert("内容不能为空！");
					}else{
						var url = '${createLink(controller:'InsideNote',action:'reply')}';
						$.post(url,{"receiverName":receiverName,"receiverId":receiverId,"content":content},function(data,textStatus){
							$.omMessageBox.alert({
				                title:'回复',
				                content:data,
				                onClose:function(value){
				                	$("#btn_close").click();
				                }
				            });
		                },'text');
						}
				
			});
			$("#btn_close").click(function(){
				parent.closeDialog(window.parent.document.getElementById("reply_dialog").parentNode.id);
			});
		});
		</script>
	</body>
</html>
