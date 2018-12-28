<head>
<meta name="layout" content="main">
<%--<title>${video?.titleC }</title>--%>
<link href="${resource(dir:"css",file:"page.css") }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resource(dir:'js',file:'swfobject.js') }"></script>
</head>
<body>
		<div id="show-article" class="content scaffold-show" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div>
				<table class='tab_tbody_list' style="margin-top:20px;">
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
	
  <div id="videoContent">
<%--     <div class="spcon">               --%>
<%--              <div class="sptit">${video?.titleC }</div>--%>
<%--              <div class="timenotice">${video?.createTime?.toString()?.substring(0,10) }</div>--%>
			  <div id="player1" style="margin-left:100px">&nbsp;</div>
<%-- 	</div> --%>
 	   <!--视频信息-->
    <div>
	    <div >
	      <p class="ybpl" style="font-size:10px;"><span>视频：</span>${articleInstance?.titleC }</p>
	      <p class="ybpr" style="font-size:10px;"><span>关键词：</span>${articleInstance?.keyWords }</p>
	    </div>
      <div>
        <div id="jszhankai">
        <g:if test="${articleInstance?.contentC.size()>100}">
        <p> ${articleInstance?.contentC?.substring(0,100) }</p>
		</g:if>
		<g:else>
		    <p> ${articleInstance?.contentC}</p>
	    </g:else>
        <a href="javascript:void(0);" class="shou"  onclick="javascript:xZhankai('jszhankai','jsxx');" style="font-size:10px;color:blue;">展开</a> 
        </div>
        <div id="jsxx" style="display:none">
         <p style="font-family:宋体;font-size:12px;"><b>新闻内容：</b></p>
          ${articleInstance?.contentC}
            <a href="javascript:void(0);" class="shou"  onclick="javascript:xZhankai('jsxx','jszhankai');" style="font-size:10px;color:blue;">收起</a>
          
        </div>
      </div>
    </div>
    
    <!--end 视频信息-->   

			 <script type="text/javascript">
					var s1 = new SWFObject("${resource(dir:'swf',file:'flvplayer.swf')}","single","500","400","7");
					s1.addParam("allowfullscreen","true");
					s1.addVariable("autostart","true");
					s1.addParam("wmode","Opaque");
					s1.addVariable("file","${request.contextPath}${filePath}");
					s1.addVariable("image","${request.contextPath}${articleInstance?.picPath}");
					s1.addVariable("width","500");
					s1.addVariable("height","400");
					s1.write("player1");
				</script>
		 </div>
</body>
</html>
