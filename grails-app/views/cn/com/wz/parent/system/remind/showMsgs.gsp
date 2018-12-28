<%@ page import="cn.com.wz.parent.system.remind.Remind" %>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=IE8,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'remind.label', default: 'Remind')}" />
	</head>
	<body>
				<div style="margin-top:28px;">
					<div id="remind_grid" style="border:0px"></div>
				</div>
	  	
	 	<script>
			// 加载时执行的语句
			$(function() {
				buildRightGrid()
	           });
			//用户自定义方法
			//创建有些表格
			function buildRightGrid(){
				$('#remind_grid').omGrid({
					dataSource:'${createLink(controller:'Remind',action:'getMsgs')}',
					limit : 0,
					height:490,
					colModel:[{header : "${message(code: 'remind.createTime.label', default: 'Time')}", name : 'createTime', width : 120, align : 'center'},
		                      {header : "${message(code: 'remind.content.label', default: 'Content')}", name : 'content', width :915, align : 'left',wrap:true,
								renderer:function(value,rowData,rowIndex){
									return '<span title="'+value+'">'+value+'</span>';
						  		}
	                      	   },
	                      	 {header : "${message(code: 'remind.operate.label', default: 'operate')}", name : 'status', width : 60, align : 'center',
	                         	  renderer:function(value,rowData,rowIndex){
	 									if(value==1){
	 										 var data = rowData;
	 										 return '<a class="btn_table" href="javascript:parent.showAffair('+"'"+data.recordId+"','"+data.module+"'"+')">查看详情</a>';
	 									}else{
		 									return '';
		 									}
	                               }
	                           }
	                      	   ]
				});
			}
						
		 </script>
	</body>
</html>