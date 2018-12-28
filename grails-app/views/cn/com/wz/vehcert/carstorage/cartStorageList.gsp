<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'carstorage.label', default: 'carstorage')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page"   style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="carstorage.veh_Clxh.label" default="Clxh" />：</span></td>
                    <td width="50"><span ><g:textField id="tf_veh_Clxh" name="veh_Clxh" maxlength="50" style="width: 100px" value="${carstorage?.veh_Clxh}"/></span></td>
                    <td width="5"></td>
                    <td width="60"><span><g:message code="carstorage.veh_Clmc.label" default="clmc" />：</span></td>
                    <td width="60"><span ><g:textField id="tf_veh_Clmc" name="veh_Clmc"  style="width: 80px" maxlength="60" value="${carstorage?.veh_Clmc}"/></span></td>
                    <td width="80" align="right"><span><g:message code="carstorage.car_storage_no.label" default="car_storage_no" />：</span></td>
                    <td width="140"><span><g:textField id="car_storage_no1" name="car_storage_no1" style="width: 140px" maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="140"><span><g:textField id="car_storage_no2" name="car_storage_no2" style="width: 140px" maxlength="200"/></span></td>
                    <td width="5"></td>
                    <td width='60px'>公告类型：</td>
                    <td>
                        <select name="carStorageType" id="carStorageType">
                            <option value="">全部</option>
                            <option value="0" >汽车</option>
                            <option value="1" >农用车</option>
                        </select>
                    </td>
                    <td width='60px'>状态：</td>
                    <td>
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
                </tr>
                <tr>
                    <td width="65"><span>发动机型号：</span></td>
                    <td width="60"><span ><g:textField id="veh_Fdjxh" name="veh_Fdjxh" maxlength="60" style="width: 100px" value="${carstorage?.veh_Fdjxh}"/></span></td>
                    <td width="5"></td>
                    <td width="60"><span>轮胎规格：</span></td>
                    <td width="60"><span ><g:textField id="veh_Ltgg" name="veh_Ltgg"  style="width: 80px" maxlength="60" value="${carstorage?.veh_Ltgg}"/></span></td>
                    <td width="80" align="right"><span>货箱尺寸：</span></td>
                        <td width="40" colspan="3"><span>长<g:textField id="veh_Hxnbc" name="veh_Hxnbc"  style="width: 84px" maxlength="80" value="${carstorage?.veh_Hxnbc}"/></span>
                        <span>宽<g:textField id="veh_Hxnbk" name="veh_Hxnbk"  style="width: 84px" maxlength="80" value="${carstorage?.veh_Hxnbk}"/></span>
                        <span>高<g:textField id="veh_Hxnbg" name="veh_Hxnbg"  style="width: 84px" maxlength="80" value="${carstorage?.veh_Hxnbg}"/></span>
                   </td>

                    <td width="5"></td>
                    <td width='60px'>车辆状态：</td>
                    <td>
                        <span>
                            <select style="width: 65px" name="veh_Clztxx" id="veh_Clztxx">
                                <option value="">全部</option>
                                <option value="QX" >整车</option>
                                <option value="DP" >底盘</option>
                            </select>
                        </span>
                    </td>
                    <td width='60px'>油耗类型：</td>
                    <td>
                        <span>
                            <select  name="veh_Yhlx" id="veh_Yhlx">
                                <option value="">全部</option>
                                <option value="1" >有</option>
                                <option value="0" >无</option>
                            </select>
                            %{--<g:textField id="veh_Jsszcrs" name="veh_Jsszcrs" style="width: 65px" maxlength="65" value="${carstorage?.veh_Jsszcrs}"/>--}%
                        </span>
                    </td>

                    <td align="left" valign="middle">
                    </td>
                </tr>
                <tr>
                    <td width="65"><span>驾驶室类型：</span></td>
                    <td width="60"><span ><g:textField id="veh_Jsslx" name="veh_Jsslx" maxlength="60" style="width: 100px" value="${carstorage?.veh_Jsslx}"/></span></td>
                    <td width="5"></td>
                    <td width="60"><span>轴距：</span></td>
                    <td width="60"><span ><g:textField id="veh_Zj" name="veh_Zj"  style="width: 80px" maxlength="60" value="${carstorage?.veh_Zj}"/></span></td>
                    <td width="80" align="right"><span>准乘人数：</span></td>
                    <td width="40" colspan="3">
                        <span>
                        <g:textField id="veh_Jsszcrs" name="veh_Jsszcrs" style="width: 123px" maxlength="120" value="${carstorage?.veh_Jsszcrs}"/>
                        </span>
                        &nbsp;<span>&nbsp;板簧：</span>
                        <span>
                            <g:textField id="veh_Gbthps" name="veh_Gbthps" style="width: 125px" maxlength="120" value="${carstorage?.veh_Gbthps}"/>
                        </span>
                    <td width="5"></td>
                    <td width='60px'>前轮距：</td>
                    <td width='40px'>
                        <span><g:textField id="veh_Qlj" name="veh_Qlj" style="width: 65px" maxlength="65"  value="${carstorage?.veh_Qlj}" /></span>
                    </td>
                    <td width='40px'>后轮距</td>
                    <td width='40px'>
                        <span><g:textField id="veh_Hlj" name="veh_Hlj" style="width: 50px" maxlength="50"  value="${carstorage?.veh_Hlj}" /></span>
                    </td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <div>
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
        <c:resourceAuth resourceCode="dv_carStorage_create">
            <input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_edit">
            <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            %{--<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" />--}%
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_delete">
            <input id="btn_delete" type="button"  class="delete" value="删除" />
        %{--<input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />--}%
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_disableDelete">
        <input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_disable">
            <input id="btn_Disable" type="button"  class="delete" value="停用" />
            %{--<input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />--}%
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_disableSearch">
            <input id="btn_disableSearch" type="button"  class="delete" value="依条件停用" />
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_view">
            <input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_import">
            <input id="btn_imp" type="button"  class="import" value="${message(code: 'default.button.import.label', default: 'import')}"/>
        </c:resourceAuth>
        <c:resourceAuth resourceCode="dv_carStorage_importModel">
            <a id="btn_impFile" class="import" href="${createLink(controller:'downLoad',action:'downFile')}?fileName=分解后公告信息导入模板.xls&filePath=/upload/template/carStorageTemplate.xls" >导入模板</a>
            <export:formats formats="['excel']"    style="border:0px;margin-left:645px;margin-top:-23px;"/>
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
                var url = "${createLink(controller:'carStorage',action:'search')}";
                url+='?'+$('#form_query').serialize();
                $('#carStorage_grid').omGrid('setData',url);
            }
        });
        $(".excel").click(function(){
            var veh_Clmc=$("#tf_veh_Clmc").val();
            var veh_Clxh=$("#tf_veh_Clxh").val();
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'CarStorage',action:'exportSearch')}?format=excel&extension=xlsx&"+$('#form_query').serialize();
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#tf_veh_Clxh").val('');
                $("#tf_veh_Clmc").val('');
                $("#carStorageType").val("");
                $("#car_storage_no1").val("");
                $("#car_storage_no2").val("");
                $("#turnOff").val("");

                $("#veh_Fdjxh").val('');
                $("#veh_Ltgg").val('');
                $("#veh_Hxnbc").val("");
                $("#veh_Hxnbk").val("");
                $("#veh_Hxnbg").val("");

                $("#veh_Jsszcrs").val('');
                $("#veh_Gbthps").val('');
                $("#veh_Jsslx").val("");
                $("#veh_Zj").val("");
                $("#veh_Wkc").val("");
                $("#veh_Wkk").val("");
                $("#veh_Wkg").val("");

                $("#veh_Qlj").val("");
                $("#veh_Hlj").val("");
                $("#veh_Clztxx").val("");
                $("#veh_Yhlx").val("");


            }
        });

        $("#btn_imp").click(function(){
            var url='${createLink(controller:'carStorage',action:'importPage')}';
            showModelDialog(url);
        });

        $('#btn_create').click(function(){
            var url = '${createLink(controller:'carStorage',action:'toCreate')}';
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'carStorage',action:'toUpdate')}';
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
                        var deleteUrl = '${createLink(controller:'carStorage',action:'jsonDelete')}';
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
                        var deleteUrl = '${createLink(controller:'carStorage',action:'disableCarStorage')}';
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
           var veh_Clmc=$("#tf_veh_Clmc").val();
           var carStorageType=$("#carStorageType").val();

            var content='';
            if(veh_Clxh==''&&veh_Clmc==''&&carStorageType==''){
                content='您确定要删除所有记录吗？';
            }else{
                content='您确定删除吗？';
            }
            $.omMessageBox.confirm({
                title:'确认删除',
                content:content,
                onClose:function(value){
                     if(value){
                       var deleteUrl = '${createLink(controller:'carStorage',action:'jsonDeleteSearch')}';
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
            var veh_Clmc=$("#tf_veh_Clmc").val();
            var carStorageType=$("#carStorageType").val();
            var car_storage_no1=$("#car_storage_no1").val();
            var car_storage_no2=$("#car_storage_no2").val();

            var veh_Fdjxh= $("#veh_Fdjxh").val();
            var veh_Ltgg=$("#veh_Ltgg").val();
            var veh_Hxnbc=$("#veh_Hxnbc").val();
            var veh_Hxnbk=$("#veh_Hxnbk").val();
            var veh_Hxnbg=$("#veh_Hxnbg").val();

            var veh_Jsszcrs=$("#veh_Jsszcrs").val();
            var veh_Gbthps=$("#veh_Gbthps").val();
            var veh_Jsslx=$("#veh_Jsslx").val();
            var veh_Zj=$("#veh_Zj").val();
            var veh_Wkc=$("#veh_Wkc").val();
            var veh_Wkk=$("#veh_Wkk").val();
            var veh_Wkg=$("#veh_Wkg").val();

            var veh_Qlj=$("#veh_Qlj").val();
            var veh_Hlj=$("#veh_Hlj").val();
            var veh_Clztxx=$("#veh_Clztxx").val();
            var veh_Yhlx=$("#veh_Yhlx").val();

            var content='';
            if(veh_Clxh==''&&veh_Clmc==''&&carStorageType==''&&car_storage_no1==''&&car_storage_no2==''&&veh_Fdjxh==''&&veh_Ltgg==''&&veh_Hxnbc==''&&veh_Hxnbk==''&&veh_Hxnbg==''
                    &&veh_Jsszcrs==''&&veh_Gbthps==''&&veh_Jsslx==''&&veh_Zj==''&&veh_Qlj==''&&veh_Hlj==''&&veh_Clztxx==''&&veh_Yhlx==''){
                content='您确定要停用所有记录吗？';
            }else{
                content='您确定停用吗？';
            }
            $.omMessageBox.confirm({
                title:'确认停用',
                content:content,
                onClose:function(value){
                    if(value){
                        var deleteUrl = '${createLink(controller:'carStorage',action:'disableSearchCarStorage')}';
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
            var url = '${createLink(controller:'carStorage',action:'show')}';
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
        var url='${createLink(controller:'CarStorage',action:'getCompResult')}';
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
            dataSource:"${createLink(controller:'carStorage',action:'search')}?date="+new Date(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12, // 分页显示，每页显示30条
            height : 440,
            colModel:[
                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 120, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_VinFourBit.label')}", width : 80, align : 'center',name : 'veh_VinFourBit'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 100, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 100, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'carstorage.veh_cpid.label')}", width : 100, align : 'center',name : 'veh_cpid'},
                {header : "${message(code: 'carstorage.veh_Ggpc.label')}", width : 80, align : 'center',name : 'veh_Ggpc'},
                {header : "${message(code: 'carstorage.veh_qxhx.label')}", width : 80, align : 'center',name : 'veh_qxhx'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width : 80, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 100, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 80, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width : 80, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width : 80, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 80, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 80, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 80, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'carstorage.veh_Hxnbg.label')}", width : 80, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'carstorage.veh_Zzl.label')}", width : 80, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'carstorage.veh_Edzzl.label')}", width : 80, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'carstorage.veh_Zbzl.label')}", width : 80, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'carstorage.veh_Zzllyxs.label')}", width : 80, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'carstorage.veh_Zqyzzl.label')}", width : 80, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'carstorage.veh_Edzk.label')}", width : 80, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'carstorage.veh_Bgcazzdyxzzl.label')}", width : 80, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 80, align : 'center',name : 'veh_Jsszcrs'},

                {header : "${message(code: 'carstorage.veh_Hzdfs.label')}", width : 80, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'carstorage.veh_Cpggh.label')}", width : 100, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 120, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'carstorage.veh_jjlqj.label')}", width : 80, align : 'center',name : 'veh_jjlqj'},
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 80, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Yhlx.label')}", width : 80, align : 'center',name : 'veh_Yhlx'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 200, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 80, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 200, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 150, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

				{header : "${message(code: 'carstorage.veh_fgbsqy.label')}", width : 80, align : 'center',name : 'veh_fgbsqy'},
                {header : "${message(code: 'carstorage.veh_fgbsxh.label')}", width : 80, align : 'center',name : 'veh_fgbsxh'},
				{header : "${message(code: 'carstorage.veh_fgbssb.label')}", width : 80, align : 'center',name : 'veh_fgbssb'},

				{header : "${message(code: 'carstorage.veh_other.label')}", width : 80, align : 'center',name : 'veh_other'},
                {header : "${message(code: 'carstorage.veh_pzxlh.label')}", width : 100, align : 'center',name : 'veh_pzxlh'},

                {header : "${message(code: 'carstorage.createrName.label')}", width : 150, align : 'center',name : 'createrName'},
                {header : "${message(code: 'carstorage.createTime.label')}", width : 150, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.updaterName.label')}", width : 150, align : 'center',name : 'updaterName'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 150, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'carstorage.car_storage_no.label')}", width : 200, align : 'center',name : 'car_storage_no'},
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
