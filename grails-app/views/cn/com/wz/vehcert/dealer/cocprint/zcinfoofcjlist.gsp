<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="一致性证书信息" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">

        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td align="left" valign="middle">
                        <input id="btn_exit" type="button" width="60"  value="<g:message code="default.button.exit.label" default="Exit" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="margin-right:8px;margin-top:8px;" >
            <div id="coc_content" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});
        //绑定退出按钮事件
        $('#btn_exit').omButton({
            width:80,
            onClick:function(){
                $.omMessageBox.confirm({
                    title:'确认',
                    content:'您确定要退出吗？',
                    onClose:function(value){
                        if(value){
                            window.location.href="${createLink(controller:'cocPrint',action:'ownLoadList')}";
                        }
                    }
                });
            }
        });
        buildRightGrid();
    })
    // ################################加载时执行的语句########################
    $(function() {
        //刚加载页面后使右下部分内容显示
        $('#page').css({height:$(document).height()-16});
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            }]
        });
    });
    function buildRightGrid(){
        $('#coc_content').omGrid({
            dataSource:'${createLink(controller:'cocPrint',action:'jsonZcinfoOfCJList')}?zcinfoID=${zcinfoID}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height : 500,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 270, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<button id="btn_print" style="color:blue" class="btn_view" onClick="goToPrint('+rowIndex+')">确定选择</button>';
                        }},
                {header : "${message(code: 'coc.coc_clxh.label', default: '车辆型号')}", name : 'coc_clxh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_clmc.label', default: '车辆名称')}", name : 'coc_clmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clys.label', default: '车身颜色')}", name : 'coc_clys', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxcd.label', default: '货箱长度')}", name : 'coc_hxcd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxkd.label', default: '货箱宽度')}", name : 'coc_hxkd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxgd.label', default: '货箱高度')}", name : 'coc_hxgd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_ltgg.label', default: '轮胎规格')}", name : 'coc_ltgg', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zj.label', default: '轴距')}", name : 'coc_zj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_lj.label', default: '轮距')}", name : 'coc_lj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gbthps.label', default: '钢板弹簧片数')}", name : 'coc_gbthps', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_fdjxh.label', default: '发动机型号')}", name : 'coc_fdjxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxxldhmc.label', default: '车型系列代号名称')}", name : 'coc_cxxldhmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zws.label', default: '座位数')}", name : 'coc_zws', width : 100, align : 'center'}
            ]
        });
    }

    //根据选择行转向打印页面
    function goToPrint(index){
        var data = $("#zcinfo_grid").omGrid("getData").rows[index];
        var updatezcinfoUrl = '${createLink(controller:'cocPrint',action:'jsonUpdate')}?zcinfoID=${zcinfoID}&id='+data.id;
        $.post(updatezcinfoUrl,function(data,textStatus){

        },'text');
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
