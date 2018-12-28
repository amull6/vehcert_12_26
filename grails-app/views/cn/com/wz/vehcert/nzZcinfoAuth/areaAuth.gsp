
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
    <th align="center" class="indexCol" colspan="17" style="width: 700px"><div align="center" class="innerCol " >合格证更换审核</div></th>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商：${nzZcinfoReAuInstance.createName }， 业务员：${nzZcinfoReAuInstance?.salesManName}，  业务员电话：${nzZcinfoReAuInstance?.salesManPhone}。
    <g:if test="${nzZcinfoReAuInstance?.change_Field=='def'}">
        正常更换
    </g:if>
    <g:elseif test="${nzZcinfoReAuInstance?.change_Field=='veh_Clbh'}">
        更换车辆编号/底盘号
    </g:elseif>
    </div></td></tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商备注：${nzZcinfoReAuInstance?.remark}
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"  style="width: 340px"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29" colspan="1" style="width: 10px">
        <div align="center" class="innerCol " ></div>
    </td>
    <td align="center" class="indexCol" colspan="8" style="width: 340px"><div align="center" class="innerCol " >更换后合格证</div></td>
</tr>

<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol "col style="width:100px" >1.车辆编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id='veh_Clbh'>${nzZcinfoReAuInstance?.veh_Clbh}</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " style="width:100px">1.车辆编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol" id='veh_Clbh_R'  <g:if test="${nzZcinfoReAuInstance?.veh_Clbh != nzZcinfoReAuInstance?.veh_Clbh_R }" >  style="color: red" </g:if>>
        ${nzZcinfoReAuInstance?.veh_Clbh_R}</div></td>
    </tr>


<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >2.底盘号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol "  id="veh_Dph">${nzZcinfoReAuInstance?.veh_Dph}</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >2.底盘号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4">
        <div align="left" class="innerCol " id="veh_Dph_R" <g:if test="${nzZcinfoReAuInstance?.veh_Dph != nzZcinfoReAuInstance?.veh_Dph_R }" >  style="color: red" </g:if>>
         ${nzZcinfoReAuInstance?.veh_Dph_R}
        </div>
    </td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >3.车型</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol "  id="veh_Cx">${nzZcinfoReAuInstance?.veh_Cx}</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol ">3.车型</div></td>
    <td align="left" abbr="id" class="col0" colspan="4">
        <div align="left" class="innerCol " <g:if test="${nzZcinfoReAuInstance?.veh_Cx != nzZcinfoReAuInstance?.veh_Cx_R }" >  style="color: red" </g:if>>
           ${nzZcinfoReAuInstance?.veh_Cx_R}
        </div>
    </td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" colspan="4" class="indexCol"><div align="left" class="innerCol ">4.出厂日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Ccrq">${nzZcinfoReAuInstance?.veh_Ccrq}</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >4.出厂日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="4">
        <div align="left" class="innerCol " <g:if test="${nzZcinfoReAuInstance?.veh_Ccrq != nzZcinfoReAuInstance?.veh_Ccrq_R }" >  style="color: red" </g:if>>
           ${nzZcinfoReAuInstance?.veh_Ccrq_R}
        </div>
    </td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >5.发动机号</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Fdjh">${nzZcinfoReAuInstance?.veh_Fdjh }</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >5.发动机号</div></td>
    <td align="left" abbr="city" class="col1" colspan="4">
        <div align="left" class="innerCol " <g:if test="${nzZcinfoReAuInstance?.veh_Fdjh != nzZcinfoReAuInstance?.veh_Fdjh_R }" >  style="color: red" </g:if>>
            ${nzZcinfoReAuInstance?.veh_Fdjh_R}
        </div>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >6.合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Hgzbh">${nzZcinfoReAuInstance?.veh_Hgzbh}</div></td>
    <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >6.合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="4">
        <div align="left" class="innerCol " >

       </div>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="17"><div align="left" class="innerCol ">区域经理审核备注：
    <g:textArea name="area_Remark" id="area_Remark" cols="40" rows="2" maxlength="500" style="width:600px;height:40px;" value="${nzZcinfoReAuInstance?.area_Remark}"/>
    </div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td  abbr="id" class="col0" colspan="17"><div align="right" class="innerCol ">
        <form id="form" method="post">

            <input type="hidden" value="${nzZcinfoReplaceId}" id="nzZcinfoReplaceId">
            <input type="hidden" value="${opFlag}" id="opFlag">
            <input type="hidden" value="" id="newId">
            <input type="hidden" value="${statusWrite}" id="statusWrite">
            &nbsp&nbsp&nbsp
            <g:if test="${nzZcinfoReAuInstance?.area_status==0}">
                <a id="btn_audit_pass" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">审核通过</a>
                <a id="btn_audit_noPass" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">审核不通过</a>
            </g:if>

            <a id="btn_return" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">返回</a>
      </form>
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
        $('#relate').omButton({
            width:40,
            onClick:function(){
                var veh_Clbh=$('#relate_value').val();
                var opFlag=$('#opFlag').val();
                if(veh_Clbh!=''){
                    var url = "${createLink(controller:'NZInfo',action:'fingNZzcinfoByClbh')}?veh_Clbh="+veh_Clbh+"&opFlag="+opFlag;
                    window.location.href=url;
                }else{
                    alert('请输入车辆编号！');
                }
            }
        });
        $("#btn_audit_pass").click(function(){
            var area_Remark=$("#area_Remark").val()
            if(area_Remark==''||area_Remark==null){
                alert('请填写审核备注');
                return
            }
            var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'areAudit')}?id='+$("#nzZcinfoReplaceId").val()+"&area_Remark="+encodeURI($("#area_Remark").val())+"&pass=Y";
            window.location.href = url;
        });
        $("#btn_audit_noPass").click(function(){
            if(area_Remark==''||area_Remark==null){
                alert('请填写审核备注');
                return
            }
            var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'areAudit')}?id='+$("#nzZcinfoReplaceId").val()+"&area_Remark="+encodeURI($("#area_Remark").val())+"&pass=N";
            window.location.href = url;
        });
        $("#btn_return").click(function(){
            var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'areaAuthList')}?searchType=1';
            window.location.href = url;
        });
    })
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
