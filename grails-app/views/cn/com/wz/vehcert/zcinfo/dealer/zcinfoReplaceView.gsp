<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="合格证更换申请" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title>合格证更换申请详情页面</title>
</head>
<body>
<form  id="formaudit">
<g:hiddenField name="veh_Zchgzbh" id="veh_Zchgzbh" value="${zcinfoReAuInstance?.veh_Zchgzbh_R}" />
<g:hiddenField name="veh_Need_Cer" id="veh_Need_Cer" value="${zcinfoReAuInstance?.veh_Need_Cer}" />
<div id="page" style="width:100%;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">


<div style="margin-right:8px;margin-top:8px; height:100%;  width:100%;">

<div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


<div class="bDiv" style="width: auto; height: 100%; width:100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <td align="center" class="indexCol" colspan="17"><div align="center" class="innerCol " >
        <g:if test="${zcinfoReAuInstance?.change_Field =='veh_Fzrq'}">合格证日期变更查看</g:if>
        <g:if test="${zcinfoReAuInstance?.change_Field !='veh_Fzrq'}">合格证基础变更查看</g:if>
        <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 0}">
            (<font color='blue'>只要整车合格证 </font>)
        </g:if>
        <g:elseif test="${zcinfoReAuInstance?.veh_Need_Cer == 1}">
            (<font color='blue'>只要底盘合格证</font>)
        </g:elseif>
        <g:elseif test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">
            (<font color='blue'>要整车合格证和底盘合格证</font>)
        </g:elseif>
        <g:else>
            (<font color='red'>参数错误</font>)
        </g:else>
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商：<c:getUserName userId='${zcinfoReAuInstance.createrId }'></c:getUserName>， 业务员：${zcinfoReAuInstance?.salesmanname}，  业务员电话：${zcinfoReAuInstance?.salesmantel}。
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 0}">只要整车合格证</g:if>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 1}">只要底盘合格证</g:if>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">整车和底盘合格证</g:if>
    </div></td></tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商备注：${zcinfoReAuInstance?.remark}
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px" >1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zchgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px" >
        2、发证日期
    </div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px" <g:if test="${zcinfoReAuInstance?.veh_Zchgzbh != zcinfoReAuInstance?.veh_Zchgzbh_R }" >  style="color: red" </g:if>>1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zchgzbh != zcinfoReAuInstance?.veh_Zchgzbh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zchgzbh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">
        <g:if test="${zcinfoReAuInstance?.change_Field =='veh_Fzrq'}"> <font color='red' >2、发证日期</font></g:if>
        <g:if test="${zcinfoReAuInstance?.change_Field !='veh_Fzrq'}"> <span <g:if test="${zcinfoReAuInstance?.veh_Fzrq != zcinfoReAuInstance?.veh_Fzrq_R }" >  style="color: red" </g:if>>发证日期</span></g:if>
    </div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fzrq != zcinfoReAuInstance?.veh_Fzrq_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Fzrq_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left"  class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Clzzqymc_R_Div" class="innerCol" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>    ${zcinfoReAuInstance?.veh_Clzzqymc_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clpp}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clmc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Clpp_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clpp != zcinfoReAuInstance?.veh_Clpp_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clpp_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Clmc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clmc != zcinfoReAuInstance?.veh_Clmc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clmc_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clxh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clsbdh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Clxh_R" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clxh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clsbdh != zcinfoReAuInstance?.veh_Clsbdh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clsbdh_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Csys}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != zcinfoReAuInstance?.veh_Csys_R }" >  style="color: red" </g:if>>7.车身颜色</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Csys_R_Div" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != zcinfoReAuInstance?.veh_Csys_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Csys_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dpxh}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dpid}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Dpxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpxh != zcinfoReAuInstance?.veh_Dpxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Dpxh_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Dpid_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpid != zcinfoReAuInstance?.veh_Dpid_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Dpid_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dphgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Fdjxh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dphgzbh != zcinfoReAuInstance?.veh_Dphgzbh_R }" >  style="color: red" </g:if>>9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dphgzbh != zcinfoReAuInstance?.veh_Dphgzbh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Dphgzbh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Fdjxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Fdjxh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjh != zcinfoReAuInstance?.veh_Fdjh_R }" >  style="color: red" </g:if>>11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjh != zcinfoReAuInstance?.veh_Fdjh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Fdjh_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Rlzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Pl}</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Gl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != zcinfoReAuInstance?.veh_Rlzl_R }" >  style="color: red" </g:if>>12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Rlzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != zcinfoReAuInstance?.veh_Rlzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Rlzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" id="veh_Pl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Pl != zcinfoReAuInstance?.veh_Pl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Pl_R}</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" id="veh_Gl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gl != zcinfoReAuInstance?.veh_Gl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Gl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Pfbz}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != zcinfoReAuInstance?.veh_Pfbz_R }" >  style="color: red" </g:if>>14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Pfbz_R_Div" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != zcinfoReAuInstance?.veh_Pfbz_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Pfbz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Yh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Yh != zcinfoReAuInstance?.veh_Yh_R }" >  style="color: red" </g:if>>15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Yh_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != zcinfoReAuInstance?.veh_Yh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Yh_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Wkc}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Wkk}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Wkg}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Hxnbc}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Hxnbk}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:30px">${zcinfoReAuInstance?.veh_Hxnbg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" id="veh_Wkc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkc != zcinfoReAuInstance?.veh_Wkc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Wkc_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" id="veh_Wkk_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkk != zcinfoReAuInstance?.veh_Wkk_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Wkk_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" id="veh_Wkg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkg != zcinfoReAuInstance?.veh_Wkg_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Wkg_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="veh_Hxnbc_R_Div"  <g:if test="${zcinfoReAuInstance?.veh_Hxnbc != zcinfoReAuInstance?.veh_Hxnbc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Hxnbc_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="veh_Hxnbk_R_Div"<g:if test="${zcinfoReAuInstance?.veh_Hxnbk != zcinfoReAuInstance?.veh_Hxnbk_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Hxnbk_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="veh_Hxnbg_R_Div" <g:if test="${zcinfoReAuInstance?.veh_Hxnbg != zcinfoReAuInstance?.veh_Hxnbg_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Hxnbg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Gbthps}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Lts}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gbthps != zcinfoReAuInstance?.veh_Gbthps_R }" >  style="color: red" </g:if>>18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Gbthps_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Gbthps != zcinfoReAuInstance?.veh_Gbthps_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Gbthps_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != zcinfoReAuInstance?.veh_Lts_R }" >  style="color: red" </g:if>>19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Lts_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != zcinfoReAuInstance?.veh_Lts_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Lts_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Ltgg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != zcinfoReAuInstance?.veh_Ltgg_R }" >  style="color: red" </g:if>>20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Ltgg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != zcinfoReAuInstance?.veh_Ltgg_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ltgg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Qlj}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Hlj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Qlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Qlj != zcinfoReAuInstance?.veh_Qlj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Qlj_R}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" id="veh_Hlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Hlj != zcinfoReAuInstance?.veh_Hlj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Hlj_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != zcinfoReAuInstance?.veh_Zj_R }" >  style="color: red" </g:if>>22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Zj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != zcinfoReAuInstance?.veh_Zj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zj_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != zcinfoReAuInstance?.veh_Zh_R }" >  style="color: red" </g:if>>23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Zh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != zcinfoReAuInstance?.veh_Zh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zs}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Zxxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zs_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zxxs != zcinfoReAuInstance?.veh_Zxxs_R }" >  style="color: red" </g:if>>25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Zxxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zxxs != zcinfoReAuInstance?.veh_Zxxs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zxxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Zbzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != zcinfoReAuInstance?.veh_Zzl_R }" >  style="color: red" </g:if>>26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != zcinfoReAuInstance?.veh_Zzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != zcinfoReAuInstance?.veh_Zbzl_R }" >  style="color: red" </g:if>>27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Zbzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != zcinfoReAuInstance?.veh_Zbzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zbzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Edzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Zzllyxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Edzzl != zcinfoReAuInstance?.veh_Edzzl_R }" >  style="color: red" </g:if>>28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Edzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzzl != zcinfoReAuInstance?.veh_Edzzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Edzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != zcinfoReAuInstance?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Zzllyxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != zcinfoReAuInstance?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zzllyxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zqyzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Bgcazzdyxzzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zqyzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zqyzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if>>31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Bgcazzdyxzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if> >${zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Jsszcrs}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != zcinfoReAuInstance?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Jsszcrs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != zcinfoReAuInstance?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Jsszcrs_R}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Edzk_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Edzk_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zgcs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zgcs_R}</div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clzzrq != zcinfoReAuInstance?.veh_Clzzrq_R }" >  style="color: red" </g:if>>35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clzzrq != zcinfoReAuInstance?.veh_Clzzrq_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clzzrq_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">备注：${zcinfoReAuInstance?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4">备注：<div align="left" id="veh_Bz_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bz != zcinfoReAuInstance?.veh_Bz_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Bz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：${zcinfoReAuInstance?.veh_clzzqyxx}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8" <g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != zcinfoReAuInstance?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>车辆制造信息：<div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != zcinfoReAuInstance?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_clzzqyxx_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="17"><div align="left" class="innerCol ">审核备注：
    <g:textArea name="auth_Remark" id="auth_Remark" cols="40" rows="3" maxlength="500" style="width:300px;height:100px;" value="${zcinfoReAuInstance?.auth_Remark}"/>
    </div></td>
</tr>

<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol ">
        %{--只有审核通过才可以下载--}%
        <g:if test="${zcinfoReAuInstance?.veh_coc_status==1 && searchType=='0'}">
          <a id="btn_dy" class="btn_basic blue_black" href="javascript:void(0)" onclick="javascript:download_zcinf()">下载</a>
        </g:if>
        <a id="btn_back" class="btn_basic blue_black" href="javascript:void(0)" onclick="javascript:window.location.href='${createLink(controller:'ZCInfoReplaceAuth',action:'index_zcinfoReplace')}?searchType=${searchType}'">返回</a>
    </div></td>
</tr>
</tbody>
</table>

</div>
</div>

</div>
</div>

</div>
</form>
<script language="javascript" type="text/javascript">

function download_zcinf()
{
    var veh_Need_Cer=$("#veh_Need_Cer").val();
    var hgz=$("#veh_Zchgzbh").val();
        if(hgz==""){
            alert("下载合格证异常,请刷新后重试!");
            return
        }
        var url = "${createLink(controller:'ZCInfo',action:'download_ZCI_by')}?hgz="+hgz+"&veh_Need_Cer="+veh_Need_Cer;
        //要求用户确认下载
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
            content:'${message(code: 'vehcert.button.dowload.confirm.message', default: '下载后系统默认合格证已打印，是否确定下载?')}',
            onClose:function(v){
                if(v){
                    $.post(url,function(data,textStatus){
                        data=eval("("+data+")");
                        if(data.message=='success'){

                            if(veh_Need_Cer=='2'){
                                var filePath=data.filePath;
                                var dpFilePath=data.dpFilePath;
                                window.location.href="${createLink(controller:'downLoad',action:'downZip')}?fileName="+encodeURI("整车合格证和底盘合格证.zip")+"&filePaths="+filePath+"&filePaths="+dpFilePath;
                            }else{
                                window.location.href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${zcinfoReAuInstance?.veh_Zchgzbh_R}.pdf&filePath="+data.filePath;
                            }
                            alert('合格证信息下载成功!')

                        }else{
                            if(data.message==0){
                                alert("合格证信息PDF不存在！请与相关管理人员联系！");
                            }else if(data.message==1){
                                alert("合格证信息更新失败！");
                            }else if(data.message==2){
                                alert("下载记录保存失败！")  ;
                            }else if(data.message==3){
                                alert('合格证信息不存在！');
                            }else if(data.message==5){
                                alert("底盘合格证信息PDF不存在！请与相关管理人员联系！");
                            }else{
                                alert('下载失败！');
                            }
                        }
                    },'text');

                }
            }
        });
}
</script>
</body>
</html>