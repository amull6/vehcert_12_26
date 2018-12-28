<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'carstorage.label', default: 'nccarstorage')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page"   style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="carstorage.veh_Clxh.label" default="Clxh" />：</span></td>
                    <td width="50"><span ><g:textField id="tf_veh_Clxh" name="veh_Clxh" maxlength="50" style="width: 100px" value="${harvestcarstorage?.veh_Clxh}"/></span></td>
                    <td width="5"></td>
                    <td width="80">发动机型号</td>
                    <td width="50"><span ><g:textField id="tf_veh_Fdjxh" name="veh_Fdjxh" maxlength="50" style="width: 100px" value="${harvestcarstorage?.veh_Fdjxh}"/></span></td>
                    <td width="80">机械小类别</td>
                    <td width="60"><span ><g:textField id="tf_veh_Jxxlb" name="veh_Jxxlb"  style="width: 50px" maxlength="60" value="${harvestcarstorage?.veh_Jxxlb}"/></span></td>
                    <td width="80" align="right"><span><g:message code="carstorage.car_storage_no.label" default="car_storage_no" />：</span></td>
                    <td width="140"><span><g:textField id="car_storage_no1" name="car_storage_no1" style="width: 140px" maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="140"><span><g:textField id="car_storage_no2" name="car_storage_no2" style="width: 140px" maxlength="200"/></span></td>
                    <td width="5"></td>
                    <td width='80px'>公告类型：</td>
                    <td width='80px'>
                        <select name="car_storage_type" id="car_storage_type">
                            <option value="">全部</option>
                            <option value="0" >玉米收</option>
                            <option value="1" >稻麦机</option>
                        </select>
                    </td>
                    <td width='60px'>状态：</td>
                    <td width='60px'>
                        <select name="turnOff" id="turnOff">
                            <option value="">全部</option>
                            <option value="0" >启用</option>
                            <option value="1" >停用</option>
                        </select>
                    </td>
                    <td align="left" valign="middle">
                        %{--<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>--}%
                        %{--<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>--}%
                    </td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                    </td>
                    <td align="left" valign="middle">
                    <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                </td>
                </tr>
                </tbody>
            </table>
        </form>
        <div>
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
            <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            %{--<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" />--}%
            <input id="btn_Disable" type="button"  class="delete" value="停用" />
            %{--<input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />--}%
            <input id="btn_disableSearch" type="button"  class="delete" value="依条件停用" />
            <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'import')}"/>
            <a id="btn_impFile" class="import" href="${createLink(controller:'downLoad',action:'downFile')}?fileName=分解后公告信息导入模板.xls&filePath=/upload/template/carStorageTemplate.xls" >导入模板</a>
            <export:formats formats="['excel']"    style="border:0px;margin-left:445px;margin-top:-23px;"/>
        </fieldset>
    </div>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="carStorage_grid" style="border:0px"></div>
        </div>
    </div>
</div>

<script>

    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});

        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'harvestCarStorage',action:'harvestSearch')}";
                url+='?'+$('#form_query').serialize();
                $('#carStorage_grid').omGrid('setData',url);
            }
        });
        $(".excel").click(function(){
            var veh_Jxxlb=$("#tf_veh_Jxxlb").val();
            var veh_Clxh=$("#tf_veh_Clxh").val();
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'harvestCarStorage',action:'exportSearch')}?format=excel&extension=xlsx&"+$('#form_query').serialize();
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#tf_veh_Clxh").val('');
                $("#tf_veh_Jxxlb").val('');
                $("#car_storage_type").val("");
                $("#car_storage_no1").val("");
                $("#car_storage_no2").val("");
                $("#turnOff").val("");
            }
        });

        $("#btn_imp").click(function(){
            var url='${createLink(controller:'harvestCarStorage',action:'importPage')}';
            showModelDialog(url);
        });

        $('#btn_create').click(function(){
            var url = '${createLink(controller:'harvestCarStorage',action:'toCreate')}';
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'harvestCarStorage',action:'toUpdate')}';
            var selected = $('#carStorage_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
               window.location.href = url+'/'+selected[0].id;
            };
        });

        //公告删除已经弃用
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#carStorage_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'harvestCarStorage',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#carStorage_grid').omGrid('reload');
                            var content = data;
                            showTopTip(content);
                        },'text');
                    }
                }
            });
        });
        $('#btn_Disable').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#carStorage_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'harvestCarStorage',action:'disableCarStorage')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#carStorage_grid').omGrid('reload');
                            var content = data;
                            showTopTip(content);
                        },'text');
                    }
                }
            });
        });

        $('#btn_deleteSearch').click(function(){
            //这里利用Ajax删除数据
           var veh_Clxh= $("#tf_veh_Clxh").val();
           var veh_Jxxlb=$("#tf_veh_Jxxlb").val();
           var car_storage_type=$("#car_storage_type").val();

            var content='';
            if(veh_Clxh==''&&veh_Jxxlb==''&&car_storage_type==''){
                content='您确定要删除所有记录吗？';
            }else{
                content='您确定删除吗？';
            }
            $.omMessageBox.confirm({
                title:'确认删除',
                content:content,
                onClose:function(value){
                     if(value){
                       var deleteUrl = '${createLink(controller:'harvestCarStorage',action:'jsonDeleteSearch')}';
                       var dialogId=showModelWait();
                       var params=$('#form_query').serialize();
                       $.post(deleteUrl,params,function(data,textStatus){
                           parent.closeDialog(dialogId);
                          var selections = $('#carStorage_grid').omGrid('reload');
                          var content = data;
                         showTopTip(content);
                       },'text');
                     }
                }
            });
        });
        $('#btn_disableSearch').click(function(){
            //只是作公告的逻辑删除
            var veh_Clxh= $("#tf_veh_Clxh").val();
            var veh_Jxxlb=$("#tf_tf_veh_Jxxlb").val();
            var car_storage_type=$("#car_storage_type").val();
            var car_storage_no1=$("#car_storage_no1").val();
            var car_storage_no2=$("#car_storage_no2").val();

            var content='';
            if(veh_Clxh==''&&veh_Jxxlb==''&&car_storage_type==''&&car_storage_no1==''&&car_storage_no2==''){
                content='您确定要停用所有记录吗？';
            }else{
                content='您确定停用吗？';
            }
            $.omMessageBox.confirm({
                title:'确认停用',
                content:content,
                onClose:function(value){
                    if(value){
                        var deleteUrl = '${createLink(controller:'harvestCarStorage',action:'disableSearchCarStorage')}';
                        var dialogId=showModelWait();
                        var params=$('#form_query').serialize();
                        $.post(deleteUrl,params,function(data,textStatus){
                            parent.closeDialog(dialogId);
                            var selections = $('#carStorage_grid').omGrid('reload');
                            var content = data;
                            showTopTip(content);
                        },'text');
                    }
                }
            });
        });

        $('#btn_view').click(function(){
            var url = '${createLink(controller:'harvestCarStorage',action:'show')}';
            var selected = $('#carStorage_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
    })
    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'harvestCarStorage',action:'getCompResult')}';
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
            }]
        });
        buildRightGrid();
    });
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }
    function buildRightGrid(){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'harvestCarStorage',action:'harvestSearch')}?date="+new Date(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12, // 分页显示，每页显示30条
            height : 440,
            colModel:[
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "发动机型号", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "机械大类", width : 120, align : 'center',name : 'veh_Jxdl'},
                {header : "燃料类型", width : 80, align : 'center',name : 'veh_Rllx'},
                {header: "机械小类别", width: 80, align: 'center', name: 'veh_Jxxlb'},
                {header: "主要参数", width: 80, align: 'center', name: 'veh_Zycs'},
                {header: "排放标准", width: 80, align: 'center', name: 'veh_Pfjd'},
                {
                    header: "类型", width: 100, align: 'center', name: 'car_storage_type',
                    renderer: function (value, rowData, rowIndex) {
                        if (value == 0) {
                            return '<span style="color: green">玉米收</span>';
                        } else if (value == 1) {
                            return '<span style="color: red">稻麦机</span>';
                        }
                    }

                },
                {
                    header: "${message(code: 'carstorage.createrName.label')}",
                    width: 150,
                    align: 'center',
                    name: 'createrName'
                },
                {
                    header: "${message(code: 'carstorage.createTime.label')}",
                    width: 150,
                    align: 'center',
                    name: 'createTime'
                },
                {
                    header: "${message(code: 'carstorage.updaterName.label')}",
                    width: 150,
                    align: 'center',
                    name: 'updaterName'
                },
                {
                    header: "${message(code: 'carstorage.updateTime.label')}",
                    width: 150,
                    align: 'center',
                    name: 'updateTime'
                },
                {
                    header: "${message(code: 'carstorage.car_storage_no.label')}",
                    width: 200,
                    align: 'center',
                    name: 'car_storage_no'
                },
                {
                    header: "状态", width: 50, align: 'center', name: 'turnOff',
                    renderer: function (value, rowData, rowIndex) {
                        if (value == 0) {
                            return '<span style="color: green">启用</span>';
                        } else if (value == 1) {
                            return '<span style="color: red">停用</span>';
                        }
                    }
                },
                {header: "停用时间", width: 150, align: 'center', name: 'turnOffTime'}
            ]

        });
    }

    function showTopTip(content) {
        $.omMessageTip.show({
            title: '${message(code: 'default.tip.message.label', default: 'Message')}',
            content: content,
            timeout: 3000
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
    function backFunc(result){
        $('#carStorage_grid').omGrid('reload');
    }
</script>
</body>
</html>
