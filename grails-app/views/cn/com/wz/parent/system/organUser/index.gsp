
<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'organUser.label', default: 'OrganUser')}" />
			<g:set var="entityName1" value="${message(code: 'user.label', default: 'user')}" />
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
					<input type="hidden" id="organizationId" name="organId"/>
				</form>
 				
 				<fieldset class="buttons" style="margin:8px 8px 8px 4px;">
					<input id="btn_add" type="button" class="create" value="${message(code: 'organUser.add.label', default: 'AddUser')}"/>
					<input id="btn_delete" type="button" class="delete" value="${message(code: 'organUser.delete.label', default: 'DeleteUser')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="content" style="border:0px"></div>
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
                		   buildRightGrid(1)
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
                		   alert('${message(code: 'organUser.selected.empty.message', default: 'Please select Organization')}');
                      		return;	
                       }
                	   
                	   var url = '${createLink(controller:'OrganUser',action:'selectEmpByOrgan')}?organId='+selected.id+'&opFlag=0';

                       //加上选择条件，已经被加入这个组织结构的用户就不用再显示了
                       url+='';
                	   showModelDialog(url);
                });
                $('#btn_delete').click(function(){
                	  var selected = $('#west-panel').omTree('getSelected');
	               	   //处理没有选中节点的情况
	               	   if(selected==null){
	               		   alert('${message(code: 'organUser.selected.empty.message', default: 'Please select Organization')}');
	                       return;	
	                   }
                	//这里利用Ajax删除数据
               		var selections = $('#content').omGrid('getSelections',true);
               		if(selections.length==0){
                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                   		return;	
                   	}
               		$.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                        onClose:function(v){
                            if(v){
                            	var deleteUrl = '${createLink(controller:'organUser',action:'jsonDelete')}';
                           		var deleteIds = '';
                           		$.each(selections,function(i,item){
        	                   		deleteIds += item.id+'_';
        	                   	});
        	                   	$.post(deleteUrl,{"deleteIds":deleteIds,"organId":selected.id,'datestr':new Date},function(data,textStatus){
        	                   		$('#content').omGrid('reload');
        	                   		var content = "<div class='message'>"+data+"</div>";
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
			         dataSource : "${createLink(controller:'organization',action:'jsonListTree')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       		  $('#center-panel').css({visibility:'visible'});
			       		  $('#organizationId').val(node.id);//查询时使用
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
					var content='<form id="form_query1" style="min-width:750px;margin:8px;height:30px;">'+
							'<table style="width:100%;border:0px;">'+
							'  <tr>'+
							'    <td width="80" align="right"><span><g:message code="user.userName.label" default="User Name" />：</span></td>'+
							'    <td width="100"  align="left"><span><g:textField id="userName1" name="userName" maxlength="200" /></span></td>'+
							'  	<td width="100" align="right"><span><g:message code="user.realName.label" default="realName" />：</span></td>'+
							'    <td width="100" align="left"><span><g:textField id="realName1" name="realName" maxlength="200"/></span></td>'+
							'    <td align="center" valign="middle">'+
							'   	<input id="btn_query1" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>'+
							'    	<input id="btn_clear1" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>'+
							'    </td>'+
							'  </tr>'+
							'</table>'+
							'	</form>'+
							'	<fieldset class="buttons">'+
							'		<input id="btn_confirm" type="button" class="create" value="${message(code:'default.button.confrm.label',default:'confirm')}"/>'+
							'		<input id="btn_close" type="button" class="create" value="${message(code:'default.button.close.label',default:'cancel')}"/>'+
							'	</fieldset>'+
						    ' <div id="user_grid"></div>';
					var title='<g:message code='default.list.label' args='[entityName1]'/>';
					//打开弹出框
					parent.showDialog(1,content,title,1000,600);
					//生成表格记录
				   $('#user_grid',parent.document).omGrid({
						dataSource:url,
						title:"<g:message code='default.list.label' args='[entityName1]'/>",
						singleSelect:false,
						limit : 12, // 分页显示，每页显示12条
						height : 440,
						colModel:[{header : "${message(code: 'user.realName.label', default: 'Real Name')}", name : 'realName', width : 80,align : 'left'},
								  {header : "${message(code: 'user.userName.label', default: 'User Name')}", name : 'userName', width : 110, align : 'left'},
								  {header : "${message(code: 'user.organization.label', default: 'Organization')}", name : 'organNames', width : 110, align : 'center' },
								  {header : "${message(code: 'user.posts.label', default: 'postsNames')}", name : 'postsNames', width : 110, align : 'center' },
								  {header : "${message(code: 'user.jobLevel.label', default: 'jobLevel')}", name : 'jobLevel', width : 70, align : 'center' },
								  {header : "${message(code: 'user.userType.label', default: 'User Type')}", name : 'userTypeName', width : 70, align : 'center' },
								  {header : "${message(code: 'user.status.label', default: 'Status')}", name : 'status', width : 70, align : 'center',
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
		                           {header : "${message(code: 'user.validStartTime.label', default: 'validStartTime')}", name : 'validStartTime', width : 120,align : 'center'}, 
		 						  {header : "${message(code: 'user.validEndTime.label', default: 'validEndTime')}", name : 'validEndTime', width : 120, align : 'center' }
		                        ]
					});

				   $('#btn_query1',parent.document).omButton({
	            		width:80,
	         	   		onClick:function(){
		   	                    var url = "${createLink(controller:'organUser',action:'selectEmpByOrgan')}";
		   	                    url+='?opFlag=0&organId='+$("#organizationId").val()+'&'+$('#form_query1',parent.document).serialize();
		   	                    $('#user_grid',parent.document).omGrid('setData',url);
		   	                  }
	         	    });
	            
		           $('#btn_clear1',parent.document).omButton({
		            		width:80,
		         	   		onClick:function(){
			   	                   	$("#realName",parent.document).val('');
			   	                   	$("#userName1",parent.document).val('');
			   	            }
		          });

		           $("#userName1",parent.document).keydown(function(e){
						  if(e.keyCode==13){
							  $("#realName1",parent.document).focus();
							  return false;
							}
				   });
		              $("#realName1",parent.document).keydown(function(e){
							 if(e.keyCode==13){
								 $('#btn_query1',parent.document).click();
								  return false;
							}
					   });
	              $("#btn_confirm",parent.document).click(function(){
	            	  confirmForSelect();
	              })
	              
	              $("#btn_close",parent.document).click(function(){
	            	 parent.closeDialog(this.parentNode.parentNode.id);
	              })
			}

			//弹出窗的确认按钮的事件
			function confirmForSelect () {
				//获取选择用户的id并组成"_"分割的字符串
    			var selections = $('#user_grid',parent.document).omGrid('getSelections',true);
    			if(selections.length==0){
    				var content = "<div >"+"${message(code:'default.selection.empty.message',default:'please select record')}"+"</div>";
                  		showTopTip(content);
                  		return;
	    		}
    			//获取选中的组织结构id
    			var selected = $('#west-panel').omTree('getSelected');
    			var organId=selected.id;
    			var userIds="";
    			$.each(selections,function(i,item){
                  		userIds += item.id+'_';
                  	});

    			//保存数据
    			var url= "${createLink(controller:'organUser',action:'save')}"+"?userIds="+userIds+"&organId="+organId;
    			$.post(url,{},function(data,textStatus){
                  		$('#content').omGrid('reload');
                  		$("#btn_close",parent.document).click();
                  		var content = "<div class='message'>"+data+"</div>";
                  		showTopTip(content);		                   	
                },'text');

     		}
     		
			//构建右侧的表格	
			function buildRightGrid(opFlag){
			$('#content').omGrid({
				dataSource:'${createLink(controller:'OrganUser',action:'selectEmpByOrgan')}?opFlag='+opFlag+'&'+$('#form_query').serialize(),
				title:"<g:message code='default.list.label' args='[entityName1]'/>",
				singleSelect:false,
				limit : 12, // 分页显示，每页显示12条
				height : 440,
				colModel:[{header : "${message(code: 'user.realName.label', default: 'Real Name')}", name : 'realName', width : 80,align : 'left'},
						  {header : "${message(code: 'user.userName.label', default: 'User Name')}", name : 'userName', width : 110, align : 'left'},
						  {header : "${message(code: 'user.organization.label', default: 'Organization')}", name : 'organNames', width : 110, align : 'center' },
						  {header : "${message(code: 'user.posts.label', default: 'postsNames')}", name : 'postsNames', width : 110, align : 'center' },
						  {header : "${message(code: 'user.jobLevel.label', default: 'jobLevel')}", name : 'jobLevel', width : 70, align : 'center' },
						  {header : "${message(code: 'user.userType.label', default: 'User Type')}", name : 'userTypeName', width : 70, align : 'center' },
						  {header : "${message(code: 'user.status.label', default: 'Status')}", name : 'status', width : 70, align : 'center',
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
                           {header : "${message(code: 'user.validStartTime.label', default: 'validStartTime')}", name : 'validStartTime', width : 120,align : 'center'}, 
 						  {header : "${message(code: 'user.validEndTime.label', default: 'validEndTime')}", name : 'validEndTime', width : 120, align : 'center' }
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
