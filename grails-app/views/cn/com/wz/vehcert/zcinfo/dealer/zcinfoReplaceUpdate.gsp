%{-- 合格证变更申请查询 合格证变更修改页面--}%
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
    <style type="text/css">

    </style>
</head>
<body>
<div id="page" style="width:1300px;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">


<div style="margin-right:8px;padding-top:8px; height:98%;  width:100%;">

<div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


<div class="bDiv" style="width:auto; height:98%;width: 100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <th align="center" class="indexCol" colspan="17"><div align="center" class="innerCol " >合格证更换申请编辑</div></th>
    <th id ="td1" style="display:none" align="center" class="indexCol" colspan="9"></th>
</tr>
<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
    <td style="display:none" id ="td2" align="center" class="indexCol" rowspan="31"><div align="center" class="innerCol " ></div></td>
    <td style="display:none" id ="td3" align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >附加底盘合格证（需要整车和底盘的变更申请填写底盘公告）</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:115px">车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clsbdh}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol "  ></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:120px">是否需要底盘合格证：</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >
        <select id="veh_Need_Cer" name="veh_Need_Cer">
            <option value="0" <g:if test="${zcinfoReplace?.veh_Need_Cer==0}">selected="true" </g:if>>只要整车合格证</option>
            <option value="1" <g:if test="${zcinfoReplace?.veh_Need_Cer==1}">selected="true" </g:if>>只要底盘合格证</option>
            <option value="2" <g:if test="${zcinfoReplace?.veh_Need_Cer==2}">selected="true" </g:if>>整车和底盘合格证</option>
        </select>
    </div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  >公告信息：<input type="button" id="btn_select" value='选择'></div></td>
    <td id ="td4" style="display:none" align="left" abbr="id" class="indexCol" colspan="1" ><div class="innerCol ">附加底盘信息:<input type="button" id="btn_select_dp" value='选择'></div></td>
    <td id ="td5" style="display:none" align="left" abbr="id" class="col1" colspan="7" ></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="3"></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >纸张编号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" id="veh_Zzbh" value="${zcinfoReplace?.veh_Zzbh}"/></div></td>
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="7"></td>
    <td id ="td6" style="display:none" align="left" class="indexCol"></td>
    <td id ="td7" style="display:none" align="left" abbr="id" class="col0" colspan="7"></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zchgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Fzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zchgzbh"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Fzrq" ></div></td>
    <td style="display:none" id ="td8" align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td style="display:none" id ="td9" align="left" abbr="id" class="col0" colspan="3"><div  align="left" class="${hasErrors(bean: replaceForSupplement, field: 'veh_Zchgzbh_R', 'error')} required" >${replaceForSupplement?.veh_Zchgzbh_R}</div></td>
    <td style="display:none" id ="td10" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td style="display:none" id ="td11" align="left" abbr="city" class="col1" colspan="3"><div  align="left" class="${hasErrors(bean: replaceForSupplement, field: 'veh_Fzrq_R', 'error')} required" >${replaceForSupplement?.veh_Fzrq_R}</div></td>
</tr>


<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc">${zcinfoReplace?.veh_Clzzqymc_R}</div></td>
    <td style="display:none" id ="td12" align="left" class="indexCol"><div align="left" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != replaceForSupplement?.veh_Clzzqymc_R }" >  style="color: red" </g:if>class="innerCol  STYLE1">3.车辆制造企业名称</div></td>
    <td style="display:none" id ="td13" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Clzzqymc_R_Div" class="innerCol"<g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != replaceForSupplement?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clzzqymc_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clpp}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clmc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp">${zcinfoReplace?.veh_Clpp_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc">${zcinfoReplace?.veh_Clmc_R}</div></td>
    <td style="display:none" id ="td14" align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td style="display:none" id ="td15" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Clpp_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clpp != replaceForSupplement?.veh_Clpp_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clpp_R}</div></td>
    <td style="display:none" id ="td16" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Clmc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clmc != replaceForSupplement?.veh_Clmc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clmc_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clxh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clsbdh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clxh">${zcinfoReplace?.veh_Clxh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " name="veh_Clsbdh">${zcinfoReplace?.veh_Clsbdh_R}</div></td>
    <td style="display:none" id ="td17" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != replaceForSupplement?.veh_Clxh_R }" >  style="color: red" </g:if>>5.车辆型号</div></td>
    <td style="display:none" id ="td18" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Clxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != replaceForSupplement?.veh_Clxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clxh_R}</div></td>
    <td style="display:none" id ="td19" align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td style="display:none" id ="td20" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " name="Dp_veh_Clsbdh_R_Div">${replaceForSupplement?.veh_Clsbdh_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="7"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Csys }</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Csys_R" value="${zcinfoReplace?.veh_Csys_R}"></div></td>
    <td style="display:none" id ="td21" align="left" class="indexCol"><div align="left" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != replaceForSupplement?.veh_Csys_R }" >  style="color: red" </g:if> >7.车身颜色</div></td>
    <td id ="td22" style="display:none" align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol "><input style="color: red" type="text" id="Dp_veh_Csys_R" value="${replaceForSupplement?.veh_Csys_R}"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Dpxh}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Dpid}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh">${zcinfoReplace?.veh_Dpxh_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid">${zcinfoReplace?.veh_Dpid_R}</div></td>
    <td style="display:none" id ="td23" align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td style="display:none" id ="td24" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Dpxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpxh != replaceForSupplement?.veh_Dpxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Dpxh_R}</div></td>
    <td style="display:none" id ="td25" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Dpid_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpid != replaceForSupplement?.veh_Dpid_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Dpid_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Dphgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Fdjxh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dphgzbh">${zcinfoReplace?.veh_Dphgzbh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  id="veh_Fdjxh">${zcinfoReplace?.veh_Fdjxh_R}</div></td>
    <td style="display:none" id ="td26" align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td style="display:none" id ="td27" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Dphgzbh_R}</div></td>
    <td style="display:none" id ="td28" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != replaceForSupplement?.veh_Fdjxh_R }" >  style="color: red" </g:if>>10.发动机型号</div></td>
    <td style="display:none" id ="td29" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Fdjxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != replaceForSupplement?.veh_Fdjxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Fdjxh_R}</div></td>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Fdjh" value="${zcinfoReplace?.veh_Fdjh_R}"></div></td>
    <td style="display:none" id ="td31" align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td id ="td91" style="display:none" align="left" abbr="city" class="col0" colspan="8"><div align="left" class="innerCol "><input type="text" id="Dp_veh_Fdjh_R" value="${replaceForSupplement?.veh_Fdjh_R}"></div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Rlzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Pl}</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zcinfoReplace?.veh_Gl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Rlzl">${zcinfoReplace?.veh_Rlzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " id="veh_Pl" >${zcinfoReplace?.veh_Pl_R}</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " id="veh_Gl"></div></td>
    <td style="display:none" id ="td32" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != replaceForSupplement?.veh_Rlzl_R }" >  style="color: red" </g:if>>12.燃料种类</div></td>
    <td style="display:none" id ="td33" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Rlzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != replaceForSupplement?.veh_Rlzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Rlzl_R}</div></td>
    <td style="display:none" id ="td34" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td style="display:none" id ="td35" align="left" abbr="city" class="col1" colspan="2"><div align="left" id="Dp_veh_Pl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Pl != replaceForSupplement?.veh_Pl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Pl_R}</div></td>
    <td style="display:none" id ="td36" align="left" abbr="city" class="col1" ><div align="left" id="Dp_veh_Gl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gl != replaceForSupplement?.veh_Gl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Gl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Pfbz}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz">${zcinfoReplace?.veh_Pfbz_R}</div></td>
    <td style="display:none" id ="td37" align="left" class="indexCol"><div align="left" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != replaceForSupplement?.veh_Pfbz_R }" >  style="color: red" </g:if>>14.排放标准</div></td>
    <td style="display:none" id ="td38" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Pfbz_R_Div" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != replaceForSupplement?.veh_Pfbz_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Pfbz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Yh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh">${zcinfoReplace?.veh_Yh_R}</div></td>
    <td style="display:none" id ="td39" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != replaceForSupplement?.veh_Yh_R }" >  style="color: red" </g:if>>15.油耗</div></td>
    <td style="display:none" id ="td40" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Yh_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != replaceForSupplement?.veh_Yh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Yh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkc}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkk}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkg}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbc}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbk}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbg}</div></td>

    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkc">${zcinfoReplace?.veh_Wkc_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkk">${zcinfoReplace?.veh_Wkk_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkg">${zcinfoReplace?.veh_Wkg_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbc">${zcinfoReplace?.veh_Hxnbc_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbk">${zcinfoReplace?.veh_Hxnbk_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbg">${zcinfoReplace?.veh_Hxnbg_R}</div></td>
    <td style="display:none" id ="td41" align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td style="display:none" id ="td42" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkc != replaceForSupplement?.veh_Wkc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkc_R}</div></td>
    <td style="display:none" id ="td43" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkk_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkk != replaceForSupplement?.veh_Wkk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkk_R}</div></td>
    <td style="display:none" id ="td44" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkg != replaceForSupplement?.veh_Wkg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkg_R}</div></td>
    <td style="display:none" id ="td45" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td style="display:none" id ="td46" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbc_R_Div"  <g:if test="${zcinfoReAuInstance?.veh_Hxnbc != replaceForSupplement?.veh_Hxnbc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbc_R}</div></td>
    <td style="display:none" id ="td47" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbk_R_Div"<g:if test="${zcinfoReAuInstance?.veh_Hxnbk != replaceForSupplement?.veh_Hxnbk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbk_R}</div></td>
    <td style="display:none" id ="td48" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbg_R_Div" <g:if test="${zcinfoReAuInstance?.veh_Hxnbg != replaceForSupplement?.veh_Hxnbg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Gbthps}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Lts}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Gbthps">${zcinfoReplace?.veh_Gbthps_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Lts">${zcinfoReplace?.veh_Lts_R}</div></td>
    <td style="display:none" id ="td49" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gbthps != replaceForSupplement?.veh_Gbthps_R }" >  style="color: red" </g:if>>18.钢板弹簧片数(片)</div></td>
    <td style="display:none" id ="td50" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Gbthps_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Gbthps != replaceForSupplement?.veh_Gbthps_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Gbthps_R}</div></td>
    <td style="display:none" id ="td51" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != zcinfoReAuInstance?.veh_Lts_R }" >  style="color: red" </g:if>>19.轮胎数</div></td>
    <td style="display:none" id ="td52" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Lts_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != replaceForSupplement?.veh_Lts_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Lts_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Ltgg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ltgg">${zcinfoReplace?.veh_Ltgg_R}</div></td>
    <td style="display:none" id ="td53" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != replaceForSupplement?.veh_Ltgg_R }" >  style="color: red" </g:if>>20.轮胎规格</div></td>
    <td style="display:none" id ="td54" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Ltgg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != replaceForSupplement?.veh_Ltgg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Ltgg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Qlj}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Hlj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Qlj">${zcinfoReplace?.veh_Qlj_R}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Hlj">${zcinfoReplace?.veh_Hlj_R}</div></td>
    <td style="display:none" id ="td55" align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td style="display:none" id ="td56" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Qlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Qlj != replaceForSupplement?.veh_Qlj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Qlj_R}</div></td>
    <td style="display:none" id ="td57" align="left" abbr="city" class="col1" colspan="4"><div align="left" id="Dp_veh_Hlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Hlj != replaceForSupplement?.veh_Hlj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hlj_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zj">${zcinfoReplace?.veh_Zj_R}</div></td>
    <td style="display:none" id ="td58" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != replaceForSupplement?.veh_Zj_R }" >  style="color: red" </g:if>>22.轴距(mm)</div></td>
    <td style="display:none" id ="td59" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Zj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != replaceForSupplement?.veh_Zj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zj_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zh">${zcinfoReplace?.veh_Zh_R}</div></td>
    <td style="display:none" id ="td60" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != replaceForSupplement?.veh_Zh_R }" >  style="color: red" </g:if>>23.轴荷(kg)</div></td>
    <td style="display:none" id ="td61" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Zh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != replaceForSupplement?.veh_Zh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zs}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zxxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zs">${zcinfoReplace?.veh_Zs_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  id="veh_Zxxs">${zcinfoReplace?.veh_Zxxs_R}</div></td>
    <td style="display:none" id ="td62" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != replaceForSupplement?.veh_Zs_R }" >  style="color: red" </g:if>>24.轴数</div></td>
    <td style="display:none" id ="td63" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != replaceForSupplement?.veh_Zs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zs_R}</div></td>
    <td style="display:none" id ="td64" align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zxxs != replaceForSupplement?.veh_Zxxs_R }" >  style="color: red" </g:if>>25.转向形式</div></td>
    <td style="display:none" id ="td65" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zxxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zxxs != replaceForSupplement?.veh_Zxxs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zxxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zbzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zzl">${zcinfoReplace?.veh_Zzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zbzl">${zcinfoReplace?.veh_Zbzl_R}</div></td>
    <td style="display:none" id ="td66" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != replaceForSupplement?.veh_Zzl_R }" >  style="color: red" </g:if>>26.总质量(kg)</div></td>
    <td style="display:none" id ="td67" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != replaceForSupplement?.veh_Zzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zzl_R}</div></td>
    <td style="display:none" id ="td68" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != replaceForSupplement?.veh_Zbzl_R }" >  style="color: red" </g:if>>27.整备质量(kg)</div></td>
    <td style="display:none" id ="td69" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zbzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != replaceForSupplement?.veh_Zbzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zbzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zzllyxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzzl">${zcinfoReplace?.veh_Edzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zzllyxs">${zcinfoReplace?.veh_Zzllyxs_R}</div></td>
    <td style="display:none" id ="td70" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Edzzl != replaceForSupplement?.veh_Edzzl_R }" >  style="color: red" </g:if>>28.额定载质量(kg)</div></td>
    <td style="display:none" id ="td71" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Edzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzzl != replaceForSupplement?.veh_Edzzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Edzzl_R}</div></td>
    <td style="display:none" id ="td72" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != replaceForSupplement?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>29.载质量利用系数</div></td>
    <td style="display:none" id ="td73" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zzllyxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != replaceForSupplement?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zzllyxs_R}</div></td>
</tr>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zqyzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Bgcazzdyxzzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zqyzzl">${zcinfoReplace?.veh_Zqyzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Bgcazzdyxzzl">${zcinfoReplace?.veh_Bgcazzdyxzzl_R}</div></td>
    <td style="display:none" id ="td74" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != replaceForSupplement?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>30.准牵引总质量(kg)</div></td>
    <td style="display:none" id ="td75" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zqyzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != replaceForSupplement?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zqyzzl_R}</div></td>
    <td style="display:none" id ="td76" align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != replaceForSupplement?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if>>31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td style="display:none" id ="td77" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Bgcazzdyxzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != replaceForSupplement?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if> >${replaceForSupplement?.veh_Bgcazzdyxzzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Jsszcrs}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Jsszcrs">${zcinfoReplace?.veh_Jsszcrs_R}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
    <td style="display:none" id ="td78" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != replaceForSupplement?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>32.驾驶室准乘人数(人)</div></td>
    <td style="display:none" id ="td79" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Jsszcrs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != replaceForSupplement?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Jsszcrs_R}</div></td>
    <td style="display:none" id ="td80" align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td style="display:none" id ="td81" align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzk">${zcinfoReplace?.veh_Edzk_R}</div></td>
    <td style="display:none" id ="td82" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != replaceForSupplement?.veh_Edzk_R }" >  style="color: red" </g:if>>33.额定载客(人)</div></td>
    <td style="display:none" id ="td83" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Edzk_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != replaceForSupplement?.veh_Edzk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Edzk_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zgcs">${zcinfoReplace?.veh_Zgcs_R}</div></td>
    <td style="display:none" id ="td84" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != replaceForSupplement?.veh_Zgcs_R }" >  style="color: red" </g:if>>34.最高车速(km/h)</div></td>
    <td style="display:none" id ="td85" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zgcs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != replaceForSupplement?.veh_Zgcs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zgcs_R}</div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clzzrq">${zcinfoReplace?.veh_Clzzrq_R}</div></td>
    <td style="display:none" id ="td86" align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td style="display:none" id ="td87" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Clzzrq_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol " id="veh_Bz">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz_R}</div></td>
    <td style="display:none" id ="td88" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Bz_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bz != replaceForSupplement?.veh_Bz_R }" >  style="color: red" </g:if>>
        备注：${replaceForSupplement?.veh_Bz_R}
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_clzzqyxx}</div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " id="veh_clzzqyxx">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_clzzqyxx_R}</div></td>
    <td style="display:none" id ="td89" align="left" abbr="id" class="col0" colspan="8"><div align="left" id ="Dp_veh_clzzqyxx_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != replaceForSupplement?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>车辆制造信息：${replaceForSupplement?.veh_clzzqyxx_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">业务员：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmanname" type="text" value="${zcinfoReplace?.salesmanname}"></div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " >业务员电话：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmantel" type="text" value="${zcinfoReplace?.salesmantel}"></div></td>
    <td id ="td90" style="display:none" align="left" abbr="id" class="col0" colspan="9"></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17">备注：&nbsp;&nbsp;&nbsp;&nbsp;<g:textArea name="remark" value="${zcinfoReplace?.remark}"></g:textArea> </td>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol "><form id="form" method="post">
        <input type="hidden" value="${zid}" id="zid">
        <input type="hidden" value="" id="newId">
        <input type="hidden" value="" id="newDpId">
        <input type="hidden" value="${statusWrite}" id="statusWrite">
        <input type='button' id="newOld" value="提交申请"> &nbsp;&nbsp;
        <input type='button' id="gotoBack" value="返回">
    </form>
    </a>
    </div></td>
</tr>
</tbody>
</table>

</div>
</div>

</div>
</div>

</div>
<script>
    $(function() {
        if  ($('#veh_Need_Cer').val()==2){
            $('#td1').show();
            $('#td2').show();
            $('#td3').show();
            $('#td4').show();
            $('#td5').show();
            $('#td6').show();
            $('#td7').show();
            $('#td8').show();
            $('#td9').show();
            $('#td10').show();
            $('#td11').show();
            $('#td12').show();
            $('#td13').show();
            $('#td14').show();
            $('#td15').show();
            $('#td16').show();
            $('#td17').show();
            $('#td18').show();
            $('#td19').show();
            $('#td20').show();
            $('#td21').show();
            $('#td22').show();
            $('#td23').show();
            $('#td24').show();
            $('#td25').show();
            $('#td26').show();
            $('#td27').show();
            $('#td28').show();
            $('#td29').show();
            $('#td30').show();
            $('#td31').show();
            $('#td32').show();
            $('#td33').show();
            $('#td34').show();
            $('#td35').show();
            $('#td36').show();
            $('#td37').show();
            $('#td38').show();
            $('#td39').show();
            $('#td40').show();
            $('#td41').show();
            $('#td42').show();
            $('#td43').show();
            $('#td44').show();
            $('#td45').show();
            $('#td46').show();
            $('#td47').show();
            $('#td48').show();
            $('#td49').show();
            $('#td50').show();
            $('#td51').show();
            $('#td52').show();
            $('#td53').show();
            $('#td54').show();
            $('#td55').show();
            $('#td56').show();
            $('#td57').show();
            $('#td58').show();
            $('#td59').show();
            $('#td60').show();
            $('#td61').show();
            $('#td62').show();
            $('#td63').show();
            $('#td64').show();
            $('#td65').show();
            $('#td66').show();
            $('#td67').show();
            $('#td68').show();
            $('#td69').show();
            $('#td70').show();
            $('#td71').show();
            $('#td72').show();
            $('#td73').show();
            $('#td74').show();
            $('#td75').show();
            $('#td76').show();
            $('#td77').show();
            $('#td78').show();
            $('#td79').show();
            $('#td80').show();
            $('#td81').show();
            $('#td82').show();
            $('#td83').show();
            $('#td84').show();
            $('#td85').show();
            $('#td86').show();
            $('#td87').show();
            $('#td88').show();
            $('#td89').show();
            $('#td90').show();
            $('#td91').show();
            document.getElementById("page").style.width="1770px";
        }else{
            $('#td1').hide();
            $('#td2').hide();
            $('#td3').hide();
            $('#td4').hide();
            $('#td5').hide();
            $('#td6').hide();
            $('#td7').hide();
            $('#td8').hide();
            $('#td9').hide();
            $('#td10').hide();
            $('#td11').hide();
            $('#td12').hide();
            $('#td13').hide();
            $('#td14').hide();
            $('#td15').hide();
            $('#td16').hide();
            $('#td17').hide();
            $('#td18').hide();
            $('#td19').hide();
            $('#td20').hide();
            $('#td21').hide();
            $('#td22').hide();
            $('#td23').hide();
            $('#td24').hide();
            $('#td25').hide();
            $('#td26').hide();
            $('#td27').hide();
            $('#td28').hide();
            $('#td29').hide();
            $('#td30').hide();
            $('#td31').hide();
            $('#td32').hide();
            $('#td33').hide();
            $('#td34').hide();
            $('#td35').hide();
            $('#td36').hide();
            $('#td37').hide();
            $('#td38').hide();
            $('#td39').hide();
            $('#td40').hide();
            $('#td41').hide();
            $('#td42').hide();
            $('#td43').hide();
            $('#td44').hide();
            $('#td45').hide();
            $('#td46').hide();
            $('#td47').hide();
            $('#td48').hide();
            $('#td49').hide();
            $('#td50').hide();
            $('#td51').hide();
            $('#td52').hide();
            $('#td53').hide();
            $('#td54').hide();
            $('#td55').hide();
            $('#td56').hide();
            $('#td57').hide();
            $('#td58').hide();
            $('#td59').hide();
            $('#td60').hide();
            $('#td61').hide();
            $('#td62').hide();
            $('#td63').hide();
            $('#td64').hide();
            $('#td65').hide();
            $('#td66').hide();
            $('#td67').hide();
            $('#td68').hide();
            $('#td69').hide();
            $('#td70').hide();
            $('#td71').hide();
            $('#td72').hide();
            $('#td73').hide();
            $('#td74').hide();
            $('#td75').hide();
            $('#td76').hide();
            $('#td77').hide();
            $('#td78').hide();
            $('#td79').hide();
            $('#td80').hide();
            $('#td81').hide();
            $('#td82').hide();
            $('#td83').hide();
            $('#td84').hide();
            $('#td85').hide();
            $('#td86').hide();
            $('#td87').hide();
            $('#td88').hide();
            $('#td89').hide();
            $('#td90').hide();
            $('#td91').hide();
            document.getElementById("page").style.width="1300px";
        }
        $("#veh_Need_Cer").change( function() {
            var veh_Need_Cer=$('#veh_Need_Cer').val();
            if  (veh_Need_Cer==2){
                $('#headDp').show();
                $('#td1').show();
                $('#td2').show();
                $('#td3').show();
                $('#td4').show();
                $('#td5').show();
                $('#td6').show();
                $('#td7').show();
                $('#td8').show();
                $('#td9').show();
                $('#td10').show();
                $('#td11').show();
                $('#td12').show();
                $('#td13').show();
                $('#td14').show();
                $('#td15').show();
                $('#td16').show();
                $('#td17').show();
                $('#td18').show();
                $('#td19').show();
                $('#td20').show();
                $('#td21').show();
                $('#td22').show();
                $('#td23').show();
                $('#td24').show();
                $('#td25').show();
                $('#td26').show();
                $('#td27').show();
                $('#td28').show();
                $('#td29').show();
                $('#td30').show();
                $('#td31').show();
                $('#td32').show();
                $('#td33').show();
                $('#td34').show();
                $('#td35').show();
                $('#td36').show();
                $('#td37').show();
                $('#td38').show();
                $('#td39').show();
                $('#td40').show();
                $('#td41').show();
                $('#td42').show();
                $('#td43').show();
                $('#td44').show();
                $('#td45').show();
                $('#td46').show();
                $('#td47').show();
                $('#td48').show();
                $('#td49').show();
                $('#td50').show();
                $('#td51').show();
                $('#td52').show();
                $('#td53').show();
                $('#td54').show();
                $('#td55').show();
                $('#td56').show();
                $('#td57').show();
                $('#td58').show();
                $('#td59').show();
                $('#td60').show();
                $('#td61').show();
                $('#td62').show();
                $('#td63').show();
                $('#td64').show();
                $('#td65').show();
                $('#td66').show();
                $('#td67').show();
                $('#td68').show();
                $('#td69').show();
                $('#td70').show();
                $('#td71').show();
                $('#td72').show();
                $('#td73').show();
                $('#td74').show();
                $('#td75').show();
                $('#td76').show();
                $('#td77').show();
                $('#td78').show();
                $('#td79').show();
                $('#td80').show();
                $('#td81').show();
                $('#td82').show();
                $('#td83').show();
                $('#td84').show();
                $('#td85').show();
                $('#td86').show();
                $('#td87').show();
                $('#td88').show();
                $('#td89').show();
                $('#td90').show();
                $('#td91').show();
                document.getElementById("page").style.width="1950px";
            }else{
                $('#headDp').hide();
                $('#td1').hide();
                $('#td2').hide();
                $('#td3').hide();
                $('#td4').hide();
                $('#td5').hide();
                $('#td6').hide();
                $('#td7').hide();
                $('#td8').hide();
                $('#td9').hide();
                $('#td10').hide();
                $('#td11').hide();
                $('#td12').hide();
                $('#td13').hide();
                $('#td14').hide();
                $('#td15').hide();
                $('#td16').hide();
                $('#td17').hide();
                $('#td18').hide();
                $('#td19').hide();
                $('#td20').hide();
                $('#td21').hide();
                $('#td22').hide();
                $('#td23').hide();
                $('#td24').hide();
                $('#td25').hide();
                $('#td26').hide();
                $('#td27').hide();
                $('#td28').hide();
                $('#td29').hide();
                $('#td30').hide();
                $('#td31').hide();
                $('#td32').hide();
                $('#td33').hide();
                $('#td34').hide();
                $('#td35').hide();
                $('#td36').hide();
                $('#td37').hide();
                $('#td38').hide();
                $('#td39').hide();
                $('#td40').hide();
                $('#td41').hide();
                $('#td42').hide();
                $('#td43').hide();
                $('#td44').hide();
                $('#td45').hide();
                $('#td46').hide();
                $('#td47').hide();
                $('#td48').hide();
                $('#td49').hide();
                $('#td50').hide();
                $('#td51').hide();
                $('#td52').hide();
                $('#td53').hide();
                $('#td54').hide();
                $('#td55').hide();
                $('#td56').hide();
                $('#td57').hide();
                $('#td58').hide();
                $('#td59').hide();
                $('#td60').hide();
                $('#td61').hide();
                $('#td62').hide();
                $('#td63').hide();
                $('#td64').hide();
                $('#td65').hide();
                $('#td66').hide();
                $('#td67').hide();
                $('#td68').hide();
                $('#td69').hide();
                $('#td70').hide();
                $('#td71').hide();
                $('#td72').hide();
                $('#td73').hide();
                $('#td74').hide();
                $('#td75').hide();
                $('#td76').hide();
                $('#td77').hide();
                $('#td78').hide();
                $('#td79').hide();
                $('#td80').hide();
                $('#td81').hide();
                $('#td82').hide();
                $('#td83').hide();
                $('#td84').hide();
                $('#td85').hide();
                $('#td86').hide();
                $('#td87').hide();
                $('#td88').hide();
                $('#td89').hide();
                $('#td90').hide();
                $('#td91').hide();
                document.getElementById("page").style.width="1300px";
            }
        });

        $('#btn_select').omButton({
            width:40,
            onClick:function(){
                var veh_Need_Cer=$('#veh_Need_Cer').val();
                var turnOff=$('#turnOff').val();
                var url = '${createLink(controller:'ZCInfo',action:'toNotice')}?veh_Need_Cer='+veh_Need_Cer+'&turnOff='+turnOff+'&supplement=0';
                create(url);
                $('#veh_Fdjh_Dp').val($('#veh_Fdjh').val());
                $('#veh_Csys_R_Dp').val($('#veh_Csys_R').val());

            }
        });

        $('#btn_select_dp').omButton({
            width:40,
            onClick:function(){
                var veh_Need_Cer=$('#veh_Need_Cer').val();
                var turnOff=$('#turnOff').val();
                var url = '${createLink(controller:'ZCInfo',action:'toNotice')}?veh_Need_Cer=1'+'&turnOff='+turnOff+'&supplement=1';
                create(url);

            }
        });

        $('#gotoBack').omButton({
            width:70,
            onClick:function(){
                var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'index_zcinfoReplace')}?searchType=${searchType}';
                window.location.href=url
            }
        });


        $('#newOld').omButton({
            width:70,
            onClick:function(){

                var newId=$('#newId').val();
                var veh_Clsbdh=$('#veh_Clsbdh').val();
                var veh_Zzbh=$('#veh_Zzbh').val();
                var veh_Need_Cer= $('#veh_Need_Cer').val();
                var veh_Fdjh= $('#veh_Fdjh').val();
                var salesmanname=$('#salesmanname').val();
                var salesmantel= $('#salesmantel').val();
                var remark= $('#remark').val();
                var zid =$('#zid').val();
                var statusWrite =$('#statusWrite').val();
                var veh_Csys_R=$("#veh_Csys_R").val();
                var newDpId=$('#newDpId').val();
                var veh_Fdjh_Dp = $('#Dp_veh_Fdjh_R').val();
                var veh_Csys_R_Dp=$('Dp_veh_Csys_R').val();
                var veh_Clsbdh_Dp=$('#Dp_veh_Clsbdh_R_Div').val();
                %{--var url= '${createLink(controller:'ZCInfoReplaceAuth',action:'zinfoReplaceUpdate')}?newId='+newId+'&statusWrite='+statusWrite+'&veh_Clsbdh='+veh_Clsbdh+'&zid='+zid+'&veh_Zzbh='+veh_Zzbh+'&veh_Need_Cer='+veh_Need_Cer+'&veh_Fdjh='+veh_Fdjh+'&salesmanname='+salesmanname+"&veh_Csys_R="+veh_Csys_R+'&salesmantel='+salesmantel+'&remark='+remark;--}%
                var url= '${createLink(controller:'ZCInfoReplaceAuth',action:'zinfoReplaceUpdate')}?veh_Fdjh='+veh_Fdjh+"&veh_Csys_R="+veh_Csys_R+'&salesmanname='+salesmanname+'&salesmantel='+salesmantel+'&remark='+remark+"&veh_Csys_R_Dp="+veh_Csys_R_Dp+"&veh_Fdjh_Dp="+veh_Fdjh_Dp;
                url=encodeURI(url);
                $.post(url,{newId:newId,veh_Clsbdh:veh_Clsbdh,veh_Zzbh:veh_Zzbh,zid:zid,veh_Need_Cer:veh_Need_Cer,statusWrite:statusWrite,veh_Clsbdh_Dp:veh_Clsbdh_Dp,newDpId:newDpId},function(data,textStatus){
                    if(textStatus){
                        alert(data);
                    }
                },'text');
            }
        });


        //绑定下载按钮事件
        $('#btn_down').omButton({
            width:40,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfo',action:'download_ZCI')}";
                url+='?'+$('#form_query').serialize();
                if($("#hidden_id").val()==""){
                    alert("请查询要下载的合格证信息!");
                    return
                }
                $.post(url,{"ids":"i"},function(data,textStatus){
                    alert(data);
                },'text');
            }
        });
    })
    function changeData(data){
        $('#newId').val(data.id);
        $('#veh_Csys').html(data.veh_Csys) ;
        $('#veh_Edzk').html(data.veh_Edzk) ;
        $('#veh_Zs').html(data.veh_Zs) ;
        $('#veh_Zqyzzl').html(data.veh_Zqyzzl) ;
        $('#veh_Bgcazzdyxzzl').html(data.veh_Bgcazzdyxzzl) ;
        $('#veh_Fdjxh').html(data.veh_Fdjxh)//發動機型號
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
        $('#veh_Cpscdz').html(data.veh_Cpscdz)      //生产地址
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
        $('#veh_VinFourBit').html(data.veh_VinFourBit)      //vin第四位
    }
    function changeDataDp(data){
        $('#newDpId').val(data.id);
        $('#Dp_veh_Edzk_R_Div').html(data.veh_Edzk) ;
        $('#Dp_veh_Zs_R_Div').html(data.veh_Zs) ;
        $('#Dp_veh_Zqyzzl_R_Div').html(data.veh_Zqyzzl) ;
        $('#Dp_veh_Bgcazzdyxzzl_R_Div').html(data.veh_Bgcazzdyxzzl) ;
        $('#Dp_veh_Fdjxh_R_Div').html(data.veh_Fdjxh)//發動機型號
        $('#Dp_veh_Clxh_R_Div').html(data.veh_Clxh)     //车辆型号
        $('#Dp_veh_Zj_R_Div').html(data.veh_Zj)            //轴距
        $('#Dp_veh_Pl_R_Div').html(data.veh_Pl)           //排量和功率
        $('#Dp_veh_Gl_R_Div').html(data.veh_Gl)
        $('#Dp_veh_Zzl_R_Div').html(data.veh_Zzl)        //总质量
        $('#Dp_veh_Clmc_R_Div').html(data.veh_Clmc)      //车辆名称
        $('#Dp_veh_Edzzl_R_Div').html(data.veh_Edzzl)    //额定载质量
        $('#Dp_veh_Pfbz_R_Div').html(data.veh_Pfbz)   //排放标准
        $('#Dp_veh_Hxnbc_R_Div').html(data.veh_Hxnbc)     //货厢内部尺寸
        $('#Dp_veh_Hxnbk_R_Div').html(data.veh_Hxnbk)     //货厢内部尺寸
        $('#Dp_veh_Hxnbg_R_Div').html(data.veh_Hxnbg)      //货厢内部尺寸
        $('#Dp_veh_Zxxs_R_Div').html(data.veh_Zxxs)     //转向形式
        $('#Dp_veh_Zbzl_R_Div').html(data.veh_Zbzl)     //整备质量
        $('#Dp_veh_Zh_R_Div').html(data.veh_Zh)      //轴荷
        $('#Dp_veh_Wkc_R_Div').html(data.veh_Wkc)     //外廓尺寸
        $('#Dp_veh_Wkk_R_Div').html(data.veh_Wkk)     //外廓尺寸
        $('#Dp_veh_Wkg_R_Div').html(data.veh_Wkg)      //外廓尺寸
        $('#Dp_veh_Zzllyxs_R_Div').html(data.veh_Zzllyxs)      //载质量利用系数
        $('#Dp_veh_Dpid_R_Div').html(data.veh_Dpid)      //底盘ID
        $('#Dp_veh_Dpxh_R_Div').html(data.veh_Dpxh)      //底盘型号
        //$('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#Dp_veh_Yh_R_Div').html(data.veh_Yh)      //油耗
        $('#Dp_veh_Gbthps_R_Div').html(data.veh_Gbthps)     //钢板弹簧片数
        $('#Dp_veh_Clpp_R_Div').html(data.veh_Clpp)     //车辆品牌
        $('#Dp_veh_Lts_R_Div').html(data.veh_Lts)      //轮胎数
        $('#Dp_veh_Clzzqymc_R_Div').html(data.veh_Clzzqymc)     //车辆企业名称
        $('#Dp_veh_Zgcs_R_Div').html(data.veh_Zgcs)      //最高车速
        $('#Dp_veh_Rlzl_R_Div').html(data.veh_Rlzl)     //燃料种类
        $('#Dp_veh_Qlj_R_Div').html(data.veh_Qlj)     //轮距
        $('#Dp_veh_Hlj_R_Div').html(data.veh_Hlj)      //轮距
        $('#Dp_veh_Ltgg_R_Div').html(data.veh_Ltgg)     //轮胎规格
        $('#Dp_veh_Jsszcrs_R_Div').html(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#Dp_veh_Bz_R_Div').html(data.veh_Bz)      //备注
    }
    function create(url){
        var tabId=window.name;
        var flag=url.indexOf('?');
        if(flag>0){
            url+="&tabId="+tabId;
        }else{
            url+="?tabId="+tabId;
        }
        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
            'margin-right:-500px;text-align:center; width:100%;height:100%" '+
            'scrolling="yes" src="'+url+'"></iframe>';
        parent.showDialog(1,content,"公告信息",1400,900);
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
