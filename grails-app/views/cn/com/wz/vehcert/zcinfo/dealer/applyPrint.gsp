<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="经销商自助打印申请" />
    <title>经销商自助打印申请</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="100"><span><g:message code="zcinfo.veh_Zchgzbh_0.label" default="完整合格证编号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Zchgzbh_0" name="veh_Zchgzbh_0" maxlength="200"/></span></td>
                    <td width="60"></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <input id="btn_apply" type="button"  class="edit" value="${message(code: 'default.button.apply.label', default: 'Apply')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="printCount_Grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'printCount',action:'zcinfoList')}";
                url+='?'+$('#form_query').serialize();
                $('#printCount_Grid').omGrid('setData',url);
            }
        });
        //绑定清除按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Zchgzbh_0").val('');
            }
        });
        $("#tf_name").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        //批量提交申请
        $('#btn_apply').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#printCount_Grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.apply.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var applyUrl = '${createLink(controller:'printCount',action:'applyBatch')}';
                        var applyIds = '';
                        $.each(selections,function(i,item){
                            applyIds += item.id+'_';
                        });
                        $.post(applyUrl,{"applyIds":applyIds},function(data,textStatus){
                            var selections = $('#printCount_Grid').omGrid('reload');
                            var content = data;
                            showTopTip(content.msg);
                        },'json');
                    }
                }
            });
        });
    })
    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'heavyGas',action:'getCompResult')}';
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
    function showModelDialog(url){
        var tabId=window.name;
        url+="?tabId="+tabId;
        var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
        var title='文件导入';
        //打开弹出框
        parent.showDialog(1,content,title,550,250);
    }
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
    function buildRightGrid(){
        $('#printCount_Grid').omGrid({
            dataSource:'${createLink(controller:'printCount',action:'zcinfoList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 60,
            height :440,
            colModel:[
                {header : "${message(code: 'zcinfo.SAP_No.label', default: 'SAP序列号')}", name : 'SAP_No', width : 70, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zchgzbh_0.label', default: '完整合格证编号')}", name : 'veh_Zchgzbh_0', width : 150, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label', default: '车辆识别代号')}", name : 'veh_Clsbdh', width : 200, align : 'center'},
                {header : "${message(code: 'zcinfo.printCount.label', default: '经销商打印次数')}", name : 'printCount', width : 70, align : 'center'},
                {header : "${message(code: 'zcinfo.limitPrintCount.label', default: '限制打印次数')}", name : 'limitPrintCount', width : 70, align : 'center'},
                {header : "产品类型", width : 50, align : 'center',name : 'veh_Type',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">汽车</span>';
                        }else if(value==1){
                            return '<span style="color: red">农用车</span>';
                        }
                    }},
                {header : "${message(code: 'zcinfo.status.label', default: '状态')}", name : 'status', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: yellow">申请初次打印</span>';
                        }else if(value==1){
                            return '<span style="color: cadetblue">申请增加打印次数</span>';
                        }else if(value==2){
                            return '<span style="color: green">通过</span>';
                        }else if(value==3){
                            return '<span style="color: red">拒绝</span>';
                        }else if(value==4){
                            return '<span style="color: black">打印完毕</span>';
                        }else if(value==5){
                            return '<span style="color: black">初始状态</span>';
                        }
                    }},
                {header : "申请时间", name : 'application_Time', width : 120, align : 'center'},
                %{--{header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'status', width: 150, align:'center', renderer:--}%
                    %{--function(value, rowData, rowIndex){--}%
                        %{--if(value==0){--}%
                            %{--//任何审核的地方都没做审核处理的，可以编辑、删除,分别表示正常更换和特殊更换两种情况未作任何审核操作的内容--}%
                            %{--return "<a id='btn_pass' class='btn_basic blue_black' href='javascript:gotoPass("+rowIndex+")'>通过</a>" +--}%
                                %{--"&nbsp&nbsp<a id='btn_refuse' class='btn_basic blue_black' href='javascript:refuse("+rowIndex+")'>拒绝</a>";--}%
                        %{--}else if(value==1){--}%
                            %{--return "<a id='btn_add' class='btn_basic blue_black' href='javascript:gotoPass("+rowIndex+")'>增加打印次数</a>";--}%
                            %{--"&nbsp&nbsp<a id='btn_refuse' class='btn_basic blue_black' href='javascript:refuse("+rowIndex+")'>拒绝</a>";--}%
                        %{--}else{--}%
                            %{--return "";--}%
                        %{--}--}%
                    %{--}},--}%
            ]
        });
    }
    function backFunc(result){
        $('#printCount_Grid').omGrid('reload');
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
