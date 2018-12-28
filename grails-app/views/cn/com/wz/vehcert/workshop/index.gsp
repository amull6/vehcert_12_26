<%@ page import="cn.com.wz.vehcert.workshop.Workshop" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="Workshop.serialNum.label" default="SerialNum" />：</span></td>
                    <td width="100"><span><g:textField id="serialNum" name="serialNum" maxlength="200" value="${workshopInstance?.serialNum}"/></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="Workshop.name.label" default="Name" />：</span></td>
                    <td width="100"><span><g:textField id="name" name="name" maxlength="200" value="${workshopInstance?.name}"/></span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
            <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            <export:formats formats="['excel']"  controller="Workshop" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="workshop_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'workshop',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#workshop_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#serialNum").val('');
                $("#name").val('');
            }
        });
        $("#serialNum").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#name").keydown(function(e){
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
            var url="${createLink(controller:'workshop',action:'export')}?format=excel&extension=xlsx&name"+name+"&serialNum="+serialNum;
            url=encodeURI(url);
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'workshop',action:'create')}';
            var selected = $('#workshop_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'workshop',action:'edit')}';
            var selected = $('#workshop_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#workshop_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'workshop',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#workshop_grid').omGrid('reload');
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
            var url = '${createLink(controller:'workshop',action:'show')}';
            var selected = $('#workshop_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'workshop',action:'importPage')}';
            showModelDialog(url);
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
        var url='${createLink(controller:'workshop',action:'getCompResult')}';
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
        $('#workshop_grid').omGrid({
            dataSource:'${createLink(controller:'workshop',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'Workshop.serialNum.label', default: 'SerialNum')}", name : 'serialNum', width : 150, align : 'center'},
                {header : "${message(code: 'Workshop.name.label', default: 'Name')}", name : 'name', width : 150, align : 'center'},
                {header : "${message(code: 'Workshop.note.label', default: 'Note')}", name : 'note', width :400, align : 'center'}]
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
