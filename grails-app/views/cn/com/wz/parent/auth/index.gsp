<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
		    	<div style="margin-right:8px;margin-top:8px;">
		    		<fieldset class="buttons" style="margin:8px 8px 8px 4px;">	
		    			<input id="btn_save" type="button" class="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
					</fieldset>	
						<table id="grid"></table>
					<input type="hidden" id="home_all" />
				</div>		
			</div>
		    	<div id="west-panel">
		    		<div id="role_tree" style="border:1px;"></div>
	    	</div>
    	</div>
    	
	 	<script>
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
			    $('#role_tree').omTree({
			         dataSource : "${createLink(controller:'menuRole',action:'selectRoleByType')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
					       	  $('#center-panel').css({visibility:'visible'});
				       		  $('#roleId').val(node.id);//查询时使用

				       		  //处理选中的是角色类型的情况
		                	   if(node.pid!=null){
		                		 //将具有权限的分类id清空
					       		  $('#home_all').val('');
		                		   buildRightGrid(node.id);
		                       }
		                	   if(node.children==null){

	                           }else{
	                        	   if(!node.isExpand){
	                             		node.isExpand='0';
	                             		}
	                               if(node.isExpand=='0'){
	                                   $('#role_tree').omTree('expand',node);
	                                   node.isExpand=1;
	                               }else{
	                                   $('#role_tree').omTree('collapse',node);
	                                   node.isExpand=0;
	                               }
	                           }
		             }
			    });
			});
			//绑定事件
			$(function(){
				$('#btn_save').click(function(){
	             	   var selected = $('#role_tree').omTree('getSelected');
	             	   //处理没有选中节点的情况
	             	   if(selected==null){
                           alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
	                   		return;	
	                    }
	             	   //处理选中的是角色类型的情况
                	   if(selected.pid==null){
                		   alert('${message(code: 'roleUser.selected.roleType.message', default: 'Please select Role')}');
                     		return;	
                       }
	                   var roleId=selected.id;
	             	   //获取选中的菜单信息
							var dictionaryIds = $('#home_all').val();
	             	   var url = '${createLink(controller:'Auth',action:'save')}';
		                   	$.post(url,{"dictionaryIds":dictionaryIds,"roleId":roleId,'dicTypeCode':"${params.dicTypeCode}"},function(data,textStatus){
		                   		showTopTip(data);
			                },'text');
	             });
			});
			//#############################用户自定义方法############################
			//构建右侧的表格	
			function buildRightGrid(roleId){
		            $('#grid').omGrid({
		                dataSource : "${createLink(controller:'Auth',action:'dicValueList')}?roleId="+roleId+"&dicTypeCode="+"${params.dicTypeCode}",
						limit:0,
		                colModel : [ {header : "${message(code: 'articleCategory.categoryNameC.label', default: 'Category NameC')}", name : 'dicValueNameC', width : 'autoExpand', align : 'left'}, 
		                             {header : "${message(code: 'auth.label', default: 'Auth')}", name : 'auths', align : 'center', width : 100 , renderer : authorityhome}
		                             ],
		                //当单击checkbox时阻止事件冒泡，这样不会选中checkbox所在的行
		                onRefresh : function(){
		                    $('#grid').find("input:checkbox").click(function(event){
		                        event.stopPropagation();
		                    });
		                }             
		            });
			}

			function authorityhome(colValue, rowData, rowIndex){
            	if(colValue== 1){
            		$('#home_all').val($('#home_all').val()+rowData.id+'_'); //将已添加权限的字典值Id保存到home_all中
            		return '<input type="checkbox" id="'+rowData.id+'_"  checked="checked" onclick="check(this)"></input>';
            	}
            		return '<input type="checkbox" id="'+rowData.id+'_"  onclick="check(this)"></input>';
            }
			//发布权限设置
			function check(obj){
	        	var oldpublish = $('#home_all').val();
	        	if(obj.checked){
	        		$('#home_all').val(oldpublish+obj.id);
	        	}else{
	        		$('#home_all').val(oldpublish.replace(obj.id,''));
	        	}
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
