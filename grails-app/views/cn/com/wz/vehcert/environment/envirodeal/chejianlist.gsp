<%@ page import="cn.com.wz.vehcert.zcinfo.ZCInfo" %>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'zcinfo.label', default: 'zcinfo')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<form id="form_query" style="margin:8px;">
    <div id="page" style="width:98%;background-color:white;">
        <div id="center-panel" style="margin-left:4px;border:1px;">

            <table style="width:100%;border:0px;">
                <tr>
                    <td style="width:  90px;text-align: right"><span>打印清单类型：</span></td>
                    <td width="100"><span>
                        %{--<g:select id="en_type" style="width:150px;" name="en_type" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','enviro')}--}%
                            %{--order('ordernum','asc')})}" optionKey="code" value="" class="one-to-one"  noSelection="['': '']" />--}%
                        <select id="en_type">
                        %{--<option value ="" selected='true'  ></option>--}%
                            <option value ="HeavyDiesel"  selected='true'>重型柴油车</option>
                            %{--<option value ="HeavyPetrol">重型汽油车</option>--}%
                            <option value="HeavyGas">重型燃气车</option>
                            <option value="LightDual">轻型双燃料车</option>
                            <option value="LightPetrol">轻型汽油车</option>
                            <option value="LightDiesel4">轻型柴油车(国四)</option>
                            <option value="LightDiesel">轻型柴油车(国五)</option>
                            <option value="City">城市车辆</option>
                            <option value="NewEnergy">新能源汽车</option>
                            <option value="LightPetrolGb6">轻型汽油车(国六）</option>
                        </select>
                    </span></td>
                    <td style="width: 50px"></td>
                    <td width="80"><span>合格证编号：</span></td>
                    <td width="100"><span><g:textField id="vehCode" name="vehCode"  value="${veh_Zchgzbh}" maxlength="200"/></span></td>
                    <td style="width: 50px"></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

            %{--<fieldset class="buttons" style="margin:0px 8px 8px 4px;">--}%
                %{--<input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>--}%
                %{--<export:formats formats="['excel','pdf']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>--}%
            %{--</fieldset>--}%
            <div style="margin-right:8px;margin-top:8px;">
                <div id="zcinfo_grid" style="border:0px"></div>
            </div>
        </div>
    </div>
</form>
<script>
    // 事件绑定
    $(function() {
        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'envirPrint',action:'jsonChejianList')}";
                url+='?'+$('#form_query').serialize();
                url=encodeURI(url);
                $('#zcinfo_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#vehCode").val('');
                $("#en_type").val('');
            }
        });
        $("#vehCode").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
    })
    // 加载时执行的语句
    $(function() {
        buildRightGrid()
    });
    //用户自定义方法
    //创建有些表格
    function buildRightGrid(){
        $('#zcinfo_grid').omGrid({
            dataSource:'${createLink(controller:'envirPrint',action:'jsonChejianList')}?'+encodeURI($('#form_query').serialize()),
            method:'POST',
            extraData:{list:'${list}'} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 10,
            height :440,
            width:'fit',
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 150, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view"  class="btn_basic blue_black"  href="javascript:goEnviron('+rowIndex+');">打印环保信息随车清单</a>'
                        }},

                {header : "合格证编号", name : 'veh_Zchgzbh', width : 120, align : 'center'},
                {header : "VIN", name : 'veh_Clsbdh', width : 130, align : 'center'},
                {header : "车辆型号", name : 'veh_Clxh', width : 120, align : 'center'},
                {header : "燃料种类", name : 'veh_Rlzl', width : 125, align : 'center'},
                {header : "发动机型号", name : 'veh_Fdjxh', width : 125, align : 'center'},
                {header : "发动机编号", name : 'veh_Fdjh', width :120, align : 'center'}


            ]
        });
    }
    //打印车辆环保信息随车清单
    function goEnviron(index){
        var en_type =    $("#en_type").val();
        if(en_type ==''){
            alert("请选择打印清单类型");
           return false;
        }else{
            //通过在页面上的必选项en_type 这个值，来判断需要向那个页面，那个控制器跳转
            var data1 = $("#zcinfo_grid").omGrid("getData").rows[index];

            if(en_type == 'HeavyDiesel'||en_type == 'City'){    //重型柴油车||市车辆
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchEnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelHeavyDieselPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
                //                     重型柴油车END#################
            }else if(en_type == 'HeavyPetrol'){//重型汽油车
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchHeavyPetrolEnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelHeavyPetrolPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'heavyPetrolPrint')}?heavyPetrolInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
            }else if(en_type == 'HeavyGas'){//重型燃气车
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchHeavyGasEnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelHeavyGasPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'heavyGasPrint')}?heavyGasInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
            }else if(en_type == 'LightDual'||en_type == 'LightPetrol'){//轻型双燃料车或者轻型汽油车
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchLightDualPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelLightDualPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
//                                        alert(textStatus);
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'lightDualPrint')}?lightDualPrintInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
            }else if(en_type == 'LightDiesel4'||en_type == 'LightDiesel'){//轻型柴油车
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchLightDieselEnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDellightDieselPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'lightDieselPrint')}?lightDieselInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }

                },'text');
            }else if(en_type == 'NewEnergy'){//新能源汽车
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchNewEnergyEnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     新能源汽车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelNewEnergyPr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'heavyGasPrint')}?heavyGasInfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
            }else if(en_type == 'LightPetrolGb6'){//重型燃气车
//                alert(en_type);
                var searchCocPrnUrl = '${createLink(controller:'envirPrint',action:'jsonSearchLightPetrolGb6EnvPrn')}?zcinfoID='+data1.id;
                $.post(searchCocPrnUrl,function(data,textStatus){
//                     重型柴油车START#################
                    if(data  ==1){   // 已打印未上传或者上传失败
                        $.omMessageBox.confirm({
                            title:'确认',
                            content:'环保随车清单已经打印,是否替换原来的信息?',
                            onClose:function(value){
                                if(value){
                                    //清除已打印的一致性证书信息
                                    var delCocPrUrl = '${createLink(controller:'envirPrint',action:'jsonDelLightPetrolGb6Pr')}?zcinfoID='+data1.id;
                                    $.post(delCocPrUrl,function(data,textStatus){
                                        if(textStatus=="success"){
                                            var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                                            window.location.href = url;
                                        } else{
                                            alert("清除已打印的一致性证书信息失败！");
                                            return false;
                                        }
                                    },'text');

                                } else{
                                    %{--var url = '${createLink(controller:'envirPrint',action:'heavyDieselPrint')}?heavyDieselInfoId='+heavyDieselInfoId+'&QRpiUrl='+QRpiUrl+'&BarPicUrl='+BarPicUrl+'&zcinfoID=${zcinfoID}&en_type=${en_type}';--}%
                                    var url = '${createLink(controller:'envirPrint',action:'lightPetrolGb6Print')}?lightPetrolGb6InfoId='+data1.id+'&printOld=1';     //不替换一致性证书
                                    window.location.href = url;
                                }
                            }
                        });
                    }else if(data  ==2)   {     //已经上传
                        %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                        %{--window.location.href = url;--}%
                        alert("打印的环保随车清单已经上传至服务器，不能再次打印！")

                    } else{//未打印
                        var url = '${createLink(controller:'envirPrint',action:'findEnvirModelList')}?zcinfoID='+data1.id+'&en_type='+en_type;
                        window.location.href = url;
                    }
                },'text');
            }

        }

    }
</script>
</body>
</html>