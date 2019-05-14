<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="轻型柴油车（国四）/轻型柴油车（国五）环保信息" />
    <title>轻型柴油车（国四）/轻型柴油车（国五）环保信息</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>车辆型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_clxh" name="en_clxh" maxlength="200" /></span></td>
                    <td style="width: 80px;text-align: right"><span>发动机型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_fdjxh" name="en_fdjxh" style="width: 90px" /></span></td>
                    <td style="width: 90px;text-align: right"><span>信息公开编号：</span></td>
                    <td style="width: 230px"><span><g:textField id="en_xxgkbh" name="en_xxgkbh" style="width: 220px"/></span></td>
                    <td  style="width: 60px;text-align: right"><span>类型：</span></td>
                    <td style="width: 100px"><span><select name="type" id="type">
                        <option selected="selected" ></option>
                        <option value="0">轻型柴油车（国四）</option>
                        <option value="1">轻型柴油车（国五）</option>
                    </select></span></td>
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
                <input id="btn_view" type="button"  class="create" value="${message(code: 'default.button.view.label', default: 'View')}"/>
                <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'Import')}" />
            <export:formats formats="['excel']"  controller="oil" action="export" style="border:0px;margin-left:220px;margin-top:-22px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="lightDiesel_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'lightDiesel',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#lightDiesel_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#en_clxh").val('');
                $("#type").val('');
                $("#en_fdjxh").val('');
                $("#en_xxgkbh").val('');
            }
        });
        $(".excel").click(function(){
            var en_clxh=$("#en_clxh").val();
            var type=$("#type").val();
            var en_fdjxh=$("#en_fdjxh").val();
            var en_xxgkbh=$("#en_xxgkbh").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'lightDiesel',action:'exportSearch')}?format=excel&extension=xlsx&en_clxh="+en_clxh+"&type="+en_clxh+"&en_fdjxh="+en_fdjxh+"&en_xxgkbh="+en_xxgkbh;
            url=encodeURI(url);
            window.location.href=url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'lightDiesel',action:'edit')}';
            var selected = $('#lightDiesel_grid').omGrid('getSelections',true);
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
            var selections = $('#lightDiesel_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'lightDiesel',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#lightDiesel_grid').omGrid('reload');
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
            var url = '${createLink(controller:'lightDiesel',action:'show')}';
            var selected = $('#lightDiesel_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });

        $('#btn_imp').click(function(){
            var url='${createLink(controller:'lightDiesel',action:'importPage')}';
            showModelDialog(url);
        });
    })



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'lightDiesel',action:'getCompResult')}';
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
        $('#lightDiesel_grid').omGrid({
            dataSource:'${createLink(controller:'lightDiesel',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :390,
            colModel:[
                {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},

                {header : "GB 18352.3-2005检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 3847-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz4', width : 200, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 90, align : 'center'},
                {header : "后处理装置型号", name : 'en_hclzzxh', width : 100, align : 'center'},
                {header : "涂层/载体/封装生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "喷油泵型号", name : 'en_pybxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "喷油器型号", name : 'en_pyqxh', width : 250, align : 'center'} ,
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'} ,
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "中冷器型号", name : 'en_zlqxh', width : 250, align : 'center'},
                {header : "变速箱型式", name : 'en_bsxxs', width : 250, align : 'center'},
                {header : "消音器型号", name : 'en_xyqxh', width : 250, align : 'center'},
                {header : "IUPR监测功能", name : 'en_iuprjcgn', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                renderer:function(value,rowData,rowIndex){
                if(value==0){
                    return '<span style="color: green">轻型柴油车（国四）</span>';
                }else if(value==1){
                    return '<span style="color: red">轻型柴油车（国五）</span>';
                }
                }},

                {header : "创建时间", name : 'create_time', width : 150, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 150, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 150, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :150, align : 'center'},
                {header : "备注", name : 'en_bz', width : 150, align : 'center'}]
        });
    }
    function backFunc(result){
        $('#lightDiesel_grid').omGrid('reload');
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
