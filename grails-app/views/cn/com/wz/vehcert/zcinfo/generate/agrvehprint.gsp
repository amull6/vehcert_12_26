<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="农用车合格证生成" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div style="background-color: #FAFAFA;line-height: 40px; box-shadow: 0 0 3px 1px #aaaaaa;">
    <div style="padding-left: 20px;">
        <a id="btn_query"  class="btn_basic blue_black" >查看</a>
        <a id="btn_print"   class="btn_basic blue_black" >打印正式合格证</a>
        <a id="btn_print3"   class="btn_basic blue_black" >打印A4纸</a>
        <a id="btn_printSet"   class="btn_basic blue_black" >打印设置</a>
    </div>
</div>
<div style="margin-right:8px;margin-top:8px;">
<form id="form" action="${createLink(controller: 'ZCInfo',action:'managesave')}" method="post">
    <input type="hidden" id="veh_Clztxx" name="veh_Clztxx" value="${zcinfoInstance?.veh_Clztxx}">
<g:hiddenField name="veh_Qyid" id="veh_Qyid" value="${zcinfoInstance?.veh_Qyid }"/>
<g:hiddenField name="veh_Hzdfs" id="veh_Hzdfs" value="${zcinfoInstance?.veh_Hzdfs }"/>
<g:hiddenField name="veh_Jss" id="veh_Jss" value="${zcinfoInstance?.veh_Jss }"/>
<g:hiddenField name="veh_VinFourBit" id="veh_VinFourBit" value="${zcinfoInstance?.veh_VinFourBit }"/>
<g:hiddenField name="veh_Zs" value="2"/>
    <div class="om-grid om-widget om-widget-content" style="height: 100%;">
    <div class="bDiv" style="width: auto; height:100%">
    <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
    <tbody>
    <tr class="om-grid-row evenRow">
        <td colspan="1">
            原合格证编号： <g:textField  name="veh_Zchgzbh_Re" id="veh_Zchgzbh_Re" maxlength="50" style="width: 180px;"  value="${vehZchgzbhRe}"/>
        </td>
        <td colspan="3">
            <g:if test="${zcinfoInstance?.upload_Path}">
                <a href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${zcinfoInstance?.veh_Zchgzbh}.pdf&filePath=${zcinfoInstance?.upload_Path}" id="down_PDF" class="btn_basic blue_black">下载PDF</a>
            </g:if>
        </td>
    </tr>
    <tr class="om-grid-row oddRow">
        <td align="left" class="indexCol">
            <div align="left" class="innerCol " style="width:200px">选择车辆状态信息：
            <p>
                <label>
                    <input type="radio" name="type" value="QX" checked="checked" />
                    全项（QX）</label>
                <br />
            </p>
            </div>
        </td>
        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">
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
            <a id="scan"style="width:120px; line-height:30px;padding-left: 20px;font-size: 14px;" class="btn_basic blue_black" >合格证扫描验证</a>
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
                             <g:textField readonly="true"  name="veh_Zchgzbh" id="veh_Zchgzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zchgzbh}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            发动机型号
                        </td>
                        <td>
                             <g:textField readonly="true"  name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjxh}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            净  功  率
                        </td>
                        <td>
                             <g:textField readonly="true"  name="veh_zdjgl" id="veh_zdjgl" maxlength="50"  value="${zcinfoInstance?.veh_zdjgl}"/>
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
                             <g:textField readonly="true"  name="veh_Csys" id="veh_Csys" maxlength="50"  value="${zcinfoInstance?.veh_Csys}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            发 动 机 号
                        </td>
                        <td>
                             <g:textField readonly="true"  name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjh}"/>
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
                            <g:textField readonly="true" name="veh_Fzrq" id="veh_Fzrq"  value="${zcinfoInstance?.veh_Fzrq}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            车辆制造日期
                        </td>
                        <td>
                            <g:textField readonly="true" name="veh_Clzzrq" id="veh_Clzzrq" value="${zcinfoInstance?.veh_Clzzrq}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            纸 张 编 号
                        </td>
                        <td>
                             <g:textField readonly="true"  name="veh_Zzbh" id="veh_Zzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zzbh}"/>
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
                             <g:textField readonly="true"  name="veh_Clsbdh" id="veh_Clsbdh" style="width:150px;"  value="${zcinfoInstance?.veh_Clsbdh}"/>
                        </td>
                        <td rowspan="2">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            配置序列号
                        </td>
                        <td>
                             <g:textField readonly="true"  name="veh_pzxlh" id="veh_pzxlh" style="width:150px;"  value="${zcinfoInstance?.veh_pzxlh}"/>
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
                            车辆类型  <g:textField readonly="true"  name="veh_Clfl" id="veh_Clfl" maxlength="50"  value="${zcinfoInstance?.veh_Clfl}"/>
                        </td>
                        <td>
                            车辆型号 <g:textField readonly="true"  name="veh_Clxh" id="veh_Clxh" maxlength="50"  value="${zcinfoInstance?.veh_Clxh}"/>
                        </td>
                        <td>
                            轴距 <g:textField readonly="true"  name="veh_Zj" id="veh_Zj" maxlength="50"  value="${zcinfoInstance?.veh_Zj}"/>
                        </td>
                        <td>
                            排量和功率 <g:textField readonly="true"  name="veh_Pl" id="veh_Pl"  size='5'   value="${zcinfoInstance?.veh_Pl}"/>
                             <g:textField readonly="true"  name="veh_Gl" id="veh_Gl"  size='5'  value="${zcinfoInstance?.veh_Gl}"/>
                        </td>
                        <td>
                            总质量 <g:textField readonly="true"  name="veh_Zzl" id="veh_Zzl" maxlength="50"  value="${zcinfoInstance?.veh_Zzl}"/>
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
                    <td>车辆名称</td><td> <g:textField readonly="true"  name="veh_Clmc" id="veh_Clmc" maxlength="50"  value="${zcinfoInstance?.veh_Clmc}"/></td>
                    <td>额定载质量</td><td> <g:textField readonly="true"  name="veh_Edzzl" id="veh_Edzzl" maxlength="50"  value="${zcinfoInstance?.veh_Edzzl}"/></td>
                    <td>排放标准</td><td> <g:textField readonly="true"  name="veh_Pfbz" id="veh_Pfbz" maxlength="50"  value="${zcinfoInstance?.veh_Pfbz}"/></td>
                    <td>货厢内部尺寸（长*宽*高）</td>
                    <td> <g:textField readonly="true"  name="veh_Hxnbc" id="veh_Hxnbc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbc}"/>
                     <g:textField readonly="true"  name="veh_Hxnbk" id="veh_Hxnbk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbk}"/>
                     <g:textField readonly="true"  name="veh_Hxnbg" id="veh_Hxnbg" size="5"  maxlength="50"  value="${zcinfoInstance?.veh_Hxnbg}"/>
                    </td>
                </tr>
                <tr>
                    <td>转向形式</td><td> <g:textField readonly="true"  name="veh_Zxxs" id="veh_Zxxs" maxlength="50"  value="${zcinfoInstance?.veh_Zxxs}"/></td>
                    <td>整备质量</td><td> <g:textField readonly="true"  name="veh_Zbzl" id="veh_Zbzl" maxlength="50"  value="${zcinfoInstance?.veh_Zbzl}"/></td>
                    <td>轴荷</td><td> <g:textField readonly="true"  name="veh_Zh" id="veh_Zh" maxlength="50"  value="${zcinfoInstance?.veh_Zh}"/></td>
                    <td>外廓尺寸（长*宽*高）</td>
                    <td>
                         <g:textField readonly="true"  name="veh_Wkc" id="veh_Wkc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkc}"/>
                         <g:textField readonly="true"  name="veh_Wkk" id="veh_Wkk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkk}"/>
                         <g:textField readonly="true"  name="veh_Wkg" id="veh_Wkg" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkg}"/>
                    </td>
                </tr>
                <tr>
                    <td>钢板弹簧片数 </td><td> <g:textField readonly="true"  name="veh_Gbthps" id="veh_Gbthps" maxlength="50"  value="${zcinfoInstance?.veh_Gbthps}"/></td>
                    <td>车辆品牌</td><td> <g:textField readonly="true"  name="veh_Clpp" id="veh_Clpp" maxlength="50"  value="${zcinfoInstance?.veh_Clpp}"/></td>
                    <td>轮胎数</td><td> <g:textField readonly="true"  name="veh_Lts" id="veh_Lts" maxlength="50"  value="${zcinfoInstance?.veh_Lts}"/></td>
                    <td>车辆企业名称</td><td> <g:textField readonly="true"  name="veh_Clzzqymc" id="veh_Clzzqymc" maxlength="50"  value="${zcinfoInstance?.veh_Clzzqymc}"/></td>
                </tr>
                <tr>
                    <td>企业标准</td><td> <g:textField readonly="true"  name="veh_Qybz" id="veh_Qybz" maxlength="50"  value="${zcinfoInstance?.veh_Qybz}"/></td>
                    <td>最高车速</td><td> <g:textField readonly="true"  name="veh_Zgcs" id="veh_Zgcs" maxlength="50"  value="${zcinfoInstance?.veh_Zgcs}"/></td>
                    <td>燃料种类</td><td> <g:textField readonly="true"  name="veh_Rlzl" id="veh_Rlzl" maxlength="50"  value="${zcinfoInstance?.veh_Rlzl}"/></td>
                    <td>轮距（前/后）</td>
                    <td>
                         <g:textField readonly="true"  name="veh_Qlj" id="veh_Qlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Qlj}"/>
                         <g:textField readonly="true"  name="veh_Hlj" id="veh_Hlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hlj}"/>
                    </td>
                </tr>
                <tr>
                    <td>驾驶室类型</td><td> <g:textField readonly="true"  name="veh_Jsslx" id="veh_Jsslx" maxlength="50"  value="${zcinfoInstance?.veh_Jsslx}"/></td>
                    <td>轮胎规格</td><td> <g:textField readonly="true"  name="veh_Ltgg" id="veh_Ltgg" maxlength="50"  value="${zcinfoInstance?.veh_Ltgg}"/></td>
                    <td>驾驶室准乘人数（人）</td><td> <g:textField readonly="true"  name="veh_Jsszcrs" id="veh_Jsszcrs" maxlength="50"  value="${zcinfoInstance?.veh_Jsszcrs}"/></td>
                    <td>备注</td><td>  <g:textField readonly="true"  name="veh_Bz" id="veh_Bz" maxlength="50"  value="${zcinfoInstance?.veh_Bz}"/></td>
                </tr>
                <tr>
                    <td>油耗</td><td> <g:textField readonly="true"  name="veh_Yh" id="veh_Yh" maxlength="50"  value="${zcinfoInstance?.veh_Yh}"/></td>
                    <td>公告批次</td><td> <g:textField readonly="true"  name="veh_Ggpc" id="veh_Ggpc" maxlength="50"  value="${zcinfoInstance?.veh_Ggpc}"/></td>
                    <td>产品公告号</td><td> <g:textField readonly="true"  name="veh_Cpggh" id="veh_Cpggh" maxlength="50"  value="${zcinfoInstance?.veh_Cpggh}"/></td>
                    <td>车辆制造企业其他信息</td><td> <g:textField readonly="true"  name="veh_Qyqtxx" id="veh_Qyqtxx" maxlength="50"  value="${zcinfoInstance?.veh_Qyqtxx}"/></td>
                </tr>
                <tr>
                    <td>车辆制造企业信息</td><td> <g:textField readonly="true"  name="veh_clzzqyxx" id="veh_clzzqyxx" maxlength="50"  value="${zcinfoInstance?.veh_clzzqyxx}"/></td>
                    <td>生产地址</td><td> <g:textField readonly="true"  name="veh_Cpscdz" id="veh_Cpscdz" maxlength="50"  value="${zcinfoInstance?.veh_Cpscdz}"/></td>
                    <td>公告生效日期</td><td> <g:textField readonly="true"  name="veh_Ggsxrq" id="veh_Ggsxrq" maxlength="50"  value="${zcinfoInstance?.veh_Ggsxrq}"/></td>
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
                    <td>轴数</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>底盘型号</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>底盘ID</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>底盘合格证</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                </tr>
                <tr>
                    <td>前制动方式</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>后制动方式</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>前制动操作方式</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>后制动操作方式</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                </tr>
                <tr>
                    <td>公告生效日期</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>纸张编号</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>打印唯一码</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td>准牵引总质量</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                </tr>
                <tr>
                    <td>额定载客</td><td><input readonly="readonly" name="" type="text" value="不需要填写" /></td>
                    <td colspan="2">半挂车鞍座最大允许总质量（kg）</td>
                    <td colspan="2"><input readonly="readonly" name="" type="text" value="不需要填写" />
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
    $(function() {
        $('#btn_query').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'managesearch')}';
            var   veh_Zchgzbh_Re =$('#veh_Zchgzbh_Re').val();
            veh_Zchgzbh_Re= $.trim(veh_Zchgzbh_Re);
            if(veh_Zchgzbh_Re.length==0){
                alert('必须输入者原合格证编号!')
            } else{
                window.location.href = url+'?kind=3&vehZchgzbhRe='+veh_Zchgzbh_Re;
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
        $('#btn_print3').click(function(){
            saveAndPrint(0);
        });
        $('#btn_print').click(function(){
            saveAndPrint(1);
        });
    });

    /**保存数据并保存成功后打印合格证信息
     * @param printType  0打印PDF，1打印正式合格证
     * @Create 2013-08-28 huxx
     */
    function saveAndPrint(printType){
        //原合格证必填
        var   veh_Zchgzbh_Re =$('#veh_Zchgzbh_Re').val();
        veh_Zchgzbh_Re= $.trim(veh_Zchgzbh_Re);
        if(veh_Zchgzbh_Re.length==0){
            alert('必须输入原合格证编号!')
            return;
        }

        var veh_Fdjh=$("#veh_Fdjh").val();
        if(veh_Fdjh==null ||  $.trim(veh_Fdjh)=='') {
            alert("保存前，请输入发动机号！");
            return;
        }

        var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
        var veh_Clsbdh   =$('#veh_Clsbdh').val();
        if(veh_Clsbdh==''){
            alert('请计算VIN!');
            return;
        }
        var fzrq=$("#veh_Fzrq").val()
        if(fzrq!=''){
            var year=fzrq.substr(0,4) ;
            var month=fzrq.substr(5,2);
            var day=fzrq.substr(8,2);
            var t=Date.parse(new Date())-Date.parse(new Date(year,month-1,day));
            if(t<0){
                alert("发证日期不合法，请重试!");
                return false;
            }
        }else{
            alert("发证日期不合法，请重试!");
            return false;
        }

        if(veh_Zchgzbh.length==14){
            var dialogId=showTipDialog();
            var updateUrl="${createLink(controller: 'ZCInfo',action:'saveForPrint')}?printType="+printType ;

            if(printType==0){ //打印虚拟合格证，先保存后打印
                var result=print(dialogId);
                if(result!="failed"){
                    parent.closeDialog(dialogId);
                    alert("<div>"+result+"</div>");
                }
            }else if(printType==1){ //打印正式合格证，先打印后保存
                var result=print(dialogId);
                if(result!="failed"){
                    var params=$("#form").serialize();//不能放在打印前执行，不然打印返回值获取不到
                    $.post(updateUrl,params,function(data,textStatus){
                        parent.closeDialog(dialogId);
                        var d=eval("("+data+")");
                        if(d.flag=='sucessed'){
                            alert("<div>"+result+"</div><div>"+d.msg+"</div>");
                            clearData();
                        }else{
                            alert(d.msg);
                        }
                    },'text');
                }
            }


        }else{
            alert('合格证编号不符合要求,请查证')
        }
    }

   
    function create(url,title,width,height){
        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
                'margin-right:-500px;text-align:center; width:100%;height:100%" '+
                'scrolling="yes" src="'+url+'"></iframe>';
        showDialogForIndex('page',1,content,title,width,height);
    }
    $('#btn_printSet').click(function(){
        var url = '${createLink(controller:'printSet',action:'edit')}';
        create(url,'打印设置',400,300);
    });
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

    function print(dialogId){
        var iRtn;
        var VehCert2;
        try{
            VehCert2 = new ActiveXObject("VCertificate.VehCert");
        }catch(err){
            parent.closeDialog(dialogId);
            alert("请使用IE浏览器，并调低IE的安全级别！");
            return "failed";
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
        VehCert2.Veh_Dpxh=$('#veh_Dpxh').val();//底盘型号
        VehCert2.Veh_Dpid=$('#veh_Dpid').val();//底盘ID
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
        VehCert2.Veh_Zs=$('#veh_Zs').val();//轴数
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
        VehCert2.Veh_Zqyzzl=$('#veh_Zqyzzl').val();//准牵引总质量
        VehCert2.Veh_Edzk=$('#veh_Edzk').val();//额定载客
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
        VehCert2.Veh_Zxzs = "1";     //转向轴个数
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
        VehCert2.Veh_Clscdwmc =$('#veh_Clzzqymc').val();          //车辆生产单位名称

        iRtn = VehCert2.PrtParaTbl(1,"FDLS-325D-SKDI-E8EK");
        if(iRtn==1){
            //回填页面信息
            var Veh_Dywym= VehCert2.Veh_Dywym
            var Veh_Jyw= VehCert2.Veh_Jyw
            var Veh_wzhgzbh=VehCert2.GetCertificateNO($('#veh_Zchgzbh').val() ,"N3PL-SX32-AEU2-BC6S");
            $('#veh_Dywym').val(Veh_Dywym);
            $('#vercode').val(Veh_Jyw);
            $('#veh_Zchgzbh_0').val(Veh_wzhgzbh);
            $("#web_virtualcode").val(VehCert2.Veh_Zchgzbh);
            return "完整合格证编号:" + VehCert2.Veh_Zchgzbh;
        }else{
            parent.closeDialog(dialogId);
            alert("打印失败，错误原因：" + VehCert2.Veh_ErrorInfo);
            return "failed";
        }
    }

    function clearData(){
        $("input[name='type']").attr("style","display:inline;");
        $("#down_PDF").hide();
        $('#veh_Zchgzbh_Re').val('');//原合格证编号
        $('#veh_Zchgzbh').val('');//整车合格证编号
        $('#veh_Clsbdh').val('');//车辆识别代号
        $('#veh_Fdjh').val('');//发动机号
        $('#veh_Fdjxh').val('');//發動機型號
        $('#veh_zdjgl').val('');//净功率
        $('#veh_Clfl').val('');    //车辆类型
        $('#veh_Clxh').val('');     //车辆型号
        $('#veh_Zj').val('');       //轴距
        $('#veh_Pl').val('');           //排量和功率
        $('#veh_Gl').val('') ;
        $('#veh_Zzl').val('');        //总质量
        $('#veh_Clmc').val('');      //车辆名称
        $('#veh_Edzzl').val('');    //额定载质量
        $('#veh_Pfbz').val('');   //排放标准
        $('#veh_Hxnbc').val('');     //货厢内部尺寸
        $('#veh_Hxnbk').val('') ;    //货厢内部尺寸
        $('#veh_Hxnbg').val('');      //货厢内部尺寸
        $('#veh_Zxxs').val('');     //转向形式
        $('#veh_Zbzl').val('');     //整备质量
        $('#veh_Zh').val('');      //轴荷
        $('#veh_Wkc').val('');     //外廓尺寸
        $('#veh_Wkk').val('');     //外廓尺寸
        $('#veh_Wkg').val('');      //外廓尺寸
        $('#veh_Zqyzzl').val('');      //准牵引总质量
        $('#veh_Edzk').val('');      //额定载客
        $('#veh_Zzllyxs').val('');      //载质量利用系数
        $('#veh_Qyqtxx').val('');      //车辆制造企业其他信息
        $('#veh_Dpid').val('');      //底盘ID
        $('#veh_Dpxh').val('');      //底盘型号
        $('#veh_Jsslx').val('');      //驾驶室类型
        $('#veh_Bgcazzdyxzzl').val('');      //半挂车鞍座最大允许总质量
        $('#veh_clzzqyxx').val('');      //车辆制造企业信息
        $('#veh_Yh').val('');      //油耗
        $('#veh_Zs').val('');      //轴数
        $('#veh_Ggpc').val('');      //公告批次
        $('#veh_Cpggh').val('');      //产品公告号
        $('#veh_Ggsxrq').val('');      //公告生效日期
        $('#veh_Gbthps').val('');     //钢板弹簧片数
        $('#veh_Clpp').val('');     //车辆品牌
        $('#veh_Lts').val('');      //轮胎数
        $('#veh_Clzzqymc').val('');     //车辆企业名称
        $('#veh_Cpscdz').val('');      //生产地址
        $('#veh_Qybz').val('');     //企业标准
        $('#veh_Zgcs').val('');      //最高车速
        $('#veh_Rlzl').val('');     //燃料种类
        $('#veh_Qlj').val('');     //轮距
        $('#veh_Hlj').val('');      //轮距
        $('#veh_Ltgg').val('');     //轮胎规格
        $('#veh_Jsszcrs').val('');     //驾驶室准乘人数
        $('#veh_Bz').val('');      //备注
        $('#veh_pzxlh').val('');      //配置序列号
        $('#veh_Qyid').val('');      //企业ID
        $('#veh_Hzdfs').val('');
        $('#veh_Jss').val('');      //驾驶室
        $('#veh_VinFourBit').val('');      //vin第四位
        $('#veh_Csys').val('');//车身颜色
        var date=new Date();
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        var day=date.getDate();
        $('#veh_Clzzrq').val(year+"年"+month+"月"+day+"日");//车辆制造日期
        $('#veh_Fzrq').val(year+"年"+month+"月"+day+"日");//发证日期
        $('#veh_Dywym').val('');
        $('#vercode').val('');
        $('#veh_Zchgzbh_0').val('');
    }
</script>
</body>
</html>
