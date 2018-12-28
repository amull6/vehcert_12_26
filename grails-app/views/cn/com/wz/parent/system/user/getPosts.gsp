<!doctype html>
<html>
	<head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'organUser.label', default: 'OrganUser')}" />
			<g:set var="entityName1" value="${message(code: 'employee.label', default: 'Employee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page1" style="width:100%;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 					<div id="center-top">
		 				<form id="form_query" style="margin:8px;height:30px;">
							<table style="width:100%;border:8px;">
							  <tr>
							  	<td width="80"><span><g:message code="post.postNameC.label" default="Post Name C" />：</span></td>
							    <td width="100"><span><g:textField id="postNameC" name="postNameC" maxlength="200" /></span></td>
							    <td width="60"></td>
							    <td align="left" valign="middle">
							    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
							    <input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
							    </td>
							  </tr>
							</table>
						</form>
		 				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
                             <input id="btn_add" type="button"  class="import" value="${message(code: 'default.button.addPost.label', default: 'Add')}"/>
				        </fieldset>
						<div style="margin-right:8px;margin-top:0px;">
							<div id="content" style="border:0px">
								
							</div>
						</div> 
 					</div>
 					<div id="center-bottom">
 						<table id="posts_table" class="tab_tbody_list">
 						<thead>
 							<tr>
 								<th>
 									岗位名称
 								</th>
 								<th>
 									岗位类型
 								</th>
 								<th>
 									操作
 								</th>
 								
 							</tr>
 						</thead>
 						<tr></tr>
 							<g:each in="${posts}" var='post'>
 							
 								<tr>
 									<td>
	 									${ post.postNameC}
		 								<input type='hidden' name='postsIds' value='${ post.id}'/>
		 								<input type='hidden' name='postsNames' value='${ post.postNameC}'/>
	 								</td>
	 								<td>
	 									${ post.code}
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
    	</div>
    	
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
		   	                    var url = "${createLink(controller:'post',action:'search')}";
		   	                    url+='?'+$('#form_query').serialize();
		   	                    $('#content').omGrid('setData',url);
		   	                  }
                });
             //绑定清除按钮事件
              $('#btn_clear').omButton({
              		width:80,
           	   		onClick:function(){
	           	   		$("#postNameC").val('');
	 	   	            $("#postNameE").val('');
	   	            }
              });
              $("#postNameC").keydown(function(e){
					 if(e.keyCode==13){
						 $('#btn_query').click();
						  return false;
					}
			   });
              $("#btn_close").omButton({
                  width:80,
                  onClick:function(){
                	  parent.closeDialog(window.parent.document.getElementById("posts_dialog").parentNode.id);
                     }
               });
               $("#btn_confirm").omButton({
                   width:80,
                   onClick:function(){
                	   var postShowName="";
                       var postIds="";
                       var d=$("input[name='postsIds']");
                       for(var i=0;i<d.length;i++){  
                    	   postIds+=d[i].value+"_";
                      }

                       var d=$("input[name='postsNames']");
                       for(var i=0;i<d.length;i++){ 
                           postShowName=postShowName+d[i].value+";";
                       }
                       var tabId='${tabId}';
                       if(postShowName.length>0){
                    	   postShowName=postShowName.substring(0,postShowName.lastIndexOf(";"));
                           }
                	   parent[tabId].getPosts(postShowName,postIds);
                      $("#btn_close").click();
                       }
                   
                });
                
                $('#btn_add').click(function(){
             		var selections = $('#content').omGrid('getSelections',true);
             		if(selections.length==0){
                 		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                 		return;	
                 	} 

             		$.each(selections,function(i,item){
             			 addPosts(item.id,item.postNameC,item.code);
                  	});
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
	           	        title:"<g:message code='postList.label' args='[entityName1]'/>",
	           	        region:"center"
	           	    }]
	            });
				buildRightGrid()
			});

			//#############################用户自定义方法############################

			//构建右侧的表格	
			function buildRightGrid(){
			$('#content').omGrid({
				dataSource:'${createLink(controller:'post',action:'search')}?'+$('#form_query').serialize(),
				title:"<g:message code='default.list.label' args='[entityName1]'/>",
				singleSelect:false,
				limit : 5,
				height : 240,
				showIndex : true,
				onRowDblClick : function(rowIndex,rowData) {
					addPosts(rowData.id,rowData.postNameC,rowData.code);
                },
				colModel:[{header : "${message(code: 'post.postNameC.label', default: 'Post NameC')}", name : 'postNameC', width : 160,align : 'center'},
						  {header : "${message(code: 'post.code.label', default: 'Code')}", name : 'code', width : 160, align : 'center'},
						  {header : "${message(code: 'post.postTypeName.label', default: 'Post Type')}", name : 'postTypeName', width : 160,align : 'center'}
                        ]
			});
		}
		    function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 3000
		        });
		    }

		    //往选中人员表格中添加一行数据
		    function addPosts(postId,postNameC,code){
			    //验证数据是否已存在
		    	 var d=$("input[name='postsIds']");
                 for(var i=0;i<d.length;i++){  
                     if(d[i].value==postId){
                    	 var a=postId;
                  		alert("["+postNameC+"]"+"${message(code: 'insidenote.haveselected.message')}")
						return;
                     }
                }
			    
			    
		    	   var table=document.getElementById("posts_table");
		    	   var count=table.rows.length;
		    	   var row=table.insertRow(table.rows.length);
		    	   row.id=count;
		    	  
		    	   var col=row.insertCell(0);
		    	   col.innerHTML=postNameC+"<input type='hidden' name='postsIds' value='"+postId+"'/>"+
		    	   "<input type='hidden' name='postsNames' value='"+postNameC+"'/>";
		    	   col.className="bk_mlst_tdwhite" ;

		    	   var col=row.insertCell(1);
		    	   col.innerHTML=code;
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
