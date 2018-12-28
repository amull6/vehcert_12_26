<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'outBox.label', default: 'outBox')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.receiverName.label" default="receiverName" />：</span></td>
					    <td width="100"><span><g:textField id="receiverName" name="receiverName" maxlength="200" value="${insideNoteInstance?.receiverName}"/></span></td>
					    <td width="60"></td>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.send.label" default="sendTime" />：</span></td>
					    <td width="100"><span><c:dataPicker id="firstTime" dateFormat='yy-mm-dd' name="firstTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="15" >到</td>
					    <td width="100"><span><c:dataPicker id="lastTime" dateFormat='yy-mm-dd'  name="lastTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="100"></td>
					    <td align="center" valign="middle">
   	                       <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    	   <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				        </td>
					  </tr>
					</table>
					
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="insideNote_grid" style="border:0px"></div>
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
		   	                    var url = "${createLink(controller:'InsideNote',action:'jsonOutBoxList')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#insideNote_grid').omGrid('setData',url);
		   	                  }
                   });

               $('#btn_clear').omButton({
            		width:80,
         	   		onClick:function(){
	   	                   	$("#receiverName").val('');
	   	                   	$("#firstTime").val('');
	   	          	        $("#lastTime").val('');
	   	            }
            });
               $("#receiverName").keydown(function(e){
   				  if(e.keyCode==13){
   					 $('#btn_query').click();
   					  return false;
   					}
   		   });
              
          
               $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'InsideNote',action:'create')}';
                	   var selected = $('#insideNote_grid').omTree('getSelected');
                	   window.location.href = url+'?tabId=${tabId}';
               });
             
               $('#btn_delete').click(function(){
                   		//这里利用Ajax删除数据
                   		var selections = $('#insideNote_grid').omGrid('getSelections',true);
                   		if(selections.length==0){
	                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                   		return;	
	                   	}
                   	 $.omMessageBox.confirm({
                         title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                         content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                         onClose:function(v){
                             if(v){
                             	var deleteUrl = '${createLink(controller:'InsideNote',action:'jsonOutBoxDelete')}';
     	                   		var deleteIds = '';
     	                   		$.each(selections,function(i,item){
     		                   		deleteIds += item.id+'_';
     		                   	});
     		                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
     		                   		var selections = $('#insideNote_grid').omGrid('reload');
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
	          //buildLeftTree();
	          buildRightGrid();
           });
		function buildRightGrid(value,event){
			$('#insideNote_grid').omGrid({
				dataSource:'${createLink(controller:'InsideNote',action:'jsonOutBoxList')}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, 
				height : 440,
				colModel:[{header : "${message(code: 'insideNote.receiverName.label', default: 'receiverName')}", name : 'receiverName', width : 150, align : 'left'},
                          {header : "${message(code: 'insideNote.content.label', default: 'content')}", name : 'content', width :600, align : 'left',
                    		  renderer:function(value,rowData,rowIndex){
										return '<span title="'+value+'">'+value+'</span>';
							  	}
                          },
                          {header : "${message(code: 'insideNote.send.label', default: 'sendTime')}", name : 'sendTime', width : 200, align :'center'},
						  {header : "${message(code: 'insideNote.status.label', default: 'status')}", name : 'status', width :'198', align : 'center',
							  renderer:function(value,rowData,rowIndex){
									 if(value==1||value==2||value==5){
										return '已发送';
									}else{
										return '参数错误';
									}
							  }
						}
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
