
<%@ page import="cn.com.wz.parent.system.user.User" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>

	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80"><span><g:message code="user.userName.label" default="User Name" />：</span></td>
					    <td width="100"><span><g:textField id="tf_userName" name="userName" maxlength="200" value="${user?.userName}"/></span></td>
					    <td width="60"></td>
					    <td width="100"><span><g:message code="user.realName.label" default="RealName" />：</span></td>
					    <td width="100"><span><g:textField id="tf_realName" name="realName" maxlength="200" value="${user?.tempName}"/></span></td>
					    <td width="60"></td>
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
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" />
					<input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
					<input id="btn_resetPassword" type="button"  class="reset" value="${message(code: 'default.button.resetPassword.label', default: 'Reset Password')}"/>
					<input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
					<export:formats  formats="['excel','pdf']"    style="border:0px;margin-left:325px;margin-top:-23px;"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="user_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
	 	$(function() {
			   $('#page').css({height:$(document).height()-16});
			 //绑定查询按钮事件
			 $(".pdf").click(function(){
				 $('.pdf').attr('href','javascript:void(0);');
				 var url="${createLink(controller:'User',action:'exportPage')}?format=pdf&extension=pdf";
				 showExport(url);
			});
			 $(".excel").click(function(){
				 $('.excel').attr('href','javascript:void(0);');
				 var url="${createLink(controller:'User',action:'exportPage')}?format=excel&extension=xls";
				showExport(url);
			});
			 
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
		   	                    var url = "${createLink(controller:'User',action:'jsonList')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#user_grid').omGrid('setData',url);
		   	           }
                });
               $('#btn_clear').omButton({
	              	width:80,
	           	   onClick:function(){
		   	               $("#tf_userName").val('');
		   	               $("#tf_realName").val('');    
		   	       }
	           });
               $("#tf_userName").keydown(function(e){
 				  if(e.keyCode==13){
 					 $('#btn_query').click();
 					  return false;
 					}
 		   		});
               $("#tf_realName").keydown(function(e){
 					 if(e.keyCode==13){
 						 $('#btn_query').click();
 						  return false;
 					}
 			   });
               $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'User',action:'create')}';
                	   var selected = $('#user_grid').omTree('getSelected');
                	   window.location.href = url;
               });
               $('#btn_edit').click(function(){
            		   var url = '${createLink(controller:'User',action:'edit')}';
            		   var selected = $('#user_grid').omGrid('getSelections',true);
            		   if(selected.length==0){
							alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
                	   }else if(selected.length>1){
	                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
                	   }else{
	                	   window.location.href = url+'/'+selected[0].id;
		               }
               });
               $('#btn_resetPassword').click(function(){
              		//这里利用Ajax删除数据
              		var selections = $('#user_grid').omGrid('getSelections',true);
              		if(selections.length==0){
                  		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                  		return;	
                  	}
              		$.omMessageBox.confirm({
                       title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                       content:'${message(code: 'default.button.resetPassword.confirm.message', default: 'Are you sure to reset password?')}',
                       onClose:function(v){
                           if(v){
                           	var resetUrl = '${createLink(controller:'User',action:'resetPassword')}';
   	                   		var ids = '';
   	                   		$.each(selections,function(i,item){
   		                   		ids += item.id+'_';
   		                   	});
   		                   	$.post(resetUrl,{"ids":ids},function(data,textStatus){
   		                   		var selections = $('#user_grid').omGrid('reload');
   		                   		alert(data);
   		                   			                   	
   			                },'text');
                           }
                       }
                   });
             	});

               $('#btn_delete').click(function(){
                   		//这里利用Ajax删除数据
                   		var selections = $('#user_grid').omGrid('getSelections',true);
                   		if(selections.length==0){
	                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                   		return;	
	                   	}
                   		$.omMessageBox.confirm({
                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                            onClose:function(v){
                                if(v){
                                	var deleteUrl = '${createLink(controller:'User',action:'jsonDelete')}';
        	                   		var deleteIds = '';
        	                   		$.each(selections,function(i,item){
        		                   		deleteIds += item.id+'_';
        		                   	});
        		                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
        		                   		var selections = $('#user_grid').omGrid('reload');
        		                   		var content = data;
        		                   		/**
        		                   		$.each(data,function(i,item){
        		                   			content += "<div class='message'>"+item+"</div>";
        			                   	});**/
        		                   		showTopTip(content);		                   	
        			                },'text');
                                }
                            }
                        });
                  	});
               $('#btn_view').click(function(){
            	   var url = '${createLink(controller:'User',action:'show')}';
        		   var selected = $('#user_grid').omGrid('getSelections',true);
        		   if(selected.length==0){
						alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            	   }else if(selected.length>1){
                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            	   }else{
                	   window.location.href = url+'/'+selected[0].id;
	               }
               });
            
               $('#btn_imp').click(function(){
           	  	var url='${createLink(controller:'User',action:'importPage')}';
           	  	showModelDialog(url);
          	});
	 	})
		function showModelDialog(url){
            var tabId=window.name;
            url+="?tabId="+tabId;
			var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
			var title='文件导入';
			//打开弹出框
			parent.showDialog(1,content,title,550,250);
		}
	 	function showExport(url){
             var tabId=window.name;
             url+="&tabId="+tabId;
             console.log(tabId)
			var content='<iframe id="wangtao_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
			var title='文件导出';
			//打开弹出框
			parent.showDialog(1,content,title,750,450);
		}
	 	function viewDetail(index){

        	var data = $("#user_grid").omGrid("getData").rows[index];
        	var url = '${createLink(controller:'User',action:'show')}';
         	   window.location.href = url+'/'+data.id;
        }
	    // 加载时执行的语句
		$(function() {
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
	          //构建左侧树
	          //buildLeftTree();
	          buildRightGrid();
           });
		function buildRightGrid(value,event){
			$('#user_grid').omGrid({
				dataSource:'${createLink(controller:'User',action:'jsonList')}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, // 分页显示，每页显示30条
				height : 440,
				colModel:[
				          {header : "${message(code: 'user.realName.label', default: 'Real Name')}", name : 'tempName', width : 80,align : 'left',
								renderer:function(value,rowData,rowIndex){
									return  '<a id="btn_view" class="btn_view" href="javascript:void(0);" onClick="viewDetail('+rowIndex+')">'+ value+'</a>';
						  			}
		       				  }, 
							{header : "${message(code: 'user.userName.label', default: 'User Name')}", name : 'userName', width : 120, align : 'left'},
							{header : "${message(code: 'user.organization.label', default: 'Organization')}", name : 'organNames', width : 120, align : 'center' },
							{header : "${message(code: 'user.posts.label', default: 'postsNames')}", name : 'postsNames', width : 120, align : 'center' },
							{header : "${message(code: 'user.jobLevel.label', default: 'jobLevel')}", name : 'jobLevel', width : 120, align : 'center' },
						  {header : "${message(code: 'user.userType.label', default: 'User Type')}", name : 'userTypeName', width : 100, align : 'center' },
						  {header : "${message(code: 'user.status.label', default: 'Status')}", name : 'status', width : 100, align : 'center',
                        	  renderer:function(value,rowData,rowIndex){
								if(value==0){
									return '未启用';
								}else if(value==1){
									return '已启用';
								}else{
									return '参数错误'
								}
							}

                           },
                           {header : "${message(code: 'user.validStartTime.label', default: 'validStartTime')}", name : 'validStartTime', width : 150,align : 'center'}, 
 						  {header : "${message(code: 'user.validEndTime.label', default: 'validEndTime')}", name : 'validEndTime', width : 150, align : 'center' },
                          {header : "${message(code: 'user.lastLoginTime.label', default: 'Last Login Time')}", name : 'lastLoginTime', width : 150, align : 'center'}]
			});
		}
		function showTopTip(content){
	        $.omMessageTip.show({
	            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
	            content : content,
	            timeout : 6000
	        });
	    }
         /**
            *关闭导入弹出框后执行的函数 必须保留
            * @param returnData
          */
         function backFunc(returnData){

         }
    </script>
	</body>
</html>
