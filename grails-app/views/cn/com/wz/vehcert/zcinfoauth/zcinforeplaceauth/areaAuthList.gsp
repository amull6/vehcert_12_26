<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfoReplace" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zcinfore.label', default: '合格证更换申请')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:98%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;">
			<form id="form_query" style="margin:2px;height:40px;">
			<table style="width:100%;border:0px;">
				  <tr>
				    <td width="80px" align="right"><span><g:message code="zcinfore.veh_cjh.label" default="旧车架号" />：</span></td>
				    <td width="125px" align='left'><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200" style="width: 125px"/></span></td>
                      <td width="80px" align="right"><span><g:message code="zcinfore.veh_Fdjh.label" default="旧发动机号" />：</span></td>
                      <td width="60px" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" maxlength="200" style="width: 90px"/></span></td>

                      <td width="80" align="right"><span><g:message code="zcinfore.veh_createTime.label" default="Apply Time" />：</span></td>
                      <td width="220"><span><c:dataPicker id="firstTime" name="firstTime" readOnly="true" dateFormat="yy-mm-dd" style="width: 75px" ></c:dataPicker></span>到<span><c:dataPicker id="secondTime" name="secondTime"  readOnly="true" dateFormat="yy-mm-dd" style="width: 75px"></c:dataPicker></span></td>

                     <td width="60px"  align="right"><span>审核状态：</span></td>
                      <td><span>
                          <select name="area_status" id="area_status" style="width: 90px">
                              <option value="All">全部</option>
                              <option value="0" selected=“selected”>未审核</option>
                              <option value="1">审核通过</option>
                              <option value="2">审核未通过</option>
                          </select>
                      </span></td>

                      <td align="left" valign="middle" colspan="2">
                      <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                      <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                          <g:hiddenField name="msg" id='msg' value="${params?.msg}"/>
                      </td>
                  </tr>
				</table>
			</form>
				<fieldset class="buttons" style="margin:2px 8px 8px 4px;">
                    <input id="btn_export" type="button"   class="excel export1"  value="${message(code: 'default.button.export.label', default: 'Export')}"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="zcinfore_grid" style="border:0px"></div>
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
                                   var id=$('#user').val();
				   	                    var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByArea')}";
				   	                    url+='?searchType=1&'+$('#form_query').serialize();
				   	                    $('#zcinfore_grid').omGrid('setData',url);
				   	           }
		                   });
                var msg=$('#msg').val()
                if(msg){
                    alert(msg);
                }

                $(".excel").click(function(){
                    $('.excel').attr('href','javascript:void(0);');
                    var veh_Clsbdh=$("#veh_Clsbdh").val();
                    var veh_Fdjh=$("#veh_Fdjh").val();
                    //当节点没有选中的情况，导出s通讯录中所有的数据
                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    var url="${createLink(controller:'ZCInfoReplaceAuth',action:'rePlaceTwoTimesExport')}?format=excel&extension=xlsx";
                    url+='&searchType=1&'+$('#form_query').serialize();
                    window.location.href=url;
                });
	                   $('#btn_clear').omButton({
	                       width:80,
	                   	   onClick:function(){
                                  $("#veh_Clsbdh").val('');
                                  $("#veh_Fdjh").val('');
                                  $("#firstTime").val('');
                                  $("#secondTime").val('');
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
                        $('#btn_query').click();
                        return false;
                    }
                });
                $("#firstTime").val('');
                $("#secondTime").val('');
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
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByArea')}?area_status=0&searchType=1',
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
                              {header : "申请日期", name : 'createTime', width : 150, align : 'center'},
		                      {header : "经销商", name : 'createrName', width : 80, align : 'center'},
		                      {header : "旧合格证编号", name : 'veh_Zchgzbh', width : 110, align : 'center'},
                              {header : "旧车架号", name : 'veh_Clsbdh', width : 130, align : 'center'},
                              {header : "旧车辆型号", name : 'veh_Clxh', width : 80, align : 'center'},
                              {header : "旧发动机号", name : 'veh_Fdjh', width : 80, align : 'center'},
                              {header : "旧发动机型号", name : 'veh_Fdjxh', width : 80, align : 'center'},
                              {header : "新合格证编号", name : 'veh_Zchgzbh_R', width : 110, align : 'center'},
                              {header : "新车架号", name : 'veh_Clsbdh_R', width : 130, align : 'center'},
                              {header : "新车辆型号", name : 'veh_Clxh_R', width : 80, align : 'center'},
                              {header : "新发动机号", name : 'veh_Fdjh_R', width : 80, align : 'center'},
                              {header : "新发动机型号", name : 'veh_Fdjxh_R', width : 80, align : 'center'},
                              {header : "业务员姓名", name : 'salesmanname', width : 80, align : 'center'},
                              {header : "业务员电话", name : 'salesmantel', width : 80, align : 'center'},
                              {header : "审核状态", name : 'area_status', width : 60, align : 'center',
                                renderer:function(value,rowData,rowIndex){
                                    if(value==1){
                                        return '已通过审核';
                                    }else if(value==2){
                                        return '未通过审核';
                                    }else{
                                        return '未审核';
                                    }
                                }
                              }


                    ]
				});
			}
            //转向查看审核页面
            function gotoview(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'areaAuthView')}?id='+data.id;
                 window.location.href = url;

            }
		 </script>
	</body>
</html>