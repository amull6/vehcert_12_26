<%@ page import="cn.com.wz.parent.cms.Article" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
</head>
<body>
<div id="show-article" class="content scaffold-show" role="main">
    <div>
        <table style="margin-top:20px;">
            <tr>
                <td style="text-align:center;font-size:18px;font-weight: bold;">
                    <span class="property-value" aria-labelledby="titleC-label"><g:fieldValue bean="${articleInstance}" field="titleC"/></span>
                </td>
            </tr>
            <tr>
                <td style="text-align:center;" >
                    <span class="property-value" aria-labelledby="articleCategory-label">${articleInstance?.createTime?.toString()?.substring(0,19)}</span>
                    ${organNameC}
                    <c:getUserName userId='${articleInstance.createrId }'></c:getUserName>
                </td>
            </tr>
        </table>
    </div>
    <hr/>
    <div>
        <table>
            <tr>
                <td style='padding-left:100px;padding-right:100px;'>
                    ${articleInstance.contentC}
                </td>
            </tr>
            <g:if test="${articleInstance.accessorys }">
                <tr>
                    <td style='padding-left:100px;padding-top:50px;'>
                        <span>附件下载：</span>
                        <span>
                            <ul>
                                <g:each in='${articleInstance.accessorys }' >
                                    <li><a href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${it?.fileName}&filePath=${it?.filePath}">${it?.nameC}</a></li>
                                </g:each>
                            </ul>
                        </span>
                    </td>
                </tr>
            </g:if>
        </table>
    </div>
</div>
</body>
</html>
