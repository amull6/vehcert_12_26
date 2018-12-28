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
                    <td width="80"><span>合格证编号：</span></td>
                    <td width="100"><span><g:textField id="vehCode" name="vehCode" maxlength="200"/></span></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

            <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <input id="btn_export" type="button"  class="export1" value="${message(code: 'default.button.export.label', default: 'Export')}"/>
                <export:formats formats="['excel','pdf']"   style="border:0px; margin-top:-25px;margin-left:28px;"/>
            </fieldset>
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
                var url = "${createLink(controller:'cocPrint',action:'jsonChejianList')}";
                url+='?'+$('#form_query').serialize();
                url=encodeURI(url);
                $('#zcinfo_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#vehCode").val('');
            }
        });
        $("#VINLast6").keydown(function(e){
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
            dataSource:'${createLink(controller:'cocPrint',action:'jsonChejianList')}?'+encodeURI($('#form_query').serialize()),
            method:'POST',
            extraData:{list:'${list}'} ,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :500,
            width:'fit',
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 80, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view"  class="btn_basic blue_black"  href="javascript:goYiZhi('+rowIndex+');">打印</a>'
                        }},
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view"  class="btn_basic blue_black"  href="javascript:goNewYiZhi('+rowIndex+');">第二版证书打印</a>'
                        }},
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view"  class="btn_basic blue_black"  href="javascript:goNewYiZhi2('+rowIndex+');">第三版证书打印</a>'
                        }},

                {header : "车辆状态信息", name : 'veh_Clztxx', width : 80, align : 'center', renderer:
                        function(value, rowData, rowIndex){
                            if(value=='QX'){
                                return '<span style="color: #000000">整车</span>';
                            }else if(value=='DP'){
                                return '<span style="color: green">底盘</span>';
                            }
                        }
                },
                {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'veh_Clxh', width : 89, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'veh_Clmc', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Csys.label', default: '车身颜色')}", name : 'veh_Csys', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.VIN.label', default: 'VIN')}", name : 'veh_Clsbdh', width : 125, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hxnbc.label', default: '货箱内部长')}", name : 'veh_Hxnbc', width :80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Hxnbk.label', default: '货箱内部宽')}", name : 'veh_Hxnbk', width : 80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Hxnbg.label', default: '货箱内部高')}", name : 'veh_Hxnbg', width : 80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label', default: '发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label', default: '发动机型号')}", name : 'veh_Fdjxh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Wkc.label', default: '外廓长')}", name : 'veh_Wkc', width : 80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Wkk.label', default: '外廓宽')}", name : 'veh_Wkk', width : 80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Wkg.label', default: '外廓高')}", name : 'veh_Wkg', width : 80, align : 'right'},
                {header : "${message(code: 'zcinfo.veh_Fzrq.label', default: '发证日期')}", name : 'veh_Fzrq', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Pl.label', default: '排量')}", name : 'veh_Pl', width : 90, align : 'right'}

            ]
        });
    }
    //根据基础信息专项相关的一致性证书列表
    function goYiZhi(index){
        var data1 = $("#zcinfo_grid").omGrid("getData").rows[index];
        var searchCocPrnUrl = '${createLink(controller:'cocPrint',action:'jsonSearchCocPrn')}?zcinfoID='+data1.id;
        $.post(searchCocPrnUrl,function(data,textStatus){

            if(data  ==1){
                $.omMessageBox.confirm({
                    title:'确认',
                    content:'产品一致性证书已经打印,是否替换原来的信息?',
                    onClose:function(value){
                        if(value){
                            //清除已打印的一致性证书信息
                            var delCocPrUrl = '${createLink(controller:'cocPrint',action:'jsonDelCocPr')}?zcinfoID='+data1.id;
                            $.post(delCocPrUrl,function(data,textStatus){
                                if(textStatus=="success"){
                                    var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';
                                    window.location.href = url;
                                } else{
                                    alert("清除已打印的一致性证书信息失败！");
                                    return false;
                                }
                            },'text');

                        } else{
                            var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?cocPrnID='+data1.id+'&usertype=0';
                            window.location.href = url;
                        }
                    }
                });
            }else if(data  ==2)   {
                %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                %{--window.location.href = url;--}%
                alert("打印的产品一致性证书已经上传至服务器，不能再次打印！")

            } else{
                var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';
                window.location.href = url;
            }
        },'text');

    }
    //新的一致性证书打印方法，为了从老一致性证书过渡到新一致性证书，不再使用同一方法，而是使用了新的方法
    //使用newCoC=1来标记是不是打印新的一致性证书
    function goNewYiZhi(index){
        var data1 = $("#zcinfo_grid").omGrid("getData").rows[index];
        var searchCocPrnUrl = '${createLink(controller:'cocPrint',action:'jsonSearchCocPrn')}?zcinfoID='+data1.id;
        $.post(searchCocPrnUrl,function(data,textStatus){

            if(data  ==1){
                $.omMessageBox.confirm({
                    title:'确认',
                    content:'产品一致性证书已经打印,是否替换原来的信息?',
                    onClose:function(value){
                        if(value){
                            //清除已打印的一致性证书信息
                            var delCocPrUrl = '${createLink(controller:'cocPrint',action:'jsonDelCocPr')}?zcinfoID='+data1.id;
                            $.post(delCocPrUrl,function(data,textStatus){
                                if(textStatus=="success"){
                                    var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0&newCoC=1';
                                    window.location.href = url;
                                } else{
                                    alert("清除已打印的一致性证书信息失败！");
                                    return false;
                                }
                            },'text');

                        } else{
                            var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?cocPrnID='+data1.id+'&usertype=0&newCoC=1&oldQRcode=1';     //不替换一致性证书
                            window.location.href = url;
                        }
                    }
                });
            }else if(data  ==2)   {
                %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                %{--window.location.href = url;--}%
                alert("打印的产品一致性证书已经上传至服务器，不能再次打印！")

            } else{
                var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0&newCoC=1';
                window.location.href = url;
            }
        },'text');

    }
    //新的一致性证书打印方法，为了从老一致性证书过渡到新一致性证书，不再使用同一方法，而是使用了新的方法
    //增加了新能源车型的一致性证书打印
    function goNewYiZhi2(index){
        var data1 = $("#zcinfo_grid").omGrid("getData").rows[index];
        var searchCocPrnUrl = '${createLink(controller:'cocPrint',action:'jsonSearchCocPrn')}?zcinfoID='+data1.id;
        $.post(searchCocPrnUrl,function(data,textStatus){

            if(data  ==1){
                $.omMessageBox.confirm({
                    title:'确认',
                    content:'产品一致性证书已经打印,是否替换原来的信息?',
                    onClose:function(value){
                        if(value){
                            //清除已打印的一致性证书信息
                            var delCocPrUrl = '${createLink(controller:'cocPrint',action:'jsonDelCocPr')}?zcinfoID='+data1.id;
                            $.post(delCocPrUrl,function(data,textStatus){
                                if(textStatus=="success"){
                                    var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0&newCoC=2';
                                    window.location.href = url;
                                } else{
                                    alert("清除已打印的一致性证书信息失败！");
                                    return false;
                                }
                            },'text');

                        } else{
                            var url = '${createLink(controller:'cocPrint',action:'cocBookPrint')}?cocPrnID='+data1.id+'&usertype=0&newCoC=2&oldQRcode=2';     //不替换一致性证书
                            window.location.href = url;
                        }
                    }
                });
            }else if(data  ==2)   {
                %{--var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0';--}%
                %{--window.location.href = url;--}%
                alert("打印的产品一致性证书已经上传至服务器，不能再次打印！")

            } else{
                var url = '${createLink(controller:'cocPrint',action:'zcinfoOfList')}?zcinfoID='+data1.id+'&usertype=0&newCoC=2';
                window.location.href = url;
            }
        },'text');

    }
</script>
</body>
</html>