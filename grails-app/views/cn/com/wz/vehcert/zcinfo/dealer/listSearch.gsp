
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'zcinfoTemp.list.label', default: 'ZcinfoTemp')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="zcinfo.veh_Zchgzbh.label" default="合格证编号" />：</span></td>
                    <td width="80" align="left"><span><g:textField id="veh_Zchgzbh" name="veh_Zchgzbh"/></span></td>
                    <td width="80" align="right"><span><g:message code="zcinfore.distributor.label" default="distributor" />：</span></td>
                    <td width="100"><span><g:select id="distributor" name="distributor.id" from="${cn.com.wz.parent.system.organization.Organization.createCriteria().list({
                        parent{eq('organCode','distributor')}
                        order('organCode','asc')
                    })}" optionKey="id"  value="${id}" onchange="changeHtml()" noSelection="['':'全部']" class="one-to-one" /></span></td>

                    <td width="80" ><span><g:message code="zcinfore.realName.label" default="distributor" />：</span></td>
                    <td width="100"><span id="change"><select id="user">
                        <option value="" selected="selected">全部</option>
                    </select></span></td>

                </tr>
                <tr>

                    <td width="80" align="right"><span><g:message code="zcinfo.insertTime.label" default="CreateTime" />：</span></td>
                    <td width="100" colspan="5"><span><c:dataPicker id="firstTime" name="firstTime" showTime="false"  ></c:dataPicker></span>至<span><c:dataPicker id="secondTime" name="secondTime"  showTime="false"></c:dataPicker></span></td>

                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>

                    </td>
                </tr>
            </table>
        </form>
        <fieldset class="buttons" style="margin:40px 8px 8px 4px;">
            <export:formats formats="['excel']" style="border:0px;margin-left:0px;margin-top:-8px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="zcinfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});

        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var id=$('#user').val();
                var url = "${createLink(controller:'ZCInfoTemp',action:'jsonListAll')}";
                url+='?'+$('#form_query').serialize()+"&user.id="+id;
                $('#zcinfo_grid').omGrid('setData',url);

            }
        });

        $(".excel").click(function(){
            var veh_Zchgzbh= $("#veh_Zchgzbh").val();
            var firstTime=$("#firstTime").val();
            var secondTime=$("#secondTime").val();
            var userid=$('#user').val();
            var distributor=$('#distributor').val();
            $('.excel').attr('href','javascript:void(0);');
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'ZCInfoTemp',action:'export_search_all')}?format=excel&extension=xlsx&veh_Zchgzbh="+veh_Zchgzbh+"&firstTime="+firstTime+"&secondTime="+secondTime+"&user.id="+userid+"&distributor.id="+distributor;
            window.location.href=url;
        });
        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#firstTime").val('');
                $("#secondTime").val('');
            }
        });

        $('#btn_create').click(function(){
            var url = '${createLink(controller:'workshop',action:'create')}';
            var selected = $('#workshop_grid').omTree('getSelected');
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'workshop',action:'edit')}';
            var selected = $('#workshop_grid').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'/'+selected[0].id;
            }
        });
        $("#tf_name").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
    })
    // 加载时执行的语句
    $(function() {
        $("#firstTime").val('');
        $("#secondTime").val('');
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
        var url='${createLink(controller:'ZCInfoTemp',action:'getCompResult')}';
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
    function buildRightGrid(){
        $('#zcinfo_grid').omGrid({
            dataSource:"${createLink(controller:'ZCInfoTemp',action:'jsonListAll')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,

            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label')}", width : 120, align : 'center',name : 'veh_Zchgzbh'},
                {header : "${message(code: 'zcinfo.veh_Dphgzbh.label')}", width : 120, align : 'center',name : 'veh_Dphgzbh'},
                {header : "${message(code: 'zcinfo.veh_Fzrq.label')}", width : 120, align : 'center',name : 'veh_Fzrq'},
                {header : "${message(code: 'zcinfo.veh_Clzzqymc.label')}", width : 200, align : 'center',name : 'veh_Clzzqymc'},
                {header : "<font color='blue'>${message(code: 'carstorage.createTime.label')}</font>", width : 120, align : 'center',name : 'createTime'},
                {header : "<font color='blue'>${message(code: 'zcinfo.user_down.label')}</font>", width : 120, align : 'center',name : 'user_down'},
                {header : "<font color='blue'>${message(code: 'zcinfo.insertTime.label')}</font>", width : 120, align : 'center',name : 'insertTime'},
                {header : "${message(code: 'zcinfo.veh_Qyid.label')}", width : 120, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'zcinfo.veh_Clfl.label')}", width : 120, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'zcinfo.veh_Clmc.label')}", width : 120, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'zcinfo.veh_Clpp.label')}", width : 120, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'zcinfo.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'zcinfo.veh_Csys.label')}", width : 120, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'zcinfo.veh_Dpxh.label')}", width : 120, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'zcinfo.veh_Dpid.label')}", width : 120, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label')}", width : 120, align : 'center',name : 'veh_Clsbdh'},
                {header : "${message(code: 'zcinfo.veh_Cjh.label')}", width : 120, align : 'center',name : 'veh_Cjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label')}", width : 120, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label')}", width : 120, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'zcinfo.veh_Rlzl.label')}", width : 120, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'zcinfo.veh_Pl.label')}", width : 120, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'zcinfo.veh_Gl.label')}", width : 120, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'zcinfo.veh_zdjgl.label')}", width : 120, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'zcinfo.veh_Zxxs.label')}", width : 120, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'zcinfo.veh_Qlj.label')}", width : 120, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'zcinfo.veh_Hlj.label')}", width : 120, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'zcinfo.veh_Lts.label')}", width : 120, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'zcinfo.veh_Ltgg.label')}", width : 120, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'zcinfo.veh_Gbthps.label')}", width : 120, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'zcinfo.veh_Zj.label')}", width : 120, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'zcinfo.veh_Zh.label')}", width : 120, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'zcinfo.veh_Zs.label')}", width : 120, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'zcinfo.veh_Wkc.label')}", width : 120, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'zcinfo.veh_Wkk.label')}", width : 120, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'zcinfo.veh_Wkg.label')}", width : 120, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'zcinfo.veh_Hxnbc.label')}", width : 120, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'zcinfo.veh_Hxnbk.label')}", width : 120, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'zcinfo.veh_Hxnbg.label')}", width : 120, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'zcinfo.veh_Zzl.label')}", width : 120, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'zcinfo.veh_Edzzl.label')}", width : 120, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'zcinfo.veh_Zbzl.label')}", width : 120, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'zcinfo.veh_Zzllyxs.label')}", width : 120, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'zcinfo.veh_Zqyzzl.label')}", width : 120, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'zcinfo.veh_Edzk.label')}", width : 120, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'zcinfo.veh_Bgcazzdyxzzl.label')}", width : 120, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'zcinfo.veh_Jsszcrs.label')}", width : 120, align : 'center',name : 'veh_Jsszcrs'},
                {header : "${message(code: 'zcinfo.veh_Qzdfs.label')}", width : 120, align : 'center',name : 'veh_Qzdfs'},
                {header : "${message(code: 'zcinfo.veh_Hzdfs.label')}", width : 120, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'zcinfo.veh_Qzdczfs.label')}", width : 120, align : 'center',name : 'veh_Qzdczfs'},
                {header : "${message(code: 'zcinfo.veh_Hzdczfs.label')}", width : 120, align : 'center',name : 'veh_Hzdczfs'},
                {header : "${message(code: 'zcinfo.veh_Cpggh.label')}", width : 120, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'zcinfo.veh_Ggsxrq.label')}", width : 120, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'zcinfo.veh_Zzbh.label')}", width : 120, align : 'center',name : 'veh_Zzbh'},
                {header : "${message(code: 'zcinfo.veh_Dywym.label')}", width : 240, align : 'left',name : 'veh_Dywym'},
                {header : "${message(code: 'zcinfo.veh_Zgcs.label')}", width : 120, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'zcinfo.veh_Clzzrq.label')}", width : 120, align : 'center',name : 'veh_Clzzrq'},
                {header : "${message(code: 'zcinfo.veh_Qybz.label')}", width : 360, align : 'left',name : 'veh_Qybz'},
                {header : "${message(code: 'zcinfo.veh_Cpscdz.label')}", width : 220, align : 'left',name : 'veh_Cpscdz'},
                {header : "${message(code: 'zcinfo.veh_Qyqtxx.label')}", width : 120, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'zcinfo.veh_Pfbz.label')}", width : 220, align : 'left',name : 'veh_Pfbz'},
                {header : "${message(code: 'zcinfo.veh_Clztxx.label')}", width : 120, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'zcinfo.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'zcinfo.veh_Jss.label')}", width : 120, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'zcinfo.veh_Jsslx.label')}", width : 120, align : 'center',name : 'veh_Jsslx'},
                {header : "${message(code: 'zcinfo.veh_VinFourBit.label')}", width : 120, align : 'center',name : 'veh_VinFourBit'},
                {header : "${message(code: 'zcinfo.veh_Bz.label')}", width : 200, align : 'left',name : 'veh_Bz'}]
        });
    }
    function changeHtml(){
        var distributor=$('#distributor').val();
        var url='${createLink(controller:'ZCInfoReplaceAuth',action:'findUserDetail')}?distributor='+distributor;
        $.post(url,{},function(data,textStatus){
            var data= eval( "(" + data + ")" );
            if(!data.total==0){
                var opSize=$('#user option').size();
                if(opSize<data.total){
                    for(var i=0;i<=data.total-opSize;i++){
                        $('#user').append('<option></option>')
                    }
                }
                for(var i=0;i<data.total;i++){
                    $('#user option').get(i+1).value=data.rows[i].id;
                    $('#user option').get(i+1).text=data.rows[i].realName;
                    //alert(data.rows[i].realName);
                }
            }else{
                $('#user option').remove();
                $('#user').append('<option value="">全部</option>')
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
