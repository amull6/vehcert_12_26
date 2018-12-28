<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'remindCon.label', default: '消息提醒配置')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
        <div style="width: 904px;float: left; margin: 0px 0 10px 200px;line-height: 20px">
            <div style="text-align: center"><h2>协同模块提醒设置</h2></div>
            <div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">
                <g:each in="${cn.com.wz.system.dictionary.DictionaryValue.createCriteria().list({
                                    dictionaryType{eq('code','affair')}
                                    order("ordernum","asc")
                                })}" >
                    <div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                        &nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />
                        <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
                    </div>
                </g:each>
            </div>
        </div>
	<div>
		<g:if test="${roleFlag=='smsReceiver' }">
			<g:if test="${phoneFlag=='phone' }">
				<div style="width: 904px;float: left; margin: 20px 0 10px 200px;line-height: 20px">
		        <div style="text-align: center"><h2>手机短信提醒设置</h2></div>
		        <div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">
		            <g:each in="${cn.com.wz.system.dictionary.DictionaryValue.createCriteria().list({
		                                dictionaryType{eq('code','mobilMsg')}
		                                order("ordernum","asc")
		                            })}" >
		                <div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
		                    &nbsp;<input type="checkbox" mmm='checkbox2' name=${it.code} value=${it.code} />
		                    <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
		                </div>
		            </g:each>
		        </div>
		    </div>
			</g:if>
		    <g:else>
		    	<div>
		    		您没有在员工工作平台中维护手机号码，请先维护您的个人信息中的手机号。
		    	</div>
		    </g:else>
	    </g:if>
	    <g:else>
	    	<div>
	    		您没有接收手机短信提醒的权限！
	    	</div>
	    	
	    </g:else>
    </div>
        <form id="form" style="margin:30px 8px 8px 14px;">
            <fieldset class="buttons" style="padding-left:240px;width: 1000px;">
                <input id="msg_save" type="button" class="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
                <input id="sms_save" type="button" class="create2" value="${message(code: 'default.button.save2.label', default: 'Save2')}"/>
            </fieldset>
        </form>
		 <script>
			// 事件绑定
			$(function() {
				$("#btn_save").click(function(){
					//获取系统消息提醒选中checkBox的ids
					var msgChecks=$("input:checkbox[mmm='checkbox1']:checked");
					var msgCheckIds='';
					if(msgChecks!=null){
						$.each(msgChecks,function(i,it){
							msgCheckIds+=it.value+'_';
						});
					}else{
						msgCheckIds='noChoose'
						}
					

					//获取手机短信提醒选中checkBox的ids
					var roleFlag="${roleFlag}"
					var smsCheckIds='';
					if(roleFlag=='smsReceiver'){
						var smsChecks=$("input:checkbox[mmm='checkbox2']:checked");
						if(smsChecks!=null){
							$.each(smsChecks,function(i,it){
								smsCheckIds+=it.value+'_';
							});
						}else{
							smsCheckIds='noChoose'
						}
					}
					
					//定义保存配置的url
					var url = '${createLink(controller:'Remind',action:'remindConSave')}'; 
					//保存配置
					$.post(url,{"msgCheckIds":msgCheckIds,"smsCheckIds":smsCheckIds,roleFlag:roleFlag},function(data,textStatus){
	                   		var content = "<div class='message'>"+data+"</div>";
	                   		showTopTip(content);		                   	
	                	},'text');
					
             });


					$("#msg_save").click(function(){
						//获取系统消息提醒选中checkBox的ids
						var msgChecks=$("input:checkbox[mmm='checkbox1']:checked");
						var msgCheckIds='';
						if(msgChecks!=null){
							$.each(msgChecks,function(i,it){
								msgCheckIds+=it.value+'_';
							});
						}else{
							msgCheckIds='noChoose'
							}
						

						
						
						//定义保存配置的url
						var url = '${createLink(controller:'Remind',action:'msgConSave')}'; 
						//保存配置
						$.post(url,{"msgCheckIds":msgCheckIds},function(data,textStatus){
		                   		var content = "<div class='message'>"+data+"</div>";
		                   		showTopTip(content);		                   	
		                	},'text');
						
	             });


						$("#sms_save").click(function(){
							

							//获取手机短信提醒选中checkBox的ids
							var roleFlag="${roleFlag}"
							var smsCheckIds='';
							if(roleFlag=='smsReceiver'){
								var smsChecks=$("input:checkbox[mmm='checkbox2']:checked");
								if(smsChecks!=null){
									$.each(smsChecks,function(i,it){
										smsCheckIds+=it.value+'_';
									});
								}else{
									smsCheckIds='noChoose'
								}
							}
							
							//定义保存配置的url
							var url = '${createLink(controller:'Remind',action:'smsConSave')}'; 
							//保存配置
							$.post(url,{"smsCheckIds":smsCheckIds,roleFlag:roleFlag},function(data,textStatus){
			                   		var content = "<div class='message'>"+data+"</div>";
			                   		showTopTip(content);		                   	
			                	},'text');
							
		             });
			});


			//#############################用户自定义方法############################
			//页面加载时执行
			$(function() {
				//系统消息提醒数据回填
				var msgCon="${msgCon}";
				alert(msgCon);
				if(msgCon!=''&&msgCon!=null){
					var msgIds=msgCon.split('_');
					for(var i=0;i<msgIds.length;i++){
						$("input[name="+msgIds[i]+"]").attr("checked",true);
						}
					}
				//手机短信提醒数据回填
				var smsCon="${smsCon}";
				alert(smsCon);
				if(smsCon!=''&&smsCon!=null){
					var smsIds=smsCon.split('_');
					for(var i=0;i<smsIds.length;i++){
						$("input[name="+smsIds[i]+"]").attr("checked",true);
						}
					}
			});
			function showTopTip(content){
		        $.omMessageTip.show({
		            title : '${message(code: 'default.tip.message.label', default: 'Message')}',
		            content : content,
		            timeout : 3000
		        });
		    }

        </script>
		
	</body>
</html>
