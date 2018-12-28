<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#edit-article" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="edit-article" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${articleInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${articleInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<form id="form_update" method="post" action="${createLink(controller:'article',action:'update')}">
				<g:hiddenField name="id" value="${articleInstance?.id}" />
				<g:hiddenField name="version" value="${articleInstance?.version}" />
				<g:hiddenField name="selectOwn" value="${selectOwn}" />
				<g:hiddenField name="currentCategoryId" id="currentCategoryId" value="${articleInstance?.articleCategory?.id}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/cms/article/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
                    <input id="view" type="button"  class="import" value="预览"/>
					<input type="button" class="save" id="btn_update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<input type="button" id="btn_cancel" class="cancel" value="${message(code: 'default.button.cancel.label', default: 'cancel')}" />
				</fieldset>
			</form>
		</div>
			<script type="text/javascript">
                $("#btn_update").click(function(){
                    $("#form_update").submit();
                });
                $("#btn_cancel").click(function(){
                    var currentCategoryId=$('#currentCategoryId').val();
                    var selectOwn=$('#selectOwn').val();
                     $.omMessageBox.confirm({
                            title:'确认',
                            content:'您确定要离开此页面吗？',
                            onClose:function(value){
                                if(value){
                                    window.location.href="${createLink(controller:'article',action:'index')}?currentCategoryId="+currentCategoryId+"&selectOwn="+selectOwn;
                                  }
                            }
                        });
                });
                $("#view").click(function(){
                    var content=CKEDITOR.instances.contentC.getData();
                    if($("#titleC").val()&&content){
                        var url="${createLink(controller:'article',action:'view')}";
//                        url+='?'+$('#form_update').serialize();
                        $.post(url,{"content":content,titleC:$("#titleC").val(),picture_uploadFileNames:$("#picture_uploadFileNames").val(),accessorys_uploadFileNames:$("#accessorys_uploadFileNames").val(),"articleCategory.id":$("#articleCategory_hidden").val()},function(data,textStatus){
                            var showUrl="${createLink(controller:'article',action:'frontShow')}?id="+data.id;
                            var contentF='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
                                    'margin-right:-50px;text-align:center; width:100%;height:100%" '+
                                    'scrolling="yes" src="'+showUrl+'"></iframe>';
                            var title='文章预览';
                            var delUrl="${createLink(controller:'article',action:'jsonDelete')}?deleteIds="+data.id;
                            //打开弹出框
                            parent.showDialogForIndex('div_wrap',1,contentF,title,1100,0,delUrl);
                        });
                    }else{
                        alert('文章标题和文章内容不能为空！');
                    }
                });
		</script>
	</body>
</html>
