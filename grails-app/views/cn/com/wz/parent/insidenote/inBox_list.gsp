<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'inBox.label', default: 'inBox')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page1" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.senderName.label" default="senderName" />：</span></td>
					    <td width="100"><span><g:textField id="senderName" name="senderName" maxlength="200" value="${insideNoteInstance?.senderName}"/></span></td>
					    <td width="60"></td>
					    <td style='text-align:right;width:80px;'><span><g:message code="insideNote.receive.label" default="sendTime"/>：</span></td>
					    <td width="100"><span><c:dataPicker id="firstTime" dateFormat='yy-mm-dd' name="firstTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="15" >到</td>
					    <td width="100"><span><c:dataPicker id="lastTime" dateFormat='yy-mm-dd' name="lastTime" maxlength="200" ></c:dataPicker></span></td>
					    <td width="100"></td>
					    <td align="center" valign="middle">
   	                       <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    	   <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				        </td>
					  </tr>
					</table>
					
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<input type="hidden" id="tabId" value="${tabId}"/>
						<input id="btn_read" type="button"  class="import" value="${message(code: 'default.button.read.label', default: 'Read')}"/>
					<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="insideNote_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
	 	$(function() {
			   $('#page1').css({height:$(document).height()-16});
			 //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
		   	                    var url = "${createLink(controller:'InsideNote',action:'jsonInBoxList')}";
		   	                    url+='?opFlag=${opFlag}'+"&"+$('#form_query').serialize();
		   	                    $('#insideNote_grid').omGrid('setData',url);
		   	                  }
                   });

               $('#btn_clear').omButton({
           		width:80,
        	   		onClick:function(){
	   	                   	$("#senderName").val('');
	   	                   	$("#firstTime").val('');
	   	          	        $("#lastTime").val('');
	   	            }
           });
               $("#senderName").keydown(function(e){
  				  if(e.keyCode==13){
  					$('#btn_query').click();
  					  return false;
  					}
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
                                	var deleteUrl = '${createLink(controller:'InsideNote',action:'jsonInBoxDelete')}';
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
               $('#btn_read').click(function(){
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
                           	var deleteUrl = '${createLink(controller:'InsideNote',action:'jsonInBoxRead')}';
   	                   		var deleteIds = '';
   	                   		$.each(selections,function(i,item){
   		                   		deleteIds += item.id+'_';
   		                   	});
   		                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
   		                   		var selections = $('#insideNote_grid').omGrid('reload');
   		                   		var content = "<div class='message'>"+data+"</div>";
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
			$('#page1').omBorderLayout({
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
	        //回复
	    function reply(index){
    	    var data = $("#insideNote_grid").omGrid("getData").rows[index];
    	    var url = '${createLink(controller:'insideNote',action:'newReply')}?id='+data.id;
    	    var urlRead = '${createLink(controller:'insideNote',action:'changeStats')}';
    	    $.post(urlRead,{"id":data.id},function(data,textStatus){
    	    	showModelDialog(url);
    	    	var selections = $('#insideNote_grid').omGrid('reload');		                   	
          	},'text');
   		 }
  		 //单个设为已读
  	 function readSingle(index){

    	    var data = $("#insideNote_grid").omGrid("getData").rows[index];
    	    var url = '${createLink(controller:'insideNote',action:'changeStats')}';
    	    $.post(url,{"id":data.id},function(data,textStatus){
                $('#register_grid').omGrid('reload');
                var content = "<div class='message'>"+data+"</div>";
                var selections = $('#insideNote_grid').omGrid('reload');
                showTopTip(content);		                   	
          	},'text');
    }
  		 	 //单个删除
  	function deleteSingle(index){

    	    var data = $("#insideNote_grid").omGrid("getData").rows[index];
    	    var url = '${createLink(controller:'insideNote',action:'deleteSingle')}';
    	    $.post(url,{"id":data.id},function(data,textStatus){
                $('#register_grid').omGrid('reload');
                var content = "<div class='message'>"+data+"</div>";
                var selections = $('#insideNote_grid').omGrid('reload');
                showTopTip(content);		                   	
          	},'text');
    }
		function buildRightGrid(value,event){
			$('#insideNote_grid').omGrid({
				dataSource:'${createLink(controller:'InsideNote',action:'jsonInBoxList')}?opFlag=${opFlag}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, 
				height : 440,
				colModel:[{header : "${message(code: 'insideNote.senderName.label', default: 'senderName')}",name : 'senderName',  width : 80, align : 'left'},
                           {header : "${message(code: 'insideNote.content.label', default: 'content')}", name : 'content', width :580, align : 'left',
                     		  renderer:function(value,rowData,rowIndex){
 										return '<span title="'+value+'">'+value+'</span>';
 							  	}
                           },
                          {header : "${message(code: 'insideNote.receiveTime.label', default: 'receiveTime')}", name : 'sendTime', width : 120, align :'center'},
						  {header : "${message(code: 'insideNote.status.label', default: 'status')}", name : 'status', width :'autoExpand', width : 70,align : 'center',
							  renderer:function(value,rowData,rowIndex){
									if(value==1||value==3){
										return '未读';
									}else if(value==2||value==4){
										return '已读';
									}else{
										return '参数错误';
									}
							  }
						},
						{header : "${message(code: 'activity.operate.label', default: 'operate')}", name : 'status', width: 270, align:'center', renderer:
                        	 function(value, rowData, rowIndex){
                    		 var data = rowData;
                    		 if(value==1||value==3){
                    			return '<button id="btn_reply" style="color:blue" class="btn_view" onClick="reply('+rowIndex+')">回复</button><button id="btn_read" style="color:blue" class="btn_view" onClick="readSingle('+rowIndex+')">设为已读</button><button id="btn_delete" style="color:blue" class="btn_view" onClick="deleteSingle('+rowIndex+')">删除</button>';
                        	}else if(value==2||value==4){
                        		 return '<button id="btn_reply" style="color:blue" class="btn_view" onClick="reply('+rowIndex+')">回复</button><button id="btn_delete" style="color:blue" class="btn_view" onClick="deleteSingle('+rowIndex+')">删除</button>';
	                        }
                        	
                         	 }}
					]
			});
		}
		function showTopTip(content){
	        $.omMessageTip.show({
	            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
	            content : content,
	            timeout : 2000
	        });
	    }

		function showModelDialog(url){
			var content='<iframe id="reply_dialog" style="margin-left:-10px;margin-top:-10px;margin-right:-50px;text-align:center; width:100%;height:100%" scrolling="no" src="'+url+'"></iframe>';
			var title="回复";
			//打开弹出框
			var tabId=$("#tabId").val();
			if(tabId=="asix_box"){
				 parent.showDialogForIndex('asix_box',1,content,title,600,450);
			}else{
				parent.showDialogForIndex('div_wrap',1,content,title,600,450);
			}

		};
		 </script>
	</body>
</html>
