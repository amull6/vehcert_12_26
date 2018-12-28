<!doctype html>
<html>
<head>
<title><g:message code="default.loginPage.title.label" /></title>
<link rel="shortcut icon" href="${resource(dir: 'images', file: 'parent/favicon.ico')}">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'parent/asix_system4.css')}" type="text/css">
<script src="${resource(dir:"js",file:"parent/jquery-1.7.2.min.js") }" type="text/javascript" ></script>
</head>
<body style='width:600px; height:100%;text-align: center;margin:0 auto;'>
		<div class="dengrukuang_box"  style="margin-left:auto ; margin-right:auto">
			<form id="loginForm" action="${createLink(controller:'login',action:'doLogin') }" method="post">
				<div style="margin-top:40px;text-align:center;font-size:28px; font-weight:900">
						<g:message code="default.page.title.label" />
				</div>
				<div class="dengrukuang_right" >
					
					<div class="dengrukuang_right_top">
						<span style="width: 65px;text-align:right;"><g:message code="default.loginPage.userName.label"/>:</span> 
						<span >
							<input name="userName" id="userName" type="text" value='${flash.userName?flash.userName:'' }' />
						</span>
						<span style="width: 65px;text-align:right;"><g:message code="default.loginPage.password.label"/>:</span>
						<span >
							<input id="password" name="password" type="password" value='' />
						</span> 
						<span style="width: 65px;">
							<input style="width:25px;float:right; border:0px solid;" type="checkbox" id='check_login'/>
						</span>
						<span style="">
							记住我的登录信息
						</span>
					</div>
				
					<div class="dengrukuang_right_under">

						<span style="margin-top:12px;">
							<input name="" type="submit" value="<g:message code="default.loginPage.login.label"/>" class='btnlogin'>
						</span>

                        <span>
                            <input name="" id='btn_close' type="reset" value="<g:message code="default.loginPage.loginout.label"/>" class='btnexit' >
                        </span>

						<span style="color:red;font-weight:bold;font-size:12px;">
							<g:if test="${flash.message}">
								<div style="margin-top:10px;" class="message" role="status">${flash.message}</div>
							</g:if>
						</span>
						
					</div>
				</div>
			</form>
		</div>
	<script type="text/javascript">
		$("#userName").focus();
		$("#userName").keypress(function(key){
			if(key.keyCode ==13){
				$("#password").focus();
				return false;
			}
			$("#password").val("");
		});

		$(function(){

			//加载本地用户信息
			var loginName=getCookie('EWP_loginName');
			$("#userName").val(loginName);
			var password=getCookie('EWP_password');
			if(password!=null&&password!='n'){
				$("#password").val(password);
			}
			var checkFlag=getCookie('EWP_checkFlag');
			if(checkFlag==1){
				$("#check_login").attr("checked",true);
			}

			//页面提交时将用户信息保存
			$("#loginForm").submit(function(){
				var validTime=3*24;//cookies有效时间
				
				var loginName=$("#userName").val();
				addCookie('EWP_loginName',loginName,validTime);
				
				if($("#check_login").attr("checked")){
					var password=$("#password").val();
					addCookie("EWP_password",password,validTime);
					addCookie("EWP_checkFlag",'1',validTime)
				}else{
					delCookie("EWP_checkFlag");
					//delCookie('EWP_loginName');
					delCookie("EWP_password");
				}


                      return true;
			});
			
			$("#btn_close").click(function(){
                if(confirm("确定要退出本系统吗？")){
                    window.open("","_self");
                    window.close();
                }

			});
			
		});

		//添加本地cookies
		function addCookie(objName,objValue,objHours){//添加cookie
		    var str = objName + "=" + encodeURIComponent(objValue);
		    if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
			     var date = new Date();
			     var ms = objHours*3600*1000;
			     str += "; max-age=" + ms;
		    }
<%--		    str+="; path=/ewp; domain=/ewp";--%>
		    document.cookie = str;
		}

		//获取指定名称的cookie的值
		 function getCookie(objName){
			    var arrStr = document.cookie.split("; ");
			    for(var i = arrStr.length-1;i >=0 ;i --){
			     	var temp = arrStr[i].split("=");
			     	if(temp[0] == objName){
				      var result=decodeURIComponent(temp[1]);
				      return result;
			    	}
			    }
		}

		//删除cookie
		function delCookie(name){
		  document.cookie=name+"=n;max-age=0";
		} 	

		function delAllCookie(){
			var arrStr = document.cookie.split("; ");
		    for(var i = 0;i < arrStr.length;i ++){
		    	var temp = arrStr[i].split("=");
			    alert(temp[0]+"="+temp[1]);
		     	delCookie(temp[0]);
		    }
		}		
			  
	</script>
</body>
</html>
