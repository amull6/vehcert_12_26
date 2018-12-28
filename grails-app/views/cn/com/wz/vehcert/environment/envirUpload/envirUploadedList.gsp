<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="已上传环保信息随车清单" />
    <title>已上传环保信息随车</title>
</head>
<body>
<div id="div_message" style="background-color: #e8ccc4;">
</div>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <input type="hidden" id="stayus" name="status" value="1">
            <table style="width:100%;border:0px;">
                <tr>
                    <td  style="width: 80px;text-align: right"><span>VIN：</span></td>
                    <td style="width: 150px" width="100"><span>
                        <g:textField id="en_clsbdh" name="en_clsbdh" style="width: 140px" />
                    </span>
                    </td>
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
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <div style="margin-top:10px;" class="buttonsDiv">
            <a id="btn_upload"  class="btn_basic blue_black" href="javascript:delData();">撤销已上传数据</a>
        </div>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="uploaded_grid" style="border:0px"></div>
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
                $('#uploaded_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#time1").val('');
                $("#time2").val('');
                $("#en_clsbdh").val('');

            }
        });
        $(".excel").click(function(){
             $('.excel').attr('href','javascript:void(0);');
            //当节点没有选中的情况，导出s通讯录中所有的数据
            dialogId=showTipDialog();
            initId=setInterval("getResult();",'3000');
            var url="${createLink(controller:'envirUpload',action:'exportSearch')}?format=excel&extension=xlsx&time1="+time1+"&time2="+time2+'&status=1';
            url=encodeURI(url);
            window.location.href=url;
        });
    })

    /**
     * @Description 上传选中的环保信息
     * @Create zhuwei 2017-03-02
     */
    function delData(){
        var url="${createLink(controller: "envirUpload",action:"delData")}";
        //这里利用Ajax删除数据
        var selections = $('#uploaded_grid').omGrid('getSelections',true);
        if(selections.length==0){
            alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
            return;
        }else if(selections.length==1){
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'确定删除已上传选中的随车请单信息吗？',
                onClose:function(v){
                    if(v){
                        var dialogId=showTipDialog();
                        $("#div_message").html("");
                        var ids = '';
                        $.each(selections,function(i,item){
                            ids += item.id+'_';
                        });
                        $.post(url,{ids:ids},function(data,textstatus){
                            parent.closeDialog(dialogId);
                            var selections = $('#uploaded_grid').omGrid('reload');
                            if(textstatus=='success'){
//                                alert(data.msg);
//                                alert(data.flag);
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;margin-left: 100px'>"+data.msg+"</div>");
                            }else{
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+"请求失败！"+"</div>");
                            }
                        });
                    }
                }
            });
        }else{
            alert('只能选择一条数据');
            return;
        }
    }

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
        $('#uploaded_grid').omGrid({
            dataSource:'${createLink(controller:'envirUpload',action:'jsonList')}?time1='+time1+'&time2='+time2+'&status=1',
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
        $('#uploaded_grid').omGrid('reload');
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
