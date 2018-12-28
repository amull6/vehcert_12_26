<%@ page import="cn.com.wz.vehcert.dealercarstorage.DealerCar" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'dealerCar.label', default: 'dealerCar')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="dealerCar.dc_Ggxh.label" default="dc_Ggxh" />：</span></td>
                    <td width="100"><span><g:textField id="dc_Ggxh" name="dc_Ggxh" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="dealerCar.dc_Jss.label" default="dc_Jss" />：</span></td>
                    <td width="100"><span><g:textField id="dc_Jss" name="dc_Jss" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="100"><span><select id="kind">
                        <option  value="all">全部</option>
                        <option  value="sanD">国三公告</option>
                        <option value="siD">国四公告</option>
                        <option value="zhuanD">国四专用车</option>
                        <option value="diD">低速货车</option>
                        <option value="qiD">三轮汽车</option>
                    </select></span></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <c:resourceAuth resourceCode="dv_youhao_add">
                <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_youhao_update">
                <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_youhao_delete">
                <input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
            </c:resourceAuth>
                <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            <export:formats formats="['excel']"  controller="dealerCar" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="dealerCar_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'dealerCar',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#dealerCar_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#dc_Ggxh").val('');
                $("#dc_Jss").val('');
            }
        });
        $(".pdf").click(function(){
            $('.pdf').attr('href','javascript:void(0);');
            var dc_Ggxh=$("#dc_Ggxh").val();
            var dc_Jss=$("#dc_Jss").val();
            var kind=$('#kind').val();
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'dealerCar',action:'export')}?format=pdf&extension=pdf&dc_Ggxh="+dc_Ggxh+"&dc_Jss="+dc_Jss+"&kind="+kind;
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            $('.excel').attr('href','javascript:void(0);');
            var dc_Ggxh=$("#dc_Ggxh").val();
            var dc_Jss=$("#dc_Jss").val();
            var kind=$('#kind').val();
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'dealerCar',action:'export')}?format=excel&extension=xlsx&dc_Ggxh="+dc_Ggxh+"&dc_Jss="+dc_Jss+"&kind="+kind;
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'dealerCar',action:'create')}';
            var selected = $('#dealerCar_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'dealerCar',action:'edit')}';
            var selected = $('#dealerCar_grid').omGrid('getSelections',true);
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
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#dealerCar_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'dealerCar',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){

                            var selections = $('#dealerCar_grid').omGrid('reload');
                            var content = data;
                            /**
                             $.each(data,function(i,item){
        		                   			content += "<div class='message'>"+item+"</div>";
        			                   	});**/
                            showTopTip(content);
                        },'text');
                    }
                }
            });
        });
        $('#btn_view').click(function(){
            var url = '${createLink(controller:'dealerCar',action:'show')}';
            var selected = $('#dealerCar_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'dealerCar',action:'importPage')}';
            showModelDialog(url);
        });
    })
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'dealerCar',action:'getCompResult')}';
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
        $('#dealerCar_grid').omGrid({
            dataSource:'${createLink(controller:'dealerCar',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'dealerCar.dc_Ggxh.label', default: 'dc_Ggxh')}", name : 'dc_Ggxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'dealerCar.dc_Jss.label', default: 'dc_Jss')}", name : 'dc_Jss', width : 150, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Ggpc.label', default: 'dc_Ggpc')}", name : 'dc_Ggpc', width : 100, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Pic.label', default: 'dc_Pic')}", name : 'dc_Pic', width : 150, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Jsszcrs.label', default: 'dc_Jsszcrs')}", name : 'dc_Jsszcrs', width : 100, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Fdj.label', default: 'dc_Fdj')}", name : 'dc_Fdj', width : 100, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Gglt.label', default: 'dc_Gglt')}", name : 'dc_Gglt', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Bhps.label', default: 'dc_Bhps')}", name : 'dc_Bhps', width :85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Qhlj.label', default: 'dc_Qhlj')}", name : 'dc_Qhlj', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Zj.label', default: 'dc_Zj')}", name : 'dc_Zj', width :85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Zccc.label', default: 'dc_Zccc')}", name : 'dc_Zccc', width : 100, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Hxcc.label', default: 'dc_Hxcc')}", name : 'dc_Hxcc', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Zzl.label', default: 'dc_Zzl')}", name : 'dc_Zzl', width :85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Zbzl.label', default: 'dc_Zbzl')}", name : 'dc_Zbzl', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_ZAzl.label', default: 'dc_ZAzl')}", name : 'dc_ZAzl', width : 100, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Kzrq.label', default: 'dc_Kzrq')}", name : 'dc_Kzrq', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.kind.label', default: 'dc_Qt')}", name : 'kind', width :85, align : 'center'},
                {header : "${message(code: 'dealerCar.dc_Qt.label', default: 'dc_Qt')}", name : 'dc_Qt', width :85, align : 'center'},
                {header : "${message(code: 'dealerCar.updaterName.label', default: 'updaterName')}", name : 'updaterName', width : 85, align : 'center'},
                {header : "${message(code: 'dealerCar.updateTime.label', default: 'updateTime')}", name : 'updateTime', width :85, align : 'center'}
            ]
        });
    }
    function backFunc(result){

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
