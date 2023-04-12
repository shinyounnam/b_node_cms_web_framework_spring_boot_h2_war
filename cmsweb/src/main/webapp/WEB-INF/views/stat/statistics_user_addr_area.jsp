<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");
    JSONObject obj = null;
    int i = 0;
    String AREA_CODE1 = header.getString("AREA_CODE1");
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
	
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />
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
				<th>시/군/구</th>
				<th>가입수</th>
				<th>시/도</th>
				<th>시/군/구</th>
				<th>가입수</th>
				<th>시/도</th>
				<th>시/군/구</th>
				<th>가입수</th>
			</tr>
			<%
			
			int nCount = 0;
			String strAreaCode1 = "";
            JSONArray data0 = data.getJSONArray(0);
			if (data0.length() > 0) {
				for (i = 0; i < data0.length(); i ++) {										
                    obj = data0.getJSONObject(i);
			%>			
				<%
				if (nCount == 0)
				{
				%>
				<tr>
				<%
				}
				if (i < data0.length()) {			
					if (strAreaCode1.equals("")==false && 
                        strAreaCode1.equals(obj.getString("AREA_CODE_1"))==false)
					{
					%>
					</tr><tr>
					<%
					nCount = 0;
					}
					strAreaCode1 = obj.getString("AREA_CODE_1");
				%>
				<td><%=obj.getString("AREA_NAME_1")%></td>
				<td><%=obj.getString("AREA_NAME_2")%></td>
				<td><%=obj.getInt("COUNT_1")%></td>	
				<%
					nCount = nCount + 1;					
				}			
				%>
				<%
				if (nCount == 3) {
				%>
					</tr>
				<%
					nCount = 0;
				}
				%>
				<%	
				if ((i+1) < data0.length()) {
					i = i + 1;		
					if (strAreaCode1.equals("")==false && 
                        strAreaCode1.equals(obj.getString("AREA_CODE_1"))==false)
					{
					%>
					</tr><tr>
					<%
					nCount = 0;
					}								
					strAreaCode1 = obj.getString("AREA_CODE_1");
				%>
				<td><%=obj.getString("AREA_NAME_1")%></td>
				<td><%=obj.getString("AREA_NAME_2")%></td>
				<td><%=obj.getInt("COUNT_1")%></td>			
				<%
					nCount = nCount + 1;
				}
				%>
				<%
				if (nCount == 3) {
				%>
					</tr>
				<%
					nCount = 0;
				}
				%>
				<%
				if ((i+1) < data0.length()) {
					i = i + 1;	
					if (strAreaCode1.equals("")==false && 
                        strAreaCode1.equals(obj.getString("AREA_CODE_1"))==false)		
					{
					%>
					</tr><tr>
					<%
					nCount = 0;
					}									
					strAreaCode1 = obj.getString("AREA_CODE_1");
				%>
				<td><%=obj.getString("AREA_NAME_1")%></td>
				<td><%=obj.getString("AREA_NAME_2")%></td>
				<td><%=obj.getInt("COUNT_1")%></td>			
				<%
					nCount = nCount + 1;
				}
				%>
				<%
				if (nCount == 3) {
				%>
					</tr>
				<%
					nCount = 0;
				}
				%>
			<%
				}
			}
			%>
		</tbody>
		
	</table>
	<input type="hidden" id="AREA_CODE1" name="AREA_CODE1" value="<%=AREA_CODE1%>"/>
	<div class="pt10"></div>
	<div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnList();" class="btn02" style="margin-left:12px;">목록</a>
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">엑셀 저장</a>
		<a href="javascript:OnExcelEmpList();" class="btn02" style="margin-left:12px;">근로자 리스트 엑셀 저장</a>
		<a href="javascript:OnExcelEmpJobList();" class="btn02" style="margin-left:12px;">근로자 리스트 직종 엑셀 저장</a>
	</div>

</div>
<script type="text/javascript">
	
	
	function OnPrint()
	{
		var url = "/stat_user_addr_area_report/info/"+$("#AREA_CODE1").val()+"/END";
		OpenWin(url,"stat_user_addr_area_report",1000,600);
	}

	function OnList()
	{
		var url = "/statistics_user_addr";
		location.href = url;
	}

	function OnExcel()
	{
		var url = j_xls_web_url + "/stat_user_addr_area.php?AREA_CODE1=" + $("#AREA_CODE1").val();
		location.href = url;		
	}

	function OnExcelEmpList()
	{
		var url = j_xls_web_url + "/stat_user_addr_tel_list.php?AREA_CODE1=" + $("#AREA_CODE1").val();
		location.href = url;		
	}

	function OnExcelEmpJobList()
	{
		var url = j_xls_web_url + "/stat_user_addr_tel_job_list.php?AREA_CODE1=" + $("#AREA_CODE1").val();
		location.href = url;		
	}
	
</script>



<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>