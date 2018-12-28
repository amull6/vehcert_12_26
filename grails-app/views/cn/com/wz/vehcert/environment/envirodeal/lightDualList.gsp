<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="双燃料/轻型汽油车环保随车清单打印" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        %{--${zcinfoID}--}%
        %{--${en_clxh}--}%
        %{--${en_fdjxh}--}%
        %{--${en_type}--}%
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
            <div id="lightDual_grid" style="border:0px"></div>
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
                                window.location.href="${createLink(controller:'envirPrint',action:'cheJianList')}";
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
        $('#lightDual_grid').omGrid({
//            双燃料/轻型汽油车这些没有发动机型号信息的的随车清单，直接根据车辆型号查询
            dataSource:'${createLink(controller:'lightDual',action:'jsonList')}?en_clxh=${en_clxh}&en_type=${en_type}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height : 410,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:goToPrint('+rowIndex+');" >确定选择</a>'
                        }},
                {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},

                {header : "GB 18352.3-2013检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 18285-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz4', width : 200, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 90, align : 'center'},
                {header : "催化转化器型号", name : 'en_chzhqxh', width : 100, align : 'center'},
                {header : "涂层/载体/封装生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "燃油蒸发控制装置型号", name : 'en_rlzfkzzzxh', width : 250, align : 'center'},
                {header : "氧传感器型号", name : 'en_ycgqxh', width : 250, align : 'center'} ,
                {header : "曲轴箱排放控制装置型号", name : 'en_qzxpfkzzzxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "IUPR监测功能", name : 'en_iuprjcgn', width : 250, align : 'center'},
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'},
                {header : "燃气混合器型号【两用燃料】", name : 'en_rqhhqxh', width : 250, align : 'center'},
                {header : "燃气喷射单元型号 【两用燃料】", name : 'en_rqpsdyxh', width : 250, align : 'center'} ,
                {header : "变速器型式", name : 'en_bsqxs', width : 250, align : 'center'},
                {header : "消音器型号", name : 'en_xyqxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">两用燃料车</span>';
                        }else if(value==1){
                            return '<span style="color: red">轻型汽油车</span>';
                        }
                    }},
                {header : "中冷器型式", name : 'en_zlqxs', width : 250, align : 'center'}
                    ]
        });
    }

    //根据选择行转向打印页面
    function goToPrint(index){
        var data1 = $("#lightDual_grid").omGrid("getData").rows[index];
        //使用newCoC标记来在cocPrint/jsonUpdate方法区分是老版一直性证书还是新版一致性证书
        var updatezcinfoUrl = '${createLink(controller:'envirPrint',action:'jsonLightDualUpdate')}?zcinfoID=${zcinfoID}&&id='+data1.id;
        $.post(updatezcinfoUrl,function(data,textStatus){
            var  jsonUpdate =eval("("+data+")");  //生成一致性证书返回的值：一致性证书id和二维码图片的路径名称
            var heavyDieselInfoId= jsonUpdate.id;
            var QRpiUrl=jsonUpdate.QRpiUrl
            var BarPicUrl=jsonUpdate.BarPicUrl
//            alert(data.id);
//            alert(data.QRpiUrl);
            if(data=="data"){
                //保存打印一致性证书信息出错
                showTopTip("生成环保信息随车清单信息出错!")
            }else{
                var url = '${createLink(controller:'envirPrint',action:'lightDualPrint')}?lightDualPrintInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';
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
