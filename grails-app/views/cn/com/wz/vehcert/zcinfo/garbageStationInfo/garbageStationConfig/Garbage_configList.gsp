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
    <g:set var="entityName" value="专用车生产配置" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">

        <fieldset class="buttons" style="margin:5px 2px 2px 2px;">
            <input id="btn_save" type="button"  class="save" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
            %{--<export:formats formats="['excel']"  style="border:0px;margin-left:0px;margin-top:-8px;"/>--}%
        </fieldset>

        <div style="margin-right:8px;margin-top:8px;">
            <div id="garbageConfig_grid" style="border:0px"></div>
        </div>
    </div>
</div>



<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $("#btn_save").click(function(){
            var data = $('#garbageConfig_grid').omGrid('getChanges');

            var updates=""
            if(data.update != null && data.update != ""){
                $.each(data.update,function(i,item){
                    if(updates==""){
                        updates+=item.code+"#"+item.value1;
                    }else{
                        updates+=":"+item.code+"#"+item.value1;
                    }
                })
            }
            /*****此处传递data到后台并处理*******/
            var url="${createLink(controller: "GarbageConfig",action:"update")}";
            $.post(url,{"updates":updates},function(data,textStatus){
                data=eval("("+data+")");
                if(data.flag=='success'){
                    $('#garbageConfig_grid').omGrid('reload');
                    var content ="<div  style='width:100px;'>"+data.msg+"</div>";
                    showTopTip(content);
                }else{
                    var content ="<div  style='width:100px;'>"+data.msg+"</div>";
                    showTopTip(content);
                }

            },'text');

        });
    })

    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'GarbageInfo',action:'getCompResult')}';
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

        buildRightGrid();
    });


    function buildRightGrid(){
        $('#garbageConfig_grid').omGrid({
            dataSource:"${createLink(controller:'GarbageConfig',action:'findConfigDicValue')}",
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 12, // 分页显示，每页显示30条
            height : 500,
            onPageChange:function(type,newPage,event){
                var data = $('#garbageConfig_grid').omGrid('getChanges');

                var flag=false;
                if(data.update != null && data.update != ""){
                    flag=true;
                }
                if(flag){
                    if(confirm("翻页将丢失进行的修改，是否翻页！") ){
                        return true;
                    }else{
                        return false;
                    }
                }

            },
            onCancelEdit:function(){
                $("#garbageConfig_grid").omGrid("cancelChanges");
            },
            colModel:[
                {header : "配置项", width : 150, align : 'left',name : 'dicValueNameC', wrap:true, editor:{editable:false}},
                {header : "配置编码", width : 80, align : 'left',name : 'code', wrap:true, editor:{editable:false}},
                {header : "配置参数值", width : 100, align : 'left',name : 'value1',editor:{}},
                {header : "创建人人", width : 60, align : 'center',name : 'createdBy', wrap:true, editor:{editable:false}},
                {header : "创建时间", width : 120, align : 'center',name : 'dateCreated', wrap:true, editor:{editable:false}},
                {header : "最后更新人", width : 60, align : 'center',name : 'lastUpdatedBy', wrap:true, editor:{editable:false}},
                {header : "最后更新时间", width : 120, align : 'center',name : 'lastUpdated', wrap:true, editor:{editable:false}}
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