
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />

    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="100"><span><g:message code="zcinfo.veh_Clsbdh.label" default="veh_Clsbdh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh" maxlength="200" value="${zciInfoModel?.veh_Clsbdh}"/></span></td>
                    <td width='20'></td>
                    <td width="50"><span>类型：</span></td>
                    <td width="80"><span>
                        <g:if test='${zciInfoModel?.veh_Clztxx}'>
                            <select id="veh_Clztxx">
                                <g:if test='${zciInfoModel?.veh_Clztxx=='DP'}'>
                                     <option value="DP">底盘</option>
                                    <option value="QX">整车</option>
                                </g:if>
                                <g:if test='${zciInfoModel?.veh_Clztxx=='QX'}'>
                                     <option value="QX">整车</option>
                                    <option value="DP">底盘</option>
                                 </g:if>
                            </select>
                        </g:if>
                        <g:else>
                             <select id="veh_Clztxx">
                                 <option value="QX">整车</option>
                                 <option value="DP">底盘</option>

                            </select>
                        </g:else>
                    </span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <a id="btn_relevance" class="btn_basic blue_black"  href="javascript:relevance();"><g:message code="com.util.GX.label" default="relevance" /></a>
                        <input type="hidden" name="hidden_id" id="hidden_id" value="${zciInfoModel?.id}"/>
                        <a id="btn_save" class="btn_basic blue_black"  href="javascript:save();"><g:message code="default.button.save.label" default="save" /></a>
                        <a id="btn_clear" class="btn_basic blue_black"  href="javascript:refresh();"><g:message code="default.button.clear.label" default="clear" /></a>
                        &nbsp;<span style="color: red">注:&nbsp;发证日期只能是当天之前的日期（包括当天的日期）</span>
                    </td>
                </tr>
            </table>

        </form>

    <div style="margin-right:8px;margin-top:8px;">
        <div class="om-grid om-widget om-widget-content" style="height: 100%;">
            <div class="hDiv om-state-default">
                <div class="hDivBox"></div>
            </div>
            <input type="hidden" name="veh_Clzzrq" id="veh_Clzzrq" value="${zciInfoModel?.veh_Clzzrq}"/>
            <div class="bDiv" style="width: auto; height:100%">
                <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <tr class="om-grid-row evenRow">
                        <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " ><b>合格证日期变更</b></br>合格证信息</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"></td>
                        <td align="left" abbr="id" class="col0" colspan="3"></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px"><span style="color: blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纸张编号</span></div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" name="veh_Zzbh_re" id="veh_Zzbh_re"></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:150px">1、合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div>
                        <g:hiddenField name="pdf_veh_Zchgzbh" value="${zciInfoModel?.veh_Zchgzbh}"/>
                        </td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">2、<span style="color: blue">发证日期</span></div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >
                            <c:dataPicker id="veh_Fzrq" editable="false" dateFormat="yy年mm月dd日" name="veh_Fzrq"  value="${zciInfoModel?.veh_Fzrq}" />
                        </div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >3、车辆制造企业名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzqymc}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >4、车辆品牌/车辆名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clpp}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clmc}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">5、车辆型号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clxh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6、车身颜色</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Csys }</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >7、车辆识别代号/车架号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clsbdh}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Cjh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >8、底盘型号/底盘ID</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpxh}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpid}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">9、底盘合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dphgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10、发动机型号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fdjxh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">11、发动机号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">12、燃料种类</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Rlzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13、排量和功率(ml/kW)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zciInfoModel?.veh_Pl}</div></td>
                        <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zciInfoModel?.veh_Gl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">14、排放标准</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Pfbz}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">15、油耗</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Yh}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">16、外廓尺寸(mm)</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkc}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkk}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkg}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17、货厢内部尺寸(mm)</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbc}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbk}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">18、钢板弹簧片数(片)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Gbthps}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19、轮胎数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Lts}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">20、轮胎规格</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ltgg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">21、轮距(前/后)(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Qlj}</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zciInfoModel?.veh_Hlj}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">22、轴距(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zj}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">23、轴荷(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">24、轴数</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zs}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25、转向形式</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zxxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">26、总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27、整备质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zbzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">28、额定载质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29、载质量利用系数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zzllyxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">30、准牵引总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zqyzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31、半挂车鞍座最大允许总质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">32、驾驶室准乘人数(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Jsszcrs}</div></td>
                        <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36、二维条码</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">33、额定载客(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzk}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">34、最高车速(km/h)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zgcs}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">35、<span style="color: blue">车辆制造日期</span></div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzrq}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：</div></td>
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
        ///判断发证日期
        if(!'${zciInfoModel}'){
             $('#veh_Fzrq').val('');
        }


        $("#veh_Dphgzbh").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_relevance').click();
                return false;
            }
        });
    })
    function save(){
        var url = "${createLink(controller:'ZCInfo',action:'updateZcinfoByDate')}";
        url+='?'+$('#form_query').serialize();
        var veh_Fzrq = $("#veh_Fzrq").val()
        var hidden_id = $("#hidden_id").val()
        var veh_Clzzrq = $("#veh_Clzzrq").val()
        var veh_Zzbh_re = $("#veh_Zzbh_re").val()
        if(hidden_id==""){
            alert("请关联要保存的合格证信息!");
            return false;
        }
        if(veh_Clzzrq==""){
            alert("车辆制造不允许为空!");
            return false;
        } if(veh_Fzrq==""){
            alert("发证日期不允许为空!");
            return false;
        }
        var year=veh_Fzrq.substr(0,4) ;
        var month=veh_Fzrq.substr(5,2);
        var day=veh_Fzrq.substr(8,2);
        var t=Date.parse(new Date())-Date.parse(new Date(year,month-1,day));
        if(t<0){
            alert("发证日期不合法，请重试!");
            return false;
        }

        $("#veh_Clzzrq").val(veh_Fzrq);

        //先打印pdf在保存记录
        $.post(url,{"veh_Clzzrq": $("#veh_Clzzrq").val(),"veh_Fzrq":veh_Fzrq,"veh_Zzbh_re":veh_Zzbh_re},function(data,textStatus){
            data=eval("("+data+")");
            alert(data.msg);
        },'text');
    };
     function relevance(){
         var veh_Clztxx=$('#veh_Clztxx').val();
        var url = "${createLink(controller:'ZCInfo',action:'zcinfoBychangeDateIndex')}";
        url+='?'+$('#form_query').serialize()+"&veh_Clztxx="+veh_Clztxx;
        window.location.href = url;

    };
    function refresh(){
        var url = "${createLink(controller:'ZCInfo',action:'zcinfoBychangeDateIndex')}";
        window.location.href=url;
    };
    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }
     ////判断发证日期是否为有效日期(不得大于当天时间 )    2013-02-02
    function iseffective(date)
    {
        var year=date.substr(0,4) ;
        var month=date.substr(5,2);
        var day=date.substr(8,2);
        var t=Date.parse(new Date())-Date.parse(new Date(year,month-1,day));
        if(t<0){
            alert("发证日期不合法，请重试!");
            return false;
        }
    }
</script>
</body>
</html>
