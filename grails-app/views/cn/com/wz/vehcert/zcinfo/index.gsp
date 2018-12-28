<%--
 @Description:
 @params:
 @return:
 @Create: 13-8-4 下午4:00   huxx
 @Update:
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'zcinfo.label', default: 'zcinfo')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page"   style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80" align="right"><span><g:message code="zcinfo.veh_Zchgzbh.label" default="veh_Zchgzbh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Zchgzbh1" name="veh_Zchgzbh1" maxlength="200"/></span></td>
                    <td width="10" align="right">至</td>
                    <td width="100"><span><g:textField id="veh_Zchgzbh2" name="veh_Zchgzbh2" maxlength="200"/></span></td>
                    <td width='10'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
            <input id="btn_synToServer" type="button" class="create" value="${message(code: 'zcinfo.button.syn.label', default: 'submit Server')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="zcinfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>

<script>
    $(function() {
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfo',action:'jsonList')}?web_status=0";
                url+='&'+$('#form_query').serialize();
                $('#zcinfo_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Zchgzbh1").val('');
                $("#veh_Zchgzbh2").val('');
            }
        });
        $('#page').css({height:$(document).height()-16});
        $("#btn_synToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'ZCInfo',action:'uploadToServer')}?"+$('#form_query').serialize();
            $.post(url,{},function(data,textStatus){
                if("数据同步成功"==data){
                    $("#zcinfo_grid").omGrid('reload');
                    //自动关闭进度提示框
                    parent.closeDialog(id);
                    showTopTip(data);
                }else{
                    $("#zcinfo_grid").omGrid('reload');
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
        $('#zcinfo_grid').omGrid({
            dataSource:"${createLink(controller:'ZCInfo',action:'jsonList')}?web_status=0",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12, // 分页显示，每页显示30条
            height : 450,
            colModel:[

                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label')}", width : 110, align : 'center',name : 'veh_Zchgzbh'},
                {header : "${message(code: 'zcinfo.veh_Dphgzbh.label')}", width : 110, align : 'center',name : 'veh_Dphgzbh'},
                {header : "${message(code: 'carstorage.veh_Fzrq.label')}", width : 80, align : 'center',name : 'veh_Fzrq'},
                {header : "${message(code: 'carstorage.veh_Clzzrq.label')}", width : 80, align : 'center',name : 'veh_Clzzrq'},
                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 140, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_VinFourBit.label')}", width : 80, align : 'center',name : 'veh_VinFourBit'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 100, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 80, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label')}", width : 120, align : 'center',name : 'veh_Clsbdh'},
                {header : "${message(code: 'zcinfo.veh_Cjh.label')}", width : 120, align : 'center',name : 'veh_Cjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label')}", width : 80, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width : 80, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 80, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 80, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width : 80, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width : 80, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 80, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 80, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 80, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'carstorage.veh_Hxnbg.label')}", width : 80, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'carstorage.veh_Zzl.label')}", width : 80, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'carstorage.veh_Edzzl.label')}", width : 80, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'carstorage.veh_Zbzl.label')}", width : 80, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'carstorage.veh_Zzllyxs.label')}", width : 80, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'carstorage.veh_Zqyzzl.label')}", width : 80, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'carstorage.veh_Edzk.label')}", width : 80, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'carstorage.veh_Bgcazzdyxzzl.label')}", width : 80, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 80, align : 'center',name : 'veh_Jsszcrs'},

                {header : "${message(code: 'carstorage.veh_Hzdfs.label')}", width : 80, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'carstorage.veh_Cpggh.label')}", width : 80, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 150, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'zcinfo.veh_Zzbh.label')}", width : 80, align : 'center',name : 'veh_Zzbh'},
                {header : "${message(code: 'zcinfo.veh_Dywym.label')}", width : 240, align : 'left',name : 'veh_Dywym'},

                {header : "${message(code: 'carstorage.veh_jjlqj.label')}", width : 80, align : 'center',name : 'veh_jjlqj'},
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 80, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 320, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 150, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width :120, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 200, align : 'left',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 120, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

                {header : "${message(code: 'carstorage.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'carstorage.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'}
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
        $('#carStorage_grid').omGrid('reload');
    }
</script>
</body>
</html>