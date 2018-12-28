<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="票据信息上传" />
    <title><g:message code="oil.label" args="[entityName]" /></title>
</head>
<body>
<div id="create-oil" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <form id="form" style="margin-top:-15px;">
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: uploadInvoice, field: 'fileBytes', 'error')} ">
                <label >
                    上传加密文件：

                </label>
                <span class="property-value" aria-labelledby="picture-label">
                    <uploader:uploader id="file" multiple="false" url="${[controller:'uploadr', action:'uploadDocs'] }">
                        <uploader:noScript>
                            <p>Why don't you get a decent browser?</p>
                        </uploader:noScript>
                        <uploader:onComplete>
                            <c:fileUpload1 id="file"></c:fileUpload1>
                        </uploader:onComplete>
                    </uploader:uploader>
                <!-- fileType 的值可为 doc、image、video -->
                    <c:fileUpload2  fileType="doc" id="file" fileNames="${fileNames }"></c:fileUpload2>
                </span>

            </div>
        </fieldset>
        <fieldset class="buttons" style="margin:5px 8px 0px 4px;padding-left:280px;">
            <input type="button" id="btn_submit" name="create" class="save" onclick="showDialog()" value="${message(code: 'default.button.save.label', default: 'Save')}" />
            <g:link  class="cancel">${message(code: 'default.button.cancel.label', default: 'Cancel')}</g:link>
        </fieldset>
    </form>
    <script type="text/javascript">
        $("#btn_submit").click(function(){
            $("#btn_submit").attr("disabled","disabled");
            var dialogId=showTipDialog();
            var url="${createLink(controller: "uploadInvoice",action: "save")}";
            $.post(url,$("#form").serialize(),function(data,textStatus){
                $("#btn_submit").enable();
                parent.closeDialog(dialogId);
               if(textStatus=='success'){
                       alert(data.msg);
               }else{
                  alert("AJAX申请失败！");
               }
            });
        }) ;
        function showDialog(){

        }
        $(".cancel").click(function(){
            $('.cancel').attr('href','javascript:void(0);');
            $.omMessageBox.confirm({
                title:'确认',
                content:'您确定要离开此页面吗？',
                onClose:function(value){
                    if(value){
                        window.location.href="${createLink(controller:'uploadInvoice',action:'list')}";
                    }
                }
            });
        });
    </script>
</div>
</body>
</html>
