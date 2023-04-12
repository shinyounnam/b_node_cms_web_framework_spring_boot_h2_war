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
		
	</script>
</head>

<script type='text/javascript'>
	function OnLogin()
	{
		if ($("#id").val()=="")
		{
			alert("아이디를 입력해주세요.");
			return;
		}

		if ($("#pwd").val()=="")
		{
			alert("비밀번호를 입력해주세요.");
			return;
		}

		document.frmLogin.submit();
	}
</script>
</head>
 <body style="background-color:#004474;">
 <form name="frmLogin" id="frmLogin" method="post" action="/login">
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
	 </div>
</form>  
 </body>
</html>

