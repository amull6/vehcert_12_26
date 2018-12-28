
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
    <style type="text/css">
        input[type='text']{
            width:150px;
        }
    </style>
</head>
<body>
<div id="page" style="background-color:white;">
<div id="center-panel" style="margin-left:4px;border:1px;">


<div style="margin-top:8px; height: 98%;">

<div class="om-grid om-widget om-widget-content">
<div class="hDiv om-state-default">
    <div class="hDivBox"></div>
</div>
<div class="bDiv" style="float:left; height:100%">

<div class="bDiv" style="width:99.5%; height:50%">
    <table id="gridRand"  cellpadding="0" cellspacing="0" border="0" style="">
        <tbody>
        <tr class="om-grid-row evenRow">
            <td align="center" class="indexCol" colspan="8">
                <div align="center" class="innerCol " >
                    合格证更换申请手动填写原合格证信息<br/>
                </div>
            </td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td  abbr="id" class="col0" colspan="8" >
                <a  id="btn_select" style="line-height:30px;margin-left: 300px; width:120px; font-size:14px;" class="btn_basic blue_black"  >按公告号提取数据</a>
           </td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >纸张编号</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Zzbh"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >1、合格证编号<br/><span style="color: red;">请填写不包含校验位的14位合格证编号</span></div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><input type="text" id="veh_Zchgzbh"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:65px">2、发证日期</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  >
                <c:dataPicker name="veh_Fzrq" id="veh_Fzrq" editable="false" dateFormat="yy年mm月dd日" ></c:dataPicker>
            </div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp"></div></td>
            <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clxh"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车身颜色</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >
                <input type="text" id="veh_Csys"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车辆识别代号/车架号</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " >
                <input id="veh_Clsbdh" type="text" name="veh_Clsbdh" style="width:150px;">
            </div></td>

        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh"></div></td>
            <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid"></div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dphgzbh"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" id="veh_Fdjxh"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Fdjh"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Rlzl"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
            <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " id="veh_Pl" ></div></td>
            <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " id="veh_Gl"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh"></div></td>

        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkc"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkk"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkg"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
            <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbc"></div></td>
            <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbk"></div></td>
            <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbg"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Gbthps"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Lts" ></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ltgg"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Qlj"></div></td>
            <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Hlj"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zj"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zh"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zs"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "id="veh_Zxxs"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zzl"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zbzl"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzzl"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zzllyxs"></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zqyzzl"></div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大允许总质量(kg)</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Bgcazzdyxzzl"></div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Jsszcrs"></div></td>
            <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
            <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzk"></div></td>

        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zgcs"></div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " >
                <c:dataPicker name="veh_Clzzrq" id="veh_Clzzrq" editable="false" dateFormat="yy年mm月dd日" ></c:dataPicker>
            </div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol " id="veh_clzzqyxx">车辆制造企业信息：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol " id="veh_Bz">备注：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" abbr="id" class="col0" colspan="8">
                <div align="left" class="innerCol " style="text-align: center;">

                    <input type="hidden" id="carstorageId" value=''/>
                    <input type="hidden" id="replaceType" value='${replaceType}'/>
                    <input type="hidden" id="turnOff" value='${turnOff}'/>
                    <input type="hidden" id="username" value='${username}'/>

                    <a id="submit"  class="btn_basic blue_black" style="width:80px;font-size: 14px;" >确定</a>
                     %{--<g:if test="${replaceType}">--}%
                         %{--二次更换的手动填写--}%
                         %{--<a id="cancel"  class="btn_basic blue_black" style="width:80px;font-size: 14px;" href="${createLink(controller:'ZCInfo',action:'replaceTwoTime')}?replaceType=1&turnOff=${turnOff}" >返回</a>--}%
                     %{--</g:if><g:else>--}%
                        %{--正常更换的手动填写--}%
                        <a id="cancel"  class="btn_basic blue_black" style="width:80px;font-size: 14px;" href="${createLink(controller:'ZCInfoNoAuth',action:'zcinfoRoundNoAuth')}?turnOff=${turnOff}&username=${username}" >返回</a>
                    %{--</g:else>--}%
                     %{--//后退+刷新--}%
                    %{--<a id="cancel"  class="btn_basic blue_black" style="width:80px;font-size: 14px;" href ="javascript:history.go(-1);">返回</a>--}%
                     %{--//后退+刷新--}%
                    %{--<a id="cancel"  class="btn_basic blue_black" style="width:80px;font-size: 14px;" href ="javascript:history.back();">返回</a>--}%
                </div></td>
        </tr>

        </tbody>
    </table>
</div>

</div>
</div>
</div>
</div>
</div>
<script src="${resource(dir:'js/layer',file:'layer.js') }" type="text/javascript" ></script>
<link rel="stylesheet" href="${resource(dir:'js/layer/skin/default',file:'layer.css') }" type="text/css">
<script>
    $(function() {
        if("${returnMessage}" !=""){
            alert("${returnMessage}");
            if('${data}'!=''){
                var data=${data?data:'1'};
                $("#veh_Zchgzbh").val(data.veh_Zchgzbh);
                $("#veh_Clsbdh").val(data.veh_Clsbdh);
                $("#veh_Zzbh").val(data.veh_Zzbh);
                $("#veh_Fzrq").val(data.veh_Fzrq);
                $("#veh_Csys").val(data.veh_Csys);
                $("#veh_Clzzrq").val(data.veh_Clzzrq);
                $("#veh_Fdjh").val(data.veh_Fdjh);
                changeData(data);
            }

        }
        $('#page').css({height:$(document).height()-250});


        $('#btn_select').click(function(){
                var url = '${createLink(controller:'ZCInfoNoAuth',action:'toNoticeWriteNoAuth')}';
                create(url);
        });
        $('#submit').click(function(){

                var replaceType=$('#replaceType').val();

                var veh_Zchgzbh=$('#veh_Zchgzbh').val();
                var veh_Zzbh=$('#veh_Zzbh').val();
                var veh_Fzrq=$('#veh_Fzrq').val();
                var veh_Clsbdh=$('#veh_Clsbdh').val();
                var veh_Csys=$('#veh_Csys').val();
                var veh_Fdjxh=$('#veh_Fdjxh').val();
                var veh_Fdjh=$('#veh_Fdjh').val();
                var veh_Clzzrq=$('#veh_Clzzrq').val();
                var carstorageId=$('#carstorageId').val();
                var turnOff=$('#turnOff').val();
                var username=$('#username').val();
                if(veh_Zchgzbh.length!=14){
                    alert("请填写14位合格证编号！");
                    return;
                }
                if(veh_Clsbdh==""){
                    alert("请填写车辆识别代号！");
                    return;
                }
                 if(carstorageId==''){
                     alert("请选择公告");
                     return;
                 }
              var url="${createLink(controller:'ZCInfoNoAuth',action:'saveWriteNoAuth')}?veh_Zchgzbh="+veh_Zchgzbh+"&veh_Fzrq="+veh_Fzrq+"&veh_Clsbdh="+veh_Clsbdh+"&veh_Csys="+veh_Csys+"&veh_Fdjxh="+veh_Fdjxh+"&veh_Fdjh="+veh_Fdjh+"&veh_Clzzrq="+veh_Clzzrq+"&carstorageId="+carstorageId+"&veh_Zzbh="+veh_Zzbh+'&replaceType='+replaceType+'&turnOff='+turnOff+'&username='+username;
                window.location.href=url;

        });

    })
    function changeData(data){
        $('#carstorageId').val(data.id);
        $('#veh_Fdjxh').val(data.veh_Fdjxh);
        $('#web_virtualcode').html(data.web_virtualcode);
        $('#veh_Dphgzbh').html(data.veh_Dphgzbh) ;
        $('#veh_Cjh').html(data.veh_Cjh) ;
        $('#veh_Edzk').html(data.veh_Edzk) ;
        $('#veh_Zs').html(data.veh_Zs) ;
        $('#veh_Zqyzzl').html(data.veh_Zqyzzl) ;
        $('#veh_Bgcazzdyxzzl').html(data.veh_Bgcazzdyxzzl) ;
        $('#veh_zdjgl').html(data.veh_zdjgl)//净功率
        $('#veh_Clfl').html(data.veh_Clfl)    //车辆类型
        $('#veh_Clxh').html(data.veh_Clxh)     //车辆型号
        $('#veh_Zj').html(data.veh_Zj)            //轴距
        $('#veh_Pl').html(data.veh_Pl)           //排量和功率
        $('#veh_Gl').html(data.veh_Gl)
        $('#veh_Zzl').html(data.veh_Zzl)        //总质量
        $('#veh_Clmc').html(data.veh_Clmc)      //车辆名称
        $('#veh_Edzzl').html(data.veh_Edzzl)    //额定载质量
        $('#veh_Pfbz').html(data.veh_Pfbz)   //排放标准
        $('#veh_Hxnbc').html(data.veh_Hxnbc)     //货厢内部尺寸
        $('#veh_Hxnbk').html(data.veh_Hxnbk)     //货厢内部尺寸
        $('#veh_Hxnbg').html(data.veh_Hxnbg)      //货厢内部尺寸
        $('#veh_Zxxs').html(data.veh_Zxxs)     //转向形式
        $('#veh_Zbzl').html(data.veh_Zbzl)     //整备质量
        $('#veh_Zh').html(data.veh_Zh)      //轴荷
        $('#veh_Wkc').html(data.veh_Wkc)     //外廓尺寸
        $('#veh_Wkk').html(data.veh_Wkk)     //外廓尺寸
        $('#veh_Wkg').html(data.veh_Wkg)      //外廓尺寸
        $('#veh_Zzllyxs').html(data.veh_Zzllyxs)      //载质量利用系数
        $('#veh_Qyqtxx').html(data.veh_Qyqtxx)      //车辆制造企业其他信息
        $('#veh_Dpid').html(data.veh_Dpid)      //底盘ID
        $('#veh_Dpxh').html(data.veh_Dpxh)      //底盘型号
        $('#veh_Jsslx').html(data.veh_Jsslx)      //驾驶室类型
        $('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#veh_Yh').html(data.veh_Yh)      //油耗
        $('#veh_Ggpc').html(data.veh_Ggpc)      //公告批次
        $('#veh_Cpggh').html(data.veh_Cpggh)      //产品公告号
        $('#veh_Gbthps').html(data.veh_Gbthps)     //钢板弹簧片数
        $('#veh_Clpp').html(data.veh_Clpp)     //车辆品牌
        $('#veh_Lts').html(data.veh_Lts)      //轮胎数
        $('#veh_Clzzqymc').html(data.veh_Clzzqymc)     //车辆企业名称
       $('#veh_Qybz').html(data.veh_Qybz)     //企业标准

        $('#veh_Zgcs').html(data.veh_Zgcs)      //最高车速
        $('#veh_Rlzl').html(data.veh_Rlzl)     //燃料种类
        $('#veh_Qlj').html(data.veh_Qlj)     //轮距
        $('#veh_Hlj').html(data.veh_Hlj)      //轮距
        $('#veh_Ltgg').html(data.veh_Ltgg)     //轮胎规格
        $('#veh_Jsszcrs').html(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#veh_Bz').html(data.veh_Bz)      //备注
        $('#veh_pzxlh').html(data.veh_pzxlh)      //配置序列号
        $('#veh_Qyid').html(data.veh_Qyid)      //企业ID
        $('#veh_Hzdfs').html(data.veh_Hzdfs)
        $('#veh_Clztxx').html(data.veh_Clztxx)
        $('#veh_Jss').html(data.veh_Jss)      //驾驶室
        $('#veh_VinFourBit').html(data.veh_VinFourBit)
             //vin第四位
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
    });
    function create(url){
        var tabId=window.name;
        tabId = '123456'
//        var flag=url.indexOf('?');
//        if(flag>0){
//            url+="&tabId="+tabId;
//        }else{
//            url+="?tabId="+tabId;
//        }
//        alert(url);
//        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
//                'margin-right:-500px;text-align:center; width:100%;height:100%" '+
//                'scrolling="yes" src="'+url+'"></iframe>';
//        parent.showDialog(1,content,"公告信息",1400,900);
        var index=layer.open({
            id:  tabId,
            type: 2,
            title: '公告选择',
            shadeClose: true,
            shade: 0.8,
            area: ['90%', '90%'],
            content: url, //iframe的url
            end:(function(){
//                var res = window["layui-layer-iframe2"].callbackdata();
//                //打印返回的值，看是否有我们想返回的值。
//                console.log(res);
//                alert(res);
            })

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
