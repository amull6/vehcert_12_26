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
</tr>
<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
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
    <td align="left" abbr="id" class="col0" colspan="3" ><div align="left" class="innerCol ">公告信息：<input type="button" id="btn_select" value='选择'></div></td>


</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="3"></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >纸张编号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" id="veh_Zzbh" value="${zcinfoReplace?.veh_Zzbh}"/></div></td>
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="7"></td>
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
</tr>


<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc">${zcinfoReplace?.veh_Clzzqymc_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clpp}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clmc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp">${zcinfoReplace?.veh_Clpp_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc">${zcinfoReplace?.veh_Clmc_R}</div></td>

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

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Csys }</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol "><input type="text" id="veh_Csys_R" value="${zcinfoReplace?.veh_Csys_R}"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Dpxh}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Dpid}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh">${zcinfoReplace?.veh_Dpxh_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid">${zcinfoReplace?.veh_Dpid_R}</div></td>
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
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><input type="text" id="veh_Fdjh" value="${zcinfoReplace?.veh_Fdjh_R}"></div></td>
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
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Pfbz}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz">${zcinfoReplace?.veh_Pfbz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Yh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh">${zcinfoReplace?.veh_Yh_R}</div></td>

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
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkc_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkk_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Wkg_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbc_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbk_R}</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px">${zcinfoReplace?.veh_Hxnbg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Gbthps}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Lts}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Gbthps_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Lts_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Ltgg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Ltgg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Qlj}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Hlj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Qlj_R}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Hlj_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zj_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zs}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zxxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zs_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zxxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zbzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zbzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zzllyxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Zzllyxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zqyzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Bgcazzdyxzzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zqyzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReplace?.veh_Bgcazzdyxzzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Jsszcrs}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Jsszcrs_R}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Edzk_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Zgcs_R}</div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReplace?.veh_Clzzrq_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zcinfoReplace?.veh_Bz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">业务员：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmanname" type="text" value="${zcinfoReplace?.salesmanname}"></div></td>
    <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " >业务员电话：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmantel" type="text" value="${zcinfoReplace?.salesmantel}"></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17">备注：&nbsp;&nbsp;&nbsp;&nbsp;<g:textArea name="remark" value="${zcinfoReplace?.remark}"></g:textArea> </td>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol "><form id="form" method="post">
        <input type="hidden" value="${zid}" id="zid">
        <input type="hidden" value="" id="newId">
        <input type="hidden" value="${statusWrite}" id="statusWrite">
        <input type="hidden" value="${username}" id="username">
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
<script src="${resource(dir:'js/layer',file:'layer.js') }" type="text/javascript" ></script>
<link rel="stylesheet" href="${resource(dir:'js/layer/skin/default',file:'layer.css') }" type="text/css">
<script>
    $(function() {

        $('#btn_select').omButton({
            width:40,
            onClick:function(){
                var veh_Need_Cer=$('#veh_Need_Cer').val();
                var username=$('#username').val();
                var url = '${createLink(controller:'ZCInfoNoAuth',action:'toNoticeNoAuth')}?veh_Need_Cer='+veh_Need_Cer+'&username='+username;
                create(url);
            }
        });

        $('#gotoBack').omButton({
            width:70,
            onClick:function(){
                var username=$("#username").val();
                var url = '${createLink(controller:'ZCInfoNoAuth',action:'salesListNoAuth')}?searchType=${searchType}'+'&username='+username;
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
                var username=$("#username").val();

                %{--var url= '${createLink(controller:'ZCInfoReplaceAuth',action:'zinfoReplaceUpdate')}?newId='+newId+'&statusWrite='+statusWrite+'&veh_Clsbdh='+veh_Clsbdh+'&zid='+zid+'&veh_Zzbh='+veh_Zzbh+'&veh_Need_Cer='+veh_Need_Cer+'&veh_Fdjh='+veh_Fdjh+'&salesmanname='+salesmanname+"&veh_Csys_R="+veh_Csys_R+'&salesmantel='+salesmantel+'&remark='+remark;--}%
                var url= '${createLink(controller:'ZCInfoNoAuth',action:'zinfoReplaceUpdateNoAuth')}?veh_Fdjh='+veh_Fdjh+"&veh_Csys_R="+veh_Csys_R+'&salesmanname='+salesmanname+'&salesmantel='+salesmantel+'&remark='+remark+'&username='+username;
                url=encodeURI(url);
                $.post(url,{newId:newId,veh_Clsbdh:veh_Clsbdh,veh_Zzbh:veh_Zzbh,zid:zid,veh_Need_Cer:veh_Need_Cer,statusWrite:statusWrite},function(data,textStatus){
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
    function create(url){
//        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
//                'margin-right:-500px;text-align:center; width:100%;height:100%" '+
//                'scrolling="yes" src="'+url+'"></iframe>';
//        showDialogForIndex('page',1,content,"公告信息",1400,900);
        //iframe层
        var index=layer.open({
//            id:  tabId,
            type: 2,
            title: '公告选择',
            shadeClose: true,
            shade: 0.8,
            area: ['90%', '90%'],
            content: url, //iframe的url
            end:(function(){
//                var res = window["layui-layer-iframe2"].callbackdata();
//                //打印返回的值，看是否有我们想返回的值。
//                console.log(res);
//                alert(res);
            })

        });
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
