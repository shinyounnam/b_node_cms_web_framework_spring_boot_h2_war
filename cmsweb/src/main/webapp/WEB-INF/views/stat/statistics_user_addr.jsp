<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj = null;
%>


<!-- contents -->
<!-- contents -->
<div class="contents">
	<h2>근로자 주소 통계</h2>
	<div class="navi"><a href="">통계</a> &nbsp; > &nbsp; <span class="blue">근로자 주소 통계</span></div>		

	<div class="pt50"></div>
	<div class="arrow">
	<a href="javascript:OnPrint();" class="btn02" style="margin-left:12px;">리포트 보기</a>
	</div>
	<div class="pt10"></div>
	<div style="width:100%;text-align:left;">
		<span style="font-size:11pt">* 클릭하시면 시/군/구 상세 가입현황을 보실 수 있습니다.</span>
	</div>
	<table class="list_A" summary="가입 신청 현황">
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
			int i = 0;
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
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getString("AREA_NAME_1")%></a></td>
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getInt("COUNT_1")%></a></td>			
					<%
					}
					%>
					<%
					if ((i + 1) < data0.length()) {
						i = i + 1;
					%>
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getString("AREA_NAME_1")%></a></td>
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getInt("COUNT_1")%></a></td>		
					<%
					}
					%>
					<%
					if ((i + 1) < data0.length()) {
						i = i + 1;
					%>					
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getString("AREA_NAME_1")%></a></td>
					<td><a href="javascript:OnArea('<%=obj.getInt("AREA_CODE_1")%>')"><%=obj.getInt("COUNT_1")%></a></td>		
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
	
	<div class="pt10"></div>
	
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">엑셀 저장</a>
	</div>	
</div>

<script type="text/javascript">
	
	
	function OnPrint()
	{
		var url = "/stat_user_addr_report/info/END";
		OpenWin(url,"stat_user_addr_report",1000,600);
	}
	
	function OnArea(aAreaCode1)
	{
		var url = "/statistics_user_addr_area/info/"+aAreaCode1+"/END";
		location.href= url;
	}

	function OnExcel()
	{
		var url = j_xls_web_url + "/stat_user_addr.php";
		location.href = url;		
	}	
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	