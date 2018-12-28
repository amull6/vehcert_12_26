<!doctype html>
<html>
	<head>
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'articleCategory.label', default: 'Article Category')}" />
		<g:set var="entityName_value" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="page" style="width:100%;background-color:white;margin:0px 0px">
	    	<div id="center-panel" style="margin-left:4px;border:1px;visibility:hidden;"> 
				<form id="form_query" style="margin:8px;height:30px;">
					<table style="width:100%;border:0px;">
					  <tr>
					    <td style="text-align:right;width:80"><span><g:message code="article.titleC.label" default="TitleC" />：</span></td>
					    <td  style="text-align:left;width:100"><span><g:textField id="titleC" name="titleC" maxlength="200" value="${articleInstance?.TitleC}"/></span></td>
					    <td width="60"></td>
					    <td style="text-align:right;width:80"><span><g:message code="article.keyWords.label" default="Key Words" />：</span></td>
					    <td style="text-align:left;width:100"><span><g:textField id="keyWords" name="keyWords" maxlength="200" value="${articleInstance?.keyWords}"/></span></td>
					    <td width="100"></td>
					    <td align="left" valign="middle">
					    	<input id="btn_query" type="button" value="<g:message code="default.button.query.label" default="Query" />"/>
					    	<input id="btn_clear" type="button" value="<g:message code="default.button.clear.label" default="Clear" />"/>
					    </td>
					  </tr>
					</table>
					<input type="hidden" id="categoryId" name=categoryId>
				</form>
				<fieldset class="buttons" style="margin:0px 8px 8px 4px;">
					<span id='span_publish'>
						<input id="btn_create" type="button" class="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
						<input id="btn_edit" type="button"  class="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
						<input id="btn_delete" type="button"  class="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/>
						<input id="btn_view" type="button"  class="view" value="${message(code: 'default.button.view.label', default: 'View')}"/>
					</span>
					
					<span id='span_audit'  >
						<input id="btn_top" type="button"  class="top" value="${message(code: 'default.button.top.label', default: 'top')}"/>
						<input id="btn_untop" type="button"  class="untop" value="${message(code: 'default.button.untop.label', default: 'untop')}"/>
						<input id="btn_audit" type="button"  class="audit" value="${message(code: 'default.button.audit.label', default: 'Audit')}"/>
						<input id="btn_unaudit" type="button"  class="unaudit" value="${message(code: 'default.button.unaudit.label', default: 'unaudit')}"/>
                        <input id="btn_recommend" type="button"  class="recommend" value="${message(code: 'default.button.recommend.label', default: 'Recommend')}"/>
                        <input id="btn_unrecommend" type="button"  class="unrecommend" value="${message(code: 'default.button.unrecommend.label', default: 'Un Recommend')}"/>
					</span>
				</fieldset>
				<div style="margin-right:8px;margin-top:8px;">
					<div id="article_grid" style="border:0px"></div>
				</div>
			</div>	   
	    	<div id="west-panel">
	    		<div id="article_tree" style="border:1px;"></div>
	    	</div>
    	</div>
	  	
	 	<script>
			// 事件绑定
			$(function() {
				$('#page').css({height:$(document).height()-16});
	                  //绑定查询按钮事件
	                   $('#btn_query').omButton({
		                   		width:80,
		                	   onClick:function(){
				   	                    var url = "${createLink(controller:'article',action:'search')}";
				   	                 	url+='?'+$('#form_query').serialize();
				   	                    $('#article_grid').omGrid('setData',url);
				   	                  }
		                   });
	                   $('#btn_clear').omButton({
			   					width:80,
			   					onClick:function(){
			   						$("#keyWords").val('');
			   						$("#titleC").val('');
			   					}
	                 	  });
	                   $("#titleC").keydown(function(e){
	     				  if(e.keyCode==13){
	     					  $("#keyWords").focus();
	     					  return false;
	     					}
	     		   });
	                  $("#keyWords").keydown(function(e){
	     					 if(e.keyCode==13){
	     			              $('#btn_query').click();
	     						  return false;
	     					}
	     			   });
	                   $('#btn_create').click(function(){
		                	   var url = '${createLink(controller:'article',action:'create')}';
		                	   var selected = $('#west-panel').omTree('getSelected');
		                	   window.location.href = url+'?articleCategory.id='+selected.id+"&selectOwn=${selectOwn}";
	                   });
	                   $('#btn_edit').click(function(){
	                		   var url = '${createLink(controller:'article',action:'edit')}';
	                		   var selected = $('#article_grid').omGrid('getSelections',true);
	                		   if(selected.length==0){
									alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
		                	   }else if(selected.length>1){
			                	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}');
		                	   }else if(selected[0].isAudited==1){
		                		   $.omMessageBox.confirm({
			                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
			                            content:'${message(code: 'edit.confirm.message', default: '您的文章已通过审核，如果您进行编辑，文章将需要重新审核，继续编辑吗？')}',
			                            onClose:function(v){
			                                if(v){
			                                	window.location.href = url+'?id='+selected[0].id+"&selectOwn=${selectOwn}";
			                                		}	
								                }
			                   			});
			                	   }else{
			                		   window.location.href = url+'?id='+selected[0].id+"&selectOwn=${selectOwn}";
				               }
	                   });
	                   
	                   $('#btn_delete').click(function(){
		                   		//这里利用Ajax删除数据
		                   		var selections = $('#article_grid').omGrid('getSelections',true);
		                   		if(selections.length==0){
			                   		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
			                   		return;	
			                   	}
		                   		$.omMessageBox.confirm({
		                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
		                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
		                            onClose:function(v){
		                                if(v){
			                        		var deleteUrl = '${createLink(controller:'article',action:'jsonDelete')}';
			                   				var deleteIds = '';
			                   				$.each(selections,function(i,item){
				                   				deleteIds += item.id+'_';
				                   			});
				                   			$.post(deleteUrl,{"deleteIds":deleteIds},function(data,textStatus){
				                   				var selections = $('#article_grid').omGrid('reload');
				                   				showTopTip(data);
							               			 },'text');
		                                		}	
							                }
		                   			});
		                  	});
	                  	
	                   $('#btn_top').click(function(){
	                	   var selections = $('#article_grid').omGrid('getSelections',true);
	                  		if(selections.length==0){
	                      		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                      		return;	
	                      	}
	                  		$.omMessageBox.confirm({
	                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
	                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
	                            onClose:function(v){
	                                if(v){
	                                	var ids="";
	    	    						$.each(selections,function(i,it){
	    	    							ids+=it.id+"_";
	    	    	                    })
	    	                      		var url = '${createLink(controller:'Article',action:'top')}';
	    	    							$.post(url,{"ids":ids},function(data,textStatus){
		    	    	                   		$('#article_grid').omGrid('reload');
		    	    	                   		var content = "<div class='message'>"+data+"</div>";
		    	    	                   		showTopTip(content);		                   	
	    	    		                	},'text');
	                                	}	
						        }
	                   		});
	                 });
		                 
	                 $('#btn_untop').click(function(){
	                	   var selections = $('#article_grid').omGrid('getSelections',true);
	                  		if(selections.length==0){
	                      		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                      		return;	
	                      	}
	                  		$.omMessageBox.confirm({
	                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
	                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
	                            onClose:function(v){
	                                if(v){
	                                	var ids="";
	    	    						$.each(selections,function(i,it){
	    	    							ids+=it.id+"_";
	    	    	                    })
	    	                      		var url = '${createLink(controller:'Article',action:'untop')}';
	    	    							$.post(url,{"ids":ids},function(data,textStatus){
		    	    	                   		$('#article_grid').omGrid('reload');
		    	    	                   		var content = "<div class='message'>"+data+"</div>";
		    	    	                   		showTopTip(content);		                   	
	    	    		                	},'text');
	                                	}	
						        }
	                   		});
	                 });

                $('#btn_recommend').click(function(){
                    var selections = $('#article_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    $.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                        onClose:function(v){
                            if(v){
                                var ids="";
                                $.each(selections,function(i,it){
                                    ids+=it.id+"_";
                                })
                                var url = '${createLink(controller:'Article',action:'recommend')}';
                                $.post(url,{"ids":ids},function(data,textStatus){
                                    $('#article_grid').omGrid('reload');
                                    showTopTip(data);
                                },'text');
                            }
                        }
                    });
                });

                $('#btn_unrecommend').click(function(){
                    var selections = $('#article_grid').omGrid('getSelections',true);
                    if(selections.length==0){
                        alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
                        return;
                    }
                    $.omMessageBox.confirm({
                        title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
                        content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
                        onClose:function(v){
                            if(v){
                                var ids="";
                                $.each(selections,function(i,it){
                                    ids+=it.id+"_";
                                })
                                var url = '${createLink(controller:'Article',action:'unrecommend')}';
                                $.post(url,{"ids":ids},function(data,textStatus){
                                    $('#article_grid').omGrid('reload');
                                    showTopTip(data);
                                },'text');
                            }
                        }
                    });
                });
		                 
	                 $('#btn_audit').click(function(){
	                	   var selections = $('#article_grid').omGrid('getSelections',true);
	                  		if(selections.length==0){
	                      		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                      		return;	
	                      	}
	                  		$.omMessageBox.confirm({
	                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
	                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
	                            onClose:function(v){
	                                if(v){
	                                	var ids="";
	    	    						$.each(selections,function(i,it){
	    	    							ids+=it.id+"_";
	    	    	                    })
	    	                      		var url = '${createLink(controller:'Article',action:'audit')}';
	    	    							$.post(url,{"ids":ids},function(data,textStatus){
		    	    	                   		$('#article_grid').omGrid('reload');
		    	    	                   		var content = "<div class='message'>"+data+"</div>";
		    	    	                   		showTopTip(content);		                   	
	    	    		                	},'text');
	                                	}	
						        }
	                   		});
	                 });
		                 
	                 $('#btn_unaudit').click(function(){
	                	   var selections = $('#article_grid').omGrid('getSelections',true);
	                  		if(selections.length==0){
	                      		alert('${message(code: 'default.selection.empty.message', default: 'Please select record')}');
	                      		return;	
	                      	}
	                  		$.omMessageBox.confirm({
	                            title:'${message(code: 'default.confirmDialog.title.label', default: 'Confirm Dialog')}',
	                            content:'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}',
	                            onClose:function(v){
	                                if(v){
	                                	var ids="";
	    	    						$.each(selections,function(i,it){
	    	    							ids+=it.id+"_";
	    	    	                    })
	    	                      		var url = '${createLink(controller:'Article',action:'unaudit')}';
	    	    							$.post(url,{"ids":ids},function(data,textStatus){
		    	    	                   		$('#article_grid').omGrid('reload');
		    	    	                   		var content = "<div class='message'>"+data+"</div>";
		    	    	                   		showTopTip(content);		                   	
	    	    		                	},'text');
	                                	}	
						        }
	                   		});
	                 });
		                 
	                   $('#btn_view').click(function(){
	                	   var url = '${createLink(controller:'article',action:'show')}';
	            		   var selected = $('#article_grid').omGrid('getSelections',true);
	            		   if(selected.length==0){
	    						alert('${message(code:'default.grid.not.selection',default:'Please select record')}');
	                	   }else if(selected.length>1){
	                    	   	alert('${message(code:'default.grid.one.selection',default:'Please Select only one Record')}')
	                	   }else{
	                    	   window.location.href = url+'?id='+selected[0].id+"&selectOwn=${selectOwn}";
	    	               }
	                   });
	                
			});
			// 加载时执行的语句
			$(function() {
		          //布局
				$('#page').omBorderLayout({
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
		          //构建左侧树
		          buildLeftTree();

		          setTimeout('inOldPage()',200);
	           });
			
			//从详情页面返回后，进入原来页面
			function inOldPage(){
				var nodeText="${params.currentCategoryId}";
				var target = $('#west-panel').omTree("findNode", "id", nodeText);
				$('#west-panel').omTree('select',target);
				var parent=$('#west-panel').omTree('getParent',target);
				$('#west-panel').omTree('expand',parent);
			}
		           
			//用户自定义方法
			//创建左侧的字典树
			function buildLeftTree(value,event) {
				var getCategoryUrl="${createLink(controller:'ArticleCategory',action:'jsonListTree')}"
				$('#west-panel').omTree({
					dataSource:getCategoryUrl,
					simpleDataModel:true,
					onSelect:function(nodeData, event){
							$('#center-panel').css({visibility:'visible'});
							$('#categoryId').val(nodeData.id);
							if(nodeData.publishAuth==1){
								$("#span_publish").css('display','inline');
							}else{
								$("#span_publish").css('display','none');
							}
							if(nodeData.auditAuth==1){
								$("#span_audit").css('display','inline');
							}else{
								$("#span_audit").css('display','none');
							}
							var dataUrl = "${createLink(controller:'article',action:'getArticleByType')}";
							dataUrl += "?articleCategory="+nodeData.id+"&selectOwn=${selectOwn}";
							buildRightGrid(dataUrl);
							if(nodeData.children==null){

                           }else{
                           		if(!nodeData.isExpand){
                           		nodeData.isExpand='0';
                           		}
                               if(nodeData.isExpand=='0'){
                                   $('#west-panel').omTree('expand',nodeData);
                                   nodeData.isExpand=1;
                               }else{
                                   $('#west-panel').omTree('collapse',nodeData);
                                   nodeData.isExpand=0;
                               }
                           }
						}
				});
			}
			 
			//构建右侧的表格
			function buildRightGrid(url){
				$('#article_grid').omGrid({
					dataSource:url,
					title:"<g:message code="default.list.label" args="[entityName_value]"/>",
					singleSelect:false,
					height:440,
					limit:12,
					colModel:[{header : "${message(code: 'article.titleC.label', default: 'Title Chinese Name')}", name : 'titleC', width : 300, align : 'left',
									renderer:function(value,rowData,rowIndex){
									var data = rowData;
											return  '<span title="'+value+'"><a id="btn_view" class="btn_view"  href="javascript:void(0);" onclick="viewDetail('+rowIndex+')">'+ value+'</a></span>';
									  }
							  },
                              {header : "${message(code: 'article.keyWords.label', default: 'key Word')}", name : 'keyWords', width : 150, align : 'left',
								  renderer:function(value,rowData,rowIndex){
											return '<span title="'+value+'">'+value+'</span>';
									  }
	                          },
                              {header : "${message(code: 'article.isTop.label', default: 'isTop')}", name : 'isTop', width : 50, align : 'center',
                            	  renderer:function(value,rowData,rowIndex){
    								if(value==0){
    									return '未置顶';
    								}else if(value==1){
    									return '已置顶';
    								}else{
    									return '参数错误'
    								}
    							  }
                               },
                            {header : "${message(code: 'default.button.recommend.label', default: 'Recommend')}", name : 'recommend', width : 50, align : 'center',
                                renderer:function(value,rowData,rowIndex){
                                    if(value==0){
                                        return '未推荐';
                                    }else if(value==1){
                                        return '已推荐';
                                    }else{
                                        return '参数错误'
                                    }
                                }
                            },
					          {header : "${message(code: 'article.isAudited.label', default: 'Is Audited')}", name : 'isAudited', width : 60, align : 'center',
                            	   renderer:function(value,rowData,rowIndex){
       								if(value==1){
       									return '已通过审核';
       								}else if(value==2){
       									return '未通过审核';
       								}else{
       									return '未审核';
       								}
       							  }
      					       },
                              {header : "${message(code: 'article.createTime.label', default: 'Create Time')}", name : 'createTime', width : 120, align : 'center'},
                              {header : "${message(code: 'article.lastUpdateTime.label', default: 'Update Time')}", name : 'lastUpdateTime', width :120, align : 'center'}
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
		     function viewDetail(index){
    	        	var data = $("#article_grid").omGrid("getData").rows[index];
    	             var url = '${createLink(controller:'article',action:'show')}';
    	         	 window.location.href = url+'?id='+data.id+"&selectOwn=${selectOwn}";
    	        }
		 </script>
	</body>
</html>
