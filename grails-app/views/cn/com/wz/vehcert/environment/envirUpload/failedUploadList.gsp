<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="上传失败环保信息随车清单" />
    <title>上传失败环保信息</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <div style="margin-right:8px;margin-top:8px;">
            <div id="failed_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    var dialogId='';
    var initId='';
    $(function() {
        $('#page').css({height:$(document).height()-16});
    })



    //获取组合结果
    function getResult(){
        var url='${createLink(controller:'envirUpload',action:'getCompResult')}';
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
            },{
                id:"west-panel",
                title:"<g:message code="default.list.label" args="[entityName]" />",
                region:"west",
                width:200
            }]
        });
        //构建左侧树
        //buildLeftTree();
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#failed_grid').omGrid({
            dataSource:'${createLink(controller:'envirUpload',action:'jsonList')}?status=2',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:true,
            limit : 10,
            height :390,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return  '<a id="btn_overTime1" class="btn_basic blue_black"  href="javascript:uploadAgain('+rowIndex+');">补传</a>'+
                                    '&nbsp;'+
                                    '<a id="btn_finish1" class="btn_basic blue_black"  href="javascript:finish('+rowIndex+');">完成</a>';
                        }},
                {header : "VIN", name : 'en_clsbdh', width : 120, align : 'center'} ,
                {header : "信息公开号", name : 'en_xxgkbh', width :195, align : 'center'},
                {header : "商标", name : 'en_sb', width : 40, align : 'center'},
                {header : "生产厂地址", name : 'en_sccdz', width : 130, align : 'center'},
                {header : "发的动机号", name : 'en_fdjh', width : 130, align : 'center'},

                {header : "生产时间", name : 'en_scrq', width : 90, align : 'center'},
                {header : "出厂时间", name : 'en_ccrq', width : 95, align : 'center'},
                {header : "出厂试验", name : 'en_ccsy', width : 150, align : 'center'} ,
                {header : "出厂结论", name : 'en_ccjl', width : 50, align : 'center'},
                {header : "公开网站", name : 'en_gfwz', width : 70, align : 'center'},

                {header : "发动机商标", name : 'en_fdjsb', width : 60, align : 'center'},
                {header : "发动机生产厂地址", name : 'en_fdjsccdz', width : 200, align : 'center'},

                {header : "车辆型号", name : 'en_clxh', width : 120, align : 'center'},
                {header : "发动机型号", name : 'en_fdjxh', width : 80, align : 'center'},
                {header : "汽车分类", name : 'en_qcfl', width : 70, align : 'center'},

                {header : "创建时间", name : 'create_time', width : 120, align : 'center'},
                {header : "创建人id", name : 'creater_id', width : 100, align : 'center'},
                {header : "类型", name : 'type', width : 80, align : 'center'}
            ]
        });
    }
    function backFunc(result){
        $('#failed_grid').omGrid('reload');
    }
    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }
    //已经上传但是上传失败得数据，在进行以此上传
    function uploadAgain(index){
        var data = $("#failed_grid").omGrid("getData").rows[index];
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
            content:'确定上传吗?',
            onClose:function(v){
                if(v){
                    var uploadUrl = '${createLink(controller:'envirUpload',action:'uploadByHand')}';
                    var ids = data.id+'_';
                    $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                        var selections = $('#failed_grid').omGrid('reload');
                        alert(data.msg);
                    },'json');
                }
            }
        });
    }
    //已经上传失败得环保随车请单数据，做完成操作
    function finish(index){
        var data = $("#failed_grid").omGrid("getData").rows[index];
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
//            content:'确定完成吗?',
            content:'确定完成选中的环保随车请单信息吗？<div style="font-weight: bold;color:red;">完成的环保随车信息将从系统中删除,并不可恢复！</div>',
            onClose:function(v){
                if(v){
                    var uploadUrl = '${createLink(controller:'envirUpload',action:'forbid')}';
                    var ids = data.id+"_";
                    $.post(uploadUrl,{"ids":ids},function(data,textStatus){
                        var selections = $('#failed_grid').omGrid('reload');
                        showTopTip(data.msg);
                    },'json');
                }
            }
        });
    }
</script>
</body>
</html>
