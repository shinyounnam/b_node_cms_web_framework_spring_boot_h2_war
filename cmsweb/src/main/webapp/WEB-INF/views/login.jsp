<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
JSONObject jsonHeader = (JSONObject)request.getAttribute("header");   

String G_USER_AGENT = jsonHeader.getString("G_USER_AGENT");
String G_SAMSUNG_BROWSER_VERSION = jsonHeader.getString("G_SAMSUNG_BROWSER_VERSION");
%>
<!doctype html>
<html lang="en">
 <head>
  <meta http-equiv="content-type" content="text/html; charset-utf-8">
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>로그인</title>
<link rel="stylesheet" href="/stylesheets/default.css" />
<%
if (G_USER_AGENT.indexOf("SAMSUNG") != -1 && Integer.parseInt(G_SAMSUNG_BROWSER_VERSION) < 15) {
	%>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js"></script>
	<%
}
else{
	%>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js"></script>
	<%
}

%>



<script type="text/javascript">
		
		var samsung_browser_version = "<%=G_SAMSUNG_BROWSER_VERSION%>";
		var userAgent=navigator.userAgent.toLowerCase();
		if (location.hostname === "localhost"){

		}
		else{
			if (userAgent.indexOf("samsung") != -1 && parseInt(samsung_browser_version)){
				
				if (document.location.protocol == 'https:') {
					document.location.href = document.location.href.replace('https:', 'http:');
				}		
			}
			else
			{
				if (document.location.protocol == 'http:') {
					document.location.href = document.location.href.replace('http:', 'https:');
				}
			}					
		}
		
	</script>

</head>
 <body style="background-color:#004474;">
 
	 <div class="login_box">
		<img src="/images/login_01.png" alt="VETERAN" />
		<div class="pt30"></div>
		<img src="/images/login_02.png" alt="VETERAN ADMINISTRATOR" /><br />
		<div class="pt50"></div>
		<input type="text" id="ID" name="ID" value="" class="logininput" maxlength="20">
		<div class="pt10"></div>
		<input type="password" id="PWD" name="PWD" value="" class="logininput" maxlength="20">
		<div class="pt30"></div>
		<a href="javascript:OnLogin();"><img src="/images/login_03.png" alt="" border="0" /></a>		
		<input type="hidden" name="LOGIN_RESULT" id="LOGIN_RESULT" value="">
		<input type="hidden" name="LOGIN_ERROR_CODE" id="LOGIN_ERROR_CODE" value="">
		<input type="hidden" id="AUTH_YN" name="AUTH_YN" value="N"/>
		<input type="hidden" name="H_HP_TEL" id="H_HP_TEL" value="">
		<input type="hidden" name="H_ID" id="H_ID" value="">
		<input type="hidden" name="H_PWD" id="H_PWD" value="">
		<input type="hidden" name="H_HP_TEL" id="H_HP_TEL" value="">
		<input type="hidden" id="CONFIRM_AUTH_YN" name="CONFIRM_AUTH_YN" value="N"/>
		<div class="pt10"></div>		
		<div style="width:100%;text-align:center;">
			<span id="span_sms_select" style="color:white;"></span>
		</div>
		<div class="pt10"></div>
		<div style="width:100%;text-align:center;">
			<span style="color:white;">인증번호</span>
			<input type="text" id="CHECK_SMS_VALUE" name="CHECK_SMS_VALUE" style="width:80px" maxlength="4" placeholder="인증번호"/>			
			<span style="color:white;" id="span_timer"></span>
			<div class="pt10"></div>
			<a href="javascript:OnConfirmAuthNo();" class="btn02">인증번호 확인</a>
			<div class="pt10"></div>
			<span style="color:white;">* 관리자정보에서 휴대폰번호가 있을 경우, <br/>인증번호 확인 후 로그인됩니다.</span>
			<div class="pt10"></div>
			<a href="javascript:OnImsiPwd();" class="btn02">비밀번호 재발급</a>
		</div>		
		<div class="pt30"></div>
	 </div>

 </body>
</html>

<script type='text/javascript'>

	function OnImsiPwd(){
		location.href = "/imsi_pwd";
	}

	
	function ValidateEmailCheck(strEmail) 
	{
		var regExp = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
		if (regExp.test(strEmail))
		{
			return true;
		}
		return false;
	}

	function OnLogin()
	{
		if ($("#ID").val()==""){
			alert("아이디를 입력해주세요.");
			$("#ID").focus();
			return;
		}
		if ($("#PWD").val()==""){
			alert("비밀번호를 입력해주세요.");
			$("#PWD").focus();
			return;
		}
		
		var id = $("#ID").val();
		var pwd = $("#PWD").val();
		var data = {};
		data.ID = id;
		data.PWD = pwd;

		$.ajax({
			url:"/login_check",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnLogin(result,id,pwd);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnLogin(data,id,pwd){
		var jsonData = JSON.parse(data);
		
		if (jsonData.result =="ok")
		{
			$("#H_ID").val(id);
			$("#H_PWD").val(pwd);
			var obj1_0 = jsonData.data[1][0];
			var HP_TEL = obj1_0.HP_TEL;
			var EMAIL = obj1_0.EMAIL;
			$("#LOGIN_RESULT").val(jsonData.result);
			$("#LOGIN_ERROR_CODE").val(jsonData.ERROR_CODE);
			if (HP_TEL == ""){											
				if($("#LOGIN_ERROR_CODE").val()=="login_ok_change_pwd"){
					var url = "/admin_mng_pwd_change/info";
					location.href = url;
				}
				else if($("#LOGIN_ERROR_CODE").val()=="login_ok"){
					var obj0_0 = jsonData.data[1][0];
					var AUTH = obj0_0.AUTH;
					if(AUTH ==""){
						alert("권한이 없습니다.");
						var url = "/logout";
						location.href = url;
					}
					else if (AUTH.indexOf("1")!=-1){
						var url = "/attend_req";
						location.href = url;
					}
					else if (AUTH.indexOf("2")!=-1){
						var url = "/user_mng";
						location.href = url;
					}
					else if (AUTH.indexOf("3")!=-1){
						var url = "/work_mng";
						location.href = url;
					}
					else if (AUTH.indexOf("4")!=-1){
						var url = "/alarm_mng";
						location.href = url;
					}
					else if (AUTH.indexOf("6")!=-1){
						var url = "/admin_cms_board";
						location.href = url;
					}
					else if (AUTH.indexOf("5")!=-1){
						var url = "/item_sale_mng";
						location.href = url;
					}
					else if (AUTH.indexOf("7")!=-1){
						var url = "/statistics";
						location.href = url;
					}
				}

			}
			else if(HP_TEL != "")
			{
				CountDownTimer();				
				$("#H_HP_TEL").val(HP_TEL);				
				if(EMAIL!=""){
					$("#span_sms_select").html("해당 사용자의 휴대폰 번호 "+HP_TEL+" 이메일 "+EMAIL+" 로 인증번호가 발송되었습니다.");
				}
				else{
					$("#span_sms_select").html("해당 사용자의 휴대폰 번호 "+HP_TEL+" 로 인증번호가 발송되었습니다.");
				}
			}
			
		}	
		else
		{
			var ERROR_CODE = jsonData.ERROR_CODE;
			$("#LOGIN_RESULT").val(jsonData.result);
			$("#LOGIN_ERROR_CODE").val(jsonData.ERROR_CODE);
            if (ERROR_CODE == "login_access_deny_ip"){                
				var message = "로그인할 수 있는 아이피가 아닙니다.";				
                alert(message);
            }
			else if (ERROR_CODE == "login_access_deny"){
                
				var message = "접근제한되었습니다.";				
                alert(message);
            }
			else if (ERROR_CODE == "login_fail"){
				var obj0_0 = jsonData.data[0][0];
				var LOGIN_FAIL_COUNT = obj0_0.LOGIN_FAIL_COUNT;
				var message = "";
				message += "비밀번호가일치하지 않습니다.연속 3회이상 불일치시 접근이 제한됩니다.";
				message += "(비밀번호불일치: "+LOGIN_FAIL_COUNT+"회)";
                alert(message);
            }
            else{
                alert(jsonData.message);
            }
		}
	}

	var timer = null;

	function CountDownTimer() {
		var _remain_second = 180;     
		
		function showRemaining() {
			_remain_second = _remain_second - 1;
			if (_remain_second < 0) {
				clearInterval(timer);
				jQuery("#span_timer").html('');
				return;
			}
			var _second = (_remain_second % 60);
			var _minute = Math.floor(_remain_second / 60);
			var timer_text = "";
			timer_text += _minute + '분 ';
			timer_text += _second + '초';
				
			jQuery("#span_timer").html(timer_text);
		}
		_remain_second = 180;
		if (timer!=null)
		{
			clearInterval(timer);
			timer = setInterval(showRemaining, 1000);
		}
		else
		{
			timer = setInterval(showRemaining, 1000);
		}
		
	}

	function ClearCountDownTimer(){
		var timer_text = "";
		if (timer!=null)
		{		 
			clearInterval(timer);
			jQuery("#span_timer").html(timer_text);
		}
		else{
			jQuery("#span_timer").html(timer_text);
		}
	}

	

	function OnConfirmAuthNo()
	{
		if ($("#LOGIN_RESULT").val()!="ok"){
			alert("로그인을 먼저 실행 해주세요.");
			return;
		}
		
		if ($("#CHECK_SMS_VALUE").val()==""){
			alert("인증번호를 입력해주세요.");
			return;
		}
		var id = $("#H_ID").val();
		var pwd = $("#H_PWD").val();
		// var hp_tel = $("#H_HP_TEL").val();
		var check_sms_value = $("#CHECK_SMS_VALUE").val();

		var data = {};
		data.ID = id;
		data.PWD = pwd;
		// data.HP_TEL = hp_tel;
		data.CHECK_SMS_VALUE = check_sms_value;

		$.ajax({
			url:"/login_select_sms",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnConfirmAuthNo(result);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnConfirmAuthNo(data){
		var jsonData = JSON.parse(data);
		if (jsonData.result =="ok")
		{
			var message = "인증되었습니다.";				
			//alert(message);
			$("#span_sms_select").html(message);
			ClearCountDownTimer();

			if($("#LOGIN_ERROR_CODE").val()=="login_ok_change_pwd"){
				var url = "/admin_mng_pwd_change/info";
				location.href = url;
			}
			else if($("#LOGIN_ERROR_CODE").val()=="login_ok"){
				var obj0_0 = jsonData.data[1][0];
				var AUTH = obj0_0.AUTH;
				if(AUTH ==""){
					alert("권한이 없습니다.");
					var url = "/logout";
					location.href = url;
				}
				else if (AUTH.indexOf("1")!=-1){
					var url = "/attend_req";
					location.href = url;
				}
				else if (AUTH.indexOf("2")!=-1){
					var url = "/user_mng";
					location.href = url;
				}
				else if (AUTH.indexOf("3")!=-1){
					var url = "/work_mng";
					location.href = url;
				}
				else if (AUTH.indexOf("4")!=-1){
					var url = "/alarm_mng";
					location.href = url;
				}
				else if (AUTH.indexOf("6")!=-1){
					var url = "/admin_cms_board";
					location.href = url;
				}
				else if (AUTH.indexOf("5")!=-1){
					var url = "/item_sale_mng";
					location.href = url;
				}
				else if (AUTH.indexOf("7")!=-1){
					var url = "/statistics";
					location.href = url;
				}
			}
		}	
		else
		{
			var ERROR_CODE = jsonData.ERROR_CODE;
            if (ERROR_CODE == "2"){                
				var message = "인증번호가 틀립니다.";				
                //alert(message);
				$("#span_sms_select").html(message);
            }
			else if (ERROR_CODE == "3"){
                
				var message = "인증번호를 생성한지 3분이 지났습니다. 다시 인증해주세요.";				
                //alert(message);
				$("#span_sms_select").html(message);
            }			
			else if (ERROR_CODE == "4"){
                
				var message = "아이디  정보가 없습니다.";				
                //alert(message);
				$("#span_sms_select").html(message);
            }			
			else if (ERROR_CODE == "5"){                
				var message = "비밀번호가 틀립니다.";				
                //alert(message);
				$("#span_sms_select").html(message);
            }			
			else if (ERROR_CODE == "6"){                
				var message = "인증할 수 있는 아이피가 아닙니다.";				
                //alert(message);
				$("#span_sms_select").html(message);
            }	
            else{
                //alert(jsonData.message);
				$("#span_sms_select").html(message);
            }
		}
	}

	function ValidateHpTel(tel_num){
		var patternPhone = /01[0|1|6|7|8|9]-[0-9]{3,4}-[0-9]{3,4}/;

		//둘중에 하나골라 쓰면 된다.
		if(patternPhone.test(tel_num))
		{
			return true;
		}  
		return false;
	}

</script>