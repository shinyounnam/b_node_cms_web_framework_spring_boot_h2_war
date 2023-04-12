<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header_pop.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String DATE = header.getString("DATE");

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
    String STR_YEAR = obj1_0.getString("STR_YEAR");
    

    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);
    JSONObject obj0_1 = data.getJSONArray(0).getJSONObject(1);
    JSONObject obj0_2 = data.getJSONArray(0).getJSONObject(2);
    JSONObject obj0_3 = data.getJSONArray(0).getJSONObject(3);
    JSONObject obj0_4 = data.getJSONArray(0).getJSONObject(4);
    JSONObject obj0_5 = data.getJSONArray(0).getJSONObject(5);
    JSONObject obj0_6 = data.getJSONArray(0).getJSONObject(6);
    JSONObject obj0_7 = data.getJSONArray(0).getJSONObject(7);

%>
<!-- contents -->
<!-- contents -->

<div class="title">
	근로자 리포트
	<a href="javascript:OnClose();"><img src="/images/popup_close.gif" alt="" /></a>
</div>

<div class="pop_contents">
	
	<div class="pt30"></div>	
	<table class="list_B" summary="통계 리포트">
		<colgroup>
			<col width="5%" />
			<col width="5%" />

			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />

			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />

			<col width="5%" />

		</colgroup>
		<tr>
			<th colspan="2">구분</th>

			<th><%=STR_YEAR%>01</th>
			<th><%=STR_YEAR%>02</th>
			<th><%=STR_YEAR%>03</th>
			<th><%=STR_YEAR%>04</th>
			<th><%=STR_YEAR%>05</th>
			<th><%=STR_YEAR%>06</th>

			<th><%=STR_YEAR%>07</th>
			<th><%=STR_YEAR%>08</th>
			<th><%=STR_YEAR%>09</th>
			<th><%=STR_YEAR%>10</th>
			<th><%=STR_YEAR%>11</th>
			<th><%=STR_YEAR%>12</th>

			<th>합계</th>

		</tr>
		<tr>
			<th rowspan="6">실적</th>
			<th>서울</th>
			<td><%=obj0_0.getInt("COUNT_1")%></td>
			<td><%=obj0_0.getInt("COUNT_2")%></td>
			<td><%=obj0_0.getInt("COUNT_3")%></td>
			<td><%=obj0_0.getInt("COUNT_4")%></td>
			<td><%=obj0_0.getInt("COUNT_5")%></td>
			<td><%=obj0_0.getInt("COUNT_6")%></td>

			<td><%=obj0_0.getInt("COUNT_7")%></td>
			<td><%=obj0_0.getInt("COUNT_8")%></td>
			<td><%=obj0_0.getInt("COUNT_9")%></td>
			<td><%=obj0_0.getInt("COUNT_10")%></td>
			<td><%=obj0_0.getInt("COUNT_11")%></td>
			<td><%=obj0_0.getInt("COUNT_12")%></td>

			<td><%=obj0_0.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th>경기</th>
			<td><%=obj0_1.getInt("COUNT_1")%></td>
			<td><%=obj0_1.getInt("COUNT_2")%></td>
			<td><%=obj0_1.getInt("COUNT_3")%></td>
			<td><%=obj0_1.getInt("COUNT_4")%></td>
			<td><%=obj0_1.getInt("COUNT_5")%></td>
			<td><%=obj0_1.getInt("COUNT_6")%></td>

			<td><%=obj0_1.getInt("COUNT_7")%></td>
			<td><%=obj0_1.getInt("COUNT_8")%></td>
			<td><%=obj0_1.getInt("COUNT_9")%></td>
			<td><%=obj0_1.getInt("COUNT_10")%></td>
			<td><%=obj0_1.getInt("COUNT_11")%></td>
			<td><%=obj0_1.getInt("COUNT_12")%></td>

			<td><%=obj0_1.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th>중부</th>
			<td><%=obj0_2.getInt("COUNT_1")%></td>
			<td><%=obj0_2.getInt("COUNT_2")%></td>
			<td><%=obj0_2.getInt("COUNT_3")%></td>
			<td><%=obj0_2.getInt("COUNT_4")%></td>
			<td><%=obj0_2.getInt("COUNT_5")%></td>
			<td><%=obj0_2.getInt("COUNT_6")%></td>

			<td><%=obj0_2.getInt("COUNT_7")%></td>
			<td><%=obj0_2.getInt("COUNT_8")%></td>
			<td><%=obj0_2.getInt("COUNT_9")%></td>
			<td><%=obj0_2.getInt("COUNT_10")%></td>
			<td><%=obj0_2.getInt("COUNT_11")%></td>
			<td><%=obj0_2.getInt("COUNT_12")%></td>

			<td><%=obj0_2.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th>남부</th>
			<td><%=obj0_3.getInt("COUNT_1")%></td>
			<td><%=obj0_3.getInt("COUNT_2")%></td>
			<td><%=obj0_3.getInt("COUNT_3")%></td>
			<td><%=obj0_3.getInt("COUNT_4")%></td>
			<td><%=obj0_3.getInt("COUNT_5")%></td>
			<td><%=obj0_3.getInt("COUNT_6")%></td>

			<td><%=obj0_3.getInt("COUNT_7")%></td>
			<td><%=obj0_3.getInt("COUNT_8")%></td>
			<td><%=obj0_3.getInt("COUNT_9")%></td>
			<td><%=obj0_3.getInt("COUNT_10")%></td>
			<td><%=obj0_3.getInt("COUNT_11")%></td>
			<td><%=obj0_3.getInt("COUNT_12")%></td>

			<td><%=obj0_3.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th>미확인</th>
			<td><%=obj0_4.getInt("COUNT_1")%></td>
			<td><%=obj0_4.getInt("COUNT_2")%></td>
			<td><%=obj0_4.getInt("COUNT_3")%></td>
			<td><%=obj0_4.getInt("COUNT_4")%></td>
			<td><%=obj0_4.getInt("COUNT_5")%></td>
			<td><%=obj0_4.getInt("COUNT_6")%></td>

			<td><%=obj0_4.getInt("COUNT_7")%></td>
			<td><%=obj0_4.getInt("COUNT_8")%></td>
			<td><%=obj0_4.getInt("COUNT_9")%></td>
			<td><%=obj0_4.getInt("COUNT_10")%></td>
			<td><%=obj0_4.getInt("COUNT_11")%></td>
			<td><%=obj0_4.getInt("COUNT_12")%></td>

			<td><%=obj0_4.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th>전체</th>
			<td><%=obj0_5.getInt("COUNT_1")%></td>
			<td><%=obj0_5.getInt("COUNT_2")%></td>
			<td><%=obj0_5.getInt("COUNT_3")%></td>
			<td><%=obj0_5.getInt("COUNT_4")%></td>
			<td><%=obj0_5.getInt("COUNT_5")%></td>
			<td><%=obj0_5.getInt("COUNT_6")%></td>

			<td><%=obj0_5.getInt("COUNT_7")%></td>
			<td><%=obj0_5.getInt("COUNT_8")%></td>
			<td><%=obj0_5.getInt("COUNT_9")%></td>
			<td><%=obj0_5.getInt("COUNT_10")%></td>
			<td><%=obj0_5.getInt("COUNT_11")%></td>
			<td><%=obj0_5.getInt("COUNT_12")%></td>

			<td><%=obj0_5.getInt("COUNT_ALL")%></td>
		</tr>

		<tr>
			<th colspan="2">실적(UV)</th>
			<td><%=obj0_6.getInt("COUNT_1")%></td>
			<td><%=obj0_6.getInt("COUNT_2")%></td>
			<td><%=obj0_6.getInt("COUNT_3")%></td>
			<td><%=obj0_6.getInt("COUNT_4")%></td>
			<td><%=obj0_6.getInt("COUNT_5")%></td>
			<td><%=obj0_6.getInt("COUNT_6")%></td>

			<td><%=obj0_6.getInt("COUNT_7")%></td>
			<td><%=obj0_6.getInt("COUNT_8")%></td>
			<td><%=obj0_6.getInt("COUNT_9")%></td>
			<td><%=obj0_6.getInt("COUNT_10")%></td>
			<td><%=obj0_6.getInt("COUNT_11")%></td>
			<td><%=obj0_6.getInt("COUNT_12")%></td>

			<td><%=obj0_6.getInt("COUNT_ALL")%></td>
		</tr>
		
		<tr>
			<th colspan="2">실사용자</th>
			<td><%=obj0_7.getInt("COUNT_1")%></td>
			<td><%=obj0_7.getInt("COUNT_2")%></td>
			<td><%=obj0_7.getInt("COUNT_3")%></td>
			<td><%=obj0_7.getInt("COUNT_4")%></td>
			<td><%=obj0_7.getInt("COUNT_5")%></td>
			<td><%=obj0_7.getInt("COUNT_6")%></td>

			<td><%=obj0_7.getInt("COUNT_7")%></td>
			<td><%=obj0_7.getInt("COUNT_8")%></td>
			<td><%=obj0_7.getInt("COUNT_9")%></td>
			<td><%=obj0_7.getInt("COUNT_10")%></td>
			<td><%=obj0_7.getInt("COUNT_11")%></td>
			<td><%=obj0_7.getInt("COUNT_12")%></td>

			<td><%=obj0_7.getInt("COUNT_ALL")%></td>
		</tr>
	</table>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnPrint();">리포트 출력</a>
		<a class="btn04" href="javascript:OnClose();">닫기</a>
	</div>
</div>

<script type="text/javascript">
	
	var DATE = "<%=DATE%>";
	
	function OnClose()
	{
		self.close();
	}

	function OnPrint()
	{
		window.print();
	}

</script>

<jsp:include page="/WEB-INF/views/templates/bottom_pop.jsp"/>	