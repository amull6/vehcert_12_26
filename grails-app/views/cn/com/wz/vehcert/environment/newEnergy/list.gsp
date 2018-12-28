<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="新能源汽车环保信息" />
    <title>新能源汽车环保信息</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>车辆型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_clxh" name="en_clxh" maxlength="200" /></span></td>
                    <td style="width: 80px;text-align: right"><span>电动机型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_ddjxh" name="en_ddjxh" style="width: 90px" /></span></td>
                    <td style="width: 90px;text-align: right"><span>信息公开编号：</span></td>
                    <td style="width: 230px"><span><g:textField id="en_xxgkbh" name="en_xxgkbh" style="width: 220px"/></span></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                %{--<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>--}%
                <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                <input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
                <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
                <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
                <export:formats formats="['excel']"  controller="oil" action="export" style="border:0px;margin-left:195px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="newEnergy_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'newEnergy',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#newEnergy_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#en_clxh").val('');
                $("#en_ddjxh").val('');
                $("#en_xxgkbh").val('');
            }
        });
        $(".excel").click(function(){
            var en_clxh=$("#en_clxh").val();
            var type=$("#type").val();
            var en_fdjxh=$("#en_ddjxh").val();
            var en_xxgkbh=$("#en_xxgkbh").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'newEnergy',action:'exportSearch')}?format=excel&extension=xlsx&en_clxh="+en_clxh+"&type="+en_clxh+"&en_fdjxh="+en_fdjxh+"&en_xxgkbh="+en_xxgkbh;
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'newEnergy',action:'edit')}';
            var selected = $('#newEnergy_grid').omGrid('getSelections',true);
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
            var selections = $('#newEnergy_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'newEnergy',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#newEnergy_grid').omGrid('reload');
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
            var url = '${createLink(controller:'newEnergy',action:'show')}';
            var selected = $('#newEnergy_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'newEnergy',action:'importPage')}';
            showModelDialog(url);
        });
    })



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'newEnergy',action:'getCompResult')}';
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
        $('#newEnergy_grid').omGrid({
            dataSource:'${createLink(controller:'newEnergy',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :390,
            colModel:[
                {header : "信息公开编号", name : 'en_xxgkbh', width : 100, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 100, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz1', width : 130, align : 'center'},

                {header : "电动机型号/生产厂", name : 'en_ddjxh', width : 100, align : 'center'},
                {header : "整车控制器型号/版本号/生产厂", name : 'en_zckzqxh', width : 130, align : 'center'},
                {header : "储能装置型号/生产厂", name : 'en_cnzzxh', width : 130, align : 'center'} ,
                {header : "电池容量/续航里程", name : 'en_dcrl', width : 130, align : 'center'},
                {header : "创建时间", name : 'create_time', width : 100, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 100, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 100, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :100, align : 'center'}]
        });
    }
    function backFunc(result){
        $('#newEnergy_grid').omGrid('reload');
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
