
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'zcinfo.transZcinfoToSap.label', default: 'transZcinfoToSap')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:0px;border:1px;">
        <div>
            <form id="form_query" style="margin:0px;">
                <input type="hidden" name="transFlag" id='transFlag' value="${transFlag}"/>
                <table style="width:1000px;border:0px;">
                <tr>
                        <td width="80px" align="right"><span><g:message code="carstorage.createTime.label" default="Start Time" />：</span></td>
                        <td width="250px">
                            <span><c:dataPicker id="createTime" name="createTime" showTime="false"   dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker>
                        -
                        <c:dataPicker id="createTime1" name="createTime1"  showTime="false"  dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker></span></td>

                        <td width="80px" align="right">合格证编号：</td>
                        <td width="80px"><span><g:textField id="veh_Zchgzbh" name="veh_Zchgzbh" style="width: 110px"  /></span></td>

                        <td width="60px" align="right">发动机号：</td>
                        <td width="80px"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" style="width: 80px"  /></span></td>


                        <td width="40px" align="right">VIN：</td>
                        <td width="80px"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" style="width: 150px"  /></span></td>

                    </tr>
                    <tr>
                        <td width="80px" align="right"><span>发证日期：</span></td>
                        <td width="250px"><span>
                            <c:dataPicker id="veh_Fzrq" name="veh_Fzrq" style="width:100px;" showTime="false" dateFormat='yy年mm月dd日' value=""></c:dataPicker>
                            -
                            <c:dataPicker id="veh_Fzrq1" name="veh_Fzrq1" style="width:100px;" showTime="false"   dateFormat="yy年mm月dd日" ></c:dataPicker>
                        </td>

                        <td width="80px" align="right">SAP唯一号：</td>
                        <td width="80px">
                            <span><g:textField id="SAP_No" name="SAP_No" style="width: 110px"  />
                            </span>
                        </td>

                        <td width="60px" align="right">状态：</td>
                        <td width="80px">
                            <span>
                                <span>
                                    <select name="SAP_status" id="SAP_status" style="width: 92px">
                                        <option value="0" >未传输</option>
                                        <option value="1">已传输</option>
                                        <option value="" selected="selected">全部</option>
                                    </select>
                                </span>
                            </span>
                        </td>
                        <td width="10px"></td>

                        <td align="left" valign="middle" width="150px">
                            <span>
                                <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                            </span>
                            <span>
                                <input id="btn_query" type="button"   value="<g:message code="default.button.query.label" default="Query" />"/>
                            </span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div style="margin-right:8px;margin-top:8px;">

            <div id="transfer_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    var dialogId='';
    var initId='';
    $(function() {


        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'transferToSap',action:'jsonListSearch')}";
                url+='?'+$('#form_query').serialize();
                $('#transfer_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#SAP_status").val('');
                $("#veh_Fzrq").val('');
                $("#veh_Fzrq1").val('');
                $("#veh_Clsbdh").val('');
                $("#veh_Zchgzbh").val('');
                $("#veh_Fdjh").val('');
                $("#SAP_No").val('');
                $("#createTime").val('');
                $("#createTime1").val('');

            }
        });

    })
    // 加载时执行的语句
    $(function() {
        //构建左侧树
        //buildLeftTree();
        $("#veh_Fzrq").val('');
        $("#veh_Fzrq1").val('');
        $("#createTime").val('');
        $("#createTime1").val('');
        createTime
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#transfer_grid').omGrid({
            dataSource:"${createLink(controller:'transferToSap',action:'jsonListSearch')}?transFlag="+$('#transFlag').val(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :500,
            colModel:[
                {header : "${message(code: 'zcinfo.operate.label')}", width : 100, align : 'center',name : 'SAP_status',
                renderer:function(value,rowData,rowIndex){
                    var data = rowData;
                    if(value==0){
                        return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:transferData('+rowIndex+');" >传输</a>';
                    }else{
                        return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:cancleTransfer('+rowIndex+');" >撤销</a>'+
                                '&nbsp;'+'&nbsp;'+'&nbsp;'+'&nbsp;'+
                                '<a id="btn_modify" class="btn_basic blue_black"  href="javascript:modify('+rowIndex+');">修改</a>';
                    }
                }},
                {header : "${message(code: 'zcinfo.SAP_status.label')}", width : 50, align : 'center',name : 'SAP_status',
                    renderer:function(value,rowData,rowIndex){
                        var text="";
                        if(value==1){
                            return '<span style="color: blue">已传输</span>';
                        }else{
                            return '<span style="color: red">未传输</span>';
                        }
                        return  text;
                    }},
                {header : "${message(code: 'zcinfo.SAP_No.label')}", width :90, align : 'center',name : 'SAP_No',
                    renderer:function(value,rowData,rowIndex){
                        if(value!=null&&value!=''){
                            return value;
                        }else{
                            return '<a id="btn_add_sapNo" class="btn_basic blue_black"  href="javascript:addSapNo('+rowIndex+');" >添加SAP唯一号</a>';
                        }
                    }
                },
                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label')}", width : 115, align : 'center',name : 'veh_Zchgzbh'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label')}", width : 125, align : 'center',name : 'veh_Clsbdh'},
                {header : "${message(code: 'zcinfo.veh_Clfl.label')}", width : 125, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label')}", width : 100, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'zcinfo.veh_Fzrq.label')}", width : 100, align : 'center',name : 'veh_Fzrq'},
                {header : "${message(code: 'zcinfo.transDate.label')}", width : 120, align : 'center',name : 'transDate'},
                {header : "${message(code: 'zcinfo.createTime.label')}", width : 120, align : 'center',name : 'createTime'}
                %{--{header : "${message(code: 'zcinfo.uploader_Time.label')}", width : 120, align : 'center',name : 'uploaderTime'}--}%
            ]
        });
    }
    ///添加SAP序列号
    function addSapNo(index){
        console.log(1)
        var data = $("#transfer_grid").omGrid("getData").rows[index];
        if(data.SAP_No==''||data.SAP_No==null){
            $.omMessageBox.prompt({
                title:'SAP车辆唯一号',
                content:'请填写SAP车辆唯一号！',
                onClose:function(value){
                    if(value===false){ //按了取消或ESC
                        return;
                    }
                    if(value==''){
                        alert('请填写SAP车辆唯一号');
                        return false; //不关闭弹出窗口
                    }
                    var url = '${createLink(controller:'transferToSap',action:'zcinfoAddSapNo')}';
                    $.post(url,{"id":data.id,"SAP_No":value},function(data,textStatus){
                        alert(data);
                    },'text');
                }
            });
        }else{
            alert('SAP序列号已经存在，不允许修改');
        }
    }
    function transferData(index){
        var data = $("#transfer_grid").omGrid("getData").rows[index];
        $.omMessageBox.prompt({
            title:'SAP车辆唯一号',
            content:'请填写SAP车辆唯一号！',
            onClose:function(value){
                if(value===false){ //按了取消或ESC
                    return;
                }
                if(value==''){
                    alert('请填写SAP车辆唯一号');
                    return false; //不关闭弹出窗口
                }

                var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData')}?ZOPIND=1&&opFlag=0';
                var id= data.id;
                $.post(uploadUrl,{"id":id,"SAP_No":value},function(data,textStatus){
                    if(data.type=='S'){
                        alert(data.msg);
                        var selections = $('#transfer_grid').omGrid('reload');
                    }else if(data.type=='E'){
                        alert(data.msg);
                    }
                },'json');
            }
        });

    }
    function modify(index){
        var data = $("#transfer_grid").omGrid("getData").rows[index];
        $.omMessageBox.prompt({
            title:'SAP车辆唯一号',
            content:'请填写SAP车辆唯一号！',
            onClose:function(value){
                if(value===false){ //按了取消或ESC
                    return;
                }
                if(value==''){
                    alert('请填写SAP车辆唯一号');
                    return false; //不关闭弹出窗口
                }

                var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData')}?ZOPIND=1&&opFlag=1';
                var id= data.id;
                $.post(uploadUrl,{"id":id,"SAP_No":value},function(data,textStatus){
                    if(data.type=='S'){
                        alert(data.msg);
                        var selections = $('#transfer_grid').omGrid('reload');
                    }else if(data.type=='E'){
                        alert(data.msg);
                    }
                },'json');
            }
        });

    }
    //删除按钮
    %{--$('#btn_delete').click(function(){--}%
        %{--//这里利用Ajax删除数据--}%
        %{--var selections = $('#warehouseInfo_grid').omGrid('getSelections',true);--}%
        %{--if(selections.length==0){--}%
            %{--alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');--}%
            %{--return;--}%
        %{--}--}%
        %{--$.omMessageBox.confirm({--}%
            %{--title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',--}%
            %{--content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',--}%
            %{--onClose:function(v){--}%
                %{--if(v){--}%
                    %{--var deleteUrl = '${createLink(controller:'warehouseInfo',action:'delete')}';--}%
                    %{--var deleteIds = '';--}%
                    %{--$.each(selections,function(i,item){--}%
                        %{--deleteIds += item.id+'_';--}%
                    %{--});--}%
                    %{--$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){--}%
                        %{--alert(data);--}%
                        %{--var selections = $('#warehouseInfo_grid').omGrid('reload');--}%
                        %{--var content = "<div class='message'>"+data+"</div>";--}%
                        %{--showTopTip(content);--}%
                    %{--},'text');--}%
                %{--}--}%
            %{--}--}%
        %{--});--}%

    %{--});--}%
    function cancleTransfer(index){
        var data = $("#transfer_grid").omGrid("getData").rows[index];
        $.omMessageBox.confirm({
            title:'撤销操作',
            content:'确认撤销SAP唯一号'+data.SAP_No+'对应的信息?',
            onClose:function(v){
                if(v){
                    var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData')}?ZOPIND=2&&opFlag=2';
                    var id= data.id;
                    $.post(uploadUrl,{"id":id},function(data,textStatus){
                        if(data.type=='S'){
                            alert(data.msg);
                            var selections = $('#transfer_grid').omGrid('reload');
                        }else if(data.type=='E'){
                            alert(data.msg);
                        }
                    },'json');
                }
            }
        });
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
