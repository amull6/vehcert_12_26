
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'nzinfo.transNZinfoToSap.label', default: 'transNZinfoToSap')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:0px;border:1px;">
        <div>
            <form id="form_query" style="margin:0px;width: 1100px">
                <input type="hidden" name="transFlag" id='transFlag' value="${transFlag}"/>
                <table style="width:1100px;border:0px;">
                <tr>
                    <td width="60px" align="right"><span><g:message code="carstorage.createTime.label" default="Start Time" />：</span></td>
                    <td width="265px">
                        <span><c:dataPicker id="createTime" name="createTime" showTime="false"   dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker>
                            -
                            <c:dataPicker id="createTime1" name="createTime1"  showTime="false"  dateFormat='yy-mm-dd' style="width:100px;"></c:dataPicker></span></td>

                        <td width="50px" align="right">车辆编号</td>
                        <td width="60px"><span><g:textField id="veh_Clbh" name="veh_Clbh" style="width: 100px" /></span></td>

                    <td width="70px" align="right">SAP唯一号：</td>
                    <td width="60px">
                        <span><g:textField id="SAP_No" name="SAP_No" style="width: 80px" />
                        </span>
                    </td>
                    <td width="40px" align="right">状态：</td>
                    <td width="80px">
                    <span>
                    <span>
                    <select name="SAP_status" id="SAP_status" style="width: 65px">
                    <option value="">全部</option>
                    <option value="0">未传输</option>
                    <option value="1">已传输</option>
                    </select>
                    </span>
                    </span>
                    </td>
                    <td align="left" colspan="2" valign="middle" width="250px">
                        &nbsp   &nbsp
                        <span>
                            <input id="btn_query" type="button"   value="<g:message code="default.button.query.label" default="Query" />"/>
                        </span>
                        <span>
                            <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
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
                var url = "${createLink(controller:'transferToSap',action:'jsonListSearch_garbage')}";
                url+='?'+$('#form_query').serialize();
                $('#transfer_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#SAP_status").val('');
                $("#veh_Clbh").val('');
                $("#SAP_No").val('');
                $("#createTime").val('');
                $("#createTime1").val('');

            }
        });

    })
    // 加载时执行的语句
    $(function() {
        //构建左侧树
        var now =new Date();
        var month=now.getMonth()+1;
        if(month<10){
            month="0"+month;
        }
        $('#createTime').val( now.getFullYear()+"-"+month+"-"+now.getDate());
        $("#createTime1").val('');
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#transfer_grid').omGrid({
            dataSource:"${createLink(controller:'transferToSap',action:'jsonListSearch_garbage')}?"+$('#form_query').serialize(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 10,
            height :450,
            colModel:[
                {header : "${message(code: 'nzinfo.operate.label')}", width : 80, align : 'center',name : 'SAP_status',
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
                {header : "${message(code: 'nzinfo.SAP_status.label')}", width : 60, align : 'center',name : 'SAP_status',
                    renderer:function(value,rowData,rowIndex){
                        var text="";
                        if(value==1){
                            return '<span style="color: blue">已传输</span>';
                        }else{
                            return '<span style="color: red">未传输</span>';
                        }
                        return  text;
                    }},
                {header : "${message(code: 'nzinfo.SAP_No.label')}", width : 90, align : 'center',name : 'SAP_No'},
                {header : "${message(code: 'nzinfo.veh_Hgzbh.label')}", width : 100, align : 'center',name : 'veh_Hgzbh'},
                {header : "机具编号", width : 100, align : 'center',name : 'veh_Clbh'},
                {header : "产品名称", width : 100, align : 'center',name : 'veh_Cpmc'},
                {header : "${message(code: 'nzinfo.veh_Ccrq.label')}", width : 100, align : 'center',name : 'veh_Ccrq'},
                {header : "${message(code: 'nzinfo.transDate.label')}", width : 120, align : 'center',name : 'transDate'},
                {header : "${message(code: 'nzinfo.createTime.label')}", width : 120, align : 'center',name : 'createTime'}
            ]
        });
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

                var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData_Garbage')}?ZOPIND=1&&opFlag=0';
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

                var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData_Garbage')}?ZOPIND=1&&opFlag=1';
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


    function cancleTransfer(index){
        var data = $("#transfer_grid").omGrid("getData").rows[index];
        $.omMessageBox.confirm({
            title:'撤销操作',
            content:'确认撤销SAP唯一号'+data.SAP_No+'对应的信息?',
            onClose:function(v){
                if(v){
                    var uploadUrl = '${createLink(controller:'transferToSap',action:'transferData_Garbage')}?ZOPIND=2&&opFlag=2';
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
