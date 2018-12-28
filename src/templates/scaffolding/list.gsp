<% import grails.persistence.Event %>
<%=packageName%>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="center-panel" style="margin-left:4px;border:1px;"> 
				<form id="form_query" style="margin:8px;height:50px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style="text-align:right;width:80"><span><g:message code="article.titleC.label" default="TitleC" />：</span></td>
					    <td  style="text-align:left;width:100"><span><g:textField id="titleC" name="titleC" maxlength="200" value=""/></span></td>
					    <td width="60"></td>
					    <td style="text-align:right;width:80"><span><g:message code="article.keyWords.label" default="Key Words" />：</span></td>
					    <td style="text-align:left;width:100"><span><g:textField id="keyWords" name="keyWords" maxlength="200" value=""/></span></td>
					    <td width="100"></td>
					    <td align="left" valign="middle">
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<ul>
						<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</fieldset>
			</div>
		<div id="list-${domainClass.propertyName}" class="content scaffold-list" role="main">
			<g:if test="\${flash.message}">
				<div class="message" role="status">\${flash.message}</div>
			</g:if>
			<div style="margin-right:8px;margin-top:8px;">
					<div class="om-grid om-widget om-widget-content" style="height: 438px;">
						<div class="titleDiv" style="">
							<div class="titleContent"><g:message code="default.list.label" args="[entityName]" /></div>
						</div>
						<div class="hDiv om-state-default">
							<div class="hDivBox">
								<table>
									<thead>
										<tr>
											<th axis="indexCol" align="center" class="indexCol,indexheader" style="text-align:center;width:25px;"></th>
											<th axis="checkboxCol" align="center" class="checkboxCol,checkboxheader" style="text-align:center;width:17px;"><span class="checkbox"></span></th>
											<%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
												allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
												props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
												Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
												props.eachWithIndex { p, i ->
													if (i < 6) {
														if (p.isAssociation()) { 
											%>
												<th axis="col<%=i%>" class="col<%=i%>"  align="left"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></th>
											<%      } else { %>
												<g:sortableColumn property="${p.name}" title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}" />
											<%  }   }   } %>
										</tr>
									</thead>
								</table>
							</div>
					    </div>
					    <div class="bDiv" style="width: auto; height: 346px;">
					    	<table id="article_grid" style="border: 0px;" cellpadding="0" cellspacing="0" border="0">
					    		<tbody>
					    			<g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
										<tr class="\${(i % 2) == 0 ? 'om-grid-row evenRow' : 'om-grid-row oddRow'} ">
											<td align="center" abbr="" class="indexCol"><div align="center" class="innerCol " style="width:25px">1</div></td> 
					    					<td align="center" abbr="" class="checkboxCol"><div align="center" class="innerCol " style="width:17px"><span class="checkbox"></span></div></td> 
											<%  props.eachWithIndex { p, i ->
													if (i == 0) { %>
												<td class="innerCol "><g:link action="show" id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
											<%      } else if (i < 6) {
														if (p.type == Boolean || p.type == boolean) { %>
												<td class="innerCol "><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
											<%          } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
												<td class="innerCol "><g:formatDate date="\${${propertyName}.${p.name}}" /></td>
											<%          } else { %>
												<td class="innerCol ">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
											<%  }   }   } %>
										</tr>
									</g:each>
					    			
					    		</tbody>
					    	</table>
					    </div>
				</div>
			</div>
			<div class="pagination">
				<g:paginate total="\${${propertyName}Total}" />
			</div>
		</div>
	</body>
</html>
