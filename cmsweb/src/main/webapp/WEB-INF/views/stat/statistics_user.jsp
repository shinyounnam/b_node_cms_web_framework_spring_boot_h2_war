<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    String DATE = header.getString("DATE");    

    JSONObject obj0_0 = data.getJSONArray(0).getJSONObject(0);        
    JSONObject obj0_1 = data.getJSONArray(0).getJSONObject(1);
    JSONObject obj0_2 = data.getJSONArray(0).getJSONObject(2);
    JSONObject obj0_3 = data.getJSONArray(0).getJSONObject(3);
    JSONObject obj0_4 = data.getJSONArray(0).getJSONObject(4);
    JSONObject obj0_5 = data.getJSONArray(0).getJSONObject(5);
    JSONObject obj0_6 = data.getJSONArray(0).getJSONObject(6);
    JSONObject obj0_7 = data.getJSONArray(0).getJSONObject(7);

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);        
    JSONObject obj = null;   
    String STR_YEAR = obj1_0.getString("STR_YEAR");
    
%>

<!-- contents -->
<!-- contents -->
<div class="contents">
	<h2>근로자 통계</h2>
	<div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">근로자 통계</span></div>
	<div class="pt30"></div>
	<div class="aligncenter"  style=" border-top:1px solid #ebebeb; border-bottom:1px solid #ebebeb; padding:12px 0; ">&nbsp; 
		<input type="text" id="DATE" name="DATE" value="<%=STR_YEAR%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>

	<div class="pt50"></div>
	<div class="arrow">
	<a href="javascript:OnPrint();" class="btn02" style="margin-left:12px;">리포트 보기</a>
	</div>
	<div class="pt10"></div>
	<table class="list_A" summary="가입 신청 현황">
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

	<div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">엑셀 저장</a>
	</div>
	
</div>


<script type="text/javascript">
	
	var DATE = "<%=DATE%>";
	
	function OnSearch()
	{
		var DATE = $("#DATE").val();
		location.href = "/statistics_user/move" +
				"/" + DATE+
				"/END";
	}

	function OnPrint()
	{
		var url = "/stat_user_report/info/"+$("#DATE").val()+"/END";
		OpenWin(url,"stat_user_report",1000,600);
	}
	
	$(document).ready(function() {
		
		$("#DATE").datepicker({ showOn: 'button', 
			buttonImageOnly: true, 
			dateFormat:'yy',
			buttonImage: '/images/day.gif' });
		
	});

	function OnExcel()
	{
		var url = j_xls_web_url + "/stat_user.php?DATE="+$("#DATE").val();
		location.href = url;
	}

</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	