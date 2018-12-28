<%@ page import="cn.com.wz.parent.system.role.Role" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'role')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-role" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="edit-role" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${roleInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${roleInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<form  id="form2"  method="post" >
				<g:hiddenField name="id" value="${roleInstance?.id}" />
				<g:hiddenField name="version" value="${roleInstance?.version}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/system/role/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
                    <input id="btn_save" type="button" class="create" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
					<g:link  class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>

				</fieldset>
			</form>
		</div>
		<script type="text/javascript">
		$(".cancel").click(function(){
			var currentCategoryId=$('#currentCategoryId').val();
			$('.cancel').attr('href','javascript:void(0);');
			 $.omMessageBox.confirm({
	                title:'确认',
	                content:'您确定要离开此页面吗？',
	                onClose:function(value){
		                if(value){
		                	window.location.href="${createLink(controller:'role',action:'index')}";
			              }
	                }
	            });
		});
        //页面加载时执行数据回填
        $(function() {
            //checkBox回填
            var checkIds="${checkIds}";
            if(checkIds!=''&&checkIds!=null){
                var codes=checkIds.split(':_:');
                for(var i=0;i<codes.length;i++){
                    $("input[name="+codes[i]+"]").attr("checked",true);
                }
            }
        });

        $("#btn_save").click(function(){
            //获取系统消息提醒选中checkBox的ids
            var checks=$("input:checkbox[mmm='checkbox1']:checked");
            var checkIds='';
            if(checks!=null&&checks!=''){
                $.each(checks,function(i,it){
                    checkIds+=it.value+':_:';
                });
            }else{
                checkIds='noChoose'
            }
            $("#checkIds").val(checkIds);
            $("#form2").attr('action','${createLink(controller:'role',action:'update')}');
            $("#form2").submit();
        });
		</script>
	</body>
</html>