<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="已打印环保信息随车清单" />
    <title>重型柴油车/城市车辆环保信息</title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>创建时间：</span></td>
                    <td style="width: 130px" width="100"><span>
                        <c:dataPicker id="time1" name="time1" showTime="false" style="width:100px"  dateFormat='yy-mm-dd'></c:dataPicker>
                    </span></td>
                    <td style="width: 20px">
                        至
                    </td>
                    <td width="100" style="width: 100px"><span>
                        <c:dataPicker id="time2" name="time2"  showTime="false" style="width:100px" dateFormat='yy-mm-dd'></c:dataPicker>
                    </span></td>
                    <td width='80px'>是否上传：</td>
                    <td width='80px'>
                        <select name="status" id="status" >
                            <option value="0" >未上传</option>
                            <option value="1" >已上传</option>
                            <option value="2" >上传失败</option>
                        </select>
                    </td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <export:formats formats="['excel']"  controller="oil" action="export" style="border:0px;margin-left:2px;margin-top:-5px;"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="heavyDiesel_grid" style="border:0px"></div>
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
                var url = "${createLink(controller:'envirUpload',action:'jsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#heavyDiesel_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#time1").val('');
                $("#time2").val('');
            }
        });
        $(".excel").click(function(){
            var time1=$("#time1").val();
            var time2=$("#time2").val();
            $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'envirUpload',action:'exportSearch')}?format=excel&extension=xlsx&time1="+time1+"&time2="+time2;
            url=encodeURI(url);
            window.location.href=url;
        });
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
        var time1=$("#time1").val();
        var time2=$("#time2").val();
        $('#heavyDiesel_grid').omGrid({
            dataSource:'${createLink(controller:'envirUpload',action:'jsonList')}?time1='+time1+'&time2='+time2,
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :390,
            colModel:[
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
        $('#heavyDiesel_grid').omGrid('reload');
    }
    function showTopTip(content){
        $.omMessageTip.show({
            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
            content : content,
            timeout : 3000
        });
    }
</script>
</body>
</html>
