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
                    <td width="80"><span><g:message code="nzcarstorage.veh_Clxh.label" default="Clxh"/>：</span></td>
                    <td width="50"><span><g:textField id="tf_veh_Clxh" name="veh_Clxh" maxlength="50"
                                                      style="width: 100px" value="${nzcarstorage?.veh_Clxh}"/></span>
                    </td>
                    <td width="80"><span><g:message code="carstorage.veh_Fdjxh.label" default="Fdjxh"/>：</span></td>
                    <td width="50"><span><g:textField id="tf_veh_Fdjxh" name="veh_Fdjxh" maxlength="50"
                                                      style="width: 100px" value="${nzcarstorage?.veh_Fdjxh}"/></span>
                    </td>
                    <td width="80"><span><g:message code="nzcarstorage.veh_Jxxlb.label" default="Jxxlb"/>：</span></td>
                    <td width="60"><span><g:textField id="tf_veh_Jxxlb" name="veh_Jxxlb" style="width: 30px"
                                                      maxlength="60" value="${nzcarstorage?.veh_Jxxlb}"/></span></td>
                    <td width="80" align="right"><span><g:message code="carstorage.car_storage_no.label"
                                                                  default="car_storage_no"/>：</span></td>
                    <td width="100"><span><g:textField id="car_storage_no1" name="car_storage_no1" style="width: 100px"
                                                       maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="100"><span><g:textField id="car_storage_no2" name="car_storage_no2" style="width: 100px"
                                                       maxlength="200"/></span></td>
                    <td width="5"></td>
                    <td width='80px'>公告类型：</td>
                    <td width='80px'>
                        <select name="car_storage_type" id="car_storage_type">
                            <option value="">全部</option>
                            <option value="0">小拖</option>
                            <option value="1">中拖</option>
                            <option value="2">大拖</option>
                            <option value="3">玉米收</option>
                            <option value="4">稲麦机</option>
                            <option value="5">青贮机</option>
                        </select>
                    </td>
                    <td width='60px'>是否出口：</td>
                    <td width='60px'>
                        <select name="is_Exp" id="is_Exp">
                            <option value="">全部</option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                    <td width='60px'>状态：</td>
                    <td width='60px'>
                        <select name="turnOff" id="turnOff">
                            <option value="">全部</option>
                            <option value="0">启用</option>
                            <option value="1">停用</option>
                        </select>
                    </td>
                    <td align="left" valign="middle">
                        %{--<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>--}%
                        %{--<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>--}%
                    </td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button"
                               value="<g:message code="default.button.query.label" default="Query"/>"/>
                    </td>
                    <td align="left" valign="middle">
                        <input id="btn_clear" type="button"
                               value="<g:message code="default.button.clear.label" default="Clear"/>"/>
                    </td>
                </tr>
            </tbody>
            </table>
        </form>

        <div>
            <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
                <c:resourceAuth resourceCode="dv_carStorage_create">
                    <input id="btn_create" type="button" class="create"
                           value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_edit">
                    <input id="btn_edit" type="button" class="edit"
                           value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                %{--<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" />--}%
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_disable">
                    <input id="btn_Disable" type="button" class="delete" value="停用"/>
                %{--<input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />--}%
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_disableSearch">
                    <input id="btn_disableSearch" type="button" class="delete" value="依条件停用"/>
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_view">
                    <input id="btn_view" type="button" class="view"
                           value="${message(code: 'default.button.view.label', default: 'View')}"/>
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_import">
                    <input id="btn_imp" type="button" class="import"
                           value="${message(code: 'default.button.import.label', default: 'import')}"/>
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_carStorage_importModel">
                    <a id="btn_impFile" class="import"
                       href="${createLink(controller: 'downLoad', action: 'downFile')}?fileName=分解后公告信息导入模板.xls&filePath=/upload/template/carStorageTemplate.xls">导入模板</a>
                    <export:formats formats="['excel']" style="border:0px;margin-left:445px;margin-top:-23px;"/>
                </c:resourceAuth>
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
                var url = "${createLink(controller:'nzCarStorage',action:'nzSearch')}";
                url+='?'+$('#form_query').serialize();
                $('#carStorage_grid').omGrid('setData',url);
            }
        });
        $(".excel").click(function(){
            var veh_Jxxlb=$("#tf_veh_Jxxlb").val();
            var veh_Clxh=$("#tf_veh_Clxh").val();
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'nzCarStorage',action:'exportSearch')}?format=excel&extension=xlsx&"+$('#form_query').serialize();
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
                $("#is_Exp").val("");
            }
        });

        $("#btn_imp").click(function(){
            var url='${createLink(controller:'nzCarStorage',action:'importPage')}';
            showModelDialog(url);
        });
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'nzCarStorage',action:'toCreate')}';
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'nzCarStorage',action:'toUpdate')}';
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
                        var deleteUrl = '${createLink(controller:'nzCarStorage',action:'jsonDelete')}';
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
                        var deleteUrl = '${createLink(controller:'nzCarStorage',action:'disableCarStorage')}';
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
                       var deleteUrl = '${createLink(controller:'nzCarStorage',action:'jsonDeleteSearch')}';
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
                        var deleteUrl = '${createLink(controller:'nzCarStorage',action:'disableSearchCarStorage')}';
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
            var url = '${createLink(controller:'nzCarStorage',action:'show')}';
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
        var url='${createLink(controller:'nzCarStorage',action:'getCompResult')}';
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
            dataSource:"${createLink(controller:'nzCarStorage',action:'nzSearch')}?date="+new Date(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12, // 分页显示，每页显示30条
            height : 440,
            colModel:[
                {header : "${message(code: 'nzcarstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 100, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'nzcarstorage.veh_Jxdl.label')}", width : 60, align : 'center',name : 'veh_Jxdl'},
                {header : "${message(code: 'nzcarstorage.veh_Rllx.label')}", width : 60, align : 'center',name : 'veh_Rllx'},
                {header : "${message(code: 'nzcarstorage.veh_Jxxlb.label')}", width : 60, align : 'center',name : 'veh_Jxxlb'},
                {header : "${message(code: 'nzcarstorage.veh_Zycs.label')}", width : 110, align : 'center',name : 'veh_Zycs'},
                {header : "${message(code: 'nzcarstorage.veh_Pfjd.label')}", width : 60, align : 'center',name : 'veh_Pfjd'},

                {header : "${message(code: 'nzcarstorage.veh_Scqymc.label')}", width :110, align : 'center',name : 'veh_Scqymc'},
                {header : "${message(code: 'nzcarstorage.veh_Pp.label')}", width : 60, align : 'center',name : 'veh_Pp'},
                {header : "${message(code: 'nzcarstorage.veh_Lx.label')}", width : 60, align : 'center',name : 'veh_Lx'},
                {header : "${message(code: 'nzcarstorage.veh_Gl.label')}", width : 60, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'nzcarstorage.veh_Pfbz.label')}", width : 60, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'nzcarstorage.veh_Jsys.label')}", width : 100, align : 'center',name : 'veh_Jsys'},
                {header : "${message(code: 'nzcarstorage.veh_Zxczxs.label')}", width : 110, align : 'center',name : 'veh_Zxczxs'},
                {header : "${message(code: 'nzcarstorage.veh_Zcrs.label')}", width : 60, align : 'center',name : 'veh_Zcrs'},
                {header : "${message(code: 'nzcarstorage.veh_Lzs.label')}", width : 60, align : 'center',name : 'veh_Lzs'},
                {header : "${message(code: 'nzcarstorage.veh_Zj.label')}", width : 60, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'nzcarstorage.veh_Lts.label')}", width : 60, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'nzcarstorage.veh_Ltgg.label')}", width : 60, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'nzcarstorage.veh_Qlj.label')}", width : 60, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'nzcarstorage.veh_Hlj.label')}", width : 60, align : 'center',name : 'veh_Hlj'},

                {header : "${message(code: 'nzcarstorage.veh_Lds.label')}", width : 60, align : 'center',name : 'veh_Lds'},
                {header : "${message(code: 'nzcarstorage.veh_Ldgg.label')}", width : 60, align : 'center',name : 'veh_Ldgg'},
                {header : "${message(code: 'nzcarstorage.veh_Gj.label')}", width : 60, align : 'center',name : 'veh_Gj'},
                {header : "${message(code: 'nzcarstorage.veh_Wkc.label')}", width : 60, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'nzcarstorage.veh_Wkk.label')}", width : 60, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'nzcarstorage.veh_Wkg.label')}", width : 60, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'nzcarstorage.veh_Bdqyl.label')}", width : 100, align : 'center',name : 'veh_Bdqyl'},
                {header : "${message(code: 'nzcarstorage.veh_Zxsyzl.label')}", width : 100, align : 'center',name : 'veh_Zxsyzl'},
                {header : "${message(code: 'nzcarstorage.veh_Zdyyzzl.label')}", width : 100, align : 'center',name : 'veh_Zdyyzzl'},
                {header : "${message(code: 'nzcarstorage.veh_Zxbh.label')}", width : 100, align : 'center',name : 'veh_Zxbz'},
                {header : "${message(code: 'nzcarstorage.veh_Scqydz.label')}", width : 100, align : 'center',name : 'veh_Scqydz'},
                {header : "${message(code: 'nzcarstorage.veh_Lxfs.label')}", width : 100, align : 'center',name : 'veh_Lxfs'},

                {header : "${message(code: 'nzcarstorage.veh_Lhqxs.label')}", width : 100, align : 'center',name : 'veh_Lhqxs'},
                {header : "${message(code: 'nzcarstorage.veh_Qpzxs.label')}", width : 100, align : 'center',name : 'veh_Qpzxs'},
                {header : "${message(code: 'nzcarstorage.veh_Hpzxs.label')}", width : 100, align : 'center',name : 'veh_Hpzxs'},
                {header : "${message(code: 'nzcarstorage.veh_Jzxs.label')}", width : 100, align : 'center',name : 'veh_Jzxs'},
                {header : "${message(code: 'nzcarstorage.veh_Hpzxs.label')}", width : 100, align : 'center',name : 'veh_Hpzxs'},
                {header : "${message(code: 'nzcarstorage.veh_Jssxs.label')}", width : 100, align : 'center',name : 'veh_Jssxs'},
                {header : "${message(code: 'nzcarstorage.veh_Bhxs.label')}", width : 100, align : 'center',name : 'veh_Bhxs'},
                {header : "${message(code: 'nzcarstorage.veh_Ddlj.label')}", width : 100, align : 'center',name : 'veh_Ddlj'},

                {header : "${message(code: 'nzcarstorage.car_storage_type.label')}", width : 60, align : 'center',name : 'car_storage_type',
                    renderer:function(value,rowData,rowIndex){
                    if(value==0){
                        return '<span style="color: green">小拖</span>';
                    }else if(value==1){
                        return '<span style="color: red">中拖</span>';
                    }else if(value==2){
                        return '<span style="color: yellow">大拖</span>';
                    }else if(value==3){
                        return '<span style="color: blue">玉米收</span>';
                    }else if(value==4){
                        return '<span style="color: darkslateblue">稲麦机</span>';
                    }else if(value==5){
                        return '<span style="color: palevioletred">青贮机</span>';
                    }
                }},
                {header : "${message(code: 'carstorage.createrName.label')}", width : 60, align : 'center',name : 'createrName'},
                {header : "${message(code: 'carstorage.createTime.label')}", width : 150, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.updaterName.label')}", width : 60, align : 'center',name : 'updaterName'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 150, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'carstorage.car_storage_no.label')}", width : 60, align : 'center',name : 'car_storage_no'},
                {header : "是否出口", width : 50, align : 'center',name : 'is_Exp',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">否</span>';
                        }else if(value==1){
                            return '<span style="color: red">是</span>';
                        }
                    }},
                {header : "状态", width : 50, align : 'center',name : 'turnOff',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">启用</span>';
                        }else if(value==1){
                            return '<span style="color: red">停用</span>';
                        }
                    }},
                {header : "停用时间", width : 150, align : 'center',name : 'turnOffTime'}
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
