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
			<form id="form_query" style="margin:8px;height:60px;">
                <input type="hidden" name="hidden_type" id="hidden_type" value="1"/>
             <div id="form1">
			<table style="width:100%;border:0px;">
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfore.veh_Dphgzbh.label" default="旧底盘号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Dphgzbh" name="veh_Dphgzbh" maxlength="200"/></span></td>
                    <td width="50"><span><g:message code="zcinfore.veh_Zchgzbh_0.label" default="旧合格证编号" />：</span></td>
                    <td width="100" align="left"><span><g:textField id="veh_Zchgzbh_0" name="veh_Zchgzbh_0" maxlength="200"/></span></td>
                </tr>
				  <tr>
				    <td width="80" align="right"><span><g:message code="zcinfore.veh_cjh.label" default="旧车架号" />：</span></td>
				    <td width="100"><span><g:textField id="veh_Cjh" name="veh_Cjh" maxlength="200"/></span></td>
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
                        %{--<option value="">全  部</option>--}%
                        %{--<option value="2">未通过</option>--}%
                        <option value="1">已通过</option>
                        %{--<option value="0">未审核</option>--}%
                        </select>
                    </span>
                    <span style="padding-left: 80px;">类型：</span><select name="type" id="type">
                        <option value="1">基础变更</option>
                        <option value="2">日期变更</option>
                    </select></td>

                    <td align="left" valign="middle" colspan="2">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>

                </tr>
				</table>
             </div>


                <div id="form2">
                    <table style="width:100%;border:0px;">
                        <tr>
                            <td width="80" align="right"><span><g:message code="zcinfo.veh_Zchgzbh.label" default="整车合格证编号" />：</span></td>
                            <td width="100"><span><g:textField id="veh_Zchgzbh_rq" name="veh_Zchgzbh_rq" maxlength="200"/></span></td>
                            <td width="50"><span></span></td>
                            <td width="100" align="left"><span></span></td>
                        </tr>
                        <tr>
                            <td width="80" align="right"><span><g:message code="zcinfo.veh_Clxh.label" default="车辆型号" />：</span></td>
                            <td width="100"><span><g:textField id="veh_Clxh_rq" name="veh_Clxh_rq" maxlength="200"/></span></td>
                            <td width="50"><span><g:message code="zcinfo.veh_Fdjxh.label" default="发动机型号" />：</span></td>
                            <td width="100" align="left"><span><g:textField id="veh_Fdjxh_rq" name="veh_Fdjxh_rq" maxlength="200"/></span></td>
                        </tr>
                        <tr>
                            <td width="80" align="right"><span><g:message code="zcinfore.veh_Fzrq_Q.label" default="变更前日期" />：</span></td>
                            <td width="220"><span><c:dataPicker id="startTime1" name="startTime1"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span>到<span><c:dataPicker id="endTime1" name="endTime1"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span></td>
                            <td width="80" align="left"><span><g:message code="zcinfore.veh_Fzrq_H.label" default="变更后日期" />：</span></td>
                            <td width="220"><span><c:dataPicker id="startTime2" name="startTime2"  readOnly="true" dateFormat="yy-mm-dd"  ></c:dataPicker></span>到<span><c:dataPicker id="endTime2" name="endTime2"  readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span></td>
                        </tr>
                        <tr>
                            <td width="80" align="right"><span>状态：</span></td>
                            <td width="220"><span> <select name="veh_coc_statu_rq">
                                <option value="1">已通过</option>
                            </select>
                            </span>
                                <span style="padding-left: 80px;">类型：</span><select name="type1" id="type1">
                                <option value="1">基础变更</option>
                                <option value="2">日期变更</option>
                            </select></td>

                            <td align="left" valign="middle" colspan="2">
                                <input id="btn_query_rq" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                                <input id="btn_clear_rq" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                            </td>

                        </tr>
                    </table>
                </div>
			</form>
				<fieldset class="buttons" style="margin:55px 8px 8px 4px;">
                    <input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>
                    <export:formats formats="['excel','pdf']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="zcinfore_grid" style="border:0px"></div>
				</div>
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
				   	                    var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}";
				   	                    url+='?'+$('#form_query').serialize();
				   	                    $('#zcinfore_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $('#btn_clear').omButton({
	                       width:80,
	                   	   onClick:function(){
	        	   	           $("#veh_Cjh").val('');
                               $("#veh_Fdjh").val('');
                               $("#firstTime").val('');
                               $("#secondTime").val('');
                               $("#firstTime1").val('');
                               $("#secondTime1").val('');
                               $("#veh_coc_status").val('');

                               $("#veh_Zchgzbh_0").val('');
                               $("#veh_Dphgzbh").val('');
	        	           }
	                    });
                //绑定查询按钮事件
                $('#btn_query_rq').omButton({
                    width:80,
                    onClick:function(){
                        var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}";
                        url+='?'+$('#form_query').serialize();
                        $('#zcinfore_grid').omGrid('setData',url);
                    }
                });
                $('#btn_clear_rq').omButton({
                    width:80,
                    onClick:function(){
                        $("#veh_Cjh").val('');
                        $("#veh_Fdjh").val('');
                        $("#firstTime").val('');
                        $("#secondTime").val('');
                        $("#firstTime1").val('');
                        $("#secondTime1").val('');
                        $("#veh_coc_status").val('');

                        $("#veh_Zchgzbh_0").val('');
                        $("#veh_Dphgzbh").val('');

                        $("#veh_Zchgzbh_rq").val('');
                        $("#veh_Clxh_rq").val('');
                        $("#veh_Fdjxh_rq").val('');
                        $("#startTime1").val('');
                        $("#endTime1").val('');
                        $("#startTime2").val('');
                        $("#endTime2").val('');
                        $("#veh_Dphgzbh_rq").val('');

                    }
                });

                $("#veh_Dphgzbh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Zchgzbh_0").focus();
                        return false;
                    }
                });
                $("#veh_Zchgzbh_0").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Cjh").focus();
                        return false;
                    }
                });

                $("#veh_Cjh").keydown(function(e){
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


                $("#veh_Zchgzbh_rq").keydown(function(e){
                    if(e.keyCode==13){
                        return false;
                    }
                });

                $("#veh_Clxh_rq").keydown(function(e){
                    if(e.keyCode==13){
                        $('#veh_Fdjxh_rq').focus();
                        return false;
                    }
                });
                $("#veh_Fdjxh_rq").keydown(function(e){
                    if(e.keyCode==13){
                        $('#startTime1').focus();
                        return false;
                    }
                });
                $("#startTime1").keydown(function(e){
                    if(e.keyCode==13){
                        $('#endTime1').focus();
                        return false;
                    }
                });
                $("#endTime1").keydown(function(e){
                    if(e.keyCode==13){
                        $('#startTime2').focus();
                        return false;
                    }
                });
                $("#startTime2").keydown(function(e){
                    if(e.keyCode==13){
                        $('#endTime2').focus();
                        return false;
                    }
                });
                $("#endTime2").keydown(function(e){
                    if(e.keyCode==13){
                        $('#btn_query').click();
                        return false;
                    }
                });

	       	 	})
			// 加载时执行的语句
			$(function() {
                $("#startTime1").val('');
                $("#endTime1").val('');
                $("#startTime2").val('');
                $("#endTime2").val('');
                tabByDiv();
	           });
			//用户自定义方法
			//创建有些表格
			function buildRightGridByJC(){
                $("#hidden_type").val("1");
                $("#type").val("1");
				$('#zcinfore_grid').omGrid({
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:true,
					limit : 12,
					height :500,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 100, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:void(0);' onclick='downloadJS("+rowIndex+")'>下载</a>"
                                }},
                        {header : "${message(code: 'zcinfore.veh_coc_status.label', default: '审核状态')}", name : 'veh_coc_status', width : 60, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==1){
                                    return '<span style="color: green"> 审核通过</span>';
                                }else if(value==2){
                                    return '<span style="color: red"> 审核未通过</span>';
                                }else{
                                    return '<span style="color: blue"> 未审核</span>';
                                }
                            }
                        },
                        {header : "${message(code: 'zcinfore.veh_createTime.label', default: '申请日期')}", name : 'veh_createTime', width : 80, align : 'center'},
		                      {header : "${message(code: 'zcinfore.dealer.label', default: '经销商')}", name : 'dealer', width : 80, align : 'center'},
		                      {header : "${message(code: 'zcinfore.veh_Zchgzbh_0.label', default: '旧合格证编号')}", name : 'veh_Zchgzbh_0', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh.label', default: '旧车架号')}", name : 'veh_Cjh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Cpggh.label', default: '旧公告号')}", name : 'veh_Cpggh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh.label', default: '旧发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Zchgzbh_0_R.label', default: '新合格证编号')}", name : 'veh_Zchgzbh_0_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh_R.label', default: '新车架号')}", name : 'veh_Cjh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Cpggh_R.label', default: '新公告号')}", name : 'veh_Cpggh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh_R.label', default: '新发动机号')}", name : 'veh_Fdjh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmanname.label', default: '业务员姓名')}", name : 'salesmanname', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmantel.label', default: '业务员电话')}", name : 'salesmantel', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.changetime.label', default: '变更日期')}", name : 'changetime', width : 80, align : 'center'}
                    ]
				});
			}


            function buildRightGridByRQ(){
                $("#hidden_type").val("2");
                $("#type1").val("2");
                $('#zcinfore_grid').omGrid({
                    dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?'+$('#form_query').serialize(),
                    title:"<g:message code="default.list.label" args="[entityName]"/>",
                    singleSelect:true,
                    limit : 12,
                    height :440,
                    colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 100, align:'center', renderer:
                            function(value, rowData, rowIndex){
                                return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:void(0);' onclick='downloadJS("+rowIndex+")'>下载</a>"
                            }},
                        {header : "${message(code: 'zcinfore.veh_coc_status.label', default: '审核状态')}", name : 'veh_coc_status', width : 60, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==1){
                                    return '<span style="color: green"> 审核通过</span>';
                                }else if(value==2){
                                    return '<span style="color: red"> 审核未通过</span>';
                                }else{
                                    return '<span style="color: blue"> 未审核</span>';
                                }
                            }
                        },
                        {header : "${message(code: 'zcinfo.veh_Zchgzbh.label', default: '整车合格证编号')}", name : 'veh_Zchgzbh', width : 140, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'veh_Clxh', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'veh_Clmc', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Fdjxh.label', default: '发动机型号')}", name : 'veh_Fdjxh', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfo.veh_Zzbh.label', default: '纸张编号')}</font>", name : 'veh_Zzbh', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfore.veh_Fzrq_Q.label', default: '变更前日期')}</font>", name : 'veh_Fzrq', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfore.veh_Fzrq_H.label', default: '变更后日期')}</font>", name : 'veh_Fzrq_R', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfore.ceate_id_bg.label', default: '变更人')}", name : 'createName', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfore.create_time_bg.label', default: '变更时间')}", name : 'createTime', width : 120, align : 'center'}
                    ]
                });
            }

            //合格证审核后 pdf 下载
            function downloadJS(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var filename = data.veh_Zchgzbh+".pdf";
                var url = "${createLink(controller:'downLoad',action:'downFile')}?fileName="+filename+"&filePath="+data.upload_Path
                window.location.href = url;
            }

            $('#type').change(function(){
                tabByDiv();
            })
            $('#type1').change(function(){
                tabByDiv();
            })

            /// 切换当前容器
            function tabByDiv()
            {
                var divType = $('#type').val();
                $("#type").val("1")
                if(divType=='1'){ ////基础变更
                    buildRightGridByJC();
                    changeSearch("#form2")
                    changehiden('#form1')
                }else{   ////日期变更
                    buildRightGridByRQ();
                    changeSearch('#form1')
                    changehiden("#form2")
                }
            }

             ////更换查询状态
             function changeSearch(div)
             {
                 if(div=='#form1'){
                     $(div).hide();
                     $("#veh_Cjh").val('');
                     $("#veh_Fdjh").val('');
                     $("#firstTime").val('');
                     $("#secondTime").val('');
                     $("#firstTime1").val('');
                     $("#secondTime1").val('');
                     $("#veh_coc_status").val('');
                     $("#veh_Zchgzbh_0").val('');
                     $("#veh_Dphgzbh").val('');

                 }else {

                     $(div).hide();         ///form2
                     $("#veh_Zchgzbh_rq").val('');
                     $("#veh_Clxh_rq").val('');
                     $("#veh_Fdjxh_rq").val('');
                     $("#startTime1").val('');
                     $("#endTime1").val('');
                     $("#startTime2").val('');
                     $("#endTime2").val('');
                     $("#veh_Dphgzbh_rq").val('');

                 }
             }

         function changehiden(div)
         {
             $(div).show();
         }
		 </script>
	</body>
</html>