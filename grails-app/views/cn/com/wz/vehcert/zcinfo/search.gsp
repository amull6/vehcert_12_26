<%--
 @Description:
 @params:
 @return:
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
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>

                    <td width="80" align="right"><span><g:message code="carstorage.createTime.label" default="Start Time" />：</span></td>
                    <td width="100"><span><c:dataPicker id="startTime" name="startTime" showTime="false"   dateFormat='yy-mm-dd' style="width:75px;"></c:dataPicker></span></td>
                    <td width="10" align="right">至</td>
                    <td width="100"><span><c:dataPicker id="endTime" name="endTime"  showTime="false"  dateFormat='yy-mm-dd' style="width:75px;"></c:dataPicker></span></td>
                    <td width='10'></td>
                    <td width="80" align="right"><span><g:message code="zcinfo.veh_Zchgzbh.label" default="veh_Zchgzbh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Zchgzbh" name="veh_Zchgzbh" maxlength="200" style="width:112px;"/></span></td>
                    <td width="90"><span><g:message code="zcinfo.veh_Clsbdh.label" default="veh_Clsbdh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200"  style="width:112px;"/></span></td>
                    <td width='10'></td>
                    <td width="100"><span>
                        <select name="web_status" id="web_status">
                            <option value="">全部</option>
                            <option value="0">未上传</option>
                            <option value="1">上传成功</option>
                            <option value="2">上传失败</option>
                            <option value="3">已撤销</option>
                          </select>
                    </span></td>
                    <td width='10'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <export:formats formats="['excel']"  style="border:0px;margin-left:0px;margin-top:-8px;"/>
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
                var url = "${createLink(controller:'ZCInfo',action:'jsonListByShop')}";
                url+='?'+$('#form_query').serialize();
                $('#zcinfo_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#startTime").val('');
                $("#endTime").val('');
                $("#veh_Zchgzbh").val('');
                $("#veh_Clsbdh").val('');
            }
        });
        $("#btn_synToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'ZCInfo',action:'uploadToServer')}";
            $.post(url,{},function(data,textStatus){
                //自动关闭进度提示框
                parent.closeDialog(id);
                var content = data;
                showTopTip(content);
            },'text');
        });


        $(".excel").click(function(){
            var startTime=$("#startTime").val();
            var endTime=$("#endTime").val();
            var web_status = $("#web_status").val();
            var veh_Zchgzbh=$("#veh_Zchgzbh").val();
            var veh_Clsbdh=$("#veh_Clsbdh").val();
            $('.excel').attr('href','javascript:void(0);');
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'ZCInfo',action:'export_search')}?format=excel&extension=xlsx&veh_Clsbdh="+veh_Clsbdh+"&veh_Zchgzbh="+veh_Zchgzbh+"&startTime="+startTime+"&endTime="+endTime+"&web_status="+web_status;
            window.location.href=url;
        });
    })

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

          $("#startTime").val('');
          $("#endTime").val('');
        buildRightGrid();
    });

    function changeStatus(index){
        var data = $("#zcinfo_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'ZCInfo',action:'changeStatus')}';
        $.post(url,{"id":data.id},function(data,status){
            if(data.flag=='success'){
                alert(data.msg);
                $('#zcinfo_grid').omGrid('reload') ;
            }else{
                alert(data.msg);
            }
        });
    }

    function synchroTable(index){
        var data = $("#zcinfo_grid").omGrid("getData").rows[index];
       var url = '${createLink(controller:'ZCInfo',action:'synchroTable')}';
        $.post(url,{"veh_Zchgzbh":data.veh_Zchgzbh,'ID':data.id},function(data,status){
            alert(data);
            var selections = $('#zcinfo_grid').omGrid('reload');
        });
    }

    function buildRightGrid(){
        $('#zcinfo_grid').omGrid({
            dataSource:"${createLink(controller:'ZCInfo',action:'jsonListByShop')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12, // 分页显示，每页显示30条
            height : 500,
            colModel:[
                {   header : "${message(code: 'zcinfo.opertion.label', default: '操作')}", name : 'zcinfoId', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var str= '<a id="btn_Syn" class="btn_basic blue_black"  href="javascript:synchroTable('+rowIndex+');">更新</a>&nbsp;&nbsp;';
                            if(rowData.web_status==0){
                                str+='<a id="btn_upload1" class="btn_basic blue_black"  href="javascript:changeStatus('+rowIndex+');">撤销</a>'+
                                        '&nbsp;';
                            }
                            return str;
                        }
                },
                {header : "${message(code: 'zcinfo.web_status.label', default: '合格证状态')}", name : 'web_status', width : 60, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: blue">未上传</span>';
                        }else if(value==1){
                            return '<span style="color: green">上传成功</span>';
                        }else if(value==2){
                            return '<span style="color: red"> 上传失败</span>';
                        } else if(value==3){
                            return '<span style="color: green">已撤销</span>';
                        }else{
                            return '<span style="color: red"> 参数错误</span>';
                        }
                    }
                },
                {header : "${message(code: 'zcinfo.idReal.label')}", width : 50, align : 'center',name : 'idReal'},
                {header : "<font color='blue'>${message(code: 'carstorage.createTime.label')}</font>", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label')}", width : 120, align : 'center',name : 'veh_Zchgzbh'},
                {header : "${message(code: 'zcinfo.veh_Dphgzbh.label')}", width : 120, align : 'center',name : 'veh_Dphgzbh'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label')}", width : 120, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label')}", width : 120, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label')}", width : 120, align : 'center',name : 'veh_Clsbdh'},
                {header : "${message(code: 'carstorage.veh_Fzrq.label')}", width : 120, align : 'center',name : 'veh_Fzrq'},
                {header : "${message(code: 'carstorage.veh_Clzzrq.label')}", width : 120, align : 'center',name : 'veh_Clzzrq'},
                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 180, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 120, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 120, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_VinFourBit.label')}", width : 120, align : 'center',name : 'veh_VinFourBit'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 120, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 120, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 120, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 120, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 120, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'zcinfo.veh_Cjh.label')}", width : 120, align : 'center',name : 'veh_Cjh'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label')}", width : 120, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 120, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 120, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width : 120, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 120, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 120, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 120, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 120, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 120, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 120, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 120, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width : 120, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 120, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 120, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width : 120, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 120, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 120, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 120, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 120, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'carstorage.veh_Hxnbg.label')}", width : 120, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'carstorage.veh_Zzl.label')}", width : 120, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'carstorage.veh_Edzzl.label')}", width : 120, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'carstorage.veh_Zbzl.label')}", width : 120, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'carstorage.veh_Zzllyxs.label')}", width : 120, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'carstorage.veh_Zqyzzl.label')}", width : 120, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'carstorage.veh_Edzk.label')}", width : 120, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'carstorage.veh_Bgcazzdyxzzl.label')}", width : 120, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 120, align : 'center',name : 'veh_Jsszcrs'},

                {header : "${message(code: 'carstorage.veh_Hzdfs.label')}", width : 120, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'carstorage.veh_Cpggh.label')}", width : 120, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 120, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'zcinfo.veh_Zzbh.label')}", width : 120, align : 'center',name : 'veh_Zzbh'},
                {header : "${message(code: 'zcinfo.veh_Dywym.label')}", width : 120, align : 'center',name : 'veh_Dywym'},

                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 120, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 120, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 120, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 180, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 180, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 120, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 200, align : 'left',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 120, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 120, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 120, align : 'center',name : 'veh_Jsslx'},
                {header : "${message(code: 'carstorage.createrId.label')}", width : 120, align : 'center',name : 'createrId'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'zcinfo.updaterId.label')}", width : 120, align : 'center',name : 'updaterId'}
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

    function backFunc(result){
        $('#carStorage_grid').omGrid('reload');
    }
</script>
</body>
</html>