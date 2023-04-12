<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    int i = 0;
    JSONObject obj = null;
%>
<div class="title">
	근로자 주소 통계 리포트
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>

<!-- contents -->
<!-- contents -->
<div class="pop_contents">
	
	<div class="pt30"></div>	
	<table class="list_B" summary="가입 신청 현황">
		<colgroup>
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
		</colgroup>				
		<tbody>
			<tr>
				<th>시/도</th>
				<th>가입수</th>
				<th>시/도</th>
				<th>가입수</th>
				<th>시/도</th>
				<th>가입수</th>
			</tr>
			<%
			i = 0;
			int nCount = 0;
            JSONArray data0 = data.getJSONArray(0);
			if (data0.length() > 0) {
				for (i =0; i < data0.length(); i++) {	
                    obj = data0.getJSONObject(i);
				%>
				<tr>					
					<%
					if (i < data0.length()) {												
					%>
					<td><%=obj.getString("AREA_NAME_1")%></td>
					<td><%=obj.getInt("COUNT_1")%></td>			
					<%
					}
					%>
					<%
					if ((i + 1) < data0.length()) {
						i = i + 1;
					%>
					<td><%=obj.getString("AREA_NAME_1")%></td>
					<td><%=obj.getInt("COUNT_1")%></td>			
					<%
					}
					%>
					<%
					if ((i + 1) < data0.length()) {
						i = i + 1;
					%>					
					<td><%=obj.getString("AREA_NAME_1")%></td>
					<td><%=obj.getInt("COUNT_1")%></td>			
					<%
					}
					%>
				</tr>
				<%
				}
			}
			%>
		</tbody>
		
	</table>

	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnPrint();">리포트 출력</a>
		<a class="btn04" href="javascript:OnClose();">닫기</a>
	</div>
</div>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	