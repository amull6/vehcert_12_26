<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfoReplace" %>
<%@ page import="cn.com.wz.parent.system.organization.Organization" %>
<html xmlns="http://www.w3.org/1999/html">
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zcinfore.label', default: '合格证更换申请')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;"> 
			<form id="form_query" style="margin:8px;height:40px;">
			<table style="width:100%;border:0px;">
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfore.distributor.label" default="distributor" />：</span></td>
                    <td width="100"><span><g:select id="distributor" name="distributor.id" from="${cn.com.wz.parent.system.organization.Organization.createCriteria().list({parent{eq('organCode','distributor')}})}" optionKey="id"  value="${id}" noSelection="['':'全部']" onchange="changeHtml()" class="one-to-one" /></span></td>
                    <td width="50" ><span><g:message code="zcinfore.realName.label" default="distributor" />：</span></td>
                    <td width="100"><span id="change"><select id="user">
                        <option value="">请选择片区</option>
                    </select></span></td>
                </tr>
				  <tr>
				    <td width="80" align="right"><span><g:message code="zcinfore.veh_cjh.label" default="旧车架号" />：</span></td>
				    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200"/></span></td>
                      <td width="50"><span><g:message code="zcinfore.veh_Fdjh.label" default="旧发动机号" />：</span></td>
                      <td width="100" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" maxlength="200"/></span></td>
                      </tr>
                <tr>
                      <td width="80" align="right"><span><g:message code="zcinfore.veh_createTime.label" default="Apply Time" />：</span></td>
                      <td width="220"><span><c:dataPicker id="firstTime" name="firstTime"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span>到<span><c:dataPicker id="secondTime" name="secondTime"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span></td>
                    <td width="80" align="left"><span><g:message code="zcinfore.authTime.label" default="Audit Time" />：</span></td>
                    <td width="220"><span><c:dataPicker id="firstTime1" name="firstTime1"  readOnly="true" dateFormat="yy-mm-dd"  ></c:dataPicker></span>到<span><c:dataPicker id="secondTime1" name="secondTime1"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span></td>
				  </tr>
                <tr>
                    <td width="80" align="right"><span>状态：</span></td>
                    <td width="220"><span> <select name="veh_coc_status" id="veh_coc_status">
                        <option value="">全  部</option>
                        <option value="2">未通过</option>
                        <option value="1">已通过</option>
                        <option value="0">未审核</option>
                        </select>
                    </span></td>
                    <td align="left" valign="middle" colspan="2">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
				</table>
			</form>
				<fieldset class="buttons" style="margin:95px 8px 8px 4px;">
                    <input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>
                    <export:formats formats="['excel']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="zcinfore_grid" style="border:0px"></div>
				</div>
			</div>	   
    	</div>
	  	
	 	<script>
             var dialogId='';
             var initId='';
             // 事件绑定
			$(function() {
				$('#page').css({height:$(document).height()-16});
                //绑定查询按钮事件
	                   $('#btn_query').omButton({
		                   		width:80,
		                	   onClick:function(){
                                        var id=$('#user').val();
				   	                    var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonChaXunList')}";
				   	                    url+='?'+$('#form_query').serialize()+'&user.id='+id;
				   	                    $('#zcinfore_grid').omGrid('setData',url);
				   	                  }
		                   });
                $(".excel").click(function(){
                    $('.excel').attr('href','javascript:void(0);');
                    //当节点没有选中的情况，导出s通讯录中所有的数据
                    var url="${createLink(controller:'ZCInfoReplaceAuth',action:'chaxunexport')}?format=excel&extension=xlsx";
                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    window.location.href=url;

                });
	                   $('#btn_clear').omButton({
	                       width:80,
	                   	   onClick:function(){
	        	   	           $("#veh_Clsbdh").val('');
                               $("#veh_Fdjh").val('');
                               $("#firstTime").val('');
                               $("#secondTime").val('');
                               $("#firstTime1").val('');
                               $("#secondTime1").val('');
                               $("#veh_coc_status").val('');

	        	           }
	                    });
                $("#veh_Clsbdh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Fdjh").focus();
                        return false;
                    }
                });
                $("#veh_Fdjh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#firstTime").focus();
                        return false;
                    }
                });
                $("#firstTime").keydown(function(e){
                    if(e.keyCode==13){
                        $("#secondTime").focus();
                        return false;
                    }
                });
                $("#secondTime").keydown(function(e){
                    if(e.keyCode==13){
                        $("#firstTime1").focus();
                        return false;
                    }
                });
                $("#firstTime1").keydown(function(e){
                    if(e.keyCode==13){
                        $("#secondTime1").focus();
                        return false;
                    }
                });
                $("#secondTime1").keydown(function(e){
                    if(e.keyCode==13){
                        $('#btn_query').click();
                        return false;
                    }
                });
                        $("#firstTime").val('');
                        $("#secondTime").val('');
                        $("#firstTime1").val('');
                        $("#secondTime1").val('');
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
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonChaXunList')}',
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:true,
					limit : 12,
					height :493,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 80, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData;
                                    return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+')">查看</a>'
                                }},
                        {header : "${message(code: 'zcinfore.veh_coc_status.label', default: '审核状态')}", name : 'veh_coc_status', width : 60, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==1){
                                    return '已通过审核';
                                }else if(value==2){
                                    return '未通过审核';
                                }else{
                                    return '未审核';
                                }
                            }
                        },
                              {header : "${message(code: 'zcinfo.veh_Zzbh.label', default: '纸张编号')}", name : 'veh_Zzbh', width : 150, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_createTime.label', default: '申请日期')}", name : 'createTime', width : 150, align : 'center'},
		                      {header : "${message(code: 'zcinfore.dealer.label', default: '经销商')}", name : 'createrId', width : 80, align : 'center'},
		                      {header : "${message(code: 'zcinfore.veh_Zchgzbh_0.label', default: '旧合格证编号')}", name : 'veh_Zchgzbh', width : 105, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh.label', default: '旧车架号')}", name : 'veh_Clsbdh', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Clxh.label', default: '旧公告号')}", name : 'veh_Clxh', width : 100, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh.label', default: '旧发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Zchgzbh_0_R.label', default: '新合格证编号')}", name : 'veh_Zchgzbh_R', width : 105, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh_R.label', default: '新车架号')}", name : 'veh_Clsbdh_R', width : 120, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_clxh_R.label', default: '新公告号')}", name : 'veh_Clxh_R', width : 100, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh_R.label', default: '新发动机号')}", name : 'veh_Fdjh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmanname.label', default: '业务员姓名')}", name : 'salesmanname', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmantel.label', default: '业务员电话')}", name : 'salesmantel', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.authTime.label', default: '审核日期')}", name : 'authTime', width : 150, align : 'center'}
                    ]
				});
			}
            function changeHtml(){
                var distributor=$('#distributor').val();
                var url='${createLink(controller:'ZCInfoReplaceAuth',action:'findUserDetail')}?distributor='+distributor;
                 $.post(url,{},function(data,textStatus){
                     var data= eval( "(" + data + ")" );
                     var opSize=$('#user option').size();
                     if(opSize<data.total){
                         for(var i=0;i<data.total-opSize;i++){
                             $('#user').append('<option></option>')
                         }
                     }
                     for(var i=0;i<data.total;i++){
                         $('#user option').get(i).value=data.rows[i].id;
                         $('#user option').get(i).text=data.rows[i].realName;
                         //alert(data.rows[i].realName);
                     }

                    },'text');
            }

                //转向查看页面
            function gotoview(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'genghuanShow')}?id='+data.id+'&type=0';
                 window.location.href = url;
            }
		 </script>
	</body>
</html>