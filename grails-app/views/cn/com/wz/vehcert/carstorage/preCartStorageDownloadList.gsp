
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span><g:message code="carstorage.veh_Clxh.label" default="veh_Clxh" />：</span></td>
                    <td width="100"><span><g:textField id="clxh" name="clxh" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="carstorage.veh_Dpxh.label" default="veh_Clxh" />：</span></td>
                    <td width="100"><span><g:textField id="clxh" name="dpxh" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="carstorage.veh_Clmc.label" default="veh_Clmc" />：</span></td>
                    <td width="100"><span><g:textField id="clmc" name="clmc" maxlength="200"/></span></td>
                    <td width='20'></td>
                    <td width="80"><span><g:message code="default.select.xxlx.label" default="info type" />：</span></td>
                    <td width="100"><span>
                        <select id="infoType" name="infoType">    <!-- 1. 公告信息   2.一致性信息-->
                            <option value="1">${message(code: 'carstorage.button.gg.label', default: 'notice info')}</option>
                            <option value="2">${message(code: 'carstorage.button.yzx.label', default: 'consistency info')}</option>
                            <option value="3">分解前公告信息</option>
                        </select>
                    </span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>
        </form>

        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <c:resourceAuth resourceCode="dv_syn_all">
                <input id="btn_synchro" type="button" class="export1" value="<g:message code="default.button.synchro.label" default="Synchro" />"/>
            </c:resourceAuth>
            <c:resourceAuth resourceCode="dv_syn_arg">
                <input id="btn_synchro_agr" type="button" class="export1" value="<g:message code="default.button.synchroagr.label" default="SynchroAgr" />"/>
            </c:resourceAuth>

            <input id="btn_initialize" type="button" class="export1" value="<g:message code="carstorage.button.initialize.lable" default="Initialize" />"/>
            %{--<export:formats formats="['excel']"  controller="" action="" style="border:0px; margin-top:-25px;margin-left:110px;"/>--}%
        </fieldset>

        <div style="margin-right:8px;margin-top:8px;">
            <div id="carStorage_grid" style="border:0px"></div>
            <div id="carStorage_grid1" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});

        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = cahangeByQuery();
                url+='?'+$('#form_query').serialize();

                $('#carStorage_grid').omGrid('setData',url);
            }
        });
        $('#infoType').change(function(){
            tabByDiv();
        })

        //绑定初始化按钮事件
        $('#btn_initialize').click(function(){
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'carstorage.initialize.lable', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        showWaiting();
                        var url = "${createLink(controller:'carStorage',action:'initialize')}";
                        $.post(url,{idss:'1'},function(data,textStatus){
                            var selections = $('#carStorage_grid').omGrid('reload');
                            alert(data);
                            $.omMessageBox.waiting('close');
                        },'text');
                    }
                }
            });
        })
        //绑定同步按钮事件
        $('#btn_synchro').click(function(){
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'carstorage.tongbu.lable', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        showWaiting();
                        var url = '${createLink(controller:'carStorage',action:'synchroTable')}';

                        $.post(url,{idss:'1',synType:"all"},function(data,textStatus){
                            var selections = $('#carStorage_grid').omGrid('reload');
                            alert(data);
                            $.omMessageBox.waiting('close');
                        },'text');
                    }
                }
            });
        });
        //绑定同步按钮事件
        $('#btn_synchro_agr').click(function(){
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'carstorage.tongbu.lable', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        showWaiting();
                        var url = '${createLink(controller:'carStorage',action:'synchroTable')}';

                        $.post(url,{idss:'1',synType:"agr"},function(data,textStatus){
                            var selections = $('#carStorage_grid').omGrid('reload');
                            alert(data);
                            $.omMessageBox.waiting('close');
                        },'text');
                    }
                }
            });
        });

        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#clxh").val('');
                $("#clmc").val('');
            }
        });
        $(".pdf").click(function(){
            var name= $("#name").val();
            $('.pdf').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            //window.location.href="${createLink(controller:'workshop',action:'export')}?format=pdf&extension=pdf";
            //window.location.href=url;
        });
        $(".excel").click(function(){
            var name= $("#name").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            //window.location.href="${createLink(controller:'workshop',action:'export')}?format=excel&extension=xls";
            //window.location.href=url;
        });
    })

    function showWaiting(){
        $.omMessageBox.waiting({
            title:'请等待',
            content:'服务器正在处理大量数据请求,请耐心等待...'
        });
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
        var url='${createLink(controller:'carStorage',action:'getCompResult')}';
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
        tabByDiv();
    });
    function buildRightGridByZcinfo(){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'carStorage',action:'jsonListByDownload')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[ {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 150, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 120, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 120, align : 'center',name : 'veh_Clfl'},
                {header : "<font color='blue'>${message(code: 'carstorage.veh_Clmc.label')}</font>", width : 120, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 120, align : 'center',name : 'veh_Clpp'},
                {header : "<font color='blue'>${message(code: 'carstorage.veh_Clxh.label')}</font>", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 120, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 120, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 120, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'carstorage.veh_cpid.label')}", width : 120, align : 'center',name : 'veh_cpid'},
                {header : "${message(code: 'carstorage.veh_Ggpc.label')}", width : 120, align : 'center',name : 'veh_Ggpc'},
                {header : "${message(code: 'carstorage.veh_qxhx.label')}", width : 120, align : 'center',name : 'veh_qxhx'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width : 80, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 120, align : 'center',name : 'veh_Ltgg'},
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
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 150, align : 'center',name : 'veh_Jsszcrs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Hzdfs.label')}", width : 80, align : 'center',name : 'veh_Hzdfs'},
                {header : "${message(code: 'carstorage.veh_Cpggh.label')}", width : 80, align : 'center',name : 'veh_Cpggh'},
                {header : "${message(code: 'carstorage.veh_Ggsxrq.label')}", width : 120, align : 'center',name : 'veh_Ggsxrq'},
                {header : "${message(code: 'carstorage.veh_jjlqj.label')}", width : 80, align : 'center',name : 'veh_jjlqj'},
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 80, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 200, align : 'left',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 160, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 200, align : 'left',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

                {header : "${message(code: 'carstorage.veh_fgbsqy.label')}", width : 80, align : 'center',name : 'veh_fgbsqy'},
                {header : "${message(code: 'carstorage.veh_fgbssb.label')}", width : 80, align : 'center',name : 'veh_fgbssb'},
                {header : "${message(code: 'carstorage.veh_fgbsxh.label')}", width : 80, align : 'center',name : 'veh_fgbsxh'},
                {header : "${message(code: 'carstorage.veh_y45pic.label')}", width : 80, align : 'center',name : 'veh_y45pic'},
                {header : "${message(code: 'carstorage.veh_zhpic.label')}", width : 80, align : 'center',name : 'veh_zhpic'},
                {header : "${message(code: 'carstorage.veh_fhpic.label')}", width : 80, align : 'center',name : 'veh_fhpic'},
                {header : "${message(code: 'carstorage.veh_pic1.label')}", width : 80, align : 'center',name : 'veh_pic1'},
                {header : "${message(code: 'carstorage.veh_pic2.label')}", width : 80, align : 'center',name : 'veh_pic2'},
                {header : "${message(code: 'carstorage.veh_other.label')}", width : 80, align : 'center',name : 'veh_other'},

                {header : "${message(code: 'carstorage.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 80, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'carstorage.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'},
                {header : "公告状态", width : 80, align : 'center',name : 'turnOff',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">启用</span>';
                        }else if(value==1){
                            return '<span style="color: red">停用</span>';
                        }
                    }
                },
                {header : "停用时间", width : 80, align : 'center',name : 'turnOffTime'}]
        });
    }
    function buildRightGridByOcocarger(){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'cocCarStorage',action:'jsonListByWork')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[
                {header : "<font color='blue'>${message(code: 'coc.coc_clxh.label', default: 'H车辆型号')}</font>", name : 'coc_clxh', width : 150, align : 'center'} ,
                {header : "<font color='blue'>${message(code: 'coc.coc_clmc.label', default: 'H车辆名称')}</font>", name : 'coc_clmc', width : 150, align : 'center'} ,
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
                {header : "${message(code: 'coc.coc_fdjzzsmc.label', default: '发动机制造商名称')}", name : 'coc_fdjzzsmc', width : 160, align : 'center'} ,
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
                {header : "${message(code: 'coc.coc_sb.label', default: '速比')}", name : 'coc_sb', width : 370, align : 'center'},
                {header : "${message(code: 'coc.coc_zcdb.label', default: '主传动比')}", name : 'coc_zcdb', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_ltgg.label', default: '轮胎规格')}", name : 'coc_ltgg', width : 250, align : 'center'},
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
                {header : "${message(code: 'coc.coc_cccno.label', default: 'CCC证书编号')}", name : 'coc_cccno', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_sybgno.label', default: '或实验报告编号')}", name : 'coc_sybgno', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_zgcs.label', default: '最高车速')}", name : 'coc_zgcs', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_sj.label', default: '声级')}", name : 'coc_sj', width : 960, align : 'center'},
                {header : "${message(code: 'coc.coc_pqpfw.label', default: '排气排放物')}", name : 'coc_pqpfw', width : 1110, align : 'center'},
                {header : "${message(code: 'coc.coc_co2.label', default: 'CO2排放')}", name : 'coc_co2', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_jgyqsybg.label', default: '结构要求的试验报告')}", name : 'coc_jgyqsybg', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_wxhwysjb.label', default: '危险货物运输的级别')}", name : 'coc_wxhwysjb', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_dwjgyqbh.label', default: '动物的结构要求的编号')}", name : 'coc_dwjgyqbh', width : 100, align : 'center'},
                {header : "${message(code: 'coc.coc_mxdwysjb.label', default: '某些动物运输的级别')}", name : 'coc_mxdwysjb', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_note.label', default: '备注')}", name : 'coc_note', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zsno.label', default: 'H证书编号')}", name : 'coc_zsno', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clsccmc.label', default: 'H车辆生产厂名称')}", name : 'coc_clsccmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clzzg.label', default: 'H车辆制造国')}", name : 'coc_clzzg', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxxldhmc.label', default: 'H车型系列代号名称')}", name : 'coc_cxxldhmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_dydhmc.label', default: 'H单元代号名称')}", name : 'coc_dydhmc', width : 190, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxdhmc.label', default: 'H车型代号名称')}", name : 'coc_cxdhmc', width : 150, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cxmc.label', default: 'H车型名称')}", name : 'coc_cxmc', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clzwpp.label', default: 'H车辆中文品牌')}", name : 'coc_clzwpp', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_cllb.label', default: 'H车辆类别')}", name : 'coc_cllb', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zzsmc.label', default: 'H制造商的名称')}", name : 'coc_zzsmc', width : 180, align : 'center'} ,
                {header : "${message(code: 'coc.coc_zzsdz.label', default: 'H制造商的地址')}", name : 'coc_zzsdz', width : 220, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdmpwz.label', default: 'H法定名牌的位置')}", name : 'coc_fdmpwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_clsbdh.label', default: 'H车辆识别代号')}", name : 'coc_clsbdh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_sbhdkwz.label', default: 'H识别号的打刻位置')}", name : 'coc_sbhdkwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjbh.label', default: 'H发动机编号')}", name : 'coc_fdjbh', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_fdjbhdkwz.label', default: 'H发动机编号的打刻位置')}", name : 'coc_fdjbhdkwz', width : 100, align : 'center'} ,
                {header : "${message(code: 'coc.coc_ccczsh.label', default: 'HCCC证书号')}", name : 'coc_ccczsh', width : 180, align : 'center'} ,
                {header : "${message(code: 'coc.coc_rq.label', default: 'H日期')}", name : 'coc_rq', width : 100, align : 'center'}
            ]
        });
    }


    function buildRightGridForPreCarstorage(){
        $('#carStorage_grid').omGrid({
            dataSource:"${createLink(controller:'PreCarStorage',action:'jsonListByDownload')}",
            method:'POST',
            extraData:{list:'${list}'} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12, // 分页显示，每页显示30条
            height : 440,
            width:'fit',
            colModel:[

                {header : "${message(code: 'carstorage.veh_Clzzqymc.label')}", width : 150, align : 'center',name : 'veh_Clzzqymc'},
                {header : "${message(code: 'carstorage.veh_Qyid.label')}", width : 80, align : 'center',name : 'veh_Qyid'},
                {header : "${message(code: 'carstorage.veh_Clfl.label')}", width : 80, align : 'center',name : 'veh_Clfl'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Clpp.label')}", width : 80, align : 'center',name : 'veh_Clpp'},
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 120, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Csys.label')}", width : 80, align : 'center',name : 'veh_Csys'},
                {header : "${message(code: 'carstorage.veh_Dpxh.label')}", width : 120, align : 'center',name : 'veh_Dpxh'},
                {header : "${message(code: 'carstorage.veh_Dpid.label')}", width : 80, align : 'center',name : 'veh_Dpid'},
                {header : "${message(code: 'carstorage.veh_cpid.label')}", width : 80, align : 'center',name : 'veh_cpid'},
                {header : "${message(code: 'carstorage.veh_Ggpc.label')}", width : 80, align : 'center',name : 'veh_Ggpc'},
                {header : "${message(code: 'carstorage.veh_qxhx.label')}", width : 270, align : 'left',name : 'veh_qxhx'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 477, align : 'left',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Rlzl.label')}", width : 80, align : 'center',name : 'veh_Rlzl'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 250, align : 'left',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Gl.label')}", width :120, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 80, align : 'center',name : 'veh_zdjgl'},
                {header : "${message(code: 'carstorage.veh_Zxxs.label')}", width : 80, align : 'center',name : 'veh_Zxxs'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Lts.label')}", width : 80, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 430, align : 'left',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 220, align : 'left',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width :150, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Zh.label')}", width : 80, align : 'center',name : 'veh_Zh'},
                {header : "${message(code: 'carstorage.veh_Zs.label')}", width : 80, align : 'center',name : 'veh_Zs'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width :120, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 120, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 150, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 150, align : 'center',name : 'veh_Hxnbk'},
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
                {header : "${message(code: 'carstorage.veh_dpqy.label')}", width : 150, align : 'center',name : 'veh_dpqy'},
                {header : "${message(code: 'carstorage.veh_Zgcs.label')}", width : 80, align : 'center',name : 'veh_Zgcs'},
                {header : "${message(code: 'carstorage.veh_Yh.label')}", width : 80, align : 'center',name : 'veh_Yh'},
                {header : "${message(code: 'carstorage.veh_Bz.label')}", width : 80, align : 'center',name : 'veh_Bz'},
                {header : "${message(code: 'carstorage.veh_Qybz.label')}", width : 80, align : 'center',name : 'veh_Qybz'},
                {header : "${message(code: 'carstorage.veh_Cpscdz.label')}", width : 150, align : 'center',name : 'veh_Cpscdz'},
                {header : "${message(code: 'carstorage.veh_Qyqtxx.label')}", width : 80, align : 'center',name : 'veh_Qyqtxx'},
                {header : "${message(code: 'carstorage.veh_Pfbz.label')}", width : 80, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'carstorage.veh_Clztxx.label')}", width : 80, align : 'center',name : 'veh_Clztxx'},
                {header : "${message(code: 'carstorage.veh_Jss.label')}", width : 80, align : 'center',name : 'veh_Jss'},
                {header : "${message(code: 'carstorage.veh_Jsslx.label')}", width : 80, align : 'center',name : 'veh_Jsslx'},

                {header : "${message(code: 'carstorage.veh_fgbsqy.label')}", width : 150, align : 'center',name : 'veh_fgbsqy'},
                {header : "${message(code: 'carstorage.veh_fgbsxh.label')}", width : 80, align : 'center',name : 'veh_fgbsxh'},
                {header : "${message(code: 'carstorage.veh_fgbssb.label')}", width : 80, align : 'center',name : 'veh_fgbssb'},

                {header : "${message(code: 'carstorage.veh_y45pic.label')}", width : 280, align : 'left',name : 'veh_y45pic'},
                {header : "${message(code: 'carstorage.veh_zhpic.label')}", width : 280, align : 'left',name : 'veh_zhpic'},
                {header : "${message(code: 'carstorage.veh_fhpic.label')}", width : 280, align : 'left',name : 'veh_fhpic'},
                {header : "${message(code: 'carstorage.veh_pic1.label')}", width : 280, align : 'left',name : 'veh_pic1'},
                {header : "${message(code: 'carstorage.veh_pic2.label')}", width : 280, align : 'left',name : 'veh_pic2'},
                {header : "${message(code: 'carstorage.veh_other.label')}", width : 3500, align : 'left',name : 'veh_other'},
                {header : "${message(code: 'carstorage.veh_clsbdh.label')}", width : 150, align : 'left',name : 'veh_clsbdh'},
                {header : "${message(code: 'carstorage.veh_cph.label')}", width : 150, align : 'left',name : 'veh_cph'},
                {header : "${message(code: 'carstorage.veh_zdjgl.label')}", width : 280, align : 'left',name : 'veh_zdjgl'},

                {header : "${message(code: 'carstorage.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'carstorage.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'}
            ]

        });
    }

    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }

    /// 切换当前容器
    function tabByDiv()
    {
        var divType = $('#infoType').val();
        if(divType=='1') ////公告信息
            buildRightGridByZcinfo();
        else if(divType=='2'){
            buildRightGridByOcocarger();
        }else if(divType=='3'){
            buildRightGridForPreCarstorage();
        }

    }

    /// 查询函数操作
    function cahangeByQuery()
    {
        var divType = $('#infoType').val();
        if(divType=='1'){

            return "${createLink(controller:'carStorage',action:'jsonListByDownload')}";
        } else if(divType=='2'){
            return "${createLink(controller:'cocCarStorage',action:'jsonListByWork')}";
        }else if(divType=='3'){
            return "${createLink(controller:'PreCarStorage',action:'jsonListByDownload')}";
        }


    }
</script>
</body>
</html>
