<%@ page import="cn.com.wz.parent.cms.ArticleCategory" %>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'titleC', 'error')} " style='margin-top:-20px;'>
	<label for="titleC">
		<g:message code="article.titleC.label" default="TitleC" />
		
	</label>
	<g:textField name="titleC" class='textLength' style="width:300px;height:20px;" value="${articleInstance?.titleC}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'keyWords', 'error')} ">
	<label for="keyWords">
		<g:message code="article.keyWords.label" default="Key Words" />
		
	</label>
	<g:textField name="keyWords" class='textLength' style="width:300px;height:20px;" value="${articleInstance?.keyWords}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'showOrder', 'error')} ">
    <label for="showOrder">
        <g:message code="article.showOrder.label" default="showOrder" />

    </label>
    <g:field type="number" name="showOrder" value="${articleInstance.showOrder}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'articleCategory', 'error')} required">
	<label for="articleCategory">
		<g:message code="article.articleCategory.label" default="Article Category" />
	</label>
	<c:ajaxTreeSelect id='articleCategory' class='textLength' style="width:300px;height:20px;" hidden_id='articleCategory_hidden' hidden_name="articleCategory.id" hidden_value="${articleInstance?.articleCategory?.id}" value="${articleInstance?.articleCategory?.categoryNameC}" url="${createLink(controller:'ArticleCategory',action:'jsonListTree')}"></c:ajaxTreeSelect>
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'contentC', 'error')} ">
	<div >
		<label for="contentC">
			<g:message code="article.contentC.label" default="ContentC" />
		</label>
		<span class="property-value" aria-labelledby="contentC-label">
            <span style="color:red"> 推荐：从word中复制，请在word中设置字体为宋体、字体大小10.5、单倍行距！</span>
			<ckeditor:editor id="contentC"  name="contentC" height="400px" width="80%" >
				${articleInstance.contentC}
			</ckeditor:editor>
		</span>
	</div>
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'description', 'error')} ">
    <div >
        <label for="contentC">
            <g:message code="article.description.label" default="Description" />
        </label>
        <span class="property-value" aria-labelledby="contentC-label">
            <ckeditor:editor id="description"  name="description" height="100px" width="80%" >
                ${articleInstance.description}
            </ckeditor:editor>
        </span>
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'accessorys', 'error')} ">
	<label for="accessorys">
		<g:message code="article.accessorys.label" default="accessorys" />
		
	</label>
	<span class="property-value" aria-labelledby="accessorys-label">
		<uploader:uploader id="accessorys" params="[prefix:'article']" url="${[controller:'uploadr', action:'uploadDocs'] }">
			<uploader:noScript>
		    	<p>Why don't you get a decent browser?</p>
		  	</uploader:noScript>
		  	<uploader:onComplete>
			  	<c:fileUpload1 id="accessorys"></c:fileUpload1>
			  </uploader:onComplete>
		 </uploader:uploader>
		 <!-- fileType 的值可为 doc、image、video -->
		 <c:fileUpload2  fileType="doc" id="accessorys" fileNames="${fileNames }"></c:fileUpload2>
	</span>
	
</div>

<div class="fieldcontain ${hasErrors(bean: articleInstance, field: 'picture', 'error')} ">
	<label for="picture">
		<g:message code="article.picture.label" default="Picture" />
		
	</label>
	<span class="property-value" aria-labelledby="picture-label">
		<uploader:uploader id="picture" multiple="false" url="${[controller:'uploadr', action:'uploadImages'] }">
			<uploader:noScript>
		    	<p>Why don't you get a decent browser?</p>
		  	</uploader:noScript>
		  	<uploader:onComplete>
			  	<c:fileUpload1 id="picture"></c:fileUpload1>
			  </uploader:onComplete>
		 </uploader:uploader>
		 <!-- fileType 的值可为 doc、image、video -->
		 <c:fileUpload2  fileType="image" id="picture" fileNames="${picNames }"></c:fileUpload2>
	</span>
	
</div>
