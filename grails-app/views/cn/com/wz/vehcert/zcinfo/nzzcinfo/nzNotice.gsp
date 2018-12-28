<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="公告信息" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body onselectstart="return false">
<div id="page" style="width:100%;height:100%;background-color:white;" >
    <div id="center-panel" style="margin-left:4px;border:1px;">

        <form id="form_query" style="margin:8px;height:80px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="8%" align="right"><g:message code="carstorage.veh_Clxh.label" default="车辆型号" />:</span></td>
                    <td width="8%" align="left"><span><g:textField id="veh_Clxh" name="veh_Clxh" style="width:100px;"  value=""/></span></td>
                    <td width="8%" align="right"><g:message code="carstorage.veh_Fdjxh.label" default="发动机型号" />:</span></td>
                    <td width="8%" align="left"><span><g:textField id="veh_Fdjxh" name="veh_Fdjxh" style="width:100px;"  value=""/></span></td>
                    <td width="8%" align="right"><span><g:message code="nzcarstorage.veh_Lhqxs.label" default="离合器型式" />:</span></td>
                    <td width="8%" align="left"><g:textField id="veh_Lhqxs" style="width:100px;" name="veh_Lhqxs"  value=""/></td>
                    <td width="8%" align="right"><span><g:message code="nzcarstorage.veh_Qpzxs.label" default="前配重型式" />:</span></td>
                    <td width="8%" align="left"><g:textField id="veh_Qpzxs" style="width:100px;" name="veh_Qpzxs"  value=""/></td>
                    <td width="8%" align="right"><span><g:message code="nzcarstorage.veh_Hpzxs.label" default="后配重型式" />:</span></td>
                    <td width="8%" align="left"><g:textField id="veh_Hpzxs" style="width:100px;" name="veh_Hpzxs"  value=""/></td>
                    <td width="8%" align="right"><span><g:message code="nzcarstorage.veh_Jzxs.label" default="机罩型式" />:</span></td>
                    <td width="8%" align="left" ><span><g:textField id="veh_Jzxs" name="veh_Jzxs" style="width:100px;"  value=""/></span></td>
                    <td width='80px'>是否出口：</td>
                    <td width='40px'>
                        <select name="is_Exp" id="is_Exp" >
                            <option value="0" >否</option>
                            <option value="1" >是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="80px" align="right"><span><g:message code="carstorage.veh_Jssxs.label" default="驾驶室型式" />: </td>
                    <td width="60px" align="left"><span><g:textField id="veh_Jssxs" name="veh_Jssxs" style="width:100px;"  value=""/></span></td>
                    <td width="80px" align="right"><span><g:message code="carstorage.veh_Bhxs.label" default="板簧型式" />: </td>
                    <td width="60px" align="left"><span><g:textField id="veh_Bhxs" name="veh_Bhxs" style="width:100px;"  value=""/></span></td>
                    <td width="80px" align="right"><span><g:message code="carstorage.veh_Ddlj.label" default="订单轮距" />: </td>
                    <td width="60px" align="left"><span><g:textField id="veh_Ddlj" name="veh_Ddlj" style="width:100px;"  value=""/></span></td>
                    <td width="80px" align="right"><span><g:message code="carstorage.veh_Ltgg.label" default="轮胎规格" />: </td>
                    <td width="60px" align="left"><span><g:textField id="veh_Ltgg" name="veh_Ltgg" style="width:100px;"  value=""/></span></td>
                    <td width="80px" align="right"><span><g:message code="carstorage.car_storage_no.label" default="公告唯一号" />: </td>
                    <td width="60px" align="left"><span><g:textField id="car_storage_no" name="car_storage_no" style="width:100px;"  value=""/></span></td>
                    <td width='80px'>公告类型：</td>
                    <td width='80px'>
                        <select name="car_storage_type" id="car_storage_type" >
                            <option value="">全部</option>
                            <option value="0" >小拖</option>
                            <option value="1" >中拖</option>
                            <option value="2" >大拖</option>
                            <option value="3" >玉米收</option>
                            <option value="4" >稻麦机</option>
                            <option value="5" >青贮机</option>
                        </select>
                    </td>
                    <td  valign="bottom" align="right">
                        <input type="hidden" id="turnOff" name="turnOff" value="${turnOff}">
                        <input type="hidden" id="veh_Clxh_select" name="veh_Clxh_select" value="">
                        <input id="btn_query" type="button" width="60" value="<g:message code="default.button.query.label" default="Query" />"/>
                    </td>
                    <td>
                        <input id="btn_clear" type="button" width="60"  value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="margin-right:8px;margin-top:10px;" >
            <div id="notice_content" style="border:0px;height:100%"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $('#veh_Clxh').omCombo({
            multi : false ,
            dataSource : "${createLink(controller:'NZInfo',action:'jsonCx')}?random="+Math.random(),
            value:'${NZInfoInstance?.veh_Cx?NZInfoInstance?.veh_Cx:''}',
            onValueChange:function() {
                var veh_Clxh=$('#veh_Clxh').val()
                if(veh_Clxh!='') {
                    $('#veh_Fdjxh').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Fdjxh&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Lhqxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Lhqxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Qpzxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Qpzxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Hpzxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Hpzxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Jzxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Jzxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Jssxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Jssxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Bhxs').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Bhxs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Ddlj').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Ddlj&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                    $('#veh_Ltgg').omCombo('setData',"${createLink(controller:'NZInfo',action:'jsonInfoSuggestion')}?field=veh_Ltgg&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                }
            }

        });
        $('#car_storage_type').val(${carStorageType});
        $('#veh_Fdjxh').omCombo({
            multi : false
        });
        $('#veh_Lhqxs').omCombo({
            multi : false
        });
        $('#veh_Qpzxs').omCombo({
            multi : false
        });

        $('#veh_Hpzxs').omCombo({
            multi : false
        });
        $('#veh_Jzxs').omCombo({
            multi : false
        });
        $('#veh_Jssxs').omCombo({
            multi : false
        });
        $('#veh_Bhxs').omCombo({
            multi : false
        });
        $('#veh_Ddlj').omCombo({
            multi : false
        });
        $('#veh_Ltgg').omCombo({
            multi : false
        });
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'NZInfo',action:'jsonNotice')}";
                url+='?'+$('#form_query').serialize();
                $('#notice_content').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Clxh").val('');
                $("#veh_Fdjxh").val('');
                $("#veh_Jxdl").val('');
                $("#veh_Rllx").val('');
                $("#veh_Jxxlb").val('');
                $("#veh_Zycs").val('');
                $("#veh_Pfjd").val('');
            }
        });
        $('#car_storage_type').val(${carStorageType})
        buildRightGrid();

    })
    // ################################加载时执行的语句########################
    $(function() {
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            }]
        });
    });
    function noticeSelect(rowIndex){
        var data = $('#notice_content').omGrid('getData').rows[rowIndex];
        parent["${tabId}"].changeData(data);
        parent.closeDialog(window.parent.document.getElementById("gonggao_dialog").parentNode.id);

    }

    function buildRightGrid(){
        $('#notice_content').omGrid({
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            height : 580,
            limit:0,
            singleSelect:false,
            onRefresh:function(nowPage,pageRecords,event){
                var startY=0;
                var endY=0;
                var startRowNum=0;
                var selected=[];
                var unselected=[];
                $("#notice_content tr").mousedown(function(p){
//                    alert(1);
//                    selected=[]  ;
//                    startY=0;
//                    endY=0;
//                    startRowNum=0;

                    startY=this.offsetTop;
//                    alert("startY="+startY) ;
//                    alert("offsettop="+this.offsetTop);
                    startRowNum=this.getAttribute("_grid_row_id");
                }) ;
                $("#notice_content tr").mouseup(function(p){
                    endY=this.offsetTop;
                    var height=0
                    if(endY>startY){  //向下拖动，实现选中状态
                       height=endY-startY;
                       var count=height/32;
                       for(var i=0;i<count+1;i++){
                           selected[i]=parseInt(startRowNum)+parseInt(i);
                       }
                       //获取已选择的记录
                       var selects= $('#notice_content').omGrid("getSelections");
                       var s=selected.concat(selects);
                       //选中状态
                       $('#notice_content').omGrid('setSelections',s);

                    }else if(endY<startY){  //向上拖动，取消选中状态
                        height=startY-endY;
                        var count=height/32;
                        for(var i=0;i<count+1;i++){
                           selected[i]=parseInt(startRowNum)-parseInt(i);
                        }
                        //获取已选择的记录
                        var selects= $('#notice_content').omGrid("getSelections");
                        //剔除反选的记录
                        var s=[]
                        for(var i=0;i<selects.length;i++){
                            if(selected.indexOf(parseInt(selects[i]))<0) {
                                s.push(selects[i]);
                            }
                        }
                        //选中记录
                        $('#notice_content').omGrid('setSelections',s);
                    }

                    $("#veh_Clxh").focus();

                }) ;
            } ,
            colModel:[
                {header : "操作", width : 60, align : 'center',name : 'id',
                    renderer:function(value,rowData,rowIndex){
                        var data = rowData;
                        return   '<a id="btn_view" class="btn_basic blue_black"  href="javascript:noticeSelect('+rowIndex+');">选择</a>';
                }},
                {header : "${message(code: 'nzcarstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 100, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'nzcarstorage.veh_Jxdl.label')}", width : 60, align : 'center',name : 'veh_Jxdl'},
                {header : "${message(code: 'nzcarstorage.veh_Rllx.label')}", width : 60, align : 'center',name : 'veh_Rllx'},
                {header : "${message(code: 'nzcarstorage.veh_Jxxlb.label')}", width : 60, align : 'center',name : 'veh_Jxxlb'},
                {header : "${message(code: 'nzcarstorage.veh_Zycs.label')}", width : 60, align : 'center',name : 'veh_Zycs'},
                {header : "${message(code: 'nzcarstorage.veh_Pfjd.label')}", width : 60, align : 'center',name : 'veh_Pfjd'},
                {header : "${message(code: 'nzcarstorage.veh_Pp.label')}", width : 60, align : 'center',name : 'veh_Pp'},
                {header : "${message(code: 'nzcarstorage.veh_Lx.label')}", width : 60, align : 'center',name : 'veh_Lx'},
                {header : "${message(code: 'nzcarstorage.veh_Gl.label')}", width : 60, align : 'center',name : 'veh_Gl'},
                {header : "${message(code: 'nzcarstorage.veh_Pfbz.label')}", width : 60, align : 'center',name : 'veh_Pfbz'},
                {header : "${message(code: 'nzcarstorage.veh_Jsys.label')}", width : 60, align : 'center',name : 'veh_Jsys'},
                {header : "${message(code: 'nzcarstorage.veh_Zxczxs.label')}", width : 60, align : 'center',name : 'veh_Zxczxs'},
                {header : "${message(code: 'nzcarstorage.veh_Zcrs.label')}", width : 60, align : 'center',name : 'veh_Zcrs'},
                {header : "${message(code: 'nzcarstorage.veh_Lzs.label')}", width : 60, align : 'center',name : 'veh_Lzs'},
                {header : "${message(code: 'nzcarstorage.veh_Zj.label')}", width : 60, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'nzcarstorage.veh_Lts.label')}", width : 60, align : 'center',name : 'veh_Lts'},
                {header : "${message(code: 'nzcarstorage.veh_Ltgg.label')}", width : 60, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'nzcarstorage.veh_Qlj.label')}", width : 60, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'nzcarstorage.veh_Hlj.label')}", width : 60, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'nzcarstorage.veh_Lds.label')}", width : 60, align : 'center',name : 'veh_Lds'},
                {header : "${message(code: 'nzcarstorage.veh_Ldgg.label')}", width : 60, align : 'center',name : 'veh_Ldgg'},
                {header : "${message(code: 'nzcarstorage.veh_Gj.label')}", width : 60, align : 'center',name : 'veh_Gj'},
                {header : "${message(code: 'nzcarstorage.veh_Wkc.label')}", width : 60, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'nzcarstorage.veh_Wkk.label')}", width : 60, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'nzcarstorage.veh_Wkg.label')}", width : 60, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'nzcarstorage.veh_Bdqyl.label')}", width : 60, align : 'center',name : 'veh_Bdqyl'},
                {header : "${message(code: 'nzcarstorage.veh_Zxsyzl.label')}", width : 60, align : 'center',name : 'veh_Zxsyzl'},
                {header : "${message(code: 'nzcarstorage.veh_Zdyyzzl.label')}", width : 60, align : 'center',name : 'veh_Zdyyzzl'},
                {header : "${message(code: 'carstorage.car_storage_no.label')}", width : 200, align : 'center',name : 'car_storage_no'}
            ]
        });
    }
    /**
     *刷新表格
     */
    function refreshGrid(){
        $('#notice_content').omGrid("reload");
    }
</script>
</body>
</html>
