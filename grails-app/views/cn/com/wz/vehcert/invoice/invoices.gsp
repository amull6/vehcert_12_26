<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="票据关联" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <g:if test="${flash.message}">
            <div class="message" role="status" style="color: red;max-width:100%;white-space:normal; display:block; word-break:break-all">${flash.message}</div>
        </g:if>
        <form id="form_query" style="margin:8px;height:30px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="80"><span>票据号：</span></td>
                    <td width="100"><span><g:textField id="invNo" name="invNo" maxlength="200" /></span></td>
                    <td width='20'></td>
                    <td width="80"><span>状态：</span></td>
                    <td width="100"><span><select name="needSol" id="needSol">
                        <option  value="2">全部</option>
                        <option selected="selected" value="1">需关联</option>
                        <option value="0">不需关联</option>

                    </select></span></td>
                    <td width='20'></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                <input id="btn_relate" type="button" class="create" value="系统关联票据"/>
                <input id="btn_relateByHand" type="button" class="create" value="手动关联票据"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="content_grid" style="border:0px"></div>
        </div>
    </div>
    <input type="hidden" id="goodsInfo" />
</div>

<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});

        $('#btn_relate').click(function(){
            relativeForMany(0);
        });
        $('#btn_relateByHand').click(function(){
            relativeForMany(1);
        });

        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'uploadInvoice',action:'invoiceJsonList')}";
                url+='?'+$('#form_query').serialize();
                $('#content_grid').omGrid('setData',url);

            }
        });

        //绑定查询按钮事件
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#invNo").val('');
                $("#needSol").val('');
            }
        });

    })

    function relativeForMany(opFlag){
        var url ="";
        if(opFlag==0){
            url="${createLink(controller: 'uploadInvoice',action: 'relateInvoicesForMany')}"
        }else{
            url="${createLink(controller: 'uploadInvoice',action: 'relateInvoicesByHand')}"
        }
        var selections= $('#content_grid').omGrid('getSelections',true);
        if(selections.length==0){
            alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
            return;
        }
        var invNos = '';
        $.each(selections,function(i,item){
            invNos += item.invNo+'_';
        });
        var dialogId=showTipDialog();
        $.post(url,{invNos:invNos},function(data,textStatus){
            parent.closeDialog(dialogId);
            if(textStatus=='success'){
                if(data.flag=='success'){
                    $('#content_grid').omGrid('reload');
                    alert(data.msg);
                }else{
                    alert(data.msg);
                }
            }else{
                alert("AJAX出现错误！请检查网络是否有问题！");
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
        buildRightGrid();
    });
    function guanlian(invNo,opFlag){
        var dialogId=showTipDialog();
        var url="";
        if(opFlag==0){
            url="${createLink(controller: 'uploadInvoice',action: 'relateInvoicesForMany')}"
        }else{
            url="${createLink(controller: 'uploadInvoice',action: 'relateInvoicesByHand')}"
        }
        $.post(url,{invNos:invNo},function(data,textStatus){
            parent.closeDialog(dialogId);
              if(textStatus=='success'){
                   if(data.flag=='success'){
                       $('#content_grid').omGrid('reload');
                       alert(data.msg);
                   }else{
                       alert(data.msg);
                   }
              }else{
                  alert('AJAX提交失败！');
              }
        });
    }
    function buildRightGrid(){
        $('#content_grid').omGrid({
            dataSource:'${createLink(controller:'uploadInvoice',action:'invoiceJsonList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 10,
            height :440,
            colModel:[
                {header:'操作',name:"",width:200,align:'center',renderer:
                  function(value,rowData,rowIndex){
                      var str='<a id="btn_view" class="btn_basic blue_black"  href="javascript:guanlian('+"'"+rowData.invNo+"',0"+');" >系统关联票据</a>'+
                              '<a id="btn_viewByHand" style="margin-left: 10px;" class="btn_basic blue_black"  href="javascript:guanlian('+"'"+rowData.invNo+"',1"+');" >手动关联票据</a>'
                      return str;
                  }
                },
                {header:"票据号",name:"invNo",width:400,align:'center'},
                {header : "票据类别码", name : 'typeCode', width : 120, align : 'center'} ,
                {header : "上传状态", name : 'resultCode', width : 100, align : 'center',  renderer:
                    function(value, rowData, rowIndex){
                         if(value=='0'){
                             return "上传成功";
                         }else{
                             return "部分上传失败";
                         }
                    }
                },
                {header : "返回信息", name : 'message', width : 400, align : 'center'}
            ] ,
            rowDetailsProvider:function(rowData,rowIndex){
                var str="<table class='table_list'><thead ><tr><th  width='20px'>序号</th><th width='200px' class='col1' >货品名称</th><th style='text-align: center;width:80px;'>货品规格</th><th style='text-align: center;width:40px;'>数量</th><th style='text-align: center;width:100px;'>状态</th><th style='width:300px;'>完整合格证编号</th><th>车架号</th><th>发动机号</th><th>信息</th></tr></thead><tbody>";
                $.each(rowData.goodsInfos,function(count,value){
                    count+=1 ;
                    var ggxh="";
                    if(value.ggxh && !(value.ggxh.toUpperCase()=='NULL')){
                       ggxh=value.ggxh;
                    }
                    var status="";
                    if(value.hgzs){
                        var lst=value.hgzs.split(",");
                        if(lst.length==value.sl){
                            if(value.needSol==1){
                                status="<span style='color:red;'>需关联（"+lst.length+"/"+value.sl+")</span>";
                            }else{
                                status="不需关联（"+lst.length+"/"+value.sl+")";
                            }

                        }else{
                            status="<span style='color:red;'>需要关联（"+lst.length+"/"+value.sl+")</span>";
                        }
                    }else{
                        status="<span style='color:red;'>需要关联（0/"+value.sl+")</span>";
                    }

                    var hgzs=""
                    if(value.hgzs && !(value.hgzs.toUpperCase()=='NULL')){
                        hgzs=value.hgzs;
                    }

                    str+="<tr><td style='text-align: center;'><div>"+count+"</div></td><td><div style='width:200px;word-wrap : break-word; overflow:hidden;white-space:normal;  '>"+value.spmc+"</div></td><td style='text-align: center;'><div>"+
                            ggxh+"</div></td><td style='text-align: center;'><div style='background-color:#a9a9a9;cursor: pointer;font-weight: bold;width:40px;' ><a href='${createLink(controller: "uploadInvoice",action:"showDetail")}?id="+value.id+"'><div style='style='width:40px;'> "+value.sl +"</div></a></div></td><td style='text-align: center;'><div>"+
                            status+"</div></td><td > <div style='width:230px;word-wrap: break-word;overflow: hidden;white-space:normal; '>"+hgzs+"</div></td><td><div style='width:255px;word-wrap : break-word; overflow:hidden;white-space:normal;  '>"+value.vins+"</div></td><td><div style='width:140px;word-wrap : break-word; overflow:hidden;white-space:normal;  '>"+
                            value.fdjhs+"</div></td><td><div style='width:140px;word-wrap : break-word; overflow:hidden;white-space:normal;'>"+value.message+"</div></td></tr>";
                });
                str+="</tbody></table>" ;
                return str;
            }

        });
    }
</script>
</body>
</html>
