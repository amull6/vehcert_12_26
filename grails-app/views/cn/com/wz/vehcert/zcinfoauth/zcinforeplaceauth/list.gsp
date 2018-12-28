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
			<form id="form_query" style="margin:8px;height:40px;">
			<table style="width:100%;border:0px;">
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
				  <tr>
				    <td width="80" align="right"><span><g:message code="zcinfore.veh_cjh.label" default="旧车架号" />：</span></td>
				    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200"/></span></td>
                      <td width="50"><span><g:message code="zcinfore.veh_Fdjh.label" default="旧发动机号" />：</span></td>
                      <td width="100" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" maxlength="200"/></span></td>
                  </tr>
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfore.veh_zchgzbh_0.label" default="旧完整合格证号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_zchgzbh_0" name="veh_zchgzbh_0" maxlength="200"/></span></td>
                    <td width="50"><span><g:message code="zcinfore.veh_Clxh.label" default="旧公告号" />：</span></td>
                    <td width="100" align="left"><span><g:textField id="veh_Clxh" name="veh_Clxh" maxlength="200"/></span></td>
                </tr>
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfore.veh_cjh_R.label" default="新车架号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clsbdh_R" name="veh_Clsbdh_R" maxlength="200"/></span></td>
                    <td width="50"><span><g:message code="zcinfore.veh_Fdjh.label" default="新发动机号" />：</span></td>
                    <td width="100" align="left"><span><g:textField id="veh_Fdjh_R" name="veh_Fdjh_R" maxlength="200"/></span></td>
                </tr>
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfore.veh_zchgzbh_0_R.label" default="新完整合格证号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_zchgzbh_0_R" name="veh_zchgzbh_0_R" maxlength="200"/></span></td>
                    <td width="50"><span><g:message code="zcinfore.veh_Clxh_R.label" default="新公告号" />：</span></td>
                    <td width="100" align="left"><span><g:textField id="veh_Clxh_R" name="veh_Clxh_R" maxlength="200"/></span></td>
                </tr>
                <tr>
                      <td width="80" align="right"><span><g:message code="zcinfore.veh_createTime.label" default="Apply Time" />：</span></td>
                      <td width="220"><span><c:dataPicker id="firstTime" name="firstTime" readOnly="true" dateFormat="yy-mm-dd" ></c:dataPicker></span>到<span><c:dataPicker id="secondTime" name="secondTime"  readOnly="true" dateFormat="yy-mm-dd"></c:dataPicker></span></td>


				    <td align="left" valign="middle" colspan="2">
				    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
				    <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
				    </td>
				  </tr>
				</table>
			</form>
				<fieldset class="buttons" style="margin:130px 8px 8px 30px;">
                    <input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>
                    <export:formats formats="['excel']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>
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
                $('#page').css({height: $(document).height() - 16});
                $('#veh_Clsbdh').omCombo({
                    multi: false
                });
                $('#veh_Fdjh').omCombo({
                    multi: false
                });
                $('#veh_zchgzbh_0').omCombo({
                    multi: false
                });
                $('#veh_Clxh').omCombo({
                    multi: false
                });

                $('#veh_Clsbdh_R').omCombo({
                    multi: false
                });
                $('#veh_Fdjh_R').omCombo({
                    multi: false
                });
                $('#veh_zchgzbh_0_R').omCombo({
                    multi: false
                });
                $('#veh_Clxh_R').omCombo({
                    multi: false
                });
                //绑定查询按钮事件
                $('#btn_query').omButton({
                    width: 80,
                    onClick: function () {
                        var id = $('#user').val();
                        var url = "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}";
                        url += '?veh_coc_status=0&formal=0&' + $('#form_query').serialize() + '&user.id=' + id + '&searchType=1';
                        $('#zcinfore_grid').omGrid('setData', url);
                    }
                });
                $("#change").change( function() {
                    var jxsId=$('#user').val()
                    var distributorId=$('#distributor').val()
                    if(change!='全部'){
                        $('#veh_Clsbdh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clsbdh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_Fdjh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Fdjh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_zchgzbh_0').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_zchgzbh_0&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_Clxh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clxh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_Clsbdh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clsbdh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_Fdjh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Fdjh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_zchgzbh_0_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_zchgzbh_0_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                        $('#veh_Clxh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clxh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());
                    }
                });
                %{--$("#distributor").change( function() {--}%
                    %{--var jxsId=$('#user').val()--}%
                    %{--var distributorId=$('#distributor').val()--}%
                    %{--if(change!='全部'){--}%
                        %{--$('#veh_Clsbdh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clsbdh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_Fdjh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Fdjh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_zchgzbh_0').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_zchgzbh_0&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_Clxh').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clxh&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_Clsbdh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clsbdh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_Fdjh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Fdjh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_zchgzbh_0_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_zchgzbh_0_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                        %{--$('#veh_Clxh_R').omCombo('setData',"${createLink(controller:'ZCInfoReplaceAuth',action:'jsonReplaceSuggestion')}?field=veh_Clxh_R&jxsId="+jxsId+"&distributorId="+distributorId+"&random="+Math.random());--}%
                    %{--}--}%
                %{--});--}%

                $(".excel").click(function(){
                    $('.excel').attr('href','javascript:void(0);');
                    var veh_Clsbdh=$("#veh_Clsbdh").val();
                    var veh_Fdjh=$("#veh_Fdjh").val();
                    var firstTime=$("#firstTime").val();
                    var secondTime=$("#secondTime").val();
                    var id=$('#user').val();
                    var idD=$('#distributor').val();
                    var veh_coc_status=$("#veh_coc_status").val();
                    //当节点没有选中的情况，导出s通讯录中所有的数据
                    dialogId=showTipDialog();
                    initId=setInterval("getResult();",'3000');
                    var url="${createLink(controller:'ZCInfoReplaceAuth',action:'shenheexport')}?format=excel&extension=xlsx&searchType=1&veh_Clsbdh="+veh_Clsbdh+"&veh_Fdjh="+veh_Fdjh+"&firstTime="+firstTime+"&secondTime="+secondTime+"&veh_coc_status=00&user.id="+id+"&distributor.id="+idD;
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
					dataSource:'${createLink(controller:'ZCInfoReplaceAuth',action:'jsonListByDealer')}?veh_coc_status=0&formal=0&'+$('#form_query').serialize(),
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
                              {header : "${message(code: 'zcinfore.veh_createTime.label', default: '申请日期')}", name : 'createTime', width : 150, align : 'center'},
		                      {header : "${message(code: 'zcinfore.dealer.label', default: '经销商')}", name : 'createrName', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.pq.label', default: '组织')}", name : 'allOrganNameC', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Zchgzbh.label', default: '旧合格证编号')}", name : 'veh_Zchgzbh', width : 110, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh.label', default: '旧车架号')}", name : 'veh_Clsbdh', width : 130, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Clxh.label', default: '旧公告号')}", name : 'veh_Clxh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh.label', default: '旧发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjxh.label', default: '旧发动机型号')}", name : 'veh_Fdjxh', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Zchgzbh_R.label', default: '新合格证编号')}", name : 'veh_Zchgzbh_R', width : 110, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_cjh_R.label', default: '新车架号')}", name : 'veh_Clsbdh_R', width : 130, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_clxh_R.label', default: '新公告号')}", name : 'veh_Clxh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjh_R.label', default: '新发动机号')}", name : 'veh_Fdjh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.veh_Fdjxh_R.label', default: '新发动机型号')}", name : 'veh_Fdjxh_R', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmanname.label', default: '业务员姓名')}", name : 'salesmanname', width : 80, align : 'center'},
                              {header : "${message(code: 'zcinfore.salesmantel.label', default: '业务员电话')}", name : 'salesmantel', width : 80, align : 'center'},
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
                              }


                    ]
				});
			}
            function changeHtml(){
                var distributor=$('#distributor').val();
                var url='${createLink(controller:'ZCInfoReplaceAuth',action:'findUserDetail')}?distributor='+encodeURI(distributor);
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
            //转向查看审核页面
            function gotoview(index){
                var data = $("#zcinfore_grid").omGrid("getData").rows[index];
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'auditView')}?id='+data.id;
                 window.location.href = url;

            }
		 </script>
	</body>
</html>