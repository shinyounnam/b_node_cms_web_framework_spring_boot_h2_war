<%@page import="org.json.*"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<%
    JSONArray data = (JSONArray)request.getAttribute("data");    
    JSONObject header = (JSONObject)request.getAttribute("header"); 
    String SEARCH_TYPE = header.getString("SEARCH_TYPE");
    String SEARCH_TXT = header.getString("SEARCH_TXT");
    int PAGE = header.getInt("PAGE");
    int i = 0;
    JSONObject obj = null;
    JSONObject obj1_0 = data.getJSONArray(1).getJSONObject(0);
%>

<!-- contents -->
<div class="contents">
	<h2>써치 회원</h2>
	<div class="navi"><a href="">회원관리</a> &nbsp; > &nbsp; <span class="blue">써치 회원</span></div>
	<div class="pt30"></div>
	<div class="floatleft  arrow">총 <span class="blue bold"><%=obj1_0.getInt("TOTAL_COUNT")%></span>개소</div>
	<div class="floatright">
		정렬&nbsp;:&nbsp;
		<%
		String searchType1 = "";
		String searchType2 = "";
		String searchType3 = "";
		String searchType4 = "";
		if (SEARCH_TYPE.equals("1")) searchType1 = "selected";
		if (SEARCH_TYPE.equals("2")) searchType2 = "selected";
		if (SEARCH_TYPE.equals("3")) searchType3 = "selected";
		if (SEARCH_TYPE.equals("4")) searchType4 = "selected";
		%>
		<select id="SEARCH_TYPE" name="SEARCH_TYPE" style="width:130px;">
			<option value="1" <%=searchType1%> >직업소개소명</option>
		</select>
		<input type="text" id="SEARCH_TXT" name="SEARCH_TXT" style="width:100px;" value="<%=SEARCH_TXT%>"/>
		<a href="javascript:OnSearch();" class="btn01">검색</a>
	</div>
	<div class="pt30"></div>
	<table class="list_A" summary="가입 신청 현황">
		<colgroup>
			<col width="8%" />
			<col width="*%" />
			<col width="20%" />
			<col width="20%" />
			<col width="10%" />
			<col width="10%" />
		</colgroup>
		<tr>
			<th>NO</th>
			<th>직업소개소 명</th>
			<th>담당자</th>
			<th>담당자연락처</th>
			<th>정보 수정일</th>
			<th>도구</th>
		</tr>	
		<%
		
		int nStartCount = (PAGE-1)*15;
		int pageIndex = 0;
		int pageSeq = 0;
        JSONArray data0 = data.getJSONArray(0);
		for (i=0;i<data0.length();i++) {
            obj = data0.getJSONObject(i);
		    pageSeq = (obj1_0.getInt("TOTAL_COUNT") - pageIndex) - ((PAGE-1)*15);
		    nStartCount = nStartCount + 1;
		%>
		<tr>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=pageSeq%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("CORP_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_NAME")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("MAN_TEL")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')"><%=obj.getString("UPD_DATE")%></a></td>
			<td><a href="javascript:OnDetail('<%=obj.getInt("CORP_ID")%>')" class="btn01">수정</a></td>
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
			document.write(PageMake('<%=PAGE%>','15','<%=obj1_0.getInt("TOTAL_PAGE")%>'));
		}
		function OnDetail(aCorpID)
		{
			location.href = "/corp_user_update/info/"+aCorpID;
		}
	</script>
	<div style="width:100%;text-align:right;">
		<a href="javascript:OnInsertCorpUser()" class="btn01">써치회사등록</a>
	</div>
</div>
<script type="text/javascript">
	function OnPage(aPage	)
	{
		var SEARCH_TYPE = $("#SEARCH_TYPE").val();
		var SEARCH_TXT = $("#SEARCH_TXT").val();
		var PAGE = aPage ;
		if (SEARCH_TXT=="") SEARCH_TXT = " ";
		location.href = "/user_search_mng/move" +
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
		location.href = "/user_search_mng/move" +
				"/" + SEARCH_TYPE+
				"/"+SEARCH_TXT+
				"/"+PAGE;
	}
	function OnInsertCorpUser()
	{
		location.href="/corp_user_add/info";
	}
</script>

<jsp:include page="/WEB-INF/views/templates/bottom.jsp"/>	