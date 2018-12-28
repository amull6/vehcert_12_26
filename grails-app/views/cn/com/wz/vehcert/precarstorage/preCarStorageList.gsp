<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'carstorage.label', default: 'carstorage')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<form id="form" method="post">
    <input type="hidden" id="" name="resultList" value='${list}'/>
</form>
<div id="page"  style="background-color:white;margin-top:8px;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <fieldset class="buttons" style="margin:0px 8px 0px 4px;line-height:10px;">
            <input id="btn_return" type="button"  class="cancel" value="${message(code: 'default.button.return.label', default: 'return')}" style=''/>
            <export:formats formats="['excel','pdf']"    style="border:0px;margin-left:60px;margin-top:-23px;"/>
        </fieldset>

        <div style="margin-right:8px;margin-top:8px;">
            <div class="om-grid om-widget om-widget-content" style="width: auto; height: 500px;overflow-x: auto;">
                <div class="titleDiv" style=""><div class="titleContent">合格证信息列表</div></div>

                <div class="bDiv" style="width: auto; height: 440px;overflow-y: auto;">
                    %{--<div class="hDiv om-state-default" style="width: auto;"><div class="hDivBox" style="width: auto;display: inline;">--}%
                        %{--<table cellpadding="0" cellspacing="0">--}%
                            %{--<thead><tr>--}%
                                %{--<th axis="indexCol" align="center" class="indexCol"><div class="indexheader" style="text-align:center;width:25px;"></div></th>--}%
                                %{--<th axis="col0" class="col0" abbr="veh_Clzzqymc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆制造企业名称<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col1" class="col1" abbr="veh_Qyid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业ID<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col2" class="col2" abbr="veh_Clfl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆分类<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col3" class="col3" abbr="veh_Clmc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆名称<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col4" class="col4" abbr="veh_Clpp" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆品牌<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col5" class="col5" abbr="veh_Clxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col6" class="col6" abbr="veh_Csys" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车身颜色<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col7" class="col7" abbr="veh_Dpxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col8" class="col8" abbr="veh_Dpid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘ID<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col9" class="col9" abbr="veh_cpid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品id<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                                %{--<th axis="col10" class="col10" abbr="veh_Ggpc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">公告批次<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col11" class="col11" abbr="veh_qxhx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">前悬后悬<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col12" class="col12" abbr="veh_Fdjxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">发动机型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col13" class="col13" abbr="veh_Rlzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">燃料种类<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col14" class="col14" abbr="veh_Pl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">排量<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col15" class="col15" abbr="veh_Gl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">功率<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col16" class="col16" abbr="veh_zdjgl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最大净功率<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col17" class="col17" abbr="veh_Zxxs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">转向形式<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col18" class="col18" abbr="veh_Qlj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">前轮距<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col19" class="col19" abbr="veh_Hlj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">后轮距<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col20" class="col20" abbr="veh_Lts" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轮胎数<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col21" class="col21" abbr="veh_Ltgg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轮胎规格<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col22" class="col22" abbr="veh_Gbthps" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">钢板弹簧片数<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col23" class="col23" abbr="veh_Zj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴距<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col24" class="col24" abbr="veh_Zh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴荷<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col25" class="col25" abbr="veh_Zs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴数<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col26" class="col26" abbr="veh_Wkc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓长<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col27" class="col27" abbr="veh_Wkk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓宽<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col28" class="col28" abbr="veh_Wkg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓高<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col29" class="col29" abbr="veh_Hxnbc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部长<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col30" class="col30" abbr="veh_Hxnbk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部宽<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col31" class="col31" abbr="veh_Hxnbg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部高<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col32" class="col32" abbr="veh_Zzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">总质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col33" class="col33" abbr="veh_Edzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">额定载质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col34" class="col34" abbr="veh_Zbzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">整备质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col35" class="col35" abbr="veh_Zzllyxs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">载质量利用系数<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col36" class="col36" abbr="veh_Zqyzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">准牵引总质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col37" class="col37" abbr="veh_Edzk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">额定载客<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col38" class="col38" abbr="veh_Bgcazzdyxzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">半挂车按座<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col39" class="col39" abbr="veh_Jsszcrs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室准乘人数<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col40" class="col40" abbr="veh_Hzdfs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">后制动方式<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col41" class="col41" abbr="veh_Cpggh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品公告号<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col42" class="col42" abbr="veh_Ggsxrq" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">公告生效日期<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col43" class="col43" abbr="veh_jjlqj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">接近离去角<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col44" class="col44" abbr="veh_dpqy" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘企业<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col45" class="col45" abbr="veh_Zgcs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最高车速<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col46" class="col46" abbr="veh_Yh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">油耗<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col47" class="col47" abbr="veh_Bz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">备注<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col48" class="col48" abbr="veh_Qybz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业标准<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col49" class="col49" abbr="veh_Cpscdz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品生产地址<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col50" class="col50" abbr="veh_Qyqtxx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业其它信息<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col51" class="col51" abbr="veh_Pfbz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">排放标准<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col52" class="col52" abbr="veh_Clztxx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆状态信息<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col53" class="col53" abbr="veh_Jss" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col54" class="col54" abbr="veh_Jsslx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室类型<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col55" class="col55" abbr="veh_fgbsqy" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示企业<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col56" class="col56" abbr="veh_fgbsxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col57" class="col57" abbr="veh_fgbssb" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示商标<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col58" class="col58" abbr="veh_y45pic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">右45度照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col59" class="col59" abbr="veh_zhpic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">正后照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col60" class="col60" abbr="veh_fhpic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">防护照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col61" class="col61" abbr="veh_pic1" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">照片1<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col62" class="col62" abbr="veh_pic2" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">照片2<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col63" class="col63" abbr="veh_other" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">其它<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col64" class="col64" abbr="createTime" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">创建时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col65" class="col65" abbr="createrId" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">创建人id<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col66" class="col66" abbr="updateTime" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">更新时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></th><th axis="col67" class="col67" abbr="updaterId" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最后更新时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></th>--}%
                            %{--</tr></thead>--}%
                        %{--</table>--}%
                    %{--</div>--}%
                    %{--</div>--}%
                    <table id="carStorage_grid" style="border: 0px;" cellpadding="0" cellspacing="0" border="0">
                        <tbody>
                        <tr style="background-color: #C7D2E4;border: 1px solid #99A8BC;">
                            <td axis="indexCol" align="center" class="indexCol"><div class="indexheader" style="text-align:center;width:25px;"></div></td>
                            <td axis="col0" class="col0" abbr="veh_Clzzqymc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆制造企业名称<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col1" class="col1" abbr="veh_Qyid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业ID<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col2" class="col2" abbr="veh_Clfl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆分类<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col3" class="col3" abbr="veh_Clmc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆名称<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col4" class="col4" abbr="veh_Clpp" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆品牌<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col5" class="col5" abbr="veh_Clxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col6" class="col6" abbr="veh_Csys" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车身颜色<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col7" class="col7" abbr="veh_Dpxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col8" class="col8" abbr="veh_Dpid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘ID<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col9" class="col9" abbr="veh_cpid" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品id<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                            <td axis="col10" class="col10" abbr="veh_Ggpc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">公告批次<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col11" class="col11" abbr="veh_qxhx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">前悬后悬<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col12" class="col12" abbr="veh_Fdjxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">发动机型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col13" class="col13" abbr="veh_Rlzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">燃料种类<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col14" class="col14" abbr="veh_Pl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">排量<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><th axis="col15" class="col15" abbr="veh_Gl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">功率<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col16" class="col16" abbr="veh_zdjgl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最大净功率<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col17" class="col17" abbr="veh_Zxxs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">转向形式<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col18" class="col18" abbr="veh_Qlj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">前轮距<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col19" class="col19" abbr="veh_Hlj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">后轮距<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col20" class="col20" abbr="veh_Lts" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轮胎数<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col21" class="col21" abbr="veh_Ltgg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轮胎规格<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col22" class="col22" abbr="veh_Gbthps" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">钢板弹簧片数<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col23" class="col23" abbr="veh_Zj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴距<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col24" class="col24" abbr="veh_Zh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴荷<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col25" class="col25" abbr="veh_Zs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">轴数<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col26" class="col26" abbr="veh_Wkc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓长<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col27" class="col27" abbr="veh_Wkk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓宽<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col28" class="col28" abbr="veh_Wkg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">外廓高<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col29" class="col29" abbr="veh_Hxnbc" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部长<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col30" class="col30" abbr="veh_Hxnbk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部宽<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col31" class="col31" abbr="veh_Hxnbg" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">货箱内部高<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col32" class="col32" abbr="veh_Zzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">总质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col33" class="col33" abbr="veh_Edzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">额定载质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col34" class="col34" abbr="veh_Zbzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">整备质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col35" class="col35" abbr="veh_Zzllyxs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">载质量利用系数<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col36" class="col36" abbr="veh_Zqyzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">准牵引总质量<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col37" class="col37" abbr="veh_Edzk" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">额定载客<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col38" class="col38" abbr="veh_Bgcazzdyxzzl" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">半挂车按座<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col39" class="col39" abbr="veh_Jsszcrs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室准乘人数<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col40" class="col40" abbr="veh_Hzdfs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">后制动方式<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col41" class="col41" abbr="veh_Cpggh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品公告号<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col42" class="col42" abbr="veh_Ggsxrq" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">公告生效日期<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col43" class="col43" abbr="veh_jjlqj" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">接近离去角<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col44" class="col44" abbr="veh_dpqy" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">底盘企业<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col45" class="col45" abbr="veh_Zgcs" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最高车速<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col46" class="col46" abbr="veh_Yh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">油耗<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col47" class="col47" abbr="veh_Bz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">备注<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col48" class="col48" abbr="veh_Qybz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业标准<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col49" class="col49" abbr="veh_Cpscdz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">产品生产地址<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col50" class="col50" abbr="veh_Qyqtxx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">企业其它信息<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col51" class="col51" abbr="veh_Pfbz" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">排放标准<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col52" class="col52" abbr="veh_Clztxx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">车辆状态信息<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col53" class="col53" abbr="veh_Jss" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col54" class="col54" abbr="veh_Jsslx" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">驾驶室类型<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col55" class="col55" abbr="veh_fgbsqy" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示企业<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col56" class="col56" abbr="veh_fgbsxh" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示型号<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col57" class="col57" abbr="veh_fgbssb" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">反光标示商标<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col58" class="col58" abbr="veh_y45pic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">右45度照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col59" class="col59" abbr="veh_zhpic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">正后照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col60" class="col60" abbr="veh_fhpic" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">防护照片<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col61" class="col61" abbr="veh_pic1" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">照片1<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col62" class="col62" abbr="veh_pic2" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">照片2<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col63" class="col63" abbr="veh_other" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">其它<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col64" class="col64" abbr="createTime" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">创建时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col65" class="col65" abbr="createrId" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">创建人id<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col66" class="col66" abbr="updateTime" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">更新时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></td><td axis="col67" class="col67" abbr="updaterId" align="center"><div style="text-align: center; width: 80px;" class="om-resizable">最后更新时间<div class="om-resizable-handle om-resizable-e" style=""></div></div></td>
                        </tr>
                            <g:each in="${result}" var="pcs" status="i">
                                <tr  class="om-grid-row ${ (i % 2) == 0 ? 'oddRow' : 'evenRow'} ">
                                    <td align="center" abbr="" class="indexCol"><div align="center" class="innerCol " style="width:25px">${i+1}</div></td>
                                    <td align="center" abbr="veh_Clzzqymc" class="col0"><div align="center" class="innerCol "  style="width:80px"> ${pcs?.veh_Clzzqymc}</div>
                                    </td> <td align="center" abbr="veh_Qyid" class="col1"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Qyid}</div>
                                    </td> <td align="center" abbr="veh_Clfl" class="col2"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Clfl}</div>
                                    </td> <td align="center" abbr="veh_Clmc" class="col3"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Clmc}</div>
                                    </td> <td align="center" abbr="veh_Clpp" class="col4"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Clpp}</div>
                                    </td> <td align="center" abbr="veh_Clxh" class="col5"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Clxh}</div>
                                    </td> <td align="center" abbr="veh_Csys" class="col6"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Csys}</div>
                                    </td> <td align="center" abbr="veh_Dpxh" class="col7"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Dpxh}</div>
                                    </td> <td align="center" abbr="veh_Dpid" class="col8"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Dpid}</div>
                                    </td> <td align="center" abbr="veh_cpid" class="col9"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_cpid}</div>
                                    </td> <td align="center" abbr="veh_Ggpc" class="col10"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Ggpc}</div>
                                    </td> <td align="center" abbr="veh_qxhx" class="col11"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_qxhx}</div>
                                    </td> <td align="center" abbr="veh_Fdjxh" class="col12"><div align="center"  class="innerCol " style="width:80px">${pcs?.veh_Fdjxh}</div>
                                    </td> <td align="center" abbr="veh_Rlzl" class="col13"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Rlzl}</div>
                                    </td> <td align="center" abbr="veh_Pl" class="col14"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Pl}</div>
                                    </td> <td align="center" abbr="veh_Gl" class="col15"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Gl}</div>
                                    </td> <td align="center" abbr="veh_zdjgl" class="col16"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_zdjgl}</div>
                                    </td> <td align="center" abbr="veh_Zxxs" class="col17"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Zxxs}</div>
                                    </td> <td align="center" abbr="veh_Qlj" class="col18"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Qlj}</div>
                                    </td> <td align="center" abbr="veh_Hlj" class="col19"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Hlj}</div>
                                    </td> <td align="center" abbr="veh_Lts" class="col20"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Lts}</div>
                                    </td> <td align="center" abbr="veh_Ltgg" class="col21"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Ltgg}</div>
                                    </td> <td align="center" abbr="veh_Gbthps" class="col22"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Gbthps}</div>
                                    </td> <td align="center" abbr="veh_Zj" class="col23"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Zj}</div>
                                    </td> <td align="center" abbr="veh_Zh" class="col24"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Zh}</div>
                                    </td> <td align="center" abbr="veh_Zs" class="col25"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Zs}</div>
                                    </td> <td align="center" abbr="veh_Wkc" class="col26"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Wkc}</div>
                                    </td> <td align="center" abbr="veh_Wkk" class="col27"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Wkk}</div>
                                    </td> <td align="center" abbr="veh_Wkg" class="col28"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Wkg}</div>
                                    </td> <td align="center" abbr="veh_Hxnbc" class="col29"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Hxnbc}</div>
                                    </td> <td align="center" abbr="veh_Hxnbk" class="col30"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Hxnbk}</div>
                                    </td> <td align="center" abbr="veh_Hxnbg" class="col31"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Hxnbg}</div>
                                    </td> <td align="center" abbr="veh_Zzl" class="col32"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Zzl}</div>
                                    </td> <td align="center" abbr="veh_Edzzl" class="col33"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Edzzl}</div>
                                    </td> <td align="center" abbr="veh_Zbzl" class="col34"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Zbzl}</div>
                                    </td> <td align="center" abbr="veh_Zzllyxs" class="col35"><div align="center"   class="innerCol "  style="width:80px">${pcs?.veh_Zzllyxs}</div>
                                    </td> <td align="center" abbr="veh_Zqyzzl" class="col36"><div align="center"  class="innerCol "  style="width:80px">${pcs?.veh_Zqyzzl}</div>
                                    </td> <td align="center" abbr="veh_Edzk" class="col37"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Edzk}</div>
                                    </td> <td align="center" abbr="veh_Bgcazzdyxzzl" class="col38"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Bgcazzdyxzzl}</div>
                                    </td> <td align="center" abbr="veh_Jsszcrs" class="col39"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Jsszcrs}</div>
                                    </td> <td align="center" abbr="veh_Hzdfs" class="col40"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Hzdfs}</div>
                                    </td> <td align="center" abbr="veh_Cpggh" class="col41"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Cpggh}</div>
                                    </td> <td align="center" abbr="veh_Ggsxrq" class="col42"><div align="center"  class="innerCol " style="width:80px">${pcs?.veh_Ggsxrq}</div>
                                    </td> <td align="center" abbr="veh_jjlqj" class="col43"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_jjlqj}</div>
                                    </td> <td align="center" abbr="veh_dpqy" class="col44"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_dpqy}</div>
                                    </td> <td align="center" abbr="veh_Zgcs" class="col45"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Zgcs}</div>
                                    </td> <td align="center" abbr="veh_Yh" class="col46"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Yh}</div>
                                    </td> <td align="center" abbr="veh_Bz" class="col47"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Bz}</div>
                                    </td> <td align="center" abbr="veh_Qybz" class="col48"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Qybz}</div>
                                    </td> <td align="center" abbr="veh_Cpscdz" class="col49"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Cpscdz}</div>
                                    </td> <td align="center" abbr="veh_Qyqtxx" class="col50"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Qyqtxx}</div>
                                    </td> <td align="center" abbr="veh_Pfbz" class="col51"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Pfbz}</div>
                                    </td> <td align="center" abbr="veh_Clztxx" class="col52"><div align="center"  class="innerCol " style="width:80px">${pcs?.veh_Clztxx}</div>
                                    </td> <td align="center" abbr="veh_Jss" class="col53"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_Jss}</div>
                                    </td> <td align="center" abbr="veh_Jsslx" class="col54"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_Jsslx}</div>
                                    </td> <td align="center" abbr="veh_fgbsqy" class="col55"><div align="center"  class="innerCol "  style="width:80px">${pcs?.veh_fgbsqy}</div>
                                    </td> <td align="center" abbr="veh_fgbsxh" class="col56"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_fgbsxh}</div>
                                    </td> <td align="center" abbr="veh_fgbssb" class="col57"><div align="center"  class="innerCol " style="width:80px">${pcs?.veh_fgbssb}</div>
                                    </td> <td align="center" abbr="veh_y45pic" class="col58"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_y45pic}</div>
                                    </td> <td align="center" abbr="veh_zhpic" class="col59"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_zhpic}</div>
                                    </td> <td align="center" abbr="veh_fhpic" class="col60"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_fhpic}</div>
                                    </td> <td align="center" abbr="veh_pic1" class="col61"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_pic1}</div>
                                    </td> <td align="center" abbr="veh_pic2" class="col62"><div align="center" class="innerCol " style="width:80px">${pcs?.veh_pic2}</div>
                                    </td> <td align="center" abbr="veh_other" class="col63"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_other}</div>
                                    </td> <td align="center" abbr="veh_clsbdh" class="col63"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_clsbdh}</div>
                                    </td> <td align="center" abbr="veh_cph" class="col63"><div align="center" class="innerCol "  style="width:80px">${pcs?.veh_cph}</div>
                                    </td> <td align="center" abbr="createTime" class="col64"><div align="center"  class="innerCol " style="width:80px">${pcs?.createTime}</div>
                                    </td> <td align="center" abbr="updaterId" class="col65"><div align="center" class="innerCol "  style="width:80px">${pcs?.updaterId}</div>
                                    </td> <td align="center" abbr="updaterId" class="col66"><div align="center"  class="innerCol " style="width:80px">${pcs?.updaterId}</div>
                                    </td> <td align="center" abbr="updaterId" class="col67"><div align="center"  class="innerCol " style="width:80px">${pcs?.updaterId}</div>
                                    </td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="pDiv om-state-default" style="border-width: 0px; display: none;"></div>
                <div class="gBlock" style="width: 100%; height: 500px; display: none;">
                    <div align="center" class="gBlock-valignMiddle"><div class="loadingImg" style="display:block"></div></div>
                </div>
            </div>

        </div>
</div>
</div>
<script>
    $(function() {
        //自动关闭进度提示框
        parent.closeDialog("${dialogId}");
        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $(".pdf").click(function(){
            $('.pdf').attr('href','javascript:void(0);');
            var url="${createLink(controller:'PreCarStorage',action:'export')}?format=pdf&extension=pdf";
            $("#form").attr("action",url);
            $("#form").submit();
        });
        $(".excel").click(function(){
            $('.excel').attr('href','javascript:void(0);');
            var url="${createLink(controller:'PreCarStorage',action:'export')}?format=excel&extension=xls";
            $("#form").attr("action",url);
            $("#form").submit();
        });

        $('#btn_return').click(function(){
             $("#form").attr("action","${createLink(controller:'preCarStorage',action:'search')}");
            $("#form").submit();
        });
    })
    // 加载时执行的语句
    $(function() {
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            },{
                id:"west-panel",
                title:"<g:message code="default.list.label" args="[entityName]" />",
                region:"west",
                width:200
            }]
        });
        //构建左侧树
        //buildLeftTree();
//        buildRightGrid();
    });
    /*function buildRightGrid(value,event){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'preCarStorage',action:'jsonList')}",
            method:'POST',
            extraData:{list:''} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 0, // 分页显示，每页显示30条
            height : 500,
            width:'fit',
            colModel:[
                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 80, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 80, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 80, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 80, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'carstorage.veh_cpid.label')}", width : 80, align : 'center',name : 'veh_cpid'},
                {header : "${message(code: 'carstorage.veh_Ggpc.label')}", width : 80, align : 'center',name : 'veh_Ggpc'},
                {header : "${message(code: 'carstorage.veh_qxhx.label')}", width : 80, align : 'center',name : 'veh_qxhx'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width : 80, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 80, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 80, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width : 80, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width : 80, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 80, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 80, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 80, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'carstorage.veh_Hxnbg.label')}", width : 80, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'carstorage.veh_Zzl.label')}", width : 80, align : 'center',name : 'veh_Zzl'},
                {header : "${message(code: 'carstorage.veh_Edzzl.label')}", width : 80, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'carstorage.veh_Zbzl.label')}", width : 80, align : 'center',name : 'veh_Zbzl'},
                {header : "${message(code: 'carstorage.veh_Zzllyxs.label')}", width : 80, align : 'center',name : 'veh_Zzllyxs'},
                {header : "${message(code: 'carstorage.veh_Zqyzzl.label')}", width : 80, align : 'center',name : 'veh_Zqyzzl'},
                {header : "${message(code: 'carstorage.veh_Edzk.label')}", width : 80, align : 'center',name : 'veh_Edzk'},
                {header : "${message(code: 'carstorage.veh_Bgcazzdyxzzl.label')}", width : 80, align : 'center',name : 'veh_Bgcazzdyxzzl'},
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 80, align : 'center',name : 'veh_Jsszcrs'},

                {header : "${message(code: 'carstorage.veh_Hzdfs.label')}", width : 80, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'carstorage.veh_Cpggh.label')}", width : 80, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 80, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'carstorage.veh_jjlqj.label')}", width : 80, align : 'center',name : 'veh_jjlqj'},
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 80, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 80, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 80, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 80, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

				{header : "${message(code: 'carstorage.veh_fgbsqy.label')}", width : 80, align : 'center',name : 'veh_fgbsqy'},
                {header : "${message(code: 'carstorage.veh_fgbsxh.label')}", width : 80, align : 'center',name : 'veh_fgbsxh'},
				{header : "${message(code: 'carstorage.veh_fgbssb.label')}", width : 80, align : 'center',name : 'veh_fgbssb'},

				{header : "${message(code: 'carstorage.veh_y45pic.label')}", width : 80, align : 'center',name : 'veh_y45pic'},
				{header : "${message(code: 'carstorage.veh_zhpic.label')}", width : 80, align : 'center',name : 'veh_zhpic'},
				{header : "${message(code: 'carstorage.veh_fhpic.label')}", width : 80, align : 'center',name : 'veh_fhpic'},
				{header : "${message(code: 'carstorage.veh_pic1.label')}", width : 80, align : 'center',name : 'veh_pic1'},
				{header : "${message(code: 'carstorage.veh_pic2.label')}", width : 80, align : 'center',name : 'veh_pic2'},
				{header : "${message(code: 'carstorage.veh_other.label')}", width : 80, align : 'center',name : 'veh_other'},
			   
                {header : "${message(code: 'carstorage.createTime.label')}", width : 80, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 80, align : 'center',name : 'updateTime'},
				{header : "${message(code: 'carstorage.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'}
            ]
               
        });
    }*/
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
