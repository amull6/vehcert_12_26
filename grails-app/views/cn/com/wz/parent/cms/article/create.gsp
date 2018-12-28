<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<script src="${resource(dir:"js",file:"parent/DivDialog.js") }" type="text/javascript" ></script>
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-article" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="create-article" class="content scaffold-create" role="main">
			<h1><g:message code="default.new.label" args="[entityName]" /></h1>
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
			<form id="creatForm">
			<g:hiddenField name="selectOwn" id="selectOwn" value="${selectOwn}" />
			<g:hiddenField name="currentCategoryId" id="currentCategoryId" value="${articleInstance?.articleCategory?.id}" />
				<fieldset class="form">
					<g:render template="/cn/com/wz/parent/cms/article/form"/>
				</fieldset>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
                    <input id="view" type="button"  class="import" value="预览"/>
                    <input type="button" id="btn_save" name="save" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
                    <input type="button" class="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
				</fieldset>
			</form>
		</div>
		<script type="text/javascript">
		$(".cancel").click(function(){
			var currentCategoryId=$('#currentCategoryId').val();
			var selectOwn=$('#selectOwn').val();
			$('.cancel').attr('href','javascript:void(0);');
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
		$("#btn_save").click(function(){
			var content=CKEDITOR.instances.contentC.getData();
            var descrip=CKEDITOR.instances.description.getData();
			if($("#titleC").val()&&content){
				var url="${createLink(controller:'article',action:'save')}";
					url+='?'+$('#creatForm').serialize();
					$.post(url,{"content":content,descrip:descrip},function(data,textStatus){
						if(textStatus){
								window.location.href="${createLink(controller:'article',action:'show')}?id="+data.id+"&selectOwn="+data.selectOwn;
							}
					});
			}else{
				alert('文章标题和文章内容不能为空！');
			}
		});
		$("#view").click(function(){
			var content=CKEDITOR.instances.contentC.getData();
			if($("#titleC").val()&&content){
				var url="${createLink(controller:'article',action:'view')}";
					url+='?'+$('#creatForm').serialize();
				$.post(url,{"content":content},function(data,textStatus){
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
