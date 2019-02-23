<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="经销商打印" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="export"/>
</head>
<body>
<div id="page" style="background-color:white;overflow:visible;">
    <div id="center-panel" style="margin-left:4px;border:1px;">
        <form id="form_query" style="margin:8px;">
            <table style="width:100%;border:0px;">
                <tr>
                    <td align="left" valign="middle">
                        <a id="btn_print" class="btn_basic blue_black"  ><g:message code="zcinfo.print.label" default="print" /></a>
                    </td>
                </tr>
            </table>

        </form>
        <div style="font-size: 14px;color:red;font-weight: bold;margin-top: 5px;">打印前请先将打印机设置在合格证纸张打印模式</div>
        <div align="center">
            %{--<object id="pdfObj" classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="1000" height="1000" border="0">--}%
                %{--<param  name="src" value="${path}"/>--}%
            %{--</object>--}%
            <iframe src="${path}" width="800" height="600"></iframe>
        </div>
    </div>
</div>
<script>
    //绑定查询按钮事件
    $('#btn_print').click(function(){
        try {
            pdfObj.printAll();
            document.getElementById("prtBtn").setAttribute("disabled", true);
        } catch (e) {
            alert("未安装adobe reader插件，请联系管理员安装！");
        }
    });
</script>
</body>
</html>
