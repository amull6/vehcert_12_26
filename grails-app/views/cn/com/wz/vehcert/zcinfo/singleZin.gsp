<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
    <style type="text/css">
    #grid{width: 575px;}

    </style>
</head>
<body>
<div id="page" style="background-color:white;">
<div id="center-panel" style="margin-left:4px;border:1px;">


<div style="margin-right:8px;margin-top:8px;">

<div class="om-grid om-widget om-widget-content" style="height: 100%;">
<div class="hDiv om-state-default">
    <div class="hDivBox"></div>
</div>
<div class="bDiv" style="float:left; height:100%">

    <table id="grid"  cellpadding="0" cellspacing="0" border="0">
        <tbody>
        <tr class="om-grid-row evenRow">
            <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >合格证信息</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:80px">1、合格证编号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zchgzbh}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:65px">2、发证日期</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fzrq}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzqymc}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clpp}</div></td>
            <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clmc}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clxh}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车身颜色</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Csys }</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车辆识别代号/车架号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clsbdh}</div></td>
            <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Cjh}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpxh}</div></td>
            <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dpid}</div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Dphgzbh}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Fdjxh}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Fdjh}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">12.燃料种类</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Rlzl}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
            <td align="left" abbr="city" class="col1" colspan="2"><div align="left" class="innerCol " >${zciInfoModel?.veh_Pl}</div></td>
            <td align="left" abbr="city" class="col1" ><div align="left" class="innerCol " >${zciInfoModel?.veh_Gl}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Pfbz}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Yh}</div></td>

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
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">18.钢板弹簧片数(片)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Gbthps}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">19.轮胎数</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Lts}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Ltgg}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Qlj}</div></td>
            <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zciInfoModel?.veh_Hlj}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zj}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zh}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zs}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zxxs}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">26.总质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zzl}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">27.整备质量(kg)</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zbzl}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">28.额定载质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzzl}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">29.载质量利用系数</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Zzllyxs}</div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zqyzzl}</div></td>
            <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大允许总质量(kg)</div></td>
            <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zciInfoModel?.veh_Bgcazzdyxzzl}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Jsszcrs}</div></td>
            <td align="left" abbr="id" class="col0" rowspan="4"><div align="left" class="innerCol ">36.二维条码</div></td>
            <td align="left" abbr="city" class="col1" colspan="3" rowspan="4"><div align="left" class="innerCol " ></div></td>
        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Edzk}</div></td>

        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Zgcs}</div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
            <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zciInfoModel?.veh_Clzzrq}</div></td>

        </tr>
        <tr class="om-grid-row oddRow">
            <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">备注：&nbsp;&nbsp;&nbsp;&nbsp;${zciInfoModel?.veh_Bz}</div></td>
        </tr>
        <tr class="om-grid-row evenRow">
            <td align="center" abbr="id" class="col0" colspan="8"><div align="center" class="innerCol ">
                <a id="btn_return"  style="line-height:30px;font-size: 14px;" class="btn_basic blue_black">${message(code: 'default.button.return.label', default: 'return')}</a>
            </div></td>
        </tr>

        </tbody>
    </table>
</div>

<div>


</div>
</div>
</div>
</div>
</div>

<script>
    $('#btn_return').click(function(){

        var url="${createLink(controller:'ZCInfo',action:'zcinfoSearchAll')}";
        window.location.href=url;

    });
</script>
</body>
</html>