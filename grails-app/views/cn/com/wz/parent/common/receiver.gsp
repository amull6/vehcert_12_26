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
		<div id="page1" style="width:100%;min-height:477px;background-color:white;">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;">
 					<div id="center-top" style="height:100%;">
		 				<form id="form_query" style="margin:4px 4px 0 0;height:30px;">
							<table style="width:100%;border:0px solid;" cellpadding="0" cellspacing="0">
							  <tr>
							  	<td width="70" align="right"><span><g:message code="bookTicket.name.label" default="Real Name" />：</span></td>
							    <td width="70" align="left"><span><g:textField id="realName" name="realName" /></span></td>
							    <td align="center" valign="middle" width="150px">
		    	                    <input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
							    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
							    </td>
							  </tr>
							</table>
							<input type="hidden" name='addressId' id="addressId" >
							<input type="hidden" name='nodeId' id="nodeId" >
							<input type="hidden" name='urlInsideNote' id="urlInsideNote" value="${urlInsideNote}">
						</form>
						<div style="margin-right:8px;margin-top:0px;height:85%;">
							<div id="content" style="border:0px;height:100%;"></div>
						</div>

 					</div>
 					<div id="center" style="margin-top:-1px;width:560px;">
                         <div id="bottom-top">
                             <div style="margin-top:1px;background:#cdd8e9;height:24px;width:540px;border:1px solid #ddd">
                                 <table class="tab_tbody_list" cellpadding="0" cellspacing="0">
                                     <thead>
                                     <tr>
                                         <td width="100px" align="center">
                                             姓名
                                         </td>
                                         <td width="250px" align="center">
                                             组织
                                         </td>
                                         <td width="100px" align="center">
                                             电话
                                         </td>
                                         <td width="60px" align="center">
                                             操作
                                         </td>
                                     </tr>
                                     </thead>
                                 </table>
                             </div>
                            <table id="receivers_table" class="tab_tbody_list" cellpadding="0" cellspacing="0">
                                <g:if test='${receivers}'>
                                    <g:each in="${receivers}" var='receiver'>
                                        <tr id="${receiver.id}">
                                            <td width="100px" align="center">
                                                ${ receiver.realName}
                                                <input type='hidden' name='receiverInfo' value='${receiver.receiverInfo}'/>
                                                <input type='hidden' name='returnNames' value='${receiver.realName}'/>
                                                <input type='hidden' name='hidden_ids' value='${receiver.id}'/>
                                            </td>
                                            <td width="250px" align="center">
                                                ${ receiver.organNames}
                                            </td>
                                            <td width="100px" align="center">
                                                ${ receiver.mPhone}
                                            </td>
                                            <td width='60' align="center"><a href="#" class='btn_basic gray_balck' onclick="delRow('${receiver.id}')">删除</a></td>
                                        </tr>
                                    </g:each>
                                </g:if>
                            </table>
                         </div>

                         <div id="bottom-bottom" style="background:#cdd8e9;">
                             <span id='showcount' style="padding-left:10px;line-height:32px;color:#1e1e1e;font-size:13px;">
                                 <g:if test='${receivers}'>
                                     您选择了${receivers.size()}位收信人！
                                 </g:if>
                             </span>
                             <span style="padding-right:35px;float:right;margin-top:5px;">
                                 <a href="javascript:void(0);" id="btn_confirm" class='btn_basic green_black '>确定</a>
                                 <a href="javascript:void(0);" id="btn_close" class='btn_basic green_black'>关闭</a>
                                 %{--<input type="button" id="btn_confirm"  value="确定">--}%
                                 %{--<input type="button" id="btn_close" value="关闭">--}%

                             </span>
                         </div>
 					</div>

	    	</div>
	    	<ul id="west-panel" style="top:-11px">
                <ul>
                    <li><a href="#publicAddressId" iconCls="java_icon">公共通讯录</a></li>
                    <li><a href="#privateAddressId" iconCls="c_icon" >个人通讯录</a></li>
                </ul>
                <div id="publicAddressId">

                </div>
                <div id="privateAddressId">

                </div>
            </ul>
    	</div>
    	
	 	<script>
			// 事件绑定
			$(function() {
				$("#realName").keydown(function(e){
					  if(e.keyCode==13){
						  $('#btn_query').click();
						  return false;
						}
			   });
                //绑定查询按钮事件
               $('#btn_query').omButton({
                   		width:80,
                	   onClick:function(){
                    	   var url='';
                    	   var selected=$('#addressId').val();
                    	   if(selected=="privateAddressId"){
                        	   url='${createLink(controller:'AddressBook',action:'selectEmpByGroup')}?'+$('#form_query').serialize();
                        	   }else{
                            	   url='${createLink(controller:'organUser',action:'selectAllUserByOrgan')}?opFlag=1'+"&"+$('#form_query').serialize();
                            	   }
                           buildRightGrid(url);
		   	           }
                });
             //绑定清除按钮事件
              $('#btn_clear').omButton({
              		width:80,
           	   		onClick:function(){
	   	                   	$("#realName").val('');
	   	            }
              });

              $("#btn_close").click(function(){
                  $.omMessageBox.confirm({
                      title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                      content:'确定关闭吗？',
                      onClose:function(v){
                          if(v){
                              close();
                          }
                      }
                  });
              });

               $("#btn_confirm").click(function(){
		       	   		 var showName="";
		                 var ids="";
		                 //将receiverId和receiverName分别用下划线"_"和";"分隔
		                 var d=$("input[name='receiverInfo']");
		                 for(var i=0;i<d.length;i++){
		               		ids+=d[i].value+"_";
		                }
		
		                 var d=$("input[name='returnNames']");
		                 for(var i=0;i<d.length;i++){  
		                		showName+=d[i].value+";";
		                 }
		                 var tabId="${params.tabId}";
		          	  	 parent[tabId].getReceivers(showName,ids);  
		                close();
	   	       });

                
                $('#btn_add').click(function(){
              	  var selected = $('#west-panel').omTree('getSelected');
	               	   //处理没有选中节点的情况
	               	   if(selected==null){
	               		   alert('${message(code: 'organUser.selected.empty.message', default: 'Please select Organization')}');
	                       return;	
	                   }
             		var selections = $('#content').omGrid('getSelections',true);
             		if(selections.length==0){
                 		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                 		return;	
                 	} 

             		var message="";
             		$.each(selections,function(i,item){
                 		var m=addReceiver(item.id,item.realName,item.mPhone,"${params.flag}",item.userName,item.organNames,item.receiverType);
						if(m!=null&&m!=""){
							message+=m+"<br/>";
						}
            			 
                  	});
                  	if(message!=""){
                  		alert(message);
                    }else{
                    	showTopTip("添加成功！");
                    }
                  	
    			});

    			
			});
			
			// ################################加载时执行的语句########################
			$(function() {
				//刚加载页面后使右下部分内容显示
				$('#center-panel').css({visibility:'visible'});
<%--				$('#page1').css({height:$(document).height()-16});--%>
				//布局
				$('#page1').omBorderLayout({
					panels:[{
	           	        id:"center-panel",
	           	     	header:false,
	           	        region:"center"
	           	    },{
	           	        id:"west-panel",
	           	        header:false,
	           	        region:"west",
	           	        width:200
	           	    }]
	            });
	            
				$('#center-panel').omBorderLayout({
					panels:[{
	           	        id:"center-top",
	           	 		header:false,
                        title:"选择人员",
	           	        region:"center",
	           	        height:100
	           	    },{
	           	        id:"center",
	           	     	header:true,
                        title:"${message(code:'receiverList.label',args:['entityName1'])}",
	           	        region:"south",
	           	        height:200,
                        collapsible:true
                    }],
                    fit:true
                });

                $('#center').omBorderLayout({
                    panels:[{
                        id:"bottom-top",
                        header:false,
                        region:"center",
                        height:180
                    },{
                        id:"bottom-bottom",
                        header:false,
                        region:"south",
                        height:32
                    }]
                });
                $("#west-panel").omAccordion({
                    width : 200,
                    height: 500,
                    collapsible:true,
                    onActivate : function(n,event) {
                        if(n==0){
                            $('#addressId').val("publicAddressId");
                            }else if(n==1){
                            	$('#addressId').val("privateAddressId");
                                }
                        $('#nodeId').val('');
                    },
                    onCollapse : function(n,event) {
                    	if(n==0){
                            $('#addressId').val("publicAddressId");
                            }else if(n==1){
                            	$('#addressId').val("privateAddressId");
                                }
                        $('#nodeId').val('');
                    }
                });

                //构建控件
			    $('#publicAddressId').omTree({
			         dataSource : "${createLink(controller:'organization',action:'jsonListTree')}", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       			$('#nodeId').val(node.id);
			       			$("#realName").val('');
                            $('#center-panel').css({visibility:'visible'});
                            var url='${createLink(controller:'organUser',action:'selectEmpByOrgan')}?opFlag=1'+"&"+$('#form_query').serialize();
                            buildRightGrid(url);
                           if(node.children==null){

                           }else{
                               if(node.isExpand=='0'){
                                   $('#publicAddressId').omTree('expand',node);
                                   node.isExpand=1;
                               }else{
                                   $('#publicAddressId').omTree('collapse',node);
                                   node.isExpand=0;
                               }
                           }
		             }
			    });

			    //构建控件
			    $('#privateAddressId').omTree({
			         dataSource : "${createLink(controller:'AddressGroup',action:'jsonListTree')}?opFlag=1", // 后台取数的URL
			       	 simpleDataModel:true,
			       	 onSelect: function(node,event) {
			       			$('#nodeId').val(node.id);
			       			$("#realName").val('');
                            $('#center-panel').css({visibility:'visible'});
                            var url='${createLink(controller:'AddressBook',action:'selectEmpByGroup')}?'+$('#form_query').serialize();
                            buildRightGrid(url);
                           if(node.children==null){

                           }else{
                               if(node.isExpand=='0'){
                                   $('#privateAddressId').omTree('expand',node);
                                   node.isExpand=1;
                               }else{
                                   $('#privateAddressId').omTree('collapse',node);
                                   node.isExpand=0;
                               }
                           }
		             }
			    });
			    buildRightGrid();
			});
			
			//#############################用户自定义方法############################

            function close(){
                parent.closeDialog(window.parent.document.getElementById("receiver_dialog").parentNode.id);
            }
			//构建右侧的表格	
			function buildRightGrid(url){
			$('#content').omGrid({
				dataSource:url,
				singleSelect:false,
				limit : 0, // 分页显示，每页显示12条
                height : 'fit',
				showIndex : true,
                onRowDeselect:function(rowIndex,rowData,event){
                    deleteRowByRowId(rowData.id);
                },
                onRowSelect : function(rowIndex,rowData) {
                    var message=addReceiver(rowData.id,rowData.realName,rowData.mPhone,"${params.flag}",rowData.userName,rowData.organNames,rowData.receiverType,rowData.postsNames);
                    if(message!=""&message!=null){
                  		alert(message);
                        uncheck(rowData.id);
                    }
                },
                onRefresh:function(rowIndex,rowData){//处理选中的记录的回显
                    //获取所有已选择的记录信息
                    var d=$("input[name='hidden_ids']");
                    for(var i=0;i<d.length;i++){
                        check(d[i].value);
                    }

                },

				colModel:[{header : "${message(code: 'bookTicket.name.label', default: 'Name')}", name : 'realName', width : 60,align : 'center',
                                renderer:function(value , rowData , rowIndex){
                                    return rowData.realName+"<input type='hidden' id='hidden_"+rowData.id+"'/> ";
                                }
                            },
                          {header : "${message(code: 'user.organs.label', default: 'Organs')}", name : 'organNames', width : 150, align : 'center'},
                          {header : "${message(code: 'user.posts.label', default: 'Posts')}", name : 'postsNames', width : 120,align : 'center'},
                          {header : "${message(code: 'user.mPhone.label', default: 'Mobile Phone')}", name : 'mPhone', width : 96, align : 'center'}
                        ]

            });
		}
		    function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 1000
		        });
		    }

            /**
             * 往选中人员表格中添加一行数据
             * @param userId 系统用户id
             * @param realName 用户真实姓名
             * @param mPhone手机号
             * @param flag 验证标识 needMPhone进行手机验证；needSystemUser进行是否系统用户验证
             * @param userName 系统用户登录账号
             * @param organNames 系统用户所属组织或所属分组
             * @param receiverType 选中人员的类型 =systemUser为系统用户；否则为外部人员
             */
		    function addReceiver(userId,realName,mPhone,flag,userName,organNames,receiverType,postsNames){
                if(flag=='needMPhone'){
                    var tell=mPhone
                    if(tell==''||tell==null){
                        return "["+realName+"]"+"${message(code: 'shortMessage.empty.message')}" ;
                    }
                }else if(flag=="needSystemUser"){
                    if(receiverType=='systemUser'){

                    }else if(receiverType=='notUsed'){
                    	return "["+realName+"]"+"${message(code: 'user.notUsed.label')}";
                    }else{
                        return "["+realName+"]"+"${message(code: 'insideNote.noSystemUser.label')}";
                    }
                }

                //验证数据是否包含在不能添加的数据里
                var canNotAddIds="${canNotAddIds}";
                var ids=canNotAddIds.split('_');
                for(var i=0;i<ids.length;i++){
                    if(ids[i]==userId){
                        return "["+realName+"]"+"${msg}";
                    }
                }
			    //验证数据是否已存在
		    	 var d=$("input[name='receiverInfo']");
                 for(var i=0;i<d.length;i++){
                     var userInfo = d[i].value.split(";");
                     var uid = userInfo[0];
                     if(uid==userId){
						return "["+realName+"]"+"${message(code: 'insidenote.haveselected.message')}";
                     }
                 }
			    var sysName='';
			    if(receiverType=="systemUser"){
			    	sysName=userName;
                }else{
                    sysName=organNames;
                }
			    
		    	   var table=document.getElementById("receivers_table");
		    	   var count=table.rows.length;
		    	   var row=table.insertRow(table.rows.length);
		    	   row.id=userId;

                   var receiverInfo=userId+';'+userName+";"+realName+';'+organNames+";"+mPhone+";"+receiverType+";"+postsNames;
		    	   var col=row.insertCell(0);
		    	   col.innerHTML=realName+"<input type='hidden' name='receiverInfo' value='"+receiverInfo+"'/>"+
		    	           "<input type='hidden' name='returnNames' value='"+realName+"'/>"+
                           "<input type='hidden' name='hidden_ids' value='"+userId+"'/>";
                      col.align="center" ;
                      col.width="100px" ;

                    var col=row.insertCell(1);
                    col.innerHTML=organNames;
                    col.align="center" ;
                    col.width="250px" ;

		    	   var col=row.insertCell(2);
		    	   col.innerHTML=mPhone;
		    	   col.align="center" ;
		    	   col.width="100px" ;

		    	   
		    	   var col=row.insertCell(3);
		    	   col.innerHTML="<a href='#'  class='btn_basic gray_balck' onclick="+'"delRow('+"'"+userId+"'"+')">删除</a>';
		    	   col.align="center" ;
                   col.width="60px";
		    	   count++;
				$("#showcount").html('您选择了'+count+'位收信人！');
		       }
            /**
             *@Description 从选中表中删除指定行,并从grid列表中取消选中状态
             *@Params userId用户id
             */
		    function delRow(id){
                $("#"+id).remove();
                uncheck(id);
                var table=document.getElementById("receivers_table");
		    	var count=table.rows.length;
		    	$("#showcount").html('您选择了'+count+'位收信人！');
            }
            /**
             * @Description 从选中表中删除选中记录行
             * @param userId
             */
            function deleteRowByRowId(userId){
                $("#"+userId).remove();
                var table=document.getElementById("receivers_table");
		    	var count=table.rows.length;
		    	$("#showcount").html('您选择了'+count+'位收信人！');
            }
            /**
             * @Description 根据条件取消选中状态
             * @param id 用户id
             */
            function uncheck(id){
                //获取记录在当前grid中的行
                var o=$("#hidden_"+id).parent().parent().parent();
                //判断记录在当前grid列表中是否存在，如果存在就将grid列表中的记录选中
                if(typeof(o)!='undefined'){
                    o.removeClass("om-state-highlight");
                    o.removeClass('selected');
                }
            }
            /**
             * @Description 根据条件选中记录
             * @param id用户id
             */
            function check(id){
                //获取记录在当前grid中的行
                var o=$("#hidden_"+id).parent().parent().parent();
                //判断记录在当前grid列表中是否存在，如果存在就将grid列表中的记录选中
                if(typeof(o)!='undefined'){
                    o.addClass("om-state-highlight");
                    o.children(1).addClass('selected');
                }
            }

 	 	</script>
	</body>
</html>
