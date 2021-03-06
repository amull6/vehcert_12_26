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

        <form id="form_query" style="margin:8px;height:60px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80px" align="right"><span style="width: 80px"><g:message code="carstorage.veh_Clxh.label" default="车辆型号" />:</span></td>
                    <td width="60px" align="left"><span><g:textField id="veh_Clxh" name="veh_Clxh" style="width:120px;"  value=""/></span></td>
                    <td width="100px" align="right"><span><g:message code="carstorage.veh_Hxnbc.label" default="货箱内部长" />:</span></td>
                    <td width="60px" align="left"><g:textField id="veh_Hxnbc" style="width:60px;" name="veh_Hxnbc"  value=""/></td>
                    <td width="100px" colspan='1' align="right"><span><g:message code="carstorage.veh_Ltgg.label" default="轮胎规格" />:</td>
                    <td><g:textField id="veh_Ltgg" name="veh_Ltgg" style="width:100px;"  value=""/></td>

                    <td width="100px" align="right"><span><g:message code="carstorage.veh_Jsszcrs.label" default="驾驶室准乘人数" />: </td>
                    <td width="60px" align="left"><span><g:textField id="veh_Jsszcrs" name="veh_Jsszcrs" style="width:60px;"  value=""/></span></td>

                    <td width="100px" align="right"><span><g:message code="carstorage.car_storage_no.label" default="公告唯一号" />: </td>
                    <td width="100px" align="left"><span><g:textField id="car_storage_no" name="car_storage_no" style="width:180px;"  value=""/></span></td>

                </tr>
                <tr>
                    <td width="80px" align="right"><span style="width: 80px"><g:message code="carstorage.veh_Fdjxh.label" default="发动机型号" />:</span></td>
                    <td width="60px" align="left"><span ><g:textField id="veh_Fdjxh" name="veh_Fdjxh"  style="width: 100px"  value=""/></span></td>
                    <td width="100" align="right"><span><g:message code="carstorage.veh_Jsslx.label" default="驾驶室类型" />:</span></td>
                    <td width="60" align="left"><span><g:textField id="veh_Jsslx" name="veh_Jsslx" style="width:60px;" value=""/></span></td>
                    <td width="100" align="right"><span><g:message code="carstorage.veh_Zj.label" default="轴距" />: </td>
                    <td width="60" align="left" ><span><g:textField id="veh_Zj" name="veh_Zj" style="width:100px;"  value=""/></span></td>
                    <td width="100" align="right"><span><g:message code="carstorage.veh_Yhlx.label" default="油耗类型" />: </td>
                    <td width="60" align="left" >
                        <span>
                            <g:select id="veh_Yhlx" optionKey="queryBy" optionValue="queryShow" name="veh_Yhlx"  value='${params?.veh_Yhlx}'
                                      from="${[[queryBy:"1",queryShow:"有"]
                                              ]}" noSelection="['0':'无']" style="width:85px;height:25px;">
                            </g:select>
                        </span>
                    </td>
                    <td  valign="bottom" align="right">
                        <input type="hidden" id="veh_Need_Cer" name="veh_Need_Cer" value="${veh_Need_Cer}">
                        <input type="hidden" id="turnOff" name="turnOff" value="${turnOff}">
                            <input id="btn_query" type="button" width="60" value="<g:message code="default.button.query.label" default="Query" />"/>
                    </td>
                    <td>
                        <input id="btn_clear" type="button" width="60"  value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>

                </tr>
            </table>
        </form>
        <div style="margin-right:8px;margin-top:8px;" >
            <div id="notice_content" style="border:0px;height:100%"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});
        $('#veh_Hxnbc').omCombo({
            multi : false
        });
        $('#veh_Fdjxh').omCombo({
            multi : false
        });
        $('#veh_Jsslx').omCombo({
            multi : false
        });
        $('#veh_Ltgg').omCombo({
            multi : false
        });

        $('#veh_Zj').omCombo({
            multi : false
        });
        $('#veh_Jsszcrs').omCombo({
            multi : false
        });
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfoNoAuth',action:'jsonNoticeNoAuth')}";
                url+='?'+$('#form_query').serialize();
                $('#notice_content').omGrid('setData',url);


            }
        });
        $("#veh_Clxh").change( function() {
            var veh_Clxh=$('#veh_Clxh').val()
            if(veh_Clxh!=''){
                $('#veh_Hxnbc').omCombo('value','')
                $('#veh_Fdjxh').omCombo('value','')
                $('#veh_Jsslx').omCombo('value','')
                $('#veh_Hxnbc').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Hxnbc&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                $('#veh_Fdjxh').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Fdjxh&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                $('#veh_Jsslx').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Jsslx&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                $('#veh_Ltgg').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Ltgg&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                $('#veh_Zj').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Zj&veh_Clxh="+veh_Clxh+"&random="+Math.random());
                $('#veh_Jsszcrs').omCombo('setData',"${createLink(controller:'ZCInfo',action:'jsonInfoSuggestion')}?field=veh_Jsszcrs&veh_Clxh="+veh_Clxh+"&random="+Math.random());
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#veh_Clxh").val('');
                $("#veh_Hxnbc").val('');
                $("#veh_Fdjxh").val('');
                $("#veh_Jsslx").val('');
                $("#veh_Ltgg").val('');
                $("#veh_Zj").val('');
                $("#veh_Jsszcrs").val('');
                $("#car_storage_no").val('');

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

        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.iframeAuto(index);
        parent.$('#carstorageId').val(data.id);
        parent.$('#veh_Fdjxh').val(data.veh_Fdjxh);
        parent.$('#web_virtualcode').html(data.web_virtualcode);
        parent.$('#veh_Dphgzbh').html(data.veh_Dphgzbh) ;
        parent.$('#veh_Cjh').html(data.veh_Cjh) ;
        parent.$('#veh_Edzk').html(data.veh_Edzk) ;
        parent.$('#veh_Zs').html(data.veh_Zs) ;
        parent.$('#veh_Zqyzzl').html(data.veh_Zqyzzl) ;
        parent.$('#veh_Bgcazzdyxzzl').html(data.veh_Bgcazzdyxzzl) ;
        parent.$('#veh_zdjgl').html(data.veh_zdjgl)//净功率
        parent.$('#veh_Clfl').html(data.veh_Clfl)    //车辆类型
        parent.$('#veh_Clxh').html(data.veh_Clxh)     //车辆型号
        parent.$('#veh_Zj').html(data.veh_Zj)            //轴距
        parent.$('#veh_Pl').html(data.veh_Pl)           //排量和功率
        parent.$('#veh_Gl').html(data.veh_Gl)
        parent.$('#veh_Zzl').html(data.veh_Zzl)        //总质量
        parent.$('#veh_Clmc').html(data.veh_Clmc)      //车辆名称
        parent.$('#veh_Edzzl').html(data.veh_Edzzl)    //额定载质量
        parent.$('#veh_Pfbz').html(data.veh_Pfbz)   //排放标准
        parent.$('#veh_Hxnbc').html(data.veh_Hxnbc)     //货厢内部尺寸
        parent.$('#veh_Hxnbk').html(data.veh_Hxnbk)     //货厢内部尺寸
        parent.$('#veh_Hxnbg').html(data.veh_Hxnbg)      //货厢内部尺寸
        parent.$('#veh_Zxxs').html(data.veh_Zxxs)     //转向形式
        parent.$('#veh_Zbzl').html(data.veh_Zbzl)     //整备质量
        parent.$('#veh_Zh').html(data.veh_Zh)      //轴荷
        parent.$('#veh_Wkc').html(data.veh_Wkc)     //外廓尺寸
        parent.$('#veh_Wkk').html(data.veh_Wkk)     //外廓尺寸
        parent.$('#veh_Wkg').html(data.veh_Wkg)      //外廓尺寸
        parent.$('#veh_Zzllyxs').html(data.veh_Zzllyxs)      //载质量利用系数
        parent.$('#veh_Qyqtxx').html(data.veh_Qyqtxx)      //车辆制造企业其他信息
        parent.$('#veh_Dpid').html(data.veh_Dpid)      //底盘ID
        parent.$('#veh_Dpxh').html(data.veh_Dpxh)      //底盘型号
        parent.$('#veh_Jsslx').html(data.veh_Jsslx)      //驾驶室类型
        parent.$('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
        parent.$('#veh_Yh').html(data.veh_Yh)      //油耗
        parent.$('#veh_Ggpc').html(data.veh_Ggpc)      //公告批次
        parent.$('#veh_Cpggh').html(data.veh_Cpggh)      //产品公告号
        parent.$('#veh_Gbthps').html(data.veh_Gbthps)     //钢板弹簧片数
        parent.$('#veh_Clpp').html(data.veh_Clpp)     //车辆品牌
        parent.$('#veh_Lts').html(data.veh_Lts)      //轮胎数
        parent.$('#veh_Clzzqymc').html(data.veh_Clzzqymc)     //车辆企业名称
        parent.$('#veh_Qybz').html(data.veh_Qybz)     //企业标准

        parent.$('#veh_Zgcs').html(data.veh_Zgcs)      //最高车速
        parent.$('#veh_Rlzl').html(data.veh_Rlzl)     //燃料种类
        parent.$('#veh_Qlj').html(data.veh_Qlj)     //轮距
        parent.$('#veh_Hlj').html(data.veh_Hlj)      //轮距
        parent.$('#veh_Ltgg').html(data.veh_Ltgg)     //轮胎规格
        parent.$('#veh_Jsszcrs').html(data.veh_Jsszcrs)     //驾驶室准乘人数
        parent.$('#veh_Bz').html(data.veh_Bz)      //备注
        parent.$('#veh_pzxlh').html(data.veh_pzxlh)      //配置序列号
        parent.$('#veh_Qyid').html(data.veh_Qyid)      //企业ID
        parent.$('#veh_Hzdfs').html(data.veh_Hzdfs)
        parent.$('#veh_Clztxx').html(data.veh_Clztxx)
        parent.$('#veh_Jss').html(data.veh_Jss)      //驾驶室
        parent.$('#veh_VinFourBit').html(data.veh_VinFourBit)

        parent.layer.close(index);//关闭窗口
    }
    var callbackdata = function () {
        var data= 'sss';
        return data;
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
                {header : "${message(code: 'carstorage.veh_Clxh.label')}", width : 80, align : 'center',name : 'veh_Clxh'},
                {header : "${message(code: 'carstorage.car_storage_no.label')}", width : 160, align : 'center',name : 'car_storage_no'},
                {header : "${message(code: 'carstorage.veh_Hxnbc.label')}", width : 80, align : 'center',name : 'veh_Hxnbc'},
                {header : "${message(code: 'carstorage.veh_Hxnbk.label')}", width : 80, align : 'center',name : 'veh_Hxnbk'},
                {header : "${message(code: 'carstorage.veh_Hxnbg.label')}", width : 80, align : 'center',name : 'veh_Hxnbg'},
                {header : "${message(code: 'carstorage.veh_Wkc.label')}", width : 80, align : 'center',name : 'veh_Wkc'},
                {header : "${message(code: 'carstorage.veh_Wkk.label')}", width : 80, align : 'center',name : 'veh_Wkk'},
                {header : "${message(code: 'carstorage.veh_Wkg.label')}", width : 80, align : 'center',name : 'veh_Wkg'},
                {header : "${message(code: 'carstorage.veh_Edzzl.label')}", width : 80, align : 'center',name : 'veh_Edzzl'},
                {header : "${message(code: 'carstorage.veh_Zj.label')}", width : 80, align : 'center',name : 'veh_Zj'},
                {header : "${message(code: 'carstorage.veh_Qlj.label')}", width : 80, align : 'center',name : 'veh_Qlj'},
                {header : "${message(code: 'carstorage.veh_Hlj.label')}", width : 80, align : 'center',name : 'veh_Hlj'},
                {header : "${message(code: 'carstorage.veh_Gbthps.label')}", width : 80, align : 'center',name : 'veh_Gbthps'},
                {header : "${message(code: 'carstorage.veh_Jsszcrs.label')}", width : 80, align : 'center',name : 'veh_Jsszcrs'},
                {header : "${message(code: 'carstorage.veh_Pl.label')}", width : 80, align : 'center',name : 'veh_Pl'},
                {header : "${message(code: 'carstorage.veh_Ltgg.label')}", width : 80, align : 'center',name : 'veh_Ltgg'},
                {header : "${message(code: 'carstorage.veh_Fdjxh.label')}", width : 80, align : 'center',name : 'veh_Fdjxh'},
                {header : "${message(code: 'carstorage.veh_Clmc.label')}", width : 80, align : 'center',name : 'veh_Clmc'},
                {header : "${message(code: 'carstorage.veh_Yhlx.label')}", width : 80, align : 'center',name : 'veh_Yhlx'}
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
