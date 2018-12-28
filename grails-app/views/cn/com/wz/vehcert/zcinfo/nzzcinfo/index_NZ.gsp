<%--
 @Description:
 @params:
 @return:
 @Create: 14-7-11 下午4:00   朱伟
 @Update:
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'nzinfo.nzinfoUpload.label', default: 'nzinfoUpload')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page"   style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80" align="right"><span><g:message code="nzinfo.veh_Clbh.label" default="veh_Clbh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clbh1" name="veh_Clbh1" maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="100"><span><g:textField id="veh_Clbh2" name="veh_Clbh2" maxlength="200"/></span></td>
                    <td width='10'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
            <input id="btn_synToServer" type="button" class="create" value="${message(code: 'nzinfo.button.syn.label', default: 'submit Server')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="nzinfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>

<script>
    $(function() {
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'NZInfo',action:'jsonList')}?upload=0";
                url+='&'+$('#form_query').serialize();
                $('#nzinfo_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Clbh1").val('');
                $("#veh_Clbh2").val('');
            }
        });
        $('#page').css({height:$(document).height()-16});
        $("#btn_synToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'NZInfo',action:'uploadToServer')}?"+$('#form_query').serialize();
            $.post(url,{},function(data,textStatus){
                if("数据同步成功"==data){
                    $("#nzinfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    showTopTip(data);
                }else{
                    $("#nzinfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    alert(data);
                }
            },'text');
        });
    })
    // 加载时执行的语句
    $(function() {
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            }]
        });
        buildRightGrid();
    });

    function buildRightGrid(){
        $('#nzinfo_grid').omGrid({
            dataSource:"${createLink(controller:'NZInfo',action:'jsonList')}?upload=0",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12, // 分页显示，每页显示30条
            height : 450,
            colModel:[
                {header : "${message(code: 'nzinfo.veh_Hgzbh.label')}", width : 110, align : 'center',name : 'veh_Hgzbh'},
                {header : "${message(code: 'nzinfo.veh_Clbh.label')}", width : 110, align : 'center',name : 'veh_Clbh'},
                {header : "${message(code: 'nzinfo.veh_Fdjh.label')}", width : 80, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'nzinfo.veh_Cx.label')}", width : 70, align : 'center',name : 'veh_Cx'},
                {header : "环保信息代码", width : 70, align : 'center',name : 'envirCode'},
                {header : "${message(code: 'nzinfo.veh_Ccrq.label')}", width : 140, align : 'center',name : 'veh_Ccrq'},
                {header : "${message(code: 'nzinfo.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'nzinfo.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'nzinfo.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'nzinfo.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'},
                {header : "${message(code: 'nzinfo.SAP_No.label')}", width : 70, align : 'center',name : 'SAP_No'},
                {header : "${message(code: 'nzinfo.veh_workshopno.label')}", width : 80, align : 'center',name : 'veh_workshopno'}
            ]

        });
    }


    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
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
    function backFunc(result){
        $('#carStorage_grid').omGrid('reload');
    }
</script>
</body>
</html>