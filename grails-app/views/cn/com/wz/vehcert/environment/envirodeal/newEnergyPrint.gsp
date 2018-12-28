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
<div style="width: 225px;height: 58px;position: absolute;right: -90px;top: 178px; text-align: center;">
    <img id="pic_fp_photo1"  src="${request.contextPath}/attachment/getImg?QRpiUrl=${BarPicUrl}" />
    <span style="font-size:14px; ">${newEnergyInfo?.en_vin}</span>
</div>
<div style="width: 102%;height: 3px;position: absolute;left:-10px;top: 170px; text-align: center;margin-left: 35px">
    <span>
        <HR width="105%" color=#000000 SIZE=1>
    </span>
</div>
<table width="645px;"    border="0px" class="tabp" style="margin-left:25px;font-size: 0px;font-family:'宋体'">
    <tr>
        <td align="center" style="text-align: center">
            %{--<B>新能源汽车环保信息随车清单</B><br>--}%
            <img src="../images/en_part1_8.png" height="20px"  border="0">
        </td>
    </tr>
    <tr>
        <td colspan="12" scope="col" style="font-family:'宋体';font-size: 14px;text-align: center " >
            信息公开编号：${newEnergyInfo?.en_xxgkbh}
        </td>
    </tr>
    <tr height="1">
        <td colspan="12"></td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;margin-top:50px;font-size: 20px;font-family:'宋体'">
    <tr>
        <td colspan="12" style="font-family:'宋体';font-size: 14px;height: 20px">
                &nbsp;  &nbsp; 浙江飞碟汽车制造有限公司声明：本清单为本企业依据《中华人民共和国大气污染防治法》和生态环境部相关规定公开的机动车环保信息，本企业对本清单所有内容的真实性、准确性、及时性和完整性负责。
            本公司承诺：我公司VIN码（见本页条形码）的电动车符合《汽车加速行驶车外噪声限值及测量方法》（GB 1495-2002）第Ⅱ阶段的要求。
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
        <td width="230px" style="font-family:'宋体';font-size:14px;text-align:  left">${newEnergyInfo?.en_clxh}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:14px;">1</td>
        <td width="250px" style=" font-family:'宋体';font-size:14px;">商&nbsp;&nbsp;&nbsp; 标: </td>
        <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">飞碟</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:14px;">3</td>
        <td width="250px" style=" font-family:'宋体';font-size:14px;">汽车分类： </td>
        <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${newEnergyInfo?.en_qcfl}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:14px;">4</td>
        <td width="250px" style="font-family:'宋体';font-size:14px;">车型的识别方法和位置：</td>
        <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">${newEnergyInfo?.en_cldsbffhwz}</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:14px;">5</td>
        <td width="250px"style=" font-family:'宋体';font-size:14px;">车辆制造商名称：</td>
        <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">浙江飞碟汽车制造有限公司</td>
    </tr>
    <tr height="10">
        <td width="10px" style=" font-family:'宋体';font-size:14px;">6</td>
        <td width="250px"style=" font-family:'宋体';font-size:14px;">生产厂地址：</td>
        <td width="300px" style="font-family:'宋体';font-size:14px;text-align:  left">山东省五莲县潮河镇驻地</td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr>
        <td colspan="12" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第二部分  检验信息--}%
            <img src="../images/en_part4_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td  width="10px" style=" font-family:'宋体';font-size:14px;">7</td>
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
        <td  style=" font-family:'宋体';font-size:14px;">GB 1495-2002</td>
        <td style=" font-family:'宋体';font-size:14px;">${newEnergyInfo?.en_jcbz1}</td>
        <td style=" font-family:'宋体';font-size:14px;">符合</td>
    </tr>
    <tr height="10" >
        <td   style=" font-family:'宋体';font-size:14px;">8</td>
        <td  style=" font-family:'宋体';font-size:14px;">出厂检验项目及结论：</td>
        <td  style=" font-family:'宋体';font-size:14px;">加速行驶车外噪声试验</td>
        <td style=" font-family:'宋体';font-size:14px;">符合</td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="10" >
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">9</td>
        <td   width="500px"style=" font-family:'宋体';font-size:14px;" colspan="4">本车型环保生产一致性保证计划及执行情况，详见本公司官方网站和生态环境部信息公开平台（网址附后）。</td>
    </tr>
    <tr>
        <td colspan="5" scope="col"style="font-family:'宋体';font-size:14px;font-weight: bold">
            %{--第四部分  污染控制技术信息--}%
            <img src="../images/en_part5_1.png"  height="13px"  border="0">
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">10</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">电动机型号/生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${newEnergyInfo?.en_ddjxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">11</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">整车控制器型号/版本号/生产厂：</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${newEnergyInfo?.en_zckzqxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">12</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">储能装置型号/生产厂： </td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">
            ${newEnergyInfo?.en_cnzzxh}
        </td>
    </tr>
    <tr height="10">
        <td   width="10px" style=" font-family:'宋体';font-size:14px;">13</td>
        <td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">电池容量/续航里程： </td>
        <td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">
            ${newEnergyInfo?.en_dcrl}
        </td>
    </tr>
    <tr height="10">
        <td  colspan="11" width="300px"style=" font-family:'宋体';font-size:14px;">
            &nbsp;  &nbsp;  本车辆环保关键零部件（电动机、整车控制器、储能装置）明显标注了永久性标识，
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
            %{--第四部分 制造商/进口企业信息--}%
            <img src="../images/en_part6_2.png"  height="13px"  border="0">
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
            &nbsp;  &nbsp;  本清单内容及相关信息可查询本公司官方网站（正在建设中）和生态环境部机动车和非道路移动机械环保信息公开平台（http://www.vecc.org.cn）。
        </td>
    </tr>
</table>
<table width="720px;"    border="0px" class="tabp" style="margin-left:15px;font-size: 20px;font-family:'宋体'">
    <tr height="260">
        %{--<td   width="10px" style=" font-family:'宋体';font-size:14px;"></td>--}%
        %{--<td  colspan="2" width="200px"style=" font-family:'宋体';font-size:14px;">车辆生产（进口）日期：</td>--}%
        %{--<td  colspan="2" width="300px"style=" font-family:'宋体';font-size:14px;">--}%
        %{--${newEnergyInfo?.en_zzrq}--}%
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
            ${newEnergyInfo?.en_zzrq}
        </td>
    </tr>


</table>
</form>
</div>

</div>
</body>
</html>