
<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'appRole.label', default: 'App Role')}" />
			<g:set var="entityName1" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;">
            <%
                double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
                double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
                double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
                out.println("Java 虚拟机试图使用的最大内存量(当前JVM的最大可用内存)maxMemory(): " + max + "MB<br/>");
                out.println("Java 虚拟机中的内存总量(当前JVM占用的内存总数)totalMemory(): " + total + "MB<br/>");
                out.println("Java 虚拟机中的空闲内存量(当前JVM空闲内存)freeMemory(): " + free + "MB<br/>");
                out.println("因为JVM只有在需要内存时才占用物理内存使用，所以freeMemory()的值一般情况下都很小，<br/>" +
                        "而JVM实际可用内存并不等于freeMemory()，而应该等于 maxMemory()-totalMemory()+freeMemory()。<br/>");
                out.println("JVM实际可用内存: " + (max - total + free) + "MB<br/>");

            %>

        </div>
	</body>
</html>
