<%@ page import="cn.com.wz.parent.cms.Article" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
        <style>
        body,ul,li,dl,dt,dd,br,form,img { margin:0; padding:0; border:0; list-style:none;color:#000;font:12px/1.5 Tahoma, Geneva, "\5B8B\4F53";}
        body{background-color: #fff}
        p { line-height:25px; font-size:14px;margin-right:0; margin: 5px;width: 100%}
        .miancontent img{margin:0 auto; display:block;}
        .miancontent{margin-top: 25px;}
        h1{ font-size:26px;font-weight:bold;line-height:28px; padding-top:25px; margin:0px 0 10px 0; width:100%; text-align:center; clear:both; }
        .xian{
            width:722px;
            height:4px;
            background:url(../../images/xian.jpg) no-repeat;
            margin: 0 auto;
        }
        </style>
	</head>
	<body>
		<div id="show-article" class="content scaffold-show" role="main">
			<div>
				<table class='tab_tbody_list' style="margin-top:20px;">
					 <tr>
						<td>
							<h1><g:fieldValue bean="${articleInstance}" field="titleC"/></h1>
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
            <div class="xian"></div>
                <div  class="miancontent">
				<table>
					<tr>
					    <td style='padding-left:70px;padding-right:70px;'>
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
								        		<li><a href="${createLink(controller:'downLoad',action:'downFile')}?fileName=${it?.fileName}&filePath=${it?.filePath}">${it?.fileName}</a></li>
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
