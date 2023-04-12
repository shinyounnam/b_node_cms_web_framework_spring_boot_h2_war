<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");  	
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    int i = 0;
    JSONObject obj = null;
%>

 <!-- wrap -->
 <div id="pop_wrap">
	<div class="title">
		직업소개소 회원 정보 상세보기
		<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
	</div>
	<!-- contents -->
	<div class="pop_contents">
	<h3>기본정보</h3>
    <table class="write_A" summary="가입 신청 상세 기본정보">
		<colgroup>
			<col width="20%" />
			<col width="30%" />
			<col width="15%" />
			<col width="35%" />
		</colgroup>
		<tr>
			<th>회사명/아이디</th>
			<td >
				<%=obj0_0.getString("CORP_NAME")%>&nbsp;(<%=obj0_0.getString("ID")%>)
			</td>
			<th>등급</th>
			<td >
				<%
				String saleLevel1 = "";
				String saleLevel2 = "";
				String saleLevel3 = "";
				String saleLevel4 = "";
				if (obj0_0.getString("SALE_LEVEL").equals("C")) saleLevel1 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("B")) saleLevel2 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("A")) saleLevel3 = "selected";
				if (obj0_0.getString("SALE_LEVEL").equals("S")) saleLevel4 = "selected";
				%>
				<select id="SALE_LEVEL" name="SALE_LEVEL" style="width:100px">
					<option value="C" <%=saleLevel1%> >C 등급</option>
					<option value="B" <%=saleLevel2%> >B 등급</option>
					<option value="A" <%=saleLevel3%> >A 등급</option>
					<option value="S" <%=saleLevel4%> >S 등급</option>
				</select>				
				<br/>
				C등급 : 최초가입 <br/>
				B등급 : 월 10만원 이하 <br/>
				A등급 : 월 20만원 이하 + 2개월 유지<br/>
				S등급 : 월 20만원 이하 + 3개월 유지<br/>
			</td>
		</tr>
		<tr>
			<th>사업자번호 또는<br/>유료직업소개</br>사업신고번호</th>
			<td >
				<%=obj0_0.getString("BIZ_NO")%>
			</td>
			<th>쿠폰번호</th>
			<td>
				<% 
				if(obj0_0.getString("VIP_NO_YN").equals("O")) {
				%>
				쿠폰사용 : [<%=obj0_0.getString("VIP_NO")%>]
				<%
				} else {
				%>
				쿠폰미사용 
				<%
				}
				%>
			</td>
		</tr>
		<tr>
			<th>대표자</th>
			<td >
				<%=obj0_0.getString("REP_NAME")%>
			</td>
			<th>대표자 연락처</th>
			<td >
				<%=obj0_0.getString("REP_TEL")%>
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>
				<%=obj0_0.getString("ADDR1")%>&nbsp;<%=obj0_0.getString("ADDR2")%>
			</td>
			<th>구분</th>
			<td>
				<%=obj0_0.getString("CORP_GBN_NM")%>
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td colspan="3">
				<%=obj0_0.getString("TEL")%>
			</td>
		</tr>
		<tr>
			<th>홈페이지</th>
			<td >
				<%=obj0_0.getString("HOME_URL")%>
			</td>
			<th>이메일</th>
			<td >
				<%=obj0_0.getString("EMAIL")%>
			</td>
		</tr>
		<tr>
			<th>승인날짜(신청일)</th>
			<td >
				<%=obj0_0.getString("ACCP_DATE")%>(<%=obj0_0.getString("REQ_DATE")%>)
			</td>
			<th>정보수정일</th>
			<td >	
				<%=obj0_0.getString("UPD_DATE")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
	<h3>담당자 정보</h3>
	<table class="write_A" summary="가입 신청 상세 담당자 정보">
		<colgroup>
			<col width="15%" />
			<col width="20%" />
			<col width="15%" />
			<col width="15%" />
			<col width="15%" />
			<col width="20%" />
		</colgroup>
		<tr>
			<th>담당자</th>
			<td >
				<%=obj0_0.getString("MAN_NAME")%>
			</td>
			<th>직급</th>
			<td >
				<%=obj0_0.getString("MAN_PSTN_NM")%>
			</td>
			<th>담당자</br>연락처</th>
			<td >
				<%=obj0_0.getString("MAN_TEL")%>
			</td>
		</tr>
	</table>
	<div class="pt30"></div>
</div>
<script>
	function OnClose(){
		window.close();
	}
</script>
<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	