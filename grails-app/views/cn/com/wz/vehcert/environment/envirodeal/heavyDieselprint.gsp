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
<g:if test="${heavyDieselInfo?.type=='0'}">
    <div style="width: 225px;height: 58px;position: absolute;right: -90px;top: 208px; text-align: center;">
        <img id="pic_fp_photo1"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${BarPicUrl}" />
        <span style="font-size:14px; ">${heavyDieselInfo?.en_clsbdh}</span>
    </div>
</g:if> <g:elseif test="${heavyDieselInfo?.type=='1'}">
    <div style="width: 225px;height: 58px;position: absolute;right: -90px;top: 223px; text-align: center;">
        <img id="pic_fp_photo2"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${BarPicUrl}" />
        <span style="font-size:14px; ">${heavyDieselInfo?.en_clsbdh}</span>
    </div>
</g:elseif>
<g:if test="${heavyDieselInfo?.type=='0'}">
    <div style="width: 102%;height: 3px;position: absolute;left:-10px;top: 192px; text-align: center;margin-left: 35px">
        <span>
            <HR width="105%" color=#000000 SIZE=1>
        </span>
    </div>
</g:if> <g:elseif test="${heavyDieselInfo?.type=='1'}">
    <div style="width: 102%;height: 3px;position: absolute;left:-10px;top: 207px; text-align: center;margin-left: 35px">
        <span>
            <HR width="105%" color=#000000 SIZE=1>
        </span>
    </div>
</g:elseif>
    <table width="645px;"    border="0px" class="tabp" style="margin-left:25px;font-size: 0px;font-family:'宋体'">
        <tr>
            <td align="center" style="text-align: center">
                <g:if test="${heavyDieselInfo?.type=='0'}">
                    <img src="../images/en_part1.png" height="20px"  border="0">
                </g:if> <g:elseif test="${heavyDieselInfo?.type=='1'}">
                    <img src="../images/en_part1_3.png" height="20px"  border="0" >
                </g:elseif>
                %{--<B>重型柴油车环保信息随车清单</B><br>--}%
            </td>
        </tr>
        <tr>
            <td colspan="12" scope="col" style="font-family:'宋体';font-size: 14px;text-align: center " >
                信息公开编号：${heavyDieselInfo?.en_xxgkbh}
            </td>
        </tr>
        <tr height="1">
            <td colspan="12"></td>
        </tr>
    </table>
    <table width="720px;"    border="0px" class="tabp" style="margin-left:15px;margin-top:50px;font-size: 20px;font-family:'宋体'">
        <tr>
        <td colspan="12" style="font-family:'宋体';font-size: 14px;height: 20px">
            <g:if test="${heavyDieselInfo?.type=='0'}">
            &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真实性、准确性、及时性和完整性负责。本公司承诺：我公司VIN码（见本页条形码）的重型柴油车符合《车用压燃式、气体燃料点燃式发动机与汽车排气污染物排放限值及测量方法（中国Ⅲ、Ⅳ、Ⅴ阶段）》（GB 17691-2005）
                <g:if test="${heavyDieselInfo?.en_pfjd=='国四'}">
                    Ⅳ
                </g:if> <g:elseif test="${heavyDieselInfo?.en_pfjd=='国五'}">
                Ⅴ
            </g:elseif>阶段、 <g:if test="${heavyDieselInfo?.en_bz == '新标准'}">
                《柴油车污染物排放限值及测量方法（自由加速法及加载减速法）》（GB3847-2018）
            </g:if> <g:else>
                《车用压燃式发动机和压燃式发动机汽车排气烟度排放限值及测量方法》（GB 3847-2005）
            </g:else>和《汽车加速行驶车外噪声限值及测量方法》（GB 1495-2002）第Ⅱ阶段的要求。
            </g:if><g:elseif test="${heavyDieselInfo?.type=='1'}">
                &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真实性、准确性、及时性和完整性负责。本公司承诺： 我公司VIN码（见本页条形码）的城市车辆符合《车用压燃式、气体燃料点燃式发动机与汽车排气污染物排放限值及测量方法（中国Ⅲ、Ⅳ、Ⅴ阶段）》（GB 17691-2005）第
                <g:if test="${heavyDieselInfo?.en_pfjd=='国四'}">
                    Ⅳ
                </g:if> <g:elseif test="${heavyDieselInfo?.en_pfjd=='国五'}">
                    Ⅴ
                </g:elseif>阶段、《城市车辆用柴油发动机排气污染物排放限值及测量方法（WHTC工况法）》（HJ 689-2014）第
                <g:if test="${heavyDieselInfo?.en_pfjd=='国四'}">
                    Ⅳ
                </g:if> <g:elseif test="${heavyDieselInfo?.en_pfjd=='国五'}">
                    Ⅴ
                </g:elseif>阶段、 <g:if test="${heavyDieselInfo?.en_bz == '新标准'}">
                    《柴油车污染物排放限值及测量方法（自由加速法及加载减速法）》（GB 3847-2018）
                </g:if> <g:else>
                    《车用压燃式发动机和压燃式发动机汽车排气烟度排放限值及测量方法》（GB 3847-2005）
                </g:else>和《汽车加速行驶车外噪声限值及测量方法》（GB 1495-2002）第Ⅱ阶段的要求，同时符合乘用车内空气质量标准和相关标准规定的环境保护耐久性要求。
            </g:elseif>
        </td>
        </tr>
        <tr height=3></tr>
    </table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height=3></tr>
        <tr>
            <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_part2.png"  height="13px" border="0">
            </td>
        </tr>

        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">1</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">车辆型号: </td>
            <td width="230px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_clxh}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">2</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">商&nbsp;&nbsp;&nbsp; 标: </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">飞碟</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">3</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">汽车分类： </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_qcfl}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">4</td>
            <td width="250px"style=" font-family:'宋体';font-size:14px;">排放阶段： </td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_pfjd}</td>
        </tr>
        <tr height="10">
            <td width="10px" style=" font-family:'宋体';font-size:14px;">5</td>
            <td width="250px" style=" font-family:'宋体';font-size:14px;">车型的识别方法和位置：</td>
            <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_clsbff}</td>
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
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
        <tr>
            <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
                %{--第二部分  发动机信息--}%
                <img src="../images/en_part3.png"  height="13px"  border="0">
        </td>
        </tr>
        <tr height="10" >
            <td  width="10px" style=" font-family:'宋体';font-size:14px;">8</td>
            <td   width="100px"style=" font-family:'宋体';font-size:14px;">发动机型号：</td>
            <td   width="350px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_fdjxh}</td>
            <td   width="10px" style=" font-family:'宋体';font-size:14px;">9</td>
            <td  width="100px"style=" font-family:'宋体';font-size:14px;">发动机编号：</td>
            <td   width="150px" style="tfont-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_fdjh}</td>
        </tr>
        <tr height="10" >
            <td  width="10px" style=" font-family:'宋体';font-size:14px;">10</td>
            <td   width="120px"style=" font-family:'宋体';font-size:14px;">制造商名称：</td>
            <td   width="250px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_zzs}</td>
            <td   width="10px" style=" font-family:'宋体';font-size:14px;">11</td>
            <td  width="120px"style=" font-family:'宋体';font-size:14px;">系族名称：</td>
            <td   width="200px" style="tfont-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_xzmc}</td>
        </tr>
        <tr height="10" >
            <td  width="10px" style=" font-family:'宋体';font-size:14px;">12</td>
            <td   width="120px"style=" font-family:'宋体';font-size:14px;">生产厂地址：</td>
            <td   width="250px" style="font-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_sccdz}</td>
            <td   width="10px" style=" font-family:'宋体';font-size:14px;">13</td>
            <td  width="120px"style=" font-family:'宋体';font-size:14px;">厂&nbsp;&nbsp;&nbsp;&nbsp;牌：</td>
            <td   width="200px" style="tfont-family:'宋体';font-size:14px;text-align:  left">${heavyDieselInfo?.en_cp}</td>
        </tr>
</table>
<table  width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
        <tr>
            <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
                <img src="../images/en_part4.png"  height="13px"  border="0">
            </td>
        </tr>
        <tr height="10">
            <td  width="10px" style=" font-family:'宋体';font-size:14px;">14</td>
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
    <g:if test="${heavyDieselInfo?.en_jcbz1!=''&&heavyDieselInfo?.en_jcbz1!=null}">
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td  style=" font-family:'宋体';font-size:14px;">GB 17691-2005</td>
            <td style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz1}</td>
            <td style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz2!=''&&heavyDieselInfo?.en_jcbz2!=null}">
        <g:if test="${heavyDieselInfo?.en_bz == '新标准'}">

        </g:if> <g:else>
        <tr height="10" >
            <td style=" font-family:'宋体';font-size:14px;"></td>
            <td style=" font-family:'宋体';font-size:14px;">GB 3847-2005</td>
            <td style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz2}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:else>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz3!=''&&heavyDieselInfo?.en_jcbz3!=null}">
        <tr height="10" >
            <td   style=" font-family:'宋体';font-size:14px;"></td>
            <td style=" font-family:'宋体';font-size:14px;">HJ 689-2014</td>
            <td  style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz3}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz4!=''&&heavyDieselInfo?.en_jcbz4!=null}">
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td  style=" font-family:'宋体';font-size:14px;">HJ 437-2008</td>
            <td  style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz4}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz5!=''&&heavyDieselInfo?.en_jcbz5!=null}">
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td  style=" font-family:'宋体';font-size:14px;">HJ 438-2008</td>
            <td  style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz5}</td>
            <td style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz6!=''&&heavyDieselInfo?.en_jcbz6!=null}">
        <tr height="10" >
            <td    style=" font-family:'宋体';font-size:14px;"></td>
            <td style=" font-family:'宋体';font-size:14px;">GB 1495-2002</td>
            <td  style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz6}</td>
            <td style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <g:if test="${heavyDieselInfo?.en_jcbz7!=''&&heavyDieselInfo?.en_jcbz7!=null}">
        <tr height="10" >
            <td   style=" font-family:'宋体';font-size:14px;"></td>
            <td style=" font-family:'宋体';font-size:14px;">GB/T 27630-2011</td>
            <td  style=" font-family:'宋体';font-size:14px;">${heavyDieselInfo?.en_jcbz7}</td>
            <td  style=" font-family:'宋体';font-size:14px;">符合</td>
        </tr>
    </g:if>
    <tr height="10" >
        <td   style=" font-family:'宋体';font-size:14px;">15</td>
        <td  style=" font-family:'宋体';font-size:14px;">出厂检验项目及结论：</td>
        <td  style=" font-family:'宋体';font-size:14px;">自由加速烟度试验，合格</td>
        <td style=" font-family:'宋体';font-size:14px;"></td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">16</td>
        <td   width="500px"style=" font-family:'宋体';font-size:14px;" colspan="4">本车型环保生产一致性保证计划及执行情况，详见本公司官方网站和生态环境部信息公开平台（网址附后）。</td>
    </tr>
    <tr>
        <td colspan="5" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第四部分  污染控制技术信息--}%
            <img src="../images/en_part5.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">17</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">最大净功率/转速(kW/r/min)：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_zzjgl}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">18</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">最大净扭矩/转速(Nm/r/min)：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_zdjnj}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">19</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">燃料供给系统型式： </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_rlggxtxs}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">20</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">喷油泵型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_pybxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">21</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">喷油器型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_pyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">22</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">增压器型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_zyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">23</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">中冷器型式：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_zlqxs}
        </td>
    </tr>

    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">24</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">OBD型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_OBDxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">25</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">EGR型号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_EGRxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">26</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">ECU型号/版本号/生产厂： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_ECUxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">27</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">排气后处理系统型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_pqhclxtxh}
        </td>
    </tr>
    <tr height="10"   style="vertical-align:top">
        <td   width="10px"  rowspan="1" style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="2" rowspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">封装/载体/涂层生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_tc}
        </td>
    </tr>
    <tr height="10"   style="vertical-align:top">
        <td   width="10px"  rowspan="1" style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            <g:if test="${heavyDieselInfo?.en_tc1.toString()=='null'}">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </g:if> <g:elseif test="${heavyDieselInfo?.en_tc1.toString().length()!='null'}">
                ${heavyDieselInfo?.en_tc1}
            </g:elseif>
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">28</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">排气后处理系统型式：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_pqhclxtxs}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">29</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">空气滤清器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_klqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">30</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">进气消声器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_jqxsqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">31</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">排气消声器型号/生产厂：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${heavyDieselInfo?.en_pqxsqxh}
        </td>
    </tr>
    <tr height="10">
        <td  colspan="11" width="300px"style=" font-family:'宋体';font-size:14px;">
        &nbsp;  &nbsp;  本车辆环保关键零部件（发动机、喷油泵、喷油器、增压器、EGR、ECU、排气后处理系统、空气滤清器、进气消声器、排气消声器）明显标注了永久性标识，
        标识内容包括该零部件的型号和生产企业名称（全称、缩写或徽标），详见本公司官方网站和生态环境部信息公开平台（网址附后）。
        </td>
    </tr>
    <tr height="20">
        %{--<td  colspan="11" width="300px"style=" font-family:'宋体';font-size:14px;">--}%
            %{--&nbsp;  &nbsp;  本车辆环保关键零部件（发动机、喷油泵、喷油器、增压器、EGR、ECU、排气后处理系统、空气滤清器、进气消声器、排气消声器）明显标注了永久性标识，--}%
            %{--标识内容包括该零部件的型号和生产企业名称（全称、缩写或徽标），详见本公司官方网站和生态环境部信息公开平台（网址附后）。--}%
        %{--</td>--}%
    </tr>
    <tr>
        <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第五部分 制造商/进口企业信息--}%
            <img src="../images/en_part6.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">32</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">法人代表：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            姜卫东
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">33</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">地    址：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            浙江省杭州市余杭区五常荆长路33号
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">34</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">联系电话：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            4006582999
            %{--13806330208--}%
        </td>
    </tr>

    <tr height="10">
        <td  colspan="11" width="300px"style=" font-family:'宋体';font-size:14px;">
            &nbsp;  &nbsp;  本信息内容可访问本公司官方网站（正在建设中）和生态环境部机动车和非道路移动机械环保信息公开平台（http://www.vecc.org.cn）。
        </td>
    </tr>
    </table>
    <table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="880">
        %{--<td   width="10px" style=" font-family:'宋体';font-size:14px;"></td>--}%
        %{--<td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">车辆生产（进口）日期：</td>--}%
        %{--<td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">--}%
            %{--${heavyDieselInfo?.en_zzrq}--}%
        %{--</td>--}%
    </tr>
    </table>
    <table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
        <tr height="10">
            <td  colspan="6"  width="200px" style=" font-family:'宋体';font-size:14px;"></td>
            <td  colspan="3" width="60px"style=" font-family:'宋体';font-size:14px;"></td>
            <td  colspan="3" width="100px"style=" font-family:'宋体';font-size:14px;text-align: left">
                （企业盖章处）
            </td>
        </tr>
    </table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    %{--<tr height="10">--}%

    %{--</tr>--}%
    <tr height="10">
        <td  colspan="8"  width="700px" style=" font-family:'宋体';font-size:14px;"></td>
        <td  colspan="3" width="250px"style=" font-family:'宋体';font-size:14px;text-align: center">车辆生产日期：</td>
        <td  colspan="2" width="250px"style=" font-family:'宋体';font-size:14px;text-align: left">
            ${heavyDieselInfo?.en_zzrq}
        </td>
    </tr>


    </table>
</form>



</div>

</div>


</body>
</html>