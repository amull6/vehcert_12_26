
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
					    	<input type="hidden" id="roleId" value="${params.roleId }"/>
					    	<input type="hidden" name='nodeId' id="nodeId" >
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
					<input type="hidden" id="organizationId" name="organId"/>
				</form>
				<fieldset class="buttons">
					<input id="btn_confirm" type="button" class="create" value="${message(code:'default.button.confrm.label',default:'confirm')}"/>
					<input id="btn_close" type="button" class="create" value="${message(code:'default.button.close.label',default:'cancel')}"/>
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
                    	   var  url='${createLink(controller:'organUser',action:'selectAllUserByOrgan')}?opFlag=1'+"&"+$('#form_query').serialize();
                		   buildRightGrid(url);
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
			});
			
			// ################################加载时执行的语句########################
			$(function() {
				var roleId=$('#roleId').val();
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
			       		$('#nodeId').val(node.id);
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

			 $("#btn_confirm").click(function(){
           	  confirmForSelect();
             });
             
             $("#btn_close").click(function(){
            	 parent.closeDialog(window.parent.document.getElementById("iframe_dialog").parentNode.id);
             });
			//弹出窗的确认按钮的事件
			function confirmForSelect () {
				var roleId=$('#roleId').val();
				   var selected = $('#west-panel').omTree('getSelected');
				   var selections = $('#content').omGrid('getSelections',true);
              	 //处理没有选中节点的情况
				   if(selections.length==0){
                   		alert('${message(code:'default.selection.empty.message',default:'please select record')}');
                   		return;
                     }
    			//获取选中的组织结构id
    			var userIds="";
    			$.each(selections,function(i,item){
                  		userIds += item.id+'_';
                  	});

    			//保存数据
    			var url= "${createLink(controller:'roleUser',action:'save')}"+"?userIds="+userIds+"&roleId="+roleId;
    			$.post(url,{},function(data,textStatus){
    	  			var tabId='${tabId}';
    	  			//为了解决在ie下home页弹出框中调用这个页面时window.name获取不到tabId
    			    if(tabId==''|| tabId==null){
    					tabId="iframe_myJob";
    				 }
    	  			parent[tabId].refreshGrid();
    	             $("#btn_close").click();
    	             
    	         },'text');

     		}
     		
			//构建右侧的表格	
			function buildRightGrid(opFlag){
				var url='';
				var roleId=$('#roleId').val();
				if(opFlag==1){
					url='${createLink(controller:'OrganUser',action:'selectEmpByOrgan')}?opFlag='+opFlag+'&'+$('#form_query').serialize()+'&roleId='+roleId;
				}else{
					url=opFlag;
				}
			$('#content').omGrid({
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
