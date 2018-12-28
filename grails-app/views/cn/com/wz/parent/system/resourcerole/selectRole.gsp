
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
		
					    <td width="80"><span><g:message code="role.roleNameC.label" default="Role NameC" />：</span></td>
					    <td width="100"><span><g:textField id="tf_roleNamec" name="roleNameC" maxlength="200" value="${roleInstance?.roleNameC}"/></span></td>
					    <td width="30"></td>
					    <td width="80"><span><g:message code="role.roleCode.label" default="Role Code" />：</span></td>
					    <td width="100"><span><g:textField id="tf_roleCode" name="roleCode" maxlength="200" value="${roleInstance?.roleCode}"/></span></td>
					    <td width="30"></td>
					    <td align="center" valign="middle">
					    	<input type="hidden" id="resourceId" name="resourceId" value="${params.resourceId }"/>
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
				</form>
				<fieldset class="buttons">
					<input id="btn_confirm" type="button" class="create" value="${message(code:'default.button.confrm.label',default:'confirm')}"/>
					<input id="btn_close" type="button" class="create" value="${message(code:'default.button.close.label',default:'cancel')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="content" style="border:0px"></div>
				</div> 
	    	</div>
    	</div>
    	
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
                		   buildRightGrid(0);
		   	           }
                });
             //绑定清除按钮事件
              $('#btn_clear').omButton({
              		width:80,
           	   		onClick:function(){
	   	                   	$("#tf_roleNamec").val('');
	   	                   	$("#tf_roleCode").val('');
	   	            }
              });
              $("#tf_roleNamec").keydown(function(e){
				  if(e.keyCode==13){
					  $("#tf_roleCode").focus();
					  return false;
					}
		      });
              $("#tf_roleCode").keydown(function(e){
					 if(e.keyCode==13){
						 $('#btn_query').click();
						  return false;
					}
			   });
			});
			
			// ################################加载时执行的语句########################
			$(function() {
                buildRightGrid(0);

				var resourceId=$('#resourceId').val();
				//刚加载页面后使右下部分内容显示
				$('#center-panel').css({visibility:'visible'});
				$('#page').css({height:$(document).height()-16});
				//布局
				$('#page').omBorderLayout({
					panels:[{
	           	        id:"center-panel",
	           	     	header:false,
	           	        region:"center"
	           	    }]
	            });

                 $("#btn_confirm").click(function(){
                  confirmForSelect();
                 });

                 $("#btn_close").click(function(){
                     parent.closeDialog(window.parent.document.getElementById("iframe_dialog").parentNode.id);
                 });
            });
			//弹出窗的确认按钮的事件
			function confirmForSelect () {
				var resourceId=$('#resourceId').val();
				   var selections = $('#content').omGrid('getSelections',true);
              	 //处理没有选中节点的情况
				   if(selections.length==0){
                   		alert('${message(code:'default.selection.empty.message',default:'please select record')}');
                   		return;
                     }
    			//获取选中的角色id
    			var roleIds="";
    			$.each(selections,function(i,item){
                    roleIds += item.id+'_';
                });

    			//保存数据
    			var url= "${createLink(controller:'resourceRole',action:'save')}"+"?roleIds="+roleIds+"&resourceId="+resourceId;
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
				var roleId=$('#resourceId').val();
			    url='${createLink(controller:'resourceRole',action:'selectRolesByResource')}?opFlag='+opFlag+'&'+$('#form_query').serialize();
                $('#content').omGrid({
                    dataSource:url,
                    title:"<g:message code='default.list.label' args='[entityName1]'/>",
                    singleSelect:false,
                    limit : 10, // 分页显示，每页显示12条
                    height : 400,
                    colModel:[{header : "${message(code: 'role.roleNameC.label', default: 'Role NameC')}", name : 'roleNameC', width : 150, align : 'center'},
                        {header : "${message(code: 'role.roleCode.label', default: 'Role Code')}", name : 'roleCode', width : 150, align : 'center'},
                        {header : "${message(code: 'role.roleType.label', default: 'Role Type')}", name : 'roleTypeName', width : 150, align : 'center'},
                        {header : "${message(code: 'role.dateCreated.label', default: 'Date Created')}", name : 'dateCreated', width : 150, align : 'center'},
                        {header : "${message(code: 'role.lastUpdateTime.label', default: 'Last Update Time')}", name : 'lastUpdateTime', width : 150,align : 'center'}
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
