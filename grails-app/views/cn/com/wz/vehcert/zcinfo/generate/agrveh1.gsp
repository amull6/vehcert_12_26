<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="农用车合格证生成" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>

<div id="page" style="width:100%;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
    <span id='span_publish'>
        <input id="btn_nextview" type="button" class="preView" value="递增查看"/>
        <input id="btn_preview" type="button" class="nextView" value="递减查看"/>
        <input id="btn_query" type="button"  class="edit" value="查看"/>
        <input id="btn_save" type="button"  class="save" value="保存"/>
        <input id="btn_print" type="button"  class="exportE" value="打印正式合格证"/>
        <input id="btn_print_a4" type="button"  class="exportE" value="打印到普通A4纸"/>
        <input id="btn_printSet" type="button"  class="edit" value="打印设置"/>
    </span>
</fieldset>
<div style="margin-right:8px;margin-top:8px;">
<form action="${createLink(controller: "ZCInfo",action:"save")}" id="form">
<input type="hidden" name="processType" value="1"/>
<input type="hidden" id="infoid" name="infoid" value="${zcinfoInstance.id}"/>
<input type="hidden" name="kind" value="1"/>
<input type="hidden" name="veh_Qyid" value="${zcinfoInstance.veh_Qyid }"/>
<input type="hidden" name="veh_Hzdfs" value="${zcinfoInstance.veh_Hzdfs }"/>
<input type="hidden" id="veh_Clztxx" name="veh_Clztxx" value="${zcinfoInstance.veh_Clztxx }"/>
<input type="hidden" name="veh_Jss" value="${zcinfoInstance.veh_Jss }"/>
<input type="hidden" id="veh_VinFourBit" name="veh_VinFourBit" value="${zcinfoInstance.veh_VinFourBit }"/>
<input type="hidden" name="isupload" value="${zcinfoInstance.web_status }"/>
<input type="hidden" name="veh_Zs" value="2"/>
<div class="om-grid om-widget om-widget-content" style="height: 100%;">
<div class="bDiv" style="width: auto; height:100%">
<table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol">
        <div align="left" class="innerCol " style="width:200px">选择车辆状态信息：
        <p>
        <label>
        <g:if test="${symboMap.containsKey('farmcar')}">
            <input type="radio" name="type" attr='QX' value="${symboMap.get('farmcar')}"checked="checked" />
            全项（QX)
        </g:if>
        </label>
        </p>
        </div>
    </td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">
        <input name="getValue" id="btn_select" style="height:30px;"  value="按公告号提取数据" type="button" />
    </div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:120px">扫描串口
        <select name="selOp" id="selOp">
            <option value="COM1">COM1</option>
            <option value="COM2">COM2</option>
            <option value="COM3" selected="true">COM3</option>
            <option value="COM4">COM4</option>
            <option value="COM5">COM5</option>
            <option value="COM6">COM6</option>
            <option value="COM7">COM7</option>
            <option value="COM8">COM8</option>
        </select></br>
        <input type="button" id="scan"style="width:120px; height:30px;" value="扫描验证" />
    </div></td>
    <td align="left" abbr="address" class="col2">
    </td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="" class="indexCol">
        <div align="left" class="innerCol ">
            <table>
                <tr>
                    <td>
                        合格证编号
                    </td>
                    <td>
                        <g:textField name="veh_Zchgzbh" id="veh_Zchgzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zchgzbh}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        发动机型号
                    </td>
                    <td>
                        <g:textField name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjxh}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        净  功  率
                    </td>
                    <td>
                        <g:textField name="veh_zdjgl" id="veh_zdjgl" maxlength="50"  value="${zcinfoInstance?.veh_zdjgl}"/>
                    </td>
                </tr>
            </table>
        </div>
    </td>
    <td align="left" abbr="id" class="col0">
        <div align="left" class="innerCol ">
            <table>
                <tr>
                    <td>
                        车 身 颜 色
                    </td>
                    <td>
                        <g:textField name="veh_Csys" id="veh_Csys" maxlength="50"  value="${zcinfoInstance?.veh_Csys}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        发 动 机 号
                    </td>
                    <td>
                        <g:textField name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjh}"/>
                    </td>
                </tr>
            </table>
        </div>
    </td>
    <td align="left" abbr="city" class="col1">
        <div align="left" class="innerCol ">
            <table>
                <tr>
                    <td>
                        发 证 日 期
                    </td>
                    <td>
                        <c:dataPicker name="veh_Fzrq" id="veh_Fzrq" editable="false" dateFormat="yy年mm月dd日"  value="${zcinfoInstance?.veh_Fzrq}"></c:dataPicker>
                    </td>
                </tr>
                <tr>
                    <td>
                        车辆制造日期
                    </td>
                    <td>
                        <c:dataPicker name="veh_Clzzrq" id="veh_Clzzrq" editable="false" dateFormat="yy年mm月dd日"  value="${zcinfoInstance?.veh_Clzzrq}"></c:dataPicker>
                    </td>
                </tr>
                <tr>
                    <td>
                        纸 张 编 号
                    </td>
                    <td>
                        <g:textField name="veh_Zzbh" id="veh_Zzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zzbh}"/>
                    </td>
                </tr>
            </table>
        </div>
    </td>
    <td align="left" >
        <div align="left">
            <table>
                <tr>
                    <td>
                        车辆识别代号
                    </td>
                    <td>
                        <g:textField name="veh_Clsbdh" id="veh_Clsbdh" style="width:150px;"  value="${zcinfoInstance?.veh_Clsbdh}"/>
                    </td>
                    <td rowspan="2">
                        <input type="button" id="btn_calculate" name="btn_calculate" style="height:50px;" value="计算VIN" />
                    </td>
                </tr>
                <tr>
                    <td>
                        配置序列号
                    </td>
                    <td>
                        <g:textField name="veh_pzxlh" id="veh_pzxlh" style="width:150px;"  value="${zcinfoInstance?.veh_pzxlh}"/>
                    </td>
                </tr>
            </table>
        </div>
    </td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="" class="indexCol" colspan="4">
        <div align="left" class="innerCol ">
            <table style="width: 100%">
                其它影响VIN的数据：
                <tr>
                    <td>
                        车辆类型 <g:textField name="veh_Clfl" id="veh_Clfl" maxlength="50"  value="${zcinfoInstance?.veh_Clfl}"/>
                    </td>
                    <td>
                        车辆型号<g:textField name="veh_Clxh" id="veh_Clxh" maxlength="50"  value="${zcinfoInstance?.veh_Clxh}"/>
                    </td>
                    <td>
                        轴距<g:textField name="veh_Zj" id="veh_Zj" maxlength="50"  value="${zcinfoInstance?.veh_Zj}"/>
                    </td>
                    <td>
                        排量和功率<g:textField name="veh_Pl" id="veh_Pl"  size='5'   value="${zcinfoInstance?.veh_Pl}"/>
                        <g:textField name="veh_Gl" id="veh_Gl"  size='5'  value="${zcinfoInstance?.veh_Gl}"/>
                    </td>
                    <td>
                        总质量<g:textField name="veh_Zzl" id="veh_Zzl" maxlength="50"  value="${zcinfoInstance?.veh_Zzl}"/>
                    </td>
                </tr>
            </table>
        </div>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr=""colspan="4">
        <table>
            <tr>
                <td colspan="8">
                    其它数据：
                </td>
            </tr>
            <tr>
                <td>车辆名称</td><td><g:textField name="veh_Clmc" id="veh_Clmc" maxlength="50"  value="${zcinfoInstance?.veh_Clmc}"/></td>
                <td>额定载质量</td><td><g:textField name="veh_Edzzl" id="veh_Edzzl" maxlength="50"  value="${zcinfoInstance?.veh_Edzzl}"/></td>
                <td>排放标准</td><td><g:textField name="veh_Pfbz" id="veh_Pfbz" maxlength="50"  value="${zcinfoInstance?.veh_Pfbz}"/></td>
                <td>货厢内部尺寸（长*宽*高）</td>
                <td><g:textField name="veh_Hxnbc" id="veh_Hxnbc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbc}"/>
                <g:textField name="veh_Hxnbk" id="veh_Hxnbk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbk}"/>
                <g:textField name="veh_Hxnbg" id="veh_Hxnbg" size="5"  maxlength="50"  value="${zcinfoInstance?.veh_Hxnbg}"/>
                </td>
            </tr>
            <tr>
                <td>转向形式</td><td><g:textField name="veh_Zxxs" id="veh_Zxxs" maxlength="50"  value="${zcinfoInstance?.veh_Zxxs}"/></td>
                <td>整备质量</td><td><g:textField name="veh_Zbzl" id="veh_Zbzl" maxlength="50"  value="${zcinfoInstance?.veh_Zbzl}"/></td>
                <td>轴荷</td><td><g:textField name="veh_Zh" id="veh_Zh" maxlength="50"  value="${zcinfoInstance?.veh_Zh}"/></td>
                <td>外廓尺寸（长*宽*高）</td>
                <td>
                    <g:textField name="veh_Wkc" id="veh_Wkc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkc}"/>
                    <g:textField name="veh_Wkk" id="veh_Wkk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkk}"/>
                    <g:textField name="veh_Wkg" id="veh_Wkg" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkg}"/>
                </td>
            </tr>
            <tr>
                <td>钢板弹簧片数 </td><td><g:textField name="veh_Gbthps" id="veh_Gbthps" maxlength="50"  value="${zcinfoInstance?.veh_Gbthps}"/></td>
                <td>车辆品牌</td><td><g:textField name="veh_Clpp" id="veh_Clpp" maxlength="50"  value="${zcinfoInstance?.veh_Clpp}"/></td>
                <td>轮胎数</td><td><g:textField name="veh_Lts" id="veh_Lts" maxlength="50"  value="${zcinfoInstance?.veh_Lts}"/></td>
                <td>车辆企业名称</td><td><g:textField name="veh_Clzzqymc" id="veh_Clzzqymc" maxlength="50"  value="${zcinfoInstance?.veh_Clzzqymc}"/></td>
            </tr>
            <tr>
                <td>企业标准</td><td><g:textField name="veh_Qybz" id="veh_Qybz" maxlength="50"  value="${zcinfoInstance?.veh_Qybz}"/></td>
                <td>最高车速</td><td><g:textField name="veh_Zgcs" id="veh_Zgcs" maxlength="50"  value="${zcinfoInstance?.veh_Zgcs}"/></td>
                <td>燃料种类</td><td><g:textField name="veh_Rlzl" id="veh_Rlzl" maxlength="50"  value="${zcinfoInstance?.veh_Rlzl}"/></td>
                <td>轮距（前/后）</td>
                <td>
                    <g:textField name="veh_Qlj" id="veh_Qlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Qlj}"/>
                    <g:textField name="veh_Hlj" id="veh_Hlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hlj}"/>
                </td>
            </tr>
            <tr>
                <td>驾驶室类型</td><td><g:textField name="veh_Jsslx" id="veh_Jsslx" maxlength="50"  value="${zcinfoInstance?.veh_Jsslx}"/></td>
                <td>轮胎规格</td><td><g:textField name="veh_Ltgg" id="veh_Ltgg" maxlength="50"  value="${zcinfoInstance?.veh_Ltgg}"/></td>
                <td>驾驶室准乘人数（人）</td><td><g:textField name="veh_Jsszcrs" id="veh_Jsszcrs" maxlength="50"  value="${zcinfoInstance?.veh_Jsszcrs}"/></td>
                <td>备注</td><td> <g:textField name="veh_Bz" id="veh_Bz" maxlength="50"  value="${zcinfoInstance?.veh_Bz}"/></td>
            </tr>
            <tr>
                <td>油耗</td><td><g:textField name="veh_Yh" id="veh_Yh" maxlength="50"  value="${zcinfoInstance?.veh_Yh}"/></td>
                <td>公告批次</td><td><g:textField name="veh_Ggpc" id="veh_Ggpc" maxlength="50"  value="${zcinfoInstance?.veh_Ggpc}"/></td>
                <td>产品公告号</td><td><g:textField name="veh_Cpggh" id="veh_Cpggh" maxlength="50"  value="${zcinfoInstance?.veh_Cpggh}"/></td>
                <td>车辆制造企业其他信息</td><td><g:textField name="veh_Qyqtxx" id="veh_Qyqtxx" maxlength="50"  value="${zcinfoInstance?.veh_Qyqtxx}"/></td>
            </tr>
            <tr>
                <td>车辆制造企业信息</td><td><g:textField name="veh_clzzqyxx" id="veh_clzzqyxx" maxlength="50"  value="${zcinfoInstance?.veh_clzzqyxx}"/></td>
                <td>生产地址</td><td><g:textField name="veh_Cpscdz" id="veh_Cpscdz" maxlength="50"  value="${zcinfoInstance?.veh_Cpscdz}"/></td>
                <td>公告生效日期</td><td><g:textField name="veh_Ggsxrq" id="veh_Ggsxrq" maxlength="50"  value="${zcinfoInstance?.veh_Ggsxrq}"/></td>
            </tr>
        </table>
    </td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="" class="indexCol"  colspan="4"><div align="left" class="innerCol ">
        <table style="width: 100%">
            <tr>
                <td colspan="8">国家规定不需要填写的数据：
                </td>
            </tr>
            <tr>
                <td>轴数</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>底盘型号</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>底盘ID</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>底盘合格证</td><td><input name="" type="text" value="不需要填写" /></td>
            </tr>
            <tr>
                <td>前制动方式</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>后制动方式</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>前制动操作方式</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>后制动操作方式</td><td><input name="" type="text" value="不需要填写" /></td>
            </tr>
            <tr>
                <td>公告生效日期</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>纸张编号</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>打印唯一码</td><td><input name="" type="text" value="不需要填写" /></td>
                <td>准牵引总质量</td><td><input name="" type="text" value="不需要填写" /></td>
            </tr>
            <tr>
                <td>额定载客</td><td><input name="" type="text" value="不需要填写" /></td>
                <td colspan="2">半挂车鞍座最大允许总质量（kg）</td>
                <td colspan="2"><input name="" type="text" value="不需要填写" />
                 </td>
            </tr>
        </table>
        </div>
    </td>
</tr>

</tbody>
</table>
</div>

</div>
    </form>
</div>

</div>
</div>

</div>


<script>
    var arrays=new Array();
    <g:each in="${symboMap.values()}" var="symbol">
        arrays.push('${symbol}')
    </g:each>
    $(function() {
        $('#btn_select').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'toNotice')}';
            create(url,'公告信息',1440,900);
        });
        $('#btn_printSet').click(function(){
            var url = '${createLink(controller:'printSet',action:'editWorkShop')}';
            create(url,'打印设置',400,300);
        });
        $('#veh_Csys').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'ZCInfo',action:'jsonColors')}?random="+Math.random(),
            value:'${zcinfoInstance?.veh_Csys?zcinfoInstance?.veh_Csys:'蓝'}'
        });

        $('#btn_save').click(function(){ //验证车间号码
            var isupload=  $('#isupload').val();
            if(isupload=='1'){
                alert('合格证已上传,不允许修改')
                return;
            }
            var veh_Clsbdh=  $('#veh_Clsbdh').val();
            if(veh_Clsbdh==null|| $.trim(veh_Clsbdh)==''){
                alert('保存前,请输入车辆识别代号')
                return;
            }

            var fzrq=$("#veh_Fzrq").val()
            if(fzrq!=''){
//                var year=fzrq.substr(0,4) ;
//                var month=fzrq.substr(5,2);
//                var day=fzrq.substr(8,2);
//                var t=Date.parse(new Date())-Date.parse(new Date(year,month-1,day));
//                if(t<0){
//                    alert("发证日期不合法，请重试!");
//                    return false;
//                }
            }else{
                alert("发证日期不合法，请重试!");
                return false;
            }

            var val=$("input[name='type']:checked").attr('attr')
            $('#veh_Clztxx').val(val);
            var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
            if(veh_Zchgzbh.length==14){
                var symbol=veh_Zchgzbh.substr(6,2)
                var isValid=false;
                for(var i=0;i<arrays.length;i++){
                    if(symbol==arrays[i]){
                        isValid=true ;
                        break;
                    }
                }
                if(!isValid){
                    alert('请输入正确的车间代码')
                }else{
                    document.forms[0].submit();
                }
            }else{
                alert('合格证编号不符合要求,请查证')
            }
        });
        $('#btn_query').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'search')}';
            var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
            if(veh_Zchgzbh.length==14){
                window.location.href = url+'?processType=1&kind=1&veh_Zchgzbh='+veh_Zchgzbh;
            }
        });
        $('#btn_calculate').click(function(){
            calculateVin();
        });
        $('#btn_nextview').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'search')}';
            var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
            if(veh_Zchgzbh.length==14){
                window.location.href = url+'?processType=1&kind=1&direction=1&veh_Zchgzbh='+veh_Zchgzbh;
            }
        });
        $('#btn_preview').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'search')}';
            var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
            if(veh_Zchgzbh.length==14){
                window.location.href = url+'?processType=1&kind=1&direction=-1&veh_Zchgzbh='+veh_Zchgzbh;
            }
        });
        $('#scan').click(function(){
            var iRtn;
            var VehCert;
            try{
                VehCert = new ActiveXObject("VCertificate.VehCert");
            }catch(err){
                alert("请使用IE浏览器，并调低IE的安全级别！");
                return false;
            }
            var objSel = document.getElementById("selOp").value;
            VehCert.Veh_Connect=objSel;
            VehCert.ViewBarcodeInfo("PER4-54LD-WXQI-DK87");
        });
        $('#btn_print_a4').click(function(){
            var isupload=  $('#isupload').val();
            if(isupload=='1'){
                alert('合格证已上传,不允许打印')
                return;
            }
            var infoid=$('#infoid').val();
            if(infoid!=null&&infoid!=''){  //先调用虚拟 再调用正式
                printAll(0);
            }else{
                alert('请先保存')
            }
        });
        $('#btn_print').click(function(){
            var isupload=  $('#isupload').val();
            if(isupload=='1'){
                alert('合格证已上传,不允许打印')
                return;
            }
            var infoid=$('#infoid').val();
            if(infoid!=null&&infoid!=''){  //先调用虚拟 再调用正式
                printAll(1);
            }else{
                alert('请先保存')
            }
        });
        $("input[name='type']") .change( function() {
            // 这里可以写些验证代码
            var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
            if(veh_Zchgzbh.length==14){
                var  type=$(this).val();
                var start=veh_Zchgzbh.substr(0,6)
                var end=veh_Zchgzbh.substr(8)
                veh_Zchgzbh=    start+ type+  end;
                $('#veh_Zchgzbh').val(veh_Zchgzbh);
                $('#btn_query').click();
            }
        });
    });
    function printAll(printType){
        var params=$("#form").serialize();
        var url = '${createLink(controller:'ZCInfo',action:'print')}?printType='+printType;
        var dialogId=showTipDialog();
        $.post(url,params,function(data,textStatus){
            parent.closeDialog(dialogId);
            var d=eval("("+data+")");
            if(d.flag=='successed'){
                alert("<div>"+d.msg+"</div>");
            }else{
                alert(d.msg);
            }
        },'text');
    }
    function changeData(data){
        $('#veh_Fdjxh').val(data.veh_Fdjxh)//發動機型號
        $('#veh_zdjgl').val(data.veh_zdjgl)//净功率
        $('#veh_Clfl').val(data.veh_Clfl)    //车辆类型
        $('#veh_Clxh').val(data.veh_Clxh)     //车辆型号
        $('#veh_Zj').val(data.veh_Zj)            //轴距
        $('#veh_Pl').val(data.veh_Pl)           //排量和功率
        $('#veh_Gl').val(data.veh_Gl)
        $('#veh_Zzl').val(data.veh_Zzl)        //总质量
        $('#veh_Clmc').val(data.veh_Clmc)      //车辆名称
        $('#veh_Edzzl').val(data.veh_Edzzl)    //额定载质量
        $('#veh_Pfbz').val(data.veh_Pfbz)   //排放标准
        $('#veh_Hxnbc').val(data.veh_Hxnbc)     //货厢内部尺寸
        $('#veh_Hxnbk').val(data.veh_Hxnbk)     //货厢内部尺寸
        $('#veh_Hxnbg').val(data.veh_Hxnbg)      //货厢内部尺寸
        $('#veh_Zxxs').val(data.veh_Zxxs)     //转向形式
        $('#veh_Zbzl').val(data.veh_Zbzl)     //整备质量
        $('#veh_Zh').val(data.veh_Zh)      //轴荷
        $('#veh_Wkc').val(data.veh_Wkc)     //外廓尺寸
        $('#veh_Wkk').val(data.veh_Wkk)     //外廓尺寸
        $('#veh_Wkg').val(data.veh_Wkg)      //外廓尺寸
        $('#veh_Zzllyxs').val(data.veh_Zzllyxs)      //载质量利用系数
        $('#veh_Qyqtxx').val(data.veh_Qyqtxx)      //车辆制造企业其他信息
        $('#veh_Dpid').val(data.veh_Dpid)      //底盘ID
        $('#veh_Dpxh').val(data.veh_Dpxh)      //底盘型号
        $('#veh_Jsslx').val(data.veh_Jsslx)      //驾驶室类型
        $('#veh_clzzqyxx').val(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#veh_Yh').val(data.veh_Yh)      //油耗
        $('#veh_Ggpc').val(data.veh_Ggpc)      //公告批次
        $('#veh_Cpggh').val(data.veh_Cpggh)      //产品公告号
        $('#veh_Gbthps').val(data.veh_Gbthps)     //钢板弹簧片数
        $('#veh_Clpp').val(data.veh_Clpp)     //车辆品牌
        $('#veh_Lts').val(data.veh_Lts)      //轮胎数
        $('#veh_Ggsxrq').val(data.veh_Ggsxrq)      //公告生效日期
        if(data.veh_Clzzqymc==null||data.veh_Clzzqymc==''){
            $('#veh_Clzzqymc').val('山东五征集团有限公司')     //车辆企业名称
        } else{
            $('#veh_Clzzqymc').val(data.veh_Clzzqymc)     //车辆企业名称
        }
        if(data.veh_Cpscdz==null||data.veh_Cpscdz==''){
            $('#veh_Cpscdz').val('山东省五莲县城长青路23号')      //生产地址
        } else{
            $('#veh_Cpscdz').val(data.veh_Cpscdz)      //生产地址
        }
        if(data.veh_Qybz==null||data.veh_Qybz==''){
            $('#veh_Qybz').val('Q/WZ 002-2005 《三轮汽车技术条件》')     //企业标准
        } else{
            $('#veh_Qybz').val(data.veh_Qybz)     //企业标准
        }
        $('#veh_Zgcs').val(data.veh_Zgcs)      //最高车速
        $('#veh_Rlzl').val(data.veh_Rlzl)     //燃料种类
        $('#veh_Qlj').val(data.veh_Qlj)     //轮距
        $('#veh_Hlj').val(data.veh_Hlj)      //轮距
        $('#veh_Ltgg').val(data.veh_Ltgg)     //轮胎规格
        $('#veh_Jsszcrs').val(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#veh_Bz').val(data.veh_Bz)      //备注
        $('#veh_pzxlh').val(data.veh_pzxlh)      //配置序列号
        $('#veh_Qyid').val(data.veh_Qyid)      //企业ID
        $('#veh_Hzdfs').val(data.veh_Hzdfs)
        $('#veh_Clztxx').val(data.veh_Clztxx)
        $('#veh_Jss').val(data.veh_Jss)      //驾驶室
        $('#veh_VinFourBit').val(data.veh_VinFourBit)      //vin第四位
    }
    function create(url,title,width,height){
        var tabId=window.name;
        var flag=url.indexOf('?');
        if(flag>0){
            url+="&tabId="+tabId;
        }else{
            url+="?tabId="+tabId;
        }

        //打开弹出框
        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
                'margin-right:-500px;text-align:center; width:100%;height:100%" '+
                'scrolling="yes" src="'+url+'"></iframe>';
        parent.showDialog(1,content,title,width,height);
    }
    function calculateVin(){
        var url = '${createLink(controller:'ZCInfo',action:'calculateVIN')}';
        var kind='1';
        var veh_Clfl=$('#veh_Clfl').val();
        var veh_Zzl=$('#veh_Zzl').val();
        var veh_Jsslx=$('#veh_Jsslx').val();
        var veh_Zj=$('#veh_Zj').val();
        var veh_Gl=$('#veh_Gl').val();
        var veh_Rlzl=$('#veh_Rlzl').val();
        var veh_Zchgzbh=$('#veh_Zchgzbh').val();
        $.post(url,{"kind":kind,'veh_Clfl':veh_Clfl,'veh_Zzl':veh_Zzl,'veh_Rlzl':veh_Rlzl,'veh_Jsslx':veh_Jsslx,'veh_Zj':veh_Zj,'veh_Gl':veh_Gl,'veh_Zchgzbh':veh_Zchgzbh},function(data){
            if(data.flag=='0'){
                alert(data.msg);
            }else if(data.flag=='1'){
                $('#veh_Clsbdh').val(data.vin);
            }
        },'json');
    }
    function printa4(){
        var iRtn;
        var VehCert;
        try{
            VehCert = new ActiveXObject("VCertificate.VehCert");
        }catch(err){
            alert("请使用IE浏览器，并调低IE的安全级别！");
            return false;
        }
        VehCert.Veh_Clztxx=$('#veh_Clztxx').val();//车辆状态信息
        VehCert.Veh_Zchgzbh=$('#veh_Zchgzbh').val();//整车合格证编号
        VehCert.Veh_Dphgzbh=$('#veh_Dphgzbh').val();//底盘合格整编号
        VehCert.Veh_Fzrq =$('#veh_Fzrq').val();//发证日起
        VehCert.Veh_Clzzqymc=$('#veh_Clzzqymc').val();//车辆制造企业名称
        VehCert.Veh_Qyid =$('#veh_Qyid').val();//企业ID
        VehCert.Veh_Clfl=$('#veh_Clfl').val();     //车辆分类
        VehCert.Veh_Clmc=$('#veh_Clmc').val();     //车辆名称
        VehCert.Veh_Clpp=$('#veh_Clpp').val();   //车辆品牌
        VehCert.Veh_Csys=$('#veh_Csys').val();          //车身颜色
        VehCert.Veh_Clxh =$('#veh_Clxh').val(); //车辆型号
        VehCert.Veh_Dpxh=$('#veh_Dpxh').val();//底盘型号
        VehCert.Veh_Dpid=$('#veh_Dpid').val();//底盘ID
        VehCert.Veh_Clsbdh=$('#veh_Clsbdh').val();//车辆识别代号
        VehCert.Veh_Fdjh=$('#veh_Fdjh').val();//发动机号
        VehCert.Veh_Fdjxh=$('#veh_Fdjxh').val();//发动机型号
        VehCert.Veh_Rlzl=$('#veh_Rlzl').val();     //燃料种类
        VehCert.Veh_Pfbz=$('#veh_Pfbz').val();    //排放标准
        VehCert.Veh_Pl=$('#veh_Pl').val();           //排量
        VehCert.Veh_Gl=$('#veh_Gl').val();          //功率

        VehCert.Veh_Zxxs =$('#veh_Zxxs').val();       //转向形式
        VehCert.Veh_Qlj=$('#veh_Qlj').val();            //前轮距
        VehCert.Veh_Hlj=$('#veh_Hlj').val();         //后轮距
        VehCert.Veh_Lts=$('#veh_Lts').val();           //轮胎数
        VehCert.Veh_Ltgg=$('#veh_Ltgg').val();         //轮胎规格
        VehCert.Veh_Gbthps=$('#veh_Gbthps').val();      //钢板弹簧片数
        VehCert.Veh_Zj=$('#veh_Zj').val();      //轴距
        VehCert.Veh_Zh=$('#veh_Zh').val();      //轴荷
        VehCert.Veh_Zs=2;//轴数
        VehCert.Veh_Wkc=$('#veh_Wkc').val(); //外廓长
        VehCert.Veh_Wkg=$('#veh_Wkg').val();    //外廓高
        VehCert.Veh_Wkk=$('#veh_Wkk').val();  //外廓宽
        VehCert.Veh_Hxnbc=$('#veh_Hxnbc').val();;   //货箱内部长
        VehCert.Veh_Hxnbg=$('#veh_Hxnbg').val();   //货箱内部高
        VehCert.Veh_Hxnbk=$('#veh_Hxnbk').val();    //货箱内部宽
        VehCert.Veh_Zzl=$('#veh_Zzl').val();    //总质量
        VehCert.Veh_Edzzl=$('#veh_Edzzl').val();   //额定载质量
        VehCert.Veh_Zbzl=$('#veh_Zbzl').val();;   //整备质量
        VehCert.Veh_Zzllyxs=$('#veh_Zzllyxs').val();//载质量利用系数
        VehCert.Veh_Zqyzzl=$('#veh_Zqyzzl').val();//准牵引总质量
        VehCert.Veh_Edzk=$('#veh_Edzk').val();//额定载客
        VehCert.Veh_Bgcazzdyxzzl=$('#veh_Bgcazzdyxzzl').val();//半挂车按座
        VehCert.Veh_Jsszcrs=$('#veh_Jsszcrs').val(); //驾驶室准乘人数
        VehCert.Veh_Yh=$('#veh_Yh').val();//油耗

        VehCert.Veh_Ggpc = $('#veh_Ggpc').val();    //公告批次
        VehCert.Veh_Cpggh = $('#veh_Cpggh').val();       //产品公告号
        VehCert.Veh_Ggsxrq =$('#veh_Ggsxrq').val();       //公告生效日期
        VehCert.Veh_Zzbh = $('#veh_Zzbh').val();
        VehCert.Veh_Zgcs =$('#veh_Zgcs').val();
        VehCert.Veh_Clzzrq=$('#veh_Clzzrq').val();   //车辆制造日期
        VehCert.Veh_Bz=$('#veh_Bz').val();     //备注
        VehCert.Veh_Qybz=$('#veh_Qybz').val();      //企业标准
        VehCert.Veh_Cpscdz =$('#veh_Cpscdz').val();       //产品生产地址
        VehCert.Veh_Qyqtxx=$('#veh_Qyqtxx').val();        //企业其它信息
        VehCert.Veh_Connect="1";       //连接串口
        VehCert.Veh_Baud="9600" ;        //串口波特率
        VehCert.Veh_Parity ="N";       //串口奇偶校验
        VehCert.Veh_Databits ="8";    //串口数据位
        VehCert.Veh_Stopbits ="1";    //串口停止位
        VehCert.Veh_Cddbj = "2";         //纯电动标记
        <g:if test="${printSet?.top}">
        VehCert.Veh_PrintPosTop =${printSet?.top};   //合格证打印的上边距
        </g:if>
        <g:else>
        VehCert.Veh_PrintPosTop =10;   //合格证打印的上边距
        </g:else>
        <g:if test="${printSet?.left}">
        VehCert.Veh_PrintPosLeft =${printSet?.left};   //合格证打印的左边距
        </g:if>
        <g:else>
        VehCert.Veh_PrintPosLeft =10;   //合格证打印的上边距
        </g:else>
        <g:if test="${printSet?.printerName}">
        VehCert.Veh_PrinterName ='${printSet?.printerName}';   //目标打印机 虚拟打印机
        </g:if>


        VehCert.Veh_Clscdwmc =$('#veh_Clzzqymc').val();          //车辆生产单位名称

        iRtn = VehCert.PrtParaTbl(1,"FDLS-325D-SKDI-E8EK");
        if(iRtn==1){
            //保存相关信息   取出id
            var infoid="${zcinfoInstance.id }";
            var url = '${createLink(controller:'ZCInfo',action:'print')}';
            var Veh_Dywym= VehCert.Veh_Dywym
            var Veh_Jyw= VehCert.Veh_Jyw
            var Veh_wzhgzbh=VehCert.GetCertificateNO($('#veh_Zchgzbh').val() ,"N3PL-SX32-AEU2-BC6S");
            var xnVeh_Zchgzbh=VehCert.Veh_Zchgzbh
            $.post(url,{"printType":'0',"xnVeh_Zchgzbh":xnVeh_Zchgzbh,"infoid":infoid,'veh_Zchgzbh_0':Veh_wzhgzbh,'veh_Dywym':Veh_Dywym,'veh_Jyw':Veh_Jyw},function(data){
                if(data.flag=='0'){
                    alert("打印成功.\r\n 合格证编号为:"+VehCert.Veh_Zchgzbh);
                }else if(data.flag=='1'){
                    alert("打印失败，错误原因："+data.errormsg);
                } else if(data.flag=='2'){
                    alert("打印失败，错误原因：合格证未找到" );
                }
            },'json');
        }else{
            alert("打印失败，错误原因：" + VehCert.Veh_ErrorInfo);
        }
    }
    function print(){
        var iRtn;
        var iRtn;
        var VehCert2;
        try{
            VehCert2 = new ActiveXObject("VCertificate.VehCert");
        }catch(err){
            alert("请使用IE浏览器，并调低IE的安全级别！");
            return false;
        }
        VehCert2.Veh_Clztxx=$('#veh_Clztxx').val();//车辆状态信息
        VehCert2.Veh_Zchgzbh=$('#veh_Zchgzbh').val();//整车合格证编号
        VehCert2.Veh_Dphgzbh=$('#veh_Dphgzbh').val();//底盘合格整编号
        VehCert2.Veh_Fzrq =$('#veh_Fzrq').val();//发证日起
        VehCert2.Veh_Clzzqymc=$('#veh_Clzzqymc').val();//车辆制造企业名称
        VehCert2.Veh_Qyid =$('#veh_Qyid').val();//企业ID
        VehCert2.Veh_Clfl=$('#veh_Clfl').val();     //车辆分类
        VehCert2.Veh_Clmc=$('#veh_Clmc').val();     //车辆名称
        VehCert2.Veh_Clpp=$('#veh_Clpp').val();   //车辆品牌
        VehCert2.Veh_Csys=$('#veh_Csys').val();          //车身颜色
        VehCert2.Veh_Clxh =$('#veh_Clxh').val(); //车辆型号
        VehCert2.Veh_Clsbdh=$('#veh_Clsbdh').val();//车辆识别代号
        VehCert2.Veh_Fdjh=$('#veh_Fdjh').val();//发动机号
        VehCert2.Veh_Fdjxh=$('#veh_Fdjxh').val();//发动机型号
        VehCert2.Veh_Rlzl=$('#veh_Rlzl').val();     //燃料种类
        VehCert2.Veh_Pfbz=$('#veh_Pfbz').val();    //排放标准
        VehCert2.Veh_Pl=$('#veh_Pl').val();           //排量
        VehCert2.Veh_Gl=$('#veh_Gl').val();          //功率
        VehCert2.Veh_Zxxs =$('#veh_Zxxs').val();       //转向形式
        VehCert2.Veh_Qlj=$('#veh_Qlj').val();            //前轮距
        VehCert2.Veh_Hlj=$('#veh_Hlj').val();         //后轮距
        VehCert2.Veh_Lts=$('#veh_Lts').val();           //轮胎数
        VehCert2.Veh_Ltgg=$('#veh_Ltgg').val();         //轮胎规格
        VehCert2.Veh_Gbthps=$('#veh_Gbthps').val();      //钢板弹簧片数
        VehCert2.Veh_Zj=$('#veh_Zj').val();      //轴距
        VehCert2.Veh_Zh=$('#veh_Zh').val();      //轴荷
        VehCert2.Veh_Zs=2;//轴数
        VehCert2.Veh_Wkc=$('#veh_Wkc').val(); //外廓长
        VehCert2.Veh_Wkg=$('#veh_Wkg').val();    //外廓高
        VehCert2.Veh_Wkk=$('#veh_Wkk').val();  //外廓宽
        VehCert2.Veh_Hxnbc=$('#veh_Hxnbc').val();;   //货箱内部长
        VehCert2.Veh_Hxnbg=$('#veh_Hxnbg').val();   //货箱内部高
        VehCert2.Veh_Hxnbk=$('#veh_Hxnbk').val();    //货箱内部宽
        VehCert2.Veh_Zzl=$('#veh_Zzl').val();    //总质量
        VehCert2.Veh_Edzzl=$('#veh_Edzzl').val();   //额定载质量
        VehCert2.Veh_Zbzl=$('#veh_Zbzl').val();;   //整备质量
        VehCert2.Veh_Zzllyxs=$('#veh_Zzllyxs').val();//载质量利用系数
        VehCert2.Veh_Bgcazzdyxzzl=$('#veh_Bgcazzdyxzzl').val();//半挂车按座
        VehCert2.Veh_Jsszcrs=$('#veh_Jsszcrs').val(); //驾驶室准乘人数
        VehCert2.Veh_Yh=$('#veh_Yh').val();//油耗

        VehCert2.Veh_Ggpc = $('#veh_Ggpc').val();    //公告批次
        VehCert2.Veh_Cpggh = $('#veh_Cpggh').val();       //产品公告号
        VehCert2.Veh_Ggsxrq =$('#veh_Ggsxrq').val();       //公告生效日期
        VehCert2.Veh_Zzbh = $('#veh_Zzbh').val();
        VehCert2.Veh_Zgcs =$('#veh_Zgcs').val();
        VehCert2.Veh_Clzzrq=$('#veh_Clzzrq').val();   //车辆制造日期
        VehCert2.Veh_Bz=$('#veh_Bz').val();     //备注
        VehCert2.Veh_Qybz=$('#veh_Qybz').val();      //企业标准
        VehCert2.Veh_Cpscdz =$('#veh_Cpscdz').val();       //产品生产地址
        VehCert2.Veh_Qyqtxx=$('#veh_Qyqtxx').val();        //企业其它信息
        VehCert2.Veh_Connect="1";       //连接串口
        VehCert2.Veh_Baud="9600" ;        //串口波特率
        VehCert2.Veh_Parity ="N";       //串口奇偶校验
        VehCert2.Veh_Databits ="8";    //串口数据位
        VehCert2.Veh_Stopbits ="1";    //串口停止位
        VehCert2.Veh_Cddbj = "2";         //纯电动标记
        <g:if test="${printSet?.top}">
        VehCert2.Veh_PrintPosTop =${printSet?.top};   //合格证打印的上边距
        </g:if>
        <g:else>
        VehCert2.Veh_PrintPosTop =10;   //合格证打印的上边距
        </g:else>
        <g:if test="${printSet?.left}">
        VehCert2.Veh_PrintPosLeft =${printSet?.left};   //合格证打印的左边距
        </g:if>
        <g:else>
        VehCert2.Veh_PrintPosLeft =10;   //合格证打印的上边距
        </g:else>
        VehCert2.Veh_PrinterName ='';   //目标打印机 虚拟打印机
        VehCert2.Veh_Clscdwmc =$('#veh_Clzzqymc').val();          //车辆生产单位名称

        iRtn = VehCert2.PrtParaTbl(1,"FDLS-325D-SKDI-E8EK");
        if(iRtn==1){
            //保存相关信息   取出id
            var infoid="${zcinfoInstance.id }";
            var url = '${createLink(controller:'ZCInfo',action:'print')}';
            var Veh_Dywym= VehCert2.Veh_Dywym
            var Veh_Jyw= VehCert2.Veh_Jyw
            var Veh_wzhgzbh=VehCert2.GetCertificateNO($('#veh_Zchgzbh').val() ,"N3PL-SX32-AEU2-BC6S");
            var xnVeh_Zchgzbh=VehCert2.Veh_Zchgzbh
            $.post(url,{"printType":'1',"xnVeh_Zchgzbh":xnVeh_Zchgzbh,"infoid":infoid,'veh_Zchgzbh_0':Veh_wzhgzbh,'veh_Dywym':Veh_Dywym,'veh_Jyw':Veh_Jyw},function(data){
                if(data.flag=='0'){
                    alert("打印成功.\r\n 合格证编号为:"+VehCert2.Veh_Zchgzbh);
                }else if(data.flag=='1'){
                    alert("打印失败，错误原因："+data.errormsg);
                } else if(data.flag=='2'){
                    alert("打印失败，错误原因：合格证未找到" );
                }
            },'json');
        }else{
            alert("打印失败，错误原因：" + VehCert2.Veh_ErrorInfo);
        }
    }
</script>
</body>
</html>
