<!doctype html>
<html>
	<head>
		<title>Grails Runtime Exception</title>
		<meta name="layout" content="main">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/errors.css')}" type="text/css">
	</head>
	<body>
		<div class="AsiaInfo_system_Middle">
			<g:renderException exception="${exception}" />
		</div>
	</body>
</html>