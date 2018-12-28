<%@ page import="cn.com.wz.vehcert.environment.Environment" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'environment.label', default: 'environment')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="environment.veh_Clxh.label" default="veh_Clxh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clxh" name="veh_Clxh" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="environment.veh_Clmc.label" default="veh_Clmc" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clmc" name="veh_Clmc" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="environment.kind.label" default="kind" />：</span></td>
                    <td width="100"><span><select name="kind" id="kind">
                        <option selected="selected" value="san">国三环保</option>
                        <option value="si">国四环保</option>
                        <option value="beijing">北京环保</option>
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
            <c:resourceAuth resourceCode="dv_huanbao_add">
                <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_huanbao_update">
                <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_huanbao_delete">
                <input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
            </c:resourceAuth>
            <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
            <c:resourceAuth resourceCode="dv_huanbao_imp">
                <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_huanbao_export">
            <export:formats formats="['excel']"  controller="environment" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
            </c:resourceAuth>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="environment_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'environment',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#environment_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Clxh").val('');
                $("#veh_Clmc").val('');
            }
        });
        $(".pdf").click(function(){
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'environment',action:'export')}?format=pdf&extension=pdf";
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            $('.excel').attr('href','javascript:void(0);');
            var veh_Clxh=$("#veh_Clxh").val();
            var veh_Clmc=$("#veh_Clmc").val();
            var kind=$("#kind").val();
            //当节点没有选中的情况，导出s通讯录中所有的数据
            var url="${createLink(controller:'environment',action:'export')}?format=excel&extension=xlsx&veh_Clxh="+veh_Clxh+"&veh_Clmc="+veh_Clmc+"&kind="+kind;
            url=encodeURI(url);
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'environment',action:'create')}';
            var selected = $('#environment_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'environment',action:'edit')}';
            var selected = $('#environment_grid').omGrid('getSelections',true);
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
            var selections = $('#environment_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'environment',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#environment_grid').omGrid('reload');
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
            var url = '${createLink(controller:'environment',action:'show')}';
            var selected = $('#environment_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'environment',action:'importPage')}';
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
        var url='${createLink(controller:'environment',action:'getCompResult')}';
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
        $('#environment_grid').omGrid({
            dataSource:'${createLink(controller:'environment',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'environment.veh_Clxh.label', default: 'veh_Clxh')}", name : 'veh_Clxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'environment.veh_Clmc.label', default: 'veh_Clmc')}", name : 'veh_Clmc', width : 150, align : 'center'},
                {header : "${message(code: 'environment.veh_Clfl.label', default: 'veh_Clfl')}", name : 'veh_Clfl', width : 100, align : 'center'},
                {header : "${message(code: 'environment.veh_Fdjxh.label', default: 'veh_Fdjxh')}", name : 'veh_Fdjxh', width : 150, align : 'center'},
                {header : "${message(code: 'environment.veh_Ggpx.label', default: 'veh_Ggpx')}", name : 'veh_Ggpx', width : 100, align : 'center'},
                {header : "${message(code: 'environment.kind.label', default: 'kind')}", name : 'kind', width : 100, align : 'center'},
                {header : "${message(code: 'environment.veh_Bz.label', default: 'veh_Bz')}", name : 'veh_Bz', width :360, align : 'center'}]
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
