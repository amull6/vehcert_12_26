<%@ page import="cn.com.wz.stvehcert.TractorModle" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="车型信息" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span>车型：</span></td>
                    <td width="100"><span><g:textField id="modle" name="modle" maxlength="200" value="${tractorModleInstance?.modle}"/></span></td>
                    <td width='20'></td>
                    <td width="80"><span>车型名称：</span></td>
                    <td width="100"><span><g:textField id="modleName" name="modleName" maxlength="200" value="${tractorModleInstance?.modleName}"/></span></td>
                    <td width="80"><span>发动机型号：</span></td>
                    <td width="100"><span><g:textField id="veh_Fdjxh" name="veh_Fdjxh" maxlength="200" value="${tractorModleInstance?.veh_Fdjxh}"/></span></td>
                    <td width='20'></td>
                    <td width="80" align="right"><span><g:message code="carstorage.car_storage_no.label" default="car_storage_no" />：</span></td>
                    <td width="140"><span><g:textField id="car_storage_no1" name="car_storage_no1" style="width: 140px" maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="140"><span><g:textField id="car_storage_no2" name="car_storage_no2" style="width: 140px" maxlength="200"/></span></td>
                    <td width="5"></td>
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
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'import')}"/>
            <a id="btn_impFile" class="import" href="${createLink(controller:'downLoad',action:'downFile')}?fileName=分解后公告信息导入模板.xls&filePath=/upload/template/StCarStorageTemplate.xls" >导入模板</a>
            <export:formats formats="['excel']"    style="border:0px;margin-left:320px;margin-top:-26px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="modle_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'tractorModle',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#modle_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#modle").val('');
                $("#modleName").val('');
                $("#car_storage_no1").val("");
                $("#car_storage_no2").val("");
                $("#turnOff").val("");
            }
        });
        $("#modle").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#modleName").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'tractorModle',action:'create')}';
            var selected = $('#modle_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'tractorModle',action:'edit')}';
            var selected = $('#modle_grid').omGrid('getSelections',true);
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
            var selections = $('#modle_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'tractorModle',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#modle_grid').omGrid('reload');
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
        $(".excel").click(function(){
            var veh_Jxxlb=$("#modle").val();
            var veh_Clxh=$("#modleName").val();
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'tractorModle',action:'exportSearch')}?format=excel&extension=xlsx&"+$('#form_query').serialize();
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
        $("#btn_imp").click(function(){
            var url='${createLink(controller:'tractorModle',action:'importPage')}';
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
        var url='${createLink(controller:'tractorModle',action:'getCompResult')}';
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
        $('#modle_grid').omGrid({
            dataSource:'${createLink(controller:'tractorModle',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :380,
            colModel:[
                {header : "车型", name : 'modle', width : 120, align : 'center'} ,
                {header : "车型名称", name : 'modleName', width : 120, align : 'center'},
                {header : "发动机型号", name : 'veh_Fdjxh', width : 120, align : 'center'} ,
                {header : "机械大类", width : 60, align : 'center',name : 'veh_Jxdl'},
                {header : "燃料类型", width : 60, align : 'center',name : 'veh_Rllx'},
                {header : "机械小类别", width : 60, align : 'center',name : 'veh_Jxxlb'},
                {header : "主要参数", width : 60, align : 'center',name : 'veh_Zycs'},
                {header : "排放阶段", width : 60, align : 'center',name : 'veh_Pfjd'},
                {header : "类型", width : 60, align : 'center',name : 'modle_type',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">山拖公告</span>';
                        }else if(value==1){
                            return '<span style="color: red">农装公告</span>';
                        }
                    }},
                {header : "创建人", name : 'createid', width : 60, align : 'center'},
                {header : "创建时间", name : 'createTime', width :150, align : 'center'},
                {header : "更新人", name : 'lastUpdateId', width : 60, align : 'center'},
                {header : "更新时间", name : 'lastUpdateTime', width : 150, align : 'center'},
                {header : "${message(code: 'carstorage.car_storage_no.label')}", width : 200, align : 'center',name : 'car_storage_no'}
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
