<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="非常规合格证生成" />
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

    <div id="page"  class="noprint" style="width:100%;background-color:white;margin:0px 0px">
        <div id="center-panel" style="margin-left:4px;border:1px;">
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <span id='span_publish'>
                    <input id="btn_nextview" type="button" class="preView" value="递增查看"/>
                    <input id="btn_preview" type="button" class="nextView" value="递减查看"/>
                    <input id="btn_query" type="button"  class="edit" value="查看"/>
                    <input id="btn_save" type="button"  class="save" value="保存"/>
                    <input id="btn_print" type="button" value="打印" class="view" />

                </span>
            </fieldset>
            <div style="margin-right:8px;margin-top:8px;">
              <form action="${createLink(controller: "NZInfo",action:"save")}" id="form">
                  <g:hiddenField name="type" id='type' value="${type}"/>
                  <g:hiddenField name="infoid" value="${STInfoInstance.id}"/>
                  <g:hiddenField name="isupload" value="${STInfoInstance.upload }"/>
                  <g:hiddenField name="processType" value="${processType}"/>
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row evenRow">
                                <td align="left" abbr="id" class="col0">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    车辆编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Clbh" id="veh_Clbh" maxlength="50"  value="${STInfoInstance?.veh_Clbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Dph" id="veh_Dph" maxlength="50"  value="${STInfoInstance?.veh_Dph}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    出厂日期
                                                </td>
                                                <td>
                                                    <c:dataPicker name="veh_Ccrq" id="veh_Fzrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${STInfoInstance?.veh_Ccrq}"></c:dataPicker>
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
                                                    <input name="veh_Cx" id="veh_Cx" maxlength="50"  value="${STInfoInstance?.veh_Cx}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机号
                                                </td>
                                                <td>
                                                    <g:set var="nzInfoService" bean="nzInfoService"/>
                                                <g:if test="${(nzInfoService.returnConfig(STInfoInstance?.veh_Fdjh))}">
                                                    <input name="veh_Fdjh" id="veh_Fdjh" maxlength="50" style="color: red" value="${STInfoInstance?.veh_Fdjh}">
                                                </g:if><g:else>
                                                   <input name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${STInfoInstance?.veh_Fdjh}">
                                                </g:else>

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                <g:if test="${(nzInfoService.returnConfig(STInfoInstance?.veh_Hgzbh))}">
                                                    <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50" style="color: red" value="${STInfoInstance?.veh_Hgzbh}">
                                                </g:if><g:else>
                                                    <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50"  value="${STInfoInstance?.veh_Hgzbh}">
                                                </g:else>

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
                        2、默认值配置，请到【农装合格证生产配置】菜单具体配置
                    </p>
                </tr>
            </div>
        </div>
    </div>

    %{--###################################打印显示的页面START#####################################--}%
    <div style="margin-top:85px;display: none" class="print" id='print'>
        <table>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px">
                    %{--车型--}%
                    ${STInfoInstance?.veh_Cx}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px">
                    %{--车辆编号--}%
                    ${STInfoInstance?.veh_Clbh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px">
                    %{--发动机号--}%
                    ${STInfoInstance?.veh_Fdjh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left:55px;font-size:16px">
                    %{--发动机号--}%
                    ${STInfoInstance?.veh_Dph}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left:55px;font-size:16px">
                    %{--空白行--}%
                    &nbsp&nbsp&nbsp &nbsp
                </td>
            </tr>

            <tr>
                <td style="line-height:25px;padding-left:55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px">
                    %{--出场日期--}%
                    ${STInfoInstance?.veh_Ccrq}
                </td>
            </tr>
        </table>
    </div>
    %{--###################################打印显示的页面END#####################################--}%

    <script>
        var arrays=new Array();
        %{--<g:each in="${symboMap.values()}" var="symbol">--}%
            %{--arrays.push('${symbol}')--}%
        %{--</g:each>--}%
        $(function() {

            $('#veh_Cx').omCombo({
                multi : false ,
                dataSource : "${createLink(controller:'NZInfo',action:'jsonCx')}?random="+Math.random(),
                value:'${STInfoInstance?.veh_Cx?STInfoInstance?.veh_Cx:'WZ260'}'

            });

            $('#btn_save').click(function(){ //验证车间号码
                var isupload=  $('#isupload').val();
                if(isupload=='1'){
                    alert('合格证已上传,不允许修改')
                    return;
                }
                var veh_Cx= $('#veh_Cx').val();
//                alert(veh_Cx);
                if(veh_Cx==null||$.trim(veh_Cx)==''){
                    alert('保存前,请输入车型')
                    return;
                }

                var veh_Fdjh=$("#veh_Fdjh").val();
//                alert(veh_Fdjh);
                if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                   alert('保存前,发动机号不能为空')
                    return;
                }
                var veh_Hgzbh= $('#veh_Hgzbh').val();
                if(veh_Hgzbh==null||$.trim(veh_Hgzbh)==''){
                    alert('保存前,请输入合格证编号')
                    return;
                }

                var veh_Dph=$("#veh_Dph").val()
                if(veh_Dph==null|| $.trim(veh_Dph)==''){
                    alert('保存前,底盘号不能为空')
                    return;
                }
                if(veh_Dph.length!=12){
                    alert('底盘号不符合要求')
                    return;
                }

                var veh_Clbh=$("#veh_Clbh").val()
                if(veh_Clbh==null|| $.trim(veh_Clbh)==''){
                    alert('保存前,车辆编号不能为空')
                    return;
                }
                if(veh_Clbh.length!=12){
                    alert('车辆编号不符合要求')
                    return;
                }
                document.forms[0].submit();
            });
            $('#btn_query').click(function(){
                var url = '${createLink(controller:'NZInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var  type=$('#type').val();
                var  processType=$('#processType').val();
                if(veh_Clbh.length==12){
                    window.location.href = url+'?veh_Clbh='+veh_Clbh+'&type='+type+'&processType='+processType;
                }
            });
            $('#recover').click(function(){
                var url = '${createLink(controller:'NZInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var type=$('#type').val();
                var  processType=$('#processType').val();
                window.location.href = url+'?recover=0&veh_Clbh='+veh_Clbh+'&type='+type+'&processType='+processType;
            });
            $('#btn_calculate').click(function(){
                calculateVin();
            });
            $('#btn_nextview').click(function(){
                var url = '${createLink(controller:'NZInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var  type=$('#type').val();
                var  processType=$('#processType').val();
                if(veh_Clbh.length==12){
                    window.location.href = url+'?direction=1&veh_Clbh='+veh_Clbh+'&type='+type+'&processType='+processType;
                }
            });
            $('#btn_preview').click(function(){
                var url = '${createLink(controller:'NZInfo',action:'search')}';
                var  veh_Clbh=$('#veh_Clbh').val();
                var  type=$('#type').val();
                var  processType=$('#processType').val();
                if(veh_Clbh.length==12){
                    window.location.href = url+'?direction=-1&veh_Clbh='+veh_Clbh+'&type='+type+'&processType='+processType;
                }
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
            var  processType=$('#processType').val();
            var url = '${createLink(controller:'NZInfo',action:'print')}?isprint=1';
            var dialogId=showTipDialog();
            $.post(url,params,function(data,textStatus){
                parent.closeDialog(dialogId);
                var d=eval("("+data+")");
                if(d.flag=='successed'){
                    $.omMessageBox.alert({
                        content: d.msg,
                        onClose:function(v){
                            var url = '${createLink(controller:'NZInfo',action:'search')}';
                            if(veh_Clbh.length>0){
                                window.location.href = url+'?type='+type+'&intI=1&veh_Clbh='+veh_Clbh+'&processType='+processType;
                            }
                        }
                    });
                }else{
                    alert(d.msg);
                }
            },'text');
        }
    </script>
    </body>
</html>
