<%@ page import="cn.com.wz.stvehcert.STInfo" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="合格证信息" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80px" align="right"><span>生产日期：</span></td>
                    <td width="270px">
                        <span><c:dataPicker id="startTime" name="startTime" showTime="false"   dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker>
                            -
                            <c:dataPicker id="endTime" name="endTime"  showTime="false"  dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker></span></td>


                    <td width="80px" align="right"><span>合格证编号：</span></td>
                    <td width="90px">
                       <span><g:textField id="veh_Hgzbh" name="veh_Hgzbh" style="width: 80px"  /></span>
                    </td>
                    <td width="60px" align="right">发动机号：</td>
                    <td width="80px"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" style="width: 80px"  /></span></td>

                    <td width="60px" align="right">状态：</td>
                    <td width="80px">
                        <span>
                            <span>
                                <select name="isprint" id="isprint" style="width: 92px">
                                    <option value="0" >未打印</option>
                                    <option value="1">已打印</option>
                                    <option value="" selected="selected">全部</option>
                                </select>
                            </span>
                        </span>
                    </td>

                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 2px 2px 4px;">
            <export:formats formats="['excel']"  controller="STInfo" action="exportForST" style="border:0px;margin-left:1px;margin-top:-2px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="STInfo_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'STInfo',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#STInfo_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#startTime").val('');
                $("#endTime").val('');
                $("#veh_Hgzbh").val('');
                $("#veh_Fdjh").val('');
                $("#isprint").val('');
            }
        });
        $("#veh_Hgzbh").keydown(function(e){
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
        $(".excel").click(function(){
            $('.excel').attr('href','javascript:void(0);');
            var serialNum=$("#serialNum").val();
            var name=$("#name").val();
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'STInfo',action:'exportForST')}?"+$('#form_query').serialize()+"&format=excel&extension=xlsx&name"+name+"&serialNum="+serialNum;
            url=encodeURI(url);
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
    })

    function showModelDialog(url){
        var tabId=window.name;
        url+="?tabId="+tabId;
        var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
        var title='文件导入';
        //打开弹出框
        parent.showDialog(1,content,title,550,250);
    }
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'STInfo',action:'getCompResult')}';
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
        $('#STInfo_grid').omGrid({
            dataSource:'${createLink(controller:'STInfo',action:'jsonList')}?'+$('#form_query').serialize(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :380,
            colModel:[
                {header : "合格证编号", name : 'veh_Hgzbh', width : 130, align : 'center'},
                {header : "生产编号", name : 'veh_Scbh_0', width : 85, align : 'center'},
                {header : "新生产编号", name : 'veh_new_clbh', width : 130, align : 'center'},
                {header : "型号", name : 'veh_Cx', width : 80, align : 'center'},
                {header : "结构区别代号", name : 'veh_Jgqbdm', width : 80, align : 'center'},
                {header : "发动机号", name : 'veh_Fdjh', width : 120, align : 'center'},
                {header : "生产日期", name : 'veh_Scrq', width : 90, align : 'center'},
                {header : "执行标准", name : 'veh_Zxbz', width : 160, align : 'center'},
                {header : "状态", name : 'isprint', width : 50, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: #000000">未打印</span>';
                        }else if(value==1){
                            return '<span style="color: green">已打印</span>';
                        }
                    }},
                {header : "创建时间", name : 'createTime', width : 120, align : 'center'},
                {header : "创建人", name : 'createrId', width : 70, align : 'center'},
                {header : "更新时间", name : 'updateTime', width : 120, align : 'center'},
                {header : "更新人", name : 'updaterId', width : 70, align : 'center'}

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
