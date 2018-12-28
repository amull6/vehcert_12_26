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
                    <td width="80px" align="right"><span style="width: 80px"><g:message code="carstorage.veh_Clxh.label" default="车辆型号" />:</span></td>
                    <td width="60px" align="left"><span><g:textField id="modle" name="modle" style="width:120px;"  value=""/></span></td>
                    <td width="80px" align="right"><span style="width: 80px"><g:message code="carstorage.veh_Fdjxh.label" default="发动机型号" />:</span></td>
                    <td width="60px" align="left"><span><g:textField id="veh_Fdjxh" name="veh_Fdjxh" style="width:120px;"  value=""/></span></td>
                    <td width="100px" align="right"><span><g:message code="nzcarstorage.veh_Jxdl.label" default="机械大类" />:</span></td>
                    <td width="60px" align="left"><g:textField id="veh_Jxdl" style="width:60px;" name="veh_Jxdl"  value=""/></td>
                    <td width="100px" align="right"><span><g:message code="nzcarstorage.veh_Rllx.label" default="燃料类型" />:</span></td>
                    <td width="60px" align="left"><g:textField id="veh_Rllx" style="width:60px;" name="veh_Rllx"  value=""/></td>
                    <td width="100px" align="right"><span><g:message code="nzcarstorage.veh_Jxxlb.label" default="机械小类别" />:</span></td>
                    <td width="60px" align="left"><g:textField id="veh_Jxxlb" style="width:60px;" name="veh_Jxxlb"  value=""/></td>
                    <td width="100" align="right"><span><g:message code="nzcarstorage.veh_Zycs.label" default="主要参数" />:</span></td>
                    <td width="60" align="left" ><span><g:textField id="veh_Zycs" name="veh_Zycs" style="width:60px;"  value=""/></span></td>
                    <td width="100px" align="right"><span><g:message code="carstorage.car_storage_no.label" default="公告唯一号" />: </td>
                    <td width="100px" align="left"><span><g:textField id="car_storage_no" name="car_storage_no" style="width:180px;"  value=""/></span></td>

                </tr>
                <tr>
                    <td width="80px" align="right"><span style="width: 80px"><g:message code="nzcarstorage.veh_Pfjd.label" default="排放阶段" />:</span></td>
                    <td width="60px" align="left"><span ><g:textField id="veh_Pfjd" name="veh_Pfjd"  style="width: 60px"  value=""/></span></td>
                    <td  valign="bottom" align="right">
                        <input id="btn_query" type="button" width="60" value="<g:message code="default.button.query.label" default="Query" />"/>
                    </td>
                    <td>
                        <input id="btn_clear" type="button" width="60"  value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>
        </form>
        <div style="margin-right:8px;margin-top:20px;" >
            <div id="notice_content" style="border:0px;height:100%"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $('#veh_Jxdl').omCombo({
            multi : false
        });
        $('#veh_Fdjxh').omCombo({
            multi : false
        });
        $('#veh_Rllx').omCombo({
            multi : false
        });

        $('#veh_Jxxlb').omCombo({
            multi : false
        });
        $('#veh_Zycs').omCombo({
            multi : false
        });
        $('#veh_Pfjd').omCombo({
            multi : false
        });
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'STInfo',action:'jsonNotice')}";
                url+='?'+$('#form_query').serialize();
                $('#notice_content').omGrid('setData',url);
            }
        });
        $("#modle").change( function() {
            var modle=$('#modle').val()
            if(modle!='') {
                $('#veh_Fdjxh').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Fdjxh&modle="+modle+"&random="+Math.random());
                $('#veh_Jxdl').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Jxdl&modle="+modle+"&random="+Math.random());
                $('#veh_Rllx').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Rllx&modle="+modle+"&random="+Math.random());
                $('#veh_Jxxlb').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Jxxlb&modle="+modle+"&random="+Math.random());
                $('#veh_Zycs').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Zycs&modle="+modle+"&random="+Math.random());
                $('#veh_Pfjd').omCombo('setData',"${createLink(controller:'STInfo',action:'jsonInfoSuggestion')}?field=veh_Pfjd&modle="+modle+"&random="+Math.random());
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#modle").val('');
                $("#veh_Fdjxh").val('');
                $("#veh_Jxdl").val('');
                $("#veh_Rllx").val('');
                $("#veh_Jxxlb").val('');
                $("#veh_Zycs").val('');
                $("#veh_Pfjd").val('');
            }
        });
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
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 100, align : 'center',name : 'modle'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 100, align : 'center',name : 'veh_Fdjxh'},
                {header : "车型名称", width : 100, align : 'center',name : 'modleName'},
                {header : "机械大类", width : 120, align : 'center',name : 'veh_Jxdl'},
                {header : "燃料类型", width : 80, align : 'center',name : 'veh_Rllx'},
                {header : "机械小类别", width : 80, align : 'center',name : 'veh_Jxxlb'},
                {header : "主要参数", width : 80, align : 'center',name : 'veh_Zycs'},
                {header : "排放阶段", width : 80, align : 'center',name : 'veh_Pfjd'},
                {header : "类型", width : 60, align : 'center',name : 'modle_type',
                    renderer:function(value,rowData,rowIndex){
                        if(value==0){
                            return '<span style="color: green">山拖公告</span>';
                        }else if(value==1){
                            return '<span style="color: red">农装公告</span>';
                        }
                    }},
                {header : "${message(code: 'carstorage.createrName.label')}", width : 150, align : 'center',name : 'createrName'},
                {header : "${message(code: 'carstorage.createTime.label')}", width : 150, align : 'center',name : 'createTime'},
                {header : "${message(code: 'carstorage.updaterName.label')}", width : 150, align : 'center',name : 'updaterName'},
                {header : "${message(code: 'carstorage.updateTime.label')}", width : 150, align : 'center',name : 'updateTime'},
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
