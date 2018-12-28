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
                    <form id="form_query" style="margin:8px;">
                        <input type="hidden" name="hidden_type" id="hidden_type" value="1"/>
                        <table style="width:100%;border:0px;">
                            <g:if test="${searchType=='1'}">
                                <tr>
                                    <td width="80" align="right"><span><g:message code="zcinfore.distributor.label" default="distributor" />：</span></td>
                                    <td width="100"><span><g:select id="distributor" name="distributor.id" from="${cn.com.wz.parent.system.organization.Organization.createCriteria().list({
                                        parent{eq('organCode','distributor')}
                                        order('organCode','asc')
                                    })}" optionKey="id"  value="${id}" onchange="changeHtml()" noSelection="['':'全部']" class="one-to-one" /></span></td>
                                    <td width="50" ><span><g:message code="zcinfore.realName.label" default="distributor" />：</span></td>
                                    <td width="100"><span id="change"><select id="user">
                                        <option value="" selected="selected">全部</option>
                                    </select></span></td>
                                </tr>
                            </g:if>

                            <tbody id="form1">
                                <g:if test="${searchType=='0'}">
                                    <tr>
                                        <td width="80" align="right"><span><g:message code="zcinfore.veh_Dphgzbh.label" default="旧底盘号" />：</span></td>
                                        <td width="100"><span><g:textField id="veh_Dphgzbh" name="veh_Dphgzbh" maxlength="200"/></span></td>
                                        <td width="50"><span><g:message code="zcinfore.veh_Zchgzbh.label" default="旧合格证编号" />：</span></td>
                                        <td width="100" align="left"><span><g:textField id="veh_Zchgzbh" name="veh_Zchgzbh" maxlength="200"/></span></td>
                                    </tr>
                                </g:if>
                                <tr>
                                    <td width="80" align="right"><span><g:message code="zcinfore.veh_Clsbdh.label" default="原车架号" />：</span></td>
                                    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200"/></span></td>
                                      <td width="50"><span><g:message code="zcinfore.veh_Fdjh.label" default="原发动机号" />：</span></td>
                                      <td width="100" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" maxlength="200"/></span></td>
                                </tr>
                                <tr>
                                    <td width="80" align="right"><span><g:message code="zcinfore.veh_Zchgzbh_0_R.label" default="新完整合格证编号" />：</span></td>
                                    <td width="100"><span><g:textField id="veh_Zchgzbh_0_R" name="veh_Zchgzbh_0_R" maxlength="200"/></span></td>
                                    <td width="50"><span><g:message code="zcinfore.veh_Clsbdh_R.label" default="新车架号" />：</span></td>
                                    <td width="100" align="left"><span><g:textField id="veh_Clsbdh_R" name="veh_Clsbdh_R" maxlength="200"/></span></td>
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
                                        <option value="0">未审核</option>
                                        <option value="1">已通过</option>
                                        <option value="2">未通过</option>
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
                            </tbody>
                            <tbody id="form2">
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
                                    <td width="220"><span><c:dataPicker id="startTime1" name="startTime1"  readOnly="true" dateFormat="yy年mm月dd日" ></c:dataPicker></span>
                                        到<span><c:dataPicker id="endTime1" name="endTime1"  readOnly="true" dateFormat="yy年mm月dd日" ></c:dataPicker></span></td>
                                    <td width="80" align="left"><span><g:message code="zcinfore.veh_Fzrq_H.label" default="变更后日期" />：</span></td>
                                    <td width="220"><span><c:dataPicker id="startTime2" name="startTime2"  readOnly="true" dateFormat="yy年mm月dd日"  ></c:dataPicker></span>
                                        到<span><c:dataPicker id="endTime2" name="endTime2"  readOnly="true" dateFormat="yy年mm月dd日" ></c:dataPicker></span></td>
                                </tr>
                                <tr>
                                    <td width="80" align="right"><span>状态：</span></td>
                                    <td width="220">
                                       <span>
                                            <select name="veh_coc_statu_rq" id="veh_coc_statu_rq">
                                                <option value="1">已通过</option>
                                            </select>
                                        </span>
                                        <span style="padding-left: 80px;">类型：</span><select name="type1" id="type1">
                                        <option value="1">基础变更</option>
                                        <option value="2">日期变更</option>
                                    </select></td>

                                    <td align="left" valign="middle" colspan="2">
                                        <input  type='hidden' id="searchType" value='${searchType}'>
                                        <input id="btn_query_rq" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                                        <input id="btn_clear_rq" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                <div >
                    <div id="export1">
                        <fieldset class="buttons" style="margin:0px 8px 8px 4px;line-height:10px;">
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Crm" type="button" class="export1" value="<g:message code="default.button.transToCrmSap.label" default="Export" />"/>
                            </c:resourceAuth>
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Crm_select" type="button" class="export1" value="<g:message code="default.button.transToCrmSap2.label" default="Export" />"/>
                            </c:resourceAuth>
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Sap" type="button" class="export1" value="<g:message code="default.button.transToSap.label" default="Export" />"/>
                            </c:resourceAuth>
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Sap_Failure" type="button" class="export1" value="<g:message code="default.button.transToSapFailure.label" default="Export" />"/>
                            </c:resourceAuth>
                                <a style="line-height:10px;"></a>
                                <export:formats formats="['excel']" style="border:0px;margin-left:800px;margin-top:-18px;"/>
                        </fieldset>
                    </div>
                    <div id="export2">
                        <fieldset class="buttons" style="margin:0px 8px 8px 4px;line-height:10px;">
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Crm" type="button" class="export1" value="<g:message code="default.button.transToCrmSap.label" default="Export" />"/>
                            </c:resourceAuth>
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Crm_select" type="button" class="export1" value="<g:message code="default.button.transToCrmSap2.label" default="Export" />"/>
                            </c:resourceAuth>
                            <c:resourceAuth resourceCode="dv_crm_transport">
                                <input id="btn_Sap" type="button" class="export1" value="<g:message code="default.button.transToSap.label" default="Export" />"/>
                            </c:resourceAuth>
                                <a style="line-height:10px;"></a>
                                <export:formats formats="['excel']" style="border:0px;margin-left:800px;margin-top:-18px;"/>
                        </fieldset>
                    </div>
                </div>
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
                $(".excel").click(function(){

                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    if($("#searchType").val()=='1'){
                        if($("#type1").val()=='1'){
                            var veh_Clsbdh=$("#veh_Clsbdh").val();
                            var veh_Fdjh=$("#veh_Fdjh").val();
                            var firstTime=$("#firstTime").val();
                            var secondTime=$("#secondTime").val();
                            var firstTime1=$("#firstTime1").val();
                            var secondTime1=$("#secondTime1").val();
                            var type=$("#type").val();
                            var id=$('#user').val();
                            var idD=$('#distributor').val();
                            var veh_coc_status=$("#veh_coc_status").val();
                            $('.excel').attr('href','javascript:void(0);');
                            var url="${createLink(controller:'ZCInfoReplaceAuth',action:'export_jc')}?format=excel&extension=xlsx&hidden_type="+type+"&searchType=${searchType}&veh_Clsbdh="+veh_Clsbdh+"&veh_Fdjh="+veh_Fdjh+"&firstTime="+firstTime+"&secondTime="+secondTime+"&firstTime1="+firstTime1+"&secondTime1="+secondTime1+"&veh_coc_status="+veh_coc_status+"&user.id="+id+"&distributor.id="+idD;
                           window.location.href=url;
                     }else if($("#type1").val()=='2'){
                        var veh_Zchgzbh_rq=$("#veh_Zchgzbh_rq").val();
                        var veh_Clxh_rq=$("#veh_Clxh_rq").val();
                        var veh_Fdjxh_rq=$("#veh_Fdjxh_rq").val();
                        var startTime1=$("#startTime1").val();
                        var endTime1=$("#endTime1").val();
                        var startTime2=$("#startTime2").val();
                        var endTime2=$("#endTime2").val();
                        var veh_coc_statu_rq=$("#veh_coc_statu_rq").val();
                        $('.excel').attr('href','javascript:void(0);');
                        var url="${createLink(controller:'ZCInfoReplaceAuth',action:'export_rq')}?format=excel&extension=xlsx&hidden_type=2&searchType=${searchType}&veh_Zchgzbh_rq="+veh_Zchgzbh_rq+"&veh_Clxh_rq="+veh_Clxh_rq+"&veh_Fdjxh_rq="+veh_Fdjxh_rq+"&startTime1="+startTime1+"&endTime1="+endTime1+"&startTime2="+startTime2+"&endTime2="+endTime2+"&veh_coc_statu_rq="+veh_coc_statu_rq;
                        window.location.href=url;
                    }

                  }else{

                        if($("#type1").val()=='2' && $("#type").val()=='2'){
                            var veh_Zchgzbh_rq=$("#veh_Zchgzbh_rq").val();
                            var veh_Clxh_rq=$("#veh_Clxh_rq").val();
                            var veh_Fdjxh_rq=$("#veh_Fdjxh_rq").val();
                            var startTime1=$("#startTime1").val();
                            var endTime1=$("#endTime1").val();
                            var startTime2=$("#startTime2").val();
                            var endTime2=$("#endTime2").val();
                            var veh_coc_statu_rq=$("#veh_coc_statu_rq").val();
                            $('.excel').attr('href','javascript:void(0);');
                            var url="${createLink(controller:'ZCInfoReplaceAuth',action:'export_rq')}?format=excel&extension=xlsx&hidden_type=2&searchType=${searchType}&veh_Zchgzbh_rq="+veh_Zchgzbh_rq+"&veh_Clxh_rq="+veh_Clxh_rq+"&veh_Fdjxh_rq="+veh_Fdjxh_rq+"&startTime1="+startTime1+"&endTime1="+endTime1+"&startTime2="+startTime2+"&endTime2="+endTime2+"&veh_coc_statu_rq="+veh_coc_statu_rq;
                            window.location.href=url;
                        }else{
                            if($("#type").val()=='1'){
                                var veh_Dphgzbh=$("#veh_Dphgzbh").val();
                                var veh_Zchgzbh=$("#veh_Zchgzbh").val();
                                var veh_Clsbdh=$("#veh_Clsbdh").val();
                                var veh_Fdjh=$("#veh_Fdjh").val();
                                var firstTime=$("#firstTime").val();
                                var secondTime=$("#secondTime").val();
                                var firstTime1=$("#firstTime1").val();
                                var secondTime1=$("#secondTime1").val();
                                var veh_coc_status=$("#veh_coc_status").val();
                                $('.excel').attr('href','javascript:void(0);');
                                var url="${createLink(controller:'ZCInfoReplaceAuth',action:'export_jc')}?format=excel&extension=xlsx&hidden_type=1&searchType=${searchType}&veh_Dphgzbh="+veh_Dphgzbh+"&veh_Zchgzbh="+veh_Zchgzbh+"&veh_Clsbdh="+veh_Clsbdh+"&veh_Fdjh="+veh_Fdjh+"&firstTime="+firstTime+"&secondTime="+secondTime+"&firstTime1="+firstTime1+"&secondTime1="+secondTime1+"&veh_coc_status="+veh_coc_status;
                                window.location.href=url;
                            }
                        }
                    }

                });

                //绑定查询按钮事件
                $('#btn_query').omButton({
                    width:80,
                   onClick:function(){
                            var id=$('#user').val();
                            var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?searchType=${searchType}&user.id="+id;
                            url+='&'+$('#form_query').serialize();
                           $('#zcinfore_grid').omGrid('setData',url);
                          }
                });
                //绑定到传输CRM事件
                $('#btn_Crm').click(function () {
                    var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'transToCrm')}";
                    $.post(url,{},function(data,textStatus){
                        alert(data);
                    });
                    }
                );
                //绑定到传输SAP事件
                $('#btn_Sap').click(function () {
                        var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'transToSap')}";
                        $.post(url,{},function(data,textStatus){
                            alert(data);
                        });
                    }
                );
                //绑定到失败再次传输SAP事件
                $('#btn_Sap_Failure').click(function () {
                            var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'transToSapFailure')}";
                            $.post(url,{},function(data,textStatus){
                                alert(data);
                            });
                        }
                );
                //选择传输CRM事件
                $('#btn_Crm_select').click(function () {
                    var selections = $('#zcinfore_grid').omGrid('getSelections', true);
                    if (selections.length == 0) {
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    var flag = 0;
                    $.each(selections, function (i, item) {
                        if(item.transToCrm==2){
                            flag =1;
                            return false;
                        }
                    });
                    var str ='确认传输到SAP/CRM？'
                    if(flag == 1){
                        var str ='存在传输到CRM成功的变更信息，是否重传？'
                    }
                    $.omMessageBox.confirm({
                        title: '${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content: str,
                        onClose: function (v) {
                            if (v) {
                                var transUrl = '${createLink(controller:'ZCInfoReplaceAuth',action:'transToCrmOfSelect')}';
                                var transIds = '';
                                $.each(selections, function (i, item) {
                                    transIds += item.id + '_';
                                });
                                $.post(transUrl, {"transIds": transIds}, function (data, textStatus) {
                                    var content = data;
                                    showTopTip(content);
                                }, 'text');
                            }
                        }
                    });
                });

                $('#btn_clear').omButton({
                   width:80,
                   onClick:function(){
                       $("#veh_Clsbdh").val('');
                       $("#veh_Clsbdh_R").val('');
                       $("#veh_Fdjh").val('');
                       $("#firstTime").val('');
                       $("#secondTime").val('');
                       $("#firstTime1").val('');
                       $("#secondTime1").val('');
                       $("#veh_coc_status").val('');

                       $("#veh_Zchgzbh").val('');
                       $("#veh_Zchgzbh_0_R").val('');
                       $("#veh_Dphgzbh").val('');
                   }
                });
                //绑定查询按钮事件
                $('#btn_query_rq').omButton({
                    width:80,
                    onClick:function(){
                        var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?searchType=${searchType}";
                        url+='&'+$('#form_query').serialize();
                        $('#zcinfore_grid').omGrid('setData',url);
                    }
                });
                $('#btn_clear_rq').omButton({
                    width:80,
                    onClick:function(){
                        $("#veh_Clsbdh").val('');
                        $("#veh_Fdjh").val('');
                        $("#firstTime").val('');
                        $("#secondTime").val('');
                        $("#firstTime1").val('');
                        $("#secondTime1").val('');
                        $("#veh_coc_status").val('');

                        $("#veh_Zchgzbh").val('');
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
                $("#veh_Clsbdh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Fdjh").focus();
                        return false;
                    }
                });
                $("#veh_Fdjh").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Zchgzbh_0_R").focus();
                        return false;
                    }
                });
                $("#veh_Zchgzbh_0_R").keydown(function(e){
                    if(e.keyCode==13){
                        $("#veh_Clsbdh_R").focus();
                        return false;
                    }
                });
                $("#veh_Clsbdh_R").keydown(function(e){
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
	       	});
			// 加载时执行的语句
			$(function() {
                $("#startTime1").val('');
                $("#endTime1").val('');
                $("#startTime2").val('');
                $("#endTime2").val('');
                //初始时基础变更查询
                tabByDiv('1');
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
			function buildRightGridByJC(){
                $("#hidden_type").val("1");
				$('#zcinfore_grid').omGrid({
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?searchType=${searchType}&'+$('#form_query').serialize(),
					title:"<g:message code="default.list.label" args="[entityName]"/>",
					singleSelect:false,
					limit : 10,
					height :440,
					colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 150, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData;

                                    <g:if  test="${searchType =='0'}">
                                        if((data.veh_coc_status==0&&data.area_status==3&&data.product_auth_status==3)||(data.veh_coc_status==3&&data.area_status==0&&data.product_auth_status==3)){
                                         //任何审核的地方都没做审核处理的，可以编辑、删除,分别表示正常更换和特殊更换两种情况未作任何审核操作的内容
                                            return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:gotoedit("+rowIndex+")'>编辑</a>" +
                                                    "&nbsp&nbsp<a id='btn_delete' class='btn_basic blue_black' href='javascript:deleteSingle("+rowIndex+")'>删除</a>";
                                        }else{
                                            return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:gotoview("+rowIndex+")'>查看</a>";
                                        }
                                    </g:if>

                                    <g:else>
                                    //若是有经过任何人审核处理的更换记录，只允许查看，不允许删除、编辑
                                        return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:gotoview("+rowIndex+")'>查看</a>"+
                                            "&nbsp&nbsp<a id='btn_addSapNo' class='btn_basic blue_black' href='javascript:addSapNo("+rowIndex+")'>添加SAP序列号</a>";
                                    </g:else>


                                }},
                        {header : "SAP序列号", name : 'SAP_No', width : 80, align : 'center'},
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
                        {header : "CRM传输状态", name : 'transToCrm', width : 80, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==0){
                                    return '<span style="color: blue"> 即将传给CRM</span>';
                                }else if(value==1){
                                    return '<span style="color: darkred"> 禁止传给CRM</span>';
                                }else if(value==2){
                                    return '<span style="color: green"> 传给CRM成功</span>';
                                }else if(value==3){
                                    return '<span style="color: red"> 传给CRM失败</span>';
                                }
                            }
                        },
                        {header : "SAP传输状态", name : 'transToSap', width : 80, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==0){
                                    return '<span style="color: blue"> 即将传给SAP</span>';
                                }else if(value==1){
                                    return '<span style="color: darkred"> 传给SAP失败</span>';
                                }else if(value==2){
                                    return '<span style="color: green"> 传给CRM成功</span>';
                                }
                            }
                        },
                        {header : "更换类型", name : 'replaceType', width : 60, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==1){
                                    return '<span style="color: green"> 特殊更换</span>';
                                }else if(value==0){
                                    return '<span > 普通更换</span>';
                                }
                            }
                        },
                        {header : "${message(code: 'zcinfore.veh_createTime.label', default: '申请日期')}", name : 'createTime', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.authTime.label', default: '审核日期')}", name : 'authTime', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Zzbh.label', default: '纸张编号')}", name : 'veh_Zzbh', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.dealer.label', default: '经销商')}", name : 'createrName', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Zchgzbh_0.label', default: '旧完整合格证编号')}", name : 'veh_Zchgzbh_0', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_cjh.label', default: '旧车架号')}", name : 'veh_Clsbdh', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Cpggh.label', default: '旧公告号')}", name : 'veh_Clxh', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Fdjh.label', default: '旧发动机号')}", name : 'veh_Fdjh', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Fdjxh.label', default: '旧发动机型号')}", name : 'veh_Fdjxh', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Zchgzbh_0_R.label', default: '新完整合格证编号')}", name : 'veh_Zchgzbh_0_R', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_cjh_R.label', default: '新车架号')}", name : 'veh_Clsbdh_R', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Cpggh_R.label', default: '新公告号')}", name : 'veh_Clxh_R', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Fdjh_R.label', default: '新发动机号')}", name : 'veh_Fdjh_R', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.veh_Fdjxh_R.label', default: '新发动机型号')}", name : 'veh_Fdjxh_R', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfore.salesmanname.label', default: '业务员姓名')}", name : 'salesmanname', width : 70, align : 'center'},
                        {header : "${message(code: 'zcinfore.salesmantel.label', default: '业务员电话')}", name : 'salesmantel', width : 120, align : 'center'},
                        {header : "${message(code: 'zcinfo.remark.label', default: '申请备注')}", name : 'remark', width : 300, align : 'center'}
                    ]
				});
			}


            function buildRightGridByRQ(){
                $("#hidden_type").val("2");
//                $("#type1").val("2");
                $('#zcinfore_grid').omGrid({
                    dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?searchType=${searchType}&'+$('#form_query').serialize(),
                    title:"<g:message code="default.list.label" args="[entityName]"/>",
                    singleSelect:false,
                    limit : 10,
                    height :440,
                    colModel:[
                        {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 100, align:'center', renderer:
                                function(value, rowData, rowIndex){
                                    var data = rowData
                                    return "<a id='btn_searchByClxh' class='btn_basic blue_black' href='javascript:gotoview("+rowIndex+")'>查看</a>";

                         }},
                        {header : "SAP序列号", name : 'SAP_No', width : 80, align : 'center'},
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
                        {header : "CRM传输状态", name : 'transToCrm', width : 80, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==0){
                                    return '<span style="color: blue"> 即将传给CRM</span>';
                                }else if(value==1){
                                    return '<span style="color: darkred"> 禁止传给CRM</span>';
                                }else if(value==2){
                                    return '<span style="color: green"> 传给CRM成功</span>';
                                }else if(value==3){
                                    return '<span style="color: red"> 传给CRM失败</span>';
                                }
                            }
                        },
                        {header : "SAP传输状态", name : 'transToSap', width : 80, align : 'center',
                            renderer:function(value,rowData,rowIndex){
                                if(value==0){
                                    return '<span style="color: blue"> 即将传给SAP</span>';
                                }else if(value==1){
                                    return '<span style="color: darkred"> 传给SAP失败</span>';
                                }else if(value==2){
                                    return '<span style="color: green"> 传给CRM成功</span>';
                                }
                            }
                        },
                        {header : "${message(code: 'zcinfo.veh_Zchgzbh_0.label', default: '完整合格证编号')}", name : 'veh_Zchgzbh_0', width : 140, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'veh_Clxh', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'veh_Clmc', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfo.veh_Fdjxh.label', default: '发动机型号')}", name : 'veh_Fdjxh', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfo.veh_Zzbh.label', default: '纸张编号')}</font>", name : 'veh_Zzbh_R', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfore.veh_Fzrq_Q.label', default: '变更前日期')}</font>", name : 'veh_Fzrq', width : 90, align : 'center'},
                        {header : "<font color='blue'>${message(code: 'zcinfore.veh_Fzrq_H.label', default: '变更后日期')}</font>", name : 'veh_Fzrq_R', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfore.ceate_id_bg.label', default: '变更人')}", name : 'createrName', width : 90, align : 'center'},
                        {header : "${message(code: 'zcinfore.changetime.label', default: '审核时间')}", name : 'authTime', width : 120, align : 'center'}
                    ]
                });
            }


            //合格证变革合格证编辑（未审核）
            function gotoedit(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'zinfoReplaceEdit')}?searchType=${searchType}&id='+data.id;
                 window.location.href = url;
            }
             //合格证删除（未审核）
             function deleteSingle(index){
                 var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                 var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'deleteSingle')}?searchType=${searchType}&id='+data.id;
                 $.post(url,{},function(data,textStatus){
                     //判断是否已处理完成
                     if(data=='success'){
                         var selections = $('#zcinfore_grid').omGrid('reload');
                         showTopTip('删除成功');
                     }else if(data=='fail'){
                         var selections = $('#zcinfore_grid').omGrid('reload');
                         showTopTip('删除失败');
                     }
                 });
             }
            $('#type').change(function(){
                tabByDiv($("#type").val());
            })
            $('#type1').change(function(){
                tabByDiv($("#type1").val());
            })

            /// 切换当前容器
            function tabByDiv(divType)
            {
                if(divType=='1'){ ////基础变更
                    $("#type").val('1');
                    $("#type1").val('1');
                    buildRightGridByJC();
                    changeSearch("#form2");
                    changehiden('#form1');
                    changeSearch("#export2");
                    changehiden('#export1');

                }else{   ////日期变更
                    $("#type").val('2');
                    $("#type1").val('2');
                    buildRightGridByRQ();
                    changeSearch('#form1');
                    changehiden("#form2");
                    changeSearch('#export1');
                    changehiden("#export2");
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
                     $("#veh_Zchgzbh").val('');
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

           ///变更合格证查看功能
            function gotoview(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'zcinfoReplaceView')}?id='+data.id+"&searchType=${searchType}";
                window.location.href = url;
            }
             ///添加SAP序列号
             function addSapNo(index){
                console.log(1)
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                if(data.SAP_No==''||data.SAP_No==null){
                    $.omMessageBox.prompt({
                        title:'SAP车辆唯一号',
                        content:'请填写SAP车辆唯一号！',
                        onClose:function(value){
                            if(value===false){ //按了取消或ESC
                                return;
                            }
                            if(value==''){
                                alert('请填写SAP车辆唯一号');
                                return false; //不关闭弹出窗口
                            }
                            var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'zcinfoReplaceAddSapNo')}';
                            $.post(url,{"id":data.id,"SAP_No":value},function(data,textStatus){
                                    alert(data);
                            },'text');
                        }
                    });
                }else{
                    alert('SAP序列号已经存在，不允许修改');
                }
             }
            function changeHtml(){
                var distributor=$('#distributor').val();
                var url='${createLink(controller:'ZCInfoReplaceAuth',action:'findUserDetail')}?distributor='+distributor;
                $.post(url,{},function(data,textStatus){
                    var data= eval( "(" + data + ")" );
                    if(!data.total==0){
                        var opSize=$('#user option').size();
                        if(opSize<data.total){
                            for(var i=0;i<=data.total-opSize;i++){
                                $('#user').append('<option></option>')
                            }
                        }
                        for(var i=0;i<data.total;i++){
                            $('#user option').get(i+1).value=data.rows[i].id;
                            $('#user option').get(i+1).text=data.rows[i].realName;
                            //alert(data.rows[i].realName);
                        }
                    }else{
                        $('#user option').remove();
                        $('#user').append('<option value="">全部</option>')
                    }

                },'text');
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