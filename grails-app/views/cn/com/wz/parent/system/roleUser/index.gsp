
<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'roleUser.label', default: 'Role User')}" />
			<g:set var="entityName1" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 				
 				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80" align="right"><span><g:message code="user.userName.label" default="User Name" />：</span></td>
					    <td width="100"  align="left"><span><g:textField id="userName" name="userName" maxlength="200" /></span></td>
					    <td width="100" align="right"><span><g:message code="user.realName.label" default="realName" />：</span></td>
					    <td width="100" align="left"><span><g:textField id="realName" name="realName" maxlength="200"/></span></td>
					    <td align="center" valign="middle">
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
				       <input type="hidden" id="roleId" name="roleId"/>
				</form>
 				
 				<fieldset class="buttons" style="margin:8px 8px 8px 4px;">
					<input id="btn_add" type="button" class="create" value="${message(code: 'roleUser.add.label', default: 'AddUser')}"/>
					<input id="btn_delete" type="button" class="delete" value="${message(code: 'roleUser.delete.label', default: 'DeleteUser')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="role_content" style="border:0px"></div>
				</div> 
	    	</div>
	    	<ul id="west-panel"></ul>
    	</div>
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
                		   buildRightGrid(1);
		   	                  }
                });
             //绑定清除按钮事件
              $('#btn_clear').omButton({
              		width:80,
           	   		onClick:function(){
	   	                   	$("#realName").val('');
	   	                   	$("#userName").val('');
	   	            }
              });
              $("#userName").keydown(function(e){
				  if(e.keyCode==13){
					  $("#realName").focus();
					  return false;
					}
		   });
              $("#realName").keydown(function(e){
					 if(e.keyCode==13){
						 $('#btn_query').click();
						  return false;
					}
			   });

                $('#btn_add').click(function(){
                	   var selected = $('#west-panel').omTree('getSelected');
                	 //处理没有选中节点的情况
                	   if(selected==null){
                		   alert('${message(code: 'roleUser.selected.empty.message', default: 'Please select Role')}');
                      		return;	
                       }
                	   //处理选中的是角色类型的情况
                	   if(selected.pid==null){
                		   alert('${message(code: 'roleUser.selected.roleType.message', default: 'Please select Role')}');
                     		return;	
                       }
                	   var url = '${createLink(controller:'RoleUser',action:'selectEmpByRole')}?roleId='+selected.id+'&opFlag=0';

                	   showModelDialog(url);
                });
                $('#btn_delete').click(function(){
                	  var selected = $('#west-panel').omTree('getSelected');
	               	   //处理没有选中节点的情况
	               	   if(selected==null){
	               		   alert('${message(code: 'roleUser.selected.empty.message', default: 'Please select Role')}');
	                       return;	
	                   }
                	//这里利用Ajax删除数据
               		var selections = $('#role_content').omGrid('getSelections',true);
               		if(selections.length==0){
                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                   		return;	
                   	}
               		$.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                        onClose:function(v){
                            if(v){
                            	var deleteUrl = '${createLink(controller:'roleUser',action:'jsonDelete')}';
                           		var deleteIds = '';
                           		$.each(selections,function(i,item){
        	                   		deleteIds += item.id+'_';
        	                   	});
        	                   	$.post(deleteUrl,{"deleteIds":deleteIds,"roleId":selected.id},function(data,textStatus){
        	                   		$('#role_content').omGrid('reload');
        	                   		var content ="<div class='message'>"+data+"</div>";
        	                   		showTopTip(content);		                   	
        		                },'text');
                            }
                        }
                    });
      			});
			});
			
			// ################################加载时执行的语句########################
			$(function() {
				//刚加载页面后使右下部分内容显示
				$('#center-panel').css({visibility:'visible'});
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
			         dataSource : "${createLink(controller:'menuRole',action:'selectRoleByType')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
					       	  $('#center-panel').css({visibility:'visible'});
				       		  $('#roleId').val(node.id);//查询时使用
			                  buildRightGrid(1)
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

			//#############################用户自定义方法############################
			
			//弹出用户选择窗口，并生成表格信息
			function showModelDialog(url){

				  var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-10px;'+
				  			  'text-align:center; width:100%;height:100%" '+
				  			  'scrolling="no" src="'+'${createLink(controller:'roleUser',action:'dialog')}'+
				  			  '?roleId='+$("#roleId").val()+"&tabId="+window.name+'"></iframe>';
				  var title='${message(code:'roleUser.selectUser.label',default:'Select User') }';
					//打开弹出框
				 var id= parent.showDialog(1,content,title,1100,550);
			}
			/**
			*刷新表格
			*/
			function refreshGrid(){
				$('#role_content').omGrid("reload");
			}
			//构建右侧的表格	
			function buildRightGrid(opFlag){
				$('#role_content').omGrid({
					dataSource:'${createLink(controller:'roleUser',action:'selectEmpByRole')}?opFlag='+opFlag+'&'+$('#form_query').serialize(),
					title:"<g:message code='default.list.label' args='[entityName1]'/>",
					singleSelect:false,
					limit : 12, // 分页显示，每页显示12条
					height : 440,
					colModel:[{header : "${message(code: 'user.realName.label', default: 'Real Name')}", name : 'tempName', width : 80,align : 'left'}, 
							  {header : "${message(code: 'user.userName.label', default: 'User Name')}", name : 'userName', width : 115, align : 'left'},
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
	 						  {header : "${message(code: 'user.validEndTime.label', default: 'validEndTime')}", name : 'validEndTime', width : 150, align : 'center' }
	                        ]
				});
			}
		    function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 3000
		        });
		    }
 	 	</script>
	</body>
</html>
