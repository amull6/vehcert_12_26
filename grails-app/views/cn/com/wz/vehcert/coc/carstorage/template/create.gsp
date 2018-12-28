<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>模板导入</title>
		<script src="${resource(dir:"js",file:"DivDialog.js") }" type="text/javascript" ></script>
	</head>
	<body>
		<div id="create-package" class="content scaffold-create" role="main">
			<div id="errorMessages" class="error"></div>
			<g:form action="upload">
				<g:hiddenField name="objId" value="${params.objId }"/>
				<fieldset class="form">
					<table  style="width: 100%;height: 100%;border: 0;" cellspacing="0" cellpadding="0">
  						<tr>
    					<td align="center" valign="middle">
    						<table  style="width: 80%; border=1;" align="center" cellspacing="0" cellpadding="0">
  								<tr>
    								<td align="right">
    									<div class="fieldcontain">
    									一致性证书信息
										</div>
    								</td>
    								<td align="left" colspan="3">
    								<div class="fieldcontain ${hasErrors(bean: cocCarStorageInstance, field: 'files', 'error')} required">
    									<span class="property-value" aria-labelledby="accessorys-label">
											<uploader:uploader id="files" params="[prefix:'file']"  url="${[controller:'uploadr', action:'uploadDocs'] }" >
												<uploader:noScript>
		    										<p>Why don't you get a decent browser?</p>
		  									</uploader:noScript>
		  									<uploader:onComplete>
			  									<c:fileUpload1 id="files"></c:fileUpload1>
			  								</uploader:onComplete>
		 								</uploader:uploader>
		 								<!-- fileType 的值可为 doc、image、video -->
							 			<c:fileUpload2  fileType="doc" id="files" fileNames="${fileNames }"></c:fileUpload2>
										</span>
    								</div>
    							</td>
  							</tr>
						</table>
    				</td>
  				</tr>
			</table>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.save.label', default: 'Save')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
