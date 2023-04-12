<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header");       
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");    
    int PAGE = header.getInt("PAGE");
    String G_EXCEL_URL = header.getString("G_EXCEL_URL");

    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
    JSONObject obj = null;
%>


<!-- contents -->
<div class="contents">
	<h2>휴면 예정 리스트</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <span class="blue">휴면 예정 리스트(직업소개소)</span></div>	
	<div class="pt30"></div>
	<div class="floatright">
		
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		String searchType5 = "";
		String searchType6 = "";
		String searchType7 = "";
		if (SEARCH_TYPE.equals("ALL")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("ID")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("NAME")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("REP_NAME")) searchType4 = "selected";
		if (SEARCH_TYPE.equals("REP_TEL")) searchType5 = "selected";		
		if (SEARCH_TYPE.equals("MAN_TEL")) searchType6 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:130px;">
			<option value="ALL" <%=searchType1%> >전체</option>
			<option value="ID" <%=searchType2%> >아이디</option>
			<option value="NAME" <%=searchType3%> >회사명</option>
			<option value="REP_NAME" <%=searchType4%> >대표자</option>
			<option value="REP_TEL" <%=searchType5%> >대표자연락처</option>
			<option value="MAN_NAME" <%=searchType6%> >담당자연락처</option>			
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<input type="hidden" id="PAGE" name="PAGE" value="<%=PAGE%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="8%" />
			<col width="10%" />
			<col width="*" />
			<col width="10%" />
			<col width="10%" />
			<col width="10%" />			
            <col width="10%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>아이디</th>
			<th>회사명</th>
			<th>대표자</th>
			<th>대표자연락처</th>
			<th>담당저연락처</th>
			<th>최근로그인</th>						
		</tr>	
		<%
		int i = 0;
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);            
		%>
		<tr>
			<td><%=pageSeq%></td>
			<td><%=obj.getString("ID")%></td>
			<td><%=obj.getString("CORP_NAME")%></td>
			<td><%=obj.getString("REP_NAME")%></td>
			<td><%=obj.getString("REP_TEL")%></td>
            <td><%=obj.getString("MAN_TEL")%></td>
            <td><%=obj.getString("LST_LOGIN_DATE")%></td>			
		</tr>
		<%
		pageIndex = pageIndex + 1;
		}
		%>
		
	</table>
	<script>
		var totalPage = '<%=obj1_0.getInt("TOTAL_PAGE")%>';
		if (totalPage > 0)
		{
			document.write(PageMake('<%=PAGE%>','10','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}		
	</script>

    <div class="pt10"></div>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnExcel();" class="btn02" style="margin-left:12px;">리스트 엑셀 저장</a>
	</div>
</div>

<script type="text/javascript">
	function OnPage(aPage)
	{		
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage ;		
		location.href = "/vendor_restore_month/move" +
				"?search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}
	

	function OnSearch()
	{			
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();		
		var PAGE = "1";		
		location.href = "/vendor_restore_month/move"+
				"?search_type=" + SEARCH_TYPE+
				"&search_txt="+encodeURIComponent(SEARCH_TXT)+
				"&page="+PAGE;
	}

    function OnExcel()
	{
        var g_excel_url = "<%=G_EXCEL_URL%>";
		var url = g_excel_url + "/vendor_restore_month.php?SEARCH_TYPE="+$("#SEARCH_TYPE").val();
		url += "&SEARCH_TXT="+$("#SEARCH_TXT").val();
		location.href = url;
	}
</script>


<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	