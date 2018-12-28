<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <style>
    .tabclass{
        border-collapse:collapse;border:none;
    }

    .tabclass td{
        border:solid #000 1px;
    }
    </style>
    <style media=print>

    .Noprint{display:none;}
    .PageNext{page-break-after: always;}
    </style>
    <meta name="layout" content="main">
    <g:set var="entityName" value="一致性证书打印" />
    <title>一致性证书打印</title>
    <script language="javascript" src="../js/vehcert/LodopFuncs.js"></script>
    <script language="javascript" type="text/javascript">
        function prn1_print(){
            window.print();
        }
    </script>
</head>
<body>
<div id="page" style="width:650px;background-color:#ffffff;margin:0px 0px;">
<form id="form_query" style="margin:8px;" class="Noprint">
    <table style="width:100%;border:0px;" >
        <tr>
            <td  align="left" valign="center">
                <a class="btn_basic blue_black" href="javascript:prn1_print()">直接打印</a>
                <g:if test="${usertype == '1'}">
                    <g:link controller="cocPrint"  class="btn_basic blue_black" action="ownLoadList"  >${message(code: 'default.button.return.label', default: 'Return')}</g:link>
                </g:if>
                <g:if test="${usertype == '0'}">
                    <g:link controller="cocPrint" class="btn_basic blue_black" action="cheJianList" >${message(code: 'default.button.return.label', default: 'Return')}</g:link>
                </g:if>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器打印时，请确认未勾选“启用收缩到纸张大小”。（文件->页面设置）[新]</div>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器下请设置上下左右页边距为19.05,19.05,19.05,19.05 </div>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">Chrome浏览器下请设置上下左右页边距为：19.0,15.0,19.0,19.0 </div>

            </td>
        </tr>
    </table>
    <input type="hidden" id="categoryId" name=categoryId>
</form>
<div class="PageNext"></div>
<form id="form2" >
%{--<table  border="0" class="tabp" style="width:645px;font-family:'宋体';font-size: 16px;margin-left:3px" >--}%
    %{--<th scope="col" style="vertical-align :bottom">车辆一致性证书参数</th>--}%

    %{--<tr>--}%
        %{--<td style="width:645px;font-family:'宋体';font-size: 12px">（以下所示数值和单位是相应CCC认证文件中给出的。对于生产一致性（COP）试验，这些值必须按照相应标准中所描述的方法进行核对，并考虑这些标准中COP试验的允差。）</td>--}%
    %{--</tr>--}%
%{--</table>--}%
<g:if test="${cocPrnInstance?.coc_new_energy=='是'}">
    <table class="tabclass " style="width:615px;margin-left: 13px;margin-top:170px;font-size: 12px ;" >
        <tr  height="5px">
            <th style="width:7%;"></th>
            <th style="width:14%;"></th>
            <th style="width:15%;"></th>
            <th style="width:16%;"></th>
            <th style="width:15%;"></th>
            <th style="width:16%;"></th>
            <th style="width:17%;"></th>
        </tr>
        <tr  height="15px">
            <td colspan="2">车辆的一致性证书编号</td>
            <td colspan="4">${cocPrnInstance?.coc_yzxzsbh}</td>
            <td rowspan="6"><img id="pic_fp_photo1"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${QRpiUrl}" style="width: 99px;height: 99px"/></td>
        </tr>
        <tr>
            <td colspan="2">车辆制造国</td>
            <td colspan="4">${cocPrnInstance?.coc_clzzg}</td>
        </tr>
        <tr>
            <td colspan="2">车辆生产企业名称</td>
            <td colspan="4">${cocPrnInstance?.coc_clsccmc}</td>
        </tr>
        <tr>
            <td colspan="2">车辆生产企业地址</td>
            <td colspan="4">${cocPrnInstance?.coc_scqydz}</td>
        </tr>
        <tr>
            <td colspan="2">生产者（制造商）名称</td>
            <td colspan="4">${cocPrnInstance?.coc_zzsmc}</td>
        </tr>
        <tr>
            <td colspan="2">新能源车</td>
            <td colspan="4">${cocPrnInstance?.coc_new_energy}</td>
        </tr>
        <tr>
            <td colspan="2">车辆识别代号</td>
            <td colspan="4">${cocPrnInstance?.coc_clsbdh}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">车辆识别代号打刻位置</td>
            <td colspan="4">${cocPrnInstance?.coc_sbhdkwz}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">法定标牌位置</td>
            <td colspan="4">${cocPrnInstance?.coc_fdmpwz}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">车辆型号/名称</td>
            <td colspan="2">${cocPrnInstance?.coc_cxdhmc}</td>
            <td colspan="2">车型名称</td>
            <td >${cocPrnInstance?.coc_cxmc}</td>
        </tr>
        <tr>
            <td colspan="2">车辆注册类型</td>
            <td colspan="2">${cocPrnInstance?.coc_clzclx}</td>
            <td colspan="2">车辆类别</td>
            <td >${cocPrnInstance?.coc_cllb}</td>
        </tr>
        <tr>
            <td colspan="2">车辆品牌</td>
            <td colspan="2">${cocPrnInstance?.coc_clzwpp}</td>
            <td colspan="2">车辆颜色</td>
            <td>${cocPrnInstance?.coc_clys}</td>
        </tr>
        <tr>
            <td colspan="2">电动机编号</td>
            <td colspan="2">${cocPrnInstance?.coc_fdjbh}</td>
            <td colspan="2">电动机编号在电动机上的打刻位置</td>
            <td>${cocPrnInstance?.coc_fdjbhdkwz}</td>
        </tr>
        <tr>
            <td colspan="2">车辆种类</td>
            <td colspan="5">${cocPrnInstance?.coc_clzl}</td>
        </tr>
        <tr>
            <td colspan="2">基本车辆一致性证书编号</td>
            <td colspan="5">${cocPrnInstance?.coc_jccccno}</td>
        </tr>
        <tr>
            <td colspan="2">基本车辆型号</td>
            <td colspan="2">${cocPrnInstance?.coc_jcclxh}</td>
            <td colspan="2">基本车辆类别</td>
            <td>${cocPrnInstance?.coc_jccllb}</td>
        </tr>
        <tr>
            <td colspan="4">最终（或本）阶段车辆CCC证书编号（版本号）/签发日期</td>
            <td colspan="3">${cocPrnInstance?.coc_zzjdcccno}/${cocPrnInstance?.coc_zzjdqfrq}</td>
        </tr>
        <tr>
            <td colspan="2">车轴数量</td>
            <td colspan="2">${cocPrnInstance?.coc_czsl}</td>
            <td colspan="2">车轮数量</td>
            <td>${cocPrnInstance?.coc_clsl}</td>
        </tr>
        <tr>
            <td colspan="2">驱动轴位置</td>
            <td colspan="2">${cocPrnInstance?.coc_qdzwz}</td>
            <td colspan="2">前悬/后悬(mm)</td>
            <td>${cocPrnInstance?.coc_qx}/${cocPrnInstance?.coc_hx}</td>
        </tr>
        <tr>
            <td colspan="2">轮距(mm)</td>
            <td colspan="2">${cocPrnInstance?.coc_lj}</td>
            <td colspan="2">外廓尺寸(mm)</td>
            <td>${cocPrnInstance?.coc_cd}*${cocPrnInstance?.coc_kd}*${cocPrnInstance?.coc_gd}</td>
        </tr>
        <tr>
            <td colspan="2">轴距(mm)</td>
            <td colspan="2">${cocPrnInstance?.coc_zj}</td>
            <td colspan="2">货箱内部尺寸(mm)</td>
            <td>${cocPrnInstance?.coc_hxcd}*${cocPrnInstance?.coc_hxkd}*${cocPrnInstance?.coc_hxgd}</td>
        </tr>
        <tr>
            <td colspan="3">牵引装置中心与车辆最前端间的距离(mm)</td>
            <td>${cocPrnInstance?.coc_zqdyqyzzjl}</td>
            <td colspan="2">接近角/离去角(°)</td>
            <td>${cocPrnInstance?.coc_jjj}/${cocPrnInstance?.coc_lqj}</td>
        </tr>
        <tr>
            <td colspan="2">整备质量(kg)</td>
            <td colspan="2">${cocPrnInstance?.coc_xsxdcsclzl}</td>
            <td colspan="2">额定载质量(kg)</td>
            <td>${cocPrnInstance?.coc_edzzl}</td>
        </tr>
        <tr>
            <td colspan="2">载质量利用系数</td>
            <td colspan="2">${cocPrnInstance?.coc_zzllyxsh}</td>
            <td colspan="2">车门的数量和结构</td>
            <td>${cocPrnInstance?.coc_cmsl}/${cocPrnInstance?.coc_cmgz}</td>
        </tr>
        <tr>
            <td colspan="2">最大允许总质量(kg)</td>
            <td colspan="2">${cocPrnInstance?.coc_zzllyxsh}</td>
            <td colspan="2">最大允许总质量对应的轴荷分配</td>
            <td>${cocPrnInstance?.coc_zlzhfp}</td>
        </tr>
        <tr>
            <td colspan="2">燃料种类</td>
            <td colspan="2">${cocPrnInstance?.coc_rlzl}</td>
            <td colspan="2">直接喷射</td>
            <td>${cocPrnInstance?.coc_zjps}</td>
        </tr>
        <tr>
            <td colspan="2">气缸数量和排列</td>
            <td colspan="2">${cocPrnInstance?.coc_qgsl}/${cocPrnInstance?.coc_qgplxs}</td>
            <td colspan="2">排量(ml)</td>
            <td>${cocPrnInstance?.coc_pl}</td>
        </tr>
        <tr>
            <td colspan="2">电动机生产厂名称</td>
            <td colspan="2">${cocPrnInstance?.coc_fdjzzsmc}</td>
            <td colspan="2">电动机型号</td>
            <td>${cocPrnInstance?.coc_fdjxh}</td>
        </tr>
        <tr>
            <td colspan="2">电动机工作电压(V)</td>
            <td colspan="2">${cocPrnInstance?.coc_ddjgzdy}</td>
            <td colspan="2">最大输出功率(kw)</td>
            <td>${cocPrnInstance?.coc_zdjgl}</td>
        </tr>
        <tr>
            <td colspan="2">动力电池型号</td>
            <td colspan="2">${cocPrnInstance?.coc_dldcxh}</td>
            <td colspan="2">动力电池额定电压</td>
            <td>${cocPrnInstance?.coc_dldceddy}</td>
        </tr>
        <tr>
            <td colspan="2">动力电池生产厂名称</td>
            <td colspan="2">${cocPrnInstance?.coc_dldcsccmc}</td>
            <td colspan="2">动力电池额定容量</td>
            <td>${cocPrnInstance?.coc_dldcedrl}</td>
        </tr>
        <tr>
            <td colspan="2">离合器(型式)</td>
            <td colspan="2">${cocPrnInstance?.coc_lhqxs}</td>
            <td colspan="2">变速器(型式)</td>
            <td>${cocPrnInstance?.coc_bsqzs}</td>
        </tr>
        <tr>
            <td colspan="2">转向型式</td>
            <td colspan="2">${cocPrnInstance?.coc_zxzlxs}</td>
            <td colspan="2">最高设计车速(km/h)</td>
            <td>${cocPrnInstance?.coc_zgcs}</td>
        </tr>
        <tr>
            <td colspan="2">轮胎规格</td>
            <td colspan="5">${cocPrnInstance?.coc_ltgg}</td>
        </tr>
        <tr>
            <td colspan="2">制动装置简要说明</td>
            <td colspan="5">${cocPrnInstance?.coc_zdzzjysm}</td>
        </tr>
        <tr>
            <td colspan="2">是否带防抱死系统</td>
            <td colspan="2">${cocPrnInstance?.coc_abs}</td>
            <td colspan="2">额定载客人数</td>
            <td>${cocPrnInstance?.coc_zws}</td>
        </tr>
        <tr>
            <td colspan="2">钢板弹簧片数(片)</td>
            <td colspan="2">${cocPrnInstance?.coc_gbthps}</td>
            <td colspan="2">车辆制造日期</td>
            <td>${cocPrnInstance?.coc_rq}</td>
        </tr>
        <tr>
            <td rowspan="3">声级</td>
            <td colspan="3">CCC认证引用的标准号及对应的实施阶段</td>
            <td colspan="3">${cocPrnInstance?.coc_sjbzh}</td>
        </tr>
        <tr>
            <td colspan="3">定置噪声(db(A))/对应的发动机转速(r/min)</td>
            <td colspan="3">${cocPrnInstance?.coc_dzzs}/${cocPrnInstance?.coc_fdjzs}</td>
        </tr>
        <tr>
            <td colspan="3">加速行驶车外噪声(db(A))</td>
            <td colspan="3">${cocPrnInstance?.coc_cwzs}</td>
        </tr>
        <tr>
            <td colspan="2">备注</td>
            <td colspan="5">${cocPrnInstance?.coc_note}</td>
        </tr>
    </table>
    </g:if> <g:elseif test="${cocPrnInstance?.coc_new_energy=='否'}">
    <table class="tabclass " style="width:615px;margin-left: 13px;font-size: 12px ;  " >
        <tr  height="15px">
            <td style="width:22%;" colspan="2">车辆的一致性证书编号</td>
            <td style="width:61%;" colspan="4">${cocPrnInstance?.coc_yzxzsbh}</td>
            <td style="width:17%;" rowspan="6"><img id="pic_fp_photo"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${QRpiUrl}" style="width: 99px;height: 99px"/></td>
        </tr>
        <tr>
            <td colspan="2">车辆制造国</td>
            <td colspan="4">${cocPrnInstance?.coc_clzzg}</td>
        </tr>
        <tr>
            <td colspan="2">车辆生产企业名称</td>
            <td colspan="4">${cocPrnInstance?.coc_clsccmc}</td>
        </tr>
        <tr>
            <td colspan="2">车辆生产企业地址</td>
            <td colspan="4">${cocPrnInstance?.coc_scqydz}</td>
        </tr>
        <tr>
            <td colspan="2">生产者（制造商）名称</td>
            <td colspan="4">${cocPrnInstance?.coc_zzsmc}</td>
        </tr>
        <tr>
            <td colspan="2">新能源车</td>
            <td colspan="4">${cocPrnInstance?.coc_new_energy}</td>
        </tr>
        <tr>
            <td colspan="2">车辆识别代号</td>
            <td colspan="4">${cocPrnInstance?.coc_clsbdh}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">车辆识别代号打刻位置</td>
            <td colspan="4">${cocPrnInstance?.coc_sbhdkwz}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">法定标牌位置</td>
            <td colspan="4">${cocPrnInstance?.coc_fdmpwz}</td>
            <td ></td>
        </tr>
        <tr>
            <td colspan="2">车辆型号/名称</td>
            <td colspan="2">${cocPrnInstance?.coc_cxdhmc}</td>
            <td colspan="2">车型名称</td>
            <td >${cocPrnInstance?.coc_cxmc}</td>
        </tr>
        <tr>
            <td colspan="2">车辆注册类型</td>
            <td colspan="2">${cocPrnInstance?.coc_clzclx}</td>
            <td colspan="2">车辆类别</td>
            <td >${cocPrnInstance?.coc_cllb}</td>
        </tr>
        <tr>
            <td colspan="2">车辆品牌</td>
            <td colspan="2">${cocPrnInstance?.coc_clzwpp}</td>
            <td colspan="2">车辆颜色</td>
            <td>${cocPrnInstance?.coc_clys}</td>
        </tr>
        <tr>
            <td colspan="2">发动机/电动机编号</td>
            <td colspan="2">${cocPrnInstance?.coc_fdjbh}</td>
            <td colspan="2">发动机编号在发动机上的打刻位置</td>
            <td>${cocPrnInstance?.coc_fdjbhdkwz}</td>
        </tr>
        <tr>
            <td colspan="2">车辆种类</td>
            <td colspan="5">${cocPrnInstance?.coc_clzl}</td>
        </tr>
        <tr>
            <td colspan="2">基本车辆一致性证书编号</td>
            <td colspan="5">${cocPrnInstance?.coc_jccccno}</td>
        </tr>
        <tr>
            <td colspan="2">基本车辆型号</td>
            <td colspan="2">${cocPrnInstance?.coc_jcclxh}</td>
            <td colspan="2">基本车辆类别</td>
            <td>${cocPrnInstance?.coc_jccllb}</td>
        </tr>
        <tr>
            <td colspan="4">最终（或本）阶段车辆CCC证书编号（版本号）/签发日期</td>
            <td colspan="3">${cocPrnInstance?.coc_zzjdcccno}/${cocPrnInstance?.coc_zzjdqfrq}</td>
        </tr>
        <tr>
            <td colspan="2">车轴数量</td>
            <td colspan="2">${cocPrnInstance?.coc_czsl}</td>
            <td colspan="2">车轮数量</td>
            <td>${cocPrnInstance?.coc_clsl}</td>
        </tr>
        <tr>
            <td colspan="2">驱动轴位置</td>
            <td colspan="2">${cocPrnInstance?.coc_qdzwz}</td>
            <td colspan="2">前悬/后悬(mm)</td>
            <td>${cocPrnInstance?.coc_qx}/${cocPrnInstance?.coc_hx}</td>
        </tr>
        <tr>
            <td colspan="2">轮距(mm)</td>
            <td colspan="2">${cocPrnInstance?.coc_lj}</td>
            <td colspan="2">外廓尺寸(mm)</td>
            <td>${cocPrnInstance?.coc_cd}*${cocPrnInstance?.coc_kd}*${cocPrnInstance?.coc_gd}</td>
        </tr>
        <tr>
            <td colspan="2">轴距(mm)</td>
            <td colspan="2">${cocPrnInstance?.coc_zj}</td>
            <td colspan="2">货箱内部尺寸(mm)</td>
            <td>${cocPrnInstance?.coc_hxcd}*${cocPrnInstance?.coc_hxkd}*${cocPrnInstance?.coc_hxgd}</td>
        </tr>
        <tr>
            <td colspan="3">牵引装置中心与车辆最前端间的距离(mm)</td>
            <td>${cocPrnInstance?.coc_zqdyqyzzjl}</td>
            <td colspan="2">接近角/离去角(°)</td>
            <td>${cocPrnInstance?.coc_jjj}/${cocPrnInstance?.coc_lqj}</td>
        </tr>
        <tr>
            <td colspan="2">整备质量(kg)</td>
            <td colspan="2">${cocPrnInstance?.coc_xsxdcsclzl}</td>
            <td colspan="2">额定载质量(kg)</td>
            <td>${cocPrnInstance?.coc_edzzl}</td>
        </tr>
        <tr>
            <td colspan="2">载质量利用系数</td>
            <td colspan="2">${cocPrnInstance?.coc_zzllyxsh}</td>
            <td colspan="2">车门的数量和结构</td>
            <td>${cocPrnInstance?.coc_cmsl}/${cocPrnInstance?.coc_cmgz}</td>
        </tr>
        <tr>
            <td colspan="2">最大允许总质量(kg)</td>
            <td colspan="2">${cocPrnInstance?.coc_zzllyxsh}</td>
            <td colspan="2">最大允许总质量对应的轴荷分配</td>
            <td>${cocPrnInstance?.coc_zlzhfp}</td>
        </tr>
        <tr>
            <td colspan="2">燃料种类</td>
            <td colspan="2">${cocPrnInstance?.coc_rlzl}</td>
            <td colspan="2">直接喷射</td>
            <td>${cocPrnInstance?.coc_zjps}</td>
        </tr>
        <tr>
            <td colspan="2">气缸数量和排列</td>
            <td colspan="2">${cocPrnInstance?.coc_qgsl}/${cocPrnInstance?.coc_qgplxs}</td>
            <td colspan="2">排量(ml)</td>
            <td>${cocPrnInstance?.coc_pl}</td>
        </tr>
        <tr>
            <td colspan="2">发动机生产厂名称</td>
            <td colspan="2">${cocPrnInstance?.coc_fdjzzsmc}</td>
            <td colspan="2">发动机型号</td>
            <td>${cocPrnInstance?.coc_fdjxh}</td>
        </tr>
        <tr>
            <td colspan="2">电动机工作电压(V)</td>
            <td colspan="2">${cocPrnInstance?.coc_ddjgzdy}</td>
            <td colspan="2">最大输出功率(kw)</td>
            <td>${cocPrnInstance?.coc_zdjgl}</td>
        </tr>
        <tr>
            <td colspan="2">动力电池型号</td>
            <td colspan="2">${cocPrnInstance?.coc_dldcxh}</td>
            <td colspan="2">动力电池额定电压</td>
            <td>${cocPrnInstance?.coc_dldceddy}</td>
        </tr>
        <tr>
            <td colspan="2">动力电池生产厂名称</td>
            <td colspan="2">${cocPrnInstance?.coc_dldcsccmc}</td>
            <td colspan="2">动力电池额定容量</td>
            <td>${cocPrnInstance?.coc_dldcedrl}</td>
        </tr>
        <tr>
            <td colspan="2">离合器(型式)</td>
            <td colspan="2">${cocPrnInstance?.coc_lhqxs}</td>
            <td colspan="2">变速器(型式)</td>
            <td>${cocPrnInstance?.coc_bsqzs}</td>
        </tr>
        <tr>
            <td colspan="2">转向型式</td>
            <td colspan="2">${cocPrnInstance?.coc_zxzlxs}</td>
            <td colspan="2">最高设计车速(km/h)</td>
            <td>${cocPrnInstance?.coc_zgcs}</td>
        </tr>
        <tr>
            <td colspan="2">轮胎规格</td>
            <td colspan="5">${cocPrnInstance?.coc_ltgg}</td>
        </tr>
        <tr>
            <td colspan="2">制动装置简要说明</td>
            <td colspan="5">${cocPrnInstance?.coc_zdzzjysm}</td>
        </tr>
        <tr>
            <td colspan="2">是否带防抱死系统</td>
            <td colspan="2">${cocPrnInstance?.coc_abs}</td>
            <td colspan="2">额定载客人数</td>
            <td>${cocPrnInstance?.coc_zws}</td>
        </tr>
        <tr>
            <td colspan="2">钢板弹簧片数(片)</td>
            <td colspan="2">${cocPrnInstance?.coc_gbthps}</td>
            <td colspan="2">车辆制造日期</td>
            <td>${cocPrnInstance?.coc_rq}</td>
        </tr>
        <tr>
            <td rowspan="3">声级</td>
            <td colspan="3">CCC认证引用的标准号及对应的实施阶段</td>
            <td colspan="3">${cocPrnInstance?.coc_sjbzh}</td>
        </tr>
        <tr>
            <td colspan="3">定置噪声(db(A))/对应的发动机转速(r/min)</td>
            <td colspan="3">${cocPrnInstance?.coc_dzzs}/${cocPrnInstance?.coc_fdjzs}</td>
        </tr>
        <tr>
            <td colspan="3">加速行驶车外噪声(db(A))</td>
            <td colspan="3">${cocPrnInstance?.coc_cwzs}</td>
        </tr>
        <tr>
            <td rowspan="7">排气排放物</td>
            <td colspan="3">CCC认证引用的标准号及对应的实施阶段</td>
            <td colspan="2">${cocPrnInstance?.coc_pfwbzh}</td>
            <td></td>
        </tr>
        <tr>
            <td rowspan="3">实验用液体燃料：${cocPrnInstance?.coc_ytrl}</td>
            <td colspan="2">CO</td>
            <td>${cocPrnInstance?.coc_ytrl_co}</td>
            <td>HC(THC国五)</td>
            <td>${cocPrnInstance?.coc_ytrl_hc}</td>
        </tr>
        <tr>
            <td colspan="2">NOx</td>
            <td>${cocPrnInstance?.coc_ytrl_nox}</td>
            <td>HC+NOx</td>
            <td>${cocPrnInstance?.coc_ytrl_hcnox }</td>
        </tr>
        <tr>
            <td colspan="2">烟度(吸收系数(m-1)的校正)</td>
            <td>${cocPrnInstance?.coc_ytrl_yd}</td>
            <td>微粒物/PN</td>
            <td>${cocPrnInstance?.coc_qtrl_pn}</td>
        </tr>
        <tr>
            <td rowspan="3">实验用气体燃料：天然气</td>
            <td colspan="2">CO</td>
            <td>${cocPrnInstance?.coc_qtrl_co}</td>
            <td>NOx</td>
            <td>${cocPrnInstance?.coc_qtrl_nox}</td>
        </tr>
        <tr>
            <td colspan="2">NMHC</td>
            <td>${cocPrnInstance?.coc_qtrl_nmhc}</td>
            <td>THC</td>
            <td>${cocPrnInstance?.coc_qtrl_thc}</td>
        </tr>
        <tr>
            <td colspan="2">CH4</td>
            <td>${cocPrnInstance?.coc_qtrl_ch4}</td>
            <td>微粒物/PN</td>
            <td>${cocPrnInstance?.coc_ytrl_pn}</td>
        </tr>
        <tr>
            <td class="tg-031e" colspan="2" rowspan="5">CO2排放量/燃料消耗量</td>
            <td class="tg-031e" colspan="2">CCC认证引用的标准号</td>
            <td colspan="2">${cocPrnInstance?.coc_co2pflbzh}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="2">CO2排放量</td>
            <td colspan="2">燃料消耗量</td>
            <td></td>
        </tr>
        <tr>
            <td>市区(g/km)</td>
            <td>${cocPrnInstance?.coc_co2_sq}</td>
            <td>市区(g/km)</td>
            <td>${cocPrnInstance?.coc_rlxh_sq}</td>
            <td></td>
        </tr>
        <tr>
            <td>市郊(g/km)</td>
            <td>${cocPrnInstance?.coc_co2_sj}</td>
            <td>市郊(g/km)</td>
            <td>${cocPrnInstance?.coc_rlxh_sj}</td>
            <td></td>
        </tr>
        <tr>
            <td>综合(g/km)</td>
            <td>${cocPrnInstance?.coc_co2_zh}</td>
            <td>综合(g/km)</td>
            <td>${cocPrnInstance?.coc_rlxh_zh}</td>
            <td></td>
        </tr>
        <tr>
            <td colspan="2">备注</td>
            <td colspan="5">${cocPrnInstance?.coc_note}</td>
        </tr>
    </table>
</g:elseif>
</form>
</div>
</body>
</html>