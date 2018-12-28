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
                    <input id="btn_save" type="button"  class="save" value="保存"/>
                    <input id="btn_print" type="button" value="打印" class="edit" />
                    <input id="btn_return" type="button" value="返回" class="edit" />
                </span>
            </fieldset>
            <div style="margin-right:8px;margin-top:8px;">
              <form action="${createLink(controller: "NZzcinfoReplaceAuth",action:"printSave")}" id="form">
                  <g:hiddenField name="type" id='type' value="${type}"/>
                  %{--更换后和合格证id--}%
                  <g:hiddenField name="newNzZcinfoId" id='newNzZcinfoId' value="${newNzZcinfo.id}"/>
                  %{--更换记录的id--}%
                  <g:hiddenField name="replaceNzZcinfoId" id='replaceNzZcinfoId' value="${replaceNzZcinfoId}"/>
                  <g:hiddenField name="isPrintSave" id='isPrintSave' value="${isPrintSave}"/>
                  <g:hiddenField  id='Hgzbh' name='Hgzbh' value="${newNzZcinfo?.veh_Hgzbh}"/>

                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%" cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row evenRow">
                                <g:set var="nzInfoService" bean="nzInfoService"/>

                                <td align="left" abbr="id" class="co10">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td >
                                                    车辆编号
                                                </td>
                                                <td id='veh_Clbh' name='veh_Clbh'>
                                                    ${newNzZcinfo?.veh_Clbh}
                                                    %{--<g:textField name="veh_Clbh" id="veh_Clbh" maxlength="50"  value="${newNzZcinfo?.veh_Clbh}"/>--}%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    底盘号
                                                </td>
                                                <td id='veh_Dph' name='veh_Dph'>
                                                    ${newNzZcinfo?.veh_Dph}
                                                    %{--<g:textField name="veh_Dph" id="veh_Dph" maxlength="50"  value="${newNzZcinfo?.veh_Dph}"/>--}%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    出厂日期
                                                </td>

                                                <td id='veh_Ccrq' name='veh_Ccrq'>
                                                    %{--<c:dataPicker name="veh_Ccrq" id="veh_Fzrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${newNzZcinfo?.veh_Ccrq}"></c:dataPicker>--}%
                                                    ${newNzZcinfo?.veh_Ccrq}
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
                                                <td id='veh_Cx' name='veh_Cx'>
                                                    ${newNzZcinfo?.veh_Cx}
                                                    %{--<input name="veh_Cx" id="veh_Cx" maxlength="50"  value="${newNzZcinfo?.veh_Cx}">--}%
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机号
                                                </td>
                                                <td id='veh_Fdjh' name='veh_Fdjh'>
                                                    ${newNzZcinfo?.veh_Fdjh}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                    <g:if test="${newNzZcinfo?.veh_Hgzbh==''||newNzZcinfo?.veh_Hgzbh==null}">
                                                        <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50" style="color: red" value="${cn.com.wz.parent.system.dictionary.DictionaryValue.findByCode('hgzbh_config').value1}">
                                                    </g:if><g:else>
                                                    <input name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50"  value="${newNzZcinfo?.veh_Hgzbh}">
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
      %{--###################################打印显示的页面START#####################################--}%
    <div style="margin-top:85px;display: none" class="print" id='print'>
        <table>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px;font-family:Microsoft Yahei">
                    %{--车型--}%
                    ${newNzZcinfo?.veh_Cx}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px;font-weight:bold">
                    %{--车辆编号--}%
                    ${newNzZcinfo?.veh_Clbh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px;font-weight:bold">
                    %{--发动机号--}%
                    ${newNzZcinfo?.veh_Fdjh}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left:55px;font-size:16px;font-weight:bold">
                    %{--发动机号--}%
                    ${newNzZcinfo?.veh_Dph}
                </td>
            </tr>
            <tr>
                <td style="line-height: 25px;padding-left: 55px"></td>
                <td style="line-height: 25px;padding-left:55px;font-size:16px;font-weight:bold">
                    %{--空白行--}%
                    &nbsp&nbsp&nbsp &nbsp
                </td>
            </tr>

            <tr>
                <td style="line-height:25px;padding-left:55px"></td>
                <td style="line-height: 25px;padding-left: 55px;font-size:16px;font-family:Microsoft Yahei">
                    %{--出场日期--}%
                    ${newNzZcinfo?.veh_Ccrq}
                </td>
            </tr>
        </table>
    </div>
    %{--###################################打印显示的页面END#####################################--}%

    <script>
        var arrays=new Array();
        $(function() {

            $('#btn_save').click(function(){
                var veh_Hgzbh= $('#veh_Hgzbh').val();
                if(veh_Hgzbh==null||$.trim(veh_Hgzbh)==''){
                    alert('保存前,请输入合格证编号')
                    return;
                }

                var veh_Cx= $('#veh_Cx').html();
                if(veh_Cx==null||$.trim(veh_Cx)==''){
                    alert('车型不能为空')
                    return;
                }
                var veh_Fdjh=$("#veh_Fdjh").html();
                if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                   alert('发动机号不能为空')
                    return;
                }

                var veh_Dph=$("#veh_Dph").html()
                if(veh_Dph==null|| $.trim(veh_Dph)==''){
                    alert('底盘号不能为空')
                    return;
                }


                var veh_Clbh=$("#veh_Clbh").html()
                if(veh_Clbh==null|| $.trim(veh_Clbh)==''){
                    alert('车辆编号不能为空')
                    return;
                }
                var veh_Ccrq=$("#veh_Ccrq").html()
                if(veh_Ccrq==null|| $.trim(veh_Ccrq)==''){
                    alert('出厂日期不能为空')
                    return;
                }

                document.forms[0].submit();
            });
            $('#btn_print').click(function(){
                var d=document.getElementById('print');
                d.style.display ="block";
                var Hgzbh=$('#Hgzbh').val();
                if(Hgzbh){
                    window.print();
                    var d=document.getElementById('print');
                    d.style.display ="none";
                    printAll();
                }else{
                    alert('请先保存合格证编号')
                }
            });

            $('#btn_return').click(function(){
                var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'changPrintList')}';
                window.location.href = url+'?searchType=2&isPrint=0';
            });
        });

        function printAll(){
            var params=$("#form").serialize();
            var  veh_Clbh=$('#veh_Clbh').html();
            var   veh_Clbh=$.trim(veh_Clbh);
            var type=$('#type').val();
            var url = '${createLink(controller:'NZInfo',action:'print')}?isprint=1&veh_Clbh='+veh_Clbh;
            var dialogId=showTipDialog();
            $.post(url,params,function(data,textStatus){
                parent.closeDialog(dialogId);
                var d=eval("("+data+")");
                if(d.flag=='successed'){
                    $.omMessageBox.alert({
                        content: d.msg,
                        onClose:function(v){
                            var url = '${createLink(controller:'NZzcinfoReplaceAuth',action:'changPrintList')}';
                            if(veh_Clbh.length>0){
                                window.location.href = url+'?searchType=2&isPrint=0';
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
