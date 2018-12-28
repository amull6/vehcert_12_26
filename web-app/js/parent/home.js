	//这个方法用来弹出系统提醒消息，它到后台取数并逐一用右下角弹窗方式提醒
		function showOne(url){
			//获取未读短信的数量，弹出未读短信提示框
			$.post(url,{},function(data,textStatus){
                //判断是否已经退出系统
	   			if(textStatus){ 
	   				var lst=eval(data);
	   				if(lst.length>0){
	   					$.each(lst,function(i,item){
		   		   			var content=item.content;
		   		   			if(item.status==1){
		   		   				content+='<a href="javascript:showAffair('+"'"+item.recordId+"','"+item.module+"'"+')">查看</a>'
		   		   			}
	                 		$.omMessageTip.show({
			   		            title : '系统提醒',
			   		            content:content
			   		        });
            			 
                  		});
	   				}
	         }
	       	},'text');
		} 
		
		//延迟显示内容
	    function showContent(){
			$("#div_wrap").css('display','block');
			
			//不能放在上一句的前面，当页面div_wrap的display为none时初始化address然后再设置div_wrap的display为block时，omgrid的分页信息不显示
			$("#address_id0").click();
		}

		//显示未读短信列表
		function showMessage(url,title){
			 var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="auto" src="'+url+'"></iframe>';
			showDialogForIndex('div_wrap',1,content,title,1200,0);
		}
		function showMessageHou(url,title){
			 var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="no" src="'+url+'"></iframe>';
			showDialogForIndex('asix_box',1,content,title,1200,0);
		}
		//显示在线用户列表
		function showUser(url,title){
			 var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-10px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="no" src="'+url+'"></iframe>';
			showDialogForIndex('div_wrap',1,content,title,1200,0);
		}
		//显示未接受协同列表
		function showAffair(url,title){
			 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-10px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+url+'"></iframe>';
			 showDialogForIndex('div_wrap',1,content,title,800,500);
		}
		//跳转到邮箱页面
		function DirectLogin(url) { 
			var vstrServer='mail.wuzheng.com.cn';
			var vstrUsername='';
			var vstrPassword='';
			$.post(url,{},function(data,textStatus){
		   		if(textStatus){
		   			var data= eval( "(" + data + ")" );
		   			if(data.flag){
		   				vstrUsername=data.eMail;
		   				vstrPassword=data.password;
		   				login(vstrServer, vstrUsername, vstrPassword);
					}else{
						alert('邮箱信息设置错误！');
					}
		       	}else{
		       		alert('网络通信错误！');
		       	}
		   		
		    },'text');
		}
		function login(vstrServer, vstrUsername, vstrPassword) { 
		    var strUrl = "https://" + vstrServer + "/owa/auth/owaauth.dll"; 
		    var strExchange = { destination: 'https://' + vstrServer + '/owa', 
		        flags: '0', forcedownlevel: '0', 
		        trusted: '0', isutf8: '1', username:vstrUsername, password: vstrPassword 
		    }; 
		    var myForm = document.createElement("form"); 
		    myForm.method = "post"; 
		    myForm.target='_blank';
		    myForm.action = strUrl; 
		    for (var varElement in strExchange) { 
		        var myInput = document.createElement("input"); 
		        myInput.setAttribute("name", varElement); 
		        myInput.setAttribute("value", strExchange[varElement]); 
		        myForm.appendChild(myInput); 
		    } 
		    document.body.appendChild(myForm); 
		    myForm.submit(); 
		    document.body.removeChild(myForm); 
		} 
		/*
		 * 通知新闻块
		 * rootId栏目标题的id
		 * curNum当前选中栏目，0第一个栏目；1第二个栏目
		 * totalCount 总栏目数
		 * cotentRootId 内容区域的id前缀
		 * url获取内容的url
		 * showCount 显示记录数
		 * showMoreUrl显示更多的url
		 * detailUrl显示详情的url
		 * maxShowCount一行显示的最多字数
		 * categoryTypeCode栏目的编码
		 * moreId 更多按钮的id
		 * timeType时间格式 0不显示时间；1显示时间到天；2显示时间到秒 默认显示时间到秒
		 */
		function changeMessage(rootId,curNum,totalCount,contentRootId,url,showCount,showMoreUrl,detailUrl,maxShowCount,categoryTypeCode,moreId,timeType){
			//设置当前选中tab的样式
			$('#'+rootId+curNum).attr('class','tbg2');
			//设置当前选中tab对应的内容，并显示
			getContent(url,contentRootId+curNum,showCount,detailUrl,maxShowCount,curNum,categoryTypeCode,moreId,timeType);
			//将原来的click事件注销
			$("#"+moreId).unbind('click');
			//绑定新的click事件
			$("#"+moreId).click(function(){
				showMore(showMoreUrl,categoryTypeCode);
			});
			$("#"+contentRootId+curNum).attr('style','display:block;');
			
			//设置未选中tab的样式
			for(var i=0;i<totalCount;i++){
				if(i!=curNum){
					$('#'+rootId+i).attr('class','tbg3');
					$("#"+contentRootId+i).attr('style','display:none;');
				}
			}
		}
		//ajax获取通知新闻内容信息
		function getContent(url,id,maxSize,detailUrl,maxShowCount,curNum,categoryTypeCode,moreId,timeType){
			//ajax获取数据
			$.post(url,{maxSize:maxSize,categoryCode:categoryTypeCode},function(data,status){
				if(data!=null&&data=="session is loginout"){
                    exitSystem();
                }
				if(status){
					var count=0;
					var content='';
					if(data.length>0){
						$.each(data, function(i, value){
							count+=1;
							var title=value.title;
							var zhuangtai=" ";
							if(title.length>maxShowCount){
								title=value.title.substr(0,maxShowCount)+'...';
							}
							//设置弹出框的title
//							if(curNum==0){
//							showTitle='通知公告';
//						}
						 if(categoryTypeCode=='notes'){
							 	showTitle='通知公告';
							}else if (categoryTypeCode=='article'){
                             showTitle='通知公告';
                             }else if (categoryTypeCode=='newProduct'){
                             showTitle='新产品公告';
							}else if (categoryTypeCode=='news'){
								showTitle='公司新闻';
							}else if (categoryTypeCode=='itAndPro'){
                                showTitle='两化融合';
                            }else if (categoryTypeCode=='myAffairs'){
									zhuangtai=value.staut;
									if(value.staut==0){
										zhuangtai="待发";
									}else if(zhuangtai==10){
										zhuangtai="执行中";
									}else if(zhuangtai==20){
										zhuangtai="结束";
									}else if(zhuangtai==30){
										zhuangtai='中止';
									}
								showTitle='我的协同';
							}else if (categoryTypeCode=='requredAffairs'){
									zhuangtai=value.stats;
									if(value.stats==0){
                                        zhuangtai='<span style="color:red;">未阅</span>';
									}else if(zhuangtai==10){
										zhuangtai="已阅";
									}else if(zhuangtai==20){
										zhuangtai="已完成";
									}else if(zhuangtai==30){
										zhuangtai="已拒绝";
									}
								showTitle='待办协同';
							}else if (categoryTypeCode=='doneAffairs'){
                             zhuangtai=value.stats;
                             if(value.stats==0){
                                 zhuangtai='<span style="color:red;">未阅</span>';
                             }else if(zhuangtai==10){
                                 zhuangtai="已阅";
                             }else if(zhuangtai==20){
                                 zhuangtai="已完成";
                             }else if(zhuangtai==30){
                                 zhuangtai="已拒绝";
                             }
                             showTitle='已办协同';
                            }else{
								showTitle='参数传递错误';
							}
							var createTime='';
							if(timeType==0){
								createTime='';
							}else if(timeType==1){
								if(value.createTime!=null){
									createTime=value.createTime.substr(0,10);
								}
								
							}else if(timeType==2){
								if(value.createTime!=null){
									createTime=value.createTime.substr(0,19);
								}
							}else{
								if(value.createTime!=null){
									createTime=value.createTime.substr(0,10);
								}
							}
							content+='<li><span>'+createTime+'</span> <a href="javascript:void(0);" onclick="showDetail('
                                +"'"+detailUrl+"'"+','+"'"+value.id+"','"+categoryTypeCode+"','"+showTitle+"')"+'" title="'+value.title+'">'+title+'</a>';
							if(value.createTime!=null){
								var time=value.createTime.substr(0,10).split("-");
								var t=Date.parse(new Date())-Date.parse(new Date(time[0],time[1]-1,time[2]));
								if(t<=24*60*60*1000*3){
									content+='<img style="margin-left:10px;" src="../images/parent/index/new.gif"/>';
								}
							}
							
							content+='</li>';
							content+='<span style="width:80px;float:right;text-align:center;margin-top:-19px;">'+zhuangtai+'</span>';
						});
					}else{
						content='<li><span></span> '+'没有记录！'+'</li>';
					}
					$("#"+id).html(content);
					
					if(count<maxSize){
						$("#"+moreId).css('display','none');
					}else{
						$("#"+moreId).css('display','inline');
					}
				}
				
			});
		}
		//显示更多通知
		function showMore(url,categoryTypeCode){
			 var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-10px;'+
				  'margin-right:-500px;text-align:center; width:100%;height:100%" '+
				  'scrolling="no" src="'+url+'&categoryCode='+categoryTypeCode+'"></iframe>';
			 var title='通告通知';
			 if(categoryTypeCode=='qywh'){
					title='企业文化';
				}else if (categoryTypeCode=='gszd'){
					title='公司制度';
				}else if (categoryTypeCode=='ygwy'){
					title='员工文苑';
				}else if (categoryTypeCode=='notes'){
					title='通知通告';
				}else if (categoryTypeCode=='news'){
					title='公司新闻';
				}else if (categoryTypeCode=='myAffairs'){
					title='我的协同';
					var delsign='myAffairs';
					showDialogForIndex('div_wrap',1,content,title,1000,550,delsign);
					return;
				}else if (categoryTypeCode=='requredAffairs'){
					title='待办协同';
					var delsign='requredAffairs';
					showDialogForIndex('div_wrap',1,content,title,1000,550,delsign);
					return;
				}else if (categoryTypeCode=='doneAffairs'){
                     title='已办协同';
                     var delsign='doneAffairs';
                     showDialogForIndex('div_wrap',1,content,title,1000,550,delsign);
                     return;
                }else if (categoryTypeCode=='myMeetings'){
					title='我的会议';
				}else if (categoryTypeCode=='meetingsManage'){
					title='会议管理';
				}else if (categoryTypeCode=='meetingsManage'){
                     title='会议管理';
                }else if (categoryTypeCode=='article'){
                 title='通知通告';
                 showDialogForIndex('page',1,content,title,1000,550);
                 return
                }else if (categoryTypeCode=='newProduct'){
                 title='新产品公告';
                 showDialogForIndex('page',1,content,title,1000,550);
                 return
                }else{
					showTitle='参数传递错误';
				}
			showDialogForIndex('div_wrap',1,content,title,1000,550);
		}

    //显示更多通知
    function getArticleByArticleCategoryCode(url,code){
        var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-10px;'+
            'margin-right:-500px;text-align:center; width:100%;height:100%" '+
            'scrolling="no" src="'+url+'&articleCategoryCode='+code+'"></iframe>';
        var title='通告通知';
        var codes=['whxc','lcgl','bgzf','qyjg','itgh','itzl','jcsj','gjjs','pxzl','xmjs','zdlcjs','hyxw','rhyw','zjsd','onlineMessage'];
       var titles=['信息化文化','流程管理','标杆做法','企业架构','IT规划','IT治理','基础与源头','关键技术','培训资料','项目建设','制度流程及能力建设','行业新闻','两化融合要闻','专家视点','互动留言'];
        for(var i=0;i<codes.length;i++){
            if(code==codes[i]){
                title=titles[i]
            }
        }
        showDialogForIndex('div_wrap',1,content,title,1000,550);
    }
		
		//##################################################################		
		//员工活动模块
		function changeActivity(url,id,showCount,showUrl,toMoreUrl,maxShowCount){
			//显示
			getActivity(url,id,showCount,showUrl,maxShowCount);
			$("#moreActivity_id").click(function(){
				moreActivity(toMoreUrl);
			});
		}
		//ajax获取通知新闻内容信息
		function getActivity(url,id,max,showUrl,maxShowCount){
			//ajax获取数据
			$.post(url,{max:max},function(data,status){
				if(status){
					var content='';
					var count=0;
					if(data.length>0){
						$.each(data, function(i, value){
							count+=1;
							var title=value.activityName;
							if(title.length>maxShowCount){
								title=title.substring(0,maxShowCount)+'...';
							}
							
							content+='<li><a href="javascript:void(0);" onclick="showActivity('+"'"+showUrl+"'"+','
                                +"'"+value.id+"','"+id+"')"+'" title="'+value.activityName+'">'+title+'</a>';
							var time=value.createTime.substr(0,10).split("-");
							var t=Date.parse(new Date())-Date.parse(new Date(time[0],time[1]-1,time[2]));	
							if(t<=24*60*60*1000*3){
								content+='<img style="margin-left:10px;" src="../images/parent/index/new.gif"/>';
							}
							content+='</li>';
						});
					}else{
						content='<li><span></span> '+'没有记录！'+'</li>';
					}
					$("#"+id).html(content);
					//当记录数小于最大限制时，不显示更多链接
					if(count<max){
						$("#moreActivity_id").css('display','none');
					}else{
						$("#moreActivity_id").css('display','inline');
					}
				}
				
			});
		}
		//显示更多活动
		function moreActivity(toMoreUrl){
			 var content='<iframe id="activity_more" style="margin-left:-5px;margin-top:-10px;'+
				  'margin-right:-500px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+toMoreUrl+'"></iframe>';
			 var title='员工活动';
			showDialogForIndex('div_wrap',1,content,title,800,500);
		}
		//#################################################################
		//票务预订快
		function applyTicket(url,staffName,cardNum,src,dest,travelDate,travelType,isReturn,showUrl){
			if(!staffName||!staffName||!cardNum||!src||!dest){
				alert("请完善订票信息");
			}else{
				//ajax获取数据
				$.post(url,{staffName:staffName,cardNum:cardNum,src:src,dest:dest,travelDate:travelDate,travelType:travelType,isReturn:isReturn},function(data,status){
					if(status=='success'){
						showTicket(showUrl,data);
						
					}
				});
				
			}
	
			
		}
		
		//#################################################################
		//通讯录栏目
		function changeAddress(rootId,curNum,totalCount,url,urlA){
			//清空查询条件
			$("#searchtxt").val('请输入搜索内容');
			
			//设置当前选中tab的样式
			$('#'+rootId+curNum).attr('class','tbg2');
			//设置未选中tab的样式
			for(var i=0;i<totalCount;i++){
				if(i!=curNum){
					$('#'+rootId+i).attr('class','tbg3');
				}
			}
			//设置显示内容
			if(curNum==0){
				$('#addressTableId').omGrid({
		            dataSource : url,
		            height : 143,
		            limit : 3,
		            colModel : [ 
		            			 {header : '部门', name : 'organNames', width : 120, align : 'center',sort:'clientSide'},
		                         {header : '姓名', name : 'name', width : 98, align : 'center',sort:'clientSide'},
		                         {header : '手机号码', name : 'mPhone', align : 'center', width : 120},
		                         {header : '办公电话', name : 'phone', align : 'center', width : 120}
		                         ] ,
		     		 onRowDblClick:
		     		           function(rowIndex,rowData,event){
		     		            		var id = rowData.id ;
		     		            		showAddress(urlA,id);
		     		 		   }
		        });
			}else if(curNum==1){
				$('#addressTableId').omGrid({
		            dataSource : url,
		            height : 143,
		            limit : 3,
		            colModel : [ 
		                         {header : '分组名称', name : 'group', width : 120, align : 'center',sort:'clientSide'},
		                         {header : '姓名', name : 'name', width : 98, align : 'center',sort:'clientSide'}, 
		                         {header : '手机号码', name : 'mobilePhone', align : 'center', width : 120},
		                         {header : '办公电话', name : 'telPhone', align : 'center', width : 120}
		                         ] ,
		    		onRowDblClick:
		    					function(rowIndex,rowData,event){
		    		     		          var id = rowData.id ;
		    		     		          showAddress(urlA,id);
		    		     		}
		        });
			}else{
				alert('传值错误');
			}

			//设置搜索按钮的事件
			//将原来的click事件注销
			$("#img_search").unbind('click');
			$("#img_search").click(function(){
				search(url);
			});

            //提示双击查看详细信息
            $('#addressTableId').omTooltip({
                trackMouse : true,
                offset:[110,10],
                html : '<span style="color:red;">双击查看详细信息！</span>'
            });

        }
		
		//搜索
		function search(url){
			
			url+='?name='+encodeURI($("#searchtxt").val());
			$('#addressTableId').omGrid('setData',url);
		}
		
		//折叠栏目
		function fold(thisId,contentId){
			var title=$("#"+thisId);
			var content=$("#"+contentId);
			if(title.attr('class')=='window_fold'){
				content.css('display','none');
				title.attr('class','window_unfold');
			}else{
				content.css('display','block');
				title.attr('class','window_fold');
			}
		}
		/**
		 * 显示用户指导页面
		 */
		function showHelp(detailUrl,title){
				 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+detailUrl+'"></iframe>';
				    //打开弹出框
			    showDialogForIndex('div_wrap',1,content,title,900,620);
		}
		/**
		 * 显示详情
		 * id公告或新闻id
		 * categoryTypeCode判断是什么类型的文章详情
		 */
		function showDetail(detailUrl,id,categoryCode,title){
				 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-13px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+detailUrl+"?signCreate=myAffair&id="+id+"&categoryCode="+categoryCode+'"></iframe>';
				 
				 if(categoryCode=='myAffairs'||categoryCode=='requredAffairs'){
					 //添加给协同关闭弹窗刷新父页面的标识
					 var delsign=categoryCode;
					 showDialogForIndex('div_wrap',1,content,title,1100,0,delsign);
				 }else{
					 showDialogForIndex('div_wrap',1,content,title,1100,0);
				 }
		}

		/**
		 * 显示活动详情
		 */
		function showActivity(showUrl,id){
			    var title='活动报名'
				 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="yes" src="'+showUrl+"?id="+id+'"></iframe>';
			       showDialogForIndex('div_wrap',1,content,title,760,550);
		}
		/**
		 * 显示订票详情
		 */
		function showTicket(showUrl,id){
		    var title='确认订票信息';
				 var content='<iframe id="detail_dialog" style="margin-left:-5px;margin-top:-20px;'+
				  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
				  'scrolling="no" src="'+showUrl+"?id="+id+'"></iframe>';
			       showDialogForIndex('div_wrap',1,content,title,400,420);
			
		}
		/**
		 * 更改日程时间
		 */
		function changeCalendar(date,event){
        	 var selectedDate = date;
        	 
            var year = selectedDate.getFullYear();
            var month = selectedDate.getMonth() + 1;
            var day = selectedDate.getDate();
            alert(year + "年" + month + "月" + day + "日");
        }
		
		/**
		 * 弹出邮箱信息设置窗口
		 */
		function setEmail(url){
			var title='邮箱信息管理';
			var content='<iframe id="email_dialog" style="margin-left:-5px;margin-top:-10px;'+
			  'text-align:center; width:100%;height:100%" '+
			  'scrolling="no" src="'+url+'"></iframe>';
			showDialogForIndex('div_wrap',1,content,title,460,250);
		}
		/**
		 * 弹出用户通讯录人员详细信息展示窗口
		 */
		function showAddress(url,id){
			var content='<iframe id="iframe_dialog" style="margin-left:-5px;margin-top:-10px;'+
			  'margin-right:-500px;text-align:center; width:100%;height:100%" '+
			  'scrolling="no" src="'+url+"?id="+id+'"></iframe>';
		 var title='联系人信息';
		showDialogForIndex('div_wrap',1,content,title,680,435);
		}
		//两化融合促进的查询页面
		function showIntegration(url,title){
			var content='<iframe  id="iframe_myJob" style="margin-left:-5px;margin-top:-10px;'+
			  'text-align:center; width:100%;height:100%;border:0px solid;" '+
			  'scrolling="yes" src="'+url+'"></iframe>';
		showDialogForIndex('div_wrap',1,content,title,1300,600);
		}
		//日程插件页面
		function showSchedule(url,title,closeUrl){
			var content='<iframe  id="iframe_myJob" style="margin-left:-5px;margin-top:-10px;'+
			  'text-align:center; width:100%;height:100%;border:0px solid;" '+
			  'scrolling="yes" src="'+url+'"></iframe>';
		showDialogForIndex('div_wrap',1,content,title,1200,675,closeUrl);
		}
		/**
		 * 弹出新建日程页面
		 */
		function newSchedule(url,selectedDate,code){
			var title='新建日程';
			var content='<iframe id="schedule_dialog" style="margin-left:-5px;margin-top:-10px;'+
			  'margin-right:-500px;text-align:center; width:100%;height:100%" '+
			  'scrolling="no" src="'+url+"?selectedDate="+selectedDate+"&code="+code+'"></iframe>';
			showDialogForIndex('div_wrap',1,content,title,550,300);
		}
		/**
		 * 弹出编辑日程页面
		 */
		function editSchedule(url,selectedId){
			var title='编辑日程';
			var content='<iframe id="editSch_dialog" style="margin-left:-5px;margin-top:-10px;'+
			  'margin-right:-500px;text-align:center; width:100%;height:100%" '+
			  'scrolling="no" src="'+url+"?selectedId="+selectedId+'"></iframe>';
			showDialogForIndex('div_wrap',1,content,title,550,350);
			
		}

		//日程管理模块中，初始化选择日期为当前系统日期
	    function setDate(){
	    		var now=new Date();
	    		var year=now.getFullYear();
                var month=now.getMonth()+1;
                var day=now.getDate();
                var selectedDate='';
                if(month<10&&day<10){
                	selectedDate=year+"-"+"0"+month+"-"+"0"+day;
                }else if(month<10&&day>=10){
                	selectedDate=year+"-"+"0"+month+"-"+day;
                }else if(month>=10&&day<10){
                	selectedDate=year+"-"+month+"-"+"0"+day;
                }else if(month>=10&&day>=10){
                	selectedDate=year+"-"+month+"-"+day;
                }
                $("#selectedDate").val(selectedDate);
                $("#riqi").html(selectedDate);
	    }	
	    
		//将隐藏域#selectedDate的值赋值为date，本方法会在子页面中调用。
		function setSelectDate(date){
			$("#selectedDate").val(date);
		}
        
      //ajax获取日程管理信息
		function getSchedule(url,type,date){
			$.each(type,function(i,value){
				var code=value;
				$.post(url,{code:code,date:date},function(data,status){
					if(status){
						if(data.length>0){
							var content='';
							$.each(data, function(i, value){
								var liColor="black"
								var selectedDate
								var scheduleDate=$.omCalendar.parseDate(value.scheduleDate, "yy-mm-dd H:i:s");
								scheduleDate.setDate(scheduleDate.getDate() + 1);//因为日程日期精确到天，所以加一天进行判断
								if(date==''||date==null){
									selectedDate=new Date();
								}else{
									selectedDate=$.omCalendar.parseDate(date, "yy-mm-dd");
								}
								if(scheduleDate<selectedDate){
                                    liColor="red";
                                }
								if(value.finishTime!=null&&value.finishTime!=''){
									var finishTime=$.omCalendar.parseDate(value.finishTime, "yy-mm-dd H:i:s");
									if(finishTime<selectedDate){
										liColor="darkGray";
										content+='<li id="'+value.id+'"  class="scheduleClass" title="日程内容：'+value.content+'"style="color:'+liColor+';"><strike>'+value.content+'</strike></li>';
										
									}else{
                                        content+='<li id="'+value.id+'"  class="scheduleClass" title="日程内容：'+value.content+'"style="color:'+liColor+';">'+value.content+'</li>';
                                    }
								}else{
									content+='<li id="'+value.id+'"  class="scheduleClass" title="日程内容：'+value.content+'"style="color:'+liColor+';">'+value.content+'</li>';
								}
								
							});
							$("#"+code).html(content);
							
							$.each(data, function(i, value){
								
								$("#"+value.id).bind('contextmenu',function(e){
									$("#selectedLi").val(value.id);
					         		$('#contextMenu').omMenu('show',e);
								});	
							});
							
							$(".scheduleClass").omDraggable({
								revert:"invalid",
								helper: "original",
								cursor:"move",
								containment: "#schedule_content"
							});
						}else{
							var content='';
							$("#"+code).html(content);
						}
					}
					
				});
			});
			//ajax获取数据
			
		}
        
        //初始化日程信息拖动效果 url改变日程级别的action;getScheduleUrl获取日程信息的url
        function initSchedule(url,getScheduleUrl){
        	//重要且紧急
			$('.scon:eq(0)').omDroppable({
				accept:"#niandu li,#iandnu li,#niandnu li",
				onDrop: function( source, event ) {
					var id=source[0].id;//日程记录id
					var selectedDate=$("#selectedDate").val();//获取隐藏域中保存的选中的日程时间
					var code="iandu";
					//ajax更新日程记录的紧急程度
					$.post(url,{"code":code,"id":id},function(data,textStatus){
		        		  getSchedule(getScheduleUrl,["iandu","iandnu","niandu","niandnu"],selectedDate);
		                },'text');
					//ajax刷新首页面日程信息
				}
			});
			//不重要紧急
			$('.scon:eq(1)').omDroppable({
				accept:"#iandu li,#iandnu li,#niandnu li",
				onDrop: function( source, event ) {
					var id=source[0].id;
					var selectedDate=$("#selectedDate").val();//获取隐藏域中保存的选中的日程时间
					var code="niandu";
					//ajax更新日程记录的紧急程度
					$.post(url,{"code":code,"id":id},function(data,textStatus){
				    	  getSchedule(getScheduleUrl,["iandu","iandnu","niandu","niandnu"],selectedDate);
		            },'text');
				}
			});
			//重要不紧急
			$('.scon:eq(2)').omDroppable({
				accept:"#iandu li,#niandu li,#niandnu li",
				onDrop: function( source, event ) {
					var id=source[0].id;
					var selectedDate=$("#selectedDate").val();//获取隐藏域中保存的选中的日程时间
					var code="iandnu";
					//ajax更新日程记录的紧急程度
					$.post(url,{"code":code,"id":id},function(data,textStatus){
				    	  getSchedule(getScheduleUrl,["iandu","iandnu","niandu","niandnu"],selectedDate);
		            },'text');
				}
			});
			//不重要不紧急
			$('.scon:eq(3)').omDroppable({
				accept:"#iandu li,#niandu li,#iandnu li",
				onDrop: function( source, event ) {
					var id=source[0].id;
					var selectedDate=$("#selectedDate").val();//获取隐藏域中保存的选中的日程时间
					var code="niandnu";
					//ajax更新日程记录的紧急程度
					$.post(url,{"code":code,"id":id},function(data,textStatus){
				    	  getSchedule(getScheduleUrl,["iandu","iandnu","niandu","niandnu"],selectedDate);
		                },'text');
				}
			});
        }
        
        function initArticles(containerId,url,categoryTypeCode,showNum,showCount,showUrl,toMoreUrl){
        	//ajax获取数据
			$.post(url,{maxSize:showNum,categoryCode:categoryTypeCode},function(data,status){
				if(status){
					var count=0;
					var content='';
					if(data.length>0){
						$.each(data, function(i, value){
							count+=1;
							var title=value.title;
							if(title.length>showCount){
								title=value.title.substr(0,showCount)+'...';
							}
							//设置弹出框的title
							var showTitle="";
							if(categoryTypeCode=='qywh'){
								showTitle='企业文化';
							}else if (categoryTypeCode=='gszd'){
								showTitle='公司制度';
							}else if (categoryTypeCode=='ygwy'){
								showTitle='员工文苑';
							}else{
								showTitle='参数传递错误';
							}
							content+='<li> <a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"
                                +value.id+"','"+categoryTypeCode+"','"+showTitle+"')"+'" title="'+value.title+'">'+title+'</a>';
							var time=value.createTime.substr(0,10).split("-");
							var t=Date.parse(new Date())-Date.parse(new Date(time[0],time[1]-1,time[2]));
							if(t<=24*60*60*1000*3){
								content+='<img style="margin-left:10px;" src="../images/parent/index/new.gif"/>';
							}
							content+='</li>';
						});
					}else{
						content='<li><span></span> '+'没有记录！'+'</li>';
					}
					$("#"+containerId).html(content);
					
					if(count<showNum){
						if(categoryTypeCode=='qywh'){
							$("#moreCulture_id").css('display','none');
							showMoreArticle('moreCulture_id',toMoreUrl,categoryTypeCode);
						}else if (categoryTypeCode=='gszd'){
							$("#moreSystem_id").css('display','none');
							showMoreArticle('moreSystem_id',toMoreUrl,categoryTypeCode);
						}else if (categoryTypeCode=='ygwy'){
							$("#moreWenYuan_id").css('display','none');
							showMoreArticle('moreWenYuan_id',toMoreUrl,categoryTypeCode);
						}else{
							showTitle='参数传递错误';
						}
						
					}else{
						if(categoryTypeCode=='qywh'){
							$("#moreCulture_id").css('display','inline');
							showMoreArticle('moreCulture_id',toMoreUrl,categoryTypeCode);
						}else if (categoryTypeCode=='gszd'){
							$("#moreSystem_id").css('display','inline');
							showMoreArticle('moreSystem_id',toMoreUrl,categoryTypeCode);
						}else if (categoryTypeCode=='ygwy'){
							$("#moreWenYuan_id").css('display','inline');
							showMoreArticle('moreWenYuan_id',toMoreUrl,categoryTypeCode);
						}else{
							showTitle='参数传递错误';
						}
					}
				}
			});
			
        }
        function showMoreArticle(moreId,showMoreUrl,categoryTypeCode){
        	//将原来的click事件注销
			$("#"+moreId).unbind('click');
			//绑定新的click事件
			$("#"+moreId).click(function(){
				showMore(showMoreUrl,categoryTypeCode);
			});
        }
        
        /*
		* 初始化快捷系统入口
		*url获取快捷系统入口的数据源url
		*errorMessage 错误信息
		*/
		function initLinkTools(url,errorMessage){
			//加载左下方导航菜单
	 		$('#linkTool').omTree({
		         dataSource : url+'?date='+new Date(), // 后台取数的URL
		       	 simpleDataModel:true,
		       	 showIcon:false,
		       	 onClick: function(node,event) {
		       		if(node.children==null){
				       var url=node.href;
	                   if(url==null || url.length==0){
						   alert(errorMessage);
		               }else{
		            	   window.open(url,'newwindow');
			           }
		       		}else{
                           if(node.isExpand=='0'){
                               $('#linkTool').omTree('expand',node);
                               node.isExpand=1;
                           }else{
                               $('#linkTool').omTree('collapse',node);
                               node.isExpand=0;
                           }

                   }
	            }
			       	 
		    });
		}
		   /*
		* 初始化常用链接
		*url获区常用链接的数据源url
		*errorMessage 错误信息
		*/
		function initUserLinks(url,errorMessage){
			//加载左下方导航菜单
	 		$('#userLinks').omTree({
		         dataSource : url+'?date='+new Date(), // 后台取数的URL
		       	 simpleDataModel:true,
		       	 showIcon:false,
		       	 onClick: function(node,event) {
		       		if(node.children==null){
				       var url=node.href;
	                   if(url==null || url.length==0){
						   alert(errorMessage);
		               }else{
		            	   window.open(url,'newwindow');
			           }
		       		}else{
                           if(node.isExpand=='0'){
                               $('#userLinks').omTree('expand',node);
                               node.isExpand=1;
                           }else{
                               $('#userLinks').omTree('collapse',node);
                               node.isExpand=0;
                           }

                    }

	            }
			       	 
		    });
		}
		/**
		*初始化我的工作
		*errorMessage 错误信息
		*/
		function initMyJob(url,errorMessage){
			//加载左下方导航菜单
	 		$('#myJob').omTree({
		         dataSource : url+'?date='+new Date(), // 后台取数的URL
		       	 simpleDataModel:true,
		       	 showIcon:false,
		       	 onClick: function(node,event) {
		       		if(node.children==null){
				       var url=node.href;
	                   if(url==null || url.length==0){
						   alert(errorMessage);
		               }else{
		            	    var content='<iframe  id="iframe_myJob" style="margin-left:-8px;margin-top:-10px;'+
				 				  'text-align:center; width:100%;height:100%;border:0px solid;" '+
				 				  'scrolling="auto" src="'+url+'"></iframe>';
                           var title=node.text;

                           showDialogForIndex('div_wrap',1,content,title,1300,600);
			           }
		       		} else{
                           if(node.isExpand=='0'){
                               $('#myJob').omTree('expand',node);
                               node.isExpand=1;
                           }else{
                               $('#myJob').omTree('collapse',node);
                               node.isExpand=0;
                           }

                    }
	            }
			       	 
		    });
		}
		/**
		*根据url获取会议
		*/
		function getmeetings(url,id,max,showUrl,maxShowCount,id2,opflag,showMoreUrl,categoryTypeCode){
			$.post(url,{max:max},function(data,status){
				
				if(status){
					var content='';
					var count=0;
					var time1=new Date();
					if(data.rows.length>0){
						$.each(data.rows, function(i, value){
							count+=1;
							var title=value.name;
							if(title.length>maxShowCount){
								title=title.substring(0,maxShowCount)+'...';
							}
							var createTime=value.applyTime.substr(0,10);
							var status='';
							    var endTime=value.endTime;
								var time2=$.omCalendar.parseDate(endTime, "yy-mm-dd H:i:s");
								 if(value.status==0){
									 status='&nbsp'+"申请中";
								}else if(value.status==1&&time1<time2){
									status='&nbsp'+"审核通过";
								}else if(value.status==2){
									status='&nbsp'+"审核未通过";
								}else if(value.status==3||(value.status==1&&time1>=time2)){
									status='&nbsp'+"已结束";
								}else if(value.status==4){
									status='&nbsp'+"已取消";
									}
							content+='<li><span >'+createTime+'&nbsp'+'</span><span style="width:380px;"><a href="javascript:void(0);" onclick="showMeeting('
                                +"'"+showUrl+"'"+','+"'"+value.id+"','"+opflag+"')"+'" title="'+value.name+'">'+title+'</a></span></li>';
							content+='<span style="width:80px;float:right;text-align:center;margin-top:-19px;">'+status+'</span>';
						});
					}else{
						content='<li><span></span> '+'没有记录！'+'</li>';
					}
					$("#"+id).html(content);
					//绑定更多按钮事件
					$("#"+id2).click(function(){
						showMore(showMoreUrl,categoryTypeCode);
					});
					//当记录数小于最大限制时，不显示更多链接
					if(count<max){
						$("#"+id2).css('display','none');
					}else{
						$("#"+id2).css('display','inline');
					}
				}
				
			});
		}
		function showMeeting(showUrl,id,opflag){
		    var title='会议详情';
			 var content='<iframe id="iframe_myJob" style="margin-left:-5px;margin-top:-20px;'+
			  'margin-right:-50px;text-align:center; width:100%;height:100%" '+
			  'scrolling="yes" src="'+showUrl+"?id="+id+"&opflag="+opflag+'"></iframe>';
		       showDialogForIndex('div_wrap',1,content,title,900,550);
	}
	
		
		function changeWork(rootId,curNum,totalCount,contentRootId,url,toDoUrl,typeCode,toCreateUrl,detailUrl,maxNum,maxCount,userName){
			//设置当前选中tab的样式
			$('#'+rootId+curNum).attr('class','tbg2');
			//设置当前选中tab对应的内容，并显示
			getWorkContent(url,contentRootId+curNum,toDoUrl,typeCode,toCreateUrl,detailUrl,maxNum,maxCount,userName);
			//将原来的click事件注销
			
			$("#"+contentRootId+curNum).attr('style','display:block;');
			
			//设置未选中tab的样式
			for(var i=0;i<totalCount;i++){
				if(i!=curNum){
					$('#'+rootId+i).attr('class','tbg3');
					$("#"+contentRootId+i).attr('style','display:none;');
				}
			}
		}
		//ajax获取通知新闻内容信息
         /**
          *
          * @param url 待办调用时获用户可查看流程的分类信息；结果通知调用时，获取用户的通知结果
          * @param id  页面上区域块的id
          * @param toDoUrl  代办页面调用时进入代办页面；结果通知页面不使用
          * @param typeCode  typeCode='message'获取通知结果信息；typeCode='backlog'获取待办信息
          * @param toCreateUrl  待办页面调用时进入流程发起页面；结果通知页面不适用
          * @param detailUrl暂时没用   z
          * @param maxNum 每行最多显示的字数
          * @param maxCount  每页显示的行数
          * @param userName 登录人的userName 暂时没用
          */
		function getWorkContent(url,id,toDoUrl,typeCode,toCreateUrl,detailUrl,maxNum,maxCount,userName){
//            toDoUrl+="&defaultPage="+encodeURIComponent("我的任务");
//            toCreateUrl+="&defaultPage="+encodeURIComponent("流程启动");
            //将区域块中的内容清空
			$("#"+id).html('');

			if(typeCode=='message'){
				url=url+'?max='+maxCount+'&offset=0';
			}else if(typeCode=='backlog'){
			}
			//ajax获取数据
			$.post(url,{postTime:new Date()},function(data,status){
				
				if(status){
					data=eval(data);
					var messageList="";
					if(data.length>0){
						var count=0;
						$.each(data, function(i, value){
							if(typeCode=='message'){//处理消息信息
								count+=1;
								 var time=value.dateCreated.substr(0,10);
								 var name=value.content;
//								 if(name.length>maxNum){
//									 name=name.substr(0,maxNum)+"...";
//								 }
								messageList+='<li><span>'+time+'</span> <span title='+"'"+value.content+"'"+'>'+value.content+'</span>'+'</li>';

                                $("#"+id).html(messageList);
								
								if(count>=maxCount ){
									$("#showMoreWorkFlowMessage_id").css('display','block');
								}else{
									$("#showMoreWorkFlowMessage_id").css('display','none');
								}
							}else if(typeCode=='backlog'){//处理待办信息
                                $("#showMoreWorkFlowMessage_id").css('display','none');
								var name=value.name;
								var id1=value.id;
                                var assigningCount=value.assigningCount;
                                var delegatingCount=value.delegatingCount;
                                var unAssigneeCount=value.unAssigneeCount;
                                var totalCount=parseInt(assigningCount) +parseInt(delegatingCount)+parseInt(unAssigneeCount);
                                if(totalCount>0){
                                    var content='';
                                    content+='<li style="height:33px;"><span>你有<a href="'+toDoUrl+
                                        '" target="_blank">('+totalCount+')</a>条未处理的【'+name+'】类的申请'+'</span></li>';
                                    $("#"+id).html($("#"+id).html()+content);
                                }
							}
						});
					}else{
                        if(typeCode=='message'){//处理消息信息
                            messageList='<li><span></span> '+'没有记录！'+'</li>';
                            $("#"+id).html(messageList);
                        }else{
                            var content='<li><span></span> '+'没有记录！'+'</li>';
                            $("#"+id).html(content);
                        }
					}

				}
			});
		}
    /*
     * home页面两化融合栏目 图片文章轮显处理方法
     *url 记录获取url；
     *showUrl 记录详情页面url
     *categoryCode栏目编码
     *maxResult 最大记录数
     *maxCount 一行最大字数
     *contentId 记录存放的容器的id
     */
    function getItAndPro(url,showUrl,categoryCode,maxResult,maxCount,contentId,picUrl){
        $.post(url,{categoryCode:categoryCode,maxSize:maxResult},function(data,textStatus){
            if(textStatus=='success'){
                var content="";
                data=eval(data);
                if(data.length>0){
                    $.each(data, function(i, value){
                        var title=value.title;
                        //处理文章题目数不大于maxCount
                        if(title.length>maxCount){
                            title=title.substr(0,maxCount)+'...';
                        }
                        var showTitle="两化融合";
                        content+='<li ><a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+categoryCode+"','"+showTitle+"'"+')" title="'+value.title+'"><img src="'+picUrl+'?picPath='+value.picPath+'"/></a><span>'+title+'</span></li>';
                    });
                }else{
                    $(".itAndPro").css('display','none');
                }
                $("#"+contentId).html(content);
            }
        },'text');
    }

    /*
     * 两化融合页面 文章处理方法
     *codeAndId 栏目编码以及在页面所属区域的Id
     *maxResult 最大记录数
     *url 取数url；
     *maxCount 一行最大字数
     *showUrl 详情页面url
     *toMoreUrl  更多记录查询url
     *timeType  记录的时间显示类型:0 不显示时间，1显示年月日，2显示月日。
     */
    function getItAndProArticles(codeAndId,maxResult,url,maxCount,showUrl,toMoreUrl,timeType){
        $.post(url,{maxSize:maxResult,articleCategoryCode:codeAndId},function(data,status){
            if(data!=null&&data=="session is loginout"){
                exitSystem();
            }
            if(status){
                var count=0;
                var content='';
                data=eval(data);
                if(data.length>0){
                    $.each(data, function(i, value){
                        count+=1;
                        var title=value.title;
                        if(title!=null){
                            if(title.length>maxCount){
                                title=value.title.substr(0,parseInt(parseInt(maxCount)-1))+'...';
                            }
                        }
                        var createTime='';
                        if(timeType==0){
                        }else if(timeType==1){
                            createTime='<span>'+value.createTime.substr(0,10)+'</span>';
                        }else if(timeType==2){
                            createTime='<span>'+value.createTime.substr(5,5)+'</span>';
                        }
                        //组装留言部分的内容
                        if(codeAndId=='onlineMessage'){
                            var replyContent=value.replyContent;
                            if(replyContent.length>maxCount){
                                replyContent=value.replyContent.substr(0,parseInt(parseInt(maxCount)-3))+'......';
                            }
                            content+='<li class="no1"><div class="ask"><span>问</span><a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','互动留言'"+')">'+value.content+'</a></div>'
                                                     +'<div class="answer"><span>答</span>'+replyContent+'<a class="actionA" href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','互动留言'"+')">[详细]</a></div>';
                        }else if(codeAndId=='whxc'){
                            content+='<li>'+createTime+'[<a href="javascript:void(0);" onclick="getArticleByArticleCategoryCode('+"'"+toMoreUrl+"'"+','+"'"+value.articleCategoryCode+"')"+'">'+value.articleCategory+'</a>]&nbsp;<a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"')"+'" title="'+value.title+'">'+title+'</a></li>';
//                            content+='<li>'+createTime+'[<a href="javascript:void(0);" onclick="getArticleByArticleCategoryCode('+"'"+toMoreUrl+"'"+','+"'"+value.articleCategoryCode+"')"+'">'+value.articleCategory+'</a>]&nbsp;<a target="_blank" href="'+showUrl+'?id='+value.id+'" title="'+value.title+'">'+title+'</a></li>';
                        }else if(codeAndId=='dsj'){
                        //组装大事记部分内容
                            content+=value.descrip
                        }else if(codeAndId=='link'){
                        //组装推荐链接部分内容
                            content+='<a target="_blank" href="'+value.code+'">'+value.name+'</a>';
                        }else if(codeAndId=='xmjs'){
                        //组装项目建设部分内容
                            var descrip=value.descrip;
                            if(descrip!=null&&descrip.length>0){
                                if(descrip.length>maxCount){
                                    descrip=descrip.substr(0,parseInt(parseInt(maxCount)-1))+'...';
                                }
                            }else{
                                descrip='暂无简介！';
                            }
                            var classss=['files','mail','cloud','sign'];
                            var ids=['one','two','three','four'];
                            if(i==0){
                                content+='<li id="'+ids[i]+'" class="'+classss[i]+'" ><a href="javascript:void(0);" class="active">'+value.title+'</a><div class="sub-menu" style="display:block;">'+descrip+'<a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')">[详细]</a></div></li>';
                            }else{
                                content+='<li  id="'+ids[i]+'" class="'+classss[i]+'" ><a href="javascript:void(0);" class=" ">'+value.title+'</a><div class="sub-menu" style="display:none;">'+descrip+'<a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')">[详细]</a></div></li>';
                            }
                        }else{
                            content+='<li>'+createTime+'<a href="javascript:void(0);" onclick="showDetail('
                                +"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"')"+'" title="'+value.title+'">'+title+'</a></li>';
                        }
                    });
                }else{
                    content='<li><span></span> '+'没有记录！'+'</li>';
                }
                $("#"+codeAndId).html(content);
                if(codeAndId=='xmjs'){
                    initAccordion();
                }
                if(count<maxResult){
                    $("#more_"+codeAndId).css('display','none');
                }else{
                    $("#more_"+codeAndId).css('display','inline');
                }
            }
        },'text');
        $("#more_"+codeAndId).click(function(){
            if(codeAndId=='dsj'){
                showDetail(showUrl,'dsj',codeAndId,'大事记');
            }else{
                getArticleByArticleCategoryCode(toMoreUrl,codeAndId);
            }
        });
    }

    /*
    *初始化项目建设
     */
    function initAccordion(){
        var accordion_head = $('.accordion > li > a'),
            accordion_body = $('.accordion li > .sub-menu');
        // Open the first tab on load
        accordion_head.first().addClass('active').next().slideDown('normal');
        // Click function
        accordion_head.on('click', function(event) {
            // Disable header links
            event.preventDefault();
            // Show and hide the tabs on click
            if ($(this).attr('class') != 'active'){
                accordion_body.slideUp('normal');
                $(this).next().stop(true,true).slideToggle('normal');
                accordion_head.removeClass('active');
                $(this).addClass('active');
            }
        });
    }

    /*
     * 两化融合页面 图片文章处理方法
     *codeAndId 栏目编码以及在页面所属区域的Id
     *maxResult 最大记录数
     *url 取数url；
     *maxCount 一行最大字数
     *showUrl 详情页面url
     *textType  是否显示文章简介：0不显示，1显示，2制度流程建设图片轮显
     */
    function getItAndProPics(codeAndId,maxResult,url,maxCount,showUrl,picUrl,textType){
        $.post(url,{maxSize:maxResult,articleCategoryCode:codeAndId,hasPic:1},function(data,status){
            if(data!=null&&data=="session is loginout"){
                exitSystem();
            }
            if(status){
                var content='';
                data=eval(data);
                if(data.length>0){
                    $.each(data, function(i, value){
                    var descrip=value.descrip;
                        if(descrip!=null&&descrip.length>0){
                            if(descrip.length>maxCount){
                                descrip=value.descrip.substr(0,parseInt(parseInt(maxCount)-1))+'...';
                            }
                        }else{
                            descrip='暂无简介！';
                        }
                        if(textType==0){
                            content+='<a title="'+value.title+'" href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')"><img alt="'+value.title+'" src="'+picUrl+'?picPath='+value.picPath+'"/><cite>'+value.title+'</cite></a>';
                        }else if(textType==1){
                            content+='<div  class="imgText_img"><a href="javascript:void(0);" title="'+value.title+'" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')"><img alt="'+value.title+'" src="'+picUrl+'?picPath='+value.picPath+'"/></a> </div>'+
                                '<div  class="text"><h4 ><a href="javascript:void(0);" title="'+value.title+'" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')">'+value.title+'</a></h4><span class="progr">'+descrip+'<a class="actionA" href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')">[详细]</a></span></div>';
                        }else if(textType==2){
                            content+='<li><a href="javascript:void(0);" onclick="showDetail('+"'"+showUrl+"'"+','+"'"+value.id+"','"+codeAndId+"','"+value.title+"'"+')"><img alt="'+value.title+'" src="'+picUrl+'?picPath='+value.picPath+'"/></a></li>';
                        }
                    });
                    $("#pic_"+codeAndId).html(content);
                    if(codeAndId=='whxc'){
                        initItAndProWhxc();
                    }
                    if(codeAndId=='zdlcjs'){
                        initItAndProZdjs();
                    }
                }else{
                    $("#pic_"+codeAndId).css('display','none');
                }
            }
        },'text');
    }

    /*
     *动态获取数据并滚动图片播放
     *focus_width 控件显示大小
     *focus_height 显示高度
     *text_height
     *n
     *color1
     *color2
     *url 数据获取url
     *showUrl 详情显示url
     *contentId 显示图片的容器id
     *categoryCode 栏目编码
     *maxResult 最大记录数
     *swfPath swf文件路径
     *@picurl 获取图片的url
     */
    function showScrollPic(focus_width,focus_height,text_height,n,color1,color2,url,showUrl,contentId,categoryCode,maxResult,swfPath,picurl){
        var swf_height = focus_height+text_height;
        var topArr1 = new Array;
        var topArr2 = new Array;
        var topArr3 = new Array;

        $.post(url,{hasPic:1,maxSize:maxResult,articleCategoryCode:categoryCode},function(data,textStatus){
            if(textStatus=='success'){
                data=eval(data);
                $.each(data,function(i,value){
                    topArr1.push(picurl+'?picPath='+value.picPath);
                    topArr2.push(showUrl+'?id='+value.id);
                });

                var pics = topArr1.join("|");
                var links = topArr2.join("|");
                var texts = topArr3.join("|");
                var content='<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'">'
                    +'<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="http://finance.ce.cn/images/slide.swf"><param name="quality" value="high"><param name="bgcolor" value="#ffffff">'
                    +'<param name="menu" value="false"><param name="wmode" value="transparent">'
                    +'<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'&color1='+color1+'&color2='+color2+'">'
                    +'<embed src="'+swfPath+'" wmode="transparent" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'&color1='+color1+'&color2='+color2+'" menu="false" bgcolor="#ffffff" quality="high" width="'+ focus_width +'" height="'+ focus_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer">'
                    +'</object>';
                $("#"+contentId).html(content);
            }
        },'text');
    }

    //初始化两化融合图片轮显
    function initItAndProWhxc(){
        var sWidth = $("#top_gsxw").width(); //获取焦点图的宽度（显示面积）
        var len = $("#top_gsxw ul li").length; //获取焦点图个数
        var index = 0;
        var picTimer;

        //以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
        var btn = "<div class='btnBg'></div><div class='btns'>";
        for(var i=0; i < len; i++) {
            btn += "<span></span>";
        }
        btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
        $("#top_gsxw").append(btn);
        $("#top_gsxw .btnBg").css("opacity",0);

        //为小按钮添加鼠标滑入事件，以显示相应的内容
        $("#top_gsxw .btns span").css("opacity",0.4).mouseover(function() {
            index = $("#top_gsxw .btns span").index(this);
            showPics(index);
        }).eq(0).trigger("mouseover");

        //上一页、下一页按钮透明度处理
        $("#top_gsxw .preNext").css("opacity",0.2).hover(function() {
            $(this).stop(true,false).animate({"opacity":"0.5"},300);
        },function() {
            $(this).stop(true,false).animate({"opacity":"0.2"},300);
        });

        //上一页按钮
        $("#top_gsxw .pre").click(function() {
            index -= 1;
            if(index == -1) {index = len - 1;}
            showPics(index);
        });

        //下一页按钮
        $("#top_gsxw .next").click(function() {
            index += 1;
            if(index == len) {index = 0;}
            showPics(index);
        });

        //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
        $("#top_gsxw ul").css("width",sWidth * (len));

        //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
        $("#top_gsxw").hover(function() {
            clearInterval(picTimer);
        },function() {
            picTimer = setInterval(function() {
                showPics(index);
                index++;
                if(index == len) {index = 0;}
            },4000); //此4000代表自动播放的间隔，单位：毫秒
        }).trigger("mouseleave");
        //显示图片函数，根据接收的index值显示相应的内容
        function showPics(index) { //普通切换
            var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
            $("#top_gsxw ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
            //$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
            $("#top_gsxw .btns span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
        }
    }

    //初始化制度流程建设图片轮显
    function initItAndProZdjs(){
        var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
        var len = $("#focus ul li").length; //获取焦点图个数
        var index = 0;
        var picTimer;

        //以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
        var btn = "<div class='btnBg'></div><div class='btns'>";
        for(var i=0; i < len; i++) {
            btn += "<span></span>";
        }
        btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
        $("#focus").append(btn);
        $("#focus .btnBg").css("opacity",0);

        //为小按钮添加鼠标滑入事件，以显示相应的内容
        $("#focus .btns span").css("opacity",0.4).mouseover(function() {
            index = $("#focus .btns span").index(this);
            showPics(index);
        }).eq(0).trigger("mouseover");

        //上一页、下一页按钮透明度处理
        $("#focus .preNext").css("opacity",0.2).hover(function() {
            $(this).stop(true,false).animate({"opacity":"0.5"},300);
        },function() {
            $(this).stop(true,false).animate({"opacity":"0.2"},300);
        });

        //上一页按钮
        $("#focus .pre").click(function() {
            index -= 1;
            if(index == -1) {index = len - 1;}
            showPics(index);
        });

        //下一页按钮
        $("#focus .next").click(function() {
            index += 1;
            if(index == len) {index = 0;}
            showPics(index);
        });

        //本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
        $("#focus ul").css("width",sWidth * (len));

        //鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
        $("#focus").hover(function() {
            clearInterval(picTimer);
        },function() {
            picTimer = setInterval(function() {
                showPics(index);
                index++;
                if(index == len) {index = 0;}
            },4000); //此4000代表自动播放的间隔，单位：毫秒
        }).trigger("mouseleave");

        //显示图片函数，根据接收的index值显示相应的内容
        function showPics(index) { //普通切换
            var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
            $("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
            //$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
            $("#focus .btns span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
        }
    }
    /*
     * 通知新闻块
     * rootId栏目标题的id
     * curNum当前选中栏目，0第一个栏目；1第二个栏目
     * totalCount 总栏目数
     * cotentRootId 内容区域的id前缀
     * url获取内容的url
     * showCount 显示记录数
     * showMoreUrl显示更多的url
     * detailUrl显示详情的url
     * maxShowCount一行显示的最多字数
     * categoryTypeCode栏目的编码
     * moreId 更多按钮的id
     * timeType时间格式 0不显示时间；1显示时间到天；2显示时间到秒 默认显示时间到秒
     */
    function welcome(curNum,totalCount,contentRootId,url,showCount,showMoreUrl,detailUrl,maxShowCount,categoryTypeCode,moreId,timeType){
        //设置当前选中tab的样式
        //设置当前选中tab对应的内容，并显示
        getContent(url,contentRootId,showCount,detailUrl,maxShowCount,curNum,categoryTypeCode,moreId,timeType);
        //将原来的click事件注销
        $("#"+moreId).unbind('click');
        //绑定新的click事件
        $("#"+moreId).click(function(){
            showMore(showMoreUrl,categoryTypeCode);
        });
        $("#"+contentRootId).attr('style','display:block;');
    }