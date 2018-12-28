<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="关联合格证信息" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <g:if test="${flash.message}">
            <div class="message" role="status" style="color: red;max-width:100%;white-space:normal; display:block; word-break:break-all">${flash.message}</div>
        </g:if>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <input id="btn_create" type="button" class="create" value="添加"/>
            <input id="btn_delete" type="button" class="delete" value="删除"/>
            <input id="btn_back" type="button" class="delete" value="返回"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="content_grid" style="border:0px"></div>
        </div>
    </div>
    <input type="hidden" id="goodsInfo" />
</div>

<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $("#btn_create").click(function(){
            $.omMessageBox.prompt({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'请填写需要关联的VIN',
                onClose:function(v){
                    if(v){
                        if(v===false){
                            return;
                        }
                        var uploadUrl = '${createLink(controller:'uploadInvoice',action:'addHgzs')}';
                        var ids = '';
                        $.post(uploadUrl,{"hgzs":v ,"id":"${goods?.id}"},function(data,textStatus){
                            if(textStatus=='success'){
                                 if(data.flag=="success"){
                                     var selections = $('#content_grid').omGrid('reload');
                                     alert(data.msg);
                                 }else{
                                     alert(data.msg);
                                 }
                            }else{
                                alert("AJAX申请出现错误！");
                            }

                        },'json');
                    }
                }
            });

        });

        $("#btn_back").click(function(){
            window.location.href="${createLink(controller:"uploadInvoice",action:"toInvoice")}";
        });

        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#content_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'uploadInvoice',action:'deleteHgzs')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.veh_Zchgzbh_0+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds,id:"${goods?.id}"},function(data,textStatus){
                            if(textStatus=='success'){
                                data=eval("("+data+")");
                                if(data.flag=="success"){
                                    var selections = $('#content_grid').omGrid('reload');
                                    alert(data.msg);
                                }else{
                                    alert(data.msg);
                                }
                            }else{
                                alert("AJAX申请出现错误！");
                            }
                        },'text');
                    }
                }
            });
        });

    })


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
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#content_grid').omGrid({
            dataSource:'${createLink(controller:'uploadInvoice',action:'zcinfoJsonList')}?id=${goods?.id}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "完整合格证编号", name : 'veh_Zchgzbh_0', width : 150, align : 'center'}  ,
                {header:"车辆识别代号",name:"veh_Clsbdh",width:150,align:'center'},
                {header : "车辆品牌/型号", name : 'veh_Clpp', width : 120, align : 'center'} ,
                {header : "配置序列号", name : 'veh_pzxlh', width : 100, align : 'center' },
                {header : "上传时间", name : 'uploader_Time', width : 150, align : 'center'}
            ]

        });
    }
</script>
</body>
</html>
