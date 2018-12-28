<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfo" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zcinfo.label', default: 'zcinfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
    <div id="page" style="background-color:white;">
        <div id="center-panel" style="margin-left:4px;border:1px;">
            <form id="form_query" style="margin:8px;">
                <table style="width:100%;border:0px;">
                    <tr>
                        <td width="80" align="right"><span><g:message code="zcinfo.insertTime.label" default="InsertTime" />：</span></td>
                        <td width="100"><span><c:dataPicker id="firstTime" name="firstTime" showTime="false"  ></c:dataPicker></span></td>
                        <td width='10'>至</td>
                        <td width="80" align="right"><span><c:dataPicker id="secondTime" name="secondTime"  showTime="false"></c:dataPicker></span></td>
                        <td width='10'></td>
                        <td align="left" valign="middle">
                            <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                            <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>

                        </td>
                    </tr>
                </table>
                <input type="hidden" name="needUpload" value="0"/>
            </form>
            <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <input id="btn_upload" type="button" class="export1" value="<g:message code="zcinfo.sc.label" default="上传" />"/>
                <export:formats formats="['excel']" style="border:0px; margin-top:-25px;margin-left:50px;"/>
            </fieldset>
            <div style="margin-right:8px;margin-top:8px;">
                <div id="zcinfo_grid" style="border:0px"></div>
            </div>
        </div>
    </div>

	 	<script>
			// 事件绑定
            var dialogId='';
            var initId='';
			$(function() {
					   $('#page').css({height:$(document).height()-16});
	                  //绑定查询按钮事件
	                   $('#btn_query').omButton({
		                   		width:80,
		                	   onClick:function(){
                                  var url = "${createLink(controller:'ZCInfo',action:'zcinfoUploadJson')}?"+$('#form_query').serialize();
                                  $('#zcinfo_grid').omGrid('setData',url);
				   	           }
		                   });

                    $(".excel").click(function(){
                        var firstTime=$("#firstTime").val();
                        var secondTime=$("#secondTime").val();
                        $('.excel').attr('href','javascript:void(0);');
                        dialogId=showTipDialog();
                        initId=setInterval("getResult();",'3000');
                        var url="${createLink(controller:'ZCInfoTemp',action:'export_search_load')}?format=excel&extension=xlsx&firstTime="+firstTime+"&secondTime="+secondTime;
                        window.location.href=url;
                    });

                    //绑定查询按钮事件
                    $('#btn_clear').omButton({
                        width:80,
                        onClick:function(){
                            $("#firstTime").val('');
                            $("#secondTime").val('');
                        }
                    });
	                   $("#VINLast6").keydown(function(e){
	      				  if(e.keyCode==13){
                             $('#btn_query').click();
	      					  return false;
	      					}
	      		   		});
                $('#btn_upload').click(function(){
                    //这里利用Ajax删除数据
                    var selections = $('#zcinfo_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    $.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'确定上传吗?',
                        onClose:function(v){
                            if(v){
                                var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadInfoByTemp')}';
                                var ids = '';
                                $.each(selections,function(i,item){
                                    ids += item.VEH_ZCHGZBH+'_';
                                });
                                $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                    var selections = $('#zcinfo_grid').omGrid('reload');
                                    alert(data.msg);
                                },'json');
                            }
                        }
                    });
                });
	       	 	})
			// 加载时执行的语句
			$(function() {
                $("#firstTime").val('')
                $("#secondTime").val('')
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
                var url='${createLink(controller:'ZCInfo',action:'getCompResult')}';
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
				$('#zcinfo_grid').omGrid({
					dataSource:'${createLink(controller:'ZCInfo',action:'zcinfoUploadJson')}?'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 12,
					height :500,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData;
                                    return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');">查看</a>&nbsp;<a id="btn_view" class="btn_basic blue_black"  href="javascript:upload('+rowIndex+');" >上传</a>';
                                }},
                            {header : "<font color='blue'>${message(code: 'zcinfo.insertTime.label', default: '下载日期')}</font>", name : 'INSERT_TIME', width : 120, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Zchgzbh.label', default: '整车合格证编号')}", name : 'VEH_ZCHGZBH', width : 120, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Fzrq.label', default: '发证日期')}", name : 'VEH_FZRQ', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clfl.label', default: '车辆分类')}", name : 'VEH_CLFL', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'VEH_CLMC', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'VEH_CLXH', width : 110, align : 'center'},
		                    {header : "${message(code: 'zcinfo.veh_Csys.label', default: '车身颜色')}", name : 'VEH_CSYS', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Dpxh.label', default: '底盘型号')}", name : 'VEH_DPXH', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Dpid.label', default: '底盘ID')}", name : 'VEH_DPID', width : 110, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clsbdh.label', default: '车辆识别代码')}", name : 'VEH_CLSBDH', width : 130, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Cjh.label', default: '车架号')}", name : 'VEH_CJH', width : 120, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Fdjh.label', default: '发动机号')}", name : 'VEH_FDJH', width : 110, align : 'center'}
                    ]
				});
			}

            //转向查看页面
            function gotoview(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoUpload',action:'showByTemp')}?id='+data.ID+'&suctype=0';
                window.location.href = url;
            }

            function upload(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                $.omMessageBox.confirm({
                    title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                    content:'确定上传吗?',
                    onClose:function(v){
                        if(v){
                            var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadInfoByTemp')}';
                            var ids =data.VEH_ZCHGZBH;
                            $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                var selections = $('#zcinfo_grid').omGrid('reload');
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