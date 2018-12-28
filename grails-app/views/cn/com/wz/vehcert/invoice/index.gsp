<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="票据上传" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <input id="btn_create" type="button" class="create" value="上传票据"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="content_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});

        $('#btn_create').click(function(){
            var url = '${createLink(controller:'uploadInvoice',action:'create')}';
            window.location.href = url;
        });

    })


    // 加载时执行的语句
    $(function() {
        //布局
        $('#page').omBorderLayout({
            panels:[{
                id:"center-panel",
                header:false,
                region:"center"
            },{
                id:"west-panel",
                title:"<g:message code="default.list.label" args="[entityName]" />",
                region:"west",
                width:200
            }]
        });
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#content_grid').omGrid({
            dataSource:'${createLink(controller:'uploadInvoice',action:'jsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12,
            height :440,
            colModel:[
                {header:"文件名称",name:"fileName",width:400,align:'center'},
                {header : "上传时间", name : 'uploadTime', width : 120, align : 'center'} ,
                {header : "上传人", name : 'uploaderName', width : 150, align : 'center'},
                {header : "上传状态", name : 'resultCode', width : 100, align : 'center',  renderer:
                    function(value, rowData, rowIndex){
                         if(value=='0'){
                             return "上传成功";
                         }else{
                             return "部分上传失败";
                         }
                    }
                },
                {header : "返回信息", name : 'message', width : 150, align : 'center'}
            ]
        });
    }
</script>
</body>
</html>
