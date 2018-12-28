
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
    <style type="text/css">

    </style>
</head>
<body>
<div id="page" style="width:1300px;background-color:white;margin:0px 0px;overflow:visible;">
<div style="margin-right:8px;padding-top:8px; height:98%;  width:100%;">

<div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


<div class="bDiv" style="width:100%; height:98%;width: 100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <th align="center" class="indexCol" colspan="18"><div align="center" class="innerCol " >合格证更换申请</div></th>
    <th id ="headDp" style="display:none" align="center" class="indexCol" colspan="8"></th>
</tr>
<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="32"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
    <td id ="td1" style="display:none" align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td id ="td2" style="display:none" align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >附加底盘合格证（需要整车和底盘的变更申请填写底盘公告）</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:115px">车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><input type="text" id="veh_Clsbdh" value="${zciInfoModel?.veh_Clsbdh}"><input type="button" id="relate" value='关联'> </div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol "  >%{--<input type="button" id="write" value='手动填写'>--}%</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:120px">是否需要底盘合格证：</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >
        <select id="veh_Need_Cer">
            <option value="0">只要整车合格证</option>
            <option value="1">只要底盘合格证</option>
            <option value="2">整车和底盘合格证</option>
        </select>
    </div></td>
    <td align="left" abbr="id" class="col0" colspan="3" ><div alig n="left" class="innerCol ">公告信息：<input type="button" id="btn_select" value='选择'></div></td>
    <td id ="td3" style="display:none" align="left" abbr="id" class="indexCol" colspan="1" ><div class="innerCol ">附加底盘信息:<input type="button" id="btn_select_dp" value='选择'></div></td>
    <td id ="td4" style="display:none" align="left" abbr="id" class="col1" colspan="7" ></td>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="3"></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >纸张编号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" id="veh_Zzbh" value="${zciInfoModel?.veh_Zzbh}"/></div></td>
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="7"></td>
    <td id ="td5" style="display:none" align="left" class="indexCol"></td>
    <td id ="td6" style="display:none" align="left" abbr="id" class="col0" colspan="7"></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zchgzbh"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Fzrq" ></div></td>
    <td id ="td7" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td id ="td8" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zchgzbh_Dp"></div></td>
    <td id ="td9" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td id ="td10" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Fzrq_Dp" ></div></td>
</tr>


<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc"></div></td>
    <td id ="td11" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td id ="td12" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clpp}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clmc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp"></div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc"></div></td>
    <td id ="td13" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td id ="td14" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp_Dp"></div></td>
    <td id ="td15" style="display:none" align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc_Dp"></div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clxh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clsbdh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clxh"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " name="veh_Clsbdh"></div></td>
    <td id ="td16" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td id ="td17" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clxh_Dp"></div></td>
    <td id ="td18" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td id ="td19" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " name="veh_Clsbdh_Dp"></div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol " >${zciInfoModel?.veh_Csys }</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol "><input type="text" id="veh_Csys_R" value="${zciInfoModel?.veh_Csys}"></div></td>
    <td id ="td20" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td id ="td21" style="display:none" align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol "><input type="text" id="veh_Csys_R_Dp" value="${zciInfoModel?.veh_Csys}"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpxh}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpid}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh"></div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid"></div></td>
    <td id ="td22" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td id ="td23" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh_Dp"></div></td>
    <td id ="td24" style="display:none" align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dphgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fdjxh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dphgzbh"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  id="veh_Fdjxh"></div></td>
    <td id ="td25" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td id ="td26" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dphgzbh_Dp"></div></td>
    <td id ="td27" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td id ="td28" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  id="veh_Fdjxh_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Fdjh" value="${zciInfoModel?.veh_Fdjh}"></div></td>
    <td id ="td29" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td id ="td30" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Fdjh_Dp" value="${zciInfoModel?.veh_Fdjh}"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Rlzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zciInfoModel?.veh_Pl}</div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zciInfoModel?.veh_Gl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Rlzl"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " id="veh_Pl" ></div></td>
    <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " id="veh_Gl"></div></td>
    <td id ="td31" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
    <td id ="td32" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Rlzl_Dp"></div></td>
    <td id ="td33" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td id ="td34" style="display:none" align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " id="veh_Pl_Dp" ></div></td>
    <td id ="td35" style="display:none" align="left" abbr="city" class="col1" ><div align="left" class="innerCol " id="veh_Gl_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Pfbz}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz"></div></td>
    <td id ="td36" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td id ="td37" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Yh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh"></div></td>
    <td id ="td38" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td id ="td39" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh_Dp"></div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkc}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkk}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Wkg}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbc}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbk}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zciInfoModel?.veh_Hxnbg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkc"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkk"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkg"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbc"></div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbk"></div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbg"></div></td>
    <td id ="td40" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td id ="td41" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkc_Dp"></div></td>
    <td id ="td42" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkk_Dp"></div></td>
    <td id ="td43" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkg_Dp"></div></td>
    <td id ="td44" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td id ="td45" style="display:none" align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbc_Dp"></div></td>
    <td id ="td46" style="display:none" align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbk_Dp"></div></td>
    <td id ="td47" style="display:none" align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbg_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Gbthps}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Lts}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Gbthps"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Lts" ></div></td>
    <td id ="td48" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td id ="td49" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Gbthps_Dp"></div></td>
    <td id ="td50" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td id ="td51" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Lts_Dp" ></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ltgg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ltgg"></div></td>
    <td id ="td52" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td id ="td53" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ltgg_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Qlj}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zciInfoModel?.veh_Hlj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Qlj"></div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Hlj"></div></td>
    <td id ="td54" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td id ="td55" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Qlj_Dp"></div></td>
    <td id ="td56" style="display:none" align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Hlj_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zj"></div></td>
    <td id ="td57" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td id ="td58" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zj_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zh"></div></td>
    <td id ="td59" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td id ="td60" style="display:none" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zh_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zs}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zxxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zs"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "id="veh_Zxxs"></div></td>
    <td id ="td61" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td id ="td62" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zs_Dp"></div></td>
    <td id ="td63" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td id ="td64" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "id="veh_Zxxs_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zbzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zzl"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zbzl"></div></td>
    <td id ="td65" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td id ="td66" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zzl_Dp"></div></td>
    <td id ="td67" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td id ="td68" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zbzl_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zzllyxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzzl"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zzllyxs"></div></td>
    <td id ="td69" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td id ="td70" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzzl_Dp"></div></td>
    <td id ="td71" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td id ="td72" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zzllyxs_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zqyzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zqyzzl"></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Bgcazzdyxzzl"></div></td>
    <td id ="td73" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td id ="td74" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zqyzzl_Dp"></div></td>
    <td id ="td75" style="display:none" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td id ="td76" style="display:none" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Bgcazzdyxzzl_Dp"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Jsszcrs}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Jsszcrs"></div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol " >36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
    <td id ="td77" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td id ="td78" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Jsszcrs_Dp"></div></td>
    <td id ="td79" style="display:none" align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol " >36.二维条码</div></td>
    <td id ="td80" style="display:none" align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzk"></div></td>
    <td id ="td81" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td id ="td82" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzk_Dp"></div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zgcs"></div></td>
    <td id ="td83" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td id ="td84" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zgcs_Dp"></div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clzzrq"></div></td>
    <td id ="td85" style="display:none" align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td id ="td86" style="display:none" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clzzrq_Dp"></div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8">备注：<div align="left" class="innerCol " id="veh_Bz"></div></td>
    <td id ="td87" style="display:none" align="left" abbr="id" class="col0" colspan="8">备注：<div align="left" class="innerCol " id="veh_Bz_Dp"></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " id="veh_clzzqyxx">车辆制造信息：</div></td>
    <td id ="td88" style="display:none" align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " id="veh_clzzqyxx_Dp">车辆制造信息：</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">业务员：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmanname" type="text" value=""></div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " >业务员电话：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmantel" type="text" value=""></div></td>
    <td id ="td89" style="display:none" align="left" abbr="id" class="col0" colspan="9"></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" vlign='middle' abbr="id" class="col0" colspan="1" style="color: red" >更换原因及要求<br>（不注明更换<br>原因的将予退回）:</td>
    <td align="left" vlign='middle' abbr="id" class="col0" colspan="16">
        <g:select id="replaceRemarkKind" name="replaceRemarkKind.id"
                  from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
                      dictionaryType { eq('code', 'replaceRemarkKind') }
                      order("ordernum", "asc")
                  })}" optionKey="dicValueNameC" optionValue="dicValueNameC" value="replaceRemarkKind" class="one-to-one"/>
        <g:textArea name="remark" style="height: 60px;width: 513px;"></g:textArea>
    </td>
</td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol "><form id="form" method="post">

        <input type="hidden" value="${zid}" id="zid">
        <input type="hidden" value="" id="newId">
        <input type="hidden" value="" id="newDpId">
        <input type="hidden" value="${statusWrite}" id="statusWrite">
        <input type="hidden" value="${turnOff}" id="turnOff">
        <span id="yincang"><input type='button' id="newOld" value="提交申请" style="width: 70px; height: 30px;" class="btn_basic blue_black"></span>
    </form>
    </a>
    </div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="left" class="innerCol " style="font-weight: 600;">备注：<br/>一、变更后的合格证各项参数只能与车辆公告相符，与实际车型不符的要以合格证为准。<br/>
    二、若因更换车辆手续等相关事宜引起的纠纷由申请单位负完全责任与山东五征集团有限公司无关。（若提出更换申请已表明承认上述条款）</div></td>
</tr>
</tbody>
</table>

</div>

</div>
</div>

</div>
<script>
    $(function() {
        $('#veh_Clsbdh').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'ZCInfo',action:'carsOfDistributor')}?random="+Math.random(),
            value:'',
        });
        $('#relate').omButton({
            width:40,
            onClick:function(){
                var veh_Clsbdh=$('#veh_Clsbdh').val();
                var turnOff=$('#turnOff').val();
                if(veh_Clsbdh!=''){
                    var url = "${createLink(controller:'ZCInfo',action:'zcinfoRondByDp')}?veh_Clsbdh="+veh_Clsbdh+'&turnOff='+turnOff;
                    window.location.href=url;
                }else{
                    alert('请输入车辆识别号！');
                }
            }
        });
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
                document.getElementById("page").style.width="1300px";
            }
        });
        $("#veh_Fdjh_Dp").change( function() {
            $('#veh_Fdjh').val($('#veh_Fdjh_Dp').val());
        });
        $("#veh_Fdjh").change( function() {
            $('#veh_Fdjh_Dp').val($('#veh_Fdjh').val());
        });
        $("#veh_Csys_R_Dp").change( function() {
            $('#veh_Csys_R').val($('#veh_Csys_R_Dp').val());
        });
        $("#veh_Csys_R").change( function() {
            $('#veh_Csys_R_Dp').val($('#veh_Csys_R').val());
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
        $('#newOld').click(function (){
                $(this).attr('disabled','disabled');
                $(this).css('background','#c1c1c1');
                $(this).css('color','#000');
                var newId=$('#newId').val();
                var veh_Clsbdh=$('#veh_Clsbdh').val();
                var veh_Zzbh=$('#veh_Zzbh').val();
                var veh_Need_Cer= $('#veh_Need_Cer').val();
                var veh_Fdjh= $('#veh_Fdjh').val();
                var salesmanname=$('#salesmanname').val();
                var salesmantel= $('#salesmantel').val();
                var remark= $('#replaceRemarkKind').val()+'('+$('#remark').val()+')';
                var zid =$('#zid').val();
                var veh_Csys_R=$("#veh_Csys_R").val();
                var statusWrite =$('#statusWrite').val();
                var newDpId=$('#newDpId').val();
                var veh_Fdjh_Dp = $('#veh_Fdjh_Dp').val();
                var veh_Csys_R_Dp=$("veh_Csys_R_Dp").val();
                var veh_Clsbdh_Dp=$('#veh_Clsbdh_Dp').val();
                var turnOff=$('#turnOff').val();
                var url= '${createLink(controller:'ZCInfoReplaceAuth',action:'zcinfoReplaceSave')}?veh_Fdjh='+veh_Fdjh+"&veh_Csys_R="+veh_Csys_R+'&salesmanname='+salesmanname+'&salesmantel='+salesmantel+'&remark='+remark+"&veh_Csys_R_Dp="+veh_Csys_R_Dp+"&veh_Fdjh_Dp="+veh_Fdjh_Dp;
                url=encodeURI(url);
                $.post(url,{newId:newId,veh_Clsbdh:veh_Clsbdh,veh_Zzbh:veh_Zzbh,zid:zid,veh_Need_Cer:veh_Need_Cer,statusWrite:statusWrite,veh_Clsbdh_Dp:veh_Clsbdh_Dp,newDpId:newDpId},function(data,textStatus){
                    if(textStatus){
                        $.omMessageBox.alert({
                            type:'success',
                            title:'提示',
                            content:data,
                            onClose:function(value){
                                var url = '${createLink(controller:'ZCInfo',action:'zcinfoRound')}?turnOff='+turnOff;
                                window.location.href=url;
                            }
                        });
                    }
                },'text');
        });

        $('#write').omButton({
            width:70,
            onClick:function(){
                var turnOff=$('#turnOff').val();
                var url = '${createLink(controller:'ZCInfo',action:'zcinfoRondWrite')}?turnOff='+turnOff;
                window.location.href=url;
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
        //绑定清空按钮事件
        $('#btn_clear').omButton({
            width:40,
            onClick:function(){
                $("#hidden_id").val("")
                var url = "${createLink(controller:'ZCInfo',action:'index_ZCI')}";
                window.location.href = url;
            }
        });

    })
    function changeData(data){
        $('#newId').val(data.id);
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
        //$('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
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
        $('#veh_Edzk_Dp').html(data.veh_Edzk) ;
        $('#veh_Zs_Dp').html(data.veh_Zs) ;
        $('#veh_Zqyzzl_Dp').html(data.veh_Zqyzzl) ;
        $('#veh_Bgcazzdyxzzl_Dp').html(data.veh_Bgcazzdyxzzl) ;
        $('#veh_Fdjxh_Dp').html(data.veh_Fdjxh)//發動機型號
        $('#veh_Clxh_Dp').html(data.veh_Clxh)     //车辆型号
        $('#veh_Zj_Dp').html(data.veh_Zj)            //轴距
        $('#veh_Pl_Dp').html(data.veh_Pl)           //排量和功率
        $('#veh_Gl_Dp').html(data.veh_Gl)
        $('#veh_Zzl_Dp').html(data.veh_Zzl)        //总质量
        $('#veh_Clmc_Dp').html(data.veh_Clmc)      //车辆名称
        $('#veh_Edzzl_Dp').html(data.veh_Edzzl)    //额定载质量
        $('#veh_Pfbz_Dp').html(data.veh_Pfbz)   //排放标准
        $('#veh_Hxnbc_Dp').html(data.veh_Hxnbc)     //货厢内部尺寸
        $('#veh_Hxnbk_Dp').html(data.veh_Hxnbk)     //货厢内部尺寸
        $('#veh_Hxnbg_Dp').html(data.veh_Hxnbg)      //货厢内部尺寸
        $('#veh_Zxxs_Dp').html(data.veh_Zxxs)     //转向形式
        $('#veh_Zbzl_Dp').html(data.veh_Zbzl)     //整备质量
        $('#veh_Zh_Dp').html(data.veh_Zh)      //轴荷
        $('#veh_Wkc_Dp').html(data.veh_Wkc)     //外廓尺寸
        $('#veh_Wkk_Dp').html(data.veh_Wkk)     //外廓尺寸
        $('#veh_Wkg_Dp').html(data.veh_Wkg)      //外廓尺寸
        $('#veh_Zzllyxs_Dp').html(data.veh_Zzllyxs)      //载质量利用系数
        $('#veh_Dpid_Dp').html(data.veh_Dpid)      //底盘ID
        $('#veh_Dpxh_Dp').html(data.veh_Dpxh)      //底盘型号
        //$('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#veh_Yh_Dp').html(data.veh_Yh)      //油耗
        $('#veh_Gbthps_Dp').html(data.veh_Gbthps)     //钢板弹簧片数
        $('#veh_Clpp_Dp').html(data.veh_Clpp)     //车辆品牌
        $('#veh_Lts_Dp').html(data.veh_Lts)      //轮胎数
        $('#veh_Clzzqymc_Dp').html(data.veh_Clzzqymc)     //车辆企业名称
        $('#veh_Zgcs_Dp').html(data.veh_Zgcs)      //最高车速
        $('#veh_Rlzl_Dp').html(data.veh_Rlzl)     //燃料种类
        $('#veh_Qlj_Dp').html(data.veh_Qlj)     //轮距
        $('#veh_Hlj_Dp').html(data.veh_Hlj)      //轮距
        $('#veh_Ltgg_Dp').html(data.veh_Ltgg)     //轮胎规格
        $('#veh_Jsszcrs_Dp').html(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#veh_Bz_Dp').html(data.veh_Bz)      //备注
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
