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
                    <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器打印时，请确认未勾选“启用收缩到纸张大小”。（文件->页面设置）</div>
                    <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">IE浏览器下请设置上下左右页边距为19.05,19.05,19.05,19.05 </div>
                    <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">Chrome浏览器下请设置上下左右页边距为：19.0,15.0,19.0,19.0 </div>

                </td>
            </tr>
        </table>
        <input type="hidden" id="categoryId" name=categoryId>
    </form>
    <form id="form1" >
        <table width="590px;"   border="0" class="tabp" style="margin-left:50px;margin-top: 120px;font-size: 14px"  >
            <tr>
                <td colspan="4" scope="col">车辆一致性证书编号：${cocPrnInstance?.coc_yzxzsbh}
                </br><hr style="boder:none;border-top:1px solid #000000;" /></td>
            </tr>
            <tr height="20">
                <td width="50" >0.1</td>
                <td width="200">车辆生产厂名称 </td>
                <td width="20">:</td>
                <td width="250">${cocPrnInstance?.coc_clsccmc}</td>
            </tr>
            <tr height="20">
                <td height="20">C0.1</td>
                <td>车辆制造国 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_clzzg}</td>
            </tr>
            <tr height="20">
                <td height="20">0.2</td>
                <td>车型系列代号/名称 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_cxxldhmc}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>单元代号/名称 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_dydhmc}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>车型代号/名称 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_cxdhmc}</td>
            </tr>
            <tr height="20">
                <td height="20">0.2.1</td>
                <td>车型名称 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_cxmc}</td>
            </tr>
            <tr height="20">
                <td height="20">C0.2</td>
                <td>车辆中文品牌 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_clzwpp}</td>
            </tr>
            <tr height="20">
                <td height="20">C0.3</td>
                <td>车辆英文品牌 </td>
                <td>:</td>
                <td>不适用</td>
            </tr>
            <tr height="20">
                <td height="20">0.4</td>
                <td>车辆类别 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_cllb}</td>
            </tr>
            <tr height="20">
                <td height="20">0.5</td>
                <td>最终制造阶段制造商的名称</td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_zzsmc}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>最终制造阶段制造商的地址</td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_zzsdz}</td>
            </tr>
            <tr height="20">
                <td height="20">0.6</td>
                <td>法定铭牌的位置 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_fdmpwz}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>车辆识别代号 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_clsbdh}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>车辆识别号的打刻位置 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_sbhdkwz}</td>
            </tr>
            <tr height="20">
                <td height="20">21</td>
                <td>发动机编号 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_fdjbh}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>发动机编号在发动机上的打刻位置</td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_fdjbhdkwz}</td>
            </tr>
            <tr height="52">
                <td height="52"></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td colspan="3">CCC认证过程中车辆的制造阶段</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>最终阶段</td>
                <td></td>
                <td></td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td colspan="3">在所有方面与本证书第二部分描述的技术参数相符合的完整单阶段制成车辆:</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>CCC证书号（包含版本号）</td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_ccczsh}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>签发日期 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_fzrq}</td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>车辆一致性主管签名 </td>
                <td>:</td>
                <td></td>
            </tr>
            <tr height="20">
                <td height="20"></td>
                <td>日期 </td>
                <td>:</td>
                <td>${cocPrnInstance?.coc_rq}</td>
            </tr>
        </table>
     </form>

    <div class="PageNext"></div>
    <form id="form2" >
        <table  border="0" class="tabp" style="width:650px;font-size: 12px" >
            <th scope="col" style="vertical-align :bottom">第二部分 车辆一致性证书参数</th>

            <tr>
                <td>（以下所示数值和单位是相应CCC认证文件中给出的。对于生产一致性（COP）试验，这些值必须按照相应标准中所描述的方法进行核对，并考虑这些标准中COP试验的允差。）</td>
            </tr>
        </table>
        <table class="tabclass " style="width:620px;margin-left: 10px;size: 8px ;  " >
        <tr  height="15px">
            <td  style="width:24.2%;">1车轴数量</td>
            <td  style="width:25.8%;">${cocPrnInstance?.coc_czsl}</td>
            <td  style="width:32.3%;">车轮数量</td>
            <td  style="width:16.1%;">${cocPrnInstance?.coc_clsl}</td>
        </tr>
        <tr  height="15px">
            <td >2驱动轴位置</td>
            <td>${cocPrnInstance?.coc_qdzwz}</td>
            <td>3轴距(mm)</td>
            <td>${cocPrnInstance?.coc_zj}</td>
        </tr>
        <tr  height="15px">
            <td >3.1牵引座的前置距(最大值(mm)/最小值(mm))</td>
            <td>${cocPrnInstance?.coc_qyzqzj}</td>
            <td>5轮距(mm)</td>
            <td>${cocPrnInstance?.coc_lj}</td>
        </tr>
        <tr  height="15px">
            <td >6.1长度</td>
            <td>${cocPrnInstance?.coc_cd}</td>
            <td>6.3车辆最前端与牵引装置中心之间的距离(mm)</td>
            <td>${cocPrnInstance?.coc_zqdyqyzzjl}</td>
        </tr>
        <tr  height="15px">
            <td >6.5装载区域长度(mm)</td>
            <td>${cocPrnInstance?.coc_zzqycd}</td>
            <td>7.1宽度(mm)</td>
            <td>${cocPrnInstance?.coc_kd}</td>
        </tr>
        <tr  height="15px">
            <td >8高度(mm)</td>
            <td>${cocPrnInstance?.coc_gd}</td>
            <td>10.2车辆在地面上的投影面积(仅适用于N2和N3类车辆)(㎡)</td>
            <td>${cocPrnInstance?.coc_tymj}　</td>
        </tr>
        <tr  height="15px">
            <td >C1前悬(mm)</td>
            <td>${cocPrnInstance?.coc_qx}　</td>
            <td>11后悬(mm)</td>
            <td>${cocPrnInstance?.coc_hx}　</td>
        </tr>
        <tr  height="15px">
            <td >C2接近角(°)</td>
            <td>${cocPrnInstance?.coc_jjj}　</td>
            <td>C3离去角(°)</td>
            <td>${cocPrnInstance?.coc_lqj}　</td>
        </tr>
        <tr  height="15px">
            <td  colspan="1">12.1行驶状态下带车身的车辆质量(kg)</td>
            <td colspan="3">${cocPrnInstance?.coc_xsxdcsclzl}</td>
        </tr>
        <tr  height="15px">
            <td >14.1额定总质量(kg)</td>
            <td>${cocPrnInstance?.coc_edzzl}</td>
            <td>14.2该质量的轴荷分配(kg)</td>
            <td>${cocPrnInstance?.coc_zlzhfp}</td>
        </tr>
        <tr  height="15px">
            <td >C5载质量利用系数</td>
            <td>${cocPrnInstance?.coc_zzllyxsh}</td>
            <td>14.4各车轴或车轴组技术上允许的最大质量(kg)</td>
            <td>${cocPrnInstance?.coc_czyxzdzl}</td>
        </tr>
        <tr  height="15px">
            <td >15可伸缩车轴的位置</td>
            <td>${cocPrnInstance?.coc_kssczwz}</td>
            <td>可承载车轴的位置</td>
            <td>${cocPrnInstance?.coc_kczczwz}</td>
        </tr>
        <tr  height="15px">
            <td >16车辆最大允许载荷(kg)</td>
            <td colspan="3">${cocPrnInstance?.coc_cdzdyxzh}</td>
        </tr>
        <tr  height="15px">
            <td colspan="4" >17下列车辆技术上允许的最大牵引质量</td>
        </tr>
        <tr  height="15px">
            <td >17.1牵引杆式挂车(kg)</td>
            <td>${cocPrnInstance?.coc_qygsgc}</td>
            <td>18.2半挂车(kg)</td>
            <td>${cocPrnInstance?.coc_bgc}</td>
        </tr>
        <tr  height="15px">
            <td >17.3中间轴挂车(kg)</td>
            <td>${cocPrnInstance?.coc_zjjgc}</td>
            <td>17.4挂车的最大质量(非制动下)(kg)</td>
            <td>${cocPrnInstance?.coc_gczdzl}</td>
        </tr>
        <tr  height="15px">
            <td >18牵引车与挂车的最大组合质量(kg)</td>
            <td>${cocPrnInstance?.coc_zdzhzl}</td>
            <td>19.1牵引车与挂车连接点处的最大垂直负荷(kg)</td>
            <td>${cocPrnInstance?.coc_jcdczdczfh}</td>
        </tr>
        <tr  height="15px">
            <td >20发动机制造商名称</td>
            <td>${cocPrnInstance?.coc_fdjzzsmc}　</td>
            <td>C4发动机型号</td>
            <td>${cocPrnInstance?.coc_fdjxh}</td>
        </tr>
        <tr  height="15px">
            <td >22发动机工作原理</td>
            <td>${cocPrnInstance?.coc_fdjgzyl}　</td>
            <td>22.1直接喷射</td>
            <td>${cocPrnInstance?.coc_zjps}</td>
        </tr>
        <tr  height="15px">
            <td >23气缸数量</td>
            <td>${cocPrnInstance?.coc_qgsl}</td>
            <td>气缸排列形式</td>
            <td>${cocPrnInstance?.coc_qgplxs}</td>
        </tr>
        <tr  height="15px">
            <td >24排量(ml)</td>
            <td>${cocPrnInstance?.coc_pl}</td>
            <td>25燃料种类</td>
            <td>${cocPrnInstance?.coc_rlzl}</td>
        </tr>
        <tr  height="15px">
            <td >26最大净功率</td>
            <td>${cocPrnInstance?.coc_zdjgl}</td>
            <td>对应的发动机转速(min-1)</td>
            <td>${cocPrnInstance?.coc_dyfdjzs}</td>
        </tr>
        <tr  height="15px">
            <td >27离合器型式</td>
            <td>${cocPrnInstance?.coc_lhqxs}</td>
            <td>28变速器形式</td>
            <td>${cocPrnInstance?.coc_bsqzs}</td>
        </tr>
        <tr  height="15px">
            <td >29速比</td>
            <td colspan="3" style="word-break:break-all;">${cocPrnInstance?.coc_sb}</td>
        </tr>
        <tr  height="15px">
            <td >30主传动比</td>
            <td colspan="3">${cocPrnInstance?.coc_zcdb}</td>
        </tr>
        <tr  height="15px">
            <td >32轮胎规格</td>
            <td colspan="3">${cocPrnInstance?.coc_ltgg}</td>
        </tr>
        <tr  height="15px">
            <td >33.1驱动轴是否安装有空气悬挂</td>
            <td>${cocPrnInstance?.coc_sfazkqxg}</td>
            <td>驱动轴是否安装有等效于空气悬挂的装置</td>
            <td>${cocPrnInstance?.coc_sfzykqxgzz}</td>
        </tr>
        <tr  height="15px">
            <td >C6钢板弹簧片数</td>
            <td>${cocPrnInstance?.coc_gbthps}</td>
            <td>34转向助力型式</td>
            <td>${cocPrnInstance?.coc_zxzlxs}</td>
        </tr>
        <tr  height="15px">
            <td >35制动装置简要说明</td>
            <td colspan="3">${cocPrnInstance?.coc_zdzzjysm}</td>
        </tr>
        <tr>
            <td >36牵引车中,挂车制动系统供气管内压力(kPa)</td>
            <td>${cocPrnInstance?.coc_zdxtgqgnyl}</td>
            <td>37车身形式</td>
            <td>${cocPrnInstance?.coc_csxs}</td>
        </tr>
        <tr  height="15px">
            <td >38车身颜色</td>
            <td>${cocPrnInstance?.coc_clys}</td>
            <td>39罐体内有效容积(仅适用于罐式车辆)(m3)</td>
            <td>${cocPrnInstance?.coc_gnyxrj}</td>
        </tr>
        <tr  height="15px">
            <td colspan="4" >C7货箱内部尺寸</td>
        </tr>
        <tr  height="15px">
            <td >C7.1长度(mm)</td>
            <td>${cocPrnInstance?.coc_hxcd}</td>
            <td>C7.2宽度(mm)</td>
            <td>${cocPrnInstance?.coc_hxkd}</td>
        </tr>
        <tr  height="15px">
            <td >C7.3高度(mm)</td>
            <td>${cocPrnInstance?.coc_hxgd}</td>
            <td>40起重机的最大力矩能力(kNm)</td>
            <td>${cocPrnInstance?.coc_qzjzdljnl}</td>
        </tr>
        <tr  height="15px">
            <td >41车门数量</td>
            <td>${cocPrnInstance?.coc_cmsl}</td>
            <td>车门构造</td>
            <td>${cocPrnInstance?.coc_cmgz}</td>
        </tr>
        <tr>
            <td >42.1座位数(包括驾驶员座)</td>
            <td>${cocPrnInstance?.coc_zws}</td>
            <td>43.1如装有牵引装置,其CCC证书编号</td>
            <td>${cocPrnInstance?.coc_cccno}</td>
        </tr>
        <tr  height="15px">
            <td >或试验报告编号</td>
            <td>${cocPrnInstance?.coc_sybgno}</td>
            <td>44最高车速</td>
            <td>${cocPrnInstance?.coc_zgcs}</td>
        </tr>
        <tr >
            <td >45声级</td>
            <td colspan="3">${cocPrnInstance?.coc_sj}</td>
        </tr>
        <tr height="40">
            <td >46.1排气排放物</td>
            <td colspan="3">${cocPrnInstance?.coc_pqpfw}</td>
        </tr>
        <tr >
            <td >48.1按照运输危险货物的结构要求的试验报告编号</td>
            <td>${cocPrnInstance?.coc_jgyqsybg}</td>
            <td>级别</td>
            <td>${cocPrnInstance?.coc_wxhwysjb}</td>
        </tr>
        <tr  height="15px">
            <td >48.2按照运输某些动物的结构要求的试验报告编号</td>
            <td>${cocPrnInstance?.coc_dwjgyqbh}</td>
            <td>级别</td>
            <td>${cocPrnInstance?.coc_mxdwysjb}</td>
        </tr>
        <tr  >
            <td >50备注</td>
            <td colspan="3">${cocPrnInstance?.coc_note}</td>
        </tr>
        </table>
    </form>


</div>

</div>


</body>
</html>