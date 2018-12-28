<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="常规山拖合格证生成" />
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
                <input id="recover" type="button"  class="edit" style="width:100px; height: 30px;" value="恢复编号"/>
                <input id="btn_query" type="button"  class="edit" value="查看"/>
                <input id="btn_save" type="button"  class="save" value="保存"/>
                <input id="btn_print" type="button" value="打印" class="view" />

            </span>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <form action="${createLink(controller: "STInfo",action:"save")}" id="form">
                <g:hiddenField name="type" id='type' value="${type}"/>
                <g:hiddenField name="infoid" value="${STInfoInstance.id}"/>
                <g:hiddenField name="isupload" value="${STInfoInstance.upload }"/>
                <g:hiddenField  id='processType' name="processType" value="${processType}"/>
                <div class="om-grid om-widget om-widget-content" style="height: 100%;">
                    <div class="bDiv" style="width: auto; height:100%">
                        <table id="grid" style="width: 80%"  cellpadding="0" cellspacing="0" border="0">
                            <tbody>
                            <tr class="om-grid-row oddRow">
                                <td align="left" abbr="id" class="col0"><div align="middle" class="innerCol " style="width:180px">
                                    <input name="getValue" id="btn_select" style="width:100px;height:50px;background-color:cyan"  value="按公告号提取数据" type="button" />
                                </div></td>
                                <td align="left" abbr="address" class="col2"><div align="middle" class="innerCol " style="width:200px">
                                    <input id="btn_calculate" type="button"  class="edit" style="width:100px; height: 50px;background-color:cyan" value="计算环保信息代码"/></div>
                                </td>
                            </tr>
                            <tr class="om-grid-row evenRow">
                                <td align="left" abbr="id" class="col0">
                                    <div align="left" class="innerCol ">
                                        <table>
                                            <tr>
                                                <td>
                                                    生产编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Scbh" id="veh_Scbh" maxlength="50"  value="${STInfoInstance?.veh_Scbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    合格证编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Hgzbh" id="veh_Hgzbh" maxlength="50"  value="${STInfoInstance?.veh_Hgzbh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    执行标准
                                                </td>
                                                <td>
                                                    <span>
                                                        <g:select id="veh_Zxbz"  style="width: 160px" name="veh_Zxbz" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','ZXBZ')}
                                                            order('ordernum','asc')})}" optionKey="code"  optionValue="value1" value="${STInfoInstance?.veh_Zxbz}"  class="one-to-one"  noSelection="['': '']" />
                                                    </span>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    结构区别代码
                                                </td>
                                                <td>
                                                    <input name="veh_Jgqbdm" id="veh_Jgqbdm" maxlength="50" style="color: red" value="${STInfoInstance?.veh_Jgqbdm}">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    发动机编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Fdjh" id="veh_Fdjh" style="color: red" maxlength="50"  value="${STInfoInstance?.veh_Fdjh}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械环保代码
                                                </td>
                                                <td>
                                                    <g:textField name="envirCode" id="envirCode" maxlength="50"  value="${STInfoInstance?.envirCode}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    燃料类型
                                                </td>
                                                <td>
                                                    <g:select id="veh_Rllx" optionKey="queryBy" optionValue="queryShow" name="modle_type"  value="${STInfoInstance?.veh_Rllx}"
                                                              from="${[[queryBy:'1',queryShow:'柴油'],[queryBy:'2',queryShow:'燃气'],[queryBy:'3',queryShow:'汽油'],[queryBy:'4',queryShow:'其它']]}" noSelection="['':'请选择']" style="width:160px;">
                                                    </g:select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械产品的主要参数
                                                </td>
                                                <td>
                                                    <g:textField name="veh_Zycs" id="veh_Zycs" maxlength="50"  value="${STInfoInstance?.veh_Zycs}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    类型
                                                </td>
                                                <td>
                                                    <g:select id="modle_type" optionKey="queryBy" optionValue="queryShow" name="modle_type"  value="${STInfoInstance?.modle_type}"
                                                              from="${[[queryBy:'0',queryShow:'山拖'],[queryBy:'1',queryShow:'农装']]}" noSelection="['':'请选择']" style="width:160px;">
                                                    </g:select>
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
                                                    新生产编号
                                                </td>
                                                <td>
                                                    <g:textField name="veh_new_clbh" id="veh_new_clbh" maxlength="50"  value="${STInfoInstance?.veh_new_clbh}"/>
                                                </td>
                                            </tr>
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
                                                    发动机型号
                                                </td>
                                                <td>
                                                    <input name="veh_Fdjxh" id="veh_Fdjxh" maxlength="50" style="color: red" value="${STInfoInstance?.veh_Fdjxh}">
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    检验员
                                                </td>
                                                <td>
                                                    <input name="veh_Jcy" id="veh_Jcy" maxlength="50" style="color: red" value="${STInfoInstance?.veh_Jcy}">

                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械大类
                                                </td>
                                                <td>
                                                    <g:select id="veh_Jxdl" optionKey="queryBy" optionValue="queryShow" name="veh_Jxdl"  value="${STInfoInstance?.veh_Jxdl}"
                                                              from="${[[queryBy:'1',queryShow:'工程机械'],[queryBy:'2',queryShow:'农业机械'],[queryBy:'3',queryShow:'林业机械'],[queryBy:'4',queryShow:'渔业机械'],[queryBy:'5',queryShow:'矿山机械'],[queryBy:'6',queryShow:'发电机'],[queryBy:'7',queryShow:'其它']]}" noSelection="['':'请选择']" style="width:160px;">
                                                    </g:select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    机械小类别
                                                </td>
                                                <td>
                                                    <g:select id="veh_Jxxlb" optionKey="queryBy" optionValue="queryShow" name="veh_Jxxlb"  value="${STInfoInstance?.veh_Jxxlb}"
                                                              from="${[[queryBy:'T',queryShow:'拖拉机'],[queryBy:'Y',queryShow:'玉米收获机械'],[queryBy:'D',queryShow:'谷物收获机械'],[queryBy:'Z',queryShow:'作业机械'],[queryBy:'Q',queryShow:'饲料作物收获机械'],[queryBy:'O',queryShow:'其它机械']]}" noSelection="['':'请选择']" style="width:160px;">
                                                    </g:select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    排放阶段
                                                </td>
                                                <td>
                                                    <g:select id="veh_Pfjd" optionKey="queryBy" optionValue="queryShow" name="veh_Pfjd"  value="${STInfoInstance?.veh_Pfjd}"
                                                              from="${[[queryBy:'2',queryShow:'国二'],[queryBy:'3',queryShow:'国三'],[queryBy:'4',queryShow:'国四'],[queryBy:'5',queryShow:'国五'],[queryBy:'6',queryShow:'国六']]}" noSelection="['':'请选择']" style="width:160px;">
                                                    </g:select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    生产日期
                                                </td>
                                                <td>
                                                    <c:dataPicker name="veh_Scrq" id="veh_Scrq" style="width: 120px" editable="false"  dateFormat="yy年mm月dd日"  value="${STInfoInstance?.veh_Scrq}"></c:dataPicker>
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

    %{--###################################打印显示的页面START#####################################--}%
        <div style="margin-top:85px;display: none" class="print" id='print'>
            <table>
                <tr>
                    <td style="line-height: 20px;padding-left: 45px"></td>
                    <td style="line-height: 20px;padding-left: 60px;font-size:18px;font-family:SimHei">
                        %{--车型--}%
                        ${STInfoInstance?.veh_Cx}
                    </td>
                </tr>
                <tr>
                    <td style="line-height: 20px;padding-left: 45px"></td>
                    <td style="line-height: 20px;padding-left: 60px;font-size:18px;font-family:SimHei">
                        %{--新车辆编号--}%
                        ${STInfoInstance?.veh_new_clbh}
                    </td>
                </tr>
                <tr>
                    <td style="line-height: 20px;padding-left:45px"></td>
                    <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                        %{--发动机型号--}%
                        ${STInfoInstance?.veh_Fdjxh}
                    </td>
                </tr>
                <tr>
                    <td style="line-height: 20px;padding-left:45px"></td>
                    <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                        %{--发动机号--}%
                        ${STInfoInstance?.veh_Fdjh}
                    </td>
                </tr>
                <tr>
                    <td style="line-height: 20px;padding-left:45px"></td>
                    <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                        %{--底盘号--}%
                        ${STInfoInstance?.veh_new_clbh}
                    </td>
                </tr>
                <tr>
                    <td style="line-height:19px;padding-left:45px"></td>
                    <td style="line-height: 19px;padding-left: 60px;font-size:18px;font-family:SimHei">
                        %{--出场日期--}%
                        ${STInfoInstance?.veh_Scrq}
                    </td>
                </tr>
                <tr>
                    <td style="line-height:19px;padding-left:45px"></td>
                    <td style="line-height: 19px;padding-left: 60px;font-size:18px;font-family:SimHei">
                        %{--执行标准--}%
                        ${STInfoInstance?.veh_Zxbz}
                    </td>
                </tr>
                <tr>
                    <td style="line-height:18px;padding-left:45px"></td>
                    <td style="line-height: 18px;padding-left: 60px;font-size:18px;font-family:SimHei">
                        %{--机械环保代码--}%
                        ${STInfoInstance?.envirCode}
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
                    <td style="line-height: 20px;padding-left: 45px"></td>
                    <td style="line-height: 20px;padding-left:60px;font-size:18px;font-family:SimHei">
                        %{--空白行--}%
                        &nbsp&nbsp&nbsp &nbsp
                    </td>
                </tr>
                <tr>
                    <td style="line-height:20px;padding-left:45px"></td>
                    <td style="line-height: 20px;padding-left: 90px;font-size:18px;font-family:SimHei">
                        %{--合格证编号--}%
                        ${STInfoInstance?.veh_Hgzbh}
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

        %{--$('#veh_Cx').omCombo({--}%
        %{--multi : false ,--}%
        %{--dataSource : "${createLink(controller:'NZInfo',action:'jsonCx')}?random="+Math.random(),--}%
        %{--value:'${STInfoInstance?.veh_Cx?STInfoInstance?.veh_Cx:'WZ260'}'--}%

        %{--});--}%
        $('#btn_select').click(function(){
            var url = '${createLink(controller:'STInfo',action:'toNotice')}';
            create(url,'公告信息',1440,900);
        });
        $('#btn_save').click(function(){ //验证车间号码
            var isupload=  $('#isupload').val();
            if(isupload=='1'){
                alert('合格证已上传,不允许修改')
                return;
            }
            var veh_Cx= $('#veh_Cx').val();
            if(veh_Cx==null||$.trim(veh_Cx)==''){
                alert('保存前,请输入型号')
                return;
            }
            var veh_Hgzbh= $('#veh_Hgzbh').val();
            if(veh_Hgzbh==null||$.trim(veh_Hgzbh)==''){
                alert('保存前,请输入合格证编号')
                return;
            }
            if(veh_Hgzbh.length!=17){
                alert('合格证编号不符合要求')
                return;
            }
            var veh_new_clbh= $('#veh_new_clbh').val();
            if(veh_new_clbh==null||$.trim(veh_new_clbh)==''){
                alert('保存前,请输入新车辆编号')
                return;
            }
            if(veh_new_clbh.length!=17){
                alert('新车辆编号不符合要求')
                return;
            }
            var envirCode= $('#envirCode').val();
            if(envirCode==null||$.trim(envirCode)==''){
                alert('保存前,请输入环保信息代码')
                return;
            }
            if(envirCode.length!=17){
                alert('环保信息代码不符合要求')
                return;
            }
            var veh_Zxbz=$("#veh_Zxbz").val()
            if(veh_Zxbz==null||$.trim(veh_Zxbz)==''){
                alert('保存前,请选择执行标准！')
                return;
            }
            var veh_Fdjh=$("#veh_Fdjh").val();
            if(veh_Fdjh==null||$.trim(veh_Fdjh)==''){
                alert('保存前,发动机号不能为空')
                return;
            }

            document.forms[0].submit();
        });
        $('#btn_query').click(function(){
            var url = '${createLink(controller:'STInfo',action:'search')}';
            var  veh_Scbh=$('#veh_Scbh').val();
            var  type=$('#type').val();
            var  processType=$('#processType').val();
            if(veh_Scbh.length==9){
                window.location.href = url+'?veh_Scbh='+veh_Scbh+'&type='+type+'&processType='+processType;
            }
        });
        $('#recover').click(function(){
            var url = '${createLink(controller:'STInfo',action:'search')}';
            var  veh_Scbh=$('#veh_Scbh').val();
            var type=$('#type').val();
            var  processType=$('#processType').val();
            window.location.href = url+'?recover=0&veh_Scbh='+veh_Scbh+'&type='+type+'&processType='+processType;
        });
        $('#btn_calculate').click(function(){
            calculateVin();
        });
        $('#btn_nextview').click(function(){
            var url = '${createLink(controller:'STInfo',action:'search')}';
            var  veh_Scbh=$('#veh_Scbh').val();
            var  type=$('#type').val();
            var  processType=$('#processType').val();
            if(veh_Scbh.length==9){
                window.location.href = url+'?direction=1&veh_Scbh='+veh_Scbh+'&type='+type+'&processType='+processType;
            }
        });
        $('#btn_preview').click(function(){
            var url = '${createLink(controller:'STInfo',action:'search')}';
            var  veh_Scbh=$('#veh_Scbh').val();
            var  type=$('#type').val();
            var  processType=$('#processType').val();
            if(veh_Scbh.length==9){
                window.location.href = url+'?direction=-1&veh_Scbh='+veh_Scbh+'&type='+type+'&processType='+processType;
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
        var  veh_Scbh=$('#veh_Scbh').val();
        var type=$('#type').val();
        var url = '${createLink(controller:'STInfo',action:'print')}?isprint=1';
        var  processType=$('#processType').val();
        var dialogId=showTipDialog();
        $.post(url,params,function(data,textStatus){
            parent.closeDialog(dialogId);
            var d=eval("("+data+")");
            if(d.flag=='successed'){
                $.omMessageBox.alert({
                    content: d.msg,
                    onClose:function(v){
                        var url = '${createLink(controller:'STInfo',action:'search')}';
                        if(veh_Scbh.length>0){
                            window.location.href = url+'?type='+type+'&intI=1&veh_Scbh='+veh_Scbh+'&processType='+processType;
                        }
                    }
                });
            }else{
                alert(d.msg);
            }
        },'text');
    }
    function changeData(data){
        $('#veh_Cx').val(data.modle)
        $('#veh_Fdjxh').val(data.veh_Fdjxh)
        $('#veh_Rllx').val(data.veh_Rllx)
        $('#veh_Jxdl').val(data.veh_Jxdl)
        $('#veh_Jxxlb').val(data.veh_Jxxlb)
        $('#veh_Zycs').val(data.veh_Zycs)
        $('#veh_Pfjd').val(data.veh_Pfjd)
        $('#modle_type').val(data.modle_type)
    }
    function calculateVin(){
        var url = '${createLink(controller:'STInfo',action:'calculateVIN')}';
        var veh_Rllx=$('#veh_Rllx').val();
        var veh_Jxdl=$('#veh_Jxdl').val();
        var veh_Jxxlb=$('#veh_Jxxlb').val();
        var veh_Zycs=$('#veh_Zycs').val();
        var veh_Pfjd=$('#veh_Pfjd').val();
        var veh_Clbh=$('#veh_Scbh').val();
        var modle_type=$('#modle_type').val();
        var type=$('#type').val();
        $.post(url,{'veh_Rllx':veh_Rllx,'veh_Jxdl':veh_Jxdl,'veh_Jxxlb':veh_Jxxlb,'veh_Zycs':veh_Zycs,'veh_Pfjd':veh_Pfjd,'veh_Clbh':veh_Clbh,'type':type,'modle_type':modle_type},function(data){
            if(data.flag=='0'){
                alert(data.msg);
            }else if(data.flag=='1'){
                $('#envirCode').val(data.envirCode);
                $('#veh_Hgzbh').val(data.envirCode);
                $('#veh_new_clbh').val(data.envirCode);
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
