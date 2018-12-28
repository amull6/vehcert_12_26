
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dictionaryType.label', default: 'DictionaryType')}" />
		<g:set var="entityName_value" value="${message(code: 'dictionaryValue.label', default: 'dictionary.DictionaryValue')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;"> 
				<form id="form_query" style="margin:8px;height:50px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80"><span><g:message code="dictionaryValue.dicValueNameC.label" default="Dic Value Name C" />：</span></td>
					    <td width="100"><span><g:textField id="dicValueNameC" name="dicValueNameC" maxlength="200" value="${dictionaryValueInstance?.dicValueNameC}"/></span></td>
					    <td width="60"></td>
					    <td width="80"><span><g:message code="dictionaryValue.dicValueNameE.label" default="Dic Value Name E" />：</span></td>
					    <td width="100"><span><g:textField id="dicValueNameE" name="dicValueNameE" maxlength="200" value="${dictionaryValueInstance?.dicValueNameE}"/></span></td>
					    <td width="100"></td>
					    <td align="left" valign="middle">
						    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
					<input type="hidden" id="dicTypeId" name=dicTypeId>
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
					<input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
<%--					<input id="btn_import" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}"/>--%>
<%--					<input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>--%>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="dictionary_grid" style="border:0px"></div>
				</div>
			</div>	   
	    	<div id="west-panel">
	    		<div id="dictionary_tree" style="border:1px;"></div>
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
				   	                    var url = "${createLink(controller:'dictionaryValue',action:'search')}";
				   	                    url+='?'+$('#form_query').serialize();
				   	                    $('#dictionary_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $("#dicValueNameC").keydown(function(e){
	 					  if(e.keyCode==13){
	 						  $("#dicValueNameE").focus();
	 						  return false;
	 						}
	 			   });
	                   $("#dicValueNameE").keydown(function(e){
		 					 if(e.keyCode==13){
		 			              $('#btn_query').click();
		 						  return false;
		 					}
		 			   });
	                   $('#btn_clear').omButton({
		    					width:80,
		    					onClick:function(){
		    						$("#dicValueNameC").val('');
		    						$("#dicValueNameE").val('');
		    					}
	          	 	  	  });
	                   $('#btn_create').click(function(){
		                	   var url = '${createLink(controller:'dictionaryValue',action:'create')}';
		                	   var selected = $('#dictionary_tree').omTree('getSelected');
		                	   window.location.href = url+'?dictionaryType.id='+selected.id;
	                   });
	                   $('#btn_edit').click(function(){
	                		   var url = '${createLink(controller:'dictionaryValue',action:'edit')}';
	                		   var selected = $('#dictionary_grid').omGrid('getSelections',true);
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
		                   		var selections = $('#dictionary_grid').omGrid('getSelections',true);
		                   		if(selections.length==0){
			                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
			                   		return;	
			                   	}
		                   		$.omMessageBox.confirm({
		                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
		                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
		                            onClose:function(v){
		                                if(v){
		                                	var deleteUrl = '${createLink(controller:'dictionaryValue',action:'jsonDelete')}';
					                   		var deleteIds = '';
					                   		$.each(selections,function(i,item){
						                   		deleteIds += item.id+'_';
						                   	});
						                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
						                   		var selections = $('#dictionary_grid').omGrid('reload');
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
	                   $('#btn_export').click(function(){
		                   //导出功能
		                   alert('未添加该功能');
	                   });
	                   $('#btn_import').click(function(){
		                   //导入功能
	                	   alert('未添加该功能');
	                   });
			});
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
		          buildLeftTree();
		          setTimeout('inOldPage();',500);
	           });
			//用户自定义方法
			
			//创建左侧的字典树
			function buildLeftTree(value,event) {
				$('#dictionary_tree').omTree({
					dataSource:"${createLink(controller:'dictionaryType',action:'jsonListTree')}",
					simpleDataModel:true,
					onSelect:function(nodeData, event){
							$('#center-panel').css({visibility:'visible'});
							$('#dicTypeId').val(nodeData.id);
							var dataUrl = "${createLink(controller:'dictionaryValue',action:'search')}";
							dataUrl += "?dicTypeId="+nodeData.id;
							buildRightGrid(dataUrl);
							if(nodeData.children==null){

	                        }else{
	                        	if(!nodeData.isExpand){
                                    nodeData.isExpand='0';
                             	}
	                            if(nodeData.isExpand=='0'){
	                                $('#dictionary_tree').omTree('expand',nodeData);
	                                nodeData.isExpand=1;
	                            }else{
	                                $('#dictionary_tree').omTree('collapse',nodeData);
	                                nodeData.isExpand=0;
	                            }
	                        }
						}
				});
			}
			function buildRightGrid(url){
				$('#dictionary_grid').omGrid({
					dataSource:url,
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit:12,
					height:440,
					colModel:[{header : "${message(code: 'dictionaryValue.dicValueNameC.label', default: 'Dictionary Chinese Name')}", name : 'dicValueNameC', width : 200, align : 'left'},
                              {header : "${message(code: 'dictionaryValue.dicValueNameE.label', default: 'Dictionary English Name')}", name : 'dicValueNameE', width : 200, align : 'left'},
                              {header : "${message(code: 'dictionaryValue.code.label', default: 'code')}", name : 'code', width : 100, align : 'center'},
                              {header : "${message(code: 'dictionaryValue.value1.label', default: 'Value One')}", name : 'value1', width : 200, align : 'left'},
                              {header : "${message(code: 'dictionaryValue.value2.label', default: 'Value Two')}", name : 'value2', width : 200, align : 'left'},
                              {header : "${message(code: 'dictionaryValue.dateCreated.label', default: 'Create Date')}", name : 'dateCreated', width : 150, align : 'center'},
                              {header : "${message(code: 'dictionaryValue.lastUpdated.label', default: 'Update Date')}", name : 'lastUpdated', width : 150, align : 'center'}]
				});
			}
			function isEditable(obj){
				var selected = $('#dictionary_grid').omGrid('getSelections',true);
     		   	if(selected.length==0){
					alert('Please Select Record');
         	   	}else{
					window.location.href=obj+"/"+selected[0].id;
             	}
				return false;
			}
			function isDeletable(obj){
				var selected = $('#dictionary_grid').omGrid('getSelections',true);
     		   	if(selected.length==0){
					alert('Please Select Record');
         	   	}else{
					//利用ajax删除记录
					//$.
             	}
				return false;
			}
		    function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 3000
		        });
		    }
		  	//从详情页面返回后，进入原来页面
		    function inOldPage(){
				var nodeText="${params.currentCategoryId}";
				var target = $('#dictionary_tree').omTree("findNode", "id", nodeText);
				$('#dictionary_tree').omTree('select',target);
				var parent=$('#dictionary_tree').omTree('getParent',target);
				$('#dictionary_tree').omTree('expand',parent);
			}
		    
		 </script>
	</body>
</html>
