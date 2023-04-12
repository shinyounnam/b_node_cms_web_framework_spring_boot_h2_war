<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<%
JSONObject jsonHeader = (JSONObject)request.getAttribute("header");   
String TOP_MENU = jsonHeader.getString("TOP_MENU");
String LEFT_MENU = jsonHeader.getString("LEFT_MENU");
String AUTH = jsonHeader.getString("AUTH");

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
  <title>가입신청현황</title>
  <script type="text/javascript" src="/javascripts/scout.js"></script>
  <script type="text/javascript" src="/javascripts/jquery.js"></script>
  <script type="text/javascript" src="/javascripts/security.js"></script>
  <script type="text/javascript" src="/stylesheets/jquery-ui.js"></script>
	<%
	if (G_USER_AGENT.indexOf("SAMSUNG") != -1 && Integer.parseInt(G_SAMSUNG_BROWSER_VERSION) < 15){
		%>
		<!-- daum map-->
		  <script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=7e5fc32831a7eeb8c7e7e582b0741eca&libraries=services,clusterer,drawing"></script>
		  <script src="http://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
		<%
	}
	else{
		%>
		<!-- daum map-->
		  <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=7e5fc32831a7eeb8c7e7e582b0741eca&libraries=services,clusterer,drawing"></script>
		  <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
		<%
	}
	%>
  	
  <link rel="stylesheet" href="/stylesheets/default.css" />
  <link rel="stylesheet" href="/stylesheets/jquery-ui.css" />
  
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
 <body style="background-color:#f0f0f0;">
 <!-- wrap -->
 <div id="wrap">
	<div class="lnb">
		<h1><a href="/"><img src="/images/lnb_01.png" alt="veteran administrator" border="0" /></a></h1>
		<ul>
			<li>
				<%
				String strClass1 = "";
				String strClass2 = "";
				String strClass3 = "";
				String strClass4 = "";
				String strClass5 = "";
				String strClass6 = "";
				String strClass7 = "";
				String strClass8 = "";
				%>
				<% if (TOP_MENU.equals("0")) { %>
					<a href="/admin_mng">관리자 관리</a>
					<ul>
						<%
						
						if (LEFT_MENU.equals("1")) strClass1 = "on";
						if (LEFT_MENU.equals("2")) strClass2 = "on";
						if (LEFT_MENU.equals("3")) strClass3 = "on";
						if (LEFT_MENU.equals("4")) strClass4 = "on";
						%>
						<li class="<%=strClass1%>"><a href="/admin_mng">- 관리자 관리</a></li>
						<li class="<%=strClass2%>"><a href="/admin_log">- 관리자 접속 로그</a></li>
						<li class="<%=strClass3%>"><a href="/admin_use_log">- 관리자 위치정보 이용자료</a></li>
						<li class="<%=strClass4%>"><a href="/admin_req_log">- 관리자 위치정보 요청자료</a></li>
					</ul>
				<% } %>
				<% if (TOP_MENU.equals("1")) { 
				if (LEFT_MENU.equals("2")) strClass2 = "on";
				if (LEFT_MENU.equals("3")) strClass3 = "on";
				if (LEFT_MENU.equals("4")) strClass4 = "on";
				%>
					<a href="/attend_req">가입 신청</a>
					<ul>
						<li class="on">
							<a href="/attend_req" >- 가입 신청 현황</a>
							<ul>
								<li class="<%=strClass2%>"><a href="/attend_req1">접수 중</a></li>
								<li class="<%=strClass3%>"><a href="/attend_req2">승인 완료</a></li>
								<li class="<%=strClass4%>"><a href="/attend_req3">보류/반려</a></li>
							</ul>
							<a href="/vip_no_list" >- 쿠폰관리</a>
						</li>
					</ul>
				<% } %>
				<% if (TOP_MENU.equals("2")) { 
					if (LEFT_MENU.equals("1")) strClass1 = "on";
					if (LEFT_MENU.equals("2")) strClass2 = "on";
					if (LEFT_MENU.equals("3")) strClass3 = "on";
					if (LEFT_MENU.equals("4")) strClass4 = "on";
					if (LEFT_MENU.equals("5")) strClass5 = "on";
				%>
					<a href="/user_mng">회원 관리</a>
					<ul>
						<li class="<%=strClass1%>"><a href="/user_mng">- 직업소개소 회원</a></li>
						<li class="<%=strClass2%>"><a href="/emp_user_mng">- 근로자 회원</a></li>
						<li class="<%=strClass3%>"><a href="/user_search_mng">- 써치 회원</a></li>
						<li class="<%=strClass4%>"><a href="/user_restore">- 휴면 신청 관리</a></li>
						<li class="<%=strClass5%>"><a href="/vendor_restore_month">- 휴면예정 리스트</a></li>
					</ul>
				<% } %>
				<% if (TOP_MENU.equals("3")) { 
					if (LEFT_MENU.equals("1")) strClass1 = "on";
					if (LEFT_MENU.equals("2")) strClass2 = "on";
				%>
					<a href="/work_mng">현장 관리</a>
					<ul>
						<li class="<%=strClass1%>"><a href="/work_mng">- 현장 현황</a></li>
						<li class="<%=strClass2%>"><a href="/work_search_mng">- 써치 현장</a></li>
					</ul>
				<% } %>
				
				<% if (TOP_MENU.equals("5")) { 
					if (LEFT_MENU.equals("1")) strClass1 = "on";
					if (LEFT_MENU.equals("2")) strClass2 = "on";					
				%>
					<a href="/item_mng">아이템 관리</a>
					<ul>
						<li class="<%=strClass1%>"><a href="/item_sale_mng">- 아이템 판매 내역</a></li>
						<li class="<%=strClass2%>"><a href="/item_use_mng">- 아이템 사용 내역</a></li>
					</ul>
				<% } %>
				<% if (TOP_MENU.equals("6")) { 
						if (LEFT_MENU.equals("1")) strClass1 = "on";
						if (LEFT_MENU.equals("2")) strClass2 = "on";
						if (LEFT_MENU.equals("3")) strClass3 = "on";
						if (LEFT_MENU.equals("4")) strClass4 = "on";
						if (LEFT_MENU.equals("5")) strClass5 = "on";
				%>
					<a href="/admin_cms_board">게시판 관리</a>
					<ul>
						<%
						%>
						<li class="<%=strClass1%>"><a href="/admin_cms_board">- 이벤트/공지사항</a></li>
						<li class="<%=strClass2%>"><a href="/admin_cms_faq">- 자주 묻는 질문</a></li>
						<li class="<%=strClass3%>"><a href="/admin_cms_online_res">- 온라인 문의 답변</a></li>
						<li class="<%=strClass4%>"><a href="/admin_cms_question">- 문의 및 요청</a></li>
						<li class="<%=strClass5%>"><a href="/admin_cms_event_s_board">- 슬로건 공모전</a></li>
					</ul>
				<% } %>
				<% if (TOP_MENU.equals("7")) { 
				
					if (LEFT_MENU.equals("1")) strClass1 = "on";
					if (LEFT_MENU.equals("2")) strClass2 = "on";
					if (LEFT_MENU.equals("3")) strClass3 = "on";
					if (LEFT_MENU.equals("4")) strClass4 = "on";
					if (LEFT_MENU.equals("5")) strClass5 = "on";
					if (LEFT_MENU.equals("6")) strClass6 = "on";
					if (LEFT_MENU.equals("7")) strClass7 = "on";
					if (LEFT_MENU.equals("8")) strClass8 = "on";
				%>
					<a href="/statistics">통계</a>
					<ul>
						<li class="<%=strClass1%>"><a href="/statistics">- 기간별 통계</a></li>
					</ul>
					<ul>
						<li class="<%=strClass2%>"><a href="/statistics_user">- 근로자 통계</a></li>
					</ul>
					<ul>
						<li class="<%=strClass3%>"><a href="/statistics_user_login">- 로그인 이벤트 통계</a></li>
					</ul>
					<ul>
						<li class="<%=strClass4%>"><a href="/statistics_event">- 이벤트 참여 통계</a></li>
					</ul>
					<ul>
						<li class="<%=strClass8%>"><a href="/statistics_v_app_update">- 이벤트 앱 (직업소개소)</a></li>
					</ul>
					<ul>
						<li class="<%=strClass5%>"><a href="/statistics_app_update">- 이벤트 앱 (근로자)</a></li>
					</ul>
					<ul>
						<li class="<%=strClass6%>"><a href="/statistics_sale">- 매출 통계</a></li>
					</ul>
					<ul>
						<li class="<%=strClass7%>"><a href="/statistics_user_addr">- 근로자 주소 통계</a></li>
					</ul>
					
				<% } %>				 
			</li>
		</ul>
	</div>
	<!-- container -->
	<div class="container">
		<div>
			<ul class="utile">
				<li class="logout"><a href="/logout">로그아웃</a></li>
				<li class="setting"><a href="/admin_mng">관리자 관리</a></li>
			</ul>
		</div>
		<div class="pt20"></div>
		<div class="gnb">
			<ul>
				<%
				String strTopClass1 = "";
				String strTopClass2 = "";
				String strTopClass3 = "";
				String strTopClass4 = "";
				String strTopClass5 = "";
				String strTopClass6 = "";
				String strTopClass7 = "";
				%>
				<% 
				if (TOP_MENU.equals("1")) strTopClass1 = "on";
				if (TOP_MENU.equals("2")) strTopClass2 = "on"; 
				if (TOP_MENU.equals("3")) strTopClass3 = "on"; 
				if (TOP_MENU.equals("4")) strTopClass4 = "on"; 
				if (TOP_MENU.equals("5")) strTopClass5 = "on"; 
				if (TOP_MENU.equals("6")) strTopClass6 = "on"; 
				if (TOP_MENU.equals("7")) strTopClass7 = "on";		

				if(AUTH.indexOf("1")!=-1){
					%>
					<li class="<%=strTopClass1%>"><a href="/attend_req">가입 신청</a></li>
					<%
				}
				if(AUTH.indexOf("2")!=-1){
					%>
					<li class="<%=strTopClass2%>"><a href="/user_mng">회원 관리</a></li>
					<%
				}
				if(AUTH.indexOf("3")!=-1){
					%>
					<li class="<%=strTopClass3%>"><a href="/work_mng">현장 관리</a></li>
					<%
				}
				if(AUTH.indexOf("4")!=-1){
					%>
					<li class="<%=strTopClass4%>"><a href="/alarm_mng">알림 관리</a></li>	
					<%
				}
				if(AUTH.indexOf("6")!=-1){
					%>
					<li class="<%=strTopClass6%>"><a href="/admin_cms_board">게시판 관리</a></li>
					<%
				}
				if(AUTH.indexOf("5")!=-1){
					%>
					<li class="<%=strTopClass5%>"><a href="/item_sale_mng">아이템 관리</a></li>
					<%
				}
				if(AUTH.indexOf("7")!=-1){
					%>
					<li class="<%=strTopClass7%>"><a href="/statistics">통계</a></li>
					<%
				}
				%>
			</ul>
		</div>