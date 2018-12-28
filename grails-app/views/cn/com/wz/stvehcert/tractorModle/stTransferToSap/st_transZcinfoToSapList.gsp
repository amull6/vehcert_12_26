
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
                        <td width="100px"><span><g:textField id="veh_Hgzbh" name="veh_Hgzbh"  style="width:100px;"/></span></td>

                        <td width="40px" align="right">生产编号</td>
                        <td width="80px"><span><g:textField id="veh_Scbh_0" name="veh_Scbh_0" style="width: 100px" /></span></td>

                        <td width="80px" align="right">发动机号：</td>
                        <td width="80px"><span><g:textField id="veh_Fdjh" name="veh_Fdjh" style="width: 100px" /></span></td>



                    </tr>
                    <tr>
                        <td width="80px" align="right"><span>生产日期：</span></td>
                        <td width="250px"><span>
                            <c:dataPicker id="veh_Scrq" name="veh_Scrq" style="width:100px;" showTime="false" dateFormat='yy年mm月dd日' value=""></c:dataPicker>
                            -
                            <c:dataPicker id="veh_Scrq1" name="veh_Scrq1" style="width:100px;" showTime="false"   dateFormat="yy年mm月dd日" ></c:dataPicker>
                        </td>

                        <td width="80px" align="right">SAP唯一号：</td>
                        <td width="80px">
                            <span><g:textField id="SAP_No" name="SAP_No" style="width: 100px" />
                            </span>
                        </td>

                        <td width="80px" align="right">状态：</td>
                        <td width="80px">
                            <span>
                                <span>
                                    <select name="SAP_status" id="SAP_status" style="width: 112px">
                                        <option value="">全部</option>
                                        <option value="0">未传输</option>
                                        <option value="1">已传输</option>
                                    </select>
                                </span>
                            </span>
                        </td>

                        %{--<td width="10px"></td>--}%

                        <td align="left" colspan="2" valign="middle" width="150px">
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

            <div id="STtransfer_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'stTransferToSAp',action:'jsonListSearch_ST')}";
                url+='?'+$('#form_query').serialize();
                console.log(url)
                $('#STtransfer_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#SAP_status").val('');
                $("#veh_Scrq").val('');
                $("#veh_Scrq1").val('');
                $("#veh_Hgzbh").val('');
                $("#veh_Scbh_0").val('');
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
        $("#veh_Scrq").val('');
        $("#veh_Scrq1").val('');
        $("#createTime").val('');
        $("#createTime1").val('');
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#STtransfer_grid').omGrid({
            dataSource:"${createLink(controller:'stTransferToSAp',action:'jsonListSearch_ST')}?transFlag="+$('#transFlag').val(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :500,
            colModel:[
                {header : "操作", width : 80, align : 'center',name : 'SAP_status',
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
                {header : "传输状态", width : 60, align : 'center',name : 'SAP_status',
                    renderer:function(value,rowData,rowIndex){
                        var text="";
                        if(value==1){
                            return '<span style="color: blue">已传输</span>';
                        }else{
                            return '<span style="color: red">未传输</span>';
                        }
                        return  text;
                    }},
                {header : "SAP唯一号", width : 80, align : 'center',name : 'SAP_No'},
                {header : "合格证编号", width : 130, align : 'center',name : 'veh_Hgzbh'},
                {header : "新生产编号", width : 130, align : 'center',name : 'veh_new_clbh'},
                {header : "生产编号", width : 100, align : 'center',name : 'veh_Scbh'},
                {header : "发动机号", width : 100, align : 'center',name : 'veh_Fdjh'},
                {header : "生产日期", width : 100, align : 'center',name : 'veh_Scrq'},
                {header : "传输日期", width : 120, align : 'center',name : 'transDate'},
                {header : "创建日期", width : 120, align : 'center',name : 'createTime'}
            ]
        });
    }

    function transferData(index){
        var data = $("#STtransfer_grid").omGrid("getData").rows[index];
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

                var uploadUrl = '${createLink(controller:'stTransferToSAp',action:'transferData_ST')}?ZOPIND=1&&opFlag=0';
                var id= data.id;
                $.post(uploadUrl,{"id":id,"SAP_No":value},function(data,textStatus){
                    if(data.type=='S'){
                        alert(data.msg);
                        var selections = $('#STtransfer_grid').omGrid('reload');
                    }else if(data.type=='E'){
                        alert(data.msg);
                    }
                },'json');
            }
        });

    }
    function modify(index){
        var data = $("#STtransfer_grid").omGrid("getData").rows[index];
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

                var uploadUrl = '${createLink(controller:'stTransferToSAp',action:'transferData_ST')}?ZOPIND=1&&opFlag=1';
                var id= data.id;
                $.post(uploadUrl,{"id":id,"SAP_No":value},function(data,textStatus){
                    if(data.type=='S'){
                        alert(data.msg);
                        var selections = $('#STtransfer_grid').omGrid('reload');
                    }else if(data.type=='E'){
                        alert(data.msg);
                    }
                },'json');
            }
        });

    }


    function cancleTransfer(index){
        var data = $("#STtransfer_grid").omGrid("getData").rows[index];
        $.omMessageBox.confirm({
            title:'撤销操作',
            content:'确认撤销SAP唯一号'+data.SAP_No+'对应的信息?',
            onClose:function(v){
                if(v){
                    var uploadUrl = '${createLink(controller:'stTransferToSAp',action:'transferData_ST')}?ZOPIND=2&&opFlag=2';
                    var id= data.id;
                    $.post(uploadUrl,{"id":id},function(data,textStatus){
                        if(data.type=='S'){
                            alert(data.msg);
                            var selections = $('#STtransfer_grid').omGrid('reload');
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
