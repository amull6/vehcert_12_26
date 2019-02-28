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


<div class="bDiv" style="width: 1300px; height: 100%; width:100%;">
<table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
<tbody>
<tr class="om-grid-row evenRow">
    <td align="center" class="indexCol" colspan="17"><div align="center" class="innerCol " >合格证更换申请审核</div></td>
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
<g:if test="${zcinfoReAuInstance.replaceType=='1'}">
    <tr class="om-grid-row oddRow">
        <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >
            区域经理审核：审核备注：${zcinfoReAuInstance?.area_Remark}， 区域经理：<c:getUserName userId='${zcinfoReAuInstance.area_AuthId }'></c:getUserName>。
        </div></td>
    </tr>

    <tr class="om-grid-row oddRow">
        <td align="left" class="indexCol" colspan="17"><div align="left" class="indexCol" >
            产品管理部审核：审核备注：${zcinfoReAuInstance?.product_auth_Remark}， 审核人：<c:getUserName userId='${zcinfoReAuInstance.product_auth_Id }'></c:getUserName>。
        </div></td>
    </tr>

</g:if>


<tr class="om-grid-row oddRow">
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >原合格证</div></td>
    <td align="center" class="indexCol" rowspan="29"><div align="center" class="innerCol " ></div></td>
    <td align="center" class="indexCol" colspan="8"><div align="center" class="innerCol " >更换后合格证</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol">
    <g:if test="${zcinfoReAuInstance?.veh_isupload ==0}">
        <div align="center" class="innerCol " >未上传</div>
    </g:if>
    <g:if test="${zcinfoReAuInstance?.veh_isupload ==1}">
        <div align="center" class="innerCol " style="color: red" >已上传</div>
    </g:if>
    </td>
    <td align="left" abbr="id" class="col0" colspan="7"></td>
    <td align="left" class="indexCol"></td>
    <td align="left" abbr="id" class="col0" colspan="3"></td>
        <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">
    <td align="left" abbr="id" class="col0" ><div align="left" class="innerCol ">
        </g:if>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer != 2}">
     <td align="left" abbr="id" class="col0" colspan="2" ><div align="left" class="innerCol ">
    </g:if>
   <div id="veh_Type_R1">
        <input type="radio" name="RadioGroup1"   value="0"  <g:if test="${zcinfoReAuInstance?.veh_Type_R == '1'}">checked="checked"</g:if> />
        农用车公告
   </div>
</br>
   <div id="veh_Type_R0">
        <input type="radio" name="RadioGroup1"  value="1" <g:if test="${zcinfoReAuInstance?.veh_Type_R == '0'}">checked="checked"</g:if>/>
        汽车公告
    </div>
</div>
</td>
    <g:if test="${zcinfoReAuInstance?.veh_Need_Cer == 2}">
        <td align="left" valign="center" style="width: 130px;" abbr="id" class="col0" ><div align="left" id="noticese"  class="innerCol ">
            <a id="btn_select" style="line-height:30px;font-size: 14px;margin-top:10px;" class="btn_basic blue_black">按公告号提取数据</a>
        </div></td>
    </g:if>

    <td align="left" abbr="id" class="col0" colspan="2"><div align="left" class="innerCol ">
        <div id="quanxiangdiv">
        <input type="radio" name="RadioGroup2" id="quanxiang" value="QX" <g:if test="${zcinfoReAuInstance?.veh_Type_R == '1'||(zcinfoReAuInstance?.veh_Type_R=='0'&&zcinfoReAuInstance?.veh_Clztxx_R=='QX')}">checked="checked"</g:if>/>
        全项（QX）
        </div>
        <div id="dipandiv">
        <input type="radio" name="RadioGroup2" id="dipan"  value="DP"  <g:if test="${zcinfoReAuInstance?.veh_Type_R=='0'&& zcinfoReAuInstance?.veh_Clztxx_R=='DP'}">checked="checked"</g:if>/>
        底盘（DP）
        </div>
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zchgzbh}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:100px">1、合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div  align="left" class="${hasErrors(bean: zcinfoReAuInstance, field: 'veh_Zchgzbh_R', 'error')} required" ><g:textField name="veh_Zchgzbh_R" id="veh_Zchgzbh_R"  maxlength="50"  value="${zcinfoReAuInstance?.veh_Zchgzbh_R}"/></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:100px">2、发证日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div  align="left" class="${hasErrors(bean: zcinfoReAuInstance, field: 'veh_Fzrq_R', 'error')} required" ><c:dataPicker name="veh_Fzrq_R" id="veh_Fzrq_R" editable="false" dateFormat="yy年mm月dd日"/></div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzqymc}</div></td>
    <td align="left" class="indexCol"><div align="left" <g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>
        class="innerCol  STYLE1">3.车辆制造企业名称</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Clzzqymc_R_Div" class="innerCol"<g:if test="${zcinfoReAuInstance?.veh_Clzzqymc != zcinfoReAuInstance?.veh_Clzzqymc_R }" >  style="color: red" </g:if>>    ${zcinfoReAuInstance?.veh_Clzzqymc_R}</div></td>
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
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "><input  name="veh_Clsbdh" style="width:150px;" id="veh_Clsbdh" maxlength="50" readOnly="true"  value="${zcinfoReAuInstance?.veh_Clsbdh}"/></div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>5.车辆型号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Clxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Clxh != zcinfoReAuInstance?.veh_Clxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Clxh_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " >6.车辆识别代号/车架号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol "><input name="veh_Clsbdh_R" style="width:150px;" id="veh_Clsbdh_R" maxlength="50" readOnly="true"  value="${zcinfoReAuInstance?.veh_Clsbdh_R}"/><a  id="btn_calculate" style="line-height:20px;font-size: 12px;" class="btn_basic blue_black">计算VIN</a></div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol " >7.车身颜色</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Csys}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol  STYLE1" <g:if test="${zcinfoReAuInstance?.veh_Csys != zcinfoReAuInstance?.veh_Csys_R }" >  style="color: red" </g:if> >7.车身颜色</div></td>
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
    <td align="left" class="indexCol"><div align="left" class="innerCol ">9.底盘合格证编号</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><g:textField name="veh_Dphgzbh_R" id="veh_Dphgzbh_R" maxlength="50"  value="${zcinfoReAuInstance?.veh_Dphgzbh_R}"/></div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>10.发动机型号</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Fdjxh_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Fdjxh != zcinfoReAuInstance?.veh_Fdjxh_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Fdjxh_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Fdjh}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">11.发动机号</div></td>
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" class="innerCol "><g:textField name="veh_Fdjh_R" id="veh_Fdjh_R" maxlength="50"  value="${zcinfoReAuInstance?.veh_Fdjh_R}"/></div></td>
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
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Yh != zcinfoReAuInstance?.veh_Yh_R }" >  style="color: red" </g:if>>15.油耗</div></td>
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
    <td align="left" abbr="id" class="col0" colspan="7"><div align="left" id="veh_Ltgg_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ltgg != zcinfoReAuInstance?.veh_Ltgg_R }" >  style="color: red" </g:if>><g:textField name="veh_Ltgg_R" id="veh_Ltgg_R" maxlength="50"  value="${zcinfoReAuInstance?.veh_Ltgg_R}"/></div></td>

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
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>24.轴数</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zs != zcinfoReAuInstance?.veh_Zs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zs_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zxxs != zcinfoReAuInstance?.veh_Zxxs_R }" >  style="color: red" </g:if>>25.转向形式</div></td>
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
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>30.准牵引总质量(kg)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zqyzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zqyzzl != zcinfoReAuInstance?.veh_Zqyzzl_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zqyzzl_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if>>31.半挂车鞍座最大</br>允许总质量(kg)</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Bgcazzdyxzzl_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Bgcazzdyxzzl != zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R }" >  style="color: red" </g:if> >${zcinfoReAuInstance?.veh_Bgcazzdyxzzl_R}</div></td>
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
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Edzk}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>33.额定载客(人)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Edzk_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Edzk != zcinfoReAuInstance?.veh_Edzk_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Edzk_R}</div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Zgcs}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>34.最高车速(km/h)</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Zgcs_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Zgcs != zcinfoReAuInstance?.veh_Zgcs_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Zgcs_R}</div></td>

</tr>
<tr class="om-grid-row oddRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Clzzrq}</div></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol ">35.车辆制造日期</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol "><c:dataPicker name="veh_Clzzrq_R"  id="veh_Clzzrq_R"   editable="false" dateFormat="yy年mm月dd日"/></div></td>

</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">备注：${zcinfoReAuInstance?.veh_Bz}</div></td>
    <td align="left" abbr="id" class="col0" colspan="4"><div align="left" id="veh_Bz_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Bz != zcinfoReAuInstance?.veh_Bz_R }" >  style="color: red" </g:if>>
        备注：<g:textField name="veh_Bz_R" id="veh_Bz_R"  style="width:250px;"  value="${zcinfoReAuInstance?.veh_Bz_R}" />
    </div></td>
</tr>
<tr class="om-grid-row oddRow">
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol ">车辆制造信息：${zcinfoReAuInstance?.veh_clzzqyxx}</div></td>
    <td align="left" abbr="id" class="col0" colspan="8"><div align="left" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_clzzqyxx != zcinfoReAuInstance?.veh_clzzqyxx_R }" >  style="color: red" </g:if>>车辆制造信息：${zcinfoReAuInstance?.veh_clzzqyxx_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" class="indexCol"><div align="left" class="innerCol ">公告批次</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" class="innerCol ">${zcinfoReAuInstance?.veh_Ggpc}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol ">公告生效日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol " >${zcinfoReAuInstance?.veh_Ggsxrq}</div></td>
    <td></td>
    <td align="left" class="indexCol"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggpc != zcinfoReAuInstance?.veh_Ggpc_R }" >  style="color: red" </g:if>>公告批次</div></td>
    <td align="left" abbr="id" class="col0" colspan="3"><div align="left" id="veh_Ggpc_R_Div" class="innerCol "<g:if test="${zcinfoReAuInstance?.veh_Ggpc != zcinfoReAuInstance?.veh_Ggpc_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ggpc_R}</div></td>
    <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != zcinfoReAuInstance?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>公告生效日期</div></td>
    <td align="left" abbr="city" class="col1" colspan="3"><div align="left" id="veh_Ggsxrq_R_Div" class="innerCol " <g:if test="${zcinfoReAuInstance?.veh_Ggsxrq != zcinfoReAuInstance?.veh_Ggsxrq_R }" >  style="color: red" </g:if>>${zcinfoReAuInstance?.veh_Ggsxrq_R}</div></td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="left" abbr="id" class="col0" colspan="8">
        <div align="left" class="innerCol ">审核备注：<g:textArea name="auth_Remark" id="auth_Remark" cols="40" rows="3" maxlength="500" style="width:300px;height:100px;" value=""/></div>
    </td>
    <td></td>
    <td align="left" abbr="id" class="col0" colspan="8">
        <div align="left" class="innerCol ">SAP序列号：</div>
        <div align="left" class="innerCol ">
        <g:if test="${zcinfoReAuInstance?.SAP_No==null}">
            <g:textField name="SAP_No"  backgroundColor= 'green' id="SAP_No"  maxlength="50"  value="${zcinfoReAuInstance?.SAP_No}"/>
        </g:if>
        <g:else>
            <g:textField name="SAP_No" readonly="readonly" id="SAP_No"  maxlength="50"  value="${zcinfoReAuInstance?.SAP_No}"/>
        </g:else></div>
    </td>
</tr>
<tr class="om-grid-row evenRow">
    <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol ">
        %{--<g:submitButton name="update"  class="save"  value="打印正式合格证"/>--}%
        <a id="btn_printOfficial" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">打印正式合格证</a>
        <a id="btn_printVirtual" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">打印生成PDF</a>
        <a id="btn_printCoc" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">打印一致性证书</a>
        <a id="btn_printEnvir" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">打印环保信息清单</a>
        %{--<input type="button" name="Submit2" id="btn_printVirtual1" value="提交"  />--}%
        <a id="btn_audit" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black">审核不通过</a>
        <g:link controller="ZCInfoReplaceAuth" style="line-height:20px;font-size: 14px;" class="btn_basic blue_black" action="list"  >${message(code: 'default.button.exit.label', default: 'Return')}</g:link>
        <a id="cancel"  class="btn_basic blue_black" style="line-height:20px;font-size: 14px;" href ="javascript:history.go(-1);">返回</a>
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
    $(function() {
        $('#btn_select').click(function(){
            //更换审核页面选择公告时只有在打印底盘时才选公告，所以直接把公告关闭表示写上就OK
            var url = '${createLink(controller:'ZCInfo',action:'toNotice')}?veh_Need_Cer=1&turnOff=0';
            create(url,'公告信息',1440,900);
        });
        $('#noticese').hide();
        if($('#veh_Type_R').val()=='0'){//汽车
            $('#veh_Type_R1').hide();
            if($('#veh_Need_Cer').val()=='0'){
                $("#dipandiv").hide();
            }else if($('#veh_Need_Cer').val()=='1'){
                $("#quanxiangdiv").hide();
            }else  if($('#veh_Need_Cer').val()=='2'){
//                $('#noticese').show();
            }

            $('#btn_calculate').unbind("click");
            $('#btn_calculate').click(function(){
                    calculateVinCar();
            });

        }else if($('#veh_Type_R').val()=='1'){//农用车
            $('#veh_Type_R0').hide();
            $("#dipandiv").hide();

            $('#btn_calculate').unbind("click");
            $('#btn_calculate').click(function(){
                    calculateVinAgrveh();
             });

        }else{
            alert("车辆分类参数错误！");
        }

        $("input[name='RadioGroup2']") .change( function() {
            var url= "${createLink(controller:'ZCInfoReplaceAuth',action:'jsonChangeData')}";
            var id="";
            var type="";
            var vin="";
            var veh_Need_Cer = $('#veh_Need_Cer').val();
            if($(this).val()=='QX'){//全项
                $('#veh_Clztxx_R').val('QX');
                if(veh_Need_Cer==2){
                    $('#noticese').hide();
                }

                id=$("#zcinforeid").val();
                type="QX";
            }else{//底盘
                $('#veh_Clztxx_R').val('DP');
                if(veh_Need_Cer==2){
                    $('#noticese').show();
                }
                vin=$("#veh_Clsbdh").val();
                id=$("#zcinforeid").val();
                type="DP";
            }

            $.post(url,{"id":id,type:type,vin:vin},function(data,textStatus){
                var d=eval("("+data+")");
                changeData(d);
            },'text');

        });
   });
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

    function zhuanhuan(value) {
        if(value==null||value==''){
            return '-' ;
        }else{
            return value;
        }
    }
    function changeData(data){
        $('#veh_Zchgzbh_R').val(data.veh_Zchgzbh)//整车合格证编号
        $('#veh_Fdjxh_R').val(data.veh_Fdjxh)//发动机型号
        $('#veh_Fdjxh_R_Div').text(zhuanhuan(data.veh_Fdjxh))//发动机型号
        $('#veh_zdjgl_R').val(data.veh_zdjgl)//净功率
        $('#veh_Clfl_R').val(data.veh_Clfl)    //车辆类型
        $('#veh_Clxh_R').val(data.veh_Clxh)     //车辆型号
        $('#veh_Clxh_R_Div').text(zhuanhuan(data.veh_Clxh))     //车辆型号
        $('#veh_Zj_R').val(data.veh_Zj)            //轴距
        $('#veh_Zj_R_Div').text(zhuanhuan(data.veh_Zj))            //轴距
        $('#veh_Pl_R').val(data.veh_Pl)           //排量
        $('#veh_Gl_R').val(data.veh_Gl)           //功率
        $('#veh_Pl_R_Div').text(zhuanhuan(data.veh_Pl))           //排量
        $('#veh_Gl_R_Div').text(zhuanhuan(data.veh_Gl))           //功率
        $('#veh_Zzl_R').val(data.veh_Zzl)        //总质量
        $('#veh_Zzl_R_Div').text(zhuanhuan(data.veh_Zzl))        //总质量
        $('#veh_Clmc_R').val(data.veh_Clmc)      //车辆名称
        $('#veh_Clmc_R_Div').text(zhuanhuan(data.veh_Clmc))      //车辆名称
        $('#veh_Edzzl_R').val(data.veh_Edzzl)    //额定载质量
        $('#veh_Edzzl_R_Div').text(zhuanhuan(data.veh_Edzzl))    //额定载质量
        $('#veh_Pfbz_R').val(data.veh_Pfbz)   //排放标准
        $('#veh_Pfbz_R_Div').text(zhuanhuan(data.veh_Pfbz))   //排放标准
        $('#veh_Hxnbc_R').val(data.veh_Hxnbc)     //货厢内部尺寸
        $('#veh_Hxnbk_R').val(data.veh_Hxnbk)     //货厢内部尺寸
        $('#veh_Hxnbg_R').val(data.veh_Hxnbg)      //货厢内部尺寸
        $('#veh_Hxnbc_R_Div').text(zhuanhuan(data.veh_Hxnbc))     //货厢内部尺寸
        $('#veh_Hxnbk_R_Div').text(zhuanhuan(data.veh_Hxnbk))     //货厢内部尺寸
        $('#veh_Hxnbg_R_Div').text(zhuanhuan(data.veh_Hxnbg))      //货厢内部尺寸
        $('#veh_Zxxs_R').val(data.veh_Zxxs)     //转向形式
        $('#veh_Zxxs_R_Div').text(zhuanhuan(data.veh_Zxxs))     //转向形式
        $('#veh_Zbzl_R').val(data.veh_Zbzl)     //整备质量
        $('#veh_Zbzl_R_Div').text(zhuanhuan(data.veh_Zbzl))     //整备质量
        $('#veh_Zh_R').val(data.veh_Zh)      //轴荷
        $('#veh_Zh_R_Div').text(zhuanhuan(data.veh_Zh))      //轴荷
        $('#veh_Wkc_R').val(data.veh_Wkc)     //外廓尺寸
        $('#veh_Wkk_R').val(data.veh_Wkk)     //外廓尺寸
        $('#veh_Wkg_R').val(data.veh_Wkg)      //外廓尺寸
        $('#veh_Wkc_R_Div').text(zhuanhuan(data.veh_Wkc))     //外廓尺寸
        $('#veh_Wkk_R_Div').text(zhuanhuan(data.veh_Wkk))     //外廓尺寸
        $('#veh_Wkg_R_Div').text(zhuanhuan(data.veh_Wkg))      //外廓尺寸
        $('#veh_Zqyzzl_R').val(data.veh_Zqyzzl)      //准牵引总质量
        $('#veh_Zqyzzl_R_Div').text(zhuanhuan(data.veh_Zqyzzl))      //准牵引总质量
        $('#veh_Edzk_R').val(data.veh_Edzk)      //额定载客
        $('#veh_Edzk_R_Div').text(zhuanhuan(data.veh_Edzk))      //额定载客
        $('#veh_Zzllyxs_R').val(data.veh_Zzllyxs)      //载质量利用系数
        $('#veh_Zzllyxs_R_Div').text(zhuanhuan(data.veh_Zzllyxs))      //载质量利用系数
        $('#veh_Qyqtxx_R').val(data.veh_Qyqtxx)      //车辆制造企业其他信息
        $('#veh_Dpid_R').val(data.veh_Dpid)      //底盘ID
        $('#veh_Dpxh_R').val(data.veh_Dpxh)      //底盘型号
        $('#veh_Dpid_R_Div').text(zhuanhuan(data.veh_Dpid))      //底盘ID
        $('#veh_Dpxh_R_Div').text(zhuanhuan(data.veh_Dpxh))      //底盘型号
        $('#veh_Jsslx_R').val(data.veh_Jsslx)      //驾驶室类型
        $('#veh_Bgcazzdyxzzl_R').val(data.veh_Bgcazzdyxzzl)      //半挂车鞍座最大允许总质量
        $('#veh_Bgcazzdyxzzl_R_Div').text(zhuanhuan(data.veh_Bgcazzdyxzzl))      //半挂车鞍座最大允许总质量
        $('#veh_clzzqyxx_R').val(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#veh_Yh_R').val(data.veh_Yh)      //油耗
        $('#veh_Yh_R_Div').text(zhuanhuan(data.veh_Yh))      //油耗
        $('#veh_Zs_R').val(data.veh_Zs)      //轴数
        $('#veh_Zs_R_Div').text(zhuanhuan(data.veh_Zs))      //轴数
        $('#veh_Gbthps_R').val(data.veh_Gbthps)     //钢板弹簧片数
        $('#veh_Gbthps_R_Div').text(zhuanhuan(data.veh_Gbthps))     //钢板弹簧片数
        $('#veh_Clpp_R').val(data.veh_Clpp)     //车辆品牌
        $('#veh_Clpp_R_Div').text(zhuanhuan(data.veh_Clpp))     //车辆品牌
        $('#veh_Lts_R').val(data.veh_Lts)      //轮胎数
        $('#veh_Lts_R_Div').text(zhuanhuan(data.veh_Lts))      //轮胎数

        $('#veh_Ggpc_R').val(data.veh_Ggpc)      //公告批次
        $('#veh_Cpggh_R').val(data.veh_Cpggh)      //产品公告号
        $('#veh_Ggsxrq_R').val(data.veh_Ggsxrq)      //公告生效日期

        $('#veh_Ggpc_R_Div').text(data.veh_Ggpc)      //公告批次
        $('#veh_Ggsxrq_R_Div').text(data.veh_Ggsxrq)      //公告生效日期
        if(data.veh_Clzzqymc==null||data.veh_Clzzqymc==''){
            $('#veh_Clzzqymc_R').val('浙江飞碟汽车制造有限公司')     //车辆企业名称
            $('#veh_Clzzqymc_R_Div').text('浙江飞碟汽车制造有限公司')     //车辆企业名称
        } else{
            $('#veh_Clzzqymc_R').val(data.veh_Clzzqymc)     //车辆企业名称
            $('#veh_Clzzqymc_R_Div').text(zhuanhuan(data.veh_Clzzqymc))     //车辆企业名称
        }
        if(data.veh_Cpscdz==null||data.veh_Cpscdz==''){
            $('#veh_Cpscdz_R').val('山东省五莲县潮河镇驻地')      //生产地址
        } else{
            $('#veh_Cpscdz_R').val(data.veh_Cpscdz)      //生产地址
        }
        if(data.veh_Qybz==null||data.veh_Qybz==''){
            $('#veh_Qybz_R').val('Q/ZFK106-2009《飞碟牌系列载货汽 车及底盘通用技术条件》')     //企业标准
        } else{
            $('#veh_Qybz_R').val(data.veh_Qybz)     //企业标准
        }
        $('#veh_Zgcs_R').val(data.veh_Zgcs)      //最高车速
        $('#veh_Zgcs_R_Div').text(zhuanhuan(data.veh_Zgcs))      //最高车速
        $('#veh_Rlzl_R').val(data.veh_Rlzl)     //燃料种类
        $('#veh_Rlzl_R_Div').text(zhuanhuan(data.veh_Rlzl))     //燃料种类
        $('#veh_Qlj_R').val(data.veh_Qlj)     //前轮距
        $('#veh_Hlj_R').val(data.veh_Hlj)      //后轮距
        $('#veh_Qlj_R_Div').text(zhuanhuan(data.veh_Qlj))     //前轮距
        $('#veh_Hlj_R_Div').text(zhuanhuan(data.veh_Hlj))      //后轮距
        $('#veh_Ltgg_R').val(data.veh_Ltgg)     //轮胎规格
        $('#veh_Jsszcrs_R').val(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#veh_Jsszcrs_R_Div').text(zhuanhuan(data.veh_Jsszcrs))     //驾驶室准乘人数
        $('#veh_Bz_R').val(data.veh_Bz)      //备注
//        $('#veh_Bz_R_Div').text(zhuanhuan(data.veh_Bz))      //备注
        $('#veh_pzxlh_R').val(data.veh_pzxlh)      //配置序列号
        $('#veh_Qyid_R').val(data.veh_Qyid)      //企业ID
        $('#veh_Hzdfs_R').val(data.veh_Hzdfs)
//        $('#veh_Clztxx_R').val(data.veh_Clztxx)
        $('#veh_Jss_R').val(data.veh_Jss)      //驾驶室
        $('#veh_VinFourBit_R').val(data.veh_VinFourBit)      //vin第四位
    }

    //汽车VIN计算
    function calculateVinCar(){
        var url = '${createLink(controller:'ZCInfo',action:'calculateVIN')}';
        var kind='0';
        var veh_VinFourBit=$('#veh_VinFourBit_R').val();
        var veh_Zzl=$('#veh_Zzl_R').val();
        var veh_Jsslx=$('#veh_Jsslx_R').val(); //
        var veh_Rlzl=$('#veh_Rlzl_R').val();
        var veh_Pl=$('#veh_Pl_R').val();
        var veh_Zs=$('#veh_Zs_R').val();
        var veh_Zj=$('#veh_Zj_R').val();
        var veh_Zchgzbh=$('#veh_Zchgzbh_R').val();
        var veh_Cpscdz=$('#veh_Cpscdz_R').val();  //
        $.post(url,{"kind":kind,'veh_VinFourBit':veh_VinFourBit,'veh_Zzl':veh_Zzl,'veh_Jsslx':veh_Jsslx,'veh_Rlzl':veh_Rlzl,'veh_Pl':veh_Pl,'veh_Zs':veh_Zs,'veh_Zj':veh_Zj,'veh_Zchgzbh':veh_Zchgzbh,'veh_Cpscdz':veh_Cpscdz},function(data){
            if(data.flag=='0'){
                alert(data.msg);
            }else if(data.flag=='1'){
                $('#veh_Clsbdh_R').val(data.vin);
                if($('#veh_Clsbdh_R').val()!=$('#veh_Clsbdh').val()){
                document.getElementById("veh_Clsbdh_R").style.color="BLUE"
                }
            }
        },'json');
    }
     //农用车VIN计算
    function calculateVinAgrveh(){
        var url = '${createLink(controller:'ZCInfo',action:'calculateVIN')}';
        var kind='1';
        var veh_Clfl=$('#veh_Clfl_R').val();
        var veh_Zzl=$('#veh_Zzl_R').val();
        var veh_Jsslx=$('#veh_Jsslx_R').val();
        var veh_Zj=$('#veh_Zj_R').val();
        var veh_Gl=$('#veh_Gl_R').val();
        var veh_Rlzl=$('#veh_Rlzl_R').val();
        var veh_Zchgzbh=$('#veh_Zchgzbh_R').val();
        $.post(url,{"kind":kind,'veh_Clfl':veh_Clfl,'veh_Zzl':veh_Zzl,'veh_Rlzl':veh_Rlzl,'veh_Jsslx':veh_Jsslx,'veh_Zj':veh_Zj,'veh_Gl':veh_Gl,'veh_Zchgzbh':veh_Zchgzbh},function(data){
            if(data.flag=='0'){
                alert(data.msg);
            }else if(data.flag=='1'){
                $('#veh_Clsbdh_R').val(data.vin);
            }
        },'json');
    }

    $('#btn_printOfficial').click(function(){
        inputSapNo(1);

    });

    $("#btn_printVirtual").click(function(){
        inputSapNo(0);
    });
    $("#btn_printCoc").click(function(){
        var veh_Zchgzbh = $('#veh_Zchgzbh_R').val();
        if(veh_Zchgzbh==null|| $.trim(veh_Zchgzbh)==''){
            alert('打印一致性前请输入变更后合格证编号')
            return;
        }
        var searchCocPrnUrl = '${createLink(controller:'cocPrint',action:'jsonSearchCocPrnByZchgzbh')}?veh_Zchgzbh='+veh_Zchgzbh;
        $.post(searchCocPrnUrl,function(data,textStatus){
            var d=eval("("+data+")");
            if(d.cocstatus =='3'){
                alert("要打印一致性的合格证信息不存在，请先打印合格证");
            }else if(d.cocstatus =='1'){
                $.omMessageBox.confirm({
                    title:'确认',
                    content:'产品一致性证书已经打印,是否替换原来的信息?',
                    onClose:function(value){
                        if(value){
                            //清除已打印的一致性证书信息
                            var delCocPrUrl = '${createLink(controller:'cocPrint',action:'jsonDelCocPr')}?zcinfoID='+d.zcinfoID;
                            $.post(delCocPrUrl,function(data,textStatus){
                                if(textStatus=="success"){
                                    var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+d.zcinfoID+'&usertype=0&newCoC=1';
                                    create(url,'更换一致性证书',1440,900);
                                } else{
                                    alert("清除已打印的一致性证书信息失败！");
                                    return false;
                                }
                            },'text');

                        } else{
                            var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?cocPrnID='+d.zcinfoID+'&usertype=0&newCoC=1&oldQRcode=1';     //不替换一致性证书
                            create(url,'更换一致性证书',1440,900);                        }
                    }
                });
            }else if(d.cocstatus =='2'){
                alert("打印的产品一致性证书已经上传至服务器，不能再次打印！")
            }else{
                var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+d.zcinfoID+'&usertype=0&newCoC=1';
                create(url,'更换一致性证书',1440,900);            }
        },'text');
    });
    $("#btn_printEnvir").click(function(){
        var veh_Zchgzbh = $('#veh_Zchgzbh_R').val();
        if(veh_Zchgzbh==null|| $.trim(veh_Zchgzbh)==''){
            alert('打印环保信息清单前请输入变更后合格证编号')
            return;
        }
        var url = '${createLink(controller:'envirPrint',action:'cheJianList')}?veh_Zchgzbh='+veh_Zchgzbh;
        create(url,'公告信息',1440,900);
    });

    $("#btn_audit").click(function(){
        var veh_Zchgzbh = $('#veh_Zchgzbh_R').val();

        var url = '${createLink(controller:'ZCInfoReplaceAuth',action:'auditNoPass')}?id='+$("#zcinforeid").val()+"&authRemark="+encodeURI($("#auth_Remark").val());
        window.location.href = url;
    });
    function  inputSapNo(printType){
        if($('#SAP_No').val()==''){
            $.omMessageBox.prompt({
                title:'SAP车辆唯一号',
                content:'请填写SAP车辆唯一号！',
                onClose:function(value){
                    if(value===false){ //按了取消或ESC
                        return;
                    }else{
                        if(value!=''&&value!=null){
                            $('#SAP_No').val(value.trim())
                        }
                        saveAndPrint(printType)
                    }
                }
            });
        }else{
            saveAndPrint(printType)
        }
    }



    /**保存数据并保存成功后打印合格证信息
     * @param printType  0打印PDF，1打印正式合格证
     * @Create 2013-08-28 huxx
     */
    function saveAndPrint(printType){
        if($("#pass").val()==1){
            alert("更换记录已审核通过！不能重复审核！");
            return;
        }
        if($('#veh_Clztxx_R').val()=='QX'){
            if($("#qxPass").val()==1){
                alert("整车合格证信息已打印！不能重复打印！");
                return;
            }
        }
        //防止打底盘合格证时输入整车合格证号段，打整车合格证时输入底盘合格证号段
        var veh_Zchgzbh_R = $("#veh_Zchgzbh_R").val()
        var str =veh_Zchgzbh_R.substring(7,8);
        var radios = document.getElementsByName("RadioGroup1");
        for ( var i = 0; i < radios.length; i++) {
            if (radios[i].checked==true&&radios[i].value==1) {
                if($('#veh_Clztxx_R').val()=='QX'){
                    if(str=='7'){
                        alert("请输入整车合格证编号");
                        return;
                    }
                }else if($('#veh_Clztxx_R').val()=='DP'){
                    if(str=='6'){
                        alert("请输入底盘合格证编号");
                        return;
                    }
                }
            }
        }
        if($('#veh_Clztxx_R').val()=='DP'){
            if($("#dpPass").val()==1){
                alert("底盘合格证信息已打印！不能重复打印！");
                return;
            }
        }

        if($("#veh_Fzrq_R").val()!=$("#veh_Clzzrq_R").val()){
            alert("发证日期和车辆制造日期必须保持一致，请重新选择!");
            return;
        }

        var veh_Clsbdh_R=$('#veh_Clsbdh_R').val();
        if(veh_Clsbdh_R==null||veh_Clsbdh_R==''){
            alert('请先计算VIN');
            return;
        }
        var fzrq=$("#veh_Fzrq_R").val()
        if(fzrq!=''){
            var year=fzrq.substr(0,4) ;
            var month=fzrq.substr(5,2);
            var day=fzrq.substr(8,2);
            var t=Date.parse(new Date())-Date.parse(new Date(year,month-1,day));
            if(t<0){
                alert("发证日期不合法，请重试!");
                return false;
            }
        }else{
            alert("发证日期不合法，请重试!");
            return false;
        }

        var dialogId=showTipDialog();

        var params=$("#form").serialize();
        //验证合格证的公告信息是否符合条件
        $.post("${createLink(controller: 'ZCInfoReplaceAuth',action:'check')}",params,function(dd,textStatus){
            var d1=eval(dd);
            if(d1.flag=='failed'){
                parent.closeDialog(dialogId);
                alert(d1.msg);
                return;
            }else{
                $.post("${createLink(controller: 'ZCInfoReplaceAuth',action:'checkLtgg')}",params,function(dd,textStatus){
                    var d2=eval(dd);
                    if(d2.flag=='failed'){
                        parent.closeDialog(dialogId);
                        var a=confirm(d2.msg);
                        if(a==true){
                            var dialogId1=showTipDialog();
                            var updateUrl="${createLink(controller: 'ZCInfoReplaceAuth',action:'update')}?printType="+printType ;
                            if(printType==0){
                                var params=$("#form").serialize();
                                $.post(updateUrl,params,function(data,textStatus){
                                    var d3=eval("("+data+")");
                                    parent.closeDialog(dialogId1);
                                    var dd=alert(d3.msg);
                                    $("#pass").val(d3.pass);
                                },'text');

                            }else if(printType==1){
                                var result=print(dialogId1);
                                if(result!="failed"){
                                    var params=$("#form").serialize();//不能放在打印前执行，不然打印返回值获取不到
                                    $.post(updateUrl,params,function(data,textStatus){
                                        parent.closeDialog(dialogId1);
                                        var d4=eval("("+data+")");
                                        if(d4.flag=='sucessed'){
                                            $("#pass").val(d4.pass);
                                            alert("<div>"+result+"</div><div>"+d4.msg+"</div>");
                                        }else{
                                            alert(d4.msg);
                                        }
                                    },'text');
                                }
                            }else{
                                alert("参数错误");
                            }
                        }else{
                            return;
                        }
                    }else{
                        var updateUrl="${createLink(controller: 'ZCInfoReplaceAuth',action:'update')}?printType="+printType ;
                        if(printType==0){
                            var params=$("#form").serialize();
                            $.post(updateUrl,params,function(data,textStatus){
                                var d5=eval("("+data+")");
                                parent.closeDialog(dialogId);
                                var dd=alert(d5.msg);
                                $("#pass").val(d5.pass);
                            },'text');

                        }else if(printType==1){
                            var result=print(dialogId);
                            if(result!="failed"){
                                var params=$("#form").serialize();//不能放在打印前执行，不然打印返回值获取不到
                                $.post(updateUrl,params,function(data,textStatus){
                                    parent.closeDialog(dialogId);
                                    var d6=eval("("+data+")");
                                    if(d6.flag=='sucessed'){
                                        $("#pass").val(d6.pass);
                                        alert("<div>"+result+"</div><div>"+d6.msg+"</div>");
                                    }else{
                                        alert(d6.msg);
                                    }
                                },'text');
                            }
                        }else{
                            alert("参数错误");
                        }
                    }
                });
            }
        });
    }
     //打印正式合格证
    function print(dialogId){
        var iRtn;
        var VehCert2 = null;
        try{
            VehCert2 = new ActiveXObject("VCertificate.VehCert");
        }catch(err){
            return "failed";
        }
        if(VehCert2==null){
            parent.closeDialog(dialogId);
            alert('合格证U盾未连接好，请确认!')
            return "failed";
        }
        VehCert2.Veh_Clztxx=$('#veh_Clztxx_R').val();//车辆状态信息
        VehCert2.Veh_Zchgzbh=$('#veh_Zchgzbh_R').val();//整车合格证编号
        VehCert2.Veh_Dphgzbh=$('#veh_Dphgzbh_R').val();//底盘合格整编号
        VehCert2.Veh_Fzrq =$('#veh_Fzrq_R').val();//发证日起
        VehCert2.Veh_Clzzqymc=$('#veh_Clzzqymc_R').val();//车辆制造企业名称
        VehCert2.Veh_Qyid =$('#veh_Qyid_R').val();//企业ID
        VehCert2.Veh_Clfl=$('#veh_Clfl_R').val();     //车辆分类
        VehCert2.Veh_Clmc=$('#veh_Clmc_R').val();     //车辆名称
        VehCert2.Veh_Clpp=$('#veh_Clpp_R').val();   //车辆品牌
        VehCert2.Veh_Csys=$('#veh_Csys_R').val();          //车身颜色
        VehCert2.Veh_Clxh =$('#veh_Clxh_R').val(); //车辆型号
        VehCert2.Veh_Dpxh=$('#veh_Dpxh_R').val();//底盘型号
        VehCert2.Veh_Dpid=$('#veh_Dpid_R').val();//底盘ID
        VehCert2.Veh_Clsbdh=$('#veh_Clsbdh_R').val();//车辆识别代号
        VehCert2.Veh_Fdjh=$('#veh_Fdjh_R').val();//发动机号
        VehCert2.Veh_Fdjxh=$('#veh_Fdjxh_R').val();//发动机型号
        VehCert2.Veh_Rlzl=$('#veh_Rlzl_R').val();     //燃料种类
        VehCert2.Veh_Pfbz=$('#veh_Pfbz_R').val();    //排放标准
        VehCert2.Veh_Pl=$('#veh_Pl_R').val();           //排量
        VehCert2.Veh_Gl=$('#veh_Gl_R').val();          //功率
        VehCert2.Veh_Zxxs =$('#veh_Zxxs_R').val();       //转向形式
        VehCert2.Veh_Qlj=$('#veh_Qlj_R').val();            //前轮距
        VehCert2.Veh_Hlj=$('#veh_Hlj_R').val();         //后轮距
        VehCert2.Veh_Lts=$('#veh_Lts_R').val();           //轮胎数
        VehCert2.Veh_Ltgg=$('#veh_Ltgg_R').val();         //轮胎规格
        VehCert2.Veh_Gbthps=$('#veh_Gbthps_R').val();      //钢板弹簧片数
        VehCert2.Veh_Zj=$('#veh_Zj_R').val();      //轴距
        VehCert2.Veh_Zh=$('#veh_Zh_R').val();      //轴荷
        VehCert2.Veh_Zs=$('#veh_Zs_R').val();//轴数
        VehCert2.Veh_Wkc=$('#veh_Wkc_R').val(); //外廓长
        VehCert2.Veh_Wkg=$('#veh_Wkg_R').val();    //外廓高
        VehCert2.Veh_Wkk=$('#veh_Wkk_R').val();  //外廓宽
        VehCert2.Veh_Hxnbc=$('#veh_Hxnbc_R').val();   //货箱内部长
        VehCert2.Veh_Hxnbg=$('#veh_Hxnbg_R').val();   //货箱内部高
        VehCert2.Veh_Hxnbk=$('#veh_Hxnbk_R').val();    //货箱内部宽
        VehCert2.Veh_Zzl=$('#veh_Zzl_R').val();    //总质量
        VehCert2.Veh_Edzzl=$('#veh_Edzzl_R').val();   //额定载质量
        VehCert2.Veh_Zbzl=$('#veh_Zbzl_R').val();   //整备质量
        VehCert2.Veh_Zzllyxs=$('#veh_Zzllyxs_R').val();//载质量利用系数
        VehCert2.Veh_Zqyzzl=$('#veh_Zqyzzl_R').val();//准牵引总质量
        VehCert2.Veh_Edzk=$('#veh_Edzk_R').val();//额定载客
        VehCert2.Veh_Bgcazzdyxzzl=$('#veh_Bgcazzdyxzzl_R').val();//半挂车按座
        VehCert2.Veh_Jsszcrs=$('#veh_Jsszcrs_R').val(); //驾驶室准乘人数
        VehCert2.Veh_Yh=$('#veh_Yh_R').val();//油耗

        VehCert2.Veh_Ggpc = $('#veh_Ggpc_R').val();    //公告批次
        VehCert2.Veh_Cpggh = $('#veh_Cpggh_R').val();       //产品公告号
        VehCert2.Veh_Ggsxrq =$('#veh_Ggsxrq_R').val();       //公告生效日期
        VehCert2.Veh_Zzbh = $('#veh_Zzbh_R').val();
        VehCert2.Veh_Zgcs =$('#veh_Zgcs_R').val();
        VehCert2.Veh_Clzzrq=$('#veh_Clzzrq_R').val();   //车辆制造日期
        VehCert2.Veh_Bz=$('#veh_Bz_R').val();     //备注
        VehCert2.Veh_Qybz=$('#veh_Qybz_R').val();      //企业标准
        VehCert2.Veh_Cpscdz =$('#veh_Cpscdz_R').val();       //产品生产地址
        VehCert2.Veh_Qyqtxx=$('#veh_Qyqtxx_R').val();        //企业其它信息
        VehCert2.Veh_Connect="1";       //连接串口
        VehCert2.Veh_Baud="9600" ;        //串口波特率
        VehCert2.Veh_Parity ="N";       //串口奇偶校验
        VehCert2.Veh_Databits ="8";    //串口数据位
        VehCert2.Veh_Stopbits ="1";    //串口停止位
        if($('#veh_Rlzl_R').val()== "电"){
            VehCert2.Veh_Cddbj = "1";         //纯电动标记
        }else{
            VehCert2.Veh_Cddbj = "2";         //纯电动标记
        }

        <g:if test="${printSet?.top}">
        VehCert2.Veh_PrintPosTop =${printSet?.top};   //合格证打印的上边距
        </g:if>
        <g:else>
        VehCert2.Veh_PrintPosTop =10;   //合格证打印的上边距
        </g:else>
        <g:if test="${printSet?.left}">
        VehCert2.Veh_PrintPosLeft =${printSet?.left};   //合格证打印的左边距
        </g:if>
        <g:else>
        VehCert2.Veh_PrintPosLeft =10;   //合格证打印的上边距
        </g:else>
        VehCert2.Veh_Clscdwmc =$('#veh_Clzzqymc_R').val();          //车辆生产单位名称

        if(VehCert2.Veh_Qlj.indexOf("/")!='-1'){
            VehCert2.Veh_Zxzs='2';
        }else{
            VehCert2.Veh_Zxzs='1';
        }

        iRtn = VehCert2.PrtParaTbl(1,"FDLS-325D-SKDI-E8EK");
        if(iRtn==1){
            var Veh_Dywym= VehCert2.Veh_Dywym
            var Veh_Jyw= VehCert2.Veh_Jyw
            var Veh_wzhgzbh=VehCert2.GetCertificateNO($('#veh_Zchgzbh_R').val() ,"N3PL-SX32-AEU2-BC6S");
            $('#veh_Dywym_R').val(Veh_Dywym) ;
            $('#veh_Zchgzbh_0_R').val(Veh_wzhgzbh) ;
            $('#vercode_r').val(Veh_Jyw) ;
            $("#web_virtualcode").val(VehCert2.Veh_Zchgzbh);

            return "";
        }else{
            parent.closeDialog(dialogId);
            alert("打印失败，错误原因：" + VehCert2.Veh_ErrorInfo);
            return "failed";
        }
    }
</script>
</body>
</html>