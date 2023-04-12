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
		
		<div style="width:100%;text-align:center;" id="div_imsi_pwd">
			<div style="width:100%;text-align:center;">
				<span style="color:white;font-size:15pt;">비밀번호 재발급</span>
			</div>
			<div class="pt10"></div>		
			<div style="width:100%;text-align:center;">
				<span style="color:white;">* 관리자정보에서 아이디, 이름, 이메일을 체크하여,<br/> 인증번호가 발송됩니다.</span>
				<div class="pt10"></div>
				<span style="color:white;">아이디</span>
				<input type="text" id="IMSI_ID" name="IMSI_ID" style="width:120px" placeholder="아이디"/>			
				<div class="pt10"></div>
				<span style="color:white;">이름</span>
				<input type="text" id="IMSI_NAME" name="IMSI_NAME" style="width:120px" placeholder="이름"/>			
				<div class="pt10"></div>
				<span style="color:white;">이메일</span>
				<input type="text" id="IMSI_EMAIL1" name="IMSI_EMAIL1" style="width:120px" placeholder=""/>
				<span style="color:white;">@</span>
				<input type="text" id="IMSI_EMAIL2" name="IMSI_EMAIL2" style="width:120px" placeholder=""/>
				<br/>
				<input type="hidden" id="IMSI_AUTH_YN" name="IMSI_AUTH_YN" value="N"/>		
				<input type="hidden" name="H_IMSI_ID" id="H_IMSI_ID" value="">
				<input type="hidden" name="H_IMSI_NAME" id="H_IMSI_NAME" value="">
				<input type="hidden" name="H_IMSI_EMAIL" id="H_IMSI_EMAIL" value="">
				<input type="hidden" name="H_IMSI_HP_TEL" id="H_IMSI_HP_TEL" value="">
				<input type="hidden" id="IMSI_CONFIRM_AUTH_YN" name="IMSI_CONFIRM_AUTH_YN" value="N"/>
				<div class="pt10"></div>
				<a href="javascript:OnImsiSelectAuthNo();" class="btn02">인증번호 발송</a>
				<div class="pt10"></div>				
			</div>
			<div style="width:100%;text-align:center;">
				<span id="span_imsi_sms_select" style="color:white;"></span>
			</div>
			<div style="width:100%;text-align:center;">
				<span style="color:white;">인증번호</span>
				<input type="text" id="IMSI_CHECK_SMS_VALUE" name="IMSI_CHECK_SMS_VALUE" style="width:80px" maxlength="4" placeholder="인증번호"/>			
				<span style="color:white;" id="span_imsi_timer"></span>
				<div class="pt10"></div>
				<a href="javascript:OnImsiConfirmAuthNo();" class="btn02">인증번호 확인</a>
				<div class="pt10"></div>
				<span style="color:white;">* 이메일, 휴대폰번호로 수신된 인증번호를 입력하면, <br/>인증번호 확인 후 비밀번호가 재발급됩니다.</span>
			</div>
		</div>
        <div class="pt10"></div>
        <a href="javascript:OnLoginUrl();" class="btn02">로그인 화면으로 이동</a>
		<div class="pt30"></div>
        
	 </div>

 </body>
</html>

<script type='text/javascript'>
    function OnLoginUrl(){
        location.href = "/";
    }
	function OnImsiPwd(){
		if($("#div_imsi_pwd").css("display")=="none"){
			$("#div_imsi_pwd").css("display","block");
		}
		else{
			$("#div_imsi_pwd").css("display","none");
		}
	}

	var imsi_timer = null;

	function ImsiCountDownTimer() {
		var _remain_second = 180;     
		
		function showRemainingImsi() {
			_remain_second = _remain_second - 1;
			if (_remain_second < 0) {
				clearInterval(imsi_timer);
				jQuery("#span_imsi_timer").html('');
				return;
			}
			var _second = (_remain_second % 60);
			var _minute = Math.floor(_remain_second / 60);
			var timer_text = "";
			timer_text += _minute + '분 ';
			timer_text += _second + '초';
				
			jQuery("#span_imsi_timer").html(timer_text);
		}
		_remain_second = 180;
		if (imsi_timer!=null)
		{
			clearInterval(imsi_timer);
			imsi_timer = setInterval(showRemainingImsi, 1000);
		}
		else
		{
			imsi_timer = setInterval(showRemainingImsi, 1000);
		}
		
	}

	function ClearImsiCountDownTimer(){
		var timer_text = "";
		if (imsi_timer!=null)
		{		 
			clearInterval(imsi_timer);
			jQuery("#span_imsi_timer").html(timer_text);
		}
		else{
			jQuery("#span_imsi_timer").html(timer_text);
		}
	}

	function OnImsiConfirmAuthNo(){
		if ($("#IMSI_AUTH_YN").val()=="N"){
			alert("인증번호 발송을 먼저 실행해주세요.");			
			return;
		}
		if ($("#IMSI_CHECK_SMS_VALUE").val()==""){
			alert("인증번호를 입력해주세요.");
			$("#IMSI_CHECK_SMS_VALUE").focus();
			return;
		}
		
		
		
		var id = $("#H_IMSI_ID").val();
		var name = $("#H_IMSI_NAME").val();		
		var email = $("#H_IMSI_EMAIL").val();
		var check_sms_value = $("#IMSI_CHECK_SMS_VALUE").val();
		var data = {};
		data.ID = id;
		data.NAME = name;
		data.EMAIL = email;
		data.CHECK_SMS_VALUE = check_sms_value;

		$.ajax({
			url:"/pwd_sms_select",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnImsiConfirmAuthNo(result,id,name,email);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnImsiConfirmAuthNo(data,id,name,email){
		var jsonData = JSON.parse(data);
		
		if (jsonData.result =="ok")
		{			
			$("#IMSI_CONFIRM_AUTH_YN").val("Y");
			var obj1_0 = jsonData.data[0][0];
			var HP_TEL = obj1_0.HP_TEL;
			var EMAIL = obj1_0.EMAIL;							
			ClearImsiCountDownTimer();				
			
			if(HP_TEL!=""){
				$("#span_imsi_sms_select").html("해당 사용자의 휴대폰 번호 "+HP_TEL+" 이메일 "+EMAIL+" 로 비밀번호가 발급되었습니다.");
			}
			else{
				$("#span_imsi_sms_select").html("해당 사용자의 이메일 "+EMAIL+" 로 비밀번호가 발급되었습니다.");
			}
			
		}	
		else
		{
			var ERROR_CODE = jsonData.ERROR_CODE;			
            if (ERROR_CODE == "6"){                
				var message = "인증할 수 있는 아이피가 아닙니다.";				
				$("#span_imsi_sms_select").html(message);
                //alert(message);
            }
			else if (ERROR_CODE == "4"){
                
				var message = "회원정보가 없습니다.";	
				//$("#span_imsi_sms_select").html(message);			
                alert(message);
            }			
			else if (ERROR_CODE == "2"){
                
				var message = "인증번호가 틀립니다.";				
				//$("#span_imsi_sms_select").html(message);
                alert(message);
            }			
			else if (ERROR_CODE == "3"){
                
				var message = "인증번호를 생성한지 3분이 지났습니다. 다시 인증해주세요.";				
				//$("#span_imsi_sms_select").html(message);
                alert(message);
            }	
            else{
                alert(jsonData.message);
				//$("#span_imsi_sms_select").html(jsonData.message);
            }
		}
	}

	function OnImsiSelectAuthNo()
	{
		if ($("#IMSI_ID").val()==""){
			alert("아이디를 입력해주세요.");
			$("#IMSI_ID").focus();
			return;
		}
		if ($("#IMSI_NAME").val()==""){
			alert("이름을 입력해주세요.");
			$("#IMSI_NAME").focus();
			return;
		}
		if ($("#IMSI_EMAIL1").val()==""){
			alert("이메일을 입력해주세요.");
			$("#IMSI_EMAIL1").focus();
			return;
		}
		if ($("#IMSI_EMAIL2").val()==""){
			alert("이메일을 입력해주세요.");
			$("#IMSI_EMAIL2").focus();
			return;
		}
		
		var email = $("#IMSI_EMAIL1").val() + "@"+$("#IMSI_EMAIL2").val();
		if (ValidateEmailCheck(email)==false){
			alert("이메일이 유효하지 않습니다. 다시 입력해주세요.");
			$("#IMSI_EMAIL1").focus();
			return;
		}
		var id = $("#IMSI_ID").val();
		var name = $("#IMSI_NAME").val();		
		var data = {};
		data.ID = id;
		data.NAME = name;
		data.EMAIL = email;

		$.ajax({
			url:"/pwd_sms_insert",
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json",
			success:function(result) {
				Callback_OnImsiSelectAuthNo(result,id,name,email);
			},
			error:function(err) {
				alert(err.responseText);
			}
		});
	}

	function Callback_OnImsiSelectAuthNo(data,id,name,email){
		var jsonData = JSON.parse(data);
		
		if (jsonData.result =="ok")
		{
			$("#H_IMSI_ID").val(id);
			$("#H_IMSI_NAME").val(name);
			$("#H_IMSI_EMAIL").val(email);
			$("#IMSI_AUTH_YN").val("Y");
			var obj1_0 = jsonData.data[0][0];
			var HP_TEL = obj1_0.HP_TEL;
			var EMAIL = obj1_0.EMAIL;
			$("#H_IMSI_HP_TEL").val(HP_TEL);				
			ImsiCountDownTimer();				
			
			if(HP_TEL!=""){
				$("#span_imsi_sms_select").html("해당 사용자의 휴대폰 번호 "+HP_TEL+" 이메일 "+EMAIL+" 로 인증번호가 발송되었습니다.");
			}
			else{
				$("#span_imsi_sms_select").html("해당 사용자의 이메일 "+EMAIL+" 로 인증번호가 발송되었습니다.");
			}
			
		}	
		else
		{
			var ERROR_CODE = jsonData.ERROR_CODE;			
            if (ERROR_CODE == "6"){                
				var message = "인증할 수 있는 아이피가 아닙니다.";				
				//$("#span_imsi_sms_select").html(message);
                alert(message);
            }
			else if (ERROR_CODE == "2"){
                
				var message = "회원정보가 없습니다.";				
				// $("#span_imsi_sms_select").html(message);
                alert(message);
            }			
            else{
				var message = jsonData.message;
				//$("#span_imsi_sms_select").html(message);
                alert(jsonData.message);
            }
		}
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