<%--
   @Description 导入页面公共处理页面
                在关闭窗口后可以调用调用页面的自定义函数 backFunc(returnData) 这个需要在调用页面添加这个自定义函数
                弹出窗口的id必须为imp_dialog
   @Params redirectUrl导入处理方法路径  tabId 调用导入页面的页面的name
   @return returnData返回到调用页面的数据 errorMessages 返回的错误信息,多个错误信息使用&*_*&分割 count 记录总数 realCount实际导入的记录总数
   @Update 2013-07-29 huxx
--%>

<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'import.label', default: 'Import Page')}" />
    <style>
    ul,ul li{
        font-size: 14px;color: red;font-weight: bold;
    }
    </style>
</head>

<body>
<a href="#edit-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div id="edit-user" class="content scaffold-edit" role="main">
    <g:form method="post" >
        <input type="hidden" id="hidden_retunData"/>
        <fieldset class="form">
            <br/>
            <span style=' font-size:14px; font-weight:800;min-height:200px;'>
                <g:message code="default.importUrl.label" default="Import Url" />:
                <uploader:uploader id="importFile" params="[prefix:'temp']" multiple="false" url="${[controller:'uploadr', action:'uploadDocs'] }" >
                    <uploader:noScript>
                        <p>Why don't you get a decent browser?</p>
                    </uploader:noScript>
                    <uploader:onComplete>
                        <c:fileUpload1 id="importFile"></c:fileUpload1>
                    </uploader:onComplete>
                </uploader:uploader>
            <!-- fileType 的值可为 doc、image、video -->
                <c:fileUpload2  fileType="doc" id="importFile" fileNames="${fileNames }"></c:fileUpload2>
            </span>
        </fieldset>

        <fieldset class="buttons" style="margin:0px 8px 0px 4px;padding-left:20px;">
            <input id="save" type="button"  class="save" value="${message(code: 'default.button.define.label', default: 'Define')}"/>
            <input id="cancel" type="button" class="cancel" value="${message(code: 'default.button.close.label', default: 'Close')}">
        </fieldset>
    </g:form>
    <div id="div_result" style="margin-left:20px;margin-top:5px;font-size: 14px;font-weight: bold;"></div>
    <ul id="div_errorMessages">
    </ul>
</div>
<script type="text/javascript">
    $(function() {
        $("#btn_viewErrors").click(function(){
            if($("#div_errorMessages").attr("display")=='none'){
                $("#div_errorMessages").attr("display","inline");
            }else{
                $("#div_errorMessages").attr("display","none");
            }
        })
        $('#save').click(function(){
            $("#div_errorMessages").html("");
            $("#div_result").html("");
            var realPath = $("#importFile_uploadFileNames").val();
            if(realPath=='doc:_:'){
                alert('请选择上传文件！');
                return false;
            }
            if(realPath!=""){
                //打开进度提示框
                var dialogId=showModelDialog();
                var resetUrl=null;
                var url="${redirectUrl}";
                if(url!=null&&url.length>0){
                    resetUrl=url
                }else{
                    resetUrl = '${createLink(controller:'User',action:'impUsers')}';
                }

                $.post(resetUrl,{"filePath":realPath},function(data,textStatus){
                    var data= eval( "(" + data + ")" );
                    $("#hidden_retunData").val(data.returnData);
                    if(data.count>0){
                        if(data.errorMessages==''||data.errorMessages=='null'){
                            $("#div_result").html("成功导入"+data.count+"条数据！,用时"+data.timeCount+"S");
                            $("#div_errorMessages").attr("display","none");
                        }else{
                            $("#div_errorMessages").attr("display","inline");
                            var errorMessages=data.errorMessages;
                            addErrors(errorMessages);
                            $("#div_result").html("<span style='color:red;'>导入失败。错误信息如下:</div>");
                        }

                    }else if(data.count==-1){
                        $("#div_errorMessages").attr("display","inline");
                        var errorMessages=data.errorMessages;
                        addErrors(errorMessages);
                        $("#div_result").html("<span style='color:red;'>导入失败。错误信息如下:</div>");
//                        alert('文件中没有数据！');
//                        $("#div_errorMessages").attr("display","inline");
                    }else{
                        alert('文件中没有数据！');
                        $("#div_errorMessages").attr("display","inline");
                    }
//                    if(data.count>0){
//                        if(data.count==data.realCount){
//
//                            $("#div_result").html("成功导入"+data.count+"条数据！");
//                            $("#div_errorMessages").attr("display","none");
//                        }else{
//                            $("#div_errorMessages").attr("display","inline");
//                            var faildCount=data.count-data.realCount;
//                            var errorMessages=data.errorMessages;
//                            addErrors(errorMessages);
//                            $("#div_result").html("<span style='color:red;'>成功导入"+data.realCount+"条数据！有"+faildCount+"条导入失败。错误信息如下:</div>");
//                        }
//
//                    }else{
//                        alert('文件中没有数据！');
//                        $("#div_errorMessages").attr("display","inline");
//                    }
                    //自动关闭进度提示框
                    parent.closeDialog(dialogId);
                },'text');
            }else{
                alert("您未选择文件！");
            }
        });

        $("#cancel").click(function(){
            parent["${tabId}"].backFunc($("#hidden_retunData").val());
            parent.closeDialog(window.parent.document.getElementById("imp_dialog").parentNode.id);

        });
    });

    function showModelDialog(){
        var content='<div style="margin-top:5px;">任务正在处理中请稍候.......</div>';
        var title='系统提示';
        //打开弹出框
        var id=parent.showDialog(1,content,title,160,80);
        return id;
    }

    function addErrors(errorMessages){
        $("#div_errorMessages").html("");
        var errors=errorMessages.split("&*_*&");
        var value="";
        $.each(errors,function(i,it){
            value+="<li>"+it+"</li>";
        });
        $("#div_errorMessages").html(value);
    }
</script>
</body>
</html>
