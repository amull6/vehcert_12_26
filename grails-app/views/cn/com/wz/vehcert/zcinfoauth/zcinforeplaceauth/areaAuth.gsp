<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="合格证更换申请审核" />
    <title>合格证更换申请审核</title>
</head>
<body>
<form id="form" action="${createLink(controller: 'ZCInfoReplaceAuth',action:'update')}" method="post">
<input type="hidden" id="pass" value="${zcinfoReAuInstance.veh_coc_status}" />
<input type="hidden" id="dpPass"    value ="${zcinfoReAuInstance?.veh_DP_status}"  />
<input type="hidden" id="qxPass"    value ="${zcinfoReAuInstance?.veh_QX_status}"  />
<g:hiddenField name="web_virtualcode" id="web_virtualcode" value="" />
<g:hiddenField name="zcinforeid" id="zcinforeid" value="${zcinfoReAuInstance?.id}" />
<g:hiddenField name="subtype" id="subtype" value="1" />
<g:hiddenField name="veh_Clpp_R" id="veh_Clpp_R" value="${zcinfoReAuInstance?.veh_Clpp_R}" />
<g:hiddenField name="veh_Ggpc_R" id="veh_Ggpc_R" value="${zcinfoReAuInstance?.veh_Ggpc_R}" />
<g:hiddenField name="veh_Ggsxrq_R" id="veh_Ggsxrq_R" value="${zcinfoReAuInstance?.veh_Ggsxrq_R}" />
<g:hiddenField name="veh_Type_R" id="veh_Type_R"  value ="${zcinfoReAuInstance?.veh_Type_R}"  />
<g:hiddenField name="veh_Clzzqymc_R" id="veh_Clzzqymc_R" value="${zcinfoReAuInstance?.veh_Clzzqymc_R}" />
<g:hiddenField name="veh_Qyid_R" id="veh_Qyid_R"  value ="${zcinfoReAuInstance?.veh_Qyid_R}"  />
<g:hiddenField name="veh_Clfl_R" id="veh_Clfl_R"  value ="${zcinfoReAuInstance?.veh_Clfl_R}"  />
<g:hiddenField name="veh_Clmc_R" id="veh_Clmc_R"  value ="${zcinfoReAuInstance?.veh_Clmc_R}"  />
<g:hiddenField name="veh_Clxh_R" id="veh_Clxh_R"  value ="${zcinfoReAuInstance?.veh_Clxh_R}"  />
<g:hiddenField name="veh_Csys_R" id="veh_Csys_R"  value ="${zcinfoReAuInstance?.veh_Csys_R}"  />
<g:hiddenField name="veh_Dpxh_R" id="veh_Dpxh_R"  value ="${zcinfoReAuInstance?.veh_Dpxh_R}"  />
<g:hiddenField name="veh_Dpid_R" id="veh_Dpid_R"  value ="${zcinfoReAuInstance?.veh_Dpid_R}"  />
<g:hiddenField name="veh_Fdjxh_R" id="veh_Fdjxh_R"  value ="${zcinfoReAuInstance?.veh_Fdjxh_R}"  />
<g:hiddenField name="veh_Rlzl_R" id="veh_Rlzl_R"  value ="${zcinfoReAuInstance?.veh_Rlzl_R}"  />
<g:hiddenField name="veh_Pl_R" id="veh_Pl_R"  value ="${zcinfoReAuInstance?.veh_Pl_R}"  />
<g:hiddenField name="veh_Gl_R" id="veh_Gl_R"  value ="${zcinfoReAuInstance?.veh_Gl_R}"  />
<g:hiddenField name="veh_zdjgl_R" id="veh_zdjgl_R"  value ="${zcinfoReAuInstance?.veh_zdjgl_R}"  />
<g:hiddenField name="veh_Zxxs_R" id="veh_Zxxs_R"  value ="${zcinfoReAuInstance?.veh_Zxxs_R}"  />
<g:hiddenField name="veh_Qlj_R" id="veh_Qlj_R"  value ="${zcinfoReAuInstance?.veh_Qlj_R}"  />
<g:hiddenField name="veh_Hlj_R" id="veh_Hlj_R"  value ="${zcinfoReAuInstance?.veh_Hlj_R}"  />
<g:hiddenField name="veh_Lts_R" id="veh_Lts_R"  value ="${zcinfoReAuInstance?.veh_Lts_R}"  />
<g:hiddenField name="veh_Ltgg_R" id="veh_Ltgg_R"  value ="${zcinfoReAuInstance?.veh_Ltgg_R}"  />
<g:hiddenField name="veh_Gbthps_R" id="veh_Gbthps_R"  value ="${zcinfoReAuInstance?.veh_Gbthps_R}"  />
<g:hiddenField name="veh_Zj_R" id="veh_Zj_R"  value ="${zcinfoReAuInstance?.veh_Zj_R}"  />
<g:hiddenField name="veh_Zh_R" id="veh_Zh_R"  value ="${zcinfoReAuInstance?.veh_Zh_R}"  />
<g:hiddenField name="veh_Zs_R" id="veh_Zs_R"  value ="${zcinfoReAuInstance?.veh_Zs_R}"  />
<g:hiddenField name="veh_Wkc_R" id="veh_Wkc_R"  value ="${zcinfoReAuInstance?.veh_Wkc_R}"  />
<g:hiddenField name="veh_Wkk_R" id="veh_Wkk_R"  value ="${zcinfoReAuInstance?.veh_Wkk_R}"  />
<g:hiddenField name="veh_Wkg_R" id="veh_Wkg_R"  value ="${zcinfoReAuInstance?.veh_Wkg_R}"  />
<g:hiddenField name="veh_Hxnbc_R" id="veh_Hxnbc_R"  value ="${zcinfoReAuInstance?.veh_Hxnbc_R}"  />
<g:hiddenField name="veh_Hxnbk_R" id="veh_Hxnbk_R"  value ="${zcinfoReAuInstance?.veh_Hxnbk_R}"  />
<g:hiddenField name="veh_Hxnbg_R" id="veh_Hxnbg_R"  value ="${zcinfoReAuInstance?.veh_Hxnbg_R}"  />
<g:hiddenField name="veh_Zzl_R" id="veh_Zzl_R"  value ="${zcinfoReAuInstance?.veh_Zzl_R}"  />
<g:hiddenField name="veh_Edzzl_R" id="veh_Edzzl_R"  value ="${zcinfoReAuInstance?.veh_Edzzl_R}"  />
<g:hiddenField name="veh_Zbzl_R" id="veh_Zbzl_R"  value ="${zcinfoReAuInstance?.veh_Zbzl_R}"  />
<g:hiddenField name="veh_Zzllyxs_R" id="veh_Zzllyxs_R"  value ="${zcinfoReAuInstance?.veh_Zzllyxs_R}"  />
<g:hiddenField name="veh_Zqyzzl_R" id="veh_Zqyzzl_R"  value ="${zcinfoReAuInstance?.veh_Zqyzzl_R}"  />
<g:hiddenField name="veh_Edzk_R" id="veh_Edzk_R"  value ="${zcinfoReAuInstance?.veh_Edzk_R}"  />
<g:hiddenField name="veh_Bgcazzdyxzzl_R" id="veh_Bgcazzdyxzzl_R"  value ="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R}"  />
<g:hiddenField name="veh_Jsszcrs_R" id="veh_Jsszcrs_R"  value ="${zcinfoReAuInstance?.veh_Jsszcrs_R}"  />
<g:hiddenField name="veh_Qzdfs_R" id="veh_Qzdfs_R"  value ="${zcinfoReAuInstance?.veh_Qzdfs_R}"  />
<g:hiddenField name="veh_Hzdfs_R" id="veh_Hzdfs_R"  value ="${zcinfoReAuInstance?.veh_Hzdfs_R}"  />
<g:hiddenField name="veh_Qzdczfs_R" id="veh_Qzdczfs_R"  value ="${zcinfoReAuInstance?.veh_Qzdczfs_R}"  />
<g:hiddenField name="veh_Hzdczfs_R" id="veh_Hzdczfs_R"  value ="${zcinfoReAuInstance?.veh_Hzdczfs_R}"  />
<g:hiddenField name="veh_Cpggh_R" id="veh_Cpggh_R"  value ="${zcinfoReAuInstance?.veh_Cpggh_R}"  />
<g:hiddenField name="veh_Zzbh_R" id="veh_Zzbh_R"  value ="${zcinfoReAuInstance?.veh_Zzbh_R}"  />
<g:hiddenField name="veh_Dywym_R" id="veh_Dywym_R"  value ="${zcinfoReAuInstance?.veh_Dywym_R}"  />
<g:hiddenField name="veh_Zgcs_R" id="veh_Zgcs_R"  value ="${zcinfoReAuInstance?.veh_Zgcs_R}"  />
<g:hiddenField name="veh_Qybz_R" id="veh_Qybz_R"  value ="${zcinfoReAuInstance?.veh_Qybz_R}"  />
<g:hiddenField name="veh_Cpscdz_R" id="veh_Cpscdz_R"  value ="${zcinfoReAuInstance?.veh_Cpscdz_R}"  />
<g:hiddenField name="veh_Qyqtxx_R" id="veh_Qyqtxx_R"  value ="${zcinfoReAuInstance?.veh_Qyqtxx_R}"  />
<g:hiddenField name="veh_Pfbz_R" id="veh_Pfbz_R"  value ="${zcinfoReAuInstance?.veh_Pfbz_R}"  />
<g:hiddenField name="veh_Clztxx_R" id="veh_Clztxx_R"  value ="${zcinfoReAuInstance?.veh_Clztxx_R}"  />
<g:hiddenField name="veh_Jss_R" id="veh_Jss_R"  value ="${zcinfoReAuInstance?.veh_Jss_R}"  />
<g:hiddenField name="veh_Jsslx_R" id="veh_Jsslx_R"  value ="${zcinfoReAuInstance?.veh_Jsslx_R}"  />
<g:hiddenField name="veh_Zchgzbh_0_R" id="veh_Zchgzbh_0_R"  value ="${zcinfoReAuInstance?.veh_Zchgzbh_0_R}"  />
<g:hiddenField name="veh_Yh_R" id="veh_Yh_R"  value ="${zcinfoReAuInstance?.veh_Yh_R}"  />
<g:hiddenField name="veh_VinFourBit_R" id="veh_VinFourBit_R"  value ="${zcinfoReAuInstance?.veh_VinFourBit_R}"  />

<g:hiddenField name="veh_pzxlh_R" id="veh_pzxlh_R"  value ="${zcinfoReAuInstance?.veh_pzxlh_R}"  />
<g:hiddenField name="upload_Path_R" id="upload_Path_R"  value ="${zcinfoReAuInstance?.upload_Path_R}"  />
<g:hiddenField name="veh_clzzqyxx_R" id="veh_clzzqyxx_R"  value ="${zcinfoReAuInstance?.veh_clzzqyxx_R}"  />
<g:hiddenField name="veh_Need_Cer" id="veh_Need_Cer"  value ="${zcinfoReAuInstance?.veh_Need_Cer}"  />
<g:hiddenField name="vercode_r" id="vercode_r"  value ="${zcinfoReAuInstance?.vercode_r}"  />
<g:hiddenField name="veh_DP_status" id="veh_DP_status"  value ="${zcinfoReAuInstance?.veh_DP_status}"  />
<g:hiddenField name="veh_QX_status" id="veh_QX_status"  value ="${zcinfoReAuInstance?.veh_QX_status}"  />
<g:hiddenField name="veh_Xnhgzbh" id="veh_Xnhgzbh" value="${zcinfoReAuInstance?.veh_Xnhgzbh}" />
<g:hiddenField name="veh_Clsbdh" id="veh_Clsbdh" value="${zcinfoReAuInstance?.veh_Clsbdh}" />
<g:hiddenField name="veh_Zchgzbh" id="veh_Zchgzbh" value="${zcinfoReAuInstance?.veh_Zchgzbh}" />
<div>
    %{--<input type="file" name="saveFileName" id="uploadFile">--}%
</div>
<div id="page" style="width:1300px;background-color:white;margin:0px 0px">
<div id="center-panel" style="margin-left:4px;border:1px;">
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>

<div style="margin-right:8px;margin-top:8px; height:100%;  width:100%;">

<div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


<div class="bDiv" style="width: auto; height: 100%; width:100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <td align="center" class="indexCol" colspan="17"><div align="center" class="innerCol " >合格证更换申请审核</div></td>
    <td id ="td1" style="display:none" align="center" class="indexCol" colspan="9"></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商：<c:getUserName userId='${zcinfoReAuInstance.createrId }'></c:getUserName>， 业务员：${zcinfoReAuInstance?.salesmanname}，  业务员电话：${zcinfoReAuInstance?.salesmantel}。
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 0}">只要整车合格证</g:if>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 1}">只要底盘合格证</g:if>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">整车和底盘合格证</g:if>
    </div></td>
    <td id ="td94" style="display:none" align="center" class="indexCol" ></td>
    <td id ="td2" style="display:none" align="center" class="indexCol" colspan="8"></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >经销商备注：${zcinfoReAuInstance?.remark}
    </div></td>
    <td style="display:none" id ="td95" style="display:none" align="center" class="indexCol" ></td>
    <td style="display:none" id ="td3" style="display:none" align="center" class="indexCol" colspan="8"></td>
</tr>

<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
    <td style="display:none" id ="td4" align="center" class="indexCol" rowspan="31"><div align="center" class="innerCol " ></div></td>
    <td style="display:none" id ="td5" align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >附加底盘合格证（需要整车和底盘的变更申请填写底盘公告）</div></td>
</tr>
%{--<tr class="om-grid-row evenRow">--}%
    %{--<td align="left" class="indexCol"></td>--}%
    %{--<td align="left" abbr="id" class="col0" colspan="7"></td>--}%
    %{--<td align="left" class="indexCol"></td>--}%
    %{--<td align="left" abbr="id" class="col0" colspan="3"></td>--}%
        %{--<g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">--}%
    %{--<td align="left" abbr="id" class="col0" ><div align="left" class="innerCol ">--}%
        %{--</g:if>--}%
    %{--<g:if test="${zcinfoReAuInstance?.veh_Need_Cer != 2}">--}%
     %{--<td align="left" abbr="id" class="col0" colspan="2" ><div align="left" class="innerCol ">--}%
    %{--</g:if>--}%
   %{--<div id="veh_Type_R1">--}%
        %{--<input type="radio" name="RadioGroup1"   value="0"  <g:if test="${zcinfoReAuInstance?.veh_Type_R == '1'}">checked="checked"</g:if> />--}%
        %{--农用车公告--}%
   %{--</div>--}%
%{--</br>--}%
   %{--<div id="veh_Type_R0">--}%
        %{--<input type="radio" name="RadioGroup1"  value="1" <g:if test="${zcinfoReAuInstance?.veh_Type_R == '0'}">checked="checked"</g:if>/>--}%
        %{--汽车公告--}%
    %{--</div>--}%
%{--</div>--}%
%{--</td>--}%
    %{--<g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">--}%
        %{--<td align="left" valign="center" style="width: 130px;" abbr="id" class="col0" ><div align="left" id="noticese"  class="innerCol ">--}%
            %{--<a id="btn_select" style="line-height:30px;font-size: 14px;margin-top:10px;" class="btn_basic blue_black">按公告号提取数据</a>--}%
        %{--</div></td>--}%
    %{--</g:if>--}%

    %{--<td align="left" abbr="id" class="col0" colspan="2"><div align="left" class="innerCol ">--}%
        %{--<div id="quanxiangdiv">--}%
        %{--<input type="radio" name="RadioGroup2" id="quanxiang" value="QX" <g:if test="${zcinfoReAuInstance?.veh_Type_R == '1'||(zcinfoReAuInstance?.veh_Type_R=='0'&&zcinfoReAuInstance?.veh_Clztxx_R=='QX')}">checked="checked"</g:if>/>--}%
        %{--全项（QX）--}%
        %{--</div>--}%
        %{--<div id="dipandiv">--}%
        %{--<input type="radio" name="RadioGroup2" id="dipan"  value="DP"  <g:if test="${zcinfoReAuInstance?.veh_Type_R=='0'&& zcinfoReAuInstance?.veh_Clztxx_R=='DP'}">checked="checked"</g:if>/>--}%
        %{--底盘（DP）--}%
        %{--</div>--}%
    %{--</div></td>--}%
%{--</tr>--}%
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zchgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div  align="left" class="${hasErrors(bean: zcinfoReAuInstance, field: 'veh_Zchgzbh_R', 'error')} required" >${zcinfoReAuInstance?.veh_Zchgzbh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div  align="left" class="${hasErrors(bean: zcinfoReAuInstance, field: 'veh_Fzrq_R', 'error')} required" >${zcinfoReAuInstance?.veh_Fzrq_R}</div></td>
    <td style="display:none" id ="td6" align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td style="display:none" id ="td7" align="left" abbr="id" class="col0" colspan="3"><div  align="left" class="${hasErrors(bean: replaceForSupplement, field: 'veh_Zchgzbh_R', 'error')} required" >${replaceForSupplement?.veh_Zchgzbh_R}</div></td>
    <td style="display:none" id ="td8" align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td style="display:none" id ="td9" align="left" abbr="city" class="col1" colspan="3"><div  align="left" class="${hasErrors(bean: replaceForSupplement, field: 'veh_Fzrq_R', 'error')} required" >${replaceForSupplement?.veh_Fzrq_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>
        class="innerCol  STYLE1">3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Clzzqymc_R_Div" class="innerCol"<g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>    ${zcinfoReAuInstance?.veh_Clzzqymc_R}</div></td>
    <td style="display:none" id ="td10" align="left" class="indexCol"><div align="left" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != replaceForSupplement?.veh_Clzzqymc_R }" >  style="color: red" </g:if>
                                           class="innerCol  STYLE1">3.车辆制造企业名称</div></td>
    <td style="display:none" id ="td11" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Clzzqymc_R_Div" class="innerCol"<g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != replaceForSupplement?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>    ${replaceForSupplement?.veh_Clzzqymc_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clpp}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clmc}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Clpp_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clpp != zcinfoReAuInstance?.veh_Clpp_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clpp_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Clmc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clmc != zcinfoReAuInstance?.veh_Clmc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clmc_R}</div></td>
    <td style="display:none" id ="td93" align="left" class="indexCol"><div align="left" class="innerCol " >4.车辆品牌/车辆名称</div></td>
    <td style="display:none" id ="td12" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Clpp_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clpp != replaceForSupplement?.veh_Clpp_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clpp_R}</div></td>
    <td style="display:none" id ="td13" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Clmc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clmc != replaceForSupplement?.veh_Clmc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clmc_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clxh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clsbdh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Clxh_R" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clxh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clsbdh_R}</div></td>
    <td style="display:none" id ="td14" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != replaceForSupplement?.veh_Clxh_R }" >  style="color: red" </g:if>>5.车辆型号</div></td>
    <td style="display:none" id ="td15" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Clxh_R" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != replaceForSupplement?.veh_Clxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Clxh_R}</div></td>
    <td style="display:none" id ="td16" align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td style="display:none" id ="td17" align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Clsbdh_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Csys}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != zcinfoReAuInstance?.veh_Csys_R }" >  style="color: red" </g:if> >7.车身颜色</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Csys_R_Div" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != zcinfoReAuInstance?.veh_Csys_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Csys_R}</div></td>
    <td style="display:none" id ="td18" align="left" class="indexCol"><div align="left" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != replaceForSupplement?.veh_Csys_R }" >  style="color: red" </g:if> >7.车身颜色</div></td>
    <td style="display:none" id ="td19" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Csys_R_Div" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != replaceForSupplement?.veh_Csys_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Csys_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dpxh}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dpid}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Dpxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpxh != zcinfoReAuInstance?.veh_Dpxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Dpxh_R}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Dpid_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpid != zcinfoReAuInstance?.veh_Dpid_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Dpid_R}</div></td>
    <td style="display:none" id ="td20" align="left" class="indexCol"><div align="left" class="innerCol " >8.底盘型号/底盘ID</div></td>
    <td style="display:none" id ="td21" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Dpxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpxh != replaceForSupplement?.veh_Dpxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Dpxh_R}</div></td>
    <td style="display:none" id ="td22" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Dpid_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Dpid != replaceForSupplement?.veh_Dpid_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Dpid_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dphgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Fdjxh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Dphgzbh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Fdjxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Fdjxh_R}</div></td>
    <td style="display:none" id ="td23" align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td style="display:none" id ="td24" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Dphgzbh_R}</div></td>
    <td style="display:none" id ="td25" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != replaceForSupplement?.veh_Fdjxh_R }" >  style="color: red" </g:if>>10.发动机型号</div></td>
    <td style="display:none" id ="td26" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Fdjxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != replaceForSupplement?.veh_Fdjxh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Fdjxh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fdjh_R}</div></td>
    <td style="display:none" id ="td27" align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td style="display:none" id ="td28" align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Fdjh_R}</div></td>
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
    <td style="display:none" id ="td29" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != replaceForSupplement?.veh_Rlzl_R }" >  style="color: red" </g:if>>12.燃料种类</div></td>
    <td style="display:none" id ="td30" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Rlzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Rlzl != replaceForSupplement?.veh_Rlzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Rlzl_R}</div></td>
    <td style="display:none" id ="td31" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">13.排量和功率(ml/kW)</div></td>
    <td style="display:none" id ="td32" align="left" abbr="city" class="col1" colspan="2"><div align="left" id="Dp_veh_Pl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Pl != replaceForSupplement?.veh_Pl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Pl_R}</div></td>
    <td style="display:none" id ="td33" align="left" abbr="city" class="col1" ><div align="left" id="Dp_veh_Gl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gl != replaceForSupplement?.veh_Gl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Gl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Pfbz}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != zcinfoReAuInstance?.veh_Pfbz_R }" >  style="color: red" </g:if>>14.排放标准</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Pfbz_R_Div" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != zcinfoReAuInstance?.veh_Pfbz_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Pfbz_R}</div></td>
    <td style="display:none" id ="td34" align="left" class="indexCol"><div align="left" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != replaceForSupplement?.veh_Pfbz_R }" >  style="color: red" </g:if>>14.排放标准</div></td>
    <td style="display:none" id ="td35" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Pfbz_R_Div" class="innerCol "  <g:if test="${zcinfoReAuInstance?.veh_Pfbz != replaceForSupplement?.veh_Pfbz_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Pfbz_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Yh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != zcinfoReAuInstance?.veh_Yh_R }" >  style="color: red" </g:if>>15.油耗</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Yh_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != zcinfoReAuInstance?.veh_Yh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Yh_R}</div></td>
    <td style="display:none" id ="td36" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != replaceForSupplement?.veh_Yh_R }" >  style="color: red" </g:if>>15.油耗</div></td>
    <td style="display:none" id ="td37" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Yh_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != replaceForSupplement?.veh_Yh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Yh_R}</div></td>

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
    <td style="display:none" id ="td38" align="left" class="indexCol"><div align="left" class="innerCol ">16.外廓尺寸(mm)</div></td>
    <td style="display:none" id ="td39" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkc_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkc != replaceForSupplement?.veh_Wkc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkc_R}</div></td>
    <td style="display:none" id ="td40" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkk_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkk != replaceForSupplement?.veh_Wkk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkk_R}</div></td>
    <td style="display:none" id ="td41" align="left" abbr="id" class="col0"><div align="left" id="Dp_veh_Wkg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Wkg != replaceForSupplement?.veh_Wkg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Wkg_R}</div></td>
    <td style="display:none" id ="td42" align="left" abbr="id" class="col0"><div align="left" class="innerCol ">17.货厢内部尺寸(mm)</div></td>
    <td style="display:none" id ="td43" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbc_R_Div"  <g:if test="${zcinfoReAuInstance?.veh_Hxnbc != replaceForSupplement?.veh_Hxnbc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbc_R}</div></td>
    <td style="display:none" id ="td44" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbk_R_Div"<g:if test="${zcinfoReAuInstance?.veh_Hxnbk != replaceForSupplement?.veh_Hxnbk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbk_R}</div></td>
    <td style="display:none" id ="td45" align="left" abbr="city" class="col1"><div align="left" class="innerCol " id="Dp_veh_Hxnbg_R_Div" <g:if test="${zcinfoReAuInstance?.veh_Hxnbg != replaceForSupplement?.veh_Hxnbg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hxnbg_R}</div></td>
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
    <td style="display:none" id ="td46" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Gbthps != replaceForSupplement?.veh_Gbthps_R }" >  style="color: red" </g:if>>18.钢板弹簧片数(片)</div></td>
    <td style="display:none" id ="td47" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Gbthps_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Gbthps != replaceForSupplement?.veh_Gbthps_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Gbthps_R}</div></td>
    <td style="display:none" id ="td48" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != zcinfoReAuInstance?.veh_Lts_R }" >  style="color: red" </g:if>>19.轮胎数</div></td>
    <td style="display:none" id ="td49" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Lts_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Lts != replaceForSupplement?.veh_Lts_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Lts_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Ltgg}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != zcinfoReAuInstance?.veh_Ltgg_R }" >  style="color: red" </g:if>>20.轮胎规格</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Ltgg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != zcinfoReAuInstance?.veh_Ltgg_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ltgg_R}</div></td>
    <td style="display:none" id ="td50" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != replaceForSupplement?.veh_Ltgg_R }" >  style="color: red" </g:if>>20.轮胎规格</div></td>
    <td style="display:none" id ="td51" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Ltgg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != replaceForSupplement?.veh_Ltgg_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Ltgg_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Qlj}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Hlj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Qlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Qlj != zcinfoReAuInstance?.veh_Qlj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Qlj_R}</div></td>
    <td align="left" abbr="city" class="col1" colspan="4"><div align="left" id="veh_Hlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Hlj != zcinfoReAuInstance?.veh_Hlj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Hlj_R}</div></td>
    <td style="display:none" id ="td52" align="left" class="indexCol"><div align="left" class="innerCol ">21.轮距(前/后)(mm)</div></td>
    <td style="display:none" id ="td53" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Qlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Qlj != replaceForSupplement?.veh_Qlj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Qlj_R}</div></td>
    <td style="display:none" id ="td54" align="left" abbr="city" class="col1" colspan="4"><div align="left" id="Dp_veh_Hlj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Hlj != replaceForSupplement?.veh_Hlj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Hlj_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zj}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != zcinfoReAuInstance?.veh_Zj_R }" >  style="color: red" </g:if>>22.轴距(mm)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Zj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != zcinfoReAuInstance?.veh_Zj_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zj_R}</div></td>
    <td style="display:none" id ="td55" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != replaceForSupplement?.veh_Zj_R }" >  style="color: red" </g:if>>22.轴距(mm)</div></td>
    <td style="display:none" id ="td56" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Zj_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zj != replaceForSupplement?.veh_Zj_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zj_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != zcinfoReAuInstance?.veh_Zh_R }" >  style="color: red" </g:if>>23.轴荷(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Zh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != zcinfoReAuInstance?.veh_Zh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zh_R}</div></td>
    <td style="display:none" id ="td57" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != replaceForSupplement?.veh_Zh_R }" >  style="color: red" </g:if>>23.轴荷(kg)</div></td>
    <td style="display:none" id ="td58" align="left" abbr="id" class="col0" colspan="7"><div align="left" id="Dp_veh_Zh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zh != replaceForSupplement?.veh_Zh_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zs}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Zxxs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zs_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zxxs != zcinfoReAuInstance?.veh_Zxxs_R }" >  style="color: red" </g:if>>25.转向形式</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Zxxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zxxs != zcinfoReAuInstance?.veh_Zxxs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zxxs_R}</div></td>
    <td style="display:none" id ="td59" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != replaceForSupplement?.veh_Zs_R }" >  style="color: red" </g:if>>24.轴数</div></td>
    <td style="display:none" id ="td60" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != replaceForSupplement?.veh_Zs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zs_R}</div></td>
    <td style="display:none" id ="td61" align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zxxs != replaceForSupplement?.veh_Zxxs_R }" >  style="color: red" </g:if>>25.转向形式</div></td>
    <td style="display:none" id ="td62" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zxxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zxxs != replaceForSupplement?.veh_Zxxs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zxxs_R}</div></td>
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
    <td style="display:none" id ="td63" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != replaceForSupplement?.veh_Zzl_R }" >  style="color: red" </g:if>>26.总质量(kg)</div></td>
    <td style="display:none" id ="td64" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzl != replaceForSupplement?.veh_Zzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zzl_R}</div></td>
    <td style="display:none" id ="td65" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != replaceForSupplement?.veh_Zbzl_R }" >  style="color: red" </g:if>>27.整备质量(kg)</div></td>
    <td style="display:none" id ="td66" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zbzl_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zbzl != replaceForSupplement?.veh_Zbzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zbzl_R}</div></td>
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
    <td style="display:none" id ="td67" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Edzzl != replaceForSupplement?.veh_Edzzl_R }" >  style="color: red" </g:if>>28.额定载质量(kg)</div></td>
    <td style="display:none" id ="td68" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Edzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzzl != replaceForSupplement?.veh_Edzzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Edzzl_R}</div></td>
    <td style="display:none" id ="td69" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != replaceForSupplement?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>29.载质量利用系数</div></td>
    <td style="display:none" id ="td70" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Zzllyxs_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Zzllyxs != replaceForSupplement?.veh_Zzllyxs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zzllyxs_R}</div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zqyzzl}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Bgcazzdyxzzl}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zqyzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zqyzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if>>31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Bgcazzdyxzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if> >${zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R}</div></td>
    <td style="display:none" id ="td71" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != replaceForSupplement?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>30.准牵引总质量(kg)</div></td>
    <td style="display:none" id ="td72" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zqyzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != replaceForSupplement?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zqyzzl_R}</div></td>
    <td style="display:none" id ="td73" align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != replaceForSupplement?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if>>31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td style="display:none" id ="td74" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Bgcazzdyxzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != replaceForSupplement?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if> >${replaceForSupplement?.veh_Bgcazzdyxzzl_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Jsszcrs}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != zcinfoReAuInstance?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>32.驾驶室准乘人数(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Jsszcrs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != zcinfoReAuInstance?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Jsszcrs_R}</div></td>
    <td align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
    <td style="display:none" id ="td75" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != replaceForSupplement?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>32.驾驶室准乘人数(人)</div></td>
    <td style="display:none" id ="td76" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Jsszcrs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Jsszcrs != replaceForSupplement?.veh_Jsszcrs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Jsszcrs_R}</div></td>
    <td style="display:none" id ="td77" align="left" abbr="id" class="col0" rowspan="5"><div align="left" class="innerCol ">36.二维条码</div></td>
    <td style="display:none" id ="td78" align="left" abbr="city" class="col1" colspan="3" rowspan="5"><div align="left" class="innerCol " ></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Edzk_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Edzk_R}</div></td>
    <td style="display:none" id ="td79" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != replaceForSupplement?.veh_Edzk_R }" >  style="color: red" </g:if>>33.额定载客(人)</div></td>
    <td style="display:none" id ="td80" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Edzk_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != replaceForSupplement?.veh_Edzk_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Edzk_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zgcs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zgcs_R}</div></td>
    <td style="display:none" id ="td81" align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != replaceForSupplement?.veh_Zgcs_R }" >  style="color: red" </g:if>>34.最高车速(km/h)</div></td>
    <td style="display:none" id ="td82" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Zgcs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != replaceForSupplement?.veh_Zgcs_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Zgcs_R}</div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzrq_R}</div></td>
    <td style="display:none" id ="td83" align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td style="display:none" id ="td84" align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${replaceForSupplement?.veh_Clzzrq_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">备注：${zcinfoReAuInstance?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Bz_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bz != zcinfoReAuInstance?.veh_Bz_R }" >  style="color: red" </g:if>>
        备注：${zcinfoReAuInstance?.veh_Bz_R}
    </div></td>
    <td style="display:none" id ="td85" align="left" abbr="id" class="col0" colspan="4"><div align="left" id="Dp_veh_Bz_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bz != replaceForSupplement?.veh_Bz_R }" >  style="color: red" </g:if>>
        备注：${replaceForSupplement?.veh_Bz_R}
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：${zcinfoReAuInstance?.veh_clzzqyxx}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != zcinfoReAuInstance?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>车辆制造信息：${zcinfoReAuInstance?.veh_clzzqyxx_R}</div></td>
    <td style="display:none" id ="td86" align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != replaceForSupplement?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>车辆制造信息：${replaceForSupplement?.veh_clzzqyxx_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">公告批次</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Ggpc}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">公告生效日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Ggsxrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggpc != zcinfoReAuInstance?.veh_Ggpc_R }" >  style="color: red" </g:if>>公告批次</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Ggpc_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Ggpc != zcinfoReAuInstance?.veh_Ggpc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ggpc_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != zcinfoReAuInstance?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>公告生效日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Ggsxrq_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != zcinfoReAuInstance?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ggsxrq_R}</div></td>
    <td style="display:none" id ="td87" align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggpc != replaceForSupplement?.veh_Ggpc_R }" >  style="color: red" </g:if>>公告批次</div></td>
    <td style="display:none" id ="td88" align="left" abbr="id" class="col0" colspan="3"><div align="left" id="Dp_veh_Ggpc_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Ggpc != replaceForSupplement?.veh_Ggpc_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Ggpc_R}</div></td>
    <td style="display:none" id ="td89" align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != replaceForSupplement?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>公告生效日期</div></td>
    <td style="display:none" id ="td90" align="left" abbr="city" class="col1" colspan="3"><div align="left" id="Dp_veh_Ggsxrq_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != replaceForSupplement?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>${replaceForSupplement?.veh_Ggsxrq_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="17"><div align="left" class="innerCol " style="color: red">审核备注：
    <g:textArea name="area_Remark" id="area_Remark" cols="40" rows="1"  style="width:600px;height:40px;" value="${zcinfoReAuInstance?.area_Remark}"/>
        </div></td>
    <th style="display:none" id ="td91"  align="center" class="indexCol" colspan="8"></th>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol ">
       <g:if test="${zcinfoReAuInstance?.area_status==0}">
           <a id="btn_pass" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">审核通过</a>
           <a id="btn_noPass" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">审核不通过</a>
       </g:if>

        <g:link controller="ZCInfoReplaceAuth" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black" action="areaAuthList"  >${message(code: 'default.button.return.label', default: 'Return')}</g:link>

    </div></td>
    <th style="display:none" id ="td92"  align="center" class="indexCol" colspan="8"></th>
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
    $(function() {
            if  ($('#veh_Need_Cer').val()==2){
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
                $('#td92').show();
                $('#td93').show();
                $('#td94').show();
                $('#td95').show();
                document.getElementById("page").style.width="1550px";
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
                $('#td92').hide();
                $('#td93').hide();
                $('#td94').show();
                $('#td95').show();
                document.getElementById("page").style.width="1300px";
            }
    })
    $("#btn_noPass").click(function(){
        var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'areaAudit')}?id='+$("#zcinforeid").val()+"&area_Remark="+encodeURI($("#area_Remark").val())+'&pass=N';
        window.location.href = url;
    });
    $("#btn_pass").click(function(){
        var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'areaAudit')}?id='+$("#zcinforeid").val()+"&area_Remark="+encodeURI($("#area_Remark").val())+'&pass=Y';
        window.location.href = url;
    });
</script>
</body>
</html>