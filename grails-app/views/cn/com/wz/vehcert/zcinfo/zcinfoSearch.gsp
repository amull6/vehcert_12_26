
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'zcinfoTemp.list.label', default: 'ZcinfoTemp')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>

</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:0px;border:1px;">
        <div>
            <form id="form_query" style="margin:0px;">
                <table style="width:100%;border:0px;">
                    <tr>
                        <td width="80" align="right"><span><g:message code="Workshop.name.label" default="车间名称" />：</span></td>
                        <td width="70"><span>
                            <g:select id="veh_workshopno" name="veh_workshopno" from="${cn.com.wz.vehcert.workshop.Workshop.createCriteria().list({})}" optionKey="serialNum"  value="${name}" noSelection="['':'全部']"  class="one-to-one" />
                        </span></td>

                        <td width="80" align="right"><span><g:message code="zcinfo.createTime.label" default="createTime" />：</span></td>
                        <td width="100"><span>
                            <c:dataPicker id="createTimeStart" name="createTimeStart" showTime="false" style="width:100px"  dateFormat='yy-mm-dd'></c:dataPicker>
                            </span></td>
                        <td><span>至
                        </span></td>
                        <td width="100"><span>
                            <c:dataPicker id="createTimeEnd" name="createTimeEnd"  showTime="false" style="width:100px" dateFormat='yy-mm-dd'></c:dataPicker>
                        </span></td>
                        <td width="80" align="right"><span><g:message code="zcinfo.veh_Fzrq.label" default="veh_Fzrq" />：</span></td>
                        <td width="100"><span>
                            <c:dataPicker id="veh_Fzrq" name="veh_Fzrq" style="width:100px;" dateFormat="yy年mm月dd日" value=""></c:dataPicker>
                            </span></td>
                        <td><span>至
                        </span></td>
                        <td width="100"><span>
                            <c:dataPicker id="veh_Fzrq1" name="veh_Fzrq1" style="width:100px;"   dateFormat="yy年mm月dd日" ></c:dataPicker>
                        </span></td>
                        </tr>
                    <tr>
                        <td width="80" align="right"><g:message code="zcinfo.veh_Fdjxh.label" default="veh_Fdjxh" />：</td>
                        <td width="80"><span><g:textField id="veh_Fdjxh" name="veh_Fdjxh"  /></span></td>
                        <td width="80" align="right"><g:message code="zcinfo.veh_Clxh.label" default="veh_Clxh" />：</td>
                        <td width="80"><span><g:textField id="veh_Clxh" name="veh_Clxh"  /></span></td>
                        <td width="40" align="right"><g:message code="zcinfo.VIN.label" default="veh_Clsbdh" />：</td>
                        <td width="80"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh"  /></span></td>

                        <td width="80" align="right"><g:message code="zcinfo.veh_Zchgzbh1.label" default="veh_Clsbdh" />：</td>
                        <td width="70"><span><g:textField id="veh_Zchgzbh" name="veh_Zchgzbh"  /></span></td>
                        <td >
                            <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                        </td>
                        <td align="left" valign="middle" width="150px">
                            <input id="btn_query" type="button"   value="<g:message code="default.button.query.label" default="Query" />"/>
                        </td>
                    </tr>
                </table>
            </form>
            <fieldset class="buttons" style="margin:10px 8px 8px 4px;">
                <export:formats formats="['excel']"  style="border:0px;margin-left:0px;margin-top:0px;"/>
            </fieldset>
        </div>
        <div style="margin-right:8px;margin-top:8px;">

            <div id="zcinfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    var dialogId='';
    var initId='';
    $(function() {

        $("#veh_Fzrq").val('');
        $("#veh_Fzrq1").val('');
        $("#createTimeStart").val('');
        $("#createTimeEnd").val('');

        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfo',action:'jsonListSearch')}";
                url+='?'+$('#form_query').serialize();
                $('#zcinfo_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_workshopno").val('');
                $("#veh_Fzrq").val('');
                $("#veh_Fzrq1").val('');
                $("#veh_Clsbdh").val('');
                $("#veh_Clsbdh6").val('');
                $("#veh_Zchgzbh").val('');
                $("#veh_Fdjxh").val('');
                $("#veh_Clxh").val('');
                $("#createTimeStart").val('');
                $("#createTimeEnd").val('');
            }
        });


        $(".excel").click(function(){
            var veh_Fzrq=$("#veh_Fzrq").val();
            var veh_Clsbdh=$("#veh_Clsbdh").val();
            var createTimeStart=$("#createTimeStart").val();
            var createTimeEnd=$("#createTimeEnd").val();
            var veh_Zchgzbh=$("#veh_Zchgzbh").val();
            var veh_workshopno=$("#veh_workshopno").val();
            $('.excel').attr('href','javascript:void(0);');
             var time='';
             if(veh_Fzrq!=''){
                var vehList1=veh_Fzrq.replace('年','-');
                 var vehList2=vehList1.replace('月','-');
                 var vehList3=vehList2.replace('日','');
                 time=vehList3;
             }
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'ZCInfo',action:'export')}?format=excel&extension=xlsx&"+$('#form_query').serialize();
            window.location.href=url;
        });

    })
    // 加载时执行的语句
    $(function() {
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
        var url='${createLink(controller:'ZCInfo',action:'getCompResult')}';
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
    function viewDetail(index){
        var data = $("#zcinfo_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'ZCInfo',action:'showSingle')}';
        window.location.href = url+'/'+data.id;
    }
    function buildRightGrid(){
        $('#zcinfo_grid').omGrid({
            dataSource:"${createLink(controller:'ZCInfo',action:'jsonListSearch')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :500,
            colModel:[   {header : "${message(code: 'carstorage.search.label')}", width : 60, align : 'center',name : 'search',
                renderer:function(value,rowData,rowIndex){
                    var data = rowData;
                    return   '<a id="btn_view" class="btn_basic blue_black"  href="javascript:viewDetail('+rowIndex+')">查看</a>';
                }},
                {header : "${message(code: 'zcinfo.web_status.label')}", width : 80, align : 'center',name : 'web_status',
                    renderer:function(value,rowData,rowIndex){
                       var text="";
                        if(value==0){
                            text="未上传";
                        }else if(value==1){
                            text="已上传";
                        }else if(value==2) {
                            text="上传失败";
                        }else if(value==3){
                            text="需要修改或撤销";
                        }else{
                            text="参数错误";
                        }
                        return  text;
                    }},
                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label')}", width : 120, align : 'center',name : 'veh_Zchgzbh'},
                {header : "${message(code: 'zcinfo.veh_Dphgzbh.label')}", width : 120, align : 'center',name : 'veh_Dphgzbh'},
                {header : "${message(code: 'zcinfo.veh_Fzrq.label')}", width : 120, align : 'center',name : 'veh_Fzrq'},
                {header : "${message(code: 'zcinfo.veh_Clzzrq.label')}", width : 80, align : 'center',name : 'veh_Clzzrq'},
                {header : "${message(code: 'zcinfo.createTime.label')}", width : 80, align : 'center',name : 'createTime'},
                {header : "${message(code: 'zcinfo.veh_Clzzqymc.label')}", width : 150, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'zcinfo.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'zcinfo.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'zcinfo.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'zcinfo.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'zcinfo.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'zcinfo.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'zcinfo.veh_Dpxh.label')}", width : 80, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'zcinfo.veh_Dpid.label')}", width : 120, align : 'center',name : 'veh_dpid'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label')}", width :120, align : 'center',name : 'veh_Clsbdh'},
                {header : "${message(code: 'zcinfo.veh_Cjh.label')}", width : 120, align : 'center',name : 'veh_Cjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label')}", width : 80, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'zcinfo.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'zcinfo.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'zcinfo.veh_Gl.label')}", width : 80, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'zcinfo.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'zcinfo.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'zcinfo.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'zcinfo.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'zcinfo.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'zcinfo.veh_Ltgg.label')}", width : 80, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'zcinfo.veh_Gbthps.label')}", width : 80, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'zcinfo.veh_Zj.label')}", width : 80, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'zcinfo.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'zcinfo.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'zcinfo.veh_Wkc.label')}", width : 80, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'zcinfo.veh_Wkk.label')}", width : 80, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'zcinfo.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'zcinfo.veh_Hxnbc.label')}", width : 80, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'zcinfo.veh_Hxnbk.label')}", width : 80, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'zcinfo.veh_Hxnbg.label')}", width : 80, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'zcinfo.veh_Zzl.label')}", width : 80, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'zcinfo.veh_Edzzl.label')}", width : 80, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'zcinfo.veh_Zbzl.label')}", width : 80, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'zcinfo.veh_Zzllyxs.label')}", width : 80, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'zcinfo.veh_Zqyzzl.label')}", width : 80, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'zcinfo.veh_Edzk.label')}", width : 80, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'zcinfo.veh_Bgcazzdyxzzl.label')}", width : 80, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'zcinfo.veh_Jsszcrs.label')}", width : 80, align : 'center',name : 'veh_Jsszcrs'},
                {header : "${message(code: 'zcinfo.veh_Qzdfs.label')}", width : 80, align : 'center',name : 'veh_Qzdfs'},
                {header : "${message(code: 'zcinfo.veh_Hzdfs.label')}", width : 80, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'zcinfo.veh_Qzdczfs.label')}", width : 80, align : 'center',name : 'veh_Qzdczfs'},
                {header : "${message(code: 'zcinfo.veh_Hzdczfs.label')}", width : 80, align : 'center',name : 'veh_Hzdczfs'},
                {header : "${message(code: 'zcinfo.veh_Cpggh.label')}", width : 80, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'zcinfo.veh_Ggsxrq.label')}", width : 80, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'zcinfo.veh_Zzbh.label')}", width : 80, align : 'center',name : 'veh_Zzbh'},
                {header : "${message(code: 'zcinfo.veh_Dywym.label')}", width : 80, align : 'center',name : 'veh_Dywym'},
                {header : "${message(code: 'zcinfo.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'zcinfo.veh_Qybz.label')}", width : 80, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'zcinfo.veh_Cpscdz.label')}", width : 80, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'zcinfo.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'zcinfo.veh_Pfbz.label')}", width : 220, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'zcinfo.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'zcinfo.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_jss'},
                {header : "${message(code: 'zcinfo.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_jsslx'},
                {header : "${message(code: 'zcinfo.veh_VinFourBit.label')}", width : 80, align : 'center',name : 'veh_VinFourBit'},
                {header : "${message(code: 'zcinfo.veh_Bz.label')}", width : 200, align : 'left',name : 'veh_Bz'},
                {header : "${message(code: 'zcinfo.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'zcinfo.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'zcinfo.updaterId.label')}", width : 80, align : 'center',name : 'updaterName'},
                {header : "${message(code: 'zcinfo.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'zcinfo.uploader_Id.label')}", width : 80, align : 'center',name : 'uploaderName'},
                {header : "${message(code: 'zcinfo.uploader_Time.label')}", width : 120, align : 'center',name : 'uploaderTime'}
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
