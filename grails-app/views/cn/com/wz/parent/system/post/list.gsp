<%@ page import="cn.com.wz.parent.system.post.Post" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'post.label', default: 'post')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
			<form id="form_query" style="margin:8px;height:40px;">
			<table style="width:100%;border:0px;">
				  <tr>
				    <td width="80"><span><g:message code="post.postNameC.label" default="Post Name" />：</span></td>
				    <td width="100"><span><g:textField id="postNameC" name="postNameC" maxlength="200" value="${postInstance?.postNameC}"/></span></td>
				    <td width="20"></td>
				    <td width="80"><span><g:message code="post.code.label" default="Code" />：</span></td>
				    <td width="100"><span><g:textField id="postCode" name="postCode" maxlength="200" /></span></td>
				    <td width="20"></td>
				    <td width="80" align="right"><span><g:message code="post.postType.label" default="Post Type" />：</span></td>
					    <td width="100">
					    	<span>
					    		<g:select id="postType" name="postType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','postType')}})}"  optionKey="id" noSelection="['':'-全部-']" class="many-to-one"/>
					    	</span>
					    </td>
				    <td align="left" valign="middle">
				    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				    </td>
				  </tr>
				</table>
			</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
					<input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
						<input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="post_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
			// 事件绑定
			$(function() {
					   $('#page').css({height:$(document).height()-16});
	                  //绑定查询按钮事件
	                   $('#btn_query').omButton({
		                   		width:80,
		                	   onClick:function(){
				   	                    var url = "${createLink(controller:'post',action:'search')}";
				   	                    url+='?'+$('#form_query').serialize();
				   	                    $('#post_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $('#btn_clear').omButton({
	                       width:80,
	                   	   onClick:function(){
	        	   	           $("#postNameC").val('');
	        	   	        	$("#postCode").val('');
	        	           }
	                    });
	                   $("#postNameC").keydown(function(e){
	      				  if(e.keyCode==13){
	      					  $("#postCode").focus();
	      					  return false;
	      					}
	      		   		});
	                    $("#postCode").keydown(function(e){
	      					 if(e.keyCode==13){
	      						$('#postType').focus();
	      						  return false;
	      					}
	      			   });
	                    $("#postType").keydown(function(e){
	      					 if(e.keyCode==13){
	      						$('#btn_query').click();
	      						  return false;
	      					}
	      			   });
	                    
	                   //创建按钮
	                   $('#btn_create').click(function(){
		                	   var url = '${createLink(controller:'post',action:'create')}';
		                	   window.location.href = url;
	                   });
	                   //更新按钮
	                   $('#btn_edit').click(function(){
	                		   var url = '${createLink(controller:'post',action:'edit')}';
	                		   var selected = $('#post_grid').omGrid('getSelections',true);
	                		   if(selected.length==0){
									alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
		                	   }else if(selected.length>1){
			                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
		                	   }else{
			                	   window.location.href = url+'?id='+selected[0].id;
				               }
	                   });
	                   //删除按钮
	                   $('#btn_delete').click(function(){
		                   		//这里利用Ajax删除数据
		                   		var selections = $('#post_grid').omGrid('getSelections',true);
		                   		if(selections.length==0){
			                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
			                   		return;	
			                   	}
		                   		$.omMessageBox.confirm({
		                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
		                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
		                            onClose:function(v){
		                                if(v){
		                                	var deleteUrl = '${createLink(controller:'post',action:'jsonDelete')}';
					                   		var deleteIds = '';
					                   		$.each(selections,function(i,item){
						                   		deleteIds += item.id+'_';
						                   	});
						                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
						                   		var selections = $('#post_grid').omGrid('reload');
						                   		var content = "<div class='message'>"+data+"</div>";
						                   		showTopTip(content);		                   	
							                },'text');
		                                }
		                            }
		                        });
			                   	
		                  	});
	                   $('#btn_imp').click(function(){
	                  	  	var url='${createLink(controller:'Post',action:'importPage')}';
	                  	  	showModelDialog(url);
	                 	});
	       	 	})
	       		function showModelDialog(url){
	       			var content='<iframe id="receiver_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="no" src="'+url+'"></iframe>';
	       			var title='文件导入';
	       			//打开弹出框
	       			parent.showDialog(1,content,title,550,250);
	       		}
			// 加载时执行的语句
			$(function() {
				buildRightGrid()
	           });
			//用户自定义方法
			//创建有些表格
			function buildRightGrid(){
				$('#post_grid').omGrid({
					dataSource:'${createLink(controller:'post',action:'search')}?'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 12,
					height :428,
					colModel:[{header : "${message(code: 'post.postNameC.label', default: 'Post NameC')}", name : 'postNameC', width : 150, align : 'center'},
		                      {header : "${message(code: 'post.code.label', default: 'Code')}", name : 'code', width : 150, align : 'center'},
		                      {header : "${message(code: 'post.postTypeName.label', default: 'Post Tyepe')}", name : 'postTypeName', width : 100, align : 'center'},
							  {header : "${message(code: 'post.createTime.label', default: 'Create Time')}", name : 'createTime', width : 200, align : 'center'},
		                      {header : "${message(code: 'post.lastUpdateTime.label', default: 'Last Update Time')}", name : 'lastUpdateTime', width :200, align : 'center'}]
				});
			}
						
		 </script>
	</body>
</html>