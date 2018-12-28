
<!doctype html>
<html>
	<head>
        <meta name="layout" content="main">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>消息提醒自定义配置</title>
        <style type="text/css">
        input {margin:2px;padding: 2px}
        </style>
	</head>
	<body>
        <div id="page" style="width:100%;margin-top:5px;margin-left: 8px;">
	    	<div id="center-panel" style="border: none">
                <div id="make-tab" style="border: none">
                         <div id="msgTab">
                             <div  id="msg-center" style="border: none">
                             <div style="width: 904px;float: left; margin: 0px 0 10px 180px;line-height: 25px">
                             
                             
                                 <div style="text-align: center"><h2>协同模块提醒设置</h2></div>
                                 <div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">
                                     <g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
                                         dictionaryType{eq('code','affair')}
                                         order("ordernum","asc")
                                     })}" >
                                         <div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                                             &nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />
                                             <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
                                         </div>
                                     </g:each>
                                 </div>
                                 
                                 
                                 <div style="text-align: center"><h2>其他提醒设置</h2></div>
                                 <div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">
                                     <g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
                                         dictionaryType{eq('code','others')}
                                         order("ordernum","asc")
                                     })}" >
                                         <div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                                             &nbsp;<input type="checkbox" mmm='checkbox1' name=${it.code} value=${it.code} />
                                             <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
                                         </div>
                                     </g:each>
                                 </div>
                                 
                                 
                                 
                             </div>
                             </div>
                             <div id="msg-bottom" style="border: none">
				                <form  style="padding-top: 10px;margin-left: 10px;">
				                    <fieldset class="buttons" style="padding-left:200px;width: 1000px;">
				                        <input id="msg_save" type="button" class="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
				                    </fieldset>
				                </form>
				            </div>
                         </div>
                         <div id="smsTab">
                             <div  id="sms-center" style="border: none">
                                 <div  style="width: 904px;float: left; margin: 20px 0 10px 180px;line-height: 25px">
                                     <div style="text-align: center"><h2>手机短信提醒设置</h2></div>
                                     <div style="float: left; border-top: 1px solid #999;border-left: 1px solid #999">
                                     	<g:if test="${roleFlag!="smsReceiver" }">
                                            <div style="float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;color: red;padding:5px;">
                                     		    您没有接收手机短信提醒的权限，无法进行手机短信提醒配置！
                                            </div>
                                     	</g:if>
                                     	<g:elseif test="${phoneFlag!="phone" }">
                                            <div style="float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;color: purple;padding:5px;">
                                     		    您没有在员工工作平台个人信息中维护手机号信息，无法进行手机短信提醒配置！
                                            </div>
                                     	</g:elseif>
                                     	<g:else>
                                     		<g:each in="${cn.com.wz.parent.system.dictionary.DictionaryValue.createCriteria().list({
                                             dictionaryType{eq('code','mobilMsg')}
                                             order("ordernum","asc")
                                                 })}" >
                                             <div style="width:300px;float: left; border-bottom: 1px solid #999;border-right: 1px solid #999;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                                                 &nbsp;<input type="checkbox" mmm='checkbox2' name=${it.code} value=${it.code} />
                                                 <span title="${it.dicValueNameC }">&nbsp;${it.dicValueNameC }</span>
                                             </div>
                                         </g:each>
                                     	</g:else>
                                         
                                     </div>
                                 </div>
                             </div>
                             <div id="sms-bottom"  style="border: none">
                                <form style="padding-top: 10px;margin-left: 10px;">
                                    <fieldset class="buttons" style="padding-left:200px;width: 1000px;">
                                        <input id="sms_save" type="button" class="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/>
                                    </fieldset>
                                </form>
                             </div>
                         </div>
                    <ul>
                        <li>
                            <a href="#msgTab">系统消息提醒</a>
                        </li>
                        <li>
                            <a href="#smsTab">手机短信提醒</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <script>

     // 事件绑定
		$(function() {
				$("#msg_save").click(function(){
					//获取系统消息提醒选中checkBox的ids
					var msgChecks=$("input:checkbox[mmm='checkbox1']:checked");
					var msgCheckIds='';
					if(msgChecks!=null){
						$.each(msgChecks,function(i,it){
							msgCheckIds+=it.value+':_:';
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
									smsCheckIds+=it.value+':_:';
								});
							}else{
								smsCheckIds='noChoose'
							}
						}else{return;}

						//定义保存配置的url
						var url = '${createLink(controller:'Remind',action:'smsConSave')}';
						//保存配置
						$.post(url,{"smsCheckIds":smsCheckIds,roleFlag:roleFlag},function(data,textStatus){
		                   		var content = "<div class='message'>"+data+"</div>";
		                   		showTopTip(content);
		                	},'text');

	             });
		});


			
                // 加载时执行的语句
                $(function() {
                        $('#page').css({height:$(document).height()-16});
                        //布局
                        $('#page').omBorderLayout({
                            panels:[{
                                id:"center-panel",
                                header:false,
                                region:"center"
                            }]
                        });
                        //创建TAB页签
                        $('#make-tab').omTabs({
                            height: 'fit',
                            closable : false
                        });
                    });


              //页面加载时执行数据回填
    			$(function() {
    				//系统消息提醒数据回填
    				var msgCon="${msgCon}";
    				if(msgCon!=''&&msgCon!=null){
    					var msgIds=msgCon.split(':_:');
    					for(var i=0;i<msgIds.length;i++){
    						$("input[name="+msgIds[i]+"]").attr("checked",true);
    						}
    					}
    				//手机短信提醒数据回填
    				var smsCon="${smsCon}";
    				var roleFlag="${roleFlag}";
    				if(smsCon!=''&&smsCon!=null&&roleFlag=="smsReceiver"){
    					var smsIds=smsCon.split(':_:');
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
