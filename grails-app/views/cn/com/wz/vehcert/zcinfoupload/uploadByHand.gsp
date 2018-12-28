<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="手动上传" />
    <title>手动上传</title>
</head>
<body>

<div id="page" style="width:98%;background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">

        <div id="div_message" style="background-color: #e8ccc4;">

        </div>
        <div style="margin-top: 5px;">
            <form id="form_query" style="margin:0px;">
                <table style="width:100%;border:0px;">
                    <tr>
                        <td width="80" align="right">合格证编号：</td>
                        <td width="70"><span>
                            <g:textField id="veh_Zchgzbh" name="veh_Zchgzbh"  />
                            <td width="80" align="right">VIN：</td>
                            <td width="80"><span><g:textField id="veh_Clsbdh" name="veh_Clsbdh"  /></span></td>
                            <td width="80" align="right"><span>合格证号段：</span></td>
                            <td width="250"><span>
                                <g:textField id="hgzStart" name="hgzStart"  />
                                -
                                <g:textField id="hgzEnd" name="hgzEnd"  />

                            </span></td>
                    </tr>
                    <tr>
                        <td width="80" align="right"><g:message code="zcinfo.veh_Fdjxh.label" default="veh_Fdjxh" />：</td>
                        <td width="80"><span><g:textField id="veh_Fdjxh" name="veh_Fdjxh"  /></span></td>
                        <td width="80" align="right">车辆型号：</td>
                        <td width="70"><g:textField id="veh_Clxh" name="veh_Clxh"  /></td>
                        <td width="40" align="right">创建日期：</td>
                        <td width="80"><span>
                            <c:dataPicker id="createTimeStart" name="createTimeStart" style="width:120px;"   dateFormat="yy-mm-dd" value="" ></c:dataPicker>
                            -
                            <c:dataPicker id="createTimeEnd" name="createTimeEnd" style="width:120px;"   dateFormat="yy-mm-dd" value="" ></c:dataPicker>
                        </span></td>

                        <td align="left" valign="middle" width="150px">
                            <input id="btn_query" type="button"   value="<g:message code="default.button.query.label" default="Query" />"/>
                            <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>

                        </td>
                    </tr>
                </table>
            </form>

            <div style="margin-top:10px;" class="buttonsDiv">
                <a id="btn_upload"  class="btn_basic blue_black" href="javascript:checkAndUpload();">手动上传</a>
                <a id="btn_uploadBy"  class="btn_basic blue_black" href="javascript:checkAndUploadByConditions();">根据条件上传</a>
                <a id="btn_forbid"  class="btn_basic blue_black" href="javascript:forbid();">禁止上传</a>
            </div>
        </div>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="zcinfo_grid" style="border:0px"></div>
        </div>
    </div>

</div>
<script>
    $(function() {
        buildRightGrid();
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfoUpload',action:'jsonUploadSucList')}?isUpload=0&searchType=hand";
                url+='&'+$('#form_query').serialize();
                $('#zcinfo_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){

                $("#veh_Clsbdh").val('');
                $("#veh_Zchgzbh").val('');
                $("#hgzStart").val('');
                $("#hgzEnd").val('');
                $("#createTimeStart").val('');
                $("#createTimeEnd").val('');
                $("#veh_Fdjxh").val('');
                $("#veh_Clxh").val('');
            }
        });
        $("#createTimeStart").val("");
        $("#createTimeEnd").val("");
    });

    /**
     * @Description 禁止上传选中的合格证信息 web_status置为6
     * @Create huxx 2014-02-12
     */
    function forbid(){
        var url="${createLink(controller: "ZCInfoUpload",action:"forbid")}";
        //这里利用Ajax删除数据
        var selections = $('#zcinfo_grid').omGrid('getSelections',true);
        if(selections.length==0){
            alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
            return;
        }else{
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'确定禁止上传选中的合格证信息吗？<div style="font-weight: bold;color:red;">禁止上传的合格证信息将从系统中删除,并不可恢复！</div>',
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
                            $('#zcinfo_grid').omGrid('reload');
                            if(textstatus=='success'){
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+data.msg+"</div>");
                            }else{
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+"请求失败！"+"</div>");
                            }
                        });
                    }
                }
            });
        }
    }

       /**
     * @Description 上传选中的合格证信息
     * @Create huxx 2014-02-12
     */
    function uploadByHand(){
        var url="${createLink(controller: "ZCInfoUpload",action:"uploadByHand")}";
        //这里利用Ajax删除数据
        var selections = $('#zcinfo_grid').omGrid('getSelections',true);
        if(selections.length==0){
            alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
            return;
        }else{
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'确定上传选中的合格证信息吗？',
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
                            if(textstatus=='success'){
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+data.msg+"</div>");
                            }else{
                                $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+"请求失败！"+"</div>");
                            }
                        });
                    }
                }
            });
        }
    }
    /**
     * @Description 验证轮胎规格是否符合公告信息并上传
     * @Create QJ 2017-08-22
     */
    function checkAndUpload(){
        var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
        //这里利用Ajax删除数据
        var selections = $('#zcinfo_grid').omGrid('getSelections',true);
        if(selections.length==0){
            alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
            return;
        }else{
            $("#div_message").html("");
            var idsCheck = '';
            $.each(selections,function(i,item){
                idsCheck += item.id+'_';
            });
            $.post(urlCheck,{ids:idsCheck},function(dd,textStatus){
                var d2=eval(dd);
                if(d2.flag=='failed'){
                    var a=confirm(d2.msg);
                    if(a==true){
                        uploadByHand();
                    }else{
                        var ids= d2.failIds;
                        var updateUrl="${createLink(controller: 'ZCInfoUpload',action:'checkFailedLtgg')}";
                            var params=$("#form").serialize();
                            $.post(updateUrl,{ids:ids},function(data,textStatus){
                               alert(data.msg);
                            },'json');
                    }
                }else{
                    uploadByHand();
                }
            });
        }
    }

    /**
     * @Description 上传满足查询条件的合格证信息
     * @Create huxx 2014-02-12
     */
    function uploadByConditions(){
        var url="${createLink(controller: "ZCInfoUpload",action:"uploadByHand")}";
        $.omMessageBox.confirm({
            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
            content:'确定上传满足条件的合格证信息吗？',
            onClose:function(v){
                if(v){
                    var dialogId=showTipDialog();
                    $("#div_message").html("");
                    $.post(url,$('#form_query').serialize(),function(data,textstatus){
                        parent.closeDialog(dialogId);
                        if(textstatus=='success'){
                            $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+data.msg+"</div>");
                        }else{
                            $("#div_message").html("<div style='padding: 15px 15px 15px 15px;'>"+"请求失败！"+"</div>");
                        }
                    });
                }
            }
        });
    }

    /**
     * @Description 条件验证选中轮胎规格是否符合公告信息并上传
     * @Create QJ 2017-08-22
     */
    function checkAndUploadByConditions(){
        var urlCheck="${createLink(controller: "ZCInfoUpload",action:"checkLtgg")}";
            $("#div_message").html("");
            $.post(urlCheck,$('#form_query').serialize(),function(dd,textStatus){
                var d2=eval(dd);
                if(d2.flag=='failed'){
                    var a=confirm(d2.msg);
                    if(a==true){
                        uploadByConditions();
                    }else{
                        var ids= d2.failIds;
                        var updateUrl="${createLink(controller: 'ZCInfoUpload',action:'checkFailedLtgg')}";
                        var params=$("#form").serialize();
                        $.post(updateUrl,{ids:ids},function(data,textStatus){
                        alert(data.msg);
                        },'json');
                    }
                }else{
                    uploadByConditions();
                }
            });
    }

    function buildRightGrid(){
        $('#zcinfo_grid').omGrid({
            dataSource:'${createLink(controller:'ZCInfoUpload',action:'jsonUploadSucList')}?isUpload=0&searchType=hand',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :500,
            colModel:[
                {header : "${message(code: 'zcinfo.opertion.label', default: 'print')}", name : 'id', width: 100, align:'center', renderer:
                        function(value, rowData, rowIndex){
                            var data = rowData;
                            return '<a id="btn_view" class="btn_basic blue_black"  href="javascript:gotoview('+rowIndex+');" >查看</a>';
                        }},
                {header : "${message(code: 'zcinfo.veh_Zchgzbh.label', default: '整车合格证编号')}", name : 'veh_Zchgzbh', width : 110, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Dphgzbh.label', default: '底盘合格证编号')}", name : 'veh_Dphgzbh', width : 110, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Fzrq.label', default: '发证日期')}", name : 'veh_Fzrq', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clzzqymc.label', default: '车辆制造企业名称')}", name : 'veh_Clzzqymc', width : 130, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qyid.label', default: '企业ID')}", name : 'veh_Qyid', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clfl.label', default: '车辆分类')}", name : 'veh_Clfl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clmc.label', default: '车辆名称')}", name : 'veh_Clmc', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clpp.label', default: '车辆品牌')}", name : 'veh_Clpp', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clxh.label', default: '车辆型号')}", name : 'veh_Clxh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Csys.label', default: '车身颜色')}", name : 'veh_Csys', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Dpxh.label', default: '底盘型号')}", name : 'veh_Dpxh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Dpid.label', default: '底盘ID')}", name : 'veh_Dpid', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clsbdh.label', default: '车辆识别代码')}", name : 'veh_Clsbdh', width : 130, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Cjh.label', default: '车架号')}", name : 'veh_Clsbdh', width : 120, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Fdjh.label', default: '发动机号')}", name : 'veh_Fdjh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Fdjxh.label', default: '发动机型号')}", name : 'veh_Fdjxh', width : 380, align : 'left'},
                {header : "${message(code: 'zcinfo.veh_Rlzl.label', default: '燃料种类')}", name : 'veh_Rlzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Pl.label', default: '排量')}", name : 'veh_Pl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Gl.label', default: '功率')}", name : 'veh_Gl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zxxs.label', default: '转向形式')}", name : 'veh_Zxxs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qlj.label', default: '前轮距')}", name : 'veh_Qlj', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hlj.label', default: '后轮距')}", name : 'veh_Hlj', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Lts.label', default: '轮胎数')}", name : 'veh_Lts', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Ltgg.label', default: '轮胎规格')}", name : 'veh_Ltgg', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Gbthps.label', default: '钢板弹簧片数')}", name : 'veh_Gbthps', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zj.label', default: '轴距')}", name : 'veh_Zj', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zh.label', default: '轴荷')}", name : 'veh_Zh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zs.label', default: '轴数')}", name : 'veh_Zs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Wkc.label', default: '外廓长')}", name : 'veh_Wkc', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Wkk.label', default: '外廓宽')}", name : 'veh_Wkk', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Wkg.label', default: '外廓高')}", name : 'veh_Wkg', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hxnbc.label', default: '货箱内部长')}", name : 'veh_Hxnbc', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hxnbk.label', default: '货箱内部宽')}", name : 'veh_Hxnbk', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hxnbg.label', default: '货箱内部高')}", name : 'veh_Hxnbg', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zzl.label', default: '总质量')}", name : 'veh_Zzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Edzzl.label', default: '额定载质量')}", name : 'veh_Edzzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zbzl.label', default: '整备质量')}", name : 'veh_Zbzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zzllyxs.label', default: '载质量利用系数')}", name : 'veh_Zzllyxs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zqyzzl.label', default: '准牵引总质量')}", name : 'veh_Zqyzzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Edzk.label', default: '额定载客')}", name : 'veh_Edzk', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Bgcazzdyxzzl.label', default: '半挂车按座')}", name : 'veh_Bgcazzdyxzzl', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Jsszcrs.label', default: '驾驶室准乘人数')}", name : 'veh_Jsszcrs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qzdfs.label', default: '前制动方式')}", name : 'veh_Qzdfs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Hzdfs.label', default: '后制动方式')}", name : 'veh_Hzdfs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qzdczfs.label', default: '前制动操作方式')}", name : 'veh_Qzdczfs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Cpggh.label', default: '产品公告号')}", name : 'veh_Cpggh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Ggsxrq.label', default: '公告生效日期')}", name : 'veh_Ggsxrq', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zzbh.label', default: '纸张编号')}", name : 'veh_Zzbh', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Dywym.label', default: '打印唯一码')}", name : 'veh_Dywym', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Zgcs.label', default: '最高车速')}", name : 'veh_Zgcs', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clzzrq.label', default: '车辆制造日期')}", name : 'veh_Clzzrq', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Bz.label', default: '备注')}", name : 'veh_Bz', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qybz.label', default: '企业标准')}", name : 'veh_Qybz', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Cpscdz.label', default: '产品生产地址')}", name : 'veh_Cpscdz', width : 130, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Qyqtxx.label', default: '企业其它信息')}", name : 'veh_Qyqtxx', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Pfbz.label', default: '排放标准')}", name : 'veh_Pfbz', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Clztxx.label', default: '车辆状态信息')}", name : 'veh_Clztxx', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Jss.label', default: '驾驶室')}", name : 'veh_Jss', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_Jsslx.label', default: '驾驶室类型')}", name : 'veh_Jsslx', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.veh_VinFourBit.label', default: 'VIN第四位')}", name : 'veh_VinFourBit', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.createrId.label', default: '创建人')}", name : 'createrId', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.createTime.label', default: '创建时间')}", name : 'createTime', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.updaterId.label', default: '更新人')}", name : 'updaterName', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.updateTime.label', default: '更新时间')}", name : 'updateTime', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.uploader_Id.label', default: '上传人')}", name : 'uploaderName', width : 80, align : 'center'},
                {header : "${message(code: 'zcinfo.uploader_Time.label', default: '上传时间')}", name : 'uploader_Time', width : 80, align : 'center'}
            ]
        });
    }

    //转向查看页面
    function gotoview(index){
        var data = $("#zcinfo_grid").omGrid("getData").rows[index];
        var url = '${createLink(controller:'ZCInfoUpload',action:'show')}?id='+data.id+'&suctype=2';
        window.location.href = url;
    }
</script>
</body>
</html>