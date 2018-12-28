<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'carstorage.label', default: 'carstorage')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<form id="page" style="margin:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
    <table style="width:100%;border:0px;">
        <tr>
            <td width="80"><span><g:message code="carstorage.veh_Clmc.label" default="veh_Clmc" />：</span></td>
            <td width="100"><span><g:textField id="clmc" name="clmc" maxlength="200" value="${precarstorage?.veh_Clmc}"/></span></td>
            <td width='20'></td>
            <td width="80"><span><g:message code="carstorage.veh_Clxh.label" default="veh_Clxh" />：</span></td>
            <td width="100"><span><g:textField id="clxh" name="clxh" maxlength="200" value="${precarstorage?.veh_Clxh}"/></span></td>
            <td width="80"><span><g:message code="carstorage.veh_Dpxh.label" default="veh_Dpxh" />：</span></td>
            <td width="100"><span><g:textField id="veh_Dpxh" name="veh_Dpxh" maxlength="200" value="${precarstorage?.veh_Dpxh}"/></span></td>
            <td width='20'></td>
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
            <div id="carStorage_grid" style="border:0px"></div>
        </div>
    </div>
</form>


<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $(".excel").click(function(){
            var veh_Clmc=$("#clmc").val();
            var veh_Clxh=$("#clxh").val();
            $('.excel').attr('href','javascript:void(0);');
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'preCarStorage',action:'exportSearch')}?format=excel&extension=xlsx&"+$('#page').serialize();
            //url=encodeURI(url);
            window.location.href=url;
        });

        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'PreCarStorage',action:'jsonListByDownload')}";
                url+='?'+$('#page').serialize();
                //url=encodeURI(url);
                $('#carStorage_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#clmc").val('');
                $("#clxh").val('');
            }
        });
    })
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'preCarStorage',action:'getCompResult')}';
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
    function viewDetail(index){
        var data = $("#carStorage_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'preCarStorage',action:'showSingle')}';
        window.location.href = url+'/'+data.id;
    }
    function buildRightGrid(value,event){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'PreCarStorage',action:'jsonListByDownload')}",
            method:'POST',
            extraData:{list:'${list}'} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12, // 分页显示，每页显示30条
            height : 500,
            width:'fit',
            colModel:[
                {header : "${message(code: 'carstorage.search.label')}", width : 60, align : 'center',name : 'search',
                    renderer:function(value,rowData,rowIndex){
                        var data = rowData;
                        return   '<a id="btn_view" class="btn_basic blue_black"  href="javascript:viewDetail('+rowIndex+');">查看</a>';
                    }},
                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 150, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 120, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 80, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'carstorage.veh_cpid.label')}", width : 80, align : 'center',name : 'veh_cpid'},
                {header : "${message(code: 'carstorage.veh_Ggpc.label')}", width : 80, align : 'center',name : 'veh_Ggpc'},
                {header : "${message(code: 'carstorage.veh_qxhx.label')}", width : 270, align : 'left',name : 'veh_qxhx'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 477, align : 'left',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 250, align : 'left',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width :120, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 430, align : 'left',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 220, align : 'left',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width :150, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width :120, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 120, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 150, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 150, align : 'center',name : 'veh_Hxnbk'},
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
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 80, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'carstorage.veh_jjlqj.label')}", width : 80, align : 'center',name : 'veh_jjlqj'},
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 150, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 80, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 150, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 80, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

                {header : "${message(code: 'carstorage.veh_fgbsqy.label')}", width : 150, align : 'center',name : 'veh_fgbsqy'},
                {header : "${message(code: 'carstorage.veh_fgbsxh.label')}", width : 80, align : 'center',name : 'veh_fgbsxh'},
                {header : "${message(code: 'carstorage.veh_fgbssb.label')}", width : 80, align : 'center',name : 'veh_fgbssb'},

                {header : "${message(code: 'carstorage.veh_y45pic.label')}", width : 280, align : 'left',name : 'veh_y45pic'},
                {header : "${message(code: 'carstorage.veh_zhpic.label')}", width : 280, align : 'left',name : 'veh_zhpic'},
                {header : "${message(code: 'carstorage.veh_fhpic.label')}", width : 280, align : 'left',name : 'veh_fhpic'},
                {header : "${message(code: 'carstorage.veh_pic1.label')}", width : 280, align : 'left',name : 'veh_pic1'},
                {header : "${message(code: 'carstorage.veh_pic2.label')}", width : 280, align : 'left',name : 'veh_pic2'},
                {header : "${message(code: 'carstorage.veh_other.label')}", width : 3500, align : 'left',name : 'veh_other'},
                {header : "${message(code: 'carstorage.veh_clsbdh.label')}", width : 150, align : 'left',name : 'veh_clsbdh'},
                {header : "${message(code: 'carstorage.veh_cph.label')}", width : 150, align : 'left',name : 'veh_cph'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 280, align : 'left',name : 'veh_zdjgl'},

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
</script>
</body>
</html>
