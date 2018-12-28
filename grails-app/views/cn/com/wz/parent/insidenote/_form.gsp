<%@ page import="cn.com.wz.parent.insidenote.InsideNote" %>

<div  class="fieldcontain ${hasErrors(bean: insideNoteInstance, field: 'receiverName', 'error')} ">
        <label for="receiverName">
            <g:message code="insideNote.receiverName.label" default="Receiver Name" />

        </label>
        <div id="click_textBox" name="receiverName" class="divForText" style="height: 80px;width:300px;margin-left:307px;margin-top:-20px;">
            <span id="span_selectedUsers">

            </span>
        </div>
        <div style="margin-left: 650px;margin-top:-80px;">
            <div ><input type="text"  id="text_suggestion" name="dd" style=" border: 1px solid #d6d6d6;height:15px;width:100px;overflow:hidden;margin-right:10px;" />输入接收人姓名回车添加</div>
            <div style="margin-top:20px;margin-bottom: 30px;" ><input type="button" class="btn_basic blue_black" style="cursor:pointer;"  name="btn_click" id="btn_click" value="点击选择接收人"/></div>
            %{--当页面只有text文本框时，回车默认提交整个页面，所以加了个隐藏的text元素--}%
            <input type="text" name='dddd' id="text_dddd" style="display: none;"/>
        </div>
        <input type="hidden" name='receiverIds' id="receiverIds" value="${receiverIds}">
</div>


<div class="fieldcontain ${hasErrors(bean: insideNoteInstance, field: 'content', 'error')} required">
	<label for="content">
		<g:message code="insideNote.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="content" id='content' style="width:300px;height:200px;" maxlenght="300" />
	<span style="vertical-align:bottom;">请填写消息内容,不超过300字。</span>
</div>

 <script>
			// 事件绑定
			$(function() {
                adjustNames();
                $("#btn_click").click(function(){
                	var url = '${createLink(controller:'dialog',action:'receiver')}';
             	   showModelDialog(url);
                });

                $('#text_suggestion').omSuggestion({
                    dataSource :"${createLink(controller:'user',action:'getJsonUserList')}?partPageFlag=0",
                    queryName:"realName",
                    listWidth:300,
                    listMaxHeight:300,
                    minChars:1,
                    onSuccess:function(data, textStatus, event){
                        if(eval(data.data).length==0){
                            $('#text_suggestion').omSuggestion('showMessage','<div style="margin-left:5px;color:red;">无提示数据！</div>');
                            return false;
                        }

                    },
                    clientFormatter : function(data,index){
                            return '<div style="">' + data.realName+"|"+data.organNames+"|"+data.postNames+'</div>';
                    },
                    onSelect:function(rowData,text,index,event){

                        var info=rowData.id+";"+rowData.userName+";"+rowData.realName+";"+rowData.organNames+";"+rowData.mPhone+";systemUser;"+rowData.postNames;
                        var infos=$("#receiverIds").val();
                        var flag=infos.indexOf(info);
                        if(flag>=0){
                            $('#text_suggestion').omSuggestion('showMessage','<div style="margin-left:5px;color:red;font-weight:bold;">【'+text+'】已选择!</div>');
                            return false;
                        }
                        infos+=info+"_";
                        $("#receiverIds").val( infos);

                        adjustNames();
                        $('#text_suggestion').val('');

                        return false;

                    }
                });
            });


            //删除选中用户，并从隐藏域中删除
            function deleteSelectedUser(info,it){
                var infos=$("#receiverIds").val();
                $("#receiverIds").val(infos.replace(info+'_',''));
                $(it).parent().remove();
            }

            /**
            * 根据选中的用户信息调整显示的用户名称
             */
            function adjustNames(){
                var infos=$("#receiverIds").val();
                var showText="";
                if(infos.length>0){
                    var list=infos.split('_');
                    if(list.length>0){
                        for(var i=0;i<list.length-1;i++){
                            var info=list[i];
                            var text=info.split(";")[2];
                            showText+='<span contenteditable="false" class="btn_basic green_black" style="line-height:20px;margin:1px 1px;">'+
                                    text+'<span class="close" onclick="deleteSelectedUser('+"'"+info+"'"+',this)">×</span></span>';

                        }
                    }
                }
                $("#span_selectedUsers").html(showText);
            }
			//#############################用户自定义方法############################
			function showModelDialog(url){
				var receiverIds=$("#receiverIds").val();
			    var tabId=window.name;
				//为了解决在ie下home页弹出框中调用这个页面时window.name获取不到tabId
			    if(tabId==''|| tabId==null){
					tabId="iframe_myJob";
				 }
			    url+="?tabId="+tabId+"&flag=needSystemUser";
				var content='<iframe id="receiver_dialog" style="margin-left:-5px;margin-top:-10px;margin-right:-50px;text-align:center;width:100%;height:100%" scrolling="yes" method="post" src="about:blank"></iframe>';
				var title='<g:message code='insideNote.receiverList.label' args='[entityName1]'/>';
					//打开弹出框
				if(tabId=="iframe_myJob"){
					parent.showDialogForIndex('div_wrap',1,content,title,800,560);
				}else{
					parent.showDialogForIndex('asix_box',1,content,title,800,560);
				}

                //解决当选择人很多的情况下，get方式提交时对url字符串长度有限制的问题
                var html = '<form action="'+url+'" method="post" target="_self" id="postData_form">'+
                        '<input id="hidden_receiverIds" name="receiverIds" type="hidden" value="'+receiverIds+'"/>'+
                        '</form>';
                parent.document.getElementById('receiver_dialog').contentWindow.document.write(html);
                parent.document.getElementById('receiver_dialog').contentWindow.document.getElementById("postData_form").submit();
			}

			/**
			*弹出框回调事件
            *showNames 文本框中显示的选中人员的名称
            *receiverInfos 选中人员的信息 字符串形式如：userId;userName;realName;organNames;mPhone;receiverType
			*/
			function getReceivers(showNames,receiverInfos){
				 $("#receiverIds").val(receiverInfos);
                adjustNames();
			}

 </script>
