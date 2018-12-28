<%@ page import="cn.com.wz.parent.system.role.Role" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'role')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80"><span><g:message code="role.roleNameC.label" default="Role NameC" />：</span></td>
					    <td width="100"><span><g:textField id="tf_roleNamec" name="roleNameC" maxlength="200" value="${roleInstance?.roleNameC}"/></span></td>
					    <td width="30"></td>
					    <td width="80"><span><g:message code="role.roleCode.label" default="Role Code" />：</span></td>
					    <td width="100"><span><g:textField id="tf_roleCode" name="roleCode" maxlength="200" value="${roleInstance?.roleCode}"/></span></td>
					    <td width="30"></td>
                        <td width="80"><span><g:message code="role.roleType.label" default="Role Type" />：</span></td>
					    <td width="100"><span><g:select id="roleType" name="roleType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('typeNameC','角色类型')}})}" optionKey="id" value="${roleInstance?.roleType?.id}"  noSelection="['':'-全部-']" /></span></td>
					    <td width="100"></td>
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
					<input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="role_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
	 	$(function() {
			   $('#page').css({height:$(document).height()-16});
			 //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
		   	                    var url = "${createLink(controller:'Role',action:'search')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#role_grid').omGrid('setData',url);
   	                  }
                 });
             $('#btn_clear').omButton({
               width:80,
           	   onClick:function(){
	   	           $("#tf_roleNamec").val('');
	   	           $("#tf_roleCode").val('');     
	   	           $("#roleType").val('');
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
						 $('#roleType').focus();
						  return false;
					}
			   });
              $("#roleType").keydown(function(e){
					 if(e.keyCode==13){
						 $('#btn_query').click();
						  return false;
					}
			   });
              
               $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'Role',action:'create')}';
                	   var selected = $('#role_grid').omTree('getSelected');
                	   window.location.href = url+'?dicTyp';
               });
               $('#btn_edit').click(function(){
            		   var url = '${createLink(controller:'Role',action:'edit')}';
            		   var selected = $('#role_grid').omGrid('getSelections',true);
            		   if(selected.length==0){
							alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
                	   }else if(selected.length>1){
	                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
                	   }else{
	                	   window.location.href = url+'/'+selected[0].id;
		               }
               });
               $('#btn_delete').click(function(){
                   		//这里利用Ajax删除数据
                   		var selections = $('#role_grid').omGrid('getSelections',true);
                   		if(selections.length==0){
	                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                   		return;	
	                   	}
                   		$.omMessageBox.confirm({
                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                            onClose:function(v){
                                if(v){
                                	var deleteUrl = '${createLink(controller:'Role',action:'jsonDelete')}';
        	                   		var deleteIds = '';
        	                   		$.each(selections,function(i,item){
        		                   		deleteIds += item.id+'_';
        		                   	});
        		                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
        		                   		var selections = $('#role_grid').omGrid('reload');
        		                   		var content = "<div class='message'>"+data+"</div>";
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
            	   var url = '${createLink(controller:'Role',action:'show')}';
        		   var selected = $('#role_grid').omGrid('getSelections',true);
        		   if(selected.length==0){
						alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            	   }else if(selected.length>1){
                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            	   }else{
                	   window.location.href = url+'/'+selected[0].id;
	               }
               });
              
	 	})

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
			$('#role_grid').omGrid({
				dataSource:'${createLink(controller:'Role',action:'jsonList')}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, // 分页显示，每页显示12条
				height : 430,
				colModel:[{header : "${message(code: 'role.roleNameC.label', default: 'Role NameC')}", name : 'roleNameC', width : 150, align : 'center'},
						  {header : "${message(code: 'role.roleCode.label', default: 'Role Code')}", name : 'roleCode', width : 150, align : 'center'},
                          {header : "${message(code: 'role.roleType.label', default: 'Role Type')}", name : 'roleTypeName', width : 150, align : 'center'},
						  {header : "${message(code: 'role.dateCreated.label', default: 'Date Created')}", name : 'dateCreated', width : 150, align : 'center'},
                          {header : "${message(code: 'role.lastUpdateTime.label', default: 'Last Update Time')}", name : 'lastUpdateTime', width : 150, 
                        	  /*renderer:function(value,rowData,rowIndex){
									var d = new Date(value);
									var result;
									var month = d.getMonth()+1;
									result = d.getFullYear()+"-"+month+"-"+d.getDate();
									return result;
								},*/align : 'center'}]
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
