<%@ page import="cn.com.wz.parent.system.user.User" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'carstorage.label', default: 'carStorage')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="show-user" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:form id="form_query">
                <g:render template="/cn/com/wz/vehcert/carstorage/form"/>
				<fieldset class="buttons" style="margin:20px 8px 0px 4px;padding-left:280px;">
					<g:hiddenField name="id" value="${carStorage?.id}" />
                <c:resourceAuth resourceCode="dv_carStorage_edit">
                    <input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
					%{--<g:link class="edit" action="toUpdate" id="${carStorage?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
                </c:resourceAuth>
					<g:link controller="carStorage" class="cancel">${message(code: 'default.button.return.label', default: 'Return')}</g:link>
				</fieldset>
			</g:form>
		</div>
    <script>
        $("input").attr('readonly','readonly');
        $('#btn_edit').click(function(){
            var url = '${createLink(controller:'carStorage',action:'toUpdate')}?id='+'${carStorage?.id}';
                window.location.href = url;
        });
    </script>
	</body>
</html>
