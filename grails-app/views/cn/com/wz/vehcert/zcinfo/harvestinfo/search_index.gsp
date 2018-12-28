<%--
 @Description:
 @params:
 @return:
 @Update:
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="农装合格证查询" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>

                    <td width="80" align="right"><span><g:message code="carstorage.createTime.label" default="Start Time" />：</span></td>
                    <td width="100"><span><c:dataPicker id="startTime" name="startTime" showTime="false"   dateFormat='yy-mm-dd' style="width:75px;" value=""></c:dataPicker></span></td>
                    <td width="10" align="right">至</td>
                    <td width="100"><span><c:dataPicker id="endTime" name="endTime"  showTime="false"  dateFormat='yy-mm-dd' style="width:75px;"></c:dataPicker></span></td>
                    <td width='10'></td>
                    <td width="80" align="right"><span><g:message code="nzinfo.veh_Clbh.label" default="veh_Clbh" />：</span></td>
                    <td width="100"><span><g:textField id="veh_Clbh" name="veh_Clbh" maxlength="200" style="width:112px;"/></span></td>
                    <td width='10'></td>
                    <td width='10'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <export:formats formats="['excel']"  style="border:0px;margin-left:0px;margin-top:-8px;"/>
        </fieldset>

        <div style="margin-right:8px;margin-top:8px;">
            <div id="harvestinfo_grid" style="border:0px"></div>
        </div>
    </div>
</div>



<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});
        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'harvestInfo',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#harvestinfo_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#startTime").val('');
                $("#endTime").val('');
                $("#veh_Clbh").val('');
            }
        });
        $("#btn_synToServer").click(function(){
            var id=showTipDialog();
            var url="${createLink(controller:'NZInfo',action:'uploadToServer')}";
            $.post(url,{},function(data,textStatus){
                //自动关闭进度提示框
                parent.closeDialog(id);
                var content = data;
                showTopTip(content);
            },'text');
        });


        $(".excel").click(function(){
            var startTime=$("#startTime").val();
            var endTime=$("#endTime").val();
//            var upload = $("#upload").val();
            var veh_Clbh=$("#veh_Clbh").val();
            $('.excel').attr('href','javascript:void(0);');
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            %{--var url="${createLink(controller:'ZCInfo',action:'export_search')}?format=excel&extension=xlsx&veh_Clsbdh="+veh_Clsbdh+"&veh_Zchgzbh="+veh_Zchgzbh+"&startTime="+startTime+"&endTime="+endTime+"&web_status="+web_status;--}%
            var url="${createLink(controller:'harvestInfo',action:'export_search')}?format=excel&extension=xlsx&veh_Clbh="+veh_Clbh+"&startTime="+startTime+"&endTime="+endTime;
            window.location.href=url;
        });
    })

    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'harvestInfo',action:'getCompResult')}';
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
            }]
        });

//          $("#startTime").val('');
        var now =new Date();
        var month=now.getMonth()+1;
        if(month<10){
            month="0"+month;
        }
        $('#startTime').val( now.getFullYear()+"-"+month+"-"+now.getDate());
       $("#endTime").val('');
        buildRightGrid();
    });


    function buildRightGrid(){
        $('#harvestinfo_grid').omGrid({
            dataSource:"${createLink(controller:'harvestInfo',action:'jsonList')}?"+$('#form_query').serialize(),
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 10, // 分页显示，每页显示30条
            height : 400,
            colModel:[
                {header : "${message(code: 'nzinfo.veh_Hgzbh.label')}", width : 100, align : 'center',name : 'veh_Hgzbh'},
                {header : "${message(code: 'nzinfo.veh_Clbh.label')}", width : 100, align : 'center',name : 'veh_Clbh'},
                {header : "${message(code: 'nzinfo.veh_Fdjh.label')}", width : 100, align : 'center',name : 'veh_Fdjh'},
                {header : "${message(code: 'nzinfo.veh_Cx.label')}", width : 120, align : 'center',name : 'veh_Cx'},
                {header : "${message(code: 'nzinfo.veh_Ccrq.label')}", width : 100, align : 'center',name : 'veh_Ccrq'},
                {header : "${message(code: 'nzinfo.createTime.label')}", width : 120, align : 'center',name : 'createTime'},
                {header : "${message(code: 'nzinfo.createrId.label')}", width : 80, align : 'center',name : 'createrId'},
                {header : "${message(code: 'nzinfo.updateTime.label')}", width : 120, align : 'center',name : 'updateTime'},
                {header : "${message(code: 'nzinfo.updaterId.label')}", width : 80, align : 'center',name : 'updaterId'},
                {header : "${message(code: 'nzinfo.veh_workshopno.label')}", width : 60, align : 'center',name : 'veh_workshopno'}
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

    function backFunc(result){
        $('#carStorage_grid').omGrid('reload');
    }
</script>
</body>
</html>