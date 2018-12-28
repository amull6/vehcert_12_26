<%@ page import="cn.com.wz.parent.system.Preference" %>
<html>
<head>
    <title><g:message code="default.setPrint.label" default="Set Email" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/main.css')}" type="text/css">
    <script src="${resource(dir:"js",file:"parent/jquery-1.7.2.min.js") }" type="text/javascript" ></script>
</head>
<body>
<g:form action="save" >
    <fieldset>

        <div class="fieldcontain ${hasErrors(bean: preference, field: 'preValue', 'error')} required">
            <label for="preValue">
                <g:message code="preValue.label" default="preValue" />：
                <span class="required-indicator">*</span>
            </label>
            <g:passwordField id="preValue" name="preValue" required="" />
        </div>

    </fieldset>
    <fieldset class="buttons" >
        <input id="btn_save" style='margin-left:100px;' type="button"  class="save" value="${message(code: 'change.label', default: 'Change')}"/>
        <input id="btn_clear" type="button" class="delete" value="<g:message code="default.button.clear.label" default="Clear" />"/>
    </fieldset>
</g:form>
<script type="text/javascript">
    $(function() {
        $('#btn_clear').click(function(){

            $("#preValue").val('');

        });
        $("#btn_close").click(function(){
            parent.closeDialog(window.parent.document.getElementById("email_dialog").parentNode.id);
        });
        $('#btn_save').click(function(){
            //获得用户输入
            var preValue=$('#preValue').val();
            //声明url
            var url = '${createLink(controller:'PrintPw',action:'printRight')}';
            if(!preValue){
                alert("打印密码必须填写");
                return;
            }
            //判断新密码长度是否符合要求
            $.post(url,{"preValue":preValue},function(data,textStatus){

                if(data=="success"){
                    if(${type=='0'}){
                        parent.frames['${tabName}'].down();
                    }else{
                        parent.frames['${tabName}'].zcinfoPrint();
                    }
                    parent.closeDialog(window.parent.document.getElementById("imp_dialog").parentNode.id);
                }else if(data=='notRight'){
                    alert("密码错误");
                }else{
                    alert("您还没有设置打印密码");
                }
            },'text');
        });
    });
</script>
</body>
</html>
