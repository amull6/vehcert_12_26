<%@ page import="cn.com.wz.vehcert.paper.Paper" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paper.label', default: 'Paper')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="paper.dealerNum.label" default="dealerNum" />：</span></td>
                    <td width="100"><span><g:textField id="dealerNum" name="dealerNum" maxlength="200" style="width: 110px;"/></span></td>
                    <td width='10'></td>
                    <td width="80"><span><g:message code="paper.dealerName.label" default="dealerName" />：</span></td>
                    <td width="100"><span><g:textField id="dealerName" name="dealerName" maxlength="200" style="width: 110px;"/></span></td>
                    <td width='10'></td>
                    <td width="80"><span><g:message code="paper.paperNum.label" default="paperNum" />：</span></td>
                    <td width="100"><span><g:textField id="paperNum" name="paperNum" maxlength="200" style="width: 110px;"/></span></td>
                    <td width='10'></td>
                    <td width="80"><span><g:message code="paper.type.label" default="type" />：</span></td>
                    <td width="60"><span>
                        <select id="type" name="type">
                            <option value="all">全部</option>
                            <option value="0">汽车整车</option>
                            <option value='1'>农用车整车</option>
                            <option value='2'>二类底盘</option>
                        </select>
                    </span></td>
                    <td width='10'></td>
                    <td width="50"><span><g:message code="paper.status.label" default="status" />：</span></td>
                    <td width="100"><span><select id="status">
                        <option value="0" selected="selected">未回收</option>
                        <option value="1">已回收</option>
                    </select></span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
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
                var url = "${createLink(controller:'PaperReco',action:'jsonList')}";
                url+='?'+$('#form_query').serialize()+'&status='+status;
                $('#paperRecover_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $('#type').val('all');
                $("#dealerNum").val('');
                $("#dealerName").val('');
                $("#paperNum").val('');
            }
        });
        $(".pdf").click(function(){
            var status= $("#status").val();
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'PaperReco',action:'export')}?format=pdf&extension=pdf&status="+status;
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            var type=$('#type').val();
            var dealerNum=$("#dealerNum").val();
            var dealerName=$("#dealerName").val();
            var paperNum=$("#paperNum").val();
            var status= $("#status").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
           var url="${createLink(controller:'PaperReco',action:'export')}?format=excel&extension=xlsx&status="+status+"&type="+type+"&dealerNum="+dealerNum+"&dealerName="+dealerName+"&paperNum="+paperNum;
            url=encodeURI(url);
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
        $("#paperNum").keydown(function(e){
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
        var url='${createLink(controller:'PaperReco',action:'getCompResult')}';
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
            dataSource:'${createLink(controller:'PaperReco',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :440,
            colModel:[
                {header : "${message(code: 'paper.dealerNum.label', default: 'dealerNum')}", name : 'dealerNum', width : 80, align : 'center'} ,
                {header : "${message(code: 'paper.dealerName.label', default: 'dealerName')}", name : 'dealerName', width : 150, align : 'center'},
                {header : "${message(code: 'paper.paperNum.label', default: 'paperNum')}", name : 'paperNum', width : 100, align : 'center'},
                {header : "${message(code: 'paper.type.label', default: 'type')}", name : 'type', width : 80, align : 'center'},
                {header : "${message(code: 'paper.status.label', default: 'operate')}", name : 'status', width : 60, align : 'center'},
                {header : "${message(code: 'paper.operate.label', default: 'status')}", name : 'status', width : 114, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value=='未回收'){
                            return '<button id="btn_recover" style="color:blue" class="btn_view" onClick="recover('+rowIndex+')">回收确认</button>';
                        }else{
                            return '';
                        }

                    }},
                {header : "${message(code: 'paper.recoverName.label', default: 'recoverName')}", name : 'recoverId', width : 100, align : 'center'},
                {header : "${message(code: 'paper.recoverTime.label', default: 'recoverTime')}", name : 'recoverTime', width :120, align : 'center'},
                {header : "${message(code: 'paper.note.label', default: 'note')}", name : 'note', width :250, align : 'center'}
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
