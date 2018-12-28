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
                    <td width="80"><span><g:message code="paper.type.label" default="type" />：</span></td>
                    <td width="100"><span>
                        <select id="type" name='type'>
                            <option value="all">全部</option>
                            <option value="0">汽车整车</option>
                            <option value="1">农用车整车</option>
                            <option value="2">二类底盘</option>
                        </select>
                    </span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="paper.provideTime.label" default="provideTime" />：</span></td>
                    <td width="100"><span><c:dataPicker id="startTime" dateFormat='yy-mm-dd' name="startTime" maxlength="200" ></c:dataPicker></span></td>
                    <td width='20'>到</td>
                    <td width="100"><span><c:dataPicker id="endTime" dateFormat='yy-mm-dd' name="endTime" maxlength="200" ></c:dataPicker></span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.confirmD.label', default: 'Edit')}"/>
            %{--<export:formats formats="['excel']"  controller="paper" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>--}%
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="paperProvide_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'PaperPro',action:'jsonListDealer')}";
                url+='?'+$('#form_query').serialize();
                $('#paperProvide_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#type").val('all');
                $("#startTime").val('');
                $("#endTime").val('');
            }
        });
        $(".pdf").click(function(){
            var name= $("#name").val();
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'PaperPro',action:'export')}?format=pdf&extension=pdf";
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            var dealerNum=$("#dealerNum").val();
            var dealerName=$("#dealerName").val();
            var startTime=$("#startTime").val();
            var endTime=$("#endTime").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'PaperPro',action:'export')}?format=excel&extension=xlsx&dealerNum="+dealerNum+"&dealerName="+dealerName+"&startTime="+startTime+"&endTime="+endTime;
            url=encodeURI(url);
            window.location.href=url;
        });

        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'PaperPro',action:'xiugai')}';
            var selected = $('#paperProvide_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_view').click(function(){
            var url = '${createLink(controller:'PaperPro',action:'show')}';
            var selected = $('#paperProvide_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
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
        var url='${createLink(controller:'PaperPro',action:'getCompResult')}';
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
    function confirm(index){
        var data = $("#paperProvide_grid").omGrid("getData").rows[index];
        if(data.confirmTime){
            alert('合格证已确认！');
            return;
        }
        var url = '${createLink(controller:'PaperPro',action:'confirm')}';
        $.post(url,{"id":data.id},function(data,textStatus){
            var selections = $('#paperProvide_grid').omGrid('reload');
            var content = '';
            if(data=='true'){
                content='确认成功';
            }else{
                content='确认失败';
            }
            showTopTip(content);
        },'text');
    }
    function buildRightGrid(){
        $('#paperProvide_grid').omGrid({
            dataSource:'${createLink(controller:'PaperPro',action:'jsonListDealer')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'paper.numRand.label', default: 'numRand')}", name : 'numRand', width : 150, align : 'center'},
                {header : "${message(code: 'paper.nums.label', default: 'nums')}", name : 'nums', width : 50, align : 'center'},
                {header : "${message(code: 'paper.type.label', default: 'type')}", name : 'type', width : 70, align : 'center'},
                {header : "${message(code: 'paper.provideName.label', default: 'provideName')}", name : 'provideId', width : 50, align : 'center'},
                {header : "${message(code: 'paper.receiveName.label', default: 'receiveName')}", name : 'receiveName', width : 50, align : 'center'},
                {header : "${message(code: 'paper.provideTime.label', default: 'provideTime')}", name : 'provideTime', width :80, align : 'center'},
                {header : "${message(code: 'paper.confirmTime.label', default: 'confirmTime')}", name : 'confirmTime', width :80, align : 'center'},
                {header : "${message(code: 'paper.operate.label', default: 'operate')}", name : 'operate', width :80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        return  '<a id="btn_view" class="btn_basic blue_black"  href="javascript:confirm('+rowIndex+')">确认</a>';
                    }},
                {header : "${message(code: 'paper.note.label', default: 'note')}", name : 'note', width :320, align : 'center'},
                {header : "${message(code: 'paper.noteConfirm.label', default: 'noteConfirm')}", name : 'noteConfirm', width :320, align : 'center'}
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
