<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="经销商自助打印次数管理" />
    <title>经销商自助打印次数管理</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="100"><span><g:message code="zcinfo.veh_Zchgzbh_0.label" default="完整合格证编号" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Zchgzbh_0" name="veh_Zchgzbh_0" maxlength="200"/></span></td>
                    <td width="50"><span><g:message code="zcinfo.status.label" default="状态" />：</span></td>
                    <td width="50"><span><g:select id="status" name="status" from="${['0':'打印初次申请状态','1':'申请增加打印次数','2':'通过状态','3':'拒绝状态','4':'打印完毕状态']}"  optionKey="key" optionValue="value"></g:select></span></td>
                    <td width="60"></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                %{--<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>--}%
                <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="printCount_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'printCount',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#printCount_grid').omGrid('setData',url);
            }
        });
        //绑定清除按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Zchgzbh_0").val('');
            }
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'printCount',action:'edit')}';
            var selected = $('#printCount_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
        $("#tf_name").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $('#btn_view').click(function(){
            var url = '${createLink(controller:'printCount',action:'show')}';
            var selected = $('#printCount_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
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
        $('#printCount_grid').omGrid({
            dataSource:'${createLink(controller:'printCount',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :390,
            colModel:[
                {header : "${message(code: 'zcinfo.SAP_No.label', default: 'SAP序列号')}", name : 'SAP_No', width : 70, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zchgzbh_0.label', default: '完整合格证编号')}", name : 'veh_Zchgzbh_0', width : 150, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label', default: '车辆识别代号')}", name : 'veh_Clsbdh', width : 200, align : 'center'},
                {header : "${message(code: 'zcinfo.printCount.label', default: '经销商打印次数')}", name : 'printCount', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.limitPrintCount.label', default: '限制打印次数')}", name : 'limitPrintCount', width : 80, align : 'center'},
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
                        }
                    }},
                {header : "经销商", name : 'applicant', width : 80, align : 'center'},
                {header : "申请时间", name : 'application_Time', width : 120, align : 'center'},
                {header : "审核时间", name : 'auth_Time', width : 120, align : 'center'},
                {header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'status', width: 250, align:'center', renderer:
                    function(value, rowData, rowIndex){
                        if(value==0){
                            //任何审核的地方都没做审核处理的，可以编辑、删除,分别表示正常更换和特殊更换两种情况未作任何审核操作的内容
                            return "<a id='btn_pass' class='btn_basic blue_black' href='javascript:gotoPass("+rowIndex+")'>通过</a>" +
                                "&nbsp&nbsp<a id='btn_refuse' class='btn_basic blue_black' href='javascript:refuse("+rowIndex+")'>拒绝</a>";
                        }else if(value==1){
                            return "<a id='btn_add' class='btn_basic blue_black' href='javascript:gotoPass("+rowIndex+")'>增加打印次数</a>";
                            "&nbsp&nbsp<a id='btn_refuse' class='btn_basic blue_black' href='javascript:refuse("+rowIndex+")'>拒绝</a>";
                        }else{
                            return "";
                        }
                    }},
            ]
        });
    }
    //通过审核申请一次自主打印
    function gotoPass(index){
        var data = $("#printCount_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'printCount',action:'gotoPass')}?id='+data.id;
        $.post(url,{},function(data,textStatus){
            //判断是否已处理完成
            if(data=='success'){
                var selections = $('#printCount_grid').omGrid('reload');
                showTopTip('已审核通过');
            }else if(data=='fail'){
                var selections = $('#printCount_grid').omGrid('reload');
                showTopTip('审核失败');
            }
        });
    }
    //拒绝申请的一次自主打印
    function refuse(index){
        var data = $("#printCount_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'printCount',action:'refuse')}?id='+data.id;
        $.post(url,{},function(data,textStatus){
            //判断是否已处理完成
            if(data=='success'){
                var selections = $('#printCount_grid').omGrid('reload');
                showTopTip('已拒绝');
            }else if(data=='fail'){
                var selections = $('#printCount_grid').omGrid('reload');
                showTopTip('拒绝失败');
            }
        });
    }
    function backFunc(result){
        $('#printCount_grid').omGrid('reload');
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
