<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'coc.label', default: 'CocCarStoragePrint')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<form id="page" style="margin:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <table style="width:100%;border:0px;">
            <tr>
                <td style="text-align: right"><span><g:message code="coc.coc_clmc.label" default="coc_clmc" />：</span></td>
                <td style="text-align: left"><span><g:textField id="coc_clmc" name="coc_clmc" style="width:120px " maxlength="120" value="${CocCarStoragePrint?.coc_clmc}"/></span></td>

                <td style="text-align: right"><span><g:message code="coc.coc_clxh.label" default="coc_clxh" />：</span></td>
                <td style="text-align: left"><span><g:textField id="coc_clxh" name="coc_clxh" style="width:120px "  maxlength="120" value="${CocCarStoragePrint?.coc_clxh}"/></span></td>

                <td style="text-align: right"><span><g:message code="coc.vin.label" default="VIN" />：</span></td>
                <td style="text-align: left"><span><g:textField id="coc_clsbdh" name="coc_clsbdh" style="width:135px "  maxlength="150" value="${CocCarStoragePrint?.coc_clsbdh}"/></span></td>

                <td style="text-align: right"><span><g:message code="zcinfo.createTime.label" default="createTime" />：</span></td>
                <td ><span>
                    <c:dataPicker id="createTimeStart" width="110px" name="createTimeStart" showTime="false" style="width:100px"  dateFormat='yy-mm-dd'></c:dataPicker>
                </span>-
                    <span>
                    <c:dataPicker id="createTimeEnd" name="createTimeEnd" width="110px"  showTime="false" style="width:100px" dateFormat='yy-mm-dd'></c:dataPicker>
                </span></td>
                <td align="left" valign="middle">
                    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                    <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                </td>
            </tr>
        </table>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <export:formats formats="['excel']"    style="border:0px;margin-top:-3px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="CocCarStoragePrint_grid" style="border:0px"></div>
        </div>
    </div>
</form>


<script>
    var dialogId='';
    var initId='';
    $(function() {
        $("#createTimeStart").val('');
        $("#createTimeEnd").val('');

        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $(".excel").click(function(){
            var coc_clmc=$("#coc_clmc").val();
            var coc_clxh=$("#coc_clxh").val();
            var createTimeStart=$("#createTimeStart").val();
            var createTimeEnd=$("#createTimeEnd").val();
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'CocPrint',action:'exportexcel')}?format=excel&extension=xlsx&coc_clmc="+coc_clmc+"&coc_clxh="+coc_clxh+"&createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd;
            url=encodeURI(url);
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            window.location.href=url;
        });

        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'CocPrint',action:'jsonList')}";
                url+='?'+$('#page').serialize();
                $('#CocCarStoragePrint_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#coc_clmc").val('');
                $("#coc_clxh").val('');
                $("#createTimeStart").val('');
                $("#createTimeEnd").val('');
                $("#coc_clsbdh").val('');

            }
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
        //构建左侧树
        //buildLeftTree();
        buildRightGrid();
    });
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'CocPrint',action:'getCompResult')}';
        $.post(url,{},function(data,textStatus){
            //判断是否已处理完成
            if(data!=null&&data=="success"){
                parent.closeDialog(dialogId);
                clearInterval(initId);
            }else if(data=='error'){
                alert("任务处理过程中出错！");
                parent.closeDialog(dialogId);
                clearInterval(initId);
            }
        });
    }
    function buildRightGrid(value,event){
        $('#CocCarStoragePrint_grid').omGrid({
            dataSource:"${createLink(controller:'CocPrint',action:'jsonList')}",
            method:'POST',
            extraData:{list:'${list}'} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12, // 分页显示，每页显示30条
            height : 435,
            width:'fit',
            colModel:[       {header : "${message(code: 'coc.coc_createTime.label')}", width :120, align : 'center',name : 'coc_createTime'},
                {header : "${message(code: 'coc.coc_yzxzsbh.label')}", width : 210, align : 'center',name : 'coc_yzxzsbh'},
                {header : "${message(code: 'coc.coc_czsl.label')}", width : 50, align : 'center',name : 'coc_czsl'},
                {header : "${message(code: 'coc.coc_clsl.label')}", width : 50, align : 'center',name : 'coc_clsl'},
                {header : "${message(code: 'coc.coc_qdzwz.label')}", width : 60, align : 'center',name : 'coc_qdzwz'},
                {header : "${message(code: 'coc.coc_zj.label')}", width : 60, align : 'center',name : 'coc_zj'},
                {header : "${message(code: 'coc.coc_qyzqzj.label')}", width : 80, align : 'center',name : 'coc_qyzqzj'},
                {header : "${message(code: 'coc.coc_lj.label')}", width : 80, align : 'center',name : 'coc_lj'},
                {header : "${message(code: 'coc.coc_cd.label')}", width : 50, align : 'center',name : 'coc_cd'},
                {header : "${message(code: 'coc.coc_zzqycd.label')}", width : 80, align : 'center',name : 'coc_zzqycd'},
                {header : "${message(code: 'coc.coc_kd.label')}", width : 50, align : 'center',name : 'coc_kd'},
                {header : "${message(code: 'coc.coc_gd.label')}", width : 50, align : 'center',name : 'coc_gd'},
                {header : "${message(code: 'coc.coc_tymj.label')}", width : 80, align : 'center',name : 'coc_tymj'},
                {header : "${message(code: 'coc.coc_qx.label')}", width : 50, align : 'center',name : 'coc_qx'},
                {header : "${message(code: 'coc.coc_jjj.label')}", width : 50, align : 'center',name : 'coc_jjj'},
                {header : "${message(code: 'coc.coc_lqj.label')}", width : 50, align : 'center',name : 'coc_lqj'},
                {header : "${message(code: 'coc.coc_xsxdcsclzl.label')}", width : 80, align : 'center',name : 'coc_xsxdcsclzl'},
                {header : "${message(code: 'coc.coc_sb.label')}", width : 450, align : 'left',name : 'coc_sb'},
                {header : "${message(code: 'coc.coc_bsqzs.label')}", width : 80, align : 'center',name : 'coc_bsqzs'},
                {header : "${message(code: 'coc.coc_lhqxs.label')}", width : 120, align : 'center',name : 'coc_lhqxs'},
                {header : "${message(code: 'coc.coc_dyfdjzs.label')}", width : 80, align : 'center',name : 'coc_dyfdjzs'},
                {header : "${message(code: 'coc.coc_zdjgl.label')}", width : 80, align : 'center',name : 'coc_zdjgl'},
                {header : "${message(code: 'coc.coc_rlzl.label')}", width : 80, align : 'center',name : 'coc_rlzl'},
                {header : "${message(code: 'coc.coc_qgplxs.label')}", width : 80, align : 'center',name : 'coc_qgplxs'},
                {header : "${message(code: 'coc.coc_qgsl.label')}", width : 80, align : 'center',name : 'coc_qgsl'},
                {header : "${message(code: 'coc.coc_zjps.label')}", width : 80, align : 'center',name : 'coc_zjps'},
                {header : "${message(code: 'coc.coc_jcdczdczfh.label')}", width : 80, align : 'center',name : 'coc_jcdczdczfh'},
                {header : "${message(code: 'coc.coc_zdzhzl.label')}", width : 80, align : 'center',name : 'coc_zdzhzl'},
                {header : "${message(code: 'coc.coc_gczdzl.label')}", width : 80, align : 'center',name : 'coc_gczdzl'},
                {header : "${message(code: 'coc.coc_zjjgc.label')}", width : 80, align : 'center',name : 'coc_zjjgc'},
                {header : "${message(code: 'coc.coc_bgc.label')}", width : 80, align : 'center',name : 'coc_bgc'},
                {header : "${message(code: 'coc.coc_qygsgc.label')}", width : 80, align : 'center',name : 'coc_qygsgc'},
                {header : "${message(code: 'coc.coc_cdzdyxzh.label')}", width : 80, align : 'center',name : 'coc_cdzdyxzh'},
                {header : "${message(code: 'coc.coc_kczczwz.label')}", width : 80, align : 'center',name : 'coc_kczczwz'},
                {header : "${message(code: 'coc.coc_kssczwz.label')}", width : 80, align : 'center',name : 'coc_kssczwz'},
                {header : "${message(code: 'coc.coc_czyxzdzl.label')}", width : 80, align : 'center',name : 'coc_czyxzdzl'},
                {header : "${message(code: 'coc.coc_zzllyxsh.label')}", width : 80, align : 'center',name : 'coc_zzllyxsh'},
                {header : "${message(code: 'coc.coc_zlzhfp.label')}", width : 80, align : 'center',name : 'coc_zlzhfp'},
                {header : "${message(code: 'coc.coc_edzzl.label')}", width : 80, align : 'center',name : 'coc_edzzl'},
                {header : "${message(code: 'coc.coc_sfzykqxgzz.label')}", width : 80, align : 'center',name : 'coc_sfzykqxgzz'},
                {header : "${message(code: 'coc.coc_fdjgzyl.label')}", width : 80, align : 'center',name : 'coc_fdjgzyl'},
                {header : "${message(code: 'coc.coc_fdjxh.label')}", width : 80, align : 'center',name : 'coc_fdjxh'},
                {header : "${message(code: 'coc.coc_pl.label')}", width : 80, align : 'center',name : 'coc_pl'},

                {header : "${message(code: 'coc.coc_rq.label')}", width : 80, align : 'center',name : 'coc_rq'},
                {header : "${message(code: 'coc.coc_clmc.label')}", width : 80, align : 'center',name : 'coc_clmc'},
                {header : "${message(code: 'coc.coc_clxh.label')}", width : 150, align : 'center',name : 'coc_clxh'},
                {header : "${message(code: 'coc.coc_ccczsh.label')}", width : 180, align : 'center',name : 'coc_ccczsh'},
                {header : "${message(code: 'coc.coc_fdjbhdkwz.label')}", width : 80, align : 'center',name : 'coc_fdjbhdkwz'},
                {header : "${message(code: 'coc.coc_fdjbh.label')}", width : 80, align : 'center',name : 'coc_fdjbh'},
                {header : "${message(code: 'coc.coc_sbhdkwz.label')}", width : 100, align : 'center',name : 'coc_sbhdkwz'},
                {header : "${message(code: 'coc.coc_clsbdh.label')}", width :150, align : 'center',name : 'coc_clsbdh'},
                {header : "${message(code: 'coc.coc_fdmpwz.label')}", width : 150, align : 'center',name : 'coc_fdmpwz'},
                {header : "${message(code: 'coc.coc_fdjzzsmc.label')}", width : 200, align : 'center',name : 'coc_fdjzzsmc'},
                {header : "${message(code: 'coc.coc_zzsdz.label')}", width : 220, align : 'center',name : 'coc_zzsdz'},
                {header : "${message(code: 'coc.coc_zzsmc.label')}", width : 220, align : 'center',name : 'coc_zzsmc'},
                {header : "${message(code: 'coc.coc_cllb.label')}", width : 80, align : 'center',name : 'coc_cllb'},
                {header : "${message(code: 'coc.coc_clzwpp.label')}", width : 80, align : 'center',name : 'coc_clzwpp'},
                {header : "${message(code: 'coc.coc_cxmc.label')}", width : 80, align : 'center',name : 'coc_cxmc'},

                {header : "${message(code: 'coc.coc_cxdhmc.label')}", width : 150, align : 'center',name : 'coc_cxdhmc'},
                {header : "${message(code: 'coc.coc_dydhmc.label')}", width : 220, align : 'center',name : 'coc_dydhmc'},
                {header : "${message(code: 'coc.coc_cxxldhmc.label')}", width : 80, align : 'center',name : 'coc_cxxldhmc'},

                {header : "${message(code: 'coc.coc_clzzg.label')}", width : 80, align : 'center',name : 'coc_clzzg'},
                {header : "${message(code: 'coc.coc_zsno.label')}", width : 80, align : 'center',name : 'coc_zsno'},
                {header : "${message(code: 'coc.coc_note.label')}", width : 80, align : 'center',name : 'coc_note'},
                {header : "${message(code: 'coc.coc_mxdwysjb.label')}", width : 80, align : 'center',name : 'coc_mxdwysjb'},
                {header : "${message(code: 'coc.coc_dwjgyqbh.label')}", width : 80, align : 'center',name : 'coc_dwjgyqbh'},

                {header : "${message(code: 'coc.coc_wxhwysjb.label')}", width : 80, align : 'center',name : 'coc_wxhwysjb'},
                {header : "${message(code: 'coc.coc_ltgg.label')}", width : 80, align : 'center',name : 'coc_ltgg'},
                {header : "${message(code: 'coc.coc_zcdb.label')}", width : 80, align : 'center',name : 'coc_zcdb'},
                {header : "${message(code: 'coc.coc_jgyqsybg.label')}", width : 80, align : 'center',name : 'coc_jgyqsybg'},
                {header : "${message(code: 'coc.coc_clzzg.label')}", width : 80, align : 'center',name : 'coc_clzzg'},
                {header : "${message(code: 'coc.coc_clsccmc.label')}", width : 220, align : 'center',name : 'coc_clsccmc'},
                {header : "${message(code: 'coc.coc_zsno.label')}", width : 80, align : 'center',name : 'coc_zsno'},
                {header : "${message(code: 'coc.coc_note.label')}", width : 80, align : 'center',name : 'coc_note'},
                {header : "${message(code: 'coc.coc_mxdwysjb.label')}", width : 80, align : 'center',name : 'coc_mxdwysjb'},
                {header : "${message(code: 'coc.coc_dwjgyqbh.label')}", width : 80, align : 'center',name : 'coc_dwjgyqbh'},

                {header : "${message(code: 'coc.coc_wxhwysjb.label')}", width : 80, align : 'center',name : 'coc_wxhwysjb'},
                {header : "${message(code: 'coc.coc_ltgg.label')}", width : 280, align : 'left',name : 'coc_ltgg'},
                {header : "${message(code: 'coc.coc_zcdb.label')}", width : 80, align : 'center',name : 'coc_zcdb'},
                {header : "${message(code: 'coc.coc_jgyqsybg.label')}", width : 80, align : 'center',name : 'coc_jgyqsybg'},


                {header : "${message(code: 'coc.coc_co2.label')}", width : 80, align : 'center',name : 'coc_co2'},
                {header : "${message(code: 'coc.coc_pqpfw.label')}", width : 1110, align : 'left',name : 'coc_pqpfw'},
                {header : "${message(code: 'coc.coc_sj.label')}", width : 960, align : 'left',name : 'coc_sj'},
                {header : "${message(code: 'coc.coc_zgcs.label')}", width : 80, align : 'center',name : 'coc_zgcs'},
                {header : "${message(code: 'coc.coc_sybgno.label')}", width : 80, align : 'center',name : 'coc_sybgno'},
                {header : "${message(code: 'coc.coc_cccno.label')}", width : 80, align : 'center',name : 'coc_cccno'},

                {header : "${message(code: 'coc.coc_zws.label')}", width : 80, align : 'center',name : 'coc_zws'},
                {header : "${message(code: 'coc.coc_cmgz.label')}", width : 80, align : 'center',name : 'coc_cmgz'},
                {header : "${message(code: 'coc.coc_cmsl.label')}", width : 80, align : 'center',name : 'coc_cmsl'},
                {header : "${message(code: 'coc.coc_qzjzdljnl.label')}", width : 80, align : 'center',name : 'coc_qzjzdljnl'},



                {header : "${message(code: 'coc.coc_hxgd.label')}", width : 80, align : 'center',name : 'coc_hxgd'},
                {header : "${message(code: 'coc.coc_hxkd.label')}", width : 80, align : 'center',name : 'coc_hxkd'},
                {header : "${message(code: 'coc.coc_hx.label')}", width : 80, align : 'center',name : 'coc_hx'},
                {header : "${message(code: 'coc.coc_hxcd.label')}", width : 80, align : 'center',name : 'coc_hxcd'},
                {header : "${message(code: 'coc.coc_gnyxrj.label')}", width : 80, align : 'center',name : 'coc_gnyxrj'},
                {header : "${message(code: 'coc.coc_clys.label')}", width : 80, align : 'center',name : 'coc_clys'},

                {header : "${message(code: 'coc.coc_csxs.label')}", width : 80, align : 'center',name : 'coc_csxs'},
                {header : "${message(code: 'coc.coc_zdxtgqgnyl.label')}", width : 80, align : 'center',name : 'coc_zdxtgqgnyl'},
                {header : "${message(code: 'coc.coc_zdzzjysm.label')}", width : 80, align : 'center',name : 'coc_zdzzjysm'},
                {header : "${message(code: 'coc.coc_zxzlxs.label')}", width : 80, align : 'center',name : 'coc_zxzlxs'},

                {header : "${message(code: 'coc.coc_gbthps.label')}", width : 80, align : 'center',name : 'coc_gbthps'},
                {header : "${message(code: 'coc.coc_sfazkqxg.label')}", width : 80, align : 'center',name : 'coc_sfazkqxg'},
                {header : "${message(code: 'coc.coc_creater.label')}", width : 80, align : 'center',name : 'coc_creater'}
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
</script>
</body>
</html>
