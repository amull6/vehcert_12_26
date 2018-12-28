

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="未提交环保清单信息" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page"   style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>创建时间：</span></td>
                    <td style="width: 130px" width="100"><span>
                        <c:dataPicker id="time1" name="time1" showTime="false" style="width:100px"  dateFormat='yy-mm-dd'></c:dataPicker>
                    </span></td>
                    <td style="width: 20px">
                        至
                    </td>
                    <td width="100" style="width: 100px"><span>
                        <c:dataPicker id="time2" name="time2"  showTime="false" style="width:100px" dateFormat='yy-mm-dd'></c:dataPicker>
                    </span></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="btn_query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="btn_clear" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
            <input id="btn_synToServer" type="button" class="create" value="${message(code: 'zcinfo.button.syn.label', default: 'submit Server')}"/>
            <input id="btn_synAllToServer" type="button" class="create" value="${message(code: 'zcinfo.button.synAll.label', default: 'submit Server')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="environmentInfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>

<script>
    $(function() {
        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'envirSubmit',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#environmentInfo_grid').omGrid('setData',url);
            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#time1").val('');
                $("#time2").val('');
            }
        });
        $('#page').css({height:$(document).height()-16});
        $("#btn_synToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'envirSubmit',action:'uploadToServer')}?"+$('#form_query').serialize();
            $.post(url,{},function(data,textStatus){
                if("数据同步成功"==data){
                    $("#environmentInfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    showTopTip(data);
                }else{
                    $("#environmentInfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    alert(data);
                }
            },'text');
        });
        $("#btn_synAllToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'envirSubmit',action:'uploadAllToServer')}";
            $.post(url,{},function(data,textStatus){
                if("数据同步成功"==data){
                    $("#environmentInfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    showTopTip(data);
                }else{
                    $("#environmentInfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    alert(data);
                }
            },'text');
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
            }]
        });
        buildRightGrid();
    });

    function buildRightGrid(){
        var time1=$("#time1").val();
        var time2=$("#time2").val();
        $('#environmentInfo_grid').omGrid({
            dataSource:'${createLink(controller:'envirSubmit',action:'jsonList')}?time1='+time1+'&time2='+time2,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :390,
            colModel:[
                {header : "VIN", name : 'en_clsbdh', width : 120, align : 'center'},
                {header : "信息公开号", name : 'en_xxgkbh', width :195, align : 'center'},
                {header : "商标", name : 'en_sb', width : 40, align : 'center'},
                {header : "生产厂地址", name : 'en_sccdz', width : 130, align : 'center'},
                {header : "发的动机号", name : 'en_fdjh', width : 130, align : 'center'},
                {header : "生产时间", name : 'en_scrq', width : 90, align : 'center'},
                {header : "出厂时间", name : 'en_ccrq', width : 95, align : 'center'},
                {header : "出厂试验", name : 'en_ccsy', width : 150, align : 'center'} ,
                {header : "出厂结论", name : 'en_ccjl', width : 50, align : 'center'},
                {header : "公开网站", name : 'en_gfwz', width : 70, align : 'center'},

                {header : "发动机商标", name : 'en_fdjsb', width : 60, align : 'center'},
                {header : "发动机生产厂地址", name : 'en_fdjsccdz', width : 200, align : 'center'},

                {header : "车辆型号", name : 'en_clxh', width : 120, align : 'center'},
                {header : "发动机型号", name : 'en_fdjxh', width : 80, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 70, align : 'center'},

                {header : "创建时间", name : 'create_time', width : 120, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 100, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center'},
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
        $('#environmentInfo_grid').omGrid('reload');
    }
</script>
</body>
</html>