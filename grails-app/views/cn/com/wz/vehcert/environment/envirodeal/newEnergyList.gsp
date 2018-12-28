<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="新能源汽车环保信息随车清单打印" />
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
            dataSource:'${createLink(controller:'newEnergy',action:'jsonList')}?en_clxh=${en_clxh}&en_fdjxh=${en_fdjxh}',
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
                {header : "信息公开编号", name : 'en_xxgkbh', width : 100, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 100, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz1', width : 130, align : 'center'},

                {header : "电动机型号/生产厂", name : 'en_ddjxh', width : 100, align : 'center'},
                {header : "整车控制器型号/版本号/生产厂", name : 'en_zckzqxh', width : 130, align : 'center'},
                {header : "储能装置型号/生产厂", name : 'en_cnzzxh', width : 130, align : 'center'} ,
                {header : "电池容量/续航里程", name : 'en_dcrl', width : 130, align : 'center'},
                {header : "创建时间", name : 'create_time', width : 100, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 100, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 100, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :100, align : 'center'}]
        });
    }

    //根据选择行转向打印页面
    function goToPrint(index){
        var data1 = $("#coc_content").omGrid("getData").rows[index];
        //使用newCoC标记来在cocPrint/jsonUpdate方法区分是老版一直性证书还是新版一致性证书
        var updatezcinfoUrl = '${createLink(controller:'envirPrint',action:'jsonNewEnergyUpdate')}?zcinfoID=${zcinfoID}&&id='+data1.id;
        $.post(updatezcinfoUrl,function(data,textStatus){
            var  jsonUpdate =eval("("+data+")");  //生成一致性证书返回的值：一致性证书id和二维码图片的路径名称
            var newEnergyInfoId= jsonUpdate.id;
            var QRpiUrl=jsonUpdate.QRpiUrl
            var BarPicUrl=jsonUpdate.BarPicUrl
//            alert(data.id);
//            alert(data.QRpiUrl);
            if(data=="data"){
                //保存打印一致性证书信息出错
                showTopTip("生成环保信息随车清单信息出错!")
            }else{
                var url = '${createLink(controller:'envirPrint',action:'newEnergyPrint')}?newEnergyInfoId='+newEnergyInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';
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
