<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'oil.label', default: 'oil')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="oil.veh_Clxh.label" default="veh_Clxh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clxh" name="veh_Clxh" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="oil.veh_Clmc.label" default="veh_Clmc" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clmc" name="veh_Clmc" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="oil.kind.label" default="kind" />：</span></td>
                    <td width="100"><span><select name="kind" id="kind">
                        <option selected="selected" value="sanOil">国三油耗</option>
                        <option value="siOil">国四油耗</option>
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
            <c:resourceAuth resourceCode="dv_youhao_imp">
                <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_youhao_export">
            <export:formats formats="['excel']"  controller="oil" action="export" style="border:0px;margin-left:260px;margin-top:-22px;"/>
        </c:resourceAuth>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="oil_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'oil',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#oil_grid').omGrid('setData',url);
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
           var url="${createLink(controller:'oil',action:'export')}?format=pdf&extension=pdf";
            url=encodeURI(url);
            window.location.href=url;
        });
        $(".excel").click(function(){
            var veh_Clxh=$("#veh_Clxh").val();
            var veh_Clmc=$("#veh_Clmc").val();
            var kind=$("#kind").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'oil',action:'export')}?format=excel&extension=xlsx&veh_Clxh="+veh_Clxh+"&veh_Clmc="+veh_Clmc+"&kind="+kind;
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'oil',action:'create')}';
            var selected = $('#oil_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'oil',action:'edit')}';
            var selected = $('#oil_grid').omGrid('getSelections',true);
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
            var selections = $('#oil_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'oil',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#oil_grid').omGrid('reload');
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
            var url = '${createLink(controller:'oil',action:'show')}';
            var selected = $('#oil_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'oil',action:'importPage')}';
            showModelDialog(url);
        });
    })



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'oil',action:'getCompResult')}';
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
        $('#oil_grid').omGrid({
            dataSource:'${createLink(controller:'oil',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'oil.veh_Clxh.label', default: 'veh_Clxh')}", name : 'veh_Clxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'oil.veh_Clmc.label', default: 'veh_Clmc')}", name : 'veh_Clmc', width : 150, align : 'center'},
                {header : "${message(code: 'oil.veh_Yh.label', default: 'veh_Yh')}", name : 'veh_Yh', width : 100, align : 'center'},
                {header : "${message(code: 'oil.veh_Fdjxh.label', default: 'veh_Fdjxh')}", name : 'veh_Fdjxh', width : 150, align : 'center'},
                {header : "${message(code: 'oil.veh_CarH.label', default: 'veh_CarH')}", name : 'veh_CarH', width : 100, align : 'center'},
                {header : "${message(code: 'oil.veh_GoodH.label', default: 'veh_GoodH')}", name : 'veh_GoodH', width : 100, align : 'center'},
                {header : "${message(code: 'oil.kind.label', default: 'kind')}", name : 'kind', width : 85, align : 'center'},
                {header : "${message(code: 'oil.veh_Bz.label', default: 'veh_Bz')}", name : 'veh_Bz', width :264, align : 'center'}]
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
