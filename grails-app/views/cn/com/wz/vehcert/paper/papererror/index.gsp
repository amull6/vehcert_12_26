<%@ page import="cn.com.wz.vehcert.paper.PaperError" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paperError.label', default: 'Paper')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;margin-left:50px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="paperError.dealerNum.label" default="dealerNum" />：</span>
                    <span style="margin-right: 10px;"><g:textField id="dealerNum" name="dealerNum" maxlength="200" /></span>
                    <span><g:message code="paperError.dealerName.label" default="dealerName" />：</span>
                    <span><g:textField id="dealerName" name="dealerName" maxlength="200" /></span>
                    <td width="80"><span><g:message code="paper.type.label" default="type" />：</span>
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
                    <td width="80"><span style="margin-right: 10px;"><g:message code="paperError.createTime.label" default="createTime" />：</span>
                    <span><c:dataPicker id="startTime" dateFormat='yy-mm-dd' name="startTime" maxlength="200"  ></c:dataPicker></span>
                    &nbsp&nbsp到&nbsp&nbsp<span><c:dataPicker id="endTime" dateFormat='yy-mm-dd' name="endTime" maxlength="200" ></c:dataPicker></span></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:45px 8px 8px 4px;">
            <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
            <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            <export:formats formats="['excel']"  controller="paper" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="PaperError_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'PaperError',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#PaperError_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#dealerNum").val('');
                $("#dealerName").val('');
                $("#type").val('all');
                $("#startTime").val('');
                $("#endTime").val('');
            }
        });
        $(".pdf").click(function(){
            var dealerNum=$("#dealerNum").val();
            var dealerName=$("#dealerName").val();
            var startTime=$("#startTime").val();
            var endTime=$("#endTime").val();
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'PaperError',action:'export')}?format=pdf&extension=pdf&dealerNum="+dealerNum+"&dealerName="+dealerName+"&startTime="+startTime+"&endTime="+endTime;
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            var dealerNum=$("#dealerNum").val();
            var dealerName=$("#dealerName").val();
            var startTime=$("#startTime").val();
            var type=$("#type").val();
            var endTime=$("#endTime").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
           var url="${createLink(controller:'PaperError',action:'export')}?format=excel&extension=xlsx&dealerNum="+dealerNum+"&type="+type+"&dealerName="+dealerName+"&startTime="+startTime+"&endTime="+endTime;
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'PaperError',action:'create')}';
            var selected = $('#PaperError_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'PaperError',action:'edit')}';
            var selected = $('#PaperError_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
        $("#dealerNum").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#PaperError_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'PaperError',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#PaperError_grid').omGrid('reload');
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
            var url = '${createLink(controller:'PaperError',action:'show')}';
            var selected = $('#PaperError_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'PaperError',action:'importPage')}';
            showModelDialog(url);
        });
    })
    function showModelDialog(url){
        var tabId=window.name;
        url+="?tabId="+tabId;
        var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="no" src="'+url+'"></iframe>';
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
        var url='${createLink(controller:'PaperError',action:'getCompResult')}';
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
        $('#PaperError_grid').omGrid({
            dataSource:'${createLink(controller:'PaperError',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'paperError.dealerNum.label', default: 'dealerNum')}", name : 'dealerNum', width : 80, align : 'center'} ,
                {header : "${message(code: 'paperError.dealerName.label', default: 'dealerName')}", name : 'dealerName', width : 150, align : 'center'},
                {header : "${message(code: 'paperError.paperNum.label', default: 'paperNum')}", name : 'paperNum', width : 220, align : 'center'},
                {header : "${message(code: 'paperError.veh_Hgzbh.label', default: 'veh_Hgzbh')}", name : 'veh_Hgzbh', width : 150, align : 'center'},
                {header : "${message(code: 'paper.type.label', default: 'type')}", name : 'type', width : 150, align : 'center'},
                {header : "${message(code: 'paperError.num.label', default: 'num')}", name : 'num', width :60, align : 'center'},
                {header : "${message(code: 'paperError.createrId.label', default: 'createrId')}", name : 'createrId', width : 100, align : 'center'},
                {header : "${message(code: 'paperError.createTime.label', default: 'createTime')}", name : 'createTime', width : 100, align : 'center'},
                {header : "${message(code: 'paperError.note.label', default: 'note')}", name : 'note', width :205, align : 'center'}
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
