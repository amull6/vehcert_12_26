<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="WuZheng"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'parent/favicon.ico')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/main.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/export.css')}" type="text/css">
		<r:require modules="om" />
		<r:require module="fileuploader" />
		<g:layoutHead/>
		<r:layoutResources />
		<ckeditor:resources/>
		<uploader:head css="${resource(dir:'css',file:'parent/upload.css') }"></uploader:head>
	</head>
	<body>
		
		<g:layoutBody/>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<g:javascript>
			//调整Iframe的大小
			function fitToContent(){
				var mainheight = $(this).contents().find("body").height()+30;
				$(this).height(mainheight);
			}
			//重载alert方法
			function alert(content){
				 $.omMessageBox.alert({
				   title:'${message(code:'default.tipDialog.title.label',default:'Tip Dialog') }',
		           content:content,
		           onClose:function(v){

			       }
			     });
				
			}
	        function showTipDialog(){
                var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
                var title='系统提示';
                //打开弹出框
                var id=parent.showDialog(1,content,title,160,80);
                return id;
            }

            //退出系统,进入系统登陆页面
            function exitSystem(){
                $.omMessageBox.alert({
				   title:'${message(code:'default.tipDialog.title.label',default:'Tip Dialog') }',
		           content:'${message(code: 'default.exitSystem.message', default: '你已退出系统，请重新登陆！')}',
		           onClose:function(v){
                        //window.open("","_self");
                        //window.close();
                        window.location.href="${request.contextPath}/";
			       }
			     });
            }
		</g:javascript>
        <r:layoutResources />
        
       
	</body>
</html>