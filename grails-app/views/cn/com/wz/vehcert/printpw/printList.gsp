<%@ page import="cn.com.wz.vehcert.oil.Oil" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'pre.label', default: 'oil')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="page" style="background-color:white;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td width="120"><span><g:message code="pre.userName.label" default="User Name" />：</span></td>
                    <td width="100"><span><g:textField id="tf_userName" name="userName" maxlength="200" value=""/></span></td>
                    <td width="60"></td>
                    <td width="120"><span><g:message code="pre.realName.label" default="RealName" />：</span></td>
                    <td width="100"><span><g:textField id="tf_realName" name="realName" maxlength="200" value=""/></span></td>
                    <td width="60"></td>
                    <td align="left" valign="middle">
                        <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
                        <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
                    </td>
                </tr>
            </table>

        </form>
        <fieldset class="buttons" style="margin:0px 8px 8px 4px;">
            <input id="btn_resetPassword" type="button"  class="reset" value="${message(code: 'default.button.resetPassword.label', default: 'Reset Password')}"/>
        </fieldset>
        <div style="margin-right:8px;margin-top:8px;">
            <div id="oil_grid" style="border:0px"></div>
        </div>
    </div>
</div>
<script>
    $(function() {
        $('#page').css({height:$(document).height()-16});

        //绑定查询按钮事件
        $('#btn_query').omButton({
            width:80,
            onClick:function(){
                var url = "${createLink(controller:'printPw',action:'pwdList')}";
                url+='?'+$('#form_query').serialize();
                $('#oil_grid').omGrid('setData',url);
            }
        });
        $('#btn_clear').omButton({
            width:80,
            onClick:function(){
                $("#tf_userName").val('');
                $("#tf_realName").val('');
            }
        });


        $("#tf_userName").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $("#tf_realName").keydown(function(e){
            if(e.keyCode==13){
                $('#btn_query').click();
                return false;
            }
        });
        $('#btn_resetPassword').click(function(){
            //这里利用Ajax删除数据
            var selections = $('#oil_grid').omGrid('getSelections',true);
            if(selections.length==0){
                alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                return;
            }
            $.omMessageBox.confirm({
                title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                content:'${message(code: 'default.button.resetPassword.confirm.message', default: 'Are you sure to reset password?')}',
                onClose:function(v){
                    if(v){
                        var resetUrl = '${createLink(controller:'printPw',action:'resetPassword')}';
                        var ids = '';
                        $.each(selections,function(i,item){
                            ids += item.id+'_';
                        });
                        $.post(resetUrl,{"ids":ids},function(data,textStatus){
                            var selections = $('#oil_grid').omGrid('reload');
                            if(data=='true'){
                                alert('重置成功');
                            }

                        },'text');
                    }
                }
            });
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
        //构建左侧树
        //buildLeftTree();
        buildRightGrid();
    });
    function buildRightGrid(){
        $('#oil_grid').omGrid({
            dataSource:'${createLink(controller:'printPw',action:'pwdList')}',
            title:"<g:message code="default.list.label" args="[entityName]"/>",
            singleSelect:false,
            limit : 12,
            height :440,
            colModel:[
                {header : "${message(code: 'pre.userName.label', default: 'userName')}", name : 'userName', width : 100, align : 'center'} ,
                {header : "${message(code: 'pre.realName.label', default: 'realName')}", name : 'realName', width : 150, align : 'center'},
                {header : "${message(code: 'pre.preName.label', default: 'preName')}", name : 'preName', width : 100, align : 'center'}]
        });
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
