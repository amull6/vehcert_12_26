
<%@ page import="cn.com.wz.parent.system.log.Log" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'log.label', default: 'Log')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:80px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td width="80" align="right"><span><g:message code="default.startTime.label" default="Start Time" />：</span></td>
					    <td width="100"><span><c:dataPicker id="firstTime" name="firstTime" showTime="true"  ></c:dataPicker></span></td>
					    <td width="80" align="right"><g:message code="default.endTime.label" default="End Time" />：</td>
					    <td width="100"><span><c:dataPicker id="secondTime" name="secondTime" showTime="true"></c:dataPicker></span></td>
					    <td width="80" align="right"><span><g:message code="log.theSystem.label" default="The System" />：</span></td>
                          <td width="100">
                              <span>
                                  <g:select id="theSystem" name="theSystem.code" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
                                      dictionaryType{parent{eq('code','appManage')}}

                                  })}" optionKey="code"   noSelection="['':'-全部-']" class="many-to-one"/>
                              </span>
                          </td>
                          <td></td>
                        </tr>
                        <tr>

                          <td width="80" align="right"><span><g:message code="log.operateObject.label" default="operateObject" />：</span></td>
                          <td width="100">
                              <span>
                                  <g:select id="operateObject" name="operateObject.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','operateObject')}})}" optionKey="id"  value="${logInstance?.operateOjbect?.id}" noSelection="['':'-全部-']" class="many-to-one"/>
                              </span>
                          </td>
					    <td width="80" align="right"><span><g:message code="log.logType.label" default="LogType" />：</span></td>
					    <td width="100">
					    	<span>
					    		<g:select id="logType" name="logType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','logType')}})}" optionKey="id"  value="${logInstance?.logType?.id}" noSelection="['':'-全部-']" class="many-to-one"/>
					    	</span>
					    </td>
					    <td align="left" valign="middle" colspan="2">
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
					
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
				<input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>
					<export:formats formats="['excel','pdf']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="log_grid" style="border:0px"></div>
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
		   	                    var url = "${createLink(controller:'Log',action:'jsonList')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#log_grid').omGrid('setData',url);
		   	                  }
                   });
               $('#btn_clear').omButton({
					width:80,
					onClick:function(){
						$("#firstTime").val('');
						$("#secondTime").val('');
						$("#operateObject").val('');
						$("#logType").val('');
                        $("#theSystem").val('');
					}
               });
               $(".pdf").click(function(){
            	  var firstTime= $("#firstTime").val();
				  var secondTime =$("#secondTime").val();
				  var operateObject=$("#operateObject").val();
				  var logType=$("#logType").val();
   				$('.pdf').attr('href','javascript:void(0);');
   				 //当节点没有选中的情况，导出s通讯录中所有的数据
   	 			window.location.href="${createLink(controller:'log',action:'export')}?format=pdf&extension=pdf&firstTime="+firstTime+"&secondTime="+secondTime+"&operateObject="+operateObject+"&logType="+logType;
   				 window.location.href=url;
   			});
   			 $(".excel").click(function(){
   				  var firstTime= $("#firstTime").val();
				  var secondTime =$("#secondTime").val();
				  var operateObject=$("#operateObject").val();
				  var logType=$("#logType").val();
 				$('.excel').attr('href','javascript:void(0);');
 				 //当节点没有选中的情况，导出s通讯录中所有的数据
 	 			window.location.href="${createLink(controller:'log',action:'export')}?format=excel&extension=xls&firstTime="+firstTime+"&secondTime="+secondTime+"&operateObject="+operateObject+"&logType="+logType;
 				 window.location.href=url;
   			});
               $('#btn_create').click(function(){
                	   var url = '${createLink(controller:'Log',action:'create')}';
                	   var selected = $('#log_grid').omTree('getSelected');
                	   window.location.href = url+'?dicTyp';
               });
               $('#btn_edit').click(function(){
            		   var url = '${createLink(controller:'Log',action:'edit')}';
            		   var selected = $('#log_grid').omGrid('getSelections',true);
            		   
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
                   		var selections = $('#log_grid').omGrid('getSelections',true);
                   		if(selections.length==0){
	                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                   		return;	
	                   	}
                   		$.omMessageBox.confirm({
                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                            onClose:function(v){
                                if(v){
                                	var deleteUrl = '${createLink(controller:'Log',action:'jsonDelete')}';
        	                   		var deleteIds = '';
        	                   		$.each(selections,function(i,item){
        		                   		deleteIds += item.id+'_';
        		                   	});
        		                   	$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
        		                   		var selections = $('#log_grid').omGrid('reload');
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
			$('#log_grid').omGrid({
				dataSource:'${createLink(controller:'Log',action:'jsonList')}',
				title:"<g:message code="default.list.label" args="[entityName]"/>",
				singleSelect:false,
				limit : 12, // 分页显示，每页显示30条
				height : 440,
				colModel:[{header : "${message(code: 'log.theSystem.label', default: 'theSystem')}", name : 'theSystem', width : 120, align : 'center'},
                          {header : "${message(code: 'log.logType.label', default: 'logType')}", name : 'logTypeName', width : 80, align : 'center'},
                          {header : "${message(code: 'log.userName.label', default: 'userName')}", name : 'userName', width : 80, align : 'center'},
						  {header : "${message(code: 'log.operateObject.label', default: 'operateOjbect')}", name : 'objectName', width : 120, align : 'center'},
                          {header : "${message(code: 'log.operateContent.label', default: 'operateContent')}", name : 'operateContent', width : 500, align : 'center',
							  renderer:function(value,rowData,rowIndex){
									if(value.length>=58){
										return '<span title="'+value+'">'+value.substring(0,56)+'...</span>';
									}else {
										return value
									}
								  }
	                          },
                          {header : "${message(code: 'log.createTime.label', default: 'createTime')}", name : 'createTime', width : 118, align : 'center'}
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
