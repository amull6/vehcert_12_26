<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>公告信息生成</title>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'insideNote.label', default: 'InsideNote')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<form style="margin-left:10px;" id="form" action="${createLink(controller:'preCarStorage',action:'generateCarStorage')}"  method="post">
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${cartStorage}">
        <ul class="errors" role="alert">
            <g:eachError bean="${cartStorage}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <div style="" class="om-grid om-widget om-widget-content">
        <div class="bDiv" style="width: auto;">
            <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr class="om-grid-row evenRow">
                <td align="center" class="indexCol" colspan="4"><div align="center" class="innerCol ">公告信息</div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; width: 95px;" class="om-resizable">车辆制造企业名称</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clzzqymc" type="text" value="${cartStorage?.veh_Clzzqymc}" style="width:510px;" />
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品公告号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Cpggh" type="text" value="${cartStorage?.veh_Cpggh}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">公告生效日期</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ggsxrq" type="text" value="${cartStorage?.veh_Ggsxrq}" style="width:200px;"/>
                </div></td>

            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">批次</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ggpc" type="text" value="${cartStorage?.veh_Ggpc}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_cpid" type="text" value="${cartStorage?.veh_cpid}" style="width:200px;"/>
                </div></td>

            </tr>

            <tr>
                <td align="left"><div style="text-align: left;" class="om-resizable">企业ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
                    <input name="veh_Qyid" type="text" value="${cartStorage?.veh_Qyid}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; width: 80px;" class="om-resizable">车辆分类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol " style="width:200px">
                    <input  name="veh_Clfl" type="text" value="${cartStorage?.veh_Clfl}" style="width:200px;"/>
                </div></td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆名称</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clmc" type="text" value="${cartStorage?.veh_Clmc}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">车辆品牌</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clpp" type="text" value="${cartStorage?.veh_Clpp}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input id="veh_Clxh" name="veh_Clxh" value="${cartStorage?.veh_Clxh}" type="text" size="20"/>
                    <a id="btn_searchByClxh" class="btn_basic blue_black" href="javascript:void(0)">查询</a>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">车身颜色</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Csys" type="text" value="${cartStorage?.veh_Csys}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">底盘型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input id="veh_Dpxh" name="veh_Dpxh" type="text" value="${cartStorage?.veh_Dpxh}" size="20"/>
                    <a id="btn_searchByDpxh" class="btn_basic blue_black" href="javascript:void(0)">查询</a>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">底盘ID</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Dpid" type="text" value="${cartStorage?.veh_Dpid}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">发动机型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Fdjxh" type="text"  value="${cartStorage?.veh_Fdjxh}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">燃料种类</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Rlzl" type="text" value="${cartStorage?.veh_Rlzl}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">排量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pl" type="text" value="${cartStorage?.veh_Pl}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">功率</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gl" type="text" value="${cartStorage?.veh_Gl}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">转向形式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zxxs" type="text" value="${cartStorage?.veh_Zxxs}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">前轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qlj" type="text" value="${cartStorage?.veh_Qlj}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">后轮距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hlj" type="text" value="${cartStorage?.veh_Hlj}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">轮胎数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Lts" type="text" value="${cartStorage?.veh_Lts}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轮胎规格</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Ltgg" type="text" value="${cartStorage?.veh_Ltgg}" style="width:200px;"/>
                </div></td>

                <td align="left"><div style="text-align: left; " class="om-resizable">钢板弹簧片数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Gbthps" type="text" value="${cartStorage?.veh_Gbthps}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">轴距</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zj" type="text" value="${cartStorage?.veh_Zj}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">轴荷</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zh" type="text" value="${cartStorage?.veh_Zh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left;" class="om-resizable">轴数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zs" type="text" value="${cartStorage?.veh_Zs}" style="width:200px;"/></div>
                </td>

                <td align="left"><div style="text-align: left; " class="om-resizable">外廓长</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkc" type="text"  value="${cartStorage?.veh_Wkc}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓宽</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkk" type="text" value="${cartStorage?.veh_Wkk}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">外廓高</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Wkg" type="text"  value="${cartStorage?.veh_Wkg}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部长</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbc" type="text" value="${cartStorage?.veh_Hxnbc}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部宽</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbk" type="text" value="${cartStorage?.veh_Hxnbk}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">货箱内部高</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hxnbg" type="text"  value="${cartStorage?.veh_Hxnbg}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">最高车速</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zgcs" type="text"  value="${cartStorage?.veh_Zgcs}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">总质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zzl" type="text"  value="${cartStorage?.veh_Zzl}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">额定载质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Edzzl" type="text"  value="${cartStorage?.veh_Edzzl}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">整备质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zbzl" type="text" value="${cartStorage?.veh_Zbzl}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">载质量利用系数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zzllyxs" type="text"  value="${cartStorage?.veh_Zzllyxs}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">准牵引总质量</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Zqyzzl" type="text" value="${cartStorage?.veh_Zqyzzl}" style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">额定载客</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Edzk" type="text" value="${cartStorage?.veh_Edzk}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">半挂车按座</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bgcazzdyxzzl" value="${cartStorage?.veh_Bgcazzdyxzzl}" type="text" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室准乘人数</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jsszcrs" type="text"  value="${cartStorage?.veh_Jsszcrs}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">最大净功率</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_zdjgl" type="text" value="${cartStorage?.veh_zdjgl}"  style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">后制动方式</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Hzdfs" type="text" value="${cartStorage?.veh_Hzdfs}"  style="width:200px;"/></div></td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">接近离去角</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_jjlqj" type="text" value="${cartStorage?.veh_jjlqj}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">前悬后悬</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_qxhx" style="width:200px;"  type="text"  value="${cartStorage?.veh_qxhx}"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left;" class="om-resizable">底盘企业</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_dpqy" type="text" value="${cartStorage?.veh_dpqy}"  style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">企业标准</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qybz" type="text" value="${cartStorage?.veh_Qybz}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品生产地址</div></td>
                <td align="left" abbr="city" colspan="3" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Cpscdz" type="text" value="${cartStorage?.veh_Cpscdz}" style="width:510px;"/></div>
                </td></tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">企业其它信息</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Qyqtxx"  type="text" value="${cartStorage?.veh_Qyqtxx}"  style="width:510px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">排放标准</div></td>
                <td align="left" colspan="3" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Pfbz" type="text" value="${cartStorage?.veh_Pfbz}"  style="width:510px;"/></div>
                </td>
            </tr>

            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jss" type="text"  value="${cartStorage?.veh_Jss}"  style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">驾驶室类型</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Jsslx" type="text" value="${cartStorage?.veh_Jsslx}" style="width:200px;"/></div>
                </td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">油耗</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Yh" type="text"   value="${cartStorage?.veh_Yh}" style="width:200px;"/>
                </div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示企业</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbsqy" type="text" value="${cartStorage?.veh_fgbsqy}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示商标</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbssb" type="text" value="${cartStorage?.veh_fgbssb}" style="width:200px;"/></div>
                </td>
                <td align="left"><div style="text-align: left; " class="om-resizable">反光标示型号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fgbsxh" type="text"  value="${cartStorage?.veh_fgbsxh}" style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">右45度照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_y45pic" type="text" value="${cartStorage?.veh_y45pic}"  style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">正后照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_zhpic" type="text" value="${cartStorage?.veh_zhpic}" style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">防护照片</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_fhpic" type="text" value="${cartStorage?.veh_fhpic}" style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">选择照片1</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_pic1" type="text" value="${cartStorage?.veh_pic1}"  style="width:200px;"/></div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">选择照片2</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_pic2" type="text" value="${cartStorage?.veh_pic2}"    style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆状态信息</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Clztxx" type="text" value="${cartStorage?.veh_Clztxx}"  style="width:200px;"/>
                </div></td>
            </tr>
            <tr>
                <td align="left"><div style="text-align: left; " class="om-resizable">车辆识别代号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_clsbdh" type="text" value="${cartStorage?.veh_clsbdh}"    style="width:200px;"/></div></td>
                <td align="left"><div style="text-align: left; " class="om-resizable">产品号</div></td>
                <td align="left" abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_cph" type="text" value="${cartStorage?.veh_cph}"  style="width:200px;"/>
                </div></td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">其它</div></td>
                <td align="left" abbr="city" class="col1" colspan="3"><div align="left" class="innerCol ">
                    <input name="veh_other"   type="text" value="${cartStorage?.veh_other}" style="width:510px;"/></div>
                </td>
            </tr>
            <tr>

                <td align="left"><div style="text-align: left; " class="om-resizable">备注</div></td>
                <td align="left" colspan='3' abbr="city" class="col1"><div align="left" class="innerCol ">
                    <input name="veh_Bz" type="text" value="${cartStorage?.veh_Bz}" style="width:510px;"/>
                </div></td>
            </tr>

            <tr >
                <td colspan="4" align="center"><div style="text-align: center; " class="om-resizable">
                   <a id="btn_generate" class="btn_basic blue_black" href="javascript:void(0)">生成公告信息</a>
                <c:resourceAuth resourceCode="dv_save_preCarStorage">
                    <a id="btn_save" class="btn_basic blue_black" href="javascript:void(0)">保存公告信息</a>
                </c:resourceAuth>
                <c:resourceAuth resourceCode="dv_imp_preCarStorage">
                   <a id="btn_imp" class="btn_basic blue_black" href="javascript:void(0)" >导入公告信息</a>
                </c:resourceAuth>
                   <a id="btn_impFile" class="btn_basic blue_black" href="${createLink(controller:'downLoad',action:'downFile')}?fileName=分解前公告信息导入模板.xls&filePath=/upload/template/preCarStorageTemplate.xls" >下载导入模板</a>
                </div></td>
            </tr>

            </tbody>
            </table>
        <div style="margin:10px 5px;">
            说明：
            <div style="margin-left:30px">
                1、车辆制造企业名称,产品公告号,公告生效日期,公告批次,产品ID,企业ID, 车辆分类,车辆名称,
                车辆型号,底盘型号,底盘ID,备注,企业其它信息,排放标准,右45度照片,正后图片,防护图片,图片1，图片2,其它,车辆识别代号  不参与排列组合。
            </div>
            <div style="margin-left:30px">
                2、除【1】中列出的属性，其它属性参与排列组合，排列组合属性中的不同参数使用英文状态下的逗号分隔。
            </div>
            <div style="margin-left:30px">
                3、排列组合中使用的公式有 【外廓长=前悬+后悬+轴距】  【总质量=额定质量+整备质量+准乘人数*65】  发动机、排量、功率、最大净功率一一对应（如果四项的个数不相同，生成记录中这四项将没有数据）
            </div>
            <div style="margin-left:30px">
                4、输入完所有参数点击生成按扭后，系统自动将逗号之间的数据进行排列组合。对生成后的数据根据实际情况将不能匹配的参数进行删除，导出后导入公告信息维护页面。
            </div>
        </div>
    </div>
    </div>
    <input type="hidden" name="dialogId" id="dialogId" value="${dialogId}">
</form>
<script>
    var dialogId='';
    var initId='';
    $(function(){
        var message="${returnMessage}";
        if(message!=''){
            alert(message);
            dialogId="${dialogId}";
            parent.closeDialog(dialogId);
        }

        $("#btn_generate").click(function(){
            var url="${createLink(controller:'preCarStorage',action:'generateCarStorage')}?format=excel&extension=xlsx";
            $("#form").attr("action",url);
            dialogId=showTipDialog();
            $("#dialogId").val(dialogId);
            var dd= $("#form").submit();

            initId=setInterval("getResult();",'3000');
        });
        $("#btn_save").click(function(){
            var url="${createLink(controller:'preCarStorage',action:'save')}";
            $("#form").attr("action",url);
            $("#form").submit();
        });
        $("#btn_imp").click(function(){
            var url='${createLink(controller:'preCarStorage',action:'importPage')}';
            showModelDialog(url);
        });
        $("#btn_searchByClxh").click(function(){
            var url="${createLink(controller:'preCarStorage',action:'search')}?searchFlag=searchByClxh";
            $("#form").attr("action",url);
            $("#form").submit();
        });
        $("#btn_searchByDpxh").click(function(){
            var url="${createLink(controller:'preCarStorage',action:'search')}?searchFlag=searchByDpxh";
            $("#form").attr("action",url);
            $("#form").submit();
        });
    });

    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'preCarStorage',action:'getCompResult')}';
        $.post(url,{},function(data,textStatus){
            //判断是否已处理完成
            if(data!=null&&data=="success"){
                parent.closeDialog(dialogId);
                clearInterval(initId);
            }else if(data=='error'){
                alert("任务处理过程中出错！");
                parent.closeDialog(dialogId);
                clearInterval(initId);
            }
        });
    }
    function showModelDialog(url){
        var tabId=window.name;
        url+="?tabId="+tabId;
        var content='<iframe id="imp_dialog"  style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="auto" src="'+url+'"></iframe>';
        var title='文件导入';
        //打开弹出框
        parent.showDialog(1,content,title,550,250);
    }

    function backFunc(result){
        var  url="${createLink(controller:'preCarStorage',action:'search')}";
        if(result!=""){
            var dd=result.split("_");
            if(dd[0]!='noClxh'){
                $("#veh_Clxh").val(dd[0]);
            }
            if(dd[1]!='noDpxh'){
                $("#veh_Dpxh").val(dd[1]);
            }

            if(dd[0]!="noClxh"){
                url+="?searchFlag=searchByClxh";
            }else if(dd[1]!="noDpxh"){
                url+="?searchFlag=searchByDpxh";
            }
        }
        $("#form").attr("action",url);
        $("#form").submit();
    }
</script>
</body>


</html>
