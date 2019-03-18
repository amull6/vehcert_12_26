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
    <g:set var="entityName" value="环保随车清单打印" />
    <title>环保随车清单打印</title>
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
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="800">
    </tr>
</table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:55px;font-size: 0px;font-family:'宋体'">
    <tr>
        <td colspan="12" scope="col">
            <div style="font-family:'方正大标宋简体';font-size: 34px;text-align: center ">浙江飞碟汽车制造有限公司</div>
        </td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:55px;font-size: 20px;font-family:'宋体'">
    <tr height="30">
    </tr>
</table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:55px;font-size: 0px;font-family:'宋体'">
    <tr>
        <td colspan="12" scope="col" style="font-family:'黑体';font-size: 20px;text-align: center " >
            <div style="font-family:'黑体';color:#A80000;font-size: 20px;text-align: center "> 信息公开编号：</div>
        </td>
    </tr>
    <tr>
        <td colspan="12" scope="col" style="font-family:'Arial';color:#A80000;font-size: 20px;text-align: center " >
          ${lightPetrolGb6Info?.en_xxgkbh}
        </td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:55px;font-size: 20px;font-family:'宋体'">
    <tr height="30">
    </tr>
</table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:55px;font-size: 0px;font-family:'宋体'">
    <tr>
        <td colspan="12" scope="col" style="font-family:'宋体';font-size: 14px;text-align: center " >
            <div style="text-align: center "> <img id="pic_fp_photo1"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${BarPicUrl}" /></div>
        </td>
    </tr>
    <tr>
        <td colspan="12" scope="col" style="font-family:'Arial';font-size: 14px;text-align: center " >
            VIN:${lightPetrolGb6Info?.en_clsbdh}
        </td>
    </tr>
</table>

<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="130">
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;margin-top:50px;font-size: 12px;font-family:'宋体'">
    <tr>
        <td colspan="9" style="font-family:'宋体';font-size: 12px;height: 20px">
                &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真实性、准确性、及时性和完整性负责。
            本公司承诺：VIN码（见封面条形码）的轻型汽油车符合《轻型汽车污染物排放限值及测量方法（中国第六阶段）》（GB 18352.6-2016）a（或b）阶段，PN满足6.0×10¹²个/km（或6.0×10¹¹个/km）,RDE仅监测报告（或满足限值），
            《点燃式发动机汽车排气污染物排放限值及测量方法（双怠速法及简易工况法）》（GB 18285）、和《汽车加速行驶车外噪声限值及测量方法》（GB 1495）的相关要求，同时符合相关标准规定的环境保护16（或20）万公里耐久性要求。
        </td>
        <td colspan="3">
            <img id="pic_fp_photo"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${QRpiUrl}" style="width: 110px;height: 110px"/>
        </td>
    </tr>
    <tr height=3></tr>
</table>
<table width="700px;"   border="0px" class="tabp" style="margin-left:15px;margin-top:0px;">
    <tr>
        <td colspan="12" scope="col" style="text-align: center " >
            <span>
                <HR width="105%" color=#000000 SIZE=1>
            </span>
        </td>
    </tr>
</table>
<table width="645px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height=3></tr>
    <tr>
        <td colspan="12" scope="col"style="font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_part2.png"  height="13px" border="0">
        </td>
    </tr>

    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">01</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">车辆型号: </td>
        <td width="230px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_clxh}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">02</td>
        <td width="250px" style=" font-family:'宋体';font-size:12px;">商&nbsp;&nbsp;标: </td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">飞碟</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">03</td>
        <td width="250px" style=" font-family:'宋体';font-size:12px;">汽车分类： </td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_qcfl}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">04</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">排放阶段： </td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_pfjd}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">05</td>
        <td width="250px" style="font-family:'宋体';font-size:12px;">车型的识别方法和位置：</td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_cldsbffhwz}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">06</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">车辆制造商名称：</td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">浙江飞碟汽车制造有限公司</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">07</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">生产厂地址：</td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">山东省五莲县潮河镇驻地</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">08</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">发动机编号：</td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_fdjh}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:12px;">09</td>
        <td width="250px"style=" font-family:'宋体';font-size:12px;">基准质量：</td>
        <td width="300px" style="font-family:'宋体';font-size:12px;text-align:  left">${lightPetrolGb6Info?.en_jzzl}</td>
    </tr>
</table>
<table  width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr>
        <td colspan="12" scope="col"style="font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_part4_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td  width="10px" style=" font-family:'宋体';font-size:12px;">10</td>
        <td   width="120px"style=" font-family:'宋体';font-size:12px;">型式检验信息：</td>
    </tr>
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  width="200px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_yjbz.png"  height="13px"  border="0">
        </td>
        <td  width="400px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_jcjg.png"  height="13px" border="0">
        </td>
        <td  width="200px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_jcjl.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10" >
        <td    style=" font-family:'宋体';font-size:12px;"></td>
        <td  style=" font-family:'宋体';font-size:12px;">GB 18352.6-2016</td>
        <td style=" font-family:'宋体';font-size:12px;">${lightPetrolGb6Info?.en_jcbz1}</td>
        <td style=" font-family:'宋体';font-size:12px;">符合</td>
    </tr>
    <tr height="10" >
        <td style=" font-family:'宋体';font-size:12px;"></td>
        <td style=" font-family:'宋体';font-size:12px;">GB 18285-2005</td>
        <td style=" font-family:'宋体';font-size:12px;">${lightPetrolGb6Info?.en_jcbz2}</td>
        <td  style=" font-family:'宋体';font-size:12px;">符合</td>
    </tr>
    <tr height="10" >
        <td    style=" font-family:'宋体';font-size:12px;"></td>
        <td  style=" font-family:'宋体';font-size:12px;">GB 1495-2002</td>
        <td  style=" font-family:'宋体';font-size:12px;">${lightPetrolGb6Info?.en_jcbz3}</td>
        <td  style=" font-family:'宋体';font-size:12px;">符合</td>
    </tr>
    <tr height="10" >
        <td    style=" font-family:'宋体';font-size:12px;"></td>
        <td  style=" font-family:'宋体';font-size:12px;">GB 27630（预存）</td>
        <td  style=" font-family:'宋体';font-size:12px;">${lightPetrolGb6Info?.en_jcbz4}</td>
        <td style=" font-family:'宋体';font-size:12px;">符合</td>
    </tr>
    <tr height="10" >
        <td    style=" font-family:'宋体';font-size:12px;"></td>
        <td style=" font-family:'宋体';font-size:12px;">GB 8702（预存）</td>
        <td  style=" font-family:'宋体';font-size:12px;">${lightPetrolGb6Info?.en_jcbz5}</td>
        <td style=" font-family:'宋体';font-size:12px;">符合</td>
    </tr>
    <tr height="10" >
        <td   style=" font-family:'宋体';font-size:12px;">11</td>
        <td  style=" font-family:'宋体';font-size:12px;">出厂检验信息：</td>
    </tr>
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  width="200px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_yjbz.png"  height="13px"  border="0">
        </td>
        <td  width="400px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
        </td>
        <td  width="200px"style=" font-family:'宋体';font-size:12px;font-weight: bold">
            <img src="../images/en_jcjl.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10" >
        <td    style=" font-family:'宋体';font-size:12px;"></td>
        <td style=" font-family:'宋体';font-size:12px;">GB 18285-2005</td>
        <td  style=" font-family:'宋体';font-size:12px;"></td>
        <td style=" font-family:'宋体';font-size:12px;">合格</td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">12</td>
        <td   width="500px"style=" font-family:'宋体';font-size:12px;" colspan="4">车型环保生产一致性保证计划及执行情况，详见本公司官方网站和生态环境部信息公开平台（网址附后）。</td>
    </tr>
    <tr>
        <td colspan="5" scope="col"style="font-family:'宋体';font-size:12px;font-weight: bold">
            %{--第三部分  污染控制技术信息--}%
            <img src="../images/en_part5_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">13</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">发动机型号/生产企业：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_fdjxh}/${lightPetrolGb6Info?.en_fdjscqy}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">14</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">催化转化器型号/生产企业：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_chzhqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">涂层/载体/封装生产企业：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_tc}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">15</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">颗粒捕集器型号/生产企业 </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_klbjqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">涂层/载体/封装生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_tc1}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">16</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">炭罐型号/生产企业</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_tgxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">17</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">氧传感器型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_ycgqxh}
        </td>
    </tr>

    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">18</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">曲轴箱排放控制装置型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_qzxpfkzzzxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">19</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">EGR型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_egrxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">20</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">OBD系统供应商：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_obdxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">21</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">ECU型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_ecuxh}
        </td>
    </tr>
    <tr height="10"   style="vertical-align:top">
        <td   width="10px"  rowspan="1" style=" font-family:'宋体';font-size:12px;">22</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">变速器型式/档位数</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_bsqxs}
        </td>
    </tr>
    <tr height="10"   style="vertical-align:top">
        <td   width="10px"  rowspan="1" style=" font-family:'宋体';font-size:12px;">23</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">消音器型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_xyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">24</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">增压器型号/生产企业：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_zyqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">25</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">中冷器型式：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            ${lightPetrolGb6Info?.en_zlqxs}
        </td>
    </tr>
    <tr>
        <td colspan="12" scope="col"style="font-family:'宋体';font-size:12px;font-weight: bold">
            %{--第五部分 制造商/进口企业信息--}%
            <img src="../images/en_part6_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">26</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">法人代表：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            姜卫东
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">27</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">地    址：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            浙江省杭州市余杭区五常荆长路33号
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:12px;">28</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">联系电话：</td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">
            4006582999
        </td>
    </tr>

    <tr height="10">
        <td  colspan="11" width="300px"style=" font-family:'宋体';font-size:12px;">
            &nbsp;  &nbsp;  本清单内容及污染控制装置永久性标识相关信息可查询本公司官方网站（正在建设中）和生态环境部机动车和非道路移动机械环保信息公开平台（http://www.vecc.org.cn）。
        </td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="80">
        %{--<td   width="10px" style=" font-family:'宋体';font-size:12px;"></td>--}%
        %{--<td  colspan="2" width="200px"style=" font-family:'宋体';font-size:12px;">车辆生产（进口）日期：</td>--}%
        %{--<td  colspan="2" width="300px"style=" font-family:'宋体';font-size:12px;">--}%
        %{--${lightPetrolGb6Info?.en_zzrq}--}%
        %{--</td>--}%
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="10">
        <td  colspan="6"  width="200px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  colspan="3" width="60px"style=" font-family:'宋体';font-size:12px;"></td>
        <td  colspan="3" width="100px"style=" font-family:'宋体';font-size:12px;text-align: left">
            （企业盖章处）
        </td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    %{--<tr height="10">--}%

    %{--</tr>--}%
    <tr height="10">
        <td  colspan="8"  width="700px" style=" font-family:'宋体';font-size:12px;"></td>
        <td  colspan="3" width="250px"style=" font-family:'宋体';font-size:12px;text-align: center">车辆生产日期：</td>
        <td  colspan="2" width="250px"style=" font-family:'宋体';font-size:12px;text-align: left">
            ${lightPetrolGb6Info?.en_zzrq}
        </td>
    </tr>


</table>
</form>



</div>

</div>


</body>
</html>