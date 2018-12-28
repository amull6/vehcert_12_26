<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>公告信息生成</title>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'insideNote.label', default: 'InsideNote')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<form style="margin-left:10px;width:950px;" id="form" action="${createLink(controller:'preCarStorage',action:'generateCarStorage')}"  method="post">
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:hasErrors bean="${preCarStorage }">
    <ul class="errors" role="alert">
        <g:eachError bean="${preCarStorage }" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<div style="" class="om-grid om-widget om-widget-content">
<div class="bDiv" style="width: auto;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <td align="center" class="indexCol" colspan="4">
        <div align="center" class="innerCol " style='min-height: 300px;'>
            <img style="width:300px;height:300px;" src="${createLink(controller:'common',action:'getImg')}?picPath=${grailsApplication.config.upload.rootDir+ preCarStorage?.veh_y45pic}" />
            <img style="width:300px;height:300px;" src="${createLink(controller:'common',action:'getImg')}?picPath=${grailsApplication.config.upload.rootDir+preCarStorage ?.veh_zhpic}" />
            <img style="width:300px;height:300px;" src="${createLink(controller:'common',action:'getImg')}?picPath=${grailsApplication.config.upload.rootDir+preCarStorage ?.veh_fhpic}" /> <br/>
            <img style="width:450px;height:450px;" src="${createLink(controller:'common',action:'getImg')}?picPath=${grailsApplication.config.upload.rootDir+preCarStorage ?.veh_pic1}" />
            <img style="width:450px;height:450px;" src="${createLink(controller:'common',action:'getImg')}?picPath=${grailsApplication.config.upload.rootDir+preCarStorage ?.veh_pic2}" />
        </div>
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">车辆制造企业名称</div></td>
    <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Clzzqymc}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">产品公告号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Cpggh}
    </div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">公告生效日期</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Ggsxrq}
    </div></td>

</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">批次</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Ggpc}
    </div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">产品ID</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_cpid}
    </div></td>

</tr>

<tr>
    <td align="left"><div style="text-align: left;" class="om-resizable">企业ID</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
        ${preCarStorage ?.veh_Qyid}
    </div></td>
    <td align="left"><div style="text-align: left; width: 80px;" class="om-resizable">车辆分类</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
        ${preCarStorage ?.veh_Clfl}
    </div></td>
</tr>

<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆名称</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Clmc}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">车辆品牌</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Clpp}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Clxh}

    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">车身颜色</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Csys}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">底盘型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Dpxh}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">底盘ID</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Dpid}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Fdjxh}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">燃料种类</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Rlzl}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">排量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Pl}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">功率</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Gl}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">转向形式</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zxxs}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">前轮距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Qlj}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">后轮距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Hlj}
    </td>

    <td align="left"><div style="text-align: left; " class="om-resizable">轮胎数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Lts}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">轮胎规格</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Ltgg}
    </div></td>

    <td align="left"><div style="text-align: left; " class="om-resizable">钢板弹簧片数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Gbthps}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">轴距</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zj}
    </td>

    <td align="left"><div style="text-align: left; " class="om-resizable">轴荷</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zh}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left;" class="om-resizable">轴数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zs}
    </td>

    <td align="left"><div style="text-align: left; " class="om-resizable">外廓长</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Wkc}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">外廓宽</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Wkk}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">外廓高</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Wkg}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部长</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Hxnbc}
    </div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部宽</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Hxnbk}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部高</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Hxnbg}
    </div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">最高车速</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zgcs}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">总质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zzl}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">额定载质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Edzzl}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">整备质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zbzl}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">载质量利用系数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zzllyxs}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">准牵引总质量</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Zqyzzl}
        <td align="left"><div style="text-align: left; " class="om-resizable">额定载客</div></td>
        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
            ${preCarStorage ?.veh_Edzk}
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">半挂车按座</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Bgcazzdyxzzl}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室准乘人数</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Jsszcrs}
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">最大净功率</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_zdjgl}
        <td align="left"><div style="text-align: left; " class="om-resizable">后制动方式</div></td>
        <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
            ${preCarStorage ?.veh_Hzdfs}
</tr>

<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">接近离去角</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_jjlqj}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">前悬后悬</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_qxhx}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left;" class="om-resizable">底盘企业</div></td>
    <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_dpqy}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">企业标准</div></td>
    <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Qybz}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">产品生产地址</div></td>
    <td align="left" abbr="city" colspan="3" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Cpscdz}
    </td></tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">企业其它信息</div></td>
    <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Qyqtxx}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">排放标准</div></td>
    <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Pfbz}
    </td>
</tr>

<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Jss}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室类型</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Jsslx}
    </td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">油耗</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Yh}
    </div></td>
    <td align="left"><div style="text-align: left; " class="om-resizable">反光标示企业</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_fgbsqy}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">反光标示商标</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_fgbssb}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">反光标示型号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_fgbsxh}
    </div></td>
</tr>
<tr>
    <td align="left"><div style="text-align: left; " class="om-resizable">车辆识别代号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_clsbdh}
    </td>
    <td align="left"><div style="text-align: left; " class="om-resizable">产品号</div></td>
    <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_cph}
    </div></td>
</tr>

<tr>

    <td align="left"><div style="text-align: left; " class="om-resizable">车辆状态信息</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">
        ${preCarStorage ?.veh_Clztxx}
    </td>
</tr>
<tr>

    <td align="left"><div style="text-align: left; " class="om-resizable">其它</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" ><div align="left" class="innerCol " style="width:750px;word-wrap:break-word;overflow:auto;">
        ${preCarStorage ?.veh_other}
    </div> </td>
</tr>
<tr>

    <td align="left"><div style="text-align: left; " class="om-resizable">备注</div></td>
    <td align="left" colspan='3' abbr="city" class="col1"><div align="left" class="innerCol " style="width:750px;word-wrap:break-word;overflow:auto;">
        ${preCarStorage ?.veh_Bz}
    </div></td>
</tr>

<tr>


    <td align="left" colspan='4' abbr="city" class="col1"><div align="left" class="innerCol ">
        <a id="btn_return" class="btn_basic blue_black" >${message(code: 'default.button.return.label', default: 'return')}</a>
    </div></td>
</tr>

</tbody>
</table>

</div>
</div>
</form>
<script>
    $('#btn_return').click(function(){

        var url="${createLink(controller:'PreCarStorage',action:'listSearch')}";
        window.location.href=url;

    });
</script>
</body>


</html>
