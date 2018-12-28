
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="环保信息清单" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>车辆型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_clxh" name="en_clxh" maxlength="200" /></span></td>
                    <td style="width: 80px;text-align: right"><span>发动机型号：</span></td>
                    <td style="width: 100px"><span><g:textField id="en_fdjxh" name="en_fdjxh" style="width: 90px" /></span></td>
                    <td style="width: 90px;text-align: right"><span>信息公开编号：</span></td>
                    <td style="width: 230px"><span><g:textField id="en_xxgkbh" name="en_xxgkbh" style="width: 220px"/></span></td>
                    <td  style="width: 60px;text-align: right"><span>类型：</span></td>
                    <td width="100"><span>
                        <select id="carType" name="carType">    <!-- 1. 公告信息   2.一致性信息-->
                            <option value="0">重型柴油车/城市车辆</option>
                            <option value="1">双燃料/轻型汽油</option>
                            <option value="2">轻型柴油国四/国五</option>
                            <option value="3">重型燃气</option>
                        </select>
                    </span></td>
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
            %{--<c:resourceAuth resourceCode="dv_syn_arg">--}%
                %{--<input id="btn_synchro_agr" type="button" class="export1" value="<g:message code="default.button.synchroagr.label" default="SynchroAgr" />"/>--}%
            %{--</c:resourceAuth>--}%

            %{--<input id="btn_initialize" type="button" class="export1" value="<g:message code="carstorage.button.initialize.lable" default="Initialize" />"/>--}%
            %{--<export:formats formats="['excel']"  controller="" action="" style="border:0px; margin-top:-25px;margin-left:110px;"/>--}%
        </fieldset>

        <div style="margin-right:8px;margin-top:8px;">
            <div id="environment_grid" style="border:0px"></div>
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

                $('#environment_grid').omGrid('setData',url);
            }
        });
        $('#carType').change(function(){
            tabByDiv();
        })

        %{--//绑定初始化按钮事件--}%
        %{--$('#btn_initialize').click(function(){--}%
            %{--$.omMessageBox.confirm({--}%
                %{--title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',--}%
                %{--content:'${message(code: 'carstorage.initialize.lable', default: 'Are you sure?')}',--}%
                %{--onClose:function(v){--}%
                    %{--if(v){--}%
                        %{--showWaiting();--}%
                        %{--var url = "${createLink(controller:'carStorage',action:'initialize')}";--}%
                        %{--$.post(url,{idss:'1'},function(data,textStatus){--}%
                            %{--var selections = $('#environment_grid').omGrid('reload');--}%
                            %{--alert(data);--}%
                            %{--$.omMessageBox.waiting('close');--}%
                        %{--},'text');--}%
                    %{--}--}%
                %{--}--}%
            %{--});--}%
        %{--})--}%
        //绑定同步按钮事件
        $('#btn_synchro').click(function(){
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'carstorage.tongbu.lable', default: 'Are you sure?')}',
                onClose:function(v){
                    if(v){
                        showWaiting();
                        var url = '${createLink(controller:'synchro',action:'synchroTable')}';
                        $.post(url,{idss:'1'},function(data,textStatus){
                            var selections = $('#environment_grid').omGrid('reload');
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
                $("#en_clxh").val('');
                $("#en_fdjxh").val('');
                $("#en_xxgkbh").val('');
                $("#carType").val('');
            }
        });
        %{--$(".pdf").click(function(){--}%
            %{--var name= $("#name").val();--}%
            %{--$('.pdf').attr('href','javascript:void(0);');--}%
            %{--//当节点没有选中的情况，导出s通讯录中所有的数据--}%
            %{--//window.location.href="${createLink(controller:'workshop',action:'export')}?format=pdf&extension=pdf";--}%
            %{--//window.location.href=url;--}%
        %{--});--}%
        %{--$(".excel").click(function(){--}%
            %{--var name= $("#name").val();--}%
            %{--$('.excel').attr('href','javascript:void(0);');--}%
            %{--//当节点没有选中的情况，导出s通讯录中所有的数据--}%
            %{--//window.location.href="${createLink(controller:'workshop',action:'export')}?format=excel&extension=xls";--}%
            %{--//window.location.href=url;--}%
        %{--});--}%
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
        var url='${createLink(controller:'synchro',action:'getCompResult')}';
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
    function buildRightGridForHeavyDiesel(){
        $('#environment_grid').omGrid({
            dataSource:"${createLink(controller:'heavyDiesel',action:'jsonList')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[  {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_clsbff', width : 130, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 100, align : 'center'},
                {header : "发动机制造商", name : 'en_zzs', width : 155, align : 'center'},
                {header : "发动机系族名称", name : 'en_xzmc', width : 150, align : 'center'} ,
                {header : "发动机生产地址", name : 'en_sccdz', width : 150, align : 'center'},
                {header : "发动机厂牌", name : 'en_cp', width : 70, align : 'center'},

                {header : "GB 17691-2005检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 3847-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "HJ 689-2014检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "HJ 437-2008检测机构", name : 'en_jcbz4', width : 200, align : 'center'},
                {header : "HJ 438-2008检测机构", name : 'en_jcbz5', width : 200, align : 'center'} ,
                {header : "GB 1495-2002检测机构", name : 'en_jcbz6', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz7', width : 200, align : 'center'},

                {header : "最大净功率", name : 'en_zzjgl', width : 90, align : 'center'},
                {header : "最大净扭矩", name : 'en_zdjnj', width : 100, align : 'center'},
                {header : "燃料供给系统", name : 'en_rlggxtxs', width : 250, align : 'center'},
                {header : "喷油泵型号", name : 'en_pybxh', width : 250, align : 'center'},
                {header : "喷油器型号", name : 'en_pyqxh', width : 250, align : 'center'} ,
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "中冷器形式", name : 'en_zlqxs', width : 250, align : 'center'},
                {header : "OBD型号", name : 'en_OBDxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_EGRxh', width : 250, align : 'center'},
                {header : "ECU型号", name : 'en_ECUxh', width : 250, align : 'center'},
                {header : "排气后处理系统型号", name : 'en_pqhclxtxh', width : 250, align : 'center'},
                {header : "SCR封装/载体/涂层生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "DOC封装/载体/涂层生产厂", name : 'en_tc1', width : 250, align : 'center'},

                {header : "排气后处理系统形式", name : 'en_pqhclxtxs', width : 250, align : 'center'} ,
                {header : "空滤器型号", name : 'en_klqxh', width : 250, align : 'center'},
                {header : "进气消声器型号", name : 'en_jqxsqxh', width : 250, align : 'center'},
                {header : "排气消声器型号", name : 'en_pqxsqxh', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">重型柴油车</span>';
                        }else if(value==1){
                            return '<span style="color: red">城市车辆</span>';
                        }
                    }},

                {header : "创建时间", name : 'create_time', width : 150, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 150, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 150, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :150, align : 'center'}]
        });
    }
    function buildRightGridForLightDual(){
        $('#environment_grid').omGrid({
            dataSource:"${createLink(controller:'lightDual',action:'jsonList')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[
                {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},

                {header : "GB 18352.3-2013检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 18285-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz4', width : 200, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 90, align : 'center'},
                {header : "催化转化器型号", name : 'en_chzhqxh', width : 100, align : 'center'},
                {header : "涂层/载体/封装生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "燃油蒸发控制装置型号", name : 'en_rlzfkzzzxh', width : 250, align : 'center'},
                {header : "氧传感器型号", name : 'en_ycgqxh', width : 250, align : 'center'} ,
                {header : "曲轴箱排放控制装置型号", name : 'en_qzxpfkzzzxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "IUPR监测功能", name : 'en_iuprjcgn', width : 250, align : 'center'},
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'},
                {header : "燃气混合器型号【两用燃料】", name : 'en_rqhhqxh', width : 250, align : 'center'},
                {header : "燃气喷射单元型号 【两用燃料】", name : 'en_rqpsdyxh', width : 250, align : 'center'} ,
                {header : "变速器型式", name : 'en_bsqxs', width : 250, align : 'center'},
                {header : "消音器型号", name : 'en_xyqxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "中冷器型式", name : 'en_zlqxs', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">两用燃料车</span>';
                        }else if(value==1){
                            return '<span style="color: red">轻型汽油车</span>';
                        }
                    }},

                {header : "创建时间", name : 'create_time', width : 150, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 150, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 150, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :150, align : 'center'}
            ]
        });
    }
    function buildRightGridForLightDiesel(){
        $('#environment_grid').omGrid({
            dataSource:"${createLink(controller:'lightDiesel',action:'jsonList')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[
                {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},

                {header : "GB 18352.3-2005检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "GB 3847-2005检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "GB 1495-2002检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz4', width : 200, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 90, align : 'center'},
                {header : "后处理装置型号", name : 'en_hclzzxh', width : 100, align : 'center'},
                {header : "涂层/载体/封装生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "喷油泵型号", name : 'en_pybxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "喷油器型号", name : 'en_pyqxh', width : 250, align : 'center'} ,
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'} ,
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "中冷器型号", name : 'en_zlqxh', width : 250, align : 'center'},
                {header : "变速箱型式", name : 'en_bsxxs', width : 250, align : 'center'},
                {header : "消音器型号", name : 'en_xyqxh', width : 250, align : 'center'},
                {header : "IUPR监测功能", name : 'en_iuprjcgn', width : 250, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">轻型柴油车（国四）</span>';
                        }else if(value==1){
                            return '<span style="color: red">轻型柴油车（国五）</span>';
                        }
                    }},

                {header : "创建时间", name : 'create_time', width : 150, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 150, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 150, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :150, align : 'center'}]
        });
    }
    function buildRightGridForHeavyGas(){
        $('#environment_grid').omGrid({
            dataSource:"${createLink(controller:'heavyGas',action:'jsonList')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[
                {header : "信息公开编号", name : 'en_xxgkbh', width : 200, align : 'center'} ,

                {header : "车辆型号", name : 'en_clxh', width : 100, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 50, align : 'center'},
                {header : "排放阶段", name : 'en_pfjd', width : 50, align : 'center'},
                {header : "车辆识别方法和位置", name : 'en_cldsbffhwz', width : 130, align : 'center'},

                {header : "发动机型号", name : 'en_fdjxh', width : 100, align : 'center'},
                {header : "发动机制造商", name : 'en_zzsmc', width : 155, align : 'center'},
                {header : "发动机系族名称", name : 'en_xzmc', width : 150, align : 'center'} ,
                {header : "发动机生产地址", name : 'en_sccdz', width : 150, align : 'center'},
                {header : "发动机厂牌", name : 'en_cp', width : 70, align : 'center'},

                {header : "GB 17691-2005检测机构", name : 'en_jcbz1', width : 200, align : 'center'},
                {header : "HJ 437-2008检测机构", name : 'en_jcbz2', width : 200, align : 'center'},
                {header : "HJ 438-2008检测机构", name : 'en_jcbz3', width : 200, align : 'center'},
                {header : "GB 18285-2005检测机构", name : 'en_jcbz4', width : 200, align : 'center'},
                {header : "GB 11340-2005检测机构", name : 'en_jcbz5', width : 200, align : 'center'} ,
                {header : "GB 1495-2002检测机构", name : 'en_jcbz6', width : 200, align : 'center'},
                {header : "GB/T 27630-2011检测机构", name : 'en_jcbz7', width : 200, align : 'center'},

                {header : "最大净功率", name : 'en_zdjgl', width : 90, align : 'center'},
                {header : "最大净扭矩", name : 'en_zdjnj', width : 100, align : 'center'},
                {header : "燃料供给系统", name : 'en_rlgjxtxs', width : 250, align : 'center'},
                {header : "氧传感器型号", name : 'en_ycgqxh', width : 250, align : 'center'},
                {header : "蒸发器或压力调节器型号", name : 'en_zfqhyltjqxh', width : 250, align : 'center'} ,
                {header : "混合装置型号", name : 'en_hhzzxh', width : 250, align : 'center'},
                {header : "EGR型号", name : 'en_egrxh', width : 250, align : 'center'},
                {header : "增压器型号", name : 'en_zyqxh', width : 250, align : 'center'},
                {header : "中冷器型式", name : 'en_zlqxs', width : 250, align : 'center'},
                {header : "ECU型号", name : 'en_ecuxh', width : 250, align : 'center'},
                {header : "OBD型号", name : 'en_obdxh', width : 250, align : 'center'},
                {header : "曲轴箱污染物排放控制装置型号", name : 'en_qzxwrwpfkzzzxh', width : 250, align : 'center'} ,
                {header : "空滤器型号", name : 'en_kqlqqxh', width : 250, align : 'center'},
                {header : "进气消声器型号", name : 'en_jqxsqxh', width : 250, align : 'center'},
                {header : "排气消声器型号", name : 'en_pqhclxtxs', width : 250, align : 'center'},
                {header : "排气后处理系统型式", name : 'en_pqhclxtxs', width : 250, align : 'center'},
                {header : "排气后处理系统型号", name : 'en_pqhclxtxh', width : 250, align : 'center'},
                {header : "封装/载体/涂层生产厂", name : 'en_tc', width : 250, align : 'center'},
                {header : "创建时间", name : 'create_time', width : 150, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 150, align : 'center'},
                {header : "最后更新时间", name : 'update_time', width : 150, align : 'center'},
                {header : "最后更新人", name : 'updater_id', width :150, align : 'center'}]
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
        var divType = $('#carType').val();
        if(divType=='0'){////公告信息
            buildRightGridForHeavyDiesel();
        }else if(divType=='1'){
            buildRightGridForLightDual();
        }else if(divType=='2'){
            buildRightGridForLightDiesel();
        }else if(divType=='3'){
            buildRightGridForHeavyGas();
        }

    }

    /// 查询函数操作
    function cahangeByQuery()
    {
        var divType = $('#carType').val();
        if(divType=='0'){
            return "${createLink(controller:'heavyDiesel',action:'jsonList')}";
        } else if(divType=='1'){
            return "${createLink(controller:'lightDual',action:'jsonList')}";
        } else if(divType=='2'){
            return "${createLink(controller:'lightDiesel',action:'jsonList')}";
        } else if(divType=='3'){
            return "${createLink(controller:'heavyGas',action:'jsonList')}";
        }

    }
</script>
</body>
</html>
