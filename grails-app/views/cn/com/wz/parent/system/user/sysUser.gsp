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
							<table style="width:100%;border:0px solid;">
							  <tr>
							  	<td width="40" align="right"><span><g:message code="user.tempName.label" default="tempName" />：</span></td>
							    <td width="40" align="left"><span><g:textField id="realName" name="realName" maxlength="200"/></span></td>
							    <td width="40" align="right"><span>岗位：</span></td>
							    <td width="40"  align="left"><span><g:textField id="post" name="post" maxlength="200" /></span></td>
							    <td width="60" align="right"><span>手机缩号：</span></td>
							    <td width="40"  align="left"><span><g:textField id="phoneCode" name="phoneCode" maxlength="200" /></span></td>
							    <td width="60" align="right"><span>手机号码：</span></td>
							    <td width="40"  align="left"><span><g:textField id="mPhone" name="mPhone" maxlength="200" /></span></td>
							    <td align="center" valign="middle">
		    	                    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
							    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
							    </td>
							  </tr>
							</table>
							<input type="hidden" name='nodeId' id="nodeId" >
							<input type="hidden" id="organizationId" name="organId"/>
						</form>
						<div style="margin-right:8px;margin-top:0px;">
							<div id="content" style="border:0px"></div>
						</div> 
 					</div>
 					<div id="center-bottom">
 					</div>
	    	</div>
	    	<ul id="west-panel"></ul>
    	</div>
    	
	 	<script>
			// 事件绑定
			$(function() {
                //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:50,
                	   onClick:function(){
                		   var  url='${createLink(controller:'organUser',action:'selectAllUserByOrgan')}?opFlag=1'+"&"+$('#form_query').serialize();
                		   buildRightGrid(url);
		   	           }
                });
             //绑定清除按钮事件
              $('#btn_clear').omButton({
              		width:50,
           	   		onClick:function(){
	   	                   	$("#realName").val('');
	   	                   	$("#post").val('');
	   	                 	$("#mPhone").val('');
	   	                 	$("#phoneCode").val('');
	   	            }
              });


              $("#realName").keydown(function(e){
				  if(e.keyCode==13){
					  $("#post").focus();
					  return false;
					}
		   });

              $("#post").keydown(function(e){
					 if(e.keyCode==13){
			              $('#phoneCode').focus();
						  return false;
					}
			   });
              $("#phoneCode").keydown(function(e){
					 if(e.keyCode==13){
			              $('#mPhone').focus();
						  return false;
					}
			   });
              $("#mPhone").keydown(function(e){
					 if(e.keyCode==13){
			              $('#btn_query').click();
						  return false;
					}
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
	           	        title:"<g:message code="default.listOrgan.label" args="[entityName]" />",
	           	        region:"west",
	           	        width:215
	           	    }]
	            });
	            
				$('#center-panel').omBorderLayout({
					panels:[{
	           	        id:"center-top",
	           	 		header:false,
	           	        region:"north",
	           	        height:560
	           	    },{
	           	        id:"center-bottom",
	           	        title:"<g:message code='receiverList.label' args='[entityName1]'/>",
	           	        region:"center"
	           	    }]
	            });
				buildRightGrid(1);
				//构建控件
			    $('#west-panel').omTree({
			         dataSource : "${createLink(controller:'organization',action:'jsonListTree')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       		$('#nodeId').val(node.id);
			       		  $('#center-panel').css({visibility:'visible'});
			       		  $('#organizationId').val(node.id);//查询时使用
		                  buildRightGrid(1)
		                  
		             }
			    });
			});

			//#############################用户自定义方法############################

			//构建右侧的表格	
			function buildRightGrid(opFlag){
				var url='';
				if(opFlag==1){
					url='${createLink(controller:'OrganUser',action:'selectEmpByOrgan')}?opFlag='+opFlag+'&'+$('#form_query').serialize();
				}else{
					url=opFlag;
				}
			$('#content').omGrid({
				dataSource:url,
				singleSelect:true,
				limit : 12, // 分页显示，每页显示5条
				height : 420,
				showIndex : true,
                onRowDblClick : function(rowIndex,rowData) {
                	var message=addReceiver(rowData.id,rowData.userName,rowData.realName);
                	if(message!=""&message!=null){
                  		alert(message);
                    }else{
                    	showTopTip("添加成功！");
                    }
                },
				
				colModel:[{header : "${message(code: 'user.tempName.label', default: 'tempName')}", name : 'realName', width : 80,align : 'center'},
				          {header : "${message(code: 'user.organs.label', default: 'Ogans')}", name : 'organNames', width : 100, align : 'center'},
						  {header : "${message(code: 'user.posts.label', default: 'posts')}", name : 'postsNames', width : 100, align : 'center'},
						  {header : "${message(code: 'user.phoneCode.label', default: 'phoneCode')}", name : 'phoneCode', width : 100, align : 'center' },
                          {header : "${message(code: 'user.mPhone.label', default: 'mPhone ')}", name : 'mPhone', width : 100, align : 'center'},
                          {header : "${message(code: 'user.eMail.label', default: 'eMail')}", name : 'eMail', width : 120, align : 'center'},
						  {header : "${message(code: 'user.address.label', default: 'Address')}", name : 'address', width : 130, align : 'center' },
						  {header : "${message(code: 'user.birthDay.label', default: 'birthDay')}", name : 'birthDay', width : 80, align : 'center' }
                         
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
		    function addReceiver(userId,userName,realName){
			    //验证数据是否已存在
		    	 var d=$("input[name='userIds']");
                 for(var i=0;i<d.length;i++){  
                     if(d[i].value==userId){
                    	 var a=userId;
                    	 return "["+realName+"]"+"${message(code: 'insidenote.haveselected.message')}";
						
                     }
                }
			    
			    
		    	   var table=document.getElementById("receivers_table");
		    	   var count=table.rows.length;
		    	   var row=table.insertRow(table.rows.length);
		    	   row.id=count;
		    	  
		    	   var col=row.insertCell(0);
		    	   col.innerHTML=userName+"<input type='hidden' name='userIds' value='"+userId+"'/>"+
		    	   "<input type='hidden' name='returnNames' value='"+userName+'【'+realName+'】'+"'/>";
		    	   col.className="bk_mlst_tdwhite" ;

		    	   var col=row.insertCell(1);
		    	   col.innerHTML=realName;
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
