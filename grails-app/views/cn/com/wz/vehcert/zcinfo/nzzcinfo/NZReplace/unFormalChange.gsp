
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'workshop.label', default: 'Workshop')}" />
    <link rel="stylesheet" type="text/css" href="../css/om/apusic/om-all.css" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
    <style type="text/css">

    </style>
</head>
<body>
<div id="page" style="width:1350px;background-color:white;margin:0px 0px;overflow:visible;">
    <div style="margin-right:8px;padding-top:8px; height:98%;  width:100%;">

        <div style="height:100%; width:100%;" class="om-grid om-widget om-widget-content">


            <div class="bDiv" style="width:100%; height:98%;width: 100%;">
                <table id="grid" style="" cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <tr class="om-grid-row evenRow">
                        <th align="center" class="indexCol" colspan="17" style="width: 700px"><div align="center" class="innerCol " >合格证更换申请</div></th>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="center" class="indexCol" colspan="8"  style="width: 340px"><div align="center" class="innerCol " >原合格证</div></td>
                        <td align="center" class="indexCol" rowspan="29" colspan="1" style="width: 10px">
                            <div align="center" class="innerCol " ></div>
                        </td>
                        <td align="center" class="indexCol" colspan="8" style="width: 340px"><div align="center" class="innerCol " >更换后合格证</div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >车辆编号/底盘号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="3" >
                            <div align="left" class="innerCol ">
                                <input type="text"  style="width: 100px" id="relate_value" value="${NZzcinfModel?.veh_Clbh}"><input type="button" id="relate" value='关联'>
                            </div>
                        </td>
                        <td align="left" abbr="city" class="col1" colspan="1" width="80px"><div align="left" class="innerCol "  ><input type="button"  id="write" value='手动填写'></div></td>
                        <td align="left" abbr="id" class="col0" colspan="8"  style="width: 340px"><div align="left" class="innerCol "></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol "col style="width:100px" >1.车辆编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id='veh_Clbh'>${NZzcinfModel?.veh_Clbh}</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " style="width:100px">1.车辆编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol" id='veh_Clbh_R' style="width:180px"></div></td>
                    </tr>


                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >2.底盘号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol "  id="veh_Dph">${NZzcinfModel?.veh_Dph}</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >2.底盘号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Dph_R"></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >3.车型</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol "  id="veh_Cx">${NZzcinfModel?.veh_Cx}</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >3.车型</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Cx_R">${NZzcinfModel?.veh_Cx}</div></td>

                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" colspan="4" class="indexCol"><div align="left" class="innerCol ">4.出厂日期</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Ccrq">${NZzcinfModel?.veh_Ccrq}</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >4.出厂日期</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4">
                            <div align="left" class="innerCol " id="veh_Ccrq_R" >
                                ${NZzcinfModel?.veh_Ccrq}
                            </div>
                        </td>

                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >5.发动机号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Fdjh">${NZzcinfModel?.veh_Fdjh }</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >5.发动机号</div></td>
                        <td align="left" abbr="city" class="col1" colspan="4"><div align="left" class="innerCol " id="veh_Fdjh_R">${NZzcinfModel?.veh_Fdjh}</div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >6.合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " id="veh_Hgzbh">${NZzcinfModel?.veh_Hgzbh}</div></td>
                        <td align="left" class="indexCol" colspan="4"><div align="left" class="innerCol " >6.合格证编号</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " ></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol ">业务员：</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div><input  id="salesManName" type="text" value=""></div></td>
                    </tr>
                    <tr class="om-grid-row oddRow">
                        <td align="left" abbr="id" class="col0" colspan="4"><div align="left" class="innerCol " >业务员电话：</div></td>
                        <td align="left" abbr="id" class="col0" colspan="4"><div><input  id="salesManPhone" type="text" value=""></div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="center" vlign='middle' abbr="id" class="col0" colspan="1" style="color: red" >更换原因及要求<br>（不注明更换<br>原因的将予退回）:</td>
                        <td  align="left" vlign='middle' abbr="id" class="col0" colspan="16"><g:textArea name="remark" style="height: 60px;width: 518px;"></g:textArea> </td>
                    </td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="center" abbr="id" class="col0" colspan="17"><div align="center" class="innerCol ">
                            <form id="form" method="post">
                                <input type="hidden" value="${zid}" id="zid">
                                <input type="hidden" name='opFlag' value="${opFlag}" id="opFlag">
                                <input type="hidden" value="" id="newId">
                                <input type="hidden" value="${statusWrite}" id="statusWrite">
                                <span id="yincang"><input type='button' id="newOld" value="提交申请" style="width: 70px; height: 30px;" class="btn_basic blue_black"></span>
                            </form>
                        </div></td>
                    </tr>
                    <tr class="om-grid-row evenRow">
                        <td align="center" abbr="id" class="col0" colspan="17"><div align="left" class="innerCol " style="font-weight: 600;">备注：<br/>一、变更后的合格证各项参数只能与车辆公告相符，与实际车型不符的要以合格证为准。<br/>
                            二、若因更换车辆手续等相关事宜引起的纠纷由申请单位负完全责任与山东五征集团有限公司无关。（若提出更换申请已表明承认上述条款）</div></td>
                    </tr>
                    </tbody>
                </table>

            </div>

        </div>
    </div>

</div>
<script>
    $(function() {
        $('#relate').omButton({
            width:40,
            onClick:function(){
                var veh_Clbh=$('#relate_value').val();
                var opFlag=$('#opFlag').val();
                if(veh_Clbh!=''){
                    var url = "${createLink(controller:'NZInfo',action:'fingNZzcinfoByClbh')}?veh_Clbh="+veh_Clbh+"&opFlag="+opFlag;
                    window.location.href=url;
                }else{
                    alert('请输入车辆编号！');
                }
            }
        });
        $('#newOld').click(function (){
            $(this).css('background','#c1c1c1');
            $(this).css('color','#000');
            var newId=$('#newId').val();

            var veh_Clbh= $('#veh_Clbh').html();
//            var veh_Clbh_R= $('#veh_Clbh_R').html();
//
            var veh_Dph=$('#veh_Dph').html();
//            var veh_Dph_R=$('#veh_Dph_R').html();

            var veh_Cx=$('#veh_Cx').html();
            var veh_Cx_R=$('#veh_Cx_R').html();

            var veh_Ccrq=$('#veh_Ccrq').html();
            var veh_Ccrq_R=$('#veh_Ccrq_R').html().trim();

            var veh_Fdjh=$('#veh_Fdjh').html();
            var veh_Fdjh_R=$('#veh_Fdjh_R').html();

            var veh_Hgzbh=$('#veh_Hgzbh').val();

            var salesManName=$('#salesManName').val();
            var salesManPhone= $('#salesManPhone').val();
            var remark= $('#remark').val();
            var zid =$('#zid').val();
            var statusWrite =$('#statusWrite').val();
            var opFlag=$('#opFlag').val();
            if(remark==''||remark==null){
                alert('请填写更换原因及要求');
                return
            }

            var url= '${createLink(controller:'NZzcinfoReplaceAuth',action:'nzZcinfoReplaceSave')}?veh_Clbh='+veh_Clbh+'&salesManName='+salesManName+'&salesManPhone='+salesManPhone+'&remark='+remark+'&change_Field=veh_Clbh';
            url=encodeURI(url);
            if(veh_Ccrq_R&&veh_Fdjh_R&&veh_Cx_R){
                $(this).attr('disabled','disabled');
                $.post(url,{newId:newId,veh_Cx_R:veh_Cx_R,veh_Fdjh_R:veh_Fdjh_R,veh_Ccrq_R:veh_Ccrq_R,zid:zid,statusWrite:statusWrite},function(data,textStatus){
                    if(textStatus){
                        $.omMessageBox.alert({
                            type:'success',
                            title:'提示',
                            content:data,
                            onClose:function(value){
                                var url = '${createLink(controller:'NZInfo',action:'nzInfoUNFormalChange')}?opFlag='+opFlag;
                                window.location.href=url;
                            }
                        });
                    }
                },'text');
            }

        });

        $('#write').omButton({

            width:70,
            onClick:function(){
                var opFlag=$('#opFlag').val();
                var url = '${createLink(controller:'NZInfo',action:'nzZcinfoRondWrite')}?opFlag='+opFlag;
                window.location.href=url;
            }
        });

        //绑定下载按钮事件
        $('#btn_down').omButton({
            width:40,
            onClick:function(){
                var url = "${createLink(controller:'ZCInfo',action:'download_ZCI')}";
                url+='?'+$('#form_query').serialize();
                if($("#hidden_id").val()==""){
                    alert("请查询要下载的合格证信息!");
                    return
                }
                $.post(url,{"ids":"i"},function(data,textStatus){
                    alert(data);
                },'text');
            }
        });
        //绑定清空按钮事件
        $('#btn_clear').omButton({
            width:40,
            onClick:function(){
                $("#hidden_id").val("")
                var url = "${createLink(controller:'ZCInfo',action:'index_ZCI')}";
                window.location.href = url;
            }
        });

    })
    function changeData(data){
        $('#newId').val(data.id);
        $('#veh_Edzk').html(data.veh_Edzk) ;
        $('#veh_Zs').html(data.veh_Zs) ;
        $('#veh_Zqyzzl').html(data.veh_Zqyzzl) ;
        $('#veh_Bgcazzdyxzzl').html(data.veh_Bgcazzdyxzzl) ;
        $('#veh_Fdjxh').html(data.veh_Fdjxh)//發動機型號
        $('#veh_zdjgl').html(data.veh_zdjgl)//净功率
        $('#veh_Clfl').html(data.veh_Clfl)    //车辆类型
        $('#veh_Clxh').html(data.veh_Clxh)     //车辆型号
        $('#veh_Zj').html(data.veh_Zj)            //轴距
        $('#veh_Pl').html(data.veh_Pl)           //排量和功率
        $('#veh_Gl').html(data.veh_Gl)
        $('#veh_Zzl').html(data.veh_Zzl)        //总质量
        $('#veh_Clmc').html(data.veh_Clmc)      //车辆名称
        $('#veh_Edzzl').html(data.veh_Edzzl)    //额定载质量
        $('#veh_Pfbz').html(data.veh_Pfbz)   //排放标准
        $('#veh_Hxnbc').html(data.veh_Hxnbc)     //货厢内部尺寸
        $('#veh_Hxnbk').html(data.veh_Hxnbk)     //货厢内部尺寸
        $('#veh_Hxnbg').html(data.veh_Hxnbg)      //货厢内部尺寸
        $('#veh_Zxxs').html(data.veh_Zxxs)     //转向形式
        $('#veh_Zbzl').html(data.veh_Zbzl)     //整备质量
        $('#veh_Zh').html(data.veh_Zh)      //轴荷
        $('#veh_Wkc').html(data.veh_Wkc)     //外廓尺寸
        $('#veh_Wkk').html(data.veh_Wkk)     //外廓尺寸
        $('#veh_Wkg').html(data.veh_Wkg)      //外廓尺寸
        $('#veh_Zzllyxs').html(data.veh_Zzllyxs)      //载质量利用系数
        $('#veh_Qyqtxx').html(data.veh_Qyqtxx)      //车辆制造企业其他信息
        $('#veh_Dpid').html(data.veh_Dpid)      //底盘ID
        $('#veh_Dpxh').html(data.veh_Dpxh)      //底盘型号
        $('#veh_Jsslx').html(data.veh_Jsslx)      //驾驶室类型
        //$('#veh_clzzqyxx').html(data.veh_clzzqyxx)      //车辆制造企业信息
        $('#veh_Yh').html(data.veh_Yh)      //油耗
        $('#veh_Ggpc').html(data.veh_Ggpc)      //公告批次
        $('#veh_Cpggh').html(data.veh_Cpggh)      //产品公告号
        $('#veh_Gbthps').html(data.veh_Gbthps)     //钢板弹簧片数
        $('#veh_Clpp').html(data.veh_Clpp)     //车辆品牌
        $('#veh_Lts').html(data.veh_Lts)      //轮胎数
        $('#veh_Clzzqymc').html(data.veh_Clzzqymc)     //车辆企业名称
        $('#veh_Cpscdz').html(data.veh_Cpscdz)      //生产地址
        $('#veh_Qybz').html(data.veh_Qybz)     //企业标准
        $('#veh_Zgcs').html(data.veh_Zgcs)      //最高车速
        $('#veh_Rlzl').html(data.veh_Rlzl)     //燃料种类
        $('#veh_Qlj').html(data.veh_Qlj)     //轮距
        $('#veh_Hlj').html(data.veh_Hlj)      //轮距
        $('#veh_Ltgg').html(data.veh_Ltgg)     //轮胎规格
        $('#veh_Jsszcrs').html(data.veh_Jsszcrs)     //驾驶室准乘人数
        $('#veh_Bz').html(data.veh_Bz)      //备注
        $('#veh_pzxlh').html(data.veh_pzxlh)      //配置序列号
        $('#veh_Qyid').html(data.veh_Qyid)      //企业ID
        $('#veh_Hzdfs').html(data.veh_Hzdfs)
        $('#veh_Clztxx').html(data.veh_Clztxx)
        $('#veh_Jss').html(data.veh_Jss)      //驾驶室
        $('#veh_VinFourBit').html(data.veh_VinFourBit)      //vin第四位
    }
    function create(url){
        var tabId=window.name;
        var flag=url.indexOf('?');
        if(flag>0){
            url+="&tabId="+tabId;
        }else{
            url+="?tabId="+tabId;
        }
        var content='<iframe id="gonggao_dialog" style="margin-left:-5px;margin-top:-10px;'+
                'margin-right:-500px;text-align:center; width:100%;height:100%" '+
                'scrolling="yes" src="'+url+'"></iframe>';
        parent.showDialog(1,content,"公告信息",1400,900);
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
