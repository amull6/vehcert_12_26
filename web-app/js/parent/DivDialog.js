//################################DivDialog###################################################
/*
 * div弹出框使用omDialog插件
 * 可以弹出多个弹出框，但在弹出多个弹出框时，多个弹出框的内容中的元素id不能有重复的
 * 关闭弹出框后，会将弹出框元素从页面删除
 * showDialog(isModal,content,title,width,height)在页面弹出指定信息的弹出框
 * showDialogForIndex(parentId,isModal,content,title,width,height)在指定的容器中弹出弹出框
 * update 2013-07-29 huxx 添加了打开弹出框后，返回弹出框的id
 */
			//弹出对话框isModal=1表示弹出模式对话框，否则弹出非模式对话框,title:弹出框的名称,width和height为0时使用默认值,parentId弹出框所属的区域,closeUrl为弹窗关闭时执行事件的Url或标识符
			function showDialogForIndex(parentId,isModal,content,title,width,height,closeUrl){
				width=parseInt(width);
				heigth=parseInt(height);
                if(width==0){
                    width=800;
                }
                if(height==0){
                    height=600;
                }
                //处理不同分辨率下弹出框的自动变化大小
                var screenWidth=screen.width;
                var screenHeight=screen.height;
                if(width>screenWidth-100){
                    width=screenWidth-100;
                }
                if(height>screenHeight-170){
                    height=screenHeight-170;
                }

				var id='';
				if(isModal==1){
					id=createModalDialog(parentId);
				}else{
					id=createModalLessDialog(parentId);
				}

				$("#"+id).html(content);
				//关闭对话框后删除生成的div对话框
				$("#"+id).omDialog({'title':title,
									'width':width,
									'height':height,
									onBeforeClose : function() {
										if(closeUrl){
											if(closeUrl=='myAffairs'){
												parent.$("#affair_id0").click();
											}else if(closeUrl=='requredAffairs'){
												parent.$("#affair_id1").click();
											}else{
												parent.getSchedule(closeUrl,["iandu","iandnu","niandu","niandnu"]);
											}
										}},
									onClose : function() {$("#"+this[0].id).remove();}});
				$("#"+id).omDialog('open');
                return id;
			}
			//为了兼容后台页面的弹出框的实现
			function showDialog(isModal,content,title,width,height){
                var parentId='';
                width=parseInt(width);
                heigth=parseInt(height);
                if(width==0){
                    width=800;
                }
                if(height==0){
                    height=600;
                }
                //处理不同分辨率下弹出框的自动变化大小
                var screenWidth=screen.width;
                var screenHeight=screen.height;
                if(width>screenWidth-100){
                    width=screenWidth-100;
                }
                if(height>screenHeight-170){
                    height=screenHeight-170;
                }
				var id='';
				if(isModal==1){
					id=createModalDialog(parentId);
				}else{
					id=createModalLessDialog(parentId);
				}

				$("#"+id).html(content);
				//关闭对话框后删除生成的div对话框
				$("#"+id).omDialog({
                    'title':title,
                    'width':width,
                    'height':height,
                    onClose : function() {
                        $("#"+this[0].id).remove();
                    }
                });
				$("#"+id).omDialog('open');
				return id;
			}

			//关闭对话框
			function closeDialog(id){
					$("#"+id).omDialog('close');
					
			}

            //获取对话框id
            function getDialogId(){
                for(var i=0;i<100;i++){
                    var id="dialog"+i;
                    if($("#"+id).length==0){
                        return id;
                    }
                }
            }
            //创建模态对话框
            function createModalDialog(parentId){
                var id=getDialogId();
                if(parentId!=null&&parentId.length>0){
                    var dialog='<div style="Z-index:9999;" id="'+id+'" title=""></div>';
                    $("#"+parentId).append(dialog);
                }else{
                    var dialog=document.createElement("div");
                    dialog.id=id;
                    document.body.appendChild(dialog);
                }

                $( "#"+id).omDialog({
                    autoOpen: false,
                    height: 600,
                    width:800,
                    modal: true,
                    title:'ModalDialog'
                });

                return id;
            }
            //创建非模态对话框
            function createModalLessDialog(){
                var id=getDialogId();
                if(parentId!=null&&parentId.length>0){
                    var dialog='<div style="Z-index:9999;" id="'+id+'" title=""></div>';
                    $("#"+parentId).append(dialog);
                }else{
                    var dialog=document.createElement("div");
                    dialog.id=id;
                    document.body.appendChild(dialog);
                }
                $( "#"+id).omDialog({
                    autoOpen: false,
                    height: 600,
                    width:800,
                    modal: false,
                    title:'ModalDialogLess'
                });
                return id;
            }
//################################DivDialogEND###################################################