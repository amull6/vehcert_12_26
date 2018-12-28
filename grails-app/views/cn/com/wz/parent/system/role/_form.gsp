<%@ page import="cn.com.wz.parent.system.role.Role" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryValue" %>
<%@ page import="cn.com.wz.parent.system.dictionary.DictionaryType" %>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'roleNameC', 'error')} " style='margin-top:-20px;'>
	<label for="roleNameC">
		<g:message code="role.roleNameC.label" default="Role NameC" />
		
	</label>
	<g:textField name="roleNameC" style="width:300px;height:20px;" maxlength="120" value="${roleInstance?.roleNameC}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'roleNameE', 'error')} ">
	<label for="roleNameE">
		<g:message code="role.roleNameE.label" default="Role NameE" />
		
	</label>
	<g:textField name="roleNameE" maxlength="120" style="width:300px;height:20px;" value="${roleInstance?.roleNameE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'roleCode', 'error')} ">
	<label for="roleCode">
		<g:message code="role.roleCode.label" default="Role Code" />
		
	</label>
	<g:textField name="roleCode" maxlength="60" style="width:300px;height:20px;" value="${roleInstance?.roleCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'roleType', 'error')} ">
	<label for="roleTypeName">
		<g:message code="role.roleType.label" default="Role Type" />
	</label>
	<g:select id="roleType" name="roleType.id" from="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({dictionaryType{eq('code','roleType')}})}" optionKey="id" required="" value="${roleInstance?.roleType?.id}" class="one-to-one" />
</div>


<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'theSystem', 'error')} ">
    <input id="checkIds" name="checkIds" value="checkIds" type="hidden" value="${checkIds}">
    <label for="theSystem">
        <g:message code="role.theSystem.label" default="The System" />
    </label>

    <g:each var='r' in="${cn.com.wz.parent.system.dictionary.DictionaryType.createCriteria().list({
        parent{eq('code','appManage')}
        order("showOrder","asc")
        })}" >

            <span title="${r.typeNameC }">&nbsp;${r.typeNameC }</span>
            <g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({  dictionaryType{eq('code',r.code)}
                order("ordernum","asc") })}" >
                    &nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />
                    <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
            </g:each>
            <br>
    </g:each>

    %{--<div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">--}%
        %{--<g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({--}%
            %{--dictionaryType{eq('code','manageSys')}--}%
            %{--order("ordernum","asc")--}%
        %{--})}" >--}%
            %{--<div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">--}%
                %{--&nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />--}%
                %{--<span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>--}%
            %{--</div>--}%
        %{--</g:each>--}%
    %{--</div>--}%

    %{--<div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">--}%
        %{--<g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({--}%
            %{--dictionaryType{eq('code','yewuManageSys')}--}%
            %{--order("ordernum","asc")--}%
        %{--})}" >--}%
            %{--<div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">--}%
                %{--&nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />--}%
                %{--<span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>--}%
            %{--</div>--}%
        %{--</g:each>--}%
    %{--</div>--}%

</div>



<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'showOrder', 'error')} required">
	<label for="showOrder">
		<g:message code="role.showOrder.label" default="Show Order" />
	</label>
	<g:select name="showOrder" from="${roleInstance.constraints.showOrder.inList}" required="" value="${fieldValue(bean: roleInstance, field: 'showOrder')}" valueMessagePrefix="roleInstance.showOrder"/>
</div>

<div class="fieldcontain ${hasErrors(bean: roleInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="role.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="300" style="width:300px;height:90px;" value="${roleInstance?.description}"/>
</div>



