
<%@ page import="cn.com.wz.parent.system.Menu" %>
<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 				<fieldset class="buttons" style="margin:8px 8px 8px 4px;">
					<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
					<input id="btn_create_sub" type="button" class="create" value="${message(code: 'menu.button.createSub.label', default: 'Create Submenu')}"/>
					<input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
<%--					<input id="btn_import" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}"/>--%>
<%--					<input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>--%>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<input type="hidden" id="menuItemId">
					<div id="content" style="border:0px"></div>
				</div> 
	    	</div>
	    	<ul id="west-panel"></ul>
    	</div>
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
                $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'menu',action:'create')}';
                	   var selected = $('#west-panel').omTree('getSelected');
                	   var parent = $('#west-panel').omTree('getParent',selected);
                	   if(parent==null){
                		   window.location.href = url;
                   	   }
                	   window.location.href = url+'?parent.id='+parent.id;
                });
                $('#btn_create_sub').click(function(){
	             	   var url = '${createLink(controller:'menu',action:'create')}';
    	               var selected = $('#west-panel').omTree('getSelected');
	             	   window.location.href = url+'?parent.id='+selected.id;
      			});
                $('#btn_edit').click(function(){
             		   var url = '${createLink(controller:'menu',action:'edit')}';
             		   var selected = $('#west-panel').omTree('getSelected');
                	   window.location.href = url+'/'+selected.id;
                });
                $('#btn_delete').click(function(){
                	$.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'${message(code: 'menu.button.delete.confirm.message', default: 'Are you sure?')}',
                        onClose:function(v){
                            if(v){
                            	var selected = $('#west-panel').omTree('getSelected');
                           		var parent = $('#west-panel').omTree('getParent',selected);
                           		var deleteUrl = '${createLink(controller:'menu',action:'jsonDelete')}/'+selected.id;
        	                   	$.post(deleteUrl,{},function(data,textStatus){
        	                   		$('#west-panel').omTree('refresh');
        	                   		if(parent!=null){
        	                   			$('#west-panel').omTree('select',parent);
        			                }else{
        			                	$('#center-panel').css({visibility:'hidden'});
        				            }
        	                   		showTopTip(data);		                   	
        		                },'text');
                            }
                        }
                    });
               	});
                $('#btn_export').click(function(){
	                   //导出功能
	                   alert('未添加该功能');
                });
                $('#btn_import').click(function(){
	                   //导入功能
             	   alert('未添加该功能');
                });
			});
			// 加载时执行的语句
			$(function() {
				setTimeout('inOldPage();',500);
				$('#page').css({height:$(document).height()-16});
				//布局
				$('#page').omBorderLayout({
					panels:[{
	           	        id:"center-panel",
	           	     	header:false,
	           	        region:"center"
	           	    },{
	           	        id:"west-panel",
	           	        title:"<g:message code="default.list.label" args="[entityName]" />",
	           	        region:"west",
	           	        width:200
	           	    }]
	            });
				//构建控件
			    $('#west-panel').omTree({
			         dataSource : "${createLink(controller:'menu',action:'jsonAllList')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       		  $('#center-panel').css({visibility:'visible'});
			       		  $('menuItemId').val(node.id);
		                  $('#content').load("${createLink(controller:'menu',action:'jsonShow')}/"+node.id);
		                  if(node.children==null){

                          }else{
                        	  if(!node.isExpand){
                           		node.isExpand='0';
                           		}
                              if(node.isExpand=='0'){
                                  $('#west-panel').omTree('expand',node);
                                  node.isExpand=1;
                              }else{
                                  $('#west-panel').omTree('collapse',node);
                                  node.isExpand=0;
                              }
                          }
		             }
			    });
			});
			//用户自定义方法
			function haojia(value,event) {
			}
		    function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 3000
		        });
		    }
		  //从详情页面返回后，进入原来页面
		    function inOldPage(){
		    var nodeText="${params.currentCategoryId}";
		    var target = $('#west-panel').omTree("findNode", "id", nodeText);
		    $('#west-panel').omTree('select',target);
		    var parent=$('#west-panel').omTree('getParent',target);
		    $('#west-panel').omTree('expand',parent);
		    }
		    		    
 	 	</script>
	</body>
</html>
