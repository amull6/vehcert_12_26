<%@ page import="cn.com.wz.vehcert.paper.PaperReco" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paper.label', default: 'Paper')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:10px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px; ">
                <tr>
                    <td width="280"><span style="width: 80px"><g:message code="paper.dealerNum.label" default="dealerNum" />：</span>
                    <span style="margin-right: 20px;"><g:textField id="dealerNum" name="dealerNum" maxlength="200" style="width: 115px;"/></span>
                    <span><g:message code="paper.dealerName.label" default="dealerName" />：</span>
                    <span><g:textField id="dealerName" name="dealerName" maxlength="200" style="width: 115px;" /></span></td>
                    <td width="300"><span><g:message code="paper.status.label" default="status" />：</span>
                    <span style="margin-right: 25px;"><select id="status">
                        <option value="0" selected="selected">全部</option>
                        <option value="1">已回收</option>
                        <option value="2">已打印</option>
                        <option value="3">空</option>
                    </select></span>
                   <span><g:message code="paper.type.label" default="type" />：</span>
                   <span>
                        <select id="type" name="type">
                            <option value="all">全部</option>
                            <option value="0">汽车整车</option>
                            <option value='1'>农用车整车</option>
                            <option value='2'>二类底盘</option>
                        </select>
                    </span></td>

                    </tr>
                <tr>
                <td width="280"><span style="margin-right: 35px;"><g:message code="paper.time.label" default="time" />：</span>
                <span><c:dataPicker id="firstTime" dateFormat='yy-mm-dd H:i:s' name="firstTime" maxlength="200" value=' ' ></c:dataPicker></span>
                到
                <span><c:dataPicker id="lastTime" dateFormat='yy-mm-dd H:i:s' name="lastTime" maxlength="200" value=' ' ></c:dataPicker></span></td>
                    <td align="left" valign="middle" >
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query"/>" />
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:50px 8px 8px 4px;">
            <export:formats formats="['excel']"  controller="paper" action="export" style="border:0px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="paperRecover_grid" style="border:0px"></div>
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
                var status=$('#status').val();
                var url = "${createLink(controller:'PaperNum',action:'jsonList')}";
                url+='?'+$('#form_query').serialize()+'&status='+status;
                $('#paperRecover_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#dealerNum").val('');
                $("#dealerName").val('');
                $(".firstTime").val('');
                $(".lastTime").val('');
            }
        });
        $(".pdf").click(function(){
            var status= $("#status").val();
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'PaperNum',action:'export')}?format=pdf&extension=pdf&"+$('#form_query').serialize()+"&status="+status;
            window.location.href=url;
        });
        $(".excel").click(function(){
            var status= $("#status").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'PaperNum',action:'export')}?format=excel&extension=xlsx&"+$('#form_query').serialize()+"&status="+status;
            window.location.href=url;
        });
        $("#dealerNum").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#dealerName").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
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
        var url='${createLink(controller:'PaperNum',action:'getCompResult')}';
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
        var content='<iframe id="receiver_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="no" src="'+url+'"></iframe>';
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
    //回收确认
    function recover(index){
        var data = $("#paperRecover_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'PaperReco',action:'recover')}';
        $.post(url,{"id":data.id},function(data,textStatus){
            showTopTip(data);
            $('#paperRecover_grid').omGrid('reload');
        },'text');
    }
    function buildRightGrid(){
        $('#paperRecover_grid').omGrid({
            dataSource:'${createLink(controller:'PaperNum',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'paper.dealerNum.label', default: 'dealerNum')}", name : 'dealerNum', width : 100, align : 'left'} ,
                {header : "${message(code: 'paper.dealerName.label', default: 'dealerName')}", name : 'dealerName', width : 250, align : 'left'},
                {header : "${message(code: 'paper.type.label', default: 'type')}", name : 'type', width : 90, align : 'center'},
                {header : "${message(code: 'paper.num.label', default: 'Num')}", name : 'num', width : 100, align : 'left'},
                {header : "${message(code: 'paper.status.label', default: 'operate')}", name : 'status', width : 60, align : 'left'},
                {header : "${message(code: 'paper.note.label', default: 'note')}", name : 'note', width :480, align : 'left'}
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
</script>
</body>
</html>
