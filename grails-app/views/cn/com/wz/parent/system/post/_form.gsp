<%@ page import="cn.com.wz.parent.system.post.Post" %>



<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'postNameC', 'error')} required" style='margin-top:-20px;'>
	<label for="postNameC">
		<g:message code="post.postNameC.label" default="Post Name C" />
	</label>
	<g:textField name="postNameC" required=""  style="width:300px;height:20px;" value="${postInstance?.postNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'postType', 'error')} required">
	<label for="postType">
		<g:message code="post.postType.label" default="Post Type" />
	</label>
	<g:select id="postType" name="postType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','postType')}})}" optionKey="id" required="" value="${postInstance?.postType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="post.code.label" default="Code" />
		
	</label>
	<g:textField name="code" style="width:300px;height:20px;" value="${postInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'showOrder', 'error')} required">
	<label for="showOrder">
		<g:message code="post.showOrder.label" default="Show Order" />
	</label>
	<g:field type="number" name="showOrder" required="" value="${postInstance.showOrder}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: postInstance, field: 'remark', 'error')} ">
	<label for="remark">
		<g:message code="post.remark.label" default="Remark" />
		
	</label>
	<g:textArea name="remark" style="width:300px;height:90px;" value="${postInstance?.remark}"/>
</div>




