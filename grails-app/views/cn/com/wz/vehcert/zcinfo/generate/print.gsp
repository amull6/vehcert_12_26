<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="合格证打印到A4" />
    <title>合格证打印</title>
</head>
<body>
<div id="page" style="width:740px;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">


<table width="90%"  border="1" cellspacing='0' cellpadding='0'  style="font-size: 10px;">
<tr height="17">
    <td height="17" width='25%'>1合格证编号</td>
    <td  width='25%'>${zcinfoInstance?.veh_Zchgzbh}</td>
    <td  width='25%'>2发证日期</td>
    <td  width='25%'>${zcinfoInstance?.veh_Fzrq}</td>
</tr>
<tr height="17">
    <td height="19">3车辆制造企业名称</td>
    <td colspan="3">${zcinfoInstance?.veh_Clzzqymc}</td>
</tr>
<tr height="17">
    <td height="17">4车辆品牌/车辆名称</td>
    <td>${zcinfoInstance?.veh_Clpp}</td>
    <td colspan="2">${zcinfoInstance?.veh_Clmc}</td>
</tr>
<tr height="17">
    <td height="17">5车辆型号</td>
    <td>${zcinfoInstance?.veh_Clxh}</td>
    <td>6车辆识别代号</td>
    <td>${zcinfoInstance?.veh_Clsbdh}</td>
</tr>
<tr height="18">
    <td height="18">7车身颜色</td>
    <td colspan="3">${zcinfoInstance?.veh_Csys}</td>
</tr>
<tr height="18">
    <td height="18">8底盘型号/底盘ID</td>
    <td colspan="2">${zcinfoInstance?.veh_Dpxh}</td>
    <td>${zcinfoInstance?.veh_Dpid}</td>
</tr>
<tr height="17">
    <td height="17">9底盘合格证编号</td>
    <td>${zcinfoInstance?.veh_Dphgzbh}</td>
    <td>10发动机型号</td>
    <td>${zcinfoInstance?.veh_Fdjxh}　</td>
</tr>
<tr height="17">
    <td height="19">11发动机号</td>
    <td colspan="3">${zcinfoInstance?.veh_Fdjh}</td>
</tr>
<tr height="17">
    <td height="17">燃料种类</td>
    <td>${zcinfoInstance?.veh_Rlzl}　</td>
    <td>排量/功率(ml/kw)</td>
    <td>${zcinfoInstance?.veh_Gl}/　</td>
</tr>
<tr height="17">
     <td height="19">排放标准</td>
     <td colspan="3">${zcinfoInstance?.veh_Pfbz}</td>
</tr>
    <tr height="17">
        <td height="19">油耗</td>
        <td colspan="3">${zcinfoInstance?.veh_Yh}</td>
    </tr>
    <tr height="17">
        <td height="17">外廓尺寸mm(长/宽/高)</td>
        <td>${zcinfoInstance?.veh_Wkc}/${zcinfoInstance?.veh_Wkk}/${zcinfoInstance?.veh_Wkg}　</td>
        <td>货箱内部尺寸mm(长/宽/高)</td>
        <td>${zcinfoInstance?.veh_Hxnbc}/${zcinfoInstance?.veh_Hxnbk}/${zcinfoInstance?.veh_Hxnbg}　</td>
    </tr>
    <tr height="17">
        <td height="17">钢板弹簧片数(片)</td>
        <td>${zcinfoInstance?.veh_Gbthps}　</td>
        <td>轮胎数</td>
        <td>${zcinfoInstance?.veh_Lts}/　</td>
    </tr>
    <tr height="17">
        <td height="17">轮胎规格</td>
        <td colspan="3">${zcinfoInstance?.veh_Ltgg}　</td>
    </tr>
    <tr height="17">
        <td height="17">轮距(前/后)(mm)</td>
        <td>${zcinfoInstance?.veh_Qlj}　</td>
        <td colspan="2">${zcinfoInstance?.veh_Hlj}　</td>
    </tr>
    <tr height="17">
        <td height="19">轴距(mm)</td>
        <td colspan="3">${zcinfoInstance?.veh_Zj}</td>
    </tr>
    <tr height="17">
        <td height="19">轴荷(kg)</td>
        <td colspan="3">${zcinfoInstance?.veh_Zh}</td>
    </tr>
    <tr height="17">
        <td height="17">轴数</td>
        <td>${zcinfoInstance?.veh_Zs}　</td>
        <td>转向形式</td>
        <td>${zcinfoInstance?.veh_Zxxs}</td>
    </tr>
    <tr height="17">
        <td height="17">总质量(kg)</td>
        <td>${zcinfoInstance?.veh_Zzl}　</td>
        <td>整备质量(kg)</td>
        <td>${zcinfoInstance?.veh_Zbzl}</td>
    </tr>
    <tr height="17">
        <td height="17">额定载质量(kg)</td>
        <td>${zcinfoInstance?.veh_Edzzl}</td>
        <td>载质量利用系数</td>
        <td>${zcinfoInstance?.veh_Zzllyxs}</td>
    </tr>
    <tr height="17">
        <td height="17">准牵引总质量(kg)</td>
        <td>${zcinfoInstance?.veh_Zqyzzl}</td>
        <td>半挂车鞍座最大允许总质量</td>
        <td>${zcinfoInstance?.veh_Bgcazzdyxzzl}</td>
    </tr>
    <tr height="17">
        <td height="17">驾驶室准乘人数(人)</td>
        <td>${zcinfoInstance?.veh_Jsszcrs}</td>
        <td>额定载客(人)</td>
        <td>${zcinfoInstance?.veh_Edzk}</td>
    </tr>
    <tr height="17">
        <td height="17">最高车速(km/h)</td>
        <td>${zcinfoInstance?.veh_Zgcs}</td>
        <td>车辆制造日期</td>
        <td>${zcinfoInstance?.veh_Clzzrq}</td>
    </tr>
    <tr height="60">
    <td height="100">备注</td>
    <td colspan="3">${zcinfoInstance?.veh_Bz}</td>
    </tr>
    <tr height="150">
        <td height="150" colspan="4">
             车辆制造企业信息：${zcinfoInstance?.veh_clzzqyxx}   </br>
            本品经过检验，符合${zcinfoInstance?.veh_Qybz}的要求，准予出厂，特此证明。  </br>
            车辆生产单位名称：${zcinfoInstance?.veh_Clzzqymc}                       </br>
            车辆生产单位地址：    ${zcinfoInstance?.veh_Cpscdz}                    </br>
            车辆制造企业其它信息：                      </br>   ${zcinfoInstance?.veh_Qyqtxx}
        </td>
    </tr>
</table>
</div>

</div>


</body>
</html>