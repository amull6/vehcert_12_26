LightDiesel<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="轻型柴油车国四/轻型柴油车国五环保信息随车清单打印" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        ${zcinfoID}
        ${en_clxh}
        ${en_fdjxh}
        ${en_type}
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
        $('#coc_content').omGrid({
            dataSource:'${createLink(controller:'lightDiesel',action:'jsonList')}?en_clxh=${en_clxh}&en_type=${en_type}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height : 430,
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

                {header : "GB 18352.3-2005检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 3847-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz4', width : 200, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 90, align : 'center'},
                {header : "后处理装置型号", name : 'en_hclzzxh', width : 100, align : 'center'},
                {header : "涂层/载体/封装生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "喷油泵型号", name : 'en_pybxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'} ,
                {header : "喷油器型号", name : 'en_pyqxh', width : 250, align : 'center'},
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'} ,
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "中冷器型号", name : 'en_zlqxh', width : 250, align : 'center'},
                {header : "变速箱型式", name : 'en_bsxxs', width : 250, align : 'center'},
                {header : "消音器型号", name : 'en_xyqxh', width : 250, align : 'center'},
                {header : "IUPR监测功能", name : 'en_iuprjcgn', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">轻型柴油车（国四）</span>';
                        }else if(value==1){
                            return '<span style="color: red">轻型柴油车（国五）</span>';
                        }
                    }}
            ]
        });
    }

    //根据选择行转向打印页面
    function goToPrint(index){
        var data1 = $("#coc_content").omGrid("getData").rows[index];
        var updatezcinfoUrl = '${createLink(controller:'envirPrint',action:'jsonLightDieselUpdate')}?zcinfoID=${zcinfoID}&&id='+data1.id;
        $.post(updatezcinfoUrl,function(data,textStatus){
            var  jsonUpdate =eval("("+data+")");  //生成一致性证书返回的值：一致性证书id和二维码图片的路径名称
            var lightDieselInfoId= jsonUpdate.id;
            var QRpiUrl=jsonUpdate.QRpiUrl
            var BarPicUrl=jsonUpdate.BarPicUrl
//            alert(data.id);
//            alert(data.QRpiUrl);
            if(data=="data"){
                //保存打印一致性证书信息出错
                showTopTip("生成环保信息随车清单信息出错!")
            }else{
                var url = '${createLink(controller:'envirPrint',action:'lightDieselPrint')}?lightDieselInfoId='+lightDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';
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
