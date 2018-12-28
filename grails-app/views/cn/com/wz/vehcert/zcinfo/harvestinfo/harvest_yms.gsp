<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="常规汽车合格证生成" />
        <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <style>
            @media print{
                .noprint{
                    display:none
                }
            }
        </style>
    </head>
	<body>

    <div id="page" class="noprint" style="width:100%;background-color:white;margin:0px 0px">
        <div id="center-panel" style="margin-left:4px;border:1px;">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <span id='span_publish'>
                    <input id="btn_nextview" type="button" class="preView" value="递增查看"/>
                    <input id="btn_preview" type="button" class="nextView" value="递减查看"/>
                    <input id="btn_query" type="button"  class="edit" value="查看"/>
                    <input id="recover" type="button"  class="edit" style="width:100px; height: 30px;" value="恢复编号"/>
                    <input id="btn_save" type="button"  class="save" value="保存"/>
                    <input id="btn_print" type="button" value="打印" class="view" />
                </span>
            </fieldset>
            <div style="margin-right:8px;margin-top:8px;">
              <form action="${createLink(controller: "HarvestInfo",action:"save")}" id="form">
                  <g:hiddenField name="type" id='type' value="${type}"/>
                  <g:hiddenField name="infoid" value="${HarvestInfoInstance.id}"/>
                  <g:hiddenField name="isupload" value="${HarvestInfoInstance.upload }"/>
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row oddRow">
                                <td align="left" abbr="id" class="col0"><div align="middle" class="innerCol " style="width:200px">
                                    <input name="getValue" id="btn_select" style="height:50px;background-color:cyan"  value="按公告号提取数据" type="button" />
                                </div></td>
                                <td align="left" abbr="address" class="col2"><div align="middle" class="innerCol " style="width:200px">
                                    <input id="btn_calculate" type="button"  class="edit" style="width:100px; height: 50px;background-color:cyan" value="计算环保信息代码"/></div>
                                </td>
                                <td align="left" abbr="id" class="col0"><div align="left" class="innerCol " style="width:200px">
                                </div></td>
                            </tr>
                            <tr class="om-grid-row evenRow">
                                <g:set var="harvestInfoService" bean="harvestInfoService"/>

                                <td align="left" abbr="id" class="co10">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    车辆编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Clbh" id="veh_Clbh" maxlength="50"  value="${HarvestInfoInstance?.veh_Clbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    新车辆编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_new_clbh" id="veh_new_clbh" maxlength="50"  value="${HarvestInfoInstance?.veh_new_clbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Dph" id="veh_Dph" maxlength="50"  value="${HarvestInfoInstance?.veh_Dph}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机型号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50"  value="${HarvestInfoInstance?.veh_Fdjxh}"/>
                                                </td>
                                            </tr>

                                            %{--<g:if test="${lsh}">--}%
                                            %{--<tr>--}%
                                            %{--<td>--}%
                                            %{--流水号--}%
                                            %{--</td>--}%
                                            %{--<td>--}%
                                            %{--${lsh}--}%
                                            %{--</td>--}%
                                            %{--</tr>--}%
                                            %{--</g:if>--}%
                                            <tr>
                                                <td>
                                                    机械环保代码
                                                </td>
                                                <td>
                                                    <g:textField name="envirCode" id="envirCode" maxlength="50"  value="${HarvestInfoInstance?.envirCode}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    燃料类型
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Rllx" id="veh_Rllx" maxlength="50"  value="${HarvestInfoInstance?.veh_Rllx}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械产品的主要参数
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Zycs" id="veh_Zycs" maxlength="50"  value="${HarvestInfoInstance?.veh_Zycs}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    出厂日期
                                                </td>
                                                <td>
                                                    <c:dataPicker name="veh_Ccrq" id="veh_Fzrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${HarvestInfoInstance?.veh_Ccrq}"></c:dataPicker>

                                                </td>
                                            </tr>

                                        </table>
                                    </div>
                                </td>
                                <td align="left" abbr="" class="indexCol">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                     车型
                                                </td>
                                                <td>
                                                    %{--<g:textField name="veh_Csys" id="veh_Csys" maxlength="50"  value="${zcinfoInstance?.veh_Csys}"/>--}%
                                                    <input name="veh_Cx" id="veh_Cx" maxlength="50"  value="${HarvestInfoInstance?.veh_Cx}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机号
                                                </td>
                                                <td>
                                                    <g:if test="${(harvestInfoService.returnConfig(HarvestInfoInstance?.veh_Fdjh))}">
                                                        <input name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  style="color: red" value="${HarvestInfoInstance?.veh_Fdjh}">
                                                    </g:if><g:else>
                                                        <input name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${HarvestInfoInstance?.veh_Fdjh}">
                                                    </g:else>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                    <g:if test="${(harvestInfoService.returnConfig(HarvestInfoInstance?.veh_Hgzbh))}">
                                                        <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50" style="color: red"  value="${HarvestInfoInstance?.veh_Hgzbh}">
                                                    </g:if><g:else>
                                                        <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50"  value="${HarvestInfoInstance?.veh_Hgzbh}">
                                                    </g:else>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械大类
                                                </td>
                                                <td>
                                                    <input name="veh_Jxdl" id="veh_Jxdl" maxlength="50"  value="${HarvestInfoInstance?.veh_Jxdl}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械小类别
                                                </td>
                                                <td>
                                                    <input name="veh_Jxxlb" id="veh_Jxxlb" maxlength="50"  value="${HarvestInfoInstance?.veh_Jxxlb}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    排放阶段
                                                </td>
                                                <td>
                                                    <input name="veh_Pfjd" id="veh_Pfjd" maxlength="50"  value="${HarvestInfoInstance?.veh_Pfjd}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    产品类型
                                                </td>
                                                <td>
                                                    <g:select style="width:143px;" name="car_storage_type" from="${['0':'玉米收','1':'稻麦机']}"  value="${HarvestInfoInstance?.car_storage_type}" optionKey="key" optionValue="value"></g:select>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
              </form>
            </div>
            <div>
                <tr>
                    <p style="color: red">
                        注意：
                    </p>
                    <p style="color: red" >
                        1、如果发动机号或者合格证编号为红色字体，说明该值是配置默认值,请注意完善该值。
                    </p>
                    <p style="color: red" >
                        2、默认值配置，请到【收获车间合格证配置】菜单具体配置
                    </p>
                </tr>
            </div>
        </div>
    </div>
      %{--###################################打印显示的页面START#####################################--}%
      %{--###################################打印显示的页面START#####################################--}%
    <div style="margin-top:85px;display: none" class="print" id='print'>
        <table>
            <tr>
                <td style="line-height: 20px;padding-left: 45px"></td>
                <td style="line-height: 20px;padding-left: 60px;font-size:18px;font-family:SimHei">
                    %{--车型--}%
                    ${HarvestInfoInstance?.veh_Cx}
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;padding-left: 45px"></td>
                <td style="line-height: 20px;padding-left: 60px;font-size:18px;font-family:SimHei">
                    %{--车辆编号--}%
                    ${HarvestInfoInstance?.veh_new_clbh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;padding-left:45px"></td>
                <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                    %{--发动机型号--}%
                    ${HarvestInfoInstance?.veh_Fdjxh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;padding-left:45px"></td>
                <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                    %{--发动机号--}%
                    ${HarvestInfoInstance?.veh_Fdjh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;padding-left:45px"></td>
                <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                    %{--底盘号--}%
                    ${HarvestInfoInstance?.veh_Dph}
                </td>
            </tr>
            <tr>
                <td style="line-height:19px;padding-left:45px"></td>
                <td style="line-height: 19px;padding-left: 60px;font-size:18px;font-family:SimHei">
                    %{--出场日期--}%
                    ${HarvestInfoInstance?.veh_Ccrq}
                </td>
            </tr>
            <tr>
                <td style="line-height:19px;padding-left:45px"></td>
                <td style="line-height: 19px;padding-left: 60px;font-size:18px;font-family:SimHei">
                    <g:if test="${HarvestInfoInstance?.car_storage_type=='0'}">
                        GB/T 21962-2008
                    </g:if>
                    <g:elseif test="${HarvestInfoInstance?.car_storage_type=='1'}">
                        JB/T 5117-2017
                    </g:elseif>
                    %{--执行标准--}%
                </td>
            </tr>
            <tr>
                <td style="line-height:18px;padding-left:45px"></td>
                <td style="line-height: 18px;padding-left: 60px;font-size:18px;font-family:SimHei">
                    %{--机械环保代码--}%
                    ${HarvestInfoInstance?.envirCode}
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;padding-left: 45px"></td>
                <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                    %{--空白行--}%
                    &nbsp&nbsp&nbsp &nbsp
                </td>
            </tr>
            <tr>
                <td style="line-height: 23px;padding-left: 45px"></td>
                <td style="line-height: 23px;padding-left:60px;font-size:18px;font-family:SimHei">
                    %{--空白行--}%
                    &nbsp&nbsp&nbsp &nbsp
                </td>
            </tr>
            <tr>
                <td style="line-height:25px;padding-left:45px"></td>
                <td style="line-height:25px;padding-left: 90px;font-size:18px;font-family:SimHei">
                    %{--合格证编号--}%
                    ${HarvestInfoInstance?.veh_Hgzbh}
                </td>
            </tr>
        </table>
    </div>
    %{--###################################打印显示的页面END#####################################--}%

    <script>
        var arrays=new Array();
        $(function() {

            %{--$('#veh_Cx').omCombo({--}%
                %{--multi : false ,--}%
                %{--dataSource : "${createLink(controller:'harvestInfo',action:'jsonCx')}?random="+Math.random(),--}%
                %{--value:'${HarvestInfoInstance?.veh_Cx?HarvestInfoInstance?.veh_Cx:'WZ260'}'--}%

            %{--});--}%
            $('#btn_select').click(function(){
                var car_storage_type=$('#car_storage_type').val();
                var url = '${createLink(controller:'harvestInfo',action:'toNotice')}?car_storage_type='+car_storage_type;
                create(url,'公告信息',1440,900);
            });
            $('#btn_save').click(function(){ //验证车间号码
                var isupload=  $('#isupload').val();
                if(isupload=='1'){
                    alert('合格证已上传,不允许修改')
                    return;
                }
                var veh_Cx= $('#veh_Cx').val();
                var type=$('#type').val();
                if(veh_Cx==null||$.trim(veh_Cx)==''){
                    alert('保存前,请输入车型')
                    return;
                }

                var veh_Hgzbh= $('#veh_Hgzbh').val();
                if(veh_Hgzbh==null||$.trim(veh_Hgzbh)==''){
                    alert('保存前,请输入合格证编号')
                    return;
                }
                var veh_Fdjh=$("#veh_Fdjh").val();
                if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                   alert('保存前,发动机号不能为空')
                    return;
                }

                var veh_Dph=$("#veh_Dph").val();
                if(veh_Dph==null|| $.trim(veh_Dph)==''){
                    alert('保存前,底盘号不能为空')
                    return;
                }
                if(type=='harvester_D'||type=='harvester_E'){
                    if(veh_Dph.length!=17){
                        alert('底盘号不符合要求')
                        return;
                    }
                }

                var veh_Clbh=$("#veh_Clbh").val()
                if(veh_Clbh==null|| $.trim(veh_Clbh)==''){
                    alert('保存前,车辆编号不能为空')
                    return;
                }
                if(type=='harvester_D'||type=='harvester_E'){
                    if(veh_Clbh.length!=12&&veh_Clbh.substring(0,1)!='3'){
                        alert('车辆编号不符合要求')
                        return;
                    }
                }
                var veh_new_clbh=$("#veh_new_clbh").val()
                if(veh_new_clbh==null|| $.trim(veh_new_clbh)==''){
                    alert('保存前,新车辆编号不能为空')
                    return;
                }
                if(type=='harvester_D'||type=='harvester_E'){
                    if(veh_new_clbh.length!=17){
                        alert('新车辆编号不符合要求')
                        return;
                    }
                }
                document.forms[0].submit();
            });
            $('#btn_query').click(function(){
                var url = '${createLink(controller:'harvestInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var type=$('#type').val();
                    if(type=='harvester_D'){
                        if(veh_Clbh.length==11){
                            window.location.href = url+'?veh_Clbh='+veh_Clbh+'&type='+type;
                        }else{
                            alert('车辆编号不符合要求')
                        }
                    }

            });
            $('#recover').click(function(){
                var url = '${createLink(controller:'harvestInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var type=$('#type').val();
                    window.location.href = url+'?recover=0&veh_Clbh='+veh_Clbh+'&type='+type;
            });
            $('#btn_nextview').click(function(){
                var url = '${createLink(controller:'harvestInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var type=$('#type').val();
                if(type=='harvester_D'){
                    if(veh_Clbh.length==11){
                        window.location.href = url+'?direction=1&veh_Clbh='+veh_Clbh+'&type='+type;
                    }else{
                        alert('车辆编号不符合要求');
                        return;
                    }
                }
            });
            $('#btn_preview').click(function(){
                var url = '${createLink(controller:'harvestInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var type=$('#type').val();
                if(type=='harvester_D'){
                    if(veh_Clbh.length==11){
                        window.location.href = url+'?direction=-1&veh_Clbh='+veh_Clbh+'&type='+type;
                    }else{
                        alert('车辆编号不符合要求');
                        return;
                    }
                }
            });
            $('#btn_calculate').click(function(){
                calculateVin();
            });
            $('#btn_print').click(function(){
                var isupload=  $('#isupload').val();
                var d=document.getElementById('print');
                d.style.display ="block";
                if(isupload=='1'){
                    alert('合格证已上传,不允许打印')
                    return;
                }
                var infoid=$('#infoid').val();
                if(infoid!=null&&infoid!=''){  //先调用虚拟 再调用正式
                    window.print();
                    var d=document.getElementById('print');
                    d.style.display ="none";
                    printAll();
                }else{
                    alert('请先保存')
                }
            });
        });

        function printAll(){
            var params=$("#form").serialize();
            var  veh_Clbh=$('#veh_Clbh').val();
            var type=$('#type').val();
            var url = '${createLink(controller:'harvestInfo',action:'print')}?isprint=1';
            var dialogId=showTipDialog();
            $.post(url,params,function(data,textStatus){
                parent.closeDialog(dialogId);
                var d=eval("("+data+")");
                if(d.flag=='successed'){
                    $.omMessageBox.alert({
                        content: d.msg,
                        onClose:function(v){
                            var url = '${createLink(controller:'harvestInfo',action:'search')}';
                            if(veh_Clbh.length>0){
                                window.location.href = url+'?type='+type+'&intI=1&veh_Clbh='+veh_Clbh;
                            }
                        }
                    });
                }else{
                    alert(d.msg);
                }
            },'text');
        }
        function changeData(data){
            $('#veh_Cx').val(data.veh_Clxh)
            $('#veh_Rllx').val(data.veh_Rllx)
            $('#veh_Jxdl').val(data.veh_Jxdl)
            $('#veh_Jxxlb').val(data.veh_Jxxlb)
            $('#veh_Zycs').val(data.veh_Zycs)
            $('#veh_Pfjd').val(data.veh_Pfjd)
            $('#car_storage_type').val(data.car_storage_type)
        }
        function calculateVin(){
            var url = '${createLink(controller:'harvestInfo',action:'calculateVIN')}';
            var veh_Rllx=$('#veh_Rllx').val();
            var veh_Jxdl=$('#veh_Jxdl').val();
            var veh_Jxxlb=$('#veh_Jxxlb').val();
            var veh_Zycs=$('#veh_Zycs').val();
            var veh_Pfjd=$('#veh_Pfjd').val();
            var veh_Clbh=$('#veh_Clbh').val();
            var veh_Fzrq=$('#veh_Fzrq').val();
            var car_storage_type=$('#car_storage_type').val();
            var type=$('#type').val();
            $.post(url,{'veh_Rllx':veh_Rllx,'veh_Jxdl':veh_Jxdl,'veh_Jxxlb':veh_Jxxlb,'veh_Zycs':veh_Zycs,'veh_Pfjd':veh_Pfjd,'veh_Clbh':veh_Clbh,'car_storage_type':car_storage_type,'veh_Fzrq':veh_Fzrq,'type':type},function(data){
                if(data.flag=='0'){
                    alert(data.msg);
                }else if(data.flag=='1'){
                    $('#envirCode').val(data.envirCode);
                    $('#veh_new_clbh').val(data.envirCode);
                    $('#veh_Dph').val(data.envirCode);
                    $('#veh_Hgzbh').val(data.envirCode);
                }
            },'json');
        }
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
    </script>
    </body>
</html>
