<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'organUser.label', default: 'OrganUser')}" />
			<g:set var="entityName1" value="${message(code: 'employee.label', default: 'Employee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page1" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 					<div id="center-top">
		 				<form id="form_query" style="margin:8px;height:30px;">
							<table style="width:100%;border:0px;">
							  <tr>
							    <td width="80"><span><g:message code="role.roleNameC.label" default="Role NameC" />：</span></td>
							    <td width="80"><span><g:textField id="tf_roleNamec" name="roleNameC" maxlength="200" value="${roleInstance?.roleNameC}"/></span></td>
							    <td width="5"></td>
							    <td align="left" valign="middle">
							    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
							    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>	
							    </td>
							  </tr>
							</table>
					
						</form>
		 				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                             <input id="btn_add" type="button"  class="import" value="${message(code: 'default.button.addRole.label', default: 'Add')}"/>
				        </fieldset>
						<div style="margin-right:8px;margin-top:0px;">
							<div id="content" style="border:0px">
								
							</div>
						</div> 
 					</div>
 					<div id="center-bottom">
 						<table id="roles_table" class="tab_tbody_list">
 						<thead>
 							<tr>
 								<th>
 									角色名称
 								</th>
 								<th>
 									角色编码
 								</th>
 								<th>
 									操作
 								</th>
 								
 							</tr>
 						</thead>
 						<tr></tr>
 							<g:each in="${roles}" var='role'>
 							
 								<tr>
 									<td>
	 									${ role.roleNameC}
		 								<input type='hidden' name='rolesIds' value='${ role.id}'/>
		 								<input type='hidden' name='rolesNames' value='${ role.roleNameC}'/>
	 								</td>
	 								<td>
	 									${ role.roleCode}
	 								</td>
 									<td><a href="#" onclick="delRow(this)">删除</a></td>
 								</tr>
				    		</g:each>
 						</table>
 						<div>
 							<input type="button" id="btn_confirm" value="确定">
 							<input type="button" id="btn_close" value="关闭">
 						</div>
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
		   	                    var url = "${createLink(controller:'Role',action:'search')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#content').omGrid('setData',url);
		   	                  }
                });
             //绑定清除按钮事件
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
					 $('#btn_query').click();
					  return false;
				}
		   });
              $("#btn_close").omButton({
                  width:80,
                  onClick:function(){
                	  parent.closeDialog(window.parent.document.getElementById("roles_dialog").parentNode.id);
                      }
               });
               $("#btn_confirm").omButton({
                   width:80,
                   onClick:function(){
                	   var roleShowName="";
                       var roleIds="";
                       var d=$("input[name='rolesIds']");
                       for(var i=0;i<d.length;i++){  
                    	   roleIds+=d[i].value+"_";
                      }

                       var d=$("input[name='rolesNames']");
                       for(var i=0;i<d.length;i++){ 
                           roleShowName=roleShowName+d[i].value+";";
                       }
                       var tabId='${tabId}';
                       if(roleShowName.length>0){
                    	   roleShowName=roleShowName.substring(0,roleShowName.lastIndexOf(";"));
                           }
                	   parent[tabId].getRoles(roleShowName,roleIds);
                      $("#btn_close").click();
                       }
                   
                });
               $("#tf_roleNamec").keydown(function(e){
					 if(e.keyCode==13){
						 $('#btn_query').click();
						  return false;
					}
			   });
                $('#btn_add').click(function(){
             		var selections = $('#content').omGrid('getSelections',true);
             		if(selections.length==0){
                 		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                 		return;	
                 	} 

             		$.each(selections,function(i,item){
             			 addRoles(item.id,item.roleNameC,item.roleCode);
                  	});
    			});

    			
			});
			
			// ################################加载时执行的语句########################
			$(function() {
				//刚加载页面后使右下部分内容显示
				$('#center-panel').css({visibility:'visible'});
				$('#page1').css({height:$(document).height()-16});
				//布局
				$('#page1').omBorderLayout({
					panels:[{
	           	        id:"center-panel",
	           	     	header:false,
	           	        region:"center"
	           	    }]
	            });
	            
				$('#center-panel').omBorderLayout({
					panels:[{
	           	        id:"center-top",
	           	 		header:false,
	           	        region:"north",
	           	        height:330
	           	    },{
	           	        id:"center-bottom",
	           	        title:"<g:message code='postList.label' args='[entityName1]'/>",
	           	        region:"center"
	           	    }]
	            });
				buildRightGrid()
			});

			//#############################用户自定义方法############################

			//构建右侧的表格	
			function buildRightGrid(value,event){
				$('#content').omGrid({
					dataSource:'${createLink(controller:'Role',action:'jsonList')}',
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 5, // 分页显示，每页显示12条
					height : 240,
					onRowDblClick : function(rowIndex,rowData) {
						addRoles(rowData.id,rowData.roleNameC,rowData.roleCode);
	                },
					colModel:[{header : "${message(code: 'role.roleNameC.label', default: 'Role NameC')}", name : 'roleNameC', width : 150, align : 'center'},
							  {header : "${message(code: 'role.roleCode.label', default: 'Role Code')}", name : 'roleCode', width : 150, align : 'center'},
	                          {header : "${message(code: 'role.roleType.label', default: 'Role Type')}", name : 'roleTypeName', width : 150, align : 'center'}
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

		    //往选中人员表格中添加一行数据
		    function addRoles(roleId,roleNameC,roleCode){
			    //验证数据是否已存在
		    	 var d=$("input[name='rolesIds']");
                 for(var i=0;i<d.length;i++){  
                     if(d[i].value==roleId){
                  		alert("["+roleNameC+"]"+"${message(code: 'insidenote.haveselected.message')}")
						return;
                     }
                }
			    
			    
		    	   var table=document.getElementById("roles_table");
		    	   var count=table.rows.length;
		    	   var row=table.insertRow(table.rows.length);
		    	   row.id=count;
		    	  
		    	   var col=row.insertCell(0);
		    	   col.innerHTML=roleNameC+"<input type='hidden' name='rolesIds' value='"+roleId+"'/>"+
		    	   "<input type='hidden' name='rolesNames' value='"+roleNameC+"'/>";
		    	   col.className="bk_mlst_tdwhite" ;

		    	   var col=row.insertCell(1);
		    	   col.innerHTML=roleCode;
		    	   col.className="bk_mlst_tdwhite" ;

		    	   
		    	   var col=row.insertCell(2);
		    	   col.innerHTML="<a href='#' onclick="+'"delRow(this)">删除</a>';
		    	   col.className="bk_mlst_tdwhite" ;

		       }
			//从表中删除指定行
		    function delRow(it){
		    	   var table=it.parentElement.parentElement.parentElement;
		    	   var rowIndex=it.parentElement.parentElement.rowIndex;
		    	  table.deleteRow(rowIndex-1);
		       }
 	 	</script>
	</body>
</html>
