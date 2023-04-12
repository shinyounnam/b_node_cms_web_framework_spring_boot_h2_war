<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
%>
<script type="text/javascript">
	function OnClose()
	{
		self.close();
	}
	
</script>

<div class="title">
	보낸 메시지 내역 상세 보기
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>
<!-- contents -->
<div class="pop_contents">
	<h3 class="floatleft">보낸 메시지 내역 상세 보기</h3>
	<table class="write_B" summary="아이템 부여하기">		
	
		<colgroup>
			<col width="25%" />
			<col width="*%" />
		</colgroup>
		<tr>
			<th>보낸 시간</th>
			<td><%=obj0_0.getString("SEND_DATE")%></td>
		</tr>
		<tr>
			<th>보낸 사람</th>
			<td><%=obj0_0.getString("CORP_NAME")%></td>
		</tr>
		<tr>
			<th>받는 사람</th>
			<td><%=obj0_0.getString("RECV_NAMES")%></td>
		</tr>
		<tr>
			<th>확인 여부</th>
			<td>전체 전송 : <%=obj0_0.getInt("TOTAL_CNT")%>   확인 : <%=obj0_0.getInt("READ_CNT")%></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><%=obj0_0.getString("CONTENT")%></td>
		</tr>
	</table>
	<div class="pt30"></div>
		<div class="alignright">
		<a class="btn03" href="javascript:OnClose();">닫기</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	