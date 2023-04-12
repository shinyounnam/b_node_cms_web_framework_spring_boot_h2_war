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
 <body style="background-color:#004474;">
 
	 <div class="login_box">
        <img src="/images/login_01.png" alt="VETERAN" />
		<div class="pt30"></div>
		<img src="/images/login_02.png" alt="VETERAN ADMINISTRATOR" /><br />
		<div class="pt50"></div>
        <div style="width:100%;text-align:center;">
            <span style="font-size:15pt;color:black;">
            로그인할 수 있는 아이피가 아닙니다.
            </span>
        </div>
     </div>
</body>
</html>