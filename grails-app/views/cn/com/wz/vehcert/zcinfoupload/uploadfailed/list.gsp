<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfo" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zcinfo.label', default: 'zcinfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
				<div  style="margin:5px 8px 8px 4px;background-color: /*#efefef*/#FAFAFA;">
                    <a id="btn_upload" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >上传</a>
                    <a id="btn_overTime" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >补传</a>
                    <a id="btn_finish" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >完成</a>
                    <a id="btn_export" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >导出EXCEL</a>
				</div>
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
				   	                    var url = "${createLink(controller:'ZCInfoUpload',action:'jsonList')}";
				   	                    url+='?'+$('#form_query').serialize();
				   	                    $('#zcinfo_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $("#VINLast6").keydown(function(e){
	      				  if(e.keyCode==13){
                             $('#btn_query').click();
	      					  return false;
	      					}
	      		   		});
                $("#btn_export").click(function(){
                    $('.excel').attr('href','javascript:void(0);');
                    //当节点没有选中的情况，导出s通讯录中所有的数据
                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    var url="${createLink(controller:'ZCInfoUpload',action:'failexport')}?format=excel&extension=xlsx";
                    window.location.href=url;
                });
                function uploadByHand(){
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
                                var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadInfo')}';
                                var ids = '';
                                $.each(selections,function(i,item){
                                    ids += item.id+'_';
                                });
                                $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                    var selections = $('#zcinfo_grid').omGrid('reload');
                                    alert(data.msg);
                                },'json');
                            }
                        }
                    });
                }
                $('#btn_upload').click(function() {
                    var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
                    //这里利用Ajax删除数据
                    var selections = $('#zcinfo_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }else{
                        $("#div_message").html("");
                        var idsCheck = '';
                        $.each(selections,function(i,item){
                            idsCheck += item.id+'_';
                        });
                        $.post(urlCheck,{ids:idsCheck},function(dd,textStatus){
                            var d2=eval(dd);
                            if(d2.flag=='failed'){
                                var a=confirm(d2.msg);
                                if(a==true){
                                    uploadByHand();
                                }else{
                                   return;
                                }
                            }else{
                                uploadByHand();
                            }
                        });
                    }
                });

                $('#btn_finish').click(function(){
                    //这里利用Ajax删除数据
                    var selections = $('#zcinfo_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    $.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'确定完成吗?',
                        onClose:function(v){
                            if(v){
                                var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'finish')}';
                                var ids = '';
                                $.each(selections,function(i,item){
                                    ids += item.id+'_';
                                });
                                $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                    var selections = $('#zcinfo_grid').omGrid('reload');
                                    showTopTip(data.msg);
                                },'json');
                            }
                        }
                    });
                });
                $('#btn_overTime').click(function() {
                    var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
                    //这里利用Ajax删除数据
                    var selections = $('#zcinfo_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }else{
                        $("#div_message").html("");
                        var idsCheck = '';
                        $.each(selections,function(i,item){
                            idsCheck += item.id+'_';
                        });
                        $.post(urlCheck,{ids:idsCheck},function(dd,textStatus){
                            var d2=eval(dd);
                            if(d2.flag=='failed'){
                                var a=confirm(d2.msg);
                                if(a==true){
                                    overTime();
                                }else{
                                    return;
                                }
                            }else{
                                overTime();
                            }
                        });
                    }
                });
                function overTime(){
                    //这里利用Ajax删除数据
                    var selections = $('#zcinfo_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    $.omMessageBox.prompt({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'请填写补传原因',
                        onClose:function(v){
                            if(v){
                                if(v===false){
                                    return;
                                }
                                var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadOverTime')}';
                                var ids = '';
                                $.each(selections,function(i,item){
                                    ids += item.id+'_';
                                });
                                $.post(uploadUrl,{"ids":ids,"reason":v},function(data,textStatus){
                                    var selections = $('#zcinfo_grid').omGrid('reload');
                                    alert(data.msg);
                                },'json');
                            }
                        }
                    });
                }
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
                var url='${createLink(controller:'ZCInfoUpload',action:'getCompResult')}';
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
					dataSource:'${createLink(controller:'ZCInfoUpload',action:'jsonList')}?'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 0,
					height :500,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 150, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData;
                                    return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >查看</a>' +
                                            '&nbsp;' +
                                            '<a id="btn_upload1" class="btn_basic blue_black"  href="javascript:upload('+rowIndex+');">上传</a>'+
                                            '&nbsp;'+
                                            '<a id="btn_overTime1" class="btn_basic blue_black"  href="javascript:overTime('+rowIndex+');">补传</a>'+
                                             '&nbsp;'+
                                            '<a id="btn_finish1" class="btn_basic blue_black"  href="javascript:finish('+rowIndex+');">完成</a>';
                                }},
                            {header : "${message(code: 'zcinfo.veh_Zchgzbh.label', default: '整车合格证编号')}", name : 'veh_Zchgzbh', width : 100, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Fzrq.label', default: '发证日期')}", name : 'veh_Fzrq', width : 100, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clfl.label', default: '车辆分类')}", name : 'veh_Clfl', width : 80, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'veh_Clmc', width : 80, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'veh_Clxh', width : 80, align : 'center'},
		                    {header : "${message(code: 'zcinfo.veh_Csys.label', default: '车身颜色')}", name : 'veh_Csys', width : 80, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Dpxh.label', default: '底盘型号')}", name : 'veh_Dpxh', width : 80, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Dpid.label', default: '底盘ID')}", name : 'veh_Dpid', width : 80, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Clsbdh.label', default: '车辆识别代码')}", name : 'veh_Clsbdh', width : 130, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Cjh.label', default: '车架号')}", name : 'veh_Clsbdh', width : 120, align : 'center'},
                            {header : "${message(code: 'zcinfo.veh_Fdjh.label', default: '发动机号')}", name : 'veh_Fdjh', width : 100, align : 'center'},
                            {header : "${message(code: 'zcinfo.web_status.label', default: '状态')}", name : 'web_status', width :200,wrap:true, align : 'left',renderer:
                                    function(value, rowData, rowIndex){
                                        if(value==2){
                                            return '上传失败;返回代码：'+rowData.upload_Failddm;
                                        }else if(value==1) {
                                            return '上传成功;返回代码：'+rowData.upload_Failddm;
                                        } else{
                                            return '状态错误';
                                        }
                                    }
                            }


                    ]
				});
			}

            //转向查看页面
            function gotoview(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoUpload',action:'show')}?id='+data.id+'&suctype=0';
                window.location.href = url;
            }
            function upload(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                var ids = data.id;
                var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
                $.post(urlCheck,{ids:ids},function(dd,textStatus){
                    var d2=eval(dd);
                    if(d2.flag=='failed'){
                        var a=confirm(d2.msg);
                        if(a==true){
                            $.omMessageBox.confirm({
                                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                                content:'确定上传吗?',
                                onClose:function(v){
                                    if(v){
                                        var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadInfo')}';
                                        $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                            var selections = $('#zcinfo_grid').omGrid('reload');
                                            alert(data.msg);
                                        },'json');
                                    }
                                }
                            });
                        }else{
                            return;
                        }
                    }else{
                        $.omMessageBox.confirm({
                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                            content:'确定上传吗?',
                            onClose:function(v){
                                if(v){
                                    var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadInfo')}';
                                    $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                        var selections = $('#zcinfo_grid').omGrid('reload');
                                        alert(data.msg);
                                    },'json');
                                }
                            }
                        });
                    }
                });
            }
            function finish(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                $.omMessageBox.confirm({
                    title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                    content:'确定完成吗?',
                    onClose:function(v){
                        if(v){
                            var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'finish')}';
                            var ids = data.id+"_";
                            $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                                var selections = $('#zcinfo_grid').omGrid('reload');
                                showTopTip(data.msg);
                            },'json');
                        }
                    }
                });
            }
            function overTime(index){
                var data = $("#zcinfo_grid").omGrid("getData").rows[index];
                var ids = data.id;
                var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
                $.post(urlCheck,{ids:ids},function(dd,textStatus){
                    var d2=eval(dd);
                    if(d2.flag=='failed'){
                        var a=confirm(d2.msg);
                        if(a==true){
                            $.omMessageBox.prompt({
                                title:'补传原因',
                                content:'请填写补传原因！',
                                onClose:function(value){
                                    if(value===false){ //按了取消或ESC
                                        return;
                                    }
                                    if(value==''){
                                        alert('请填写补传原因');
                                        return false; //不关闭弹出窗口
                                    }

                                    var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadOverTime')}';
                                    var ids = data.id;
                                    $.post(uploadUrl,{"ids":ids,"reason":value},function(data,textStatus){
                                        var selections = $('#zcinfo_grid').omGrid('reload');
                                        alert(data.msg);
                                    },'json');
                                }
                            });
                        }else{
                            return;
                        }
                    }else{
                        $.omMessageBox.prompt({
                            title:'补传原因',
                            content:'请填写补传原因！',
                            onClose:function(value){
                                if(value===false){ //按了取消或ESC
                                    return;
                                }
                                if(value==''){
                                    alert('请填写补传原因');
                                    return false; //不关闭弹出窗口
                                }

                                var uploadUrl = '${createLink(controller:'ZCInfoUpload',action:'uploadOverTime')}';
                                var ids = data.id;
                                $.post(uploadUrl,{"ids":ids,"reason":value},function(data,textStatus){
                                    var selections = $('#zcinfo_grid').omGrid('reload');
                                    alert(data.msg);
                                },'json');
                            }
                        });
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