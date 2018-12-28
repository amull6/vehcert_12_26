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
            %{--保存是不是打印新版的一致性证书标记的隐藏域--}%
            <g:hiddenField name="newCoC" id='newCoC' value="${newCoC}"/>
            %{--保存是不是打印新版的一致性证书标记的隐藏域--}%
            <table style="width:100%;border:0px;">
                <tr>
                    <td align="left" valign="middle">
                        <input id="btn_exit" type="button" width="60"  value="<g:message code="default.button.exit.label" default="Exit" />"/>
                        <input id="btn_return" type="button" width="60"  value="<g:message code="default.button.return.label" default="Return" />"/>
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
                            if(${usertype}=='0'){
                                window.location.href="${createLink(controller:'cocPrint',action:'cheJianList')}";
                            }else{
                                window.location.href="${createLink(controller:'cocPrint',action:'ownLoadList')}";
                            }

                        }
                    }
                });
            }
        });
        $('#btn_return').omButton({
            width:80,
            onClick:function(){
                window.location.href="javascript:history.go(-1);";
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
            dataSource:'${createLink(controller:'cocPrint',action:'jsonZcinfoOfList')}?zcinfoID=${zcinfoID}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height : 500,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:goToPrint('+rowIndex+');" >确定选择</a>'
                        }},
                {header : "${message(code: 'coc.coc_clzl.label', default: '车辆种类')}", name : 'coc_clzl', width : 150, align : 'left'},
                {header : "${message(code: 'coc.coc_ltgg.label', default: '轮胎规格')}", name : 'coc_ltgg', width : 250, align : 'left'},
                {header : "${message(code: 'coc.coc_clxh.label', default: '车辆型号')}", name : 'coc_clxh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_yzxzsbh.label', default: '一致性证书编号')}", name : 'coc_yzxzsbh', width : 180, align : 'center'},
                {header : "${message(code: 'coc.coc_clmc.label', default: '车辆名称')}", name : 'coc_clmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clys.label', default: '车身颜色')}", name : 'coc_clys', width : 80, align : 'center'},
                {header : "${message(code: 'coc.coc_fdjxh.label', default: '发动机型号')}", name : 'coc_fdjxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxxldhmc.label', default: '车型系列代号名称')}", name : 'coc_cxxldhmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_hxcd.label', default: '货箱长度')}", name : 'coc_hxcd', width : 80, align : 'center'},
                {header : "${message(code: 'coc.coc_hxkd.label', default: '货箱宽度')}", name : 'coc_hxkd', width : 80, align : 'center'},
                {header : "${message(code: 'coc.coc_hxgd.label', default: '货箱高度')}", name : 'coc_hxgd', width : 80, align : 'center'},

                {header : "${message(code: 'coc.coc_zj.label', default: '轴距')}", name : 'coc_zj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_lj.label', default: '轮距')}", name : 'coc_lj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gbthps.label', default: '钢板弹簧片数')}", name : 'coc_gbthps', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zws.label', default: '座位数')}", name : 'coc_zws', width : 100, align : 'center'}
            ]
        });
    }

    //根据选择行转向打印页面
    function goToPrint(index){
        var data1 = $("#coc_content").omGrid("getData").rows[index];
        //使用newCoC标记来在cocPrint/jsonUpdate方法区分是老版一直性证书还是新版一致性证书
        var updatezcinfoUrl = '${createLink(controller:'cocPrint',action:'jsonUpdate')}?zcinfoID=${zcinfoID}&usertype=${usertype}&newCoC=${newCoC}&id='+data1.id;
        $.post(updatezcinfoUrl,function(data,textStatus){
            var  jsonUpdate =eval("("+data+")");  //生成一致性证书返回的值：一致性证书id和二维码图片的路径名称
            var cocId= jsonUpdate.id;
            var QRpiUrl=jsonUpdate.QRpiUrl
//            alert(data.id);
//            alert(data.QRpiUrl);
            if(data=="data"){
                //保存打印一致性证书信息出错
                showTopTip("生成已打印一致性证书信息出错!")
            }else{
                %{--var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?newCoC=${newCoC}&cocPrnID='+data+'&usertype='+${usertype};--}%
                var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?newCoC=${newCoC}&cocPrnID='+cocId+'&QRpiUrl='+QRpiUrl+'&usertype='+${usertype};
                window.location.href = url;
            }
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
