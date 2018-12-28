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
            <fieldset class="buttons" style="margin:4px 8px 8px 4px;">
            <span id='span_publish'>
                <input id="btn_save" type="button"  class="save" value="保存"/>
                <input id="btn_return" type="button"  class="delete" value="返回"/>
            </span>
        </fieldset>
            <div style="margin-right:8px;margin-top:8px;">
              <form action="${createLink(controller: "NZInfo",action:"saveWrite")}" id="form">
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <input type="hidden" name='opFlag' value="${opFlag}" id="opFlag">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row evenRow">
                                <g:set var="nzInfoService" bean="nzInfoService"/>

                                <td align="left" abbr="id" class="co10">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    车辆编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Clbh" id="veh_Clbh" maxlength="50"  value="${NZInfoInstance?.veh_Clbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Dph" id="veh_Dph" maxlength="50"  value="${NZInfoInstance?.veh_Dph}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    出厂日期
                                                </td>

                                                <td>
                                                    <c:dataPicker name="veh_Ccrq" id="veh_Fzrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${NZInfoInstance?.veh_Ccrq}"></c:dataPicker>

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
                                                    <input name="veh_Cx" id="veh_Cx" maxlength="50"  value="${NZInfoInstance?.veh_Cx}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机号
                                                </td>
                                                <td>
                                                        <input name="veh_Fdjh" id="veh_Fdjh" maxlength="50"  value="${NZInfoInstance?.veh_Fdjh}">

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                        <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50"  value="${NZInfoInstance?.veh_Hgzbh}">

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
                %{--<tr>--}%
                    %{--<p style="color: red">--}%
                        %{--注意：--}%
                    %{--</p>--}%
                    %{--<p style="color: red" >--}%
                        %{--1、如果发动机号或者合格证编号为红色字体，说明该值是配置默认值,请注意完善该值。--}%
                    %{--</p>--}%
                    %{--<p style="color: red" >--}%
                        %{--2、默认值配置，请到【农装合格证生产配置】菜单具体配置--}%
                    %{--</p>--}%
                %{--</tr>--}%
            </div>
        </div>
    </div>

    <script>
        var arrays=new Array();
        $(function() {
            if("${returnMessage}" !=""){
                alert("${returnMessage}");
            }
            $('#veh_Cx').omCombo({
                multi : false ,
                dataSource : "${createLink(controller:'NZInfo',action:'jsonCx')}?random="+Math.random(),
                value:'${NZInfoInstance?.veh_Cx?NZInfoInstance?.veh_Cx:'WZ260'}'

            });
            $('#veh_Fzrq').val('');

            $('#btn_save').click(function(){ //验证车间号码
                var isupload=  $('#isupload').val();
                var veh_Cx= $('#veh_Cx').val();


                var veh_Clbh=$("#veh_Clbh").val()
                if(veh_Clbh==null|| $.trim(veh_Clbh)==''){
                    alert('保存前,车辆编号不能为空')
                    return;
                }
                var veh_Dph=$("#veh_Dph").val()
                if(veh_Dph==null|| $.trim(veh_Dph)==''){
                    alert('保存前,底盘号不能为空')
                    return;
                }

                var veh_Fzrq=$("#veh_Fzrq").val()
                if(veh_Fzrq==null|| $.trim(veh_Fzrq)==''){
                    alert('保存前,出厂日期不能为空')
                    return;
                }

                if(veh_Cx==null||$.trim(veh_Cx)==''){
                    alert('保存前,请输入车型')
                    return;
                }

                var veh_Fdjh=$("#veh_Fdjh").val();
                if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                    alert('保存前,发动机号不能为空')
                    return;
                }
                var veh_Hgzbh= $('#veh_Hgzbh').val();
                if(veh_Hgzbh==null||$.trim(veh_Hgzbh)==''){
                    alert('保存前,请输入合格证编号')
                    return;
                }



                document.forms[0].submit();
            });
            $('#btn_return').click(function(){
                var opFlag=$('#opFlag').val();
                var url = '${createLink(controller:'NZInfo',action:'nzInfoFormalChange')}';
                window.location.href = url+'?opFlag='+opFlag;
            });
        });

    </script>
    </body>
</html>
