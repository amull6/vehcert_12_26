<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;overflow:visible;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="100"><span>车辆识别代号：</span></td>
                    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="500" value="${zciInfoModel?.veh_Clsbdh}"/></span></td>
                    <td width='20'></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <a id="btn_search" class="btn_basic blue_black" ><g:message code="default.button.query.label" default="Search" /></a>
                        %{--<a id="btn_down" class="btn_basic blue_black"  ><g:message code="zcinfo.download.label" default="download" /></a>--}%
                        <a id="btn_print" class="btn_basic blue_black"  ><g:message code="zcinfo.print.label" default="print" /></a>
                        <a id="btn_clear" class="btn_basic blue_black" ><g:message code="default.button.clear.label" default="clear" /></a>
                        <input type="hidden" name="hidden_id" id="hidden_id" value="${zciInfoModel?.id}"/>
                    </td>
                </tr>
            </table>

        </form>
        <div align="center">
            <object id="pdfObj" classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="0" height="0" border="0">
                <param  name="src" value="../pdf/zcinfopdf/20131026/WCB01306198710.pdf"/>
            </object>
        </div>
    <div style="margin-right:8px;margin-top:8px;">
        <div class="om-grid om-widget om-widget-content" style="height: 100%;">
            <div class="hDiv om-state-default">
                <div class="hDivBox"></div>
            </div>
            <div class="bDiv" style="width: auto; height:100%">
                <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <tr class="om-grid-row evenRow">
                        <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >合格证信息</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:150px">1、合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:150px">2、发证日期</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fzrq}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzqymc}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clpp}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clmc}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clxh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车身颜色</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Csys }</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >7.虚拟完整合格证号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.web_virtualcode}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpxh}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpid}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dphgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fdjxh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Rlzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zciInfoModel?.veh_Pl}</div></td>
                        <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zciInfoModel?.veh_Gl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Pfbz}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Yh}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkc}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkk}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkg}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbc}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbk}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Gbthps}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Lts}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ltgg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Qlj}</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zciInfoModel?.veh_Hlj}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zj}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zs}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zxxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zbzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zzllyxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zqyzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大允许总质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Jsszcrs}</div></td>
                        <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzk}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zgcs}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzrq}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#veh_Clsbdh').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'ZCInfo',action:'salecarsOfDistributor')}?random="+Math.random(),
            value:'',
        });
        //绑定查询按钮事件
        $('#btn_search').click(function(){
            var checkUrl = "${createLink(controller:'ZCInfo',action:'checkLimit')}";
            checkUrl+='?'+$('#form_query').serialize();
            $.post(checkUrl,function(data,textStatus){
                data=eval("("+data+")");
                if(data.flag=='0'){
                    alert(data.msg);
                }else{
                    var url = "${createLink(controller:'ZCInfo',action:'index_ZCI')}";
                    url+='?'+$('#form_query').serialize();
                    window.location.href = url;
                }
            },'text');
        });
        //绑定打印按钮事件
        $('#btn_print').click(function(){
                if($("#hidden_id").val()==""){
                alert("请查询要打印的合格证信息!");
                return
            }
            var url="${createLink(controller:'printPw',action:'timeOrPrint')}";
            $.post(url,function(data,textStatus){
                if(data=='true'){
                    zcinfoPrint();
                }else{
                    var urlPrint= "${createLink(controller:'printPw',action:'setPrint')}?type=1";
                    showModelDialog(urlPrint);
                }
            },'text');
            //弹出打印密码输入的窗口
        });
        //绑定下载按钮事件
        $('#btn_down').click(function(){
                if($("#hidden_id").val()==""){
                    alert("请查询要下载的合格证信息!");
                    return
                }
                var url="${createLink(controller:'printPw',action:'timeOrPrint')}";
                $.post(url,function(data,textStatus){
                    if(data=='true'){
                        down();
                    }else{
                        var urlPrint= "${createLink(controller:'printPw',action:'setPrint')}?type=0";
                        showModelDialog(urlPrint);
                    }
                },'text');
                //弹出打印密码输入的窗口
        });
        //绑定清空按钮事件
        $('#btn_clear').click(function(){
                $("#hidden_id").val("")
                var url = "${createLink(controller:'ZCInfo',action:'index_ZCI')}";
                window.location.href = url;
        });
        $("#web_virtualcode").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_search').click();
                return false;
            }
        });
    })
    function zcinfoPrint(){
        var url = "${createLink(controller:'ZCInfo',action:'download_ZCI')}";
        url+='?'+$('#form_query').serialize()+'&type=0';
        //要求用户确认下载
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
            content:'${message(code: 'vehcert.button.dowload.confirm.message', default: '是否确认打印正式合格证?')}',
            onClose:function(v){
                if(v){
                    $.post(url,function(data,textStatus){
                        data=eval("("+data+")");
                        if(data.flag=='success'){
                            var updateurl = "${createLink(controller:'ZCInfoTemp',action:'checkCount')}";
                            updateurl+='?'+$('#form_query').serialize();
                            $.post(updateurl,function(checkData,textStatus){
                                checkData=eval("("+checkData+")");
                                if(checkData.flag=='1'){
                                    var fileCopyUrl = "${createLink(controller:'dealerPrint',action:'fileCopy')}";
                                    fileCopyUrl+='?path='+data.upload_path
                                    console.log(fileCopyUrl)
                                    $.post(fileCopyUrl,function(fileCopyData,textStatus){
                                        fileCopyData=eval("("+fileCopyData+")");
                                        if(fileCopyData.flag=='1'){
                                            window.location.href="${createLink(controller:'dealerPrint',action:'forDealerPrint')}?path="+data.upload_path;
                                        }else{
                                            alert(fileCopyData.msg);
                                        }
                                    },'text');
                                }else{
                                    alert(checkData.msg);
                                }
                            },'text');
                        }else{
                            if(data.msg=='1'){
                                alert("合格证记录不存在，请重新选择！");
                            }else if (data.msg=='2'){
                                alert("公告批次错误或已扩展，请更换后再操作！");
                            }else if (data.msg=='3'){
                                alert("发证日期已超过48小时，请更换后再操作！");
                            }else if (data.msg=='4'){
                                alert("打印PDf后合格证信息更新失败！");
                            }else if (data.msg=='5'){
                                alert("保存下载/打印记录失败！");
                            }else if(data.msg=='6'){
                                alert("您没有权限打印或下载此合格证，请联系CRM系统管理员查看此车状态！");
                            }else {
                                alert(data.msg);
                            }
                        }
                    },'text');

                }
            }
        });
    }
    function down(){
        var url = "${createLink(controller:'ZCInfo',action:'download_ZCI')}";
        url += '?' + $('#form_query').serialize()+'&type=1';
        //要求用户确认打印
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
            content:'${message(code: 'vehcert.button.print.confirm.message', default: '下载后系统默认正式合格证已打印，是否确定下载?')}',
            onClose:function(v){
                if(v){
                    $.post(url,function(data,textStatus){
                        data=eval("("+data+")");
                        if(data.flag=='success'){
                            window.location.href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${zciInfoModel?.veh_Zchgzbh}.pdf&filePath="+data.upload_path;
                        }else{
                            if(data.msg=='1'){
                                alert("下载记录不存在，请重新选择下载！");
                            }else if (data.msg=='2'){
                                alert("公告批次错误或已扩展，请更换后再下载！");
                            }else if (data.msg=='3'){
                                alert("发证日期已超过48小时，请更换后再下载！");
                            }else if (data.msg=='4'){
                                alert("打印PDf后合格证信息更新失败！");
                            }else if (data.msg=='5'){
                                alert("保存下载记录失败！");
                            }else if(data.msg=='6'){
                                alert("您没有权限下载此合格证！");
                            }else {
                                alert(data.msg);
                            }
                        }
                    },'text');

                }
            }
        });
    }
    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }
    function closeModelWait(id){
        parent.closeDialog(id);
    }
    function showModelDialog(url){
        var name=window.name;
        url+='&tabName='+name;
        var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
        var title='打印密码';
        //打开弹出框
        parent.showDialog(1,content,title,550,250);
    }
    function printPage(){
        try {

        } catch (e) {
            alert("未安装adobe reader插件，请联系管理员安装！");
        }
    }

</script>
</body>
</html>
