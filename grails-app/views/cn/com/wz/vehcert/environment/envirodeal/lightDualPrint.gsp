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
                    <g:link controller="envirPrint" class="btn_basic blue_black" action="cheJianList" >${message(code: 'default.button.return.label', default: 'Return')}</g:link>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器打印时，请确认未勾选“启用收缩到纸张大小”。（文件->页面设置）[新]</div>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器下请设置上下左右页边距为19.05,19.05,19.05,19.05 </div>
                <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">Chrome浏览器下请设置上下左右页边距为：19.0,15.0,19.0,19.0 </div>

            </td>
        </tr>
    </table>
    <input type="hidden" id="categoryId" name=categoryId>
</form>
<form id="form1" style="position: relative">
    <img id="pic_fp_photo"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${QRpiUrl}" style="width: 110px;height: 110px;position: absolute;right: -90px;top: -12px"/>
<div style="width: 225px;height: 58px;position: absolute;right: -90px;top: 208px; text-align: center;">
<img id="pic_fp_photo1"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${BarPicUrl}" />
    <span style="font-size:14px; ">${lightDualInfo?.en_clsbdh}</span>
</div>
    <div style="width: 107%;height: 3px;position: absolute;left:-10px;top: 180px; text-align: center;margin-left: 35px">
        <span>
            <HR width="99%" color=#000000 SIZE=1>
        </span>
    </div>

    <table width="645px;"    border="0px" class="tabp" style="margin-left:25px;font-size: 0px;font-family:'宋体'">
        <tr>
            <g:if test="${lightDualInfo?.type=='0'}">
            <td style="text-align: center;margin-right: 20px" >
                    %{--双燃料--}%
                    <img src="../images/en_part1_1.png" height="20px"  border="0">
            </td>
            </g:if>
            <g:elseif test="${lightDualInfo?.type=='1'}">
                <td style="text-align: center;margin-right: 200px" >
                    <img src="../images/en_part1_2.png" height="20px"  border="0">
                </td>
            </g:elseif>
        </tr>
        <tr>
            <td colspan="12" scope="col" style="font-family:'宋体';font-size: 14px;text-align: center " >
                信息公开编号：${lightDualInfo?.en_xxgkbh}
            </td>
        </tr>
        <tr height="1">
            <td colspan="12"></td>
        </tr>
    </table>
    <table width="720px;"    border="0px" class="tabp" style="margin-left:15px;margin-top:50px;font-size: 20px;font-family:'宋体'">
        <tr>
        <td colspan="12" style="font-family:'宋体';font-size: 14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                %{--双燃料--}%
                &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真
             实性、准确性、及时性和完整性负责。本公司承诺：我公司VIN码（见本页条形码）的轻型两用燃料车符合《轻型汽车污染物排放限值及测量方法（中国第五阶段）》
             （GB 18352.5-2013）、<g:if test="${lightDualInfo?.en_bz == '新标准'}">
                《汽油车污染物排放限值及测量方法（双怠速法及简易工况法）》（GB 18285-2018）
            </g:if> <g:else>
                《点燃式发动机汽车排气污染物排放限值及测量方法（双怠速法及简易工况法）》（GB 18285）
            </g:else>和《汽车加速行驶车外噪声限值及测量方法》
             （GB 1495-2002）第Ⅱ阶段的要求。
            </g:if>
            <g:elseif test="${lightDualInfo?.type=='1'}" >
                &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真实性、准确性、
               及时性和完整性负责。本公司承诺：我公司VIN码（见本页条形码）的轻型汽油车符合《轻型汽车污染物排放限值及测量方法（中国第五阶段）》（GB 18352.5-2013）、
                <g:if test="${lightDualInfo?.en_bz == '新标准'}">
                    《汽油车污染物排放限值及测量方法（双怠速法及简易工况法）》（GB 18285-2018）
                </g:if> <g:else>
                《点燃式发动机汽车排气污染物排放限值及测量方法（双怠速法及简易工况法）》（GB 18285）
            </g:else>和《汽车加速行驶车外噪声限值及测量方法》（GB 1495-2002）
               第Ⅱ阶段的要求。
            </g:elseif>


        </td>
            %{--<br>--}%
        </tr>
    </table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
        <tr>
            <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_part2.png"  height="13px" border="0">
            </td>
        </tr>

        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">1</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">车辆型号: </td>
            <td width="230px" style="font-family:'宋体';font-size:14px;text-align:  left">${lightDualInfo?.en_clxh}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">2</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">商&nbsp;&nbsp;&nbsp; 标: </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">飞碟</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">3</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">汽车分类： </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${lightDualInfo?.en_qcfl}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">4</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">排放阶段： </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${lightDualInfo?.en_pfjd}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">5</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">车型的识别方法和位置：</td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${lightDualInfo?.en_cldsbffhwz}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">6</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">车辆制造商名称：</td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">浙江飞碟汽车制造有限公司</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">7</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">生产厂地址：</td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">山东省五莲县潮河镇驻地</td>
        </tr>
</table>
<table  width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
        <tr>
            <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_part4_1.png"  height="13px"  border="0">
            </td>
        </tr>
        <tr height="10">
            <td  width="10px" style=" font-family:'宋体';font-size:14px;">8</td>
            <td   width="120px"style=" font-family:'宋体';font-size:14px;">型式检验信息：</td>
        </tr>
        <tr height="10" >
            <td   width="10px" style=" font-family:'宋体';font-size:14px;"></td>
            <td  width="200px"style=" font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_yjbz.png"  height="13px"  border="0">
            </td>
            <td  width="400px"style=" font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_jcjg.png"  height="13px" border="0">
            </td>
            <td  width="200px"style=" font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_jcjl.png"  height="13px"  border="0">
            </td>
        </tr>
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td  style=" font-family:'宋体';font-size:14px;">GB 18352.5-2013</td>
            <td style=" font-family:'宋体';font-size:14px;">${lightDualInfo?.en_jcbz1}</td>
            <td style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    <g:if test="${lightDualInfo?.en_bz == '新标准'}">

    </g:if> <g:else>
        <tr height="10" >
            <td style=" font-family:'宋体';font-size:14px;"></td>
            <td style=" font-family:'宋体';font-size:14px;">GB 18285-2005</td>
            <td style=" font-family:'宋体';font-size:14px;">${lightDualInfo?.en_jcbz2}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:else>
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td  style=" font-family:'宋体';font-size:14px;">GB 1495-2002</td>
            <td  style=" font-family:'宋体';font-size:14px;">${lightDualInfo?.en_jcbz3}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    <tr height="10" >
        <td   style=" font-family:'宋体';font-size:14px;">9</td>
        <td  style=" font-family:'宋体';font-size:14px;">出厂检验项目及结论：</td>
        <td  style=" font-family:'宋体';font-size:14px;">双怠速试验</td>
        <td style=" font-family:'宋体';font-size:14px;">符合</td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">10</td>
        <td   width="500px"style=" font-family:'宋体';font-size:14px;" colspan="4">本车型环保生产一致性保证计划及执行情况，详见本公司官方网站和生态环境部信息公开平台（网址附后）。</td>
    </tr>
    <tr>
        <td colspan="5" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第四部分  污染控制技术信息--}%
            <img src="../images/en_part5_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">11</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">发动机型号/生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_fdjxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">12</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">催化转化器型号/生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_chzhqxh}
        </td>
    </tr>
    <tr height="10"  style="vertical-align:top">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">涂层/载体/封装生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_tc}
        </td>
    </tr>
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">13</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">燃油蒸发控制装置型号/生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_rlzfkzzzxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">14</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">氧传感器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_ycgqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">15</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">曲轴箱排放控制装置型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_qzxpfkzzzxh}
        </td>
    </tr>
    %{--<tr height="10">--}%
        %{--<td   width="10px" style=" font-family:'宋体';font-size:14px;">22</td>--}%
        %{--<td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">增压器型号/生产厂： </td>--}%
        %{--<td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">--}%
            %{--${lightDualInfo?.en_zyqxh}--}%
        %{--</td>--}%
    %{--</tr>--}%
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">16</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">EGR型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_egrxh}
        </td>
    </tr>

    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">17</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">OBD型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_obdxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">18</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">IUPR监测功能： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_iuprjcgn}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">19</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">ECU型号/版本号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_ecuxh}
        </td>
    </tr>
    <g:if test="${lightDualInfo?.type=='0'}">
        %{--双燃料--}%
        <tr height="10">
            <td   width="10px" style=" font-family:'宋体';font-size:14px;">20</td>
            <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">燃气混合器型号/生产厂</td>
            <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
                ${lightDualInfo?.en_rqhhqxh}
            </td>
        </tr>
        <tr height="10">
            <td   width="10px" style=" font-family:'宋体';font-size:14px;">21</td>
            <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">燃气喷射单元型号/生产厂：</td>
            <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
                ${lightDualInfo?.en_rqpsdyxh}
            </td>
        </tr>
    </g:if>

    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
        <g:if test="${lightDualInfo?.type=='0'}">
            22
        </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
             20
        </g:elseif>

        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">变速器型式/档位数：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_bsqxs}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                23
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                21
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">消声器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_xyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                24
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                22
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">增压器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_zyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                25
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                23
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">中冷器型式：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${lightDualInfo?.en_zlqxs}
        </td>
    </tr>
    <tr height="20" style="vertical-align:top">
        <td  colspan="12" width="300px"style=" font-family:'宋体';font-size:14px;">
        &nbsp;  &nbsp; 本车辆环保关键零部件（发动机、催化转化器、燃油蒸发控制装置、氧传感器、EGR、ECU、消声器、增压器）明显标注了永久性标识，标识内容包括该零部件的型号和生产企业名称
        （全称、缩写或徽标）， 详见本公司官方网站和生态环境部信息公开平台（网址附后）。
        </td>
    </tr>

</table>
<table  width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr>
        <td colspan="5" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第五部分 制造商/进口企业信息--}%
            <img src="../images/en_part6_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                26
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                24
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">法人代表：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            姜卫东
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                27
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                25
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">地    址：</td>
        <td  colspan="7" width="300px"style=" font-family:'宋体';font-size:14px;">
            浙江省杭州市余杭区五常荆长路33号
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">
            <g:if test="${lightDualInfo?.type=='0'}">
                28
            </g:if> <g:elseif test="${lightDualInfo?.type=='1'}">
                26
            </g:elseif>
        </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">联系电话：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            4006582999
        </td>
    </tr>

    <tr height="10">
        <td  colspan="11" width="300px"style=" font-family:'宋体';font-size:14px;">
            &nbsp;  &nbsp;  本信息内容可访问本公司官方网站（正在建设中）和生态环境部机动车和非道路移动机械环保信息公开平台（http://www.vecc.org.cn）。
        </td>
    </tr>
    </table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;margin-top:-10px;font-size: 20px;font-family:'宋体'">
    <g:if test="${lightDualInfo?.type=='1'}">
    <tr height="12">
    </tr>
    </g:if>

    <tr height="10">
        <td  colspan="6"  width="200px" style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="3" width="60px"style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="3" width="100px"style=" font-family:'宋体';font-size:14px;text-align: left">
            （企业盖章处）
        </td>
    </tr>
</table>

    <table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;margin-top:-5px;font-family:'宋体'">

        <tr height="10">
            <td  colspan="8"  width="700px" style=" font-family:'宋体';font-size:14px;"></td>
            <td  colspan="3" width="250px"style=" font-family:'宋体';font-size:14px;text-align: center">车辆生产日期：</td>
            <td  colspan="2" width="250px"style=" font-family:'宋体';font-size:14px;text-align: left">
                ${lightDualInfo?.en_zzrq}
            </td>
        </tr>


    </table>

    </table>
</form>



</div>

</div>


</body>
</html>