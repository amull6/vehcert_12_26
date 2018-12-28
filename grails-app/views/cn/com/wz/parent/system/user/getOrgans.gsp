<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <g:set var="entityName" value="${message(code: 'organization.label', default: 'Organ')}" />
		<meta name="layout" content="main">
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page1" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 					<div id="center-top">
		 				<fieldset class="buttons" style="margin:10px 8px 8px 4px;">
                             <input id="btn_add" type="button"  class="import" value="${message(code: 'default.button.addOrgan.label', default: 'Add')}"/>
				        </fieldset>
						<div style="margin-right:8px;margin-top:0px;">
							<div id="content" style="border:0px"></div>
						</div> 
 					</div>
 					<div id="center-bottom">
 						<table id="organs_table"  class="tab_tbody_list">
	 						<thead>
	 							<tr>
	 								<th>
	 									组织结构名称
	 								</th>
	 								<th>
	 									组织结构编码
	 								</th>
	 								<th>
	 									操作
	 								</th>
	 								
	 							</tr>
	 						</thead>
	 						<tr>
	 						</tr>
 							<g:each in="${organs}" var='organ'>
 							
 								<tr>
 									<td>
	 									${ organ.organNameC}
		 								<input type='hidden' name='organsIds' value='${ organ.id}'/>
		 								<input type='hidden' name='organsNames' value='${ organ.organNameC}'/>
	 								</td>
	 								<td>
	 									${ organ.organCode}
	 								</td>
 									<td><a href="#" onclick="delRow(this)">删除</a></td>
 								</tr>
				    		</g:each>
 						</table>
 						<div>
 							<input type="button" id="btn_confirm" value="确定">
 							<input type="button" id="btn_close" value="关闭">
 						</div>
 					</div>
	    	</div>
	    	<ul id="west-panel"></ul>
    	</div>
    	
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
              $("#btn_close").omButton({
                  width:80,
                  onClick:function(){
                	  parent.closeDialog(window.parent.document.getElementById("organs_dialog").parentNode.id);
                      }
               });
               $("#btn_confirm").omButton({
                   width:80,
                   onClick:function(){
                	   var organShowName="";
                       var organIds="";
                       var d=$("input[name='organsIds']");
                       for(var i=0;i<d.length;i++){  
                    	   organIds+=d[i].value+"_";
                      }

                       var d=$("input[name='organsNames']");
                       for(var i=0;i<d.length;i++){  
                    	   organShowName=organShowName+d[i].value+";";
                       }
                       if(organShowName.length>0){
                    	   organShowName=organShowName.substring(0,organShowName.lastIndexOf(";"));
                           }
                       var tabId='${tabId}';
                	   parent[tabId].getOrgans(organShowName,organIds);
                      $("#btn_close").click();
                       }
                   
                });
                
                $('#btn_add').click(function(){
              	  var selected = $('#west-panel').omTree('getSelected');
	               	   //处理没有选中节点的情况
	               	   if(selected==null){
	               		   alert('${message(code: 'organUser.selected.empty.message', default: 'Please select Organization')}');
	                       return;	
	                   }

             			 addOrgan(selected.id,selected.text,selected.organCode);
    			});

    			
			});
			
			// ################################加载时执行的语句########################
			$(function() {
				//刚加载页面后使右下部分内容显示
				$('#center-panel').css({visibility:'visible'});
				$('#page1').css({height:$(document).height()-16});
				//布局
				$('#page1').omBorderLayout({
					panels:[{
	           	        id:"center-panel",
	           	     	header:false,
	           	        region:"center"
	           	    },{
	           	        id:"west-panel",
	           	        title:"<g:message code="default.list.label" args="[entityName]" />",
	           	        region:"west",
	           	        width:200
	           	    }]
	            });
	            
				$('#center-panel').omBorderLayout({
					panels:[{
	           	        id:"center-top",
	           	 		header:false,
	           	        region:"north",
	           	        height:330
	           	    },{
	           	        id:"center-bottom",
	           	        title:"<g:message code='organList.label' args='[entityName1]'/>",
	           	        region:"center"
	           	    }]
	            });
				//构建控件
			    $('#west-panel').omTree({
			         dataSource : "${createLink(controller:'organization',action:'jsonListTree')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       		  $('#center-panel').css({visibility:'visible'});
			       		  $('#content').load("${createLink(controller:'organization',action:'jsonShow')}/"+node.id);
		             }
			    });
			});

			//#############################用户自定义方法############################

			//构建右侧的表格	

		    //往选中人员表格中添加一行数据
		    function addOrgan(organId,organName,organCode){
			    //验证数据是否已存在
		    	 var d=$("input[name='organsIds']");
                 for(var i=0;i<d.length;i++){  
                     if(d[i].value==organId){
                  		alert("["+organName+"]"+"${message(code: 'insidenote.haveselected.message')}")
						return;
                     }
                }
			    
			    
		    	   var table=document.getElementById("organs_table");
		    	   var count=table.rows.length;
		    	   var row=table.insertRow(table.rows.length);
		    	   row.id=count;
		    	  
		    	   var col=row.insertCell(0);
		    	   col.innerHTML=organName+"<input type='hidden' name='organsIds' value='"+organId+"'/>"+
		    	   "<input type='hidden' name='organsNames' value='"+organName+"'/>";
		    	   col.className="bk_mlst_tdwhite" ;

		    	   var col=row.insertCell(1);
		    	   col.innerHTML=organCode;
		    	   col.className="bk_mlst_tdwhite" ;

		    	   var col=row.insertCell(2);
		    	   col.innerHTML="<a href='#' onclick="+'"delRow(this)">删除</a>';
		    	   col.className="bk_mlst_tdwhite" ;

		       }
			//从表中删除指定行
		    function delRow(it){
		    	   var table=it.parentElement.parentElement.parentElement;
		    	   var rowIndex=it.parentElement.parentElement.rowIndex;
		    	  table.deleteRow(rowIndex-1);
		       }
 	 	</script>
	</body>
</html>

