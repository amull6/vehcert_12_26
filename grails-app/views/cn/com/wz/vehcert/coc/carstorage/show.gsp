<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="一致性证书信息" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div id="show-Package" class="content scaffold-show" role="main">
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<div id="page" style="width:600px;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">

<div style="margin-right:8px;margin-top:8px; height:100%;">

<div style="height:100%;" class="om-grid om-widget om-widget-content">

<div class="bDiv" style="width: auto; height: 100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >一致性证书信息</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车轴数量</div></td>
    <td align="left" abbr="city" class="col1"><div  align="left" class="${hasErrors(bean: cocCarStorageInstance, field: 'coc_czsl', 'error')} required" >${cocCarStorageInstance?.coc_czsl}</div></td>
    <td align="left"><div style="text-align: left; ">车轮数量</div></td>
    <td align="left" abbr="city" class="col1"><div  align="left" class="${hasErrors(bean: cocCarStorageInstance, field: 'coc_clsl', 'error')} required" >${cocCarStorageInstance?.coc_clsl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">驱动轴位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qdzwz', 'error')} required" >${cocCarStorageInstance?.coc_qdzwz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">轴距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zj', 'error')} required" >${cocCarStorageInstance?.coc_zj}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">牵引座前置距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qyzqzj', 'error')} required" >${cocCarStorageInstance?.coc_qyzqzj}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">轮距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_lj', 'error')} required" >${cocCarStorageInstance?.coc_lj}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">长度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cd', 'error')} required" >${cocCarStorageInstance?.coc_cd}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">最前端与牵引装置的距离</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zqdyqyzzjl', 'error')} required" >${cocCarStorageInstance?.coc_zqdyqyzzjl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">装载区域长度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzqycd', 'error')} required" >${cocCarStorageInstance?.coc_zzqycd}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆在地面上的投影面积</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_tymj', 'error')} required" >${cocCarStorageInstance?.coc_tymj}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">宽度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_kd', 'error')} required" >${cocCarStorageInstance?.coc_kd}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">高度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_gd', 'error')} required" >${cocCarStorageInstance?.coc_gd}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">前悬</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qx', 'error')} required" >${cocCarStorageInstance?.coc_qx}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">后悬</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_hx', 'error')} required" >${cocCarStorageInstance?.coc_hx}</div></td>
</tr><tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">接近角</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jjj', 'error')} required" >${cocCarStorageInstance?.coc_jjj}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">离去角</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_lqj', 'error')} required" >${cocCarStorageInstance?.coc_lqj}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">行驶下带车身的车辆质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_xsxdcsclzl', 'error')} required" >${cocCarStorageInstance?.coc_xsxdcsclzl}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">额定总质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_edzzl', 'error')} required" >${cocCarStorageInstance?.coc_edzzl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">该质量的轴荷分配</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zlzhfp', 'error')} required" >${cocCarStorageInstance?.coc_zlzhfp}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzllyxsh', 'error')} required" >${cocCarStorageInstance?.coc_zzllyxsh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车轴允许的最大质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_czyxzdzl', 'error')} required" >${cocCarStorageInstance?.coc_czyxzdzl}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">可伸缩车轴的位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_kssczwz', 'error')} required" >${cocCarStorageInstance?.coc_kssczwz}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">可承载车轴位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_kczczwz', 'error')} required" >${cocCarStorageInstance?.coc_kczczwz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车顶最大允许载荷</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cdzdyxzh', 'error')} required" >${cocCarStorageInstance?.coc_cdzdyxzh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">牵引杆式挂车</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qygsgc', 'error')} required" >${cocCarStorageInstance?.coc_qygsgc}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">半挂车</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_bgc', 'error')} required" >${cocCarStorageInstance?.coc_bgc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">中间轴挂车</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zjjgc', 'error')} required" >${cocCarStorageInstance?.coc_zjjgc}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">挂车的最大质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_gczdzl', 'error')} required" >${cocCarStorageInstance?.coc_gczdzl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">最大组合质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zdzhzl', 'error')} required" >${cocCarStorageInstance?.coc_zdzhzl}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">接触点处的最大垂直负荷</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jcdczdczfh', 'error')} required" >${cocCarStorageInstance?.coc_jcdczdczfh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">直接喷射</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zjps', 'error')} required" >${cocCarStorageInstance?.coc_zjps}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">气缸数量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qgsl', 'error')} required" >${cocCarStorageInstance?.coc_qgsl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">气缸排列形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qgplxs', 'error')} required" >${cocCarStorageInstance?.coc_qgplxs}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">燃料种类</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_rlzl', 'error')} required" >${cocCarStorageInstance?.coc_rlzl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">最大净功率</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zdjgl', 'error')} required" >${cocCarStorageInstance?.coc_zdjgl}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">对应的发动机转速</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dyfdjzs', 'error')} required" >${cocCarStorageInstance?.coc_dyfdjzs}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">离合器形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_lhqxs', 'error')} required" >${cocCarStorageInstance?.coc_lhqxs}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">变速器形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_bsqzs', 'error')} required" >${cocCarStorageInstance?.coc_bsqzs}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">速比</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sb', 'error')} required" >${cocCarStorageInstance?.coc_sb}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">主传动比</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zcdb', 'error')} required" >${cocCarStorageInstance?.coc_zcdb}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">轮胎规格</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ltgg', 'error')} required" >${cocCarStorageInstance?.coc_ltgg}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">是否安装有空气悬挂</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sfazkqxg', 'error')} required" >${cocCarStorageInstance?.coc_sfazkqxg}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">钢板弹簧的片数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_gbthps', 'error')} required" >${cocCarStorageInstance?.coc_gbthps}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">转向助力形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zxzlxs', 'error')} required" >${cocCarStorageInstance?.coc_zxzlxs}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">制动装置简要说明</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zdzzjysm', 'error')} required" >${cocCarStorageInstance?.coc_zdzzjysm}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">制动系统供气管内压力</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zdxtgqgnyl', 'error')} required" >${cocCarStorageInstance?.coc_zdxtgqgnyl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车身形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_csxs', 'error')} required" >${cocCarStorageInstance?.coc_csxs}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆颜色</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clys', 'error')} required" >${cocCarStorageInstance?.coc_clys}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">罐体内有效容积</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_gnyxrj', 'error')} required" >${cocCarStorageInstance?.coc_gnyxrj}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱长度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_hxcd', 'error')} required" >${cocCarStorageInstance?.coc_hxcd}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱高度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_hxgd', 'error')} required" >${cocCarStorageInstance?.coc_hxgd}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱宽度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_hxkd', 'error')} required" >${cocCarStorageInstance?.coc_hxkd}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">是否装有空气悬挂的装置</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sfzykqxgzz', 'error')} required" >${cocCarStorageInstance?.coc_sfzykqxgzz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">起重机的最大力矩能力</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qzjzdljnl', 'error')} required" >${cocCarStorageInstance?.coc_qzjzdljnl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车门数量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cmsl', 'error')} required" >${cocCarStorageInstance?.coc_cmsl}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车门构造</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cmgz', 'error')} required" >${cocCarStorageInstance?.coc_cmgz}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">座位数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zws', 'error')} required" >${cocCarStorageInstance?.coc_zws}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">CCC证书编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cccno', 'error')} required" >${cocCarStorageInstance?.coc_cccno}</div></td>
</tr>




<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">或实验报告编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sybgno', 'error')} required" >${cocCarStorageInstance?.coc_sybgno}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">最高车速</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zgcs', 'error')} required" >${cocCarStorageInstance?.coc_zgcs}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">声级</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sj', 'error')} required" >${cocCarStorageInstance?.coc_sj}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">排气排放物</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_pqpfw', 'error')} required" >${cocCarStorageInstance?.coc_pqpfw}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">CO2排放</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_co2', 'error')} required" >${cocCarStorageInstance?.coc_co2}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">结构要求的试验报告</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jgyqsybg', 'error')} required" >${cocCarStorageInstance?.coc_jgyqsybg}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">危险货物运输的级别</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_wxhwysjb', 'error')} required" >${cocCarStorageInstance?.coc_wxhwysjb}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">动物的结构要求的编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dwjgyqbh', 'error')} required" >${cocCarStorageInstance?.coc_dwjgyqbh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">某些动物运输的级别</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_mxdwysjb', 'error')} required" >${cocCarStorageInstance?.coc_mxdwysjb}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">备注</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_note', 'error')} required" >${cocCarStorageInstance?.coc_note}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">证书编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zsno', 'error')} required" >${cocCarStorageInstance?.coc_zsno}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆生产厂名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clsccmc', 'error')} required" >${cocCarStorageInstance?.coc_clsccmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆制造国</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clzzg', 'error')} required" >${cocCarStorageInstance?.coc_clzzg}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车型系列代号名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cxxldhmc', 'error')} required" >${cocCarStorageInstance?.coc_cxxldhmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">单元代号名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dydhmc', 'error')} required" >${cocCarStorageInstance?.coc_dydhmc}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车型代号名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cxdhmc', 'error')} required" >${cocCarStorageInstance?.coc_cxdhmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车型名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cxmc', 'error')} required" >${cocCarStorageInstance?.coc_cxmc}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆中文品牌</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clzwpp', 'error')} required" >${cocCarStorageInstance?.coc_clzwpp}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆类别</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cllb', 'error')} required" >${cocCarStorageInstance?.coc_cllb}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">制造商的名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzsmc', 'error')} required" >${cocCarStorageInstance?.coc_zzsmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">制造商的地址</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzsdz', 'error')} required" >${cocCarStorageInstance?.coc_zzsdz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机制造商名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjzzsmc', 'error')} required" >${cocCarStorageInstance?.coc_fdjzzsmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">法定名牌的位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdmpwz', 'error')} required" >${cocCarStorageInstance?.coc_fdmpwz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆识别代号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clsbdh', 'error')} required" >${cocCarStorageInstance?.coc_clsbdh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">识别号的打刻位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sbhdkwz', 'error')} required" >${cocCarStorageInstance?.coc_sbhdkwz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjbh', 'error')} required" >${cocCarStorageInstance?.coc_fdjbh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机编号的打刻位置</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjbhdkwz', 'error')} required" >${cocCarStorageInstance?.coc_fdjbhdkwz}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">CCC证书号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ccczsh', 'error')} required" >${cocCarStorageInstance?.coc_ccczsh}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clxh', 'error')} required" >${cocCarStorageInstance?.coc_clxh}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clmc', 'error')} required" >${cocCarStorageInstance?.coc_clmc}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">日期</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_rq', 'error')} required" >${cocCarStorageInstance?.coc_rq}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">排量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_pl', 'error')} required" >${cocCarStorageInstance?.coc_pl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjxh', 'error')} required" >${cocCarStorageInstance?.coc_fdjxh}</div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机工作原理</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjgzyl', 'error')} required" >${cocCarStorageInstance?.coc_fdjgzyl}</div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">是否新能源车型</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_new_energy', 'error')} required" ><g:textField name="coc_new_energy" id="coc_new_energy" maxlength="50"  value="${cocCarStorageInstance?.coc_new_energy}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">是否带防抱死系统</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_abs', 'error')} required" ><g:textField name="coc_abs" id="coc_abs" maxlength="50"  value="${cocCarStorageInstance?.coc_abs}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">生产企业地址</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_scqydz', 'error')} required" ><g:textField name="coc_scqydz" id="coc_scqydz" maxlength="50"  value="${cocCarStorageInstance?.coc_scqydz}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆种类</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clzl', 'error')} required" ><g:textField name="coc_clzl" id="coc_clzl" maxlength="50"  value="${cocCarStorageInstance?.coc_clzl}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">基础车辆一致性证书编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jccccno', 'error')} required" ><g:textField name="coc_jccccno" id="coc_jccccno" maxlength="50"  value="${cocCarStorageInstance?.coc_jccccno}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">基础车辆型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jcclxh', 'error')} required" ><g:textField name="coc_jcclxh" id="coc_jcclxh" maxlength="50"  value="${cocCarStorageInstance?.coc_jcclxh}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">基础车辆类别</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_jccllb', 'error')} required" ><g:textField name="coc_jccllb" id="coc_jccllb" maxlength="50"  value="${cocCarStorageInstance?.coc_jccllb}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">最终阶段车辆CCC证书编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzjdcccno', 'error')} required" ><g:textField name="coc_zzjdcccno" id="coc_zzjdcccno" maxlength="50"  value="${cocCarStorageInstance?.coc_zzjdcccno}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">最终阶段车辆CCC证书编号签发日期</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zzjdqfrq', 'error')} required" ><g:textField name="coc_zzjdqfrq" id="coc_zzjdqfrq" maxlength="50"  value="${cocCarStorageInstance?.coc_zzjdqfrq}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">最大允许总质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_zdyxzzl', 'error')} required" ><g:textField name="coc_zdyxzzl" id="coc_zdyxzzl" maxlength="50"  value="${cocCarStorageInstance?.coc_zdyxzzl}"/></div></td>
</tr>

<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">CCC认证标准号及对应的实施阶段</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_sjbzh', 'error')} required" ><g:textField name="coc_sjbzh" id="coc_sjbzh" maxlength="50"  value="${cocCarStorageInstance?.coc_sjbzh}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">定置噪声</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dzzs', 'error')} required" ><g:textField name="coc_dzzs" id="coc_dzzs" maxlength="50"  value="${cocCarStorageInstance?.coc_dzzs}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">对应的发动机转速</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_fdjzs', 'error')} required" ><g:textField name="coc_fdjzs" id="coc_fdjzs" maxlength="50"  value="${cocCarStorageInstance?.coc_fdjzs}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">排气排放物标准号及对应的实施阶段</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_pfwbzh', 'error')} required" ><g:textField name="coc_pfwbzh" id="coc_pfwbzh" maxlength="50"  value="${cocCarStorageInstance?.coc_pfwbzh}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">实验用液体燃料</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl', 'error')} required" ><g:textField name="coc_ytrl" id="coc_ytrl" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">液体燃料CO排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_co', 'error')} required" ><g:textField name="coc_ytrl_co" id="coc_ytrl_co" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_co}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">液体燃料NOX排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_nox', 'error')} required" ><g:textField name="coc_ytrl_nox" id="coc_ytrl_nox" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_nox}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">液体燃料烟度</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_yd', 'error')} required" ><g:textField name="coc_ytrl_yd" id="coc_ytrl_yd" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_yd}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">实验用气体燃料</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl', 'error')} required" ><g:textField name="coc_qtrl" id="coc_qtrl" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">加速行驶车外噪音</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_cwzs', 'error')} required" ><g:textField name="coc_cwzs" id="coc_cwzs" maxlength="50"  value="${cocCarStorageInstance?.coc_cwzs}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">气体燃料CO排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_co', 'error')} required" ><g:textField name="coc_qtrl_co" id="coc_qtrl_co" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_co}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">气体燃料NMHC排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_nmhc', 'error')} required" ><g:textField name="coc_qtrl_nmhc" id="coc_qtrl_nmhc" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_nmhc}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">气体燃料CH4排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_ch4', 'error')} required" ><g:textField name="coc_qtrl_ch4" id="coc_qtrl_ch4" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_ch4}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">CO2排放量/燃料消耗量CCC认证引用的标准号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_co2pflbzh', 'error')} required" ><g:textField name="coc_co2pflbzh" id="coc_co2pflbzh" maxlength="50"  value="${cocCarStorageInstance?.coc_co2pflbzh}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">市区CO2排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_co2_sq', 'error')} required" ><g:textField name="coc_co2_sq" id="coc_co2_sq" maxlength="50"  value="${cocCarStorageInstance?.coc_co2_sq}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">市郊CO2排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_co2_sj', 'error')} required" ><g:textField name="coc_co2_sj" id="coc_co2_sj" maxlength="50"  value="${cocCarStorageInstance?.coc_co2_sj}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">综合CO2排放量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_co2_zh', 'error')} required" ><g:textField name="coc_co2_zh" id="coc_co2_zh" maxlength="50"  value="${cocCarStorageInstance?.coc_co2_zh}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">市区燃料消耗量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_rlxh_sq', 'error')} required" ><g:textField name="coc_rlxh_sq" id="coc_rlxh_sq" maxlength="50"  value="${cocCarStorageInstance?.coc_rlxh_sq}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">市郊燃料消耗量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_rlxh_sj', 'error')} required" ><g:textField name="coc_rlxh_sj" id="coc_rlxh_sj" maxlength="50"  value="${cocCarStorageInstance?.coc_rlxh_sj}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">综合燃料消耗量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_rlxh_zh', 'error')} required" ><g:textField name="coc_rlxh_zh" id="coc_rlxh_zh" maxlength="50"  value="${cocCarStorageInstance?.coc_rlxh_zh}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用液体燃料HC</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_hc', 'error')} required" ><g:textField name="coc_ytrl_hc" id="coc_ytrl_hc" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_hc}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用液体燃料HC+NOx</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_hcnox', 'error')} required" ><g:textField name="coc_ytrl_hcnox" id="coc_ytrl_hcnox" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_hcnox}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用液体燃料微粒物/PN</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ytrl_pn', 'error')} required" ><g:textField name="coc_ytrl_pn" id="coc_ytrl_pn" maxlength="50"  value="${cocCarStorageInstance?.coc_ytrl_pn}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用气体燃料NOx</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_nox', 'error')} required" ><g:textField name="coc_qtrl_nox" id="coc_qtrl_nox" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_nox}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用气体燃料THC</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_thc', 'error')} required" ><g:textField name="coc_qtrl_thc" id="coc_qtrl_thc" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_thc}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">试验用气体燃料PN</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_qtrl_pn', 'error')} required" ><g:textField name="coc_qtrl_pn" id="coc_qtrl_pn" maxlength="50"  value="${cocCarStorageInstance?.coc_qtrl_pn}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">电动机工作电压</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_ddjgzdy', 'error')} required" ><g:textField name="coc_ddjgzdy" id="coc_ddjgzdy" maxlength="50"  value="${cocCarStorageInstance?.coc_ddjgzdy}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">动力电池型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dldcxh', 'error')} required" ><g:textField name="coc_dldcxh" id="coc_dldcxh" maxlength="50"  value="${cocCarStorageInstance?.coc_dldcxh}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">动力电池额定电压</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dldceddy', 'error')} required" ><g:textField name="coc_dldceddy" id="coc_dldceddy" maxlength="50"  value="${cocCarStorageInstance?.coc_dldceddy}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">动力电池额定容量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dldcedrl', 'error')} required" ><g:textField name="coc_dldcedrl" id="coc_dldcedrl" maxlength="50"  value="${cocCarStorageInstance?.coc_dldcedrl}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">动力电池生产厂名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_dldcsccmc', 'error')} required" ><g:textField name="coc_dldcsccmc" id="coc_dldcsccmc" maxlength="50"  value="${cocCarStorageInstance?.coc_dldcsccmc}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable"></div>车辆注册类型</td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_clzclx', 'error')} required" ><g:textField name="coc_clzclx" id="coc_clzclx" maxlength="50"  value="${cocCarStorageInstance?.coc_clzclx}"/></div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">一致性证书编号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class=" ${hasErrors(bean: cocCarStorageInstance, field: 'coc_yzxzsbh', 'error')} required" ><g:textField name="coc_yzxzsbh" id="coc_yzxzsbh" maxlength="50"  value="${cocCarStorageInstance?.coc_yzxzsbh}"/></div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable"></div></td>
    <td align="left" abbr="city" class="col1"><div align="left"></div></td>
</tr>

</tbody>
</table>
</div>
</div>

</div>
</div>

</div>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${cocCarStorageInstance?.id}" />
        <g:link class="edit" action="edit" id="${cocCarStorageInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:link controller="cocCarStorage" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
    </fieldset>
</g:form>
</div>
</body>
</html>
