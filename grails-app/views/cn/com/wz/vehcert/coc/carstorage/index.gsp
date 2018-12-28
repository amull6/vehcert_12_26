<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="一致性证书信息" />
    <script src="${resource(dir:"js/parent",file:"DivDialog.js") }" type="text/javascript" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="width:100%;background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="coc.coc_clxh.label" default="车辆型号" />：</span></td>
                    <td width="100"><span><g:textField id="coc_clxh" name="coc_clxh" maxlength="200"/></span></td>
                    <td width="60"></td>
                    <td width="100"><span><g:message code="coc.coc_clmc.label" default="车辆名称" />：</span></td>
                    <td width="100"><span><g:textField id="coc_clmc" name="coc_clmc" maxlength="200"/></span></td>
                    <td width="60"></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:8px 8px 8px 4px;">
            <input id="btn_create" type="button" class="create" value="新建"/>
            <input id="btn_edit" type="button"  class="edit" value="编辑"/>
            <input id="btn_delete" type="button"  class="delete" value="删除"/>
            <input id="btn_deleteSearch" type="button"  class="delete" value="${message(code: 'default.button.deleteSearch.label', default: 'Delete')}" />
            <input id="btn_view" type="button"  class="edit" value="查看"/>
            <input id="btn_template" type="button"  class="edit" value="导入模板下载"/>
            <input id="btn_import" type="button"  class="import" value="导入"/>
            <input id="btn_exportexcel" type="button" class="edit" value="导出excel"/>
            %{--//<input id="btn_exportpdf" type="button" class="edit" value="导出pdf"/>--}%
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;" >
            <div id="coc_content" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'cocCarStorage',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#coc_content').omGrid('setData',url);
            }
        });
        //绑定清除按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#coc_clxh").val('');
                $("#coc_clmc").val('');
            }
        });
        buildRightGrid();
        $('#btn_create').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'create')}';
            window.location.href = url;
        });
        $('#btn_template').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'downloadTemplate')}';
            window.location.href = url;
        });
        $('#btn_import').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'batch')}';
            create(url);
        });
        $('#btn_exportexcel').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'exportexcel')}';
            var coc_clxh=$("#coc_clxh").val();
            var coc_clmc= $("#coc_clmc").val();
            url=url+"?format=excel&extension=xlsx&coc_clxh="+coc_clxh+"&coc_clmc="+coc_clmc

            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            url=encodeURI(url);
            window.location.href = url;
        });
        $('#btn_exportpdf').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'exportpdf')}';
            var coc_clxh=$("#coc_clxh").val();
            var coc_clmc= $("#coc_clmc").val();
            url=url+"?coc_clxh="+coc_clxh+"&coc_clmc="+coc_clmc
            url=encodeURI(url);
            window.location.href = url;
        });
        $('#btn_edit').click(function(){
            var selections = $('#coc_content').omGrid('getSelections',true);
            if(selections.length!=1){
                alert('请选择一条一致性证书信息进行编辑');
                return;
            }
            var url = '${createLink(controller:'cocCarStorage',action:'edit')}';
            window.location.href = url+'/'+selections[0].id;
        });
        $('#btn_delete').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#coc_content').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        var deleteUrl = '${createLink(controller:'cocCarStorage',action:'jsonDelete')}';
                        var deleteIds = '';
                        $.each(selections,function(i,item){
                            deleteIds += item.id+'_';
                        });
                        $.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
                            var selections = $('#coc_content').omGrid('reload');
                            var content = "<div class='message'>"+data+"</div>";
                            showTopTip(content);
                        },'text');
                    }
                }
            });

        });
        $('#btn_deleteSearch').click(function(){
            //这里利用Ajax删除数据
            var veh_Clxh= $("#coc_clxh").val();
            var veh_Clmc=$("#coc_clmc").val();
            var content='';
            if(veh_Clxh==''&&veh_Clmc==''){
                content='您确定要删除所有记录吗？';
            }else{
                content='您确定删除吗？';
            }
            $.omMessageBox.confirm({
                title:'确认删除',
                content:content,
                onClose:function(value){
                    if(value){
                        var deleteUrl = '${createLink(controller:'cocCarStorage',action:'jsonDeleteSearch')}';
                        var dialogId=showModelWait();
                        $.post(deleteUrl,{'veh_Clmc':veh_Clmc,'veh_Clxh':veh_Clxh},function(data,textStatus){
                            parent.closeDialog(dialogId);
                            var selections = $('#coc_content').omGrid('reload');
                            var content = data;
                            showTopTip(content);
                        },'text');
                    }
                }
            });

        });
        $('#btn_view').click(function(){
            var url = '${createLink(controller:'cocCarStorage',action:'show')}';
            var selected = $('#coc_content').omGrid('getSelections',true);
            if(selected.length==0){
                alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
            }else if(selected.length>1){
                alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
            }else{
                window.location.href = url+'?id='+selected[0].id;
            }
        });
    })
    // ################################加载时执行的语句########################
    $(function() {
        //刚加载页面后使右下部分内容显示
        $('#page').css({height:$(document).height()-16});
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            }]
        });
    });
    function backFunc(result){

    }
    function create(url){
        showModelDialog(url);
    }

    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'cocCarStorage',action:'getCompResult')}';
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
    function showModelWait(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }
    function buildRightGrid(){
        $('#coc_content').omGrid({
            dataSource:'${createLink(controller:'cocCarStorage',action:'jsonList')}?'+$('#form_query').serialize(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height : 440,
            colModel:[
                {header : "${message(code: 'coc.coc_czsl.label', default: '车轴数量')}", name : 'coc_czsl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_clsl.label', default: '车轮数量')}", name : 'coc_clsl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qdzwz.label', default: '驱动轴位置')}", name : 'coc_qdzwz', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zj.label', default: '轴距')}", name : 'coc_zj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qyzqzj.label', default: '牵引座前置距')}", name : 'coc_qyzqzj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_lj.label', default: '轮距')}", name : 'coc_lj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_cd.label', default: '长度')}", name : 'coc_cd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zqdyqyzzjl.label', default: '最前端与牵引装置的距离')}", name : 'coc_zqdyqyzzjl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zzqycd.label', default: '装载区域长度')}", name : 'coc_zzqycd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_kd.label', default: '宽度')}", name : 'coc_kd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gd.label', default: '高度')}", name : 'coc_gd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_tymj.label', default: '车辆在地面上的投影面积')}", name : 'coc_tymj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qx.label', default: '前悬')}", name : 'coc_qx', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hx.label', default: '后悬')}", name : 'coc_hx', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_jjj.label', default: '接近角')}", name : 'coc_jjj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_lqj.label', default: '离去角')}", name : 'coc_lqj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_xsxdcsclzl.label', default: '行驶下带车身的车辆质量')}", name : 'coc_xsxdcsclzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_edzzl.label', default: '额定总质量')}", name : 'coc_edzzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zlzhfp.label', default: '该质量的轴荷分配')}", name : 'coc_zlzhfp', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zzllyxsh.label', default: '载质量利用系数')}", name : 'coc_zzllyxsh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_czyxzdzl.label', default: '车轴允许的最大质量')}", name : 'coc_czyxzdzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_kssczwz.label', default: '可伸缩车轴的位置')}", name : 'coc_kssczwz', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_kczczwz.label', default: '可承载车轴位置')}", name : 'coc_kczczwz', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_cdzdyxzh.label', default: '车顶最大允许载荷')}", name : 'coc_cdzdyxzh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qygsgc.label', default: '牵引杆式挂车')}", name : 'coc_qygsgc', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_bgc.label', default: '半挂车')}", name : 'coc_bgc', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zjjgc.label', default: '中间轴挂车')}", name : 'coc_zjjgc', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gczdzl.label', default: '挂车的最大质量')}", name : 'coc_gczdzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zdzhzl.label', default: '最大组合质量')}", name : 'coc_zdzhzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_jcdczdczfh.label', default: '接触点处的最大垂直负荷')}", name : 'coc_jcdczdczfh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_fdjzzsmc.label', default: '发动机制造商名称')}", name : 'coc_fdjzzsmc', width : 150, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjxh.label', default: '发动机型号')}", name : 'coc_fdjxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjgzyl.label', default: '发动机工作原理')}", name : 'coc_fdjgzyl', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zjps.label', default: '直接喷射')}", name : 'coc_zjps', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qgsl.label', default: '气缸数量')}", name : 'coc_qgsl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qgplxs.label', default: '气缸排列形式')}", name : 'coc_qgplxs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_pl.label', default: '排量')}", name : 'coc_pl', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_rlzl.label', default: '燃料种类')}", name : 'coc_rlzl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zdjgl.label', default: '最大净功率')}", name : 'coc_zdjgl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_dyfdjzs.label', default: '对应的发动机转速')}", name : 'coc_dyfdjzs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_lhqxs.label', default: '离合器形式')}", name : 'coc_lhqxs', width : 120, align : 'center'},
                {header : "${message(code: 'coc.coc_bsqzs.label', default: '变速器形式')}", name : 'coc_bsqzs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_sb.label', default: '速比')}", name : 'coc_sb', width : 370, align : 'left'},
                {header : "${message(code: 'coc.coc_zcdb.label', default: '主传动比')}", name : 'coc_zcdb', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_ltgg.label', default: '轮胎规格')}", name : 'coc_ltgg', width : 250, align : 'left'},
                {header : "${message(code: 'coc.coc_sfazkqxg.label', default: '是否安装有空气悬挂')}", name : 'coc_sfazkqxg', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_sfzykqxgzz.label', default: '是否装有空气悬挂的装置')}", name : 'coc_sfzykqxgzz', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gbthps.label', default: '钢板弹簧的片数')}", name : 'coc_gbthps', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zxzlxs.label', default: '转向助力形式')}", name : 'coc_zxzlxs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zdzzjysm.label', default: '制动装置简要说明')}", name : 'coc_zdzzjysm', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zdxtgqgnyl.label', default: '制动系统供气管内压力')}", name : 'coc_zdxtgqgnyl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_csxs.label', default: '车身形式')}", name : 'coc_csxs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_clys.label', default: '车辆颜色')}", name : 'coc_clys', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_gnyxrj.label', default: '罐体内有效容积')}", name : 'coc_gnyxrj', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxcd.label', default: '货箱长度')}", name : 'coc_hxcd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxkd.label', default: '货箱宽度')}", name : 'coc_hxkd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_hxgd.label', default: '货箱高度')}", name : 'coc_hxgd', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_qzjzdljnl.label', default: '起重机的最大力矩能力')}", name : 'coc_qzjzdljnl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_cmsl.label', default: '车门数量')}", name : 'coc_cmsl', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_cmgz.label', default: '车门构造')}", name : 'coc_cmgz', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zws.label', default: '座位数')}", name : 'coc_zws', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_cccno.label', default: 'CCC证书编号')}", name : 'coc_cccno', width : 150, align : 'center'},
                {header : "${message(code: 'coc.coc_sybgno.label', default: '或实验报告编号')}", name : 'coc_sybgno', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zgcs.label', default: '最高车速')}", name : 'coc_zgcs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_sj.label', default: '声级')}", name : 'coc_sj', width : 960, align : 'left'},
                {header : "${message(code: 'coc.coc_pqpfw.label', default: '排气排放物')}", name : 'coc_pqpfw', width : 1110, align : 'left'},
                {header : "${message(code: 'coc.coc_co2.label', default: 'CO2排放')}", name : 'coc_co2', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_jgyqsybg.label', default: '结构要求的试验报告')}", name : 'coc_jgyqsybg', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_wxhwysjb.label', default: '危险货物运输的级别')}", name : 'coc_wxhwysjb', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_dwjgyqbh.label', default: '动物的结构要求的编号')}", name : 'coc_dwjgyqbh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_mxdwysjb.label', default: '某些动物运输的级别')}", name : 'coc_mxdwysjb', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_note.label', default: '备注')}", name : 'coc_note', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zsno.label', default: 'H证书编号')}", name : 'coc_zsno', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clsccmc.label', default: 'H车辆生产厂名称')}", name : 'coc_clsccmc', width : 220, align : 'left'} ,
                {header : "${message(code: 'coc.coc_clzzg.label', default: 'H车辆制造国')}", name : 'coc_clzzg', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxxldhmc.label', default: 'H车型系列代号名称')}", name : 'coc_cxxldhmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_dydhmc.label', default: 'H单元代号名称')}", name : 'coc_dydhmc', width : 190, align : 'left'} ,
                {header : "${message(code: 'coc.coc_cxdhmc.label', default: 'H车型代号名称')}", name : 'coc_cxdhmc', width : 150, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxmc.label', default: 'H车型名称')}", name : 'coc_cxmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clzwpp.label', default: 'H车辆中文品牌')}", name : 'coc_clzwpp', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cllb.label', default: 'H车辆类别')}", name : 'coc_cllb', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zzsmc.label', default: 'H制造商的名称')}", name : 'coc_zzsmc', width : 180, align : 'left'} ,
                {header : "${message(code: 'coc.coc_zzsdz.label', default: 'H制造商的地址')}", name : 'coc_zzsdz', width : 220, align : 'left'} ,
                {header : "${message(code: 'coc.coc_fdmpwz.label', default: 'H法定名牌的位置')}", name : 'coc_fdmpwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clsbdh.label', default: 'H车辆识别代号')}", name : 'coc_clsbdh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_sbhdkwz.label', default: 'H识别号的打刻位置')}", name : 'coc_sbhdkwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjbh.label', default: 'H发动机编号')}", name : 'coc_fdjbh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjbhdkwz.label', default: 'H发动机编号的打刻位置')}", name : 'coc_fdjbhdkwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ccczsh.label', default: 'HCCC证书号')}", name : 'coc_ccczsh', width : 180, align : 'left'} ,
                {header : "${message(code: 'coc.coc_clxh.label', default: 'H车辆型号')}", name : 'coc_clxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clmc.label', default: 'H车辆名称')}", name : 'coc_clmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_rq.label', default: 'H日期')}", name : 'coc_rq', width : 100, align : 'center'},

                {header : "${message(code: 'coc.coc_new_energy.label', default: '是否新能源车型')}", name : 'coc_new_energy', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_ddjgzdy.label', default: '电动机工作电压')}", name : 'coc_ddjgzdy', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_dldcxh.label', default: '动力电池型号')}", name : 'coc_dldcxh', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_dldceddy.label', default: '动力电池额定电压')}", name : 'coc_dldceddy', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_dldcedrl.label', default: '动力电池额定容量')}", name : 'coc_dldcedrl', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_dldcsccmc.label', default: '动力电池生产厂名称')}", name : 'coc_dldcsccmc', width : 100, align : 'left'} ,

                {header : "${message(code: 'coc.coc_abs.label', default: '是否带防抱死系统')}", name : 'coc_abs', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_scqydz.label', default: '生产企业地址')}", name : 'coc_scqydz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clzl.label', default: '车辆种类')}", name : 'coc_clzl', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_jccccno.label', default: '基础车辆一致性证书编号')}", name : 'coc_jccccno', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_jcclxh.label', default: '基础车辆型号')}", name : 'coc_jcclxh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_jccllb.label', default: '基础车辆类别')}", name : 'coc_jccllb', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zzjdcccno.label', default: '最终阶段车辆CCC证书编号')}", name : 'coc_zzjdcccno', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_zzjdqfrq.label', default: '最终阶段车辆CCC证书编号签发日期')}", name : 'coc_zzjdqfrq', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zdyxzzl.label', default: '最大允许总质量')}", name : 'coc_zdyxzzl', width : 100, align : 'center'} ,

                {header : "${message(code: 'coc.coc_sjbzh.label', default: 'CCC认证标准号及对应的实施阶段')}", name : 'coc_sjbzh', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_dzzs.label', default: '定置噪声')}", name : 'coc_dzzs', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_fdjzs.label', default: '对应的发动机转速')}", name : 'coc_fdjzs', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cwzs.label', default: '加速行驶车外噪音')}", name : 'coc_cwzs', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_pfwbzh.label', default: '排气排放物标准号及对应的实施阶段')}", name : 'coc_pfwbzh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl.label', default: '实验用液体燃料')}", name : 'coc_ytrl', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl_co.label', default: '实验用液体燃料CO')}", name : 'coc_ytrl_co', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl_nox.label', default: '实验用液体燃料NOX')}", name : 'coc_ytrl_nox', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl_yd.label', default: '实验用液体燃料烟度')}", name : 'coc_ytrl_yd', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_qtrl.label', default: '实验用气体燃料')}", name : 'coc_qtrl', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_qtrl_co.label', default: '实验用气体燃料CO')}", name : 'coc_qtrl_co', width : 100, align : 'center'} ,

                {header : "${message(code: 'coc.coc_qtrl_nmhc.label', default: '实验用气体燃料NMHC')}", name : 'coc_qtrl_nmhc', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_qtrl_ch4.label', default: '实验用气体燃料CH4')}", name : 'coc_qtrl_ch4', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_co2pflbzh.label', default: 'CO2排放量标准号')}", name : 'coc_co2pflbzh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_co2_sq.label', default: '市区CO2排放量')}", name : 'coc_co2_sq', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_co2_sj.label', default: '市郊CO2排放量')}", name : 'coc_co2_sj', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_co2_zh.label', default: '综合CO2排放量')}", name : 'coc_co2_zh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_rlxh_sq.label', default: '市区燃料消耗量')}", name : 'coc_rlxh_sq', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_rlxh_sq.label', default: '市郊燃料消耗量')}", name : 'coc_rlxh_sq', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_rlxh_zh.label', default: '综合燃料消耗量')}", name : 'coc_rlxh_zh', width : 100, align : 'center'} ,

                {header : "${message(code: 'coc.coc_ytrl_hc.label', default: '试验用液体燃料HC')}", name : 'coc_ytrl_hc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl_hcnox.label', default: '试验用液体燃料HC+NOx')}", name : 'coc_ytrl_hcnox', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ytrl_pn.label', default: '试验用液体燃料微粒物/PN')}", name : 'coc_ytrl_pn', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_qtrl_nox.label', default: '试验用气体燃料NOx')}", name : 'coc_qtrl_nox', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_qtrl_thc.label', default: '试验用气体燃料THC')}", name : 'coc_qtrl_thc', width : 100, align : 'left'} ,
                {header : "${message(code: 'coc.coc_qtrl_pn.label', default: '试验用气体燃料PN')}", name : 'coc_qtrl_pn', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clzclx.label', default: '车辆注册类型')}", name : 'coc_clzclx', width : 100, align : 'center'} ,

                {header : "${message(code: 'coc.coc_yzxzsbh.label', default: '一致性证书编号')}", name : 'coc_yzxzsbh', width : 170, align : 'center'},
                {header : "${message(code: 'coc.coc_creater.label', default: '创建人')}", name : 'creater', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_createTime.label', default: '创建时间')}", name : 'coc_createTime', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_updater.label', default: '更新人')}", name : 'updater', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_updateTime.label', default: '更新时间')}", name : 'coc_updateTime', width : 100, align : 'center'}
            ]
        });
    }
    /**
     *刷新表格
     */
    function refreshGrid(){
        $('#coc_content').omGrid("reload");
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
