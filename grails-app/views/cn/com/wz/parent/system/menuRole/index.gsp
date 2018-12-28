
<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
			<g:set var="entityName1" value="${message(code: 'employee.label', default: 'Employee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 				<fieldset class="buttons" style="margin:8px 8px 8px 4px;">
					<input id="btn_save" type="button" class="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
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
                $('#btn_save').click(function(){
                
                	   var selected = $('#west-panel').omTree('getSelected');
                	 //处理没有选中节点的情况
                	   if(selected==null){
                		   alert('${message(code: 'menuRole.selected.empty.message', default: 'Please select role')}');
                      		return;	
                       }
                	   //获取选中的菜单信息
                	   var selections=$('#content').omTree('getChecked',true);
                	   var menuIds='';
                	   $.each(selections,function(i,item){
                		   menuIds += item.id+'_';
	                   	});
                	   var url = '${createLink(controller:'menuRole',action:'save')}';
	                   	$.post(url,{"menuIds":menuIds,"roleId":selected.id,'datestr':new Date},function(data,textStatus){
	                   		var content = "<div class='message'>"+data+"</div>";
	                   		showTopTip(content);		                   	
		                },'text');
                	  

                     
                });
			});
			
			// ################################加载时执行的语句########################
			$(function() {
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
			       		
			       		  if(node.type=='role'){
			       			  $('#center-panel').css({visibility:'visible'});
			       			  buildRightTree(node.id);
				       	  }else{
				       		 $('#center-panel').css({visibility:'hidden'});
					      }
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
			//构建右侧的菜单树	
			function buildRightTree(roleId){
				$('#content').omTree({
			         dataSource : "${createLink(controller:'menu',action:'jsonAllList')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 showCheckbox:true,
			       	 onSelect: function(node,event) {
                            if(node.isExpand=='0'){
                                $('#content').omTree('expand',node);
                                node.isExpand=1;
                            }else{
                                $('#content').omTree('collapse',node);
                                node.isExpand=0;
                            }
		             }
				             
			    });

				//对以选中的菜单需要延时加载
				 window.setTimeout(function(){getOldMenu(roleId);}, 200); 
			}
			//获取已选中的菜单信息
			function getOldMenu(roleId){
				//给原来的菜单信息进行赋值
			    url="${createLink(controller:'menuRole',action:'selectOldMenusByRole')}"
			 	$.post(url,{"roleId":roleId,'datestr':new Date},function(data,textStatus){
				 	if(textStatus){
						var ids=data.split("_");
						$.each(ids,function(i,it){
							var node1=$('#content').omTree('findNode', 'id', it);
							if(node1!=null){
								$('#content').omTree('check',node1);
							}
							
						});
					}else{
							showTopTip("<div class='message'>"+'${message(code:'default.reload.failed.label',default:'Menu Tree reload Failed')}'+'</div>');
					}
				 	
                },'text');
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
