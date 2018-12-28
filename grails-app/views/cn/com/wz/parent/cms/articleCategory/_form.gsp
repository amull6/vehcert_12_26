<%@ page import="cn.com.wz.parent.cms.ArticleCategory" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'categoryNameC', 'error')} required">
	<label for="categoryNameC">
		<g:message code="articleCategory.categoryNameC.label" default="Category Name C" />
		<span class="required-indicator"></span>
	</label>
	<g:textField name="categoryNameC" maxlength="200"style="width:300px;" required="" value="${articleCategoryInstance?.categoryNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'categoryNameE', 'error')} required">
	<label for="categoryNameE">
		<g:message code="articleCategory.categoryNameE.label" default="Category Name E" />
		<span class="required-indicator"></span>
	</label>
	<g:textField name="categoryNameE" maxlength="200" style="width:300px;"required="" value="${articleCategoryInstance?.categoryNameE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'categoryCode', 'error')} required">
	<label for="categoryCode">
		<g:message code="articleCategory.categoryCode.label" default="Category Code" />
		<span class="required-indicator"></span>
	</label>
	<g:textField name="categoryCode" maxlength="200"style="width:300px;" required="" value="${articleCategoryInstance?.categoryCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'needAuth', 'error')} ">
	<label for="needAuth">
		<g:message code="articleCategory.needAuth.label" default="Need Auth" />
		
	</label>
	<g:checkBox name="needAuth" value="${articleCategoryInstance?.needAuth}" />
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'showOrder', 'error')} ">
    <label for="showOrder">
        <g:message code="articleCategory.showOrder.label" default="showOrder" />

    </label>
    <g:field type="number" name="showOrder" value="${articleCategoryInstance.showOrder}"/>
</div>
%{--<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'showOrder', 'error')} required">--}%
	%{--<label for="showOrder">--}%
		%{--<g:message code="articleCategory.showOrder.label" default="Show Order" />--}%
		%{--<span class="required-indicator"></span>--}%
	%{--</label>--}%
	%{--<g:select name="showOrder" from="${articleCategoryInstance.constraints.showOrder.inList}" required="" value="${fieldValue(bean: articleCategoryInstance, field: 'showOrder')}" valueMessagePrefix="articleCategory.showOrder"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'categoryType', 'error')} ">
	<label for="categoryTypeName">
		<g:message code="articleCategory.categoryType.label" default="Category Type" />
	</label>
	<g:select id="categoryType" name="categoryType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','articleCategoryType')}})}" optionKey="id" required="" value="${articleCategoryInstance?.categoryType?.id}" class="one-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'style', 'error')} ">
	<label for="style">
		<g:message code="articleCategory.style.label" default="Style"/>
		
	</label>
	<g:textField name="style" value="${articleCategoryInstance?.style}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'parent', 'error')}">
	<label for="parent">
		<g:message code="menu.parent.label" default="Parent" />
	</label>
	<c:ajaxTreeSelect id='parent' readonly="readonly"   hidden_id='parent_hidden' hidden_name="parent.id" hidden_value="${articleCategoryInstance?.parent?.id}" value="${articleCategoryInstance?.parent?.categoryNameC}" url="${createLink(controller:'articleCategory',action:'jsonListTree')}"></c:ajaxTreeSelect>
<%--	<g:select id="parent" name="parent.id" from="${cn.com.wz.parent.cms.ArticleCategory.list()}" optionKey="id" value="${articleCategoryInstance?.parent?.id}" class="many-to-one" noSelection="['null': '根节点']"/>--%>
</div>

<div class="fieldcontain ${hasErrors(bean: articleCategoryInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="articleCategory.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="3" maxlength="300"style="width:300px;height:90px;" value="${articleCategoryInstance?.description}"/>
</div>

