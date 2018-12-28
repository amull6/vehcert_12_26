
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
<div id="page" style="width:1350px;background-color:white;margin:0px 0px;overflow:visible;">
    <div style="margin-right:8px;padding-top:8px; height:98%;  width:100%;">

        <div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


            <div class="bDiv" style="width:100%; height:98%;width: 100%;">
                <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <tr class="om-grid-row evenRow">
                        <th align="center" class="indexCol" colspan="17"><div align="center" class="innerCol " >A4合格证更换正式合格证申请</div></th>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原A4合格证</div></td>
                        <td align="center" class="indexCol" rowspan="30"><div align="center" class="innerCol " ></div></td>
                        <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后正式合格证</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:115px">完整合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><input type="text" id="veh_Zchgzbh_0" value="${zciInfoModel?.veh_Zchgzbh_0}"><input type="button" id="relate" value='关联'> </div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol "  >是否将A4整车和底盘合格证都更换成正式</div>
                            <div align="left" class="innerCol " style="width:120px">
                                <select id="isAll">
                                    <option selected="selected" value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </td>
                        <td align="left" abbr="id" class="col0"></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " ></div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol "  ></div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"></td>
                        <td align="left" abbr="id" class="col0" colspan="3"></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >纸张编号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " ><input type="text" id="veh_Zzbh" value="${zciInfoModel?.veh_Zzbh}"/></div></td>
                        <td align="left" class="indexCol"></td>
                        <td align="left" abbr="id" class="col0" colspan="7"></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fzrq}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Fzrq" >${zciInfoModel?.veh_Fzrq}</div></td>
                    </tr>


                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzqymc}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Clzzqymc">${zciInfoModel?.veh_Clzzqymc}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clpp}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clmc}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clpp">${zciInfoModel?.veh_Clpp}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Clmc">${zciInfoModel?.veh_Clmc}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clxh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><input type="text" id="vin" value="${zciInfoModel?.veh_Clsbdh}"></div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clxh" ><input readonly="ture"  type="text" id="clxh" value="${zciInfoModel?.veh_Clxh}"></div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " name="veh_Clsbdh" >${zciInfoModel?.veh_Clsbdh}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
                        <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol " >${zciInfoModel?.veh_Csys }</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
                        <td align="left" abbr="city" class="col1" colspan="8"><div align="left" class="innerCol ">${zciInfoModel?.veh_Csys }</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpxh}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpid}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dpxh"><input readonly="ture"  type="text" id="dpxh" value="${zciInfoModel?.veh_Dpxh}"></div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dpid"><input readonly="ture"  type="text" id="dpid" value="${zciInfoModel?.veh_Dpid}"></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dphgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fdjxh}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Dphgzbh">${zciInfoModel?.veh_Dphgzbh}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "  id="veh_Fdjxh">${zciInfoModel?.veh_Fdjxh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Rlzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zciInfoModel?.veh_Pl}</div></td>
                        <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zciInfoModel?.veh_Gl}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Rlzl">${zciInfoModel?.veh_Rlzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " id="veh_Pl" >${zciInfoModel?.veh_Pl}</div></td>
                        <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " id="veh_Gl">${zciInfoModel?.veh_Gl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Pfbz}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Pfbz">${zciInfoModel?.veh_Pfbz}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Yh}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Yh">${zciInfoModel?.veh_Yh}</div></td>

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
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkc">${zciInfoModel?.veh_Wkc}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkk">${zciInfoModel?.veh_Wkk}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:50px" id="veh_Wkg">${zciInfoModel?.veh_Wkg}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbc">${zciInfoModel?.veh_Hxnbc}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbk">${zciInfoModel?.veh_Hxnbk}</div></td>
                        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:50px" id="veh_Hxnbg">${zciInfoModel?.veh_Hxnbg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Gbthps}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Lts}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Gbthps">${zciInfoModel?.veh_Gbthps}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Lts" >${zciInfoModel?.veh_Lts}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ltgg}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ltgg">${zciInfoModel?.veh_Ltgg}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Qlj}</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zciInfoModel?.veh_Hlj}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Qlj">${zciInfoModel?.veh_Qlj}</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Hlj">${zciInfoModel?.veh_Hlj}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zj}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zj">${zciInfoModel?.veh_Zj}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zh}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Zh">${zciInfoModel?.veh_Zh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zs}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zxxs}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zs">${zciInfoModel?.veh_Zs}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "id="veh_Zxxs">${zciInfoModel?.veh_Zxxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zbzl}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zzl">${zciInfoModel?.veh_Zzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zbzl">${zciInfoModel?.veh_Zbzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zzllyxs}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzzl">${zciInfoModel?.veh_Edzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Zzllyxs">${zciInfoModel?.veh_Zzllyxs}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zqyzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zqyzzl">${zciInfoModel?.veh_Zqyzzl}</div></td>
                        <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " id="veh_Bgcazzdyxzzl">${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Jsszcrs}</div></td>
                        <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Jsszcrs">${zciInfoModel?.veh_Jsszcrs}</div></td>
                        <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol " >36.二维条码</div></td>
                        <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzk}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Edzk">${zciInfoModel?.veh_Edzk}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zgcs}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Zgcs">${zciInfoModel?.veh_Zgcs}</div></td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzrq}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol " id="veh_Clzzrq">${zciInfoModel?.veh_Clzzrq}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">37.公告批次</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ggpc}</div></td>
                        <td align="left" class="indexCol"><div align="left" class="innerCol ">37.公告批次</div></td>
                        <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol " id="veh_Ggpc" ><input readonly="ture"  type="text" id="ggpc" value="${zciInfoModel?.veh_Ggpc}"></div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
                        <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol " id="veh_Bz">备注：${zciInfoModel?.veh_Bz}</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                        <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " id="veh_clzzqyxx">车辆制造信息：</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol ">业务员：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmanname" type="text" value=""></div></td>
                        <td align="left" abbr="id" class="col0" colspan="9"><div align="left" class="innerCol " >业务员电话：&nbsp;&nbsp;&nbsp;&nbsp;<input  id="salesmantel" type="text" value=""></div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="center" vlign='middle' abbr="id" class="col0" colspan="1" style="color: red" >更换原因及要求<br>（不注明更换<br>原因的将予退回）:</td>
                        <td  align="left" vlign='middle' abbr="id" class="col0" colspan="14"><g:textArea name="remark" style="height: 60px;width: 518px;"></g:textArea> </td>
                    </td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol "><form id="form" method="post">
                            <input type="hidden" value="${replaceType}" name='replaceType' id="replaceType">
                            <input type="hidden" value="${zid}" id="zid">
                            <input type="hidden" value="" id="otherid">
                            <input type="hidden" value="" id="newId">
                            <input type="hidden" value="${zciInfoModel?.veh_Clztxx}" id="veh_Clztxx_R">
                            <input type="hidden" value="${statusWrite}" id="statusWrite">
                            <input type="hidden" value="${turnOff}" id="turnOff">
                            <input type="hidden" value="${formal}" id="formal">
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
        $('#veh_Zchgzbh_0').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'ZCInfo',action:'hgzbhOfDistributor')}?random="+Math.random(),
            value:'',
        });
        $('#relate').omButton({
            width:40,
            onClick:function(){
                var veh_Zchgzbh_0=$('#veh_Zchgzbh_0').val();
                var turnOff=$('#turnOff').val();
                var formal=$('#formal').val();
                var replaceType=$('#replaceType').val()
                if(veh_Zchgzbh_0!=''){
                    var url = "${createLink(controller:'ZCInfo',action:'zcinfoByZchgzbh')}?veh_Zchgzbh_0="+veh_Zchgzbh_0+'&turnOff='+turnOff+'&formal='+formal+'&replaceType='+replaceType;
                    window.location.href=url;
                }else{
                    alert('请输入完整合格证编号！');
                }
            }
        });
        $('#newOld').click(function (){
            $(this).attr('disabled','disabled');
            $(this).css('background','#c1c1c1');
            $(this).css('color','#000');

            var formal=$('#formal').val();
            var replaceType=$('#replaceType').val()
            var veh_Zzbh=$('#veh_Zzbh').val();
            var salesmanname=$('#salesmanname').val();
            var salesmantel= $('#salesmantel').val();
            var remark= $('#remark').val();
            var zid =$('#zid').val();
            var otherid=$('#otherid').val();
            var isAll=$('#isAll').val();
            var statusWrite =$('#statusWrite').val();
            var turnOff=$('#turnOff').val();
            var veh_Clxh=$('#clxh').val();
            var veh_Ggpc=$('#ggpc').val();
            var veh_Clztxx=$('#veh_Clztxx_R').val();
            var veh_Dpid=$('#dpid').val();
            if(isAll=='1'&&veh_Clztxx=='DP'){
                alert('整车和底盘都更换正时合格证，请用整车合格证申请')
                return
            }
            $.post("${createLink(controller: 'ZCInfoReplaceAuth',action:'checkGgpc')}",{veh_Clxh:veh_Clxh,veh_Ggpc:veh_Ggpc,veh_Clztxx:veh_Clztxx,veh_Dpid:veh_Dpid,otherid:otherid,isAll:isAll},function(dd,textStatus){
                var d2=eval(dd);
                if(d2.flag=='failed'){
                    alert(d2.msg);
                    return;
                }else{
                    var url= '${createLink(controller:'ZCInfoReplaceAuth',action:'zcinfoPdfReplaceSave')}?salesmanname='+salesmanname+'&salesmantel='+salesmantel+'&remark='+remark;
                    url=encodeURI(url);
                    $.post(url,{veh_Zzbh:veh_Zzbh,zid:zid,otherid:otherid,isAll:isAll,statusWrite:statusWrite,replaceType:replaceType,formal:formal},function(data,textStatus){
                        if(textStatus){
                            $.omMessageBox.alert({
                                type:'success',
                                title:'提示',
                                content:data,
                                onClose:function(value){
                                    var url = '${createLink(controller:'ZCInfo',action:'zcinfoRoundForPdf')}?turnOff='+turnOff+'&replaceType='+replaceType+'&formal='+formal;
                                    window.location.href=url;
                                }
                            });
                        }
                    },'text');
                }
            });
        });
        $("#isAll").change( function() {
            var isAll=$('#isAll').val()
            if(isAll=='1') {
                var veh_Zchgzbh_0 =$('#veh_Zchgzbh_0').val()
                var veh_Clsbdh =$('#vin').val()
                var url = "${createLink(controller:'ZCInfo',action:'checkZcinfoCount')}";
                $.post(url,{'veh_Zchgzbh_0':veh_Zchgzbh_0,'veh_Clsbdh':veh_Clsbdh,},function(data,textStatus){
                     if(data.count=='2'){
                         $('#otherid').val(data.msg)
                     }else{
                        alert(data.msg);
                        $('#otherid').val('1')
                     }
                },'json');
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
