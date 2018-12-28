<div>
	<label for="receiver">
		<g:message code="remind.receiver.label" default="接收人" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea  type="text" name="receiverName" style="width:400px;height:35px;" id="click_textBox" value="${receiverName}" readonly="true"/>
	  <input type="hidden" name='receiverIds' id="receiverIds" value="${receiverIds}">
</div>
<br/>
<br/>

<div>
	<label for="content">
		<g:message code="insideNote.content.label" default="Content" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="content" style="width:410px;height:200px;"/>
</div>

<script>
			// 事件绑定
			$(function() {
                $("#click_textBox").click(function(){
                	var url = '${createLink(controller:'dialog',action:'receiver')}';
              	   showModelDialog(url);
                });
			});
			//#############################用户自定义方法############################
			function showModelDialog(url){
				    var tabId=window.name;
				    //为了解决在ie下home页弹出框中调用这个页面时window.name获取不到tabId
				    if(tabId==''|| tabId==null){		    	
						tabId="iframe_myJob";
					 }
				    var receiverIds=$("#receiverIds").val();
				    url+="?tabId="+tabId+"&flag=needSystemUser&receiverIds="+receiverIds;
					var content='<iframe id="receiver_dialog" style="margin-left:-10px;margin-top:-10px;margin-right:-50px;text-align:center; width:100%;height:100%" scrolling="yes" src="'+url+'"></iframe>';
					var title='<g:message code='affair.chooseRecipient.label' args='[entityName1]'/>';
					//打开弹出框
					if(tabId=="iframe_myJob"){
						parent.showDialogForIndex('div_wrap',1,content,title,800,560);
					}else if(tabId=="detail_dialog"){
						parent.showDialogForIndex('div_wrap',1,content,title,800,560);
					}else{
						parent.showDialogForIndex('asix_box',1,content,title,800,560);
					}
					
			}

			/**
			*弹出框回调事件
            *showNames 文本框中显示的选中人员的名称
            *receiverInfos 选中人员的信息 字符串形式如：userId;userName;realName;organNames;mPhone;receiverType
			*/
			function getReceivers(showNames,receiverInfos){
				 $("#click_textBox").val(showNames);
				 $("#receiverIds").val(receiverInfos);
			}

 </script>


