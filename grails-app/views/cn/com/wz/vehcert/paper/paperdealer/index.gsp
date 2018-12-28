<%@ page import="cn.com.wz.vehcert.paper.PaperDealer" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'paperDealer.label', default: 'paperDealer')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="paperDealer.paperNum.label" default="paperNum" />：</span></td>
                    <td width="100"><span><g:textField id="paperNum" name="paperNum" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="paperDealer.type.label" default="type" />：</span></td>
                    <td width="100"><span>
                        <select id="type" name="type">
                            <option value="all">全部</option>
                            <option value="0">汽车整车</option>
                            <option value='1'>农用车整车</option>
                            <option value='2'>二类底盘</option>
                        </select>
                    </span></td>
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
            <export:formats formats="['excel']"  controller="paperDealer" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="PaperDealer_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'PaperDealer',action:'jsonList')}";
                url+="?"+$('#form_query').serialize()+"&only=1";
                $('#PaperDealer_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#paperNum").val('');
                $("#type").val('all');
            }
        });
        $(".excel").click(function(){
            var paperNum=$("#paperNum").val();
            var type=$("#type").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
           var url="${createLink(controller:'paperDealer',action:'export')}?format=excel&extension=xlsx&paperNum="+paperNum+"&type="+type+"&only=1";
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'paperDealer',action:'create')}';
            var selected = $('#PaperDealer_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'paperDealer',action:'edit')}';
            var selected = $('#PaperDealer_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
        $("#paperNum").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#PaperDealer_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'paperDealer',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#PaperDealer_grid').omGrid('reload');
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
            var url = '${createLink(controller:'paperDealer',action:'show')}';
            var selected = $('#PaperDealer_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'paperDealer',action:'importPage')}';
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
        var url='${createLink(controller:'paperDealer',action:'getCompResult')}';
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
        $('#PaperDealer_grid').omGrid({
            dataSource:'${createLink(controller:'paperDealer',action:'jsonList')}?only=1',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'paperDealer.paperNum.label', default: 'paperNum')}", name : 'paperNum', width : 120, align : 'center'} ,
                {header : "${message(code: 'paperDealer.zcinfoNum.label', default: 'zcinfoNum')}", name : 'zcinfoNum', width : 120, align : 'center'},
                {header : "${message(code: 'paperDealer.vin.label', default: 'vin')}", name : 'vin', width : 120, align : 'center'},
                {header : "${message(code: 'paperDealer.type.label', default: 'type')}", name : 'type', width : 80, align : 'center'},
                {header : "${message(code: 'paperDealer.createrId.label', default: 'createrId')}", name : 'createrId', width :100, align : 'center'},
                {header : "${message(code: 'paperDealer.createTime.label', default: 'createTime')}", name : 'createTime', width : 100, align : 'center'},
                {header : "${message(code: 'paperDealer.updaterId.label', default: 'updaterId')}", name : 'updaterId', width : 100, align : 'center'},
                {header : "${message(code: 'paperDealer.updateTime.label', default: 'updateTime')}", name : 'updateTime', width :100, align : 'center'}
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
