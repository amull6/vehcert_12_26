<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfoReplace" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zcinfore.label', default: '需要修改或撤销的合格证信息')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
			<form id="form_query" style="margin:8px;">
			<table style="width:100%;border:0px;">
				  <tr>
				    <td width="120" align="right"><span><g:message code="zcinfore.veh_Clsbdh.label" default="旧车辆识别代号" />：</span></td>
				    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200"/></span></td>
                      <td width="50"><span><g:message code="zcinfore.veh_Fdjh.label" default="旧发动机号" />：</span></td>
                      <td width="100" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" maxlength="200"/></span></td>
                      </tr>
                <tr>
                    <td width="120" align="right"><span>处理状态：</span></td>
                    <td width="220"><span> <select name="veh_managestatus" id="veh_managestatus">
                        <option value="">全  部</option>
                        <option value="0" selected="selected">待处理</option>
                        <option value="1">已完成</option>
                        </select>
                    </span></td>
                    <td width="80" align="right"><span>是否换号：</span></td>
                    <td width="220"><span>
                        <select name="changeType" id="changeType">
                            <option value="">全部</option>
                            <option value="1" >换号</option>
                            <option value="0" selected="selected">不换号</option>
                        </select>
                    </span></td>
                    <td align="left" valign="middle" colspan="1">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
				</table>
			</form>
                <div  style="margin:5px 8px 8px 4px;background-color: /*#efefef*/#FAFAFA;">
                    <a id="btn_delete" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >撤销</a>
                    <a id="btn_insert" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >上传</a>
                    <a id="btn_update" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >修改</a>
                    <a id="btn_export" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >导出EXCEL</a>
                </div>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="zcinfore_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
             var dialogId='';
             var initId='';
			$(function() {
					   $('#page').css({height:$(document).height()-16});
	                  //绑定查询按钮事件
	                   $('#btn_query').omButton({
		                   		width:80,
		                	   onClick:function(){
				   	                    var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonAmendbackList')}";
				   	                    url+='?'+$('#form_query').serialize();
				   	                    $('#zcinfore_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $('#btn_clear').omButton({
	                       width:80,
	                   	   onClick:function(){
	        	   	           $("#veh_Clsbdh").val('');
                               $("#veh_Fdjh").val('');
                               $("#veh_managestatus").val('');
	        	           }
	                    });
                $("#btn_export").click(function(){
                    $('#btn_export').attr('href','javascript:void(0);');
                    //当节点没有选中的情况，导出s通讯录中所有的数据
                    var url="${createLink(controller:'ZCInfoReplaceAuth',action:'exportReplace')}?format=excel&extension=xlsx";

                    url+='&'+$('#form_query').serialize();
                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    window.location.href=url;

                });

                $("#veh_Clsbdh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Fdjh").focus();
                        return false;
                    }
                });
                $("#veh_Fdjh").keydown(function(e){
                    if(e.keyCode==13){
                        $('#btn_query').click();
                        return false;
                    }
                });

                $('#btn_update').click(function(){
                     update();
                });
                $('#btn_insert').click(function(){
                    upload();
                });
                $('#btn_delete').click(function(){
                    del();
                });
	       	})
			// 加载时执行的语句
			$(function() {
				buildRightGrid()
	           });
			//用户自定义方法
             function showModelWait(){
                 var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
                 var title='系统提示';
                 //打开弹出框
                 var id=parent.showDialog(1,content,title,160,80);
                 return id;
             }



             //获取组合结果
             function getResult(){
                 var url='${createLink(controller:'ZCInfoReplaceAuth',action:'getCompResult')}';
                 $.post(url,{},function(data,textStatus){
                     //判断是否已处理完成
                     if(data!=null&&data=="success"){
                         parent.closeDialog(dialogId);
                         clearInterval(initId);
                     }else if(data=='error'){
                         alert("任务处理过程中出错！");
                         parent.closeDialog(dialogId);
                         clearInterval(initId);
                     }
                 });
             }
			//创建有些表格
			function buildRightGrid(){
				$('#zcinfore_grid').omGrid({
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonAmendbackList')}?'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 12,
					height :493,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 250, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData;
                                    if(data.veh_managestatus==0){
                                        return '<a  class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >查看</a>' +
                                                ' &nbsp;<a  class="btn_basic blue_black"  href="javascript:del();">撤销</a>'+
                                                ' &nbsp;<a  class="btn_basic blue_black"  href="javascript:upload();">上传</a>'+
                                                ' &nbsp;<a  class="btn_basic blue_black"  href="javascript:update();">修改</a>'+
                                                ' &nbsp;<a  class="btn_basic blue_black"  href="javascript:gotofinish('+rowIndex+');">完成</a>';
                                    } else{
                                        return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >查看</a>'
                                    }
                                }},
		                      {header : "${message(code: 'zcinfore.veh_Zchgzbh_0.label', default: '旧合格证编号')}", name : 'veh_Zchgzbh', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Clsbdh.label', default: '旧车辆识别代号')}", name : 'veh_Clsbdh', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Clxh.label', default: '旧公告号')}", name : 'veh_Clxh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh.label', default: '旧发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                               {header : "${message(code: 'zcinfore.veh_Edzzl.label', default: '旧额定载质量')}", name : 'veh_Edzzl', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Zchgzbh_0_R.label', default: '新合格证编号')}", name : 'veh_Zchgzbh_R', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Clsbdh_R.label', default: '新车辆识别代号')}", name : 'veh_Clsbdh_R', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_clxh_R.label', default: '新公告号')}", name : 'veh_Clxh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh_R.label', default: '新发动机号')}", name : 'veh_Fdjh_R', width : 80, align : 'center'},
                               {header : "${message(code: 'zcinfore.veh_Edzzl_R.label', default: '新额定载质量')}", name : 'veh_Edzzl_R', width : 80, align : 'center'}
                    ]
				});
			}

            //转向查看页面
            function gotoview(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'genghuanShow')}?id='+data.id+'&type=1';
                 window.location.href = url;
            }
            //完成处理操作
            function gotofinish(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'tofinish')}?id='+data.id;
                $.post(url,function(data,textStatus){
                    $('#zcinfore_grid').omGrid('reload');
                    var content = "<div class='message'>"+data+"</div>";
                    showTopTip(content);
                },'text');
            }

             function del(){
                 var selections = $('#zcinfore_grid').omGrid('getSelections',true);
                 if(selections.length==0){
                     alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                     return;
                 }
                 $.omMessageBox.prompt({
                     title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                     content:'请填写撤销原因',
                     onClose:function(v){
                         if(v){
                             if(v==false){
                                 return;
                             }
                             if(v==""){
                                 alert("请填写撤销原因");
                                 return false;
                             }
                             var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadDelete')}';
                             var ids = '';
                             $.each(selections,function(i,item){
                                 ids += item.veh_Zchgzbh_0+";"+item.veh_Type+'_';
                             });
                             $.post(uploadUrl,{"ids":ids,"reason":v},function(data,textStatus){
                                 var selections = $('#zcinfore_grid').omGrid('reload');
                                 alert(data.msg);
                             },'json');
                         }
                     }
                 });
             }
             function update(){
                 //这里利用Ajax删除数据
                 var selections = $('#zcinfore_grid').omGrid('getSelections',true);
                 if(selections.length==0){
                     alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                     return;
                 }
                 $.omMessageBox.prompt({
                     title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                     content:'请填写修改原因',
                     onClose:function(v){
                         if(v){
                             if(v==false){
                                 return;
                             }
                             if(v==""){
                                 alert("请填写修改原因");
                                 return false;
                             }
                             var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadUpdate')}';
                             var ids = '';
                             $.each(selections,function(i,item){
                                 ids += item.veh_Zchgzbh_R+'_';
                             });
                             $.post(uploadUrl,{"ids":ids,"reason":v},function(data,textStatus){
                                 var selections = $('#zcinfore_grid').omGrid('reload');
                                 alert(data.msg);
                             },'json');
                         }
                     }
                 });
             }
             function upload(){
                 //这里利用Ajax删除数据
                 var selections = $('#zcinfore_grid').omGrid('getSelections',true);
                 if(selections.length==0){
                     alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                     return;
                 }
                 $.omMessageBox.confirm({
                     title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                     content:'确定上传吗?',
                     onClose:function(v){
                         if(v){
                             var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'upload')}';
                             var ids = '';
                             $.each(selections,function(i,item){
                                 ids += item.veh_Zchgzbh_R+'_';
                             });
                             $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                 var selections = $('#zcinfore_grid').omGrid('reload');
                                 alert(data.msg);
                             },'json');
                         }
                     }
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