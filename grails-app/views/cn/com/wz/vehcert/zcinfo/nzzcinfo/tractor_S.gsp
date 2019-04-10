<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="常规汽车合格证生成" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <style>
    @media print{
        .noprint{
            display:none
        }
    }
    </style>
</head>
<body>

<div id="page" class="noprint" style="width:100%;background-color:white;margin:0px 0px">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <span id='span_publish'>
                <input id="btn_nextview" type="button" class="preView" value="递增查看"/>
                <input id="btn_preview" type="button" class="nextView" value="递减查看"/>
                <input id="btn_query" type="button"  class="edit" value="查看"/>
                <input id="recover" type="button"  class="edit" style="width:100px; height: 30px;" value="恢复编号"/>
                <input id="btn_save" type="button"  class="save" value="保存"/>
                <input id="btn_print" type="button" value="打印" class="view" />
            </span>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <form action="${createLink(controller: "NZInfo",action:"save")}" id="form">
                <g:hiddenField name="type" id='type' value="${type}"/>
                <g:hiddenField name="typeFlag" id='typeFlag' value="${car_storage_type}"/>
                <g:hiddenField name="car_storage_type" id='car_storage_type' value="${NZInfoInstance.car_storage_type}"/>
                <g:hiddenField name="infoid" value="${NZInfoInstance.id}"/>
                <g:hiddenField name="isupload" value="${NZInfoInstance.upload }"/>
                <g:hiddenField name="is_Exp" id='is_Exp' value="${NZInfoInstance.is_Exp}"/>
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row oddRow">
                                <td align="left" abbr="id" class="col0"><div align="middle" class="innerCol " style="width:200px">
                                    <input name="getValue" id="btn_select" style="height:50px;background-color:cyan"  value="按公告号提取数据" type="button" />
                                </div></td>
                                <td align="left" abbr="address" class="col2"><div align="middle" class="innerCol " style="width:200px">
                                    <input id="btn_calculate" type="button"  class="edit" style="width:100px; height: 50px;background-color:cyan" value="计算环保信息代码"/></div>
                                </td>
                                <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">
                                </div></td>
                            </tr>
                            <tr class="om-grid-row evenRow">
                                <g:set var="nzInfoService" bean="nzInfoService"/>
                                <td align="left" abbr="id" class="co10">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    车间计数编码
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Clbh" id="veh_Clbh" maxlength="50"   value="${NZInfoInstance?.veh_Clbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械环保代码
                                                </td>
                                                <td>
                                                    <g:textField name="envirCode" id="envirCode" maxlength="50"  value="${NZInfoInstance?.envirCode}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机型号
                                                </td>

                                                <td>
                                                    <g:textField name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50"  value="${NZInfoInstance?.veh_Fdjxh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    生产企业名称
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Scqymc" id="veh_Scqymc" maxlength="50"  value="${NZInfoInstance?.veh_Scqymc}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    品牌
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Pp" id="veh_Pp" maxlength="50"  value="${NZInfoInstance?.veh_Pp}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发证日期
                                                </td>

                                                <td>
                                                    <c:dataPicker name="veh_Fzrq" id="veh_Fzrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${NZInfoInstance?.veh_Fzrq}"></c:dataPicker>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    类型
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Lx" id="veh_Lx" maxlength="50"  value="${NZInfoInstance?.veh_Lx}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    型号名称
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Cx" id="veh_Cx" maxlength="50"  value="${NZInfoInstance?.veh_Cx}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    轮胎规格
                                                </td>
                                                <td>
                                                    <input name="veh_Ltgg" id="veh_Ltgg" maxlength="50" style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Ltgg}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    功率/kW
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Gl" id="veh_Gl" maxlength="50"  value="${NZInfoInstance?.veh_Gl}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="" class="indexCol">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    出厂编号
                                                </td>
                                                <td>
                                                    <input name="veh_new_clbh" id="veh_new_clbh" maxlength="50"  value="${NZInfoInstance?.veh_new_clbh}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机号码
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Fdjh" id="veh_Fdjh" maxlength="50" style="background-color:lightyellow"  value="${NZInfoInstance?.veh_Fdjh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机身颜色
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Jsys" id="veh_Jsys" maxlength="50"  style="background-color:lightyellow"  value="${NZInfoInstance?.veh_Jsys}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Dph" id="veh_Dph" maxlength="50"  style="background-color:lightyellow"  value="${NZInfoInstance?.veh_Dph}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    准乘人数
                                                </td>
                                                <td>
                                                    <input name="veh_Zcrs" id="veh_Zcrs" maxlength="50"  value="${NZInfoInstance?.veh_Zcrs}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    轮轴数
                                                </td>
                                                <td>
                                                    <input name="veh_Lzs" id="veh_Lzs" maxlength="50"   style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Lzs}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    轴距/mm
                                                </td>
                                                <td>
                                                    <input name="veh_Zj" id="veh_Zj" maxlength="50"     style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Zj}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    轮胎数
                                                </td>
                                                <td>
                                                    <input name="veh_Lts" id="veh_Lts" maxlength="50"  style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Lts}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    前轮距
                                                </td>
                                                <td>
                                                    <input name="veh_Qlj" id="veh_Qlj" maxlength="50" style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Qlj}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    后轮距
                                                </td>
                                                <td>
                                                    <input name="veh_Hlj" id="veh_Hlj" maxlength="50" style="background-color:greenyellow"  value="${NZInfoInstance?.veh_Hlj}">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="" class="indexCol">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    转向操纵方式
                                                </td>
                                                <td>
                                                    <input name="veh_Zxczxs" id="veh_Zxczxs" maxlength="50"  style="color: red" value="${NZInfoInstance?.veh_Zxczxs}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    履带数
                                                </td>
                                                <td>
                                                    <input name="veh_Lds" id="veh_Lds" maxlength="50" style="background-color:lightgreen" value="${NZInfoInstance?.veh_Lds}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    履带规格
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Ldgg" id="veh_Ldgg" maxlength="50" style="background-color:lightgreen" value="${NZInfoInstance?.veh_Ldgg}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    轨距/mm
                                                </td>
                                                <td>
                                                    <input name="veh_Gj" id="veh_Gj" maxlength="50"  style="background-color:lightgreen" value="${NZInfoInstance?.veh_Gj}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    外廓长/mm
                                                </td>
                                                <td>
                                                    <input name="veh_Wkc" id="veh_Wkc" maxlength="50"  value="${NZInfoInstance?.veh_Wkc}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    外廓宽/mm
                                                </td>
                                                <td>
                                                    <input name="veh_Wkk" id="veh_Wkk" maxlength="50"  value="${NZInfoInstance?.veh_Wkk}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    外廓高/mm
                                                </td>
                                                <td>
                                                    <input name="veh_Wkg" id="veh_Wkg" maxlength="50"  value="${NZInfoInstance?.veh_Wkg}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    标定牵引力/N
                                                </td>
                                                <td>
                                                    <input name="veh_Bdqyl" id="veh_Bdqyl" maxlength="50"  value="${NZInfoInstance?.veh_Bdqyl}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    最小使用质量/kg
                                                </td>
                                                <td>
                                                    <input name="veh_Zxsyzl" id="veh_Zxsyzl" maxlength="50"  value="${NZInfoInstance?.veh_Zxsyzl}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    最大允许载质量/kg
                                                </td>
                                                <td>
                                                    <input name="veh_Zdyyzzl" id="veh_Zdyyzzl" maxlength="50"  value="${NZInfoInstance?.veh_Zdyyzzl}">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="" class="indexCol">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                    <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="100"  value="${NZInfoInstance?.veh_Hgzbh}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    生产企业地址
                                                </td>
                                                <td>
                                                    <input name="veh_Scqydz" id="veh_Scqydz" maxlength="50"  value="${NZInfoInstance?.veh_Scqydz}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    联系方式
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Lxfs" id="veh_Lxfs" maxlength="50"  value="${NZInfoInstance?.veh_Lxfs}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械大类
                                                </td>
                                                <td>
                                                    <input name="veh_Jxdl" id="veh_Jxdl" maxlength="50"  style="color: red" value="${NZInfoInstance?.veh_Jxdl}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    燃料类型
                                                </td>
                                                <td>
                                                    <input name="veh_Rllx" id="veh_Rllx" maxlength="50"  value="${NZInfoInstance?.veh_Rllx}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械小类别
                                                </td>
                                                <td>
                                                    <input name="veh_Jxxlb" id="veh_Jxxlb" maxlength="50"  value="${NZInfoInstance?.veh_Jxxlb}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械产品的主要参数
                                                </td>
                                                <td>
                                                    <input name="veh_Zycs" id="veh_Zycs" maxlength="50"  value="${NZInfoInstance?.veh_Zycs}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    排放阶段
                                                </td>
                                                <td>
                                                    <input name="veh_Pfjd" id="veh_Pfjd" maxlength="50"  value="${NZInfoInstance?.veh_Pfjd}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    执行标准
                                                </td>
                                                <td>
                                                    <input name="veh_Zxbz" id="veh_Zxbz" maxlength="50"  value="${NZInfoInstance?.veh_Zxbz}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    排放标准号及排放阶段
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Pfbz" id="veh_Pfbz" maxlength="50"  value="${NZInfoInstance?.veh_Pfbz}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    生产日期
                                                </td>
                                                <td>
                                                    <c:dataPicker name="veh_Ccrq" id="veh_Ccrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${NZInfoInstance?.veh_Ccrq}"></c:dataPicker>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
            </form>
        </div>
        <div>
            <tr>
                <p style="color: red">
                    注意：
                </p>
                <p style="color: red" >
                    1、如果发动机号或者合格证编号为红色字体，说明该值是配置默认值,请注意完善该值。
                </p>
                <p style="color: red" >
                    2、默认值配置，请到【农装合格证生产配置】菜单具体配置
                </p>
            </tr>
        </div>
    </div>
</div>
%{--###################################打印显示的页面START#####################################--}%
<div style="margin-top:0px;margin-left: 5px;display: none" class="print" id='print'>
    <style>
    #customers
    {   font-family:Microsoft Yahei;
        font-size:13px;
        border-collapse:collapse;
        border:1px solid black;
    }
    </style>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <table id="customers">
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >1．合格证编号</td>
        <td colspan="3"style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Hgzbh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >2．发证日期</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center"> ${NZInfoInstance?.veh_Fzrq}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >3．生产企业名称</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Scqymc}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >4．品牌</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Pp}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >5．类型</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Lx}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >6．型号名称</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Cx}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >7．发动机型号</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Fdjxh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >8．发动机号码</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Fdjh}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >9．功率/kW</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Gl}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >10．排放标准号及排放阶段</td>
        <td colspan="3"style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Pfbz}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >11．出厂编号</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_new_clbh}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >
            <g:if test="${type=='tractor_A'||type=='tractor_B'||type=='tractor_C'}">
                12．底盘号
            </g:if>
            <g:else>
                12．机架号
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Dph}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >13．机身颜色</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Jsys}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >14．转向操纵方式</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Zxczxs}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >15．准乘人数</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Zcrs}</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >16．轮轴数</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Lzs==''||NZInfoInstance?.veh_Lzs==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Lzs}
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >17．轴距/mm</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Zj==''||NZInfoInstance?.veh_Zj==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Zj}
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >18．轮胎数</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Lts==''||NZInfoInstance?.veh_Lts==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Lts}
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >19．轮胎规格</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Ltgg==''||NZInfoInstance?.veh_Ltgg==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Ltgg}
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >20．轮距（前/后）/mm</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${(NZInfoInstance?.veh_Qlj==''||NZInfoInstance?.veh_Qlj==null)&&(NZInfoInstance?.veh_Hlj==''||NZInfoInstance?.veh_Hlj==null)}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Qlj}/${NZInfoInstance?.veh_Hlj}
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >21．履带数</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Lds==''||NZInfoInstance?.veh_Lds==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Lds}
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >22．履带规格</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center">
            <g:if test="${NZInfoInstance?.veh_Ldgg==''||NZInfoInstance?.veh_Ldgg==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Ldgg}
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >23．轨距/mm</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Gj==''||NZInfoInstance?.veh_Gj==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Gj}
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >24．外廓尺寸/mm</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Wkc}*${NZInfoInstance?.veh_Wkk}*${NZInfoInstance?.veh_Wkg}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >
            <g:if test="${type=='tractor_A'||type=='tractor_B'||type=='tractor_C'||type=='tractor_J'}">
                25．标定牵引力/N
            </g:if>
            <g:else>
                25．割台宽度/mm
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Bdqyl}</td>
        <td rowspan="4" style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >29．二维码/条码</td>
        <td rowspan="4" align="center" valign="center">
            <g:if test="${NZInfoInstance?.qr_name}">
                <img id="pic_fp_photo" src="${request.contextPath}/attachment/getImg?QRpiUrl=${NZInfoInstance?.qr_name}" width="120px" height="120px"/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >
            <g:if test="${type=='tractor_A'||type=='tractor_B'||type=='tractor_C'||type=='tractor_J'}">
                26．最小使用质量/kg
            </g:if>
            <g:else>
                26．喂入量（kg/s）/行数
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Zxsyzl}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >
            <g:if test="${type=='tractor_A'||type=='tractor_B'||type=='tractor_C'||type=='tractor_J'}">
                27．最大允许载质量/kg
            </g:if>
            <g:else>
                27．联合收割（获）机质量/kg
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >
            <g:if test="${NZInfoInstance?.veh_Zdyyzzl==''||NZInfoInstance?.veh_Zdyyzzl==null}">
                —
            </g:if>
            <g:else>
                ${NZInfoInstance?.veh_Zdyyzzl}
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px" >28．生产日期</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:32px"align="center" >${NZInfoInstance?.veh_Ccrq}</td>
    </tr>
    <tr>
        <td COLSPAN="4" style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:145px;height:33px" >30．执行标准</br> &nbsp;&nbsp;本产品经过出厂检验，符合 <span style="text-decoration: underline;"> ${NZInfoInstance?.veh_Zxbz}</span> 的要求，准予出厂，特此证明。</td>
    </tr>
    <tr rowspan="4">
        <td colspan="4"  valign="top" style="font-size:1em;  border:1px solid black;padding:2px 7px 2px 7px;width:145px;height:267px" >
            <a>31．企业信息</a> </br>
            <a>生产企业地址：${NZInfoInstance?.veh_Scqydz}</a> </br>
            <a>联系方式：${NZInfoInstance?.veh_Lxfs}</a> </br>
        </td>
    </tr>
</table>
</div>
<div style="margin-top:0px;margin-left: 5px;display: none" class="printExp" id='printExp'>
    <style>
    #customers
    {   font-family:Microsoft Yahei;
        font-size:13px;
        border-collapse:collapse;
        border:1px solid black;
    }
    </style>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <table id="customersExp">
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >1．Certificate No. 合格证编号</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Hgzbh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >2．Lssue date 发证日期</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center"> ${NZInfoInstance?.veh_Fzrq}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >3．Manufacturer 生产企业名称</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Scqymc}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >4．Brand 品牌</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Pp}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >5．Type 类型</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Lx}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >6．Model 型号名称</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Cx}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >7．Engine model 发动机型号</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Fdjxh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >8．Engine No. 发动机号码</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Fdjh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >9．Power 功率/kW</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Gl}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >10．Fuel 燃油类型</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >
            <g:if test='${NZInfoInstance?.veh_Rllx=='0'}'>
                Diesel
            </g:if>
            <g:elseif test='${NZInfoInstance?.veh_Rllx=='1'}'>
                Petrol
            </g:elseif>
            <g:elseif test='${NZInfoInstance?.veh_Rllx=='2'}'>
                Gas
            </g:elseif>
            <g:else>
                Other
            </g:else>
        </td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >11．Emission Standard 排放标准号及排放阶段</td>
        <td colspan="3"style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Pfbz}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >12．Factory 's serial No. 出厂编号</td>
        <td colspan="3"style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_new_clbh}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >
            <g:if test="${type=='tractor_A'||type=='tractor_B'||type=='tractor_C'}">
                13．Chassis No. 底盘号
            </g:if>
            <g:else>
                13．Chassis No. 底盘号
            </g:else>
        </td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Dph}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >14．Steering type 转向操纵方式</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Zxczxs}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >15．Person in Cab 准乘人数</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Zcrs}</td>
    </tr>
    <tr>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:200px;height:30px" >16．Date 生产日期</td>
        <td style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:500px;height:30px"align="center" >${NZInfoInstance?.veh_Ccrq}</td>
    </tr>
    <tr>
        <td COLSPAN="4" style="font-size:1em; border:1px solid black;padding:3px 7px 2px 7px;width:700px;height:33px" >17．Standard 执行标准</br> &nbsp;&nbsp;It is hereby certified that this product has passed factory inspection and meets the requirements of 本产品经过出厂检验，符合 <span style="text-decoration: underline;"> ${NZInfoInstance?.veh_Zxbz}</span> ，allowed the factory 准予出厂。</td>
    </tr>
    <tr rowspan="4">
        <td colspan="4"  valign="top" style="font-size:1em;  border:1px solid black;padding:2px 7px 2px 7px;width:700px;height:267px" >
            <a>18．Manufacturer's Information 企业信息</a> </br>
            <a>Address 生产企业地址：${NZInfoInstance?.veh_Scqydz}</a> </br>
            <a>Tel 联系方式：${NZInfoInstance?.veh_Lxfs}</a> </br>
        </td>
    </tr>
</table>
</div>
%{--###################################打印显示的页面END#####################################--}%

<script>
    var arrays=new Array();
    $(function() {
        $('#btn_select').click(function(){
            var veh_Clbh=$("#veh_Clbh").val()
            if(veh_Clbh.length!=7){
                alert('车辆流水号不符合要求')
                return;
            }
            var car_storage_type=$('#typeFlag').val();
            var url = '${createLink(controller:'NZInfo',action:'toNotice')}?car_storage_type='+car_storage_type;
            create(url,'公告信息',1440,900);
        });
        $('#veh_Jsys').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'NZInfo',action:'jsonColors')}?random="+Math.random(),
            value:'${NZInfoInstance?.veh_Jsys?NZInfoInstance?.veh_Jsys:'红'}'

        });
        $('#btn_save').click(function(){ //验证车间号码
            var isupload=  $('#isupload').val();
            if(isupload=='1'){
                alert('合格证已上传,不允许修改')
                return;
            }
            var veh_Cx= $('#veh_Cx').val();
            var type=$('#type').val();
            if(veh_Cx==null||$.trim(veh_Cx)==''){
                alert('保存前,请输入车型')
                return;
            }
            var veh_Hgzbh= $('#veh_Hgzbh').val();
            var veh_new_clbh=$("#veh_new_clbh").val();
            if(veh_Hgzbh.length!=18+veh_new_clbh.length){
                alert('合格证编号不符合要求')
                return;
            }
            if(veh_new_clbh==null||$.trim(veh_new_clbh)==''){
                alert('保存前,车辆编号不能为空')
                return;
            }
            var veh_Fdjh=$("#veh_Fdjh").val();
            if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                alert('保存前,发动机号不能为空')
                return;
            }
            var veh_Clbh=$("#veh_Clbh").val()
            if(veh_Clbh.length!=7){
                alert('车辆流水号不符合要求')
                return;
            }
            var veh_Fdjh=$("#veh_Scqymc").val();
            if(veh_Scqymc==null||$.trim(veh_Scqymc)==''){
                alert('保存前,生产企业名称不能为空')
                return;
            }
            var veh_Pp=$("#veh_Pp").val();
            if(veh_Pp==null||$.trim(veh_Pp)==''){
                alert('保存前,品牌不能为空')
                return;
            }
            var veh_Lx=$("#veh_Lx").val();
            if(veh_Lx==null||$.trim(veh_Lx)==''){
                alert('保存前,类型不能为空')
                return;
            }
            var veh_Gl=$("#veh_Gl").val();
            if(veh_Gl==null||$.trim(veh_Gl)==''){
                alert('保存前,功率不能为空')
                return;
            }
            var veh_Pfbz=$("#veh_Pfbz").val();
            if(veh_Pfbz==null||$.trim(veh_Pfbz)==''){
                alert('保存前,排放标准及阶段不能为空')
                return;
            }
            var veh_Jsys=$("#veh_Jsys").val();
            if(veh_Jsys==null||$.trim(veh_Jsys)==''){
                alert('保存前,机身颜色不能为空')
                return;
            }
            var veh_Zxczxs=$("#veh_Zxczxs").val();
            if(veh_Zxczxs==null||$.trim(veh_Zxczxs)==''){
                alert('保存前,转向操纵形式不能为空')
                return;
            }
            var veh_Wkc=$("#veh_Wkc").val();
            if(veh_Wkc==null||$.trim(veh_Wkc)==''){
                alert('保存前,外廓长不能为空')
                return;
            }
            var veh_Wkk=$("#veh_Wkk").val();
            if(veh_Wkk==null||$.trim(veh_Wkk)==''){
                alert('保存前,外廓宽不能为空')
                return;
            }
            var veh_Wkg=$("#veh_Wkg").val();
            if(veh_Wkg==null||$.trim(veh_Wkg)==''){
                alert('保存前,外廓高不能为空')
                return;
            }
            var veh_Bdqyl=$("#veh_Bdqyl").val();
            if(veh_Bdqyl==null||$.trim(veh_Bdqyl)==''){
                alert('保存前,标定牵引力不能为空')
                return;
            }
            var veh_Zxsyzl=$("#veh_Zxsyzl").val();
            if(veh_Zxsyzl==null||$.trim(veh_Zxsyzl)==''){
                alert('保存前,最小使用质量不能为空')
                return;
            }
            var veh_Scqydz=$("#veh_Scqydz").val();
            if(veh_Scqydz==null||$.trim(veh_Scqydz)==''){
                alert('保存前,生产企业地址不能为空')
                return;
            }
            var veh_Lxfs=$("#veh_Lxfs").val();
            if(veh_Lxfs==null||$.trim(veh_Lxfs)==''){
                alert('保存前,联系方式不能为空')
                return;
            }
            document.forms[0].submit();
        });
        $('#btn_query').click(function(){
            var url = '${createLink(controller:'NZInfo',action:'search')}';
            var  veh_Clbh=$('#veh_Clbh').val();
            var type=$('#type').val();
            var car_storage_type=$('#typeFlag').val();
            if(veh_Clbh.length==7){
                window.location.href = url+'?veh_Clbh='+veh_Clbh+'&type='+type+'&car_storage_type='+car_storage_type;
            }
        });
        $('#recover').click(function(){
            var url = '${createLink(controller:'NZInfo',action:'search')}';
            var  veh_Clbh=$('#veh_Clbh').val();
            var type=$('#type').val();
            var car_storage_type=$('#typeFlag').val();
            window.location.href = url+'?recover=0&veh_Clbh='+veh_Clbh+'&type='+type+'&car_storage_type='+car_storage_type;
        });
        $('#btn_nextview').click(function(){
            var url = '${createLink(controller:'NZInfo',action:'search')}';
            var  veh_Clbh=$('#veh_Clbh').val();
            var type=$('#type').val();
            if(veh_Clbh.length==7){
                window.location.href = url+'?direction=1&veh_Clbh='+veh_Clbh+'&type='+type;
            }
        });
        $('#btn_preview').click(function(){
            var url = '${createLink(controller:'NZInfo',action:'search')}';
            var  veh_Clbh=$('#veh_Clbh').val();
            var type=$('#type').val();
            var car_storage_type=$('#typeFlag').val();
            if(veh_Clbh.length==7){
                window.location.href = url+'?direction=-1&veh_Clbh='+veh_Clbh+'&type='+type+'&car_storage_type='+car_storage_type;
            }
        });
        $('#btn_print').click(function(){
            var isupload=  $('#isupload').val();
            var is_Exp=  $('#is_Exp').val();
            if(is_Exp=='1'){
                var d=document.getElementById('printExp');
            }else{
                var d=document.getElementById('print');
            }
            d.style.display ="block";
            if(isupload=='1'){
                alert('合格证已上传,不允许打印')
                return;
            }
            var infoid=$('#infoid').val();
            if(infoid!=null&&infoid!=''){  //先调用虚拟 再调用正式
                window.print();
                if(is_Exp=='1'){
                    var d=document.getElementById('printExp');
                }else{
                    var d=document.getElementById('print');
                }
                d.style.display ="none";
                printAll();
            }else{
                alert('请先保存')
            }
        });
        $('#btn_calculate').click(function(){
            calculateVin();
        });
    });

    function printAll(){
        var params=$("#form").serialize();
        var veh_Clbh=$('#veh_Clbh').val();
        var type=$('#type').val();
        var url = '${createLink(controller:'NZInfo',action:'print')}?isprint=1';
        var dialogId=showTipDialog();
        $.post(url,params,function(data,textStatus){
            parent.closeDialog(dialogId);
            var d=eval("("+data+")");
            if(d.flag=='successed'){
                $.omMessageBox.alert({
                    content: d.msg,
                    onClose:function(v){
                        var url = '${createLink(controller:'NZInfo',action:'search')}';
                        if(veh_Clbh.length>0){
                            window.location.href = url+'?type='+type+'&intI=1&veh_Clbh='+veh_Clbh;
                        }
                    }
                });
            }else{
                alert(d.msg);
            }
        },'text');
    }
    function changeData(data){
        if(data.car_storage_type=='3'||data.car_storage_type=='4'||data.car_storage_type=='5'){
            if($('#veh_Clbh').val().length==7){
                $('#veh_Dph').val($('#veh_Clbh').val().substring(0,1)+data.veh_Pfjd+$('#veh_Clbh').val().substring(1,7))
            }else if($('#veh_Clbh').val().length==8){
                $('#veh_Dph').val($('#veh_Clbh').val().substring(0,1)+data.veh_Pfjd+$('#veh_Clbh').val().substring(2,8))
            }
        }
        $('#veh_Cx').val(data.veh_Clxh)
        $('#veh_Fdjxh').val(data.veh_Fdjxh)
        $('#veh_Rllx').val(data.veh_Rllx)
        $('#veh_Jxdl').val(data.veh_Jxdl)
        $('#veh_Jxxlb').val(data.veh_Jxxlb)
        $('#veh_Zycs').val(data.veh_Zycs)
        $('#veh_Pfjd').val(data.veh_Pfjd)
        $('#car_storage_type').val(data.car_storage_type)
        $('#veh_Scqymc').val(data.veh_Scqymc)
        $('#veh_Pp').val(data.veh_Pp)
        $('#veh_Lx').val(data.veh_Lx)
        $('#veh_Cx').val(data.veh_Clxh)
        $('#veh_Gl').val(data.veh_Gl)
        $('#veh_Jsys').val(data.veh_Jsys)
        $('#veh_Zxczxs').val(data.veh_Zxczxs)
        $('#veh_Zcrs').val(data.veh_Zcrs)
        $('#veh_Lzs').val(data.veh_Lzs)

        $('#veh_Zj').val(data.veh_Zj)
        $('#veh_Lts').val(data.veh_Lts)
        $('#veh_Ltgg').val(data.veh_Ltgg)
        $('#veh_Qlj').val(data.veh_Qlj)
        $('#veh_Hlj').val(data.veh_Hlj)
        $('#veh_Lds').val(data.veh_Lds)
        $('#veh_Ldgg').val(data.veh_Ldgg)
        $('#veh_Gj').val(data.veh_Gj)
        $('#veh_Wkc').val(data.veh_Wkc)
        $('#veh_Wkk').val(data.veh_Wkk)
        $('#veh_Wkg').val(data.veh_Wkg)
        $('#veh_Bdqyl').val(data.veh_Bdqyl)
        $('#veh_Zxsyzl').val(data.veh_Zxsyzl)
        $('#veh_Scqydz').val(data.veh_Scqydz)
        $('#veh_Lxfs').val(data.veh_Lxfs)
        $('#veh_Pfbz').val(data.veh_Pfbz)
        $('#veh_Zxbz').val(data.veh_Zxbz)
        $('#veh_Zdyyzzl').val(data.veh_Zdyyzzl)
        $('#typeFlag').val(data.car_storage_type)
        $('#is_Exp').val(data.is_Exp)
    }
    function calculateVin(){
        var url = '${createLink(controller:'NZInfo',action:'calculateVIN')}';
        var type= $('#type').val();
        var veh_Pp= $('#veh_Pp').val().trim();
        var creditCode
        var veh_Qydm
        if(veh_Pp.indexOf("TS牌") != -1){
            creditCode ='913708827953187247'
            veh_Qydm ='H36'
        }else if(veh_Pp.indexOf("五征牌") != -1){
            creditCode ='91371121165881395X'
            veh_Qydm ='576'
        }else{
            alert('请检查当前选择公告的车辆品牌是否正确')
            return;
        }
        var veh_Rllx=$('#veh_Rllx').val();
        var veh_Jxdl=$('#veh_Jxdl').val();
        var veh_Jxxlb=$('#veh_Jxxlb').val();
        var veh_Zycs=$('#veh_Zycs').val();
        var veh_Pfjd=$('#veh_Pfjd').val();
        var veh_Clbh=$('#veh_Clbh').val();
        var veh_Fzrq=$('#veh_Fzrq').val();
        var car_storage_type=$('#car_storage_type').val();
        var type=$('#type').val();
        $.post(url,{'veh_Rllx':veh_Rllx,'veh_Jxdl':veh_Jxdl,'veh_Jxxlb':veh_Jxxlb,'veh_Zycs':veh_Zycs,'veh_Pfjd':veh_Pfjd,'veh_Clbh':veh_Clbh,'car_storage_type':car_storage_type,'veh_Fzrq':veh_Fzrq,'veh_Qydm':veh_Qydm},function(data){
            if(data.flag=='0'){
                alert(data.msg);
            }else if(data.flag=='1'){
                $('#envirCode').val(data.envirCode);
                $('#veh_new_clbh').val(data.envirCode);
                $('#veh_Hgzbh').val(creditCode+data.envirCode);
            }
        },'json');
    }
    function create(url,title,width,height){
        var tabId=window.name;
        var flag=url.indexOf('?');
        if(flag>0){
            url+="&tabId="+tabId;
        }else{
            url+="?tabId="+tabId;
        }

        //打开弹出框
        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
            'margin-right:-500px;text-align:center; width:100%;height:100%" '+
            'scrolling="yes" src="'+url+'"></iframe>';
        parent.showDialog(1,content,title,width,height);
    }
</script>
</body>
</html>
