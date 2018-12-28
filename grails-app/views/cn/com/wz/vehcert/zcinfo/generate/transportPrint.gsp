<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="运输管理科合格证打印" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;margin:0px 0px">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div style="background-color: #FAFAFA;line-height: 40px; box-shadow: 0 0 3px 1px #aaaaaa;">
            <div style="padding-left: 20px;">
                <a id="btn_query"  class="btn_basic blue_black" >查看</a>
                %{--<a id="btn_print"   class="btn_basic blue_black" >打印正式合格证</a>--}%
                <a id="btn_print3"   class="btn_basic blue_black" >打印PDF</a>
                %{--<a id="btn_printSet"   class="btn_basic blue_black" >打印设置</a>--}%
            </div>
        </div>
        <div style="margin-right:8px;margin-top:8px;">
            <form id="form" action="${createLink(controller: 'ZCInfo',action:'managesave')}" method="post">
                <input type="hidden" name="web_virtualcode" id="web_virtualcode" value="${zcinfoInstance.web_virtualcode}"/>
                <input type="hidden" name="veh_Dywym" id="veh_Dywym" value="${zcinfoInstance.veh_Dywym}"/>
                <input type="hidden" name="vercode" id="vercode" value="${zcinfoInstance.vercode}"/>
                <input type="hidden" name="veh_Zchgzbh_0" id="veh_Zchgzbh_0" value="${zcinfoInstance.veh_Zchgzbh_0}"/>
                <input type="hidden" name="kind" id="kind" value="${kind}"/>
                <input type="hidden" name="veh_Type" id="veh_Type" value="${zcinfoInstance.veh_Type}"/>
                <g:hiddenField name="infoid" value="${zcinfoInstance.id }"/>
                <g:hiddenField name="veh_Qyid" value="${zcinfoInstance.veh_Qyid }"/>
                <g:hiddenField name="veh_Hzdfs" value="${zcinfoInstance.veh_Hzdfs }"/>
                <g:hiddenField name="veh_Clztxx" value="${zcinfoInstance.veh_Clztxx }"/>
                <g:hiddenField name="veh_Jss" value="${zcinfoInstance.veh_Jss }"/>
                <g:hiddenField name="veh_VinFourBit" value="${zcinfoInstance.veh_VinFourBit }"/>
                <g:hiddenField name="veh_Need_Cer" value="0"/>
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>

                            <tr class="om-grid-row evenRow">
                                <td colspan="1">
                                    合格证编号：<g:textField name="veh_Zchgzbh_Re" id="veh_Zchgzbh_Re" maxlength="50" style="width: 180px;"  value="${vehZchgzbhRe}"/>
                                </td>
                                <td colspan="3">
                                    <g:if test="${zcinfoInstance.upload_Path}">
                                        <a href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${zcinfoInstance?.veh_Zchgzbh}.pdf&filePath=${zcinfoInstance.upload_Path}" id="down_PDF" class="btn_basic blue_black">下载PDF</a>
                                    </g:if>
                                </td>
                            </tr>
                            <tr class="om-grid-row oddRow">
                                <td align="left" class="indexCol"><div align="left" class="innerCol " style="width:200px">选择车辆状态信息：
                                    <p>
                                        <g:if test="${zcinfoInstance.veh_Clztxx}">

                                            <label>
                                                <input type="radio" name="type" value="QX" <g:if test="${zcinfoInstance.veh_Clztxx == 'QX'}"> checked="checked"</g:if>/>
                                                全项（QX）
                                            </label>
                                            <label>
                                                <input type="radio" name="type" value="DP" <g:if test="${zcinfoInstance.veh_Clztxx == 'DP'}"> checked="checked"</g:if> />
                                                底盘（DP）
                                            </label>
                                        </g:if>
                                        <g:else>
                                            <input type="radio" name="type" value="QX" checked="checked" />
                                            全项（QX）
                                            <input type="radio" name="type" value="DP" />
                                            底盘（DP）
                                        </g:else>
                                        <br />
                                    </p>
                                </div></td>
                                <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">
                                    %{--<a  id="btn_select" style="line-height:30px;width:120px;padding-left: 20px;margin:10px 60px; font-size:14px;" class="btn_basic blue_black"  >按公告号提取数据</a>--}%
                                </div></td>
                                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:120px">
                                    %{--扫描串口--}%
                                    %{--<select name="selOp" id="selOp">--}%
                                    %{--<option value="COM1">COM1</option>--}%
                                    %{--<option value="COM2">COM2</option>--}%
                                    %{--<option value="COM3" selected="true">COM3</option>--}%
                                    %{--<option value="COM4">COM4</option>--}%
                                    %{--<option value="COM5">COM5</option>--}%
                                    %{--<option value="COM6">COM6</option>--}%
                                    %{--<option value="COM7">COM7</option>--}%
                                    %{--<option value="COM8">COM8</option>--}%
                                    %{--</select></br>--}%
                                    %{--<a id="scan"style="width:120px; line-height:30px;padding-left: 20px;font-size: 14px;" class="btn_basic blue_black" >合格证扫描验证</a>--}%
                                </div></td>
                                <td align="left" abbr="address" class="col2">
                                </td>
                            </tr>
                            <tr class="om-grid-row evenRow">
                                <td align="left" abbr="" class="indexCol">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td >
                                                    合格证编号
                                                </td>
                                                <td style="width: 165px">
                                                    ${zcinfoInstance?.veh_Zchgzbh}
                                                    <g:hiddenField name="veh_Zchgzbh" id="veh_Zchgzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zchgzbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机型号
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_Fdjxh}
                                                    <g:hiddenField name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjxh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    净  功  率
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_zdjgl}
                                                    <g:hiddenField name="veh_zdjgl" id="veh_zdjgl" maxlength="50"  value="${zcinfoInstance?.veh_zdjgl}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="id" class="col0">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    车 身 颜 色
                                                </td>
                                                <td style="width: 170px">
                                                    ${zcinfoInstance?.veh_Csys}
                                                    <g:hiddenField name="veh_Csys" id="veh_Csys" maxlength="50"  value="${zcinfoInstance?.veh_Csys}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发 动 机 号
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_Fdjh}
                                                    <g:hiddenField name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${zcinfoInstance?.veh_Fdjh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘合格证编号
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_Dphgzbh}
                                                    <g:hiddenField name="veh_Dphgzbh" id="veh_Dphgzbh" maxlength="50"  value="${zcinfoInstance?.veh_Dphgzbh}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="city" class="col1">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    发 证 日 期
                                                </td>
                                                <td style="width: 180px">
                                                    ${zcinfoInstance?.veh_Fzrq}
                                                    <g:hiddenField name="veh_Fzrq" id="veh_Fzrq" editable="false" dateFormat="yy年mm月dd日" value="${zcinfoInstance?.veh_Fzrq}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    车辆制造日期
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_Clzzrq}
                                                    <g:hiddenField name="veh_Clzzrq" id="veh_Clzzrq" editable="false" dateFormat="yy年mm月dd日" value="${zcinfoInstance?.veh_Clzzrq}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    纸 张 编 号
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_Zzbh}
                                                    <g:hiddenField name="veh_Zzbh" id="veh_Zzbh" maxlength="50"  value="${zcinfoInstance?.veh_Zzbh}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td align="left" >
                                    <div align="left">
                                        <table>
                                            <tr>
                                                <td>
                                                    车辆识别代号
                                                </td>
                                                <td style="width: 180px">
                                                    ${zcinfoInstance?.veh_Clsbdh}
                                                    <g:hiddenField name="veh_Clsbdh" id="veh_Clsbdh" style="width:150px;"  value="${zcinfoInstance?.veh_Clsbdh}"/>
                                                </td>
                                                <td rowspan="2">
                                                    %{--<a  id="btn_calculate"  class="btn_basic blue_black"  style="line-height: 50px;font-size: 14px;" value="计算VIN">计算VIN</a>--}%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    配置序列号
                                                </td>
                                                <td>
                                                    ${zcinfoInstance?.veh_pzxlh}
                                                    <g:hiddenField name="veh_pzxlh" id="veh_pzxlh" style="width:150px;"  value="${zcinfoInstance?.veh_pzxlh}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr class="om-grid-row oddRow">
                                <td align="left" abbr="" class="indexCol" colspan="4">
                                    <div align="left" class="innerCol ">
                                        <table style="width: 100%">
                                            其它影响VIN的数据：
                                            <tr>
                                                <td style="width: 215px">

                                                    车辆类型 &nbsp;&nbsp;<g:hiddenField name="veh_Clfl" id="veh_Clfl" maxlength="50"  value="${zcinfoInstance?.veh_Clfl}"/>
                                                    ${zcinfoInstance?.veh_Clfl}
                                                </td>
                                                <td style="width: 215px">
                                                    车辆型号 &nbsp;&nbsp;<g:hiddenField name="veh_Clxh" id="veh_Clxh" maxlength="50"  value="${zcinfoInstance?.veh_Clxh}"/>
                                                    ${zcinfoInstance?.veh_Clxh}
                                                </td>
                                                <td>
                                                    轴距  &nbsp;&nbsp;<g:hiddenField name="veh_Zj" id="veh_Zj" maxlength="50"  value="${zcinfoInstance?.veh_Zj}"/>
                                                    ${zcinfoInstance?.veh_Zj}
                                                </td>
                                                <td>
                                                    排量和功率  &nbsp;&nbsp;<g:hiddenField name="veh_Pl" id="veh_Pl"  size='5'   value="${zcinfoInstance?.veh_Pl}"/>
                                                    <g:hiddenField name="veh_Gl" id="veh_Gl"  size='5'  value="${zcinfoInstance?.veh_Gl}"/>
                                                    ${zcinfoInstance?.veh_Pl} &nbsp;&nbsp; ${zcinfoInstance?.veh_Gl}
                                                </td>
                                                <td>
                                                    总质量  &nbsp;&nbsp;<g:hiddenField name="veh_Zzl" id="veh_Zzl" maxlength="50"  value="${zcinfoInstance?.veh_Zzl}"/>
                                                    ${zcinfoInstance?.veh_Zzl}
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr class="om-grid-row evenRow">
                                <td align="left" abbr=""colspan="4">
                                    <table>
                                        <tr>
                                            <td colspan="8">
                                                其它数据：
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>车辆名称</td><td style="width: 185px"><g:hiddenField name="veh_Clmc" id="veh_Clmc" maxlength="50"  value="${zcinfoInstance?.veh_Clmc}"/> ${zcinfoInstance?.veh_Clmc}</td>
                                            <td>额定载质量</td><td style="width: 185px"><g:hiddenField name="veh_Edzzl" id="veh_Edzzl" maxlength="50"  value="${zcinfoInstance?.veh_Edzzl}"/> ${zcinfoInstance?.veh_Edzzl}</td>
                                            <td>排放标准</td><td style="width: 185px"><g:hiddenField name="veh_Pfbz" id="veh_Pfbz" maxlength="50"  value="${zcinfoInstance?.veh_Pfbz}"/> ${zcinfoInstance?.veh_Pfbz}</td>
                                            <td>货厢内部尺寸（长*宽*高）</td>
                                            <td style="width: 185px"><g:hiddenField name="veh_Hxnbc" id="veh_Hxnbc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbc}"/> ${zcinfoInstance?.veh_Hxnbc}&nbsp;
                                            <g:hiddenField name="veh_Hxnbk" id="veh_Hxnbk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hxnbk}"/> ${zcinfoInstance?.veh_Hxnbk}&nbsp;
                                            <g:hiddenField name="veh_Hxnbg" id="veh_Hxnbg" size="5"  maxlength="50"  value="${zcinfoInstance?.veh_Hxnbg}"/> ${zcinfoInstance?.veh_Hxnbg}&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>转向形式</td><td><g:hiddenField name="veh_Zxxs" id="veh_Zxxs" maxlength="50"  value="${zcinfoInstance?.veh_Zxxs}"/>${zcinfoInstance?.veh_Zxxs}</td>
                                            <td>整备质量</td><td><g:hiddenField name="veh_Zbzl" id="veh_Zbzl" maxlength="50"  value="${zcinfoInstance?.veh_Zbzl}"/>${zcinfoInstance?.veh_Zbzl}</td>
                                            <td>轴荷</td><td><g:hiddenField name="veh_Zh" id="veh_Zh" maxlength="50"  value="${zcinfoInstance?.veh_Zh}"/>${zcinfoInstance?.veh_Zh}</td>
                                            <td>外廓尺寸（长*宽*高）</td>
                                            <td>
                                                <g:hiddenField name="veh_Wkc" id="veh_Wkc" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkc}"/>${zcinfoInstance?.veh_Wkc}&nbsp;
                                                <g:hiddenField name="veh_Wkk" id="veh_Wkk" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkk}"/>${zcinfoInstance?.veh_Wkk}&nbsp;
                                                <g:hiddenField name="veh_Wkg" id="veh_Wkg" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Wkg}"/>${zcinfoInstance?.veh_Wkg}&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>钢板弹簧片数 </td><td><g:hiddenField name="veh_Gbthps" id="veh_Gbthps" maxlength="50"  value="${zcinfoInstance?.veh_Gbthps}"/>${zcinfoInstance?.veh_Gbthps}</td>
                                            <td>车辆品牌</td><td><g:hiddenField name="veh_Clpp" id="veh_Clpp" maxlength="50"  value="${zcinfoInstance?.veh_Clpp}"/>${zcinfoInstance?.veh_Clpp}</td>
                                            <td>轮胎数</td><td><g:hiddenField name="veh_Lts" id="veh_Lts" maxlength="50"  value="${zcinfoInstance?.veh_Lts}"/>${zcinfoInstance?.veh_Lts}</td>
                                            <td>车辆企业名称</td><td><g:hiddenField name="veh_Clzzqymc" id="veh_Clzzqymc" maxlength="50"  value="${zcinfoInstance?.veh_Clzzqymc}"/>${zcinfoInstance?.veh_Clzzqymc}</td>
                                        </tr>
                                        <tr>
                                            <td>企业标准</td><td style="width: 185px"><g:hiddenField name="veh_Qybz" id="veh_Qybz" maxlength="50"  value="${zcinfoInstance?.veh_Qybz}"/>
                                            <g:if test="${zcinfoInstance?.veh_Qybz?.length()>20}">
                                                ${zcinfoInstance?.veh_Qybz.substring(0,19)}
                                            </g:if><g:else>
                                                ${zcinfoInstance?.veh_Qybz}
                                            </g:else></td>
                                            <td>最高车速</td><td><g:hiddenField name="veh_Zgcs" id="veh_Zgcs" maxlength="50"  value="${zcinfoInstance?.veh_Zgcs}"/>${zcinfoInstance?.veh_Zgcs}</td>
                                            <td>燃料种类</td><td><g:hiddenField name="veh_Rlzl" id="veh_Rlzl" maxlength="50"  value="${zcinfoInstance?.veh_Rlzl}"/>${zcinfoInstance?.veh_Rlzl}</td>
                                            <td>轮距（前/后）</td>
                                            <td>
                                                <g:hiddenField name="veh_Qlj" id="veh_Qlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Qlj}"/>${zcinfoInstance?.veh_Qlj} &nbsp;
                                                <g:hiddenField name="veh_Hlj" id="veh_Hlj" size="5" maxlength="50"  value="${zcinfoInstance?.veh_Hlj}"/>${zcinfoInstance?.veh_Hlj}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>轮胎规格</td><td><g:hiddenField name="veh_Ltgg" id="veh_Ltgg" maxlength="50"  value="${zcinfoInstance?.veh_Ltgg}"/>${zcinfoInstance?.veh_Ltgg}</td>
                                            <td>驾驶室准乘人数（人）</td><td><g:hiddenField name="veh_Jsszcrs" id="veh_Jsszcrs" maxlength="50"  value="${zcinfoInstance?.veh_Jsszcrs}"/>${zcinfoInstance?.veh_Jsszcrs}</td>
                                            <td>备注</td><td> <g:hiddenField name="veh_Bz" id="veh_Bz" maxlength="50"  value="${zcinfoInstance?.veh_Bz}"/>${zcinfoInstance?.veh_Bz}</td>
                                            <td>准牵引总质量</td><td> <g:hiddenField name="veh_Zqyzzl" id="veh_Zqyzzl" maxlength="50"  value="${zcinfoInstance?.veh_Zqyzzl}"/>${zcinfoInstance?.veh_Zqyzzl}</td>
                                        </tr>
                                        <tr>
                                            <td>额定载客</td><td><g:hiddenField name="veh_Edzk" id="veh_Edzk" maxlength="50"  value="${zcinfoInstance?.veh_Edzk}"/>${zcinfoInstance?.veh_Edzk}</td>
                                            <td>载质量利用系数</td><td><g:hiddenField name="veh_Zzllyxs" id="veh_Zzllyxs" maxlength="50"  value="${zcinfoInstance?.veh_Zzllyxs}"/>${zcinfoInstance?.veh_Zzllyxs}</td>
                                            <td>车辆制造企业其他信息</td><td><g:hiddenField name="veh_Qyqtxx" id="veh_Qyqtxx" maxlength="50"  value="${zcinfoInstance?.veh_Qyqtxx}"/>${zcinfoInstance?.veh_Qyqtxx}</td>
                                            <td>底盘ID</td><td><g:hiddenField name="veh_Dpid" id="veh_Dpid" maxlength="50"  value="${zcinfoInstance?.veh_Dpid}"/>${zcinfoInstance?.veh_Dpid}</td>
                                        </tr>
                                        <tr>
                                            <td>底盘型号 </td><td><g:hiddenField name="veh_Dpxh" id="veh_Dpxh" maxlength="50"  value="${zcinfoInstance?.veh_Dpxh}"/>${zcinfoInstance?.veh_Dpxh}</td>
                                            <td>驾驶室类型</td><td><g:hiddenField name="veh_Jsslx" id="veh_Jsslx" maxlength="50"  value="${zcinfoInstance?.veh_Jsslx}"/>${zcinfoInstance?.veh_Jsslx}</td>
                                            <td>半挂车鞍座最大允许总质量</td><td><g:hiddenField name="veh_Bgcazzdyxzzl" id="veh_Bgcazzdyxzzl" maxlength="50"  value="${zcinfoInstance?.veh_Bgcazzdyxzzl}"/>${zcinfoInstance?.veh_Bgcazzdyxzzl}</td>
                                            <td>生产地址</td><td><g:hiddenField name="veh_Cpscdz" id="veh_Cpscdz" maxlength="50"  value="${zcinfoInstance?.veh_Cpscdz}"/>${zcinfoInstance?.veh_Cpscdz}</td>
                                        </tr>
                                        <tr>
                                            <td>油耗</td><td><g:hiddenField name="veh_Yh" id="veh_Yh" maxlength="50"  value="${zcinfoInstance?.veh_Yh}"/>${zcinfoInstance?.veh_Yh}</td>
                                            <td>轴数</td><td><g:hiddenField name="veh_Zs" id="veh_Zs" maxlength="50"  value="${zcinfoInstance?.veh_Zs}"/>${zcinfoInstance?.veh_Zs}</td>
                                            <td>公告批次</td><td><g:hiddenField name="veh_Ggpc" id="veh_Ggpc" maxlength="50"  value="${zcinfoInstance?.veh_Ggpc}"/>${zcinfoInstance?.veh_Ggpc}</td>
                                            <td>产品公告号</td><td><g:hiddenField name="veh_Cpggh" id="veh_Cpggh" maxlength="50"  value="${zcinfoInstance?.veh_Cpggh}"/>${zcinfoInstance?.veh_Cpggh}</td>
                                        </tr>
                                        <tr>
                                            <td>车辆制造企业信息</td><td><g:hiddenField name="veh_clzzqyxx" id="veh_clzzqyxx" maxlength="50"  value="${zcinfoInstance?.veh_clzzqyxx}"/>${zcinfoInstance?.veh_clzzqyxx}</td>
                                            <td>公告生效日期</td><td><g:hiddenField name="veh_Ggsxrq" id="veh_Ggsxrq" maxlength="50"  value="${zcinfoInstance?.veh_Ggsxrq}"/>${zcinfoInstance?.veh_Ggsxrq}</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
            </form>
        </div>

    </div>
</div>

</div>
<script>
    $(function() {

        if($('#veh_Clztxx').val()==''){
            $('#veh_Clztxx').val('QX');
        }
        $("input[name='type']") .change( function() {
            if($(this).val()=='QX'){//全项
                $('#veh_Clztxx').val('QX');
                $('#veh_Need_Cer').val('0');
            }else{
                $('#veh_Clztxx').val('DP');
                $('#veh_Need_Cer').val('1');
            }
        })


        %{--$('#btn_select').click(function(){--}%
        %{--var veh_Need_Cer=$('#veh_Need_Cer').val();--}%
        %{--var turnOff=$('#turnOff').val();--}%
        %{--var url = '${createLink(controller:'ZCInfo',action:'toNotice')}?turnOff='+turnOff;--}%
        %{--create(url,'公告信息',1440,900);--}%
        %{--});--}%
        %{--$('#btn_printSet').click(function(){--}%
        %{--var url = '${createLink(controller:'printSet',action:'edit')}';--}%
        %{--create(url,'打印设置',400,300);--}%
        %{--});--}%
        %{--$('#veh_Csys').omCombo({--}%
        %{--multi : false ,--}%
        %{--dataSource : "${createLink(controller:'ZCInfo',action:'jsonColors')}?random="+Math.random()--}%
        %{--});--}%

        $('#btn_query').click(function(){
            var url = '${createLink(controller:'ZCInfo',action:'transportPrintSearch')}';
            var   veh_Zchgzbh_Re =$('#veh_Zchgzbh_Re').val();
            var kind = $('#kind').val()
            var veh_Type = $('#veh_Type').val();
            veh_Zchgzbh_Re= $.trim(veh_Zchgzbh_Re);
            if(veh_Zchgzbh_Re.length==0){
                alert('必须输入原合格证编号!')
            } else{
                window.location.href = url+'?kind='+kind+'&vehZchgzbhRe='+veh_Zchgzbh_Re+'&veh_Type='+veh_Type;
            }
        });
//            $('#btn_calculate').click(function(){
//                calculateVin();
//            });
//            $('#scan').click(function(){
//                var VehCert = new ActiveXObject("VCertificate.VehCert");
//                var objSel = document.getElementById("selOp").value;
//                VehCert.Veh_Connect=objSel;
//                VehCert.ViewBarcodeInfo("PER4-54LD-WXQI-DK87");
//            });
        $('#btn_print3').click(function(){
            saveAndPrint(0);
        });
//            $('#btn_print').click(function(){
//                saveAndPrint(1);
//            });
    });
    /**保存数据并保存成功后打印合格证信息
     * @param printType  0打印PDF，1打印正式合格证
     * @Create 2013-08-28 huxx
     */
    function saveAndPrint(printType){
        //原合格证必填
        var   veh_Zchgzbh_Re =$('#veh_Zchgzbh_Re').val();
        veh_Zchgzbh_Re= $.trim(veh_Zchgzbh_Re);
        if(veh_Zchgzbh_Re.length==0){
            alert('必须输入原合格证编号!')
            return;
        }

        var veh_Fdjh=$("#veh_Fdjh").val();
        if(veh_Fdjh==null ||  $.trim(veh_Fdjh)=='') {
            alert("保存前，请输入发动机号！");
            return;
        }

        var  veh_Zchgzbh=$('#veh_Zchgzbh').val();
        var veh_Clsbdh   =$('#veh_Clsbdh').val();
        if(veh_Clsbdh==''){
            alert('请计算VIN!');
            return;
        }

        var fzrq=$("#veh_Fzrq").val()
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

        if(veh_Zchgzbh.length==14){
            var dialogId=showTipDialog();

            var params=$("#form").serialize();
            //验证合格证的公告信息是否符合条件
            $.post("${createLink(controller: 'ZCInfo',action:'transportCheck')}",params,function(dd,textStatus){

                var d1=eval(dd);
                if(d1.flag=='failed'){
                    parent.closeDialog(dialogId);
                    alert(d1.msg);
                    return;
                } else{
                    var updateUrl="${createLink(controller: 'ZCInfo',action:'managesave')}?printType="+printType+'&transPrint=1';

                    if(printType==0){ //打印虚拟合格证，先保存后打印

                        $.post(updateUrl,params,function(data,textStatus){
                            var d=eval("("+data+")");
                            parent.closeDialog(dialogId);
                            alert(d.msg);
                        },'text');
                    }else if(printType==1){ //打印正式合格证，先打印后保存
                        var result=print(dialogId);
                        if(result!="failed"){
                            var p=$("#form").serialize();//不能放在打印前执行，不然打印返回值获取不到
                            $.post(updateUrl,p,function(data,textStatus){
                                parent.closeDialog(dialogId);
                                var d=eval("("+data+")");
                                if(d.flag=='sucessed'){
                                    alert("<div>"+result+"</div><div>"+d.msg+"</div>");
                                    clearData();
                                }else{
                                    alert(d.msg);
                                }
                            },'text');
                        }
                    }
                }
            });

        }else{
            alert('合格证编号不符合要求,请查证')
        }
    }
    function print(dialogId){
        var iRtn;
        var VehCert2;
        try{
            VehCert2 = new ActiveXObject("VCertificate.VehCert");
        }catch(err){
            parent.closeDialog(dialogId);
            alert("请使用IE浏览器，并调低IE的安全级别！");
            return "failed";
        }
        VehCert2.Veh_Clztxx=$('#veh_Clztxx').val();//车辆状态信息
        VehCert2.Veh_Zchgzbh=$('#veh_Zchgzbh').val();//整车合格证编号
        VehCert2.Veh_Dphgzbh=$('#veh_Dphgzbh').val();//底盘合格整编号
        VehCert2.Veh_Fzrq =$('#veh_Fzrq').val();//发证日起
        VehCert2.Veh_Clzzqymc=$('#veh_Clzzqymc').val();//车辆制造企业名称
        VehCert2.Veh_Qyid =$('#veh_Qyid').val();//企业ID
        VehCert2.Veh_Clfl=$('#veh_Clfl').val();     //车辆分类
        VehCert2.Veh_Clmc=$('#veh_Clmc').val();     //车辆名称
        VehCert2.Veh_Clpp=$('#veh_Clpp').val();   //车辆品牌
        VehCert2.Veh_Csys=$('#veh_Csys').val();          //车身颜色
        VehCert2.Veh_Clxh =$('#veh_Clxh').val(); //车辆型号
        VehCert2.Veh_Dpxh=$('#veh_Dpxh').val();//底盘型号
        VehCert2.Veh_Dpid=$('#veh_Dpid').val();//底盘ID
        VehCert2.Veh_Clsbdh=$('#veh_Clsbdh').val();//车辆识别代号
        VehCert2.Veh_Fdjh=$('#veh_Fdjh').val();//发动机号
        VehCert2.Veh_Fdjxh=$('#veh_Fdjxh').val();//发动机型号
        VehCert2.Veh_Rlzl=$('#veh_Rlzl').val();     //燃料种类
        VehCert2.Veh_Pfbz=$('#veh_Pfbz').val();    //排放标准
        VehCert2.Veh_Pl=$('#veh_Pl').val();           //排量
        VehCert2.Veh_Gl=$('#veh_Gl').val();          //功率

        VehCert2.Veh_Zxxs =$('#veh_Zxxs').val();       //转向形式
        VehCert2.Veh_Qlj=$('#veh_Qlj').val();            //前轮距
        VehCert2.Veh_Hlj=$('#veh_Hlj').val();         //后轮距
        VehCert2.Veh_Lts=$('#veh_Lts').val();           //轮胎数
        VehCert2.Veh_Ltgg=$('#veh_Ltgg').val();         //轮胎规格
        VehCert2.Veh_Gbthps=$('#veh_Gbthps').val();      //钢板弹簧片数
        VehCert2.Veh_Zj=$('#veh_Zj').val();      //轴距
        VehCert2.Veh_Zh=$('#veh_Zh').val();      //轴荷
        VehCert2.Veh_Zs=$('#veh_Zs').val();//轴数
        VehCert2.Veh_Wkc=$('#veh_Wkc').val(); //外廓长
        VehCert2.Veh_Wkg=$('#veh_Wkg').val();    //外廓高
        VehCert2.Veh_Wkk=$('#veh_Wkk').val();  //外廓宽
        VehCert2.Veh_Hxnbc=$('#veh_Hxnbc').val();;   //货箱内部长
        VehCert2.Veh_Hxnbg=$('#veh_Hxnbg').val();   //货箱内部高
        VehCert2.Veh_Hxnbk=$('#veh_Hxnbk').val();    //货箱内部宽
        VehCert2.Veh_Zzl=$('#veh_Zzl').val();    //总质量
        VehCert2.Veh_Edzzl=$('#veh_Edzzl').val();   //额定载质量
        VehCert2.Veh_Zbzl=$('#veh_Zbzl').val();;   //整备质量
        VehCert2.Veh_Zzllyxs=$('#veh_Zzllyxs').val();//载质量利用系数
        VehCert2.Veh_Zqyzzl=$('#veh_Zqyzzl').val();//准牵引总质量
        VehCert2.Veh_Edzk=$('#veh_Edzk').val();//额定载客
        VehCert2.Veh_Bgcazzdyxzzl=$('#veh_Bgcazzdyxzzl').val();//半挂车按座
        VehCert2.Veh_Jsszcrs=$('#veh_Jsszcrs').val(); //驾驶室准乘人数
        VehCert2.Veh_Yh=$('#veh_Yh').val();//油耗

        VehCert2.Veh_Ggpc = $('#veh_Ggpc').val();    //公告批次
        VehCert2.Veh_Cpggh = $('#veh_Cpggh').val();       //产品公告号
        VehCert2.Veh_Ggsxrq =$('#veh_Ggsxrq').val();       //公告生效日期
        VehCert2.Veh_Zzbh = $('#veh_Zzbh').val();
        VehCert2.Veh_Zgcs =$('#veh_Zgcs').val();
        VehCert2.Veh_Clzzrq=$('#veh_Clzzrq').val();   //车辆制造日期
        VehCert2.Veh_Bz=$('#veh_Bz').val();     //备注
        VehCert2.Veh_Qybz=$('#veh_Qybz').val();      //企业标准
        VehCert2.Veh_Cpscdz =$('#veh_Cpscdz').val();       //产品生产地址
        VehCert2.Veh_Qyqtxx=$('#veh_Qyqtxx').val();        //企业其它信息
        VehCert2.Veh_Connect="1";       //连接串口
        VehCert2.Veh_Baud="9600" ;        //串口波特率
        VehCert2.Veh_Parity ="N";       //串口奇偶校验
        VehCert2.Veh_Databits ="8";    //串口数据位
        VehCert2.Veh_Stopbits ="1";    //串口停止位
        VehCert2.Veh_Cddbj = "2";         //纯电动标记
        VehCert2.Veh_Zxzs = "1";     //转向轴个数
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
        VehCert2.Veh_Clscdwmc =$('#veh_Clzzqymc').val();          //车辆生产单位名称

        if(VehCert2.Veh_Qlj.indexOf("/")!='-1'){
            VehCert2.Veh_Zxzs='2';
        }else{
            VehCert2.Veh_Zxzs='1';
        }

        iRtn = VehCert2.PrtParaTbl(1,"FDLS-325D-SKDI-E8EK");
        if(iRtn==1){
            //回填页面信息
            var Veh_Dywym= VehCert2.Veh_Dywym
            var Veh_Jyw= VehCert2.Veh_Jyw
            var Veh_wzhgzbh=VehCert2.GetCertificateNO($('#veh_Zchgzbh').val() ,"N3PL-SX32-AEU2-BC6S");
            $('#veh_Dywym').val(Veh_Dywym);
            $('#vercode').val(Veh_Jyw);
            $('#veh_Zchgzbh_0').val(Veh_wzhgzbh);
            $("#web_virtualcode").val(VehCert2.Veh_Zchgzbh);
            return "完整合格证编号:" + VehCert2.Veh_Zchgzbh;
        }else{
            parent.closeDialog(dialogId);
            alert("打印失败，错误原因：" + VehCert2.Veh_ErrorInfo);
            return "failed";
        }
    }

    function clearData(){
        $("input[name='type']").attr("style","display:inline;");
        $("#down_PDF").hide();
        $('#veh_Zchgzbh_Re').val('');//原合格证编号
        $('#veh_Zchgzbh').val('');//整车合格证编号
        $('#veh_Clsbdh').val('');//车辆识别代号
        $('#veh_Fdjh').val('');//发动机号
        $('#veh_Fdjxh').val('');//發動機型號
        $('#veh_zdjgl').val('');//净功率
        $('#veh_Clfl').val('');    //车辆类型
        $('#veh_Clxh').val('');     //车辆型号
        $('#veh_Zj').val('');       //轴距
        $('#veh_Pl').val('');           //排量和功率
        $('#veh_Gl').val('') ;
        $('#veh_Zzl').val('');        //总质量
        $('#veh_Clmc').val('');      //车辆名称
        $('#veh_Edzzl').val('');    //额定载质量
        $('#veh_Pfbz').val('');   //排放标准
        $('#veh_Hxnbc').val('');     //货厢内部尺寸
        $('#veh_Hxnbk').val('') ;    //货厢内部尺寸
        $('#veh_Hxnbg').val('');      //货厢内部尺寸
        $('#veh_Zxxs').val('');     //转向形式
        $('#veh_Zbzl').val('');     //整备质量
        $('#veh_Zh').val('');      //轴荷
        $('#veh_Wkc').val('');     //外廓尺寸
        $('#veh_Wkk').val('');     //外廓尺寸
        $('#veh_Wkg').val('');      //外廓尺寸
        $('#veh_Zqyzzl').val('');      //准牵引总质量
        $('#veh_Edzk').val('');      //额定载客
        $('#veh_Zzllyxs').val('');      //载质量利用系数
        $('#veh_Qyqtxx').val('');      //车辆制造企业其他信息
        $('#veh_Dpid').val('');      //底盘ID
        $('#veh_Dpxh').val('');      //底盘型号
        $('#veh_Jsslx').val('');      //驾驶室类型
        $('#veh_Bgcazzdyxzzl').val('');      //半挂车鞍座最大允许总质量
        $('#veh_clzzqyxx').val('');      //车辆制造企业信息
        $('#veh_Yh').val('');      //油耗
        $('#veh_Zs').val('');      //轴数
        $('#veh_Ggpc').val('');      //公告批次
        $('#veh_Cpggh').val('');      //产品公告号
        $('#veh_Ggsxrq').val('');      //公告生效日期
        $('#veh_Gbthps').val('');     //钢板弹簧片数
        $('#veh_Clpp').val('');     //车辆品牌
        $('#veh_Lts').val('');      //轮胎数
        $('#veh_Clzzqymc').val('');     //车辆企业名称
        $('#veh_Cpscdz').val('');      //生产地址
        $('#veh_Qybz').val('');     //企业标准
        $('#veh_Zgcs').val('');      //最高车速
        $('#veh_Rlzl').val('');     //燃料种类
        $('#veh_Qlj').val('');     //轮距
        $('#veh_Hlj').val('');      //轮距
        $('#veh_Ltgg').val('');     //轮胎规格
        $('#veh_Jsszcrs').val('');     //驾驶室准乘人数
        $('#veh_Bz').val('');      //备注
        $('#veh_pzxlh').val('');      //配置序列号
        $('#veh_Qyid').val('');      //企业ID
        $('#veh_Hzdfs').val('');
        $('#veh_Jss').val('');      //驾驶室
        $('#veh_VinFourBit').val('');      //vin第四位
        $('#veh_Csys').val('');//车身颜色
        var date=new Date();
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        var day=date.getDate();
        $('#veh_Clzzrq').val(year+"年"+month+"月"+day+"日");//车辆制造日期
        $('#veh_Fzrq').val(year+"年"+month+"月"+day+"日");//发证日期
        $('#veh_Dywym').val('');
        $('#vercode').val('');
        $('#veh_Zchgzbh_0').val('');
    }

    //        function create(url,title,width,height){
    //            var tabId=window.name;
    //            var flag=url.indexOf('?');
    //            if(flag>0){
    //                url+="&tabId="+tabId;
    //            }else{
    //                url+="?tabId="+tabId;
    //            }
    //
    //            //打开弹出框
    //            var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
    //                    'margin-right:-500px;text-align:center; width:100%;height:100%" '+
    //                    'scrolling="yes" src="'+url+'"></iframe>';
    //            parent.showDialog(1,content,title,width,height);
    //        }

    %{--function calculateVin(){--}%
    %{--var url = '${createLink(controller:'ZCInfo',action:'calculateVIN')}';--}%
    %{--var kind='0';--}%
    %{--var veh_VinFourBit=$('#veh_VinFourBit').val();--}%
    %{--var veh_Zzl=$('#veh_Zzl').val();--}%
    %{--var veh_Jsslx=$('#veh_Jsslx').val();--}%
    %{--var veh_Rlzl=$('#veh_Rlzl').val();--}%
    %{--var veh_Pl=$('#veh_Pl').val();--}%
    %{--var veh_Zs=$('#veh_Zs').val();--}%
    %{--var veh_Zj=$('#veh_Zj').val();--}%
    %{--var veh_Zchgzbh=$('#veh_Zchgzbh').val();--}%
    %{--var veh_Cpscdz=$('#veh_Cpscdz').val();--}%
    %{--$.post(url,{"kind":kind,'veh_VinFourBit':veh_VinFourBit,'veh_Zzl':veh_Zzl,'veh_Jsslx':veh_Jsslx,'veh_Rlzl':veh_Rlzl,'veh_Pl':veh_Pl,'veh_Zs':veh_Zs,'veh_Zj':veh_Zj,'veh_Zchgzbh':veh_Zchgzbh,'veh_Cpscdz':veh_Cpscdz},function(data){--}%
    %{--if(data.flag=='0'){--}%
    %{--alert(data.msg);--}%
    %{--}else if(data.flag=='1'){--}%
    %{--$('#veh_Clsbdh').val(data.vin);--}%
    %{--}--}%
    %{--},'json');--}%
    %{--}--}%



    function changeData(data){
        $('#veh_Fdjxh').val(data.veh_Fdjxh);//發動機型號
        $('#veh_zdjgl').val(data.veh_zdjgl);//净功率
        $('#veh_Clfl').val(data.veh_Clfl);    //车辆类型
        $('#veh_Clxh').val(data.veh_Clxh);     //车辆型号
        $('#veh_Zj').val(data.veh_Zj);       //轴距
        $('#veh_Pl').val(data.veh_Pl);           //排量和功率
        $('#veh_Gl').val(data.veh_Gl) ;
        $('#veh_Zzl').val(data.veh_Zzl);        //总质量
        $('#veh_Clmc').val(data.veh_Clmc);      //车辆名称
        $('#veh_Edzzl').val(data.veh_Edzzl);    //额定载质量
        $('#veh_Pfbz').val(data.veh_Pfbz);   //排放标准
        $('#veh_Hxnbc').val(data.veh_Hxnbc);     //货厢内部尺寸
        $('#veh_Hxnbk').val(data.veh_Hxnbk) ;    //货厢内部尺寸
        $('#veh_Hxnbg').val(data.veh_Hxnbg);      //货厢内部尺寸
        $('#veh_Zxxs').val(data.veh_Zxxs);     //转向形式
        $('#veh_Zbzl').val(data.veh_Zbzl);     //整备质量
        $('#veh_Zh').val(data.veh_Zh);      //轴荷
        $('#veh_Wkc').val(data.veh_Wkc);     //外廓尺寸
        $('#veh_Wkk').val(data.veh_Wkk);     //外廓尺寸
        $('#veh_Wkg').val(data.veh_Wkg);      //外廓尺寸
        $('#veh_Zqyzzl').val(data.veh_Zqyzzl);      //准牵引总质量
        $('#veh_Edzk').val(data.veh_Edzk);      //额定载客
        $('#veh_Zzllyxs').val(data.veh_Zzllyxs);      //载质量利用系数
        $('#veh_Qyqtxx').val(data.veh_Qyqtxx);      //车辆制造企业其他信息
        $('#veh_Dpid').val(data.veh_Dpid);      //底盘ID
        $('#veh_Dpxh').val(data.veh_Dpxh);      //底盘型号
        $('#veh_Jsslx').val(data.veh_Jsslx);      //驾驶室类型
        $('#veh_Bgcazzdyxzzl').val(data.veh_Bgcazzdyxzzl);      //半挂车鞍座最大允许总质量
        $('#veh_clzzqyxx').val(data.veh_clzzqyxx);      //车辆制造企业信息
        $('#veh_Yh').val(data.veh_Yh);      //油耗
        $('#veh_Zs').val(data.veh_Zs);      //轴数
        $('#veh_Ggpc').val(data.veh_Ggpc);      //公告批次
        $('#veh_Cpggh').val(data.veh_Cpggh);      //产品公告号
        $('#veh_Ggsxrq').val(data.veh_Ggsxrq);      //公告生效日期
        $('#veh_Gbthps').val(data.veh_Gbthps);     //钢板弹簧片数
        $('#veh_Clpp').val(data.veh_Clpp);     //车辆品牌
        $('#veh_Lts').val(data.veh_Lts);      //轮胎数
        if(data.veh_Clzzqymc==null||data.veh_Clzzqymc==''){
            $('#veh_Clzzqymc').val('浙江飞碟汽车制造有限公司');     //车辆企业名称
        } else{
            $('#veh_Clzzqymc').val(data.veh_Clzzqymc);     //车辆企业名称
        }
        if(data.veh_Cpscdz==null||data.veh_Cpscdz==''){
            $('#veh_Cpscdz').val('山东省五莲县潮河镇驻地');      //生产地址
        } else{
            $('#veh_Cpscdz').val(data.veh_Cpscdz);      //生产地址
        }
        if(data.veh_Qybz==null||data.veh_Qybz==''){
            $('#veh_Qybz').val('Q/ZFK106-2009《飞碟牌系列载货汽 车及底盘通用技术条件》');     //企业标准
        } else{
            $('#veh_Qybz').val(data.veh_Qybz);     //企业标准
        }
        $('#veh_Zgcs').val(data.veh_Zgcs);      //最高车速
        $('#veh_Rlzl').val(data.veh_Rlzl);     //燃料种类
        $('#veh_Qlj').val(data.veh_Qlj);     //轮距
        $('#veh_Hlj').val(data.veh_Hlj);      //轮距
        $('#veh_Ltgg').val(data.veh_Ltgg);     //轮胎规格
        $('#veh_Jsszcrs').val(data.veh_Jsszcrs);     //驾驶室准乘人数
        $('#veh_Bz').val(data.veh_Bz);      //备注
        $('#veh_pzxlh').val(data.veh_pzxlh);      //配置序列号
        $('#veh_Qyid').val(data.veh_Qyid);      //企业ID
        $('#veh_Hzdfs').val(data.veh_Hzdfs);
        $('#veh_Jss').val(data.veh_Jss);      //驾驶室
        $('#veh_VinFourBit').val(data.veh_VinFourBit);      //vin第四位
    }
</script>
</body>
</html>
