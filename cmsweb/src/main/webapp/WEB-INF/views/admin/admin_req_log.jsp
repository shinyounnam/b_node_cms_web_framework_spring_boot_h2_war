<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");

    
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");    

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);    
	int i = 0;
    JSONObject obj = null;   
%>

<!-- contents -->
<div class="contents">
	<h2>관리자 위치정보 요청자료</h2>
	<div class="navi"><a href="">관리자 관리</a> &nbsp; > &nbsp; <span class="blue">관리자 위치정보 요청자료</span></div>
	<div class="pt30"></div>
	<div class="alignright">
		<%
		String searchType1 = "";
		String searchType2 = "";
		if (SEARCH_TYPE.equals("1")) { searchType1="selected"; }
		if (SEARCH_TYPE.equals("2")) { searchType2="selected"; }
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:200px;">
			<option value="1" <%=searchType1%> >요청자</option>
			<option value="2" <%=searchType2%> >목적</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:200px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="6%" />
			<col width="15%" />
			<col width="15%" />
			<col width="*%" />
			<col width="15%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>취급자</th>
			<th>요청자</th>
			<th>목적</th>
			<th>일시</th>
		</tr>
		<%
		int nPAGE = PAGE;
		int nSEQ = (nPAGE-1)*15;
		JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
			obj = data0.getJSONObject(i);
			nSEQ = nSEQ + 1;
		%>
		<tr>
			<td><%=nSEQ%></td>
			<td><%=obj.getString("ID")%></td>
			<td><%=obj.getString("REQ_NAME")%></td>
			<td><%=obj.getString("CONTENT")%></td>
			<td><%=obj.getString("LOG_DATE")%></td>
		</tr>
		<%
		}
		%>
	</table>
	<script>
		var totalPage = '<%=obj1_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','20','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
	</script>
	<div class="pt30"></div>
	<div class="alignright">
		<a class="btn03" href="javascript:OnAdminReqLogSave();">개인정보 요청 등록</a>
	</div>
	

</div>
<!-- contents -->
<script type="text/javascript">
	
	function OnPage(aPage)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_req_log/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	

	function OnSearch()
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		if (SEARCH_TXT == "") SEARCH_TXT = " ";
		var PAGE = "1";
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/admin_req_log/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	
	function OnAdminReqLogSave()
	{
		location.href="/admin_req_log_save";
	}

	function OnUpdate(aAdminID)
	{
		location.href="/admin_req_log_update/info/"+aAdminID;
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	