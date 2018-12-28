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
                    <td width="80px" align="right"><span>旧车辆编号：</span></td>
                    <td width="80px" align="left"><span><g:textField id="veh_Clbh" name="veh_Clbh" style="width: 90px" maxlength="200"/></span></td>
                    <td width="80px" align="right"><span>旧发动机号：</span></td>
                    <td width="80px" align="left"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" style="width: 90px" maxlength="200"/></span></td>
                    <td width="60px" align="right"><span>申请时间：</span></td>
                    <td width="235px" align='left'><span><c:dataPicker id="firstTime" name="firstTime" readOnly="true" dateFormat="yy-mm-dd" style="width: 80px" ></c:dataPicker></span>到<span><c:dataPicker id="secondTime" name="secondTime"  readOnly="true" style="width:80px"  dateFormat="yy-mm-dd"></c:dataPicker></span></td>

                    <td width="90px"  align="right"><span>区域审核状态：</span></td>
                    <td><span>
                        <select name="area_status" id="auth_status" style="width: 90px">
                            <option value="">全部</option>
                            <option value="0" selected=“selected”>未审核</option>
                            <option value="1">审核通过</option>
                            <option value="2">审核未通过</option>
                        </select>
                    </span></td>
                    <td align="left" valign="middle" colspan="2">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                        <g:hiddenField name="searchType" id='searchType' value="${searchType}"/>
                    </td>
                </tr>
            </table>
        </form>
        <fieldset class="buttons" style="margin:-10px 8px 8px 4px;">
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
        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var id=$('#user').val();
                var url = "${createLink(controller:'NZzcinfoReplaceAuth',action:'jsonListBySales')}";
                url+='?'+$('#form_query').serialize();
                $('#zcinfore_grid').omGrid('setData',url);
            }
        });
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
                $("#veh_Clbh").val('');
                $("#veh_Fdjh").val('');
                $("#firstTime").val('');
                $("#secondTime").val('');
            }
        });
        $("#veh_Clbh").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#veh_Fdjh").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#firstTime").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
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
            dataSource:'${createLink(controller:'NZzcinfoReplaceAuth',action:'jsonListBySales')}?searchType='+$('#searchType').val(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :493,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'id', width: 40, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+')">查看</a>'
                        }},

                {header : "经销商", name : 'createName', width : 80, align : 'center'},
                {header : "旧车辆编号", name : 'veh_Clbh', width : 70, align : 'center'},
                {header : "旧底盘号", name : 'veh_Dph', width : 70, align : 'center'},
                {header : "旧车型", name : 'veh_Cx', width :60, align : 'center'},
                {header : "旧发动机号", name : 'veh_Fdjh', width : 80, align : 'center'},
                {header : "旧出厂日期", name : 'veh_Ccrq', width : 90, align : 'center'},
                {header : "新车辆编号", name : 'veh_Clbh_R', width : 70, align : 'center'},
                {header : "新底盘号", name : 'veh_Dph_R', width : 70, align : 'center'},
                {header : "新车型", name : 'veh_Cx_R', width : 60, align : 'center'},
                {header : "新出厂日期", name : 'veh_Ccrq_R', width : 90, align : 'center'},
                {header : "业务员姓名", name : 'salesManName', width : 60, align : 'center'},
                {header : "业务员电话", name : 'salesManPhone', width : 75, align : 'center'},
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
                },
                {header : "申请日期", name : 'createTime', width : 125, align : 'center'}


            ]
        });
    }
    //转向查看审核页面
    function gotoview(index){
        var data = $("#zcinfore_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'salesView')}?id='+data.id+'&searchType='+$('#searchType').val();
        window.location.href = url;

    }
</script>
</body>
</html>